<%@ include file="/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/quatro-tag.tld" prefix="quatro" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<script type="text/javascript" src='<c:out value="${ctx}"/>/js/quatroLookup.js'></script>

<bean:define id="language" name="quatroIntakeEditForm" property="language" type="com.quatro.common.LookupTagValue" />
<bean:define id="originalCountry" name="quatroIntakeEditForm" property="originalCountry" />
 
<html-el:form action="/PMmodule/QuatroIntake/Edit.do">
<html:hidden property="type"/>
<html:hidden property="clientId"/>
<html:hidden property="intakeId"/>
<table width="100%" class="edit">
<tr><td colspan="4"><br><div class="tabs" id="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th title="LookupTableList">Personal information</th></tr>
</table></div></td></tr>
<tr><td width="15%">First name*</td>
<td width="35%"><html-el:text property="client.firstName" size="20" maxlength="30" /></td>
<td width="19%">Gender*</td>
<td width="31%"><html-el:select property="client.sex">
   <html-el:optionsCollection property="optionList.gender" value="value" label="label"/>
</html-el:select></td></tr>
<tr><td>Last name*</td><td><html-el:text property="client.lastName" size="20" maxlength="30" /></td>
<td>Date of birth<br>(yyyy-mm-dd)</td><td>
<quatro:datePickerTag property="dob" width="65%" openerForm="quatroIntakeEditForm">
</quatro:datePickerTag>
</td></tr>
<tr><td>Alias</td>
<td><html-el:text size="30" maxlength="70" property="client.alias"/></td>
<td></td><td></td></tr>
</table>

<table width="100%" class="edit">
<tr><td colspan="2"><br><div class="tabs" id="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th title="LookupTableList">Referred by</th></tr>
</table></div></td></tr>
<tr><td width="20%">Referred by</td>
<td width="80%"><html-el:select property="referredBy">
<html-el:optionsCollection property="optionList.referredBy" value="value" label="label"/>
</html-el:select></td></tr>
<tr><td>Contact name</td>
<td><html-el:text property="contactName" /></td></tr>
<tr><td>Contact number</td>
<td><html-el:text property="contactNumber" /></td></tr>
<tr><td>Contact email</td>
<td><html-el:text property="contactEmail" /></td></tr>
</table>

<table width="100%" class="edit">
<tr><td colspan="4"><br><div class="tabs" id="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th title="LookupTableList">Other information</th></tr>
</table></div></td></tr>
<tr><td width="22%">Language</td>
<td width="32%"><quatro:lookupTag name="language" tableName="LNG" formProperty="quatroIntakeEditForm" 
   codeProperty ="val" bodyProperty="valDesc" width="90%" codeWidth="1px" /></td>
<td width="19%">Youth</td>
<td width="27%"><html-el:select property="youth">
   <html-el:option value="Y" key="1" />
   <html-el:option value="N" key="0" />
</html-el:select></td></tr>
<tr><td>Aboriginal</td>
<td><html-el:select property="aboriginal">
<html-el:optionsCollection property="optionList.aboriginal" value="value" label="label"/>
</html-el:select></td>
<td>Aboriginal other</td>
<td><html-el:text property="aboriginalOther" /></td></tr>
<tr><td>VAW</td>
<td><html-el:select property="VAW">
   <html-el:option value="Y" key="1" />
   <html-el:option value="N" key="0" />
</html-el:select></td><td></td><td></td></tr>
<tr><td>Current sleeping arrangements</td>
<td cospan="2"><html-el:select property="curSleepArrangement">
<html-el:optionsCollection property="optionList.curSleepArrangement" value="value" label="label"/>
</html-el:select></td><td></td></tr>
<tr><td>Have you stayed in a shelter Before?</td>
<td><html-el:select property="inShelterBefore">
   <html-el:option value="Y" key="1" />
   <html-el:option value="N" key="0" />
