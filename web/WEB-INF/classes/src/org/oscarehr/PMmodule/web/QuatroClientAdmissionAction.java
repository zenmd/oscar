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
import org.oscarehr.PMmodule.model.Bed;
import org.oscarehr.PMmodule.model.Program;
import org.oscarehr.PMmodule.model.ProgramClientRestriction;
import org.oscarehr.PMmodule.model.Room;
import org.oscarehr.PMmodule.service.AdmissionManager;
import org.oscarehr.PMmodule.service.ClientRestrictionManager;
import org.oscarehr.PMmodule.service.BedDemographicManager;
import org.oscarehr.PMmodule.service.BedManager;
import org.oscarehr.PMmodule.service.ClientManager;
import org.oscarehr.PMmodule.service.ProgramManager;
import org.oscarehr.PMmodule.service.ProviderManager;
import org.oscarehr.PMmodule.service.RoomDemographicManager;
import org.oscarehr.PMmodule.service.RoomManager;
//import org.oscarehr.PMmodule.web.formbean.ClientManagerFormBean;
import org.oscarehr.PMmodule.web.utils.UserRoleUtils;
import org.oscarehr.casemgmt.service.CaseManagementManager;
import org.oscarehr.util.SessionConstants;
import com.quatro.service.LookupManager;
import com.quatro.service.IntakeManager;
import com.quatro.common.KeyConstants;
import org.oscarehr.PMmodule.model.Provider;
import org.oscarehr.PMmodule.model.QuatroIntakeDB;
import org.oscarehr.PMmodule.model.Admission;
import oscar.MyDateFormat;
import org.oscarehr.PMmodule.web.formbean.QuatroClientAdmissionForm;
import org.oscarehr.PMmodule.model.QuatroIntake;

import org.oscarehr.PMmodule.model.RoomDemographic;
import org.oscarehr.PMmodule.model.BedDemographic;
import org.oscarehr.PMmodule.model.RoomDemographicPK;
import org.oscarehr.PMmodule.model.BedDemographicPK;

public class QuatroClientAdmissionAction  extends BaseClientAction {
   private ClientManager clientManager;
   private LookupManager lookupManager;
   private ProgramManager programManager;
   private AdmissionManager admissionManager;
   private CaseManagementManager caseManagementManager;
   private BedDemographicManager bedDemographicManager;
   private RoomDemographicManager roomDemographicManager;
   private RoomManager roomManager;
   private BedManager bedManager;
   private IntakeManager intakeManager;
   private ProviderManager providerManager;
   private ClientRestrictionManager clientRestrictionManager;

