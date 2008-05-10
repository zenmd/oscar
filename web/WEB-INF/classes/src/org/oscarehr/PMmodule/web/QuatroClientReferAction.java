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

import oscar.oscarDemographic.data.DemographicRelationship;

public class QuatroClientReferAction  extends DispatchAction {
   private ClientManager clientManager;
   private ProviderManager providerManager;
   private ProgramManager programManager;
   private AdmissionManager admissionManager;
   private CaseManagementManager caseManagementManager;
   private BedDemographicManager bedDemographicManager;
   private RoomDemographicManager roomDemographicManager;
   private RoomManager roomManager;
   private BedManager bedManager;

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
//       String id = request.getParameter("id");

//       Integer facilityId= (Integer)request.getSession().getAttribute("currentFacilityId");
/*
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
   
   public ActionForward refer_select_program(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       DynaActionForm clientForm = (DynaActionForm) form;
       Program p = (Program) clientForm.get("program");
       
       setEditAttributes(form, request);

       long programId = p.getId();
       Program program = programManager.getProgram(programId);
       p.setName(program.getName());
       request.setAttribute("program", program);

       request.setAttribute("clientId", (String)clientForm.get("clientId"));
       
       request.setAttribute("do_refer", true);
       request.setAttribute("temporaryAdmission", programManager.getEnabled());

       return mapping.findForward("edit");
   }

   public ActionForward search_programs(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       DynaActionForm clientForm = (DynaActionForm) form;

       Program criteria = (Program) clientForm.get("program");

       request.setAttribute("programs", programManager.search(criteria));

       HashMap actionParam = (HashMap) request.getAttribute("actionParam");
       if(actionParam==null){
    	  actionParam = new HashMap();
          actionParam.put("clientId", request.getParameter("clientId")); 
       }
       request.setAttribute("actionParam", actionParam);
       String demographicNo= (String)actionParam.get("clientId");
       request.setAttribute("clientId", demographicNo);

       ProgramUtils.addProgramRestrictions(request);

       return mapping.findForward("search_programs");
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
       
       request.setAttribute("client", clientManager.getClientByDemographicNo(demographicNo));

       String providerNo = ((Provider) request.getSession().getAttribute("provider")).getProviderNo();

       request.setAttribute("referrals", clientManager.getActiveReferrals(demographicNo, String.valueOf(facilityId)));

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
   
}
