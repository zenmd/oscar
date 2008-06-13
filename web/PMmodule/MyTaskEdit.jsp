<%@include file="/ticklerPlus/header.jsp"%>
<%@page import="java.util.Calendar"%>
	
<script type="text/javascript">
function submitForm(methodVal) {
   document.forms(0).method.value = methodVal;
   document.forms(0).submit();
}
</script>

<table border="0" cellspacing="0" cellpadding="1" width="100%">
<tr><th  class="pageTitle">My Tasks -Update Task</th></tr>

<tr><td class="buttonBar">
 <a href='javascript:submitForm("save");'	style="color:Navy;text-decoration:none;">
 img border=0 src=<html:rewrite page="/images/Save16.png"/> />&nbsp;Save&nbsp;&nbsp;</a>|
	<html:link action="/PMmodule/Task.do" name="actionParam" style="color:Navy;text-decoration:none;">
	<img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Close&nbsp;&nbsp;</html:link>
</td></tr>
<tr><td align="left" class="message">
   <logic:messagesPresent message="true">
     <html:messages id="message" message="true" bundle="pmm"><c:out escapeXml="false" value="${message}" /></html:messages> 
   </logic:messagesPresent>
</td></tr>
	
</table>

<table width="100%" class="simple">
	<html:form action="/PMmodule/Task.do" onsubmit="return validateTicklerForm(this);">
        <input type="hidden" name="method"/>
		<html:hidden property="tickler.creator" />
		<html:hidden property="tickler.demographic_no" />
		<html:hidden property="tickler.tickler_no" />
		
		<tr><td width="20%">Client:</td>
		<td width="80%"><c:out value="${client.formattedName}"/></td></tr>
		<tr><td>Service Date:</td>
		<td><c:out value="${ticklerForm.tickler.serviceDate}"/></td></tr>
		<tr><td>Service Time:</td>
		<td><c:out value="${ticklerForm.tickler.service_hour}"/> : 
		  <c:out value="${ticklerForm.tickler.service_minute}"/> &nbsp; 
		  <c:out value="${ticklerForm.tickler.service_ampm}"/></td></tr>
		<tr><td>Priority:</td>
		<td><c:out value="${ticklerForm.tickler.priority}"/></td></tr>
		<tr><td>Creator:</td>
		<td><c:out value="${ticklerForm.provider.formattedName}" /></td></tr>
		<tr><td>Task Assigned To:</td>
		<td>Program: <c:out value="${ticklerForm.program.name}" />
		  Provider: <c:out value="${ticklerForm.assignee.formattedName}" /></td></tr>
		<tr><td>Status:</td>
		<td><html:select property="tickler.status">
			<option value="Active">Active</option>
			<option value="Completed">Completed</option>
			</html:select>
		</td></tr>
		<tr><td>Message:</td>
		<td><c:out value="${ticklerForm.tickler.message}" /></td></tr>

	</html:form>
</table>

	</body>
</html>
