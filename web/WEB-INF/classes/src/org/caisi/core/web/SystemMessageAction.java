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

package org.caisi.core.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;
import org.caisi.model.SystemMessage;
import org.caisi.service.SystemMessageManager;

import com.quatro.common.KeyConstants;
import com.quatro.service.LookupManager;
import com.quatro.service.security.SecurityManager;

public class SystemMessageAction extends DispatchAction {

	private static Logger log = LogManager.getLogger(SystemMessageAction.class);
	
	protected SystemMessageManager mgr = null;
	private LookupManager lookupManager;
	
	public void setSystemMessageManager(SystemMessageManager mgr) {
		this.mgr = mgr;
	}
	
	public ActionForward unspecified(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		return list(mapping,form,request,response);
	}
		
	public ActionForward list(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response) {
//		List activeMessages = mgr.getMessages();
		List activeMessages = mgr.getActiveMessages();
		request.setAttribute("ActiveMessages",activeMessages);
		return mapping.findForward("list");
	}
	
	public ActionForward edit(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm systemMessageForm = (DynaActionForm)form;
		String messageId = request.getParameter("id");
		if(messageId == null)
			messageId = (String)request.getAttribute("systemMsgId");
		
		if(messageId != null) {
			SystemMessage msg = mgr.getMessage(messageId);
			
			if(msg == null) {
				ActionMessages webMessage = new ActionMessages();
				webMessage.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("system_message.missing"));
				saveErrors(request,webMessage);
				return list(mapping,form,request,response);
			}
			systemMessageForm.set("system_message",msg);
		}
		
		List msgTypepList = lookupManager.LoadCodeList("MTP", true, null, null);
        request.setAttribute("msgTypepList", msgTypepList);
        boolean isReadOnly =false;		
		SecurityManager sec = (SecurityManager) request.getSession()
		.getAttribute(KeyConstants.SESSION_KEY_SECURITY_MANAGER);	
		if (sec.GetAccess(KeyConstants.FUN_ADMIN_SYSTEMMESSAGE, null).compareTo(KeyConstants.ACCESS_READ) <= 0) 
			isReadOnly=true;
		if(isReadOnly) request.setAttribute("isReadOnly", Boolean.valueOf(isReadOnly));
		return mapping.findForward("edit");
	}

	public ActionForward save(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DynaActionForm userForm = (DynaActionForm)form;
		SystemMessage msg = (SystemMessage)userForm.get("system_message");
		msg.setCreation_date(new Date());
	
        try{
        	mgr.saveSystemMessage(msg);
			ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.save.success", request.getContextPath()));
            saveMessages(request, messages);
		}catch(Exception e){
	        ActionMessages messages = new ActionMessages();
	        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.save.failed", request.getContextPath()));
	        saveMessages(request,messages);
		}
		request.setAttribute("systemMsgId", msg.getId().toString());

        return edit(mapping, form, request, response);
	}
	public ActionForward view(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		List messages = mgr.getActiveMessages();
		if(messages.size()>0) {
			request.setAttribute("messages",messages);
		}
		return mapping.findForward("view");
	}

	public void setLookupManager(LookupManager lookupManager) {
		this.lookupManager = lookupManager;
	}
}
