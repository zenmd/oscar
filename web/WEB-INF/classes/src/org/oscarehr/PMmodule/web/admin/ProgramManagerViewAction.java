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

package org.oscarehr.PMmodule.web.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.oscarehr.PMmodule.dao.FacilityDAO;
import org.oscarehr.PMmodule.model.Admission;
import org.oscarehr.PMmodule.model.Bed;
import org.oscarehr.PMmodule.model.Demographic;
import org.oscarehr.PMmodule.model.Facility;
import org.oscarehr.PMmodule.model.Program;
import org.oscarehr.PMmodule.model.ProgramClientInfo;
import org.oscarehr.PMmodule.model.ProgramQueue;
import org.oscarehr.PMmodule.model.QuatroIntake;
import org.oscarehr.PMmodule.model.QuatroIntakeFamily;
import org.oscarehr.PMmodule.model.RoomDemographic;
import org.oscarehr.PMmodule.service.AdmissionManager;
import org.oscarehr.PMmodule.service.BedManager;
import org.oscarehr.PMmodule.service.ClientManager;
import org.oscarehr.PMmodule.service.ClientRestrictionManager;
import org.oscarehr.PMmodule.service.IncidentManager;
import org.oscarehr.PMmodule.service.LogManager;
import org.oscarehr.PMmodule.service.ProgramManager;
import org.oscarehr.PMmodule.service.ProgramQueueManager;
import org.oscarehr.PMmodule.service.ProviderManager;
import org.oscarehr.PMmodule.service.RoomDemographicManager;
import org.oscarehr.PMmodule.utility.DateTimeFormatUtils;
import org.oscarehr.PMmodule.web.BaseAction;
import org.oscarehr.PMmodule.web.BaseProgramAction;
import org.oscarehr.PMmodule.web.formbean.ClientForm;
import org.oscarehr.PMmodule.web.formbean.IncidentForm;
import org.oscarehr.PMmodule.web.formbean.ProgramManagerViewFormBean;
import org.oscarehr.PMmodule.web.formbean.StaffForm;
import org.oscarehr.casemgmt.service.CaseManagementManager;

import oscar.MyDateFormat;

import com.quatro.common.KeyConstants;
import com.quatro.model.security.SecProvider;
import com.quatro.model.security.Secuserrole;
import com.quatro.service.IntakeManager;
import com.quatro.service.LookupManager;
import com.quatro.service.security.SecurityManager;
import com.quatro.service.security.UsersManager;
import com.quatro.util.KeyValueBean;
import com.quatro.util.Utility;

public class ProgramManagerViewAction extends BaseProgramAction {

    private static final Log log = LogFactory.getLog(ProgramManagerViewAction.class);

    private ClientRestrictionManager clientRestrictionManager;

    private FacilityDAO facilityDAO=null;

    private AdmissionManager admissionManager;

    private RoomDemographicManager roomDemographicManager;


    private BedManager bedManager;

    private ClientManager clientManager;

    private LogManager logManager;

    private ProgramManager programManager;

    private ProviderManager providerManager;

    private ProgramQueueManager programQueueManager;
    
    private IncidentManager incidentManager;
    
    private UsersManager usersManager;
    
    private LookupManager lookupManager;
    
    private IntakeManager intakeManager;
    
    private static final int REMOVE = 1;
    private static final int ADD = 2;
    private static final int RESET = 3;

