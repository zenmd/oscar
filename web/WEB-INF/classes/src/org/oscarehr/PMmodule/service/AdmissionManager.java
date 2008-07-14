package org.oscarehr.PMmodule.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.oscarehr.PMmodule.dao.AdmissionDao;
import org.oscarehr.PMmodule.dao.BedDemographicDAO;
import org.oscarehr.PMmodule.dao.BedDemographicHistoricalDao;
import org.oscarehr.PMmodule.dao.ClientHistoryDao;
import org.oscarehr.PMmodule.dao.ClientReferralDAO;
import org.oscarehr.PMmodule.dao.ProgramDao;
import org.oscarehr.PMmodule.dao.ProgramQueueDao;
import org.oscarehr.PMmodule.dao.RoomDAO;
import org.oscarehr.PMmodule.dao.RoomDemographicDAO;
import org.oscarehr.PMmodule.dao.RoomDemographicHistoricalDao;
import org.oscarehr.PMmodule.model.Admission;
import org.oscarehr.PMmodule.model.BedDemographic;
import org.oscarehr.PMmodule.model.BedDemographicHistorical;
import org.oscarehr.PMmodule.model.ClientReferral;
import org.oscarehr.PMmodule.model.Program;
import org.oscarehr.PMmodule.model.ProgramQueue;
import org.oscarehr.PMmodule.model.QuatroIntake;
import org.oscarehr.PMmodule.model.QuatroIntakeFamily;
import org.oscarehr.PMmodule.model.RoomDemographic;
import org.oscarehr.PMmodule.model.RoomDemographicHistorical;
import org.oscarehr.PMmodule.model.RoomDemographicPK;
import org.oscarehr.PMmodule.web.formbean.ClientForm;
import org.oscarehr.PMmodule.model.Room;

import com.quatro.common.KeyConstants;
import com.quatro.dao.IntakeDao;

public class AdmissionManager {
	private IntakeDao intakeDao;
	private ProgramDao programDao;
	private AdmissionDao admissionDao;
	private RoomDAO roomDAO;
	private RoomDemographicDAO roomDemographicDAO;
	private BedDemographicDAO bedDemographicDAO;
	private ProgramQueueDao programQueueDao;
	private ClientReferralDAO clientReferralDAO;
	private RoomDemographicHistoricalDao roomDemographicHistoricalDao;
	private BedDemographicHistoricalDao bedDemographicHistoricalDao;

	public List getAdmissions(Integer demographicNo,String providerNo, Integer shelterId) {
		return admissionDao.getAdmissionList(demographicNo,false,providerNo, shelterId);
	}
	
	public RoomDemographicHistorical getLatestRoomDemographicHistory(Integer admissionId){
        return roomDemographicHistoricalDao.getLatestRoomDemographicHistory(admissionId);
	}
	 
	public BedDemographicHistorical getLatestBedDemographicHistory(Integer admissionId, Calendar latestUsageStart){
        return bedDemographicHistoricalDao.getLatestBedDemographicHistory(admissionId, latestUsageStart);
	}

	private void saveRoomDemographicHistory(Admission admission, RoomDemographic rdm, Calendar currentTime){
        RoomDemographicHistorical history = new RoomDemographicHistorical( rdm.getId().getRoomId(), admission.getId(), admission.getClientId(), currentTime, null);
        roomDemographicHistoricalDao.saveOrUpdate(history);
	}
	
	private void updateRoomDemographicHistory(Admission admission, RoomDemographic rdm){
        RoomDemographicHistorical history = roomDemographicHistoricalDao.findById(rdm.getId().getRoomId(), admission.getId());
        if(history!=null){
     	   	history.setUsageEnd(Calendar.getInstance());
            roomDemographicHistoricalDao.saveOrUpdate(history);
        }
	}
	
	private void saveBedDemographicHistory(Admission admission, BedDemographic bdm, Calendar currentTime){
        BedDemographicHistorical history = new BedDemographicHistorical( bdm.getId().getBedId(), admission.getId(), admission.getClientId(), currentTime, null);
        bedDemographicHistoricalDao.saveOrUpdate(history);
	}
	
