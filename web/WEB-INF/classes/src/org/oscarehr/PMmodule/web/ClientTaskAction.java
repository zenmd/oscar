package org.oscarehr.PMmodule.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.util.LabelValueBean;

import org.apache.struts.action.DynaActionForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;
import org.caisi.model.CustomFilter;
import org.caisi.service.TicklerManager;
import org.oscarehr.PMmodule.model.Bed;
import org.oscarehr.PMmodule.model.Program;
import org.oscarehr.PMmodule.model.ProgramClientRestriction;
import org.oscarehr.PMmodule.model.QuatroIntakeFamily;
import org.oscarehr.PMmodule.model.Room;
import org.oscarehr.PMmodule.service.AdmissionManager;
import org.oscarehr.PMmodule.service.ClientRestrictionManager;
import org.oscarehr.PMmodule.service.BedDemographicManager;
import org.oscarehr.PMmodule.service.BedManager;
import org.oscarehr.PMmodule.service.ClientManager;
import org.oscarehr.PMmodule.service.ProgramManager;
import org.oscarehr.PMmodule.service.ProviderManager;
import org.oscarehr.PMmodule.service.RoomDemographicManager;
import org.oscarehr.PMmodule.service.RoomManager;
import org.oscarehr.casemgmt.service.CaseManagementManager;
import org.oscarehr.util.SessionConstants;
import com.quatro.service.LookupManager;
import com.quatro.service.IntakeManager;
import com.quatro.common.KeyConstants;
import org.oscarehr.PMmodule.model.Provider;
import org.oscarehr.PMmodule.model.QuatroIntakeDB;
import org.oscarehr.PMmodule.model.Admission;
import oscar.MyDateFormat;
import org.oscarehr.PMmodule.web.formbean.QuatroClientAdmissionForm;
import org.oscarehr.PMmodule.model.QuatroIntake;

import org.oscarehr.PMmodule.model.RoomDemographic;
import org.oscarehr.PMmodule.model.BedDemographic;
import org.oscarehr.PMmodule.model.RoomDemographicPK;
import org.oscarehr.PMmodule.model.BedDemographicPK;
import org.oscarehr.PMmodule.exception.AdmissionException;
import org.oscarehr.PMmodule.exception.ProgramFullException;
import org.oscarehr.PMmodule.exception.ServiceRestrictionException;

import org.caisi.model.Tickler;

public class ClientTaskAction extends BaseClientAction{
    private ClientManager clientManager;
    private ProgramManager programManager;
    private TicklerManager ticklerManager;
    private ProviderManager providerManager;

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return list(mapping, form, request, response);
    }

    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
 	    HashMap actionParam = new HashMap();
        actionParam.put("clientId", request.getParameter("clientId")); 
        request.setAttribute("actionParam", actionParam);

        request.setAttribute("clientId", request.getParameter("clientId"));
        request.setAttribute("client", clientManager.getClientByDemographicNo(request.getParameter("clientId")));
 	    super.setScreenMode(request, KeyConstants.TAB_CLIENT_TASK);

    	DynaActionForm ticklerForm = (DynaActionForm) form;
        CustomFilter filter = (CustomFilter) ticklerForm.get("filter");
        
        Integer currentFacilityId=(Integer)request.getSession().getAttribute(SessionConstants.CURRENT_FACILITY_ID);        
        String providerId = (String)request.getSession().getAttribute("user");
        String programId = "";
        
        List programs=programManager.getProgramByProvider(providerId, currentFacilityId);
        request.setAttribute("programs", programs);
        
        // if program selected default to first
        if (filter.getProgramId()==null || filter.getProgramId().length()==0)
        {
            if (programs.size()>0) filter.setProgramId(((Program)programs.get(0)).getId().toString());
        }
        
        List ticklers = ticklerManager.getTicklers(filter, currentFacilityId,providerId, programId);

        String filter_order = (String) request.getSession().getAttribute("filter_order");
        request.getSession().setAttribute("ticklers", ticklers);
        request.setAttribute("providers", providerManager.getProviders());
//        request.setAttribute("demographics", demographicMgr.getDemographics());
        
		request.setAttribute("customFilters", ticklerManager.getCustomFilters(this.getProviderNo(request)));
//        request.setAttribute("from", getFrom(request));
        request.getSession().setAttribute("filter_order", filter_order);
        return mapping.findForward("list");
    }

    public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
 	    cal.get(field)
 	    tickler.setService_hour(service_hour);
 	    tickler.setService_minute(service_minute);
 	    tickler.setService_ampm(service_ampm);
*/
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
