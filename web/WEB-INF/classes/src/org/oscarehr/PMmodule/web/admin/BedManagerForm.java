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
package org.oscarehr.PMmodule.web.admin;

import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionForm;
import org.oscarehr.PMmodule.model.Room;
import org.oscarehr.PMmodule.model.Bed;

public class BedManagerForm extends ActionForm {

    private Integer facilityId;
    
    private Room room= new Room();
    private Bed bed= new Bed();

    private List assignedBedLst;
    
    private List programs;
    private Integer roomStatusFilter;
    private Integer bedStatusFilter;
    private Integer bedProgramFilterForRoom;

    private Map roomStatusNames;
    private Map bedStatusNames;

    public Integer getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Integer facilityId) {
        this.facilityId = facilityId;
    }

    public List getPrograms() {
        return programs;
    }

    public void setPrograms(List programs) {
        this.programs = programs;
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

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public List getAssignedBedLst() {
		return assignedBedLst;
	}

	public void setAssignedBedLst(List assignedBedLst) {
		this.assignedBedLst = assignedBedLst;
	}

	public Bed getBed() {
		return bed;
	}

	public void setBed(Bed bed) {
		this.bed = bed;
	}	

}
