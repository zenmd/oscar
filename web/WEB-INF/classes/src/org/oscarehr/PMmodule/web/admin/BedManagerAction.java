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
import org.oscarehr.PMmodule.model.Bed;
import org.oscarehr.PMmodule.model.Facility;
import org.oscarehr.PMmodule.model.Program;
import org.oscarehr.PMmodule.model.Room;
import org.oscarehr.PMmodule.service.BedManager;
import org.oscarehr.PMmodule.service.FacilityManager;
import org.oscarehr.PMmodule.service.ProgramManager;
import org.oscarehr.PMmodule.service.RoomManager;
import org.oscarehr.PMmodule.web.BaseFacilityAction;
import org.springframework.dao.DataIntegrityViolationException;

import com.quatro.common.KeyConstants;

public class BedManagerAction extends BaseFacilityAction {

    private FacilityManager facilityManager;
    private BedManager bedManager;
    private ProgramManager programManager;
    private RoomManager roomManager;

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        return manageroom(mapping, form, request, response);
    }

    public ActionForward editRoom(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	prepareLeftNav(request);
        
    	BedManagerForm bForm = (BedManagerForm) form;
    	String providerNo = (String) request.getSession(true).getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
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

        Bed[] temp = bedManager.getActiveBedsByRoom(roomId);
        request.setAttribute("activebednum", String.valueOf(temp.length));

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
          bed.setRoomId(roomId);
        }
        bForm.setBed(bed);
        
        return mapping.findForward("editbed");
    }

    public ActionForward manageroom(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	prepareLeftNav(request);
        
    	BedManagerForm bForm = (BedManagerForm) form;
    	String providerNo = (String) request.getSession(true).getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
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
    	String providerNo = (String) request.getSession(true).getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);
        Integer facilityId = Integer.valueOf(request.getParameter("facilityId"));
        Integer roomId = Integer.valueOf(request.getParameter("roomId"));
        request.setAttribute("roomId", roomId);

        bForm.setFacilityId(facilityId);

        Room room=roomManager.getRoom(roomId);
        bForm.setRoom(room);

        Program program = programManager.getProgram(room.getProgramId());
        request.setAttribute("program", program);
        
        Bed[] temp = bedManager.getAllBedsByRoom(roomId);
        
        processDisplayBed(form, request, temp, providerNo, facilityId);

        return mapping.findForward("managebed");
    }
    
    public ActionForward doRoomFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	prepareLeftNav(request);
    	
    	Integer facilityId = Integer.valueOf(request.getParameter("facilityId"));
    	String providerNo = (String) request.getSession(true).getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO);

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

    private void processDisplayBed(ActionForm form, HttpServletRequest request, Bed[] bedLst,
    		String providerNo, Integer facilityId){
    	BedManagerForm bForm = (BedManagerForm) form;
        	
        request.setAttribute("beds", bedLst);
        
        request.setAttribute("roomTypes", roomManager.getRoomTypes());
        request.setAttribute("bedTypes", bedManager.getBedTypes());
    }

    private boolean isRoomOverProgramCapacity(Room room, Bed bed,HttpServletRequest request){
    	boolean isValid=true;
    	ActionMessages messages = new ActionMessages();    
    	Integer pId=room.getProgramId();
    	if(pId==null ||pId.intValue()==0) return isValid;    	
    	Program pObj=programManager.getProgram(pId);
    	int capActual=pObj.getCapacity_actual().intValue();
    	if (bed != null) {
    		if (bed.isActive()) capActual+=1;
    		if(bed.getId().intValue() > 0)
    		{
    			Bed bedOld  = bedManager.getBed(bed.getId());
    			if (bedOld.isActive()) capActual -= 1;
    		}
    	}
    	else
    	{
	    	//for new room 
	    	if((room.getId()==null|| room.getId().intValue()==0)&&room.isActive()){
	    		if(room.getAssignedBed().intValue()==0)capActual+=room.getCapacity().intValue();
	    	}else{ // existing room
	    		//actual capacity plus changed value
	    		if(room.isActive()){
	    			if(room.getAssignedBed().intValue()==0)capActual+=room.getCapacity().intValue();
	    		}
	    		//  for update only actual capacity minus original value
	    		Room roomOld = roomManager.getRoom(room.getId());
	    		if(roomOld!=null && roomOld.isActive()){
	    			if(roomOld.getAssignedBed().intValue()==0)capActual-=room.getCapacity().intValue();
	    		}
	    	}
    	}
    	if(capActual>pObj.getCapacity_space().intValue()){
    		isValid = false;
    		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.program.room.over", request.getContextPath(),new Integer(capActual),pObj.getName(),pObj.getCapacity_space()));
  	        saveMessages(request, messages);
    	}    
    	return isValid;
    }
    
    public ActionForward saveRoom(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	prepareLeftNav(request);
    	
    	BedManagerForm bForm = (BedManagerForm) form;
        ActionMessages messages = new ActionMessages();

        Room room = bForm.getRoom();

        boolean isValid = isRoomOverProgramCapacity(room,null, request);
        
       	if(isValid){
            try{
            	roomManager.saveRoom(room);
                messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.save.success", request.getContextPath()));
                saveMessages(request, messages);
            }catch(IllegalStateException ex){
                messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("room.state.error", request.getContextPath(),ex.getMessage()));
                saveMessages(request, messages);
            }
            catch(DataIntegrityViolationException ex)
            {
                messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("room.state.error", request.getContextPath(),"Duplicate Room Name Detected"));
                saveMessages(request, messages);
            }
       	}

        Integer facilityId = Integer.valueOf(request.getParameter("facilityId"));
        String providerNo = (String)(request.getSession(true).getAttribute(KeyConstants.SESSION_KEY_PROVIDERNO));

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
/*
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
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("room.inactive.error", request.getContextPath()));
            saveMessages(request, messages);
        }
        
        return manageroom(mapping, form, request, response);
   }
*/    

    public ActionForward saveBed(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	prepareLeftNav(request);
    	
    	BedManagerForm bForm = (BedManagerForm) form;
    	ActionMessages messages = new ActionMessages();

    	Bed bed = bForm.getBed();
        Room room=roomManager.getRoom(bed.getRoomId());

        boolean isValid = true;
        //not need check for change bed inactive 
        if(bed!=null && bed.isActive()) isValid =isRoomOverProgramCapacity(room, bed, request);        
       	if(isValid){
       		try{
           	  bedManager.saveBed(bed);           
              messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.save.success", request.getContextPath()));
              saveMessages(request, messages);
            }catch(IllegalStateException ex){
                messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("bed.state.error", request.getContextPath(),ex.getMessage()));
                saveMessages(request, messages);
            }
            catch(DataIntegrityViolationException ex)
            {
                messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("bed.state.error", request.getContextPath(),"Duplicate Bed Name Detected"));
                saveMessages(request, messages);
            }

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
    }

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

	private void prepareLeftNav(HttpServletRequest request){
				
		String facilityId = request.getParameter("facilityId");
				
		HashMap actionParam = (HashMap) request.getAttribute("actionParam");
		if(actionParam==null) actionParam = new HashMap();
	    actionParam.put("facilityId", facilityId); 
	    request.setAttribute("actionParam", actionParam);
	    Facility facility=facilityManager.getFacility(Integer.valueOf(facilityId));
	    request.setAttribute("facility", facility);
	    super.setScreenMode(request, KeyConstants.TAB_FACILITY_BED);
	    boolean isReadOnly= super.isReadOnly(request, KeyConstants.FUN_FACILITY_BED, facility.getId());
	    if(isReadOnly) request.setAttribute("isReadOnly", Boolean.valueOf(isReadOnly));
	   
	}
}
