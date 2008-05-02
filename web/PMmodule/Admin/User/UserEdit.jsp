<%@ include file="/taglibs.jsp"%>


<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
	<tr>
		<th class="pageTitle" align="center"><span
			id="_ctl0_phBody_lblTitle" align="left">User Management</span></th>
	</tr>
	<tr>
		<td align="left" class="buttonBar"><html:link
			action="/PMmodule/Admin/UserManager.do"
			style="color:Navy;text-decoration:none;">
			<img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Back to User List&nbsp;&nbsp;|</html:link>
			<logic:present	name="userForEdit">
				<html:link href="javascript:submitForm('edit');"
				style="color:Navy;text-decoration:none;">
				<img border=0 src=<html:rewrite page="/images/Save16.png"/> />&nbsp;Save&nbsp;&nbsp;|</html:link>
			</logic:present> 
			<logic:notPresent name="userForEdit">
				<html:link href="javascript:submitForm('new');"
				style="color:Navy;text-decoration:none;">
				<img border=0 src=<html:rewrite page="/images/Save16.png"/> />&nbsp;Save&nbsp;&nbsp;|</html:link>
			</logic:notPresent>

			
		</td>

	</tr>

	<tr>
		<td align="left" class="message"><logic:messagesPresent
			message="true">
			<html:messages id="message" message="true" bundle="pmm">
				<c:out escapeXml="false" value="${message}" />
			</html:messages>
		</logic:messagesPresent></td>
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
				<td align="left" class="clsHomePageHeader" colspan="5"><logic:present
					name="userForEdit">
					<h2>User Edit</h2>
				</logic:present> <logic:notPresent name="userForEdit">
					<h2>New User</h2>
				</logic:notPresent></td>
			</tr>



		</table>

		<html:form action="/PMmodule/Admin/UserManager" method="post">
			<html:hidden property="method" value="" />
			<html:hidden property="securityNo" />
			<html:hidden property="providerNo" />
			<table>

				<tr>
					<td>User ID:</td>
					<td><html:text property="userName" /></td><td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>Email:</td>
					<td><html:text property="email" /></td>
				</tr>
				<tr>
					<td>First Name:</td>
					<td><html:text property="firstName" /></td><td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>Password:</td>
					<td><html:password property="password" /></td>
				</tr>
				<tr>
					<td>Last Name:</td>
					<td><html:text property="lastName" /></td><td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>Confirm Password:</td>
					<td><html:password property="confirmPassword"  /></td>
				</tr>
				<tr>
					<td>Initial:</td>
					<td><html:text property="init" /></td><td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>PIN:</td>
					<td><html:password property="pin" /></td>
				</tr>
				<tr>
					<td>Title:</td>
					<td><html:select property="title" >
							<html:option value="">Select Title</html:option>
							<html:option value="title1" />
							<html:option value="title2" />
						</html:select></td><td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>Confirm PIN:</td>
					<td><html:password property="confirmPin" /></td>
				</tr>
				<tr>
					<td>Job Title:</td>
					<td><html:text property="jobTitle" /></td><td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>Active:</td>
					<td><html:checkbox property="status" /></td>
				</tr>

			</table>

		</html:form></div>
		</td>
	</tr>
</table>
<script language="javascript" type="text/javascript">
<!--

function submitForm(func){
	document.forms[0].method.value='save';
	
	var fld_userName = document.getElementsByName('userName')[0];
	var fld_password = document.getElementsByName('password')[0];
	var fld_cPassword = document.getElementsByName('confirmPassword')[0];
	var fld_pin = document.getElementsByName('pin')[0];
	var fld_cPin = document.getElementsByName('confirmPin')[0];
	
	if(func == 'new'){
	
		if(validateRequired(fld_userName, "UserID") && validateLength(fld_userName, "UserID", 30, 1) &&
			validateRequired(fld_password, "Password")&& validateLength(fld_password, "Password", 20, 4)&&
			validateRequired(fld_cPassword, "Confirm Password")&& validateLength(fld_cPassword, "Confirm Password", 20, 4)&&
			validateRequired(fld_pin, "PIN")&& validateLength(fld_pin, "PIN", 4, 4)&&
			validateRequired(fld_cPin, "Confirm PIN")&& validateLength(fld_cPin, "Confirm PIN", 4, 4))
			
			document.forms[0].submit();
	}
	if(func == 'edit'){
		var v1 = false;
		var v2 = false;
		var v3 = false;
		var v4 = false;
		var v5 = false;	
				
		if (validateRequired(fld_userName, "UserID") && validateLength(fld_userName, "UserID", 30, 1))
			v1 = true;

		if (fld_password.value != null && fld_password.value.length > 0){
		 	v2 = validateLength(fld_userName, "Password", 30, 1);
		}else{
			v2 = true;
		}	

		if (fld_cPassword.value != null && fld_cPassword.value.length > 0){
		 	v3 = validateLength(fld_cPassword, "Confirm Password", 30, 1);
		}else{
			v3 = true;
		}	
		if (fld_pin.value != null && fld_pin.value.length > 0){
		 	v4 = validateLength(fld_pin, "PIN", 30, 1);
		}else{
			v4 = true;
		}	
		if (fld_cPin.value != null && fld_cPin.value.length > 0){
		 	v5 = validateLength(fld_cPin, "Confirm PIN", 30, 1);
		}else{
			v5 = true;
		}	
		if(v1 && v2 && v3 && v4 && v5)
			document.forms[0].submit();
	
	}
}

function validateRequired(field, fieldNameDisplayed ){
	if (field.value == null || field.value == ''){
		alert('The field "' + fieldNameDisplayed + '" is required.');
		return(false);
	}
	
	return(true);
}

function validateLength(field, fieldNameDisplayed, maxLength, minLength){
	
	if (maxLength > 0 && field.value.length > maxLength){
		alert('The value you entered for "'+ fieldNameDisplayed + '" is too long, maximum length allowed is '+maxLength+' characters.');
		return(false);
	}

	if (minLength > 0 && field.value.length < minLength){
		alert('The value you entered for "' + fieldNameDisplayed + '" is too short, minimum length allowed is ' + minLength+' characters.');
		return(false);
	}
	
	return(true);
}

//-->
</script>

