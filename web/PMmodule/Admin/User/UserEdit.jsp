<!-- 

Source:web/PMmodule/Admin/User/UserEdit.jsp 

-->

<%@ include file="/taglibs.jsp"%>


<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
	<tr>
		<th class="pageTitle" align="center"><span
			id="_ctl0_phBody_lblTitle" align="left">User Profile</span></th>
	</tr>
	<tr height="18px">
		<td align="left" class="buttonBar"><html:link
			action="/PMmodule/Admin/UserSearch.do"
			style="color:Navy;text-decoration:none;">
			<img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Back to User List&nbsp;&nbsp;|</html:link>
			<logic:present	name="userForEdit">
				<html:link href="javascript:submitForm('saveEdit');"
				style="color:Navy;text-decoration:none;">
				<img border=0 src=<html:rewrite page="/images/Save16.png"/> />&nbsp;Save&nbsp;&nbsp;|</html:link>
				<html:link href="javascript:submitForm('profile');"
				style="color:Navy;text-decoration:none;">
				<img border=0 src=<html:rewrite page="/images/New16.png"/> />&nbsp;Role/Org Security&nbsp;&nbsp;|</html:link>
			</logic:present> 
			<logic:notPresent name="userForEdit">
				<html:link href="javascript:submitForm('saveNew');"
				style="color:Navy;text-decoration:none;">
				<img border=0 src=<html:rewrite page="/images/Save16.png"/> />&nbsp;Save&nbsp;&nbsp;|</html:link>
			</logic:notPresent>
			
		</td>

	</tr>

	<tr>
		<td align="left" class="message">
			<br />
			<logic:messagesPresent
			message="true">
			<html:messages id="message" message="true" bundle="pmm">
				<c:out escapeXml="false" value="${message}" />
			</html:messages>
		</logic:messagesPresent>
		<br /></td>
	</tr>


	<tr>
		<td align="left"></td>
	</tr>
	<tr>
		<td height="100%">
		<div
			style="color: Black; background-color: White; border-width: 1px; border-style: Ridge;
                    height: 100%; width: 100%; overflow: auto;">

		

		<html:form action="/PMmodule/Admin/UserManager" method="post">
			<html:hidden property="method" value="" />
			<html:hidden property="securityNo" />
			<html:hidden property="providerNo" />
			
			<div class="tabs">
			<table cellpadding="3" cellspacing="0" border="0">
				<tr>
					<th title="sinatures">User Information</th>
				</tr>
			</table>
			</div>
			
			<table>

				<tr>
					<td>User ID:</td>
					<td><html:text property="userName" tabindex="1" /></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>Email:</td>
					<td><html:text property="email" tabindex="6"/></td>
				</tr>
				<tr>
					<td>First Name:</td>
					<td><html:text property="firstName" tabindex="2" /></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>Initial:</td>
					<td><html:text property="init" tabindex="7" /></td>
					
				</tr>
				<tr>
					<td>Last Name:</td>
					<td><html:text property="lastName" tabindex="3" /></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>Title:</td>
					<td><html-el:select property="title" tabindex="8">
						<c:forEach var="title" items="${titleLst}">
							<html-el:option value="${title.code}">
								<c:out value="${title.description}" />
							</html-el:option>
						</c:forEach>
					</html-el:select>
					</td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><html:password property="password" tabindex="4"/></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>Job Title:</td>
					<td><html:text property="jobTitle" tabindex="9" /></td>
				</tr>
				<tr>
					<td>Confirm Password:</td>
					<td><html:password property="confirmPassword"  tabindex="5"/></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>Active:</td>
					<td><html:checkbox property="status" tabindex="10" /></td>
				</tr>
				
					<tr style="visibility:hidden;">
						<td>PIN:</td>
						<td><html:password property="pin" value="****" /></td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>Confirm PIN:</td>
						<td><html:password property="confirmPin" value="****" /></td>
					</tr>
 				
			</table>

		</html:form></div>
		</td>
	</tr>
</table>
<script language="javascript" type="text/javascript">
<!--

function submitForm(func){
	document.forms[0].method.value=func;
	
	var fld_userName = document.getElementsByName('userName')[0];
	var fld_password = document.getElementsByName('password')[0];
	var fld_cPassword = document.getElementsByName('confirmPassword')[0];
	var fld_pin = document.getElementsByName('pin')[0];
	var fld_cPin = document.getElementsByName('confirmPin')[0];
	var fld_firstName = document.getElementsByName('firstName')[0];
	var fld_lastName = document.getElementsByName('lastName')[0];
	
	if(func == 'saveNew'){
		document.forms[0].method.value="save";
		if(validateRequired(fld_userName, "UserID") && validateLength(fld_userName, "UserID", 30, 3) &&
			validateRequired(fld_firstName, "First Name") && validateLength(fld_firstName, "First Name", 30, 2) &&
			validateRequired(fld_lastName, "Last Name") && validateLength(fld_lastName, "Last Name", 30, 2) &&
			validateRequired(fld_password, "Password")&& validateLength(fld_password, "Password", 20, 4)&&
			validateRequired(fld_cPassword, "Confirm Password")&& validateLength(fld_cPassword, "Confirm Password", 20, 4)&&
			validateRequired(fld_pin, "PIN")&& validateLength(fld_pin, "PIN", 4, 4)&&
			validateRequired(fld_cPin, "Confirm PIN")&& validateLength(fld_cPin, "Confirm PIN", 4, 4))
			
			document.forms[0].submit();
	}
	else if(func == 'saveEdit'){
		document.forms[0].method.value="save";
		var v1 = false;
		var v2 = false;
		var v3 = false;
		var v4 = false;
		var v5 = false;	
		var v6 = false;
		var v7 = false;
				
		if (validateRequired(fld_userName, "UserID") && validateLength(fld_userName, "UserID", 30, 3)){
			v1 = true;
		}
		
		if ( validateRequired(fld_firstName, "First Name") && validateLength(fld_firstName, "First Name", 30, 2))
		{
			v2 = true;
		}
		if ( validateRequired(fld_lastName, "Last Name") && validateLength(fld_lastName, "Last Name", 30, 2))
		{
			v3 = true;
		}	
		if ( validateRequired(fld_password, "Password") && validateLength(fld_password, "Password", 20, 4))
		{
			v4 = true;
		}
		if ( validateRequired(fld_cPassword, "Confirm Password") && validateLength(fld_cPassword, "Confirm Password", 20, 4))
		{
			v5 = true;
		}
		if ( validateRequired(fld_pin, "PIN") && validateLength(fld_pin, "PIN", 4, 4))
		{
			v6 = true;
		}
		if ( validateRequired(fld_cPin, "Confirm PIN") && validateLength(fld_cPin, "Confirm PIN", 4, 4))
		{
			v7 = true;
		}	
		
			
			
				
		if(v1 && v2 && v3 && v4 && v5 && v6 && v7)
			document.forms[0].submit();
	}
	else
	{
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

