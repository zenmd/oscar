<%@ include file="/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/quatro-tag.tld" prefix="quatro" %>
<%@page import="org.oscarehr.PMmodule.model.Admission"%>
<%@page import="java.util.Date"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<script type="text/javascript" src='<c:out value="${ctx}"/>/js/quatroLookup.js'></script>
 
<html-el:form action="/PMmodule/QuatroAdmission.do">
<input type="hidden" name="method"/>
<script lang="javascript">
	function submitForm(methodVal) {
		document.forms(0).method.value = methodVal;
		document.forms(0).submit();
	}
</script>
<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
	<tr>
		<th class="pageTitle" align="center">Client Management - Admission</th>
	</tr>
	<tr>
		<td align="left" valign="middle" class="buttonBar2">
		&nbsp;<html:link action="/PMmodule/QuatroClientSummary.do" name="actionParam" style="color:Navy;text-decoration:none;">Summary</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/ClientHistory.do" name="actionParam" style="color:Navy;text-decoration:none;">History</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroIntake.do" name="actionParam" style="color:Navy;text-decoration:none;">Intake</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<b>Admission</b>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroRefer.do" name="actionParam" style="color:Navy;text-decoration:none;">Refer</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroDischarge.do" name="actionParam" style="color:Navy;text-decoration:none;">Discharge</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroServiceRestriction.do" name="actionParam" style="color:Navy;text-decoration:none;">Service Restriction</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroComplaint.do" name="actionParam" style="color:Navy;text-decoration:none;">Complaints</html:link>
		</td>
	</tr>
	<tr>
		<td align="left" class="buttonBar">
		<html:link action="/PMmodule/ClientSearch2.do" style="color:Navy;text-decoration:none;">
		<img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Close&nbsp;&nbsp;</html:link></td>
	</tr>
	<tr><td align="left" class="message">
      <logic:messagesPresent message="true">
        <html:messages id="message" message="true" bundle="pmm"><c:out escapeXml="false" value="${message}" />
        </html:messages> 
      </logic:messagesPresent>
	</td></tr>
	<tr>
		<td height="100%">
		<div
			style="color: Black; background-color: White; border-width: 1px; border-style: Ridge;
                    height: 100%; width: 100%; overflow: auto;">

<!--  start of page content -->
<table width="100%" class="edit">
<tr><td><br><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Admission Information</th></tr>
</table></div></td></tr>

<tr><td>
<table class="simple" cellspacing="2" cellpadding="3">
<tr><td width="25%">Ontario Resident Status</td>
<td width="25%"><html:text property="residentStatus" /></td>
<td width="20%">Primary Worker</td>
<td width="30%"><html:text property="primaryWorker" /></td></tr>
<tr><td>Locker#</td>
<td><html:text property="lockerNo" /></td>
<td>Primary Worker</td>
<td><html:text property="noOfBags" /></td></tr>
</table>
</td></tr>

<tr><td><br><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Next of Kin</th></tr>
</table></div></td></tr>

<tr><td>
<table class="simple" cellspacing="2" cellpadding="3">
<tr><td width="10%">Name</td>
<td width="25%"><html:text property="nextKinName" /></td>
<td width="10%">Relationship</td>
<td width="20%"><html:text property="nextKinRelationship" /></td>
<td width="10%">Telephone</td>
<td width="25%"><html:text property="nextKinTelephone" style="width: 70%" /></td></tr>
<tr><td>Number</td>
<td><html:text property="nextKinNumber" /></td>
<td>Street</td>
<td colspan="3"><html:text property="nextKinStreet" style="width: 80%" /></td></tr>
<tr><td>City</td>
<td><html:text property="nextKinCity" /></td>
<td>Province</td>
<td><html:select property="nextKinProvince" /></td>
<td>Postal</td>
<td><html:text property="nextKinPostal" style="width: 70%" /></td></tr>
</table>
</td></tr>

<tr><td><br><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Overnight Pass</th></tr>
</table></div></td></tr>

<tr><td>
<table class="simple" cellspacing="2" cellpadding="3">
<tr><td colspan="4">Overnight Pass</td>
<td>Issued By</td></tr>
<tr><td width="5%">Start</td>
<td width="20%">
<quatro:datePickerTag property="ovPassStartDate" width="70%" openerForm="quatroClientAdmissionForm" />
</td>
<td width="5%">End</td>
<td width="20%">
<quatro:datePickerTag property="ovPassEndDate" width="70%" openerForm="quatroClientAdmissionForm" />
</td>
<td width="50%"><c:out value="${issuedBy}" /></td></tr>
</table>
</td></tr>

<tr><td><br><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Bed/Room Reservation</th></tr>
</table></div></td></tr>

<tr><td>
<c:choose>
  <c:when test="${empty bedProgramId}">
	<table class="simple" cellspacing="2" cellpadding="3">
	  <tr><td><span style="color:red">Client is not admitted to a bed program</span></td></tr>
	</table>
  </c:when>
</c:choose>	
</td></tr>

