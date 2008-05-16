package org.oscarehr.PMmodule.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.util.LabelValueBean;

import org.apache.struts.action.DynaActionForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;
//import org.oscarehr.PMmodule.model.Admission;
import org.oscarehr.PMmodule.model.Bed;
import org.oscarehr.PMmodule.model.Program;
import org.oscarehr.PMmodule.model.ProgramClientRestriction;
import org.oscarehr.PMmodule.model.Room;
import org.oscarehr.PMmodule.service.AdmissionManager;
import org.oscarehr.PMmodule.service.ClientRestrictionManager;
import org.oscarehr.PMmodule.service.QuatroAdmissionManager;
import org.oscarehr.PMmodule.service.BedDemographicManager;
import org.oscarehr.PMmodule.service.BedManager;
import org.oscarehr.PMmodule.service.ClientManager;
import org.oscarehr.PMmodule.service.ProgramManager;
import org.oscarehr.PMmodule.service.RoomDemographicManager;
import org.oscarehr.PMmodule.service.RoomManager;
//import org.oscarehr.PMmodule.web.formbean.ClientManagerFormBean;
import org.oscarehr.PMmodule.web.utils.UserRoleUtils;
import org.oscarehr.casemgmt.service.CaseManagementManager;
import org.oscarehr.util.SessionConstants;
import com.quatro.service.LookupManager;
import com.quatro.service.IntakeManager;
import com.quatro.common.KeyConstants;

import org.oscarehr.PMmodule.model.QuatroIntakeDB;
import org.oscarehr.PMmodule.model.QuatroAdmission;
import oscar.MyDateFormat;
import org.oscarehr.PMmodule.web.formbean.QuatroClientAdmissionForm;
import org.oscarehr.PMmodule.model.QuatroIntake;

public class QuatroClientAdmissionAction  extends DispatchAction {
   private ClientManager clientManager;
   private LookupManager lookupManager;
   private ProgramManager programManager;
   private AdmissionManager admissionManager2;
   private QuatroAdmissionManager admissionManager;
   private CaseManagementManager caseManagementManager;
   private BedDemographicManager bedDemographicManager;
   private RoomDemographicManager roomDemographicManager;
   private RoomManager roomManager;
   private BedManager bedManager;
   private IntakeManager intakeManager;
   private ClientRestrictionManager clientRestrictionManager;

