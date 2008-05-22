<%@ include file="/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/quatro-tag.tld" prefix="quatro" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<script type="text/javascript" src='<c:out value="${ctx}"/>/js/quatroLookup.js'></script>
 
<html-el:form action="/PMmodule/QuatroFamilyIntake.do">
<input type="hidden" name="method"/>
<input type="hidden" name="clientId" value="<c:out value="${clientId}" />"/>
<html:hidden property="intakeId" />
<script lang="javascript">
function submitForm(methodVal) {
	document.forms(0).method.value = methodVal;
	document.forms(0).submit();
}

function checkExistClients(i){
   var varStr="quatroClientFamilyIntakeForm," + "dependent[" + i + "].firstName," +
        "dependent[" + i + "].lastName," + "dependent[" + i + "].sex," +
        "dependent[" + i + "].dob," + "dependent[" + i + "].alias," +
        "dependent[" + i + "].clientId," + "dependent[" + i + "].statusMsg," +
        "dependent[" + i + "].newClientChecked";

   var lastName = document.getElementsByName("dependent[" + i + "].lastName")[0];
   var firstName = document.getElementsByName("dependent[" + i + "].firstName")[0];
   var dob = document.getElementsByName("dependent[" + i + "].dob")[0];
   var sex = document.getElementsByName("dependent[" + i + "].sex")[0];
   var alias = document.getElementsByName("dependent[" + i + "].alias")[0];
   var url='<c:out value="${ctx}" />/PMmodule/DuplicateClientCheck.do?' +
     "var=" + varStr + 
     "&firstName=" + firstName.value + "&lastName=" + lastName.value + 
     "&dob=" + dob.value + "&sex=" + sex.value +
     "&alias=" + alias.value + "&shortFlag=Y";
   win = window.open(url,"_blank","toolbar=yes,menubar= yes,resizable=yes,scrollbars=yes,status=yes,width=750,height=600");
   win.focus();
}
</script>
<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
	<tr><th class="pageTitle" align="center">Client Management - Family Intake</th></tr>
	<tr>
	<td>
		<table width="100%" class="simple">
			<tr>
			<td style="width: 15%"><font><b>Client No.</b></font></td><td colspan="3"><font><b><c:out value="${client.demographicNo}" /></b></font></td>
			</tr>
			<tr>
				<td style="width: 15%"<font><b>Name</b></font></td>
				<td style="width: 35%"><font><b><c:out value="${client.formattedName}" /></b></font></td>
				<td style="width: 15%"><font><b>Date of Birth </b></font></td>
				<td style="width: 35%"><font><b><c:out value="${client.yearOfBirth}" />/<c:out value="${client.monthOfBirth}" />/<c:out value="${client.dateOfBirth}" /></b></font></td>
			</tr>
		</table>
	</td>
	</tr>
	<tr>
		<td align="left" valign="middle" class="buttonBar3">
		&nbsp;Summary&nbsp;&nbsp;|&nbsp;&nbsp;
		History&nbsp;&nbsp;|&nbsp;&nbsp;
		<b>Intake</b>&nbsp;&nbsp;|&nbsp;&nbsp;
		Admission&nbsp;&nbsp;|&nbsp;&nbsp;
		Refer&nbsp;&nbsp;|&nbsp;&nbsp;
		Discharge&nbsp;&nbsp;|&nbsp;&nbsp;
		Service Restriction&nbsp;&nbsp;|&nbsp;&nbsp;
		Complaints
		</td>
	</tr>
	<tr>
		<td align="left" class="buttonBar"><a href='javascript:submitForm("save");'
			style="color:Navy;text-decoration:none;">
			<img border=0 src=<html:rewrite page="/images/Save16.png"/> />&nbsp;Save&nbsp;&nbsp;</a>|
            <html:link action="/PMmodule/QuatroIntake.do" name="actionParam" style="color:Navy;text-decoration:none;">
            <img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Close&nbsp;</html:link></td>
	</tr>
	<tr><td align="left" class="message">
      <logic:messagesPresent message="true">
        <html:messages id="message" message="true" bundle="pmm"><c:out escapeXml="false" value="${message}" />
        </html:messages> 
      </logic:messagesPresent>
	</td></tr>
	<tr>
		<td height="100%">
		<div style="color: Black; background-color: White; border-width: 1px; border-style: Ridge;
                    height: 100%; width: 100%; overflow: auto;">
