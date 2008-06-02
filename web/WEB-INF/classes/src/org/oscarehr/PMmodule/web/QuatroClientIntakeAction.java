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
import org.oscarehr.PMmodule.model.QuatroIntakeHeader;
import org.oscarehr.PMmodule.model.Demographic;
import org.oscarehr.PMmodule.model.DemographicExt;
import org.oscarehr.PMmodule.model.HealthSafety;
import org.oscarehr.PMmodule.model.Intake;
import org.oscarehr.PMmodule.model.Program;
import org.oscarehr.PMmodule.model.caisi_ProgramProvider;
import org.oscarehr.PMmodule.model.Provider;
import org.oscarehr.PMmodule.model.Room;
import org.oscarehr.PMmodule.model.RoomDemographic;
import org.oscarehr.PMmodule.service.AdmissionManager;
import org.oscarehr.PMmodule.service.BedDemographicManager;
import org.oscarehr.PMmodule.service.BedManager;
import org.oscarehr.PMmodule.service.ClientManager;

import com.quatro.common.KeyConstants;
import com.quatro.service.IntakeManager;
import org.oscarehr.PMmodule.service.ProgramManager;
import org.oscarehr.PMmodule.service.ProviderManager;
import org.oscarehr.PMmodule.web.formbean.ClientManagerFormBean;
import org.oscarehr.PMmodule.web.utils.UserRoleUtils;
import org.oscarehr.util.SessionConstants;

import oscar.oscarDemographic.data.DemographicRelationship;

public class QuatroClientIntakeAction  extends BaseClientAction {
   private ClientManager clientManager;
   private ProgramManager programManager;
   private AdmissionManager admissionManager;
   private IntakeManager intakeManager;

   public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       return edit(mapping, form, request, response);
   }
   
   public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       //On newclient intake page, if save button not clicked before close button clicked, goto client search page. 
	   if(request.getParameter("clientId")!=null){
	     String demographicNo= request.getParameter("clientId");
	     if(demographicNo.equals("0")) return mapping.findForward("close");
       }else{
    	 return mapping.findForward("close");
       }
	   super.setScreenMode(request, KeyConstants.TAB_CLIENT_INTAKE);
       setEditAttributes(form, request);
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

       Integer facilityId=(Integer)request.getSession().getAttribute(SessionConstants.CURRENT_FACILITY_ID);
       
       request.setAttribute("clientId", demographicNo);
       request.setAttribute("client", clientManager.getClientByDemographicNo(demographicNo));

       String providerNo = (String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
       List lstIntake = intakeManager.getQuatroIntakeHeaderListByFacility(Integer.valueOf(demographicNo), facilityId, providerNo);
       request.setAttribute("quatroIntake", lstIntake);
   }

   public void setAdmissionManager(AdmissionManager admissionManager) {
	 this.admissionManager = admissionManager;
   }

   public void setClientManager(ClientManager clientManager) {
	 this.clientManager = clientManager;
   }

   public void setProgramManager(ProgramManager programManager) {
	 this.programManager = programManager;
   }

   public void setIntakeManager(IntakeManager intakeManager) {
	 this.intakeManager = intakeManager;
   }

}
