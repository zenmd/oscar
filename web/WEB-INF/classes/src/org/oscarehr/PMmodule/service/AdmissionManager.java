package org.oscarehr.PMmodule.service;

import java.util.Date;
import java.util.List;

import com.quatro.dao.IntakeDao;
import org.oscarehr.PMmodule.dao.AdmissionDao;
import org.oscarehr.PMmodule.dao.ClientReferralDAO;
import org.oscarehr.PMmodule.dao.ProgramDao;
import org.oscarehr.PMmodule.dao.ProgramQueueDao;
import org.oscarehr.PMmodule.dao.RoomDemographicDAO;
import org.oscarehr.PMmodule.dao.BedDemographicDAO;
import org.oscarehr.PMmodule.exception.AdmissionException;


import org.oscarehr.PMmodule.model.Admission;
import org.oscarehr.PMmodule.model.ClientReferral;
import org.oscarehr.PMmodule.model.Program;
import org.oscarehr.PMmodule.model.ProgramQueue;
import org.oscarehr.PMmodule.model.QuatroIntake;
import org.oscarehr.PMmodule.model.RoomDemographic;
import org.oscarehr.PMmodule.model.RoomDemographicPK;
import org.oscarehr.PMmodule.model.BedDemographic;
import org.oscarehr.PMmodule.model.BedDemographicPK;
import org.oscarehr.PMmodule.web.formbean.ClientForm;

import com.quatro.common.KeyConstants;

public class AdmissionManager {
	private IntakeDao intakeDao;
	private ProgramDao programDao;
	private AdmissionDao admissionDao;
	private RoomDemographicDAO roomDemographicDAO;
	private BedDemographicDAO bedDemographicDAO;
	private ProgramQueueDao programQueueDao;
	private ClientReferralDAO clientReferralDAO;

	public List getAdmissionsByFacility(Integer demographicNo, Integer facilityId) {
		return admissionDao.getAdmissionsByFacility(demographicNo, facilityId);
	}
	
	public void saveAdmission(Admission admission, Integer intakeId, Integer queueId, 
    		Integer referralId, RoomDemographic roomDemographic, BedDemographic bedDemographic, boolean bFamily) {
    	intakeDao.setIntakeStatusAdmitted(intakeId);
		admissionDao.saveAdmission(admission, intakeId, queueId, referralId);
    	
    	//remove old room/bed assignment
    	RoomDemographic rdm = roomDemographicDAO.getRoomDemographicByDemographic(roomDemographic.getId().getDemographicNo());
    	BedDemographic bdm = bedDemographicDAO.getBedDemographicByDemographic(bedDemographic.getId().getDemographicNo());
    	if(bFamily){
    	   if(rdm!=null && !rdm.getId().getRoomId().equals(roomDemographic.getId().getRoomId())){
         	  roomDemographicDAO.deleteRoomDemographic(rdm);
         	  if(bdm!=null) bedDemographicDAO.deleteBedDemographic(bdm);
        	  roomDemographicDAO.saveRoomDemographic(roomDemographic);
    	   }
    	}else{
    	  if(!(rdm!=null && bdm!=null &&
    	    rdm.getId().getRoomId().equals(roomDemographic.getId().getRoomId()) &&
    	    bdm.getId().getBedId().equals(bedDemographic.getId().getBedId()))){
      	    if(rdm!=null) roomDemographicDAO.deleteRoomDemographic(rdm);
     	    if(bdm!=null) bedDemographicDAO.deleteBedDemographic(bdm);

     	    roomDemographicDAO.saveRoomDemographic(roomDemographic);
    	    bedDemographicDAO.saveBedDemographic(bedDemographic);
    	  }
    	}
    	
    }

    public void dischargeAdmission(String admissionIds) {
    	admissionDao.dischargeAdmission(admissionIds);
    }
    
