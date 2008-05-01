package org.oscarehr.PMmodule.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.DynaActionForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;

import org.oscarehr.PMmodule.service.ClientManager;
import org.oscarehr.PMmodule.service.ProviderManager;
import org.oscarehr.PMmodule.service.ProgramManager;
import org.oscarehr.PMmodule.service.AdmissionManager;
import org.oscarehr.casemgmt.service.CaseManagementManager;
import org.oscarehr.PMmodule.service.ConsentManager;
import org.oscarehr.PMmodule.service.BedDemographicManager;
import org.oscarehr.PMmodule.service.RoomDemographicManager;
import org.oscarehr.PMmodule.service.RoomManager;


import org.oscarehr.PMmodule.model.Admission;
import org.oscarehr.PMmodule.model.Agency;
import org.oscarehr.PMmodule.model.BedDemographic;
import org.oscarehr.PMmodule.model.Consent;
import org.oscarehr.PMmodule.model.Demographic;
import org.oscarehr.PMmodule.model.DemographicExt;
import org.oscarehr.PMmodule.model.HealthSafety;
import org.oscarehr.PMmodule.model.JointAdmission;
import org.oscarehr.PMmodule.model.Program;
import org.oscarehr.PMmodule.model.ProgramProvider;
import org.oscarehr.PMmodule.model.Provider;
import org.oscarehr.PMmodule.model.Room;
import org.oscarehr.PMmodule.model.RoomDemographic;
import org.oscarehr.PMmodule.model.Demographic.ConsentGiven;
import org.oscarehr.PMmodule.service.HealthSafetyManager;
import org.oscarehr.PMmodule.web.formbean.ClientManagerFormBean;
import org.oscarehr.PMmodule.web.utils.UserRoleUtils;
import org.oscarehr.util.SessionConstants;

import oscar.oscarDemographic.data.DemographicRelationship;

import com.quatro.common.KeyConstants;

public class QuatroClientSummaryAction extends DispatchAction {

   private HealthSafetyManager healthSafetyManager;
   private ClientManager clientManager;
   private ProviderManager providerManager;
   private ProgramManager programManager;
   private AdmissionManager admissionManager;
   private CaseManagementManager caseManagementManager;
   private ConsentManager consentManager;
   private BedDemographicManager bedDemographicManager;
   private RoomDemographicManager roomDemographicManager;
   private RoomManager roomManager;
   
   public static final String ID = "id";
   
   public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
/*
	   DynaActionForm clientForm = (DynaActionForm) form;
       clientForm.set("view", new ClientManagerFormBean());
       Integer clientId = (Integer)request.getSession().getAttribute("clientId");
       if (clientId != null) request.setAttribute(ID, clientId.toString());
*/
       return edit(mapping, form, request, response);
   }

   public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
/*
	   String id = request.getParameter("id");
       Integer facilityId= (Integer)request.getSession().getAttribute("currentFacilityId");

       if (id == null || id.equals("")) {
           Object o = request.getAttribute("demographicNo");

           if (o instanceof String) {
               id = (String) o;
           }

           if (o instanceof Long) {
               id = String.valueOf((Long) o);
           }
       }
       if (id == null || id.equals("")) {
       	id=(String) request.getAttribute(ID);
       }
*/
       setEditAttributes(form, request);

