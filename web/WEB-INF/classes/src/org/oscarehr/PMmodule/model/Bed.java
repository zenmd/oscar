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

package org.oscarehr.PMmodule.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Bed implements Serializable {

    private static final long serialVersionUID = 1L;
/*
    private static final String DEFAULT_NAME = "";
    private static final boolean DEFAULT_ACTIVE = true;
    public static String REF = "Bed";
    public static String PROP_ACTIVE = "active";
    public static String PROP_BED_TYPE_ID = "bedTypeId";
    public static String PROP_NAME = "name";
    public static String PROP_ROOM_ID = "roomId";
    public static String PROP_ID = "id";
*/
    private int hashCode = Integer.MIN_VALUE;// primary key

    private Integer id;// fields
    private Integer bedTypeId;
    private Integer roomId;
    private String name;
    private boolean active;

    private BedType bedType;
//    private Room room;
    private Integer bedOccupied;
    
//    private Integer communityProgramId;

    private String lastUpdateUser;
    private Calendar lastUpdateDate;

    public Calendar getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Calendar lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
/*
    // constructors
    public Bed () {
        initialize();
    }

    public Bed (Integer id) {
        this.setId(id);
        initialize();
    }

    public Bed (
            Integer id,
            Integer bedTypeId,
            Integer roomId,
            String name,
            boolean active) {

        this.setId(id);
        this.setBedTypeId(bedTypeId);
        this.setRoomId(roomId);
        this.setName(name);
        this.setActive(active);
        initialize();
    }
*/
/*	
    public boolean isLatePass() {
        return bedDemographic != null ? bedDemographic.isLatePass() : false;
    }
*/
    public String getBedTypeName() {
        return bedType.getName();
    }
/*
    public String getRoomName() {
        return room.getName();
    }

    public String getProgramName() {
        return room.getProgramName();
    }
*/    
    
/*
    public BedDemographic getBedDemographic() {
        return bedDemographic;
    }

    public String getDemographicName() {
        return bedDemographic != null ? bedDemographic.getDemographicName() : null;
    }
*/
    
/*
    public Integer getCommunityProgramId() {
        return communityProgramId;
    }
*/
    public BedType getBedType() {
        return this.bedType;
    }

    public void setBedType(BedType bedType) {
        this.bedType = bedType;
    }
    
/*
    public void setRoom(Room room) {
        this.room = room;
    }
*/    
    
/*
    public void setBedDemographic(BedDemographic bedDemographic) {
        this.bedDemographic = bedDemographic;
    }

    public void setLatePass(boolean latePass) {
        if (bedDemographic != null) {
            bedDemographic.setLatePass(latePass);
        }
    }
*/
    
/*
    public void setCommunityProgramId(Integer communityProgramId) {
        this.communityProgramId = communityProgramId;
    }
*/
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

//    protected void initialize () {}

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
        this.hashCode = Integer.MIN_VALUE;
    }

    public Integer getBedTypeId () {
        return bedTypeId;
    }

    public void setBedTypeId (Integer bedTypeId) {
        this.bedTypeId = bedTypeId;
    }

    public Integer getRoomId () {
        return roomId;
    }

    public void setRoomId (Integer roomId) {
        this.roomId = roomId;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public boolean isActive () {
        return active;
    }

    public void setActive (boolean active) {
        this.active = active;
    }

    public boolean equals (Object obj) {
        if (null == obj) return false;
        if (!(obj instanceof Bed)) return false;
        else {
            Bed bed = (Bed) obj;
            if (null == this.getId() || null == bed.getId()) return false;
            else return (this.getId().equals(bed.getId()));
        }
    }

    public int hashCode () {
        if (Integer.MIN_VALUE == this.hashCode) {
            if (null == this.getId()) return super.hashCode();
            else {
                String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
                this.hashCode = hashStr.hashCode();
            }
        }
        return this.hashCode;
    }

	public Integer getBedOccupied() {
		return bedOccupied;
	}

	public void setBedOccupied(Integer bedOccupied) {
		this.bedOccupied = bedOccupied;
	}

}