package org.oscarehr.PMmodule.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.DynaActionForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;
import org.oscarehr.PMmodule.model.Admission;
import org.oscarehr.PMmodule.model.BedDemographic;
import org.oscarehr.PMmodule.model.Demographic;
import org.oscarehr.PMmodule.model.DemographicExt;
import org.oscarehr.PMmodule.model.JointAdmission;
import org.oscarehr.PMmodule.model.Program;
import org.oscarehr.PMmodule.model.ProgramProvider;
import org.oscarehr.PMmodule.model.Provider;
import org.oscarehr.PMmodule.model.Room;
import org.oscarehr.PMmodule.model.RoomDemographic;
import org.oscarehr.PMmodule.model.Demographic.ConsentGiven;
import org.oscarehr.PMmodule.service.AdmissionManager;
import org.oscarehr.PMmodule.service.BedDemographicManager;
import org.oscarehr.PMmodule.service.ClientManager;
import org.oscarehr.PMmodule.service.ProgramManager;
import org.oscarehr.PMmodule.service.ProviderManager;
import org.oscarehr.PMmodule.service.RoomDemographicManager;
import org.oscarehr.PMmodule.service.RoomManager;
import org.oscarehr.PMmodule.web.formbean.ClientManagerFormBean;
import org.oscarehr.casemgmt.service.CaseManagementManager;
import org.oscarehr.util.SessionConstants;
import org.oscarehr.PMmodule.model.QuatroComplaint;
import org.oscarehr.PMmodule.model.QuatroComplaintOutcome;
import org.apache.struts.util.LabelValueBean;
import org.oscarehr.PMmodule.web.formbean.QuatroClientComplaintForm;

import oscar.oscarDemographic.data.DemographicRelationship;

public class QuatroClientComplaintAction  extends DispatchAction {
   private ClientManager clientManager;
   private ProviderManager providerManager;
   private ProgramManager programManager;
   private AdmissionManager admissionManager;
   private CaseManagementManager caseManagementManager;
   private BedDemographicManager bedDemographicManager;
   private RoomDemographicManager roomDemographicManager;
   private RoomManager roomManager;

   public static final String ID = "id";

   public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
/*
	   QuatroClientComplaintForm clientForm = (QuatroClientComplaintForm) form;
       Integer clientId = (Integer)request.getSession().getAttribute("clientId");
       if (clientId != null) request.setAttribute(ID, clientId.toString());
*/       
       return edit(mapping, form, request, response);
   }
   
   public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
