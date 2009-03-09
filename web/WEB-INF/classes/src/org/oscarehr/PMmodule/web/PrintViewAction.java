package org.oscarehr.PMmodule.web;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.oscarehr.PMmodule.service.ConsentManager;
import org.springframework.web.context.support.WebApplicationContextUtils;

import oscar.OscarProperties;

import com.crystaldecisions.report.web.viewer.CrPrintMode;
import com.crystaldecisions.report.web.viewer.CrystalReportViewer;
import com.crystaldecisions.report.web.viewer.ReportExportControl;
import com.crystaldecisions.reports.sdk.ReportClientDocument;
import com.crystaldecisions.sdk.occa.report.data.ConnectionInfo;
import com.crystaldecisions.sdk.occa.report.data.ConnectionInfos;
import com.crystaldecisions.sdk.occa.report.data.Fields;
import com.crystaldecisions.sdk.occa.report.data.IConnectionInfo;
import com.crystaldecisions.sdk.occa.report.data.ParameterField;
import com.crystaldecisions.sdk.occa.report.data.ParameterFieldDiscreteValue;
import com.crystaldecisions.sdk.occa.report.data.Values;
import com.crystaldecisions.sdk.occa.report.exportoptions.ExportOptions;
import com.crystaldecisions.sdk.occa.report.exportoptions.ReportExportFormat;
import com.crystaldecisions.sdk.occa.report.lib.ReportSDKException;
import com.crystaldecisions.sdk.occa.report.lib.ReportSDKExceptionBase;
import com.crystaldecisions.sdk.occa.report.reportsource.IReportSource;
import com.quatro.common.KeyConstants;
import com.quatro.model.ReportOptionValue;
import com.quatro.model.ReportValue;
import com.quatro.service.QuatroReportManager;
import com.quatro.util.Utility;

public class PrintViewAction extends BaseClientAction {
	private ConsentManager consentManager;
	
	ReportValue _rptValue;
    ReportOptionValue _rptOption;
    public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {	
    	try {
    		PaintReport(request, response);
    		return null;
    	}
    	catch (Exception e) {
    		return mapping.findForward("failure");
    	}
	}
	private void PaintReport(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String rId =request.getParameter("rId");
		request.setAttribute("rId", rId);
		Integer id =Integer.valueOf(rId);
    	String module=request.getParameter("moduleName");
    	Integer progId = Integer.valueOf(request.getParameter("programId"));
    	if(module.toLowerCase().equals("consent"))
    	{
    		super.getAccess(request, KeyConstants.FUN_CLIENTCONSENT,progId);
    	}
    	else if(module.toLowerCase().equals("bedcheck")) {
    		super.getAccess(request, KeyConstants.FUN_PROGRAM_CLIENTS,progId);
    	}
    	
    	String loginId = (String)request.getSession().getAttribute("user");  
    	 String rptPath="";
    	 ReportClientDocument reportDocument1 = new ReportClientDocument();
    	 
    try{
    	rptPath = getServlet().getServletContext().getResource("/").getPath();
    	String reqPath=request.getContextPath();
//    	String path=rptPath  + "print/" + module+".rpt";
    	String path="/print/" + module+".rpt";
    	if(path.substring(2, 3).equals(":")){  //for Windows System
        	path=path.substring(1);
        }
        reportDocument1.open(path,0);
        
        IReportSource reportSource = (IReportSource)reportDocument1.getReportSource();
        
    	Fields parameterFields = new Fields();
        ParameterField numberParamField = new ParameterField();
        numberParamField.setReportName("");
        Values numberValues = new Values();
        ParameterFieldDiscreteValue numParameterFieldDiscreteValue = new ParameterFieldDiscreteValue();
        
        numberParamField.setName("RecordId");
        numParameterFieldDiscreteValue.setValue(id);
        numberValues.add(numParameterFieldDiscreteValue);
        numberParamField.setCurrentValues(numberValues);
        parameterFields.add(numberParamField);
        
        ConnectionInfos connInfos = new ConnectionInfos();
        ConnectionInfo connInfo1 = new ConnectionInfo();   
        OscarProperties opObj = OscarProperties.getInstance();
        connInfo1.setUserName(opObj.getDbUserName());
        connInfo1.setPassword(opObj.getDbPassword());
       // reportDocument1.getDatabaseController().setDataSource(arg0, arg1, arg2)
        connInfos.add(connInfo1);
        ExportOptions oExportOptions = new ExportOptions();
        oExportOptions.setExportFormatType(ReportExportFormat.PDF);
        ReportExportControl oReportExportControl = new ReportExportControl();
        oReportExportControl.setReportSource(reportSource);
        oReportExportControl.setExportOptions(oExportOptions);
        oReportExportControl.setExportAsAttachment(true);
        oReportExportControl.setDatabaseLogonInfos(connInfos);
        oReportExportControl.setParameterFields(parameterFields);
        oReportExportControl.setEnableParameterPrompt(true);
        //oReportExportControl.refresh();
        oReportExportControl.processHttpRequest(request, response, getServlet().getServletContext(), null); 
        /*
        CrystalReportViewer crViewer = new CrystalReportViewer();
        crViewer.setReportSource(reportSource);
        crViewer.setOwnPage(true);
        crViewer.setOwnForm(true);
        crViewer.setHasLogo(false);
        crViewer.setPrintMode(CrPrintMode.PDF);
        crViewer.setHasPrintButton(true);
        crViewer.setHasExportButton(true);
        crViewer.setParameterFields(parameterFields); 
        crViewer.setDatabaseLogonInfos(connInfos);
        crViewer.processHttpRequest(request, response, getServlet().getServletContext(), null);
        */ 
    }
    catch(ReportSDKException sdkEx) {
        System.out.println(sdkEx);
    }
    catch(ReportSDKExceptionBase ex){
    	System.out.print(ex);
    }
    catch(Exception ex){
    	System.out.print(ex);
    }
    finally
    {
    	try {
    		if(reportDocument1 != null)
    			reportDocument1.close();
    	}
    	catch(Exception ex)
    	{
    		;
    	}
    }
   }
	public void setConsentManager(ConsentManager consentManager) {
		this.consentManager = consentManager;
	}
 }
