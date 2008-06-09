package org.oscarehr.PMmodule.web;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.caisi.model.CustomFilter;
import org.caisi.model.Tickler;
import org.caisi.service.TicklerManager;
import org.oscarehr.PMmodule.model.Program;
import org.oscarehr.PMmodule.service.ClientManager;
import org.oscarehr.PMmodule.service.ProgramManager;
import org.oscarehr.PMmodule.service.ProviderManager;
import org.oscarehr.PMmodule.web.formbean.TicklerForm;

import com.quatro.common.KeyConstants;

public class ClientTaskAction extends BaseClientAction{
    private ClientManager clientManager;
    private ProgramManager programManager;
    private TicklerManager ticklerManager;
    private ProviderManager providerManager;

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return list(mapping, form, request, response);
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
        request.setAttribute("programs", programs);
        
        
        List ticklers = ticklerManager.getTicklersByClientId(shelterId, providerNo, Integer.valueOf(request.getParameter("clientId")));

        request.getSession().setAttribute("ticklers", ticklers);
        request.setAttribute("providers", providerManager.getProviders());
        
        return mapping.findForward("list");
    }

    public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	TicklerForm ticklerForm = (TicklerForm) form;

    	HashMap actionParam = new HashMap();
        actionParam.put("clientId", request.getParameter("clientId")); 
        request.setAttribute("actionParam", actionParam);

        request.setAttribute("clientId", request.getParameter("clientId"));
        request.setAttribute("client", clientManager.getClientByDemographicNo(request.getParameter("clientId")));
 	    super.setScreenMode(request, KeyConstants.TAB_CLIENT_TASK);
 	    Tickler tickler = new Tickler();
 	    Calendar cal = Calendar.getInstance();
 	    tickler.setService_date(cal.getTime());
/*
 	    GregorianCalendar now = new GregorianCalendar();
		int curYear = now.get(Calendar.YEAR);
		int curMonth = now.get(Calendar.MONTH);
		int curDay = now.get(Calendar.DAY_OF_MONTH);
		int curHour = now.get(Calendar.HOUR);
		int curMinute = now.get(Calendar.MINUTE);
		boolean curAm = (now.get(Calendar.HOUR_OF_DAY) <= 12) ? true : false;
*/
 	    
 	    tickler.setService_hour(String.valueOf(cal.get(Calendar.HOUR)));
 	    tickler.setService_minute(String.valueOf(cal.get(Calendar.MINUTE)));
		boolean curAm = (cal.get(Calendar.HOUR_OF_DAY) <= 12) ? true : false;
        if(curAm){
 	      tickler.setService_ampm("AM");
        }else{
   	      tickler.setService_ampm("PM");
        }
        
        ticklerForm.setTickler(tickler);
        
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
