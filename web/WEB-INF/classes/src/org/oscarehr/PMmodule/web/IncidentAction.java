package org.oscarehr.PMmodule.web;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.oscarehr.PMmodule.service.ComplaintManager;
import org.oscarehr.PMmodule.service.IncidentManager;
import org.oscarehr.PMmodule.web.formbean.IncidentForm;
import org.oscarehr.PMmodule.web.formbean.QuatroClientComplaintForm;

import oscar.MyDateFormat;

import com.quatro.model.Complaint;
import com.quatro.model.IncidentValue;
import com.quatro.model.LookupCodeValue;
import com.quatro.service.LookupManager;

public class IncidentAction extends DispatchAction {

	private ComplaintManager complaintManager;

	private LookupManager lookupManager;
	
	private IncidentManager incidentManager;

	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		return list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		System.out
				.println("=========== list ========= in IncidentAction");

		
		// TODO:
		Long programId =Long.valueOf("200011");
			
		List incidents = incidentManager.getIncidentsByProgramId(programId);

		request.setAttribute("incidents", incidents);

		return mapping.findForward("list");
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		System.out
				.println("=========== edit ========= in QuatroClientComplaintAction");
		/*
		HashMap actionParam = (HashMap) request.getAttribute("actionParam");
		if (actionParam == null) {
			actionParam = new HashMap();
			actionParam.put("id", request.getParameter("id"));
		}
		request.setAttribute("actionParam", actionParam);
*/
		String programId = request.getParameter("programId");
		request.setAttribute("id", programId);

		IncidentForm incidentForm = (IncidentForm) form;

		String incidentId = request.getParameter("incidentId");
		
		if (incidentId == null && incidentForm.getIncident() != null
				&& incidentForm.getIncident().getId() != null)
			incidentId = incidentForm.getIncident().getId().toString();

		IncidentValue incident = null;
		if (incidentId == null || "0".equals(incidentId)) {
			incident = new IncidentValue();
		} else {
			incident = incidentManager.getIncidentsById(Long.valueOf(incidentId));
		}
		

			
		
		String others = incident.getOtherInvolved();
		String natures = incident.getNature();
		String issues = incident.getClientIssues();
		String disposition = incident.getDisposition();
		if (others != null) {
			String[] othersArr = others.split(",");
			incidentForm.setOthersArr(othersArr);
		}
		
		if (natures != null) {
			String[] naturesArr = natures.split(",");
			incidentForm.setNaturesArr(naturesArr);
		}
		
		if (issues != null) {
			String[] issuesArr = issues.split(",");
			incidentForm.setIssuesArr(issuesArr);
		}
		
		if (disposition != null) {
			String[] dispositionArr = disposition.split(",");
			incidentForm.setDispositionArr(dispositionArr);
		}
		
		List<LookupCodeValue> clientIssuesLst = lookupManager.LoadCodeList("ICI", true,
				null, null);
		List<LookupCodeValue> dispositionLst = lookupManager.LoadCodeList("IDS", true,
				null, null);
		List<LookupCodeValue> natureLst = lookupManager.LoadCodeList("INI",
				true, null, null);
		List<LookupCodeValue> othersLst = lookupManager.LoadCodeList("IOI",
				true, null, null);

		int length = (natureLst.size()) / 2; // value of offset in JSP file
		
		incidentForm.setClientIssuesLst(clientIssuesLst);
		incidentForm.setDispositionLst(dispositionLst);
		incidentForm.setNatureLst(natureLst);
		incidentForm.setOthersLst(othersLst);

		incidentForm.setIncident(incident);

		request.setAttribute("IncidentForm_length", length);

		return mapping.findForward("edit");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out
				.println("=========== save ========= in IncidentAction");

		HashMap actionParam = (HashMap) request.getAttribute("actionParam");
		if (actionParam == null) {
			actionParam = new HashMap();
			actionParam.put("id", request.getParameter("id"));
		}
		request.setAttribute("actionParam", actionParam);

		String tmp = (String) actionParam.get("id");
		request.setAttribute("id", tmp);

		IncidentForm incidentForm = (IncidentForm) form;
		IncidentValue incident = incidentForm.getIncident();
		
		incident.setCreatedDate(MyDateFormat.getCalendar(incidentForm.getCreatedDateStr()));
		incident.setFollowupDate(MyDateFormat.getCalendar(incidentForm.getFollowupDateStr()));
		incident.setIncidentDate(MyDateFormat.getCalendar(incidentForm.getIncidentDateStr()));
		incident.setInvestigationDate(MyDateFormat.getCalendar(incidentForm.getInvestigationDateStr()));

		// TODO:
		Long programId =Long.valueOf("200011");
		
		if (incident.getId() == null || incident.getId() == 0) {
			incident.setId(null);
			incident.setCreatedDate(Calendar.getInstance());
			incident.setProgramId(programId);
		} 
		
		// checkboxes
		String[] othersArr = incidentForm.getOthersArr();
		String[] naturesArr = incidentForm.getNaturesArr();
		String[] issuesArr = incidentForm.getIssuesArr();
		String[] dispositionArr = incidentForm.getDispositionArr();
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < othersArr.length; i++) {
			sb.append("," + othersArr[i]);
		}
		incident.setOtherInvolved(sb.toString());
		
		StringBuilder sb2 = new StringBuilder();
		for (int i = 0; i < naturesArr.length; i++) {
			sb2.append("," + naturesArr[i]);
		}
		incident.setNature(sb2.toString());
		
		StringBuilder sb3 = new StringBuilder();
		for (int i = 0; i < issuesArr.length; i++) {
			sb3.append("," + issuesArr[i]);
		}
		incident.setClientIssues(sb3.toString());
		
		StringBuilder sb4 = new StringBuilder();
		for (int i = 0; i < dispositionArr.length; i++) {
			sb4.append("," + dispositionArr[i]);
		}
		incident.setDisposition(sb4.toString());

		
		
		
		Map map = request.getParameterMap();
		ActionMessages messages = new ActionMessages();

		try {
			incidentManager.save(incident);

			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.saveIncident.success", request.getContextPath()));
			saveMessages(request, messages);
			incidentForm.setIncident(incident);

		} catch (Exception e) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.saveIncident.failed", request.getContextPath()));
			saveMessages(request, messages);

		}
		return edit(mapping, form, request, response);
		// return mapping.findForward("edit");
	}


	public void setComplaintManager(ComplaintManager complaintManager) {
		this.complaintManager = complaintManager;
	}

	public void setLookupManager(LookupManager lookupManager) {
		this.lookupManager = lookupManager;
	}

	public void setIncidentManager(IncidentManager incidentManager) {
		this.incidentManager = incidentManager;
	}

}
