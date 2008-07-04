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
import org.apache.struts.util.LabelValueBean;
import org.oscarehr.PMmodule.exception.RoomHasActiveBedsException;
import org.oscarehr.PMmodule.model.Bed;
import org.oscarehr.PMmodule.model.Facility;
import org.oscarehr.PMmodule.model.Program;
import org.oscarehr.PMmodule.model.Room;
import org.oscarehr.PMmodule.service.BedDemographicManager;
import org.oscarehr.PMmodule.service.BedManager;
import org.oscarehr.PMmodule.service.FacilityManager;
import org.oscarehr.PMmodule.service.ProgramManager;
import org.oscarehr.PMmodule.service.RoomDemographicManager;
import org.oscarehr.PMmodule.service.RoomManager;
import org.oscarehr.PMmodule.web.BaseFacilityAction;

import org.oscarehr.PMmodule.exception.DuplicateBedNameException;
import com.quatro.common.KeyConstants;

public class BedManagerAction extends BaseFacilityAction {

    private FacilityManager facilityManager;
    private BedManager bedManager;
    private ProgramManager programManager;
    private RoomManager roomManager;
    private RoomDemographicManager roomDemographicManager;
    private BedDemographicManager bedDemographicManager;

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        return manageroom(mapping, form, request, response);
    }

    public ActionForward editRoom(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	prepareLeftNav(request);
        
    	BedManagerForm bForm = (BedManagerForm) form;
    	String providerNo = (String) request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
        Integer facilityId = Integer.valueOf(request.getParameter("facilityId"));
        Integer roomId = Integer.valueOf(request.getParameter("roomId"));

        bForm.setFacilityId(facilityId);

        ArrayList assignedBedLst= new ArrayList();
        assignedBedLst.add(new LabelValueBean("N", "0"));
        assignedBedLst.add(new LabelValueBean("Y", "1"));
        request.setAttribute("assignedBedLst", assignedBedLst);

        request.setAttribute("roomId", roomId);

        request.setAttribute("roomTypes", roomManager.getRoomTypes());
        
        Room room;
        if(roomId.intValue()>0){
          room = roomManager.getRoom(roomId);
        }else{
          room = new Room();
          room.setFacilityId(facilityId);
        }
        bForm.setRoom(room);
        
        List pLst= programManager.getBedProgramsInFacility(providerNo, facilityId);
        bForm.setPrograms(pLst);

        return mapping.findForward("editroom");
    }

    public ActionForward editBed(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	prepareLeftNav(request);
        
    	BedManagerForm bForm = (BedManagerForm) form;
        Integer facilityId = Integer.valueOf(request.getParameter("facilityId"));
        Integer roomId = Integer.valueOf(request.getParameter("roomId"));
        Integer bedId = Integer.valueOf(request.getParameter("bedId"));

        bForm.setFacilityId(facilityId);
        Room room=roomManager.getRoom(roomId);
        bForm.setRoom(room);

        Program program = programManager.getProgram(room.getProgramId());
        request.setAttribute("program", program);
        
        request.setAttribute("roomId", roomId);
        request.setAttribute("bedId", bedId);

        request.setAttribute("roomTypes", roomManager.getRoomTypes());
        request.setAttribute("bedTypes", bedManager.getBedTypes());
        
        Bed bed;
        if(bedId.intValue()>0){
          bed = bedManager.getBed(bedId);
        }else{
          bed = new Bed();
          bed.setFacilityId(facilityId);
          bed.setRoomId(roomId);
        }
        bForm.setBed(bed);
        
        return mapping.findForward("editbed");
    }

    public ActionForward manageroom(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	prepareLeftNav(request);
        
    	BedManagerForm bForm = (BedManagerForm) form;
    	String providerNo = (String) request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
        Integer facilityId = Integer.valueOf(request.getParameter("facilityId"));

        bForm.setFacilityId(facilityId);

        Room[] temp =null;
        temp = roomManager.getRooms(facilityId);
        
        processDisplayRoom(form, request, temp, providerNo, facilityId);

        return mapping.findForward("manageroom");
    }

    public ActionForward managebed(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	prepareLeftNav(request);
        
    	BedManagerForm bForm = (BedManagerForm) form;
    	String providerNo = (String) request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
        Integer facilityId = Integer.valueOf(request.getParameter("facilityId"));
        Integer roomId = Integer.valueOf(request.getParameter("roomId"));
        request.setAttribute("roomId", roomId);

        bForm.setFacilityId(facilityId);

        Room room=roomManager.getRoom(roomId);
        bForm.setRoom(room);

        Program program = programManager.getProgram(room.getProgramId());
        request.setAttribute("program", program);
        
        List temp = bedManager.getBedsByFilter(facilityId, roomId, null, false);
        
        processDisplayBed(form, request, temp, providerNo, facilityId);

        return mapping.findForward("managebed");
    }
    
    public ActionForward doRoomFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	prepareLeftNav(request);
    	
    	Integer facilityId = Integer.valueOf(request.getParameter("facilityId"));
    	String providerNo = (String) request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);

        Integer roomStatus = Integer.valueOf(request.getParameter("roomStatusFilter"));
        Integer roomFilteredProgram = Integer.valueOf(request.getParameter("bedProgramFilterForRoom"));
        
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

        Room[] temp = roomManager.getRooms(facilityId, roomFilteredProgram, roomStatusBoolean);

        processDisplayRoom(form, request, temp, providerNo, facilityId);

        return mapping.findForward("manageroom");
    }

    private void processDisplayRoom(ActionForm form, HttpServletRequest request, Room[] roomLst,
    		String providerNo, Integer facilityId){
    	BedManagerForm bForm = (BedManagerForm) form;

        Room[] rooms = null;

        ArrayList rmLst = new ArrayList();
        for(int i =0; i < roomLst.length; i++){
        	Room rm = roomLst[i];
        	if(rm.getAssignedBed().intValue() == 1)	rmLst.add(rm);
        }
        if(rmLst.size()>0){
        	rooms = new Room[rmLst.size()];
        	for(int i = 0; i < rmLst.size(); i++){
        		rooms[i] = (Room)rmLst.get(i);
        	}
        }
        	
        request.setAttribute("rooms", roomLst);
        
        request.setAttribute("roomTypes", roomManager.getRoomTypes());

        ArrayList assignedBedLst= new ArrayList();
        assignedBedLst.add(new LabelValueBean("0", "N"));
        assignedBedLst.add(new LabelValueBean("1", "Y"));
        request.setAttribute("assignedBedLst", assignedBedLst);

        List pLst= programManager.getBedProgramsInFacility(providerNo, facilityId);
        if(pLst.size() < 1){
        	ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.program.not.exist", request.getContextPath()));
            saveMessages(request, messages);
        }
        bForm.setPrograms(pLst);
        Map statusNames = new HashMap();
        statusNames.put("1", "Active");
        statusNames.put("0", "Inactive");
        statusNames.put("2", "Any");
        bForm.setRoomStatusNames(statusNames);
    }

    private void processDisplayBed(ActionForm form, HttpServletRequest request, List bedLst,
    		String providerNo, Integer facilityId){
    	BedManagerForm bForm = (BedManagerForm) form;
        	
        request.setAttribute("beds", bedLst);
        
        request.setAttribute("roomTypes", roomManager.getRoomTypes());
        request.setAttribute("bedTypes", bedManager.getBedTypes());
    }

    private void processDisplay(ActionForm form, HttpServletRequest request){
/*    	
    	prepareLeftNav(request);
        
    	BedManagerForm bForm = (BedManagerForm) form;
    	String providerNo = (String) request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
        Integer facilityId = Integer.valueOf(request.getParameter("facilityId"));
        Facility facility = facilityManager.getFacility(facilityId);

        bForm.setFacilityId(facilityId);
        Room[] rooms = null;
        Boolean hasError= Boolean.FALSE;
        if(null!=request.getAttribute("hasErrorRoom"))
        hasError =(Boolean)request.getAttribute("hasErrorRoom");
        Room[] temp =null;
        if(hasError.booleanValue())temp=bForm.getRooms();
        else temp = roomManager.getRooms(facilityId);
        ArrayList rmLst = new ArrayList();
        for(int i =0; i < temp.length; i++){
        	Room rm = temp[i];
        	if(rm.getAssignedBed().intValue() == 1)
        		rmLst.add(rm);
        }
        if(rmLst.size()>0){
        	rooms = new Room[rmLst.size()];
        	for(int i = 0; i < rmLst.size(); i++){
        		rooms[i] = (Room)rmLst.get(i);
        	}
        }
        	
        bForm.setRooms(temp);
        
        //bForm.setAssignedBedRooms(roomManager.getAssignedBedRooms(facilityId));
        bForm.setAssignedBedRooms(rooms);
        bForm.setRoomTypes(roomManager.getRoomTypes());
        bForm.setNumRooms(new Integer(1));
        Integer tmp = bForm.getBedRoomFilterForBed();
        if(temp.length == 0){
        	tmp = null;
        	bForm.setBedRoomFilterForBed(new Integer(-1));
        	bForm.setExistRooms("NO");
        }else{
        	bForm.setExistRooms("YES");
        }
        Room[] room = bForm.getAssignedBedRooms();
        if( tmp != null && room != null){
        	
        	for(int i=0; i< room.length;i++){
	        	if(tmp.intValue() == room[i].getId().intValue()){
	        		break;
	        	}
	        	if(i==room.length-1)
	        		bForm.setBedRoomFilterForBed(new Integer(-1));
        	}
        		
        }else if(room == null){
        	bForm.setBedRoomFilterForBed(new Integer(-1));
        }
        
        if (bForm.getBedRoomFilterForBed() == null || bForm.getBedRoomFilterForBed().intValue() == -1) {
        	if(room != null && room.length > 0)
        		bForm.setBedRoomFilterForBed(room[0].getId());
        	else
        		bForm.setBedRoomFilterForBed(new Integer(-1));
        }

        List lst = bedManager.getBedsByFilter(facilityId, bForm.getBedRoomFilterForBed(), null, false);
        Bed[] bedsTemp= null;
        hasError =Boolean.FALSE;
        if(null!=request.getAttribute("hasErrorBed"))
            hasError =(Boolean)request.getAttribute("hasErrorBed");
        if(hasError.booleanValue()) bedsTemp = bForm.getBeds();
        else {
        	bedsTemp=new Bed[lst.size()];        
	        for(int i=0;i<lst.size();i++){
	        	bedsTemp[i]= (Bed)lst.get(i);
	        }
	    }
        bForm.setBeds(bedsTemp);


        bForm.setBedTypes(bedManager.getBedTypes());
        bForm.setNumBeds(new Integer(1));
        List pLst= programManager.getBedProgramsInFacility(providerNo, facilityId);
        if(pLst.size() < 1){
        	ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.program.not.exist", request.getContextPath()));
            saveMessages(request, messages);
        }
        bForm.setPrograms(pLst);
        bForm.setFacility(facility);
        Map statusNames = new HashMap();
        statusNames.put("1", "Active");
        statusNames.put("0", "Inactive");
        statusNames.put("2", "Any");
        bForm.setRoomStatusNames(statusNames);
        bForm.setBedStatusNames(statusNames);
*/
    }
    
