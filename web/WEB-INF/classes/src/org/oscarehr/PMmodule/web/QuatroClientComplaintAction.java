package org.oscarehr.PMmodule.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.oscarehr.PMmodule.service.ComplaintManager;
import org.oscarehr.PMmodule.web.formbean.QuatroClientComplaintForm;

import oscar.MyDateFormat;

import com.quatro.model.Complaint;
import com.quatro.model.LookupCodeValue;
import com.quatro.service.LookupManager;

public class QuatroClientComplaintAction extends DispatchAction {

	private ComplaintManager complaintManager;

	private LookupManager lookupManager;


	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		return list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		System.out
				.println("=========== list ========= in QuatroClientComplaintAction");
		HashMap actionParam = (HashMap) request.getAttribute("actionParam");
		if (actionParam == null) {
			actionParam = new HashMap();
			actionParam.put("clientId", request.getParameter("clientId"));
		}
		request.setAttribute("actionParam", actionParam);

		String tmp = (String) actionParam.get("clientId");
		request.setAttribute("clientId", tmp);

		// String tmp = request.getParameter("id");
		Long clientId = Long.valueOf(tmp);

		List complaints = complaintManager.getComplaintsByClientId(clientId);

		request.setAttribute("complaints", complaints);

		return mapping.findForward("list");
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		System.out
				.println("=========== edit ========= in QuatroClientComplaintAction");
		HashMap actionParam = (HashMap) request.getAttribute("actionParam");
		if (actionParam == null) {
			actionParam = new HashMap();
			actionParam.put("clientId", request.getParameter("clientId"));
		}
		request.setAttribute("actionParam", actionParam);

		String tmp = (String) actionParam.get("clientId");
		request.setAttribute("clientId", tmp);

		QuatroClientComplaintForm complaintForm = (QuatroClientComplaintForm) form;

		String complaintId = request.getParameter("complaintId");
		if (complaintId == null && complaintForm.getComplaint() != null
				&& complaintForm.getComplaint().getId() != null)
			complaintId = complaintForm.getComplaint().getId().toString();

		Complaint complaint = null;
		if (complaintId == null || "0".equals(complaintId)) {
			complaint = new Complaint();
		} else {
			complaint = complaintManager
					.getComplaint(Long.valueOf(complaintId));
		}
		if (complaint.getCompletedDate() != null)
			complaint.setCompletedDatex(MyDateFormat.getStandardDate(complaint
					.getCompletedDate()));
		if (complaint.getCreatedDate() != null)
			complaint.setCreatedDatex(MyDateFormat.getStandardDate(complaint
					.getCreatedDate()));
		if (complaint.getDate1() != null)
			complaint.setDate1x(MyDateFormat.getStandardDate(complaint
					.getDate1()));
		if (complaint.getDate2() != null)
			complaint.setDate2x(MyDateFormat.getStandardDate(complaint
					.getDate2()));
		if (complaint.getDate3() != null)
			complaint.setDate3x(MyDateFormat.getStandardDate(complaint
					.getDate3()));
		if (complaint.getDate4() != null)
			complaint.setDate4x(MyDateFormat.getStandardDate(complaint
					.getDate4()));

		String standards = complaint.getStandards();
		if (standards != null) {
			String[] standards1 = standards.split(",");
			complaint.setStandards1(standards1);
		}
				
		List<LookupCodeValue> sources = lookupManager.LoadCodeList("CPS", true,
				null, null);
		List<LookupCodeValue> methods = lookupManager.LoadCodeList("CPM", true,
				null, null);
		List<LookupCodeValue> outcomes = lookupManager.LoadCodeList("CPO",
				true, null, null);
		List<LookupCodeValue> allSections = lookupManager.LoadCodeList("CPB",
				true, null, null);

		int length = (allSections.size()) / 2; // value of offset in JSP file
		int parentPlace = 0;
		for (int i = 0; i < allSections.size(); i++) {
			LookupCodeValue item = allSections.get(i);
			if (item.getCode().equals(item.getParentCode())) { // section
				item.setParentCode("0");
				parentPlace = i;
				if (i < length && length - i < 2)
					length = length - 1;

			} else { // subsection
				if (i >= length && parentPlace < length)
					length = i + 1;
			}

		}

		complaintForm.setSources(sources);
		complaintForm.setMethods(methods);
		complaintForm.setOutcomes(outcomes);
		complaintForm.setSections(allSections);

		complaintForm.setComplaint(complaint);

		request.setAttribute("ComplaintForm_length", length);

		return mapping.findForward("edit");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out
				.println("=========== save ========= in QuatroClientComplaintAction");

		HashMap actionParam = (HashMap) request.getAttribute("actionParam");
		if (actionParam == null) {
			actionParam = new HashMap();
			actionParam.put("clientId", request.getParameter("clientId"));
		}
		request.setAttribute("actionParam", actionParam);

		String tmp = (String) actionParam.get("clientId");
		request.setAttribute("clientId", tmp);

		QuatroClientComplaintForm complaintForm = (QuatroClientComplaintForm) form;
		Complaint complaint = complaintForm.getComplaint();
		complaint.setDate1(MyDateFormat.getCalendar(complaint.getDate1x()));
		complaint.setDate2(MyDateFormat.getCalendar(complaint.getDate2x()));
		complaint.setDate3(MyDateFormat.getCalendar(complaint.getDate3x()));
		complaint.setDate4(MyDateFormat.getCalendar(complaint.getDate4x()));

		if (complaint.getCompletedDatex() != null
				&& complaint.getCompletedDatex().length() > 0) {
			complaint.setCompletedDate(MyDateFormat.getCalendar(complaint
					.getCompletedDatex()));
			complaint.setStatus("1");
		} else {
			complaint.setStatus("0");
		}
		if (complaint.getId() == null || complaint.getId() == 0) {
			complaint.setId(null);
			complaint.setCreatedDate(Calendar.getInstance());
			complaint.setClientId(Integer.valueOf(tmp));
		} else {
			String str = complaint.getCreatedDatexFromPage();
			Calendar c = MyDateFormat.getCalendar(str);
			complaint.setCreatedDate(c);
		}
		
		// checkboxes
		String[] standards1 = complaint.getStandards1();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < standards1.length; i++) {
			sb.append("," + standards1[i]);
		}
		
		complaint.setStandards(sb.toString());

		Map map = request.getParameterMap();
		ActionMessages messages = new ActionMessages();

		try {
			complaintManager.save(complaint);

			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.saveComplaint.success", request.getContextPath()));
			saveMessages(request, messages);
			complaintForm.setComplaint(complaint);

		} catch (Exception e) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.saveComplaint.failed", request.getContextPath()));
			saveMessages(request, messages);

		}
		return edit(mapping, form, request, response);
		
	}

	
	public void setComplaintManager(ComplaintManager complaintManager) {
		this.complaintManager = complaintManager;
	}

	public void setLookupManager(LookupManager lookupManager) {
		this.lookupManager = lookupManager;
	}

}
