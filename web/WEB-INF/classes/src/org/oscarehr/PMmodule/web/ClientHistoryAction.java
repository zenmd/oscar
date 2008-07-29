package org.oscarehr.PMmodule.web;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.oscarehr.PMmodule.service.ClientHistoryManager;
import org.oscarehr.PMmodule.service.ClientManager;
import org.oscarehr.PMmodule.service.ComplaintManager;
import org.oscarehr.PMmodule.service.IncidentManager;
import org.oscarehr.PMmodule.web.formbean.IncidentForm;
import org.oscarehr.PMmodule.web.formbean.QuatroClientComplaintForm;

import oscar.MyDateFormat;

import com.quatro.model.Complaint;
import com.quatro.model.IncidentValue;
import com.quatro.model.LookupCodeValue;
import com.quatro.service.LookupManager;
import com.quatro.common.KeyConstants;
public class ClientHistoryAction extends BaseClientAction {

	private ClientHistoryManager historyManager;
	private ClientManager clientManager;
	public void setClientManager(ClientManager clientManager) {
		this.clientManager = clientManager;
	}

	public void setClientHistoryManager(ClientHistoryManager historyManager)
	{
		this.historyManager = historyManager;
	}
	
	public static final String ID = "id";

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		return list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		HashMap actionParam = (HashMap) request.getAttribute("actionParam");
		if (actionParam == null) {
			actionParam = new HashMap();
			actionParam.put("clientId", request.getParameter("clientId"));
		}
		request.setAttribute("actionParam", actionParam);
		super.setScreenMode(request, KeyConstants.TAB_CLIENT_HISTORY);
		String tmp = (String) actionParam.get("clientId");
		request.setAttribute("clientId", tmp);

		// String tmp = request.getParameter("id");
		Integer clientId = Integer.valueOf(tmp);
		String providerNo = (String) request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
			
        Integer shelterId=(Integer)request.getSession().getAttribute(KeyConstants.SESSION_KEY_SHELTERID);
		List histories = historyManager.getClientHistory(clientId, providerNo, shelterId);

		request.setAttribute("histories", histories);
		request.setAttribute("client", clientManager.getClientByDemographicNo(clientId.toString()));
		return mapping.findForward("list");
	}

}
