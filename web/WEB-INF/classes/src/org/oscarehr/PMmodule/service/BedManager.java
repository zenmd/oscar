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

package org.oscarehr.PMmodule.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.oscarehr.PMmodule.dao.BedDAO;
import org.oscarehr.PMmodule.dao.ProgramDao;
import org.oscarehr.PMmodule.dao.ProgramTeamDAO;
import org.oscarehr.PMmodule.dao.RoomDAO;
import org.oscarehr.PMmodule.exception.BedReservedException;
import org.oscarehr.PMmodule.exception.DuplicateBedNameException;
import org.oscarehr.PMmodule.model.Bed;
import org.oscarehr.PMmodule.model.BedDemographic;
import org.oscarehr.PMmodule.model.BedType;
import org.oscarehr.PMmodule.model.Program;
import org.oscarehr.PMmodule.model.Room;

/**
 * Implementation of BedManager interface
 */
public class BedManager {

    private static final Log log = LogFactory.getLog(BedManager.class);

    private BedDAO bedDAO;
    private RoomDAO roomDAO;
    private ProgramTeamDAO teamDAO;
    private BedDemographicManager bedDemographicManager;
    private ProgramDao programDao;

    public void setBedDAO(BedDAO bedDAO) {
        this.bedDAO = bedDAO;
    }

    public void setRoomDAO(RoomDAO roomDAO) {
        this.roomDAO = roomDAO;
    }

    public void setTeamDAO(ProgramTeamDAO teamDAO) {
        this.teamDAO = teamDAO;
    }
    
    public void setProgramDao(ProgramDao programDao) {
        this.programDao = programDao;
    }

    public void setBedDemographicManager(BedDemographicManager bedDemographicManager) {
        this.bedDemographicManager = bedDemographicManager;
    }

    /**
     * Get bed
     *
     * @param bedId
     *            bed identifier
     * @return bed
     */
    public Bed getBed(Integer bedId) {
        if (bedId == null) {
            throw new IllegalArgumentException("bedId must not be null");
        }

        Bed bed = bedDAO.getBed(bedId);
        setAttributes(bed);

        return bed;
    }

    public Bed getBedForDelete(Integer bedId) {
        if (bedId == null) {
            throw new IllegalArgumentException("bedId must not be null");
        }
        Bed bed = bedDAO.getBed(bedId);
        return bed;
    }

    /**
     * Get beds by program
     *
     * @param programId
     *            program identifier
     * @param reserved
     *            reserved flag
     * @return array of beds
     */
    public Bed[] getBedsByProgram( Integer programId, boolean reserved) {
        if (programId == null) {
            return new Bed[] {};
        }

        List beds = new ArrayList();

//        for (Room room : roomDAO.getRooms(null, programId, Boolean.TRUE)) {
        Room[] rooms = roomDAO.getRooms(null, programId, Boolean.TRUE);
        for (int i=0;i<rooms.length;i++) {
        	Room room = rooms[i];
//            for (Bed bed : bedDAO.getBedsByRoom(room.getId(), Boolean.TRUE)) {
        	Bed[] beds2 = bedDAO.getBedsByRoom(room.getId(), Boolean.TRUE);
            for (int j=0;j<beds2.length;j++) {
            	Bed bed = beds2[j];
            	setAttributes(bed);

                if (!filterBed(bed, new Boolean(reserved))) {
                    beds.add(bed);
                }
            }
        }

        Bed[] ret=  new Bed[beds.size()];
        for(int i=0;i<beds.size();i++){
        	ret[i]= (Bed)beds.get(i);
        }
        return ret;
        //return beds.toArray(new Bed[beds.size()]);
    }

    /**
     * Get beds by available rooms & assigned program
     *
     * @param availableRooms
     * @param programId
     *            program identifier
     * @param reserved
     *            reserved flag
     * @return array of beds
     */
    public Bed[] getBedsByRoomProgram(Room[] availableRooms, Integer programId, boolean reserved) {
        if (programId == null) {
            return new Bed[] {};
        }
        List beds = new ArrayList();

//        for (Room room : availableRooms) {
        for (int i=0;i<availableRooms.length;i++) {
        	Room room = availableRooms[i];
//            for (Bed bed : bedDAO.getBedsByRoom(room.getId(), Boolean.TRUE)) {
        	Bed[] beds2 = bedDAO.getBedsByRoom(room.getId(), Boolean.TRUE);
            for (int j=0;j<beds2.length;j++) {
            	Bed bed = beds2[j];
                setAttributes(bed);

                if (!filterBed(bed, new Boolean(reserved))) {
                    beds.add(bed);
                }
            }
        }
//        return beds.toArray(new Bed[beds.size()]);
        Bed[] ret=  new Bed[beds.size()];
        for(int i=0;i<beds.size();i++){
        	ret[i]= (Bed)beds.get(i);
        }
        return ret;
    }
    
