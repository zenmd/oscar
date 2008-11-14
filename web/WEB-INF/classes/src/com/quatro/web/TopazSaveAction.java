package com.quatro.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.oscarehr.PMmodule.model.Admission;
import org.oscarehr.PMmodule.model.ConsentDetail;
import org.oscarehr.PMmodule.service.AdmissionManager;
import org.oscarehr.PMmodule.service.ConsentManager;
import org.oscarehr.PMmodule.web.BaseClientAction;

import com.quatro.service.TopazManager;

import com.quatro.common.KeyConstants;
import com.quatro.model.security.NoAccessException;
import com.quatro.model.signaturePad.TopazValue;

public class TopazSaveAction extends BaseClientAction {
	
    private TopazManager topazManager=null;
    private ConsentManager consentManager=null;
    private AdmissionManager admissionManager=null;

    public void setTopazManager(TopazManager topazManager) {
        this.topazManager = topazManager;
    }

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return save(mapping, form, request, response);
    }
    
    public ActionForward save(ActionMapping mapping, ActionForm form, 
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
			   super.getAccess(request, KeyConstants.FUN_CLIENTCONSENT, consent.getProgramId());
		   }
		   else if ("admission".equals(tobj.getModuleName())) {
			   Admission adm = admissionManager.getAdmission(tobj.getRecordId());
			   if (adm == null) throw new NoAccessException();
			   super.getAccess(request, KeyConstants.FUN_CLIENTCONSENT, adm.getBedProgramId());
		   }
		   else
			   throw new NoAccessException();

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

    public ActionForward view(ActionMapping mapping, ActionForm form, 
       		HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
//	   String providerNo = (String)request.getSession().getAttribute("user");
//     TopazValue tv= topazManager.getTopazValue(providerNo);

	   return mapping.findForward("view");
    }

	public void setAdmissionManager(AdmissionManager admissionManager) {
		this.admissionManager = admissionManager;
	}

	public void setConsentManager(ConsentManager consentManager) {
		this.consentManager = consentManager;
	}

}
