<%@ include file="/taglibs.jsp"%>
<%@page import="com.quatro.common.KeyConstants"%>
<table border="0" cellspacing="0" cellpadding="1" width="100%">
<tr><th  class="pageTitle">Client Management - Tasks</th></tr>
<input type="hidden" name="method" value="save" />
<tr>
			<td class="simple" style="background: lavender"><%@ include file="ClientInfo.jsp" %></td>
		</tr>
<tr><td class="buttonBar2">
	<html:link action="/PMmodule/ClientSearch2.do" style="color:Navy;text-decoration:none;">&nbsp;
		<img style="vertical-align: middle" border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Back to Client Search&nbsp;&nbsp;|</html:link>
	<c:if test="${currentIntakeProgramId>0}">
		<security:oscarSec objectName="<%=KeyConstants.FUN_CLIENTTASKS %>"   orgCd='<%=((Integer) request.getAttribute("currentIntakeProgramId")).toString()%>' rights="<%=KeyConstants.ACCESS_WRITE %>">		
		<html:link action="/PMmodule/Task.do?method=add" name="actionParam"
				style="color:Navy;text-decoration:none;">
		<img style="vertical-align: middle" border=0 src=<html:rewrite page="/images/New16.png"/> />&nbsp;New&nbsp;&nbsp;</html:link>
		</security:oscarSec>
	</c:if>
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
<c:when test="${tickler.priority == 'High'}">
<img border=0 src=<html:rewrite page="/images/importance_high.jpg"/> />
</c:when>
<c:when test="${tickler.priority == 'Normal'}">
&nbsp;
</c:when>
<c:otherwise>
<img border=0 src=<html:rewrite page="/images/importance_low.jpg"/> />
</c:otherwise>
</c:choose>
</display:column>
<display:column sortable="true" sortProperty="tickler_no" title="Task ID">
 <a href='<html:rewrite page="/PMmodule/Task.do"/>?method=view&clientId=<c:out value="${clientId}"/>&ticklerNo=<c:out value="${tickler.tickler_no}" />'>
   <c:out value="${tickler.tickler_no}" /></a>
</display:column>
<display:column property="status" sortable="true" title="Status" />
<display:column property="provider.formattedName" sortable="true" title="Creator" />
<display:column property="service_date.time" sortable="true" title="Date" format="{0, date, yyyy/MM/dd hh:mm a}"  />
<display:column property="assignee.formattedName" sortable="true" sortProperty="assignee.lastName"	title="Assigned To" />
<display:column property="program.name" sortable="true"	title="Program" />
</display:table> 
</td></tr>
</table>
</body>

</html>

