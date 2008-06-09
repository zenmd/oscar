<%@ include file="/taglibs.jsp"%>

<script type="text/javascript">
	function validateRequiredField(fieldId, fieldName, maxLength)
	{
		var field=document.getElementById(fieldId);
	
		if (field.value==null || field.value=='')
		{
			alert('The field '+fieldName+' is required.');
			return(false);
		}
		
		if (field.value.length > maxLength)
		{
			alert('The value you entered for '+fieldName+' is too long, maximum length allowed is '+maxLength+' characters.');
			return(false);
		}
		
		return(true);
	}


	function submitForm(){
		
		var isOk = false;
		isOk = validateRequiredField('facilityName', 'Facility Name', 32);
		if (isOk) isOk = validateRequiredField('facilityDesc', 'Facility Description', 70);
		if(isOk){
			document.forms[0].submit();
		}
	}
</script>
<!-- don't close in 1 statement, will break IE7 -->

<html:form action="/PMmodule/FacilityManager.do">
	<input type="hidden" name="method" value="save" />


	<table cellpadding="0" cellspacing="0" border="0" width="100%"
		height="100%">

		<!-- Title -->
		<tr>
			<th class="pageTitle" align="center">Facility management</th>
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
						<html:link
						href="javascript:submitForm();"
						style="color:Navy;text-decoration:none;">
						<img border="0" src="<html:rewrite page="/images/Save16.png"/>" />&nbsp;Save&nbsp;&nbsp;</html:link>
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





					<table width="100%" height="100%" cellpadding="0px"
						cellspacing="0px">

						<tr>
							<td>
							<table width="100%" border="1" cellspacing="2" cellpadding="3">
								<tr class="b">
									<td width="20%">Facility Id:</td>
									<td><c:out value="${facility.id}" /></td>

								</tr>
								<tr class="b">
									<td width="20%">Name: *</td>
									<td><html:text property="facility.name" size="32"
										maxlength="32" styleId="facilityName" /></td>
								</tr>
								<tr class="b">
									<td width="20%">Description: *</td>
									<td><html:text property="facility.description" size="70"
										maxlength="70" styleId="facilityDesc" /></td>
								</tr>
								<tr class="b">
									<td width="20%">HIC:</td>
									<td><html:checkbox property="facility.hic" /></td>
								</tr>
								<tr class="b">
									<td width="20%">Primary Contact Name:</td>
									<td><html:text property="facility.contactName" /></td>
								</tr>
								<tr class="b">
									<td width="20%">Primary Contact Email:</td>
									<td><html:text property="facility.contactEmail" /></td>
								</tr>
								<tr class="b">
									<td width="20%">Primary Contact Phone:</td>
									<td><html:text property="facility.contactPhone" /></td>
								</tr>
								<%
									Integer orgId = (Integer) request.getAttribute("orgId");
									Integer sectorId = (Integer) request.getAttribute("sectorID");
								%>
								<tr class="b">
									<td width="20%">Shelter:</td>
									<td><select name="facility.orgId">
										<option value="0">&nbsp;</option>
										<c:forEach var="org" items="${orgList}">
											<c:choose>
												<c:when test="${orgId == org.code }">
													<option value="<c:out value="${org.code}"/>" selected><c:out
														value="${org.description}" /></option>
												</c:when>
												<c:otherwise>
													<option value="<c:out value="${org.code}"/>"><c:out
														value="${org.description}" /></option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select></td>
								</tr>
								<tr class="b">
									<td width="20%">Sector:</td>
									<td><select name="facility.sectorId">
										<option value="0">&nbsp;</option>
										<c:forEach var="sector" items="${sectorList}">
											<c:choose>
												<c:when test="${sectorId == sector.code }">
													<option value="<c:out value="${sector.code}"/>" selected><c:out
														value="${sector.description}" /></option>
												</c:when>
												<c:otherwise>
													<option value="<c:out value="${sector.code}"/>"><c:out
														value="${sector.description}" /></option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select></td>
								</tr>
								<tr class="b">
									<td width="20%">Active:</td>
									<td><html:checkbox property="facility.active" /></td>
								</tr>
								
							</table>
							</td>
						</tr>
					</table>

					</div>
					</td>
				</tr>
			</table>



			</td>
		</tr>
		<!-- body end -->
	</table>

</html:form>
