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

package org.oscarehr.PMmodule.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.oscarehr.PMmodule.dao.BedDAO;
import org.oscarehr.PMmodule.dao.ClientDao;
import org.oscarehr.PMmodule.dao.ClientHistoryDao;
import org.oscarehr.PMmodule.dao.ProviderDao;
import org.oscarehr.PMmodule.dao.RoomDAO;
import org.oscarehr.PMmodule.dao.RoomDemographicDAO;
import org.oscarehr.PMmodule.model.Admission;
import org.oscarehr.PMmodule.model.ClientHistory;
import org.oscarehr.PMmodule.model.Room;
import org.oscarehr.PMmodule.model.RoomDemographic;
import org.oscarehr.PMmodule.service.RoomDemographicManager;

/**
 * Implementation of RoomDemographicManager interface
 */
public class RoomDemographicManagerImpl implements RoomDemographicManager {

	private static final Log log = LogFactory.getLog(RoomDemographicManagerImpl.class);
	

	private RoomDemographicDAO roomDemographicDAO;
	private ProviderDao providerDao;
	private ClientDao clientDao;
	private ClientHistoryDao clientHistoryDao;
	private RoomDAO roomDAO;
	private BedDAO bedDAO;

	public void setRoomDemographicDAO(RoomDemographicDAO roomDemographicDAO) {
		this.roomDemographicDAO = roomDemographicDAO;
	}

	public void setProviderDao(ProviderDao providerDao) {
		this.providerDao = providerDao;
	}

	public void setClientDao(ClientDao clientDao) {
		this.clientDao = clientDao;
	}
	public void setClientHistoryDao(ClientHistoryDao clientHistoryDao) {
		this.clientHistoryDao = clientHistoryDao;
	}

	public void setRoomDAO(RoomDAO roomDAO) {
		this.roomDAO = roomDAO;
	}

	public void setBedDAO(BedDAO bedDAO) {
		this.bedDAO = bedDAO;
	}

	/**
	 * @see org.oscarehr.PMmodule.service.RoomDemographicManager#roomDemographicExists(java.lang.Integer)
	 */
	public boolean roomDemographicExists(Integer roomId) {
		return roomDemographicDAO.roomDemographicExists(roomId);
	}

	public int getRoomOccupanyByRoom(Integer roomId){
		return roomDemographicDAO.getRoomOccupanyByRoom(roomId);
	}
	
	/**
	 * @see org.oscarehr.PMmodule.service.RoomDemographicManager#getRoomDemographicByRoom(java.lang.Integer)
	 */
	public List getRoomDemographicByRoom(Integer roomId) {
		if (roomId == null) {
			throw new IllegalArgumentException("roomId must not be null");
		}
		List roomDemographicList = null;
		roomDemographicList = roomDemographicDAO.getRoomDemographicByRoom(roomId);
			
		if(roomDemographicList != null  &&  roomDemographicList.size() > 0){
			//Demographic demographic = demographicDAO.getClientByDemographicNo(roomDemographicList.get(0).getId().getDemographicNo());
			//roomDemographicList.get(0).setDemographic(demographic);
		}
		return roomDemographicList;
	}

	public RoomDemographic getRoomDemographicByDemographic(Integer demographicNo){
		if (demographicNo == null) {
			throw new IllegalArgumentException("demographicNo must not be null");
		}
		RoomDemographic roomDemographic = roomDemographicDAO.getRoomDemographicByDemographic(demographicNo);
		if (roomDemographic != null)
			setAttributes(roomDemographic);
		return roomDemographic;
	}

	public RoomDemographic getRoomDemographicByAdmissionId(Integer admissionId){
		if (admissionId == null) {
			throw new IllegalArgumentException("admissionId must not be null");
		}
		RoomDemographic roomDemographic = roomDemographicDAO.getRoomDemographicByAdmissionId(admissionId);
		if (roomDemographic != null)
			setAttributes(roomDemographic);
		return roomDemographic;
	}

	/**
	 * @see org.oscarehr.PMmodule.service.RoomDemographicManager#saveRoomDemographic(org.oscarehr.PMmodule.model.RoomDemographic)
	 */
	public void saveRoomDemographic(RoomDemographic roomDemographic) {
		if (roomDemographic == null) {
			throw new IllegalArgumentException("roomDemographic must not be null");
		}
		boolean isNoRoomAssigned = (roomDemographic.getId().getRoomId().intValue() == 0);
		
		if(!isNoRoomAssigned){
			validate(roomDemographic);
		}

		// only discharge out of previous room in the same facility
        Room room=roomDAO.getRoom(roomDemographic.getId().getRoomId());
		RoomDemographic roomDemographicPrevious = getRoomDemographicByDemographic(roomDemographic.getId().getDemographicNo()); 
		if(roomDemographicPrevious != null){
			deleteRoomDemographic(roomDemographicPrevious);
		}
		if(!isNoRoomAssigned){
			roomDemographicDAO.saveRoomDemographic(roomDemographic);
		}
	}
	
	/**
	 * @see org.oscarehr.PMmodule.service.RoomDemographicManager#saveRoomDemographic(org.oscarehr.PMmodule.model.RoomDemographic)
	 */
	public void saveRoomDemographic(RoomDemographic roomDemographic, Admission admission) {
		saveRoomDemographic(roomDemographic);
		
	  String roomName = roomDAO.getRoom(roomDemographic.getId().getRoomId()).getName();
	  String bedName = "";
  	  if (roomDemographic.getBedId() != null) bedName = bedDAO.getBed(roomDemographic.getBedId()).getName();
      clientHistoryDao.saveClientHistory(admission,roomName, bedName);
	}
	
	/**
	 * @see org.oscarehr.PMmodule.service.RoomDemographicManager#deleteRoomDemographic(RoomDemographic)
	 */
	public void deleteRoomDemographic(RoomDemographic roomDemographic) {
		if (roomDemographic == null) {
			throw new IllegalArgumentException("roomDemographic must not be null");
		}
		
		roomDemographicDAO.deleteRoomDemographic(roomDemographic);
	}
	public void deleteRoomDemographic(String clients, Integer roomId){
		if(clients==null) return; 
		roomDemographicDAO.deleteRoomDemographic(clients,roomId);
	}

	void setAttributes(RoomDemographic roomDemographic) {

//		roomDemographic.setAssignEnd(duration);

		String providerNo = roomDemographic.getProviderNo();
		roomDemographic.setProvider(providerDao.getProvider(providerNo));
	}

	void validate(RoomDemographic roomDemographic) {
		validateProvider(roomDemographic.getProviderNo());
		validateRoom(roomDemographic.getId().getRoomId());
//		validateDemographic(roomDemographic.getId().getDemographicNo());
	}

/*	
	void validateRoomDemographic(RoomDemographic roomDemographic) {
		if (!roomDemographic.isValidAssign()) {
			throw new IllegalArgumentException("invalid Assignvation: " + roomDemographic.getAssignStart() + " - " + roomDemographic.getAssignEnd());
		}
	}
*/
	void validateProvider(String providerId) {
		if (!providerDao.providerExists(providerId)) {
			throw new IllegalArgumentException("no provider with id : " + providerId);
		}
	}

	void validateRoom(Integer roomId) {
		if (!roomDAO.roomExists(roomId)) {
			throw new IllegalArgumentException("no room with id : " + roomId);
		}
	}

	void validateDemographic(Integer demographicNo) {
		if (!clientDao.clientExists(demographicNo)) {
			throw new IllegalArgumentException("no demographic with id : " + demographicNo);
		}
	}

}