<tr><td>
<table class="simple" cellspacing="2" cellpadding="3">
<!-- Begin of Assign Room -------------------------------------------------------------->
	<tr><th width="20%">Assign Room</th>
	<td>
	  <c:choose>
		<c:when test="${availableRooms == null}">
	      <select property="roomId">
			<option value="" selected="selected"> No available rooms</option>
		  </select>
		</c:when>
		<c:otherwise>
		  <select name="roomId"  onchange="clientManagerForm.method.value='refreshBedDropDownForReservation';clientManagerForm.submit();">
			<option value="0">Select a room</option>
			<c:forEach var="availableRoom" items="${availableRooms}">
			  <c:choose>
				<c:when test="${roomId == availableRoom.id}">
				   <option value="<c:out value="${availableRoom.id}"/>" selected="selected">
					 <c:out value="${availableRoom.name}" />
				   </option>
				</c:when>
			    <c:otherwise>
				  <option value="<c:out value="${availableRoom.id}"/>">
					 <c:out value="${availableRoom.name}" />
				  </option>
				</c:otherwise>
			   </c:choose>
			</c:forEach>
		  </select>
		</c:otherwise>					
	   </c:choose>
	</td></tr>
<!-- End of Assign Room -------------------------------------------------------------->
	<tr><th width="20%">Assign Bed</th>
	<td><html:select property="bedDemographic.bedId">
		<c:choose>
		  <c:when test="${!isAssignedBed}">
			<option value="0" selected="selected"><c:out value="N/A" /></option>
		  </c:when>
		  <c:otherwise>
			<option value="0"></option>
		  </c:otherwise>
		</c:choose>
					
		<c:forEach var="unreservedBed" items="${clientManagerForm.map.unreservedBeds}">
		  <c:choose>
			<c:when test="${unreservedBed.id == clientManagerForm.map.bedDemographic.bedId}">
			  <option value="<c:out value="${unreservedBed.id}"/>" selected="selected">
				<c:out value="${unreservedBed.roomName}" /> - <c:out value="${unreservedBed.name}" />
			  </option>
			</c:when>
		    <c:otherwise>
			  <option value="<c:out value="${unreservedBed.id}"/>">
				 <c:out value="${unreservedBed.roomName}" /> - <c:out value="${unreservedBed.name}" />
			  </option>
			</c:otherwise>
		  </c:choose>
		</c:forEach>
	  </html:select>
	</td></tr>
	<tr><th width="20%">Status</th>
	<td>
	  <html:select property="bedDemographic.bedDemographicStatusId">
	  <c:forEach var="bedDemographicStatus" items="${clientManagerForm.map.bedDemographicStatuses}">
		<c:choose>
		   <c:when test="${bedDemographicStatus.id == clientManagerForm.map.bedDemographic.bedDemographicStatusId}">
			 <option value="<c:out value="${bedDemographicStatus.id}"/>" selected="selected">
			   <c:out value="${bedDemographicStatus.name}" />
			 </option>
		   </c:when>
		   <c:otherwise>
			  <option value="<c:out value="${bedDemographicStatus.id}"/>">
				 <c:out value="${bedDemographicStatus.name}" />
			  </option>
		   </c:otherwise>
		</c:choose>
	  </c:forEach>
	  </html:select>
	 </td></tr>
	 <tr><th width="20%">Late Pass</th>
	 <td><html:checkbox property="bedDemographic.latePass" /></td></tr>
	 <tr><th width="20%">Until</th>
	 <td>
	   <c:choose>
		  <c:when test="${clientManagerForm.map.bedDemographic.strReservationEnd == clientManagerForm.map.bedDemographic.infiniteDate}">
			 <c:set var="endDate" value="" />
		  </c:when>
		  <c:otherwise>
			 <c:set var="endDate" value="${clientManagerForm.map.bedDemographic.strReservationEnd}" />
		  </c:otherwise>
		</c:choose>

		<input type="hidden" name="bedDemographic.strReservationEnd" id="strReservationEnd_field_hidden" value="<c:out value="${clientManagerForm.map.bedDemographic.strReservationEnd}"/>" />
		<input type="text" name="" id="strReservationEnd_field" readonly="readonly" value="<c:out value="${endDate}"/>"  onchange="setEndDate();" />
		<img align="top" src="<html:rewrite page="/images/calendar.gif" />" id="strReservationEnd_field-button" alt="Reserve Until Calendar" title="Reserve Until Calendar" />
				
		<script type="text/javascript">
			Calendar.setup(
			{
				inputField :  'strReservationEnd_field',
				ifFormat :    '%Y-%m-%d',
				button :      'strReservationEnd_field-button',
				align :       'cr',
				singleClick : true,
				firstDay :    1
			});
						
			function setEndDate(){
				document.getElementById('strReservationEnd_field_hidden').value = document.getElementById('strReservationEnd_field').value;
			}
		</script>
	  </td></tr>
	</table>
</td></tr>

<tr><td align="center">	
 <input value="Sign" type="button" onclick="clientManagerForm.method.value='saveBedReservation';">&nbsp;&nbsp;
 <html:submit onclick="clientManagerForm.method.value='saveBedReservation';">Save</html:submit>&nbsp;&nbsp;
<input value="Preview" type="button" onclick="clientManagerForm.method.value='saveBedReservation';">
</td></tr>

<tr><td align="center">Reason for not signing&nbsp;
<html-el:select property="notSignReason">
      <html-el:optionsCollection property="notSignReasonList" value="value" label="label"/>
</html-el:select>
</td></tr>

</table>

<!--  end of page content -->
</div>
</td>
</tr>
</table>
</html-el:form>
