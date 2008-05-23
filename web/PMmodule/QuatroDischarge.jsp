<%@ include file="/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/quatro-tag.tld" prefix="quatro"%>

<c:set var="ctx" value="${pageContext.request.contextPath}"
	scope="request" />
<script type="text/javascript"
	src='<c:out value="${ctx}"/>/js/quatroLookup.js'></script>

<script lang="javascript">
	function submitForm(methodVal) {
		document.forms[0].method.value = methodVal;
		document.forms[0].submit();
	}
	
	function validateProgram()
	{
		if(
			(document.quatroClientDischargeForm.admission.communityProgramCode==null || 
				document.quatroClientDischargeForm.admission.communityProgramCode=="") && 
			(document.quatroClientDischargeForm.admission.bedProgramId==null || 
				document.quatroClientDischargeForm.admission.bedProgramId=="")
		    )	
			return false;		
		else if((document.quatroClientDischargeForm.admission.communityProgramCode!="") && 
			(document.quatroClientDischargeForm.admission.bedProgramId!="")) 
			return false;
		else return true;	
	}
	function validateSave(){
	
		var str1=" Please select Community Program or Temporary Program before save." ;
		
		if (!validateProgram()){
			alert(str1); return false;
		}
		return true;
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
			<td class="simple" style="background: lavender"><%@ include file="ClientInfo.jsp" %></td>
		</tr>
		<tr>
			<td align="left" class="buttonBar">
				<a onclick='return validateSave();'	href='javascript:submitForm("save");'	style="color:Navy;text-decoration:none;"> 
					<img border=0	src=<html:rewrite page="/images/Save16.png"/> />&nbsp;Save&nbsp;&nbsp;</a>|
				<a href='javascript:submitForm("list");'	style="color:Navy;text-decoration:none;">
					<img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Close&nbsp;&nbsp;</a></td>
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
				<td><html:textarea cols="60" rows="7"
					property="admission.dischargeNotes" /></td>
			</tr>
		</table>
	
</html-el:form>