/*    
    public ActionForward manageFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	prepareLeftNav(request);
    	
        BedManagerForm bForm = (BedManagerForm) form;

        Integer facilityId = Integer.valueOf(request.getParameter("facilityId"));
        String providerNo = (String)(request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO));
        Facility facility = facilityManager.getFacility(facilityId);

        bForm.setFacilityId(facilityId);
//        bForm.setRooms(bForm.getRooms());
//        bForm.setAssignedBedRooms(roomManager.getAssignedBedRooms(facilityId));
//        bForm.setRoomTypes(roomManager.getRoomTypes());
        request.setAttribute("roomTypes", roomManager.getRoomTypes());
        bForm.setNumRooms(new Integer(1));

        bForm.setBeds(bForm.getBeds());
        bForm.setBedTypes(bedManager.getBedTypes());
        bForm.setNumBeds(new Integer(1));
        bForm.setPrograms(programManager.getBedProgramsInFacility(providerNo, facilityId));
//        bForm.setFacility(facility);
        Map statusNames = new HashMap();
        statusNames.put("1", "Active");
        statusNames.put("0", "Inactive");
        statusNames.put("2", "Any");
        bForm.setRoomStatusNames(statusNames);
        bForm.setBedStatusNames(statusNames);

        return mapping.findForward(FORWARD_MANAGE);
    }
*/

