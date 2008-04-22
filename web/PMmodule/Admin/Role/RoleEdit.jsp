<%@ include file="/taglibs.jsp"%>


<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
	<tr>
		<th class="pageTitle" align="center"><span
			id="_ctl0_phBody_lblTitle" align="left">Role Management</span></th>
	</tr>
	<tr>
		<td align="left" class="buttonBar"><html:link
			action="/PMmodule/Admin/SysAdmin.do"
			style="color:Navy;text-decoration:none;">
			<img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Close&nbsp;&nbsp;|</html:link>
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

		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td align="left" class="clsHomePageHeader" colspan="5">
				<h2>Edit Role</h2>
				</td>
			</tr>

		</table>

		<html:form action="/PMmodule/Admin/RoleManager" method="post">
			<html:hidden property="method" value="save" />
			<table>
				<tr>
					<td>Role No.:</td>
					<td><html:text property="roleNo" readonly="true" size="50" /></td>
				</tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td>Role Name:</td>
					<td><html:text property="roleName" size="50" /></td>
				</tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td>Description:</td>
					<td><html:text property="description" size="50" /></td>
				</tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2"><html:submit value="Submit" property="submit" />
					<html:button value="Cancel" property="cancels" 
						onclick="javascript:this.form.action=/PMmodule/Admin/RoleManager.do?method=list;this.form.submit();" /></td>
				</tr>
			</table>




		</html:form></div>
		</td>
	</tr>
</table>

