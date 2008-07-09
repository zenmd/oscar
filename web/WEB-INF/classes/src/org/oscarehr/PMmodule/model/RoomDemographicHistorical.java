package org.oscarehr.PMmodule.model;

import java.util.Calendar;

public class RoomDemographicHistorical implements java.io.Serializable {

	// Fields


	private Integer roomId;
	private Integer admissionId;
	private Integer demographicNo;
	private Calendar usageEnd;
	private Calendar usageStart;
	private String roomName;

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
	
	// Constructors

	/** default constructor */
	public RoomDemographicHistorical() {
	}


	public RoomDemographicHistorical(Integer roomId, Integer admissionId, Integer demographicNo, Calendar usageStart, Calendar usageEnd) {
		this.roomId = roomId;
		this.admissionId = admissionId;
		this.demographicNo = demographicNo;
		this.usageEnd = usageEnd;
		this.usageStart = usageStart;
	}


	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RoomDemographicHistorical))
			return false;
		RoomDemographicHistorical castOther = (RoomDemographicHistorical) other;

		return ((this.getAdmissionId() == castOther.getAdmissionId()) || (this
				.getAdmissionId() != null
				&& castOther.getAdmissionId() != null && this
				.getAdmissionId().equals(castOther.getAdmissionId())))
				&& ((this.getRoomId() == castOther.getRoomId()) || (this
						.getRoomId() != null
						&& castOther.getRoomId() != null && this
						.getRoomId().equals(castOther.getRoomId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getAdmissionId() == null ? 0 : this.getAdmissionId()
						.hashCode());
		result = 37
				* result
				+ (getRoomId() == null ? 0 : this.getRoomId()
						.hashCode());
		return result;
	}


	public Integer getAdmissionId() {
		return admissionId;
	}


	public void setAdmissionId(Integer admissionId) {
		this.admissionId = admissionId;
	}


	public Integer getRoomId() {
		return roomId;
	}


	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}


	public Integer getDemographicNo() {
		return demographicNo;
	}


	public void setDemographicNo(Integer demographicNo) {
		this.demographicNo = demographicNo;
	}


	public Calendar getUsageEnd() {
		return usageEnd;
	}


	public void setUsageEnd(Calendar usageEnd) {
		this.usageEnd = usageEnd;
	}


	public Calendar getUsageStart() {
		return usageStart;
	}


	public void setUsageStart(Calendar usageStart) {
		this.usageStart = usageStart;
	}


	public String getRoomName() {
		return roomName;
	}


	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
}