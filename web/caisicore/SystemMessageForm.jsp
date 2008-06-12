
<%@ include file="/taglibs.jsp"%>
<%
String s = "debug";
%>


<html:form action="/SystemMessage.do">
	<input type="hidden" name="method" value="save" />
	<html:hidden property="system_message.id" />
	

	<table cellpadding="0" cellspacing="0" border="0" width="100%"
		height="100%">

		<!-- Title -->
		<tr>
			<th class="pageTitle" align="center">System Messages</th>
		</tr>

		<!-- body start -->
		<tr>
			<td height="100%">

			<table width="100%" cellpadding="0px" cellspacing="0px" height="100%"
				border="0">
				<!-- submenu -->
				<tr>
					<td align="left" class="buttonBar"><html:link
						action="/SystemMessage.do?method=list"
						style="color:Navy;text-decoration:none;">
						<img border="0" src="<html:rewrite page="/images/Back16.png"/>" />&nbsp;Close&nbsp;&nbsp;</html:link>
					<html:link href="javascript:submitForm();" 
						style="color:Navy;text-decoration:none;">
						<img border="0" src="<html:rewrite page="/images/Save16.png"/>" />&nbsp;Save&nbsp;&nbsp;</html:link>
					</td>
				</tr>

				<!-- messages -->
				<tr>
					<td align="left" class="message"><logic:messagesPresent
						message="true">
						<br />
						<html:messages id="message" message="true" bundle="pmm">
							<c:out escapeXml="false" value="${message}" />
						</html:messages>
						<br />
					</logic:messagesPresent></td>
				</tr>

				<tr>
					<td height="100%">
					<div
						style="color: Black; background-color: White; border-width: 1px; border-style: Ridge;
				                    height: 100%; width: 100%; overflow: auto;">





					<br />
					<div class="tabs" id="tabs">
					<table cellpadding="3" cellspacing="0" border="0">
						<tr>
							<th title="System Messages">Messages</th>
						</tr>
					</table>
					</div>

					<br />

					<table width="100%" cellpadding="0px" cellspacing="0px">
						<tr class="b">
							<td width="150px">Expiry Day:&nbsp;</td>
							<td><quatro:datePickerTag property="system_message.expiry_day" 
									width="150px" openerForm="systemMessageForm" />
							</td>
							<td></td>
						</tr>
						<tr class="b">
							<td>Expiry Time:&nbsp;</td>
							<td>Hour: <html:select
								property="system_message.expiry_hour">
								<%
								for (int x = 0; x < 24; x++) {
								%>
								<html:option value="<%=String.valueOf(x) %>">
									<%=x%>
								</html:option>
								<%
								}
								%>
							</html:select> &nbsp;&nbsp; Minute: <html:select
								property="system_message.expiry_minute">
								<%
								for (int x = 0; x < 60; x++) {
								%>
								<html:option value="<%=String.valueOf(x) %>">
									<%=x%>
								</html:option>
								<%
								}
								%>
							</html:select></td>
							<td></td>
						</tr>
						<tr class="b">
							<td>Message&nbsp;</td>
							<td colspan="2">
								<html-el:textarea property="system_message.message" rows="3" cols="50" 
									onkeyup="javascript:txtAreaLenChecker(this, 4000);"/>
							</td>
						</tr>

						

						
					</table>






					</div>
					</td>
				</tr>
			</table>



			</td>
		</tr>
		<!-- body end -->
	</table>
</html:form>
<script type="text/javascript">
<!--
	function submitForm(){
		document.forms[0].submit();
	}
	
	function txtAreaLenChecker(obj, maxLen) {
	   //counting each end of line as two characters
	   
		var v = obj.value;
		var len = v.length;
		
		if(len > maxLen){
			alert("Length of this field can not exceed " + maxLen + " characters.");
			obj.value = v.substr(0, maxLen);
	   }
	
	}
	
//-->
</script>

