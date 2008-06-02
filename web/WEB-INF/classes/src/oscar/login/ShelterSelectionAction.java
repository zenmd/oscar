package oscar.login;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.oscarehr.PMmodule.service.ProviderManager;
import org.oscarehr.util.SpringUtils;

import com.quatro.common.KeyConstants;
import com.quatro.service.LookupManager;

public final class ShelterSelectionAction extends DispatchAction {

    private ProviderManager providerManager = (ProviderManager) SpringUtils.getBean("providerManager");
    private LookupManager lookupManager = (LookupManager) SpringUtils.getBean("lookupManager");

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  
    	String providerNo=(String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
    	List shelters=providerManager.getFacilityIds(providerNo);
    	String shlts = String.valueOf(shelters.get(0));
    	for(int i=1; i<shelters.size(); i++)
    	{
    		shlts += "," + String.valueOf(shelters.get(i));
    	}
    	List facilityCodes = lookupManager.LoadCodeList("FAC", false, shlts, null);
    	request.setAttribute("shelters", facilityCodes);
    	return mapping.findForward("shelterSelection");
    }
}