    public void updateAdmission(Admission admission, RoomDemographic roomDemographic, BedDemographic bedDemographic, boolean bFamily) {
    	admissionDao.updateAdmission(admission);
    	//remove old room/bed assignment
    	RoomDemographic rdm = roomDemographicDAO.getRoomDemographicByDemographic(roomDemographic.getId().getDemographicNo());
    	BedDemographic bdm = bedDemographicDAO.getBedDemographicByDemographic(bedDemographic.getId().getDemographicNo());
    	if(bFamily){
    	   if(rdm!=null && !rdm.getId().getRoomId().equals(roomDemographic.getId().getRoomId())){
         	  roomDemographicDAO.deleteRoomDemographic(rdm);
         	  if(bdm!=null) bedDemographicDAO.deleteBedDemographic(bdm);
        	  roomDemographicDAO.saveRoomDemographic(roomDemographic);
    	   }
    	}else{
    	  if(!(rdm!=null && bdm!=null &&
    	    rdm.getId().getRoomId().equals(roomDemographic.getId().getRoomId()) &&
    	    bdm.getId().getBedId().equals(bedDemographic.getId().getBedId()))){
      	    if(rdm!=null) roomDemographicDAO.deleteRoomDemographic(rdm);
     	    if(bdm!=null) bedDemographicDAO.deleteBedDemographic(bdm);

     	    roomDemographicDAO.saveRoomDemographic(roomDemographic);
    	    bedDemographicDAO.saveBedDemographic(bedDemographic);
    	  }else if(rdm==null && bdm==null){
       	    roomDemographicDAO.saveRoomDemographic(roomDemographic);
    	    bedDemographicDAO.saveBedDemographic(bedDemographic);
    	  }
    	}
    }

    public void setAdmissionDao(AdmissionDao admissionDao) {
		this.admissionDao = admissionDao;
	}

	public List getIntakeAdmissionList(Integer clientId) {
		return admissionDao.getIntakeAdmissionList(clientId);
	}

	public List getAdmissionList(Integer clientId, Integer facilityId, String providerNo) {
		return admissionDao.getAdmissionList(clientId, facilityId, providerNo);
	}
	
	public List getAdmissionListByProgram(Integer programId) {
		return admissionDao.getAdmissionListByProgram(programId);
	}
	public List getClientsListByProgram(Integer programId, ClientForm form) {
		return admissionDao.getClientsListByProgram(programId, form);
	}
	public List getClientsListByProgram(Program program, ClientForm form) {
		return admissionDao.getClientsListByProgram2(program, form);
	}
	
    public Admission getAdmissionByIntakeId(Integer intakeId){
		return admissionDao.getAdmissionByIntakeId(intakeId);
    }

    public Admission getAdmissionByAdmissionId(Integer admissionId){
		return admissionDao.getAdmissionByAdmissionId(admissionId);
    }
    
    public List<Admission> getCurrentAdmissionsByFacility(Integer demographicNo, Integer facilityId) {
		return admissionDao.getCurrentAdmissionsByFacility(demographicNo, facilityId);
    }

	public void setBedDemographicDAO(BedDemographicDAO bedDemographicDao) {
		this.bedDemographicDAO = bedDemographicDao;
	}

	public void setRoomDemographicDAO(RoomDemographicDAO roomDemographicDao) {
		this.roomDemographicDAO = roomDemographicDao;
	}

	public void setIntakeDao(IntakeDao intakeDao) {
		this.intakeDao = intakeDao;
	}

	public Admission getRecentAdmissionByFacility(Integer clientId,Integer facilityId){
		return admissionDao.getRecentAdmissionByFacility(clientId, facilityId);
	}
	
	public Admission getCurrentBedProgramAdmission(Integer demographicNo) {
		return admissionDao.getCurrentBedProgramAdmission(programDao, demographicNo);
	}

	public void setProgramDao(ProgramDao programDao) {
		this.programDao = programDao;
	}
	
	public List getCurrentAdmissionsByProgramId(String programId) {
		return admissionDao.getCurrentAdmissionsByProgramId(Integer.valueOf(programId));
	}
	
    public Admission getAdmission(Integer id) {
		return admissionDao.getAdmission(id);
	}

	public void saveAdmission(Admission admission) {
		admissionDao.saveAdmission(admission);		
	}
    
    public void processDischargeToCommunity(Integer communityProgramId, Integer demographicNo, String providerNo, String notes, String radioDischargeReason) throws AdmissionException {
        processDischargeToCommunity(communityProgramId,demographicNo,providerNo,notes,radioDischargeReason,null);
    }
    
