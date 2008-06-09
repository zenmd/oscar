package org.oscarehr.PMmodule.web;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;
import org.oscarehr.PMmodule.model.BedDemographic;
import org.oscarehr.PMmodule.model.Provider;
import org.oscarehr.PMmodule.model.Room;
import org.oscarehr.PMmodule.model.RoomDemographic;
import org.oscarehr.PMmodule.service.AdmissionManager;
import org.oscarehr.PMmodule.service.BedDemographicManager;
import org.oscarehr.PMmodule.service.ClientManager;
import org.oscarehr.PMmodule.service.ProgramManager;
import org.oscarehr.PMmodule.service.ProviderManager;
import org.oscarehr.PMmodule.service.RoomDemographicManager;
import org.oscarehr.PMmodule.service.RoomManager;
import org.oscarehr.PMmodule.web.formbean.ClientManagerFormBean;
import org.oscarehr.casemgmt.service.CaseManagementManager;
import com.quatro.common.KeyConstants;

public class QuatroClientHistoryAction  extends DispatchAction {
   private ClientManager clientManager;
   private ProviderManager providerManager;
   private ProgramManager programManager;
   private AdmissionManager admissionManager;
   private CaseManagementManager caseManagementManager;
   private BedDemographicManager bedDemographicManager;
   private RoomDemographicManager roomDemographicManager;
   private RoomManager roomManager;


   public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       return edit(mapping, form, request, response);
   }
   
   public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
       return mapping.findForward("edit");
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
