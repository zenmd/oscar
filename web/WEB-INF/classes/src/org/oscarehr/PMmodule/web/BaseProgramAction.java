package org.oscarehr.PMmodule.web;

import javax.servlet.http.HttpServletRequest;

import com.quatro.common.KeyConstants;
import com.quatro.service.security.SecurityManager;
import com.quatro.util.Utility;

public class BaseProgramAction extends BaseAction {

	
	protected void setViewScreenMode(HttpServletRequest request, String currentTab,Integer orgId) {
		super.setMenu(request, KeyConstants.MENU_PROGRAM);
		SecurityManager sec = super.getSecurityManager(request);
		// general
		
		if (sec.GetAccess(KeyConstants.FUN_PROGRAM, orgId.toString()).compareTo(KeyConstants.ACCESS_READ) >= 0) {
			request.setAttribute(KeyConstants.TAB_PROGRAM_GENERAL,	KeyConstants.ACCESS_VIEW);
			if (currentTab.equals(KeyConstants.TAB_PROGRAM_GENERAL))
				request.setAttribute(KeyConstants.TAB_PROGRAM_GENERAL,	KeyConstants.ACCESS_CURRENT);
		}
		else request.setAttribute(KeyConstants.TAB_PROGRAM_GENERAL,	KeyConstants.ACCESS_NULL);
		
		
		// Queue
		if (sec.GetAccess(KeyConstants.FUN_PROGRAM_QUEUE, orgId.toString()).compareTo(KeyConstants.ACCESS_READ) >= 0) {
			request.setAttribute(KeyConstants.TAB_PROGRAM_QUEUE,	KeyConstants.ACCESS_VIEW);
			if (currentTab.equals(KeyConstants.TAB_PROGRAM_QUEUE))
				request.setAttribute(KeyConstants.TAB_PROGRAM_QUEUE,	KeyConstants.ACCESS_CURRENT);
		} 
		else request.setAttribute(KeyConstants.TAB_PROGRAM_QUEUE,	KeyConstants.ACCESS_NULL);
		
		
		// Clients
		if (sec.GetAccess(KeyConstants.FUN_PROGRAM_CLIENTS  , orgId.toString()).compareTo(KeyConstants.ACCESS_READ) >= 0) {
			request.setAttribute(KeyConstants.TAB_PROGRAM_CLIENTS,	KeyConstants.ACCESS_VIEW);
			if (currentTab.equals(KeyConstants.TAB_PROGRAM_CLIENTS))
				request.setAttribute(KeyConstants.TAB_PROGRAM_CLIENTS,	KeyConstants.ACCESS_CURRENT);
		} 
		else request.setAttribute(KeyConstants.TAB_PROGRAM_CLIENTS,KeyConstants.ACCESS_NULL);
		
		
		//	Incidents
		if (sec.GetAccess(KeyConstants.FUN_PROGRAM_INCIDENT, orgId.toString()).compareTo(KeyConstants.ACCESS_READ) >= 0) {
			request.setAttribute(KeyConstants.TAB_PROGRAM_INCIDENTS,KeyConstants.ACCESS_VIEW);
			if (currentTab.equals(KeyConstants.TAB_PROGRAM_INCIDENTS))
				request.setAttribute(KeyConstants.TAB_PROGRAM_INCIDENTS,KeyConstants.ACCESS_CURRENT);
		} 
		else
			request.setAttribute(KeyConstants.TAB_PROGRAM_INCIDENTS,KeyConstants.ACCESS_NULL);
		
		
		//	Bed
		if (sec.GetAccess(KeyConstants.FUN_PROGRAM_SERVICERESTRICTIONS, orgId.toString()).compareTo(KeyConstants.ACCESS_READ) >= 0) {
			request.setAttribute(KeyConstants.TAB_PROGRAM_SEVICE,	KeyConstants.ACCESS_VIEW);
			if (currentTab.equals(KeyConstants.TAB_PROGRAM_SEVICE))
				request.setAttribute(KeyConstants.TAB_PROGRAM_SEVICE,	KeyConstants.ACCESS_CURRENT);
		} else
			request.setAttribute(KeyConstants.TAB_PROGRAM_SEVICE,	KeyConstants.ACCESS_NULL);
		
//		Staff
		if (sec.GetAccess(KeyConstants.FUN_PROGRAM_STAFF, orgId.toString()).compareTo(KeyConstants.ACCESS_READ) >= 0) {
			request.setAttribute(KeyConstants.TAB_PROGRAM_STAFF,	KeyConstants.ACCESS_VIEW);
			if (currentTab.equals(KeyConstants.TAB_PROGRAM_STAFF))
				request.setAttribute(KeyConstants.TAB_PROGRAM_STAFF,	KeyConstants.ACCESS_CURRENT);
		} else
			request.setAttribute(KeyConstants.TAB_PROGRAM_STAFF,	KeyConstants.ACCESS_NULL);
		
	}
	
	protected void setEditScreenMode(HttpServletRequest request, String currentTab) {
		super.setMenu(request, KeyConstants.MENU_PROGRAM);
		SecurityManager sec = super.getSecurityManager(request);
		// general
		
		if (sec.GetAccess(KeyConstants.FUN_PROGRAM, null).compareTo(KeyConstants.ACCESS_READ) >= 0) {
			request.setAttribute(KeyConstants.TAB_PROGRAM_GENERAL,	KeyConstants.ACCESS_VIEW);
			if (currentTab.equals(KeyConstants.TAB_PROGRAM_GENERAL))
				request.setAttribute(KeyConstants.TAB_PROGRAM_GENERAL,	KeyConstants.ACCESS_CURRENT);
		}
		else request.setAttribute(KeyConstants.TAB_PROGRAM_GENERAL,	KeyConstants.ACCESS_NULL);			
		
		
		if (sec.GetAccess(KeyConstants.FUN_PROGRAMEDIT_SERVICERESTRICTIONS, null).compareTo(KeyConstants.ACCESS_READ) >= 0) {
			request.setAttribute(KeyConstants.TAB_PROGRAM_SEVICE,	KeyConstants.ACCESS_VIEW);
			if (currentTab.equals(KeyConstants.TAB_PROGRAM_SEVICE))
				request.setAttribute(KeyConstants.TAB_PROGRAM_SEVICE,	KeyConstants.ACCESS_CURRENT);
		} else
			request.setAttribute(KeyConstants.TAB_PROGRAM_SEVICE,	KeyConstants.ACCESS_NULL);
		
	}
	
	
	public boolean isReadOnly(HttpServletRequest request, String funName,Integer programId){
		boolean readOnly =false;
		
		SecurityManager sec = super.getSecurityManager(request);
		//summary
		String orgCd="";
		if(programId!=null ||programId.intValue()!=0) orgCd=programId.toString();
		if (sec.GetAccess(funName, orgCd).compareTo(KeyConstants.ACCESS_READ) <= 0) 
			readOnly=true;
		return readOnly;
	}


}
