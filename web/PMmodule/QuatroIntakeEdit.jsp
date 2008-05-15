<%@ include file="/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/quatro-tag.tld" prefix="quatro" %>
<bean:define id="language" name="quatroIntakeEditForm" property="language" type="com.quatro.model.LookupCodeValue" />
<bean:define id="originalCountry" name="quatroIntakeEditForm" property="originalCountry" type="com.quatro.model.LookupCodeValue" />

<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<script type="text/javascript" src='<c:out value="${ctx}"/>/js/quatroLookup.js'></script>
 
<html-el:form action="/PMmodule/QuatroIntakeEdit.do">
<html:hidden property="intake.staffId"/>
<html:hidden property="intake.intakeStatus"/>
<html:hidden property="intake.referralId"/>
<html:hidden property="intake.queueId"/>
<html:hidden property="intake.clientId"/>
<html:hidden property="intake.id"/>
<html:hidden property="intake.createdOnTxt" />
<input type="hidden" name="method"/>
<script lang="javascript">
function submitForm(methodVal) {
	document.forms(0).method.value = methodVal;
	document.forms(0).submit();
}
</script>
<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
	<tr><th class="pageTitle" align="center">Client Management - Add/Edit Intake</th></tr>
	<tr>
		<td align="left" valign="middle" class="buttonBar3">
		&nbsp;Summary&nbsp;&nbsp;|&nbsp;&nbsp;
		History&nbsp;&nbsp;|&nbsp;&nbsp;
		<b>Intake</b>&nbsp;&nbsp;|&nbsp;&nbsp;
		Admission&nbsp;&nbsp;|&nbsp;&nbsp;
		Refer&nbsp;&nbsp;|&nbsp;&nbsp;
		Discharge&nbsp;&nbsp;|&nbsp;&nbsp;
		Service Restriction&nbsp;&nbsp;|&nbsp;&nbsp;
		Complaints
		</td>
	</tr>
	<tr>
		<td align="left" class="buttonBar"><a href='javascript:submitForm("save");'
			style="color:Navy;text-decoration:none;">
			<img border=0 src=<html:rewrite page="/images/Save16.png"/> />&nbsp;Save&nbsp;&nbsp;</a>|
            <html:link action="/PMmodule/QuatroIntake.do" name="actionParam" style="color:Navy;text-decoration:none;">
            <img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Close&nbsp;&nbsp;</html:link>
          <c:if test="${quatroIntakeEditForm.intake.id!=0}">
            |
            <html:link action="/PMmodule/QuatroFamilyIntake.do" name="actionParam" style="color:Navy;text-decoration:none;">
            <img border=0 src=<html:rewrite page="/images/sel.gif"/> />&nbsp;Family Intake</html:link>
          </c:if>    
            </td>
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
<tr><td colspan="4"><br><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Personal information</th></tr>
</table></div></td></tr>
<tr><td width="15%">First name*</td>
<td width="35%"><html-el:text property="client.firstName" size="20" maxlength="30" /></td>
<td width="19%">Gender*</td>
<td width="31%"><html-el:select property="client.sex">
   <html-el:optionsCollection property="optionList.gender" value="value" label="label"/>
</html-el:select></td></tr>
<tr><td>Last name*</td><td><html-el:text property="client.lastName" size="20" maxlength="30" /></td>
<td>Date of birth<br>(yyyy/mm/dd)</td><td>
<quatro:datePickerTag property="dob" width="65%" openerForm="quatroIntakeEditForm">
</quatro:datePickerTag>
</td></tr>
<tr><td>Alias</td>
<td><html-el:text size="30" maxlength="70" property="client.alias"/></td>
<td></td><td></td></tr>
</table>
<table width="100%" class="edit">
<tr><td colspan="2"><br><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Referred by</th></tr>
</table></div></td></tr>
<tr><td width="20%">Referred by</td>
<td width="80%"><html-el:select property="intake.referredBy">
<html-el:optionsCollection property="optionList.referredBy" value="value" label="label"/>
</html-el:select></td></tr>
<tr><td>Contact name</td>
<td><html-el:text property="intake.contactName" /></td></tr>
<tr><td>Contact number</td>
<td><html-el:text property="intake.contactNumber" /></td></tr>
<tr><td>Contact email</td>
<td><html-el:text property="intake.contactEmail" /></td></tr>
</table>

<table width="100%" class="edit">
<tr><td colspan="4"><br><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Other information</th></tr>
</table></div></td></tr>
<tr><td width="22%">Language</td>
<td width="32%"><quatro:lookupTag name="language" tableName="LNG" formProperty="quatroIntakeEditForm" 
   codeProperty ="code" bodyProperty="description" width="90%" codeWidth="1px" showCode="false" /></td>