    /**
     * Get beds by facility
     *
     * @param facilityId
     *            facility identifier
     * @param reserved
     *            reserved flag
     * @return array of beds
     */
/*
    public Bed[] getBedsByFacility(Integer shelterId, boolean reserved) {
        if (facilityId == null) {
            return new Bed[] {};
        }
        List<Bed> beds = new ArrayList<Bed>();
        for (Bed bed : bedDAO.getBedsByFacility(facilityId, Boolean.TRUE)) {
            setAttributes(bed);

            if (!filterBed(bed, reserved)) {
                beds.add(bed);
            }
        }
        return beds.toArray(new Bed[beds.size()]);
    }
    
    public Bed[] getBedsByFacility(Integer shelterId, Integer roomId, Boolean active, boolean reserved) {
        if (facilityId == null) {
            return new Bed[] {};
        }
        List<Bed> beds = new ArrayList<Bed>();
        for (Bed bed : bedDAO.getBedsByFacility(facilityId, active)) {
            setAttributes(bed);

            if (!filterBed(bed, reserved)) {
                beds.add(bed);
            }
        }
        return beds.toArray(new Bed[beds.size()]);
    }
*/
    
    public List getBedsByFilter(Integer facilityId, Integer roomId, Boolean active,  boolean reserved) {
    	List beds = new ArrayList();
//        for (Bed bed : bedDAO.getBedsByFilter(facilityId, roomId, active)) {
    	Bed[] beds2 = bedDAO.getBedsByFilter(facilityId, roomId, active);
        for (int i=0;i<beds2.length;i++) {
        	Bed bed = beds2[i];
        	setAttributes(bed);

            if (!filterBed(bed, new Boolean(reserved))) {
                beds.add(bed);
            }
        }
        return beds;
    }
    
    /**
     * Get beds by facility
     *
     * @param facilityId
     *            facility identifier
     * @param reserved
     *            reserved flag
     * @return array of beds
     */
    public Bed[] getBedsByFacility(Integer facilityId) {
        if (facilityId == null) {
            return new Bed[] {};
        }
        List beds = new ArrayList();

//        for (Bed bed : bedDAO.getBedsByFacility(facilityId, Boolean.TRUE)) {
        List beds2 = bedDAO.getBedsByFacility(facilityId, Boolean.TRUE);
        for (int i=0;i<beds2.size();i++) {
        	Bed bed = (Bed)beds2.get(i);
            setAttributes(bed);
            beds.add(bed);
        }
//        return beds.toArray(new Bed[beds.size()]);
        Bed[] ret=  new Bed[beds.size()];
        for(int i=0;i<beds.size();i++){
        	ret[i]= (Bed)beds.get(i);
        }
        return ret;
    }

    /**
     * Get beds by facility
     *
     * @param facilityId the facility we're looking up
     * @param reserved
     *            reserved flag
     * @return array of beds
     */
    public Bed[] getBedsByProgramAndFacility(Integer facilityId, boolean reserved) {
        List beds = new ArrayList();

//        for (Room room : roomDAO.getRooms(facilityId, null, Boolean.TRUE)) {
        Room[] rooms = roomDAO.getRooms(facilityId, null, Boolean.TRUE);
        for (int i=0;i<rooms.length;i++) {
        	Room room = rooms[i];
//            for (Bed bed : bedDAO.getBedsByRoom(room.getId(), Boolean.TRUE)) {
        	Bed[] beds2 = bedDAO.getBedsByRoom(room.getId(), Boolean.TRUE);
            for (int j=0;j<beds2.length;j++) {
            	Bed bed = beds2[j];
            	setAttributes(bed);

                if (!filterBed(bed, new Boolean(reserved))) {
                    beds.add(bed);
                }
            }
        }

//        return beds.toArray(new Bed[beds.size()]);
        Bed[] ret=  new Bed[beds.size()];
        for(int i=0;i<beds.size();i++){
        	ret[i]= (Bed)beds.get(i);
        }
        return ret;
    }

