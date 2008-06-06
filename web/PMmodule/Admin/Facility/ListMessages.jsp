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
			<th class="pageTitle" align="center">Facility management - Messages</th>
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




					
					<br />
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

