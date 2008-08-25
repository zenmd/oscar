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

import com.quatro.common.KeyConstants;
import com.quatro.service.security.SecurityManager;

public abstract class BaseFacilityAction extends BaseAction {

	protected void setScreenMode(HttpServletRequest request, String currentTab) {
		super.setMenu(request, KeyConstants.MENU_FACILITY);
		SecurityManager sec = super.getSecurityManager(request);
		// general
		
		if (sec.GetAccess(KeyConstants.FUN_FACILITY, null).compareTo(KeyConstants.ACCESS_READ) >= 0) {
			request.setAttribute(KeyConstants.TAB_FACILITY_GENERAL,	KeyConstants.ACCESS_VIEW);
			if (currentTab.equals(KeyConstants.TAB_FACILITY_GENERAL))
				request.setAttribute(KeyConstants.TAB_FACILITY_GENERAL,	KeyConstants.ACCESS_CURRENT);
		}
		else request.setAttribute(KeyConstants.TAB_FACILITY_GENERAL,	KeyConstants.ACCESS_NULL);
		
		
		// program
		if (sec.GetAccess(KeyConstants.FUN_FACILITY_PROGRAM, null).compareTo(KeyConstants.ACCESS_READ) >= 0) {
			request.setAttribute(KeyConstants.TAB_FACILITY_PROGRAM,	KeyConstants.ACCESS_VIEW);
			if (currentTab.equals(KeyConstants.TAB_FACILITY_PROGRAM))
				request.setAttribute(KeyConstants.TAB_FACILITY_PROGRAM,	KeyConstants.ACCESS_CURRENT);
		} 
		else request.setAttribute(KeyConstants.TAB_FACILITY_PROGRAM,	KeyConstants.ACCESS_NULL);
		
		
		// message
		if (sec.GetAccess(KeyConstants.FUN_FACILITY_MESSAGE  , null).compareTo(KeyConstants.ACCESS_READ) >= 0) {
			request.setAttribute(KeyConstants.TAB_FACILITY_MESSAGE,	KeyConstants.ACCESS_VIEW);
			if (currentTab.equals(KeyConstants.TAB_FACILITY_MESSAGE))
				request.setAttribute(KeyConstants.TAB_FACILITY_MESSAGE,	KeyConstants.ACCESS_CURRENT);
		} 
		else request.setAttribute(KeyConstants.TAB_FACILITY_MESSAGE,KeyConstants.ACCESS_NULL);
		
		
		//	Edit
		if (sec.GetAccess(KeyConstants.FUN_FACILITY_EDIT, null).compareTo(KeyConstants.ACCESS_READ) >= 0) {
			request.setAttribute(KeyConstants.TAB_FACILITY_EDIT,KeyConstants.ACCESS_VIEW);
			if (currentTab.equals(KeyConstants.TAB_FACILITY_EDIT))
				request.setAttribute(KeyConstants.TAB_FACILITY_EDIT,KeyConstants.ACCESS_CURRENT);
		} 
		else
			request.setAttribute(KeyConstants.TAB_FACILITY_EDIT,KeyConstants.ACCESS_NULL);
		
		
		//	Bed
		if (sec.GetAccess(KeyConstants.FUN_FACILITY_BED, null).compareTo(KeyConstants.ACCESS_READ) >= 0) {
			request.setAttribute(KeyConstants.TAB_FACILITY_BED,	KeyConstants.ACCESS_VIEW);
			if (currentTab.equals(KeyConstants.TAB_FACILITY_BED))
				request.setAttribute(KeyConstants.TAB_FACILITY_BED,	KeyConstants.ACCESS_CURRENT);
		} else
			request.setAttribute(KeyConstants.TAB_FACILITY_BED,	KeyConstants.ACCESS_NULL);
		
	}
	public boolean isReadOnly(HttpServletRequest request, String funName,Integer facilityId){
		boolean readOnly =false;
		
		SecurityManager sec = super.getSecurityManager(request);
		//summary
		String orgCd="";
		if(facilityId!=null ||facilityId.intValue()!=0) orgCd=facilityId.toString();
		if (sec.GetAccess(funName, orgCd).compareTo(KeyConstants.ACCESS_READ) <= 0) 
			readOnly=true;
		return readOnly;
	}


}