<%@ include file="/taglibs.jsp"%>


<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
	<tr>
		<th class="pageTitle" align="center"><span
			id="_ctl0_phBody_lblTitle" align="left">User Management</span></th>
	</tr>
	<tr>
		<td align="left" class="buttonBar"><html:link
			action="/PMmodule/Admin/SysAdmin.do"
			style="color:Navy;text-decoration:none;">
			<img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Close&nbsp;&nbsp;|</html:link>
			<html:link action="/PMmodule/Admin/UserManager.do?method=preNew"
			style="color:Navy;text-decoration:none;">
			<img border=0 src=<html:rewrite page="/images/New16.png"/> />&nbsp;New&nbsp;&nbsp;|</html:link>
			<html:link href="javascript:submitForm('search')"
			style="color:Navy;text-decoration:none;">
			<img border=0 src=<html:rewrite page="/images/search16.gif"/> />&nbsp;Search&nbsp;&nbsp;|</html:link>
			<html:link href="javascript:resetForm()"
			style="color:Navy;text-decoration:none;">
			<img border=0 src=<html:rewrite page="/images/searchreset.gif"/> />&nbsp;Reset&nbsp;&nbsp;|</html:link>
		</td>

	</tr>
	<tr>
		<td align="left"></td>
	</tr>
	<tr>
		<td height="100%"><html:form action="/PMmodule/Admin/UserSearch">
			<input type="hidden" name="method" value="search" />

			<div class="h4">
			<h4>Search user by entering search criteria below</h4>
			</div>
			<div class="axial">
			<table border="0" cellspacing="2" cellpadding="3">
				<tr>
					<th><bean-el:message key="UserSearch.userName" bundle="pmm" /></th>
					<td><html:text property="criteria.userName" size="20" /></td>
				</tr>
				<tr>
					<th><bean-el:message key="UserSearch.firstName" bundle="pmm" /></th>
					<td><html:text property="criteria.firstName" size="20" /></td>
				</tr>

				<tr>
					<th><bean-el:message key="UserSearch.lastName" bundle="pmm" />
					</th>
					<td><html:text property="criteria.lastName" size="20" /></td>
				</tr>

				<tr>
					<th><bean-el:message key="UserSearch.active" bundle="pmm" /></th>
					<td><html:select property="criteria.active">
						<html:option value="">Any</html:option>
						<html:option value="1">Yes</html:option>
						<html:option value="0">No</html:option>
					</html:select></td>
				</tr>
				
				<tr>
					<th><bean-el:message key="UserSearch.roleName" bundle="pmm" /></th>
					<td><html-el:select property="criteria.roleName">
						<html-el:option value="">Any</html-el:option>
						<c:forEach var="role" items="${roles}">
							<html-el:option value="${role.roleName}">
								<c:out value="${role.description}" />
							</html-el:option>
						</c:forEach>
					</html-el:select></td>
				</tr>
 

			</table>

			</div>

		</html:form> 
		
		<logic:present name="secuserroles">
		<logic:notEmpty name="secuserroles">
			<div
				style="color: Black; background-color: White; border-width: 1px; border-style: Ridge;
                    height: 100%; width: 100%; overflow: auto;">

			<table width="100%" cellpadding="0" cellspacing="0" border="10">
				<tr>
					<td align="left" class="clsHomePageHeader">
					<h2>User List</h2>
					</td>
				</tr>

			</table>

			<display:table class="simple" cellspacing="2" cellpadding="3"
				id="user" name="secuserroles" export="false" pagesize="0"
				requestURI="/PMmodule/Admin/UserSearch.do">


				<display:column sortable="true" title="User ID">
					<a
						href="<html:rewrite action="/PMmodule/Admin/UserManager.do"/>?method=profile&providerNo=<c:out value="${user.providerNo}" />">
					<c:out value="${user.userName}" /> </a>
				</display:column>

				<display:column property="fullName" sortable="true" title="Name" />

				<display:column title="">
					<a
						href="<html:rewrite action="/PMmodule/Admin/UserManager.do"/>?method=edit&providerNo=<c:out value="${user.providerNo}" />">
					Edit </a>
				</display:column>

				<display:column title="">
					<a
						href="<html:rewrite action="/PMmodule/Admin/UserManager.do"/>?method=addRole&providerNo=<c:out value="${user.providerNo}" />">
					Assign ORG/Role </a>
				</display:column>

			</display:table></div>
		</logic:notEmpty>
		<logic:empty name="secuserroles">
		<br />No recoed found! Please try again.
		</logic:empty>
		</logic:present>
		</td>
	</tr>
</table>










<script language="javascript" type="text/javascript">
<!--

function submitForm(mthd){

	document.forms[0].method.value=mthd;
	document.forms[0].submit();

}

function resetForm(){

	document.forms[0].reset();

}



//-->
</script>
