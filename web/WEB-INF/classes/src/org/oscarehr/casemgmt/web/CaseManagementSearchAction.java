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
* Toronto, Ontario, Canada  - UPDATED: Quatro Group 2008/2009
 */

package org.oscarehr.casemgmt.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.oscarehr.PMmodule.model.Admission;
import org.oscarehr.PMmodule.model.QuatroIntakeHeader;
import org.oscarehr.PMmodule.service.AdmissionManager;
import org.oscarehr.casemgmt.model.CaseManagementCPP;
import org.oscarehr.casemgmt.model.CaseManagementNote;
import org.oscarehr.casemgmt.model.CaseManagementSearchBean;
import org.oscarehr.casemgmt.model.CaseManagementTmpSave;
import org.oscarehr.casemgmt.web.formbeans.CaseManagementViewFormBean;
import org.oscarehr.common.model.UserProperty;

import com.quatro.common.KeyConstants;
import com.quatro.model.LookupCodeValue;
import com.quatro.model.security.NoAccessException;
import com.quatro.service.IntakeManager;
import com.quatro.service.security.SecurityManager;
import com.quatro.util.Utility;


public class CaseManagementSearchAction extends BaseCaseManagementViewAction {

    private IntakeManager intakeManager;
    private AdmissionManager admissionManager;
    
    private static Logger log = LogManager.getLogger(CaseManagementSearchAction.class);

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CaseManagementViewFormBean caseForm = (CaseManagementViewFormBean) form;
        caseForm.setFilter_provider("");        
    	super.getAccess(request, KeyConstants.FUN_CLIENTCASE, null);
        return client(mapping, form, request, response);
     //   return view(mapping, form, request, response);
    }
    private ActionForward client(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
       // request.getSession(true).setAttribute(KeyConstants.SESSION_KEY_CURRENT_FUNCTION, "cv");
		
    	 HashMap actionParam = (HashMap) request.getAttribute("actionParam");
	       if(actionParam==null){
	    	  actionParam = new HashMap();
	          actionParam.put("clientId", request.getParameter("clientId")); 
	       }
	       request.setAttribute("actionParam", actionParam);	      
    	String demono= (String)actionParam.get("clientId");
    
	    request.setAttribute("clientId", demono);
	    request.setAttribute("client", clientManager.getClientByDemographicNo(demono));
    	
    	
		CaseManagementViewFormBean caseForm = (CaseManagementViewFormBean) form;
		String nView=(String)request.getParameter("note_view");
		caseForm.setDemographicNo(demono);
		if(!Utility.IsEmpty(nView)) {
			request.setAttribute("note_view", nView);
			return view(mapping,form,request, response);
		}
		else
		{
			if (Utility.IsEmpty(demono)) {
				
				return mapping.findForward("client");
			}
			else
			{
				request.getSession(true).setAttribute("casemgmt_DemoNo",demono);
				return view(mapping,form,request, response);
			}
		}
    }
    
    
    /* show case management view */
    /*
     * Session variables : case_program_id casemgmt_DemoNo casemgmt_VlCountry casemgmt_msgBeans readonly
     */
    private String getAdmissionPrimaryWorks(List admissions) {    	
    	
    	String cds="";    	
    	for(int i=0;i<admissions.size();i++){
    		Admission admission= (Admission)admissions.get(i);
    		if(!Utility.IsEmpty(admission.getPrimaryWorker()) && cds.indexOf(admission.getPrimaryWorker()+"',")<0){
    			cds+= admission.getPrimaryWorker()+",";
    		}
    	}
    	if(cds.endsWith(",")) cds=cds.substring(0,cds.length()-1);    	    	
		return cds ;

    }
    
    private ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        long start = System.currentTimeMillis();
        long current = 0;
        Integer currentFacilityId=(Integer)request.getSession(true).getAttribute(KeyConstants.SESSION_KEY_SHELTERID);
        CaseManagementViewFormBean caseForm = (CaseManagementViewFormBean) form;        
        HttpSession se = request.getSession(true);
        String cId=request.getParameter("clientId");
        if(Utility.IsEmpty(cId)) cId=request.getSession(true).getAttribute("casemgmt_DemoNo").toString();
        HashMap actionParam = (HashMap) request.getAttribute("actionParam");
	       if(actionParam==null){
	    	  actionParam = new HashMap();
	          actionParam.put("clientId",cId ); 
	       }
	       request.setAttribute("actionParam", actionParam);	      
	       String demoNo= (String)actionParam.get("clientId");
	       request.setAttribute("clientId", demoNo);
	       request.setAttribute("client", clientManager.getClientByDemographicNo(demoNo));
        String providerNo = (String)se.getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
        
