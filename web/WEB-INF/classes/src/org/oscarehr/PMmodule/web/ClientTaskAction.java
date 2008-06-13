package org.oscarehr.PMmodule.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;
import org.caisi.model.CustomFilter;
import org.caisi.model.Tickler;
import org.caisi.service.TicklerManager;
import org.oscarehr.PMmodule.service.ClientManager;
import org.oscarehr.PMmodule.service.ProgramManager;
import org.oscarehr.PMmodule.service.ProviderManager;
import org.oscarehr.PMmodule.web.formbean.TicklerForm;

import oscar.Misc;

import com.quatro.common.KeyConstants;

public class ClientTaskAction extends BaseClientAction{
    private ClientManager clientManager;
    private ProgramManager programManager;
    private TicklerManager ticklerManager;
    private ProviderManager providerManager;

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return list(mapping, form, request, response);
    }

    //for My Task
    public ActionForward filter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	TicklerForm ticklerForm = (TicklerForm) form;

    	Integer shelterId=(Integer)request.getSession().getAttribute(KeyConstants.SESSION_KEY_SHELTERID);        
        String providerNo = (String)request.getSession().getAttribute("user");
        
        List programs=programManager.getPrograms(providerNo, shelterId);
        ticklerForm.setProgramLst(programs);
        
        //CustomFilter filter = ticklerForm.getFilter();
        List ticklers = ticklerManager.getTicklers(null, shelterId, providerNo);

        request.setAttribute("ticklers", ticklers);
        
        return mapping.findForward("mytask_filter");
    }

    public ActionForward mytaskedit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	TicklerForm ticklerForm = (TicklerForm) form;
        return mapping.findForward("mytask_edit");
    }
    
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	TicklerForm ticklerForm = (TicklerForm) form;

    	HashMap actionParam = new HashMap();
        actionParam.put("clientId", request.getParameter("clientId")); 
        request.setAttribute("actionParam", actionParam);

        request.setAttribute("clientId", request.getParameter("clientId"));
        request.setAttribute("client", clientManager.getClientByDemographicNo(request.getParameter("clientId")));
 	    super.setScreenMode(request, KeyConstants.TAB_CLIENT_TASK);

        Integer shelterId=(Integer)request.getSession().getAttribute(KeyConstants.SESSION_KEY_SHELTERID);        
        String providerNo = (String)request.getSession().getAttribute("user");
        
        List programs=programManager.getBedPrograms(providerNo, shelterId);
        ticklerForm.setProgramLst(programs);
