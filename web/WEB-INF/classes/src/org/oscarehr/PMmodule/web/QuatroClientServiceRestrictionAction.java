package org.oscarehr.PMmodule.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import org.oscarehr.PMmodule.exception.ClientAlreadyRestrictedException;
import org.oscarehr.PMmodule.model.Admission;
import org.oscarehr.PMmodule.model.Agency;
import org.oscarehr.PMmodule.model.Bed;
import org.oscarehr.PMmodule.model.BedDemographic;
import org.oscarehr.PMmodule.model.Consent;
import org.oscarehr.PMmodule.model.Demographic;
import org.oscarehr.PMmodule.model.DemographicExt;
import org.oscarehr.PMmodule.model.Facility;
import org.oscarehr.PMmodule.model.HealthSafety;
import org.oscarehr.PMmodule.model.Intake;
import org.oscarehr.PMmodule.model.JointAdmission;
import org.oscarehr.PMmodule.model.Program;
import org.oscarehr.PMmodule.model.ProgramClientRestriction;
import org.oscarehr.PMmodule.model.ProgramProvider;
import org.oscarehr.PMmodule.model.Provider;
import org.oscarehr.PMmodule.model.Room;
import org.oscarehr.PMmodule.model.RoomDemographic;
import org.oscarehr.PMmodule.model.Demographic.ConsentGiven;
import org.oscarehr.PMmodule.service.AdmissionManager;
import org.oscarehr.PMmodule.service.BedDemographicManager;
import org.oscarehr.PMmodule.service.BedManager;
import org.oscarehr.PMmodule.service.ClientManager;
import org.oscarehr.PMmodule.service.HealthSafetyManager;
import org.oscarehr.PMmodule.service.ProgramManager;
import org.oscarehr.PMmodule.service.ProviderManager;
import org.oscarehr.PMmodule.service.RoomDemographicManager;
import org.oscarehr.PMmodule.service.RoomManager;
import org.oscarehr.PMmodule.service.ClientRestrictionManager;
import org.oscarehr.PMmodule.web.formbean.ClientManagerFormBean;
import org.oscarehr.PMmodule.web.utils.UserRoleUtils;
import org.oscarehr.casemgmt.service.CaseManagementManager;
import org.oscarehr.util.SessionConstants;

import com.quatro.common.KeyConstants;
import com.quatro.service.LookupManager;
import com.quatro.service.security.SecurityManager;

import oscar.oscarDemographic.data.DemographicRelationship;

public class QuatroClientServiceRestrictionAction  extends BaseAction {
   private ClientManager clientManager;
   private ProviderManager providerManager;
   private ProgramManager programManager;
   private AdmissionManager admissionManager;
   private CaseManagementManager caseManagementManager;
   private BedDemographicManager bedDemographicManager;
   private RoomDemographicManager roomDemographicManager;
   private RoomManager roomManager;
   private BedManager bedManager;
   private ClientRestrictionManager clientRestrictionManager;
   private LookupManager lookupManager;


