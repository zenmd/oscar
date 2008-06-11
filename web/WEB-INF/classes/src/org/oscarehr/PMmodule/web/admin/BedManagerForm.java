package org.oscarehr.PMmodule.web.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionForm;
import org.oscarehr.PMmodule.model.Bed;
import org.oscarehr.PMmodule.model.BedType;
import org.oscarehr.PMmodule.model.Facility;
import org.oscarehr.PMmodule.model.Room;
import org.oscarehr.PMmodule.model.RoomType;
/**
 */
public class BedManagerForm extends ActionForm {

    private Integer facilityId;
    private Facility facility;
    private Integer numRooms;
    private Integer numBeds;
    private Room[] rooms;
    private Room[] assignedBedRooms;
    private RoomType[] roomTypes;
    private Bed[] beds;
    private BedType[] bedTypes;
    private List programs;
    private Integer roomToDelete;
    private Integer bedToDelete;
    private Integer roomStatusFilter;
    private Integer bedStatusFilter;
    private Integer bedProgramFilterForRoom;
    private Integer bedRoomFilterForBed;
    private Map roomStatusNames;
    private Map bedStatusNames;
    private String existRooms;
   

    public Integer getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Integer facilityId) {
        this.facilityId = facilityId;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public Integer getNumRooms() {
        return numRooms;
    }

    public void setNumRooms(Integer numRooms) {
        this.numRooms = numRooms;
    }

    public Integer getNumBeds() {
        return numBeds;
    }

    public void setNumBeds(Integer numBeds) {
        this.numBeds = numBeds;
    }

    public Room[] getRooms() {
        return rooms;
    }

    public void setRooms(Room[] rooms) {
        this.rooms = rooms;
    }

    public RoomType[] getRoomTypes() {
        return roomTypes;
    }

    public void setRoomTypes(RoomType[] roomTypes) {
        this.roomTypes = roomTypes;
    }

    public Bed[] getBeds() {
        return beds;
    }

    public void setBeds(Bed[] beds) {
        this.beds = beds;
    }

    public BedType[] getBedTypes() {
        return bedTypes;
    }

    public void setBedTypes(BedType[] bedTypes) {
        this.bedTypes = bedTypes;
    }

    public List getPrograms() {
        return programs;
    }

    public void setPrograms(List programs) {
        this.programs = programs;
    }

	public Room[] getAssignedBedRooms() {
		return assignedBedRooms;
	}

	public void setAssignedBedRooms(Room[] assignedBedRooms) {
		this.assignedBedRooms = assignedBedRooms;
	}

	public Integer getBedToDelete() {
		return bedToDelete;
	}

	public void setBedToDelete(Integer bedToDelete) {
		this.bedToDelete = bedToDelete;
	}

	public Integer getRoomToDelete() {
		return roomToDelete;
	}

	public void setRoomToDelete(Integer roomToDelete) {
		this.roomToDelete = roomToDelete;
	}

	public Integer getBedRoomFilterForBed() {
		return bedRoomFilterForBed;
	}

	public void setBedRoomFilterForBed(Integer bedRoomFilterForBed) {
		this.bedRoomFilterForBed = bedRoomFilterForBed;
	}

	public Integer getBedProgramFilterForRoom() {
		return bedProgramFilterForRoom;
	}

	public void setBedProgramFilterForRoom(Integer bedProgramFilterForRoom) {
		this.bedProgramFilterForRoom = bedProgramFilterForRoom;
	}

	public Integer getBedStatusFilter() {
		return bedStatusFilter;
	}

	public void setBedStatusFilter(Integer bedStatusFilter) {
		this.bedStatusFilter = bedStatusFilter;
	}

	public Integer getRoomStatusFilter() {
		return roomStatusFilter;
	}

	public void setRoomStatusFilter(Integer roomStatusFilter) {
		this.roomStatusFilter = roomStatusFilter;
	}

	public Map getBedStatusNames() {
		return bedStatusNames;
	}

	public void setBedStatusNames(Map bedStatusNames) {
		this.bedStatusNames = bedStatusNames;
	}

	public Map getRoomStatusNames() {
		return roomStatusNames;
	}

	public void setRoomStatusNames(Map roomStatusNames) {
		this.roomStatusNames = roomStatusNames;
	}

	public String getExistRooms() {
		return existRooms;
	}

	public void setExistRooms(String existRooms) {
		this.existRooms = existRooms;
	}

	

}
