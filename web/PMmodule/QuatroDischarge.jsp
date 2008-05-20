<%@ include file="/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/quatro-tag.tld" prefix="quatro"%>

<c:set var="ctx" value="${pageContext.request.contextPath}"
	scope="request" />
<script type="text/javascript"
	src='<c:out value="${ctx}"/>/js/quatroLookup.js'></script>

<script lang="javascript">
	function submitForm(methodVal) {
		document.forms(0).method.value = methodVal;
		document.forms(0).submit();
	}
</script>

<html-el:form action="/PMmodule/QuatroDischarge.do">
<html:hidden property="program.id" />
	<input type="hidden" name="method" />
		<table width="100%" cellpadding="0px" cellspacing="0px">
		<tr>
			<th class="pageTitle" align="center">Client Management -Discharge Edit</th>
		</tr>
		<tr>
	<td>
		<table width="100%" class="simple">
			<tr>
			<td style="width: 15%"><font><b>Client No.</b></font></td><td colspan="3"><font><b><c:out value="${client.demographicNo}" /></b></font></td>
			</tr>
			<tr>
				<td style="width: 15%"<font><b>Name</b></font></td>
				<td style="width: 35%"><font><b><c:out value="${client.formattedName}" /></b></font></td>
				<td style="width: 15%"><font><b>Date of Birth </b></font></td>
				<td style="width: 35%"><font><b><c:out value="${client.yearOfBirth}" />/<c:out value="${client.monthOfBirth}" />/<c:out value="${client.dateOfBirth}" /></b></font></td>
			</tr>
		</table>
	</td>
	</tr>
		<tr>
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
			<b>Discharge</b>&nbsp;&nbsp;|&nbsp;&nbsp; <html:link
				action="/PMmodule/QuatroServiceRestriction.do" name="actionParam"
				style="color:Navy;text-decoration:none;">Service Restriction</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
			<html:link action="/PMmodule/QuatroComplaint.do" name="actionParam"
				style="color:Navy;text-decoration:none;">Complaints</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroConsent.do" name="actionParam" style="color:Navy;text-decoration:none;">Consent</html:link>
				</td>
		</tr>
		<tr>
			<td align="left" class="buttonBar"><a
				href='javascript:submitForm("save");'
				style="color:Navy;text-decoration:none;"> <img border=0
				src=<html:rewrite page="/images/Save16.png"/> />&nbsp;Save&nbsp;&nbsp;</a>|
			<html:link action="/PMmodule/ClientSearch2.do"
				style="color:Navy;text-decoration:none;">
				<img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Close&nbsp;&nbsp;</html:link></td>
		</tr>
		<tr>
			<td align="left" class="message"><logic:messagesPresent
				message="true">
				<html:messages id="message" message="true" bundle="pmm">
					<c:out escapeXml="false" value="${message}" />
				</html:messages>
			</logic:messagesPresent></td>
		</tr>
	</table>
	<br>
	<div class="tabs">
	<table cellpadding="3" cellspacing="0" border="0">
		<tr>
			<th>Discharge</th>
		</tr>
	</table>
	</div>
			<!--  start of page content -->

		<table width="100%" class="edit">
			<tr>
				<td width="25%" >Discharge To Community Program</td>
				<td width="75%">
					<html:select	property="admission.communityProgramCode">
					<html:option value=""></html:option>
					<html:options collection="lstCommProgram" property="code"
						labelProperty="description"></html:options>
				</html:select></td>
			</tr>
			<tr>
				<td width="25%">Discharge To Temporary Program</td>
				<td width="75%">
				<html:select property="admission.bedProgramId">
					<html:option value=""></html:option>
					<html:options collection="lstBedProgram" property="id"
						labelProperty="descr"></html:options>
				</html:select>
			</tr>
			<tr>
				<td width="25%">Discharge Reason</td>
				<td><html:select property="admission.radioDischargeReason">
					<html:option value=""></html:option>
					<html:options collection="lstDischargeReason" property="code"
						labelProperty="description"></html:options>
				</html:select></td>
			</tr>
			<tr>
				<td width="25%">Transportation Type Provided</td>
				<td><html:select property="admission.transportationType">
					<html:option value=""></html:option>
					<html:options collection="lstTransType" property="code"
						labelProperty="description"></html:options>
				</html:select></td>
			</tr>
			<tr>
				<td width="25%">Discharge Notes</td>
				<td><html:textarea cols="50" rows="7"
					property="admission.dischargeNotes" /></td>
			</tr>

		</table>
	
</html-el:form>
