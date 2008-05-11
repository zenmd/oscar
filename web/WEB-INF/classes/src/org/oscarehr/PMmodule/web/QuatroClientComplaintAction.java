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
		/*
		 * QuatroClientComplaintForm clientForm = (QuatroClientComplaintForm)
		 * form; Integer clientId =
		 * (Integer)request.getSession().getAttribute("clientId"); if (clientId !=
		 * null) request.setAttribute(ID, clientId.toString());
		 */
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
			complaint.setClientId(Long.valueOf(tmp));
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
		// return mapping.findForward("edit");
	}

	/*
	 * private void setEditAttributes(ActionForm form, HttpServletRequest
	 * request) { QuatroClientComplaintForm clientForm =
	 * (QuatroClientComplaintForm) form;
	 * 
	 * HashMap actionParam = (HashMap) request.getAttribute("actionParam");
	 * if(actionParam==null){ actionParam = new HashMap(); actionParam.put("id",
	 * request.getParameter("id")); } request.setAttribute("actionParam",
	 * actionParam); String demographicNo= (String)actionParam.get("id");
	 * 
	 * QuatroComplaint complaint = new QuatroComplaint(); ArrayList<LabelValueBean>
	 * accessToShelters = new ArrayList<LabelValueBean>();
	 * accessToShelters.add(new LabelValueBean("Admission and Discharge", "1"));
	 * accessToShelters.add(new LabelValueBean("Bed Registration for Incoming
	 * and Registed Residents", "2")); accessToShelters.add(new
	 * LabelValueBean("Occupied Bed", "3")); accessToShelters.add(new
	 * LabelValueBean("Overnight Passes and leaves with Permission", "4"));
	 * accessToShelters.add(new LabelValueBean("Substance Use", "5"));
	 * accessToShelters.add(new LabelValueBean("Service Restrions(Barrings)",
	 * "6")); accessToShelters.add(new LabelValueBean("Meeting the Needs of
	 * Transgendered/Transsexual/Two-spirited Redidents", "7"));
	 * complaint.setAccessToShelters(accessToShelters);
	 * 
	 * ArrayList<LabelValueBean> healthSafetys = new ArrayList<LabelValueBean>();
	 * healthSafetys.add(new LabelValueBean("Health Standards", "1"));
	 * healthSafetys.add(new LabelValueBean("Safety Standards", "2"));
	 * healthSafetys.add(new LabelValueBean("Resident Medication", "3"));
	 * healthSafetys.add(new LabelValueBean("Weapons", "4"));
	 * complaint.setHealthSafetys(healthSafetys);
	 * 
	 * ArrayList<LabelValueBean> organizationStandards = new ArrayList<LabelValueBean>();
	 * organizationStandards.add(new LabelValueBean("Orginizational Status for
	 * Purchase-of Service Shelter", "1")); organizationStandards.add(new
	 * LabelValueBean("Governmance", "2")); organizationStandards.add(new
	 * LabelValueBean("Financial Accountability", "3"));
	 * organizationStandards.add(new LabelValueBean("Program Accountability",
	 * "4")); organizationStandards.add(new LabelValueBean("Conflict of
	 * Interest", "5"));
	 * complaint.setOrganizationStandards(organizationStandards);
	 * 
	 * ArrayList<LabelValueBean> programStandards = new ArrayList<LabelValueBean>();
	 * programStandards.add(new LabelValueBean("Provision of Essential
	 * Services", "1")); programStandards.add(new LabelValueBean("Conselling
	 * Supportants", "2")); programStandards.add(new LabelValueBean("Daytime
	 * Access", "3")); programStandards.add(new LabelValueBean("Services to
	 * Children", "4")); programStandards.add(new LabelValueBean("Duty to Report
	 * Suspected Cases of Child Abuse", "5")); programStandards.add(new
	 * LabelValueBean("Confidentiality", "6")); programStandards.add(new
	 * LabelValueBean("Sharing of Resident Information", "7"));
	 * programStandards.add(new LabelValueBean("Resident Information and
	 * Resident Files", "8")); programStandards.add(new LabelValueBean("Staff
	 * Code of Conduct", "9")); complaint.setProgramStandards(programStandards);
	 * 
	 * ArrayList<LabelValueBean> residentRights = new ArrayList<LabelValueBean>();
	 * residentRights.add(new LabelValueBean("Resident Input", "1"));
	 * residentRights.add(new LabelValueBean("Complaints and Appeals", "2"));
	 * complaint.setResidentRights(residentRights);
	 * 
	 * clientForm.setComplaint(complaint);
	 * 
	 * 
	 * QuatroComplaintOutcome outcome = new QuatroComplaintOutcome();
	 * clientForm.setOutcome(outcome);
	 * 
	 * ArrayList<LabelValueBean> titleList = new ArrayList<LabelValueBean>();
	 * titleList.add(new LabelValueBean("Mr.", "1")); titleList.add(new
	 * LabelValueBean("Ms.", "2")); clientForm.setTitleList(titleList);
	 * 
	 * ArrayList<LabelValueBean> radioStatuses = new ArrayList<LabelValueBean>();
	 * radioStatuses.add(new LabelValueBean("Completed", "1"));
	 * radioStatuses.add(new LabelValueBean("In Process", "2"));
	 * clientForm.setRadioStatuses(radioStatuses);
	 * 
	 * ArrayList<LabelValueBean> radioYNs = new ArrayList<LabelValueBean>();
	 * radioYNs.add(new LabelValueBean("Yes", "1")); radioYNs.add(new
	 * LabelValueBean("No", "0")); clientForm.setRadioYNs(radioYNs);
	 * 
	 * ArrayList<LabelValueBean> outcomeSatisfiedList = new ArrayList<LabelValueBean>();
	 * outcomeSatisfiedList.add(new LabelValueBean("Partial", "1"));
	 * outcomeSatisfiedList.add(new LabelValueBean("Unknown-No complainant
	 * follow-up", "2")); outcomeSatisfiedList.add(new LabelValueBean("Yes",
	 * "3")); outcomeSatisfiedList.add(new LabelValueBean("Not Applicable",
	 * "4")); outcomeSatisfiedList.add(new LabelValueBean("No", "5"));
	 * clientForm.setOutcomeSatisfiedList(outcomeSatisfiedList);
	 * 
	 * ArrayList<LabelValueBean> sourceComplaintList = new ArrayList<LabelValueBean>();
	 * sourceComplaintList.add(new LabelValueBean("layor's office", "1"));
	 * sourceComplaintList.add(new LabelValueBean("Agency/Orgnization", "2"));
	 * sourceComplaintList.add(new LabelValueBean("W/ODSP Worker", "3"));
	 * sourceComplaintList.add(new LabelValueBean("Community Member", "4"));
	 * sourceComplaintList.add(new LabelValueBean("lient", "5"));
	 * sourceComplaintList.add(new LabelValueBean("Police", "6"));
	 * sourceComplaintList.add(new LabelValueBean("Family Doctor", "7"));
	 * sourceComplaintList.add(new LabelValueBean("Other", "8"));
	 * sourceComplaintList.add(new LabelValueBean("Family Member", "9"));
	 * sourceComplaintList.add(new LabelValueBean("Councillor's Office
	 * /Politician", "10")); sourceComplaintList.add(new LabelValueBean("Shelter
	 * Staff", "11")); sourceComplaintList.add(new
	 * LabelValueBean("Advocate/Advocacy Group", "12"));
	 * sourceComplaintList.add(new LabelValueBean("Agency Worker", "13"));
	 * clientForm.setSourceComplaintList(sourceComplaintList);
	 * 
	 * 
	 * ArrayList<LabelValueBean> methodContactList = new ArrayList<LabelValueBean>();
	 * methodContactList.add(new LabelValueBean("In-Person", "1"));
	 * methodContactList.add(new LabelValueBean("Letter", "2"));
	 * methodContactList.add(new LabelValueBean("Fax", "3"));
	 * methodContactList.add(new LabelValueBean("E-mail", "4"));
	 * methodContactList.add(new LabelValueBean("Telephone", "5"));
	 * clientForm.setMethodContactList(methodContactList); }
	 */

	public void setComplaintManager(ComplaintManager complaintManager) {
		this.complaintManager = complaintManager;
	}

	public void setLookupManager(LookupManager lookupManager) {
		this.lookupManager = lookupManager;
	}

}
