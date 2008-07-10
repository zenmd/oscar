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

import oscar.MyDateFormat;

import com.quatro.common.KeyConstants;
import com.quatro.model.FieldDefValue;
import com.quatro.model.LookupTableDefValue;
import com.quatro.service.LookupManager;
import com.quatro.util.Utility;

public class LookupCodeEditAction extends DispatchAction {
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
		LookupCodeEditForm qform = (LookupCodeEditForm) form;
		
		qform.setTableDef(tableDef);
		qform.setCodeFields(codeFields);
		qform.setNewCode(isNew);
		qform.setErrMsg("");
		return mapping.findForward("edit");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		com.quatro.web.lookup.LookupCodeEditForm qform = (com.quatro.web.lookup.LookupCodeEditForm) form;
		LookupTableDefValue tableDef = qform.getTableDef();
		List fieldDefList = qform.getCodeFields();
		boolean isNew = qform.isNewCode();
		String providerNo = (String) request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
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