<td width="19%">Youth</td>
<td width="27%"><html-el:select property="intake.youth">
   <html-el:option value="1">Yes</html-el:option>
   <html-el:option value="0">No</html-el:option>
</html-el:select></td></tr>
<tr><td>Aboriginal</td>
<td><html-el:select property="intake.aboriginal">
<html-el:optionsCollection property="optionList.aboriginal" value="value" label="label"/>
</html-el:select></td>
<td>Aboriginal other</td>
<td><html-el:text property="intake.aboriginalOther" /></td></tr>
<tr><td>VAW</td>
<td><html-el:select property="intake.VAW">
   <html-el:option value="1">Yes</html-el:option>
   <html-el:option value="0">No</html-el:option>
</html-el:select></td><td></td><td></td></tr>
<tr><td>Current sleeping arrangements</td>
<td cospan="2"><html-el:select property="intake.curSleepArrangement">
<html-el:optionsCollection property="optionList.curSleepArrangement" value="value" label="label"/>
</html-el:select></td><td></td></tr>
<tr><td>Have you stayed in a shelter Before?</td>
<td><html-el:select property="intake.inShelterBefore">
   <html-el:option value="Yes" key="1" />
   <html-el:option value="No" key="0" />
</html-el:select></td><td></td><td></td></tr>
<tr><td>Length of homelessness</td>
<td cospan="2"><html-el:select property="intake.lengthOfHomeless">
<html-el:optionsCollection property="optionList.lengthOfHomeless" value="value" label="label"/>
</html-el:select></td><td></td></tr>
<tr><td>Reason for homelessness</td>
<td cospan="2"><html-el:select property="intake.reasonForHomeless">
<html-el:optionsCollection property="optionList.reasonForHomeless" value="value" label="label"/>
</html-el:select></td><td></td></tr>
</table>

<table width="100%" class="edit">
<tr><td colspan="5"><br><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Presenting issues</th></tr>
</table></div></td></tr>
<tr><td width="15%">Pregnant</td>
<td width="20%"></td>
<td width="15%"><html-el:checkbox property="intake.pregnant" /></td>
<td width="35%">Disclosed substance abuse</td>
<td><html-el:checkbox property="intake.disclosedAbuse" /></td></tr>
<tr><td>Disability</td>
<td colspan="2"><html-el:text property="intake.disability" /></td>
<td>Observed substance abuse</td>
<td><html-el:checkbox property="intake.observedAbuse" /></td></tr>
<tr><td colspan="2">Disclosed mental health issues</td>
<td><html-el:checkbox property="intake.disclosedMentalIssue" /></td>
<td>Poor hygiene</td>
<td><html-el:checkbox property="intake.poorHygiene" /></td></tr>
<tr><td colspan="2">Observed mental health issues</td>
<td><html-el:checkbox property="intake.observedMentalIssue" /></td>
<td>Disclosed alcohol abuse</td>
<td><html-el:checkbox property="intake.disclosedAlcoholAbuse" /></td></tr>
<tr><td colspan="2"></td><td></td>
<td>Observed alcohol abuse</td>
<td><html-el:checkbox property="intake.observedAlcoholAbuse" /></td></tr>
</table>

<table width="100%" class="edit">
<tr><td colspan="4"><br><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Identification</th></tr>
</table></div></td></tr>
<tr><td width="30%">Birth Certificate</td>
<td width="50%"><html-el:text property="intake.birthCertificate" size="20" maxlength="30" /></td>
<td width="12%">On file?</td>
<td width="8%"><html-el:checkbox property="intake.birthCertificateYN" /></td></tr>
<tr><td>Social Insurance No.</td>
<td><html-el:text property="intake.SIN" size="20" maxlength="30" /></td>
<td>On file?</td>
<td><html-el:checkbox property="intake.SINYN" /></td></tr>
<tr><td>Health card No.</td>
<td><html-el:text property="intake.healthCardNo" size="20" maxlength="30" /></td>
<td>On file?</td>
<td><html-el:checkbox property="intake.healthCardNoYN" /></td></tr>
<tr><td>Driver's License No.</td>
<td><html-el:text property="intake.driverLicenseNo" size="20" maxlength="30" /></td>
<td>On file?</td>
<td><html-el:checkbox property="intake.driverLicenseNoYN" /></td></tr>
<tr><td>Canadian Citizenship Card</td>
<td><html-el:text property="intake.citizenCardNo" size="20" maxlength="30" /></td>
<td>On file?</td>
<td><html-el:checkbox property="intake.citizenCardNoYN" /></td></tr>
<tr><td>Native Reserve Card</td>
<td><html-el:text property="intake.nativeReserveNo" size="20" maxlength="30" /></td>
<td>On file?</td>
<td><html-el:checkbox property="intake.nativeReserveNoYN" /></td></tr>
<tr><td>Veteran No.</td>
<td><html-el:text property="intake.veteranNo" size="20" maxlength="30" /></td>
<td>On file?</td>
<td><html-el:checkbox property="intake.veteranNoYN" /></td></tr>
<tr><td>Record of Landing</td>
<td><html-el:text property="intake.recordLanding" size="20" maxlength="30" /></td>
<td>On file?</td>
<td><html-el:checkbox property="intake.recordLandingYN" /></td></tr>
<tr><td>Library Card</td>
<td><html-el:text property="intake.libraryCard" size="20" maxlength="30" /></td>
<td>On file?</td>
<td><html-el:checkbox property="intake.libraryCardYN" /></td></tr>
<tr><td>Other</td>
<td><html-el:text property="intake.idOther" size="20" maxlength="30" /></td>
<td></td><td></td></tr>
</table>

