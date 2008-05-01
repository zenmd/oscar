package com.quatro.web.admin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Hashtable;
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
import com.quatro.service.security.RolesManager;
import com.quatro.service.security.UsersManager;

public class UserManagerAction extends BaseAction {

	private LogManager logManager;

	private RolesManager rolesManager;

	private UsersManager usersManager;
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

		ArrayList<Secuserrole> profilelist = new ArrayList<Secuserrole>();

		List list = usersManager.getProfile(providerNo);
		
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] tmp = (Object[]) list.get(i);

				Secuserrole sur = new Secuserrole();
				if (tmp != null) {
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

				surlist.add(sur);
			}

		}

		request.setAttribute("secuserroles", surlist);
		logManager.log("read", "full secuserroles list", "", request);

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
		System.out
				.println("=========== save ========= in UserManagerAction");
		ActionMessages messages = new ActionMessages();

		DynaActionForm secuserForm = (DynaActionForm) form;

		SecProvider provider = null;
		Security user = null;
		String providerNo = (String) secuserForm.get("providerNo");
		if(providerNo != null && providerNo.length() > 0){
			provider = usersManager.getProviderByProviderNo(providerNo);
			List userList = usersManager.getUserByProviderNo(providerNo);
			if(userList != null && userList.size()>0)
				user = (Security)userList.get(0);
		}else
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

		if(user == null)
			user = new Security();
		
		user.setUserName((String) secuserForm.get("userName"));
		
		String password = (String) secuserForm.get("password");
		String cpass = (String) secuserForm.get("confirmPassword");
		String pin = (String) secuserForm.get("pin");
		String cpin = (String) secuserForm.get("confirmPin"); 
		
		if(password.equals(cpass) && pin.equals(cpin)){
			if(password != null && password.length()>0){
				StringBuffer sbTemp = new StringBuffer();
				byte[] pwd = password.getBytes();
				try {
		            md = MessageDigest.getInstance("SHA"); 
		        } catch (NoSuchAlgorithmException foo) {
		        	logManager.log("new user", "NoSuchAlgorithmException - SHA", "", request);
		        }
		        
		        byte[] btTypeInPasswd = md.digest(pwd);
		        for (int i = 0; i < btTypeInPasswd.length; i++)
		            sbTemp = sbTemp.append(btTypeInPasswd[i]);
		        password = sbTemp.toString();
				user.setPassword(password);
			}	
			if(pin != null && pin.length() >0)
				user.setPin(oscar.Misc.encryptPIN(pin));
		}else{
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.newuser.passwordNotMatch", request.getContextPath()));
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

		//return mapping.findForward("list");
		return list(mapping, form, request, response);
	}

	

}