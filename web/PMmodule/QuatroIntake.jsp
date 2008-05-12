<%@ include file="/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/quatro-tag.tld" prefix="quatro" %>
<%@page import="org.oscarehr.PMmodule.model.Admission"%>
<%@page import="java.util.Date"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<script type="text/javascript" src='<c:out value="${ctx}"/>/js/quatroLookup.js'></script>
 
<html-el:form action="/PMmodule/QuatroIntake.do">
<input type="hidden" name="method"/>
<script lang="javascript">
function submitForm(methodVal) {
		document.forms(0).method.value = methodVal;
		document.forms(0).submit();
}
	
function updateQuatroIntake(clientId, intakeId) {
	location.href = '<html:rewrite action="/PMmodule/QuatroIntakeEdit.do"/>' + "?method=update&intakeId=" + intakeId + "&clientId=" + clientId;
}
	
</script>
<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
	<tr>
		<th class="pageTitle" align="center">Client Management - Intake</th>
	</tr>
	<tr>
		<td align="left" valign="middle" class="buttonBar2">
		&nbsp;<html:link action="/PMmodule/QuatroClientSummary.do" name="actionParam" style="color:Navy;text-decoration:none;">Summary</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/ClientHistory.do" name="actionParam" style="color:Navy;text-decoration:none;">History</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<b>Intake</b>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroAdmission.do" name="actionParam" style="color:Navy;text-decoration:none;">Admission</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroRefer.do" name="actionParam" style="color:Navy;text-decoration:none;">Refer</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroDischarge.do" name="actionParam" style="color:Navy;text-decoration:none;">Discharge</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroServiceRestriction.do" name="actionParam" style="color:Navy;text-decoration:none;">Service Restriction</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroComplaint.do" name="actionParam" style="color:Navy;text-decoration:none;">Complaints</html:link>
		</td>
	</tr>
	<tr>
		<td align="left" class="buttonBar">
		<html:link action="/PMmodule/ClientSearch2.do" style="color:Navy;text-decoration:none;">
		<img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Close&nbsp;&nbsp;</html:link></td>
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
<tr><th>Intake Form</th></tr>
</table></div></td></tr>

<tr><td>
<table class="simple" cellspacing="2" cellpadding="3">
  <tr><td>Program Type</td>
  <td>Created On</td>
  <td>Staff</td>
  <td>Status</td>
  <td>Actions</td></tr>

  <logic-el:iterate id="intake" collection="${quatroIntake}">
    <tr><td width="20%"><c:out value="${intake.programType}" /></td>
    <td><c:out value="${intake.createdOnStr}" /></td>
    <td><c:out value="${sessionScope.provider.formattedName}" /></td>
    <td><c:out value="${intake.intakeStatus}"/></td>
    <td><input type="button" value="Update" 
      onclick="updateQuatroIntake('<c:out value="${client.demographicNo}" />', '<c:out value="${intake.id}" />')" /></td></tr>
  </logic-el:iterate>						
  <tr><td></td>
  <td></td>
  <td></td>
  <td></td>
  <td><input type="button" value="Create" 
     onclick="updateQuatroIntake('<c:out value="${client.demographicNo}" />', '0')" /></td></tr>
</table>
</td></tr>

</table>

<!--  end of page content -->
</div>
</td>
</tr>
</table>
</html-el:form>
