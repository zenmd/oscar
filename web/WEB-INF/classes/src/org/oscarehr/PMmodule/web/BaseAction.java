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
//import org.oscarehr.PMmodule.service.AgencyManager;
import org.oscarehr.PMmodule.service.ProgramManager;
import org.oscarehr.PMmodule.service.ProgramQueueManager;
import org.oscarehr.PMmodule.service.ProviderManager;
import org.oscarehr.PMmodule.service.RatePageManager;
import org.oscarehr.PMmodule.service.RoomDemographicManager;
import org.oscarehr.PMmodule.service.RoomManager;
import org.oscarehr.PMmodule.utility.Utility;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import oscar.OscarProperties;

import com.quatro.common.KeyConstants;
import com.quatro.model.security.NoAccessException;
import com.quatro.service.security.SecurityManager;
import com.quatro.service.security.UserAccessManager;

public abstract class BaseAction extends DispatchAction {

	protected static final String PARAM_START = "?";

	protected static final String PARAM_EQUALS = "=";

	protected static final String PARAM_AND = "&";

	public void addError(HttpServletRequest req, String message) {
		ActionMessages msgs = getErrors(req);
		msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"errors.detail", message));
		addErrors(req, msgs);
	}

	public void addMessage(HttpServletRequest req, String message) {
		ActionMessages msgs = getMessages(req);
		msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"errors.detail", message));
		addMessages(req, msgs);
	}

	public void addMessage(HttpServletRequest req, String key, String val) {
		ActionMessages msgs = getMessages(req);
		msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(key, val));
		addMessages(req, msgs);
	}

	public ApplicationContext getAppContext() {
		return WebApplicationContextUtils.getWebApplicationContext(getServlet()
				.getServletContext());
	}
