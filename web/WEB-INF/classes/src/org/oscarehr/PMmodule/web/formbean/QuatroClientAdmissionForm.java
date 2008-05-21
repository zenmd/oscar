package org.oscarehr.PMmodule.web.formbean;

import org.apache.struts.validator.ValidatorForm;
import java.util.List;
import org.oscarehr.PMmodule.model.Bed;
import org.oscarehr.PMmodule.model.BedDemographic;
import org.oscarehr.PMmodule.model.BedDemographicPK;
import org.oscarehr.PMmodule.model.QuatroIntake;
import org.oscarehr.PMmodule.model.Room;
import org.oscarehr.PMmodule.model.QuatroAdmission;
import org.oscarehr.PMmodule.model.RoomDemographic;
import org.oscarehr.PMmodule.model.RoomDemographicPK;


public class QuatroClientAdmissionForm extends ValidatorForm{

    private QuatroAdmission admission = new QuatroAdmission();

    private List providerList;
    private List provinceList;
    private List notSignReasonList;
    private Room[] availableRooms;
    private Bed[] availableBeds;

    private RoomDemographic roomDemographic = new RoomDemographic(new RoomDemographicPK());
    private BedDemographic bedDemographic = new BedDemographic(new BedDemographicPK());
    
    
	public QuatroAdmission getAdmission() {
		return admission;
	}

	public void setAdmission(QuatroAdmission admission) {
		this.admission = admission;
	}

	public Bed[] getAvailableBeds() {
		if(availableBeds==null) return new Bed[0];
		return availableBeds;
	}

	public void setAvailableBeds(Bed[] availableBeds) {
		this.availableBeds = availableBeds;
	}

	public Room[] getAvailableRooms() {
		if(availableRooms==null) return new Room[0];
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

	public List getProviderList() {
		return providerList;
	}

	public void setProviderList(List providerList) {
		this.providerList = providerList;
	}

	public BedDemographic getBedDemographic() {
		return bedDemographic;
	}

	public void setBedDemographic(BedDemographic bedDemographic) {
		this.bedDemographic = bedDemographic;
	}

	public RoomDemographic getRoomDemographic() {
		return roomDemographic;
	}

	public void setRoomDemographic(RoomDemographic roomDemographic) {
		this.roomDemographic = roomDemographic;
	}
	
}
