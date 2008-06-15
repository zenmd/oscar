package oscar.login;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.oscarehr.PMmodule.service.ProviderManager;
import org.oscarehr.util.SpringUtils;

import oscar.log.LogAction;

import com.quatro.common.KeyConstants;
import com.quatro.model.LookupCodeValue;
import com.quatro.service.LookupManager;

public final class ShelterSelectionAction extends DispatchAction {

    private ProviderManager providerManager = (ProviderManager) SpringUtils.getBean("providerManager");
    private LookupManager lookupManager = (LookupManager) SpringUtils.getBean("lookupManager");

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	 String mthd =request.getParameter("method");
         if(mthd!=null && mthd.equals("select")){
        	 select(mapping,form,request,response);
        	 return mapping.findForward("home");
         }
         
    	String providerNo=(String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
    	List shelters=providerManager.getShelterIds(providerNo);
    	ActionMessages messages = new ActionMessages();
    	if (shelters==null || shelters.size()<=0) {
    		 messages.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.miss.shelter", request.getContextPath()));
            saveMessages(request,messages);
            return mapping.getInputForward();
        }    	
    	
    	String shlts = String.valueOf(shelters.get(0));
      
         if (shelters.size() > 1) {

         	for(int i=1; i<shelters.size(); i++)
         	{
         		shlts += "," + String.valueOf(shelters.get(i));
         	}
         	List facilityCodes = lookupManager.LoadCodeList("SHL", false, shlts, null);
         	request.setAttribute("shelters", facilityCodes);
      
             return(mapping.findForward("shelterSelection"));
         }
         else if (shelters.size() == 1) {
             Integer shelterId = (Integer) shelters.get(0);
             LookupCodeValue shelter=lookupManager.GetLookupCode("SHL",String.valueOf(shelterId));
             request.getSession().setAttribute(KeyConstants.SESSION_KEY_SHELTERID , shelterId);
             request.getSession().setAttribute(KeyConstants.SESSION_KEY_SHELTER, shelter);             
            // LogAction.addLog(strAuth[0], LogConst.LOGIN, LogConst.CON_LOGIN, "shelterId="+shelterId, ip);
         }
         else {
             request.getSession().setAttribute(KeyConstants.SESSION_KEY_SHELTERID, new Integer(0));
             request.getSession().setAttribute(KeyConstants.SESSION_KEY_SHELTER, new LookupCodeValue());
         }
        
         return mapping.findForward("home");
            	
    }
    public void select(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	
		String shelter =request.getParameter("shelterId");
		 request.getSession().setAttribute(KeyConstants.SESSION_KEY_SHELTERID, new Integer(shelter));
         request.getSession().setAttribute(KeyConstants.SESSION_KEY_SHELTER, new LookupCodeValue());
        
    }
}