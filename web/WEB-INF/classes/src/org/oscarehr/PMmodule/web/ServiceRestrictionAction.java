package org.oscarehr.PMmodule.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.DynaActionForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;
import org.oscarehr.PMmodule.exception.ClientAlreadyRestrictedException;

import org.oscarehr.PMmodule.model.Program;
import org.oscarehr.PMmodule.model.ProgramClientRestriction;
import org.oscarehr.PMmodule.model.QuatroIntakeHeader;

import org.oscarehr.PMmodule.model.Provider;

import org.oscarehr.PMmodule.service.ClientManager;

import org.oscarehr.PMmodule.service.ProgramManager;
import org.oscarehr.PMmodule.service.ClientRestrictionManager;


import oscar.MyDateFormat;

import com.quatro.common.KeyConstants;
import com.quatro.model.security.NoAccessException;
import com.quatro.service.IntakeManager;
import com.quatro.service.LookupManager;
import com.quatro.util.Utility;



public class ServiceRestrictionAction  extends BaseClientAction {
   private ClientManager clientManager;
   private ProgramManager programManager;
   private ClientRestrictionManager clientRestrictionManager;
   private LookupManager lookupManager;
   private IntakeManager intakeManager;
   
   public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	   
       return list(mapping, form, request, response);
   }
   public ActionForward terminate_early(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws NoAccessException {
	   DynaActionForm clientForm = (DynaActionForm) form;
       ProgramClientRestriction restriction = (ProgramClientRestriction) clientForm.get("serviceRestriction");
	   String recId=request.getParameter("rId");
	   if(Utility.IsEmpty(recId)) 
	   		recId=restriction.getId().toString();
       Integer rId=Integer.valueOf(recId);
	   String providerNo=(String)request.getSession().getAttribute("user");
       super.getAccess(request, KeyConstants.FUN_CLIENTRESTRICTION,restriction.getProgramId(),KeyConstants.ACCESS_UPDATE);
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
       
       return list(mapping, form, request, response);
   }

   public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
//	   setListAttributes(form, request);
	   try {
	       HashMap actionParam = (HashMap) request.getAttribute("actionParam");
	       if(actionParam==null){
	    	  actionParam = new HashMap();
	          actionParam.put("clientId", request.getParameter("clientId")); 
	       }
	       request.setAttribute("actionParam", actionParam);
	       String demographicNo= (String)actionParam.get("clientId");
	       Integer shelterId=(Integer)request.getSession().getAttribute(KeyConstants.SESSION_KEY_SHELTERID);
	       
	       request.setAttribute("clientId", demographicNo);
	       request.setAttribute("client", clientManager.getClientByDemographicNo(demographicNo));
	
	      
	       String providerNo = ((Provider) request.getSession().getAttribute("provider")).getProviderNo();
	             
	       /* service restrictions */
	       	 //  List proPrograms = providerManager.getProgramDomain(providerNo);
	           //request.setAttribute("serviceRestrictions", clientRestrictionManager.getActiveRestrictionsForClient(Integer.valueOf(demographicNo), facilityId, new Date()));
	       Integer cId =Integer.valueOf(demographicNo);
	       request.setAttribute("serviceRestrictions", clientRestrictionManager.getAllRestrictionsForClient(cId,providerNo,shelterId));
		
		    List lstIntakeHeader = intakeManager.getQuatroIntakeHeaderListByFacility(cId, shelterId, providerNo);	  
		    if(lstIntakeHeader.size()>0) {
		       QuatroIntakeHeader obj0= (QuatroIntakeHeader)lstIntakeHeader.get(0);
	           request.setAttribute("currentIntakeProgramId", obj0.getProgramId());
		    }else{
	           request.setAttribute("currentIntakeProgramId", new Integer(0));
		    }
	
		    super.setScreenMode(request, KeyConstants.TAB_CLIENT_RESTRICTION);
	       return mapping.findForward("list");
       }
       catch(NoAccessException e)
       {
	       return mapping.findForward("failure");
       }
   }

   public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws NoAccessException {	   
	   ActionMessages messages = new ActionMessages();
	   DynaActionForm clientForm = (DynaActionForm) form;
       ProgramClientRestriction restriction = (ProgramClientRestriction) clientForm.get("serviceRestriction");       
       super.getAccess(request, KeyConstants.FUN_CLIENTRESTRICTION, restriction.getProgramId(),KeyConstants.ACCESS_UPDATE);
       try {
	       Integer days = (Integer) clientForm.get("serviceRestrictionLength");       
	       super.setScreenMode(request, KeyConstants.TAB_CLIENT_RESTRICTION);
	       
	       String providerNo=(String)request.getSession().getAttribute("user");
	       restriction.setProviderNo(providerNo);       
	       restriction.setEnabled(true);
	       boolean hasError = false;
	      if(Utility.IsEmpty(restriction.getCommentId())){
	    	  hasError =true;
	    	  messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"error.service.reason", request.getContextPath()));		
				saveMessages(request, messages);	
	      }
	     
	       if (restriction.getProgramId() == null
					|| restriction.getProgramId().intValue() <= 0) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"error.referral.program", request.getContextPath()));	
				 hasError =true;
				saveMessages(request, messages);				 
			}
	       else{
	    	   Program progObj=programManager.getProgram(restriction.getProgramId());
	    	   Integer maxDays = progObj.getMaximumServiceRestrictionDays();
	    	   if (maxDays != null) {
		    	   if(days.intValue() > maxDays.intValue()){
		    		 messages.add(ActionMessages.GLOBAL_MESSAGE, 
		    				 new ActionMessage("restrict.exceeds_maximum_days", request.getContextPath(), progObj.getMaximumServiceRestrictionDays()));	
		   			 hasError =true;
		   			saveMessages(request, messages);
		    	   }
	    	   }
	       }
	       if(hasError){
	    	   setEditAttributes(form, request);		   
			   return mapping.findForward("detail"); 
	       }
    	   //String sDt = restriction.getStartDateStr();
    	   //restriction.setStartDate(MyDateFormat.getCalendar(sDt));    	 
    	   //Calendar cal2 =MyDateFormat.getCalendar(sDt);
    	   String startDateStr = restriction.getStartDateStr();
    	   if(startDateStr.equals("0")){
    		   Calendar now = Calendar.getInstance();
    		   restriction.setStartDate(now);
    	   
	    	   Calendar cal2 = Calendar.getInstance(); 
	    	   cal2.add(Calendar.DAY_OF_MONTH, days.intValue());
	    	   restriction.setEndDate(cal2);
    	   }else{
    		   restriction.setStartDate(MyDateFormat.getCalendarwithTime(startDateStr));
    		   Calendar cal2 = MyDateFormat.getCalendarwithTime(startDateStr); 
	    	   cal2.add(Calendar.DAY_OF_MONTH, days.intValue());
	    	   restriction.setEndDate(cal2);
    	   }
    	   restriction.setLastUpdateDate(Calendar.getInstance());
           clientRestrictionManager.saveClientRestriction(restriction);
           messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.save.success", request.getContextPath()));
           saveMessages(request, messages);

           // clientForm.set("program", new Program());
           clientForm.set("serviceRestriction", restriction);
           //clientForm.set("serviceRestrictionLength", null);       
           setEditAttributes(form, request);
           //logManager.log("write", "service_restriction", id, request);
           return mapping.findForward("detail");       
	   }
       catch (ClientAlreadyRestrictedException e) {         
           messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("restrict.already_restricted", request.getContextPath()));
           saveMessages(request, messages);
           return mapping.findForward("detail");       
       }
	   catch(NoAccessException e)
	   {
	       return mapping.findForward("failure");
	   }
      catch(Exception e){
    	   messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("restrict.exceeds_maximum_days", request.getContextPath(),"1","180"));
           saveMessages(request, messages);
           return mapping.findForward("detail");       
       }
   }
 public ActionForward print(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	   
	   return mapping.findForward("detail"); 
 }
   private void setEditAttributes(ActionForm form, HttpServletRequest request) throws NoAccessException {
       DynaActionForm clientForm = (DynaActionForm) form;

       HashMap actionParam = (HashMap) request.getAttribute("actionParam");
       if(actionParam==null){
    	  actionParam = new HashMap();
          actionParam.put("clientId", request.getParameter("clientId")); 
       }
       request.setAttribute("actionParam", actionParam);
       String demographicNo= (String)actionParam.get("clientId");
            
       Integer shelterId=(Integer)request.getSession().getAttribute(KeyConstants.SESSION_KEY_SHELTERID);
       String providerNo =(String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
       request.setAttribute("clientId", demographicNo);
       request.setAttribute("client", clientManager.getClientByDemographicNo(demographicNo));
       ProgramClientRestriction pcrObj =(ProgramClientRestriction)clientForm.get("serviceRestriction");
       String rId=request.getParameter("rId");      
		if(Utility.IsEmpty(rId) && pcrObj.getId()!=null) rId=pcrObj.getId().toString();	
       if ("0".equals(rId) || rId==null) {
			pcrObj = new ProgramClientRestriction();
			pcrObj.setDemographicNo(Integer.valueOf(demographicNo));
//	        Integer days = (Integer) clientForm.get("serviceRestrictionLength");       
//			clientForm.set("serviceRestrictionLength", new Integer(180));			
			pcrObj.setId(null);			
		} else if (!Utility.IsEmpty(rId)) 
		{			
			pcrObj =  clientRestrictionManager.find(Integer.valueOf(rId));
			
			pcrObj.setStartDateStr(MyDateFormat.getStandardDateTime(pcrObj.getStartDate()));
			clientForm.set("serviceRestriction", pcrObj);
			boolean readOnly =super.isReadOnly(request, pcrObj.getStatus() ,KeyConstants.FUN_CLIENTRESTRICTION, pcrObj.getProgramId());
			if(readOnly) request.setAttribute("isReadOnly", Boolean.valueOf(readOnly));
		}

//       List allPrograms = programManager.getPrograms(Program.PROGRAM_STATUS_ACTIVE,providerNo,shelterId);
       List programIds =clientManager.getRecentProgramIds(Integer.valueOf(demographicNo),providerNo,shelterId);
	    List allPrograms = null;
	     if (programIds.size() > 0) {
		     String progs = ((Integer)programIds.get(0)).toString();
		     for (int i=1; i<programIds.size(); i++)
		     {
		   	   progs += "," + ((Integer)programIds.get(i)).toString();
		     }
		     allPrograms =  lookupManager.LoadCodeList("PRO", true, progs, null);
	      }
	      else
	      {
	    	  allPrograms = new ArrayList();
	      }
       request.setAttribute("allPrograms", allPrograms);
//		clientForm.set("serviceRestriction", pcrObj);
		request.setAttribute("serviceObj", pcrObj);
     //  request.setAttribute("serviceRestrictions", clientRestrictionManager.getActiveRestrictionsForClient(Integer.valueOf(demographicNo), facilityId, new Date()));
       request.setAttribute("serviceRestrictionList",lookupManager.LoadCodeList("SRT",true, null, null));
   }
   public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	   try {
		   DynaActionForm clientForm = (DynaActionForm) form;
	       HashMap actionParam = (HashMap) request.getAttribute("actionParam");
	       if(actionParam==null){
	    	  actionParam = new HashMap();
	          actionParam.put("clientId", request.getParameter("clientId")); 
	       }
	       Integer len = (Integer)clientForm.get("serviceRestrictionLength");
	       
	       if(len == null || len.intValue() == 0)
	    	   clientForm.set("serviceRestrictionLength", null);
	       
	       request.setAttribute("actionParam", actionParam);
	       String demographicNo= (String)actionParam.get("clientId");
	            
	       Integer shelterId=(Integer)request.getSession().getAttribute(KeyConstants.SESSION_KEY_SHELTERID);
	       String providerNo =(String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
	       request.setAttribute("clientId", demographicNo);
	       request.setAttribute("client", clientManager.getClientByDemographicNo(demographicNo));
	       ProgramClientRestriction pcrObj = (ProgramClientRestriction)clientForm.get("serviceRestriction");
	       String rId=request.getParameter("rId");      
	       
	       if(Utility.IsEmpty(rId) && pcrObj.getId()!=null && pcrObj.getId().intValue() != 0) {
	    	   pcrObj =  clientRestrictionManager.find(pcrObj.getId());
	    	   rId=pcrObj.getId().toString();    	   
	       }
	       if(pcrObj != null && pcrObj.getProgramId() != null){
	    	   pcrObj = (ProgramClientRestriction)clientForm.get("serviceRestriction");
	    	   pcrObj.setDemographicNo(Integer.valueOf(demographicNo));
	       } else if ("0".equals(rId)) {
				pcrObj = new ProgramClientRestriction();
				pcrObj.setDemographicNo(Integer.valueOf(demographicNo));
				pcrObj.setStartDateStr("0");
				//clientForm.set("serviceRestrictionLength", new Integer(180));			
				pcrObj.setId(null);
				
			} else if (!Utility.IsEmpty(rId)){
				
				pcrObj =  clientRestrictionManager.find(new Integer(rId));
				
				pcrObj.setStartDateStr(MyDateFormat.getStandardDateTime(pcrObj.getStartDate()));
				boolean readOnly =super.isReadOnly(request, pcrObj.getStatus() ,KeyConstants.FUN_CLIENTRESTRICTION, pcrObj.getProgramId());
				if(readOnly) request.setAttribute("isReadOnly", Boolean.valueOf(readOnly));
				
				int length = MyDateFormat.getDaysDiff(pcrObj.getStartDate(), pcrObj.getEndDate());
		    	clientForm.set("serviceRestrictionLength", new Integer(length));
				
			}
	
	       List programIds =clientManager.getRecentProgramIds(Integer.valueOf(demographicNo),providerNo,shelterId);
		     List programs = null;
		     if (programIds.size() > 0) {
			     String progs = ((Integer)programIds.get(0)).toString();
			     for (int i=1; i<programIds.size(); i++)
			     {
			   	   progs += "," + ((Integer)programIds.get(i)).toString();
			     }
			     programs =  lookupManager.LoadCodeList("PRO", true, progs, null);
		      }
		      else
		      {
		    	 programs = new ArrayList();
		      }
	       request.setAttribute("allPrograms", programs);
	
	//       if (pcrObj.getProgramId() == null || pcrObj.getProgramId().intValue() <= 0) {
	//    	   if(allPrograms.size() > 0)
	//    		   pcrObj.setProgramId(((Program)allPrograms.get(0)).getId());
	//       }
	       if(programs.size() > 0 && pcrObj.getProgramId()!=null && pcrObj.getProgramId().intValue()!=0){
	    	   Program program = programManager.getProgram(pcrObj.getProgramId().toString());
	//    	   Integer defaultDays = program.getDefaultServiceRestrictionDays();
	//    	   if( defaultDays == null || defaultDays.intValue() < 1)
	//    		   defaultDays = new Integer(1);
	//    	   clientForm.set("serviceRestrictionLength", defaultDays);
	
	    	   Integer maxDays = program.getMaximumServiceRestrictionDays();
	    	   if(maxDays == null )
	    		   clientForm.set("maxLength", new Integer(0));
	    	   else
	    		   clientForm.set("maxLength", maxDays);
	 
	    	   if(Utility.IsEmpty(rId)) {
		    	   Integer defDays = program.getDefaultServiceRestrictionDays();
		    	   if(defDays != null && defDays.intValue() > 0)
		    		   clientForm.set("serviceRestrictionLength", defDays);
		    	   else
		    		   clientForm.set("serviceRestrictionLength", null);
	    	   }
	       }
	       else
	       {
	    	   if(Utility.IsEmpty(rId)) clientForm.set("serviceRestrictionLength", null);
	       }
	    	   
	//       if (!Utility.IsEmpty(rId) && !("0".equals(rId))){
	//    	   int length = MyDateFormat.getDaysDiff(pcrObj.getStartDate(), pcrObj.getEndDate());
	//    	   clientForm.set("serviceRestrictionLength", new Integer(length));
	//       }
	       
	       clientForm.set("serviceRestriction", pcrObj);
	       
	       request.setAttribute("serviceObj", pcrObj);
	       
	       if(pcrObj.getEndDate()!=null && pcrObj.getEndDate().getTime().getTime() < System.currentTimeMillis()){
	           request.setAttribute("serviceObjStatus", "completed");
	       }else{
	           request.setAttribute("serviceObjStatus", "not");
	       }
	
	       request.setAttribute("serviceRestrictionList",lookupManager.LoadCodeList("SRT",true, null, null));
	       
	       
	       super.setScreenMode(request, KeyConstants.TAB_CLIENT_RESTRICTION);
	       return mapping.findForward("detail");
	   }
	   catch(NoAccessException e)
	   {
		   return mapping.findForward("failure");
	   }
   }
   public void setClientManager(ClientManager clientManager) {
	 this.clientManager = clientManager;
   }

   public void setProgramManager(ProgramManager programManager) {
	 this.programManager = programManager;
   }


public void setClientRestrictionManager(
		ClientRestrictionManager clientRestrictionManager) {
	this.clientRestrictionManager = clientRestrictionManager;
}

public void setLookupManager(LookupManager lookupManager) {
	this.lookupManager = lookupManager;
}
public void setIntakeManager(IntakeManager intakeManager) {
	this.intakeManager = intakeManager;
}

}
