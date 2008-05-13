package com.quatro.web.admin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
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

import com.quatro.model.security.SecProvider;
import com.quatro.model.security.Security;
import com.quatro.model.security.Secuserrole;
import com.quatro.service.ORGManager;
import com.quatro.service.security.RolesManager;
import com.quatro.service.security.UsersManager;

public class UserManagerAction extends BaseAction {

	private LogManager logManager;

	private RolesManager rolesManager;

	private UsersManager usersManager;
	private ORGManager orgManager;

	private MessageDigest md;

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
		org.apache.struts.validator.DynaValidatorForm secForm = (org.apache.struts.validator.DynaValidatorForm) form;
		ArrayList<Secuserrole> profilelist = new ArrayList<Secuserrole>();
		
		DynaActionForm secuserForm = (DynaActionForm) form;
		
		SecProvider provider = usersManager
			.getProviderByProviderNo(providerNo);

		if (provider != null) {
			secuserForm.set("providerNo", providerNo);
			secuserForm.set("firstName", provider.getFirstName());
			secuserForm.set("lastName", provider.getLastName());
			secuserForm.set("init", provider.getInit());
			secuserForm.set("title", provider.getTitle());
			secuserForm.set("jobTitle", provider.getJobTitle());
			secuserForm.set("email", provider.getEmail());
		}
		
		Security user;
		List ulist = usersManager.getUserByProviderNo(providerNo);
		if (ulist != null && ulist.size() > 0) {
			user = (Security) ulist.get(0);

			secuserForm.set("securityNo", user.getSecurityNo());
			secuserForm.set("userName", user.getUserName());

			request.setAttribute("userForEdit", user);
		}
		
