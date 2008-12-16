package org.oscarehr.PMmodule.model;

import java.io.Serializable;
import java.util.Calendar;

public class SdmtIn implements Serializable {
	 private static final long serialVersionUID = 1L;
	 private Integer recordId;
	 private Integer batchNumber;
	 private Calendar batchDate;
	 private String firstName;
	 private String lastName;
	 private Calendar dob;
	 private String benefitUnitStatus;
	 private String program;
	 private String office;
	 private Calendar terminationDate;
	 private String lastBenMonth;
	 private Double totalPayment;
	 private Double basicAmount;
	 private Double housingAmount;
	 private Calendar paidDate;
	 private String address;
	 private String status;
	 private String role;
	 private Integer sdmtBenUnitId;
	 private Integer memberId;
	 private Integer clientId;
	 private String lastUpdateUser;
	 private Calendar lastUpdateDate;
	 public Integer getRecordId()
	 {
		 return recordId;
	 }
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Double getBasicAmount() {
		return basicAmount;
	}
	public void setBasicAmount(Double basicAmount) {
		this.basicAmount = basicAmount;
	}
	public Calendar getBatchDate() {
		return batchDate;
	}
	public void setBatchDate(Calendar batchDate) {
		this.batchDate = batchDate;
	}
	public Integer getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(Integer batchNumber) {
		this.batchNumber = batchNumber;
	}
	public String getBenefitUnitStatus() {
		return benefitUnitStatus;
	}
	public void setBenefitUnitStatus(String benefitUnitStatus) {
		this.benefitUnitStatus = benefitUnitStatus;
	}
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public Calendar getDob() {
		return dob;
	}
	public void setDob(Calendar dob) {
		this.dob = dob;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public Double getHousingAmount() {
		return housingAmount;
	}
	public void setHousingAmount(Double housingAmount) {
		this.housingAmount = housingAmount;
	}
	public String getLastBenMonth() {
		return lastBenMonth;
	}
	public void setLastBenMonth(String lastBenMonth) {
		this.lastBenMonth = lastBenMonth;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getOffice() {
		return office;
	}
	public void setOffice(String office) {
		this.office = office;
	}
	public Calendar getPaidDate() {
		return paidDate;
	}
	public void setPaidDate(Calendar paidDate) {
		this.paidDate = paidDate;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId)
	{
		this.memberId = memberId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Integer getSdmtBenUnitId() {
		return sdmtBenUnitId;
	}
	public void setSdmtBenUnitId(Integer sdmtBenUnitId) {
		this.sdmtBenUnitId = sdmtBenUnitId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Calendar getTerminationDate() {
		return terminationDate;
	}
	public void setTerminationDate(Calendar terminationDate) {
		this.terminationDate = terminationDate;
	}
	public Double getTotalPayment() {
		return totalPayment;
	}
	public void setTotalPayment(Double totalPayment) {
		this.totalPayment = totalPayment;
	}	 
}
