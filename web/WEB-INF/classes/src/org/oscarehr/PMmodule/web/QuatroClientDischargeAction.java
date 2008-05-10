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
import org.oscarehr.PMmodule.model.JointAdmission;
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

import com.quatro.common.KeyConstants;
import com.quatro.service.IntakeManager;
import com.quatro.service.LookupManager;

import oscar.oscarDemographic.data.DemographicRelationship;

public class QuatroClientDischargeAction  extends DispatchAction {
   private ClientManager clientManager;
   private ProviderManager providerManager;
   private ProgramManager programManager;
   private AdmissionManager admissionManager;
   private CaseManagementManager caseManagementManager;
   private BedDemographicManager bedDemographicManager;
   private RoomDemographicManager roomDemographicManager;
   private RoomManager roomManager;
   private BedManager bedManager;
   private LookupManager lookupManager;
   private IntakeManager intakeManager;

   public static final String ID = "id";

   public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       
       return list(mapping, form, request, response);
   }
   
   public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       setEditAttributes(form, request);
       
       return mapping.findForward("edit");
   }
   public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	   DynaActionForm clientForm = (DynaActionForm) form;
	   Admission admObj =(Admission)clientForm.get("admission");
	   admObj.setAdmissionStatus(KeyConstants.INTAKE_STATUS_DISCHARGED);
	   admObj.setDischargeDate(new Date());
	   admissionManager.saveAdmission(admObj);
       return mapping.findForward("edit");
   }
   public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       setListAttributes(form, request);
       return mapping.findForward("list");
   }
   private void setEditAttributes(ActionForm form, HttpServletRequest request) {
       DynaActionForm clientForm = (DynaActionForm) form;

       HashMap actionParam = (HashMap) request.getAttribute("actionParam");
       Long aId = new Long(request.getParameter("admissionId"));
       if(actionParam==null){
    	  actionParam = new HashMap();
          actionParam.put("id", request.getParameter("clientId")); 
       }
       request.setAttribute("actionParam", actionParam);
       String demographicNo= (String)actionParam.get("clientId");
       
       ClientManagerFormBean tabBean = (ClientManagerFormBean) clientForm.get("view");

       Integer facilityId=(Integer)request.getSession().getAttribute(SessionConstants.CURRENT_FACILITY_ID);
       
       request.setAttribute("id", demographicNo);
       request.setAttribute("client", clientManager.getClientByDemographicNo(demographicNo));
       
       clientForm.set("admission", admissionManager.getAdmission(aId));       
       /* discharge */
       List lstCommProgram =lookupManager.LoadCodeList("CMP", true, null, null);
       request.setAttribute("lstCommProgram", lstCommProgram);
       List lstDischargeReason =lookupManager.LoadCodeList("DRN", true, null, null);
       request.setAttribute("lstDischargeReason", lstDischargeReason);
       List lstTransType =lookupManager.LoadCodeList("", true, null, null);
       request.setAttribute("lstTransType", lstTransType);
   }

   private void setListAttributes(ActionForm form, HttpServletRequest request) {
	   DynaActionForm clientForm = (DynaActionForm) form;

       HashMap actionParam = (HashMap) request.getAttribute("actionParam");
       if(actionParam==null){
    	  actionParam = new HashMap();
          actionParam.put("id", request.getParameter("id")); 
       }
       request.setAttribute("actionParam", actionParam);
       String demographicNo= (String)actionParam.get("id");
       
       ClientManagerFormBean tabBean = (ClientManagerFormBean) clientForm.get("view");

       Integer facilityId=(Integer)request.getSession().getAttribute(SessionConstants.CURRENT_FACILITY_ID);
       
       request.setAttribute("id", demographicNo);
       request.setAttribute("client", clientManager.getClientByDemographicNo(demographicNo));

       DemographicExt demographicExtConsent = clientManager.getDemographicExt(Integer.parseInt(demographicNo), Demographic.CONSENT_GIVEN_KEY);
       DemographicExt demographicExtConsentMethod = clientManager.getDemographicExt(Integer.parseInt(demographicNo), Demographic.METHOD_OBTAINED_KEY);

       ConsentGiven consentGiven = ConsentGiven.NONE;
       if (demographicExtConsent != null) consentGiven = ConsentGiven.valueOf(demographicExtConsent.getValue());

       Demographic.MethodObtained methodObtained = Demographic.MethodObtained.IMPLICIT;
       if (demographicExtConsentMethod != null) methodObtained = Demographic.MethodObtained.valueOf(demographicExtConsentMethod.getValue());

       request.setAttribute("consentStatus", consentGiven.name());
       request.setAttribute("consentMethod", methodObtained.name());
       boolean consentStatusChecked = Demographic.ConsentGiven.ALL == consentGiven || Demographic.ConsentGiven.CIRCLE_OF_CARE == consentGiven;
       request.setAttribute("consentCheckBoxState", consentStatusChecked ? "checked=\"checked\"" : "");

       String providerNo = ((Provider) request.getSession().getAttribute("provider")).getProviderNo();

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
           String providerNo2 = (String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
           
           List lstDischarge = admissionManager.getAdmissionListByStatus(Integer.valueOf(demographicNo), facilityId, providerNo2, KeyConstants.INTAKE_STATUS_ADMITTED);           
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
   
}
