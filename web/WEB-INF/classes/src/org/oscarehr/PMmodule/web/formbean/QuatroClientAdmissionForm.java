package org.oscarehr.PMmodule.web.formbean;

import org.apache.struts.validator.ValidatorForm;
import java.util.List;
import org.oscarehr.PMmodule.model.Bed;
import org.oscarehr.PMmodule.model.BedDemographic;
import org.oscarehr.PMmodule.model.BedDemographicStatus;
import org.oscarehr.PMmodule.model.Room;

import java.util.Calendar;

public class QuatroClientAdmissionForm extends ValidatorForm{
    private Integer clientId;
    private Integer intakeId;
    private Integer programId;
    private Integer admissionId;
    private Calendar admissionDate;
    
    private Integer roomId;
    private Integer bedId;

    private Room[] availableRooms;
    private Bed[] availableBeds;

    private String residentStatus;
    private String primaryWorker;
    private String lockerNo;
    private String noOfBags;
    private String nextKinName;
    private String nextKinRelationship;
    private String nextKinTelephone;
    private String nextKinNumber;
    private String nextKinStreet;
    private String nextKinCity;
    private String nextKinProvince;
    private List provinceList;
    private String nextKinPostal;
    private String ovPassStartDate;
    private String ovPassEndDate;
    private String issuedBy;
    private String notSignReason;
    private List notSignReasonList;
    
/*
    public BedDemographicStatus[] getBedDemographicStatuses() {
		return bedDemographicStatuses;
	}
	public void setBedDemographicStatuses(
			BedDemographicStatus[] bedDemographicStatuses) {
		this.bedDemographicStatuses = bedDemographicStatuses;
	}
*/	
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public Integer getIntakeId() {
		return intakeId;
	}
	public void setIntakeId(Integer intakeId) {
		this.intakeId = intakeId;
	}
	public String getIssuedBy() {
		return issuedBy;
	}
	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}
	public String getLockerNo() {
		return lockerNo;
	}
	public void setLockerNo(String lockerNo) {
		this.lockerNo = lockerNo;
	}
	public String getNextKinCity() {
		return nextKinCity;
	}
	public void setNextKinCity(String nextKinCity) {
		this.nextKinCity = nextKinCity;
	}
	public String getNextKinName() {
		return nextKinName;
	}
	public void setNextKinName(String nextKinName) {
		this.nextKinName = nextKinName;
	}
	public String getNextKinNumber() {
		return nextKinNumber;
	}
	public void setNextKinNumber(String nextKinNumber) {
		this.nextKinNumber = nextKinNumber;
	}
	public String getNextKinPostal() {
		return nextKinPostal;
	}
	public void setNextKinPostal(String nextKinPostal) {
		this.nextKinPostal = nextKinPostal;
	}
	public String getNextKinProvince() {
		return nextKinProvince;
	}
	public void setNextKinProvince(String nextKinProvince) {
		this.nextKinProvince = nextKinProvince;
	}
	public String getNextKinRelationship() {
		return nextKinRelationship;
	}
	public void setNextKinRelationship(String nextKinRelationship) {
		this.nextKinRelationship = nextKinRelationship;
	}
	public String getNextKinStreet() {
		return nextKinStreet;
	}
	public void setNextKinStreet(String nextKinStreet) {
		this.nextKinStreet = nextKinStreet;
	}
	public String getNextKinTelephone() {
		return nextKinTelephone;
	}
	public void setNextKinTelephone(String nextKinTelephone) {
		this.nextKinTelephone = nextKinTelephone;
	}
	public String getNoOfBags() {
		return noOfBags;
	}
	public void setNoOfBags(String noOfBags) {
		this.noOfBags = noOfBags;
	}
	public String getNotSignReason() {
		return notSignReason;
	}
	public void setNotSignReason(String notSignReason) {
		this.notSignReason = notSignReason;
	}
	public List getNotSignReasonList() {
		return notSignReasonList;
	}
	public void setNotSignReasonList(List notSignReasonList) {
		this.notSignReasonList = notSignReasonList;
	}
	public String getOvPassEndDate() {
		return ovPassEndDate;
	}
	public void setOvPassEndDate(String ovPassEndDate) {
		this.ovPassEndDate = ovPassEndDate;
	}
	public String getOvPassStartDate() {
		return ovPassStartDate;
	}
	public void setOvPassStartDate(String ovPassStartDate) {
		this.ovPassStartDate = ovPassStartDate;
	}
	public String getPrimaryWorker() {
		return primaryWorker;
	}
	public void setPrimaryWorker(String primaryWorker) {
		this.primaryWorker = primaryWorker;
	}
	public List getProvinceList() {
		return provinceList;
	}
	public void setProvinceList(List provinceList) {
		this.provinceList = provinceList;
	}
	public String getResidentStatus() {
		return residentStatus;
	}
	public void setResidentStatus(String residentStatus) {
		this.residentStatus = residentStatus;
	}
/*	
	public Bed[] getUnreservedBeds() {
		return unreservedBeds;
	}
	public void setUnreservedBeds(Bed[] unreservedBeds) {
		this.unreservedBeds = unreservedBeds;
	}
*/	
	public Integer getProgramId() {
		return programId;
	}
	public void setProgramId(Integer programId) {
		this.programId = programId;
	}
	public Calendar getAdmissionDate() {
		return admissionDate;
	}
	public void setAdmissionDate(Calendar admissionDate) {
		this.admissionDate = admissionDate;
	}
/*	
	public BedDemographic getBedDemographic() {
		if(bedDemographic==null) return new BedDemographic(); 
		return bedDemographic;
	}
	public void setBedDemographic(BedDemographic bedDemographic) {
		this.bedDemographic = bedDemographic;
	}
*/
	public Integer getRoomId() {
		return roomId;
	}
	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}
	public Integer getBedId() {
		return bedId;
	}
	public void setBedId(Integer bedId) {
		this.bedId = bedId;
	}
	public Room[] getAvailableRooms() {
		return availableRooms;
	}
	public void setAvailableRooms(Room[] availableRooms) {
		this.availableRooms = availableRooms;
	}
	public Bed[] getAvailableBeds() {
		return availableBeds;
	}
	public void setAvailableBeds(Bed[] availableBeds) {
		this.availableBeds = availableBeds;
	}
	public Integer getAdmissionId() {
		return admissionId;
	}
	public void setAdmissionId(Integer admissionId) {
		this.admissionId = admissionId;
	}
}
