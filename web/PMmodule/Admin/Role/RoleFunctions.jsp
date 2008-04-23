<%@ include file="/taglibs.jsp"%>


<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
	<tr>
		<th class="pageTitle" align="center"><span
			id="_ctl0_phBody_lblTitle" align="left">Role Management</span></th>
	</tr>
	<tr>
		<td align="left" class="buttonBar"><html:link
			action="/PMmodule/Admin/RoleManager.do"
			style="color:Navy;text-decoration:none;">
			<img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Back to Role List&nbsp;&nbsp;|</html:link>
		<html:link href="javascript:submitForm();"
			style="color:Navy;text-decoration:none;">
			<img border=0 src=<html:rewrite page="/images/Save16.png"/> />&nbsp;Save&nbsp;&nbsp;|</html:link>
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
				<h2>Add Functions to Role</h2>
				</td>
			</tr>


		</table>

		<html:form action="/PMmodule/Admin/RoleManager" method="post">
			<html:hidden property="method" value="save" />
			<table>

				<tr>
					<td>Role No.:</td>
					<td><html:text property="roleNo" readonly="true"
						style="border: none" /></td>
				</tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>

				<tr>
					<td>Role Name:</td>
					<td><html:text property="roleName" readonly="true"
						style="border: none" /></td>
				</tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td>Description:</td>
					<td><html:text property="description" readonly="true"
						style="border: none" /></td>
				</tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
				

			</table>

Functions:<bean:define id="functions" name="secroleForm"
						property="functions" type="com.quatro.model.LookupCodeValue" /> 
						<quatro:lookupTag
						name="functions" tableName="FUN" formProperty="secroleForm"
						codeProperty="code" bodyProperty="description" width="90%"
						codeWidth="1px" showCode="false" />
					

		</html:form></div>
		</td>
	</tr>
</table>

<script language="javascript" type="text/javascript">
<!--
function gotoRoleList(){
 	window.open("<c:out value='${ctx}'/>/PMmodule/Admin/RoleManager.do?method=list", "_self") ;
}
function submitForm(){
	//document.forms[0].method.value="save";
	//document.forms[0].submit();
}
//-->
</script>