/*    
    //please implement this method for QuatroShelter
    private boolean isRoomOverProgramCapacity(Room room, Bed bed){
    	return true;
    }
*/
    
    private boolean isRoomOverProgramCapacity(Room room, Bed bed,HttpServletRequest request){
    	boolean isValid=true;
    	ActionMessages messages = new ActionMessages();    
    	Integer pId=room.getProgramId();
    	if(pId==null ||pId.intValue()==0) return isValid;    	
    	Program pObj=programManager.getProgram(pId);
    	Integer capActual=pObj.getCapacity_actual();
    	//for new room 
    	if((room.getId()==null|| room.getId()==0)&&room.isActive()){
    		if(room.getAssignedBed()==null ||room.getAssignedBed()==0)capActual+=room.getOccupancy();
    		else if(room.getAssignedBed()>0 && bed!=null && bed.isActive()) capActual+=1;
    		
    	}else if(room.getId()!=null && room.getId()>0){
    		//actual capacity plus changed value
    		if(room.isActive()){
    			if(room.getAssignedBed()==null ||room.getAssignedBed()==0)capActual+=room.getOccupancy();
        		else if(room.getAssignedBed()>0 && bed!=null && bed.isActive()) capActual+=1;
    		}
    		//  actual capacity minus original value
    		Room roomOld = roomManager.getRoom(room.getId());
    		if(roomOld.isActive()){
    			if(roomOld.getAssignedBed()==null ||roomOld.getAssignedBed()==0)capActual-=room.getOccupancy();
        		else if(roomOld.getAssignedBed()>0 && bed!=null && bed.isActive()) capActual-=1;
    		}
    	}	    	
    	if(capActual.compareTo(pObj.getCapacity_space())> 0){
    		isValid = false;
    		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.program.room.over", request.getContextPath(),capActual,pObj.getName(),pObj.getCapacity_space()));
  	        saveMessages(request, messages);
    	}    
    	return isValid;
    }
    
