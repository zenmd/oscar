/*
* 
* Copyright (c) 2001-2002. Centre for Research on Inner City Health, St. Michael's Hospital, Toronto. All Rights Reserved. *
* This software is published under the GPL GNU General Public License. 
* This program is free software; you can redistribute it and/or 
* modify it under the terms of the GNU General Public License 
* as published by the Free Software Foundation; either version 2 
* of the License, or (at your option) any later version. * 
* This program is distributed in the hope that it will be useful, 
* but WITHOUT ANY WARRANTY; without even the implied warranty of 
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
* GNU General Public License for more details. * * You should have received a copy of the GNU General Public License 
* along with this program; if not, write to the Free Software 
* Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA. * 
* 
* <OSCAR TEAM>
* 
* This software was written for 
* Centre for Research on Inner City Health, St. Michael's Hospital, 
* Toronto, Ontario, Canada 
*/

package org.oscarehr.casemgmt.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.caisi.service.IssueAdminManager;
import org.oscarehr.PMmodule.service.AdmissionManager;
import org.oscarehr.PMmodule.service.ClientManager;
import org.oscarehr.PMmodule.service.ProgramManager;
import org.oscarehr.PMmodule.service.ProviderManager;
//import org.oscarehr.PMmodule.service.RoleManager;
//import org.oscarehr.PMmodule.service.SurveyManager;
import org.oscarehr.casemgmt.model.ClientImage;
import org.oscarehr.casemgmt.service.CaseManagementManager;
import org.oscarehr.casemgmt.service.ClientImageManager;
import org.caisi.service.TicklerManager;

import com.quatro.common.KeyConstants;
import com.quatro.service.LookupManager;
import org.oscarehr.PMmodule.web.BaseClientAction;

public class BaseCaseManagementViewAction extends BaseClientAction {
	
	protected CaseManagementManager caseManagementMgr;
	protected TicklerManager ticklerManager;
	protected ClientImageManager clientImageMgr;
//	protected RoleManager roleMgr;
	protected ProgramManager programMgr;	
	protected LookupManager lookupMgr;
	protected IssueAdminManager issAdmManager;
	protected ClientManager clientManager;
	
	
	public void setCaseManagementManager(CaseManagementManager caseManagementMgr) {
		this.caseManagementMgr = caseManagementMgr;
	}
	public void setLookupManager(LookupManager lkMgr) {
		this.lookupMgr = lkMgr;
	}
	public void setTicklerManager(TicklerManager mgr) {
		this.ticklerManager = mgr;
	}
	
	public void setClientImageManager(ClientImageManager mgr) {
		this.clientImageMgr = mgr;
	}
/*	
	public void setRoleManager(RoleManager mgr) {
		this.roleMgr = mgr;
	}
*/	
	public void setProgramManager(ProgramManager mgr) {
		this.programMgr = mgr;
	}
	
	
	protected String getDemographicNo(HttpServletRequest request) {
		String demono= request.getParameter("demographicNo");
		if (demono==null || "".equals(demono)) 
			demono=(String)request.getSession(true).getAttribute("casemgmt_DemoNo");		
		/*    
		if(null != request.getSession().getAttribute(KeyConstants.SESSION_KEY_CLIENTID)){
		    	demono=((Integer)request.getSession().getAttribute(KeyConstants.SESSION_KEY_CLIENTID)).toString();	
		    	request.getSession().setAttribute("casemgmt_DemoNo", demono);
		    }
		    */
		else
			request.getSession(true).setAttribute("casemgmt_DemoNo", demono);
//		set for session use
		request.getSession(true).setAttribute(KeyConstants.SESSION_KEY_CLIENTID,demono);
		return demono;
	}
	
	protected String getDemoName(String demoNo){
		if (demoNo==null) return "";
		return caseManagementMgr.getDemoName(demoNo);
	}

	protected String getDemoAge(String demoNo){
		if (demoNo==null) return "";
		return caseManagementMgr.getDemoAge(demoNo);
	}

	protected String getDemoDOB(String demoNo){
		if (demoNo==null) return "";
		return caseManagementMgr.getDemoDOB(demoNo);
	}
	
    protected int getProviderId(HttpServletRequest request){
        return(Integer.parseInt(getProviderNo(request)));
    }
    
	protected String getProviderName(HttpServletRequest request){
		String providerNo=getProviderNo(request);
		if (providerNo==null)
			return "";
		return caseManagementMgr.getProviderName(providerNo);
	}
	
	protected String getImageFilename(String demoNo, HttpServletRequest request) {
		ClientImage img = clientImageMgr.getClientImage(demoNo);
		
		if(img != null) {
			String path=request.getSession(true).getServletContext().getRealPath("/");
			int encodedValue = (int)(Math.random()*Integer.MAX_VALUE);
			String filename = "client" +encodedValue+"."+ img.getImage_type();
			try {
				java.io.FileOutputStream os= new java.io.FileOutputStream(path+"/images/"+filename);
				os.write(img.getImage_data());
				os.flush();
				os.close();
				return filename;
			}catch(Exception e) {
				log.warn(e);
			}
		}
		return null;
	}

	
	public void setClientManager(ClientManager clientManager) {
		this.clientManager = clientManager;
	}
	
}
