package org.oscarehr.PMmodule.web.formbean;

import org.apache.struts.validator.ValidatorForm;
import java.util.List;
import org.oscarehr.PMmodule.model.Bed;
import org.oscarehr.PMmodule.model.BedDemographic;
import org.oscarehr.PMmodule.model.BedDemographicStatus;
import org.oscarehr.PMmodule.model.QuatroIntake;
import org.oscarehr.PMmodule.model.Room;
import org.oscarehr.PMmodule.model.QuatroAdmission;

import java.util.Calendar;

public class QuatroClientAdmissionForm extends ValidatorForm{
//    private Integer clientId;
//    private Integer intakeId;
//    private Integer programId;

    private QuatroAdmission admission;

    private List provinceList;
    private List notSignReasonList;
    private Room[] availableRooms;
    private Bed[] availableBeds;

    //will be replaced by RoomDemographic and BedDemographic
    private Integer roomId;
    private Integer bedId;
    
/*    
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
*/

	public QuatroAdmission getAdmission() {
		return admission;
	}

	public void setAdmission(QuatroAdmission admission) {
		this.admission = admission;
	}

	public Bed[] getAvailableBeds() {
		return availableBeds;
	}

	public void setAvailableBeds(Bed[] availableBeds) {
		this.availableBeds = availableBeds;
	}

	public Room[] getAvailableRooms() {
		return availableRooms;
	}

	public void setAvailableRooms(Room[] availableRooms) {
		this.availableRooms = availableRooms;
	}

	public List getNotSignReasonList() {
		return notSignReasonList;
	}

	public void setNotSignReasonList(List notSignReasonList) {
		this.notSignReasonList = notSignReasonList;
	}

	public List getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List provinceList) {
		this.provinceList = provinceList;
	}

	public Integer getBedId() {
		return bedId;
	}

	public void setBedId(Integer bedId) {
		this.bedId = bedId;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}
	
}