   public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	   
       return list(mapping, form, request, response);
   }
   public ActionForward terminate_early(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	   
       return edit(mapping, form, request, response);
   }
   public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       setEditAttributes(form, request);
       super.setScreenMode(request, KeyConstants.TAB_RESTRICTION);
       return mapping.findForward("edit");
   }
   public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	   setListAttributes(form, request);
	   super.setScreenMode(request, KeyConstants.TAB_RESTRICTION);
       return mapping.findForward("list");
   }
   public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	   DynaActionForm clientForm = (DynaActionForm) form;
       ProgramClientRestriction restriction = (ProgramClientRestriction) clientForm.get("serviceRestriction");
       Integer days = (Integer) clientForm.get("serviceRestrictionLength");
       super.setScreenMode(request, KeyConstants.TAB_RESTRICTION);
       Program p = (Program) clientForm.get("program");
       restriction.setProgramId(p.getId());
       Integer cId =(Integer)request.getSession().getAttribute(KeyConstants.SESSION_KEY_CLIENTID);
       restriction.setDemographicNo(cId);
       restriction.setStartDate(restriction.getStartDate());
       String providerNo=(String)request.getSession().getAttribute("user");
       restriction.setProviderNo(providerNo);
       Calendar cal = new GregorianCalendar();
       cal.setTime(new Date());
       cal.set(Calendar.HOUR, 23);
       cal.set(Calendar.MINUTE, 59);
       cal.set(Calendar.SECOND, 59);
       cal.set(Calendar.DATE, cal.get(Calendar.DATE) + days);
       restriction.setEndDate(cal.getTime());
       restriction.setEnabled(true);

       boolean success;
       try {
           clientRestrictionManager.saveClientRestriction(restriction);
           success = true;
       }
       catch (ClientAlreadyRestrictedException e) {
           ActionMessages messages = new ActionMessages();
           messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("restrict.already_restricted"));
           saveMessages(request, messages);
           success = false;
       }

       if (success) {
           ActionMessages messages = new ActionMessages();
           messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("restrict.success"));
           saveMessages(request, messages);
       }
       clientForm.set("program", new Program());
       clientForm.set("serviceRestriction", restriction);
       //clientForm.set("serviceRestrictionLength", null);       
       setEditAttributes(form, request);
       //logManager.log("write", "service_restriction", id, request);

       return mapping.findForward("edit");

       
   }
   private void setEditAttributes(ActionForm form, HttpServletRequest request) {
       DynaActionForm clientForm = (DynaActionForm) form;

       HashMap actionParam = (HashMap) request.getAttribute("actionParam");
       if(actionParam==null){
    	  actionParam = new HashMap();
          actionParam.put("clientId", request.getParameter("clientId")); 
       }
       request.setAttribute("actionParam", actionParam);
       String demographicNo= (String)actionParam.get("clientId");
       
       ClientManagerFormBean tabBean = (ClientManagerFormBean) clientForm.get("view");

       Integer facilityId=(Integer)request.getSession().getAttribute(SessionConstants.CURRENT_FACILITY_ID);
       
       request.setAttribute("clientId", demographicNo);
       request.setAttribute("client", clientManager.getClientByDemographicNo(demographicNo));

       DemographicExt demographicExtConsent = clientManager.getDemographicExt(Integer.parseInt(demographicNo), Demographic.CONSENT_GIVEN_KEY);
       DemographicExt demographicExtConsentMethod = clientManager.getDemographicExt(Integer.parseInt(demographicNo), Demographic.METHOD_OBTAINED_KEY);

       ConsentGiven consentGiven = ConsentGiven.NONE;
       if (demographicExtConsent != null) consentGiven = ConsentGiven.valueOf(demographicExtConsent.getValue());

       Demographic.MethodObtained methodObtained = Demographic.MethodObtained.IMPLICIT;
       if (demographicExtConsentMethod != null) methodObtained = Demographic.MethodObtained.valueOf(demographicExtConsentMethod.getValue());

       String providerNo = ((Provider) request.getSession().getAttribute("provider")).getProviderNo();


       // check role permission
       HttpSession se = request.getSession();
       List admissions = admissionManager.getCurrentAdmissions(Integer.valueOf(demographicNo));
       for (Iterator it = admissions.iterator(); it.hasNext();) {
           Admission admission = (Admission) it.next();
           Integer inProgramId = admission.getProgramId();
           String inProgramType = admission.getProgramType();
           if ("service".equalsIgnoreCase(inProgramType)) {
               se.setAttribute("performDischargeService", hasAccess(request, inProgramId, "_pmm_clientDischarge", SecurityManager.ACCESS_UPDATE));
               se.setAttribute("performAdmissionService", hasAccess(request, inProgramId,"_pmm_clientAdmission", SecurityManager.ACCESS_UPDATE));

           }
           else if ("bed".equalsIgnoreCase(inProgramType)) {
               se.setAttribute("performDischargeBed", hasAccess(request, inProgramId,"_pmm_clientDischarge", SecurityManager.ACCESS_UPDATE));
               se.setAttribute("performAdmissionBed", hasAccess(request, inProgramId,"_pmm_clientAdmission", SecurityManager.ACCESS_UPDATE));
               se.setAttribute("performBedAssignments",hasAccess(request, inProgramId,"_pmm_clientAdmission", SecurityManager.ACCESS_UPDATE));
           }
       }

       // tab override - from survey module
       String tabOverride = (String) request.getAttribute("tab.override");

       if (tabOverride != null && tabOverride.length() > 0) {
           tabBean.setTab(tabOverride);
       }

       if (tabBean.getTab().equals("Summary")) {

           // request.setAttribute("admissions", admissionManager.getCurrentAdmissions(Integer.valueOf(demographicNo)));
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

       }

       /* service restrictions */
//       if (tabBean.getTab().equals("Service Restrictions")) {
           request.setAttribute("serviceRestrictions", clientRestrictionManager.getActiveRestrictionsForClient(Integer.valueOf(demographicNo), facilityId, new Date()));
           request.setAttribute("serviceRestrictionList",lookupManager.LoadCodeList("SRT",true, null, null));
//       }

   }

   private void setListAttributes(ActionForm form, HttpServletRequest request) {
       DynaActionForm clientForm = (DynaActionForm) form;

       HashMap actionParam = (HashMap) request.getAttribute("actionParam");
       if(actionParam==null){
    	  actionParam = new HashMap();
          actionParam.put("clientId", request.getParameter("clientId")); 
       }
       request.setAttribute("actionParam", actionParam);
       String demographicNo= (String)actionParam.get("clientId");
       
       ClientManagerFormBean tabBean = (ClientManagerFormBean) clientForm.get("view");

       Integer facilityId=(Integer)request.getSession().getAttribute(SessionConstants.CURRENT_FACILITY_ID);
       
       request.setAttribute("clientId", demographicNo);
       request.setAttribute("client", clientManager.getClientByDemographicNo(demographicNo));

      
       String providerNo = ((Provider) request.getSession().getAttribute("provider")).getProviderNo();
       

       // check role permission
       HttpSession se = request.getSession();
       List admissions = admissionManager.getCurrentAdmissions(Integer.valueOf(demographicNo));
       for (Iterator it = admissions.iterator(); it.hasNext();) {
           Admission admission = (Admission) it.next();
           Integer inProgramId = admission.getProgramId();
           String inProgramType = admission.getProgramType();
           if ("service".equalsIgnoreCase(inProgramType)) {
               se.setAttribute("performDischargeService", hasAccess(request, inProgramId, "_pmm_clientDischarge", SecurityManager.ACCESS_UPDATE));
               se.setAttribute("performAdmissionService", hasAccess(request, inProgramId,"_pmm_clientAdmission", SecurityManager.ACCESS_UPDATE));

           }
           else if ("bed".equalsIgnoreCase(inProgramType)) {
               se.setAttribute("performDischargeBed", hasAccess(request, inProgramId,"_pmm_clientDischarge", SecurityManager.ACCESS_UPDATE));
               se.setAttribute("performAdmissionBed", hasAccess(request, inProgramId,"_pmm_clientAdmission", SecurityManager.ACCESS_UPDATE));
               se.setAttribute("performBedAssignments",hasAccess(request, inProgramId,"_pmm_clientAdmission", SecurityManager.ACCESS_UPDATE));
           }
       }

       // tab override - from survey module
       String tabOverride = (String) request.getAttribute("tab.override");

       if (tabOverride != null && tabOverride.length() > 0) {
           tabBean.setTab(tabOverride);
       }

       if (tabBean.getTab().equals("Summary")) {

           // request.setAttribute("admissions", admissionManager.getCurrentAdmissions(Integer.valueOf(demographicNo)));
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

       }

       /* service restrictions */
       	   List proPrograms = providerManager.getProgramDomain(providerNo);
           //request.setAttribute("serviceRestrictions", clientRestrictionManager.getActiveRestrictionsForClient(Integer.valueOf(demographicNo), facilityId, new Date()));
       	  request.setAttribute("serviceRestrictions", clientRestrictionManager.getAllRestrictionsForClient(Integer.valueOf(demographicNo),proPrograms));

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

   public void setBedManager(BedManager bedManager) {
	 this.bedManager = bedManager;
   }

public void setClientRestrictionManager(
		ClientRestrictionManager clientRestrictionManager) {
	this.clientRestrictionManager = clientRestrictionManager;
}

public void setLookupManager(LookupManager lookupManager) {
	this.lookupManager = lookupManager;
}
private Boolean hasAccess(HttpServletRequest request, Integer programId, String function, String right)
{
    SecurityManager sec = (SecurityManager)request.getSession().getAttribute(KeyConstants.SESSION_KEY_SECURITY_MANAGER);
    String orgCd = "P" + programId.toString();
    return sec.GetAccess(function,orgCd).compareTo(right) >= 0;
}

}
