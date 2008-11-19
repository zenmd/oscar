<%@ include file="/taglibs.jsp" %>
<table border="0" style="width:100%">
	<tr>
	<td >
		<table width="100%" class="simple"  cellspacing="2" cellpadding="3">
			<tr>
			<td style="width: 15%" ><font><b>Client No.</b></font></td><td colspan="3"><font><b><c:out value="${client.demographicNo}" /></b></font></td>
			</tr>
			<tr>
				<td style="width: 10%"><font><b>Name</b></font></td>
				<td style="width: 30%"><font><b><c:out value="${client.formattedName}" /></b></font></td>
				<td style="width: 10%"><font><b>DOB </b></font></td>
				<td style="width: 20%"><font><b><c:out value="${client.dob}" /></b></font></td>
				<td style="width: 10%"><font><b>Age </b></font></td>
				<td style="width: 20%"><font><b><c:out value="${client.age}" /></b></font></td>
			</tr>
		</table>
	</td>
	</tr>
</table>		