    public void setFacilityDAO(FacilityDAO facilityDAO) {
        this.facilityDAO = facilityDAO;
    }
    
    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	//super.setMenu(request,KeyConstants.MENU_PROGRAM);
    	return view(mapping, form, request, response);
    }

    //@SuppressWarnings("unchecked")
    public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ProgramManagerViewFormBean formBean = (ProgramManagerViewFormBean) form;
        String viewTab=request.getParameter("tab");
        String id = request.getParameter("programId");
        if(id == null || id.equals(""))
        	id = (String)request.getAttribute("programId");
        HashMap actionParam = (HashMap) request.getAttribute("actionParam");
        if(actionParam==null){
     	  actionParam = new HashMap();
           actionParam.put("programId", id); 
        }
        request.setAttribute("actionParam", actionParam);
        request.setAttribute("programId", id);
        if (Utility.IsEmpty(viewTab)) {
        	viewTab =KeyConstants.TAB_PROGRAM_GENERAL;            
        }
        formBean.setTab(viewTab);
      
        // find the program id       
        Integer programId = Integer.valueOf(id);
        Program program = programManager.getProgram(programId);
        request.setAttribute("program", program);
        Facility facility=facilityDAO.getFacility(program.getFacilityId());
        if(facility!=null) request.setAttribute("facilityName", facility.getName());

        String demographicNo = request.getParameter("clientId");
        if (demographicNo != null) {
            request.setAttribute("clientId", demographicNo);
        }

