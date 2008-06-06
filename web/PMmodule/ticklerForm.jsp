	<%@include file="/ticklerPlus/header.jsp"%>
	
	<%@page import="java.util.GregorianCalendar"%>
	<%@page import="java.util.Calendar"%>
	
	<%
/*
		GregorianCalendar now = new GregorianCalendar();
	
		int curYear = now.get(Calendar.YEAR);
		int curMonth = now.get(Calendar.MONTH);
		int curDay = now.get(Calendar.DAY_OF_MONTH);
		int curHour = now.get(Calendar.HOUR);
		int curMinute = now.get(Calendar.MINUTE);
		
		boolean curAm = (now.get(Calendar.HOUR_OF_DAY) <= 12) ? true : false;
*/		
	%>
	<script type="text/javascript" src="../js/checkDate.js"></script>
	<script type="text/javascript">
		function check_tickler_service_date() {
			var serviceDate = document.ticklerForm.elements['tickler.serviceDate'].value;
			if(check_date(serviceDate)) {		
				return true;
			} else {
				return false;
			}	
		}
		
		function search_demographic() {
			window.open('<c:out value="${ctx}"/>/ticklerPlus/demographicSearch.jsp?query=' + document.ticklerForm.elements['tickler.demographic_webName'].value,'demographic_search');
		}
		
	
		function validateTicklerForm(form) {
			if (form.elements['tickler.demographic_no'].value == '' || form.elements['tickler.demographic_no'].value == '0') {
				alert('You must provide patient information. Please use the search button');
				return false;
			}
			
			if (form.elements['tickler.task_assigned_to'].value == '0' || form.elements['tickler.task_assigned_to'].value == '') {
				alert('You must assign the task to a valid provider. Please use the search button');
				return false;
			}
			
			if (form.elements['tickler.serviceDate'].value == '') {
				alert('You must provide a valid service date');
				return false;
			}
			
			if (form.elements['tickler.message'].value == '') {
				alert('You must provide a message');
				return false;
			}
			
			return check_tickler_service_date();
		}
	</script>

<table border="0" cellspacing="0" cellpadding="1" width="100%">
<tr><th  class="pageTitle">Client Management - New Task</th></tr>

<tr><td class="buttonBar"><a href='javascript:submitForm("save");'
			style="color:Navy;text-decoration:none;">
			<img border=0 src=<html:rewrite page="/images/Save16.png"/> />&nbsp;Save&nbsp;&nbsp;</a>|
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
	<html:form action="/Tickler" onsubmit="return validateTicklerForm(this);">
	
		<input type="hidden" name="method" value="save" />
		<html:hidden property="tickler.creator" />
		<html:hidden property="tickler.tickler_no" />
		
		<tr><td width="20%">Client:</td>
		<td width="80%"><input type=hidden name="clientId" value="<c:out value="${client.demographicNo}"/>">
		<c:out value="${client.formattedName}"/></td></tr>
		<tr><td>Service Date:</td>
		<td><quatro:datePickerTag property="tickler.serviceDate" openerForm="ticklerForm" width="30%"/></td></tr>
		<tr><td>Service Time:</td>
		<td><html:select property="tickler.service_hour">
		</html:select> : 
		<html:select property="tickler.service_minute">
		</html:select> &nbsp; 
		<html:select property="tickler.service_ampm">
           <html:optionsCollection property="ampmLst" value="code" label="description"/>
		</html:select>
		</td></tr>
		<tr><td>Priority:</td>
		<td><html:select property="tickler.priority">
<!-- 
					<option value="Normal">Normal</option>
					<option value="High">High</option>
					<option value="Low">Low</option>
 -->							
		</html:select></td></tr>
		<tr><td>Task Assigned To:</td>
		<td><quatro:lookupTag tableName="NON" formProperty="ticklerForm" 
		  bodyProperty="tickler.task_assigned_to" codeProperty="tickler.task_assigned_to_name"
          showCode="false" width="50%"/>
<!-- 
		<html:hidden property="tickler.task_assigned_to" />
			<html:text property="tickler.task_assigned_to_name" />
			<input type="button" value="Search" onclick="search_provider();" />
 -->			
		</td></tr>
		<tr><td>Status:</td>
		<td><html:select property="tickler.status">
			<option value="A">Active</option>
			<option value="C">Completed</option>
			</html:select>
		</td></tr>
		<tr><td>Message:</td>
		<td><html:textarea style="width: 90%" rows="16" property="tickler.message" /></td></tr>

<!-- 
		<tr>
			<td class="fieldValue" colspan="3" align="left">
				<html:submit styleClass="button">Save</html:submit>
				<input type="button" value="Cancel" onclick="location.href='<c:out value="${ctx}"/>/Tickler.do';"/>
			</td>
		</tr>
 -->		
	</html:form>
</table>

	</body>
</html>