/*
    private boolean isRoomOverProgramCapacity(Room[] rooms,HttpServletRequest request){
    	boolean isValid =true;
    	ActionMessages messages = new ActionMessages();
    	ArrayList programLst = new ArrayList();
    	for (int i = 0; i < rooms.length; i++) {
    		Integer pId=rooms[i].getProgramId();
    		if(i==0){
    			  Program pObj=programManager.getProgram(pId);
    			  if(rooms[i].isActive()) pObj.setTotalUsedRoom(rooms[i].getOccupancy());
    			  programLst.add(pObj);
    		}
    		else{
	    		  for(int j=0;j<programLst.size();j++){
	    			  Program pLocal =(Program)programLst.get(j);
	    			  if(pId.intValue()==pLocal.getId().intValue())  pLocal.setTotalUsedRoom(Integer.valueOf((pLocal.getTotalUsedRoom().intValue() +rooms[i].getOccupancy().intValue())));	    			  
	    			  else {
	    				  if(rooms[i].isActive())pLocal.setTotalUsedRoom(rooms[i].getOccupancy());
	        			  programLst.add(pLocal);
	    			  }
	    		  }
    		}    		  
    	}
    	//Validation
    	Iterator item = programLst.iterator();
    	while(item.hasNext()){
    		Program pLocal =(Program)item.next();
    		if(pLocal.getTotalUsedRoom().compareTo(pLocal.getCapacity_space())> 0){
    			isValid = false;
    			  messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.program.room.over", request.getContextPath(),pLocal.getName(),pLocal.getCapacity_space()));
  	            saveMessages(request, messages);
    		}
    		
    	}
    	return isValid;
    }
*/    
    public ActionForward saveRoom(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	prepareLeftNav(request);
    	
    	BedManagerForm bForm = (BedManagerForm) form;
        ActionMessages messages = new ActionMessages();

        Room room = bForm.getRoom();

        boolean isValid = isRoomOverProgramCapacity(room,null, request);
        
       	if(isValid){
            roomManager.saveRoom(room);
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.save.success", request.getContextPath()));
            saveMessages(request, messages);
       	}

        Integer facilityId = Integer.valueOf(request.getParameter("facilityId"));
        String providerNo = (String)(request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO));

        ArrayList assignedBedLst= new ArrayList();
        assignedBedLst.add(new LabelValueBean("N", "0"));
        assignedBedLst.add(new LabelValueBean("Y", "1"));
        request.setAttribute("assignedBedLst", assignedBedLst);

        request.setAttribute("roomId", room.getId());
        request.setAttribute("roomTypes", roomManager.getRoomTypes());

        List pLst= programManager.getBedProgramsInFacility(providerNo, facilityId);
        bForm.setPrograms(pLst);

        return mapping.findForward("editroom");
    }

    public ActionForward deleteRoom(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	prepareLeftNav(request);
    	
        ActionMessages messages = new ActionMessages();
        BedManagerForm bForm = (BedManagerForm) form;

        Integer roomId = Integer.valueOf(request.getParameter("roomId"));

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
        
        return manageroom(mapping, form, request, response);
   }
    

    public ActionForward saveBed(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	prepareLeftNav(request);
    	
    	BedManagerForm bForm = (BedManagerForm) form;
    	ActionMessages messages = new ActionMessages();

    	Bed bed = bForm.getBed();
        Room room=roomManager.getRoom(bed.getRoomId());

        boolean isValid = isRoomOverProgramCapacity(room, bed, request);
        
       	if(isValid){
           	bedManager.saveBed(bed);           
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.save.success", request.getContextPath()));
            saveMessages(request, messages);
       	}
       	
        Integer facilityId = Integer.valueOf(request.getParameter("facilityId"));
        Integer roomId = bed.getRoomId();
        Integer bedId = bed.getId();

        bForm.setFacilityId(facilityId);
        bForm.setRoom(room);

        Program program = programManager.getProgram(room.getProgramId());
        request.setAttribute("program", program);
        
        request.setAttribute("roomId", roomId);
        request.setAttribute("bedId", bedId);

        request.setAttribute("roomTypes", roomManager.getRoomTypes());
        request.setAttribute("bedTypes", bedManager.getBedTypes());
        
        bForm.setBed(bed);

        return mapping.findForward("editbed");
/*       	
       	Integer facilityId = Integer.valueOf(request.getParameter("facilityId"));
        String providerNo = (String)(request.getSession().getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO));

        request.setAttribute("roomId", room.getId());
        request.setAttribute("roomTypes", roomManager.getRoomTypes());

        return mapping.findForward("editbed");
*/       	
/*
        try {
            beds = bedManager.getBedsForUnfilledRooms(rooms, beds);
            if(rooms!=null)
            	for(int i=0;i<rooms.length;i++){
            		if(rooms[i].getTotalBedOccupancy().compareTo(rooms[i].getOccupancy())>0){
            			isValid =false;
            			messages.add(ActionMessages.GLOBAL_MESSAGE, 
            					new ActionMessage("message.room.bed.over", request.getContextPath(),rooms[i].getName(),rooms[i].getOccupancy()));
                    	saveMessages(request, messages);            			
            		}
            	}
            if(isValid){
            	bedManager.saveBeds(beds);           
            	messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.save.success", request.getContextPath()));
            	saveMessages(request, messages);
            }

        }
        catch (BedReservedException e) {
        	isValid =false;
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("bed.reserved.error", e.getMessage()));
            saveMessages(request, messages);
        }
        catch (DuplicateBedNameException e) {
        	isValid =false;
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("bed.duplicate.name.error", e.getMessage()));
            saveMessages(request, messages);
        }
        request.setAttribute("hasErrorBed",Boolean.valueOf(!isValid));
        return manage(mapping, form, request, response);
*/        
    }

    
