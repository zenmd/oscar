<!-- 

Source: web/PMmodule/Admin/ProgramView/incidentList.jsp 

-->

<%@ include file="/taglibs.jsp"%>


<table width="100%" cellpadding="0px" cellspacing="0px" height="100%"
	border="0">
	<!-- submenu -->
	<tr height="18px">
		<td align="left" class="buttonBar">
			<html:link
			href="javascript:editIncident('0','new');"
			style="color:Navy;text-decoration:none;">
			<img border="0" src="<html:rewrite page="/images/New16.png"/>" />&nbsp;New&nbsp;&nbsp;</html:link>
			<html:link
			href="javascript:searchIncident();"
			style="color:Navy;text-decoration:none;">
			<img border="0" src="<html:rewrite page="/images/search16.gif"/>" />&nbsp;Search&nbsp;&nbsp;</html:link>
			<html:link href="javascript:resetForm()"
			style="color:Navy;text-decoration:none;">
			<img border=0 src=<html:rewrite page="/images/searchreset.gif"/> />&nbsp;Reset&nbsp;&nbsp;</html:link>
			<a href="javascript:clickTab('General');"
			style="color:Navy;text-decoration:none;">
			<img border="0" src="<html:rewrite page="/images/Back16.png"/>" />&nbsp;Close&nbsp;&nbsp;</a>
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
				<table border="0" cellspacing="2" cellpadding="3">
					<tr>
						<th>Client ID:</th>
						<td><html:text property="incidentForm.clientId" size="20" maxlength="10"/></td>
					</tr>
					<tr>
						<th>First or Last Name:</th>
						<td><html:text property="incidentForm.clientName" size="20" maxlength="30"/></td>
					</tr>
					<tr>
						<th>Incident Date:</th>
						<td><quatro:datePickerTag property="incidentForm.incDateStr"
											width="90%" openerForm="programManagerViewForm" /></td>
					</tr>
				
				</table>
			</div>
			
			

		</td>
	</tr>



	<tr>
		<td height="100%">
		
		<div
			style="color: Black; background-color: White; border-width: 1px; border-style: Ridge;
                    height: 100%; width: 100%; overflow: auto;">
  
			 	
			
			<br />
			<div class="tabs" id="tabs">
			<table cellpadding="3" cellspacing="0" border="0">
				<tr>
					<th title="Incidents">Incidents</th>
				</tr>
			</table>
			</div>
			
			
			<display:table class="simple" cellspacing="2" cellpadding="3"
				id="incident" name="incidents" export="false" pagesize="0"
				requestURI="/PMmodule/ProgramManagerView.do">
				<display:setProperty name="paging.banner.placement" value="bottom" />
				<display:setProperty name="basic.msg.empty_list"
					value="No incidents currently in place for this program." />
			
				<display:column sortable="true" title="Incident ID">
			
			
					<a href="javascript:void(0)"
						onclick="javascript:editIncident('<c:out value="${incident.id}" />','edit');return false;">
					<c:out value="${incident.id}" /></a>
			
				</display:column>
				
				<display:column property="incidentDatex" sortable="true"
					title="Incident Date" />
			
			</display:table>

 
 		</div>

		</td>
	</tr>
</table>