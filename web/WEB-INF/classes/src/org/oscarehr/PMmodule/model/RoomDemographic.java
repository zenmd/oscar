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
* Toronto, Ontario, Canada  - UPDATED: Quatro Group 2008/2009
*/

package org.oscarehr.PMmodule.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.oscarehr.PMmodule.utility.DateTimeFormatUtils;
import org.oscarehr.common.dao.Auditable;

public class RoomDemographic implements Auditable, Serializable {

	private static final long serialVersionUID = 1L;
    private int hashCode = Integer.MIN_VALUE;

    private RoomDemographicPK id;
    private Integer admissionId;
    private Integer bedId;
    private String bedName;
    private String roomName;
    private String providerNo;
    private Date assignStart;
    private String comment;
    private Provider provider;
    private Demographic demographic;
    private Calendar lastUpdateDate;    

	public Calendar getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Calendar lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public RoomDemographic () {
	}

	public RoomDemographic (org.oscarehr.PMmodule.model.RoomDemographicPK id) {
		this.setId(id);
	}

	public void setProvider(Provider provider) {
	    this.provider = provider;
    }
	
	public void setDemographic(Demographic demographic) {
	    this.demographic = demographic;
    }
	
	public String getProviderName() {
		return provider.getFormattedName();
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getDemographicName() {
		return demographic != null ? demographic.getFormattedName() : null;
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

    public RoomDemographicPK getId () {
        return id;
    }

    public void setId (RoomDemographicPK id) {
        this.id = id;
        this.hashCode = Integer.MIN_VALUE;
    }

    public String getProviderNo () {
        return providerNo;
    }

    public void setProviderNo (String providerNo) {
        this.providerNo = providerNo;
    }

    public Date getAssignStart () {
        return assignStart;
    }

    public void setAssignStart (Date assignStart) {
        this.assignStart = assignStart;
    }

    public boolean equals (Object obj) {
        if (null == obj) return false;
        if (!(obj instanceof RoomDemographic)) return false;
        else {
            RoomDemographic roomDemographic = (RoomDemographic) obj;
            if (null == this.getId() || null == roomDemographic.getId()) return false;
            else return (this.getId().equals(roomDemographic.getId()));
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


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getAdmissionId() {
		return admissionId;
	}

	public void setAdmissionId(Integer admissionId) {
		this.admissionId = admissionId;
	}

	public Integer getBedId() {
		return bedId;
	}

	public void setBedId(Integer bedId) {
		this.bedId = bedId;
	}

	public String getBedName() {
		return bedName;
	}

	public void setBedName(String bedName) {
		this.bedName = bedName;
	}

	public String getRoomName() {
		return roomName;
	}
}