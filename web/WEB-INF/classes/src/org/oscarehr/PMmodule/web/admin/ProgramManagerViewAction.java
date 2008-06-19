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
import org.oscarehr.PMmodule.model.BedDemographic;
import org.oscarehr.PMmodule.model.Demographic;
import org.oscarehr.PMmodule.model.Facility;
import org.oscarehr.PMmodule.model.Program;
import org.oscarehr.PMmodule.model.ProgramClientInfo;
import org.oscarehr.PMmodule.model.ProgramQueue;
import org.oscarehr.PMmodule.model.RoomDemographic;
import org.oscarehr.PMmodule.service.AdmissionManager;
import org.oscarehr.PMmodule.service.BedDemographicManager;
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

public class ProgramManagerViewAction extends BaseAction {

    private static final Log log = LogFactory.getLog(ProgramManagerViewAction.class);

    private ClientRestrictionManager clientRestrictionManager;

    private FacilityDAO facilityDAO=null;

    private CaseManagementManager caseManagementManager;

    private AdmissionManager admissionManager;

    private RoomDemographicManager roomDemographicManager;

    private BedDemographicManager bedDemographicManager;

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
    	super.setMenu(request,KeyConstants.MENU_PROGRAM);
    	return view(mapping, form, request, response);
    }

    //@SuppressWarnings("unchecked")
    public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ProgramManagerViewFormBean formBean = (ProgramManagerViewFormBean) form;

        if (formBean.getTab() == null || formBean.getTab().equals("")) {
            formBean.setTab("Queue");
        }

        // find the program id
        String id = request.getParameter("id");

        if (id == null) {
            id = (String) request.getAttribute("id");
        }
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
        HttpSession se=request.getSession();
        String providerNo = (String)se.getAttribute("user");
        se.setAttribute("performAdmissions",hasAccess(request, programId, "_pmm_clientAdmission",SecurityManager.ACCESS_UPDATE));
        // need the queue to determine which tab to go to first
        if ("Queue".equalsIgnoreCase(formBean.getTab())) {
	        List queue = programQueueManager.getProgramQueuesByProgramId(programId);
	        request.setAttribute("queue", queue);
        
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
        else if (formBean.getTab().equalsIgnoreCase("Service Restrictions")) {
            request.setAttribute("service_restrictions", clientRestrictionManager.getActiveRestrictionsForProgram(programId, new Date()));
        }
        else if (formBean.getTab().equalsIgnoreCase("Staff")) {
        	processStaff( request, programId, formBean);
        }
        else if(formBean.getTab().equalsIgnoreCase("Function User")) {
            request.setAttribute("functional_users", programManager.getFunctionalUsers(programId.toString()));
        }
        else if (formBean.getTab().equalsIgnoreCase("Clients")) {
        	processClients( request, program, formBean);
        }
        else if (formBean.getTab().equalsIgnoreCase("Client Status")) {
            request.setAttribute("client_statuses", programManager.getProgramClientStatuses(programId));
        }
        else if (formBean.getTab().equalsIgnoreCase("Incidents")) {
        	processIncident( request, programId.toString(), formBean);
        }
        else
        {
        	// General - nothing need to do
        }

        logManager.log("view", "program", programId.toString(), request);

        request.setAttribute("id", programId);
        request.setAttribute("programManagerViewFormBean", formBean);

        return mapping.findForward("view");
    }

    
    private void processClients( HttpServletRequest request, Program program, ProgramManagerViewFormBean formBean){

    	
    	String mthd = request.getParameter("mthd");
    	ClientForm clientForm = formBean.getClientForm();
    	
    	List lst = admissionManager.getClientsListByProgram(program, clientForm);
    	
    	
    	
    	List clientsLst = new ArrayList();
    	Iterator it = lst.iterator();
    	while(it.hasNext()){
    		Object[] objLst = (Object[])it.next();
    		ProgramClientInfo pClient = new ProgramClientInfo();
    		if(objLst[0]!=null){
//    			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
    		    String admissionDate = formatter.format(((Calendar)objLst[0]).getTime());
    		    pClient.setAdmissionDate(admissionDate);
    		}
    		if(objLst[1]!=null)
    			pClient.setAdmissionNote(objLst[1].toString());
    		if(objLst[2]!=null && objLst[3]!=null){
	    		Calendar now = Calendar.getInstance();
	    		Calendar start = (Calendar)objLst[2];
	    		Calendar end = (Calendar)objLst[3];
	    		if(start.before(now) && end.after(now)){
	    			pClient.setIsDischargeable("0");
	    		}else{
	    			pClient.setIsDischargeable("1");
	    		}
    		}else{
    			pClient.setIsDischargeable("1");
    		}
    		pClient.setAdmissionId((Integer)objLst[4]);
    		pClient.setFirstName((String)objLst[5]);
    		pClient.setLastName((String)objLst[6]);
    		pClient.setClientId(objLst[7].toString());
    		pClient.setRoom((String)objLst[8]);
            pClient.setBed((String)objLst[9]);
            Integer intakeId = null;
            Integer intakeHeadId = null;
            if(objLst[10] != null)
            	intakeId = (Integer)objLst[10];
            if(objLst[11] != null)
            	intakeHeadId = (Integer)objLst[11];
            if(intakeHeadId == null || intakeHeadId.equals(intakeId)){
            	pClient.setIsHead(true);
            }else{
            	pClient.setIsHead(false);
            }
    		
    		clientsLst.add(pClient);
    	}
    	request.setAttribute("clientsLst", clientsLst);
    	
 
 		if(mthd == null || mthd.equals("")){
 			clientForm = new ClientForm();
			formBean.setClientForm(clientForm);
 		}
    	
           	
        List lstDischargeReason =lookupManager.LoadCodeList("DRN", true, null, null);
        request.setAttribute("lstDischargeReason", lstDischargeReason);
        
        List lstCommProgram =lookupManager.LoadCodeList("CMP", true, null, null);
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
    	
    	HttpSession se=request.getSession();
        String providerNo = (String)se.getAttribute("user");
        
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
	
    public ActionForward viewBedReservationChangeReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        Integer reservedBedId = Integer.valueOf(request.getParameter("reservedBedId"));
        System.err.println(reservedBedId);
        // BedDemographicChange[] bedDemographicChanges = bedDemographicManager.getBedDemographicChanges(reservedBedId)
        request.setAttribute("bedReservationChanges", null);

        return mapping.findForward("viewBedReservationChangeReport");
    }

    public ActionForward viewBedCheckReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        Integer programId = Integer.valueOf(request.getParameter("programId"));

    	Bed[] beds = bedManager.getBedsByProgram(programId, true);
    	beds = bedManager.addFamilyIdsToBeds(clientManager, beds);
        request.setAttribute("reservedBeds", beds);
        return mapping.findForward("viewBedCheckReport");
    }

