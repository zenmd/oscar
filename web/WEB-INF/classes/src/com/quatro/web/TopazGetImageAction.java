package com.quatro.web;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Date;

import javax.servlet.ServletOutputStream; 
import javax.servlet.ServletInputStream;

import org.apache.struts.action.DynaActionForm;

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

public class TopazGetImageAction extends BaseClientAction{
	
    private TopazManager topazManager=null;
    private ConsentManager consentManager=null;
    private AdmissionManager admissionManager=null;

    public void setTopazManager(TopazManager topazManager) {
        this.topazManager = topazManager;
    }

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return image(mapping, form, request, response);
    }

    public ActionForward image(ActionMapping mapping, ActionForm form, 
       		HttpServletRequest request, HttpServletResponse response) throws Exception {
       
	   Integer recordId = Integer.valueOf((String)request.getParameter("rid"));
	   String mCd=request.getParameter("moduleName");
	   
	   if (mCd.equals("consent")) {
		   ConsentDetail consent = consentManager.getConsentDetail(recordId);
		   if (consent == null) throw new NoAccessException();
		   super.getAccess(request, KeyConstants.FUN_CLIENTCONSENT, consent.getProgramId());
	   }
	   else if (mCd.equals("admission")) {
		   Admission adm = admissionManager.getAdmission(recordId);
		   if (adm == null) throw new NoAccessException();
		   super.getAccess(request, KeyConstants.FUN_CLIENTCONSENT, adm.getBedProgramId());
	   }
	   else
		   throw new NoAccessException();
	   
	   TopazValue tv= topazManager.getTopazValue(recordId,mCd);
       if(tv!=null){
	     response.setContentType("image/gif");
	     OutputStream o = response.getOutputStream();
	     o.write(tv.getSignature());
	     o.flush(); 
	     o.close();
       }else{
         response.getOutputStream().print("<font color='#ff0000'>No signature found.</font>");    	   
       }
	   return null;
    }

	public void setAdmissionManager(AdmissionManager admissionManager) {
		this.admissionManager = admissionManager;
	}

	public void setConsentManager(ConsentManager consentManager) {
		this.consentManager = consentManager;
	}    
}
