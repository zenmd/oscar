<%@ include file="/taglibs.jsp" %>
<html:html locale="true">
<head>
<html:base />
</head>

<body>
<table border="0" style="width:100%">
	<tr>
	<td >
		<table width="100%" class="simple"  cellspacing="2" cellpadding="3">
			<tr>
			<td style="width: 15%" ><font><b>Client No.</b></font></td><td colspan="3"><font><b><c:out value="${client.demographicNo}" /></b></font></td>
			</tr>
			<tr>
				<td style="width: 15%"><font><b>Name</b></font></td>
				<td style="width: 35%"><font><b><c:out value="${client.formattedName}" /></b></font></td>
				<td style="width: 15%"><font><b>Date of Birth </b></font></td>
				<td style="width: 35%"><font><b><c:out value="${client.yearOfBirth}" />/<c:out value="${client.monthOfBirth}" />/<c:out value="${client.dateOfBirth}" /></b></font></td>
			</tr>
		</table>
	</td>
	</tr>
	<tr  style="height: 22px">
		<td align="left" valign="middle" class="buttonBar2">
	
		&nbsp;
		<c:choose >
			<c:when test="${'C' eq summary}">
				<b>Summary</b>&nbsp;&nbsp;|&nbsp;&nbsp;	
			</c:when>
			<c:when test="${'V' eq summary}">
				<html:link action="/PMmodule/QuatroClientSummary.do" name="actionParam" style="color:Navy;text-decoration:none;">Summary</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
			</c:when>
			<c:otherwise>
				&nbsp;
			</c:otherwise>
		</c:choose>
		<c:choose >
			<c:when test="${'C' eq history}">
				<b>History</b>&nbsp;&nbsp;|&nbsp;&nbsp;	
			</c:when>
			<c:when test="${'V' eq history}">
			<html:link action="/PMmodule/ClientHistory.do" name="actionParam" style="color:Navy;text-decoration:none;">History</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
			</c:when>
			<c:otherwise>
				&nbsp;
			</c:otherwise>
		</c:choose>
		<c:choose >
			<c:when test="${'C' eq intake}">
				<b>Intake</b>&nbsp;&nbsp;|&nbsp;&nbsp;	
			</c:when>
			<c:when test="${'V' eq intake}">
			<html:link action="/PMmodule/QuatroIntake.do" name="actionParam" style="color:Navy;text-decoration:none;">Intake</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
			</c:when>
			<c:otherwise>
				&nbsp;
			</c:otherwise>
		</c:choose>
		<c:choose >
			<c:when test="${'C' eq admission}">
				<b>Admission</b>&nbsp;&nbsp;|&nbsp;&nbsp;	
			</c:when>
			<c:when test="${'V' eq admission}">
			<html:link action="/PMmodule/QuatroAdmission.do" name="actionParam" style="color:Navy;text-decoration:none;">Admission</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
			</c:when>
			<c:otherwise>
				&nbsp;
			</c:otherwise>
		</c:choose>
		<c:choose >
			<c:when test="${'C' eq refer}">
				<b>Referral</b>&nbsp;&nbsp;|&nbsp;&nbsp;	
			</c:when>
			<c:when test="${'V' eq refer}">
			<html:link action="PMmodule/QuatroRefer.do" name="actionParam" style="color:Navy;text-decoration:none;"> Referral</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
			</c:when>
			<c:otherwise>
				&nbsp;
			</c:otherwise>
		</c:choose>
		<c:choose >
			<c:when test="${'C' eq discharge}">
				<b>Discharge</b>&nbsp;&nbsp;|&nbsp;&nbsp;	
			</c:when>
			<c:when test="${'V' eq discharge}">
			<html:link action="/PMmodule/QuatroDischarge.do" name="actionParam" style="color:Navy;text-decoration:none;">Discharge</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
			</c:when>
			<c:otherwise>
				&nbsp;
			</c:otherwise>
		</c:choose>
		<c:choose >
			<c:when test="${'C' eq restriction}">
				<b>Service Restriction</b>&nbsp;&nbsp;|&nbsp;&nbsp;	
			</c:when>
			<c:when test="${'V' eq restriction}">
			<html:link action="/PMmodule/QuatroServiceRestriction.do" name="actionParam" style="color:Navy;text-decoration:none;">Service Restriction</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
			</c:when>
			<c:otherwise>
				&nbsp;
			</c:otherwise>
		</c:choose>
		<c:choose >
			<c:when test="${'C' eq complaint}">
				<b>Complaint</b>&nbsp;&nbsp;|&nbsp;&nbsp;	
			</c:when>
			<c:when test="${'V' eq complaint}">
			<html:link action="/PMmodule/QuatroComplaint.do" name="actionParam" style="color:Navy;text-decoration:none;">Complaint</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		</c:when>
			<c:otherwise>
				&nbsp;
			</c:otherwise>
		</c:choose>
		<c:choose >
			<c:when test="${'C' eq consent}">
				<b>Consent</b>&nbsp;&nbsp;|&nbsp;&nbsp;	
			</c:when>
			<c:when test="${'V' eq consent}">
		 	   <html:link action="/PMmodule/QuatroConsent.do" name="actionParam" style="color:Navy;text-decoration:none;">Consent</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
			</c:when>
			<c:otherwise>
				&nbsp;
			</c:otherwise>
		</c:choose>
		<c:choose >
			<c:when test="${'C' eq case}">
				<b>Case Management</b>&nbsp;&nbsp;|&nbsp;&nbsp;	
			</c:when>
			<c:when test="${'V' eq case}">
			<html:link 	action="/CaseManagementView2.do" name="actionParam" style="color:Navy;text-decoration:none;">Case Management</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		</c:when>
			<c:otherwise>
				&nbsp;
			</c:otherwise>
		</c:choose>
		<c:choose >
			<c:when test="${'C' eq attachment}">
				<b>Attachment</b>&nbsp;&nbsp;|&nbsp;&nbsp;	
			</c:when>
			<c:when test="${'V' eq attachment}">
			<html:link 	action="/PMmodule/UploadFile.do" name="actionParam" style="color:Navy;text-decoration:none;">Attachment</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		</c:when>
			<c:otherwise>
				&nbsp;
			</c:otherwise>
		</c:choose>
		</td>
	</tr>
</table>		
</body>
</html:html>

