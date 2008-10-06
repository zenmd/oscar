<!-- 

Source: web/PMmodule/Admin/ProgramView/incidentList.jsp 

-->

<%@ include file="/taglibs.jsp"%>
<%@page import="com.quatro.common.KeyConstants;"%>

<table width="100%" cellpadding="0px" cellspacing="0px" height="100%"
	border="0">
	<!-- submenu -->
	<tr>
		<td align="left" class="buttonBar2">
			<html:link	action="/PMmodule/ProgramManager.do"	style="color:Navy;text-decoration:none;">&nbsp;
				<img style="vertical-align: middle" border="0" src="<html:rewrite page="/images/Back16.png"/>" />&nbsp;Back to Programs&nbsp;
			</html:link>
			<c:if test="${programActive}">			
			<security:oscarSec objectName="<%=KeyConstants.FUN_PROGRAM_INCIDENT %>" rights="<%=KeyConstants.ACCESS_WRITE %>">
				&nbsp;|&nbsp;<html:link	href="javascript:submitForm('new');"	style="color:Navy;text-decoration:none;">&nbsp;
					<img style="vertical-align: middle" border="0" src="<html:rewrite page="/images/New16.png"/>" />&nbsp;New Incident&nbsp;</html:link>
			</security:oscarSec>
			</c:if>
			&nbsp;|&nbsp;<html:link	href="javascript:searchIncident();"	style="color:Navy;text-decoration:none;">&nbsp;
				<img style="vertical-align: middle" border="0" src="<html:rewrite page="/images/search16.gif"/>" />&nbsp;Search&nbsp;</html:link>
			&nbsp;|&nbsp;<html:link href="javascript:resetForm()"	style="color:Navy;text-decoration:none;">&nbsp;
			<img style="vertical-align: middle" border=0 src=<html:rewrite page="/images/searchreset.gif"/> />&nbsp;Reset&nbsp;</html:link>
		</td>
	</tr>
	
	<!-- messages -->
	<tr>
		<td align="left" class="message">
			<logic:messagesPresent message="true">
				<br />
				<html:messages id="message" message="true" bundle="pmm">
					<c:out escapeXml="false" value="${message}" />
				</html:messages>
				<br />
			</logic:messagesPresent>
		</td>
	</tr>


	<tr>
		<td>
			<div class="axial">
				<table border="0" cellspacing="1" cellpadding="1" width="100%">
					<tr>
						<th width="20%" align="right">Client ID</th>
						<td align="left" width="80%"><html:text property="incidentForm.clientId" size="20" maxlength="10"/></td>
					</tr>
					<tr>
						<th width="20%" align="right">First or Last Name</th>
						<td align="left" width="80%"><html:text property="incidentForm.clientName" size="20" maxlength="30"/></td>
					</tr>
					<tr>
						<th width="20%" align="right">Incident Date</th>
						<td align="left" width="80%"><quatro:datePickerTag property="incidentForm.incDateStr"
											width="20%" openerForm="programManagerViewForm" /></td>
					</tr>
				
				</table>
			</div>
		</td>
	</tr>

	<tr>
		<td>
			<div class="tabs" id="tabs">
			<br />
			<table cellpadding="3" cellspacing="0" border="0">
				<tr>
					<th title="Incidents">Incidents</th>
				</tr>
			</table>
			</div>
		</td>
	</tr>
	<tr height="100%">
		<td>
			<div style="color: Black; background-color: White; border-width: 1px; border-style: Ridge;  height: 100%; width: 100%; overflow: auto;" id="scrollBar">
  
			<display:table class="simple" cellspacing="2" cellpadding="3"	id="incident" name="incidents" export="false" pagesize="0"
				requestURI="/PMmodule/ProgramManagerView.do">
				<display:setProperty name="paging.banner.placement" value="bottom" />
				<display:setProperty name="basic.msg.empty_list"	value="No incidents currently in place for this program." />
			
				<display:column sortable="true" title="Incident ID">			
					<a href="javascript:void(0)" onclick="javascript:editIncident2('<c:out value="${incident.id}" />','view');return false;">
						<c:out value="${incident.id}" />
					</a>			
				</display:column>
				<display:column property="clientsNames"	title="Clients Involved" />				
				<display:column property="incidentDatex" sortable="true"	title="Incident Date" />			
			</display:table>
 			</div>

		</td>
	</tr>
</table>