/*    
    public ActionForward admit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String programId = request.getParameter("id");
        String clientId = request.getParameter("clientId");
        String queueId = request.getParameter("queueId");

        ProgramQueue queue = programQueueManager.getProgramQueue(queueId);
        Program fullProgram = programManager.getProgram(String.valueOf(programId));
        String dischargeNotes = request.getParameter("admission.dischargeNotes");
        String admissionNotes = request.getParameter("admission.admissionNotes");
        List<Integer>  dependents = clientManager.getDependentsList(new Integer(clientId));
        
        

        try {
            admissionManager.processAdmission(Integer.valueOf(clientId), getProviderNo(request), fullProgram, dischargeNotes, admissionNotes, 
            		queue.isTemporaryAdmission(),dependents);
            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("admit.success"));
            saveMessages(request, messages);
        }
        catch (ProgramFullException e) {
            log.error(e);
            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("admit.full"));
            saveMessages(request, messages);
        }
        catch (AdmissionException e) {
            log.error(e);
            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("admit.error", e.getMessage()));
            saveMessages(request, messages);
        }
        catch (ServiceRestrictionException e) {
            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("admit.service_restricted", e.getRestriction().getComments(), e.getRestriction()
                    .getProvider().getFormattedName()));
            saveMessages(request, messages);

            // store this for display
            ProgramManagerViewFormBean formBean = (ProgramManagerViewFormBean) form;

            formBean.setServiceRestriction(e.getRestriction());

            request.getSession().setAttribute("programId", programId);
            request.getSession().setAttribute("admission.dischargeNotes", dischargeNotes);
            request.getSession().setAttribute("admission.admissionNotes", admissionNotes);

            request.setAttribute("id", programId);

            request.setAttribute("hasOverridePermission", hasAccess(request, Integer.valueOf(programId),"_pmm_editProgram.serviceRestrictions", SecurityManager.ACCESS_ALL));

            return mapping.findForward("service_restriction_error");
        }

        logManager.log("view", "admit to program", clientId, request);

        return view(mapping, form, request, response);
    }
*/

