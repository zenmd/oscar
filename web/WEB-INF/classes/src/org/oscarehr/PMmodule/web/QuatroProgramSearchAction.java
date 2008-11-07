package org.oscarehr.PMmodule.web;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;
import org.oscarehr.PMmodule.model.Demographic;
import org.oscarehr.PMmodule.model.Program;
import org.oscarehr.PMmodule.model.Provider;

import org.oscarehr.PMmodule.service.ClientManager;
import org.oscarehr.PMmodule.service.ProgramManager;

import com.quatro.common.KeyConstants;
import com.quatro.service.LookupManager;

public class QuatroProgramSearchAction  extends BaseAction {
	   
	   private ProgramManager programManager;
	   private ClientManager clientManager;
	   private LookupManager lookupManager;
	   
	   public void setLookupManager(LookupManager lookupManager) {
		this.lookupManager = lookupManager;
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	    		
	       return search_programs(mapping, form, request, response);
	   }	  
	    
	   public ActionForward search_programs(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	       DynaActionForm clientForm = (DynaActionForm) form;

	       Program criteria = (Program) clientForm.get("program");
	       List lstProgram=programManager.search(criteria);
	       request.setAttribute("programs", lstProgram);
	       String cId = request.getParameter("clientId");
	       //have save client ID to session for button search purpose
	       if(cId!=null) request.getSession(true).setAttribute(KeyConstants.SESSION_KEY_CLIENTID, cId);
	       else cId =(String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_CLIENTID);
	       HashMap actionParam = (HashMap) request.getAttribute("actionParam");
	       if(actionParam==null){
	    	  actionParam = new HashMap();
	          actionParam.put("clientId", cId); 
	       }
	      
	       request.setAttribute("actionParam", actionParam);
	       Demographic clientObj =clientManager.getClientByDemographicNo(cId);
	       request.setAttribute("clientId", cId);
	       request.setAttribute("formName", request.getParameter("formName"));
	       request.setAttribute("formElementId", request.getParameter("formElementId"));
	       request.setAttribute("gender", clientObj.getSex());
	       request.setAttribute("age", clientObj.getAge());
	       List lstFacility=this.lookupManager.LoadCodeList("FAC", false, null, null);
	       request.setAttribute("lstFacility", lstFacility);
	       ProgramUtils.addProgramRestrictions(request);
		   
	       request.setAttribute("notoken", "Y");

	       return mapping.findForward("view");
	   }
	   
	 
	   public void setClientManager(ClientManager clientManager) {
		 this.clientManager = clientManager;
	   }

	   public void setProgramManager(ProgramManager programManager) {
		 this.programManager = programManager;
	   }

	}