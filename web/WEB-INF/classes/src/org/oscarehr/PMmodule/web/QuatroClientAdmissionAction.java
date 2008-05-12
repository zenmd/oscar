package org.oscarehr.PMmodule.web;

import java.util.ArrayList;
import java.util.Calendar;
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
import org.oscarehr.PMmodule.model.Admission;
import org.oscarehr.PMmodule.model.Bed;
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
import org.oscarehr.PMmodule.service.AdmissionManager;
import org.oscarehr.PMmodule.service.QuatroAdmissionManager;
import org.oscarehr.PMmodule.service.BedDemographicManager;
import org.oscarehr.PMmodule.service.BedManager;
import org.oscarehr.PMmodule.service.ClientManager;
import org.oscarehr.PMmodule.service.HealthSafetyManager;
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

   public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       return edit(mapping, form, request, response);
   }

   public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       setEditAttributes(form, request);
       return mapping.findForward("edit");
   }
   
   public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       ActionMessages messages = new ActionMessages();
       boolean isError = false;
       boolean isWarning = false;
       QuatroClientAdmissionForm qform = (QuatroClientAdmissionForm) form;

       Integer clientId = qform.getClientId();
       Integer intakeId = qform.getIntakeId();
       Integer programId = qform.getProgramId();
       Integer facilityId=(Integer)request.getSession().getAttribute(SessionConstants.CURRENT_FACILITY_ID);

       QuatroAdmission admission = new QuatroAdmission();
       admission.setId(0);
       admission.setClientId(clientId);
       admission.setProgramId(programId);
       admission.setAdmissionDate(Calendar.getInstance());
       admission.setAdmissionStatus(KeyConstants.INTAKE_STATUS_ADMITTED);
       admission.setIntakeId(intakeId);
       admission.setFacilityId(facilityId);
       admission.setResidentStatus(qform.getResidentStatus());
       admission.setPrimaryWorker(qform.getPrimaryWorker());
       admission.setLockerNo(qform.getLockerNo());
       admission.setNoOfBags(qform.getNoOfBags());
       admission.setNextKinName(qform.getNextKinName());

       admission.setNextKinRelationship(qform.getNextKinRelationship());
       admission.setNextKinTelephone(qform.getNextKinTelephone());
       admission.setNextKinNumber(qform.getNextKinNumber());
       admission.setNextKinStreet(qform.getNextKinStreet());
       admission.setNextKinCity(qform.getNextKinCity());
       admission.setNextKinProvince(qform.getNextKinProvince());
       admission.setNextKinPostal(qform.getNextKinPostal());
       admission.setOvPassStartDate(MyDateFormat.getCalendar(qform.getOvPassStartDate()));
       admission.setOvPassEndDate(MyDateFormat.getCalendar(qform.getOvPassEndDate()));
       admission.setNotSignReason(qform.getNotSignReason());
       
       
       admissionManager.saveAdmission(admission);
       
	   if(!(isWarning || isError)) messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.intake.admission.saved", request.getContextPath()));
       saveMessages(request,messages);

       return mapping.findForward("edit");
   }

   public ActionForward sign(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       setEditAttributes(form, request);
       return mapping.findForward("edit");
   }

   private void setEditAttributes(ActionForm form, HttpServletRequest request) {
       DynaActionForm clientForm = (DynaActionForm) form;

       HashMap actionParam = (HashMap) request.getAttribute("actionParam");
       if(actionParam==null){
    	  actionParam = new HashMap();
          actionParam.put("clientId", request.getParameter("clientId"));
          if(request.getParameter("intakeId")==null){
        	  Integer queueId=Integer.valueOf(request.getParameter("queueId"));
              QuatroIntakeDB intakeDB =  intakeManager.getQuatroIntakeDBByQueueId(queueId);
              if(intakeDB!=null){
                actionParam.put("intakeId", intakeDB.getId());
              }else{
                actionParam.put("intakeId", 0);
              }  
          }else{
             actionParam.put("intakeId", request.getParameter("intakeId"));
          }
       }
       request.setAttribute("actionParam", actionParam);

       String demographicNo= (String)actionParam.get("clientId");
       Integer intakeId = (Integer)actionParam.get("intakeId");
       
       Integer facilityId=(Integer)request.getSession().getAttribute(SessionConstants.CURRENT_FACILITY_ID);
       
       request.setAttribute("clientId", demographicNo);
       request.setAttribute("intakeId", intakeId);
       request.setAttribute("client", clientManager.getClientByDemographicNo(demographicNo));

       String providerNo = (String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);


       // check role permission
       HttpSession se = request.getSession();
       List admissions = admissionManager2.getCurrentAdmissions(Integer.valueOf(demographicNo));
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

	   List provinceList = lookupManager.LoadCodeList("POV",true, null, null);
	   clientForm.set("provinceList", provinceList);

	   List notSignReasonList = lookupManager.LoadCodeList("RNS",true, null, null);
       clientForm.set("notSignReasonList", notSignReasonList);
       
	   
       // only allow bed/service programs show up.(not external program)
       List currentAdmissionList = admissionManager2.getCurrentAdmissionsByFacility(Integer.valueOf(demographicNo), facilityId);
       List bedServiceList = new ArrayList();
       for (Iterator ad = currentAdmissionList.iterator(); ad.hasNext();) {
           Admission admission1 = (Admission) ad.next();
           if ("External".equalsIgnoreCase(programManager.getProgram(admission1.getProgramId()).getType())) {
               continue;
           }
           bedServiceList.add(admission1);
       }
       request.setAttribute("admissions", bedServiceList);


       
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

       
//       if (tabBean.getTab().equals("Bed/Room Reservation")) {
           boolean isRefreshRoomDropDown = false;
           if (request.getAttribute("isRefreshRoomDropDown") != null && request.getAttribute("isRefreshRoomDropDown").equals("Y")) {
               isRefreshRoomDropDown = true;
           }
           else {
               isRefreshRoomDropDown = false;
           }

           String roomId = request.getParameter("roomId");
           if (roomDemographic != null && roomId == null) {
               roomId = roomDemographic.getId().getRoomId().toString();
           }

           // set bed program id
           Admission bedProgramAdmission = admissionManager2.getCurrentBedProgramAdmission(Integer.valueOf(demographicNo));
           Integer bedProgramId = null;
           if(bedProgramAdmission != null){
           	bedProgramId = (bedProgramAdmission != null) ? bedProgramAdmission.getProgramId() : null;
           }
           request.setAttribute("bedProgramId", bedProgramId);

           Bed reservedBed = null;

           if (bedDemographic == null) {
               bedDemographic = BedDemographic.create(Integer.valueOf(demographicNo), bedDemographicManager.getDefaultBedDemographicStatus(), providerNo);

               if (roomDemographic != null) {
                   bedDemographic.setReservationStart(roomDemographic.getAssignStart());
                   bedDemographic.setReservationEnd(roomDemographic.getAssignEnd());
               }

               reservedBed = null;

           }
           else {

               reservedBed = bedManager.getBed(bedDemographic.getBedId());
           }

           if (isRefreshRoomDropDown) {
               bedDemographic.setRoomId(Integer.valueOf(roomId));
           }

           clientForm.set("bedDemographic", bedDemographic);

           Room[] availableRooms = roomManager.getAvailableRooms(facilityId, bedProgramId, Boolean.TRUE, demographicNo);
           
           request.setAttribute("availableRooms", availableRooms);

           if ((isRefreshRoomDropDown && roomId != null) || (reservedBed == null && !"0".equals(roomId))) {
               request.setAttribute("roomId", roomId);
           }
           else if (reservedBed != null) {
               request.setAttribute("roomId", reservedBed.getRoomId().toString());
           }
           else {
               request.setAttribute("roomId", "0");
           }
           request.setAttribute( "isAssignedBed", String.valueOf(roomManager.isAssignedBed((String)request.getAttribute("roomId"), availableRooms ) ) ); 
           										
           // retrieve an array of beds associated with this roomId
           Bed[] unreservedBeds = null;

           if (isRefreshRoomDropDown && request.getAttribute("unreservedBeds") != null) {
               unreservedBeds = (Bed[]) request.getAttribute("unreservedBeds");

           }
           else if (reservedBed != null) {

               // unreservedBeds = bedManager.getBedsByRoomProgram(availableRooms, bedProgramId, false);
               unreservedBeds = bedManager.getCurrentPlusUnreservedBedsByRoom(reservedBed.getRoomId(), bedDemographic.getId().getBedId(), false);
           }

           clientForm.set("unreservedBeds", unreservedBeds);

           // set bed demographic statuses
           clientForm.set("bedDemographicStatuses", bedDemographicManager.getBedDemographicStatuses());
//       }
           
       List<?> currentAdmissions = admissionManager2.getCurrentAdmissions(Integer.valueOf(demographicNo));

   }

   public void setQuatroAdmissionManager(QuatroAdmissionManager admissionManager) {
	 this.admissionManager = admissionManager;
   }

   public void setAdmissionManager(AdmissionManager admissionManager2) {
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
   
}