<table width="100%" class="edit">
<tr><td colspan="4"><br><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Additional information</th></tr>
</table></div></td></tr>
<tr><td width="20%">Source of income</td>
<td width="35%"><html-el:select property="intake.sourceIncome">
<html-el:optionsCollection property="optionList.sourceIncome" value="value" label="label"/>
</html-el:select></td>
<td width="8%">Income</td>
<td width="37%"><html-el:text property="intake.income" size="20" maxlength="30" /></td></tr>
<tr><td>Name and contact information for income worker (if applicable)</td>
<td colspan="3"><table style="background-color:#e0e0e0;" width="100%" cellpadding="1" cellspacing="1">
<tr><td>Name:<html-el:text property="intake.incomeWorkerName1" size="20" maxlength="30" />
Phone:<html-el:text property="intake.incomeWorkerPhone1" size="8" maxlength="20" />
Email:<html-el:text property="intake.incomeWorkerEmail1" size="15" maxlength="30" /></td></tr>
<tr><td>Name:<html-el:text property="intake.incomeWorkerName2" size="20" maxlength="30" />
Phone:<html-el:text property="intake.incomeWorkerPhone2" size="8" maxlength="20" />
Email:<html-el:text property="intake.incomeWorkerEmail2" size="15" maxlength="30" /></td></tr>
<tr><td>Name:<html-el:text property="intake.incomeWorkerName3" size="20" maxlength="30" />
Phone:<html-el:text property="intake.incomeWorkerPhone3" size="8" maxlength="20" />
Email:<html-el:text property="intake.incomeWorkerEmail3" size="15" maxlength="30" /></td></tr>
</table>
</td></tr>
<tr><td>Lived the last 12 months</td>
<td><html-el:select property="intake.livedBefore">
<html-el:optionsCollection property="optionList.livedBefore" value="value" label="label"/>
</html-el:select></td>
<td>Other</td>
<td><html-el:text property="intake.livedBeforeOther" size="20" maxlength="30" /></td></tr>
<tr><td>Status in Canada</td>
<td><html-el:select property="intake.statusInCanada">
<html-el:optionsCollection property="optionList.statusInCanada" value="value" label="label"/>
</html-el:select></td>
<td></td><td></td></tr>
<tr><td>Country of origin</td>
<td><quatro:lookupTag name="originalCountry" tableName="CNT" formProperty="quatroIntakeEditForm" 
   codeProperty ="code" bodyProperty="description" width="90%" codeWidth="1px" showCode="false" /></td>
<td></td><td></td></tr>
<tr><td>Referred to</td>
<td><html-el:select property="intake.referredTo">
<html-el:optionsCollection property="optionList.referredTo" value="value" label="label"/>
</html-el:select></td>
<td></td><td></td></tr>
<tr><td>Reason for non-admittance</td>
<td><html-el:select property="intake.reasonNoAdmit">
<html-el:optionsCollection property="optionList.reasonNoAdmit" value="value" label="label"/>
</html-el:select></td>
<td></td><td></td></tr>
</table>

<table width="100%" class="edit">
<tr><td colspan="2"><br><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Program</th></tr>
</table></div></td></tr>
<tr><td width="15%">Program</td>
<td width="85%"><html-el:hidden property="intake.currentProgramId" />
<html-el:select property="intake.programId">
<html-el:optionsCollection property="programList" value="value" label="label" />
</html-el:select></td></tr>
</table>

<table width="100%" class="edit">
<tr><td colspan="2"><br><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Comments/Details</th></tr>
</table></div></td></tr>
<tr><td width="15%">Comments/Details</td>
<td width="85%"><html-el:textarea property="intake.comments" rows="6" style="width:90%" /></td></tr>
<tr><td></td><td align="center">&nbsp;</td></tr>
</table>
<!--  end of page content -->
</div>
</td>
</tr>
</table>
</html-el:form>
