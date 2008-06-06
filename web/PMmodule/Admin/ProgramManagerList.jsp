<!-- 

Source:web/PMmodule/Admin/ProgramManagerList.jsp 

-->
<% String a ="asdf"; %>

<%@ include file="/taglibs.jsp"%>


<table width="100%" height="100%" cellpadding="0px" cellspacing="0px"
	border="0">
	<tr>
		<th class="pageTitle" align="center">Program Management - Program
		Search</th>
	</tr>
	<tr>
		<td align="left" class="buttonBar"><html:link
			href="javascript:submitForm('add');"
			style="color:Navy;text-decoration:none;">
			<img border="0" src="<html:rewrite page="/images/New16.png"/>" />&nbsp;New Program&nbsp;&nbsp;</html:link>
		<html:link href="javascript:submitForm('list');"
			style="color:Navy;text-decoration:none;">
			<img border="0" src="<html:rewrite page="/images/search16.gif"/>" />&nbsp;Search&nbsp;&nbsp;</html:link>
		<html:link href="javascript:resetForm();"
			style="color:Navy;text-decoration:none;">
			<img border="0" src="<html:rewrite page="/images/searchreset.gif"/>" />&nbsp;Reset&nbsp;&nbsp;</html:link>
		</td>
	</tr>
	<tr>
		<td>
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


		<br />

		<html:form action="/PMmodule/ProgramManager.do">
			<html:hidden property="method" />
			<div class="app">
			<table cellspacing="1" cellpadding="4" width="100%" bgcolor="#E8E8E8">
				<tr>
					<th align="left" width="20%">Status:</th>
					<th align="left" width="80%"><html:select property="searchStatus">
						<html:option value="Any" />
						<html:option value="active" />
						<html:option value="inactive" />
					</html:select></th>
				</tr>
				<tr>
					<th align="left" width="20%">Type:</th>
					<th align="left" width="80%"><html-el:select property="searchType">
						<html:option value="Any" />
						<c:forEach var="pt" items="${programTypeLst}">
							<html-el:option value="${pt.code}">
								<c:out value="${pt.description}" />
							</html-el:option>
						</c:forEach>
					</html-el:select></th>
				</tr>
				<tr>
					<th align="left" width="20%">Facility:</th>
					<th align="left" width="80%"><html-el:select property="searchFacilityId">
						<html-el:option value="0">Any</html-el:option>
						<c:forEach var="facility" items="${facilities}">
							<html-el:option value="${facility.id}">
								<c:out value="${facility.name}" />
							</html-el:option>
						</c:forEach>
					</html-el:select></th>
				</tr>

			</table>
			</div>
		</html:form> <br />
		</td>
	</tr>
	<tr>
		<td height="100%">
		<div
			style="color: Black; background-color: White; border-width: 1px; border-style: Ridge;
                    height: 100%; width: 100%; overflow: auto;">

		<!-- Page Content Start -->

	
			<br />
			<div class="tabs" id="tabs">
			<table cellpadding="3" cellspacing="0" border="0">
				<tr>
					<th title="Programs">Programs</th>
				</tr>
			</table>
			</div>
	
	
			<display:table class="simple" cellspacing="2" cellpadding="3"
				id="program" name="programs" export="false" pagesize="0"
				requestURI="/PMmodule/ProgramManager.do">
				<display:setProperty name="paging.banner.placement" value="bottom" />
				<display:setProperty name="paging.banner.item_name" value="program" />
				<display:setProperty name="paging.banner.items_name" value="programs" />
				<display:setProperty name="basic.msg.empty_list"
					value="No programs found." />
	
				<display:column sortable="false" title="">
					<a	onclick="return ConfirmDelete('<c:out value="${program.nameJs}"/>')"
						href="<html:rewrite action="/PMmodule/ProgramManager.do"/>?method=delete&id=<c:out value="${program.id}"/>&name=<c:out value="${program.name}"/>">
					Delete </a>
				</display:column>
	
				<display:column sortable="false" title="">
						<a	href="<html:rewrite action="/PMmodule/ProgramManager.do"/>?method=edit&id=<c:out value="${program.id}" />">
							Edit </a>
				</display:column>
					
				<display:column sortable="true" title="Name" sortName="program" sortProperty="name">
					<a
						href="<html:rewrite action="/PMmodule/ProgramManagerView.do"/>?id=<c:out value="${program.id}" />">
					<c:out value="${program.name}" /> </a>
				</display:column>
				<display:column property="descr" sortable="true" title="Description" />
				<display:column property="type" sortable="true" title="Type" />
				<display:column property="programStatus" sortable="true"
					title="Status" />
				<display:column property="facilityDesc" sortable="true" title="Facility" />
				
				<display:column sortable="true" title="Occupancy" sortName="program" sortProperty="numOfMembers">
					<c:out value="${program.numOfMembers}" />
				</display:column>
				<display:column sortable="true" title="Queue" sortName="program" sortProperty="queueSize">
					<c:out value="${program.queueSize}" />
				</display:column>
				
				<display:column sortable="true" title="Capacity (actual)" sortName="program" sortProperty="capacity_actual">
					<c:out value="${program.capacity_actual}" />
				</display:column>
				<display:column sortable="true" title="Capacity (funding)" sortName="program" sortProperty="capacity_funding">
					<c:out value="${program.capacity_funding}" />
				</display:column>
			</display:table>  
			
			<script>
				function ConfirmDelete(name)
				{
					if(confirm("Are you sure you want to delete " + name + " ?")) {
						return true;
					}
					return false;
				}
				function submitForm(method)
				{
					document.programManagerForm.method.value=method;
					document.programManagerForm.submit()
				}
				function resetForm()
				{
					document.getElementsByName("searchStatus")[0].value="Any";
					document.getElementsByName("searchType")[0].value="Any";
					document.getElementsByName("searchFacilityId")[0].value="0";
					document.programManagerForm.submit()
				}
			</script> 
		
		<!-- Page Content End -->

		</div>
		</td>
	</tr>

</table>
