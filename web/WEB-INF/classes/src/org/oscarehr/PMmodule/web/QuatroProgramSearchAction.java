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
import org.oscarehr.PMmodule.model.Program;
import org.oscarehr.PMmodule.model.Provider;

import org.oscarehr.PMmodule.service.ClientManager;
import org.oscarehr.PMmodule.service.ProgramManager;

import org.oscarehr.util.SessionConstants;

import com.quatro.common.KeyConstants;
import com.quatro.service.LookupManager;

public class QuatroProgramSearchAction  extends DispatchAction {
	   
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
	       
	       request.setAttribute("programs", programManager.search(criteria));
	       String cId = (String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_CLIENTID);
	       HashMap actionParam = (HashMap) request.getAttribute("actionParam");
	       if(actionParam==null){
	    	  actionParam = new HashMap();
	          actionParam.put("clientId", cId); 
	       }
	       request.setAttribute("actionParam", actionParam);
	       String demographicNo= (String)actionParam.get("clientId");
	       request.setAttribute("clientId", demographicNo);
	       List lstFacility=this.lookupManager.LoadCodeList("FAC", true, null, null);
	       request.setAttribute("lstFacility", lstFacility);
	       ProgramUtils.addProgramRestrictions(request);

	       return mapping.findForward("view");
	   }
	   
	 
	   public void setClientManager(ClientManager clientManager) {
		 this.clientManager = clientManager;
	   }

	   public void setProgramManager(ProgramManager programManager) {
		 this.programManager = programManager;
	   }

	}