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
</table>		
</body>
</html:html>

