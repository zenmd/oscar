package org.oscarehr.PMmodule.model;

import java.io.Serializable;
import java.util.Calendar;

import com.quatro.util.Utility;

public class SdmtOut implements Serializable {

	private static final long serialVersionUID = 1L;
	 private Integer batchNumber;
	 private Calendar batchDateStr;
	 private String firstName;
	 private String lastName;
	 private Calendar dobStr;
	 private String sin;
	 private String healthCardNo;	 
	 private Integer recordId;
	 private Integer sdmtId;
	 private Integer sdmtBenUnitId;
	 private Integer clientId;
	 private Integer maxBatchNo;
	 private boolean sendOut;
	
	public boolean isSendOut() {
		return sendOut;
	}
	public void setSendOut(boolean sendOut) {
		this.sendOut = sendOut;
	}
	public Integer getMaxBatchNo() {
		return maxBatchNo;
	}
	public void setMaxBatchNo(Integer maxBatchNo) {
		this.maxBatchNo = maxBatchNo;
	}
	public String getBatchDate() {
		String retVal="";
		retVal=Utility.FormatIntNoWithZero(batchDateStr.get(Calendar.YEAR),4)+Utility.FormatIntNoWithZero(batchDateStr.get(Calendar.MONTH)+1,2)+
		Utility.FormatIntNoWithZero(batchDateStr.get(Calendar.DATE),2)+Utility.FormatIntNoWithZero(batchDateStr.get(Calendar.HOUR),2)+
		Utility.FormatIntNoWithZero(batchDateStr.get(Calendar.MINUTE),2);
		return retVal;
	}
	
	
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public String getDob() {
		//mmddyyyy
		String retVal="";
		retVal=Utility.FormatIntNoWithZero(dobStr.get(Calendar.MONTH)+1,2)+
			Utility.FormatIntNoWithZero(dobStr.get(Calendar.DATE),2)+Utility.FormatIntNoWithZero(dobStr.get(Calendar.YEAR),4);
		return retVal;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getHealthCardNo() {
		return healthCardNo;
	}
	public void setHealthCardNo(String healthCardNo) {
		this.healthCardNo = healthCardNo;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Integer getRecordId() {
		return recordId;
	}
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	public Integer getSdmtBenUnitId() {
		return sdmtBenUnitId;
	}
	public void setSdmtBenUnitId(Integer sdmtBenUnitId) {
		this.sdmtBenUnitId = sdmtBenUnitId;
	}
	public Integer getSdmtId() {
		return sdmtId;
	}
	public void setSdmtId(Integer sdmtId) {
		this.sdmtId = sdmtId;
	}
	public String getSin() {
		return sin;
	}
	public void setSin(String sin) {
		this.sin = sin;
	}
	public Integer getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(Integer batchNumber) {
		this.batchNumber = batchNumber;
	}
	public Calendar getBatchDateStr() {
		return batchDateStr;
	}
	public void setBatchDateStr(Calendar batchDateStr) {
		this.batchDateStr = batchDateStr;
	}
	public Calendar getDobStr() {
		return dobStr;
	}
	public void setDobStr(Calendar dobStr) {
		this.dobStr = dobStr;
	}
	
}
