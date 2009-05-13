/*******************************************************************************
 * Copyright (c) 2008, 2009 Quatro Group Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU General Public License
 * which accompanies this distribution, and is available at
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 * Contributors:
 *     <Quatro Group Software Systems inc.>  <OSCAR Team>
 *******************************************************************************/
package org.oscarehr.PMmodule.web;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;
import org.oscarehr.PMmodule.model.HealthSafety;
import org.oscarehr.PMmodule.model.Provider;
import org.oscarehr.PMmodule.service.HealthSafetyManager;
import com.quatro.service.IntakeManager;
import com.quatro.common.KeyConstants;
import com.quatro.model.security.NoAccessException;

public class HealthSafetyAction extends BaseClientAction {
	private static Log log = LogFactory.getLog(HealthSafetyAction.class);

    private HealthSafetyManager healthSafetyManager=null;
    private IntakeManager intakeManager = null;
    
	public void setHealthSafetyManager(HealthSafetyManager healthSafetyManager) {
		this.healthSafetyManager = healthSafetyManager;
	}
	public void setIntakeManager(IntakeManager intakeManager) {
		this.intakeManager = intakeManager;
	}
	public IntakeManager getIntakeManager() {
		return this.intakeManager;
	}

	
	public ActionForward unspecified(ActionMapping mapping,	ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		logManager.log("read","full provider list","",request);
//		log.warn("Program doesn't have a name?");
		return form(mapping,form,request,response);
	}
	
	
	public ActionForward form(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws NoAccessException {
		
		DynaActionForm healthSafetyForm = (DynaActionForm)form;
		
		String id = request.getParameter("clientId");
				
		HealthSafety healthsafety = healthSafetyManager.getHealthSafetyByDemographic(Integer.valueOf(id));
		if(healthsafety != null) {
			super.getAccess(request, KeyConstants.FUN_CLIENTHEALTHSAFETY,null, KeyConstants.ACCESS_UPDATE);
			healthSafetyForm.set("healthsafety", healthsafety);
            request.setAttribute("healthsafety", healthsafety);
		}else{
			super.getAccess(request, KeyConstants.FUN_CLIENTHEALTHSAFETY,null,KeyConstants.ACCESS_WRITE);
			healthsafety= new HealthSafety();
			Provider provider=(Provider) request.getSession().getAttribute("provider");
			healthsafety.setUserName(provider.getFormattedName());
			healthsafety.setDemographicNo(Integer.valueOf(id));
			healthSafetyForm.set("healthsafety", healthsafety);
            request.setAttribute("healthsafety", healthsafety);
		}
		request.setAttribute("notoken", "Y");
		
		return mapping.findForward("edit");
	}
	
	
	public ActionForward savehealthSafety(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws NoAccessException {
		
		log.debug("Saving health and Safety");
		DynaActionForm healthSafetyForm = (DynaActionForm)form;
		

		HealthSafety healthsafety= (HealthSafety)healthSafetyForm.get("healthsafety");
		if (healthsafety.getId() != null && healthsafety.getId().intValue() > 0)
			super.getAccess(request, KeyConstants.FUN_CLIENTHEALTHSAFETY, null,KeyConstants.ACCESS_UPDATE);
		else
			super.getAccess(request, KeyConstants.FUN_CLIENTHEALTHSAFETY, null,KeyConstants.ACCESS_WRITE);

		healthsafety.setUpdateDate(new Timestamp(System.currentTimeMillis()));

		healthSafetyManager.saveHealthSafetyByDemographic(healthsafety);	
		request.setAttribute("notoken", "Y");
		
		return mapping.findForward("success");
	}

}