/*
	public AgencyManager getAgencyManager() {
		return (AgencyManager) getAppContext().getBean("agencyManager");
	}
*/
	public ProgramManager getProgramManager() {
		return (ProgramManager) getAppContext().getBean("programManager");
	}

	public UserAccessManager getUserAccessManager() {
		return (UserAccessManager) getAppContext().getBean("userAccessManager");
	}

	public ProgramQueueManager getProgramQueueManager() {
		return (ProgramQueueManager) getAppContext().getBean(
				"programQueueManager");
	}

	public RoomManager getRoomManager() {
		return (RoomManager) getAppContext().getBean("roomManager");
	}

	public RoomDemographicManager getRoomDemographicManager() {
		return (RoomDemographicManager) getAppContext().getBean(
				"roomDemographicManager");
	}

	public ProviderManager getProviderManager() {
		return (ProviderManager) getAppContext().getBean("providerManager");
	}

	public RatePageManager getRateManager() {
		return (RatePageManager) getAppContext().getBean("ratePageManager");
	}

	protected String getProviderNo(HttpServletRequest request) {
		return ((Provider) request.getSession().getAttribute("provider"))
				.getProviderNo();
	}

	protected Provider getProvider(HttpServletRequest request) {
		return ((Provider) request.getSession().getAttribute("provider"));
	}

	protected SecurityManager getSecurityManager(HttpServletRequest request)
	{
		return (SecurityManager) request.getSession()
		.getAttribute(KeyConstants.SESSION_KEY_SECURITY_MANAGER);
	}
	protected String getParameter(HttpServletRequest request,
			String parameterName) {
		return request.getParameter(parameterName);
	}

	protected Object getSessionAttribute(HttpServletRequest request,
			String attributeName) {
		Object attribute = request.getSession().getAttribute(attributeName);

		if (attribute != null) {
			request.getSession().removeAttribute(attributeName);
		}

		return attribute;
	}
	
	protected void setMenu(HttpServletRequest request, String currentMenu) throws NoAccessException {
		/*
		  isPageChangedFlag appeared?
		*/
		
		if (request.getAttribute("pageChanged") == null) {
			if(request.getParameter("pageChanged")!= null) request.setAttribute("pageChanged", request.getParameter("pageChanged"));
		}
		String lastMenu = (String) request.getSession().getAttribute("currMenu");
		if (lastMenu == null) {
			initMenu(request);
		}
		else
		{
			request.getSession().setAttribute(lastMenu, KeyConstants.ACCESS_VIEW);
		}
		// check home page access
		if(!currentMenu.equals(KeyConstants.MENU_HOME))
		{
			if (request.getSession().getAttribute(currentMenu).equals(KeyConstants.ACCESS_NULL))
			{
				throw new NoAccessException();
			}
		}
		String scrollPosition = (String) request.getParameter("scrollPosition");
		if(null != scrollPosition) {
			request.setAttribute("scrPos", scrollPosition);
		}
		else
		{
			request.setAttribute("scrPos", "0");
		}
	}

	private void initMenu(HttpServletRequest request)
	{
		SecurityManager sec = getSecurityManager(request);
		if (sec==null) return;		
		//Client Management
		if (sec.GetAccess(KeyConstants.FUN_CLIENT, "").compareTo(KeyConstants.ACCESS_READ) >= 0) {
			request.getSession().setAttribute(KeyConstants.MENU_CLIENT, KeyConstants.ACCESS_VIEW);
		} else
			request.getSession().setAttribute(KeyConstants.MENU_CLIENT, KeyConstants.ACCESS_NULL);
	
		//Program
		if (sec.GetAccess(KeyConstants.FUN_PROGRAM, "").compareTo(KeyConstants.ACCESS_READ) >= 0) {
			request.getSession().setAttribute(KeyConstants.MENU_PROGRAM, KeyConstants.ACCESS_VIEW);
		} else
			request.getSession().setAttribute(KeyConstants.MENU_PROGRAM, KeyConstants.ACCESS_NULL);

		//Facility Management
		if (sec.GetAccess(KeyConstants.FUN_FACILITY, "").compareTo(KeyConstants.ACCESS_READ) >= 0) {
			request.getSession().setAttribute(KeyConstants.MENU_FACILITY, KeyConstants.ACCESS_VIEW);
		} else
			request.getSession().setAttribute(KeyConstants.MENU_FACILITY, KeyConstants.ACCESS_NULL);

		//Report Runner
		if (sec.GetAccess(KeyConstants.FUN_REPORTS, "").compareTo(KeyConstants.ACCESS_READ) >= 0) {
			request.getSession().setAttribute(KeyConstants.MENU_REPORT, KeyConstants.ACCESS_VIEW);
		} else
			request.getSession().setAttribute(KeyConstants.MENU_REPORT, KeyConstants.ACCESS_NULL);

		//System Admin
		if (OscarProperties.getInstance().isAdminOptionOn() && sec.GetAccess("_admin", "").compareTo(KeyConstants.ACCESS_READ) >= 0) {
			request.getSession().setAttribute(KeyConstants.MENU_ADMIN, KeyConstants.ACCESS_VIEW);
		} else
			request.getSession().setAttribute(KeyConstants.MENU_ADMIN, KeyConstants.ACCESS_NULL);
		request.getSession().setAttribute(KeyConstants.MENU_HOME, KeyConstants.ACCESS_VIEW);
		request.getSession().setAttribute(KeyConstants.MENU_TASK, KeyConstants.ACCESS_VIEW);
	}
	
	protected ActionForward createRedirectForward(ActionMapping mapping,
			String forwardName, StringBuffer parameters) {
		ActionForward forward = mapping.findForward(forwardName);
		StringBuffer path = new StringBuffer(forward.getPath());
		path.append(parameters);

		return new RedirectingActionForward(path.toString());
	}
	protected String getClientId(HttpServletRequest request){
		String clientId=request.getParameter("demoNo");
		if(Utility.isNotNullOrEmptyStr(clientId)) clientId=request.getParameter("clientId");
		return clientId;
	}
}