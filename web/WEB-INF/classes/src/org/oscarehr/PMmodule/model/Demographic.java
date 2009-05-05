/**
 * Copyright (C) 2007.
 * Centre for Research on Inner City Health, St. Michael's Hospital, Toronto, Ontario, Canada.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.oscarehr.PMmodule.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.oscarehr.PMmodule.utility.DateTimeFormatUtils;
import org.oscarehr.PMmodule.utility.Utility;
import oscar.MyDateFormat;

/**
 * This is the object class that relates to the demographic table. Any
 * customizations belong here.
 */
public class Demographic implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String DEFAULT_SEX = "M";

	// private static final String DEFAULT_PATIENT_STATUS =
	// PatientStatus.AC.name();
	private static final String DEFAULT_HEATH_CARD_TYPE = "ON";

	private static final String DEFAULT_FUTURE_DATE = "2100/01/01";

	public static final String CONSENT_GIVEN_KEY = "CONSENT_GIVEN";

	public static final String METHOD_OBTAINED_KEY = "METHOD_OBTAINED";

	private int hashCode = Integer.MIN_VALUE;// primary key

	private Integer demographicNo;// fields

	private String phone;

	private String patientStatus;

	private String rosterStatus;

	private String providerNo;

	private String pin;

	private String hin;

	private String address;

	private String province;

	private String ver;

	private Calendar dateOfBirth;

	private String sex;

	private String sexDesc;

	private Date dateJoined;

	private String familyDoctor;

	private String city;

	private String firstName;

	private String postal;

	private Date hcRenewDate;

	private String phone2;

	private String pcnIndicator;

	private Date endDate;

	private String lastName;

	private String hcType;

	private String chartNo;

	private String email;

	private Date effDate;

	private String effDateTxt;

	private String links;

	private DemographicExt[] extras;

	private String alias;

	private String previousAddress;

	private String children;

	private String sourceOfIncome;

	private String citizenship;

	private String sin;

	private Integer headRecord = null;

	private Set subRecord = null;

	private String benefitUnitStatus;

	private int activeCount = 0;

	private int hsAlertCount = 0;

	private boolean merged = false;
	
	private boolean familyHead = false;

	private Calendar lastUpdateDate;

	public static final String ConsentGiven_ALL = "ALL";

	public static final String ConsentGiven_CIRCLE_OF_CARE = "CIRCLE_OF_CARE";

	public static final String ConsentGiven_EMPI = "EMPI";

	public static final String ConsentGiven_INCAPACITATED_EMERGENCY = "INCAPACITATED_EMERGENCY";

	public static final String ConsentGiven_NONE = "NONE";

	/*
	 * public enum ConsentGiven { ALL, CIRCLE_OF_CARE, EMPI,
	 * INCAPACITATED_EMERGENCY, NONE }
	 */
	public static final String MethodObtained_IMPLICIT = "IMPLICIT";

	public static final String MethodObtained_EXPLICIT = "EXPLICIT";

	/*
	 * public enum MethodObtained { IMPLICIT, EXPLICIT }
	 */

	/*
	 * public enum PatientStatus { AC,IN,DE,IC,ID,MO,FI }
	 */
	public static Demographic create(String firstName, String lastName,
			Calendar dateOfBirth, String hin, String ver,
			boolean applyDefaultBirthDate) {
		Demographic demographic = new Demographic();

		demographic.setFirstName(firstName);
		demographic.setLastName(lastName);
		demographic.setDateOfBirth(dateOfBirth);
		demographic.setHin(hin);
		demographic.setVer(ver);

		demographic.setChartNo(StringUtils.EMPTY);
		demographic.setFamilyDoctor(StringUtils.EMPTY);
		demographic.setHcType(DEFAULT_HEATH_CARD_TYPE);
		demographic.setPcnIndicator(StringUtils.EMPTY);
		// demographic.setPatientStatus(DEFAULT_PATIENT_STATUS);
		demographic.setPin(StringUtils.EMPTY);
		demographic.setRosterStatus(StringUtils.EMPTY);
		demographic.setSex(DEFAULT_SEX);

		demographic.setDateJoined(DateTimeFormatUtils.getToday());
		demographic.setEffDate(DateTimeFormatUtils.getToday());
		demographic.setEndDate(DateTimeFormatUtils
				.getDateFromString(DEFAULT_FUTURE_DATE));
		demographic.setHcRenewDate(DateTimeFormatUtils
				.getDateFromString(DEFAULT_FUTURE_DATE));

		demographic.setAddress(StringUtils.EMPTY);
		demographic.setCity(StringUtils.EMPTY);
		demographic.setProvince(StringUtils.EMPTY);
		demographic.setPostal(StringUtils.EMPTY);
		demographic.setEmail(StringUtils.EMPTY);
		demographic.setPhone(StringUtils.EMPTY);
		demographic.setPhone2(StringUtils.EMPTY);

		return demographic;
	}

	// constructors
	public Demographic() {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public Demographic(Integer demographicNo) {
		this.setDemographicNo(demographicNo);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public Demographic(Integer demographicNo, String firstName, String lastName) {

		this.setDemographicNo(demographicNo);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		initialize();
	}

	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id generator-class="native" column="demographic_no"
	 */
	public Integer getDemographicNo() {
		return demographicNo;
	}

	/**
	 * Set the unique identifier of this class
	 * 
	 * @param demographicNo
	 *            the new ID
	 */
	public void setDemographicNo(Integer demographicNo) {
		this.demographicNo = demographicNo;
		this.hashCode = Integer.MIN_VALUE;
	}

	/**
	 * Return the value associated with the column: phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Set the value related to the column: phone
	 * 
	 * @param phone
	 *            the phone value
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Return the value associated with the column: patient_status
	 */
	public String getPatientStatus() {
		return patientStatus;
	}

	/**
	 * Set the value related to the column: patient_status
	 * 
	 * @param patientStatus
	 *            the patient_status value
	 */
	public void setPatientStatus(String patientStatus) {
		this.patientStatus = patientStatus;
	}

	/**
	 * Return the value associated with the column: roster_status
	 */
	public String getRosterStatus() {
		return rosterStatus;
	}

	/**
	 * Set the value related to the column: roster_status
	 * 
	 * @param rosterStatus
	 *            the roster_status value
	 */
	public void setRosterStatus(String rosterStatus) {
		this.rosterStatus = rosterStatus;
	}

	/**
	 * Return the value associated with the column: provider_no
	 */
	public String getProviderNo() {
		return providerNo;
	}

	/**
	 * Set the value related to the column: provider_no
	 * 
	 * @param providerNo
	 *            the provider_no value
	 */
	public void setProviderNo(String providerNo) {
		this.providerNo = providerNo;
	}

	/**
	 * Return the value associated with the column: pin
	 */
	public String getPin() {
		return pin;
	}

	/**
	 * Set the value related to the column: pin
	 * 
	 * @param pin
	 *            the pin value
	 */
	public void setPin(String pin) {
		this.pin = pin;
	}

	/**
	 * Return the value associated with the column: hin
	 */
	public String getHin() {
		return hin;
	}

	/**
	 * Set the value related to the column: hin
	 * 
	 * @param hin
	 *            the hin value
	 */
	public void setHin(String hin) {
		this.hin = hin;
	}

	/**
	 * Return the value associated with the column: address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Set the value related to the column: address
	 * 
	 * @param address
	 *            the address value
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Return the value associated with the column: province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * Set the value related to the column: province
	 * 
	 * @param province
	 *            the province value
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * Return the value associated with the column: ver
	 */
	public String getVer() {
		return ver;
	}

	/**
	 * Set the value related to the column: ver
	 * 
	 * @param ver
	 *            the ver value
	 */
	public void setVer(String ver) {
		this.ver = ver;
	}

	public String getDob() {
		return MyDateFormat.getStandardDate(dateOfBirth);
	}

	/**
	 * Return the value associated with the column: date_of_birth
	 */
	public Calendar getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * Set the value related to the column: date_of_birth
	 * 
	 * @param dateOfBirth
	 *            the date_of_birth value
	 */
	public void setDateOfBirth(Calendar dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setDob(String dateOfBirth) {
		this.dateOfBirth = MyDateFormat.getCalendar(dateOfBirth);
	}

	/**
	 * Return the value associated with the column: sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * Set the value related to the column: sex
	 * 
	 * @param sex
	 *            the sex value
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * Return the value associated with the column: date_joined
	 */
	public Date getDateJoined() {
		return dateJoined;
	}

	/**
	 * Set the value related to the column: date_joined
	 * 
	 * @param dateJoined
	 *            the date_joined value
	 */
	public void setDateJoined(Date dateJoined) {
		this.dateJoined = dateJoined;
	}

	/**
	 * Return the value associated with the column: family_doctor
	 */
	public String getFamilyDoctor() {
		return familyDoctor;
	}

	/**
	 * Set the value related to the column: family_doctor
	 * 
	 * @param familyDoctor
	 *            the family_doctor value
	 */
	public void setFamilyDoctor(String familyDoctor) {
		this.familyDoctor = familyDoctor;
	}

	/**
	 * Return the value associated with the column: city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Set the value related to the column: city
	 * 
	 * @param city
	 *            the city value
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Return the value associated with the column: first_name
	 */
	public String getFirstName() {
		return firstName;
	}
	public String getFirstNameJS()
	{
		return oscar.Misc.JSEscape(firstName);
	}

	/**
	 * Set the value related to the column: first_name
	 * 
	 * @param firstName
	 *            the first_name value
	 */
	public void setFirstName(String firstName) {
		if (firstName != null)
			this.firstName = firstName.trim();
		else
			this.firstName = firstName;
	}

	/**
	 * Return the value associated with the column: postal
	 */
	public String getPostal() {
		return postal;
	}

	/**
	 * Set the value related to the column: postal
	 * 
	 * @param postal
	 *            the postal value
	 */
	public void setPostal(String postal) {
		this.postal = postal;
	}

	/**
	 * Return the value associated with the column: hc_renew_date
	 */
	public Date getHcRenewDate() {
		return hcRenewDate;
	}

	/**
	 * Set the value related to the column: hc_renew_date
	 * 
	 * @param hcRenewDate
	 *            the hc_renew_date value
	 */
	public void setHcRenewDate(Date hcRenewDate) {
		this.hcRenewDate = hcRenewDate;
	}

	/**
	 * Return the value associated with the column: phone2
	 */
	public String getPhone2() {
		return phone2;
	}

	/**
	 * Set the value related to the column: phone2
	 * 
	 * @param phone2
	 *            the phone2 value
	 */
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	/**
	 * Return the value associated with the column: pcn_indicator
	 */
	public String getPcnIndicator() {
		return pcnIndicator;
	}

	/**
	 * Set the value related to the column: pcn_indicator
	 * 
	 * @param pcnIndicator
	 *            the pcn_indicator value
	 */
	public void setPcnIndicator(String pcnIndicator) {
		this.pcnIndicator = pcnIndicator;
	}

	/**
	 * Return the value associated with the column: end_date
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Set the value related to the column: end_date
	 * 
	 * @param endDate
	 *            the end_date value
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Return the value associated with the column: last_name
	 */
	public String getLastName() {
		return lastName;
	}
	public String getLastNameJS()
	{
		return oscar.Misc.JSEscape(lastName);
	}
	/**
	 * Set the value related to the column: last_name
	 * 
	 * @param lastName
	 *            the last_name value
	 */
	public void setLastName(String lastName) {
		if (lastName != null)
			this.lastName = lastName.trim();
		else
			this.lastName = lastName;
	}

	/**
	 * Return the value associated with the column: hc_type
	 */
	public String getHcType() {
		return hcType;
	}

	/**
	 * Set the value related to the column: hc_type
	 * 
	 * @param hcType
	 *            the hc_type value
	 */
	public void setHcType(String hcType) {
		this.hcType = hcType;
	}

	/**
	 * Return the value associated with the column: chart_no
	 */
	public String getChartNo() {
		return chartNo;
	}

	/**
	 * Set the value related to the column: chart_no
	 * 
	 * @param chartNo
	 *            the chart_no value
	 */
	public void setChartNo(String chartNo) {
		this.chartNo = chartNo;
	}

	/**
	 * Return the value associated with the column: email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set the value related to the column: email
	 * 
	 * @param email
	 *            the email value
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Return the value associated with the column: eff_date
	 */
	public String getEffDateTxt() {
		return effDateTxt;
	}

	public Date getEffDate() {
		return effDate;
	}

	/**
	 * Set the value related to the column: eff_date
	 * 
	 * @param effDate
	 *            the eff_date value
	 */
	public void setEffDate(Date effDate) {
		this.effDate = effDate;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		if (alias != null)
			this.alias = alias.trim();
		else
			this.alias = alias;
	}

	public String getChildren() {
		return children;
	}

	public void setChildren(String children) {
		this.children = children;
	}

	public String getCitizenship() {
		return citizenship;
	}

	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

	public String getPreviousAddress() {
		return previousAddress;
	}

	public void setPreviousAddress(String previousAddress) {
		this.previousAddress = previousAddress;
	}

	public String getSin() {
		return sin;
	}

	public void setSin(String sin) {
		this.sin = sin;
	}

	public String getSourceOfIncome() {
		return sourceOfIncome;
	}

	public void setSourceOfIncome(String sourceOfIncome) {
		this.sourceOfIncome = sourceOfIncome;
	}

	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof Demographic))
			return false;
		else {
			Demographic demographic = (Demographic) obj;
			if (null == this.getDemographicNo()
					|| null == demographic.getDemographicNo())
				return false;
			else
				return (this.getDemographicNo().equals(demographic
						.getDemographicNo()));
		}
	}

	public int hashCode() {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getDemographicNo())
				return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":"
						+ this.getDemographicNo().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}

	public String toString() {
		return super.toString();
	}

	protected void initialize() {
		links = StringUtils.EMPTY;
	}

	public String addZero(String text, int num) {
		text = text.trim();

		for (int i = text.length(); i < num; i++) {
			text = "0" + text;
		}

		return text;
	}

	public String getAge() {
		if (null == getDateOfBirth())
			return "0";
		return (Utility.calcAge(getDateOfBirth().getTime()));
	}

	public String getAgeAsOf(Date asofDate) {
		if (null == getDateOfBirth())
			return "";
		return Utility.calcAgeAtDate(getDateOfBirth().getTime(), asofDate);
	}

	public int getAgeInYears() {
		return Utility.getNumYears(getDateOfBirth().getTime(), Calendar
				.getInstance().getTime());
	}

	public int getAgeInYearsAsOf(Date asofDate) {
		return Utility.getNumYears(getDateOfBirth().getTime(), asofDate);
	}

	public DemographicExt[] getExtras() {
		return extras;
	}

	public String getFormattedDob() {
		return MyDateFormat.getStandardDate(getDateOfBirth());
	}

	public String getFormattedLinks() {
		StringBuffer response = new StringBuffer();

		if (getNumLinks() > 0) {
			String[] links = getLinks().split(",");
			for (int x = 0; x < links.length; x++) {
				if (response.length() > 0) {
					response.append(",");
				}
			}
		}

		return response.toString();
	}

	public String getFormattedName() {
		return getLastName() + ", " + getFirstName();
	}

	public String getLinks() {
		return links;
	}

	public int getNumLinks() {
		if (getLinks() == null) {
			return 0;
		}

		if (getLinks().equals("")) {
			return 0;
		}

		return getLinks().split(",").length;
	}

	public void setExtras(DemographicExt[] extras) {
		this.extras = extras;
	}

	public void setLinks(String links) {
		this.links = links;
	}

	public Integer getHeadRecord() {
		return headRecord;
	}

	public void setHeadRecord(Integer headRecord) {
		this.headRecord = headRecord;
	}

	public Integer getCurrentRecord() {
		if (headRecord != null)
			return headRecord;
		return demographicNo;
	}

	public String getSexDesc() {
		return sexDesc;
	}

	public void setSexDesc(String sexDesc) {
		this.sexDesc = sexDesc;
	}

	public boolean isActive() {
		return activeCount > 0;
	}

	public boolean isSubRecordEmpty() {
		return this.subRecord.isEmpty();
	}

	public boolean hasHsAlert() {
		return hsAlertCount > 0;
	}

	public int getActiveCount() {
		return activeCount;
	}

	public void setActiveCount(int activeCount) {
		this.activeCount = activeCount;
	}

	public int getHsAlertCount() {
		return hsAlertCount;
	}

	public void setHsAlertCount(int hsAlertCount) {
		this.hsAlertCount = hsAlertCount;
	}

	public void setEffDateTxt(String effDateTxt) {
		this.effDateTxt = effDateTxt;
	}

	public boolean isMerged() {
		return merged;
	}

	public void setMerged(boolean merged) {
		this.merged = merged;
	}

	public String getBenefitUnitStatus() {
		return benefitUnitStatus;
	}

	public void setBenefitUnitStatus(String benefitUnitStatus) {
		this.benefitUnitStatus = benefitUnitStatus;
	}

	public Calendar getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Calendar lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public boolean isFamilyHead() {
		return familyHead;
	}

	public void setFamilyHead(boolean familyHead) {
		this.familyHead = familyHead;
	}

}