//        request.setAttribute("temporaryAdmission", new Boolean(programManager.getEnabled()));

        // check role permission
        HttpSession se=request.getSession(true);        
        String providerNo = (String) request.getSession(true).getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
        se.setAttribute("performAdmissions",hasAccess(request, programId, KeyConstants.FUN_CLIENTADMISSION,SecurityManager.ACCESS_UPDATE));
        // need the queue to determine which tab to go to first
        if (KeyConstants.TAB_PROGRAM_QUEUE.equals(formBean.getTab())) {
	        List queue = programQueueManager.getProgramQueuesByProgramId(programId);
	        request.setAttribute("queue", queue);
	        super.setViewScreenMode(request, KeyConstants.TAB_PROGRAM_QUEUE, programId);
	        boolean isReadOnly = super.isReadOnly(request, KeyConstants.FUN_PROGRAM_QUEUE, programId);
	        if(isReadOnly)request.setAttribute("isReadOnly", Boolean.valueOf(isReadOnly));
	        HashSet genderConflict = new HashSet();
	        HashSet ageConflict = new HashSet();
	//        for (ProgramQueue programQueue : queue) {
	        for (int i=0;i<queue.size();i++) {
	        	ProgramQueue programQueue = (ProgramQueue)queue.get(i); 
	            Demographic demographic=clientManager.getClientByDemographicNo(String.valueOf(programQueue.getClientId()));
	            
	            if (program.getManOrWoman()!=null && demographic.getSex()!=null)
	            {
	                if ("Man".equals(program.getManOrWoman()) && !"M".equals(demographic.getSex()))
	                {
	                    genderConflict.add(programQueue.getClientId());
	                }
	                if ("Woman".equals(program.getManOrWoman()) && !"F".equals(demographic.getSex()))
	                {
	                    genderConflict.add(programQueue.getClientId());
	                }
	                if ("Transgendered".equals(program.getManOrWoman()) && !"T".equals(demographic.getSex()))
	                {
	                    genderConflict.add(programQueue.getClientId());
	                }
	            }
	            
	            if (demographic != null && demographic.getAge()!=null)
	            {
	                int age=Integer.parseInt(demographic.getAge());
	                if (age<program.getAgeMin().intValue() || age>program.getAgeMax().intValue()) ageConflict.add(programQueue.getClientId());
	            }
	        }
	
	        request.setAttribute("genderConflict", genderConflict);
	        request.setAttribute("ageConflict", ageConflict);
        }
        else if (formBean.getTab().equals(KeyConstants.TAB_PROGRAM_SEVICE)) {
            request.setAttribute("service_restrictions", clientRestrictionManager.getActiveRestrictionsForProgram(programId, new Date()));
            super.setViewScreenMode(request, KeyConstants.TAB_PROGRAM_SEVICE, programId);
	        boolean isReadOnly = super.isReadOnly(request, KeyConstants.FUN_PROGRAM_SERVICERESTRICTIONS, programId);
	        if(isReadOnly)request.setAttribute("isReadOnly", Boolean.valueOf(isReadOnly));
        }
        else if (formBean.getTab().equals(KeyConstants.TAB_PROGRAM_STAFF)) {
        	processStaff( request, programId, formBean);
        	super.setViewScreenMode(request, KeyConstants.TAB_PROGRAM_STAFF, programId);
	        boolean isReadOnly = super.isReadOnly(request, KeyConstants.FUN_PROGRAM_STAFF, programId);
	        if(isReadOnly)request.setAttribute("isReadOnly", Boolean.valueOf(isReadOnly));
        }        
        else if (formBean.getTab().equals(KeyConstants.TAB_PROGRAM_CLIENTS)) {
        	processClients( request, program, formBean);
        	super.setViewScreenMode(request, KeyConstants.TAB_PROGRAM_CLIENTS, programId);
	        boolean isReadOnly = super.isReadOnly(request, KeyConstants.FUN_PROGRAM_CLIENTS, programId);
	        if(isReadOnly)request.setAttribute("isReadOnly", Boolean.valueOf(isReadOnly));
        }
        
        else if (formBean.getTab().equals(KeyConstants.TAB_PROGRAM_INCIDENTS)) {
        	processIncident( request, programId.toString(), formBean);  
        	super.setViewScreenMode(request, KeyConstants.TAB_PROGRAM_INCIDENTS, programId);
        }
        else
        {
        	super.setViewScreenMode(request, KeyConstants.TAB_PROGRAM_GENERAL, programId);
	        boolean isReadOnly = super.isReadOnly(request, KeyConstants.FUN_PROGRAM, programId);
	        if(isReadOnly)request.setAttribute("isReadOnly",Boolean.valueOf(isReadOnly));
        }

        logManager.log("view", "program", programId.toString(), request);

        request.setAttribute("programId", programId);
        request.setAttribute("programManagerViewFormBean", formBean);

        return mapping.findForward("view");
    }

    
    private void processClients( HttpServletRequest request, Program program, ProgramManagerViewFormBean formBean){

    	
    	String mthd = request.getParameter("mthd");
    	ClientForm clientForm = formBean.getClientForm();
    	
    	List clientsLst;
    	if(KeyConstants.PROGRAM_TYPE_Bed.equals(program.getType())){
    	  clientsLst = admissionManager.getClientsListForBedProgram(program, clientForm);
    	}else{
      	  clientsLst = intakeManager.getClientsListForServiceProgram(program, 
      		clientForm.getFirstName(), clientForm.getLastName(), clientForm.getClientId());
    	}
    	
    	request.setAttribute("clientsLst", clientsLst);
    	request.setAttribute("programType", program.getType());
    	
 
 		if(mthd == null || mthd.equals("")){
 			clientForm = new ClientForm();
			formBean.setClientForm(clientForm);
 		}
    	
           	
        List lstDischargeReason =lookupManager.LoadCodeList("DRN", true, null, null);
        request.setAttribute("lstDischargeReason", lstDischargeReason);
        
        List lstCommProgram =lookupManager.LoadCodeList("IDS", true, null, null);
        request.setAttribute("lstCommProgram", lstCommProgram);
        
    }
   
    
    private void processStaff( HttpServletRequest request, Integer programId, ProgramManagerViewFormBean formBean){
    	
    	changeLstTable(RESET, formBean, request);
    	
    	
    	//List lst = programManager.getProgramProviders("P" + programId);
    	
    	//request.setAttribute("existStaffLst", lst);
    	
    	    	
    	String mthd = request.getParameter("mthd");
    	
    	StaffForm staffForm = null;
    	
		List lst = new ArrayList();
		if(mthd != null && mthd.equals("search")){
			
	    	String orgcd = "P" + programId;
	    	
			staffForm = formBean.getStaffForm();
			staffForm.setOrgcd(orgcd);
			lst = programManager.searchStaff(staffForm);
			
		}else{
			staffForm = new StaffForm();
			formBean.setStaffForm(staffForm);
//			lst = providerManager.getActiveProviders(programId);
			lst = programManager.getProgramProviders("P" + programId.toString(), true);
		}
		
		request.setAttribute("existStaffLst", lst);
   	
    }
    
    private void processIncident(HttpServletRequest request, String programId, ProgramManagerViewFormBean formBean){
    	String incidentId = request.getParameter("incidentId");
    	String mthd = request.getParameter("mthd");
    	Integer pid = Integer.valueOf(programId);
    	Program prog=programManager.getProgram(pid);
    	request.setAttribute("programActive", Boolean.valueOf(prog.isActive()));
    	HttpSession se=request.getSession(true);
    	String providerNo = (String) se.getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
        
    	IncidentForm incidentForm = null;
    	if(incidentId == null){
    		// incident list
    		List lst = new ArrayList();
    		if(mthd != null && mthd.equals("search")){
    			incidentForm = formBean.getIncidentForm();
    			incidentForm.setProgramId(programId);
    			lst = incidentManager.search(incidentForm);
    		}else{
    			incidentForm = new IncidentForm();
    			formBean.setIncidentForm(incidentForm);
    			lst = incidentManager.getIncidentsByProgramId(pid);
    		}
    		
    		request.setAttribute("incidents", lst);
    	}else {
    		// new/edit incident
    		
    		if(mthd.equals("save")){
    			incidentForm = formBean.getIncidentForm();
    			incidentForm.getIncident().setProgramId(pid);
    			incidentForm.getIncident().setProviderNo(providerNo);
    			
    			Map map = request.getParameterMap();
    			ActionMessages messages = new ActionMessages();
    			if(incidentForm.getInvestigationDateStr().length()>0 && incidentForm.getIncidentDateStr().length()>0){
	    			if(MyDateFormat.getCalendar(incidentForm.getInvestigationDateStr()).before(MyDateFormat.getCalendar(incidentForm.getIncidentDateStr()))){
	    					messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
	        						"error.investigate.date.failed", request.getContextPath()));
	        				saveMessages(request, messages);

	        				String strClients = "";
	        				if(incidentForm.getTxtClientKeys()!=null) strClients = incidentForm.getTxtClientKeys() + "/" + incidentForm.getTxtClientValues(); 
	        				if(strClients.length() > 1){
	        					ArrayList clientSelectionList = new ArrayList();
	        					int sep = strClients.indexOf("/");
	        					String keys = strClients.substring(0, sep);
	        					String values = strClients.substring(sep + 1);
	        					String[] k = keys.split(":");
	        					String[] v = values.split(":");
	        					for(int i = 0; i < k.length; i++){
	        						clientSelectionList.add(new KeyValueBean(k[i],v[i]));
	        					}
	        					incidentForm.setClientSelectionList(clientSelectionList);
	        				}
	        				String strStaffs = "";
	        				if(incidentForm.getTxtStaffKeys()!=null) strStaffs = incidentForm.getTxtStaffKeys() + "/" + incidentForm.getTxtStaffValues(); 
	        				if(strStaffs.length() > 1){
	        					ArrayList staffSelectionList = new ArrayList();
	        					int sep = strStaffs.indexOf("/");
	        					String keys = strStaffs.substring(0, sep);
	        					String values = strStaffs.substring(sep + 1);
	        					String[] k = keys.split(":");
	        					String[] v = values.split(":");
	        					for(int i = 0; i < k.length; i++){
	        						staffSelectionList.add(new KeyValueBean(k[i],v[i]));
	        					}
	        					incidentForm.setStaffSelectionList(staffSelectionList);
	        				}
	        				
	        				
	        				formBean.setIncidentForm(incidentForm);
	        				return;
	        				
	    			}
    			}
    			try {
    				incidentId = incidentManager.saveIncident(incidentForm);
       			
    				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
    						"message.save.success", request.getContextPath()));
    				saveMessages(request, messages);
    				
    			} catch (Exception e) {
    				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
    						"error.saveIncident.failed", request.getContextPath()));
    				saveMessages(request, messages);

    			}
    			
    		}
    		
    		incidentForm = incidentManager.getIncidentForm(incidentId);
    		incidentForm.getIncident().setProgramId(pid);
    		if(mthd.equals("new")){
    			incidentForm.getIncident().setProviderNo(providerNo);
    			SecProvider provider = incidentManager.findProviderById(providerNo);
    			incidentForm.setProviderName(provider.getFirstName() + " " + provider.getLastName() );
    		}
    		formBean.setIncidentForm(incidentForm);
    	}
    	
        boolean isReadOnly = super.isReadOnly(request, KeyConstants.FUN_PROGRAM_INCIDENT, pid);
        isReadOnly=isReadOnly || !prog.isActive() || "1".equals(incidentForm.getIncident().getReportCompleted());
        if(isReadOnly)
         request.setAttribute("isReadOnly", Boolean.valueOf(isReadOnly));        
    }

	public void changeLstTable(int operationType, ActionForm myForm,
			HttpServletRequest request) {
		
//		ActionMessages messages = new ActionMessages();
		
		ArrayList newStaffLst = new ArrayList();
		ArrayList existStaffLst = new ArrayList();

		
		switch (operationType) {
		
		case REMOVE: // remove
			newStaffLst = (ArrayList)getRowList(request, myForm, REMOVE);
			break;
			
		case ADD: // add
			newStaffLst = (ArrayList)getRowList(request, myForm, ADD);
			
			// add one more
			newStaffLst.add(new Secuserrole());
			
			break;
			
		case RESET: // reset
				
			// add one more
			//newStaffLst.add(new Secuserrole());
			
			break;

		}
		
		existStaffLst = (ArrayList)getStaffList(request, myForm, ADD);
		request.setAttribute("existStaffLst", existStaffLst);
		
		request.setAttribute("newStaffLst", newStaffLst);

	}

	public List getRowList(HttpServletRequest request, ActionForm form, int operationType){
		
		ArrayList newStaffLst = new ArrayList();
		
		//ProgramManagerViewFormBean programManagerViewForm = (ProgramManagerViewFormBean) form;
		//String providerNo = (String) secuserForm.get("providerNo");

		Map map = request.getParameterMap();
		String[] arr_lineno = (String[]) map.get("lineno");
		int lineno = 0;
		if (arr_lineno != null)
			lineno = arr_lineno.length;
		
		for (int i = 0; i < lineno; i++) {
			
			String[] isChecked = (String[]) map.get("p" + i);
			if ((operationType == REMOVE && isChecked == null) || operationType != REMOVE) {

				Secuserrole objNew = new Secuserrole();
				
				String[] providerNo = (String[]) map
						.get("providerNo" + i);
				String[] providerName = (String[]) map
						.get("providerName" + i);
				String[] role_code = (String[]) map
						.get("role_code" + i);
				String[] role_description = (String[]) map
						.get("role_description" + i);
		
				if (providerNo != null)
					objNew.setProviderNo(providerNo[0]);
				if (providerName != null)
					objNew.setProviderName(providerName[0]);
				if (role_code != null)
					objNew.setRoleName(role_code[0]);
				if (role_description != null)
					objNew.setRoleName_desc(role_description[0]);
				
		
				newStaffLst.add(objNew);
			}
			
		}
		return newStaffLst;
	}
	
	public List getStaffList(HttpServletRequest request, ActionForm form, int operationType){
		
		ArrayList newStaffLst = new ArrayList();
		ArrayList staffIdLstForRemove = new ArrayList();
		
		//ProgramManagerViewFormBean programManagerViewForm = (ProgramManagerViewFormBean) form;
		//String providerNo = (String) secuserForm.get("providerNo");

		Map map = request.getParameterMap();
		String[] arr_lineno = (String[]) map.get("lineno2");
		int lineno = 0;
		if (arr_lineno != null)
			lineno = arr_lineno.length;
		
		for (int i = 1; i <= lineno; i++) {
			
			String[] isChecked = (String[]) map.get("p2" + i);
			if ((operationType == REMOVE && isChecked == null) || operationType != REMOVE) {

				Secuserrole objNew = new Secuserrole();
				
				String[] providerNo = (String[]) map
						.get("providerNo2" + i);
				String[] providerName = (String[]) map
						.get("providerName2" + i);
				String[] role_code = (String[]) map
						.get("role_code2" + i);
				String[] role_description = (String[]) map
						.get("role_description2" + i);
				String[] id = (String[]) map
						.get("id" + i);
		
				if (providerNo != null)
					objNew.setProviderNo(providerNo[0]);
				if (providerName != null)
					objNew.setProviderName(providerName[0]);
				if (role_code != null)
					objNew.setRoleName(role_code[0]);
				if (role_description != null)
					objNew.setRoleName_desc(role_description[0]);
				if (id != null)
					objNew.setId(Integer.valueOf(id[0]));
		
				newStaffLst.add(objNew);
			}
			
			if (operationType == REMOVE && isChecked != null ) {

				Integer id = Integer.valueOf(isChecked[0]);
		
				staffIdLstForRemove.add(id);
			}
			
			
		}
		request.setAttribute("staffIdLstForRemove", staffIdLstForRemove);
		
		return newStaffLst;
	}

	/////////////////
    
    public ActionForward batch_discharge(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.info("do batch discharge");
        
        ProgramManagerViewFormBean formBean = (ProgramManagerViewFormBean) form;
        ClientForm clientForm = formBean.getClientForm();

        String message = "";

        // get clients
        String dischargeReason = clientForm.getDischargeReason();
        String communityProgramCode = clientForm.getCommunityProgramCode();
        String providerNo = (String) request.getSession(true).getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
        
        Enumeration e = request.getParameterNames();
                
        while (e.hasMoreElements()) {
            String name = (String) e.nextElement();
            if (name.startsWith("checked_") && request.getParameter(name).equals("on")) {
                Integer clientIdx =new Integer(name.indexOf(":"));
            	String admissionId = name.substring(8,clientIdx.intValue());
                Admission admission = admissionManager.getAdmission(new Integer(admissionId));
                if (admission == null) {
                    log.warn("admission #" + admissionId + " not found.");
                    continue;
                }

                
                // lets see if there's room first
                Program programToAdmit = null;
                Demographic client = clientManager.getClientByDemographicNo(admission.getClientId().toString());

                admission.setDischargeDate(Calendar.getInstance());
                admission.setDischargeNotes("Batch discharge");
                admission.setAdmissionStatus(KeyConstants.INTAKE_STATUS_DISCHARGED);
                
                admission.setDischargeReason(dischargeReason);
                admission.setCommunityProgramCode(communityProgramCode);
                
                admission.setTransportationType("");
                admission.setLastUpdateDate(Calendar.getInstance());
                admission.setProviderNo(providerNo);
                
                List lstFamily = intakeManager.getClientFamilyByIntakeId(admission.getIntakeId().toString());
                
//                admissionManager.dischargeAdmission(admission, communityProgramCode.equals(""), lstFamily);
                admissionManager.dischargeAdmission(admission, false, lstFamily);
                
                message += client.getFormattedName() + " has been discharged.<br>";
                
            }
        }

        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.detail", message));
        saveMessages(request, messages);

        return view(mapping, form, request, response);
        
    }
    
    public ActionForward select_client_for_reject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("do_reject", Boolean.TRUE);
        return view(mapping, form, request, response);
    }

    //please write your code without JointAdmission if you need call saveReservedBeds(), dawson wrote May 26, 2008  
    public ActionForward saveReservedBeds(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        return view(mapping, form, request, response);
    }
    
    public ActionForward switch_beds(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ActionMessages messages = new ActionMessages();

        Bed bed1 = null;
        Bed bed2 = null;
        Integer client1 = null;
        Integer client2 = null;

        boolean isFamilyHead1 = false;
        boolean isFamilyHead2 = false;
        boolean isFamilyDependent1 = false;
        boolean isFamilyDependent2 = false;
        boolean isIndependent1 = false;
        boolean isIndependent2 = false;

        Date today = DateTimeFormatUtils.getToday();

        Integer shelterId= (Integer)request.getSession(true).getAttribute(KeyConstants.SESSION_KEY_SHELTERID);

    	Enumeration e = request.getParameterNames();
        ArrayList lstBeds = new ArrayList();
        ArrayList lstAdmission = new ArrayList();
         while (e.hasMoreElements()) {
             String name = (String) e.nextElement();
             if (name.startsWith("checked_") && request.getParameter(name).equals("on")) {                 
            	  Integer clientIdx =new Integer(name.indexOf(":"));
              	  String clientId = name.substring(clientIdx.intValue()+1);
                  RoomDemographic rdObj =roomDemographicManager.getRoomDemographicByDemographic(new Integer(clientId));
            	  lstBeds.add(rdObj);  
            	 String admissionId = name.substring(8,clientIdx.intValue());
                 Admission admission = admissionManager.getAdmission(new Integer(admissionId));
                 lstAdmission.add(admission);
             }
         }
         
   		String providerNo = (String) request.getSession(true).getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);

   		RoomDemographic roomDemographic1 = (RoomDemographic)lstBeds.get(0);
   		RoomDemographic roomDemographic2 = (RoomDemographic)lstBeds.get(1);
   		
   		if(roomDemographic1 == null  ||  roomDemographic2 == null){
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("bed.check.error"));
            saveMessages(request, messages);
            return view(mapping, form, request, response);
   		}

   		if(roomDemographic1.getBedId()!=null) bed1 = bedManager.getBed(roomDemographic1.getBedId());
   		if(roomDemographic2.getBedId()!=null) bed2 = bedManager.getBed(roomDemographic2.getBedId());
   		
   		client1 = roomDemographic1.getId().getDemographicNo();
   		client2 = roomDemographic2.getId().getDemographicNo();

   		Integer roomId1 = roomDemographic1.getId().getRoomId();
		Integer roomId2 = roomDemographic2.getId().getRoomId();

   		Integer bedId1 = roomDemographic1.getBedId();
		Integer bedId2 = roomDemographic2.getBedId();

		//please overwrite my code below. For family intake, suppose get from intakeManager. dawson May 26, 2008
		isFamilyHead1 = false;
		isFamilyHead2 = false;
		isFamilyDependent1 = false;
		isFamilyDependent2 = false;
		
   		Admission admObj1 =(Admission)lstAdmission.get(0);
   		Admission admObj2 =(Admission)lstAdmission.get(1);
   		if(null==admObj1.getIntakeHeadId()) isFamilyDependent1 =false;
   		else if(admObj1.getIntakeId()==admObj1.getIntakeHeadId()) isFamilyHead1 =true;
   		else if(admObj1.getIntakeId()!=admObj1.getIntakeHeadId()) isFamilyDependent1 =true;
   		
   		if(null==admObj2.getIntakeHeadId()) isFamilyDependent2 =false;
   		else if(admObj2.getIntakeId()==admObj2.getIntakeHeadId()) isFamilyHead2 =true;
   		else if(admObj2.getIntakeId()!=admObj2.getIntakeHeadId()) isFamilyDependent2 =true;
   			
   		if(!isFamilyHead1  &&  !isFamilyDependent1){
   			isIndependent1 = true;
   		}
   		if(!isFamilyHead2  &&  !isFamilyDependent2){
   			isIndependent2 = true;
   		}

   		//Check whether both clients are indpendents
   		if(isIndependent1  &&  isIndependent2){
   	   			
   	   	    roomDemographic1.setAssignStart(today);
   	   	    roomDemographic1.setProviderNo(providerNo);
   	   	    roomDemographic1.getId().setRoomId(roomId2);
   	   	    roomDemographic1.setBedId(bedId2);
   	   		       	   		    
   	   	    roomDemographic2.setAssignStart(today);
   	   	    roomDemographic2.setProviderNo(providerNo);
   	   		roomDemographic2.getId().setRoomId(roomId1);
   	   	    roomDemographic2.setBedId(bedId1);

   	   	    roomDemographicManager.saveRoomDemographic(roomDemographic1);
   	   		roomDemographicManager.saveRoomDemographic(roomDemographic2);
   		    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("bed.check.swap_room_success"));
   		    saveMessages(request, messages);   		           
   		}else if(!isIndependent1  &&  !isIndependent2){
  			List f1 =intakeManager.getClientFamilyByIntakeId(admObj1.getIntakeId().toString());
   			List f2 =intakeManager.getClientFamilyByIntakeId(admObj2.getIntakeId().toString());
   			String cIds="";   	   	   			
   			Iterator f1Items =f1.iterator();   					
   			while(f1Items.hasNext()){
   				QuatroIntakeFamily f1Intake =(QuatroIntakeFamily)f1Items.next();
   				cIds +=f1Intake.getClientId()+",";
   			}
   			String[] clients=cIds.split(",");
   			for(int i=0;i<clients.length;i++){
                RoomDemographic rdObj =roomDemographicManager.getRoomDemographicByDemographic(new Integer(clients[i]));
   	   	   			
   	   	   	    rdObj.setAssignStart(today);
   	   	   	    rdObj.setProviderNo(providerNo);   	   	   		    
   	   	   	    rdObj.getId().setRoomId(roomId2);
   	   	   	    roomDemographicManager.saveRoomDemographic(rdObj); 
  			}

   			Iterator f2Items =f2.iterator();
   			cIds="";
   			while(f2Items.hasNext()){
   				QuatroIntakeFamily f2Intake =(QuatroIntakeFamily)f2Items.next();
   				cIds +=f2Intake.getClientId()+",";
   			}
   			clients=cIds.split(",");
   			for(int i=0;i<clients.length;i++){
                RoomDemographic rdObj =roomDemographicManager.getRoomDemographicByDemographic(new Integer(clients[i]));
   	   	   	    rdObj.setAssignStart(today);
   	   	   	    rdObj.setProviderNo(providerNo);   	   	   		    
   	   	   	    rdObj.getId().setRoomId(roomId1);
   	   	   	    roomDemographicManager.saveRoomDemographic(rdObj); 
   			}
   		    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("bed.check.swap_room_success"));
   		    saveMessages(request, messages);   		           
   		}else{
   			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("bed.check.error_person_type"));
   		    saveMessages(request, messages);		            
   		}
        return view(mapping, form, request, response);
    }
    
    public void setClientRestrictionManager(ClientRestrictionManager clientRestrictionManager) {
        this.clientRestrictionManager = clientRestrictionManager;
    }

    public void setAdmissionManager(AdmissionManager mgr) {
    	this.admissionManager = mgr;
    }

    public void setRoomDemographicManager(RoomDemographicManager roomDemographicManager) {
    	this.roomDemographicManager = roomDemographicManager;
    }

    public void setBedManager(BedManager bedManager) {
    	this.bedManager = bedManager;
    }

    public void setClientManager(ClientManager mgr) {
    	this.clientManager = mgr;
    }

    public void setLogManager(LogManager mgr) {
    	this.logManager = mgr;
    }

    public void setProgramManager(ProgramManager mgr) {
    	this.programManager = mgr;
    }

    public void setProviderManager(ProviderManager mgr) {
    	this.providerManager = mgr;
    }

    public void setProgramQueueManager(ProgramQueueManager mgr) {
    	this.programQueueManager = mgr;
    }

	public void setIncidentManager(IncidentManager incidentManager) {
		this.incidentManager = incidentManager;
	}
	private Boolean hasAccess(HttpServletRequest request, Integer programId, String function, String right)
	{
	    SecurityManager sec = (SecurityManager)request.getSession(true).getAttribute(KeyConstants.SESSION_KEY_SECURITY_MANAGER);
	    String orgCd = "P" + programId.toString();
	    return new Boolean(sec.GetAccess(function,orgCd).compareTo(right) >= 0);
	}

	public void setUsersManager(UsersManager usersManager) {
		this.usersManager = usersManager;
	}

	public void setLookupManager(LookupManager lookupManager) {
		this.lookupManager = lookupManager;
	}

	public void setIntakeManager(IntakeManager intakeManager) {
		this.intakeManager = intakeManager;
	}

}