/*    
    public ActionForward override_restriction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String programId = (String) request.getSession().getAttribute("programId");
        String clientId = request.getParameter("clientId");
        String queueId = request.getParameter("queueId");

        String dischargeNotes = (String) request.getSession().getAttribute("admission.dischargeNotes");
        String admissionNotes = (String) request.getSession().getAttribute("admission.admissionNotes");

        request.setAttribute("id", programId);

        if (isCancelled(request)
                || !hasAccess(request, Integer.valueOf(programId),"_pmm_editProgram.serviceRestrictions",SecurityManager.ACCESS_ALL)) {
            return view(mapping, form, request, response);
        }

        ProgramQueue queue = programQueueManager.getProgramQueue(queueId);
        Program fullProgram = programManager.getProgram(String.valueOf(programId));

        try {
            admissionManager.processAdmission(Integer.valueOf(clientId), getProviderNo(request), fullProgram, dischargeNotes, admissionNotes, queue
                    .isTemporaryAdmission(), true);
            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("admit.success"));
            saveMessages(request, messages);
        }
        catch (ProgramFullException e) {
            log.error(e);
            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("admit.full"));
            saveMessages(request, messages);
        }
        catch (AdmissionException e) {
            log.error(e);
            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("admit.error", e.getMessage()));
            saveMessages(request, messages);
        }
        catch (ServiceRestrictionException e) {
            throw new RuntimeException(e);
        }

        logManager.log("view", "override service restriction", clientId, request);

        logManager.log("view", "admit to program", clientId, request);

        return view(mapping, form, request, response);
    }
*/
    
