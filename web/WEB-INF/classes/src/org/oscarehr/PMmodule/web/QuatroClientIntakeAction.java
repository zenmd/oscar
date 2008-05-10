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

import com.quatro.common.KeyConstants;
import com.quatro.service.IntakeManager;
import org.oscarehr.PMmodule.service.ProgramManager;
import org.oscarehr.PMmodule.service.ProviderManager;
import org.oscarehr.PMmodule.web.formbean.ClientManagerFormBean;
import org.oscarehr.PMmodule.web.utils.UserRoleUtils;
import org.oscarehr.util.SessionConstants;

import oscar.oscarDemographic.data.DemographicRelationship;

public class QuatroClientIntakeAction  extends DispatchAction {
   private ClientManager clientManager;
   private ProviderManager providerManager;
   private ProgramManager programManager;
   private AdmissionManager admissionManager;
   private IntakeManager intakeManager;

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

       request.setAttribute("consentStatus", consentGiven.name());
       request.setAttribute("consentMethod", methodObtained.name());
       boolean consentStatusChecked = Demographic.ConsentGiven.ALL == consentGiven || Demographic.ConsentGiven.CIRCLE_OF_CARE == consentGiven;
       request.setAttribute("consentCheckBoxState", consentStatusChecked ? "checked=\"checked\"" : "");

       String providerNo = ((Provider) request.getSession().getAttribute("provider")).getProviderNo();


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


           String providerNo2 = (String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
           List lstIntake = intakeManager.getQuatroIntakeHeaderListByFacility(Integer.valueOf(demographicNo), facilityId, providerNo2);
           List lstIntake2 = new ArrayList();
           for(Object element : lstIntake){
        	   QuatroIntakeHeader obj = (QuatroIntakeHeader)element;
        	   if(obj.getIntakeStatus().equals(KeyConstants.INTAKE_STATUS_ACTIVE)) lstIntake2.add(obj);
           }
           request.setAttribute("quatroIntake", lstIntake2);
//       }


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

   public void setProviderManager(ProviderManager providerManager) {
	 this.providerManager = providerManager;
   }

   public void setIntakeManager(IntakeManager intakeManager) {
	 this.intakeManager = intakeManager;
   }

}