	private void updateBedDemographicHistory(Admission admission, BedDemographic bdm){
        BedDemographicHistorical history = bedDemographicHistoricalDao.findById(bdm.getId().getBedId(), admission.getId());
        if(history!=null){
     	   	history.setUsageEnd(Calendar.getInstance());
            bedDemographicHistoricalDao.saveOrUpdate(history);
        }
	}
	
	
	public Integer saveAdmission(Admission admission, Integer intakeId, Integer queueId, 
    		Integer referralId, RoomDemographic roomDemographic, BedDemographic bedDemographic, boolean bFamily) {
  	    String roomName = null;
   	    String bedName = null;
   	    Integer admissionId= new Integer(0);

   	    //to set RoomDemographicHistory:usageStart and BedDemographicHistory:usageStart with same value. 
    	Calendar cal = Calendar.getInstance();

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
   		     
   		     if(admission.getClientId().intValue()==qif.getClientId().intValue()) admissionId=admObj.getId(); 
       	     
 		     //remove old room assignment
   		     rdm2.setAdmissionId(admObj.getId());
   		     RoomDemographic rdm = roomDemographicDAO.getRoomDemographicByDemographic(qif.getClientId());
   		     if(rdm!=null && !rdm.getId().getRoomId().equals(rdm2.getId().getRoomId())){
           	   roomDemographicDAO.deleteRoomDemographic(rdm);
          	   roomDemographicDAO.saveRoomDemographic(rdm2);
          	   Room room = roomDAO.getRoom(rdm2.getId().getRoomId());
          	   if(room!=null) roomName = room.getName();
      	     }else{
               roomDemographicDAO.saveRoomDemographic(rdm2);
          	   Room room = roomDAO.getRoom(rdm2.getId().getRoomId());
          	   if(room!=null) roomName = room.getName();
      	     }
             clientHistoryDao.saveClientHistory(admObj, roomName, bedName);
 
             // save room_history
             saveRoomDemographicHistory(admObj, roomDemographic, cal);
                         
           }
    	}else{
		  intakeDao.setIntakeStatusAdmitted(intakeId);
		  admissionDao.saveAdmission(admission, intakeId, queueId, referralId);
		  admissionId=admission.getId();
    	
    	  //remove old room/bed assignment
    	  RoomDemographic rdm = roomDemographicDAO.getRoomDemographicByDemographic(roomDemographic.getId().getDemographicNo());
    	  BedDemographic bdm = bedDemographicDAO.getBedDemographicByDemographic(bedDemographic.getId().getDemographicNo());
          roomDemographic.setAdmissionId(admissionId);
          bedDemographic.setAdmissionId(admissionId);
    	  if(rdm!=null && bdm!=null){
    	    if(!(rdm.getId().getRoomId().equals(roomDemographic.getId().getRoomId()) &&
    	             bdm.getId().getBedId().equals(bedDemographic.getId().getBedId()))){
      	      roomDemographicDAO.deleteRoomDemographic(rdm);
     	      bedDemographicDAO.deleteBedDemographic(bdm);
         	  
     	      // update room_history
              updateRoomDemographicHistory(admission, rdm);
     	      // update bed_history
              updateBedDemographicHistory(admission, bdm); 
              
     	      roomDemographicDAO.saveRoomDemographic(roomDemographic);
    	      bedDemographicDAO.saveBedDemographic(bedDemographic);
        	  Room room = roomDAO.getRoom(roomDemographic.getId().getRoomId());
          	  if(room!=null) roomName = room.getName();
    	      bedName = bedDemographic.getBedName();
    	    }
    	  }else{
       	    roomDemographicDAO.saveRoomDemographic(roomDemographic);
    	    bedDemographicDAO.saveBedDemographic(bedDemographic);
      	    Room room = roomDAO.getRoom(roomDemographic.getId().getRoomId());
      	    if(room!=null) roomName = room.getName();
    	    bedName = bedDemographic.getBedName();
    	  }
          clientHistoryDao.saveClientHistory(admission,roomName, bedName);
          
          // save room_history
          saveRoomDemographicHistory(admission, roomDemographic, cal);
          // save bed_history
          saveBedDemographicHistory(admission, bedDemographic, cal);
          

    	}
    	
    	return admissionId;
    	
    }

    public void dischargeAdmission(String admissionIds) {
    	admissionDao.dischargeAdmission(admissionIds);
    }
    

    //update one person (one of family memeber or single person) admission, 
    public void updateAdmission(Admission admission, RoomDemographic roomDemographic, BedDemographic bedDemographic) {
    	admissionDao.updateAdmission(admission);
    	//remove old room/bed assignment
    	String roomName=null;
    	String bedName = null;
    	roomDemographic.setAdmissionId(admission.getId());
    	RoomDemographic rdm = roomDemographicDAO.getRoomDemographicByDemographic(roomDemographic.getId().getDemographicNo());
  	    
    	//to set RoomDemographicHistory:usageStart and BedDemographicHistory:usageStart with same value. 
    	Calendar cal = Calendar.getInstance();
    	
  	    if(rdm!=null){
  	      if(!rdm.getId().getRoomId().equals(roomDemographic.getId().getRoomId())){
        	roomDemographicDAO.deleteRoomDemographic(rdm);
       	    roomDemographicDAO.saveRoomDemographic(roomDemographic);
      	    Room room = roomDAO.getRoom(roomDemographic.getId().getRoomId());
      	    if(room!=null) roomName = room.getName();
            
       	    // update room_history
            updateRoomDemographicHistory(admission, rdm);
       	    // save room_history
            saveRoomDemographicHistory(admission, roomDemographic, cal);
            
  	      }
  	    }else{
  	    	roomDemographicDAO.saveRoomDemographic(roomDemographic);
      	    Room room = roomDAO.getRoom(roomDemographic.getId().getRoomId());
      	    if(room!=null) roomName = room.getName();
     	    // save room_history
            saveRoomDemographicHistory(admission, roomDemographic, cal);            
        }

  	    if(bedDemographic!=null) {
  	      BedDemographic bdm = bedDemographicDAO.getBedDemographicByDemographic(bedDemographic.getId().getDemographicNo());
    	  roomDemographic.setAdmissionId(admission.getId());
    	  if(bdm!=null){
    	    if(!bdm.getId().getBedId().equals(bedDemographic.getId().getBedId())){
     	      bedDemographicDAO.deleteBedDemographic(bdm);
    	      if(bedDemographic.getBedId().intValue()>0) {
    	    	bedDemographicDAO.saveBedDemographic(bedDemographic);
    	    	bedName = bedDemographic.getBedName();
           	    // update bed_history
                updateBedDemographicHistory(admission, bdm);
           	    // save bed_history
                saveBedDemographicHistory(admission, bedDemographic, cal);
    	      }
    	    }  
    	  }else{
    	   if(bedDemographic.getBedId().intValue()>0) {
    		   bedDemographicDAO.saveBedDemographic(bedDemographic);
    		   bedName = bedDemographic.getBedName();
          	   // save bed_history
               saveBedDemographicHistory(admission, bedDemographic, cal);    		   
    	   }
    	  }
  	    }
  	    clientHistoryDao.saveClientHistory(admission, roomName, bedName);
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
		clientHistoryDao.saveClientHistory(admission, null, null);
	}
  
  public void dischargeAdmission(Admission admission, boolean isReferral, List lstFamily){
	   admissionDao.updateDischargeInfo(admission);
       clientHistoryDao.saveClientHistory(admission,null,null);

	   RoomDemographic rdm = roomDemographicDAO.getRoomDemographicByDemographic(admission.getClientId());
	   if(rdm!=null) {
		   roomDemographicDAO.deleteRoomDemographic(rdm);
      	   // update room_history
           updateRoomDemographicHistory(admission, rdm);
	   }
	   BedDemographic bdm = bedDemographicDAO.getBedDemographicByDemographic(admission.getClientId());
	   if(bdm!=null){
		   bedDemographicDAO.deleteBedDemographic(bdm);
      	   // update bed_history
           updateBedDemographicHistory(admission, bdm);		   
	   }

	   if(lstFamily!=null){
		 Iterator item = lstFamily.iterator();
		 while(item.hasNext()){
			QuatroIntakeFamily qifTmp = (QuatroIntakeFamily)item.next();
			Admission admLoc =admissionDao.getAdmissionByIntakeId(qifTmp.getIntakeId());
			if(admLoc != null && admLoc.getId().intValue()!=admission.getId().intValue()){ 
				admLoc.setAdmissionStatus(KeyConstants.INTAKE_STATUS_DISCHARGED);
				admLoc.setDischargeDate(Calendar.getInstance());
			  	admLoc.setDischargeNotes(admission.getDischargeNotes());	
				admLoc.setCommunityProgramCode(admission.getCommunityProgramCode());
			  	admissionDao.updateDischargeInfo(admLoc);
				RoomDemographic rdm2 = roomDemographicDAO.getRoomDemographicByDemographic(admLoc.getClientId());
				if(rdm2!=null){ 
					roomDemographicDAO.deleteRoomDemographic(rdm2);
		      	   // update room_history
		           updateRoomDemographicHistory(admission, rdm);
				}
				BedDemographic bdm2 = bedDemographicDAO.getBedDemographicByDemographic(admLoc.getClientId());
				if(bdm2!=null) {
					bedDemographicDAO.deleteBedDemographic(bdm2);
		      	   // update bed_history
		           updateBedDemographicHistory(admission, bdm2);					
				}
		        clientHistoryDao.saveClientHistory(admLoc,null,null);
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
		  referral.setStatus(KeyConstants.STATUS_PENDING);
		  referral.setAutoManual(KeyConstants.AUTOMATIC);
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
	        referral.setStatus(KeyConstants.STATUS_PENDING);
	        referral.setAutoManual(KeyConstants.AUTOMATIC);
	        
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
    private ClientHistoryDao clientHistoryDao;
	public void setClientHistoryDao(ClientHistoryDao clientHistoryDao) {
		this.clientHistoryDao = clientHistoryDao;
	}


	public ClientReferralDAO getClientReferralDAO() {
		return clientReferralDAO;
	}


	public void setRoomDemographicHistoricalDao(
			RoomDemographicHistoricalDao roomDemographicHistoricalDao) {
		this.roomDemographicHistoricalDao = roomDemographicHistoricalDao;
	}


	public void setBedDemographicHistoricalDao(
			BedDemographicHistoricalDao bedDemographicHistoricalDao) {
		this.bedDemographicHistoricalDao = bedDemographicHistoricalDao;
	}

	public void setRoomDAO(RoomDAO roomDAO) {
		this.roomDAO = roomDAO;
	}
 }