/*    
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
*/

/*    
    public ActionForward addRoom(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

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
        }

    	
        processDisplay(form, request);
        
        if(numRooms != null && numRooms.intValue() > 0){
        	int numOfRoomsInDB = bForm.getRooms().length;
        	int len = roomslines.intValue(); //bForm.getRooms().length;
	        Room[] roomsTemp= new Room[len + numRooms.intValue()];
	        // keep existing value, include not saved.
	        for(int i = 0; i < len; i++){
	        	Room rm;
	        	if(i < numOfRoomsInDB){
	        		rm = bForm.getRooms()[i];
	        	}else{
	        		rm = new Room();
	        		rm.setFacilityId(bForm.getFacilityId());
	        	}
	        	String name = request.getParameter("rooms[" + i + "].name");
	        	rm.setName(name);
	        	rm.setFloor(request.getParameter("rooms[" + i + "].floor"));
	        	String rmTypeId = request.getParameter("rooms[" + i + "].roomTypeId");
	        	rm.setRoomTypeId(Integer.valueOf(rmTypeId));
	        	String assignBed = request.getParameter("rooms[" + i + "].assignedBed");
	        	rm.setAssignedBed(Integer.valueOf(assignBed));
	        	String occupancy = request.getParameter("rooms[" + i + "].occupancy");
	        	rm.setOccupancy(Integer.valueOf(occupancy));
	        	String programId = request.getParameter("rooms[" + i + "].programId");
	        	if(programId != null)
	        		rm.setProgramId(Integer.valueOf(programId));
	        	String active = request.getParameter("rooms[" + i + "].active");
	        	rm.setActive(active!=null);
	        	roomsTemp[i]= rm;
	        }

	        for(int i = len; i < len + numRooms.intValue(); i++){
	        	Room rm = new Room();
	        	rm.setFacilityId(bForm.getFacilityId());
	        	rm.setAssignedBed(new Integer(1));
	        	rm.setActive(true);
	        	roomsTemp[i]= rm;
	        }
	        bForm.setRooms(roomsTemp);
        }
        
    	return mapping.findForward(FORWARD_MANAGE);

    }
*/
    
