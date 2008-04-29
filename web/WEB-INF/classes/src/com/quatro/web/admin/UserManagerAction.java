package com.quatro.web.admin;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

import com.quatro.model.DataViews;
import com.quatro.model.LookupCodeValue;
import com.quatro.model.security.Secobjprivilege;
import com.quatro.model.security.Secrole;
import com.quatro.model.security.Secuserrole;
import com.quatro.service.security.RolesManager;
import com.quatro.service.security.UsersManager;

public class UserManagerAction extends BaseAction {

	private LogManager logManager;

	private RolesManager rolesManager;

	private UsersManager usersManager;

	public void setLogManager(LogManager mgr) {
		this.logManager = mgr;
	}

	public void setRolesManager(RolesManager rolesManager) {
		this.rolesManager = rolesManager;
	}

	public void setUsersManager(UsersManager usersManager) {
		this.usersManager = usersManager;
	}

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return list(mapping, form, request, response);
	}
	
	public ActionForward profile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String providerNo = request.getParameter("providerNo");
		
		ArrayList<Secuserrole> profilelist = new ArrayList<Secuserrole>();

		List list = usersManager.getProfile(providerNo);//sur.providerNo, sur.roleName, sur.orgcd
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] tmp = (Object[]) list.get(i);

				Secuserrole sur = new Secuserrole();
				if (tmp != null ) {
					sur.setProviderNo((String) tmp[0]);
					sur.setRoleName((String) tmp[1]);
					sur.setOrgcd((String) tmp[2]);
					sur.setUserName((String) tmp[3]);
					profilelist.add(sur);
				}

			}
		}


		request.setAttribute("profilelist", profilelist);
		logManager.log("read", "full secuserroles list", "", request);

		return mapping.findForward("profile");

	}
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ArrayList<Secuserrole> surlist = new ArrayList<Secuserrole>();

		List userlist = usersManager.getUsers();
		Hashtable<String, Secuserrole> ht = new Hashtable<String, Secuserrole>();
		if (userlist != null && userlist.size() > 0) {
			for (int i = 0; i < userlist.size(); i++) {
				Object[] tmp = (Object[]) userlist.get(i);

				Secuserrole sur = new Secuserrole();
				sur.setId((Long) tmp[0]);
				sur.setUserName((String) tmp[1]);
				sur.setFullName((String) tmp[2] + ", " + (String) tmp[3]);
				sur.setProviderNo((String) tmp[4]);

				//ht.put((String) tmp[4], sur);
				surlist.add(sur);
			}
			
		}
			
			/*
			List rolelist = usersManager.getSecuserroles();
			if (rolelist != null && rolelist.size() > 0) {
				for (int i = 0; i < rolelist.size(); i++) {
					Object[] tmp = (Object[]) rolelist.get(i);

					Secuserrole sur = ht.get((String) tmp[0]);
					if (sur != null) {
						String roleNames = sur.getRoleName();
						if (roleNames == null || roleNames.length() == 0)
							sur.setRoleName((String) tmp[1]);
						else
							sur.setRoleName(roleNames + ", " + (String) tmp[1]);

						sur.setOrgcd((String) tmp[2]);
					}

				}
			}

		}

		Iterator it = ht.values().iterator();
		while (it.hasNext())
			surlist.add((Secuserrole) it.next());
*/
		request.setAttribute("secuserroles", surlist);
		logManager.log("read", "full secuserroles list", "", request);

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
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"role.missing"));
				saveMessages(request, messages);

				return list(mapping, form, request, response);
			}

			secroleForm.set("roleNo", secrole.getRoleNo());
			secroleForm.set("roleName", secrole.getRoleName());
			secroleForm.set("description", secrole.getDescription());
			request.setAttribute("secroleForEdit", secrole);

		}

		return mapping.findForward("edit");

	}

	public ActionForward saveNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ActionMessages messages = new ActionMessages();
		boolean isError = false;
		boolean isWarning = false;

		DynaActionForm secroleForm = (DynaActionForm) form;

		Secrole secrole = new Secrole();
		secrole.setRoleNo((Long) secroleForm.get("roleNo"));
		String roleName = (String) secroleForm.get("roleName");
		secrole.setRoleName(roleName);
		secrole.setDescription((String) secroleForm.get("description"));

		// check rolename, should be unique
		Secrole existRole = rolesManager.getRoleByRolename(roleName);

		if (existRole == null) {
			rolesManager.save(secrole);

			secroleForm.set("roleNo", secrole.getRoleNo());

			LookupCodeValue functions = new LookupCodeValue();
			secroleForm.set("functions", functions);

			messages
					.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"message.role.created", request.getContextPath(),
							roleName));
			saveMessages(request, messages);

			return addFunction(mapping, form, request, response);// mapping.findForward("functions");
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.role.exist", request.getContextPath(), roleName));
			saveMessages(request, messages);

			return mapping.findForward("preNew");

		}

	}

	public ActionForward saveChange(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		System.out
				.println("=========== SAVE Change ========= in RoleManagerAction");

		DynaActionForm secroleForm = (DynaActionForm) form;

		Secrole secrole = new Secrole();
		secrole.setRoleNo((Long) secroleForm.get("roleNo"));
		String roleName = (String) secroleForm.get("roleName");
		secrole.setRoleName(roleName);
		secrole.setDescription((String) secroleForm.get("description"));

		rolesManager.save(secrole);

		secroleForm.set("roleNo", secrole.getRoleNo());

		LookupCodeValue functions = new LookupCodeValue();
		secroleForm.set("functions", functions);

		return list(mapping, form, request, response);

	}

	public ActionForward preNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		System.out.println("=========== preNew ========= in RoleManagerAction");

		return mapping.findForward("preNew");

	}

	public ActionForward addFunction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		System.out
				.println("=========== addFunction ========= in RoleManagerAction");
		DynaActionForm secroleForm = (DynaActionForm) form;
		ChangeFunLstTable(2, secroleForm, request);

		return mapping.findForward("functions");

	}

	public ActionForward removeFunction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		System.out
				.println("=========== removeFunction ========= in RoleManagerAction");
		DynaActionForm secroleForm = (DynaActionForm) form;
		ChangeFunLstTable(1, secroleForm, request);

		return mapping.findForward("functions");

	}

	public void ChangeFunLstTable(int operationType, DynaActionForm myForm,
			HttpServletRequest request) {

		ActionMessages messages = new ActionMessages();

		ArrayList<Secobjprivilege> secobjprivilegeLst = new ArrayList<Secobjprivilege>();

		ArrayList funLst = new ArrayList();
		if (request.getSession().getAttribute(DataViews.REPORT_CRI) != null)
			funLst = (ArrayList) request.getSession().getAttribute(
					DataViews.REPORT_CRI);

		Map map = request.getParameterMap();
		String[] arr_lineno = (String[]) map.get("lineno");
		int lineno = 0;
		if (arr_lineno != null)
			lineno = arr_lineno.length;

		switch (operationType) {
		case 1: // remove
			for (int i = 0; i < lineno; i++) {
				String[] isChecked = (String[]) map.get("p" + i);

				if (isChecked == null) {
					Secobjprivilege objNew = (Secobjprivilege) funLst.get(i);

					String[] accessType_code = (String[]) map
							.get("accessTypes_code" + i);
					String[] accessType_description = (String[]) map
							.get("accessTypes_description" + i);
					String[] function_code = (String[]) map.get("function_code"
							+ i);
					String[] function_description = (String[]) map
							.get("function_description" + i);

					if (accessType_code != null)
						objNew.setPrivilege_code(accessType_code[0]);
					if (accessType_description != null)
						objNew.setPrivilege(accessType_description[0]);
					if (function_code != null)
						objNew.setObjectname_code(function_code[0]);
					if (function_description != null)
						objNew.setObjectname_desc(function_description[0]);

					secobjprivilegeLst.add(objNew);

				}

			}
			break;
		case 2: // add
			for (int i = 0; i < lineno; i++) {
				Secobjprivilege objNew = (Secobjprivilege) funLst.get(i);

				String[] accessType_code = (String[]) map
						.get("accessTypes_code" + i);
				String[] accessType_description = (String[]) map
						.get("accessTypes_description" + i);
				String[] function_code = (String[]) map
						.get("function_code" + i);
				String[] function_description = (String[]) map
						.get("function_description" + i);

				if (accessType_code != null)
					objNew.setPrivilege_code(accessType_code[0]);
				if (accessType_description != null)
					objNew.setPrivilege(accessType_description[0]);
				if (function_code != null)
					objNew.setObjectname_code(function_code[0]);
				if (function_description != null)
					objNew.setObjectname_desc(function_description[0]);

				secobjprivilegeLst.add(objNew);
			}
			Secobjprivilege objNew2 = new Secobjprivilege();
			secobjprivilegeLst.add(objNew2);
			break;

		}
		myForm.set("secobjprivilegeLst", secobjprivilegeLst);
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"message.role.function.add", request.getContextPath()));
		saveMessages(request, messages);

		request.getSession().setAttribute(DataViews.REPORT_CRI,
				secobjprivilegeLst);

	}

	public ActionForward saveFunction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		System.out
				.println("=========== saveFunction ========= in RoleManagerAction");
		DynaActionForm secroleForm = (DynaActionForm) form;

		String roleName = (String) secroleForm.get("roleName");
		String providerNo = (String) request.getSession().getAttribute("user");
		ArrayList funLst = new ArrayList();
		if (request.getSession().getAttribute(DataViews.REPORT_CRI) != null)
			funLst = (ArrayList) request.getSession().getAttribute(
					DataViews.REPORT_CRI);

		Map map = request.getParameterMap();
		String[] arr_lineno = (String[]) map.get("lineno");
		int lineno = 0;
		if (arr_lineno != null)
			lineno = arr_lineno.length;

		for (int i = 0; i < lineno; i++) {
			String[] function_code = (String[]) map.get("function_code" + i);
			if (function_code != null && function_code[0].length() > 0) {
				Secobjprivilege objNew = (Secobjprivilege) funLst.get(i);
				objNew.setObjectname(function_code[0]);
				objNew.setRoleusergroup(roleName);

				String[] accessType_code = (String[]) map
						.get("accessTypes_code" + i);
				if (accessType_code != null)
					objNew.setPrivilege(accessType_code[0]);

				objNew.setProviderNo(providerNo);
				objNew.setPriority(new Long("0"));

				rolesManager.saveFunction(objNew);
			}
		}

		return list(mapping, form, request, response);

	}

}