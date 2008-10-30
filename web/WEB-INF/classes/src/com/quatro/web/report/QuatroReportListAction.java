package com.quatro.web.report;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.oscarehr.PMmodule.web.*;
import org.oscarehr.PMmodule.web.admin.BaseAdminAction;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.quatro.model.security.NoAccessException;
import com.quatro.service.QuatroReportManager;
import com.quatro.util.Utility;
import com.quatro.common.KeyConstants;
public class QuatroReportListAction extends BaseAdminAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		return reportlist(mapping,form,request,response);
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws NoAccessException {
		super.getAccess(request, KeyConstants.FUN_REPORTS,KeyConstants.ACCESS_WRITE);
		QuatroReportListForm myForm = (QuatroReportListForm) form;
		String chkNo= myForm.getChkDel();
		if(Utility.IsEmpty(chkNo) ) return reportlist(mapping,form,request,response);
		
		QuatroReportManager reportManager = (QuatroReportManager)WebApplicationContextUtils.getWebApplicationContext(
        		getServlet().getServletContext()).getBean("quatroReportManager");
		
		chkNo = chkNo.substring(1);
		StringBuffer str = new StringBuffer();
		String[] sArray = chkNo.split(","); 
		for(int i=0;i<sArray.length;i++){
		  String param = (String)request.getParameter("p" + sArray[i]);
		  if(param!=null) str.append("," + param);
		}

		if(str.toString()!=null){
		  String templateNo= str.toString();
		  if("".equals(templateNo)==false){
			  templateNo = templateNo.substring(1);
			  reportManager.DeleteReportTemplates(templateNo);
		  }
		}  
		return reportlist(mapping,form,request,response);
	}
	
	public ActionForward reportlist(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String providerNo = (String)request.getSession(true).getAttribute("user");
			QuatroReportManager reportManager = (QuatroReportManager)WebApplicationContextUtils.getWebApplicationContext(
	        		getServlet().getServletContext()).getBean("quatroReportManager");
			com.quatro.service.security.SecurityManager sm = (com.quatro.service.security.SecurityManager) request.getSession().getAttribute(KeyConstants.SESSION_KEY_SECURITY_MANAGER);
			boolean hasXRights = (sm.GetAccess(KeyConstants.FUN_REPORTS).compareTo(KeyConstants.ACCESS_ALL)>=0);
			List reports = reportManager.GetReportGroupList(providerNo, hasXRights);
			QuatroReportListForm qform = (QuatroReportListForm) form;
			qform.setReportGroups(reports);
			qform.setProvider(providerNo);
			super.setMenu(request,KeyConstants.MENU_REPORT);
			return mapping.findForward("reportlist");
    	}
    	catch(NoAccessException e)
    	{
    		return mapping.findForward("failure");
    	}
	}
}