/*
        //for new button security
        Admission curAdminssion=clientManager.getCurrentAdmission(new Integer(cId), providerNo, currentFacilityId);
        boolean hasAdm=false;
        if(curAdminssion!=null && curAdminssion.getId().intValue()>0) hasAdm=true;
        request.setAttribute("hasAdmission", Boolean.valueOf(hasAdm));
*/
        Integer shelterId=(Integer)request.getSession().getAttribute(KeyConstants.SESSION_KEY_SHELTERID);
	    List lstIntakeHeader = intakeManager.getQuatroIntakeHeaderListByFacility(Integer.valueOf(cId), shelterId, providerNo);
	    if(lstIntakeHeader.size()>0) {
	       QuatroIntakeHeader obj0= (QuatroIntakeHeader)lstIntakeHeader.get(0);
           request.setAttribute("currentIntakeProgramId", obj0.getProgramId());
	    }else{
           request.setAttribute("currentIntakeProgramId", new Integer(0));
	    }
        
        
        if(providerNo==null || demoNo ==null) {
         request.getSession(true).setAttribute(KeyConstants.SESSION_KEY_CURRENT_FUNCTION, "cv");
         return mapping.findForward("client");
        }        
        request.setAttribute("casemgmt_demoName", getDemoName(demoNo));
        request.setAttribute("casemgmt_demoAge", getDemoAge(demoNo));
        request.setAttribute("casemgmt_demoDOB", getDemoDOB(demoNo));
        request.setAttribute("demographicNo", demoNo);

        // get client image
        request.setAttribute("image_filename", this.getImageFilename(demoNo, request));

        Integer programId = (Integer) request.getSession(true).getAttribute("case_program_id");

        if (programId==null || programId.intValue()==0) {
        	List intakes = this.clientManager.getIntakeByFacility(new Integer(demoNo), currentFacilityId);
        	if (intakes.size() == 0) {
                super.setScreenMode(request, KeyConstants.TAB_CLIENT_CASE);
                return mapping.findForward("domain-error");             
        	}
        	else
        	{
                QuatroIntakeHeader curIntake = (QuatroIntakeHeader) intakes.get(0);
        		programId = curIntake.getProgramId();
            	request.setAttribute("case_program_id", programId);
        	}
        }
                          
            /* PROGRESS NOTES */
            List notes = null;

            // filter the notes by the checked issues and date if set
            UserProperty userProp = caseManagementMgr.getUserProperty(providerNo, UserProperty.STALE_NOTEDATE);         
