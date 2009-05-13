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
package com.quatro.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Calendar;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.oscarehr.PMmodule.model.Admission;
import org.oscarehr.PMmodule.model.ConsentDetail;
import org.oscarehr.PMmodule.model.QuatroIntake;
import com.quatro.service.IntakeManager;
import org.oscarehr.PMmodule.service.ConsentManager;
import org.oscarehr.PMmodule.web.BaseClientAction;

import com.quatro.service.TopazManager;

import com.quatro.common.KeyConstants;
import com.quatro.model.security.NoAccessException;
import com.quatro.model.signaturePad.TopazValue;

public class TopazSaveAction extends BaseClientAction {
	
    private TopazManager topazManager=null;
    private ConsentManager consentManager=null;
    private IntakeManager intakeManager=null;

    public void setTopazManager(TopazManager topazManager) {
        this.topazManager = topazManager;
    }

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	request.setAttribute("notoken", "Y");
    	return save(mapping, form, request, response);
    }
    
    private ActionForward save(ActionMapping mapping, ActionForm form, 
   		HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
       ObjectInputStream ois = new ObjectInputStream(request.getInputStream());       
       String msg = "";
       try {
    	   TopazValue tobj = (TopazValue)ois.readObject();
    	   ois.close();
    	   if ("consent".equals(tobj.getModuleName()))
    	   {
    		   ConsentDetail consent = consentManager.getConsentDetail(tobj.getRecordId());
			   if (consent == null) throw new NoAccessException();
			   super.getAccess(request, KeyConstants.FUN_CLIENTCONSENT, consent.getProgramId(),KeyConstants.ACCESS_UPDATE);
		   }
		   else if ("admission".equals(tobj.getModuleName())) {
			   QuatroIntake intake = intakeManager.getQuatroIntake(tobj.getRecordId());
			   if (intake == null) throw new NoAccessException();
			   super.getAccess(request, KeyConstants.FUN_CLIENTADMISSION, intake.getProgramId(),KeyConstants.ACCESS_UPDATE);
		   }
		   else
			   throw new NoAccessException();
	       String providerNo = (String) request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
	       tobj.setProviderNo(providerNo);
	       tobj.setLastUpdateDate(Calendar.getInstance());	
    	   topazManager.saveTopazValue(tobj);
		   msg = "confirmed:Saved Successfully";
       }
       catch(Exception e){ 
    	   msg="failed: Error in saving the signature: " + e.getMessage();
       }

       PrintWriter out = new PrintWriter(response.getOutputStream());
       response.setContentType("text/plain");
	   out.println(msg);
	   out.flush();
	   out.close(); 

	   return null;
    }

	public void setIntakeManager(IntakeManager intakeManager) {
		this.intakeManager = intakeManager;
	}
	public IntakeManager getIntakeManager() {
		return this.intakeManager;
	}

	public void setConsentManager(ConsentManager consentManager) {
		this.consentManager = consentManager;
	}

}
