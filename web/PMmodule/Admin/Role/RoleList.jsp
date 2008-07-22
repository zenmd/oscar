<!-- Source:web/PMmodule/Admin/Role/RoleList.jsp -->

<%@ include file="/taglibs.jsp"%>


<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
	<tr>
		<th class="pageTitle" align="center"><span
			id="_ctl0_phBody_lblTitle" align="left">Role Management - Role List</span></th>
	</tr>
	<tr>
		<td align="left" class="buttonBar2"><html:link
			action="/PMmodule/Admin/SysAdmin.do"
			style="color:Navy;text-decoration:none;">
			<img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Close&nbsp;&nbsp;|</html:link>
		<html:link action="/PMmodule/Admin/RoleManager.do?method=preNew"
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
                    height: 100%; width: 100%; overflow: auto;" id="scrollBar">



		<div class="tabs" id="tabs">
			<table cellpadding="3" cellspacing="0" border="0">
				<tr>
					<th title="Programs">Roles</th>
				</tr>
			</table>
		</div>
		<display:table class="simple" cellspacing="2" cellpadding="3"
			id="role" name="roles" export="false" pagesize="0"
			requestURI="/PMmodule/Admin/RoleManager.do">

			<display:column sortable="true" title="Role" sortProperty="roleName">
				<a href="<html:rewrite action="/PMmodule/Admin/RoleManager.do"/>?method=edit&roleName=<c:out value="${role.roleName}" />">
				<c:out value="${role.roleName}" /> </a>
			</display:column>

			<display:column property="description" sortable="true"
				title="Description" />

		</display:table></div>
		</td>
	</tr>
</table>

