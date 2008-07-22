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
import org.oscarehr.PMmodule.utility.DateTimeFormatUtils;
import org.oscarehr.common.dao.Auditable;

/**
 * RoomDemographic
 */
public class RoomDemographic implements Auditable, Serializable {

	private static final long serialVersionUID = 1L;
    public static String REF = "RoomDemographic";
    public static String PROP_PROVIDER_NO = "providerNo";
    public static String PROP_ASSIGN_START = "assignStart";
    public static String PROP_ASSIGN_END = "assignEnd";
    public static String PROP_COMMENT = "comment";
    public static String PROP_ID = "id";

    private int hashCode = Integer.MIN_VALUE;// primary key

    private RoomDemographicPK id;// fields
    private Integer admissionId;
    private Integer bedId;
    private String bedName;
    private String roomName;
    private String providerNo;
    private Date assignStart;
    private String comment;
    private Provider provider;
//    private Room room;
    private Demographic demographic;
    private Calendar lastUpdateDate;    

	public Calendar getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Calendar lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}


    public static RoomDemographic create(Integer demographicNo, String providerNo) {
		RoomDemographicPK id = new RoomDemographicPK();
		id.setDemographicNo(demographicNo);

		RoomDemographic roomDemographic = new RoomDemographic();
		roomDemographic.setId(id);
		roomDemographic.setProviderNo(providerNo);

		// set assign start to today and assign end to today + duration
		Date today = DateTimeFormatUtils.getToday();
		
		roomDemographic.setAssignStart(today);
//		roomDemographic.setAssignEnd(today);
		return roomDemographic;
	}

    // constructors
	public RoomDemographic () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public RoomDemographic (org.oscarehr.PMmodule.model.RoomDemographicPK id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
/*	
	public RoomDemographic (
		org.oscarehr.PMmodule.model.RoomDemographicPK id,
		String providerNo,
		java.util.Date assignStart,
		java.util.Date assignEnd,
		String comment) {
		
		this.setId(id);
		this.setProviderNo(providerNo);
		this.setAssignStart(assignStart);
		this.setAssignEnd(assignEnd);
		this.setComment(comment);
		initialize();
	}
*/
	
	public void setProvider(Provider provider) {
	    this.provider = provider;
    }
	
	
	public void setDemographic(Demographic demographic) {
	    this.demographic = demographic;
    }
/*	
	public boolean isExpired() {
		Date end = DateTimeFormatUtils.getDateFromDate(getAssignEnd());
		Date today = DateTimeFormatUtils.getToday();
		
		return end.before(today);
	}
*/	

/*	
	public boolean isValidAssign() {
		Date start = DateTimeFormatUtils.getDateFromDate(getAssignStart());
		Date end = DateTimeFormatUtils.getDateFromDate(getAssignEnd());
		Date today = DateTimeFormatUtils.getToday();

		return start.before(end) && today.before(end);
	}
*/
	
	public String getProviderName() {
		return provider.getFormattedName();
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getDemographicName() {
		return demographic != null ? demographic.getFormattedName() : null;
	}
	
/*	
	// property adapted for view
	public String getStrAssignEnd() {
		return DateTimeFormatUtils.getStringFromDate(getAssignEnd());
	}

	// property adapted for view
	public void setStrAssignEnd(String strAssignEnd) {
		setAssignEnd(DateTimeFormatUtils.getDateFromString(strAssignEnd));
	}

	public void setAssignEnd(Integer duration) {
		if (duration != null && duration.intValue() > 0) {
			Date startPlusDuration = DateTimeFormatUtils.getFuture(getAssignStart(), duration);
			Date end = DateTimeFormatUtils.getDateFromDate(getAssignEnd());
			
			// start + duration > end
			if (startPlusDuration.after(end)) {
				setAssignEnd(startPlusDuration);
			}
		}
	}
*/
	
	//@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

    protected void initialize () {}

    /**
	 * Return the unique identifier of this class
* @hibernate.id
*/
    public RoomDemographicPK getId () {
        return id;
    }

    /**
	 * Set the unique identifier of this class
     * @param id the new ID
     */
    public void setId (RoomDemographicPK id) {
        this.id = id;
        this.hashCode = Integer.MIN_VALUE;
    }

    /**
	 * Return the value associated with the column: provider_no
     */
    public String getProviderNo () {
        return providerNo;
    }

    /**
	 * Set the value related to the column: provider_no
     * @param providerNo the provider_no value
     */
    public void setProviderNo (String providerNo) {
        this.providerNo = providerNo;
    }

    /**
	 * Return the value associated with the column: assign_start
     */
    public Date getAssignStart () {
        return assignStart;
    }

    /**
	 * Set the value related to the column: assign_start
     * @param assignStart the assign_start value
     */
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