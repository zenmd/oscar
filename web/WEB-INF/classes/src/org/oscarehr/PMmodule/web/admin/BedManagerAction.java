package org.oscarehr.PMmodule.web.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.oscarehr.PMmodule.exception.BedReservedException;
import org.oscarehr.PMmodule.exception.DuplicateBedNameException;
import org.oscarehr.PMmodule.exception.DuplicateRoomNameException;
import org.oscarehr.PMmodule.exception.RoomHasActiveBedsException;
import org.oscarehr.PMmodule.model.Bed;
import org.oscarehr.PMmodule.model.BedDemographic;
import org.oscarehr.PMmodule.model.Facility;
import org.oscarehr.PMmodule.model.Room;
import org.oscarehr.PMmodule.service.BedDemographicManager;
import org.oscarehr.PMmodule.service.BedManager;
import org.oscarehr.PMmodule.service.FacilityManager;
import org.oscarehr.PMmodule.service.ProgramManager;
import org.oscarehr.PMmodule.service.RoomDemographicManager;
import org.oscarehr.PMmodule.service.RoomManager;
import org.oscarehr.PMmodule.web.BaseFacilityAction;

import com.quatro.common.KeyConstants;

/**
 * Responsible for managing beds
 */
public class BedManagerAction extends BaseFacilityAction {

    private static final String FORWARD_MANAGE = "manage";

    private FacilityManager facilityManager;
    private BedManager bedManager;
    private ProgramManager programManager;
    private RoomManager roomManager;
    private RoomDemographicManager roomDemographicManager;
    private BedDemographicManager bedDemographicManager;

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        // dispatch to correct method based on which button was selected
        // Please don't make changes that causes addRoom and addBed button not working any more!
        if ("".equals(request.getParameter("submit.saveRoom")) == false) return saveRooms(mapping, form, request, response);
        else if (request.getParameter("submit.deleteRoom") != null) return deleteRoom(mapping, form, request, response);
        else if ("".equals(request.getParameter("submit.addRoom")) == false) return addRooms(mapping, form, request, response);
        else if ("".equals(request.getParameter("submit.saveBed")) == false) return saveBeds(mapping, form, request, response);
        else if (request.getParameter("submit.deleteBed") != null) return deleteBed(mapping, form, request, response);
        else if ("".equals(request.getParameter("submit.addBed")) == false) return addBeds(mapping, form, request, response);
        else return manage(mapping, form, request, response);
    }

    public ActionForward manage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	
    	prepareLeftNav(request);
        
    	BedManagerForm bForm = (BedManagerForm) form;

        Integer facilityId = Integer.valueOf(request.getParameter("facilityId"));
        Facility facility = facilityManager.getFacility(facilityId);

        bForm.setFacilityId(facilityId);
        bForm.setRooms(roomManager.getRooms(facilityId));
        bForm.setAssignedBedRooms(roomManager.getAssignedBedRooms(facilityId));
        bForm.setRoomTypes(roomManager.getRoomTypes());
        bForm.setNumRooms(new Integer(1));

        if (bForm.getBedRoomFilterForBed() == null) {
            Room[] room = bForm.getRooms();
            if (room != null && room.length > 0) {
                bForm.setBedRoomFilterForBed(room[0].getId());
            }
        }
        // bForm.setBeds(bedManager.getBedsByFacility(facilityId, null, false));
        List lst = bedManager.getBedsByFilter(facilityId, bForm.getBedRoomFilterForBed(), null, false);
        Bed[] bedsTemp= new Bed[lst.size()];
        for(int i=0;i<lst.size();i++){
        	bedsTemp[i]= (Bed)lst.get(i);
        }
        bForm.setBeds(bedsTemp);
