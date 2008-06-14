package org.oscarehr.PMmodule.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.oscarehr.PMmodule.model.Admission;
import org.oscarehr.PMmodule.model.BedDemographic;
import org.oscarehr.PMmodule.model.Demographic;
import org.oscarehr.PMmodule.model.HealthSafety;
import org.oscarehr.PMmodule.model.Provider;
import org.oscarehr.PMmodule.model.QuatroIntakeHeader;
import org.oscarehr.PMmodule.model.Room;
import org.oscarehr.PMmodule.model.RoomDemographic;
import org.oscarehr.PMmodule.service.AdmissionManager;
import org.oscarehr.PMmodule.service.BedDemographicManager;
import org.oscarehr.PMmodule.service.ClientManager;
import org.oscarehr.PMmodule.service.HealthSafetyManager;
import org.oscarehr.PMmodule.service.ProgramManager;
import org.oscarehr.PMmodule.service.RoomDemographicManager;
import org.oscarehr.PMmodule.service.RoomManager;
import org.oscarehr.PMmodule.service.LogManager;

import com.quatro.common.KeyConstants;
import com.quatro.service.IntakeManager;

public class QuatroClientSummaryAction extends BaseClientAction {

   private HealthSafetyManager healthSafetyManager;
   private ClientManager clientManager;
   private ProgramManager programManager;
   private AdmissionManager admissionManager;
   private BedDemographicManager bedDemographicManager;
   private RoomDemographicManager roomDemographicManager;
   private RoomManager roomManager;
   private IntakeManager intakeManager;
   private LogManager logManager;
   public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       return edit(mapping, form, request, response);
   }

   public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       super.setScreenMode(request, KeyConstants.TAB_CLIENT_SUMMARY);
       HashMap actionParam = (HashMap) request.getAttribute("actionParam");
       if(actionParam==null){
    	  actionParam = new HashMap();
          actionParam.put("clientId", request.getParameter("clientId")); 
//        don't delete sample code below for html:link parameter       
//        actionParam.put("id", "200492"); 
//        actionParam.put("floatProperty", new Float(444.0)); 
//        actionParam.put("intProperty", new Integer(555)); 
//        actionParam.put("stringArray", new String[] { "Value 1", "Value 2", "Value 3" }); 
       }

       request.setAttribute("actionParam", actionParam);

       String demographicNo= (String)actionParam.get("clientId");

       request.getSession().setAttribute(KeyConstants.SESSION_KEY_CURRENT_MODULE, KeyConstants.MODULE_ID_CLIENT);
       
       setEditAttributes(form, request, demographicNo);
       logManager.log("read", "client", demographicNo, request);

       return mapping.findForward("edit");
   }

   private void setEditAttributes(ActionForm form, HttpServletRequest request, String demographicNo) {
       
       Integer shelterId=(Integer)request.getSession().getAttribute(KeyConstants.SESSION_KEY_SHELTERID);
       
       request.setAttribute("clientId", demographicNo);
       request.setAttribute("client", clientManager.getClientByDemographicNo(demographicNo));

       String providerNo = ((Provider) request.getSession().getAttribute("provider")).getProviderNo();
       
       List lst = intakeManager.getQuatroIntakeHeaderListByFacility(Integer.valueOf(demographicNo), shelterId, providerNo);
//       for(Object element: lst){
       for(int i=0;i<lst.size();i++){
    	 QuatroIntakeHeader obj = (QuatroIntakeHeader)lst.get(i);
    	 //One client should have no more than one bed program intake in one facility.
    	 //One facility may have more than one bed program, but we don't allow any client
    	 //to register muitiple bed programs in one facility.
    	 if((obj.getIntakeStatus().equals(KeyConstants.INTAKE_STATUS_ACTIVE) ||
    		obj.getIntakeStatus().equals(KeyConstants.INTAKE_STATUS_ADMITTED)) && 
    		obj.getProgramType().equals(KeyConstants.BED_PROGRAM_TYPE)){
            List lst2 = intakeManager.getClientFamilyByIntakeId(obj.getId().toString());
    		request.setAttribute("family", lst2);
   		    break;
    	 }
       }
/*       
       // check role permission
       HttpSession se = request.getSession();
       List admissions = admissionManager.getCurrentAdmissions(Integer.valueOf(demographicNo));
       for (Iterator it = admissions.iterator(); it.hasNext();) {
           Admission admission = (Admission) it.next();
           String inProgramId = String.valueOf(admission.getProgramId());
           String inProgramType = admission.getProgramType();
           if ("service".equalsIgnoreCase(inProgramType)) {
               se.setAttribute("performDischargeService", new Boolean(caseManagementManager.hasAccessRight("perform discharges", "access", providerNo, demographicNo, inProgramId)));
               se.setAttribute("performAdmissionService", new Boolean(caseManagementManager.hasAccessRight("perform admissions", "access", providerNo, demographicNo, inProgramId)));

           }
           else if ("bed".equalsIgnoreCase(inProgramType)) {
               se.setAttribute("performDischargeBed", new Boolean(caseManagementManager.hasAccessRight("perform discharges", "access", providerNo, demographicNo, inProgramId)));
               se.setAttribute("performAdmissionBed", new Boolean(caseManagementManager.hasAccessRight("perform admissions", "access", providerNo, demographicNo, inProgramId)));
               se.setAttribute("performBedAssignments", new Boolean(caseManagementManager.hasAccessRight("perform bed assignments", "access", providerNo, demographicNo, inProgramId)));

           }
       }
*/

           // only allow bed/service programs show up.(not external program)
           List currentAdmissionList = admissionManager.getCurrentAdmissions(Integer.valueOf(demographicNo),providerNo, shelterId);
           List bedServiceList = new ArrayList();
           for (Iterator ad = currentAdmissionList.iterator(); ad.hasNext();) {
               Admission admission1 = (Admission) ad.next();
               if ("External".equalsIgnoreCase(programManager.getProgram(admission1.getProgramId()).getType())) {
                   continue;
               }
               bedServiceList.add(admission1);
           }
           request.setAttribute("admissions", bedServiceList);

           HealthSafety healthsafety = healthSafetyManager.getHealthSafetyByDemographic(Integer.valueOf(demographicNo));
           request.setAttribute("healthsafety", healthsafety);

           request.setAttribute("referrals", clientManager.getActiveReferrals(demographicNo, String.valueOf(shelterId)));
//       }

           
//       List<?> currentAdmissions = admissionManager.getCurrentAdmissions(Integer.valueOf(demographicNo));

       /* bed reservation view */
       BedDemographic bedDemographic = bedDemographicManager.getBedDemographicByDemographic(Integer.valueOf(demographicNo));
       request.setAttribute("bedDemographic", bedDemographic);
       
       RoomDemographic roomDemographic = roomDemographicManager.getRoomDemographicByDemographic(Integer.valueOf(demographicNo));

		if(roomDemographic != null){
			Integer roomIdInt = roomDemographic.getId().getRoomId();
			Room room = null;
			if(roomIdInt != null){
				room = roomManager.getRoom(roomIdInt);
			}
			if(room != null){
				roomDemographic.setRoom(room);
			}
		}
		request.setAttribute("roomDemographic", roomDemographic);
		
   }
   
   public void setHealthSafetyManager(HealthSafetyManager healthSafetyManager) {
	 this.healthSafetyManager = healthSafetyManager;
   }


   public void setAdmissionManager(AdmissionManager admissionManager) {
	 this.admissionManager = admissionManager;
   }

   public void setBedDemographicManager(BedDemographicManager bedDemographicManager) {
	 this.bedDemographicManager = bedDemographicManager;
   }

   public void setClientManager(ClientManager clientManager) {
	 this.clientManager = clientManager;
   }

   public void setProgramManager(ProgramManager programManager) {
	 this.programManager = programManager;
   }

   public void setRoomDemographicManager(RoomDemographicManager roomDemographicManager) {
	 this.roomDemographicManager = roomDemographicManager;
   }

   public void setRoomManager(RoomManager roomManager) {
 	 this.roomManager = roomManager;
   }

   public void setIntakeManager(IntakeManager intakeManager) {
	 this.intakeManager = intakeManager;
   }

public void setLogManager(LogManager logManager) {
	this.logManager = logManager;
}

   
}
