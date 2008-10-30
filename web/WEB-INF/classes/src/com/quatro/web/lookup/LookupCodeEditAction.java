package com.quatro.web.lookup;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.oscarehr.PMmodule.web.BaseAction;
import org.oscarehr.PMmodule.web.admin.BaseAdminAction;

import oscar.MyDateFormat;

import com.quatro.common.KeyConstants;
import com.quatro.model.FieldDefValue;
import com.quatro.model.LookupTableDefValue;
import com.quatro.model.security.NoAccessException;
import com.quatro.service.LookupManager;
import com.quatro.service.security.SecurityManager;
import com.quatro.util.Utility;

public class LookupCodeEditAction extends BaseAdminAction {
    private LookupManager lookupManager=null;

	public LookupManager getLookupManager() {
		return lookupManager;
	}

	public void setLookupManager(LookupManager lookupManager) {
		this.lookupManager = lookupManager;
	}
	
    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    		return loadCode(mapping,form,request,response);
	}
	
	public ActionForward loadCode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	try {
    		super.getAccess(request,KeyConstants.FUN_ADMIN_LOOKUP);
			String [] codeIds = request.getParameter("id").split(":");
	        String tableId = codeIds[0];
	        String code = "0";
	        boolean isNew = true;
	        if (codeIds.length > 1) {
	        	code = codeIds[1];
	        	isNew = false;
	        }
	
	        LookupTableDefValue tableDef = lookupManager.GetLookupTableDef(tableId);
	        
			List codeFields = lookupManager.GetCodeFieldValues(tableDef, code);
			boolean editable=false;
			for(int i=0;i<codeFields.size();i++){
				FieldDefValue fdv =(FieldDefValue)codeFields.get(i);
				if(fdv.isEditable()){
					editable=true;
					break;
				}
			}
			LookupCodeEditForm qform = (LookupCodeEditForm) form;
			
			qform.setTableDef(tableDef);
			qform.setCodeFields(codeFields);
			qform.setNewCode(isNew);
			qform.setErrMsg("");
			boolean isReadOnly =false;		
			SecurityManager sec = (SecurityManager) request.getSession()
			.getAttribute(KeyConstants.SESSION_KEY_SECURITY_MANAGER);	
			if (sec.GetAccess(KeyConstants.FUN_ADMIN_LOOKUP, null).compareTo(KeyConstants.ACCESS_READ) <= 0) 
				isReadOnly=true;
			if(!editable) isReadOnly = true;
			if(isReadOnly) request.setAttribute("isReadOnly", Boolean.valueOf(isReadOnly));
			return mapping.findForward("edit");
    	}
    	catch(NoAccessException e)
    	{
    		return mapping.findForward("failure");
    	}
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws NoAccessException 
	{
		super.getAccess(request,KeyConstants.FUN_ADMIN_LOOKUP,KeyConstants.ACCESS_UPDATE);
		com.quatro.web.lookup.LookupCodeEditForm qform = (com.quatro.web.lookup.LookupCodeEditForm) form;
		LookupTableDefValue tableDef = qform.getTableDef();
		List fieldDefList = qform.getCodeFields();
		boolean isNew = qform.isNewCode();
		String providerNo = (String) request.getSession(true).getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
		Map map=request.getParameterMap();
		String errMsg = "";
		for(int i=0; i<fieldDefList.size(); i++)
		{
			FieldDefValue fdv = (FieldDefValue) fieldDefList.get(i);
			if (fdv.getGenericIdx() == 8) {
				fdv.setVal(providerNo);
			}
			else if(fdv.getGenericIdx() == 9)
			{
				fdv.setVal(MyDateFormat.getStandardDateTime(Calendar.getInstance()));
			}
			else
			{	
				String [] val = (String[]) map.get("field[" + i + "].val");
				if (val != null) {
					fdv.setVal(val[0]);
					if("D".equals(fdv.getFieldType())) {
						if(!Utility.IsDate(fdv.getVal())) {
							if (!Utility.IsEmpty(errMsg)) errMsg += "<BR/>";
							errMsg += fdv.getFieldDesc() + "should be a Date";
						}
					}
					else if("N".equals(fdv.getFieldType()))
					{
						if (!(fdv.isAuto() && isNew)) {
							if(!Utility.IsInt(fdv.getVal()))
							{
								if (!Utility.IsEmpty(errMsg)) errMsg += "<BR/>";
								errMsg += fdv.getFieldDesc() + " should be an Integer number";
							}else if(!Utility.IsIntBiggerThanZero(fdv.getVal())){
								if (!Utility.IsEmpty(errMsg)) errMsg += "<BR/>";
								errMsg += fdv.getFieldDesc() + " should be greater than 0";
							}
						}
					}
				}
				else
				{
					fdv.setVal("");
				}
			}
		}
		if(!Utility.IsEmpty(errMsg)) 
		{
			qform.setErrMsg(errMsg);
			return mapping.findForward("edit");
		}
		try {
			String code = lookupManager.SaveCodeValue(isNew, tableDef, fieldDefList);
			fieldDefList = lookupManager.GetCodeFieldValues(tableDef, code);
			qform.setCodeFields(fieldDefList);
			qform.setNewCode(false);
			qform.setErrMsg("Saved Successfully");
			return mapping.findForward("edit");
		}
		catch(SQLException e)
		{
			qform.setErrMsg(e.getMessage());
			return mapping.findForward("edit");
		}
	}
}