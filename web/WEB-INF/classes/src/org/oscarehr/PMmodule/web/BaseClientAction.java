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

import javax.servlet.http.HttpServletRequest;

import org.oscarehr.PMmodule.service.AdmissionManager;
import org.oscarehr.PMmodule.service.ClientManager;
import org.oscarehr.casemgmt.service.CaseManagementManager;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.quatro.common.KeyConstants;
import com.quatro.service.security.SecurityManager;

public abstract class BaseClientAction extends BaseAction {

	protected void setScreenMode(HttpServletRequest request, String currentTab) {
		super.setMenu(request, KeyConstants.MENU_CLIENT);
		SecurityManager sec = super.getSecurityManager(request);
		//summary
		if (sec.GetAccess("_pmm.clientSearch", "").compareTo("r") >= 0) {
			request.setAttribute(KeyConstants.TAB_CLIENT_SUMMARY, KeyConstants.ACCESS_VIEW);
			if (currentTab.equals(KeyConstants.TAB_CLIENT_SUMMARY))request.setAttribute(KeyConstants.TAB_CLIENT_SUMMARY, KeyConstants.ACCESS_CURRENT);
		} else
			request.setAttribute(KeyConstants.TAB_CLIENT_SUMMARY, KeyConstants.ACCESS_NULL);
		//discharge
		if (sec.GetAccess("_pmm_clientDischarge", "").compareTo("r") >= 0) {
			request.setAttribute(KeyConstants.TAB_CLIENT_DISCHARGE, KeyConstants.ACCESS_VIEW);
			if (currentTab.equals(KeyConstants.TAB_CLIENT_DISCHARGE))	request.setAttribute(KeyConstants.TAB_CLIENT_DISCHARGE, KeyConstants.ACCESS_CURRENT);
		}else request.setAttribute(KeyConstants.TAB_CLIENT_DISCHARGE, KeyConstants.ACCESS_NULL);
		//admission
		if (sec.GetAccess("_pmm_clientAdmission", "").compareTo("r") >= 0) {
			request.setAttribute(KeyConstants.TAB_CLIENT_ADMISSION, KeyConstants.ACCESS_VIEW);
			if(currentTab.equals(KeyConstants.TAB_CLIENT_ADMISSION))request.setAttribute(KeyConstants.TAB_CLIENT_ADMISSION, KeyConstants.ACCESS_CURRENT);
		}
		else request.setAttribute(KeyConstants.TAB_CLIENT_ADMISSION, KeyConstants.ACCESS_NULL);
		//consent
		if (sec.GetAccess("_pmm_clientConsent", "").compareTo("r") >= 0) {
			request.setAttribute(KeyConstants.TAB_CLIENT_CONSENT, KeyConstants.ACCESS_VIEW);
			if(currentTab.equals(KeyConstants.TAB_CLIENT_CONSENT))request.setAttribute(KeyConstants.TAB_CLIENT_CONSENT, KeyConstants.ACCESS_CURRENT);
		}
		else request.setAttribute(KeyConstants.TAB_CLIENT_CONSENT, KeyConstants.ACCESS_NULL);
		//history
		if (sec.GetAccess("_pmm_clientHistory", "").compareTo("r") >= 0) {
			request.setAttribute(KeyConstants.TAB_CLIENT_HISTORY, KeyConstants.ACCESS_VIEW);
			if (currentTab.equals(KeyConstants.TAB_CLIENT_HISTORY))request.setAttribute(KeyConstants.TAB_CLIENT_HISTORY, KeyConstants.ACCESS_CURRENT);
		}
		else request.setAttribute(KeyConstants.TAB_CLIENT_HISTORY, KeyConstants.ACCESS_NULL);
		//intake
		if (sec.GetAccess("_pmm_clientIntake", "").compareTo("r") >= 0) {
			request.setAttribute(KeyConstants.TAB_CLIENT_INTAKE, KeyConstants.ACCESS_VIEW);
			if(currentTab.equals(KeyConstants.TAB_CLIENT_INTAKE))request.setAttribute(KeyConstants.TAB_CLIENT_INTAKE, KeyConstants.ACCESS_CURRENT);
		}
		else request.setAttribute(KeyConstants.TAB_CLIENT_INTAKE, KeyConstants.ACCESS_NULL);
		//refer
		if (sec.GetAccess("_pmm_clientRefer", "").compareTo("r") >= 0) {
			request.setAttribute(KeyConstants.TAB_CLIENT_REFER, KeyConstants.ACCESS_VIEW);
			if(currentTab.equals(KeyConstants.TAB_CLIENT_REFER))request.setAttribute(KeyConstants.TAB_CLIENT_REFER, KeyConstants.ACCESS_CURRENT);
		}
		else request.setAttribute(KeyConstants.TAB_CLIENT_REFER, KeyConstants.ACCESS_NULL);
		//restriction
		if (sec.GetAccess("_pmm_clientRestriction", "").compareTo("r") >= 0) {
			request.setAttribute(KeyConstants.TAB_CLIENT_RESTRICTION, KeyConstants.ACCESS_VIEW);
			if(currentTab.equals(KeyConstants.TAB_CLIENT_RESTRICTION))request.setAttribute(KeyConstants.TAB_CLIENT_RESTRICTION, KeyConstants.ACCESS_CURRENT);
		}
		else request.setAttribute(KeyConstants.TAB_CLIENT_RESTRICTION, KeyConstants.ACCESS_NULL);
		//complaint
		if (sec.GetAccess("_pmm_clientComplaint", "").compareTo("r") >= 0) {
			request.setAttribute(KeyConstants.TAB_CLIENT_COMPLAINT, KeyConstants.ACCESS_VIEW);
			if(currentTab.equals(KeyConstants.TAB_CLIENT_COMPLAINT))request.setAttribute(KeyConstants.TAB_CLIENT_COMPLAINT, KeyConstants.ACCESS_CURRENT);
		}
		else request.setAttribute(KeyConstants.TAB_CLIENT_COMPLAINT, KeyConstants.ACCESS_NULL);
		//case
		if (sec.GetAccess("_pmm_clientCase", "").compareTo("r") >= 0) {
			request.setAttribute(KeyConstants.TAB_CLIENT_CASE, KeyConstants.ACCESS_VIEW);
			if (currentTab.equals(KeyConstants.TAB_CLIENT_CASE))request.setAttribute(KeyConstants.TAB_CLIENT_CASE, KeyConstants.ACCESS_CURRENT);
		}
		else request.setAttribute(KeyConstants.TAB_CLIENT_CASE, KeyConstants.ACCESS_NULL);
		//attachment
		if (sec.GetAccess("_pmm.document", "").compareTo("r") >= 0) {
			request.setAttribute(KeyConstants.TAB_CLIENT_ATTCHMENT, KeyConstants.ACCESS_VIEW);
			if (currentTab.equals(KeyConstants.TAB_CLIENT_ATTCHMENT))request.setAttribute(KeyConstants.TAB_CLIENT_ATTCHMENT, KeyConstants.ACCESS_CURRENT);
		}
		else request.setAttribute(KeyConstants.TAB_CLIENT_ATTCHMENT, KeyConstants.ACCESS_NULL);

		//task
		if (sec.GetAccess("_pmm.task", "").compareTo("r") >= 0) {
			request.setAttribute(KeyConstants.TAB_CLIENT_TASK, KeyConstants.ACCESS_VIEW);
			if (currentTab.equals(KeyConstants.TAB_CLIENT_TASK))request.setAttribute(KeyConstants.TAB_CLIENT_TASK, KeyConstants.ACCESS_CURRENT);
		}
		else request.setAttribute(KeyConstants.TAB_CLIENT_TASK, KeyConstants.ACCESS_NULL);
	}
	
	

	public CaseManagementManager getCaseManagementManager() {
		return (CaseManagementManager) getAppContext().getBean(
				"caseManagementManager");
	}

	public AdmissionManager getAdmissionManager() {
		return (AdmissionManager) getAppContext().getBean("admissionManager");
	}
	public ClientManager getClientManager() {
		return (ClientManager) getAppContext().getBean("clientManager");
	}


	public ApplicationContext getAppContext() {
	return WebApplicationContextUtils.getWebApplicationContext(getServlet()
			.getServletContext());
	}	
}