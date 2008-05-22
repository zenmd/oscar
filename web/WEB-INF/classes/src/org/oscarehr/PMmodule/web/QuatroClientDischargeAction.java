package org.oscarehr.PMmodule.web;

import java.util.ArrayList;
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
import org.oscarehr.PMmodule.model.Admission;
import org.oscarehr.PMmodule.model.Agency;
import org.oscarehr.PMmodule.model.Bed;
import org.oscarehr.PMmodule.model.BedDemographic;
import org.oscarehr.PMmodule.model.Consent;
import org.oscarehr.PMmodule.model.Demographic;
import org.oscarehr.PMmodule.model.DemographicExt;
import org.oscarehr.PMmodule.model.HealthSafety;
import org.oscarehr.PMmodule.model.Intake;
//import org.oscarehr.PMmodule.model.JointAdmission;
import org.oscarehr.PMmodule.model.Program;
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
import org.oscarehr.PMmodule.web.formbean.ClientManagerFormBean;
import org.oscarehr.PMmodule.web.utils.UserRoleUtils;
import org.oscarehr.casemgmt.service.CaseManagementManager;
import org.oscarehr.util.SessionConstants;
import org.oscarehr.PMmodule.model.QuatroIntakeFamily;
import com.quatro.common.KeyConstants;
import com.quatro.model.security.Secuserrole;
import com.quatro.service.IntakeManager;
import com.quatro.service.LookupManager;
import com.quatro.util.Utility;

import oscar.oscarDemographic.data.DemographicRelationship;
import org.oscarehr.PMmodule.service.QuatroAdmissionManager;

public class QuatroClientDischargeAction  extends BaseAction {
   private ClientManager clientManager;
   private ProviderManager providerManager;
   private ProgramManager programManager;
   private AdmissionManager admissionManager;
   private QuatroAdmissionManager quatroAdmissionManager;
   private CaseManagementManager caseManagementManager;
   private BedDemographicManager bedDemographicManager;
   private RoomDemographicManager roomDemographicManager;
   private RoomManager roomManager;
   private BedManager bedManager;
   private LookupManager lookupManager;
   private IntakeManager intakeManager;
   


   public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       
       return list(mapping, form, request, response);
   }
   
   public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       setEditAttributes(form, request);
       
       super.setScreenMode(request, KeyConstants.TAB_DISCHARGE);
       return mapping.findForward("edit");
   }
   public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	   DynaActionForm clientForm = (DynaActionForm) form;
	   super.setScreenMode(request, KeyConstants.TAB_DISCHARGE);
	   HashMap actionParam = (HashMap) request.getAttribute("actionParam");
       if(actionParam==null){
    	  actionParam = new HashMap();
          actionParam.put("clientId", request.getParameter("clientId")); 
       }
       request.setAttribute("actionParam", actionParam);
       
       log.debug("Saving Discharge");
		ActionMessages messages = new ActionMessages();
		 boolean isError = false;
	     boolean isWarning = false;
	   Admission admObj =(Admission)clientForm.get("admission");	  
	   Admission admOld= (Admission) request.getAttribute("admission");
	   String comProgram=admObj.getCommunityProgramCode();
	   Integer bedProg =admObj.getBedProgramId();
	   String rReason = admObj.getRadioDischargeReason();
	   String transType=admObj.getTransportationType();
	   String disNotes=admObj.getDischargeNotes();
	   admObj =(Admission)admOld.clone();
	   admObj.setCommunityProgramCode(comProgram);
	   admObj.setBedProgramId(bedProg);
	   admObj.setRadioDischargeReason(rReason);
	   admObj.setTransportationType(transType);
	   admObj.setDischargeNotes(disNotes);
	   List<Admission> admLst = new ArrayList<Admission>();
	   admLst.add(admObj);
	   // 
	   List lstFamily = intakeManager.getClientFamilyByIntakeId(admObj.getIntakeId().toString());
	   if(!lstFamily.isEmpty()){
		   admissionManager.saveAdmission(admObj);
		   Iterator item = lstFamily.iterator();
			while(item.hasNext()){
				QuatroIntakeFamily qifTmp = (QuatroIntakeFamily)item.next();
				List<Admission> lst =admissionManager.getAdmissionList(qifTmp.getIntakeId(),KeyConstants.INTAKE_STATUS_ADMITTED);
				Iterator admItem =lst.iterator();
				while (admItem.hasNext()){
					Admission admLoc=(Admission)admItem.next();
					if(admLoc.getId().intValue()!=admObj.getId().intValue()){ 
						admLoc.setAdmissionStatus(KeyConstants.INTAKE_STATUS_DISCHARGED);
						admLoc.setDischargeDate(new Date());
					  	admLoc.setDischargeNotes(admObj.getDischargeNotes());	
					  	admissionManager.saveAdmission(admLoc);
					  	// admLst.add(admLoc);
					}
				}
			}
			
	   }else{
		   boolean isReferal=false;
		   if(null!=admObj.getBedProgramId() && admObj.getBedProgramId()>0) {
			   isReferal =true;
			   admObj.setCommunityProgramCode(admObj.getBedProgramId().toString());
		   
		   }
		   admissionManager.saveAdmission(admObj,isReferal);		  
	   }
	   if(!(isWarning || isError)) messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.save.success", request.getContextPath()));
       saveMessages(request,messages);	
       return mapping.findForward("edit");
   }
   public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       setListAttributes(form, request);
       super.setScreenMode(request, KeyConstants.TAB_DISCHARGE);
       return mapping.findForward("list");
   }
   private void setEditAttributes(ActionForm form, HttpServletRequest request) {
       DynaActionForm clientForm = (DynaActionForm) form;

       HashMap actionParam = (HashMap) request.getAttribute("actionParam");
       Integer aId = new Integer(request.getParameter("admissionId"));
       
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
       Admission admsObj =admissionManager.getAdmission(aId);
      
       clientForm.set("admission", admsObj);       
       /* discharge */
       List lstCommProgram =lookupManager.LoadCodeList("CMP", true, null, null);
       request.setAttribute("lstCommProgram", lstCommProgram);
       List lstDischargeReason =lookupManager.LoadCodeList("DRN", true, null, null);
       request.setAttribute("lstDischargeReason", lstDischargeReason);
       List lstTransType =lookupManager.LoadCodeList("TPT", true, null, null);
       request.setAttribute("lstTransType", lstTransType);     
       Program[]  lstBed=programManager.getBedPrograms(facilityId);
       request.setAttribute("lstBedProgram",lstBed);
       request.setAttribute("admission", admsObj);
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
       
       Integer facilityId=(Integer)request.getSession().getAttribute(SessionConstants.CURRENT_FACILITY_ID);
       
       request.setAttribute("clientId", demographicNo);
       request.setAttribute("client", clientManager.getClientByDemographicNo(demographicNo));


       String providerNo = (String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
           
       List lstDischarge = quatroAdmissionManager.getAdmissionList(Integer.valueOf(demographicNo), facilityId, providerNo);
       //.getAdmissionList(Integer.valueOf(demographicNo), facilityId, providerNo2);           
       request.setAttribute("quatroDischarge", lstDischarge);
   }
   
   public void setIntakeManager(IntakeManager intakeManager){
	   this.intakeManager =intakeManager;
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

public void setLookupManager(LookupManager lookupManager) {
	this.lookupManager = lookupManager;
}

public void setQuatroAdmissionManager(
		QuatroAdmissionManager quatroAdmissionManager) {
	this.quatroAdmissionManager = quatroAdmissionManager;
}
   
}
