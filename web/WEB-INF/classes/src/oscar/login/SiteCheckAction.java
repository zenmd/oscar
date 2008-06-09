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
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.quatro.model.LookupCodeValue;
import com.quatro.model.security.*;

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
import oscar.util.AlertTimer;

import com.quatro.service.LookupManager;
import com.quatro.service.security.*;
import com.quatro.service.security.SecurityManager;
import com.quatro.common.KeyConstants;

public final class SiteCheckAction extends DispatchAction {
    private static final Logger _logger = Logger.getLogger(LoginAction.class);
    private static final String LOG_PRE = "Login!@#$: ";

    private ProviderManager providerManager = (ProviderManager) SpringUtils.getBean("providerManager");
    private LookupManager lookupManager = (LookupManager) SpringUtils.getBean("lookupManager");

    private SecSiteManager secSiteManager;
    
    public void setSecSiteManager(SecSiteManager  secSiteManager)
    {
    	this.secSiteManager = secSiteManager;
    }

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectInputStream ois = new ObjectInputStream(request.getInputStream());       
	    SecSiteValue ssv;
	    try {
	    	ssv = (SecSiteValue)ois.readObject();
 	    }
 	    catch(Exception ex)
 	    {
 	    	sendMessage(response,"error");
 	    	return null;
 	    }
	    ois.close();
 	    if(ssv.getUserName() == null || "".equals(ssv.getUserName())) {
 	    	try{
     	    	int newKey = 0;
	     	    if (secSiteManager.isKeyValid(ssv.getSiteId(),ssv.getSiteKey()))
	     	    {
	     	    	newKey = secSiteManager.generateNewKey();
		     	    sendMessage(response,"confirmed:" + newKey);
	     	    }
	     	    else
	     	    {
		     	    sendMessage(response,"error:" + "Your computer is not autorized to access QuatroShelter, please contact system administrator");
	     	    }
 	    	}catch(Exception e){ 
 	    		sendMessage(response,"error");
 	    	}
	    	return null;
 	    }
 	    else
 	    {
 	    	secSiteManager.setSiteKey(ssv.getSiteId(), ssv.getSiteKey()); 
 	    	sendMessage(response,login(request,mapping, ssv));
 	    	return null;
 	    }
    }
    
    private void sendMessage(HttpServletResponse response, String message) throws IOException
    {
	        response.setContentType("text/plain");
		    PrintWriter out = new PrintWriter(response.getOutputStream());
  	        out.println(message);
 		    out.flush();
 		    out.close();
    }
    
    private String login(HttpServletRequest request,ActionMapping mapping, SecSiteValue ssv)
    {
            String ip = request.getRemoteAddr();

            String where = "failure";
            // String userName, password, pin, propName;
            String userName = ssv.getUserName();
            String password = ssv.getPassword();
            String pin = ssv.getPin();
            if (userName.equals("")) {
                return "error:Login failed, please try again";
            }

            LoginCheckLogin cl = new LoginCheckLogin();
            if (cl.isBlock(ip, userName)) {
                _logger.info(LOG_PRE + " Blocked: " + userName);
                return("error:Your account is locked. Please contact your administrator to unlock.");
            }

            String[] strAuth;
            ApplicationContext appContext = getAppContext();
            try {
                strAuth = cl.auth(userName, password, pin, ip, appContext);
            }
            catch (Exception e) {
                return("error:Server is temporarily unavailable");
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

                // get View Type
                String viewType = LoginViewTypeHlp.getInstance().getProperty(strAuth[3].toLowerCase());
                String providerNo = strAuth[0];

//                session.setAttribute("user", strAuth[0]);
//                session.setAttribute("userfirstname", strAuth[1]);
//                session.setAttribute("userlastname", strAuth[2]);

                session.setAttribute(KeyConstants.SESSION_KEY_PROVIDERNO, strAuth[0]);
                session.setAttribute(KeyConstants.SESSION_KEY_PROVIDERNAME, strAuth[2] + ", "+ strAuth[1]);
                
                session.setAttribute("userprofession", viewType);
                session.setAttribute("userrole", strAuth[4]);
                session.setAttribute("oscar_context_path", request.getContextPath());
                session.setAttribute("expired_days", strAuth[5]);
                
                // initiate security manager
                UserAccessManager userAccessManager = (UserAccessManager) getAppContext().getBean("userAccessManager");
                SecurityManager secManager = userAccessManager.getUserUserSecurityManager(providerNo);
                session.setAttribute(KeyConstants.SESSION_KEY_SECURITY_MANAGER, secManager);
                
                String default_pmm = null;
                if (viewType.equalsIgnoreCase("receptionist") || viewType.equalsIgnoreCase("doctor")) {
                    // get preferences from preference table
                    String[] strPreferAuth = cl.getPreferences();
                    session.setAttribute("starthour", strPreferAuth[0]);
                    session.setAttribute("endhour", strPreferAuth[1]);
                    session.setAttribute("everymin", strPreferAuth[2]);
                    session.setAttribute("groupno", strPreferAuth[3]);
                    if (org.oscarehr.common.IsPropertiesOn.isCaisiEnable()) {
                        session.setAttribute("newticklerwarningwindow", strPreferAuth[4]);
                        session.setAttribute("default_pmm", strPreferAuth[5]);
                        default_pmm = strPreferAuth[5];
                    }
                }

                if (viewType.equalsIgnoreCase("receptionist")) { // go to receptionist view
                    // where =
                    // "receptionist";//receptionistcontrol.jsp?year="+nowYear+"&month="+(nowMonth)+"&day="+(nowDay)+"&view=0&displaymode=day&dboperation=searchappointmentday";
                    where = "provider";
                }
                else if (viewType.equalsIgnoreCase("doctor")) { // go to provider view
                    where = "provider"; // providercontrol.jsp?year="+nowYear+"&month="+(nowMonth)+"&day="+(nowDay)+"&view=0&displaymode=day&dboperation=searchappointmentday";
                }
                else if (viewType.equalsIgnoreCase("admin")) { // go to admin view
                    where = "admin";
                }

                if (where.equals("provider") && default_pmm != null && "enabled".equals(default_pmm)) {
                    where = "caisiPMM";
                }
                /*
                 * if (OscarProperties.getInstance().isTorontoRFQ()) { where = "caisiPMM"; }
                 */
                // Lazy Loads AlertTimer instance only once, will run as daemon for duration of server runtime
                if (pvar.getProperty("billregion").equals("BC")) {
                    String alertFreq = pvar.getProperty("ALERT_POLL_FREQUENCY");
                    if (alertFreq != null) {
                        Long longFreq = new Long(alertFreq);
                        String[] alertCodes = OscarProperties.getInstance().getProperty("CDM_ALERTS").split(",");
                        AlertTimer.getInstance(alertCodes, longFreq.longValue());
                    }
                }
                CRHelper.recordLoginSuccess(userName, strAuth[0], request);

                // setup caisi stuff
                String username = (String) session.getAttribute("user");
                Provider provider = providerManager.getProvider(username);
                session.setAttribute("provider", provider);

                List shelterIds = providerManager.getShelterIds(provider.getProviderNo());
                if (shelterIds.size() > 1) {
                    return("confirmed:/QuatroShelter" + mapping.findForward("facilitySelection").getPath());
                }
                else if (shelterIds.size() == 1) {
                    Integer shelterId = (Integer) shelterIds.get(0);
                    LookupCodeValue shelter=lookupManager.GetLookupCode("SHL",String.valueOf(shelterId));
                    request.getSession().setAttribute(KeyConstants.SESSION_KEY_SHELTERID , shelterId);
                    request.getSession().setAttribute(KeyConstants.SESSION_KEY_SHELTER, shelter);
                    LogAction.addLog(strAuth[0], LogConst.LOGIN, LogConst.CON_LOGIN, "shelterId="+shelterId, ip);
                }
                else {
                    request.getSession().setAttribute(KeyConstants.SESSION_KEY_SHELTERID, new Integer(0));
                    request.getSession().setAttribute(KeyConstants.SESSION_KEY_SHELTER, new LookupCodeValue());
                }
            }
            // expired password
            else if (strAuth != null && strAuth.length == 1 && strAuth[0].equals("expired")) {
                cl.updateLoginList(ip, userName);
                return("error:Your account is expired. Please contact your administrator.");
            }
            else { // go to normal directory
                cl.updateLoginList(ip, userName);
                CRHelper.recordLoginFailure(userName, request);
                return "error:Login failed, please try again";
            }

//            return mapping.findForward(where);
            return("confirmed:" + mapping.findForward(where).getPath());
        }
    
	public ApplicationContext getAppContext() {
		return WebApplicationContextUtils.getWebApplicationContext(getServlet().getServletContext());
	}
}
