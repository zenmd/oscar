<%@ include file="/taglibs.jsp"%>

<script>
    function ConfirmDelete(name)
    {
        return confirm("Are you sure you want to delete " + name + " ?");
    }
    
    function roomFilter(){
		document.forms[0].action = '<html:rewrite action="/PMmodule/BedManager.do?method=doRoomFilter" />';
		document.forms[0].submit();
    }
    
    function bedFilter(){
		document.forms[0].action = '<html:rewrite action="/PMmodule/BedManager.do?method=doBedFilter" />';
		document.forms[0].submit();
    }
    
    function addBeds(){
      /*if(bedManagerForm.bedslines.value!=null && parseInt(bedManagerForm.bedslines.value)>=10){
        alert("You cannot add more than 10 beds at a time.");
        return;
      }else{  
        bedManagerForm.method.value='addBeds';
        var obj= document.getElementsByName("submit.addBed")[0]
        obj.value='Add Beds';
        bedManagerForm.submit();
      } 
      */ 
      bedManagerForm.method.value='addBeds';
        var obj= document.getElementsByName("submit.addBed")[0]
        obj.value='Add Beds';
        bedManagerForm.submit();
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
    
    function saveBeds(){
      var i;
      var obj;
      if(bedManagerForm.bedslines.value!=null){
        for(i=0;i<parseInt(bedManagerForm.bedslines.value);i++){
          obj= document.getElementsByName("beds[" + i + "].name")[0] 
          if(obj.value==""){
            if(confirm("Bed can not be saved if it do not have a name. Are you sure you want to continue?")){
            	break;
            }else{
            	return;
            }
          }
        }
        bedManagerForm.method.value='saveBeds';
        obj= document.getElementsByName("submit.saveBed")[0]
        obj.value='Save Beds';
        bedManagerForm.submit();
      }  
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
    
    function deleteBed(){
 		var doDelete = confirm("Are you sure you want to delete the bed?");
 		if(doDelete){
			document.forms[0].action = '<html:rewrite action="/PMmodule/BedManager.do?method=deleteBed" />';
			document.forms[0].submit();
 		}
 	}
    
    function deleteBed2(bid){
		document.forms[0].bedToDelete.value=bid;
		deleteBed();
 	}
</script>
<% 
String s="debug"; 
%>
<html:form action="/PMmodule/BedManager.do">
	<table cellpadding="0" cellspacing="0" border="0" width="100%" 	height="100%">
		<tr>
			<th class="pageTitle" align="center">Facility Management - Manage Beds</th>
		</tr>
		<tr>
			<td height="100%">
			<table width="100%" cellpadding="0px" cellspacing="0px" height="100%" 	border="0">
				<!-- submenu -->
				<tr>
					<td align="left" class="buttonBar2">
						<html:link action="/Home.do"	style="color:Navy;text-decoration:none">&nbsp;
							<img style="vertical-align: middle" border=0 src=<html:rewrite page="/images/close16.png"/> />&nbsp;Close&nbsp;&nbsp;|</html:link>
						<html:link action="/PMmodule/FacilityManager.do?method=list" style="color:Navy;text-decoration:none;">
							<img border="0" src="<html:rewrite page="/images/Back16.png"/>" />&nbsp;Back to Facilities&nbsp;&nbsp;</html:link>
							
					</td>
				</tr>

				<!-- messages -->
				<tr>
					<td align="left" class="message">
						<logic:messagesPresent	message="true">
							<br />
							<html:messages id="message" message="true" bundle="pmm">
								<c:out escapeXml="false" value="${message}" />
							</html:messages>
							<br />
							</logic:messagesPresent>
					</td>
				</tr>

				<tr>
					<td height="100%">
					<div style="color: Black; background-color: White; border-width: 1px; border-style: Ridge;  height: 100%; width: 100%; overflow: auto;" id="scrollBar">
					<logic:notEmpty name="bedManagerForm" property="programs">
					<table width="100%" height="100%" cellpadding="0px"	cellspacing="0px">				
						<tr>
							<td>
							<table width="100%" summary="Manage rooms and beds">
								<html:hidden property="facilityId" />
								<html:hidden property="roomToDelete" />
								<html:hidden property="bedToDelete" />

								<tr>
									<td width="100%">
									<div class="tabs">
									<table cellpadding="3" cellspacing="0" border="0">
										<tr><th>Rooms</th></tr>
									</table>
									</div>									
									<!-- begin room status & bed program filter -->
									<table width="100%">
										<tr><td colspan="2" class="buttonBar2">
											<c:if test="${!isReadOnly}">
												<html:link href="javascript:saveRooms();" 	style="color:Navy;text-decoration:none;">
													<img border="0" src="<html:rewrite page="/images/Save16.png"/>" />&nbsp;Save Rooms&nbsp;&nbsp;</html:link>
											</c:if>	
										</td>
										<tr>
											<td style="font-weight:bold" align="left" width="250px">Room Status
												<html:select property="roomStatusFilter"	name="bedManagerForm" onchange="roomFilter();">
													<html:optionsCollection property="roomStatusNames"	value="key" label="value" />
												</html:select>
											</td>
											
											<td align="left" style="font-weight:bold">
												Bed	Program
											<html:select property="bedProgramFilterForRoom"	name="bedManagerForm" onchange="roomFilter();">
												<option value="0">Any</option>
												<html:optionsCollection property="programs" value="id"	label="name" />
											</html:select></td>
											
										</tr>
									</table>

									<!-- end room status & bed program filter --> 
									<display:table class="simple" name="sessionScope.bedManagerForm.rooms"	uid="room" requestURI="/PMmodule/BedManager.do"	summary="Edit rooms">
										<display:column title="Name" sortable="true" sortName="room" sortProperty="name">
											<input type="text" style="width:100%" name="rooms[<c:out value="${room_rowNum - 1}" />].name"
												value="<c:out value="${room.name}" />" />
										</display:column>
										<display:column title="Floor" sortable="true" sortName="room" sortProperty="floor">
											<input type="text" style="width:100%"	name="rooms[<c:out value="${room_rowNum - 1}" />].floor" value="<c:out value="${room.floor}" />" />
										</display:column>
										<display:column title="Type">
											<select name="rooms[<c:out value="${room_rowNum - 1}" />].roomTypeId">
												<c:forEach var="roomType"	items="${bedManagerForm.roomTypes}">
													<c:choose>
														<c:when test="${roomType.id == room.roomTypeId}">
															<option value="<c:out value="${roomType.id}"/>"	selected="selected">
															<c:out	value="${roomType.name}" /></option>
														</c:when>
														<c:otherwise>
															<option value="<c:out value="${roomType.id}"/>"><c:out
																value="${roomType.name}" /></option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select>
										</display:column>
										<display:column title="Assigned Beds">
											<select
												name="rooms[<c:out value="${room_rowNum - 1}" />].assignedBed">
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
											<select	name="rooms[<c:out value="${room_rowNum - 1}" />].occupancy">

												<c:forEach var="num" begin="1" end="20" step="1">
													<c:choose>
														<c:when test="${room.occupancy == num}">
															<option value="<c:out value='${num}' />"
																selected="selected"><c:out value="${num}" /></option>
														</c:when>
														<c:otherwise>
															<option value="<c:out value='${num}' />"><c:out	value="${num}" /></option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select>
										</display:column>
										<display:column title="Program">
											<select
												name="rooms[<c:out value="${room_rowNum - 1}" />].programId">
												<c:forEach var="program" items="${bedManagerForm.programs}">
													<c:choose>
														<c:when test="${program.id == room.programId}">
															<option value="<c:out value="${program.id}"/>"	selected="selected"><c:out
																value="${program.name}" /></option>
														</c:when>
														<c:otherwise>
															<option value="<c:out value="${program.id}"/>"><c:out
																value="${program.name}" /></option>
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
													<input type="checkbox"
														name="rooms[<c:out value="${room_rowNum - 1}" />].active" />
												</c:otherwise>
											</c:choose>
										</display:column>

										<display:column title="Delete">	
											<c:if test="${!isReadOnly}">	
												
												<c:if test="${room.id != null}">									
													<a href="javascript:deleteRoom2(<c:out value="${room.id}"/>);">Delete</a>											
												</c:if>								
													
											</c:if>
										</display:column>

									</display:table></td>
									
								</tr>
								
								<tr>
									<td><html:hidden property="numRooms" /> 
									<c:if test="${!isReadOnly}">
										<html:link 	style="color:Navy;text-decoration:underline;"  href="javascript:addRooms();">Add Rooms</html:link>
									</c:if>	
									<input type=hidden name="submit.addRoom" value="">
									<input type=hidden name="roomslines" value="<c:out value="${room_rowNum}" />"> 
										
									<input type=hidden name="submit.saveRoom" value="">
									</td>
								</tr>
								<tr>
									<td><br /></td>
								</tr>
								<logic:equal name="bedManagerForm" property="existRooms" value="YES">
									<logic:notEqual name="bedManagerForm" property="bedRoomFilterForBed" value="-1">
									
									<tr>
										<td >
										<div class="tabs">
										<table cellpadding="3" cellspacing="0" border="0">
											<tr>
												<th>Beds</th>
											</tr>
										</table>
										</div>
										<table width="100%" cellpadding="0px" cellspacing="0px" border="0">
											<!-- submenu -->
											<tr>
												<td align="left" class="buttonBar2">
													<c:if test="${!isReadOnly}">
														<html:link href="javascript:saveBeds();" style="color:Navy;text-decoration:none;">
														<img border="0" src="<html:rewrite page="/images/Save16.png"/>" />&nbsp;Save Beds&nbsp;&nbsp;</html:link>
													</c:if>
												</td>
											</tr>
										</table>
	
										<!-- begin bed status & bedRoom filter -->
										<table width="100%">
											<tr>
												
												<td align="left" width="250px" style="font-weight:bold">Bed Status
													<html:select property="bedStatusFilter" name="bedManagerForm"	onchange="bedFilter();">
														<html:optionsCollection property="bedStatusNames"	value="key" label="value" />
													</html:select>
												</td>												
												<td align="left" style="font-weight:bold">Room
													<html:select property="bedRoomFilterForBed"	name="bedManagerForm" onchange="bedFilter();">
														<html:optionsCollection property="assignedBedRooms" value="id"	label="name" />
													</html:select>
												</td>
											</tr>
										</table>
										<!-- end bed status & bedRoom filter --> 
										<display:table	class="simple" name="sessionScope.bedManagerForm.beds"	uid="bed" requestURI="/PMmodule/BedManager.do"	summary="Edit beds">
	
											<display:column title="Name" sortable="true" sortName="bed" sortProperty="name">
												<input type="text" style="width:100%" 	name="beds[<c:out value="${bed_rowNum - 1}" />].name"
													value="<c:out value="${bed.name}" />" />
											</display:column>
											<display:column title="Type">
												<select	name="beds[<c:out value="${bed_rowNum - 1}" />].bedTypeId">
													<c:forEach var="bedType" items="${bedManagerForm.bedTypes}">
														<c:choose>
															<c:when test="${bedType.id == bed.bedTypeId}">
																<option value="<c:out value="${bedType.id}"/>"	selected="selected"><c:out
																	value="${bedType.name}" />
																</option>
															</c:when>
															<c:otherwise>
																<option value="<c:out value="${bedType.id}"/>">
																<c:out	value="${bedType.name}" /></option>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</select>
											</display:column>
											<display:column title="Room">
												<select	name="beds[<c:out value="${bed_rowNum - 1}" />].roomId">
													<c:forEach var="room"	items="${bedManagerForm.assignedBedRooms}">
														<c:choose>
															<c:when test="${room.id == bed.roomId}">
																<option value="<c:out value="${room.id}"/>" 
																	selected="selected"><c:out value="${room.name}" />
																</option>
															</c:when>															
														</c:choose>
													</c:forEach>
												</select>
											</display:column>
											
											<display:column title="Active" sortable="true">
												<c:choose>
													<c:when test="${bed.active}">
														<input type="checkbox"	name="beds[<c:out value="${bed_rowNum - 1}" />].active"	checked="checked" />
													</c:when>
													<c:otherwise>
														<input type="checkbox"	name="beds[<c:out value="${bed_rowNum - 1}" />].active" />
													</c:otherwise>
												</c:choose>
											</display:column>
	
											<display:column title="Delete">												
												<c:if test="${!isReadOnly}">
													<c:if test="${bed.id != null}">	
														<a href="javascript:deleteBed2(<c:out value="${bed.id}"/>);">Delete </a>
													</c:if>
												</c:if>
												
											</display:column>
	
										</display:table></td>
										
									</tr>
									<tr>
										<td>
											<input type=hidden name="bedslines"		value="<c:out value="${bed_rowNum}" />"> 
											<c:choose>
											<c:when test="${not empty bedManagerForm.rooms}">
												<html:hidden property="numBeds" />	
												<c:if test="${!isReadOnly}">											
													<html:link	style="color:Navy;text-decoration:underline;" href="javascript:addBeds();">Add Beds</html:link>													
												</c:if>
												<input type=hidden name="submit.addBed" value="">
											</c:when>
											<c:otherwise>												
											</c:otherwise>
										</c:choose>	
										<input	type=hidden name="submit.saveBed" value="">
										</td>
									</tr>
									
								</logic:notEqual>
								</logic:equal>					
								
							</table>
							</td>
						</tr>
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
