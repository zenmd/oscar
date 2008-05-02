<%@ include file="/taglibs.jsp"%>

<html:form action="/PMmodule/Admin/UserManager" method="post">
			<html:hidden property="method" value="search" />
			
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
		
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td rowspan="3" >
					<a	href="<html:rewrite action="/PMmodule/Admin/UserManager.do"/>?method=search">Search</a>
				</td>
				<td >UserID/Name:</td>
				<td ><html:text property="userName" /></td>
								
			</tr>
			<tr>
				<td >Role:</td>
				<td >
				
					<table cellpadding="0" style="border:0px;" cellspacing="0"	width="100%">
						<tr>
							<td style="border:0px;" width="1px"><input type="text"
								style="width:100%;"
								name="roleNmae" readonly></td>
							<td style="border:0px;" width="100%"><input
								style="width:100%;" type="text" 
								name="role_description"
								readonly></td>
							<td style="border:0px;" width="35px"><a  
								onclick="showLookup('ROL', '', '', 'secuserForm','roleName','role_description', true, '<c:out value="${ctx}"/>');"><img
								src="/QuatroShelter/images/microsoftsearch.gif"></a></td>
						</tr>
					</table>
				
				
				
				</td>
			</tr>
			<tr>
				<td >Active:</td>
				<td ><html:checkbox property="status" /></td>
			</tr>

		</table>


		<display:table class="simple" cellspacing="2" cellpadding="3"
			id="user" name="secuserroles" export="false" pagesize="0"
			requestURI="/PMmodule/Admin/UserManager.do" >


			<display:column sortable="true" title="User ID" >
				<a
					href="<html:rewrite action="/PMmodule/Admin/UserManager.do"/>?method=profile&providerNo=<c:out value="${user.providerNo}" />">
				<c:out value="${user.userName}" /> </a>
			</display:column>
			
			<display:column property="fullName" sortable="true" title="Name" />
				
			<display:column title="">
				<a	href="<html:rewrite action="/PMmodule/Admin/UserManager.do"/>?method=edit&providerNo=<c:out value="${user.providerNo}" />">
				Edit </a>
			</display:column>
			
			<display:column title="">
				<a	href="<html:rewrite action="/PMmodule/Admin/UserManager.do"/>?method=addRole&providerNo=<c:out value="${user.providerNo}" />">
				Assign ORG/Role </a>
			</display:column>

		</display:table></div>
		</td>
	</tr>
</table>

</html:form>