/*       
       String roles = (String) request.getSession().getAttribute("userrole");

       Demographic demographic = clientManager.getClientByDemographicNo(id);
       request.getSession().setAttribute("clientGender", demographic.getSex());
       request.getSession().setAttribute("clientAge", demographic.getAge());
*/
       return mapping.findForward("edit");
   }

   public ActionForward save_joint_admission(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       JointAdmission jadmission = new JointAdmission();

       String headClientId = request.getParameter("headClientId");
       String clientId = request.getParameter("dependentClientId");
       String type = request.getParameter("type");
       Long headInteger = new Long(headClientId);
       Long clientInteger = new Long(clientId);

       jadmission.setAdmissionDate(new Date());
       jadmission.setHeadClientId(headInteger);
       jadmission.setArchived(false);
       jadmission.setClientId(clientInteger);
       jadmission.setProviderNo((String) request.getSession().getAttribute("user"));
       jadmission.setTypeId(new Long(type));
       System.out.println(jadmission.toString());
       clientManager.saveJointAdmission(jadmission);
       setEditAttributes(form, request, (String) request.getParameter("clientId"));

       HashMap actionParam = (HashMap) request.getAttribute("actionParam");
       if(actionParam==null){
    	  actionParam = new HashMap();
          actionParam.put("id", request.getParameter("clientId")); 
       }
       request.setAttribute("actionParam", actionParam);
       
       return mapping.findForward("edit");
   }
   
   public ActionForward remove_joint_admission(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       String clientId = request.getParameter("dependentClientId");
       clientManager.removeJointAdmission(new Long(clientId), (String) request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO));
       setEditAttributes(form, request, (String) request.getParameter("clientId"));

       HashMap actionParam = (HashMap) request.getAttribute("actionParam");
       if(actionParam==null){
    	  actionParam = new HashMap();
          actionParam.put("id", request.getParameter("clientId")); 
       }
       request.setAttribute("actionParam", actionParam);

       return mapping.findForward("edit");
   }
   
   private void setEditAttributes(ActionForm form, HttpServletRequest request) {
       DynaActionForm clientForm = (DynaActionForm) form;
       
       HashMap actionParam = (HashMap) request.getAttribute("actionParam");
       if(actionParam==null){
    	  actionParam = new HashMap();
          actionParam.put("id", request.getParameter("id")); 
//        don't delete sample code below for html:link parameter       
//        actionParam.put("id", "200492"); 
//        actionParam.put("floatProperty", new Float(444.0)); 
//        actionParam.put("intProperty", new Integer(555)); 
//        actionParam.put("stringArray", new String[] { "Value 1", "Value 2", "Value 3" }); 
       }

       request.setAttribute("actionParam", actionParam);

       String demographicNo= (String)actionParam.get("id");
       setEditAttributes(form, request, demographicNo);
   }
   
   private void setEditAttributes(ActionForm form, HttpServletRequest request, String demographicNo) {
       
       Integer facilityId=(Integer)request.getSession().getAttribute(SessionConstants.CURRENT_FACILITY_ID);
       
       request.setAttribute("id", demographicNo);
       request.setAttribute("client", clientManager.getClientByDemographicNo(demographicNo));

       DemographicExt demographicExtConsent = clientManager.getDemographicExt(Integer.parseInt(demographicNo), Demographic.CONSENT_GIVEN_KEY);
       DemographicExt demographicExtConsentMethod = clientManager.getDemographicExt(Integer.parseInt(demographicNo), Demographic.METHOD_OBTAINED_KEY);

       ConsentGiven consentGiven = ConsentGiven.NONE;
       if (demographicExtConsent != null) consentGiven = ConsentGiven.valueOf(demographicExtConsent.getValue());

       Demographic.MethodObtained methodObtained = Demographic.MethodObtained.IMPLICIT;
       if (demographicExtConsentMethod != null) methodObtained = Demographic.MethodObtained.valueOf(demographicExtConsentMethod.getValue());

//       request.setAttribute("consentStatus", consentGiven.name());
//       request.setAttribute("consentMethod", methodObtained.name());
//       boolean consentStatusChecked = Demographic.ConsentGiven.ALL == consentGiven || Demographic.ConsentGiven.CIRCLE_OF_CARE == consentGiven;
//       request.setAttribute("consentCheckBoxState", consentStatusChecked ? "checked=\"checked\"" : "");

       String providerNo = ((Provider) request.getSession().getAttribute("provider")).getProviderNo();

       // program domain
       List<Program> programDomain = new ArrayList<Program>();

       for (Iterator<?> i = providerManager.getProgramDomain(providerNo).iterator(); i.hasNext();) {
           ProgramProvider programProvider = (ProgramProvider) i.next();
           programDomain.add(programManager.getProgram(programProvider.getProgramId()));
       }

       request.setAttribute("programDomain", programDomain);

       // check role permission
       HttpSession se = request.getSession();
       List admissions = admissionManager.getCurrentAdmissions(Integer.valueOf(demographicNo));
       for (Iterator it = admissions.iterator(); it.hasNext();) {
           Admission admission = (Admission) it.next();
           String inProgramId = String.valueOf(admission.getProgramId());
           String inProgramType = admission.getProgramType();
           if ("service".equalsIgnoreCase(inProgramType)) {
               se.setAttribute("performDischargeService", new Boolean(caseManagementManager.hasAccessRight("perform discharges", "access", providerNo, demographicNo, inProgramId)));
               se.setAttribute("performAdmissionService", new Boolean(caseManagementManager.hasAccessRight("perform admissions", "access", providerNo, demographicNo, inProgramId)));

           }
           else if ("bed".equalsIgnoreCase(inProgramType)) {
               se.setAttribute("performDischargeBed", new Boolean(caseManagementManager.hasAccessRight("perform discharges", "access", providerNo, demographicNo, inProgramId)));
               se.setAttribute("performAdmissionBed", new Boolean(caseManagementManager.hasAccessRight("perform admissions", "access", providerNo, demographicNo, inProgramId)));
               se.setAttribute("performBedAssignments", new Boolean(caseManagementManager.hasAccessRight("perform bed assignments", "access", providerNo, demographicNo, inProgramId)));

           }
       }

       // tab override - from survey module
//       String tabOverride = (String) request.getAttribute("tab.override");

//       if (tabOverride != null && tabOverride.length() > 0) {
//           tabBean.setTab(tabOverride);
//       }

//       if (tabBean.getTab().equals("Summary")) {

           // only allow bed/service programs show up.(not external program)
           List currentAdmissionList = admissionManager.getCurrentAdmissionsByFacility(Integer.valueOf(demographicNo), facilityId);
           List bedServiceList = new ArrayList();
           for (Iterator ad = currentAdmissionList.iterator(); ad.hasNext();) {
               Admission admission1 = (Admission) ad.next();
               if ("External".equalsIgnoreCase(programManager.getProgram(admission1.getProgramId()).getType())) {
                   continue;
               }
               bedServiceList.add(admission1);
           }
           request.setAttribute("admissions", bedServiceList);

           HealthSafety healthsafety = healthSafetyManager.getHealthSafetyByDemographic(Long.valueOf(demographicNo));
           request.setAttribute("healthsafety", healthsafety);

           Consent consent = consentManager.getMostRecentConsent(Long.valueOf(demographicNo));
           request.setAttribute("consent", consent);

           request.setAttribute("referrals", clientManager.getActiveReferrals(demographicNo, String.valueOf(facilityId)));
//       }

           
       List<?> currentAdmissions = admissionManager.getCurrentAdmissions(Integer.valueOf(demographicNo));

       /* bed reservation view */
       BedDemographic bedDemographic = bedDemographicManager.getBedDemographicByDemographic(Integer.valueOf(demographicNo), facilityId);
       request.setAttribute("bedDemographic", bedDemographic);
       
       RoomDemographic roomDemographic = roomDemographicManager.getRoomDemographicByDemographic(Integer.valueOf(demographicNo), facilityId);

		if(roomDemographic != null){
			Integer roomIdInt = roomDemographic.getId().getRoomId();
			Room room = null;
			if(roomIdInt != null){
				room = roomManager.getRoom(roomIdInt);
			}
			if(room != null){
				roomDemographic.setRoom(room);
			}
		}
		request.setAttribute("roomDemographic", roomDemographic);

       /* Relations */
       DemographicRelationship demoRelation = new DemographicRelationship();
       ArrayList<Hashtable> relList = demoRelation.getDemographicRelationshipsWithNamePhone(demographicNo, facilityId);
       List<JointAdmission> list = clientManager.getDependents(new Long(demographicNo));
       JointAdmission clientsJadm = clientManager.getJointAdmission(new Long(demographicNo));
       int familySize = list.size() + 1;
       if (familySize > 1) {
           request.setAttribute("groupHead", "yes");
       }
       if (clientsJadm != null) {
           request.setAttribute("dependentOn", clientsJadm.getHeadClientId());
           List depList = clientManager.getDependents(clientsJadm.getHeadClientId());
           familySize = depList.size() + 1;
           Demographic headClientDemo = clientManager.getClientByDemographicNo("" + clientsJadm.getHeadClientId());
           request.setAttribute("groupName", headClientDemo.getFormattedName() + " Group");
       }

       if (relList != null && relList.size() > 0) {
           for (Hashtable h : relList) {
               String demographic = (String) h.get("demographicNo");
               Long demoLong = new Long(demographic);
               JointAdmission demoJadm = clientManager.getJointAdmission(demoLong);
               System.out.println("DEMO JADM: " + demoJadm);

               // IS PERSON JOINTLY ADMITTED WITH ME, They will either have the same HeadClient or be my headClient
               if (clientsJadm != null && clientsJadm.getHeadClientId().longValue() == demoLong) { // they're my head client
                   h.put("jointAdmission", "head");
               }
               else if (demoJadm != null && clientsJadm != null && clientsJadm.getHeadClientId().longValue() == demoJadm.getHeadClientId().longValue()) {
                   // They depend on the same person i do!
                   h.put("jointAdmission", "dependent");
               }
               else if (demoJadm != null && demoJadm.getHeadClientId().longValue() == new Long(demographicNo).longValue()) {
                   // They depend on me
                   h.put("jointAdmission", "dependent");
               }
               // Can this person be added to my depended List
               if (clientsJadm == null && demoJadm == null && clientManager.getDependents(demoLong).size() == 0) {
                   // yes if - i am not dependent on anyone
                   // - this person is not dependent on someone
                   // - this person is not a head of a family already
                   h.put("dependentable", "yes");
               }
               if (demoJadm != null) { // DEPENDS ON SOMEONE
                   h.put("dependentOn", demoJadm.getHeadClientId());
                   if (demoJadm.getHeadClientId().longValue() == new Long(demographicNo).longValue()) {
                       h.put("dependent", demoJadm.getTypeId());
                   }
               }
               else if (clientsJadm != null && clientsJadm.getHeadClientId().longValue() == demoLong) { // HEAD PERSON WON'T DEPEND ON ANYONE
                   h.put("dependent", new Long(0));
               }
           }
           request.setAttribute("relations", relList);
           request.setAttribute("relationSize", familySize);

       }
   }
   
   public void setHealthSafetyManager(HealthSafetyManager healthSafetyManager) {
	 this.healthSafetyManager = healthSafetyManager;
   }


   public void setAdmissionManager(AdmissionManager admissionManager) {
	 this.admissionManager = admissionManager;
   }

   public void setBedDemographicManager(BedDemographicManager bedDemographicManager) {
	 this.bedDemographicManager = bedDemographicManager;
   }

   public void setCaseManagementManager(CaseManagementManager caseManagementManager) {
	 this.caseManagementManager = caseManagementManager;
   }

   public void setClientManager(ClientManager clientManager) {
	 this.clientManager = clientManager;
   }

   public void setConsentManager(ConsentManager consentManager) {
	 this.consentManager = consentManager;
   }

   public void setProgramManager(ProgramManager programManager) {
	 this.programManager = programManager;
   }

   public void setProviderManager(ProviderManager providerManager) {
	 this.providerManager = providerManager;
   }

   public void setRoomDemographicManager(RoomDemographicManager roomDemographicManager) {
	 this.roomDemographicManager = roomDemographicManager;
   }

   public void setRoomManager(RoomManager roomManager) {
 	 this.roomManager = roomManager;
   }
   
}
