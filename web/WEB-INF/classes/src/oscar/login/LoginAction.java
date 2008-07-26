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
import java.util.List;
import java.util.Hashtable;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.annotation.XmlElementDecl.GLOBAL;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.oscarehr.PMmodule.dao.FacilityDAO;
import org.oscarehr.PMmodule.model.Facility;
import org.oscarehr.PMmodule.model.Provider;
import org.oscarehr.PMmodule.service.ProviderManager;
import org.oscarehr.util.SpringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import oscar.OscarProperties;
import oscar.log.LogAction;
import oscar.log.LogConst;
import oscar.oscarDB.DBHandler;
import oscar.oscarSecurity.CRHelper;

import com.quatro.model.LookupCodeValue;
import com.quatro.service.LookupManager;
import com.quatro.service.security.*;
import com.quatro.service.security.SecurityManager;
import com.quatro.common.KeyConstants;

public final class LoginAction extends DispatchAction {
    private static final Logger _logger = Logger.getLogger(LoginAction.class);
    private static final String LOG_PRE = "Login!@#$: ";

    private ProviderManager providerManager = (ProviderManager) SpringUtils.getBean("providerManager");
    private LookupManager lookupManager = (LookupManager) SpringUtils.getBean("lookupManager");

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ActionMessages messages = new ActionMessages();
        String ip = request.getRemoteAddr();
        String where = "shelterSelection";
        // String userName, password, pin, propName;
        String userName = ((LoginForm) form).getUsername();
        String password = ((LoginForm) form).getPassword();
        String pin = ((LoginForm) form).getPin();
        if (userName.equals("")) {
            messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.login.invalid"));
            saveMessages(request,messages);
            return mapping.getInputForward();
        }

        LoginCheckLogin cl = new LoginCheckLogin();
        if (cl.isBlock(userName)) {
            _logger.info(LOG_PRE + " Blocked: " + userName);
            // return mapping.findForward(where); //go to block page
            messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.login","Your account is locked. Please contact your administrator to unlock."));
            saveMessages(request,messages);
            return mapping.getInputForward();
        }
        String[] strAuth;
        ApplicationContext appContext = getAppContext();
        try {
            strAuth = cl.auth(userName, password, pin, ip, appContext);
        }
        catch (Exception e) {
            String newURL = mapping.findForward("error").getPath();
            messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.login", "Server is temporarily unavailable, please try again later"));
            saveMessages(request,messages);
            return mapping.getInputForward();
        }

        if (strAuth != null && strAuth.length != 1) { // login successfully
            // invalidate the existing sesson
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
                session = request.getSession(); // Create a new session for this user
            }

            _logger.info("Assigned new session for: " + strAuth[0] + " : " + strAuth[3] + " : " + strAuth[4]);
            LogAction.addLog(strAuth[0], LogConst.LOGIN, LogConst.CON_LOGIN, "", ip);

            // initial db setting
            Properties pvar = cl.getOscarVariable();
            session.setAttribute("oscarVariables", pvar);
            if (!DBHandler.isInit()) {
                DBHandler.init(pvar.getProperty("db_name"), pvar.getProperty("db_driver"), pvar.getProperty("db_uri"), pvar.getProperty("db_username"), pvar.getProperty("db_password"));
            }

            String providerNo = strAuth[0];


            session.setAttribute(KeyConstants.SESSION_KEY_PROVIDERNO, strAuth[0]);
            session.setAttribute(KeyConstants.SESSION_KEY_PROVIDERNAME, strAuth[2] + ", "+ strAuth[1]);

            session.setAttribute("userrole", strAuth[4]);
            session.setAttribute("oscar_context_path", request.getContextPath());
            session.setAttribute("expired_days", strAuth[5]);
            
            // initiate security manager
            UserAccessManager userAccessManager = (UserAccessManager) getAppContext().getBean("userAccessManager");
            
            SecurityManager secManager = userAccessManager.getUserUserSecurityManager(providerNo,lookupManager);
            session.setAttribute(KeyConstants.SESSION_KEY_SECURITY_MANAGER, secManager);
            /*
             * if (OscarProperties.getInstance().isTorontoRFQ()) { where = "caisiPMM"; }
             */
            CRHelper.recordLoginSuccess(userName, strAuth[0], request);

            // setup caisi stuff
            String username = (String) session.getAttribute("user");
            Provider provider = providerManager.getProvider(username);
            session.setAttribute("provider", provider);
            return mapping.findForward(where);
        }
        // expired password
        else if (strAuth != null && strAuth.length == 1 && strAuth[0].equals("expired")) {
           // cl.updateLoginList(ip, userName);
   	     	messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.login",	"Your account is expired. Please contact your administrator."));
        }
        else { // go to normal directory
            // request.setAttribute("login", "failed");
            LogAction.addLog(userName, "failed", LogConst.CON_LOGIN, "", ip);
            cl.updateLoginList(userName);
            CRHelper.recordLoginFailure(userName, request);
   	     	messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.login.invalid"));
        }
        saveMessages(request,messages);        
        return mapping.getInputForward();
    }
    
	public ApplicationContext getAppContext() {
		return WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
	}
}