/*    
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
        }

        
        Room room = roomManager.getRoom(roomId);
        int max = room.getOccupancy().intValue();
        
        processDisplay(form, request);
        
        int len = bForm.getBeds().length;
        int newLen = numBeds.intValue() + bedslines.intValue();
        if(newLen <= max){
	        if(numBeds != null && numBeds.intValue() > 0){
	        	
		        Bed[] bedsTemp= new Bed[newLen];
//		      TODO: keep existing value, include not saved.
		        for(int i = 0; i < len; i++){
		        	bedsTemp[i]= bForm.getBeds()[i];
		        }
		        
		        for(int i = len; i < newLen; i++){
		        	Bed bed = new Bed();
		        	bed.setFacilityId(facilityId);
		        	bed.setActive(true);
		        	bed.setRoomId(roomId);
		        	bedsTemp[i]= bed;
		        }
		        bForm.setBeds(bedsTemp);
	        }
        }else{
            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.addBed.tooMany", request.getContextPath(),new Integer(max)));
            saveMessages(request, messages);
        }
    	return mapping.findForward(FORWARD_MANAGE);
    	
    }
*/

/*    
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
*/
    
    public FacilityManager getFacilityManager() {
        return facilityManager;
    }

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
				
		String facilityId = request.getParameter("facilityId");
				
		HashMap actionParam = (HashMap) request.getAttribute("actionParam");
		if(actionParam==null){
	 	  actionParam = new HashMap();
	       actionParam.put("facilityId", facilityId); 
	    }
	    request.setAttribute("actionParam", actionParam);
	    Facility facility=facilityManager.getFacility(Integer.valueOf(facilityId));
	    request.setAttribute("facility", facility);
	    super.setScreenMode(request, KeyConstants.TAB_FACILITY_BED,facility.getId());
	    boolean isReadOnly= super.isReadOnly(request, KeyConstants.FUN_PMM_FACILITY_BED, facility.getId());
	    if(isReadOnly) request.setAttribute("isReadOnly", Boolean.valueOf(isReadOnly));
	   
	}
}