//        request.setAttribute("programs", programs);
        
        
        List ticklers = ticklerManager.getTicklersByClientId(shelterId, providerNo, Integer.valueOf(request.getParameter("clientId")));

        request.setAttribute("ticklers", ticklers);
        
        return mapping.findForward("list");
    }
    
    public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	TicklerForm ticklerForm = (TicklerForm) form;
    	
    	String clientId = request.getParameter("clientId");
    	HashMap actionParam = new HashMap();
        actionParam.put("clientId", clientId); 
        request.setAttribute("actionParam", actionParam);

        request.setAttribute("clientId", clientId);
        request.setAttribute("client", clientManager.getClientByDemographicNo(clientId));
 	    super.setScreenMode(request, KeyConstants.TAB_CLIENT_TASK);
 	    
        ArrayList ampmLst = new ArrayList();
        LabelValueBean lvb1 = new LabelValueBean("AM", "AM");
        ampmLst.add(lvb1);
        LabelValueBean lvb2 = new LabelValueBean("PM", "PM");
        ampmLst.add(lvb2);
        ticklerForm.setAmpmLst(ampmLst);

        ArrayList serviceHourLst = new ArrayList();
        for(int i=1;i<=12;i++){
          LabelValueBean lvb3 = new LabelValueBean(Misc.forwardZero(String.valueOf(i),2), Misc.forwardZero(String.valueOf(i),2));
          serviceHourLst.add(lvb3);
        }
        ticklerForm.setServiceHourLst(serviceHourLst);

        ArrayList serviceMinuteLst = new ArrayList();
        for(int i=0;i<60;i++){
          LabelValueBean lvb4 = new LabelValueBean(Misc.forwardZero(String.valueOf(i),2), Misc.forwardZero(String.valueOf(i),2));
          serviceMinuteLst.add(lvb4);
        }
        ticklerForm.setServiceMinuteLst(serviceMinuteLst);
        
        ArrayList priorityLst = new ArrayList();
        LabelValueBean lvb5 = new LabelValueBean("High", "High");
        priorityLst.add(lvb5);
        LabelValueBean lvb6 = new LabelValueBean("Normal", "Normal");
        priorityLst.add(lvb6);
        LabelValueBean lvb7 = new LabelValueBean("Low", "Low");
        priorityLst.add(lvb7);
        ticklerForm.setPriorityLst(priorityLst);

		Integer shelterId = (Integer) request.getSession().getAttribute(KeyConstants.SESSION_KEY_SHELTERID);
		String providerNo = (String) request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
        List programLst = programManager.getPrograms(Integer.valueOf(clientId), providerNo, shelterId);
        ticklerForm.setProgramLst(programLst);
        
        String tickler_no =  request.getParameter("ticklerNo");
        Tickler tickler = ticklerManager.getTickler(tickler_no);
        ticklerForm.setTickler(tickler);
        request.setAttribute("viewTickler", "Y");

        List providerLst = providerManager.getActiveProviders(tickler.getProgram_id());
        ticklerForm.setProviderLst(providerLst);
        
        return mapping.findForward("add");
    }
    
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionMessages messages = new ActionMessages();
        super.setScreenMode(request, KeyConstants.TAB_CLIENT_TASK);
        boolean isError = false;
        boolean isWarning = false;

        TicklerForm ticklerForm = (TicklerForm) form;
    	
    	String clientId = request.getParameter("clientId");
    	HashMap actionParam = new HashMap();
        actionParam.put("clientId", clientId); 
        request.setAttribute("actionParam", actionParam);

        request.setAttribute("clientId", clientId);
        request.setAttribute("client", clientManager.getClientByDemographicNo(clientId));
 	    super.setScreenMode(request, KeyConstants.TAB_CLIENT_TASK);
 	    
 	    
        Tickler tickler = (Tickler) ticklerForm.getTickler();

        tickler.setServiceTime(tickler.getService_hour() + ":" + tickler.getService_minute() + 
           		" " + tickler.getService_ampm());
        tickler.setUpdate_date(new java.util.Date());
        ticklerManager.addTickler(tickler);

        
        ArrayList ampmLst = new ArrayList();
        LabelValueBean lvb1 = new LabelValueBean("AM", "AM");
        ampmLst.add(lvb1);
        LabelValueBean lvb2 = new LabelValueBean("PM", "PM");
        ampmLst.add(lvb2);
        ticklerForm.setAmpmLst(ampmLst);

        ArrayList serviceHourLst = new ArrayList();
        for(int i=1;i<=12;i++){
          LabelValueBean lvb3 = new LabelValueBean(Misc.forwardZero(String.valueOf(i),2), Misc.forwardZero(String.valueOf(i),2));
          serviceHourLst.add(lvb3);
        }
        ticklerForm.setServiceHourLst(serviceHourLst);

        ArrayList serviceMinuteLst = new ArrayList();
        for(int i=0;i<60;i++){
          LabelValueBean lvb4 = new LabelValueBean(Misc.forwardZero(String.valueOf(i),2), Misc.forwardZero(String.valueOf(i),2));
          serviceMinuteLst.add(lvb4);
        }
        ticklerForm.setServiceMinuteLst(serviceMinuteLst);
        
        ArrayList priorityLst = new ArrayList();
        LabelValueBean lvb5 = new LabelValueBean("High", "High");
        priorityLst.add(lvb5);
        LabelValueBean lvb6 = new LabelValueBean("Normal", "Normal");
        priorityLst.add(lvb6);
        LabelValueBean lvb7 = new LabelValueBean("Low", "Low");
        priorityLst.add(lvb7);
        ticklerForm.setPriorityLst(priorityLst);

		Integer shelterId = (Integer) request.getSession().getAttribute(KeyConstants.SESSION_KEY_SHELTERID);
		String providerNo = (String) request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
        List programLst = programManager.getPrograms(Integer.valueOf(clientId), providerNo, shelterId);
        ticklerForm.setProgramLst(programLst);
        
        List providerLst = providerManager.getActiveProviders(ticklerForm.getTickler().getProgram_id());
        ticklerForm.setProviderLst(providerLst);

 	    if(!(isWarning || isError)) messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.save.success", request.getContextPath()));
        saveMessages(request,messages);

       return mapping.findForward("add");
    }
    
    public ActionForward changeProgram(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	TicklerForm ticklerForm = (TicklerForm) form;
    	
    	String clientId = request.getParameter("clientId");
    	HashMap actionParam = new HashMap();
        actionParam.put("clientId", clientId); 
        request.setAttribute("actionParam", actionParam);

        request.setAttribute("clientId", clientId);
        request.setAttribute("client", clientManager.getClientByDemographicNo(clientId));
 	    super.setScreenMode(request, KeyConstants.TAB_CLIENT_TASK);
 	    
        ArrayList ampmLst = new ArrayList();
        LabelValueBean lvb1 = new LabelValueBean("AM", "AM");
        ampmLst.add(lvb1);
        LabelValueBean lvb2 = new LabelValueBean("PM", "PM");
        ampmLst.add(lvb2);
        ticklerForm.setAmpmLst(ampmLst);

        ArrayList serviceHourLst = new ArrayList();
        for(int i=1;i<=12;i++){
          LabelValueBean lvb3 = new LabelValueBean(Misc.forwardZero(String.valueOf(i),2), Misc.forwardZero(String.valueOf(i),2));
          serviceHourLst.add(lvb3);
        }
        ticklerForm.setServiceHourLst(serviceHourLst);

        ArrayList serviceMinuteLst = new ArrayList();
        for(int i=0;i<60;i++){
          LabelValueBean lvb4 = new LabelValueBean(Misc.forwardZero(String.valueOf(i),2), Misc.forwardZero(String.valueOf(i),2));
          serviceMinuteLst.add(lvb4);
        }
        ticklerForm.setServiceMinuteLst(serviceMinuteLst);
        
        ArrayList priorityLst = new ArrayList();
        LabelValueBean lvb5 = new LabelValueBean("High", "High");
        priorityLst.add(lvb5);
        LabelValueBean lvb6 = new LabelValueBean("Normal", "Normal");
        priorityLst.add(lvb6);
        LabelValueBean lvb7 = new LabelValueBean("Low", "Low");
        priorityLst.add(lvb7);
        ticklerForm.setPriorityLst(priorityLst);

		Integer shelterId = (Integer) request.getSession().getAttribute(KeyConstants.SESSION_KEY_SHELTERID);
		String providerNo = (String) request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
        List programLst = programManager.getPrograms(Integer.valueOf(clientId), providerNo, shelterId);
        ticklerForm.setProgramLst(programLst);
        
        List providerLst = providerManager.getActiveProviders(ticklerForm.getTickler().getProgram_id());
        ticklerForm.setProviderLst(providerLst);

        return mapping.findForward("add");
    }
    
    public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	TicklerForm ticklerForm = (TicklerForm) form;

    	String clientId = request.getParameter("clientId");
    	HashMap actionParam = new HashMap();
        actionParam.put("clientId", clientId); 
        request.setAttribute("actionParam", actionParam);

        request.setAttribute("clientId", clientId);
        request.setAttribute("client", clientManager.getClientByDemographicNo(clientId));
 	    super.setScreenMode(request, KeyConstants.TAB_CLIENT_TASK);
 	    Tickler tickler = new Tickler();
        tickler.setTickler_no(new Integer(0));
		String providerNo = (String) request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
        tickler.setCreator(providerNo);
        tickler.setDemographic_no(Integer.valueOf(clientId));


        Calendar cal = com.quatro.util.CurrentDateTime.getCurrentDateTime();
 	    
 	    tickler.setService_date(cal.getTime());

 	    if(cal.get(Calendar.HOUR_OF_DAY)==0 || cal.get(Calendar.HOUR_OF_DAY)==12){
  	       tickler.setService_hour("12");
 	    }else{
 	       tickler.setService_hour(Misc.forwardZero(String.valueOf(cal.get(Calendar.HOUR)),2));
 	    }
 	    
 	    tickler.setService_minute(Misc.forwardZero(String.valueOf(cal.get(Calendar.MINUTE)),2));
		boolean curAm = (cal.get(Calendar.HOUR_OF_DAY) < 12) ? true : false;
        if(curAm){
 	      tickler.setService_ampm("AM");
        }else{
   	      tickler.setService_ampm("PM");
        }
        if(tickler.getPriority()==null) tickler.setPriority("Normal");
        
        ticklerForm.setTickler(tickler);
        
        ArrayList ampmLst = new ArrayList();
        LabelValueBean lvb1 = new LabelValueBean("AM", "AM");
        ampmLst.add(lvb1);
        LabelValueBean lvb2 = new LabelValueBean("PM", "PM");
        ampmLst.add(lvb2);
        ticklerForm.setAmpmLst(ampmLst);

        ArrayList serviceHourLst = new ArrayList();
        for(int i=1;i<=12;i++){
          LabelValueBean lvb3 = new LabelValueBean(Misc.forwardZero(String.valueOf(i),2), Misc.forwardZero(String.valueOf(i),2));
          serviceHourLst.add(lvb3);
        }
        ticklerForm.setServiceHourLst(serviceHourLst);

        ArrayList serviceMinuteLst = new ArrayList();
        for(int i=0;i<60;i++){
          LabelValueBean lvb4 = new LabelValueBean(Misc.forwardZero(String.valueOf(i),2), Misc.forwardZero(String.valueOf(i),2));
          serviceMinuteLst.add(lvb4);
        }
        ticklerForm.setServiceMinuteLst(serviceMinuteLst);
        
        ArrayList priorityLst = new ArrayList();
        LabelValueBean lvb5 = new LabelValueBean("High", "High");
        priorityLst.add(lvb5);
        LabelValueBean lvb6 = new LabelValueBean("Normal", "Normal");
        priorityLst.add(lvb6);
        LabelValueBean lvb7 = new LabelValueBean("Low", "Low");
        priorityLst.add(lvb7);
        ticklerForm.setPriorityLst(priorityLst);

		Integer shelterId = (Integer) request.getSession().getAttribute(KeyConstants.SESSION_KEY_SHELTERID);
        List programLst = programManager.getPrograms(Integer.valueOf(clientId), providerNo, shelterId);
        ticklerForm.setProgramLst(programLst);
        
        ticklerForm.setProviderLst(new ArrayList());
        
        return mapping.findForward("add");
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

	public void setTicklerManager(TicklerManager ticklerManager) {
		this.ticklerManager = ticklerManager;
	}

}
