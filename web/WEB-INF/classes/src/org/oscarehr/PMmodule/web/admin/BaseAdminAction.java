package org.oscarehr.PMmodule.web.admin;

import javax.servlet.http.HttpServletRequest;
import org.oscarehr.PMmodule.web.BaseAction;

import com.quatro.common.KeyConstants;
import com.quatro.model.security.NoAccessException;
import com.quatro.service.security.SecurityManager;

public class BaseAdminAction extends BaseAction {    
	protected String getAccess(HttpServletRequest request,String functionName) throws NoAccessException
	{
		SecurityManager sec = super.getSecurityManager(request);
		String acc = sec.GetAccess(functionName, "");
		if (acc.equals(KeyConstants.ACCESS_NONE)) throw new NoAccessException();
		return acc;
	}
}
