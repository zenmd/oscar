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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
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

public class MergeClientAction extends BaseClientAction {
	
	private ClientManager clientManager;

	private ProviderManager providerManager;

	private ProgramManager programManager;	
	private LookupManager lookupManager;
	private MergeClientManager mergeClientManager;
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {	
		return mergeSearch(mapping, form, request, response);
	}
	public ActionForward mergedSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm searchForm = (DynaActionForm) form;
		ClientSearchFormBean formBean = (ClientSearchFormBean) searchForm
				.get("criteria");

		// formBean.setProgramDomain((List)request.getSession().getAttribute("program_domain"));
		boolean allowOnlyOptins = UserRoleUtils.hasRole(request, UserRoleUtils.Roles_external);
		if ("MyP".equals(formBean.getBedProgramId())) {
			Integer facilityId = (Integer) request.getSession().getAttribute(
					KeyConstants.SESSION_KEY_FACILITYID);
			String providerNo = (String) request.getSession().getAttribute(
					KeyConstants.SESSION_KEY_PROVIDERNO);
			List allBedPrograms = programManager.getProgramsByProvider(facilityId, providerNo);
			String prgId = "";
//			for (Program prg : allBedPrograms) {
			for (int i=0;i<allBedPrograms.size();i++) {
				Program prg = (Program)allBedPrograms.get(i);
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
			consent = Demographic.ConsentGiven_ALL;
		else if (emergencySearch != null)
			consent = Demographic.ConsentGiven_ALL;
		request.setAttribute("consent", consent);

		
		setLookupLists(request);

		return mapping.findForward("view");
	}
	public ActionForward unmerge(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionMessages messages = new ActionMessages();
		
		if (request.getParameterValues("records") == null) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.merge.errors.select", request.getContextPath()));
			saveMessages(request, messages);
			return mapping.findForward("view");
		}
		boolean isSuccess=true;
		ArrayList records = new ArrayList(Arrays.asList(request.getParameterValues("records")));
		String head = request.getParameter("head");
		String providerNo = (String) request.getSession().getAttribute(	KeyConstants.SESSION_KEY_PROVIDERNO);
		if (records.size() > 0) {				
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
						isSuccess=false;
					}
				}
		}
		if(!isSuccess){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.unmerge.errors", request.getContextPath()));
			saveMessages(request, messages);
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.merge.success", request.getContextPath()));
			saveMessages(request, messages);
		}
		return mapping.findForward("view");

	}
	public ActionForward merge(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionMessages messages = new ActionMessages();
		if (request.getParameterValues("records") == null) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.merge.errors.select", request.getContextPath()));
			saveMessages(request, messages);
			return mapping.findForward("view");			
		}
		boolean isSuccess = true;
		ArrayList records = new ArrayList(Arrays.asList(request.getParameterValues("records")));
		String head = request.getParameter("head");
		String action = request.getParameter("mergeAction");
		String providerNo = (String) request.getSession().getAttribute(
				KeyConstants.SESSION_KEY_PROVIDERNO);
		DemographicMerged dmDAO = new DemographicMerged();

		if (head != null && records.size() > 1
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
						isSuccess = false;
					}
			}

			} else {
			isSuccess = false;
		}
		if(!isSuccess){
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.merge.errors", request.getContextPath()));
			saveMessages(request, messages);
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"message.merge.success", request.getContextPath()));
			saveMessages(request, messages);
		}
		return mapping.findForward("view");
	}

	public ActionForward mergeSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		setLookupLists(request);		
		return mapping.findForward("view");
	}
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		DynaActionForm searchForm = (DynaActionForm) form;
		ClientSearchFormBean formBean = (ClientSearchFormBean) searchForm.get("criteria");
			
		boolean allowOnlyOptins = UserRoleUtils.hasRole(request,UserRoleUtils.Roles_external);		
		/* do the search */
		request.setAttribute("clients", clientManager.search(formBean,allowOnlyOptins));
		setLookupLists(request);		
		return mapping.findForward("view");
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
		List allProviders = providerManager.getActiveProviders(
				facilityId.toString(), null);
		request.setAttribute("allProviders", allProviders);
		request.setAttribute("genders", lookupManager.LoadCodeList("GEN", true,
				null, null));
		request.setAttribute("moduleName", " - Client Management");		
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
