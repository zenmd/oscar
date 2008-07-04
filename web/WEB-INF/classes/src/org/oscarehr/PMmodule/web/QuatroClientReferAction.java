package org.oscarehr.PMmodule.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.oscarehr.PMmodule.model.ClientReferral;
import org.oscarehr.PMmodule.model.Program;
import org.oscarehr.PMmodule.model.Provider;
import org.oscarehr.PMmodule.service.AdmissionManager;
import org.oscarehr.PMmodule.service.BedDemographicManager;
import org.oscarehr.PMmodule.service.BedManager;
import org.oscarehr.PMmodule.service.ClientManager;
import org.oscarehr.PMmodule.service.ProgramManager;
import org.oscarehr.PMmodule.service.ProviderManager;
import org.oscarehr.PMmodule.service.RoomDemographicManager;
import org.oscarehr.PMmodule.service.RoomManager;
import org.oscarehr.casemgmt.service.CaseManagementManager;

import com.quatro.common.KeyConstants;
import com.quatro.util.Utility;

public class QuatroClientReferAction  extends BaseClientAction {
   private ClientManager clientManager;
   private ProviderManager providerManager;
   private ProgramManager programManager;
   private AdmissionManager admissionManager;
   private CaseManagementManager caseManagementManager;
   private BedDemographicManager bedDemographicManager;
   private RoomDemographicManager roomDemographicManager;
   private RoomManager roomManager;
   private BedManager bedManager;

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		setListAttributes(form, request);
		super.setScreenMode(request, KeyConstants.TAB_CLIENT_REFER);
		return mapping.findForward("list");
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		setEditAttributes(form, request);		
		return mapping.findForward("edit");
	}

	public ActionForward refer_select_program(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DynaActionForm clientForm = (DynaActionForm) form;
		Program p = (Program) clientForm.get("program");
		super.setScreenMode(request, KeyConstants.TAB_CLIENT_REFER);
		setEditAttributes(form, request);

		Integer programId = p.getId();
		Program program = programManager.getProgram(programId);
		p.setName(program.getName());
		request.setAttribute("program", program);

		request.setAttribute("clientId", (String) clientForm.get("clientId"));

		request.setAttribute("do_refer", Boolean.TRUE);
//		request.setAttribute("temporaryAdmission", new Boolean(programManager.getEnabled()));

		return mapping.findForward("edit");
	}

	private void setListAttributes(ActionForm form, HttpServletRequest request) {
		ActionMessages messages = new ActionMessages();
		boolean isError = false;
		boolean isWarning = false;
		DynaActionForm clientForm = (DynaActionForm) form;

		HashMap actionParam = (HashMap) request.getAttribute("actionParam");
		String cId = request.getParameter("clientId");
		if (Utility.IsEmpty(cId)) {
			cId = (String) request.getParameter("referral.clientId");
		}
		if (actionParam == null) {
			actionParam = new HashMap();
			actionParam.put("clientId", cId);
		}
		request.setAttribute("actionParam", actionParam);
		String demographicNo = (String) actionParam.get("clientId");

		String providerNo =(String)request.getSession(true).getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
		Integer shelterId =(Integer)request.getSession(true).getAttribute(KeyConstants.SESSION_KEY_SHELTERID);
		request.setAttribute("client", clientManager.getClientByDemographicNo(demographicNo));	
		request.setAttribute("clientId", cId);		
		try {
			List lstRefers = clientManager.getReferrals(demographicNo,providerNo,shelterId);
			request.setAttribute("lstRefers", lstRefers);
		} catch (Exception e) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.listRefer.failed", request.getContextPath()));
			saveMessages(request, messages);
		}
	}

	public ActionForward search_programs(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DynaActionForm clientForm = (DynaActionForm) form;
		super.setScreenMode(request, KeyConstants.TAB_CLIENT_REFER);
		Program criteria = (Program) clientForm.get("program");

		request.setAttribute("programs", programManager.search(criteria));

		HashMap actionParam = (HashMap) request.getAttribute("actionParam");
		if (actionParam == null) {
			actionParam = new HashMap();
			actionParam.put("clientId", request.getParameter("clientId"));
		}
		request.setAttribute("actionParam", actionParam);
		String demographicNo = (String) actionParam.get("clientId");
		clientForm.set("clientId", demographicNo);
		ProgramUtils.addProgramRestrictions(request);

		return mapping.findForward("search_programs");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		super.setScreenMode(request, KeyConstants.TAB_CLIENT_REFER);
		log.debug("Saving Refer");
		ActionMessages messages = new ActionMessages();
		boolean isError = false;
		boolean isWarning = false;
		DynaActionForm refForm = (DynaActionForm) form;
		ClientReferral refObj = (ClientReferral) refForm.get("referral");
		HashMap actionParam = (HashMap) request.getAttribute("actionParam");
		if (actionParam == null) {
			actionParam = new HashMap();
			actionParam.put("clientId", refObj.getClientId().toString());
		}
		request.setAttribute("actionParam", actionParam);
		String cId = (String) actionParam.get("clientId");
		request.setAttribute("clientId", cId);
		Provider p = (Provider) request.getSession().getAttribute("provider");
		refObj.setCompletionDate(new Date());
		refObj.setProviderNo(p.getProviderNo());
		refObj.setReferralDate(new Date());
		refObj.setStatus(KeyConstants.STATUS_ACTIVE);
		if(refObj.getId().intValue()==0) refObj.setId(null);
		if (refObj.getProgramId() == null
				|| refObj.getProgramId().intValue() <= 0) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.referral.program", request.getContextPath()));
			isError = true;
			saveMessages(request, messages);			
		}
		if(refObj.getNotes()!=null && refObj.getNotes().length()>4000){
			isError = true;			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.referral.reason", request.getContextPath()));			
			saveMessages(request, messages);
		}
		if(refObj.getPresentProblems()!=null && refObj.getPresentProblems().length()>4000){
			isError = true;			
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.referral.problem", request.getContextPath()));			
			saveMessages(request, messages);
		}
		if(isError){
			setEditAttributes(form, request);
			return mapping.findForward("edit");
		}
		clientManager.saveClientReferral(refObj);
		refForm.set("referral", refObj);
		if (!(isWarning || isError))
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.save.success", request.getContextPath()));
		saveMessages(request, messages);
		setEditAttributes(form, request);
		//return  edit(mapping,form,request,response);
		
		return mapping.findForward("edit");
	}

	private void setEditAttributes(ActionForm form, HttpServletRequest request) {
		DynaActionForm clientForm = (DynaActionForm) form;
		String cId =request.getParameter("clientId");
		ClientReferral crObj=(ClientReferral)clientForm.get("referral");
		if(Utility.IsEmpty(cId)&&crObj!=null){			
			cId = crObj.getClientId().toString();
		}
		HashMap actionParam = (HashMap) request.getAttribute("actionParam");
		if (actionParam == null) {
			actionParam = new HashMap();
			actionParam.put("clientId",cId );
		}
		request.setAttribute("actionParam", actionParam);
		String demographicNo = (String) actionParam.get("clientId");
		request.setAttribute("client", clientManager
				.getClientByDemographicNo(demographicNo));
		String rId = request.getParameter("rId");
		if(Utility.IsEmpty(rId) && crObj.getId()!=null) rId=crObj.getId().toString();		
		String programId = request.getParameter("selectedProgramId");
		// Integer
		// facilityId=(Integer)request.getSession().getAttribute(SessionConstants.CURRENT_FACILITY_ID);
		clientForm.set("clientId", demographicNo);
		request.setAttribute("clientId", demographicNo);
		super.setScreenMode(request, KeyConstants.TAB_CLIENT_REFER);
		String providerNo = ((Provider) request.getSession().getAttribute(
				"provider")).getProviderNo();		
		if ("0".equals(rId) || rId==null) {
			crObj = new ClientReferral();
			crObj.setId(new Integer(0));
			crObj.setStatus(KeyConstants.INTAKE_STATUS_ACTIVE);
			crObj.setClientId(Integer.valueOf(demographicNo));			
		} else if (!Utility.IsEmpty(rId)) 
		{			
			crObj = clientManager.getClientReferral(rId);			
			if(Utility.IsEmpty(programId)) programId=crObj.getProgramId().toString();
			boolean readOnly=super.isReadOnly(request,crObj.getStatus(), KeyConstants.FUN_PMM_CLIENTREFER,crObj.getProgramId());
			if(readOnly) request.setAttribute("isReadOnly", Boolean.valueOf(readOnly));
		}

		Program program = (Program) clientForm.get("program");
		if (!Utility.IsEmpty(programId)) {
			program = programManager.getProgram(programId);
			crObj.setProgramId(Integer.valueOf(programId));
			crObj.setFacilityId(program.getFacilityId());
			request.setAttribute("program", program);
		}
		
		clientForm.set("referral", crObj);
		request.setAttribute("referralStatus", crObj.getStatus());		
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