//            Integer shelterId=(Integer)request.getSession(true).getAttribute(KeyConstants.SESSION_KEY_SHELTERID);
           
            if(request.getAttribute("Notes")!=null) notes=(List)request.getAttribute("Notes");
            else notes = caseManagementMgr.getNotes(demoNo, userProp,shelterId,providerNo);
            
            //notes = manageLockedNotes(notes, false, this.getUnlockedNotesMap(request));
           

            log.info("FETCHED " + notes.size() + " NOTES");
            // apply role based access
            // if(request.getSession(true).getAttribute("archiveView")!="true")
            current = System.currentTimeMillis();
            log.debug("GET NOTES " + String.valueOf(current - start));
            start = current;
          //  notes = caseManagementMgr.filterNotes(notes, providerNo, programId,currentFacilityId);
          //  notes = caseManagementMgr.filterNotes(notes,providerNo,currentFacilityId,caseForm.getSearchServiceComponent(),caseForm.getSearchCaseStatus());
            current = System.currentTimeMillis();
            log.debug("FILTER NOTES " + String.valueOf(current - start));
            start = current;

            // apply provider filter
            //commemt by lillian
            /*
            Set providers = new HashSet();
            notes = applyProviderFilters(notes, providers, caseForm.getFilter_providers());
            current = System.currentTimeMillis();
            log.debug("FILTER NOTES PROVIDER " + String.valueOf(current - start));
            start = current;
          */
           
            List caseStatus=lookupMgr.LoadCodeList("CST", true, null, null);
            request.setAttribute("caseStatusList", caseStatus);
            
            List issues = this.lookupMgr.LoadCodeList("ISS", true,null,null);
            request.setAttribute("issues", issues);
            
            List admis = admissionManager.getAdmissions(Integer.valueOf(demoNo),providerNo, shelterId);
            String workerIds = getAdmissionPrimaryWorks(admis);
            List caseWorkers;
            if(!Utility.IsEmpty(workerIds)) {
            	caseWorkers = this.lookupMgr.LoadCodeList("USR", true,getAdmissionPrimaryWorks(admis),null);
            }
            else
            {
            	caseWorkers = new ArrayList();
            }
            request.setAttribute("caseWorkers", caseWorkers);            
            
            this.caseManagementMgr.getEditors(notes);
            /*
             * Notes are by default sorted from the past to the most recent So we sort only if preference is set in form or site wide setting in oscar.properties
             */
            String noteSort = caseForm.getNote_sort();
            if (noteSort != null && noteSort.length() > 0) {
                request.setAttribute("Notes", sort_notes(notes, noteSort));
            }
            else {
                oscar.OscarProperties p = oscar.OscarProperties.getInstance();
                noteSort = p.getProperty("CMESort", "");
                if (noteSort.trim().equalsIgnoreCase("UP")) request.setAttribute("Notes", sort_notes(notes, "update_date_asc"));
                else request.setAttribute("Notes", notes);
            }


        
            /*
		        CaseManagementCPP cpp = this.caseManagementMgr.getCPP(this.getDemographicNo(request));
		        if (cpp == null) {
		            cpp = new CaseManagementCPP();
		            cpp.setDemographic_no(getDemographicNo(request));
		        }
		        request.setAttribute("cpp", cpp);
		        caseForm.setCpp(cpp);
             */
        /* get allergies */
        /*
        List allergies = this.caseManagementMgr.getAllergies(this.getDemographicNo(request));
        request.setAttribute("Allergies", allergies);
		*/
               /* set form value for e-chart */

        Locale vLocale = (Locale) se.getAttribute(org.apache.struts.Globals.LOCALE_KEY);
        caseForm.setVlCountry(vLocale.getCountry());
        caseForm.setDemographicNo(demoNo);

        se.setAttribute("casemgmt_DemoNo", demoNo);
        caseForm.setRootCompURL((String) se.getAttribute("casemgmt_oscar_baseurl"));
        se.setAttribute("casemgmt_VlCountry", vLocale.getCountry());

        // readonly access to define creat a new note button in jsp.
        SecurityManager sec = (SecurityManager) request.getSession(true).getAttribute(KeyConstants.SESSION_KEY_SECURITY_MANAGER);
       
        boolean tmp = sec.GetAccess(KeyConstants.FUN_CLIENTCASE,"P" + programId.toString()).equals(SecurityManager.ACCESS_READ);
        Boolean readonly = new Boolean(tmp);
        se.setAttribute("readOnly", readonly);

        // if we have just saved a note, remove saveNote flag
        Boolean saved = (Boolean) se.getAttribute("saveNote");
        if (saved != null && saved.booleanValue() == true) {
            request.setAttribute("saveNote", saved);
            se.removeAttribute("saveNote");
        }
        current = System.currentTimeMillis();
        log.debug("THE END " + String.valueOf(current - start));
        
        super.setScreenMode(request, KeyConstants.TAB_CLIENT_CASE);
        String useNewCaseMgmt = (String) request.getSession(true).getAttribute("newCaseManagement");
        request.getSession(true).setAttribute(KeyConstants.SESSION_KEY_CURRENT_FUNCTION, "cv");
        if (useNewCaseMgmt != null && useNewCaseMgmt.equals("true")) return mapping.findForward("page.newcasemgmt.view");
      //  else return mapping.findForward("page.casemgmt.view");
        else return mapping.findForward("page.casemgmt.search");

    }

    public ActionForward close(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	//request.getSession(true).setAttribute(KeyConstants.SESSION_KEY_SWITCH_MODULE,"Y");
    	if(request.getSession(true).getAttribute("casemgmt_DemoNo")!=null) request.getSession(true).removeAttribute("casemgmt_DemoNo");
    	return mapping.findForward("client");
    }

    public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	super.getAccess(request, KeyConstants.FUN_CLIENTCASE, null);
    	Integer programId = (Integer) request.getSession(true).getAttribute("case_program_id");
        String cId =request.getParameter("clientId");
        if(Utility.IsEmpty(cId)){
        	if (request.getSession(true).getAttribute("casemgmt_DemoNo") == null) throw new NoAccessException();
        	cId =request.getSession(true).getAttribute("casemgmt_DemoNo").toString();
        }
        HashMap actionParam = (HashMap) request.getAttribute("actionParam");
	       if(actionParam==null){
	    	  actionParam = new HashMap();
	          actionParam.put("clientId",cId ); 
	       }
	    request.setAttribute("actionParam", actionParam);
	    String demoNo= (String)actionParam.get("clientId");
	    request.setAttribute("clientId", demoNo);
	    request.setAttribute("client", clientManager.getClientByDemographicNo(demoNo));
        CaseManagementViewFormBean caseForm = (CaseManagementViewFormBean) form;
        CaseManagementSearchBean searchBean = new CaseManagementSearchBean(this.getDemographicNo(request));
        //searchBean.setSearchEncounterType(caseForm.getSearchEncounterType());
        searchBean.setSearchEndDate(caseForm.getSearchEndDate());
        //searchBean.setSearchProgramId(caseForm.getSearchProgramId());
        searchBean.setSearchProviderNo(caseForm.getSearchProviderNo());
        //searchBean.setSearchRoleId(caseForm.getSearchRoleId());
        searchBean.setSearchStartDate(caseForm.getSearchStartDate());
        //searchBean.setSearchText(caseForm.getSearchText());
        searchBean.setSearchCaseStatus(caseForm.getSearchCaseStatus());
        searchBean.setSearchCaseWorker(caseForm.getSearchCaseWorker());
        searchBean.setSearchServiceComponent(caseForm.getSearchServiceComponent());
        String providerNo = (String)request.getSession(true).getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
        Integer shelterId =(Integer)request.getSession(true).getAttribute(KeyConstants.SESSION_KEY_SHELTERID);
        List results = this.caseManagementMgr.search(searchBean,shelterId,providerNo);
        List filtered1 = results;           