/*    
    public ActionForward assign_team_client(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String admissionId = request.getParameter("admissionId");
        String teamId = request.getParameter("teamId");
        String programName = request.getParameter("program_name");
        Admission ad = admissionManager.getAdmission(Integer.valueOf(admissionId));

        ad.setTeamId(Integer.valueOf(teamId));

        admissionManager.saveAdmission(ad);

        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("program.saved", programName));
        saveMessages(request, messages);

        logManager.log("write", "edit program - assign client to team", "", request);
        return view(mapping, form, request, response);
    }

    public ActionForward assign_status_client(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String admissionId = request.getParameter("admissionId");
        String statusId = request.getParameter("clientStatusId");
        String programName = request.getParameter("program_name");
        Admission ad = admissionManager.getAdmission(Integer.valueOf(admissionId));

        ad.setClientStatusId(Integer.valueOf(statusId));

        admissionManager.saveAdmission(ad);

        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("program.saved", programName));
        saveMessages(request, messages);

        logManager.log("write", "edit program - assign client to status", "", request);
        return view(mapping, form, request, response);
    }
*/
    
    public ActionForward batch_discharge(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.info("do batch discharge");
        
        ProgramManagerViewFormBean formBean = (ProgramManagerViewFormBean) form;
        ClientForm clientForm = formBean.getClientForm();

        String message = "";

        // get clients
        String dischargeReason = clientForm.getDischargeReason();
        String communityProgramCode = clientForm.getCommunityProgramCode();
        String providerNo = (String) request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
        
        Enumeration e = request.getParameterNames();
                
        while (e.hasMoreElements()) {
            String name = (String) e.nextElement();
            if (name.startsWith("checked_") && request.getParameter(name).equals("on")) {
                String admissionId = name.substring(8);
                Admission admission = admissionManager.getAdmission(Integer.valueOf(admissionId));
                if (admission == null) {
                    log.warn("admission #" + admissionId + " not found.");
                    continue;
                }

                
                // lets see if there's room first
                Program programToAdmit = null;
                Demographic client = clientManager.getClientByDemographicNo(admission.getClientId().toString());
                if(communityProgramCode.length() > 0){
                	programToAdmit = programManager.getProgram(communityProgramCode);
                
                	if (programToAdmit != null && programToAdmit.getNumOfMembers().intValue() >= programToAdmit.getMaxAllowed().intValue()) {
	                	//client= clientManager.getClientByDemographicNo(admission.getClientId().toString());
	                    message += "Program Full. Cannot admit " + client.getFormattedName() + "\n";
	                    continue;
                	}
                }
                admission.setDischargeDate(Calendar.getInstance());
                admission.setDischargeNotes("Batch discharge");
                admission.setAdmissionStatus(KeyConstants.INTAKE_STATUS_DISCHARGED);
                
                admission.setDischargeReason(dischargeReason);
                admission.setCommunityProgramCode(communityProgramCode);
                
                admission.setTransportationType("");
                admission.setLastUpdateDate(Calendar.getInstance());
                admission.setProviderNo(providerNo);
                
                List lstFamily = intakeManager.getClientFamilyByIntakeId(admission.getIntakeId().toString());
                
                admissionManager.dischargeAdmission(admission, communityProgramCode.equals(""), lstFamily);
                
                message += client.getFormattedName() + " has been discharged.<br>";
                
            }
        }

        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.detail", message));
        saveMessages(request, messages);

        return view(mapping, form, request, response);
        
    }
/*
    public ActionForward reject_from_queue(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String notes = request.getParameter("admission.admissionNotes");
        String programId = request.getParameter("id");
        String clientId = request.getParameter("clientId");
        String rejectionReason = request.getParameter("radioRejectionReason");

        //please write your code without JointAdmission, dawson wrote May 26, 2008  
        List  dependents = null;
        
        log.debug("rejecting from queue: program_id=" + programId + ",clientId=" + clientId);

        programQueueManager.rejectQueue(programId, clientId, notes, rejectionReason);
        
        if (dependents != null){
//            for (Integer l: dependents){
            for (int i=0;i<dependents.size();i++){
            	Integer l = (Integer)dependents.get(i);
                log.debug("rejecting from queue: program_id=" + programId + ",clientId=" + l.intValue());
                programQueueManager.rejectQueue(programId, l.toString(), notes, rejectionReason);
            }
        }

        return view(mapping, form, request, response);
    }
*/
    
/*    
    public ActionForward select_client_for_admit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String programId = request.getParameter("id");
        String clientId = request.getParameter("clientId");
        String queueId = request.getParameter("queueId");

        Program program = programManager.getProgram(String.valueOf(programId));
        ProgramQueue queue = programQueueManager.getProgramQueue(queueId);

        int numMembers = program.getNumOfMembers().intValue();
        int maxMem     = program.getMaxAllowed().intValue();
        int familySize = clientManager.getDependents(Integer.valueOf(clientId)).size();
        //TODO: add warning if this admission ( w/ dependents) will exceed the maxMem 
        
        // If the user is currently enrolled in a bed program, we must warn the provider that this will also be a discharge
        if (program.getType().equalsIgnoreCase("bed") && queue != null && !queue.isTemporaryAdmission()) {
            Admission currentAdmission = admissionManager.getCurrentBedProgramAdmission(Integer.valueOf(clientId));
            if (currentAdmission != null) {
                log.warn("client already in a bed program..doing a discharge/admit if proceeding");
                request.setAttribute("current_admission", currentAdmission);
                Program currentProgram=programManager.getProgram(String.valueOf(currentAdmission.getProgramId()));
                request.setAttribute("current_program", currentProgram);
                
                request.setAttribute("sameFacility", program.getFacilityId()==currentProgram.getFacilityId());
            }
        }
        request.setAttribute("do_admit", Boolean.TRUE);

        return view(mapping, form, request, response);
    }
*/

    public ActionForward select_client_for_reject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("do_reject", Boolean.TRUE);
        return view(mapping, form, request, response);
    }

    //please write your code without JointAdmission if you need call saveReservedBeds(), dawson wrote May 26, 2008  
    public ActionForward saveReservedBeds(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        return view(mapping, form, request, response);
    }
