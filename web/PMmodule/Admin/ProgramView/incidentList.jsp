<%@ include file="/taglibs.jsp"%>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<td align="left" class="buttonBar"><html:link
			href="javascript:editIncident('0','new');"
			style="color:Navy;text-decoration:none;">
			<img border="0" src="<html:rewrite page="/images/New16.png"/>" />&nbsp;New&nbsp;&nbsp;</html:link>
		</td>
	</tr>

	<tr>
		<td align="left" class="message">
		<logic:messagesPresent
			message="true">
			<html:messages id="message" message="true" bundle="pmm">
				<c:out escapeXml="false" value="${message}" />
			</html:messages>
		</logic:messagesPresent></td>
	</tr>
</table>

<div class="tabs" id="tabs">
<table cellpadding="3" cellspacing="0" border="0">
	<tr>
		<th title="Incidents">Incidents</th>
	</tr>
</table>
</div>

<display:table class="simple" cellspacing="2" cellpadding="3"
	id="incident" name="incidents" export="false" pagesize="0"
	requestURI="/PMmodule/ProgramManager.do">
	<display:setProperty name="paging.banner.placement" value="bottom" />
	<display:setProperty name="basic.msg.empty_list"
		value="No incidents currently in place for this program." />

	<display:column sortable="true" title="Incident ID">


		<a href="javascript:void(0)"
			onclick="javascript:editIncident('<c:out value="${incident.id}" />','edit');return false;">
		<c:out value="${incident.id}" /></a>

	</display:column>
	<display:column property="id" sortable="true" title="Id" />
	<display:column property="incidentDatex" sortable="true"
		title="Incident Date" />

</display:table>

