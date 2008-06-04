package org.oscarehr.PMmodule.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.DynaActionForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;
import org.oscarehr.PMmodule.exception.ClientAlreadyRestrictedException;

import org.oscarehr.PMmodule.model.ClientReferral;
import org.oscarehr.PMmodule.model.Program;
import org.oscarehr.PMmodule.model.ProgramClientRestriction;

import org.oscarehr.PMmodule.model.Provider;

import org.oscarehr.PMmodule.service.AdmissionManager;
import org.oscarehr.PMmodule.service.BedDemographicManager;
import org.oscarehr.PMmodule.service.BedManager;
import org.oscarehr.PMmodule.service.ClientManager;

import org.oscarehr.PMmodule.service.ProgramManager;
import org.oscarehr.PMmodule.service.ProviderManager;
import org.oscarehr.PMmodule.service.RoomDemographicManager;
import org.oscarehr.PMmodule.service.RoomManager;
import org.oscarehr.PMmodule.service.ClientRestrictionManager;

import org.oscarehr.casemgmt.service.CaseManagementManager;
import org.oscarehr.util.SessionConstants;

import oscar.MyDateFormat;

import com.quatro.common.KeyConstants;
import com.quatro.service.LookupManager;
import com.quatro.service.security.SecurityManager;
import com.quatro.util.Utility;



public class ServiceRestrictionAction  extends BaseClientAction {
   private ClientManager clientManager;
   private ProviderManager providerManager;
   private ProgramManager programManager;
   private ClientRestrictionManager clientRestrictionManager;
   private LookupManager lookupManager;


