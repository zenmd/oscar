package org.oscarehr.PMmodule.web;

import javax.servlet.http.HttpServletRequest;

import com.quatro.common.KeyConstants;
import com.quatro.service.security.SecurityManager;
import com.quatro.util.Utility;

public class BaseProgramAction extends BaseAction {

	protected void setScreenMode(HttpServletRequest request, String currentTab) {
		super.setMenu(request, KeyConstants.MENU_PROGRAM);
		SecurityManager sec = (SecurityManager) request.getSession()
				.getAttribute(KeyConstants.SESSION_KEY_SECURITY_MANAGER);
		//summary
		if (sec.GetAccess(KeyConstants.FUN_PMM_EDITPROGRAM_GENERAL, "").compareTo("r") >= 0) {
			request.setAttribute(KeyConstants.TAB_CLIENT_SUMMARY, KeyConstants.ACCESS_VIEW);
			if (currentTab.equals(KeyConstants.TAB_CLIENT_SUMMARY))request.setAttribute(KeyConstants.TAB_CLIENT_SUMMARY, KeyConstants.ACCESS_CURRENT);
		} else
			request.setAttribute(KeyConstants.TAB_CLIENT_SUMMARY, KeyConstants.ACCESS_NULL);		
	}
	public boolean isReadOnly(HttpServletRequest request, String status,String funName,Integer programId){
		boolean readOnly =false;
		if(KeyConstants.STATUS_COMPLETED.equals(status)) readOnly =true;
		else if(KeyConstants.STATUS_SIGNED.equals(status)) readOnly =true;
		else if(KeyConstants.STATUS_DISCHARGED.equals(status)) readOnly =true;
		else if(KeyConstants.STATUS_EXPIRED.equals(status)) readOnly =true;
		else if(KeyConstants.STATUS_TERMEARLY.equals(status)) readOnly =true;
		/*
		else if(KeyConstants.STATUS_ADMITTED.equals(status)){			
			if(KeyConstants.FUNCTION_INTAKE.equals(funName))readOnly =true;
			else if(KeyConstants.FUNCTION_ADMISSION.equals(funName))readOnly =true;	
			else if(KeyConstants.FUNCTION_REFERRAL.equals(funName))readOnly =true;	
		}
		*/
		else if(KeyConstants.STATUS_READONLY.equals(status)) readOnly =true;
		else if(KeyConstants.STATUS_REJECTED.equals(status)) {
			readOnly =true;
			if(KeyConstants.FUN_PMM_CLIENTREFER.equals(funName))readOnly =false;
		}
		SecurityManager sec = super.getSecurityManager(request);
		//summary
		String orgCd="";
		if(programId!=null ||programId!=0) orgCd=programId.toString();
		if (sec.GetAccess(funName, orgCd).compareTo(KeyConstants.ACCESS_READ) == 0) 
			readOnly=true;
		return readOnly;
	}

	
}
