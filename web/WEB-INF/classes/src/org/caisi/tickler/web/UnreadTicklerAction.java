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


package org.caisi.tickler.web;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.validator.LazyValidatorForm;
import org.caisi.model.CustomFilter;
import org.caisi.service.TicklerManager;
import org.oscarehr.util.SessionConstants;

import oscar.login.LoginCheckLogin;

public class UnreadTicklerAction extends DispatchAction {

	private TicklerManager ticklerMgr = null;
	
	public void setTicklerManager(TicklerManager ticklerManager) {
		this.ticklerMgr = ticklerManager;
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String providerNo = (String) request.getSession().getAttribute("user");
		if(providerNo == null) {
			return mapping.findForward("login");
		}
		return refresh(mapping,form,request,response);
	}

	public ActionForward refresh(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String providerNo = (String) request.getSession().getAttribute("user");
		if(providerNo == null) {
			return mapping.findForward("login");
		}
		
		int oldNum = -1;
		if(request.getSession().getAttribute("num_ticklers") != null) {
			oldNum = ((Integer)request.getSession().getAttribute("num_ticklers")).intValue();
		}
        CustomFilter filter = new CustomFilter();
        filter.setAssignee(providerNo);
        Integer currentFacilityId=(Integer)request.getSession().getAttribute(SessionConstants.CURRENT_FACILITY_ID);        
        String providerId = (String)request.getSession().getAttribute("user");
        String programId = "";
        Collection coll = ticklerMgr.getTicklers(filter, currentFacilityId, providerId, programId);
        if(oldNum != -1 && (coll.size() > oldNum)) {
        	request.setAttribute("difference",new Integer(coll.size() - oldNum));
        }
        request.setAttribute("ticklers",coll);
        request.getSession().setAttribute("num_ticklers",new Integer(coll.size()));    
		return mapping.findForward("view");
	}
	
}
