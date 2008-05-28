package org.oscarehr.PMmodule.model;

public class ProgramClientInfo {
	private Integer admissionId;
	private String firstName;
	private String lastName;
	private String formattedName;
	private String admissionDate;
	private String admissionNote;
	private String room;
	private String bed;
	private String isDischargeable;
	
	public String getIsDischargeable() {
		return isDischargeable;
	}
	public void setIsDischargeable(String isDischargeable) {
		this.isDischargeable = isDischargeable;
	}
	public String getAdmissionDate() {
		return admissionDate;
	}
	public void setAdmissionDate(String admissionDate) {
		this.admissionDate = admissionDate;
	}
	public String getAdmissionNote() {
		return admissionNote;
	}
	public void setAdmissionNote(String admissionNote) {
		this.admissionNote = admissionNote;
	}
	public String getBed() {
		return bed;
	}
	public void setBed(String bed) {
		this.bed = bed;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getFormattedName() {
		formattedName = lastName + ", " + firstName;
		return formattedName;
	}
	public void setFormattedName(String formattedName) {
		this.formattedName = formattedName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public Integer getAdmissionId() {
		return admissionId;
	}
	public void setAdmissionId(Integer admissionId) {
		this.admissionId = admissionId;
	}
	

}
