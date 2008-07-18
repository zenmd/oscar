package com.quatro.web;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Date;

import javax.servlet.ServletInputStream;

import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.quatro.service.TopazManager;
import org.oscarehr.PMmodule.service.AdmissionManager;
import org.oscarehr.PMmodule.model.Admission;

import com.quatro.model.TopazValue;

public class TopazSaveAction extends DispatchAction {
	
    private TopazManager topazManager=null;

    public void setTopazManager(TopazManager topazManager) {
        this.topazManager = topazManager;
    }

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return save(mapping, form, request, response);
    }
    
    public ActionForward save(ActionMapping mapping, ActionForm form, 
   		HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
//	   System.out.println("in topazSaveServlet service");
       ObjectInputStream ois = new ObjectInputStream(request.getInputStream());       
       try{
    	   TopazValue tobj = (TopazValue)ois.readObject();
    	
// 	    System.out.println("size is :" + tobj.getSignature().length);

 	       PrintWriter out = new PrintWriter(response.getOutputStream());
 	       response.setContentType("text/plain");
		   out.println("confirmed");
		   out.flush();
		   out.close(); 

		   String providerNo = (String)request.getSession().getAttribute("user");
//		   tobj.setProviderNo(providerNo);
		
		   topazManager.saveTopazValue(tobj);
//		   request.getSession().setAttribute("signatureID", tobj.getRecordId());
	
//		   TopazValue tv=topazManager.getTopazValue(providerNo);
		   
//        File file = new File("c:\\sigTopaz.jpg");
//    	FileOutputStream fos= new FileOutputStream(file);
//    	fos.write(tv.getSignature());
//        fos.close();
		
		
       }catch(Exception e){ 
    	 System.out.println(" Error in topazSaveServlet: " + e.getMessage());
       }

	   return null;
    }

    public ActionForward view(ActionMapping mapping, ActionForm form, 
       		HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
//	   String providerNo = (String)request.getSession().getAttribute("user");
//     TopazValue tv= topazManager.getTopazValue(providerNo);

	   return mapping.findForward("view");
    }

}