    /**
     * Get beds
     *
     * @return array of beds
     */
    public Bed[] getBeds() {
        Bed[] beds = bedDAO.getBedsByRoom(null, null);

//        for (Bed bed : beds) {
        for (int i=0;i<beds.length;i++) {
        	Bed bed = beds[i];
            setAttributes(bed);
        }

        return beds;
    }
    /**
     * Get beds by roomId
     * @param roomId
     * @return array of beds
     */
    public Bed[] getBedsByRoom(Integer roomId) {
        Bed[] beds = bedDAO.getBedsByRoom(roomId, Boolean.TRUE);
//        for (Bed bed : beds) {
        for (int i=0;i<beds.length;i++) {
        	Bed bed = beds[i];
            setAttributes(bed);
        }
        return beds;
    }
    public Bed[] getAvailableBedsByRoom(Integer roomId) {
        Bed[] beds = bedDAO.getBedsByRoom(roomId, Boolean.TRUE);
//        for (Bed bed : beds) {
        for (int i=0;i<beds.length;i++) {
           	Bed bed = beds[i];
            setAttributes(bed);
        }
        ArrayList lst = new ArrayList();
        for(int i=0;i<beds.length;i++){
          BedDemographic bdm = beds[i].getBedDemographic();
          if(bdm==null) lst.add(beds[i]);
        }
//        return (Bed[]) lst.toArray(new Bed[lst.size()]);
        Bed[] ret=  new Bed[lst.size()];
        for(int i=0;i<lst.size();i++){
        	ret[i]= (Bed)lst.get(i);
        }
        return ret;
    }

    public Bed[] getBedsForDeleteByRoom(Integer roomId) {
        Bed[] beds = bedDAO.getBedsByRoom(roomId, Boolean.TRUE);
        return beds;
    }

    /*
     * 
     * (e.g. used in BedManagerAction.saveBeds() )
     * 
     * @param rooms
     * @param beds
     * @return array of beds
     */
    public Bed[] getBedsForUnfilledRooms(Room[] rooms, Bed[] beds){
    	
    	if(rooms == null  ||  beds == null){
    		return null;
    	}
    	
    	List bedList = new ArrayList();
    	for(int i=0; i < beds.length; i++){
    		
    		for(int j=0; j < rooms.length; j++){
    			
    			if(beds[i].getRoomId().intValue() == rooms[j].getId().intValue()){
    				bedList.add(beds[i]);
    				if(beds[i].isActive())rooms[j].setTotalBedOccupancy(Integer.valueOf(rooms[j].getTotalBedOccupancy().intValue()+1));
    				break;
    			}
    		}
    	}
    	return (Bed[])bedList.toArray(new Bed[bedList.size()]);
    }
    /**
     * Get unreserved beds by roomId and clientBedId
     * @param roomId
     * @param clientBedId
     * @return array of beds
     */
    public Bed[] getReservedBedsByRoom(Integer roomId, boolean reserved) {
        Bed[] beds = bedDAO.getBedsByRoom(roomId, Boolean.TRUE);
        List bedList = new ArrayList();
//        for (Bed bed : beds) {
        for (int i=0;i<beds.length;i++) {
           	Bed bed = beds[i];
            setAttributes(bed);
            
            // filter for unreserved beds for roomId only
            if (!filterBed(bed, new Boolean(reserved))) {
            	bedList.add(bed);
            }
        }
//        return bedList.toArray(new Bed[bedList.size()]);
        Bed[] ret=  new Bed[bedList.size()];
        for(int i=0;i<bedList.size();i++){
        	ret[i]= (Bed)bedList.get(i);
        }
        return ret;
    }
    
    /**
     * Get unreserved beds by roomId and clientBedId
     * @param roomId
     * @param clientBedId
     * @return array of beds
     */
    public Bed[] getCurrentPlusUnreservedBedsByRoom(Integer roomId, Integer clientBedId, boolean reserved) {
        Bed[] beds = bedDAO.getBedsByRoom(roomId, Boolean.TRUE);
        List bedList = new ArrayList();
        
//        for (Bed bed : beds) {
          for (int i=0;i<beds.length;i++) {
        	Bed bed = beds[i];
            setAttributes(bed);
            
            // filter for unreserved beds for roomId only
            if (!filterBed(bed, new Boolean(reserved))) {
            	bedList.add(bed);
            }
            // include the reserved bed of this current room/bed combination for changing
            if(bed.getId().intValue() == clientBedId.intValue()  &&  bed.getRoomId().intValue() == roomId.intValue()){
            	bedList.add(bed);
            }
        }
//        return bedList.toArray(new Bed[bedList.size()]);
        Bed[] ret=  new Bed[bedList.size()];
        for(int i=0;i<bedList.size();i++){
        	ret[i]= (Bed)bedList.get(i);
        }
        return ret;
    }
 