   public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	   
       return list(mapping, form, request, response);
   }
   public ActionForward terminate_early(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	   DynaActionForm clientForm = (DynaActionForm) form;
       ProgramClientRestriction restriction = (ProgramClientRestriction) clientForm.get("serviceRestriction");
	   String recId=request.getParameter("rId");
	   if(Utility.IsEmpty(recId)) recId=restriction.getId().toString();
       Integer rId=Integer.valueOf(recId);
	   String providerNo=(String)request.getSession().getAttribute("user");
       clientRestrictionManager.terminateEarly(rId,providerNo);
       HashMap actionParam = (HashMap) request.getAttribute("actionParam");
       if(actionParam==null){
    	  actionParam = new HashMap();
          actionParam.put("clientId", request.getParameter("clientId")); 
       }
       request.setAttribute("actionParam", actionParam);
       String demographicNo= (String)actionParam.get("clientId");
       request.setAttribute("clientId", demographicNo);
       request.setAttribute("client", clientManager.getClientByDemographicNo(demographicNo));
       
       return edit(mapping, form, request, response);
   }
   public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       setEditAttributes(form, request);
       super.setScreenMode(request, KeyConstants.TAB_CLIENT_RESTRICTION);
       return mapping.findForward("detail");
   }
   public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	   setListAttributes(form, request);
	   super.setScreenMode(request, KeyConstants.TAB_CLIENT_RESTRICTION);
       return mapping.findForward("list");
   }
   public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	   
	   ActionMessages messages = new ActionMessages();
	   DynaActionForm clientForm = (DynaActionForm) form;
       ProgramClientRestriction restriction = (ProgramClientRestriction) clientForm.get("serviceRestriction");
       ProgramClientRestriction servObj=(ProgramClientRestriction)request.getAttribute("serviceObj");

       Integer days = (Integer) clientForm.get("serviceRestrictionLength");       
       super.setScreenMode(request, KeyConstants.TAB_CLIENT_RESTRICTION);
       Program p = (Program) clientForm.get("program");     
       String providerNo=(String)request.getSession().getAttribute("user");
       restriction.setProviderNo(providerNo);       
       restriction.setEnabled(true);
       boolean success;
       if (restriction.getProgramId() == null
				|| restriction.getProgramId().intValue() <= 0) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.referral.program", request.getContextPath()));		
			saveMessages(request, messages);	
			setEditAttributes(form, request);		   
		   return mapping.findForward("detail");  
		}
       try {
    	   String sDt = restriction.getStartDateStr();
    	   restriction.setStartDate(MyDateFormat.getCalendar(sDt));    	 
    	   Calendar cal2 =MyDateFormat.getCalendar(sDt);
    	   cal2.add(Calendar.DAY_OF_MONTH, days.intValue());
    	   restriction.setEndDate(cal2);
           clientRestrictionManager.saveClientRestriction(restriction);
           success = true;
       }
       catch (ClientAlreadyRestrictedException e) {         
           messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("restrict.already_restricted"));
           saveMessages(request, messages);
           success = false;
       }
       catch(Exception e){
    	   messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("restrict.exceeds_maximum_days","1","180"));
           saveMessages(request, messages);
           success = false;
       }

       if (success) {           
           messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.save.success"));
           saveMessages(request, messages);
       }
      // clientForm.set("program", new Program());
       clientForm.set("serviceRestriction", restriction);
       //clientForm.set("serviceRestrictionLength", null);       
       setEditAttributes(form, request);
       //logManager.log("write", "service_restriction", id, request);

       return mapping.findForward("detail");       
   }
 public ActionForward print(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	   
	   ActionMessages messages = new ActionMessages();
	   DynaActionForm clientForm = (DynaActionForm) form;
	   return mapping.findForward("detail"); 
 }
   private void setEditAttributes(ActionForm form, HttpServletRequest request) {
       DynaActionForm clientForm = (DynaActionForm) form;

       HashMap actionParam = (HashMap) request.getAttribute("actionParam");
       if(actionParam==null){
    	  actionParam = new HashMap();
          actionParam.put("clientId", request.getParameter("clientId")); 
       }
       request.setAttribute("actionParam", actionParam);
       String demographicNo= (String)actionParam.get("clientId");
            
       Integer facilityId=(Integer)request.getSession().getAttribute(SessionConstants.CURRENT_FACILITY_ID);
       String providerNo =(String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
       request.setAttribute("clientId", demographicNo);
       request.setAttribute("client", clientManager.getClientByDemographicNo(demographicNo));
       ProgramClientRestriction pcrObj =(ProgramClientRestriction)clientForm.get("serviceRestriction");
       String rId=request.getParameter("rId");      
		if(Utility.IsEmpty(rId) && pcrObj.getId()!=null) rId=pcrObj.getId().toString();	
       if ("0".equals(rId) || rId==null) {
			pcrObj = new ProgramClientRestriction();
			pcrObj.setDemographicNo(Integer.valueOf(demographicNo));
			clientForm.set("serviceRestrictionLength", new Integer(180));			
			pcrObj.setId(null);
		} else if (!Utility.IsEmpty(rId)) 
		{			
			pcrObj =  clientRestrictionManager.find(Integer.valueOf(rId));
			
			pcrObj.setStartDateStr(MyDateFormat.getStandardDate(pcrObj.getStartDate()));
		}

       List allPrograms = programManager.getProgramsByProvider(facilityId, providerNo);
       request.setAttribute("allPrograms", allPrograms);
		clientForm.set("serviceRestriction", pcrObj);
		request.setAttribute("serviceObj", pcrObj);
     //  request.setAttribute("serviceRestrictions", clientRestrictionManager.getActiveRestrictionsForClient(Integer.valueOf(demographicNo), facilityId, new Date()));
       request.setAttribute("serviceRestrictionList",lookupManager.LoadCodeList("SRT",true, null, null));
   }

   private void setListAttributes(ActionForm form, HttpServletRequest request) {
       DynaActionForm clientForm = (DynaActionForm) form;

       HashMap actionParam = (HashMap) request.getAttribute("actionParam");
       if(actionParam==null){
    	  actionParam = new HashMap();
          actionParam.put("clientId", request.getParameter("clientId")); 
       }
       request.setAttribute("actionParam", actionParam);
       String demographicNo= (String)actionParam.get("clientId");
       Integer facilityId=(Integer)request.getSession().getAttribute(SessionConstants.CURRENT_FACILITY_ID);
       
       request.setAttribute("clientId", demographicNo);
       request.setAttribute("client", clientManager.getClientByDemographicNo(demographicNo));

      
       String providerNo = ((Provider) request.getSession().getAttribute("provider")).getProviderNo();
             
       /* service restrictions */
       	 //  List proPrograms = providerManager.getProgramDomain(providerNo);
           //request.setAttribute("serviceRestrictions", clientRestrictionManager.getActiveRestrictionsForClient(Integer.valueOf(demographicNo), facilityId, new Date()));
       	  request.setAttribute("serviceRestrictions", clientRestrictionManager.getAllRestrictionsForClient(Integer.valueOf(demographicNo),providerNo,facilityId));

   }

   

   public void setClientManager(ClientManager clientManager) {
	 this.clientManager = clientManager;
   }

   public void setProgramManager(ProgramManager programManager) {
	 this.programManager = programManager;
   }

   public void setProviderManager(ProviderManager providerManager) {
	 this.providerManager = providerManager;
   }


public void setClientRestrictionManager(
		ClientRestrictionManager clientRestrictionManager) {
	this.clientRestrictionManager = clientRestrictionManager;
}

public void setLookupManager(LookupManager lookupManager) {
	this.lookupManager = lookupManager;
}
private Boolean hasAccess(HttpServletRequest request, Integer programId, String function, String right)
{
    SecurityManager sec = (SecurityManager)request.getSession().getAttribute(KeyConstants.SESSION_KEY_SECURITY_MANAGER);
    String orgCd = "P" + programId.toString();
    return new Boolean(sec.GetAccess(function,orgCd).compareTo(right) >= 0);
}

}
