package oscar.login;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.oscarehr.PMmodule.dao.ProviderDao;
import org.oscarehr.PMmodule.model.Provider;
import org.oscarehr.PMmodule.service.ProviderManager;
import org.oscarehr.util.SpringUtils;

import com.quatro.service.*;
import com.quatro.model.*;

public final class FacilitySelectionAction extends DispatchAction {
    private static final Logger _logger = Logger.getLogger(LoginAction.class);
    private static final String LOG_PRE = "Login!@#$: ";

    private ProviderManager providerManager = (ProviderManager) SpringUtils.getBean("providerManager");
    private LookupManager lookupManager = (LookupManager) SpringUtils.getBean("lookupManager");

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  
    	Provider provider=(Provider)request.getSession().getAttribute("provider");
    	List<Integer> facilityIds=ProviderDao.getFacilityIds(provider.getProvider_no());
    	ArrayList<LookupCodeValue> facilities = new ArrayList<LookupCodeValue>();
    	for(int fid:facilityIds)
    	{
    		LookupCodeValue f = lookupManager.GetLookupCode("FAC", String.valueOf(fid));
    		facilities.add(f);
    	}
    	request.setAttribute("facilities", facilities);
    	return mapping.findForward("facilitySelection");
    }
}