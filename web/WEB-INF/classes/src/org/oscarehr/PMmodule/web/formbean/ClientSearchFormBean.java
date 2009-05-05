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

package org.oscarehr.PMmodule.web.formbean;

import java.util.List;

public class ClientSearchFormBean {
	
	private String demographicNo;
	private String firstName;
	private String lastName;
	private String dob;
	private String healthCardNumber;
	private String healthCardVersion;
	private String gender;
	private String active;
	private boolean searchOutsideDomain;
	private boolean searchUsingSoundex;
	private String bedProgramId; 
	private String assignedToProviderNo; 
	private String dateFrom;
	private String dateTo;
	private List genders;
	
	public ClientSearchFormBean() {
		//setSearchOutsideDomain(true);
		//setSearchUsingSoundex(true);
	}
	
	/**
	 * @return Returns the dob.
	 */
	public String getDob() {
		return dob;
	}
	/**
	 * @param dob The dob to set.
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}
	/**
	 * @return Returns the firstName.
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName The firstName to set.
	 */
	public void setFirstName(String firstName) {
		//this.firstName = firstName.replace('\'',' ');
		this.firstName = firstName;
	}
	/**
	 * @return Returns the healthCardNumber.
	 */
	public String getHealthCardNumber() {
		return healthCardNumber;
	}
	/**
	 * @param healthCardNumber The healthCardNumber to set.
	 */
	public void setHealthCardNumber(String healthCardNumber) {
		this.healthCardNumber = healthCardNumber;
	}
	/**
	 * @return Returns the healthCardVersion.
	 */
	public String getHealthCardVersion() {
		return healthCardVersion;
	}
	/**
	 * @param healthCardVersion The healthCardVersion to set.
	 */
	public void setHealthCardVersion(String healthCardVersion) {
		this.healthCardVersion = healthCardVersion;
	}
	/**
	 * @return Returns the lastName.
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName The lastName to set.
	 */
	public void setLastName(String lastName) {
		//this.lastName = lastName.replace('\'',' ');
		this.lastName = lastName;
	}
	/**
	 * @return Returns the searchOutsideDomain.
	 */
	public boolean isSearchOutsideDomain() {
		return searchOutsideDomain;
	}
	/**
	 * @param searchOutsideDomain The searchOutsideDomain to set.
	 */
	public void setSearchOutsideDomain(boolean searchOutsideDomain) {
		this.searchOutsideDomain = searchOutsideDomain;
	}
	
	/**
	 * @return Returns the searchUsingSoundex.
	 */
	public boolean isSearchUsingSoundex() {
		return searchUsingSoundex;
	}
	/**
	 * @param searchUsingSondex The searchOutsideDomain to set.
	 */
	public void setSearchUsingSoundex(boolean searchUsingSoundex) {
		this.searchUsingSoundex = searchUsingSoundex;
	}
	
	/**
	 * @return Returns the bedProgramId.
	 */
	public String getBedProgramId() {
		return bedProgramId;
	}

	/**
	 * @param bedProgramId The bedProgramId to set.
	 */
	public void setBedProgramId(String bedProgramId) {
		this.bedProgramId = bedProgramId;
	}
	/**
	 * @return Returns the dateFrom.
	 */
	public String getDateFrom() {
		return dateFrom;
	}
	/**
	 * @param dateFrom The dateFrom to set.
	 */
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	/**
	 * @return Returns the dateTo.
	 */
	public String getDateTo() {
		return dateTo;
	}
	/**
	 * @param dateTo The dateTo to set.
	 */
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public String getDemographicNo() {
		return demographicNo;
	}

	public void setDemographicNo(String demographicNo) {
		this.demographicNo = demographicNo;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public List getGenders() {
		return genders;
	}

	public void setGenders(List genders) {
		this.genders = genders;
	}

	public String getAssignedToProviderNo() {
		return assignedToProviderNo;
	}

	public void setAssignedToProviderNo(String assignedToProviderNo) {
		this.assignedToProviderNo = assignedToProviderNo;
	}
	
}