//        List filtered1 = manageLockedNotes(results, false, this.getUnlockedNotesMap(request));           
        // comment by lillian, it does not need apply for filter
        //List filteredResults = caseManagementMgr.filterNotes(filtered1, getProviderNo(request), programId,currentFacilityId);
        
      //  List filteredResults = caseManagementMgr.filterNotes(filtered1, getProviderNo(request),shelterId,caseForm.getSearchServiceComponent(),caseForm.getSearchCaseStatus());

        List sortedResults = this.sort_notes(filtered1, caseForm.getNote_sort());
       // request.setAttribute("search_results", sortedResults);
        request.setAttribute("Notes", sortedResults);
        return view(mapping, form, request, response);
       //return mapping.findForward("page.newcasemgmt.view");
    }

    private List sort_notes(List notes, String field) throws Exception {
        log.debug("Sorting notes by field: " + field); 
        if (field == null || field.equals("") || field.equals("update_date")) {
            return notes;
        }

        if (field.equals("providerName")) {
            Collections.sort(notes, CaseManagementNote.getProviderComparator());
        }
        if (field.equals("programName")) {
            Collections.sort(notes, CaseManagementNote.getProgramComparator());
        }
        /*
        if (field.equals("roleName")) {
            Collections.sort(notes, CaseManagementNote.getRoleComparator());
        }
        */
        if (field.equals("update_date_asc")) {
            Collections.reverse(notes);
        }

        return notes;
    }

	public void setIntakeManager(IntakeManager intakeManager) {
		this.intakeManager = intakeManager;
	}

	public void setAdmissionManager(AdmissionManager admissionManager) {
		this.admissionManager = admissionManager;
	}

}
