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
import org.oscarehr.PMmodule.model.Facility;
import org.oscarehr.util.SpringUtils;

import com.quatro.common.KeyConstants;

public final class FacilitySelectionAction extends DispatchAction {

    private ProviderManager providerManager = (ProviderManager) SpringUtils.getBean("providerManager");

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  
    	String providerNo=(String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
    	List<Facility> facilities=providerManager.getFacilitiesInProgramDomain(providerNo);
    	request.setAttribute("facilities", facilities);
    	return mapping.findForward("facilitySelection");
    }
}