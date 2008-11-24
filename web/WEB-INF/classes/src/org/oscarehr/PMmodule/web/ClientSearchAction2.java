/*
 * 
 * Copyright (c) 2001-2002. Centre for Research on Inner City Health, St. Michael's Hospital, Toronto. All Rights Reserved. *
 * This software is published under the GPL GNU General Public License. 
 * This program is free software; you can redistribute it and/or 
 * modify it under the terms of the GNU General Public License 
 * as published by the Free Software Foundation; either version 2 
 * of the License, or (at your option) any later version. * 
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
 * GNU General Public License for more details. * * You should have received a copy of the GNU General Public License 
 * along with this program; if not, write to the Free Software 
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA. * 
 * 
 * <OSCAR TEAM>
 * 
 * This software was written for 
 * Centre for Research on Inner City Health, St. Michael's Hospital, 
 * Toronto, Ontario, Canada 
 */

package org.oscarehr.PMmodule.web;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.oscarehr.PMmodule.model.Demographic;
import org.oscarehr.PMmodule.model.Program;
import org.oscarehr.PMmodule.model.Provider;
import org.oscarehr.PMmodule.service.ClientManager;
import org.oscarehr.PMmodule.service.LogManager;
import org.oscarehr.PMmodule.service.ProgramManager;
import org.oscarehr.PMmodule.service.ProviderManager;
import org.oscarehr.PMmodule.web.formbean.ClientSearchFormBean;
import org.oscarehr.PMmodule.web.utils.UserRoleUtils;
import com.quatro.common.KeyConstants;
import com.quatro.model.security.NoAccessException;
import com.quatro.service.LookupManager;
import com.quatro.util.Utility;
import org.apache.struts.actions.DispatchAction;

public class ClientSearchAction2 extends BaseClientAction {

	private LookupManager lookupManager;

	private ClientManager clientManager;

	private LogManager logManager;

	private ProgramManager programManager;

	private ProviderManager providerManager;

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (!Utility.IsEmpty(request.getParameter("client"))) {
			request.getSession().setAttribute(
					KeyConstants.SESSION_KEY_CURRENT_FUNCTION, "client");
		}
		return form(mapping, form, request, response);
	}

	private ActionForward form(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
		  HashMap actionParam = (HashMap) request.getAttribute("actionParam");
	       if(actionParam==null){
	    	  actionParam = new HashMap();
	          actionParam.put("clientId", request.getParameter("clientId")); 
	       }
	       request.setAttribute("actionParam", actionParam);
			if (clientManager.isOutsideOfDomainEnabled()) {
				request.getSession().setAttribute("outsideOfDomainEnabled", "true");
			} else {
				request.getSession()
						.setAttribute("outsideOfDomainEnabled", "false");
			}
	
			setLookupLists(request);
			DynaActionForm searchForm = (DynaActionForm) form;
			ClientSearchFormBean formBean = (ClientSearchFormBean) searchForm
					.get("criteria");
			if ("cv".equals(request.getSession().getAttribute(
					KeyConstants.SESSION_KEY_CURRENT_FUNCTION))) {
				formBean.setBedProgramId("MyP");
			}
			/*
			if (null != request.getSession().getAttribute(
					KeyConstants.SESSION_KEY_CLIENTID)) {
				String cId = request.getSession().getAttribute(
						KeyConstants.SESSION_KEY_CLIENTID).toString();
				List lst = new ArrayList();
				lst.add(this.clientManager.getClientByDemographicNo(cId));
				request.setAttribute("clients", lst);
			}
			*/
			Integer shelterId = (Integer) request.getSession().getAttribute(KeyConstants.SESSION_KEY_SHELTERID);
			String providerNo = (String) request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
			//check new Client link
			List allBedPrograms = programManager.getBedPrograms(providerNo, shelterId);
			String prgId = "0";
			if(allBedPrograms.size()>0){
				Program prg = (Program)allBedPrograms.get(0);
				prgId =prg.getId().toString();
			}
			request.setAttribute("searchClient", "true");
			super.setScreenMode(request, KeyConstants.FUN_CLIENT);		
			return mapping.findForward("form");
	   }
	   catch(NoAccessException e)
	   {
		   return mapping.findForward("failure");
	   }
	}
	
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws NoAccessException {

		super.getAccess(request, KeyConstants.FUN_CLIENT, new Integer(0));
		DynaActionForm searchForm = (DynaActionForm) form;
		ClientSearchFormBean formBean = (ClientSearchFormBean) searchForm
				.get("criteria");

		// formBean.setProgramDomain((List)request.getSession().getAttribute("program_domain"));
		boolean allowOnlyOptins = UserRoleUtils.hasRole(request,
				UserRoleUtils.Roles_external);
		if ("MyP".equals(formBean.getBedProgramId())) {
			Integer shelterId = (Integer) request.getSession().getAttribute(
					KeyConstants.SESSION_KEY_SHELTERID);
			String providerNo = (String) request.getSession().getAttribute(
					KeyConstants.SESSION_KEY_PROVIDERNO);
			//List allBedPrograms = programManager.getBedPrograms(providerNo, shelterId);
			List allPrograms = programManager.getPrograms(Program.PROGRAM_STATUS_ACTIVE,providerNo,shelterId);
			String prgId = "";
//			for (Program prg : allBedPrograms) {
			for (int i=0;i<allPrograms.size();i++) {
				Program prg = (Program)allPrograms.get(i);
				prgId += prg.getId().toString() + ",";
			}
			if (!"".equals(prgId)) {
				prgId = prgId.substring(0, prgId.length() - 1);
			}
			else
			{
				prgId = "1";
			}
			formBean.setBedProgramId(prgId);
		}

		/* do the search */
		request.setAttribute("clients", clientManager.search(formBean,allowOnlyOptins,false));

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

		if (formBean.isSearchOutsideDomain()) {
			logManager.log("read", "out of domain client search", "", request);
		}
		setLookupLists(request);

		return mapping.findForward("form");
	}

	private void setLookupLists(HttpServletRequest request) {
		Integer shelterId = (Integer) request.getSession().getAttribute(
				KeyConstants.SESSION_KEY_SHELTERID);
		String providerNo = (String) request.getSession().getAttribute(
				KeyConstants.SESSION_KEY_PROVIDERNO);
		List allPrograms = programManager.getPrograms(Program.PROGRAM_STATUS_ACTIVE,providerNo, shelterId);

		request.setAttribute("allBedPrograms", allPrograms);
		List allProviders = providerManager.getActiveProviders(providerNo,
				shelterId);
		request.setAttribute("allProviders", allProviders);
		request.setAttribute("genders", lookupManager.LoadCodeList("GEN", true,
				null, null));
		request.setAttribute("moduleName", " - Client Management");
		if ("cv".equals(request.getSession().getAttribute(
				KeyConstants.SESSION_KEY_CURRENT_FUNCTION))) {
			request.setAttribute(KeyConstants.SESSION_KEY_CURRENT_FUNCTION,
					"cv");
//			request.setAttribute("moduleName", " - Case Management");
		}
	}

	public void setLookupManager(LookupManager lookupManager) {
		this.lookupManager = lookupManager;
	}

	public void setClientManager(ClientManager mgr) {
		this.clientManager = mgr;
	}

	public void setLogManager(LogManager mgr) {
		this.logManager = mgr;
	}

	public void setProgramManager(ProgramManager mgr) {
		this.programManager = mgr;
	}

	public void setProviderManager(ProviderManager providerManager) {
		this.providerManager = providerManager;
	}

}
