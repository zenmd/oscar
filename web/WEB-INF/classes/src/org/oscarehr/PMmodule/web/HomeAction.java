/*******************************************************************************
 * Copyright (c) 2005, 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU General Public License
 * which accompanies this distribution, and is available at
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 * Contributors:
 *     <Quatro Group Software Systems inc.>  <OSCAR Team>
 *******************************************************************************/
package org.oscarehr.PMmodule.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import com.quatro.common.KeyConstants;
import com.quatro.model.security.NoAccessException;

public class HomeAction extends BaseAction {
    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	try {
    		super.setMenu(request,KeyConstants.MENU_HOME);
    		if ("yes".equals(oscar.OscarProperties.getInstance().getProperty("ldap_authentication")))
    		{
    			request.setAttribute("changePassword","N");
    		}
    		else
    		{
    			request.setAttribute("changePassword", "Y");
    		}
    		return mapping.findForward("home");
    	}
    	catch(NoAccessException e)
    	{
    		return mapping.findForward("failure");
    	}
	}
}
