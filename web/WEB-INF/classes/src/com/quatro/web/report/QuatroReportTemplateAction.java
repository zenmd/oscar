/*******************************************************************************
 * Copyright (c) 2005, 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU General Public License
 * which accompanies this distribution, and is available at
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 * Contributors:
 *     <Quatro Group Software Systems inc.>  <OSCAR Team>
 *******************************************************************************/
package com.quatro.web.report;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.oscarehr.PMmodule.web.BaseAction;

import com.quatro.model.DataViews;
import com.quatro.model.ReportTempValue;
import com.quatro.model.ReportValue;
import com.quatro.model.security.NoAccessException;
import com.quatro.service.QuatroReportManager;
import com.quatro.util.Utility;


public class QuatroReportTemplateAction extends BaseAction {

	private QuatroReportManager reportManager;
	public void setQuatroReportManager(QuatroReportManager reportManager) {
		this.reportManager = reportManager;
	}
	
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		QuatroReportSaveTemplateForm myForm = (QuatroReportSaveTemplateForm)form;

		String param=(String)request.getSession(true).getAttribute(DataViews.REPORTTPL);
		if(param!=null){
		   if(Integer.parseInt(param)>0){
			 myForm.setOptSaveAsSelected("optOld");
			 String param2=(String)request.getParameter("postback");
			 if(param2==null){  // not postback
			   ReportValue rptValue = (ReportValue)request.getSession(true).getAttribute(DataViews.REPORT);
			   myForm.setTxtTitle(rptValue.getReportTemp().getDesc());
			 }  
		   }else{
			 myForm.setOptSaveAsSelected("optNew");
		   }
		}

		if((String)request.getParameter("Save")!=null) btnSave_Click(myForm, request);
		ActionForward forward = mapping.findForward("success");
		return forward;
	}

	private void btnSave_Click(QuatroReportSaveTemplateForm myForm, HttpServletRequest request) throws NoAccessException
	{
		ReportValue rptValue = (ReportValue)request.getSession(true).getAttribute(DataViews.REPORT);
		if(rptValue == null) throw new NoAccessException();

		ReportTempValue temp = rptValue.getReportTemp();
		
        if ("optOld".equals((String)request.getParameter("optSaveAs"))==false){
            temp.setTemplateNo(0);
            temp.setDesc(myForm.getTxtDescription());
        }else{
            temp.setDesc(myForm.getTxtTitle());
        }

        if (Utility.IsEmpty(temp.getDesc())) {
            myForm.setMsg("Please specify the Description");
    		return;
        }

        temp.setPrivateTemplate(myForm.getChkPrivate()!=null);

		String userId = (String)request.getSession(true).getAttribute("user");

        ArrayList temps = (ArrayList)reportManager.GetReportTemplates(temp.getReportNo(), userId, true);
        for(int i=0;i<temps.size();i++){
          ReportTempValue rtv = (ReportTempValue)temps.get(i);	
          if (temp.getTemplateNo() != rtv.getTemplateNo()){
            if (rtv.getDesc().toLowerCase().equals(temp.getDesc().toLowerCase())){
            	myForm.setMsg("Duplicate Description is detected, please try to re-wording your descrition.");
        		return;
            }
          }
        }

        try{
            reportManager.SaveReportTemplate(temp);
    		request.getSession(true).setAttribute(DataViews.REPORTTPL, String.valueOf(temp.getTemplateNo()));
			myForm.setMsg(" The template saved successfully");
		}
		catch(Exception ex){
            ;
		}

	}
	
}