/*
	   String id = request.getParameter("id");

       if (id == null || id.equals("")) {
           Object o = request.getAttribute("demographicNo");

           if (o instanceof String) {
               id = (String) o;
           }

           if (o instanceof Long) {
               id = String.valueOf((Long) o);
           }
       }
       if (id == null || id.equals("")) {
       	  id=(String) request.getAttribute(ID);
       }
*/
       setEditAttributes(form, request);

       return mapping.findForward("edit");
   }
   
   private void setEditAttributes(ActionForm form, HttpServletRequest request) {
	   QuatroClientComplaintForm clientForm = (QuatroClientComplaintForm) form;
       
       HashMap actionParam = (HashMap) request.getAttribute("actionParam");
       if(actionParam==null){
    	  actionParam = new HashMap();
          actionParam.put("id", request.getParameter("id")); 
       }
       request.setAttribute("actionParam", actionParam);
       String demographicNo= (String)actionParam.get("id");
       
       QuatroComplaint complaint = new QuatroComplaint();
       ArrayList<LabelValueBean> accessToShelters = new ArrayList<LabelValueBean>();
       accessToShelters.add(new LabelValueBean("Admission and Discharge", "1"));
       accessToShelters.add(new LabelValueBean("Bed Registration for Incoming and Registed Residents", "2"));
       accessToShelters.add(new LabelValueBean("Occupied Bed", "3"));
       accessToShelters.add(new LabelValueBean("Overnight Passes and leaves with Permission", "4"));
       accessToShelters.add(new LabelValueBean("Substance Use", "5"));
       accessToShelters.add(new LabelValueBean("Service Restrions(Barrings)", "6"));
       accessToShelters.add(new LabelValueBean("Meeting the Needs of Transgendered/Transsexual/Two-spirited Redidents", "7"));
       complaint.setAccessToShelters(accessToShelters);

       ArrayList<LabelValueBean> healthSafetys = new ArrayList<LabelValueBean>();
       healthSafetys.add(new LabelValueBean("Health Standards", "1"));
       healthSafetys.add(new LabelValueBean("Safety Standards", "2"));
       healthSafetys.add(new LabelValueBean("Resident Medication", "3"));
       healthSafetys.add(new LabelValueBean("Weapons", "4"));
       complaint.setHealthSafetys(healthSafetys);
       
       ArrayList<LabelValueBean> organizationStandards = new ArrayList<LabelValueBean>();
       organizationStandards.add(new LabelValueBean("Orginizational Status for Purchase-of Service Shelter", "1"));
       organizationStandards.add(new LabelValueBean("Governmance", "2"));
       organizationStandards.add(new LabelValueBean("Financial Accountability", "3"));
       organizationStandards.add(new LabelValueBean("Program Accountability", "4"));
       organizationStandards.add(new LabelValueBean("Conflict of Interest", "5"));
       complaint.setOrganizationStandards(organizationStandards);

       ArrayList<LabelValueBean> programStandards = new ArrayList<LabelValueBean>();
       programStandards.add(new LabelValueBean("Provision of Essential Services", "1"));
       programStandards.add(new LabelValueBean("Conselling Supportants", "2"));
       programStandards.add(new LabelValueBean("Daytime Access", "3"));
       programStandards.add(new LabelValueBean("Services to Children", "4"));
       programStandards.add(new LabelValueBean("Duty to Report Suspected Cases of Child Abuse", "5"));
       programStandards.add(new LabelValueBean("Confidentiality", "6"));
       programStandards.add(new LabelValueBean("Sharing of Resident Information", "7"));
       programStandards.add(new LabelValueBean("Resident Information and Resident Files", "8"));
       programStandards.add(new LabelValueBean("Staff Code of Conduct", "9"));
       complaint.setProgramStandards(programStandards);
       
       ArrayList<LabelValueBean> residentRights = new ArrayList<LabelValueBean>();
       residentRights.add(new LabelValueBean("Resident Input", "1"));
       residentRights.add(new LabelValueBean("Complaints and Appeals", "2"));
       complaint.setResidentRights(residentRights);

       clientForm.setComplaint(complaint);

       
       QuatroComplaintOutcome outcome = new QuatroComplaintOutcome();
       clientForm.setOutcome(outcome);
       
       ArrayList<LabelValueBean> titleList = new ArrayList<LabelValueBean>();
       titleList.add(new LabelValueBean("Mr.", "1"));
       titleList.add(new LabelValueBean("Ms.", "2"));
       clientForm.setTitleList(titleList);

       ArrayList<LabelValueBean> radioStatuses = new ArrayList<LabelValueBean>();
       radioStatuses.add(new LabelValueBean("Completed", "1"));
       radioStatuses.add(new LabelValueBean("In Process", "2"));
       clientForm.setRadioStatuses(radioStatuses);
       
       ArrayList<LabelValueBean> radioYNs = new ArrayList<LabelValueBean>();
       radioYNs.add(new LabelValueBean("Yes", "1"));
       radioYNs.add(new LabelValueBean("No", "0"));
       clientForm.setRadioYNs(radioYNs);
       
       ArrayList<LabelValueBean> outcomeSatisfiedList = new ArrayList<LabelValueBean>();
       outcomeSatisfiedList.add(new LabelValueBean("Partial", "1"));
       outcomeSatisfiedList.add(new LabelValueBean("Unknown-No complainant follow-up", "2"));
       outcomeSatisfiedList.add(new LabelValueBean("Yes", "3"));
       outcomeSatisfiedList.add(new LabelValueBean("Not Applicable", "4"));
       outcomeSatisfiedList.add(new LabelValueBean("No", "5"));
       clientForm.setOutcomeSatisfiedList(outcomeSatisfiedList);

       ArrayList<LabelValueBean> sourceComplaintList = new ArrayList<LabelValueBean>();
       sourceComplaintList.add(new LabelValueBean("layor's office", "1"));
       sourceComplaintList.add(new LabelValueBean("Agency/Orgnization", "2"));
       sourceComplaintList.add(new LabelValueBean("W/ODSP Worker", "3"));
       sourceComplaintList.add(new LabelValueBean("Community Member", "4"));
       sourceComplaintList.add(new LabelValueBean("lient", "5"));
       sourceComplaintList.add(new LabelValueBean("Police", "6"));
       sourceComplaintList.add(new LabelValueBean("Family Doctor", "7"));
       sourceComplaintList.add(new LabelValueBean("Other", "8"));
       sourceComplaintList.add(new LabelValueBean("Family Member", "9"));
       sourceComplaintList.add(new LabelValueBean("Councillor's Office /Politician", "10"));
       sourceComplaintList.add(new LabelValueBean("Shelter Staff", "11"));
       sourceComplaintList.add(new LabelValueBean("Advocate/Advocacy Group", "12"));
       sourceComplaintList.add(new LabelValueBean("Agency Worker", "13"));
       clientForm.setSourceComplaintList(sourceComplaintList);
       

       ArrayList<LabelValueBean> methodContactList = new ArrayList<LabelValueBean>();
       methodContactList.add(new LabelValueBean("In-Person", "1"));
       methodContactList.add(new LabelValueBean("Letter", "2"));
       methodContactList.add(new LabelValueBean("Fax", "3"));
       methodContactList.add(new LabelValueBean("E-mail", "4"));
       methodContactList.add(new LabelValueBean("Telephone", "5"));
       clientForm.setMethodContactList(methodContactList);

   }

   public void setAdmissionManager(AdmissionManager admissionManager) {
	 this.admissionManager = admissionManager;
   }

   public void setBedDemographicManager(BedDemographicManager bedDemographicManager) {
	 this.bedDemographicManager = bedDemographicManager;
   }

   public void setCaseManagementManager(CaseManagementManager caseManagementManager) {
	 this.caseManagementManager = caseManagementManager;
   }

   public void setClientManager(ClientManager clientManager) {
	 this.clientManager = clientManager;
   }

   public void setProgramManager(ProgramManager programManager) {
	 this.programManager = programManager;
   }

   public void setProviderManager(ProviderManager providerManager) {
	 this.providerManager = providerManager;
   }

   public void setRoomDemographicManager(RoomDemographicManager roomDemographicManager) {
	 this.roomDemographicManager = roomDemographicManager;
   }

   public void setRoomManager(RoomManager roomManager) {
	 this.roomManager = roomManager;
   }
   
}
