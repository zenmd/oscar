package org.oscarehr.PMmodule.web.formbean;

import org.apache.struts.validator.ValidatorForm;

public class ClientForm extends ValidatorForm{
	private String dischargeReason;
	private String communityProgramCode;
	
	private String firstName;
	private String lastName;
	private String room;
	private String bed;
	private String clientId;

	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getCommunityProgramCode() {
		return communityProgramCode;
	}
	public void setCommunityProgramCode(String communityProgramCode) {
		this.communityProgramCode = communityProgramCode;
	}
	public String getDischargeReason() {
		return dischargeReason;
	}
	public void setDischargeReason(String dischargeReason) {
		this.dischargeReason = dischargeReason;
	}
	public String getBed() {
		return bed;
	}
	public void setBed(String bed) {
		this.bed = bed;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	
	
}