</html-el:select></td><td></td><td></td></tr>
<tr><td>Length of homelessness</td>
<td cospan="2"><html-el:select property="lengthOfHomeless">
<html-el:optionsCollection property="optionList.lengthOfHomeless" value="value" label="label"/>
</html-el:select></td><td></td></tr>
<tr><td>Reason for homelessness</td>
<td cospan="2"><html-el:select property="reasonForHomeless">
<html-el:optionsCollection property="optionList.reasonForHomeless" value="value" label="label"/>
</html-el:select></td><td></td></tr>
</table>

<table width="100%" class="edit">
<tr><td colspan="5"><br><div class="tabs" id="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th title="LookupTableList">Presenting issues</th></tr>
</table></div></td></tr>
<tr><td width="15%">Pregnant</td>
<td width="20%"></td>
<td width="15%"><html-el:checkbox property="pregnant" /></td>
<td width="35%">Disclosed substance abuse</td>
<td><html-el:checkbox property="disclosedAbuse" /></td></tr>
<tr><td>Disability</td>
<td colspan="2"><html-el:text property="disability" /></td>
<td>Observed substance abuse</td>
<td><html-el:checkbox property="observedAbuse" /></td></tr>
<tr><td colspan="2">Disclosed mental health issues</td>
<td><html-el:checkbox property="disclosedMentalIssue" /></td>
<td>Poor hygiene</td>
<td><html-el:checkbox property="poorHygiene" /></td></tr>
<tr><td colspan="2">Observed mental health issues</td>
<td><html-el:checkbox property="observedMentalIssue" /></td>
<td>Disclosed alcohol abuse</td>
<td><html-el:checkbox property="disclosedAlcoholAbuse" /></td></tr>
<tr><td colspan="2"></td><td></td>
<td>Observed alcohol abuse</td>
<td><html-el:checkbox property="observedAlcoholAbuse" /></td></tr>
</table>

<table width="100%" class="edit">
<tr><td colspan="4"><br><div class="tabs" id="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th title="LookupTableList">Identification</th></tr>
</table></div></td></tr>
<tr><td width="30%">Birth Certificate</td>
<td width="50%"><html-el:text property="birthCertificate" size="20" maxlength="30" /></td>
<td width="12%">On file?</td>
<td width="8%"><html-el:checkbox property="birthCertificateYN" /></td></tr>
<tr><td>Social Insurance No.</td>
<td><html-el:text property="SIN" size="20" maxlength="30" /></td>
<td>On file?</td>
<td><html-el:checkbox property="SINYN" /></td></tr>
<tr><td>Health card No.</td>
<td><html-el:text property="healthCardNo" size="20" maxlength="30" /></td>
<td>On file?</td>
<td><html-el:checkbox property="healthCardNoYN" /></td></tr>
<tr><td>Driver's License No.</td>
<td><html-el:text property="driverLicenseNo" size="20" maxlength="30" /></td>
<td>On file?</td>
<td><html-el:checkbox property="driverLicenseNoYN" /></td></tr>
<tr><td>Canadian Citizenship Card</td>
<td><html-el:text property="citizenCardNo" size="20" maxlength="30" /></td>
<td>On file?</td>
<td><html-el:checkbox property="citizenCardNoYN" /></td></tr>
<tr><td>Native Reserve Card</td>
<td><html-el:text property="nativeReserveNo" size="20" maxlength="30" /></td>
<td>On file?</td>
<td><html-el:checkbox property="nativeReserveNoYN" /></td></tr>
<tr><td>Veteran No.</td>
<td><html-el:text property="veteranNo" size="20" maxlength="30" /></td>
<td>On file?</td>
<td><html-el:checkbox property="veteranNoYN" /></td></tr>
<tr><td>Record of Landing</td>
<td><html-el:text property="recordLanding" size="20" maxlength="30" /></td>
<td>On file?</td>
<td><html-el:checkbox property="recordLandingYN" /></td></tr>
<tr><td>Library Card</td>
<td><html-el:text property="libraryCard" size="20" maxlength="30" /></td>
<td>On file?</td>
<td><html-el:checkbox property="libraryCardYN" /></td></tr>
<tr><td>Other</td>
<td><html-el:text property="idOther" size="20" maxlength="30" /></td>
<td></td><td></td></tr>
</table>

