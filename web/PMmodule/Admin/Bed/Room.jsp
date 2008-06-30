<%@ include file="/taglibs.jsp"%>

<script>
    function ConfirmDelete(name)
    {
        return confirm("Are you sure you want to delete " + name + " ?");
    }
    
    function roomFilter(){
//		document.forms[0].action = '<html:rewrite action="/PMmodule/BedManager.do?method=doRoomFilter" />';
        document.forms[0].method.value='doRoomFilter';
		document.forms[0].submit();
    }
    
    function addRooms(){
    /*
      if(bedManagerForm.roomslines.value!=null && parseInt(bedManagerForm.roomslines.value)>=10){
        alert("You cannot add more than 10 rooms at a time.");
        return;
      }else{  
        bedManagerForm.method.value='addRooms';
        var obj= document.getElementsByName("submit.addRoom")[0]
        obj.value='Add Rooms';
        bedManagerForm.submit();
      }
      */
      	bedManagerForm.method.value='addRooms';
        var obj= document.getElementsByName("submit.addRoom")[0]
        obj.value='Add Rooms';
        bedManagerForm.submit();  
    }    
    
    function saveRooms(){
      var i;
      var obj;
      if(bedManagerForm.roomslines.value!=null){
        for(i=0;i<parseInt(bedManagerForm.roomslines.value);i++){
          obj= document.getElementsByName("rooms[" + i + "].name")[0] 
          if(obj.value==""){
            //alert("Room name can not be empty.");
            if(confirm("Room can not be saved if it do not have a name. Are you sure you want to continue?")){
            	break;
            }else{
            	return;
            }
          }
        }
        bedManagerForm.method.value='saveRooms';
        obj= document.getElementsByName("submit.saveRoom")[0]
        obj.value='Save Rooms';
        bedManagerForm.submit();
      }  
    }    
    
    function deleteRoom(){
    	var doDelete = confirm("Are you sure you want to delete the room?");
    	if(doDelete){
			document.forms[0].action = '<html:rewrite action="/PMmodule/BedManager.do?method=deleteRoom" />';
			document.forms[0].submit();
    	}
    }
    function deleteRoom2(rid){
    	document.forms[0].roomToDelete.value=rid;
    	deleteRoom();
    }
</script>

