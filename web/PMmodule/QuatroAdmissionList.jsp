<%@ include file="/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/quatro-tag.tld" prefix="quatro" %>
<%@page import="org.oscarehr.PMmodule.model.Admission"%>
<%@page import="java.util.Date"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
 
<html-el:form action="/PMmodule/QuatroAdmission.do">
<input type="hidden" name="method"/>
<input type="hidden" name="clientId"/>
<script lang="javascript">
function submitForm(methodVal) {
		document.forms(0).method.value = methodVal;
		document.forms(0).submit();
}
	
function updateQuatroAdmission(clientId, admissionId) {
	location.href = '<html:rewrite action="/PMmodule/QuatroAdmission.do"/>' + "?method=update&admissionId=" + admissionId + "&clientId=" + clientId;
}
	
</script>
<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
	<tr>
		<th class="pageTitle" align="center">Client Management - Admission</th>
	</tr>
	<tr>
		<td class="simple" style="background: lavender"><%@ include file="ClientInfo.jsp" %></td>
	</tr>
	<tr>
		<td align="left" class="buttonBar">
		<html:link action="/PMmodule/QuatroClientSummary.do" name="actionParam" style="color:Navy;text-decoration:none;">
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
<tr><th>Admission Form</th></tr>
</table></div></td></tr>

<tr><td>
<table class="simple" cellspacing="2" cellpadding="3">
  <tr><td>Program Type</td>
  <td>Created On</td>
  <td>Staff</td>
  <td>Status</td>
  <td>Actions</td></tr>

  <logic-el:iterate id="admission" collection="${admission}">
    <tr><td width="20%"><c:out value="${admission.programType}" /></td>
    <td><c:out value="${admission.admissionDateStr}" /></td>
    <td><c:out value="${sessionScope.provider.formattedName}" /></td>
    <td><c:out value="${admission.admissionStatus}"/></td>
	<td>
	<c:choose>								
	<c:when test="${admission.admissionStatus == 'admitted'}">
     <input type="button" value="Update" 
      onclick="updateQuatroAdmission('<c:out value="${clientId}" />', '<c:out value="${admission.id}" />')" />
	</c:when>
	<c:otherwise>
     <input type="button" value="View" />
	</c:otherwise>
	</c:choose>
    </td></tr>
  </logic-el:iterate>						
</table>
</td></tr>

</table>

<!--  end of page content -->
</div>
</td>
</tr>
</table>
</html-el:form>