    /**
	 * Used by AdmissionManager during processDischarge()  to  delete discharged 
	 * program-related room/bed reservation records
     * @param demographicNo
     * @param programId
     */
    public boolean isBedOfDischargeProgramAssignedToClient(Integer demographicNo, Integer programId){
    	/*
		 *(1)admission.clientId ===[table:bed_demographic]===>>  bedDemographic.bedId
		 *(2)bedDemographic.bedId ===[table:bed]===>>  bed.roomId
		 *(3)bed.roomId ===[table:room]===>>   room.programId
		 *(4)Compare  admission.programId  with  room.programId
	     *   - if true -->  delete  bedDemographic record
		 *   - if false -->  do nothing
    	 */
    	if(demographicNo == null  ||  programId == null){
    		return false;
    	}
        
    	Program program=programDao.getProgram(programId);
        Integer facilityId=null;
        if (program!=null) facilityId=program.getFacilityId();

        BedDemographic bedDemographic = bedDemographicManager.getBedDemographicByDemographic(demographicNo);
    	if(bedDemographic != null){
	    	Bed bed = getBed(bedDemographic.getId().getBedId());
	    	if(bed != null){
		    	Room room = roomDAO.getRoom(bed.getRoomId());
		    	if(room != null  &&  programId.intValue() == room.getProgramId().intValue()){
		    		return true;
		    	}
	    	}
    	}
    	return false;
    }
    
    public Integer[] getBedClientIds(Bed[] beds){
    	
    	Integer[] bedClientIds = null;
        if(beds != null  &&  beds.length > 0){
        	BedDemographic bd = null;
        	bedClientIds = new Integer[beds.length];
        	for(int i=0; i < beds.length; i++){
        		bd = bedDemographicManager.getBedDemographicByBed(beds[i].getId());
        		if(bd != null){
        			bedClientIds[i] = bd.getId().getDemographicNo();
        		}else{
        			bedClientIds[i] = null;
        		}
        	}
        }
        return bedClientIds;

    }

    //please write your code without JointAdmission if you need call addFamilyIdsToBeds(), dawson wrote May 26, 2008  
    public Bed[] addFamilyIdsToBeds(ClientManager clientManager, Bed[] beds){
    	return null;
    }
/*
    public Bed[] addFamilyIdsToBeds(ClientManager clientManager, Bed[] beds){
    	
    	if(clientManager == null  ||  beds == null  ||  beds.length <= 0){
    		return null;
    	}
    	Integer[] bedClientIds = new Integer[beds.length];
    	JointAdmission clientsJadmFamily = null;
    	boolean isFamilyHead = false;
    	Integer headRecord = 0;
    	
	    if(beds != null  &&  beds.length > 0){
	    	BedDemographic bd = null;
	    	
	    	for(int i=0; i < beds.length; i++){
	    		bd = bedDemographicManager.getBedDemographicByBed(beds[i].getId());
	    		if(bd != null){
	    			bedClientIds[i] = bd.getId().getDemographicNo();
	    			clientsJadmFamily = clientManager.getJointAdmission(Integer.valueOf(bedClientIds[i].toString()));
	    			isFamilyHead = clientManager.isClientFamilyHead(bedClientIds[i]);
	    			if(clientsJadmFamily != null){
	    				headRecord = Integer.valueOf(clientsJadmFamily.getHeadClientId().toString());
	    			}else if(isFamilyHead){
	    				headRecord = bedClientIds[i];
	    			}else{
	    				headRecord = null;
	    			}
	   			    isFamilyHead = false;
	    			beds[i].setFamilyId(headRecord);
	    		}else{
	    			bedClientIds[i] = null;
	    			beds[i].setFamilyId(null);
	    		}
	    	}
	    }
	    return beds;
    }
*/
    
    /**
     * @see org.oscarehr.PMmodule.service.BedManager#getBedTypes()
     */
    public BedType[] getBedTypes() {
        return bedDAO.getBedTypes();
    }

    /**
     * Add new beds
     *
     * @param numBeds
     *            number of beds
     * @throws BedReservedException
     *             bed is inactive and reserved
     */
    public void addBeds(Integer facilityId, Integer roomId, int numBeds) throws BedReservedException {
        if (numBeds < 1) {
            throw new IllegalArgumentException("numBeds must be greater than or equal to 1");
        }

        BedType defaultBedType = getDefaultBedType();

        for (int i = 0; i < numBeds; i++) {
            saveBed(Bed.create(facilityId, roomId, defaultBedType));
        }
    }

