package org.oscarehr.PMmodule.web;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.oscarehr.PMmodule.model.ClientMerge;
import org.oscarehr.PMmodule.model.Demographic;
import org.oscarehr.PMmodule.model.Program;
import org.oscarehr.PMmodule.model.Provider;
import org.oscarehr.PMmodule.service.ClientManager;
import org.oscarehr.PMmodule.service.MergeClientManager;
import org.oscarehr.PMmodule.service.ProgramManager;
import org.oscarehr.PMmodule.service.ProviderManager;

import org.oscarehr.PMmodule.web.formbean.ClientSearchFormBean;
import org.oscarehr.PMmodule.web.utils.UserRoleUtils;

import oscar.oscarDemographic.data.DemographicMerged;


import com.quatro.common.KeyConstants;
import com.quatro.service.LookupManager;
import com.quatro.util.Utility;

public class MergeClientAction extends BaseAction {
	
	private ClientManager clientManager;

	private ProviderManager providerManager;

	private ProgramManager programManager;	
	private LookupManager lookupManager;
	private MergeClientManager mergeClientManager;
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (!Utility.IsEmpty(request.getParameter("client"))) {
			request.getSession().setAttribute(
					KeyConstants.SESSION_KEY_CURRENT_FUNCTION, "client");
		}
		return form(mapping, form, request, response);
	}
	public ActionForward mergedSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm searchForm = (DynaActionForm) form;
		ClientSearchFormBean formBean = (ClientSearchFormBean) searchForm
				.get("criteria");

		// formBean.setProgramDomain((List)request.getSession().getAttribute("program_domain"));
		boolean allowOnlyOptins = UserRoleUtils.hasRole(request,
				UserRoleUtils.Roles.external);
		if ("MyP".equals(formBean.getBedProgramId())) {
			Integer facilityId = (Integer) request.getSession().getAttribute(
					KeyConstants.SESSION_KEY_FACILITYID);
			String providerNo = (String) request.getSession().getAttribute(
					KeyConstants.SESSION_KEY_PROVIDERNO);
			List<Program> allBedPrograms = programManager
					.getProgramsByProvider(facilityId, providerNo);
			String prgId = "";
			for (Program prg : allBedPrograms) {
				prgId += prg.getId().toString() + ",";
			}
			if (!"".equals(prgId))
				prgId = prgId.substring(0, prgId.length() - 1);
			formBean.setBedProgramId(prgId);
		}

		/* do the search */
		request.setAttribute("clients", clientManager.search(formBean,
				allowOnlyOptins));

		// sort out the consent type used to search
		String consentSearch = StringUtils.trimToNull(request
				.getParameter("search_with_consent"));
		String emergencySearch = StringUtils.trimToNull(request
				.getParameter("emergency_search"));
		String consent = null;

		if (consentSearch != null && emergencySearch != null)
			throw (new IllegalStateException(
					"This is an unexpected state, both search_with_consent and emergency_search are not null."));
		else if (consentSearch != null)
			consent = Demographic.ConsentGiven.ALL.name();
		else if (emergencySearch != null)
			consent = Demographic.ConsentGiven.ALL.name();
		request.setAttribute("consent", consent);

		
		setLookupLists(request);

		return mapping.findForward("form");
	}
	public ActionForward merge(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (request.getParameterValues("records") == null) {
			return mapping.findForward("failure");
		}
		String outcome = "success";
		ArrayList records = new ArrayList(Arrays.asList(request.getParameterValues("records")));
		String head = request.getParameter("head");
		String action = request.getParameter("mergeAction");
		String providerNo = (String) request.getSession().getAttribute(
				KeyConstants.SESSION_KEY_PROVIDERNO);
		DemographicMerged dmDAO = new DemographicMerged();

		if (action.equals("merge") && head != null && records.size() > 1
				&& records.contains(head)) {

			for (int i = 0; i < records.size(); i++) {
				if (!((String) records.get(i)).equals(head))
					try {
						ClientMerge cmObj = new ClientMerge(); 
						cmObj.setClientId(Integer.valueOf((String) records.get(i)));
						cmObj.setMergedToClientId(Integer.valueOf(head));
						cmObj.setProviderNo(providerNo);
						cmObj.setLastUpdateDate(new GregorianCalendar());
						mergeClientManager.merge(cmObj);
					} catch (Exception e) {
						outcome = "failure";
					}
			}

		} else if (action.equals("unmerge") && records.size() > 0) {
			outcome = "successUnMerge";
			for (int i = 0; i < records.size(); i++) {
				String demographic_no = (String) records.get(i);
				try {					
					ClientMerge cmObj = new ClientMerge(); 
					cmObj.setClientId(Integer.valueOf(demographic_no));
					cmObj.setMergedToClientId(Integer.valueOf(head));
					cmObj.setProviderNo(providerNo);
					cmObj.setLastUpdateDate(new GregorianCalendar());
					mergeClientManager.unMerge(cmObj);
				} catch (Exception e) {
					outcome = "failureUnMerge";
				}
			}

		} else {
			outcome = "failure";
		}
		request.setAttribute("mergeoutcome", outcome);
		return mapping.findForward(outcome);
	}

	public ActionForward form(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		setLookupLists(request);
		DynaActionForm searchForm = (DynaActionForm) form;
		ClientSearchFormBean formBean = (ClientSearchFormBean) searchForm.get("criteria");
			
		boolean allowOnlyOptins = UserRoleUtils.hasRole(request,UserRoleUtils.Roles.external);		
		/* do the search */
		request.setAttribute("clients", clientManager.search(formBean,allowOnlyOptins));
		
		return mapping.findForward("form");
	}
	private void setLookupLists(HttpServletRequest request) {
		Integer facilityId = (Integer) request.getSession().getAttribute(
				KeyConstants.SESSION_KEY_FACILITYID);
		String providerNo = (String) request.getSession().getAttribute(
				KeyConstants.SESSION_KEY_PROVIDERNO);
		List allBedPrograms = programManager.getProgramsByProvider(facilityId,
				providerNo);

		request.setAttribute("allBedPrograms", allBedPrograms);

		request.setAttribute("allBedPrograms", allBedPrograms);
		List<Provider> allProviders = providerManager.getActiveProviders(
				facilityId.toString(), null);
		request.setAttribute("allProviders", allProviders);
		request.setAttribute("genders", lookupManager.LoadCodeList("GEN", true,
				null, null));
		request.setAttribute("moduleName", " - Client Management");
		if ("cv".equals(request.getSession().getAttribute(
				KeyConstants.SESSION_KEY_CURRENT_FUNCTION))) {
			request.setAttribute(KeyConstants.SESSION_KEY_CURRENT_FUNCTION,
					"cv");
			request.setAttribute("moduleName", " - Case Management");
		}
	}
	public void setClientManager(ClientManager clientManager) {
		this.clientManager = clientManager;
	}
	public void setLookupManager(LookupManager lookupManager) {
		this.lookupManager = lookupManager;
	}
	public void setProgramManager(ProgramManager programManager) {
		this.programManager = programManager;
	}
	public void setProviderManager(ProviderManager providerManager) {
		this.providerManager = providerManager;
	}
	public void setMergeClientManager(MergeClientManager mergeClientManager) {
		this.mergeClientManager = mergeClientManager;
	}
}