   public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       return list(mapping, form, request, response);
   }

   public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       QuatroClientAdmissionForm clientForm = (QuatroClientAdmissionForm) form;
       super.setScreenMode(request, KeyConstants.TAB_CLIENT_ADMISSION);
       HashMap actionParam = (HashMap) request.getAttribute("actionParam");
       if(actionParam==null){
    	  actionParam = new HashMap();
          actionParam.put("clientId", request.getParameter("clientId")); 
       }
       request.setAttribute("actionParam", actionParam);
       String demographicNo= (String)actionParam.get("clientId");
       
       Integer facilityId=(Integer)request.getSession().getAttribute(SessionConstants.CURRENT_FACILITY_ID);
       request.setAttribute("clientId", demographicNo);
       request.setAttribute("client", clientManager.getClientByDemographicNo(demographicNo));
       String providerNo = (String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
       List lstAdmission = admissionManager.getAdmissionList(Integer.valueOf(demographicNo), facilityId, providerNo);
       request.setAttribute("admission", lstAdmission);

       return mapping.findForward("list");
   }
   
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       return update(mapping, form, request, response);
   }
   	
   public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       setEditAttributes(form, request);
       super.setScreenMode(request, KeyConstants.TAB_CLIENT_ADMISSION);
       return mapping.findForward("edit");
   }
   
   public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       ActionMessages messages = new ActionMessages();
       super.setScreenMode(request, KeyConstants.TAB_CLIENT_ADMISSION);
       boolean isError = false;
       boolean isWarning = false;
       QuatroClientAdmissionForm clientForm = (QuatroClientAdmissionForm) form;

       Admission admission = clientForm.getAdmission();
       Integer clientId = admission.getClientId();
       Integer intakeId = admission.getIntakeId();
       Integer programId = admission.getProgramId();
       Integer admissionId = admission.getId();
       Integer facilityId=(Integer)request.getSession().getAttribute(SessionConstants.CURRENT_FACILITY_ID);
       request.setAttribute("client", clientManager.getClientByDemographicNo(clientId.toString()));
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
         List lst=admissionManager.getIntakeAdmissionList(clientId);
         for(int i=0;i<lst.size();i++){
        	Admission admission_exist = (Admission)lst.get(0);
        	StringBuilder sb = new StringBuilder();
        	sb.append("," + admission_exist.getId());
            if(sb.length()>0){
              //auto-discharge from other program   
              admissionManager.dischargeAdmission(sb.substring(1));
            }
         }
       }
       
       String providerNo = (String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
	   admission.setProviderNo(providerNo);
       admission.setAdmissionDate(MyDateFormat.getCalendar(admission.getAdmissionDateTxt()));
       admission.setFacilityId(facilityId);
       admission.setOvPassStartDate(MyDateFormat.getCalendar(admission.getOvPassStartDateTxt()));
       admission.setOvPassEndDate(MyDateFormat.getCalendar(admission.getOvPassEndDateTxt()));
       
       RoomDemographic roomDemographic = clientForm.getRoomDemographic();
       BedDemographic bedDemographic = clientForm.getBedDemographic();
 	   //suppose auto-discharge deleted current roomdemographic/beddemographic record already.
 	   RoomDemographicPK rdmPK= roomDemographic.getId();
 	   rdmPK.setDemographicNo(admission.getClientId());
 	   roomDemographic.setId(rdmPK);
 	   roomDemographic.setProviderNo(providerNo);
 	   roomDemographic.setAssignStart(new Date());
 	   BedDemographicPK bdmPK= bedDemographic.getId();
 	   bdmPK.setDemographicNo(admission.getClientId());
 	   bedDemographic.setId(bdmPK);
 	   bedDemographic.setProviderNo(providerNo);
 	   bedDemographic.setReservationStart(new Date());

       if(admission.getId().intValue()==0){
    	  QuatroIntake intake = intakeManager.getQuatroIntake(intakeId); 
    	  admissionManager.saveAdmission(admission, intakeId, intake.getQueueId(), 
   			  intake.getReferralId(),roomDemographic,bedDemographic, false);
       }else{
    	  admissionManager.updateAdmission(admission, roomDemographic,bedDemographic, false);
       }
       
	   if(!(isWarning || isError)) messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.save.success", request.getContextPath()));
       saveMessages(request,messages);

 	   clientForm.setAdmission(admission);
 	   clientForm.setRoomDemographic(roomDemographic);
 	   clientForm.setBedDemographic(bedDemographic);
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
       Integer admissionId =null;
       String clientId = null;
       Integer facilityId=(Integer)request.getSession().getAttribute(SessionConstants.CURRENT_FACILITY_ID);
       
       RoomDemographic rdm=null;
       BedDemographic bdm=null;
       HashMap actionParam = (HashMap) request.getAttribute("actionParam");
       if(actionParam==null){
    	  actionParam = new HashMap();
          //from program queue list page
          if(request.getParameter("queueId")!=null){
        	  clientId=request.getParameter("clientId");
              actionParam.put("clientId", clientId);
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
              Admission admission = new Admission(); 
              admission.setProgramId(programId);
              admission.setIntakeId(intakeId);
              admission.setClientId(Integer.valueOf(clientId));
              admission.setAdmissionDate(Calendar.getInstance());
              admission.setAdmissionDateTxt(MyDateFormat.getStandardDate(admission.getAdmissionDate()));
              admission.setAdmissionStatus(KeyConstants.INTAKE_STATUS_ADMITTED);
              clientForm.setAdmission(admission);
              
          //from pages under client mamagement
          }else{
        	if(request.getParameter("admissionId")!=null){
        	   admissionId = Integer.valueOf(request.getParameter("admissionId"));
        	 }else{
        		 admissionId = clientForm.getAdmission().getId();
        	 }
        	 Admission admission = admissionManager.getAdmissionByAdmissionId(admissionId);
       	     clientId=admission.getClientId().toString();
             programId = admission.getProgramId();
       	     admission.setAdmissionDateTxt(MyDateFormat.getStandardDate(admission.getAdmissionDate()));
       	     admission.setOvPassStartDateTxt(MyDateFormat.getStandardDate(admission.getOvPassStartDate()));
       	     admission.setOvPassEndDateTxt(MyDateFormat.getStandardDate(admission.getOvPassStartDate()));
             clientForm.setAdmission(admission);
         	 rdm = roomDemographicManager.getRoomDemographicByDemographic(Integer.valueOf(clientId), facilityId);
        	 bdm = bedDemographicManager.getBedDemographicByDemographic(Integer.valueOf(clientId), facilityId);
        	 if(rdm!=null) clientForm.setRoomDemographic(rdm);
        	 if(bdm!=null) clientForm.setBedDemographic(bdm);             
             actionParam.put("clientId", clientId);            
        	 actionParam.put("intakeId", admission.getIntakeId());
          }
       }
       request.setAttribute("actionParam", actionParam);
       
       List providerList = providerManager.getActiveProviders(facilityId.toString(), programId.toString());
  	   Provider pObj= new Provider();
  	   pObj.setProviderNo("");
  	   providerList.add(0, pObj);
  	   clientForm.setProviderList(providerList);
       
       clientId = (String)actionParam.get("clientId");
       request.setAttribute("clientId", clientId);
	   request.setAttribute("client", clientManager.getClientByDemographicNo(clientId));
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
              if(rdm!=null){
    	  Room current_room= roomManager.getRoom(rdm.getRoomId());
    	  ArrayList availableRoomLst = new ArrayList();
    	  availableRoomLst.add(current_room);
    	  for(int i=0;i<availableRooms.length;i++){
    		if(current_room.equals(availableRooms[i])) continue; 
        	availableRoomLst.add(availableRooms[i]);
    	  }
    	  Room[] availableRooms2 =  (Room[]) availableRoomLst.toArray(new Room[availableRoomLst.size()]);
          clientForm.setAvailableRooms(availableRooms2);
       }else{
         clientForm.setAvailableRooms(availableRooms);
       }

       Bed[] availableBeds=new Bed[1];
       availableBeds[0] = new Bed();
       availableBeds[0].setId(0);
       availableBeds[0].setName("N/A");
       if(clientForm.getRoomDemographic()!=null && clientForm.getRoomDemographic().getRoomId()!=null && clientForm.getRoomDemographic().getRoomId().intValue()>0){
         availableBeds = bedManager.getAvailableBedsByRoom(clientForm.getRoomDemographic().getRoomId());
       }
       if(bdm!=null){
     	  Bed current_bed= bedManager.getBed(bdm.getBedId());
     	  ArrayList availableBedLst = new ArrayList();
     	  availableBedLst.add(current_bed);
     	  for(int i=0;i<availableBeds.length;i++){
         	availableBedLst.add(availableBeds[i]);
     	  }
     	  Bed[] availableBeds2 =  (Bed[]) availableBedLst.toArray(new Bed[availableBedLst.size()]);
          clientForm.setAvailableBeds(availableBeds2);
        }else{
          clientForm.setAvailableBeds(availableBeds);
        }

   }
/*
   public void setAdmissionManager2(AdmissionManager admissionManager2) {
		 this.admissionManager2 = admissionManager2;
   }
*/
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
   
   public void setProviderManager(ProviderManager providerManager) {
	 this.providerManager = providerManager;
  }

   public void setAdmissionManager(AdmissionManager admissionManager) {
	 this.admissionManager = admissionManager;
  }
   
}
