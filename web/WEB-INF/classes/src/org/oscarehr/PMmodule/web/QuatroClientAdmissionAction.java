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
import org.oscarehr.PMmodule.model.QuatroIntakeFamily;
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
import org.oscarehr.PMmodule.service.ProgramQueueManager;
import org.oscarehr.casemgmt.service.CaseManagementManager;
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
import org.oscarehr.PMmodule.exception.AdmissionException;
import org.oscarehr.PMmodule.exception.ProgramFullException;
import org.oscarehr.PMmodule.exception.ServiceRestrictionException;

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
   private ProgramQueueManager programQueueManager;

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
       
       Integer shelterId=(Integer)request.getSession().getAttribute(KeyConstants.SESSION_KEY_SHELTERID);
       request.setAttribute("clientId", demographicNo);
       request.setAttribute("client", clientManager.getClientByDemographicNo(demographicNo));
       String providerNo = (String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
       List lstAdmission = admissionManager.getAdmissions(Integer.valueOf(demographicNo),providerNo, shelterId);
       request.setAttribute("admission", lstAdmission);

       List queue = programQueueManager.getProgramQueuesByClientId(Integer.valueOf(demographicNo));
       request.setAttribute("queue", queue);
       
       return mapping.findForward("list");
   }

   //from queue page 
   public ActionForward queue(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
 	   QuatroClientAdmissionForm clientForm = (QuatroClientAdmissionForm) form;

       Integer programId=null;
       Integer intakeId = null;
       Integer admissionId =null;
       String clientId = null;
       Integer shelterId=(Integer)request.getSession().getAttribute(KeyConstants.SESSION_KEY_SHELTERID);
       
       HashMap actionParam = new HashMap();
       clientId=request.getParameter("clientId");
       request.setAttribute("clientId", clientId);
	   request.setAttribute("client", clientManager.getClientByDemographicNo(clientId));

	   actionParam.put("clientId", clientId);
       Integer queueId=Integer.valueOf(request.getParameter("queueId"));
       QuatroIntakeDB intakeDB =  intakeManager.getQuatroIntakeDBByQueueId(queueId);
       intakeId = intakeDB.getId();
       actionParam.put("intakeId", intakeId);
       request.setAttribute("actionParam", actionParam);

       programId=intakeDB.getProgramId();
       Integer intakeFamilyHeadId = intakeManager.getIntakeFamilyHeadId(intakeId.toString());
       if(intakeFamilyHeadId.intValue()==0){
         clientForm.setFamilyIntakeType("N");
       }else{
         clientForm.setFamilyIntakeType("Y");
       }

       Admission admission = new Admission(); 
       admission.setId(new Integer(0));
       admission.setProgramId(programId);
       admission.setIntakeId(intakeId);
       admission.setClientId(Integer.valueOf(clientId));
       admission.setAdmissionDate(Calendar.getInstance());
       admission.setAdmissionDateTxt(MyDateFormat.getStandardDateTime(admission.getAdmissionDate()));
       admission.setAdmissionStatus(KeyConstants.INTAKE_STATUS_ACTIVE);
       admission.setNextKinProvince("ON");
       clientForm.setAdmission(admission);

       Room[] availableRooms = roomManager.getAvailableRooms(null, programId, Boolean.TRUE, clientId, clientForm.getFamilyIntakeType().equals("Y"));
 	   ArrayList availableRoomLst = new ArrayList();
	   Room emptyRoom=new Room();
	   emptyRoom.setId(new Integer(0));
	   emptyRoom.setName(" ---- ");
	   availableRoomLst.add(emptyRoom);
   	   for(int i=0;i<availableRooms.length;i++){
   		availableRoomLst.add(availableRooms[i]);
   	   }
   	   Room[] availableRooms2 =  (Room[]) availableRoomLst.toArray(new Room[availableRoomLst.size()]);
       clientForm.setAvailableRooms(availableRooms2);
       clientForm.setCurDB_RoomId(new Integer(0));

       if(!clientForm.getFamilyIntakeType().equals("Y")){
           Bed[] availableBeds=new Bed[1];
           Bed emptyBed = new Bed();
           emptyBed.setId(new Integer(0));
           emptyBed.setName(" ---- ");
           availableBeds[0]=emptyBed;
           clientForm.setAvailableBeds(availableBeds);
           clientForm.setCurDB_BedId(new Integer(0));
       }
       
	   //set dropdown values
	   List providerList = providerManager.getActiveProviders(programId);
  	   Provider pObj= new Provider();
  	   pObj.setProviderNo("");
  	   providerList.add(0, pObj);
  	   clientForm.setProviderList(providerList);
	   List provinceList = lookupManager.LoadCodeList("POV",true, null, null);
	   clientForm.setProvinceList(provinceList);
	   List notSignReasonList = lookupManager.LoadCodeList("RNS",true, null, null);
       clientForm.setNotSignReasonList(notSignReasonList);
       
       super.setScreenMode(request, KeyConstants.TAB_CLIENT_ADMISSION);
       return mapping.findForward("edit");
   }
   
   //from quatroClientAdmission.jsp room change event 
   public ActionForward roomchange(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
 	   QuatroClientAdmissionForm clientForm = (QuatroClientAdmissionForm) form;

       Integer shelterId=(Integer)request.getSession().getAttribute(KeyConstants.SESSION_KEY_SHELTERID);
       
       HashMap actionParam = new HashMap();
       actionParam.put("clientId", clientForm.getAdmission().getClientId());            
   	   actionParam.put("intakeId", clientForm.getAdmission().getIntakeId());
   	   
       Integer programId=clientForm.getAdmission().getProgramId();
   	   String clientId = clientForm.getAdmission().getClientId().toString();
       request.setAttribute("clientId", clientId);
	   request.setAttribute("client", clientManager.getClientByDemographicNo(clientId));
       request.setAttribute("actionParam", actionParam);
       
       //setup rooms
       Integer curDB_RoomId = clientForm.getCurDB_RoomId();
       Room currentDB_room = null;
       if(curDB_RoomId.intValue()>0) currentDB_room = roomManager.getRoom(curDB_RoomId);
       ArrayList availableRoomLst = new ArrayList();
	   Room emptyRoom=new Room();
	   emptyRoom.setId(new Integer(0));
	   emptyRoom.setName(" ---- ");
	   availableRoomLst.add(emptyRoom);
       if(currentDB_room!=null)availableRoomLst.add(currentDB_room);
       Room[] availableRooms = roomManager.getAvailableRooms(null, programId, Boolean.TRUE, 
    		           clientId, clientForm.getFamilyIntakeType().equals("Y"));
       for(int i=0;i<availableRooms.length;i++){
     	   if(currentDB_room!=null && currentDB_room.equals(availableRooms[i])) continue; 
           availableRoomLst.add(availableRooms[i]);
       }
       Room[] availableRooms2 =  (Room[]) availableRoomLst.toArray(new Room[availableRoomLst.size()]);
       clientForm.setAvailableRooms(availableRooms2);

       //setup beds
       //family intake doesn't need assign beds, just room. 
       if(!clientForm.getFamilyIntakeType().equals("Y")){
         Integer curDB_BedId = clientForm.getCurDB_BedId();
         ArrayList availableBedLst = new ArrayList();
  	     Bed emptyBed=new Bed();
  	     emptyBed.setId(new Integer(0));
  	     emptyBed.setName(" ---- ");
  	     availableBedLst.add(emptyBed);
         if(clientForm.getRoomDemographic().getRoomId().intValue()>0){
           Bed currentDB_bed = null;
           if(curDB_RoomId.equals(clientForm.getRoomDemographic().getRoomId())){
        	  if(curDB_BedId!=null) currentDB_bed = bedManager.getBed(curDB_BedId);
              if(currentDB_bed!=null) availableBedLst.add(currentDB_bed);
           }
           Bed[] availableBeds = bedManager.getAvailableBedsByRoom(clientForm.getRoomDemographic().getRoomId());
           for(int i=0;i<availableBeds.length;i++){
         	   if(currentDB_bed!=null && currentDB_bed.equals(availableBeds[i])) continue; 
               availableBedLst.add(availableBeds[i]);
           }
       	   Bed[] availableBeds2 =  (Bed[]) availableBedLst.toArray(new Bed[availableBedLst.size()]);
           clientForm.setAvailableBeds(availableBeds2);
   	     }else{
           Bed[] availableBeds=new Bed[1];
     	   availableBeds[0] = emptyBed;
           clientForm.setAvailableBeds(availableBeds);
         }
       }

	   //set dropdown values
	   List providerList = providerManager.getActiveProviders(programId);
  	   Provider pObj= new Provider();
  	   pObj.setProviderNo("");
  	   providerList.add(0, pObj);
  	   clientForm.setProviderList(providerList);
	   List provinceList = lookupManager.LoadCodeList("POV",true, null, null);
	   clientForm.setProvinceList(provinceList);
	   List notSignReasonList = lookupManager.LoadCodeList("RNS",true, null, null);
       clientForm.setNotSignReasonList(notSignReasonList);
       
       super.setScreenMode(request, KeyConstants.TAB_CLIENT_ADMISSION);
       return mapping.findForward("edit");
   }
   	
   public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       QuatroClientAdmissionForm clientForm = (QuatroClientAdmissionForm) form;

       HashMap actionParam = new HashMap();

       Integer shelterId=(Integer)request.getSession().getAttribute(KeyConstants.SESSION_KEY_SHELTERID);

       Integer admissionId;
       Admission admission;
       if(request.getParameter("admissionId")!=null){
          admissionId = Integer.valueOf(request.getParameter("admissionId"));
          admission = admissionManager.getAdmissionByAdmissionId(admissionId);
          admission.setAdmissionDateTxt(MyDateFormat.getStandardDateTime(admission.getAdmissionDate()));
    	  if(admission.getOvPassStartDate()!=null) admission.setOvPassStartDateTxt(MyDateFormat.getStandardDate(admission.getOvPassStartDate()));
    	  if(admission.getOvPassStartDate()!=null) admission.setOvPassEndDateTxt(MyDateFormat.getStandardDate(admission.getOvPassStartDate()));
          clientForm.setAdmission(admission);
       }else{
          admissionId = clientForm.getAdmission().getId();
          admission = clientForm.getAdmission();
       }

	   String clientId=admission.getClientId().toString();
       Integer programId = admission.getProgramId();
       actionParam.put("clientId", admission.getClientId());            
   	   actionParam.put("intakeId", admission.getIntakeId());
       request.setAttribute("clientId", clientId);
	   request.setAttribute("client", clientManager.getClientByDemographicNo(clientId));
       request.setAttribute("actionParam", actionParam);


       Integer intakeId = admission.getIntakeId();
       clientForm.setFamilyIntakeType("N");
	   Integer intakeFamilyHeadId = intakeManager.getIntakeFamilyHeadId(intakeId.toString());
       if(intakeFamilyHeadId.intValue()==0){
         clientForm.setFamilyAdmissionType("N");
       }else{
         clientForm.setFamilyAdmissionType("Y");
       }
       
       if(request.getParameter("admissionId")!=null){
         RoomDemographic rdm = roomDemographicManager.getRoomDemographicByDemographic(Integer.valueOf(clientId));
         clientForm.setRoomDemographic(rdm);
         clientForm.setCurDB_RoomId(rdm.getRoomId());

         BedDemographic bdm =null;
         if(!clientForm.getFamilyIntakeType().equals("Y")){
    	   bdm = bedDemographicManager.getBedDemographicByDemographic(Integer.valueOf(clientId), shelterId);
    	   if(bdm!=null){
    	     clientForm.setBedDemographic(bdm);
    	     clientForm.setCurDB_BedId(bdm.getBedId());
    	   }
         }
       }
       
       //setup rooms
       Integer curDB_RoomId = clientForm.getCurDB_RoomId();
       Room currentDB_room = null;
       if(curDB_RoomId.intValue()>0) currentDB_room = roomManager.getRoom(curDB_RoomId);
       ArrayList availableRoomLst = new ArrayList();
	   Room emptyRoom=new Room();
	   emptyRoom.setId(new Integer(0));
	   emptyRoom.setName(" ---- ");
	   availableRoomLst.add(emptyRoom);
       if(currentDB_room!=null)availableRoomLst.add(currentDB_room);
       Room[] availableRooms = roomManager.getAvailableRooms(null, programId, Boolean.TRUE, 
    		   clientId, clientForm.getFamilyIntakeType().equals("Y"));
       for(int i=0;i<availableRooms.length;i++){
     	   if(currentDB_room!=null && currentDB_room.equals(availableRooms[i])) continue; 
           availableRoomLst.add(availableRooms[i]);
       }
       Room[] availableRooms2 =  (Room[]) availableRoomLst.toArray(new Room[availableRoomLst.size()]);
       clientForm.setAvailableRooms(availableRooms2);

       //setup beds
       //family intake doesn't need assign beds, just room. 
       if(!clientForm.getFamilyIntakeType().equals("Y")){
         Integer curDB_BedId = clientForm.getCurDB_BedId();
         ArrayList availableBedLst = new ArrayList();
  	     Bed emptyBed=new Bed();
  	     emptyBed.setId(new Integer(0));
  	     emptyBed.setName(" ---- ");
  	     availableBedLst.add(emptyBed);
         if(clientForm.getRoomDemographic().getRoomId().intValue()>0){
           Bed currentDB_bed = null;
           if(curDB_RoomId.equals(clientForm.getRoomDemographic().getRoomId())){
        	  if(curDB_BedId!=null && curDB_BedId.intValue()>0) currentDB_bed = bedManager.getBed(curDB_BedId);
              if(currentDB_bed!=null) availableBedLst.add(currentDB_bed);
           }
           Bed[] availableBeds = bedManager.getAvailableBedsByRoom(clientForm.getRoomDemographic().getRoomId());
           for(int i=0;i<availableBeds.length;i++){
         	   if(currentDB_bed!=null && currentDB_bed.equals(availableBeds[i])) continue; 
               availableBedLst.add(availableBeds[i]);
           }
       	   Bed[] availableBeds2 =  (Bed[]) availableBedLst.toArray(new Bed[availableBedLst.size()]);
           clientForm.setAvailableBeds(availableBeds2);
   	     }else{
           Bed[] availableBeds=new Bed[1];
     	   availableBeds[0] = emptyBed;
           clientForm.setAvailableBeds(availableBeds);
         }
       }
       
	   //set dropdown values
	   List providerList = providerManager.getActiveProviders(programId);
  	   Provider pObj= new Provider();
  	   pObj.setProviderNo("");
  	   providerList.add(0, pObj);
  	   clientForm.setProviderList(providerList);
	   List provinceList = lookupManager.LoadCodeList("POV",true, null, null);
	   clientForm.setProvinceList(provinceList);
	   List notSignReasonList = lookupManager.LoadCodeList("RNS",true, null, null);
       clientForm.setNotSignReasonList(notSignReasonList);
       
       if(admission.getProviderNo()!=null) request.setAttribute("issuedBy",providerManager.getProvider(admission.getProviderNo()).getFormattedName());

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
       Integer shelterId=(Integer)request.getSession().getAttribute(KeyConstants.SESSION_KEY_SHELTERID);
       request.setAttribute("client", clientManager.getClientByDemographicNo(clientId.toString()));

       //don't check these if intake admitted.
       if(admissionId.intValue()==0){
    	 if(clientForm.getFamilyIntakeType().equals("Y")){  
           //service restriction check
           StringBuffer sb = new StringBuffer();
    	   List lstFamily = intakeManager.getClientFamilyByIntakeId(admission.getIntakeId().toString());    	  
           for(int i=0;i<lstFamily.size();i++){
             QuatroIntakeFamily qif = (QuatroIntakeFamily)lstFamily.get(i);
             ProgramClientRestriction restrInPlace = clientRestrictionManager.checkClientRestriction(
           	     admission.getProgramId(), qif.getClientId(), new Date());
             if(restrInPlace != null) {
           	   sb.append(qif.getLastName() + ", " + qif.getFirstName() + "<br>");
               isError = true;
             }  
           }
    	   if(isError){
        	   Program program = programManager.getProgram(programId); 
    	       messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.intake.admission.family_service_restriction",
                  			request.getContextPath(), program.getName(), sb.toString()));
               saveMessages(request,messages);
               return update(mapping, form, request, response);
    	   }

    	   //check client active in other program
           for(int i=0;i<lstFamily.size();i++){
             QuatroIntakeFamily qif = (QuatroIntakeFamily)lstFamily.get(i);
             List lst=admissionManager.getCurrentAdmissions(qif.getClientId(),KeyConstants.SYSTEM_USER_PROVIDER_NO,null);
             for(int j=0;j<lst.size();j++){
        	   Admission admission_exist = (Admission)lst.get(j);
        	   StringBuffer sb2 = new StringBuffer();
        	   sb2.append("," + admission_exist.getId());
               if(sb2.length()>0){
                 //auto-discharge from other program   
                 admissionManager.dischargeAdmission(sb2.substring(1));
               }
             }
           }

    	 }else{
           //service restriction check
	       ProgramClientRestriction restrInPlace = clientRestrictionManager.checkClientRestriction(
			   programId, clientId, new Date());
           if (restrInPlace != null) {
    	     Program program = programManager.getProgram(programId); 
	         messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.intake.admission.service_restriction",
              			request.getContextPath(), program.getName()));
             isError = true;
             saveMessages(request,messages);
             return update(mapping, form, request, response);
	       }

           //check client active in other program
           List lst=admissionManager.getCurrentAdmissions(clientId,KeyConstants.SYSTEM_USER_PROVIDER_NO,null);
           for(int i=0;i<lst.size();i++){
        	 Admission admission_exist = (Admission)lst.get(i);
        	 StringBuffer sb = new StringBuffer();
        	 sb.append("," + admission_exist.getId());
             if(sb.length()>0){
               //auto-discharge from other program   
               admissionManager.dischargeAdmission(sb.substring(1));
             }
           }
    	 }
       }
       
       //check if roomId /bedId selected
       RoomDemographic rdm = clientForm.getRoomDemographic();
       if(rdm.getId().getRoomId().intValue()==0){
	     messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.intake.admission.empty_roomId",
         			request.getContextPath()));
         isError = true;
         saveMessages(request,messages);
         return update(mapping, form, request, response);
       }else{
    	  //check bedId selected for single person intake admission
    	  //admitted family member may not necessary be assigned bed on this page.  
    	  if(!clientForm.getFamilyIntakeType().equals("Y") && !"Y".equals(clientForm.getFamilyAdmissionType())){
    	     BedDemographic bdm = clientForm.getBedDemographic();
    	     if(bdm.getBedId().intValue()==0){
    	        messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.intake.admission.empty_bedId",
      			   request.getContextPath()));
                isError = true;
                saveMessages(request,messages);
                return update(mapping, form, request, response);
    	     } 
    	  }   
       }
       
       String providerNo = (String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
	   admission.setProviderNo(providerNo);
       admission.setAdmissionDate(MyDateFormat.getCalendarwithTime(admission.getAdmissionDateTxt()));
       admission.setFacilityId(shelterId);
       admission.setOvPassStartDate(MyDateFormat.getCalendar(admission.getOvPassStartDateTxt()));
       admission.setOvPassEndDate(MyDateFormat.getCalendar(admission.getOvPassEndDateTxt()));
       
       RoomDemographic roomDemographic = clientForm.getRoomDemographic();
 	   RoomDemographicPK rdmPK= roomDemographic.getId();
 	   rdmPK.setDemographicNo(admission.getClientId());
 	   roomDemographic.setId(rdmPK);
 	   roomDemographic.setProviderNo(providerNo);
 	   roomDemographic.setAssignStart(new Date());
       
 	   BedDemographic bedDemographic;
 	   if(!clientForm.getFamilyIntakeType().equals("Y") || "Y".equals(clientForm.getFamilyAdmissionType())){  
 	     bedDemographic = clientForm.getBedDemographic();
 	     BedDemographicPK bdmPK= bedDemographic.getId();
 	     bdmPK.setDemographicNo(admission.getClientId());
 	     bedDemographic.setId(bdmPK);
 	     bedDemographic.setProviderNo(providerNo);
 	     bedDemographic.setReservationStart(new Date());
  	   }else{
  		 bedDemographic =null;  
  	   }
  	   
       if(admission.getId().intValue()==0){
    	  QuatroIntake intake = intakeManager.getQuatroIntake(intakeId);
    	  admission.setAdmissionStatus(KeyConstants.INTAKE_STATUS_ADMITTED);
    	  admissionManager.saveAdmission(admission, intakeId, intake.getQueueId(), 
   			  intake.getReferralId(),roomDemographic,bedDemographic, clientForm.getFamilyIntakeType().equals("Y"));
       }else{
    	  admissionManager.updateAdmission(admission, roomDemographic,bedDemographic);
       }
       
	   if(!(isWarning || isError)) messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.save.success", request.getContextPath()));
       saveMessages(request,messages);

 	   clientForm.setAdmission(admission);
 	   clientForm.setRoomDemographic(roomDemographic);
 	   clientForm.setBedDemographic(bedDemographic);
 	   clientForm.setCurDB_RoomId(roomDemographic.getRoomId());
 	   if(bedDemographic!=null) clientForm.setCurDB_BedId(bedDemographic.getBedId());

       return update(mapping, form, request, response);
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
   
   public void setProviderManager(ProviderManager providerManager) {
	 this.providerManager = providerManager;
  }

   public void setAdmissionManager(AdmissionManager admissionManager) {
	 this.admissionManager = admissionManager;
  }

public void setProgramQueueManager(ProgramQueueManager programQueueManager) {
	this.programQueueManager = programQueueManager;
}
   
}
