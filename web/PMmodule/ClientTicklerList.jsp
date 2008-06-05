<%@ include file="/taglibs.jsp"%>
<%@ include file="/ticklerPlus/header.jsp"%>

<table border="0" cellspacing="0" cellpadding="1" width="100%">
<tr><th  class="pageTitle">Client Management - Tasks</th></tr>
<input type="hidden" name="method" value="save" />

<tr><td class="buttonBar"><html:link action="/PMmodule/Task.do?method=add" name="actionParam"
			style="color:Navy;text-decoration:none;">
	<img border=0 src=<html:rewrite page="/images/New16.png"/> />&nbsp;New&nbsp;&nbsp;|</html:link>
	<html:link action="/PMmodule/ClientSearch2.do" style="color:Navy;text-decoration:none;">
	<img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Close&nbsp;&nbsp;</html:link>
</td></tr>
<tr><td align="left" class="message">
   <logic:messagesPresent message="true">
     <html:messages id="message" message="true" bundle="pmm"><c:out escapeXml="false" value="${message}" /></html:messages> 
   </logic:messagesPresent>
</td></tr>

<tr><td><br><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Tasks</th></tr>
</table></div></td></tr>

<tr><td>
<display:table class="simple" cellspacing="2" cellpadding="3" id="tickler" name="ticklers" 
                       pagesize="0"	requestURI="/PMmodule/Task.do">
<display:setProperty name="paging.banner.placement" value="bottom" />
<display:setProperty name="basic.msg.empty_list" value="No task found." />
<display:column sortable="false" title="" sortProperty="priority" >
<c:choose>
<c:when test="${priority == com.quatro.common.KeyConstants.TASK_PRIORITY_HIGH}">
<img border=0 src=<html:rewrite page="/images/importance_high.jpg"/> />
</c:when>
<c:when test="${priority == com.quatro.common.KeyConstants.TASK_PRIORITY_NORMAL}">
&nbsp;
</c:when>
<c:otherwise>
<img border=0 src=<html:rewrite page="/images/importance_high.jpg"/> />
</c:otherwise>
</c:choose>
</display:column>
<display:column property="status" sortable="true" title="Status" />
<display:column property="provider.formattedName" sortable="true" sortProperty="provider.lastName, provider.firstName" title="Creator" />
<display:column property="service_date" sortable="true"	title="Date" />
<display:column property="assignee.formattedName" sortable="true" sortProperty="assignee.lastName"	title="Task Assigned To" />
<display:column property="program.name" sortable="true"	title="Program" />
</display:table> 
</td></tr>
</table>
</body>
</html>

