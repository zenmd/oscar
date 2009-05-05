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
package org.oscarehr.PMmodule.web.formbean;

import org.apache.struts.validator.ValidatorForm;
import java.util.List;
import org.oscarehr.PMmodule.model.Bed;
import org.oscarehr.PMmodule.model.QuatroIntake;
import org.oscarehr.PMmodule.model.Room;
import org.oscarehr.PMmodule.model.Admission;
import org.oscarehr.PMmodule.model.RoomDemographic;
import org.oscarehr.PMmodule.model.RoomDemographicPK;


public class QuatroClientAdmissionForm extends ValidatorForm{

    private Admission admission = new Admission();
    private String familyIntakeType;  //Y: family Intake/Admission
    private Integer intakeClientNum;

    private List providerList;
    private List provinceList;
    private List notSignReasonList;
    private Room[] availableRooms;
    private Bed[] availableBeds;

    private RoomDemographic roomDemographic = new RoomDemographic(new RoomDemographicPK());
    private Integer bedId;
    
    private Integer curDB_RoomId;
    private Integer curDB_RoomCapacity;
    private Integer curDB_BedId;
    
	public Admission getAdmission() {
		return admission;
	}

	public void setAdmission(Admission admission) {
		this.admission = admission;
	}

	public Bed[] getAvailableBeds() {
//		if(availableBeds==null) return new Bed[0];
		return availableBeds;
	}

	public void setAvailableBeds(Bed[] availableBeds) {
		this.availableBeds = availableBeds;
	}

	public Room[] getAvailableRooms() {
//		if(availableRooms==null) return new Room[0];
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

	public Integer getBedId() {
		return bedId;
	}

	public void setBedId(Integer bedId) {
		this.bedId = bedId;
	}

	public RoomDemographic getRoomDemographic() {
		return roomDemographic;
	}

	public void setRoomDemographic(RoomDemographic roomDemographic) {
		this.roomDemographic = roomDemographic;
	}

	public String getFamilyIntakeType() {
		return familyIntakeType;
	}

	public void setFamilyIntakeType(String familyIntakeType) {
		this.familyIntakeType = familyIntakeType;
	}

	public Integer getCurDB_BedId() {
		return curDB_BedId;
	}

	public void setCurDB_BedId(Integer curDB_BedId) {
		this.curDB_BedId = curDB_BedId;
	}

	public Integer getCurDB_RoomId() {
		return curDB_RoomId;
	}

	public void setCurDB_RoomId(Integer curDB_RoomId) {
		this.curDB_RoomId = curDB_RoomId;
	}

	public Integer getIntakeClientNum() {
		return intakeClientNum;
	}

	public void setIntakeClientNum(Integer intakeClientNum) {
		this.intakeClientNum = intakeClientNum;
	}

	public Integer getCurDB_RoomCapacity() {
		return curDB_RoomCapacity;
	}

	public void setCurDB_RoomCapacity(Integer curDB_RoomCapacity) {
		this.curDB_RoomCapacity = curDB_RoomCapacity;
	}
}