    /**
     * Save beds
     *
     * @param beds
     *            beds to save
     * @throws BedReservedException
     *             bed is inactive and reserved
     */
    public void saveBeds(Bed[] beds) throws BedReservedException, DuplicateBedNameException {
        if (beds == null) {
            throw new IllegalArgumentException("beds must not be null");
        }

		// Checks if there are beds with same name in the same room.
		ArrayList duplicateBeds = new ArrayList();
		
		for (int i = 0; i < beds.length; i++) {
			String name1 =beds[i].getName();
			for (int j = 0; j < beds.length; j++) {
				String name2 = beds[j].getName();
				if (i == j)
					continue;
				if (name1.trim().length()>0 && name1.equalsIgnoreCase(name2)
						&& beds[i].getRoomId().intValue() == beds[j].getRoomId().intValue()) {
					
					beds[i].setRoom(roomDAO.getRoom(beds[i].getRoomId()));
					duplicateBeds.add(beds[i]);
					StringBuffer errMsg = new StringBuffer();
					for (Iterator it = duplicateBeds.iterator(); it.hasNext();) {
						Bed theBed = (Bed) it.next();
						if(theBed != null){
							errMsg.append(theBed.getName() + " " + theBed.getRoomName());
						}
					}
					throw new DuplicateBedNameException(errMsg.toString());
//					return;
				}
			}
		}
//        for (Bed bed : beds) {
        for (int i=0;i<beds.length;i++) {
        	Bed bed =  beds[i];
	        if(bed.getName().trim().length()>0){ // bed without name will not be saved 
	        	bed.setRoomStart(Calendar.getInstance().getTime());
	            saveBed(bed);
        	}
        }
    }

    /**
     * Save bed
     *
     * @param bed
     *            bed to save
     * @throws BedReservedException
     *             bed is inactive and reserved
     */
    public void saveBed(Bed bed) throws BedReservedException {
        validate(bed);
        bedDAO.saveBed(bed);
    }

    public void deleteBed(Bed bed) {
        
        bedDAO.deleteBed(bed);
    }

    BedType getDefaultBedType() {
//        for (BedType bedType : getBedTypes()) {
    	BedType[] bedTypes = getBedTypes();
        for (int i=0;i<bedTypes.length;i++) {
        	BedType bedType = bedTypes[i];
            if (bedType.isDefault()) {
                return bedType;
            }
        }

        throw new IllegalStateException("no default bed type");

//        return null;
    }

    boolean filterBed(Bed bed, Boolean reserved) {
        if (reserved == null) {
            return false;
        }

        return reserved.booleanValue() != bed.isReserved();
    }

    void setAttributes(Bed bed) {
   		bed.setBedType(bedDAO.getBedType(bed.getBedTypeId()));
        if (bed.getRoomId() != null){
            bed.setRoom(roomDAO.getRoom(bed.getRoomId()));
        }

        Integer teamId = bed.getTeamId();

        if (teamId != null) {
            bed.setTeam(teamDAO.getProgramTeam(teamId));
        }

        BedDemographic bedDemographic = bedDemographicManager.getBedDemographicByBed(bed.getId());

        if (bedDemographic != null) {
            bed.setBedDemographic(bedDemographic);
        }
    }

    void validate(Bed bed) throws BedReservedException {
        if (bed == null) {
            throw new IllegalStateException("bed must not be null");
        }

        validateBed(bed.getId(), bed);
        validateBedType(bed.getBedTypeId());
        validateRoom(bed.getRoomId());
        validateTeam(bed.getTeamId());
    }

    void validateBed(Integer bedId, Bed bed) throws BedReservedException {
        if (bedId != null) {
            if (!bedDAO.bedExists(bedId)) {
                throw new IllegalStateException("no bed with id : " + bedId);
            }

            if (!bed.isActive() && bedDemographicManager.demographicExists(bed.getId())) {
                throw new BedReservedException("bed with id : " + bedId + " has a reservation");
            }
        }
    }

    void validateBedType(Integer bedTypeId) {
        if (!bedDAO.bedTypeExists(bedTypeId)) {
            throw new IllegalStateException("no bed type with id : " + bedTypeId);
        }
    }

    void validateRoom(Integer roomId) {
        if (roomId != null && !roomDAO.roomExists(roomId)) {
            throw new IllegalStateException("no room with id : " + roomId);
        }
    }

    void validateTeam(Integer teamId) {
        if (teamId != null && !teamDAO.teamExists(teamId)) {
            throw new IllegalStateException("no team with id : " + teamId);
        }
    }

}