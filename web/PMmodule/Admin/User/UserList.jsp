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
		<html:link action="/PMmodule/Admin/User/UserEdit.jsp"
			style="color:Navy;text-decoration:none;">
			<img border=0 src=<html:rewrite page="/images/New16.png"/> />&nbsp;New&nbsp;&nbsp;|</html:link>
		</td>

	</tr>
	<tr>
		<td align="left"></td>
	</tr>
	<tr>
		<td height="100%">
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
			requestURI="/PMmodule/Admin/UserManager.do" >

			<display:column title="">
				<a
					href="<html:rewrite action="/PMmodule/Admin/UserManager.do"/>?method=edit&providerNo=<c:out value="${user.providerNo}" />">
				Edit </a>
			</display:column>

			<display:column title="">
				<input type="checkbox" name="chk"
					value="<c:out value="${user.providerNo}" />" />
			</display:column>

			<display:column sortable="true" title="Name">
				<a
					href="<html:rewrite action="/PMmodule/Admin/UserManager.do"/>?method=profile&providerNo=<c:out value="${user.providerNo}" />">
				<c:out value="${user.fullName}" /> </a>
			</display:column>

			<display:column property="userName" sortable="true" title="User ID" />

		</display:table></div>
		</td>
	</tr>
</table>

