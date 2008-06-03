<%@ include file="/taglibs.jsp"%>
<% String s = "debug"; %>
<%@ page import="org.oscarehr.PMmodule.model.Facility"%>
<bean:define id="facility" name="facilityManagerForm"
	property="facility" />

<html:form action="/PMmodule/FacilityManager.do">
	<input type="hidden" name="method" value="save" />

	<table cellpadding="0" cellspacing="0" border="0" width="100%"
		height="100%">

		<!-- Title -->
		<tr>
			<th class="pageTitle" align="center">Facility management - Facility Details</th>
		</tr>

		<!-- body start -->
		<tr>
			<td height="100%">

			<table width="100%" cellpadding="0px" cellspacing="0px" height="100%"
				border="0">
				<!-- submenu -->
				<tr>
					<td align="left" class="buttonBar"><html:link
						action="/PMmodule/FacilityManager.do?method=list"
						style="color:Navy;text-decoration:none;">
						<img border="0" src="<html:rewrite page="/images/Back16.png"/>" />&nbsp;Close&nbsp;&nbsp;</html:link>
					</td>
				</tr>

				<!-- messages -->
				<tr>
					<td align="left" class="message"><logic:messagesPresent
						message="true">
						<br />
						<html:messages id="message" message="true" bundle="pmm">
							<c:out escapeXml="false" value="${message}" />
						</html:messages>
						<br />
					</logic:messagesPresent></td>
				</tr>

				<tr>
					<td height="100%">
					<div
						style="color: Black; background-color: White; border-width: 1px; border-style: Ridge;
				                    height: 100%; width: 100%; overflow: auto;">




					<table width="100%" cellpadding="0px" cellspacing="0px">

						<tr>
							<td>
							<table width="100%" border="1" cellspacing="2" cellpadding="3">
								<tr class="b">
									<td width="20%">Facility Id:</td>
									<td><c:out value="${requestScope.id}" /></td>
								</tr>
								<tr class="b">
									<td width="20%">Name:</td>
									<td><c:out
										value="${requestScope.facilityManagerForm.facility.name}" /></td>
								</tr>
								<tr class="b">
									<td width="20%">Description:</td>
									<td><c:out
										value="${facilityManagerForm.facility.description}" /></td>
								</tr>
								<tr class="b">
									<td width="20%">HIC:</td>
									<td><c:out value="${facilityManagerForm.facility.hic}" /></td>
								</tr>
								<tr class="b">
									<td width="20%">Primary Contact Name:</td>
									<td><c:out
										value="${facilityManagerForm.facility.contactName}" /></td>
								</tr>
								<tr class="b">
									<td width="20%">Primary Contact Email:</td>
									<td><c:out
										value="${facilityManagerForm.facility.contactEmail}" /></td>
								</tr>
								<tr class="b">
									<td width="20%">Primary Contact Phone:</td>
									<td><c:out
										value="${facilityManagerForm.facility.contactPhone}" /></td>
								</tr>


							</table>
							</td>
						</tr>
					</table>
					<br />
					<div class="tabs" id="tabs">
					<table cellpadding="3" cellspacing="0" border="0">
						<tr>
							<th title="Associated programs">Associated programs</th>
						</tr>
					</table>
					</div>
					<logic:empty name="associatedPrograms">No programs.<br /></logic:empty>  
					<logic:notEmpty name="associatedPrograms">
						<display:table class="simple" cellspacing="2" cellpadding="3"
							id="program" name="associatedPrograms" export="false"
							requestURI="/PMmodule/FacilityManager.do">
							<display:setProperty name="basic.msg.empty_list"
								value="No programs." />
	
							<logic:equal name="program" property="facilityId"
								value="<%=((Facility)facility).getId().toString()%>">
								<display:column sortable="true" sortProperty="name"
									title="Program Name">
									<a
										href="<html:rewrite action="/PMmodule/ProgramManagerView"/>?id=<c:out value="${program.id}"/>"><c:out
										value="${program.name}" /></a>
								</display:column>
							</logic:equal>
							<logic:notEqual name="program" property="facilityId"
								value="<%=((Facility)facility).getId().toString()%>">
								<display:column sortable="true" sortProperty="name"
									title="Program Name">
									<c:out value="${program.name}" />
								</display:column>
							</logic:notEqual>
	
							<display:column property="type" sortable="true"
								title="Program Type" />
							<display:column property="queueSize" sortable="true"
								title="Clients in Queue" />
						</display:table> 
					</logic:notEmpty>
					<br>
					
					<div class="tabs" id="tabs">
					<table cellpadding="3" cellspacing="0" border="0">
						<tr>
							<th title="Facility Messages">Messages</th>
						</tr>
					</table>
					</div>
					<br>
					This table displays client automatic discharges from this facility
					from the past seven days. An automatic discharge occurs when the
					client is admitted to another facility while still admitted in this
					facility.

					<table width="100%" border="1" cellspacing="2" cellpadding="3" class="simple" >
						<thead>
							<tr>
								<th>Name</th>
								<th>Client DOB</th>
								<th>Bed Program</th>
								<th>Discharge Date/Time</th>
							</tr>
						</thead>
						<c:forEach var="client" items="${associatedClients}">

							<%
							String styleColor = "";
							%>
							<c:if test="${client.inOneDay}">
								<%
								styleColor = "style=\"color:red;\"";
								%>
							</c:if>
							<tr class="b" <%=styleColor%>>
								<td><c:out value="${client.name}" /></td>
								<td><c:out value="${client.dob}" /></td>
								<td><c:out value="${client.programName}" /></td>
								<td><c:out value="${client.dischargeDate}" /></td>
							</tr>

						</c:forEach>
					</table>


					<br>
					Automatic discharges in the past 24 hours appear red.
					
					</div>
					</td>
				</tr>
			</table>



			</td>
		</tr>
		<!-- body end -->
	</table>
</html:form>

