<%@ include file="/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/quatro-tag.tld" prefix="quatro" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<script type="text/javascript" src='<c:out value="${ctx}"/>/js/quatroLookup.js'></script>
 
<html-el:form action="/PMmodule/QuatroFamilyIntake.do">
<input type="hidden" name="method"/>
<input type="hidden" name="id" value="<c:out value="${id}" />"/>
<html:hidden property="intakeId" />
<script lang="javascript">
function submitForm(methodVal) {
	document.forms(0).method.value = methodVal;
	document.forms(0).submit();
}
</script>
<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
	<tr><th class="pageTitle" align="center">Client Management - Family Intake</th></tr>
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
		<div
			style="color: Black; background-color: White; border-width: 1px; border-style: Ridge;
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
<td width="17%">Last Name</td>
<td width="17%">First Name</td>
<td width="17%">DOB</td>
<td width="13%">Gender</td>
<td width="17%">Alias(es)</td>
<td width="17%"></td></tr>
<tr><td></td>
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
<a href='javascript:submitForm("view");'style="color:Navy;text-decoration:underline;">View Family Head Intake</a>
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
<td width="17%">Last Name</td>
<td width="17%">First Name</td>
<td width="16%">DOB</td>
<td width="13%">Gender</td>
<td width="17%">Alias(es)</td>
<td width="17%">Relationship</td></tr>

<logic:iterate id="dependent" name="quatroClientFamilyIntakeForm" property="dependents" indexId="rIndex">
    <tr><td>
     <html:checkbox name="dependent" property="select" indexed="true" />
     <html:hidden name="dependent" property="intakeId" indexed="true" />
     <html:hidden name="dependent" property="clientId" indexed="true" />
    </td>
    <td><html:text name="dependent" property="lastName" indexed="true" style="width:90%" /></td>
    <td><html:text name="dependent" property="firstName" indexed="true" style="width:90%" /></td>
    <td><quatro:datePickerTag name="dependent" property="dob" indexed="true" openerForm="quatroClientFamilyIntakeForm" style="width:90%"></quatro:datePickerTag></td>
    <td><html:select name="dependent" property="sex" indexed="true" style="width:90%">
       <html:optionsCollection property="genders" value="code" label="description"/>
      </html:select>
    </td>
    <td><html:text name="dependent" property="alias" indexed="true" style="width:90%" /></td>
    <td><html:select name="dependent" property="relationship" indexed="true" style="width:90%">
       <html:optionsCollection property="relationships" value="code" label="description"/>
      </html:select>
    </td></tr>
    <tr><td></td><td colspan="6">
    <logic:equal name="dependent" property="dupliDemographicNo" value="0">
       <html:hidden name="dependent" property="dupliDemographicNo" value="0" indexed="true" />
      <html:hidden name="dependent" property="newClientCheck" value="N" indexed="true"/>
    </logic:equal>
    <logic:notEqual name="dependent" property="dupliDemographicNo" value="0">
       <logic:equal name="dependent" property="bServiceRestriction" value="true">
       <font color="#ff0000">Service Restriction applied.<br></font>
       </logic:equal>
       <logic:equal name="dependent" property="needCheck" value="true">
       <font color="#ff0000">Check potential duplicated client: &nbsp;</font>
       </logic:equal>
       Pick up existing client: <html:checkbox name="dependent" property="useDupliDemographicNo" indexed="true"> ClientNo: <c:out value="${dependent.dupliDemographicNo}" /></html:checkbox>
       <html:hidden name="dependent" property="dupliDemographicNo" indexed="true" />
       <html:hidden name="dependent" property="newClientCheck" value="Y" indexed="true"/>
    </logic:notEqual>
    </td></tr>
</logic:iterate>

<tr><td colspan="8" class="buttonBar4">
<html:hidden property="dependentsSize"/>
&nbsp;<a href='javascript:submitForm("add");'style="color:Navy;text-decoration:underline;">Add Dependent</a>
&nbsp;|&nbsp;<a href='javascript:submitForm("delete");'style="color:Navy;text-decoration:underline;">Delete Dependent</a></td>
</tr>
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