		List list = usersManager.getProfile(providerNo);

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] tmp = (Object[]) list.get(i);

				Secuserrole sur = new Secuserrole();
				if (tmp != null) {
					sur.setProviderNo((String) tmp[0]);
					sur.setRoleName((String) tmp[1]);
					sur.setOrgcd_desc((String) tmp[2]);
					sur.setUserName((String) tmp[3]);
					profilelist.add(sur);
				}

			}
		}

		request.setAttribute("profilelist", profilelist);
		secForm.set("secUserRoleLst", profilelist);
		logManager.log("read", "full secuserroles list", "", request);

		return mapping.findForward("addRoles");

	}


	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		return mapping.findForward("list");

	}
	

	public ActionForward preNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		System.out.println("=========== preNew ========= in UserManagerAction");

		return mapping.findForward("edit");

	}

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		System.out.println("=========== EDIT ========= in UserManagerAction");

		DynaActionForm secuserForm = (DynaActionForm) form;
		String providerNo = request.getParameter("providerNo");

		if (isCancelled(request)) {
			return list(mapping, form, request, response);
		}

		if (providerNo != null) {

			SecProvider provider = usersManager
					.getProviderByProviderNo(providerNo);

			if (provider != null) {
				secuserForm.set("providerNo", providerNo);
				secuserForm.set("firstName", provider.getFirstName());
				secuserForm.set("lastName", provider.getLastName());
				secuserForm.set("init", provider.getInit());
				secuserForm.set("title", provider.getTitle());
				secuserForm.set("jobTitle", provider.getJobTitle());
				secuserForm.set("email", provider.getEmail());

				String isChecked = provider.getStatus();
				if (isChecked != null)
					secuserForm.set("status", "on");

				request.setAttribute("userForEdit", provider);

				Security user;
				List list = usersManager.getUserByProviderNo(providerNo);
				if (list != null && list.size() > 0) {
					user = (Security) list.get(0);

					secuserForm.set("securityNo", user.getSecurityNo());
					secuserForm.set("userName", user.getUserName());

					request.setAttribute("userForEdit", user);
				}
			}

		} else {
			return list(mapping, form, request, response);
		}

		return mapping.findForward("edit");

	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("=========== save ========= in UserManagerAction");
		ActionMessages messages = new ActionMessages();

		DynaActionForm secuserForm = (DynaActionForm) form;

		SecProvider provider = null;
		Security user = null;
		String providerNo = (String) secuserForm.get("providerNo");
		if (providerNo != null && providerNo.length() > 0) {
			provider = usersManager.getProviderByProviderNo(providerNo);
			List userList = usersManager.getUserByProviderNo(providerNo);
			if (userList != null && userList.size() > 0)
				user = (Security) userList.get(0);
		} else
			provider = new SecProvider();

		provider.setFirstName((String) secuserForm.get("firstName"));
		provider.setLastName((String) secuserForm.get("lastName"));
		provider.setInit((String) secuserForm.get("init"));
		provider.setTitle((String) secuserForm.get("title"));
		provider.setJobTitle((String) secuserForm.get("jobTitle"));
		provider.setEmail((String) secuserForm.get("email"));

		Map map = request.getParameterMap();
		String[] isChecked = (String[]) map.get("status");
		if (isChecked != null)
			provider.setStatus("1");

		if (user == null) {
			user = new Security();
			user.setBLocallockset(new Long(1));
			user.setBRemotelockset(new Long(1));
			user.setBExpireset(new Long(1));
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				java.util.Date aDate = sdf.parse("01/01/2999");
				user.setDateExpiredate(aDate);
			} catch (Exception e) {

			}

		}
		user.setUserName((String) secuserForm.get("userName"));

		String password = (String) secuserForm.get("password");
		String cpass = (String) secuserForm.get("confirmPassword");
		String pin = (String) secuserForm.get("pin");
		String cpin = (String) secuserForm.get("confirmPin");

		if (password.equals(cpass) && pin.equals(cpin)) {
			if (password != null && password.length() > 0) {
				StringBuffer sbTemp = new StringBuffer();
				byte[] pwd = password.getBytes();
				try {
					md = MessageDigest.getInstance("SHA");
				} catch (NoSuchAlgorithmException foo) {
					logManager.log("new user",
							"NoSuchAlgorithmException - SHA", "", request);
				}

				byte[] btTypeInPasswd = md.digest(pwd);
				for (int i = 0; i < btTypeInPasswd.length; i++)
					sbTemp = sbTemp.append(btTypeInPasswd[i]);
				password = sbTemp.toString();
				user.setPassword(password);
			}
			if (pin != null && pin.length() > 0)
				user.setPin(oscar.Misc.encryptPIN(pin));
		} else {
			messages
					.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
							"error.newuser.passwordNotMatch", request
									.getContextPath()));
			saveMessages(request, messages);
			return mapping.findForward("edit");
		}

		try {
			usersManager.save(provider, user);
		} catch (Exception e) {
			String msg = e.getMessage();
			int i = msg
					.indexOf("org.hibernate.exception.ConstraintViolationException");// unique constraint
			if (i >= 0) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"error.user.exist", request.getContextPath(), user
								.getUserName()));
				saveMessages(request, messages);
			} else {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"error.newuser.failed", request.getContextPath()));
				saveMessages(request, messages);

			}
			return mapping.findForward("edit");
		}

		//return addRole(mapping, form, request, response);
		return mapping.findForward("edit");
	}

	public ActionForward addRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		System.out
				.println("=========== addRole ========= in UserManagerAction");
		
		DynaActionForm secuserForm = (DynaActionForm) form;
		String providerNo = request.getParameter("providerNo");

		if (isCancelled(request)) {
			return list(mapping, form, request, response);
		}

	

		SecProvider provider = usersManager
				.getProviderByProviderNo(providerNo);

		if (provider != null) {
			secuserForm.set("providerNo", providerNo);
			secuserForm.set("firstName", provider.getFirstName());
			secuserForm.set("lastName", provider.getLastName());
			secuserForm.set("init", provider.getInit());
			secuserForm.set("title", provider.getTitle());
			secuserForm.set("jobTitle", provider.getJobTitle());
			secuserForm.set("email", provider.getEmail());

			String isChecked = provider.getStatus();
			if (isChecked != null)
				secuserForm.set("status", "on");

			
			Security user;
			List list = usersManager.getUserByProviderNo(providerNo);
			if (list != null && list.size() > 0) {
				user = (Security) list.get(0);

				secuserForm.set("securityNo", user.getSecurityNo());
				secuserForm.set("userName", user.getUserName());

				request.setAttribute("userForEdit", user);
			}
		}

		
		changeRoleLstTable(2, secuserForm, request);

		return mapping.findForward("addRoles");

	}

	public ActionForward removeRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		System.out
				.println("=========== removeRole ========= in UserManagerAction");
		DynaActionForm secroleForm = (DynaActionForm) form;
		changeRoleLstTable(1, secroleForm, request);

		return mapping.findForward("addRoles");

	}

	public void changeRoleLstTable(int operationType, DynaActionForm myForm,
			HttpServletRequest request) {
		
		ActionMessages messages = new ActionMessages();
		
		ArrayList<Secuserrole> secUserRoleLst = new ArrayList<Secuserrole>();

		
		switch (operationType) {
		
		case 1: // remove
			secUserRoleLst = (ArrayList)getRowList(request, myForm, 1);
			break;
			
		case 2: // add
			secUserRoleLst = (ArrayList)getRowList(request, myForm, 2);
			
			Secuserrole objNew2 = new Secuserrole();
			
			secUserRoleLst.add(objNew2);
			
			break;

		}
		myForm.set("secUserRoleLst", secUserRoleLst);
		
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.user.addRole",
       			request.getContextPath()));
		saveMessages(request,messages);
		
		request.getSession().setAttribute("secUserRoleLst",	secUserRoleLst);

	}

	public List getRowList(HttpServletRequest request, ActionForm form, int operationType){
		ArrayList<Secuserrole> secUserRoleLst = new ArrayList<Secuserrole>();
		
		DynaActionForm secuserForm = (DynaActionForm) form;
		String providerNo = (String) secuserForm.get("providerNo");

		Map map = request.getParameterMap();
		String[] arr_lineno = (String[]) map.get("lineno");
		int lineno = 0;
		if (arr_lineno != null)
			lineno = arr_lineno.length;
		
		for (int i = 0; i < lineno; i++) {
			
			String[] isChecked = (String[]) map.get("p" + i);
			if ((operationType == 1 && isChecked == null) || operationType != 1) {

				Secuserrole objNew = new Secuserrole();
				
				String[] org_code = (String[]) map
						.get("org_code" + i);
				String[] org_description = (String[]) map
						.get("org_description" + i);
				String[] role_code = (String[]) map
						.get("role_code" + i);
				String[] role_description = (String[]) map
						.get("role_description" + i);
		
				if (org_code != null)
					objNew.setOrgcd(org_code[0]);
				if (org_description != null)
					objNew.setOrgcd_desc(org_description[0]);
				if (role_code != null)
					objNew.setRoleName(role_code[0]);
				if (role_description != null)
					objNew.setRoleName_desc(role_description[0]);
				
				objNew.setProviderNo(providerNo);
				objNew.setActiveyn(new Long("1"));
		
				secUserRoleLst.add(objNew);
			}
			
		}
		return secUserRoleLst;
	}
	
	public ActionForward saveRoles(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		System.out.println("=========== saveRoles ========= in UserManagerAction");
		
		List secUserRoleLst = getRowList(request, form, 0);
		ArrayList<Secuserrole> LstforSave = new ArrayList<Secuserrole>();
		
		Iterator it = secUserRoleLst.iterator();
		while(it.hasNext()){
			Secuserrole tmp = (Secuserrole)it.next();
			if(tmp.getOrgcd() != null && tmp.getOrgcd().length() > 0 && tmp.getRoleName() != null && tmp.getRoleName().length() > 0)
				LstforSave.add(tmp);
			
		}
		
		usersManager.saveRolesToUser(LstforSave);
		
		return profile(mapping,form,request,response);

	}

	public void setOrgManager(ORGManager orgManager) {
		this.orgManager = orgManager;
	}

}