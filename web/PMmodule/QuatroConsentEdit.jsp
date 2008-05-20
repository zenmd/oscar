<%@ include file="/taglibs.jsp"%>
<%@page import="java.util.Date"%>

<c:set var="ctx" value="${pageContext.request.contextPath}"
	scope="request" />
<script type="text/javascript"
	src='<c:out value="${ctx}"/>/js/quatroLookup.js'></script>
<script lang="javascript">
	function submitForm(methodVal) {
		document.forms(0).method.value = methodVal;
		document.forms(0).submit();
	}
	function validateStateLength(){
	    var fieldValue = document.consentDetailForm.elements["consent.statePurpose"].value;
		if (fieldValue.length>512)
		{
			alert("State Purpose input length is over 512!!!");
			return false;
		}else{
		 	return true;
		}	
	}
	function validateNotesLength(){
	    var fieldValue = document.consentDetailForm.elements["consent.notes"].value;
		if (fieldValue.length>512)
		{
			return false;
		}else{
		 	return true;
		}	
	}
	function validateSave(){
	
		var str1="State Purpose input length is over 512!!!" ;
		var str2="Notes input length is over 512!!!" ;
		
		if (!validateStateLength()){
			alert(str1); return false;
		}
		if (!validateNotesLength()){
			alert(str2); return false;
		}
			return true;
	}
	
</script>

<html-el:form action="/PMmodule/QuatroConsent.do">
	<input type="hidden" name="method" />
	<html-el:hidden property="consent.demographicNo"  />
	<html-el:hidden property="consent.id"  />

	<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
		<tr>
			<th class="pageTitle" align="center">Client Management - Consent</th>
			
		</tr>
		<tr height="18px">
			<td align="left" valign="middle" class="buttonBar2">&nbsp;<html:link
				action="/PMmodule/QuatroClientSummary.do" name="actionParam"
				style="color:Navy;text-decoration:none;">Summary</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
			<html:link action="/PMmodule/ClientHistory.do" name="actionParam"
				style="color:Navy;text-decoration:none;">History</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
			<html:link action="/PMmodule/QuatroIntake.do" name="actionParam"
				style="color:Navy;text-decoration:none;">Intake</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
			<html:link action="/PMmodule/QuatroAdmission.do" name="actionParam"
				style="color:Navy;text-decoration:none;">Admission</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
			<html:link action="/PMmodule/QuatroRefer.do" name="actionParam"
				style="color:Navy;text-decoration:none;">Refer</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
			<html:link action="/PMmodule/QuatroDischarge.do" name="actionParam"
				style="color:Navy;text-decoration:none;">Discharge</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
			<html:link action="/PMmodule/QuatroServiceRestriction.do"
				name="actionParam" style="color:Navy;text-decoration:none;">Service Restriction</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
			<html:link action="/PMmodule/QuatroComplain.do" name="actionParam" style="color:Navy;text-decoration:none;">Complaints
		</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<b>Consent</b>
		</td>
		</tr>
		<tr>
			<td align="left" class="buttonBar"><html:link
				action="/PMmodule/QuatroConsent.do?method=list" name="actionParam"
				style="color:Navy;text-decoration:none;">
				<img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Close&nbsp;&nbsp;</html:link>
			<a href='javaScript:submitForm("update");' onclick="return validateSave();"
				style="color:Navy;text-decoration:none;">
				<img border=0 src=<html:rewrite page="/images/Save16.png"/> />&nbsp;Save&nbsp;&nbsp;</a></td>
		</tr>
		<tr height="18px">
			<td align="left" class="message">
			<br />
			<logic:messagesPresent
				message="true">
				<html:messages id="message" message="true" bundle="pmm">
					<c:out escapeXml="false" value="${message}" />
				</html:messages>
			</logic:messagesPresent>
			<br /></td>
		</tr>
		<tr>
			<td height="100%">
			<div
				style="color: Black; background-color: White; border-width: 1px; border-style: Ridge;
                    height: 100%; width: 100%; overflow: auto;">
<!--  start of page content -->

				<table width="100%" class="edit">
					<tr>
						<td>
						<div class="tabs">
							<table cellpadding="3" cellspacing="0" border="0">
								<tr>
									<th>Consent</th>
								</tr>
							</table>
						</div>
						</td>
					</tr>					
					<tr>
						<td>
						<table class="simple" cellspacing="2" cellpadding="3">
							<tr>
								<td colspan="2">I	
								<bean:write name="consentDetailForm" property="consent.clientFormattedName"/>						
								  consent to the release and exchange of the following information:
								</td>
							</tr>
							<tr>
								<td colspan="2" width="90%" ><html:textarea  rows="3" property="consent.notes" cols="120"  /> </td>
							</tr>
						</table>
						<table class="simple" cellspacing="2" cellpadding="3">
							<tr><td colspan="2">Between an authorized representative of {shelter name} and the agency/organization named below:</td></tr>
							<tr><td>Agency Name:</td><td ><html-el:text property="consent.agencyName" maxlength="60" /></td></tr>
							<tr><td >Contact Person Name:</td><td><html-el:text property="consent.contactName" maxlength="60" /></td></tr>
							<tr><td>Contact Person Title:</td><td><html-el:text property="consent.contactTitle" maxlength="30" /></td></tr>
							<tr><td>Contact Person Phone:</td><td><html-el:text property="consent.contactPhone" maxlength="15" /></td></tr>							
							<tr><td colspan="2">For the following Stated purpose(s):</td></tr>
							<tr><td colspan="2"><html-el:textarea property="consent.statePurpose" cols="120"  rows="3" /></td></tr>
					  </table>
							<table  class="simple" cellspacing="2" cellpadding="3">
							<tr>
								<td colspan="2">
									I fully understand the purpose of this consent and give it voluntarily.  The consent is valid for the period from 
								</td>
							</tr>
							<tr>
								<td>
									 Today's Date<quatro:datePickerTag property="consent.startDateStr" openerForm="consentDetailForm" width="150px"></quatro:datePickerTag>
								</td>
								<td > To <quatro:datePickerTag property="consent.endDateStr" openerForm="consentDetailForm" width="150px"></quatro:datePickerTag></td>
							</tr>
							<tr><td >Dated this Today's Date</td><td>
							<jsp:useBean id="now" class="java.util.Date" />
							<fmt:formatDate pattern="yyyy/MM/dd" value="${now}" /></td></tr>
							<tr><td>Client Signature</td><td>User Id</td></tr>
							<tr><td >Witness Name</td><td><bean:write name="consentDetailForm" property="consent.providerFormattedName" /></td></tr>
						</table>
						</td>
					</tr>
				</table>

<!--  end of page content -->
			
			</div>
			</td>
		</tr>
	</table>
</html-el:form>
