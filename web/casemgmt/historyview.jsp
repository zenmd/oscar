
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
			<td class="simple" style="background: lavender"><%@ include file="/PMmodule/ClientInfo.jsp" %></td>
		</tr>
		<tr>
			<td align="left" class="buttonBar"><html:link
				action="/CaseManagementView2.do"  paramId="clientId" paramName="clientId"	style="color:Navy;text-decoration:none;">
				<img border=0 src=<html:rewrite page="/images/close16.png"/> />&nbsp;Close&nbsp;&nbsp;|
			</html:link></td>
		</tr>
	</table>	
	<table width="400" border="0">
		<tr>
			<td class="fieldValue">
			<div style="color: Black; background-color: White; border-style: ridge; border-width: 1px;
                        width: 100%; height: 100%; overflow: auto">
			<textarea name="caseNote_history"
				cols="107" rows="29" wrap="soft"><nested:write
				property="caseNote_history" /></textarea>
			</div>	
			</td>
		</tr>
		<br>

		</nested:form>
</body>
</html>