   public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       return list(mapping, form, request, response);
   }

   public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       QuatroClientAdmissionForm clientForm = (QuatroClientAdmissionForm) form;

       HashMap actionParam = (HashMap) request.getAttribute("actionParam");
       if(actionParam==null){
    	  actionParam = new HashMap();
          actionParam.put("clientId", request.getParameter("clientId")); 
       }
       request.setAttribute("actionParam", actionParam);
       String demographicNo= (String)actionParam.get("clientId");
       
       Integer facilityId=(Integer)request.getSession().getAttribute(SessionConstants.CURRENT_FACILITY_ID);
       request.setAttribute("clientId", demographicNo);

       String providerNo = (String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
       List lstAdmission = admissionManager.getAdmissionList(Integer.valueOf(demographicNo), facilityId, providerNo);
       request.setAttribute("quatroAdmission", lstAdmission);

       return mapping.findForward("list");
   }

   public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       setEditAttributes(form, request);
       return mapping.findForward("edit");
   }
   
   public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       ActionMessages messages = new ActionMessages();
       boolean isError = false;
       boolean isWarning = false;
       QuatroClientAdmissionForm clientForm = (QuatroClientAdmissionForm) form;

       Integer clientId = clientForm.getClientId();
       Integer intakeId = clientForm.getIntakeId();
       Integer programId = clientForm.getProgramId();
       Integer admissionId = clientForm.getAdmissionId();
       Integer facilityId=(Integer)request.getSession().getAttribute(SessionConstants.CURRENT_FACILITY_ID);

       //don't check these if intake admitted.
       if(admissionId.intValue()==0){
         //service restriction check
	     ProgramClientRestriction restrInPlace = clientRestrictionManager.checkClientRestriction(
			   programId.intValue(), clientId.intValue(), new Date());
         if (restrInPlace != null) {
    	   Program program = programManager.getProgram(programId); 
	       messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.intake.admission.service_restriction",
              			request.getContextPath(), program.getName()));
           isError = true;
           saveMessages(request,messages);
           setEditAttributes(form, request);
           return mapping.findForward("edit");
	     }

         //check client active in other program
         List lst=admissionManager.getAdmissionList(clientId, facilityId);
         if(lst.size()>0){
        	QuatroAdmission admission_exist = (QuatroAdmission)lst.get(0);
        	StringBuilder sb = new StringBuilder();
        	if(admission_exist.getAdmissionStatus().equals(KeyConstants.INTAKE_STATUS_ADMITTED)){
        	  sb.append("," + admission_exist.getId());
        	}
            if(sb.length()>0){
              String admissionIds=sb.substring(1);
      	      //auto-discharge from other program   
              admissionManager.dischargeAdmission(admissionIds);
            }
         }
       }
       
       String providerNo = (String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
       QuatroAdmission admission = new QuatroAdmission();
       admission.setId(admissionId);
       admission.setClientId(clientId);
       admission.setProgramId(programId);
       admission.setProviderNo(providerNo);
       admission.setAdmissionDate(Calendar.getInstance());
       admission.setAdmissionStatus(KeyConstants.INTAKE_STATUS_ADMITTED);
       admission.setIntakeId(intakeId);
       admission.setFacilityId(facilityId);
       admission.setResidentStatus(clientForm.getResidentStatus());
       admission.setPrimaryWorker(clientForm.getPrimaryWorker());
       admission.setLockerNo(clientForm.getLockerNo());
       admission.setNoOfBags(clientForm.getNoOfBags());
       admission.setNextKinName(clientForm.getNextKinName());

       admission.setNextKinRelationship(clientForm.getNextKinRelationship());
       admission.setNextKinTelephone(clientForm.getNextKinTelephone());
       admission.setNextKinNumber(clientForm.getNextKinNumber());
       admission.setNextKinStreet(clientForm.getNextKinStreet());
       admission.setNextKinCity(clientForm.getNextKinCity());
       admission.setNextKinProvince(clientForm.getNextKinProvince());
       admission.setNextKinPostal(clientForm.getNextKinPostal());
       admission.setOvPassStartDate(MyDateFormat.getCalendar(clientForm.getOvPassStartDate()));
       admission.setOvPassEndDate(MyDateFormat.getCalendar(clientForm.getOvPassEndDate()));
       admission.setNotSignReason(clientForm.getNotSignReason());
       
       if(admission.getId().intValue()==0){
    	  QuatroIntake intake = intakeManager.getQuatroIntake(intakeId); 
          admissionManager.saveAdmission(admission, intakeId, intake.getQueueId(), intake.getReferralId());
       }else{
          admissionManager.updateAdmission(admission);
       }
       
	   if(!(isWarning || isError)) messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.save.success", request.getContextPath()));
       saveMessages(request,messages);

 	   clientForm.setAdmissionId(admission.getId());
       setEditAttributes(form, request);
       
       return mapping.findForward("edit");
   }

   public ActionForward sign(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       setEditAttributes(form, request);
       return mapping.findForward("edit");
   }

   private void setEditAttributes(ActionForm form, HttpServletRequest request) {
	   QuatroClientAdmissionForm clientForm = (QuatroClientAdmissionForm) form;

       Integer programId=null;
       Integer intakeId = null;
       String clientId = null;
       
       HashMap actionParam = (HashMap) request.getAttribute("actionParam");
       if(actionParam==null){
    	  actionParam = new HashMap();
    	  clientId=clientForm.getClientId().toString();
          actionParam.put("clientId", clientId);
          //from program queue list page
          if(clientForm.getIntakeId()==null){
        	  Integer queueId=Integer.valueOf(request.getParameter("queueId"));
              QuatroIntakeDB intakeDB =  intakeManager.getQuatroIntakeDBByQueueId(queueId);
              if(intakeDB!=null){
                intakeId = intakeDB.getId();
                actionParam.put("intakeId", intakeId);
                programId=intakeDB.getProgramId();
              }else{
                intakeId = 0;
                actionParam.put("intakeId", 0);
                programId = 0;
              }
              clientForm.setProgramId(programId);
              clientForm.setIntakeId(intakeId);
          //from pages under client mamagement
          }else{
             intakeId=clientForm.getIntakeId();
        	 actionParam.put("intakeId", intakeId);
             if(clientForm.getProgramId()!=null){
                programId = clientForm.getProgramId();
              }else{
                QuatroIntakeDB intakeDB =  intakeManager.getQuatroIntakeDBByIntakeId(intakeId);
                programId= intakeDB.getProgramId();
                clientForm.setProgramId(programId);
              }
          }
       }
       request.setAttribute("actionParam", actionParam);
       
       if(clientForm.getAdmissionId()==null){
          QuatroAdmission admission = admissionManager.getAdmission(intakeId);
          if(admission != null){
        	  clientForm.setAdmissionId(admission.getId());
          }else{
        	  clientForm.setAdmissionId(0);
          }
       }
       
       Integer facilityId=(Integer)request.getSession().getAttribute(KeyConstants.SESSION_KEY_FACILITYID);
       
       clientId = (String)actionParam.get("clientId");
       request.setAttribute("clientId", clientId);

       String providerNo = (String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);

//create exception when save new admission, ask for temporary_admission value that we don't use at all.       
/*
       // check role permission
       HttpSession se = request.getSession();
       List admissions = admissionManager2.getCurrentAdmissions(Integer.valueOf(clientId));
       for (Iterator it = admissions.iterator(); it.hasNext();) {
           Admission admission = (Admission) it.next();
           String inProgramId = String.valueOf(admission.getProgramId());
           String inProgramType = admission.getProgramType();
           if ("service".equalsIgnoreCase(inProgramType)) {
               se.setAttribute("performDischargeService", new Boolean(caseManagementManager.hasAccessRight("perform discharges", "access", providerNo, clientId, inProgramId)));
               se.setAttribute("performAdmissionService", new Boolean(caseManagementManager.hasAccessRight("perform admissions", "access", providerNo, clientId, inProgramId)));

           }
           else if ("bed".equalsIgnoreCase(inProgramType)) {
               se.setAttribute("performDischargeBed", new Boolean(caseManagementManager.hasAccessRight("perform discharges", "access", providerNo, clientId, inProgramId)));
               se.setAttribute("performAdmissionBed", new Boolean(caseManagementManager.hasAccessRight("perform admissions", "access", providerNo, clientId, inProgramId)));
               se.setAttribute("performBedAssignments", new Boolean(caseManagementManager.hasAccessRight("perform bed assignments", "access", providerNo, clientId, inProgramId)));

           }
       }
*/
       
	   List provinceList = lookupManager.LoadCodeList("POV",true, null, null);
	   clientForm.setProvinceList(provinceList);

	   List notSignReasonList = lookupManager.LoadCodeList("RNS",true, null, null);
       clientForm.setNotSignReasonList(notSignReasonList);
       
       //service programs don't need admission and queue, intake is enough. 
       Room[] availableRooms = roomManager.getAvailableRooms(facilityId, programId, Boolean.TRUE, clientId);
       clientForm.setAvailableRooms(availableRooms);

       Bed[] availableBeds=new Bed[1];
       availableBeds[0] = new Bed();
       availableBeds[0].setId(0);
       availableBeds[0].setName("N/A");
       if(clientForm.getRoomId()!=null && clientForm.getRoomId().intValue()>0){
         availableBeds = bedManager.getBedsByRoom(clientForm.getRoomId());
       }
       clientForm.setAvailableBeds(availableBeds);

   }

   public void setAdmissionManager(QuatroAdmissionManager admissionManager) {
	 this.admissionManager = admissionManager;
   }

   public void setAdmissionManager2(AdmissionManager admissionManager2) {
		 this.admissionManager2 = admissionManager2;
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

   public void setRoomDemographicManager(RoomDemographicManager roomDemographicManager) {
	 this.roomDemographicManager = roomDemographicManager;
   }

   public void setRoomManager(RoomManager roomManager) {
	 this.roomManager = roomManager;
   }

   public void setBedManager(BedManager bedManager) {
	 this.bedManager = bedManager;
   }

   public void setLookupManager(LookupManager lookupManager) {
	 this.lookupManager = lookupManager;
   }

   public void setIntakeManager(IntakeManager intakeManager) {
	 this.intakeManager = intakeManager;
   }

   public void setClientRestrictionManager(ClientRestrictionManager clientRestrictionManager) {
	 this.clientRestrictionManager = clientRestrictionManager;
   }
   
}
