package org.oscarehr.PMmodule.model;

import java.util.Calendar;

public class BedDemographicHistorical implements java.io.Serializable {

	// Fields


	private Integer bedId;
	private Integer admissionId;
	private Integer demographicNo;
	private Calendar usageEnd;
	private Calendar usageStart;
	// Constructors

	/** default constructor */
	public BedDemographicHistorical() {
	}


	public BedDemographicHistorical(Integer bedId, Integer admissionId, Integer demographicNo, Calendar usageStart, Calendar usageEnd) {
		this.bedId = bedId;
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
		if (!(other instanceof BedDemographicHistorical))
			return false;
		BedDemographicHistorical castOther = (BedDemographicHistorical) other;

		return ((this.getAdmissionId() == castOther.getAdmissionId()) || (this
				.getAdmissionId() != null
				&& castOther.getAdmissionId() != null && this
				.getAdmissionId().equals(castOther.getAdmissionId())))
				&& ((this.getBedId() == castOther.getBedId()) || (this
						.getBedId() != null
						&& castOther.getBedId() != null && this
						.getBedId().equals(castOther.getBedId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getAdmissionId() == null ? 0 : this.getAdmissionId()
						.hashCode());
		result = 37
				* result
				+ (getBedId() == null ? 0 : this.getBedId()
						.hashCode());
		return result;
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
}