    public void processDischargeToCommunity(Integer communityProgramId, Integer demographicNo, String providerNo, String notes, String radioDischargeReason,List<Integer> dependents) throws AdmissionException {
/*
       Admission currentBedAdmission = getCurrentBedProgramAdmission(demographicNo);

       Program program=programDao.getProgram(communityProgramId);
       Integer facilityId=null;
       if (program!=null) facilityId=(int)program.getFacilityId();
    
	   if (currentBedAdmission != null) {
		  processDischarge(currentBedAdmission.getProgramId(), demographicNo, notes, radioDischargeReason);
		
		  BedDemographic bedDemographic = bedDemographicManager.getBedDemographicByDemographic(demographicNo, facilityId);
		
		  if (bedDemographic != null) {
			bedDemographicManager.deleteBedDemographic(bedDemographic);
		  }
	  }

	  Admission currentCommunityAdmission = getCurrentCommunityProgramAdmission(demographicNo);

  	  if (currentCommunityAdmission != null) {
		processDischarge(currentCommunityAdmission.getProgramId(), demographicNo, notes, radioDischargeReason);
	  }

	  // Create and save admission object
	  Admission admission = new Admission();
	  admission.setAdmissionDate(new Date());
	  admission.setAdmissionNotes(notes);
	  admission.setAdmissionStatus(Admission.STATUS_CURRENT);
	  admission.setClientId(demographicNo);
	  admission.setProgramId(communityProgramId);
	  admission.setProviderNo(providerNo);
	  admission.setTeamId(0);
	  admission.setTemporaryAdmission(false);
	  admission.setRadioDischargeReason(radioDischargeReason);
	  admission.setClientStatusId(0);
	  saveAdmission(admission);
            
      if (dependents != null){
         for(Integer l:dependents){
            processDischargeToCommunity(communityProgramId,new Integer(l.intValue()),providerNo, notes, radioDischargeReason,null);
        }
      }
*/      
  }
    
  public List getCurrentAdmissions(Integer demographicNo) {
	return admissionDao.getCurrentAdmissions(demographicNo);
  }

  public void updateDischargeInfo(Admission admission){
	  admissionDao.updateDischargeInfo(admission);
  }
  
  public void saveAdmission(Admission admission,boolean isReferral){
		 admissionDao.saveAdmission(admission);
		 if(isReferral){
	    	ClientReferral referral = new ClientReferral();
	        referral.setClientId(admission.getClientId());
	        referral.setNotes("Discharge Automated referral");
	        referral.setProgramId(admission.getBedProgramId().intValue());
	        referral.setProviderNo(admission.getProviderNo());
	        referral.setReferralDate(new Date());
	        referral.setStatus(KeyConstants.STATUS_ACTIVE);	        
	        
	        ProgramQueue queue = new ProgramQueue();
	        
	          queue.setClientId(referral.getClientId());
	          queue.setNotes(referral.getNotes());
	          queue.setProgramId(referral.getProgramId());
	          queue.setProviderNo(Integer.parseInt(referral.getProviderNo()));
	          queue.setReferralDate(referral.getReferralDate());
	          queue.setStatus(KeyConstants.STATUS_ACTIVE);
	          queue.setReferralId(referral.getId());
	          queue.setTemporaryAdmission(referral.isTemporaryAdmission());
	          queue.setPresentProblems(referral.getPresentProblems());
	        
	          //delete old referral and queue records linked to this intake
	         
	          QuatroIntake intake=intakeDao.getQuatroIntake(admission.getIntakeId());
			    if(intake.getReferralId() != null &&  intake.getReferralId().intValue()>0){
			      ClientReferral referralOld = new ClientReferral(Integer.valueOf(intake.getReferralId().intValue()));
			      referralOld.setClientId(intake.getClientId());
			      referralOld.setProgramId(Integer.valueOf(intake.getProgramId().intValue()));
			      clientReferralDAO.delete(referralOld);
	            }  
	            if(intake.getQueueId() != null && intake.getQueueId().intValue()>0){
			      ProgramQueue queueOld = new ProgramQueue(Integer.valueOf(intake.getQueueId().intValue()));
			      queueOld.setClientId(intake.getClientId());
			      queueOld.setProviderNo(Integer.valueOf(intake.getStaffId()));
			      queueOld.setProgramId(Integer.valueOf(intake.getProgramId().intValue()));
			      programQueueDao.delete(queueOld);
	            }
	          
	          
			      clientReferralDAO.saveClientReferral(referral);
			      queue.setReferralId(referral.getId());
			      programQueueDao.saveProgramQueue(queue);
		 }
	}

    public void setClientReferralDAO(ClientReferralDAO clientReferralDAO) {
	  this.clientReferralDAO = clientReferralDAO;
    }

    public void setProgramQueueDao(ProgramQueueDao programQueueDao) {
	  this.programQueueDao = programQueueDao;
    }
  
	public Admission getCurrentAdmission(String programId, Integer demographicNo) {
		return admissionDao.getCurrentAdmission(Integer.valueOf(programId), demographicNo);
	}
    
}
