package org.oscarehr.PMmodule.web;

import javax.servlet.http.HttpServletRequest;

import com.quatro.common.KeyConstants;
import com.quatro.service.security.SecurityManager;

public class BaseProgramAction extends BaseAction {

	protected void setScreenMode(HttpServletRequest request, String currentTab) {
		super.setMenu(request, KeyConstants.MENU_PROGRAM);
		SecurityManager sec = (SecurityManager) request.getSession()
				.getAttribute(KeyConstants.SESSION_KEY_SECURITY_MANAGER);
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
	}
}