<table width="100%" class="edit">
<tr><td colspan="4"><br><div class="tabs" id="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th title="LookupTableList">Additional information</th></tr>
</table></div></td></tr>
<tr><td width="20%">Source of income</td>
<td width="35%"><html-el:select property="sourceIncome">
<html-el:optionsCollection property="optionList.sourceIncome" value="value" label="label"/>
</html-el:select></td>
<td width="8%">Income</td>
<td width="37%"><html-el:text property="income" size="20" maxlength="30" /></td></tr>
<tr><td>Name and contact information for income worker (if applicable)</td>
<td colspan="3"><table style="background-color:#e0e0e0;" width="100%" cellpadding="1" cellspacing="1">
<tr><td>Name:<html-el:text property="incomeWorkerName1" size="20" maxlength="30" />
Phone:<html-el:text property="incomeWorkerPhone1" size="8" maxlength="20" />
Email:<html-el:text property="incomeWorkerEmail1" size="15" maxlength="30" /></td></tr>
<tr><td>Name:<html-el:text property="incomeWorkerName2" size="20" maxlength="30" />
Phone:<html-el:text property="incomeWorkerPhone2" size="8" maxlength="20" />
Email:<html-el:text property="incomeWorkerEmail2" size="15" maxlength="30" /></td></tr>
<tr><td>Name:<html-el:text property="incomeWorkerName3" size="20" maxlength="30" />
Phone:<html-el:text property="incomeWorkerPhone3" size="8" maxlength="20" />
Email:<html-el:text property="incomeWorkerEmail3" size="15" maxlength="30" /></td></tr>
</table>
</td></tr>
<tr><td>Lived the last 12 months</td>
<td><html-el:select property="livedBefore">
<html-el:optionsCollection property="optionList.livedBefore" value="value" label="label"/>
</html-el:select></td>
<td>Other</td>
<td><html-el:text property="livedBeforeOther" size="20" maxlength="30" /></td></tr>
<tr><td>Status in Canada</td>
<td><html-el:select property="statusInCanada">
<html-el:optionsCollection property="optionList.statusInCanada" value="value" label="label"/>
</html-el:select></td>
<td></td><td></td></tr>
<tr><td>Coutry of origin</td>
<td><quatro:lookupTag name="originalCountry" tableName="CNT" formProperty="quatroIntakeEditForm" 
   codeProperty ="val" bodyProperty="valDesc" width="90%" codeWidth="1px" /></td>
<td></td><td></td></tr>
<tr><td>Referred to</td>
<td><html-el:select property="referredTo">
<html-el:optionsCollection property="optionList.referredTo" value="value" label="label"/>
</html-el:select></td>
<td></td><td></td></tr>
<tr><td>Reason for Non-Admittance</td>
<td><html-el:select property="reasonNoAdmit">
<html-el:optionsCollection property="optionList.reasonNoAdmit" value="value" label="label"/>
</html-el:select></td>
<td></td><td></td></tr>
</table>

<table width="100%" class="edit">
<tr><td colspan="2"><br><div class="tabs" id="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th title="LookupTableList">Program</th></tr>
</table></div></td></tr>
<tr><td width="15%">Program</td>
<td width="85%"><html-el:select property="program">
<html-el:optionsCollection property="programList" value="value" label="label"/>
</html-el:select></td></tr>
</table>

<table width="100%" class="edit">
<tr><td colspan="2"><br><div class="tabs" id="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th title="LookupTableList">Comments/Details</th></tr>
</table></div></td></tr>
<tr><td width="15%">Comments/Details</td>
<td width="85%"><html-el:textarea property="comments" rows="6" style="width:90%" /></td></tr>
<tr><td colspan="2"><hr></td></tr>
<tr><td></td><td align="center"><html-el:submit property="method" value="save" /></td></tr>
</table>

</html-el:form>
