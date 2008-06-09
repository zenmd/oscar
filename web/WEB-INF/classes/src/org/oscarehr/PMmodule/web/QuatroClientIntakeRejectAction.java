package org.oscarehr.PMmodule.web;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.oscarehr.PMmodule.service.ClientManager;
import org.oscarehr.PMmodule.web.formbean.QuatroClientIntakeRejectForm;
import org.oscarehr.PMmodule.service.ProgramQueueManager;

import com.quatro.common.KeyConstants;
import com.quatro.service.LookupManager;

public class QuatroClientIntakeRejectAction extends BaseClientAction {
   private ClientManager clientManager;
   private ProgramQueueManager programQueueManager;
   private LookupManager lookupManager;
	   
   public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       return edit(mapping, form, request, response);
   }
   
   public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	   QuatroClientIntakeRejectForm clientForm = (QuatroClientIntakeRejectForm) form;
       super.setScreenMode(request, KeyConstants.TAB_CLIENT_ADMISSION);
       HashMap actionParam = (HashMap) request.getAttribute("actionParam");
       if(actionParam==null){
    	  actionParam = new HashMap();
          actionParam.put("clientId", request.getParameter("clientId")); 
       }
       request.setAttribute("actionParam", actionParam);
       String demographicNo= (String)actionParam.get("clientId");
       
       request.setAttribute("clientId", demographicNo);
       
       request.setAttribute("client", clientManager.getClientByDemographicNo(demographicNo));

       List rejectReasons = lookupManager.LoadCodeList("IRR", true, null, null);
       clientForm.setRejectReasonList(rejectReasons);
       return mapping.findForward("edit");
   }

   public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       ActionMessages messages = new ActionMessages();
       super.setScreenMode(request, KeyConstants.TAB_CLIENT_ADMISSION);
       boolean isError = false;
       boolean isWarning = false;
	   QuatroClientIntakeRejectForm clientForm = (QuatroClientIntakeRejectForm) form;
	   programQueueManager.rejectQueue(clientForm.getQueueId(), clientForm.getRejectNote(), clientForm.getRejectReason());

	   if(!(isWarning || isError)) messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("message.save.success", request.getContextPath()));
       saveMessages(request,messages);
	   
       return edit(mapping, form, request, response);
   }
   
   public void setClientManager(ClientManager clientManager) {
	 this.clientManager = clientManager;
   }

   public void setProgramQueueManager(ProgramQueueManager programQueueManager) {
	 this.programQueueManager = programQueueManager;
   }

   public void setLookupManager(LookupManager lookupManager) {
	 this.lookupManager = lookupManager;
   }

}
