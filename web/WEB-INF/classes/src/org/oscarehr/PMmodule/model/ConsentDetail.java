/*******************************************************************************
 * Copyright (c) 2008, 2009 Quatro Group Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU General Public License
 * which accompanies this distribution, and is available at
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 * Contributors:
 *     <Quatro Group Software Systems inc.>  <OSCAR Team>
 *******************************************************************************/
package org.oscarehr.PMmodule.model;

import java.io.Serializable;
import java.util.Calendar;

import com.quatro.util.Utility;

import oscar.MyDateFormat;

public class ConsentDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	private int hashCode = Integer.MIN_VALUE;// primary key

	private Integer id;// fields

	private Integer demographicNo;

	private String clientFirstName;

	private String clientLastName;

	private String providerNo;

	private String providerFirstName;

	private String providerLastName;

	private Calendar dateSigned;

	private String notes;

	private String agencyName;

	private String contactName;

	private String contactTitle;

	private String contactPhone;

	private String statePurpose;

	private Calendar startDate;

	private Calendar endDate;

	private String status;

	private boolean hardCopy;

	private String formName;

	private String formVersion;

	private String startDateStr;

	private String endDateStr;
	
	private Integer programId;
	private String programName;
	private String lnkAction;
	private Calendar lastUpdateDate;
	private Integer shelterId;

	public Integer getShelterId() {
		return shelterId;
	}

	public void setShelterId(Integer shelterId) {
		this.shelterId = shelterId;
	}

	public String getLnkAction() {
		return lnkAction;
	}

	public void setLnkAction(String lnkAction) {
		this.lnkAction = lnkAction;
	}

	public Integer getProgramId() {
		return programId;
	}

	public void setProgramId(Integer programId) {
		this.programId = programId;
	}

	public String getEndDateStr() {
		String eStr =endDateStr;
		if(Utility.IsEmpty(eStr))
		{			 
			if(endDate!=null) eStr=MyDateFormat.getStandardDate(endDate);
		}
			
		return eStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public String getStartDateStr() {
		String str = startDateStr;
		if(Utility.IsEmpty(str)){		
			if (startDate != null)
			str = MyDateFormat.getStandardDate(startDate);
		}
		return str;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	// constructors
	public ConsentDetail() {

	}

	/**
	 * Constructor for primary key
	 */
	public ConsentDetail(Integer id) {

		this.id = id;
	}

	/**
	 * Constructor for required fields
	 */
	public ConsentDetail(Integer id, Integer _demographicNo, String _providerNo) {

		this.id = id;
		this.demographicNo = _demographicNo;
		this.providerNo = _providerNo;
	}

	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof ConsentDetail))
			return false;
		else {
			ConsentDetail mObj = (ConsentDetail) obj;
			if (null == this.getId() || null == mObj.getId())
				return false;
			else
				return (this.getId().equals(mObj.getId()));
		}
	}

	public int hashCode() {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId())
				return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":"
						+ this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}

	public String getClientFormattedName() {
		return getClientLastName() + ", " + getClientFirstName();
	}

	public String getProviderFormattedName() {
		return getProviderLastName() + ", " + getProviderFirstName();
	}

	public String toString() {
		return super.toString();
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactTitle() {
		return contactTitle;
	}

	public void setContactTitle(String contactTitle) {
		this.contactTitle = contactTitle;
	}

	public Calendar getDateSigned() {
		return dateSigned;
	}

	public void setDateSigned(Calendar dateSigned) {
		this.dateSigned = dateSigned;
	}

	public Integer getDemographicNo() {
		return demographicNo;
	}

	public void setDemographicNo(Integer demographicNo) {
		this.demographicNo = demographicNo;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getFormVersion() {
		return formVersion;
	}

	public void setFormVersion(String formVersion) {
		this.formVersion = formVersion;
	}

	public boolean isHardCopy() {
		return hardCopy;
	}

	public void setHardCopy(boolean hardcopy) {
		this.hardCopy = hardcopy;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getProviderNo() {
		return providerNo;
	}

	public void setProviderNo(String providerNo) {
		this.providerNo = providerNo;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public String getStatePurpose() {
		return statePurpose;
	}

	public void setStatePurpose(String statePurpose) {
		this.statePurpose = statePurpose;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getClientFirstName() {
		return clientFirstName;
	}

	public void setClientFirstName(String clientFirstName) {
		this.clientFirstName = clientFirstName;
	}

	public String getClientLastName() {
		return clientLastName;
	}

	public void setClientLastName(String clientLastName) {
		this.clientLastName = clientLastName;
	}

	public String getProviderFirstName() {
		return providerFirstName;
	}

	public void setProviderFirstName(String providerFirstName) {
		this.providerFirstName = providerFirstName;
	}

	public String getProviderLastName() {
		return providerLastName;
	}

	public void setProviderLastName(String providerLastName) {
		this.providerLastName = providerLastName;
	}

	public String getDateSignedStr() {
		String str = "";
		if (dateSigned != null)
			str = MyDateFormat.getStandardDate(dateSigned);
		return str;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public Calendar getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Calendar lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
}
