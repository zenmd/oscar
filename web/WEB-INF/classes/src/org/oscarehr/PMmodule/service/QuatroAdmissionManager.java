package org.oscarehr.PMmodule.service;

import java.util.Date;
import java.util.List;

import org.oscarehr.PMmodule.dao.QuatroAdmissionDao;
import org.oscarehr.PMmodule.dao.RoomDemographicDAO;
import org.oscarehr.PMmodule.dao.BedDemographicDAO;


import org.oscarehr.PMmodule.model.Admission;
import org.oscarehr.PMmodule.model.QuatroAdmission;
import org.oscarehr.PMmodule.model.RoomDemographic;
import org.oscarehr.PMmodule.model.RoomDemographicPK;
import org.oscarehr.PMmodule.model.BedDemographic;
import org.oscarehr.PMmodule.model.BedDemographicPK;

import com.quatro.common.KeyConstants;

public class QuatroAdmissionManager {
	private QuatroAdmissionDao quatroAdmissionDao;
	private RoomDemographicDAO roomDemographicDAO;
	private BedDemographicDAO bedDemographicDAO;

	public void saveAdmission(QuatroAdmission admission, Integer intakeId, Integer queueId, 
    		Integer referralId, RoomDemographic roomDemographic, BedDemographic bedDemographic, boolean bFamily) {
    	quatroAdmissionDao.saveAdmission(admission, intakeId, queueId, referralId);
    	
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
    	quatroAdmissionDao.dischargeAdmission(admissionIds);
    }
    
    public void updateAdmission(QuatroAdmission admission, RoomDemographic roomDemographic, BedDemographic bedDemographic, boolean bFamily) {
    	quatroAdmissionDao.updateAdmission(admission);
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

    public void setQuatroAdmissionDao(QuatroAdmissionDao quatroAdmissionDao) {
		this.quatroAdmissionDao = quatroAdmissionDao;
	}

	public List getIntakeAdmissionList(Integer clientId) {
		return quatroAdmissionDao.getIntakeAdmissionList(clientId);
	}

	public List getAdmissionList(Integer clientId, Integer facilityId, String providerNo) {
		return quatroAdmissionDao.getAdmissionList(clientId, facilityId, providerNo);
	}

    public QuatroAdmission getAdmissionByIntakeId(Integer intakeId){
		return quatroAdmissionDao.getAdmissionByIntakeId(intakeId);
    }

    public QuatroAdmission getAdmissionByAdmissionId(Integer admissionId){
		return quatroAdmissionDao.getAdmissionByAdmissionId(admissionId);
    }
    
    public List<QuatroAdmission> getCurrentAdmissionsByFacility(Integer demographicNo, Integer facilityId) {
		return quatroAdmissionDao.getCurrentAdmissionsByFacility(demographicNo, facilityId);
    }

	public void setBedDemographicDAO(BedDemographicDAO bedDemographicDao) {
		this.bedDemographicDAO = bedDemographicDao;
	}

	public void setRoomDemographicDAO(RoomDemographicDAO roomDemographicDao) {
		this.roomDemographicDAO = roomDemographicDao;
	}

}