//        bForm.setBeds(lst.toArray(new Bed[lst.size()]));

        bForm.setBedTypes(bedManager.getBedTypes());
        bForm.setNumBeds(new Integer(1));
        bForm.setPrograms(programManager.getBedPrograms(facilityId));
        bForm.setFacility(facility);
        Map statusNames = new HashMap();
        statusNames.put("1", "Active");
        statusNames.put("0", "Inactive");
        statusNames.put("2", "Any");
        bForm.setRoomStatusNames(statusNames);
        bForm.setBedStatusNames(statusNames);

        return mapping.findForward(FORWARD_MANAGE);
    }

    public ActionForward manageFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	prepareLeftNav(request);
    	
        BedManagerForm bForm = (BedManagerForm) form;

        Integer facilityId = Integer.valueOf(request.getParameter("facilityId"));
        Facility facility = facilityManager.getFacility(facilityId);

        bForm.setFacilityId(facilityId);
        bForm.setRooms(bForm.getRooms());
        bForm.setAssignedBedRooms(roomManager.getAssignedBedRooms(facilityId));
        bForm.setRoomTypes(roomManager.getRoomTypes());
        bForm.setNumRooms(new Integer(1));
        bForm.setBeds(bForm.getBeds());
        bForm.setBedTypes(bedManager.getBedTypes());
        bForm.setNumBeds(new Integer(1));
        bForm.setPrograms(programManager.getBedPrograms(facilityId));
        bForm.setFacility(facility);
        Map statusNames = new HashMap();
        statusNames.put("1", "Active");
        statusNames.put("0", "Inactive");
        statusNames.put("2", "Any");
        bForm.setRoomStatusNames(statusNames);
        bForm.setBedStatusNames(statusNames);

        return mapping.findForward(FORWARD_MANAGE);
    }

    public ActionForward saveRooms(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	prepareLeftNav(request);
    	
    	BedManagerForm bForm = (BedManagerForm) form;

        Room[] rooms = bForm.getRooms();

        // detect check box false
        for (int i = 0; i < rooms.length; i++) {
            if (request.getParameter("rooms[" + i + "].active") == null) {
                rooms[i].setActive(false);
            }
        }
        try {
            roomManager.saveRooms(rooms);
            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.save.success", request.getContextPath()));
            saveMessages(request, messages);
        }
        catch (RoomHasActiveBedsException e) {
            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("room.active.beds.error", e.getMessage()));
            saveMessages(request, messages);
        }
        catch (DuplicateRoomNameException e) {
            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("room.duplicate.name.error", e.getMessage()));
            saveMessages(request, messages);
        }

        return manage(mapping, form, request, response);
    }

    public ActionForward deleteRoom(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	prepareLeftNav(request);
    	
        ActionMessages messages = new ActionMessages();
        BedManagerForm bForm = (BedManagerForm) form;

        Integer roomId = bForm.getRoomToDelete();

        // (1)Check whether any client is assigned to this room ('room_demographic' table)->
        // if yes, disallow room delete and display message.
        // (2)if no client assigned, check whether any beds assigned ('bed' table) ->
        // if some bed assigned, retrieve all beds assigned to this room -> delete them all <-- ???
        // (3)then delete this room ('room' table)
        try {
            List roomDemographicList = roomDemographicManager.getRoomDemographicByRoom(roomId);

            if (roomDemographicList != null && !roomDemographicList.isEmpty()) {
                throw new RoomHasActiveBedsException("The room has client(s) assigned to it and cannot be removed.");
            }

            Bed[] beds = bedManager.getBedsForDeleteByRoom(roomId);

            if (beds != null && beds.length > 0) {

                for (int i = 0; i < beds.length; i++) {
                    bedManager.deleteBed(beds[i]);
                }
            }

            Room room = roomManager.getRoom(roomId);
            roomManager.deleteRoom(room);
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.remove.success", request.getContextPath()));
            saveMessages(request, messages);

        }
        catch (RoomHasActiveBedsException e) {
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("room.active.beds.error", e.getMessage()));
            saveMessages(request, messages);
        }

        return manage(mapping, form, request, response);
    }

    public ActionForward saveBeds(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	prepareLeftNav(request);
    	
    	BedManagerForm bForm = (BedManagerForm) form;

        Bed[] beds = bForm.getBeds();

        for (int i = 0; i < beds.length; i++) {
            if (request.getParameter("beds[" + i + "].active") == null) {
                beds[i].setActive(false);
            }
        }
        Room[] rooms = roomManager.getUnfilledRoomIds(beds);
        if (rooms == null) {
            rooms = bForm.getRooms();
            if (rooms == null) {
                log.error("saveBeds(): No beds are assigned to rooms.");
            }
        }
        try {
            beds = bedManager.getBedsForUnfilledRooms(rooms, beds);
            bedManager.saveBeds(beds);
            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.save.success", request.getContextPath()));
            saveMessages(request, messages);

        }
        catch (BedReservedException e) {
            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("bed.reserved.error", e.getMessage()));
            saveMessages(request, messages);
        }
        catch (DuplicateBedNameException e) {
            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("bed.duplicate.name.error", e.getMessage()));
            saveMessages(request, messages);
        }

        return manage(mapping, form, request, response);
    }

    public ActionForward deleteBed(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	prepareLeftNav(request);
    	
    	BedManagerForm bForm = (BedManagerForm) form;

        Integer bedId = bForm.getBedToDelete();
        // (1)Check whether any client is assigned to this bed ('bed_demographic' table)->
        // if yes, disallow bed delete and display message.
        // (2)if no client assigned, delete this bed ('bed' table)
        ActionMessages messages = new ActionMessages();
        try {

            BedDemographic bedDemographic = bedDemographicManager.getBedDemographicByBed(bedId);

            if (bedDemographic != null) {
                throw new BedReservedException("The bed has client assigned to it and cannot be removed.");
            }

            Bed bed = bedManager.getBedForDelete(bedId);
            bedManager.deleteBed(bed);
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.remove.success", request.getContextPath()));
            saveMessages(request, messages);

        }
        catch (BedReservedException e) {
           
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("bed.reserved.error", e.getMessage()));
            saveMessages(request, messages);
        }

        return manage(mapping, form, request, response);
    }

    public ActionForward addRooms(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	prepareLeftNav(request);
    	
    	BedManagerForm bForm = (BedManagerForm) form;
        Integer numRooms = bForm.getNumRooms();

        Integer roomslines = new Integer(0);
        if ("".equals(request.getParameter("roomslines")) == false) {
            roomslines = Integer.valueOf(request.getParameter("roomslines"));
        }

        if (numRooms != null) {
            if (numRooms.intValue() <= 0) {
                numRooms = new Integer(0);
            }
            else if (numRooms.intValue() + roomslines.intValue() > 10) {
                numRooms = new Integer(10 - roomslines.intValue());
            }
        }

        if (numRooms != null && numRooms.intValue() > 0) {
            try {
                roomManager.addRooms(bForm.getFacilityId(), numRooms.intValue());
            }
            catch (RoomHasActiveBedsException e) {
                ActionMessages messages = new ActionMessages();
                messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("room.active.beds.error", e.getMessage()));
                saveMessages(request, messages);
            }
        }

        return manage(mapping, form, request, response);
    }

    public ActionForward addBeds(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	prepareLeftNav(request);
    	
    	Integer facilityId = Integer.valueOf(request.getParameter("facilityId"));
        BedManagerForm bForm = (BedManagerForm) form;
        Integer numBeds = bForm.getNumBeds();
        Integer roomId = bForm.getBedRoomFilterForBed();

        Integer bedslines = new Integer(0);
        if ("".equals(request.getParameter("bedslines")) == false) {
            bedslines = Integer.valueOf(request.getParameter("bedslines"));
        }

        if (numBeds != null) {
            if (numBeds.intValue() <= 0) {
                numBeds = new Integer(0);
            }
            else if (numBeds.intValue() + bedslines.intValue() > 10) {
                numBeds = new Integer(10 - bedslines.intValue());
            }
        }

        if (numBeds != null && numBeds.intValue() > 0) {
            try {
                bedManager.addBeds(facilityId, roomId, numBeds.intValue());
            }
            catch (BedReservedException e) {
                ActionMessages messages = new ActionMessages();
                messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("bed.reserved.error", e.getMessage()));
                saveMessages(request, messages);
            }
        }

        return manage(mapping, form, request, response);
    }

    public ActionForward doRoomFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	prepareLeftNav(request);
    	
    	Integer facilityId = Integer.valueOf(request.getParameter("facilityId"));
        BedManagerForm bForm = (BedManagerForm) form;
        Integer roomStatus = bForm.getRoomStatusFilter();
        Integer roomFilteredProgram = bForm.getBedProgramFilterForRoom();
        Boolean roomStatusBoolean = new Boolean(false);

        if (roomStatus.intValue() == 1) {
            roomStatusBoolean = new Boolean(true);
        }
        else if (roomStatus.intValue() == 0) {
            roomStatusBoolean = new Boolean(false);
        }
        else {
            roomStatusBoolean = null;
        }

        if (roomFilteredProgram.intValue() == 0) {
            roomFilteredProgram = null;
        }
        Room[] filteredRooms = roomManager.getRooms(facilityId, roomFilteredProgram, roomStatusBoolean);

        bForm.setRooms(filteredRooms);
        form = bForm;

        return manageFilter(mapping, form, request, response);
    }

    public ActionForward doBedFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	prepareLeftNav(request);
    	
    	Integer facilityId = Integer.valueOf(request.getParameter("facilityId"));
        BedManagerForm bForm = (BedManagerForm) form;
        Integer bedStatus = bForm.getBedStatusFilter();
        Integer bedFilteredProgram = bForm.getBedRoomFilterForBed();
        Boolean bedStatusBoolean = new Boolean(false);

        List filteredBeds = new ArrayList();

        if (bedStatus.intValue() == 1) {
            bedStatusBoolean = new Boolean(true);
        }
        else if (bedStatus.intValue() == 0) {
            bedStatusBoolean = new Boolean(false);
        }
        else {
            bedStatusBoolean = null;
        }

        if (bedFilteredProgram.intValue() == 0) {
            bedFilteredProgram = null;
        }
        /*
         * List<Bed> filteredBedsList = null; Room[] filteredRooms = roomManager.getAssignedBedRooms(facilityId, bedFilteredProgram, bedStatusBoolean); for(int i=0; filteredRooms != null && i < filteredRooms.length; i++){
         * 
         * if(filteredRooms[i] != null){ filteredBedsList = bedManager.getBedsByFilter(facilityId, filteredRooms[i].getId(), bedStatusBoolean, false); filteredBeds.addAll(filteredBedsList); } }
         */

        filteredBeds = bedManager.getBedsByFilter(facilityId, bForm.getBedRoomFilterForBed(), bedStatusBoolean, false);

        Bed[] filteredBedsTemp = new Bed[filteredBeds.size()];
        for(int i=0;i<filteredBeds.size();i++){
        	filteredBedsTemp[i]= (Bed)filteredBeds.get(i);
        }
        bForm.setBeds(filteredBedsTemp);
