<%@ include file="/taglibs.jsp" %>
<%@page import="org.oscarehr.PMmodule.model.ClientHistory"%>
<%@page import="java.util.Calendar"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<script type="text/javascript" src='<c:out value="${ctx}"/>/js/quatroLookup.js'></script>
 
<html-el:form action="/PMmodule/ClientHistory.do">
<input type="hidden" name="method"/>
<input type="hidden" name="clientId" value="<c:out value="${clientId}"/>" />
<script lang="javascript">
function submitForm(methodVal) {
   trimInputBox();
   document.forms[0].method.value = methodVal;
   document.forms[0].submit();
}

</script>
<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
	<tr>
		<th class="pageTitle" align="center">Client Management - History</th>
	</tr>
	<tr>
		<td class="simple" style="background: lavender"><%@ include file="ClientInfo.jsp" %></td>
	</tr>
	<tr>
		<td align="left" class="buttonBar2">
		<html:link action="/Home.do"
		style="color:Navy;text-decoration:none">&nbsp;
		<img style="vertical-align: middle" border=0 src=<html:rewrite page="/images/close16.png"/> />&nbsp;Close&nbsp;&nbsp;|</html:link>
		<html:link action="/PMmodule/ClientSearch2.do" 
		style="color:Navy;text-decoration:none;">&nbsp;
		<img style="vertical-align: middle" border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Back to Client Search&nbsp;&nbsp;|</html:link>
		<a href='javascript:submitForm("list");' style="color:Navy;text-decoration:none;">
        &nbsp;<img style="vertical-align: middle" border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Filter&nbsp;&nbsp;</a></td>
	</tr>
	<tr><td align="left" class="message">
      <logic:messagesPresent message="true">
        <html:messages id="message" message="true" bundle="pmm"><c:out escapeXml="false" value="${message}" />
        </html:messages> 
      </logic:messagesPresent>
	</td></tr>

    <tr><td>
	<table border="0" cellspacing="2" cellpadding="2" width="100%">
	  <tr><th width="20%" align="right">Action Date</th>
	  <td width="10%" align="right">Start</td>
	  <td width="25%" align="left"><quatro:datePickerTag property="actionStartDateTxt" openerForm="clientHistoryForm" width="90%" /></td>
	  <td width="10%" align="right">End</td>
	  <td width="25%" align="left"><quatro:datePickerTag property="actionEndDateTxt" openerForm="clientHistoryForm" width="90%" /></td>
	  <td width="10%"></td></tr>
	  <tr><th align="right">Action</th>
	  <td colspan="5"><html:select property="actionTxt">
		<html-el:optionsCollection name="actions" value="value" label="label" />
	  </html:select></td></tr>
 	  <tr><th align="right">Program</th>
	  <td colspan="5"><html:select property="programId">
		<html-el:optionsCollection name="programs" value="value" label="label" />
	  </html:select></td></tr>
	</table>
	</td></tr>

<tr><td><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Client History</th></tr>
</table></div></td></tr>

	<tr>
		<td height="100%">
		<div
			style="color: Black; background-color: White; border-width: 1px; border-style: Ridge;
                    height: 100%; width: 100%; overflow: auto;" id="scrollBar">

<!--  start of page content -->
<table width="100%" class="edit">

<tr><td>
<display:table class="simple" cellspacing="2" cellpadding="3" id="history" name="histories"  requestURI="/PMmodule/ClientHistory.do" >
	<display:setProperty name="paging.banner.placement" value="bottom" />
	<display:setProperty name="basic.msg.empty_list" value="No client history available" />
	<display:column property="actionDate.time" format="{0, date, yyyy/MM/dd}" sortable="true" title="Action Date" />
	<display:column property="action" sortable="true" title="Action" />
	<display:column property="notes" sortable="false" title="Reference" />
    <display:column property="programName" sortable="true" title="Program" />
	<display:column property="providerName" sortable="true" title="Staff" />
	<display:column property="historyDate" format="{0, date, yyyy/MM/dd hh:mm:ss a}" sortable="true" title="Update Date" />
</display:table>
</td></tr>
</table>

<!--  end of page content -->
</div>
</td>
</tr>
</table>
</html-el:form>