<!--  start of page content -->
<table width="100%" class="edit">
<tr><td><br><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Head</th></tr>
</table></div></td></tr>
<tr><td>
<table class="simple" cellpadding="3" cellspacing="0" border="0">
<tr><td width="3%"></td>
<td width="6%"></td>
<td width="15%">Last Name*</td>
<td width="15%">First Name*</td>
<td width="16%">DOB</td>
<td width="14%">Gender*</td>
<td width="14%">Alias(es)</td>
<td width="18%"></td></tr>
<tr><td></td>
<td></td>
<td><c:out value="${quatroClientFamilyIntakeForm.familyHead.lastName}" />
<html:hidden property="familyHead.demographicNo"  />
<html:hidden property="familyHead.lastName" /></td>
<td><c:out value="${quatroClientFamilyIntakeForm.familyHead.firstName}" />
<html:hidden property="familyHead.firstName" /></td>
<td><c:out value="${quatroClientFamilyIntakeForm.dob}" />
<html:hidden property="dob" /></td>
<td><c:out value="${quatroClientFamilyIntakeForm.familyHead.sexDesc}" />
<html:hidden property="familyHead.sex" /></td>
<td><c:out value="${quatroClientFamilyIntakeForm.familyHead.alias}" />
<html:hidden property="familyHead.alias" /></td>
<td>
<a href="<c:out value="${ctx}"/>/PMmodule/QuatroIntakeEdit.do?method=update&intakeId=<c:out value="${quatroClientFamilyIntakeForm.intakeId}"/>&clientId=<c:out value="${clientId}"/>" style="color:Navy;text-decoration:underline;">View Family Head Intake</a>
</td></tr>
</table>
</td></tr>
</table>

<table width="100%" class="edit">
<tr><td><br><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Spouse/Dependent(s)</th></tr>
</table></div></td></tr>

<tr><td>
<table class="simple" cellpadding="3" cellspacing="0" border="0">
<tr><td width="3%"></td>
<td width="6%"></td>
<td width="15%">Last Name*</td>
<td width="15%">First Name*</td>
<td width="16%">DOB</td>
<td width="14%">Gender*</td>
<td width="14%">Alias(es)</td>
<td width="18%">Relationship</td></tr>

<logic:iterate id="dependent" name="quatroClientFamilyIntakeForm" property="dependents" indexId="rIndex">
    <tr><td>
     <html:checkbox name="dependent" property="select" indexed="true" />
     <html:hidden name="dependent" property="intakeId" indexed="true" />
     <html:hidden name="dependent" property="clientId" indexed="true" />
     <html:hidden name="dependent" property="effDate" indexed="true" />
     <html:hidden name="dependent" property="joinFamilyDateTxt" indexed="true" />
     <html:hidden name="dependent" property="newClientChecked" indexed="true" />
     <html:hidden name="dependent" property="duplicateClient" indexed="true" />
     <html:hidden name="dependent" property="serviceRestriction" indexed="true" />
    </td>
    <td>
      <a href="javascript:checkExistClients(<%=rIndex%>);" style="color:Navy;text-decoration:none;">
       <img border="0" src="<html:rewrite page="/images/search16.gif"/>" />
       </a>
       <html:text name="dependent" property="statusMsg" indexed="true" readonly="readonly" style="border: 0px;width: 20px"/>
    </td>
    <td><html:text name="dependent" property="lastName" indexed="true" style="width:90%" /></td>
    <td><html:text name="dependent" property="firstName" indexed="true" style="width:90%" /></td>
    <td><quatro:datePickerTag name="dependent" property="dob" indexed="true" openerForm="quatroClientFamilyIntakeForm" style="width:90%"></quatro:datePickerTag></td>
    <td><html:select name="dependent" property="sex" indexed="true" style="width:90%">
       <html:optionsCollection property="genders" value="code" label="description"/>
      </html:select>
    </td>
    <td><html:text name="dependent" property="alias" indexed="true" style="width:95%" /></td>
    <td><html:select name="dependent" property="relationship" indexed="true" style="width:90%">
       <html:optionsCollection property="relationships" value="code" label="description"/>
      </html:select>
    </td></tr>
    <tr><td></td><td colspan="6">
    <logic:equal name="dependent" property="clientId" value="0">
       <logic:equal name="dependent" property="duplicateClient" value="Y">
       <font color="#ff0000">This new client may be an existing client.</font>
       </logic:equal>
    </logic:equal>
    <logic:notEqual name="dependent" property="clientId" value="0">
       <logic:equal name="dependent" property="serviceRestriction" value="Y">
       <font color="#ff0000">Service Restriction applied.<br></font>
       </logic:equal>
    </logic:notEqual>
    </td></tr>
</logic:iterate>

<tr><td colspan="8" class="buttonBar4">
<html:hidden property="dependentsSize"/>
&nbsp;<a href='javascript:submitForm("add");'style="color:Navy;text-decoration:underline;">Add Dependent</a>
&nbsp;|&nbsp;<a href='javascript:submitForm("delete");'style="color:Navy;text-decoration:underline;">Delete Dependent</a>
<c:choose>
<c:when test="${bDupliDemographicNoApproved==false}">
<input type=checkbox name="newClientConfirmed" value="Y">Confirm all new clients.
</c:when>
<c:when test="${bDupliDemographicNoApproved==true}">
<input type="hidden" name="newClientConfirmed" value="Y">
</c:when>
<c:otherwise>
<input type="hidden" name="newClientConfirmed" value="N">
</c:otherwise>
</c:choose>
</td>
</tr>

<tr><td colspan="8" class="buttonBar4">#&nbsp; existing client</td></tr>
</table>
</td></tr>

</table>

<!--  end of page content -->
</div>
</td>
</tr>
</table>
<c:out value="${saveOK}" />
</html-el:form>
