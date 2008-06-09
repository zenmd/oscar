package org.oscarehr.PMmodule.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
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
import org.oscarehr.PMmodule.model.QuatroIntakeFamily;
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

	public List getAdmissions(Integer demographicNo,String providerNo, Integer shelterId) {
		return admissionDao.getAdmissionList(demographicNo,false,providerNo, shelterId);
	}
	
	public void saveAdmission(Admission admission, Integer intakeId, Integer queueId, 
    		Integer referralId, RoomDemographic roomDemographic, BedDemographic bedDemographic, boolean bFamily) {
    	if(bFamily){
    	   List lstFamily= intakeDao.getClientIntakeFamily(intakeId.toString());
           for(int i=0;i<lstFamily.size();i++){
             QuatroIntakeFamily qif = (QuatroIntakeFamily)lstFamily.get(i);
             RoomDemographic rdm2 = new RoomDemographic();
             RoomDemographicPK rdmPK = new RoomDemographicPK(qif.getClientId(), roomDemographic.getId().getRoomId());
             rdm2.setId(rdmPK);
             rdm2.setProviderNo(roomDemographic.getProviderNo());
             rdm2.setAssignStart(roomDemographic.getAssignStart());
   		     intakeDao.setIntakeStatusAdmitted(qif.getIntakeId());
   		     Admission admObj =(Admission)admission.clone();
   		     admObj.setClientId(qif.getClientId());
   		     admObj.setIntakeId(qif.getIntakeId());
   		     admissionDao.saveAdmission(admObj, qif.getIntakeId(), queueId, referralId);
       	  
       	     //remove old room assignment
   		     RoomDemographic rdm = roomDemographicDAO.getRoomDemographicByDemographic(qif.getClientId());
       	     if(rdm!=null && !rdm.getId().getRoomId().equals(rdm2.getId().getRoomId())){
           	   roomDemographicDAO.deleteRoomDemographic(rdm);
          	   roomDemographicDAO.saveRoomDemographic(rdm2);
      	     }else{
               roomDemographicDAO.saveRoomDemographic(rdm2);
      	     }
           }
    	}else{
		  intakeDao.setIntakeStatusAdmitted(intakeId);
		  admissionDao.saveAdmission(admission, intakeId, queueId, referralId);
    	
    	  //remove old room/bed assignment
    	  RoomDemographic rdm = roomDemographicDAO.getRoomDemographicByDemographic(roomDemographic.getId().getDemographicNo());
    	  BedDemographic bdm = bedDemographicDAO.getBedDemographicByDemographic(bedDemographic.getId().getDemographicNo());
    	  if(rdm!=null && bdm!=null){
    	    if(!(rdm.getId().getRoomId().equals(roomDemographic.getId().getRoomId()) &&
    	             bdm.getId().getBedId().equals(bedDemographic.getId().getBedId()))){
      	      roomDemographicDAO.deleteRoomDemographic(rdm);
     	      bedDemographicDAO.deleteBedDemographic(bdm);
         
     	      roomDemographicDAO.saveRoomDemographic(roomDemographic);
    	      bedDemographicDAO.saveBedDemographic(bedDemographic);
    	    }
    	  }else{
       	    roomDemographicDAO.saveRoomDemographic(roomDemographic);
    	    bedDemographicDAO.saveBedDemographic(bedDemographic);
    	  }
    	}
    	
    }

    public void dischargeAdmission(String admissionIds) {
    	admissionDao.dischargeAdmission(admissionIds);
    }
    
    //update one person (one of family memeber or single person) admission, 
    public void updateAdmission(Admission admission, RoomDemographic roomDemographic, BedDemographic bedDemographic) {
    	admissionDao.updateAdmission(admission);
    	//remove old room/bed assignment
    	RoomDemographic rdm = roomDemographicDAO.getRoomDemographicByDemographic(roomDemographic.getId().getDemographicNo());
  	    if(rdm!=null){
  	      if(!rdm.getId().getRoomId().equals(roomDemographic.getId().getRoomId())){
        	roomDemographicDAO.deleteRoomDemographic(rdm);
       	    roomDemographicDAO.saveRoomDemographic(roomDemographic);
  	      }
  	    }else{
       	  roomDemographicDAO.saveRoomDemographic(roomDemographic);
        }

  	    if(bedDemographic==null) return;
  	    
  	    BedDemographic bdm = bedDemographicDAO.getBedDemographicByDemographic(bedDemographic.getId().getDemographicNo());
    	if(bdm!=null){
    	  if(!bdm.getId().getBedId().equals(bedDemographic.getId().getBedId())){
     	    bedDemographicDAO.deleteBedDemographic(bdm);
    	    if(bedDemographic.getBedId().intValue()>0) bedDemographicDAO.saveBedDemographic(bedDemographic);
    	  }  
    	}else{
    	   if(bedDemographic.getBedId().intValue()>0) bedDemographicDAO.saveBedDemographic(bedDemographic);
    	}
    }

    public void setAdmissionDao(AdmissionDao admissionDao) {
		this.admissionDao = admissionDao;
	}
	
	public List getAdmissionListByProgram(Integer programId) {
		return admissionDao.getAdmissionListByProgram(programId);
	}

	public List getClientsListByProgram(Program program, ClientForm form) {
		return admissionDao.getClientsListByProgram(program, form);
	}
	
    public Admission getAdmissionByIntakeId(Integer intakeId){
		return admissionDao.getAdmissionByIntakeId(intakeId);
    }

    public Admission getAdmissionByAdmissionId(Integer admissionId){
		return admissionDao.getAdmissionByAdmissionId(admissionId);
    }
    
    public List getCurrentAdmissions(Integer demographicNo, String providerNo,Integer shelterId) {
		return admissionDao.getCurrentAdmissions(demographicNo,providerNo, shelterId);
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

	public Admission getRecentAdmissions(Integer clientId,String providerNo,Integer shelterId){
		return admissionDao.getRecentAdmission(clientId, providerNo, shelterId);
	}
	
	public Admission getCurrentBedProgramAdmission(Integer demographicNo) {
		return admissionDao.getCurrentBedProgramAdmission(demographicNo);
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
    
  public void dischargeAdmission(Admission admission, boolean isReferral, List lstFamily){
	   admissionDao.updateDischargeInfo(admission);

	   RoomDemographic rdm = roomDemographicDAO.getRoomDemographicByDemographic(admission.getClientId());
	   if(rdm!=null) roomDemographicDAO.deleteRoomDemographic(rdm);
	   BedDemographic bdm = bedDemographicDAO.getBedDemographicByDemographic(admission.getClientId());
	   if(bdm!=null) bedDemographicDAO.deleteBedDemographic(bdm);

	   if(lstFamily!=null){
		 Iterator item = lstFamily.iterator();
		 while(item.hasNext()){
			QuatroIntakeFamily qifTmp = (QuatroIntakeFamily)item.next();
			Admission admLoc =admissionDao.getAdmissionByIntakeId(qifTmp.getIntakeId());
			if(admLoc.getId().intValue()!=admission.getId().intValue()){ 
				admLoc.setAdmissionStatus(KeyConstants.INTAKE_STATUS_DISCHARGED);
				admLoc.setDischargeDate(Calendar.getInstance());
			  	admLoc.setDischargeNotes(admission.getDischargeNotes());	
				admLoc.setCommunityProgramCode(admission.getCommunityProgramCode());
			  	admissionDao.updateDischargeInfo(admLoc);
				RoomDemographic rdm2 = roomDemographicDAO.getRoomDemographicByDemographic(admission.getClientId());
				if(rdm2!=null) roomDemographicDAO.deleteRoomDemographic(rdm2);
				BedDemographic bdm2 = bedDemographicDAO.getBedDemographicByDemographic(admission.getClientId());
				if(bdm2!=null) bedDemographicDAO.deleteBedDemographic(bdm2);
			}
		 }
	   }
	  
	  if(isReferral){
		ClientReferral referral= clientReferralDAO.getReferralsByProgramId(admission.getClientId(), admission.getProgramId());
		ProgramQueue queue = programQueueDao.getQueue(admission.getProgramId(), admission.getClientId());
		if(referral==null){
		  referral = new ClientReferral();
		  referral.setClientId(admission.getClientId());
		  referral.setNotes("Discharge Automated referral");
		  referral.setProgramId(admission.getBedProgramId());
		  referral.setProviderNo(admission.getProviderNo());
		  referral.setReferralDate(new Date());
		  referral.setStatus(KeyConstants.STATUS_ACTIVE);	        
	      clientReferralDAO.saveClientReferral(referral);
	      if(queue!=null) programQueueDao.delete(queue);
		  queue = new ProgramQueue();
	      queue.setClientId(referral.getClientId());
	      queue.setNotes(referral.getNotes());
	      queue.setProgramId(referral.getProgramId());
		  queue.setProviderNo(Integer.valueOf(referral.getProviderNo()));
		  queue.setReferralDate(referral.getReferralDate());
	      queue.setReferralId(referral.getId());
	      queue.setPresentProblems(referral.getPresentProblems());
		  queue.setReferralId(referral.getId());
		  programQueueDao.saveProgramQueue(queue);
		}else{
		  if(queue==null){
		    queue = new ProgramQueue();
            queue.setClientId(referral.getClientId());
            queue.setNotes(referral.getNotes());
            queue.setProgramId(referral.getProgramId());
		    queue.setProviderNo(Integer.valueOf(referral.getProviderNo()));
		    queue.setReferralDate(referral.getReferralDate());
            queue.setReferralId(referral.getId());
            queue.setPresentProblems(referral.getPresentProblems());
	        queue.setReferralId(referral.getId());
	        programQueueDao.saveProgramQueue(queue);
		  }
		}
	  }
  }
  
  public void saveAdmission(Admission admission,boolean isReferral){
		 admissionDao.saveAdmission(admission);
		 if(isReferral){
	    	ClientReferral referral = new ClientReferral();
	        referral.setClientId(admission.getClientId());
	        referral.setNotes("Discharge Automated referral");
	        referral.setProgramId(admission.getBedProgramId());
	        referral.setProviderNo(admission.getProviderNo());
	        referral.setReferralDate(new Date());
	        referral.setStatus(KeyConstants.STATUS_ACTIVE);	        
	        
	        ProgramQueue queue = new ProgramQueue();
	        
	          queue.setClientId(referral.getClientId());
	          queue.setNotes(referral.getNotes());
	          queue.setProgramId(referral.getProgramId());
	          queue.setProviderNo(Integer.valueOf(referral.getProviderNo()));
	          queue.setReferralDate(referral.getReferralDate());
	          queue.setReferralId(referral.getId());
	          queue.setPresentProblems(referral.getPresentProblems());
	        
	          //delete old referral and queue records linked to this intake
	         
	          QuatroIntake intake=intakeDao.getQuatroIntake(admission.getIntakeId());
			    if(intake.getReferralId() != null &&  intake.getReferralId().intValue()>0){
			      ClientReferral referralOld = new ClientReferral(new Integer(intake.getReferralId().intValue()));
			      referralOld.setClientId(intake.getClientId());
			      referralOld.setProgramId(new Integer(intake.getProgramId().intValue()));
			      clientReferralDAO.delete(referralOld);
	            }  
	            if(intake.getQueueId() != null && intake.getQueueId().intValue()>0){
			      ProgramQueue queueOld = new ProgramQueue(new Integer(intake.getQueueId().intValue()));
			      queueOld.setClientId(intake.getClientId());
			      queueOld.setProviderNo(new Integer(intake.getStaffId()));
			      queueOld.setProgramId(new Integer(intake.getProgramId().intValue()));
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
  }
