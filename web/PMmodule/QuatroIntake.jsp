<%@ include file="/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/quatro-tag.tld" prefix="quatro" %>
<%@page import="org.oscarehr.PMmodule.model.Admission"%>
<%@page import="java.util.Date"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<script type="text/javascript" src='<c:out value="${ctx}"/>/js/quatroLookup.js'></script>
 
<html-el:form action="/PMmodule/QuatroIntake.do">
<input type="hidden" name="method"/>
<input type="hidden" name="clientId"/>
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
		<td class="simple" style="background: lavender"><%@ include file="ClientInfo.jsp" %></td>
	</tr>
	<tr>
		<td align="left" class="buttonBar">
		<a href="javascript:updateQuatroIntake('<c:out value="${client.demographicNo}" />', '0')" style="color:Navy;text-decoration:none;">
			<img border=0 src=<html:rewrite page="/images/New16.png"/> />&nbsp;New Intake</a>&nbsp;&nbsp;|
		
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
<display:table class="simple" cellspacing="2" cellpadding="3" id="intake" name="quatroIntake" export="false" pagesize="50" requestURI="/PMmodule/QuatroIntake.do">
			<display:setProperty name="paging.banner.placement" value="bottom" />
			<display:setProperty name="basic.msg.empty_list" value="No intakes found." />
			<display:column sortable="true" title="Program Type">
                 <c:out value="${intake.programType}" /></a>
            </display:column>
            <display:column title="Created On">
	            <c:out value="${intake.createdOnStr}" />
            </display:column>
            <display:column title="Staff">
				<c:out value="${sessionScope.provider.formattedName}" />
            </display:column>
			<display:column title="Status">
				<c:out value="${intake.intakeStatus}"/>
			</display:column>
			<display:column title="Actions">
	<c:choose>								
	<c:when test="${intake.intakeStatus == 'active' || intake.intakeStatus == 'admitted'}">
	  <a href="javascript:updateQuatroIntake('<c:out value="${client.demographicNo}" />', '<c:out value="${intake.id}" />')" >Update</a>			
	</c:when>
	<c:otherwise>
	  <a href="javascript:updateQuatroIntake('<c:out value="${client.demographicNo}" />', '<c:out value="${intake.id}" />')" >View</a>			
	</c:otherwise>
	</c:choose>		
			</display:column>
</display:table>
</td></tr>
</table>

<!--  end of page content -->
</div>
</td>
</tr>
</table>
</html-el:form>
