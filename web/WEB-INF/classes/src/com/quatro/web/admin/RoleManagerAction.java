
package com.quatro.web.admin;

import java.util.ArrayList;
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
import com.quatro.model.ReportFilterValue;
import com.quatro.model.ReportTempCriValue;
import com.quatro.model.ReportValue;
import com.quatro.model.Secrole;
import com.quatro.model.security.Secobjprivilege;
import com.quatro.model.security.SecobjprivilegeId;
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
		
		secroleForm.set("roleNo", secrole.getRoleNo());
        		
		LookupCodeValue functions = new LookupCodeValue();
		secroleForm.set("functions", functions);
        
		return mapping.findForward("functions");

	}
		
	public ActionForward preNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("=========== preNew ========= in RoleManagerAction");
				        
		return mapping.findForward("preNew");

	}
	

	public ActionForward addFunction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("=========== addFunction ========= in RoleManagerAction");
		DynaActionForm secroleForm = (DynaActionForm) form;
		ChangeFunLstTable(2, secroleForm, request);
		
		return mapping.findForward("functions");

	}
	
    public void ChangeFunLstTable(int operationType, DynaActionForm myForm, HttpServletRequest request)
    {	
    	ArrayList<Secobjprivilege> obj= new ArrayList<Secobjprivilege>();
    	
    	ArrayList funs = new ArrayList();
		if(request.getSession().getAttribute(DataViews.REPORT_CRI)!=null)
			 funs = (ArrayList) request.getSession().getAttribute(DataViews.REPORT_CRI);

		Map map=request.getParameterMap();
		String[] obj2= (String[])map.get("lineno");
		int lineno=0;
		if(obj2!=null) lineno=obj2.length;
		
				
		switch(operationType)
		{
		  case 1:  //remove
		    for(int i=0;i<lineno;i++)
		    {
	         /* if(map.get("p" + i)!=null) continue;
	          ReportTempCriValue criNew=(ReportTempCriValue)cris.get(i);
	          String[] arRelation=(String[])map.get("tplCriteria[" + i + "].relation");
	          String[] arFieldNo=(String[])map.get("tplCriteria[" + i + "].fieldNo");
	          String[] arOp=(String[])map.get("tplCriteria[" + i + "].op");
	          String[] arVal=(String[])map.get("tplCriteria[" + i + "].val");
	          String[] arValDesc=(String[])map.get("tplCriteria[" + i + "].valdesc");
	          String[] arFieldType=(String[])map.get("tplCriteria[" + i + "].filter.fieldType");
	          String[] arLookupTable=(String[])map.get("tplCriteria[" + i + "].filter.lookupTable");
		      if(arRelation!=null) criNew.setRelation(arRelation[0]);
		      if(arOp!=null) criNew.setOp(arOp[0]);
		      if(arVal!=null) criNew.setVal(arVal[0]);
		      if(arValDesc!=null) criNew.setValDesc(arValDesc[0]);
	          if(arFieldNo!=null){
		  		int iFieldNo = Integer.parseInt(arFieldNo[0]);
				ReportFilterValue filter = new ReportFilterValue();
			    filter.setFieldNo(iFieldNo);
			    if(arFieldType!=null) filter.setFieldType(arFieldType[0]);
			    if(arLookupTable!=null) filter.setLookupTable(arLookupTable[0]);
		        criNew.setFilter(filter);
		      }  
	          obj.add(criNew);
	          */
		    }
		    break;
		  case 2:  //add
			for(int i=0;i<lineno;i++)
			{
				Secobjprivilege objNew=(Secobjprivilege)funs.get(i);
		      String[] accessType=(String[])map.get("tplCriteria[" + i + "].relation");
	          String[] function=(String[])map.get("tplCriteria[" + i + "].fieldNo");
		      
			  if(accessType!=null) objNew.setPrivilege(accessType[0]);
			  if(function!=null){
				  SecobjprivilegeId id = new SecobjprivilegeId();
				  id.setObjectname(function[0]);
				  id.setRoleusergroup((String)myForm.get("roleName"));
				  objNew.setId(id);
			  }
			  // providerNo ...
			  
		      obj.add(objNew);
			}
			Secobjprivilege objNew2 = new Secobjprivilege();
			obj.add(objNew2);
			break;
		  
		}
		myForm.set("functionsList", obj);
		
		request.getSession().setAttribute(DataViews.REPORT_CRI, obj);
		
		
    }

	

}