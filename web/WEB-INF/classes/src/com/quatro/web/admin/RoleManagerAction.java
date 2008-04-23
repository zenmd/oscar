
package com.quatro.web.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.oscarehr.PMmodule.service.LogManager;
import org.oscarehr.PMmodule.web.BaseAction;

import com.quatro.model.LookupCodeValue;
import com.quatro.model.LookupTableDefValue;
import com.quatro.model.Secrole;
import com.quatro.service.LookupManager;
import com.quatro.service.RolesManager;

public class RoleManagerAction extends BaseAction {

	private LogManager logManager;
	private RolesManager rolesManager;
	private LookupManager lookupManager;

	public void setLookupManager(LookupManager lookupManager) {
		this.lookupManager = lookupManager;
	}

	public void setLogManager(LogManager mgr) {
		this.logManager = mgr;
	}

	public void setRolesManager(RolesManager rolesManager) {
		this.rolesManager = rolesManager;
	}

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		List<Secrole> list = null;
		list = rolesManager.getRoles();

		request.setAttribute("roles", list);
		logManager.log("read", "full roles list", "", request);

		return mapping.findForward("list");

	}
	
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("=========== EDIT ========= in RoleManagerAction");
		
        DynaActionForm secroleForm = (DynaActionForm) form;

        String roleNo = request.getParameter("roleNo");

        if (isCancelled(request)) {
            return list(mapping, form, request, response);
        }

        if (roleNo != null) {
            Secrole secrole = rolesManager.getRole(roleNo);

            if (secrole == null) {
                ActionMessages messages = new ActionMessages();
                messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("role.missing"));
                saveMessages(request, messages);

                return list(mapping, form, request, response);
            }

            secroleForm.set("roleNo", secrole.getRoleNo());
            secroleForm.set("roleName", secrole.getRoleName());
            secroleForm.set("description", secrole.getDescription());
            request.setAttribute("secroleForEdit",secrole);

         }

        
        return mapping.findForward("edit");

	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("=========== SAVE ========= in RoleManagerAction");
		
		DynaActionForm secroleForm = (DynaActionForm) form;
		
		Secrole secrole = new Secrole();
		secrole.setRoleNo((Long)secroleForm.get("roleNo"));
		secrole.setRoleName((String)secroleForm.get("roleName"));
		secrole.setDescription((String)secroleForm.get("description"));
		rolesManager.save(secrole);
		 
		LookupCodeValue functions = new LookupCodeValue();//lookupManager.GetLookupCode("LNG", obj.getLanguage());
		secroleForm.set("functions", functions);
        
		return mapping.findForward("functions");

	}
	
	public ActionForward preNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("=========== preNew ========= in RoleManagerAction");
				        
		return mapping.findForward("preNew");

	}
	


	

}