//        bForm.setBeds(filteredBeds.toArray(new Bed[filteredBeds.size()]));
        form = bForm;

        return manageFilter(mapping, form, request, response);
    }

    public FacilityManager getFacilityManager() {
        return facilityManager;
    }

    //@Required
    public void setFacilityManager(FacilityManager facilityManager) {
        this.facilityManager = facilityManager;
    }

    public void setBedManager(BedManager bedManager) {
        this.bedManager = bedManager;
    }

    public void setProgramManager(ProgramManager mgr) {
        this.programManager = mgr;
    }

    public void setRoomManager(RoomManager roomManager) {
        this.roomManager = roomManager;
    }

	public void setRoomDemographicManager(
			RoomDemographicManager roomDemographicManager) {
		this.roomDemographicManager = roomDemographicManager;
	}

	public void setBedDemographicManager(BedDemographicManager bedDemographicManager) {
		this.bedDemographicManager = bedDemographicManager;
	}
	
	private void prepareLeftNav(HttpServletRequest request){
		super.setScreenMode(request, KeyConstants.TAB_FACILITY_BED);
		
		String facilityId = request.getParameter("facilityId");
				
		HashMap actionParam = (HashMap) request.getAttribute("actionParam");
		if(actionParam==null){
	 	  actionParam = new HashMap();
	       actionParam.put("facilityId", facilityId); 
	       
	    }
	    request.setAttribute("actionParam", actionParam);
	    
	    request.setAttribute("facility", facilityManager.getFacility(Integer.valueOf(facilityId)));
	}
}