/*    
    public ActionForward saveReservedBeds(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	
        ProgramManagerViewFormBean programManagerViewFormBean = (ProgramManagerViewFormBean) form;

        ActionMessages messages = new ActionMessages();
        Bed[] reservedBeds = programManagerViewFormBean.getReservedBeds();
        List<Integer> familyList = new ArrayList<Integer>();
        boolean isClientDependent = false; 
        boolean isClientFamilyHead = false; 

        Integer shelterId= (Integer)request.getSession().getAttribute("currentFacilityId");

        for (int i=0; reservedBeds != null  &&  i < reservedBeds.length; i++) {
            Bed reservedBed = reservedBeds[i];

            // detect check box false
            if (request.getParameter("reservedBeds[" + i + "].latePass") == null) {
                reservedBed.setLatePass(false);
            }
            // save bed
            try {
                BedDemographic bedDemographic = reservedBed.getBedDemographic();
                //Since bed_check.jsp blocked dependents to have Community programs displayed in dropdown, 
                //  so reservedBed for dependents have communityProgramId == 0
                //When changed to Community program --> how about room_demographic update???
                if(bedDemographic != null) {
                	Integer clientId = bedDemographic.getId().getDemographicNo();
                	
					if(clientId != null){
						isClientDependent = clientManager.isClientDependentOfFamily(clientId);
						isClientFamilyHead = clientManager.isClientFamilyHead(clientId);
					}
					
                	if(clientId == null  ||  isClientDependent){//Forbid saving of this particular bedDemographic record when client is dependent of family
                		//bedManager.saveBed(reservedBed);//should not save bed if dependent                 		
                	}else{//client can be family head or independent
                		
                		if(isClientFamilyHead){
                			familyList.clear();
                			List<JointAdmission> dependentList = clientManager.getDependents(clientId);
							familyList.add(clientId);
							for(int j=0; dependentList != null  &&  j < dependentList.size(); j++){
								familyList.add(Integer.valueOf( dependentList.get(j).getClientId().toString()));
							}

                			for(int k=0; familyList != null  &&  k < familyList.size(); k++){
                				bedDemographic.getId().setDemographicNo(familyList.get(k));
                				
                				BedDemographic dependentBD = bedDemographicManager.getBedDemographicByDemographic(familyList.get(k), facilityId);
                				
                				if(dependentBD != null){
                					bedDemographic.getId().setBedId(dependentBD.getId().getBedId());
                				}
		                		bedManager.saveBed(reservedBed);  
		                		
			                    // save bed demographic
			                    bedDemographicManager.saveBedDemographic(bedDemographic);
			
			                    Integer communityProgramId = reservedBed.getCommunityProgramId();
			
			                    if (communityProgramId > 0) {
			                        try {
			                            // discharge to community program
			                            String providerNo = (String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
			                            admissionManager.processDischargeToCommunity(communityProgramId, bedDemographic.getId().getDemographicNo(), 
			                            		providerNo, "bed reservation ended - manually discharged", "0");
			                        }
			                        catch (AdmissionException e) {
			                            
			                            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("discharge.failure", e.getMessage()));
			                            saveMessages(request, messages);
			                        }
			                    }
                			}
                			
                		}else{//client is indpendent
	                		bedManager.saveBed(reservedBed);   
	                		
		                    // save bed demographic
		                    bedDemographicManager.saveBedDemographic(bedDemographic);
		
		                    Integer communityProgramId = reservedBed.getCommunityProgramId();
		
		                    if (communityProgramId > 0) {
		                        try {
		                            // discharge to community program
		                            String providerNo = (String)request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
		                            admissionManager.processDischargeToCommunity(communityProgramId, bedDemographic.getId().getDemographicNo(), 
		                            		providerNo,
		                                    "bed reservation ended - manually discharged", "0");
		                        }
		                        catch (AdmissionException e) {
		                            
		                            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("discharge.failure", e.getMessage()));
		                            saveMessages(request, messages);
		                        }
		                    }
                		}
                	}
                }
            }
            catch (BedReservedException e) {
                
                messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("bed.reserved.error", e.getMessage()));
                saveMessages(request, messages);
            }
        }//end of for (int i=0; i < reservedBeds.length; i++)

        return view(mapping, form, request, response);
    }
*/
    
    //@SuppressWarnings("unchecked")
    public ActionForward switch_beds(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        /*
    	 *	(1)Check whether both clients are from same program //??? probably not necessary ???
    	 *	(1.1)If not, disallow bed switching
		 *
    	 *	(2)Check whether both beds are from same room:
    	 *	(2.1)If beds are from same room: you can switch beds in any circumstances
    	 *
    	 *	(2.2)If beds are from different rooms:
    	 *	(2.2.1)Only 2 indpendent clients can switch beds between different rooms.
    	 *	(2.2.2)If either client is a dependent, disallow switching beds of different rooms
    	 *	???(2.2.3)If 2 clients are family heads, allow switching beds with different rooms with conditions:
    	 *	(2.2.3.1)all dependents have to switch together ???
    	 *
    	 *	(3)Save changes to the 'bed' table ??? <- not implemented yet
		*/
    	ProgramManagerViewFormBean formBean = (ProgramManagerViewFormBean) form;
        ActionMessages messages = new ActionMessages();
        Bed[] reservedBeds = formBean.getReservedBeds();
        Bed bed1 = null;
        Bed bed2 = null;
        Integer client1 = null;
        Integer client2 = null;
        boolean isSameRoom = false;
        boolean isFamilyHead1 = false;
        boolean isFamilyHead2 = false;
        boolean isFamilyDependent1 = false;
        boolean isFamilyDependent2 = false;
        boolean isIndependent1 = false;
        boolean isIndependent2 = false;
        Integer bedDemographicStatusId1 = null;
        boolean latePass1 = false;
        Date reservationEnd1 = null;
        Date assignEnd1 = null;
        Date today = DateTimeFormatUtils.getToday();
        //List<Integer> familyList = new ArrayList<Integer>();

        Integer shelterId= (Integer)request.getSession().getAttribute(KeyConstants.SESSION_KEY_SHELTERID);

    	String switchBed1 = formBean.getSwitchBed1();
    	String switchBed2 = formBean.getSwitchBed2();
    	
    	if(bedManager == null  ||  bedDemographicManager == null  ||  roomDemographicManager == null){
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("bed.check.error"));
            saveMessages(request, messages);
            return view(mapping, form, request, response);
    	}
    	if(switchBed1 == null  ||  switchBed1.length() <= 0){
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("bed.check.error"));
            saveMessages(request, messages);
            return view(mapping, form, request, response);
    	}
		//System.out.println("ProgramManagerViewAction.switch_beds(): switchBed1 = " + switchBed1);    	
		//System.out.println("ProgramManagerViewAction.switch_beds(): switchBed2 = " + switchBed2);    	
   		bed1 = bedManager.getBed(Integer.valueOf(switchBed1));
   		bed2 = bedManager.getBed(Integer.valueOf(switchBed2));
   		
   		if(bed1 == null  ||  bed2 == null){
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("bed.check.error"));
            saveMessages(request, messages);
            return view(mapping, form, request, response);
   		}
   		BedDemographic bedDemographic1 = bedDemographicManager.getBedDemographicByBed(bed1.getId());
   		BedDemographic bedDemographic2 = bedDemographicManager.getBedDemographicByBed(bed2.getId());
   		
   		if(bedDemographic1 == null  ||  bedDemographic2 == null){
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("bed.check.error"));
            saveMessages(request, messages);
            return view(mapping, form, request, response);
   		}
   		client1 = bedDemographic1.getId().getDemographicNo();
   		client2 = bedDemographic2.getId().getDemographicNo();
   		
        bedDemographicStatusId1 = bedDemographic1.getBedDemographicStatusId();
        latePass1 = bedDemographic1.isLatePass();
        reservationEnd1 = bedDemographic1.getReservationEnd();

		//System.out.println("ProgramManagerViewAction.switch_beds(): client1 = " + client1);    	
		//System.out.println("ProgramManagerViewAction.switch_beds(): client2 = " + client2);    	

   		//Check whether both beds are from same room:
   		if( bed1.getRoomId().intValue() == bed2.getRoomId().intValue() ){
   			isSameRoom = true;
   		}

   		//System.out.println("ProgramManagerViewAction.switch_beds(): isSameRoom = " + isSameRoom);    	
   		if(isSameRoom){//you can switch beds in same room for any client combination
   			bedDemographicManager.deleteBedDemographic(bedDemographic1);
   			bedDemographicManager.deleteBedDemographic(bedDemographic2);

   			bedDemographic1.getId().setDemographicNo(client2);
   			bedDemographic1.setBedDemographicStatusId(bedDemographic2.getBedDemographicStatusId());
   			bedDemographic1.setLatePass(bedDemographic2.isLatePass());
   			bedDemographic1.setReservationStart(today);
   			bedDemographic1.setReservationEnd(bedDemographic2.getReservationEnd());
   			bedDemographic2.getId().setDemographicNo(client1);
   			bedDemographic2.setBedDemographicStatusId(bedDemographicStatusId1);
   			bedDemographic2.setLatePass(latePass1);
   			bedDemographic2.setReservationStart(today);
   			bedDemographic2.setReservationEnd(reservationEnd1);

   			bedDemographicManager.saveBedDemographic(bedDemographic1);
   			bedDemographicManager.saveBedDemographic(bedDemographic2);
   		}else{//beds are from different rooms

   			//please overwrite my code below. For family intake, suppose get from intakeManager. dawson May 26, 2008
   			isFamilyHead1 = false;
   			isFamilyHead2 = false;
   			isFamilyDependent1 = false;
   			isFamilyDependent2 = false;
/*
   			isFamilyHead1 = clientManager.isClientFamilyHead(client1);
   			isFamilyHead2 = clientManager.isClientFamilyHead(client2);
   			isFamilyDependent1 = clientManager.isClientDependentOfFamily(client1);
   			isFamilyDependent2 = clientManager.isClientDependentOfFamily(client2);
*/
   			
			//System.out.println("ProgramManagerViewAction.switch_beds(): isFamilyHead1 = " + isFamilyHead1);    	
			//System.out.println("ProgramManagerViewAction.switch_beds(): isFamilyHead2 = " + isFamilyHead2);    	
			//System.out.println("ProgramManagerViewAction.switch_beds(): isFamilyDependent1 = " + isFamilyDependent1);    	
			//System.out.println("ProgramManagerViewAction.switch_beds(): isFamilyDependent2 = " + isFamilyDependent2);    	
		
   			RoomDemographic roomDemographic1 = roomDemographicManager.getRoomDemographicByDemographic(client1);
   			RoomDemographic roomDemographic2 = roomDemographicManager.getRoomDemographicByDemographic(client2);
   			
   			if(roomDemographic1 == null  ||  roomDemographic2 == null){
   	            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("bed.check.error"));
   	            saveMessages(request, messages);
   	            return view(mapping, form, request, response);
   			}
   			
   			if(!isFamilyHead1  &&  !isFamilyDependent1){
   				isIndependent1 = true;
   			}
   			if(!isFamilyHead2  &&  !isFamilyDependent2){
   				isIndependent2 = true;
   			}

   			//System.out.println("ProgramManagerViewAction.switch_beds(): isIndependent1 = " + isIndependent1);    	
   			//System.out.println("ProgramManagerViewAction.switch_beds(): isIndependent2 = " + isIndependent2);    	
   			//Check whether both clients are indpendents
   			if(isIndependent1  &&  isIndependent2){
   				//Can switch beds and rooms
   	   			bedDemographicManager.deleteBedDemographic(bedDemographic1);
   	   			bedDemographicManager.deleteBedDemographic(bedDemographic2);
   	   			
   	   			bedDemographic1.getId().setDemographicNo(client2);
   	   			bedDemographic1.setBedDemographicStatusId(bedDemographic2.getBedDemographicStatusId());
   	   			bedDemographic1.setLatePass(bedDemographic2.isLatePass());
   	   			bedDemographic1.setReservationStart(today);
   	   			bedDemographic1.setReservationEnd(bedDemographic2.getReservationEnd());
   	   			bedDemographic2.getId().setDemographicNo(client1);
   	   			bedDemographic2.setBedDemographicStatusId(bedDemographicStatusId1);
   	   			bedDemographic2.setLatePass(latePass1);
   	   			bedDemographic2.setReservationStart(today);
   	   			bedDemographic2.setReservationEnd(reservationEnd1);
   	   			
   	   			bedDemographicManager.saveBedDemographic(bedDemographic1);
   	   			bedDemographicManager.saveBedDemographic(bedDemographic2);

   	   			roomDemographicManager.deleteRoomDemographic(roomDemographic1);
   	   			roomDemographicManager.deleteRoomDemographic(roomDemographic2);
   	   			
   	   		    assignEnd1 = roomDemographic1.getAssignEnd();
   	   			roomDemographic1.getId().setDemographicNo(client2);
   	   		    roomDemographic1.setAssignStart(today);
   	   		    roomDemographic1.setAssignEnd(roomDemographic2.getAssignEnd());
   	   			roomDemographic2.getId().setDemographicNo(client1);
   	   		    roomDemographic2.setAssignStart(today);
   	   		    roomDemographic2.setAssignEnd(assignEnd1);

   	   			roomDemographicManager.saveRoomDemographic(roomDemographic1);
   	   			roomDemographicManager.saveRoomDemographic(roomDemographic2);
   			}else{
   				if(isFamilyDependent1  ||  isFamilyDependent2){//if either client is dependent or both are
   					//do not allow bed switching
   		            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("bed.check.dependent_disallowed"));
   		            saveMessages(request, messages);
   		            return view(mapping, form, request, response);
   				}
   				if(isFamilyHead1  ||  isFamilyHead2){//if either clients are family head
   					// very complicated!!!
   		            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("bed.check.familyHead_switch"));
   		            saveMessages(request, messages);
   		            return view(mapping, form, request, response);
   				}
   			}
   		}
        return view(mapping, form, request, response);
    }
    
    //@Required
    public void setClientRestrictionManager(ClientRestrictionManager clientRestrictionManager) {
        this.clientRestrictionManager = clientRestrictionManager;
    }

    public void setCaseManagementManager(CaseManagementManager caseManagementManager) {
    	this.caseManagementManager = caseManagementManager;
    }

    public void setAdmissionManager(AdmissionManager mgr) {
    	this.admissionManager = mgr;
    }

    public void setBedDemographicManager(BedDemographicManager demographicBedManager) {
    	this.bedDemographicManager = demographicBedManager;
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
	    SecurityManager sec = (SecurityManager)request.getSession().getAttribute(KeyConstants.SESSION_KEY_SECURITY_MANAGER);
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