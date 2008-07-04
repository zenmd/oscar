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

                beds.add(bed);
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

                beds.add(bed);
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

            beds.add(bed);
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

                beds.add(bed);
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

    
    public BedType[] getBedTypes() {
        return bedDAO.getBedTypes();
    }


    /**
     * Save bed
     *
     * @param bed
     *            bed to save
     * @throws BedReservedException
     *             bed is inactive and reserved
     */
    public void saveBed(Bed bed) throws IllegalStateException{
        validate(bed);
        bedDAO.saveBed(bed);
    }

    public void deleteBed(Bed bed) {
        
        bedDAO.deleteBed(bed);
    }
/*
    boolean filterBed(Bed bed, Boolean reserved) {
        if (reserved == null) {
            return false;
        }

        return reserved.booleanValue() != bed.isReserved();
    }
*/
    void setAttributes(Bed bed) {
   		bed.setBedType(bedDAO.getBedType(bed.getBedTypeId()));
        if (bed.getRoomId() != null){
            bed.setRoom(roomDAO.getRoom(bed.getRoomId()));
        }

        BedDemographic bedDemographic = bedDemographicManager.getBedDemographicByBed(bed.getId());

        if (bedDemographic != null) {
            bed.setBedDemographic(bedDemographic);
        }
    }

    void validate(Bed bed) {
        if (bed == null) {
            throw new IllegalStateException("bed must not be null");
        }

        validateBedType(bed.getBedTypeId());
        validateRoom(bed.getRoomId());
        
        if(bed.isActive()==false){
        	BedDemographic bdm = bedDemographicManager.getBedDemographicByBed(bed.getId());
            if(bdm!=null)
          	  throw new IllegalStateException("bed can not be inactive with client occupied");
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

}