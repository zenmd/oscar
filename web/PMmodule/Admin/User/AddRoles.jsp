<!-- Source:web/PMmodule/Admin/User/AddRoles.jsp -->

<%@ include file="/taglibs.jsp"%>


<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
	<tr>
		<th class="pageTitle" align="center"><span
			id="lblTitle" align="left">User Management</span></th>
	</tr>
	<tr>
		<td align="left" class="buttonBar"><html:link
			href="javascript:submitForm('edit');"
			style="color:Navy;text-decoration:none;">
			<img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Back to User Profile&nbsp;&nbsp;|</html:link>
		<html:link href="javascript:submitForm('saveRoles');"
			onclick="javascript:getFunctionsList();"
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
			<tr><td align="left" class="message">
		      <logic:messagesPresent message="true">
		        <html:messages id="message" message="true" bundle="pmm"><c:out escapeXml="false" value="${message}" />
		        </html:messages> 
		      </logic:messagesPresent>
			</td></tr>
		</table>

		<html:form action="/PMmodule/Admin/UserManager" method="post">
			<html:hidden property="method" value="save" />
			<html:hidden property="providerNo" />
			<table>
				<tr>
					<td>User ID:</td>
					<td><html:text property="userName" readonly="true" /></td>
				</tr>
				<tr>
					<td>Last Name:</td>
					<td><html:text property="lastName" readonly="true" maxlength="30"/>First Name:<html:text property="firstName" readonly="true" maxlength="30"/></td>
				<tr>
					<td colspan="2">Org/Role Profile:</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td width="650px">
					<br />
					<TABLE align="center" class="simple" width="100%">
						<thead>
							<TR>
								<th align="center"><b>Select</b></th>
								<th align="center"><b>Organization</b></TH>
								<TH align="center"><b>Role</b></TH>
							</TR>
						</thead>

						<logic:iterate id="secUserRole" name="secuserForm"
							property="secUserRoleLst" indexId="rIndex">
							<TR>
								<TD align="center" width="50px"><input type="checkbox"
									name="p<%=String.valueOf(rIndex)%>" value="" /> <input
									type="hidden" name="lineno" value="<%=String.valueOf(rIndex)%>" /></TD>

								<TD width="350px">
									<table cellpadding="0" style="border:0px;" cellspacing="0"
										width="100%">
										<tr>
											<td style="border:0px;" width="40%"><input 
												style="width:100%;" type="text"
												name="org_code<%=String.valueOf(rIndex)%>"
												value='<c:out value="${secUserRole.orgcd}"/>'></td>
											<td style="border:0px;"><input id="ORGfld<%=String.valueOf(rIndex)%>" 
												style="width:100%;" type="text"  
												name="org_description<%=String.valueOf(rIndex)%>"
												value='<c:out value="${secUserRole.orgcd_desc}"/>'
												readonly></td>
											<td style="border:0px;" width="35px"><a
												onclick="showLookupTree('ORG', '', '', 'secuserForm','org_code<%=String.valueOf(rIndex)%>','org_description<%=String.valueOf(rIndex)%>', true, '<c:out value="${ctx}"/>');"><img
												src="<c:out value="${ctx}"/>/images/microsoftsearch.gif"></a></td>
										</tr>
									</table>
								</TD>
								<TD width="250px">
									<table cellpadding="0" style="border:0px;" cellspacing="0"
										width="100%">
										<tr>
											<td style="border:0px;" width="1px"><input type="text"
												style="width:100%;"
												name="role_code<%=String.valueOf(rIndex)%>"
												value='<c:out value="${secUserRole.roleName}"/>'></td>
											<td style="border:0px;" width="100%"><input
												style="width:100%;" type="text" 
												name="role_description<%=String.valueOf(rIndex)%>"
												value='<c:out value="${secUserRole.roleName}"/>'
												readonly></td>
											<td style="border:0px;" width="35px"><a  
												onclick="showLookup2('ORGfld<%=String.valueOf(rIndex)%>','ROL', '', '', 'secuserForm','role_code<%=String.valueOf(rIndex)%>','role_description<%=String.valueOf(rIndex)%>', true, '<c:out value="${ctx}"/>');"><img
												src="<c:out value="${ctx}"/>/images/microsoftsearch.gif"></a></td>
										</tr>
									</table>
								</TD>
							</TR>
						</logic:iterate>
					</TABLE>
					<table width="100%">
						<tr>
							<td class="clsButtonBarText" width="100%">&nbsp;&nbsp;<a
								href="javascript:submitForm('addRole');">Add</a>&nbsp;&nbsp;&nbsp;|
							&nbsp;&nbsp;<a href="javascript:submitForm('removeRole');">Remove</a>
							</td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
		</html:form></div>
		</td>
	</tr>
</table>

<script language="javascript" type="text/javascript">
<!--
function submitForm(mthd){
	document.forms[0].method.value=mthd;
	document.forms[0].submit();
}


function showLookup2(id, tableId, grandParentName, parentName, openerFormName, codeFieldName, descFieldName, displayCode, appRoot){
	//alert(id);
	var orgFld = document.getElementById(id);
	
	if(orgFld.value.length >0){
		showLookup(tableId, grandParentName, parentName, openerFormName, codeFieldName, descFieldName, displayCode, appRoot);
	}else{
		alert("Please select ORG first!");
	}
	 
}
//-->
</script>