<html:form action="/PMmodule/BedManager.do">
  <input type="hidden" name="method" />
  <input type="hidden" name="hasErrorRoom" />
  <input type="hidden" name="hasErrorBed" />
  <table cellpadding="0" cellspacing="0" border="0" width="100%" 	height="100%">
	<tr><th class="pageTitle" align="center">Facility Management - Manage Rooms</th></tr>
	<tr><td height="100%">
	 <table width="100%" cellpadding="0px" cellspacing="0px" height="100%" 	border="0">
	 <!-- submenu -->
	  <tr><td align="left" class="buttonBar2">
		<c:if test="${!isReadOnly}">
		  <html:link href="javascript:saveRooms();" style="color:Navy;text-decoration:none;">
		  <img border="0" src="<html:rewrite page="/images/Save16.png"/>" />&nbsp;Save Rooms&nbsp;&nbsp;|</html:link>

		  <html:link href="javascript:addRooms();" style="color:Navy;text-decoration:none;">
		  <img style="vertical-align: middle" border=0 src=<html:rewrite page="/images/New16.png"/> />&nbsp;Add Rooms&nbsp;&nbsp;|</html:link>
		</c:if>	

		<html:link action="/Home.do"	style="color:Navy;text-decoration:none">&nbsp;
		<img style="vertical-align: middle" border=0 src=<html:rewrite page="/images/close16.png"/> />&nbsp;Close&nbsp;&nbsp;|</html:link>
		<html:link action="/PMmodule/FacilityManager.do?method=list" style="color:Navy;text-decoration:none;">
		<img border="0" src="<html:rewrite page="/images/Back16.png"/>" />&nbsp;Back to Facilities&nbsp;&nbsp;</html:link>
	  </td></tr>

	  <!-- messages -->
	  <tr><td align="left" class="message">
		<logic:messagesPresent	message="true"><br />
		  <html:messages id="message" message="true" bundle="pmm"><c:out escapeXml="false" value="${message}" />
		  </html:messages><br />
		</logic:messagesPresent>
	  </td></tr>

   	  <tr><td height="100%">
		<div style="color: Black; background-color: White; border-width: 1px; border-style: Ridge;  height: 100%; width: 100%; overflow: auto;" id="scrollBar">
		 <logic:notEmpty name="bedManagerForm" property="programs">
		  <table width="100%" height="100%" cellpadding="0px"	cellspacing="0px">				
			<tr><td>
			  <table width="100%" summary="Manage rooms and beds">
				<html:hidden property="facilityId" />
				<html:hidden property="roomToDelete" />
				<html:hidden property="bedToDelete" />
				<tr><td width="100%">								
				<!-- begin room status & bed program filter -->
				<table width="100%">
				  <tr><td style="font-weight:bold" align="left" width="250px">Room Status
					<html:select property="roomStatusFilter"	name="bedManagerForm" onchange="roomFilter();">
					<html:optionsCollection property="roomStatusNames"	value="key" label="value" />
					</html:select>
				  </td>
				  <td align="left" style="font-weight:bold">Bed	Program
				  <html:select property="bedProgramFilterForRoom"	name="bedManagerForm" onchange="roomFilter();">
					<option value="0">Any</option>
					<html:optionsCollection property="programs" value="id" label="name" />
				  </html:select></td></tr>
				</table>

				<!-- end room status & bed program filter --> 
				<display:table class="simple" name="bedManagerForm.rooms"	id="room" requestURI="/PMmodule/BedManager.do">
				   <display:column title="Name" sortable="true" sortName="room" sortProperty="name">
					  <input type="text" style="width:100%" name="rooms[<c:out value="${room_rowNum - 1}" />].name"	value="<c:out value="${room.name}" />" />
				   </display:column>
				   <display:column title="Floor" sortable="true" sortProperty="floor">
					  <input type="text" style="width:100%"	name="rooms[<c:out value="${room_rowNum - 1}" />].floor" value="<c:out value="${room.floor}" />" />
				   </display:column>
				   <display:column title="Type">
					  <select name="rooms[<c:out value="${room_rowNum - 1}" />].roomTypeId">
						<c:forEach var="roomType"	items="${bedManagerForm.roomTypes}">
						<c:choose>
						  <c:when test="${roomType.id == room.roomTypeId}">
							<option value="<c:out value="${roomType.id}"/>"	selected="selected"><c:out value="${roomType.name}" /></option>
						  </c:when>
						  <c:otherwise>
							<option value="<c:out value="${roomType.id}"/>"><c:out value="${roomType.name}" /></option>
						  </c:otherwise>
						</c:choose>
						</c:forEach>
					  </select>
				   </display:column>
				   <display:column title="Assigned Beds">
					<select name="rooms[<c:out value="${room_rowNum - 1}" />].assignedBed">
						<c:choose>
						<c:when test="${room.assignedBed != 1}">
						  <option value="1">Y</option>
						  <option value="0" selected>N</option>
						</c:when>
						<c:otherwise>
						  <option value="1" selected>Y</option>
						  <option value="0">N</option>
						</c:otherwise>
						</c:choose>
					 </select>
				   </display:column>
				   <display:column title="Room Capacity">
					  <select name="rooms[<c:out value="${room_rowNum - 1}" />].occupancy">
						 <c:forEach var="num" begin="1" end="20" step="1">
						 <c:choose>
						 <c:when test="${room.occupancy == num}">
						   <option value="<c:out value='${num}' />" selected="selected"><c:out value="${num}" /></option>
						 </c:when>
						 <c:otherwise>
						   <option value="<c:out value='${num}' />"><c:out	value="${num}" /></option>
						 </c:otherwise>
						 </c:choose>
					 	 </c:forEach>
					  </select>
					</display:column>
					<display:column title="Program">
					  <select name="rooms[<c:out value="${room_rowNum - 1}" />].programId">
						 <c:forEach var="program" items="${bedManagerForm.programs}">
						 <c:choose>
						 <c:when test="${program.id == room.programId}">
							<option value="<c:out value="${program.id}"/>"	selected="selected"><c:out value="${program.name}" /></option>
						 </c:when>
						 <c:otherwise>
							<option value="<c:out value="${program.id}"/>">
							<c:out value="${program.name}" /></option>
						 </c:otherwise>
						 </c:choose>
						 </c:forEach>
					  </select>
					</display:column>
					<display:column title="Active" sortable="true">
					   <c:choose>
					     <c:when test="${room.active}">
						   <input type="checkbox"	name="rooms[<c:out value="${room_rowNum - 1}" />].active" checked="checked" />
					     </c:when>
					     <c:otherwise>
						   <input type="checkbox"	name="rooms[<c:out value="${room_rowNum - 1}" />].active" />
					     </c:otherwise>
					   </c:choose>
					</display:column>
					<display:column title="Delete">	
					  <c:if test="${!isReadOnly &&  room.id != null}">									
						 <a href="javascript:deleteRoom2(<c:out value="${room.id}"/>);">Delete</a>											
					  </c:if>								
					</display:column>
				</display:table>
				</td></tr>
								
				<tr>
					<td><html:hidden property="numRooms" /> 
						<input type=hidden name="submit.addRoom" value="">
						<input type=hidden name="roomslines" value="<c:out value="${room_rowNum}" />"> 
						<input type=hidden name="submit.saveRoom" value="">
					</td>
				</tr>
			</table>
			</td></tr>
		</table>

</logic:notEmpty>

					</div>
					</td>
				</tr>
			</table>



			</td>
		</tr>
		<!-- body end -->
	</table>
	<%@ include file="/common/readonly.jsp" %>
</html:form>
