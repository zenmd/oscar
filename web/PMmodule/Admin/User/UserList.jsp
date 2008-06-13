<!-- Source:web/PMmodule/Admin/User/UserList.jsp -->

<%@ include file="/taglibs.jsp"%>


<table width="100%" height="100%" cellpadding="0px" cellspacing="0px"
	border="1" bordercolor="red">
	<tr>
		<th class="pageTitle" align="center"><span
			id="_ctl0_phBody_lblTitle" align="left">User Management - User
		List</span></th>
	</tr>
	<tr height="18px">
		<td align="left" class="buttonBar"><html:link
			action="/PMmodule/Admin/SysAdmin.do"
			style="color:Navy;text-decoration:none;">
			<img border=0 src=<html:rewrite page="/images/close16t.png"/> />&nbsp;Close&nbsp;&nbsp;|</html:link>
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
		<td><br />
		<br />
		<html:form action="/PMmodule/Admin/UserSearch">
			<input type="hidden" name="method" value="search" />
			<div class="axial">
			<table border="0" cellspacing="2" cellpadding="3">
				<tr>
					<th><bean-el:message key="UserSearch.userName" bundle="pmm" /></th>
					<td><html:text property="criteria.userName" size="20" maxlength="30"/></td>
				</tr>
				<tr>
					<th><bean-el:message key="UserSearch.lastName" bundle="pmm" />
					</th>
					<td><html:text property="criteria.lastName" size="20" maxlength="30"/></td>
				</tr>
				<tr>
					<th><bean-el:message key="UserSearch.firstName" bundle="pmm" /></th>
					<td><html:text property="criteria.firstName" size="20" maxlength="30"/></td>
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

		</html:form></td>
	</tr>
	<tr style="height: 100%">
		<td>
		<div
			style="color: Black; background-color: White; border-width: 1px; border-style: Ridge;
                    height: 100%; width: 100%; overflow: auto;">

		<div class="tabs" id="tabs">
		<table cellpadding="3" cellspacing="0" border="0">
			<tr>
				<th title="Programs">Users</th>
			</tr>
		</table>
		</div>
		<display:table class="simple" cellspacing="2" cellpadding="3"
			id="user" name="secuserroles" export="false" pagesize="100"
			requestURI="/PMmodule/Admin/UserSearch.do">

			<display:setProperty name="basic.msg.empty_list"
				value="Please enter a filter and click search." />

			<display:column sortable="true" title="User ID" sortProperty="userName">
				<a
					href="<html:rewrite action="/PMmodule/Admin/UserManager.do"/>?method=edit&providerNo=<c:out value="${user.providerNo}" />">
				<c:out value="${user.userName}" /> </a>
			</display:column>

			<display:column sortable="true" title="Name" sortProperty="fullName">
				<a
					href="<html:rewrite action="/PMmodule/Admin/UserManager.do"/>?method=edit&providerNo=<c:out value="${user.providerNo}" />">
				<c:out value="${user.fullName}" /> </a>
			</display:column>
		</display:table></div>
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
		var form = document.forms[0];
		form.elements['criteria.userName'].value='';
		form.elements['criteria.firstName'].value='';
		form.elements['criteria.lastName'].value='';
		form.elements['criteria.active'].selectedIndex = 0;
		form.elements['criteria.roleName'].selectedIndex = 0;
}



//-->
</script>
