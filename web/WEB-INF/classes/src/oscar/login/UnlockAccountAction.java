/*
 * Copyright (c) 2005. Department of Family Medicine, McMaster University. All Rights Reserved. *
 * This software is published under the GPL GNU General Public License. This program is free
 * software; you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either version 2 of the License, or (at
 * your option) any later version. * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details. * * You should have
 * received a copy of the GNU General Public License along with this program; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA. * <OSCAR
 * TEAM> This software was written for the Department of Family Medicine McMaster University
 * Hamilton Ontario, Canada
 */
package oscar.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.oscarehr.PMmodule.service.ProviderManager;
import org.oscarehr.util.SpringUtils;

import com.quatro.common.KeyConstants;
import com.quatro.service.LookupManager;
import java.util.*;
import oscar.log.LogAction;

public final class UnlockAccountAction extends DispatchAction {
    private static final Logger _logger = Logger.getLogger(LoginAction.class);
    private static final String LOG_PRE = "Login!@#$: ";

    private ProviderManager providerManager = (ProviderManager) SpringUtils.getBean("providerManager");
    private LookupManager lookupManager = (LookupManager) SpringUtils.getBean("lookupManager");

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	String providerNo = (String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
    
    	String ip = request.getRemoteAddr();
    	  String msg = "Unlock";
    	  //LoginList llist = null;
    	  LoginCheckLogin cl = new LoginCheckLogin();
    	  Vector vec = cl.findLockList();
    	  if(vec == null) vec = new Vector();
    	  
    	  if (request.getParameter("submit") != null && request.getParameter("submit").equals("Unlock")) {
    	    // unlock
    	    if(request.getParameter("userName") != null && request.getParameter("userName").length()>0) {
    	      String userName = request.getParameter("userName");
    	      vec.remove(userName);
    	      cl.unlock(userName);
    		  LogAction.addLog(userName,providerNo, "unlock", "adminUnlock", userName, ip);
    	      msg = "The login account " + userName + " was unlocked.";
    	    }
    	  }
    	  return mapping.getInputForward();
    }
}