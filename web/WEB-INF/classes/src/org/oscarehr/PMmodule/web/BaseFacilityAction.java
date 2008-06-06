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

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.RedirectingActionForward;
import org.apache.struts.actions.DispatchAction;
import org.oscarehr.PMmodule.model.Provider;
import org.oscarehr.PMmodule.service.AdmissionManager;
import org.oscarehr.PMmodule.service.AgencyManager;
import org.oscarehr.PMmodule.service.BedDemographicManager;
import org.oscarehr.PMmodule.service.ClientManager;
import org.oscarehr.PMmodule.service.ProgramManager;
import org.oscarehr.PMmodule.service.ProgramQueueManager;
import org.oscarehr.PMmodule.service.ProviderManager;
import org.oscarehr.PMmodule.service.RatePageManager;
import org.oscarehr.PMmodule.service.RoomDemographicManager;
import org.oscarehr.PMmodule.service.RoomManager;
import org.oscarehr.casemgmt.service.CaseManagementManager;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.quatro.common.KeyConstants;
import com.quatro.service.security.*;
import com.quatro.service.security.SecurityManager;

public abstract class BaseFacilityAction extends BaseAction {

	protected void setScreenMode(HttpServletRequest request, String currentTab) {
		super.setMenu(request, KeyConstants.MENU_FACILITY);
		SecurityManager sec = super.getSecurityManager(request);
		// general
		if (sec.GetAccess("_pmm_facility.general", "").compareTo("r") >= 0) {
			request.setAttribute(KeyConstants.TAB_FACILITY_GENERAL,
					KeyConstants.ACCESS_VIEW);
			if (currentTab.equals(KeyConstants.TAB_FACILITY_GENERAL))
				request.setAttribute(KeyConstants.TAB_FACILITY_GENERAL,
						KeyConstants.ACCESS_CURRENT);
		} else{
			request.setAttribute(KeyConstants.TAB_FACILITY_GENERAL,
					KeyConstants.ACCESS_NULL);
		}
		
		// program
		if (sec.GetAccess("_pmm_facility.program", "").compareTo("r") >= 0) {
			request.setAttribute(KeyConstants.TAB_FACILITY_PROGRAM,
					KeyConstants.ACCESS_VIEW);
			if (currentTab.equals(KeyConstants.TAB_FACILITY_PROGRAM))
				request.setAttribute(KeyConstants.TAB_FACILITY_PROGRAM,
						KeyConstants.ACCESS_CURRENT);
		} else{
			request.setAttribute(KeyConstants.TAB_FACILITY_PROGRAM,
					KeyConstants.ACCESS_NULL);
		}
		
		// message
		if (sec.GetAccess("_pmm_facility.message", "").compareTo("r") >= 0) {
			request.setAttribute(KeyConstants.TAB_FACILITY_MESSAGE,
					KeyConstants.ACCESS_VIEW);
			if (currentTab.equals(KeyConstants.TAB_FACILITY_MESSAGE))
				request.setAttribute(KeyConstants.TAB_FACILITY_MESSAGE,
						KeyConstants.ACCESS_CURRENT);
		} else{
			request.setAttribute(KeyConstants.TAB_FACILITY_MESSAGE,
					KeyConstants.ACCESS_NULL);
		}
		
		//	Edit
		if (sec.GetAccess("_pmm_facility.edit", "").compareTo("r") >= 0) {
			request.setAttribute(KeyConstants.TAB_FACILITY_EDIT,
					KeyConstants.ACCESS_VIEW);
			if (currentTab.equals(KeyConstants.TAB_FACILITY_EDIT))
				request.setAttribute(KeyConstants.TAB_FACILITY_EDIT,
						KeyConstants.ACCESS_CURRENT);
		} else{
			request.setAttribute(KeyConstants.TAB_FACILITY_EDIT,
					KeyConstants.ACCESS_NULL);
		}
		
		//	Bed
		if (sec.GetAccess("_pmm_facility.bed", "").compareTo("r") >= 0) {
			request.setAttribute(KeyConstants.TAB_FACILITY_BED,
					KeyConstants.ACCESS_VIEW);
			if (currentTab.equals(KeyConstants.TAB_FACILITY_BED))
				request.setAttribute(KeyConstants.TAB_FACILITY_BED,
						KeyConstants.ACCESS_CURRENT);
		} else{
			request.setAttribute(KeyConstants.TAB_FACILITY_BED,
					KeyConstants.ACCESS_NULL);
		}
	}

}