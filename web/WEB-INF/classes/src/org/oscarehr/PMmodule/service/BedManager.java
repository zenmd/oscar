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
import java.util.List;
import org.oscarehr.PMmodule.dao.BedDAO;
import org.oscarehr.PMmodule.dao.RoomDemographicDAO;
import org.oscarehr.PMmodule.dao.RoomDAO;
import org.oscarehr.PMmodule.model.Bed;
import org.oscarehr.PMmodule.model.BedType;

public class BedManager {

    private BedDAO bedDAO;
    private RoomDAO roomDAO;
    private RoomDemographicDAO roomDemographicDAO;

    public void setBedDAO(BedDAO bedDAO) {
        this.bedDAO = bedDAO;
    }

    public void setRoomDAO(RoomDAO roomDAO) {
        this.roomDAO = roomDAO;
    }

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

    public Bed[] getActiveBedsByRoom(Integer roomId) {
        Bed[] beds = bedDAO.getBedsByRoom(roomId, Boolean.TRUE);
        for (int i=0;i<beds.length;i++) {
        	Bed bed = beds[i];
            setAttributes(bed);
        }
        return beds;
    }

    public Bed[] getAllBedsByRoom(Integer roomId) {
        Bed[] beds = bedDAO.getBedsByRoom(roomId, null);
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
          if(beds[i].getBedOccupied().intValue()==0) lst.add(beds[i]);
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
    
    public BedType[] getBedTypes() {
        return bedDAO.getBedTypes();
    }
    public BedType[] getActiveBedTypes() {
        return bedDAO.getActiveBedTypes();
    }


    public void saveBed(Bed bed) throws IllegalStateException{
        validate(bed);
        bedDAO.saveBed(bed);
    }

    public void deleteBed(Bed bed) {
        
        bedDAO.deleteBed(bed);
    }

    void setAttributes(Bed bed) {
   		bed.setBedType(bedDAO.getBedType(bed.getBedTypeId()));
    }

    void validate(Bed bed) {
        if (bed == null) {
            throw new IllegalStateException("bed must not be null");
        }

        validateBedType(bed.getBedTypeId());
        validateRoom(bed.getRoomId());
        
        if(!bed.isActive()){
        	List bdm = roomDemographicDAO.getRoomDemographicByBed(bed.getId());
            if(bdm.size() > 0)
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

	public void setRoomDemographicDAO(RoomDemographicDAO roomDemographicDAO) {
		this.roomDemographicDAO = roomDemographicDAO;
	}

}