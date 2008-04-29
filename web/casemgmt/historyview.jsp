<!-- 
/*
* 
* Copyright (c) 2001-2002. Centre for Research on Inner City Health, St. Michael's Hospital, Toronto. All Rights Reserved. *
* This software is published under the GPL GNU General Public License. 
* This program is free software; you can redistribute it and/or 
* modify it under the terms of the GNU General Public License 
* as published by the Free Software Foundation; either version 2 
* of the License, or (at your option) any later version. * 
* This program is distributed in the hope that it will be useful, 
* but WITHOUT ANY WARRANTY; without even the implied warranty of 
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
* GNU General Public License for more details. * * You should have received a copy of the GNU General Public License 
* along with this program; if not, write to the Free Software 
* Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA. * 
* 
* <OSCAR TEAM>
* 
* This software was written for 
* Centre for Research on Inner City Health, St. Michael's Hospital, 
* Toronto, Ontario, Canada 
*/
 -->

<%@ include file="../taglibs.jsp"%>

<html>
<head>
<title>Note History</title>
<c:set var="ctx" value="${pageContext.request.contextPath}"
	scope="request" />
<link rel="stylesheet" href="<c:out value="${ctx}"/>/css/casemgmt.css"
	type="text/css">

</head>
<body bgcolor="#eeeeff">
<nested:form action="/CaseManagementEntry2">
	<br>
	<table style="width: 100%">
		<tr>
			<th class="pageTitle">Archived Note Update History</th>
		</tr>
		<tr>
			<td align="left" class="buttonBar"><html:link
				action="/CaseManagementView2.do"
				style="color:Navy;text-decoration:none;">
				<img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Close&nbsp;&nbsp;|
			</html:link></td>
		</tr>
	</table>

	<table class="simple">
		<tr>
			<td>Client name</td>
			<td><logic:notEmpty name="demoName" scope="request">
				<c:out value="${requestScope.demoName}" />
			</logic:notEmpty> <logic:empty name="demoName" scope="request">
				<c:out value="${param.demoName}" />
			</logic:empty></td>
		</tr>
		<tr>
			<th>Age</th>
			<td><logic:notEmpty name="demoName" scope="request">
				<c:out value="${requestScope.demoAge}" />
			</logic:notEmpty> <logic:empty name="demoName" scope="request">
				<c:out value="${param.demoAge}" />
			</logic:empty></td>
		</tr>
		<tr>
			<th>DOB</th>
			<td><logic:notEmpty name="demoName" scope="request">
				<c:out value="${requestScope.demoDOB}" />
			</logic:notEmpty> <logic:empty name="demoName" scope="request">
				<c:out value="${param.demoDOB}" />
			</logic:empty></td>
		</tr>
	</table>
	<br>
	<table width="400" border="0">
		<tr>
			<td class="fieldValue"><textarea name="caseNote_history"
				cols="107" rows="29" wrap="soft"><nested:write
				property="caseNote_history" /></textarea></td>
		</tr>
		<br>

		</nested:form>
</body>
</html>
