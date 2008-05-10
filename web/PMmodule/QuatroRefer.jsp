<%@ include file="/taglibs.jsp" %>
<%@page import="org.oscarehr.PMmodule.model.Admission"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/WEB-INF/caisi-tag.tld" prefix="caisi" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<script type="text/javascript" src='<c:out value="${ctx}"/>/js/quatroLookup.js'></script>
 
<html-el:form action="/PMmodule/QuatroRefer.do">
<input type="hidden" name="method"/>
<html:hidden property="clientId"/>
<script>
	function resetClientFields() {
		var form = document.quatroClientReferForm;
		form.elements['program.name'].value='';
	}

	function search_programs() {
		var form = document.quatroClientReferForm;
		
		form.method.value='refer_select_program';
		var programName = form.elements['program.name'].value;
		var typeEl = form.elements['program.type'];
		var programType = typeEl.options[typeEl.selectedIndex].value;
		var manOrWomanEl = form.elements['program.manOrWoman'];
		var manOrWoman = manOrWomanEl.options[manOrWomanEl.selectedIndex].value;
 		var transgender = form.elements['program.transgender'].checked;
 		var firstNation = form.elements['program.firstNation'].checked;
 		var bedProgramAffiliated = form.elements['program.bedProgramAffiliated'].checked;
 		var alcohol = form.elements['program.alcohol'].checked;
 		var abstinenceSupportEl = form.elements['program.abstinenceSupport'];
 		var abstinenceSupport = abstinenceSupportEl.options[abstinenceSupportEl.selectedIndex].value;
 		var physicalHealth = form.elements['program.physicalHealth'].checked;
 		var mentalHealth = form.elements['program.mentalHealth'].checked;
 		var housing = form.elements['program.housing'].checked;		
 		var id = form.elements['id'].value;		
		
		var url = '<html:rewrite action="/PMmodule/QuatroRefer.do"/>';
		url += '?method=search_programs&program.name=' + programName + '&program.type=' + programType;
		url += '&program.manOrWoman='+manOrWoman+'&program.transgender='+transgender+'&program.firstNation='+firstNation+'&program.bedProgramAffiliated='+bedProgramAffiliated+'&program.alcohol='+alcohol+'&program.abstinenceSupport='+abstinenceSupport+'&program.physicalHealth='+physicalHealth+'&program.mentalHealth='+mentalHealth+'&program.housing='+housing;
		url += '&formName=quatroClientReferForm&formElementName=program.name&formElementId=program.id&formElementType=program.type&submit=true';
		url += '&id=' + id;
		
		window.open(url, "program_search", "width=800, height=600, scrollbars=1,location=1,status=1");
	}

	function do_referral() {
		var form = document.quatroClientReferForm;
		form.method.value='refer';
		form.submit();
	}
</script>
<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
	<tr>
		<th class="pageTitle" align="center">Client Management - Refer</th>
	</tr>
	<tr>
		<td align="left" valign="middle" class="buttonBar2">
		&nbsp;<html:link action="/PMmodule/QuatroClientSummary.do" name="actionParam" style="color:Navy;text-decoration:none;">Summary</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroHistory.do" name="actionParam" style="color:Navy;text-decoration:none;">History</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroIntake.do" name="actionParam" style="color:Navy;text-decoration:none;">Intake</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroAdmission.do" name="actionParam" style="color:Navy;text-decoration:none;">Admission</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<b>Refer</b>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroDischarge.do" name="actionParam" style="color:Navy;text-decoration:none;">Discharge</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroServiceRestriction.do" name="actionParam" style="color:Navy;text-decoration:none;">Service Restriction</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroComplaint.do" name="actionParam" style="color:Navy;text-decoration:none;">Complaints</html:link>
		</td>
	</tr>
	<tr>
		<td align="left" class="buttonBar">
		<html:link action="/PMmodule/ClientSearch2.do" style="color:Navy;text-decoration:none;">
		<img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Close&nbsp;&nbsp;</html:link></td>
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
<tr><td><br><div class="tabs" id="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th title="LookupTableList">Search Program</th></tr>
</table></div></td></tr>

<tr><td>
<html:hidden property="program.id" />
<table class="simple" cellspacing="2" cellpadding="3">
  <tr><td width="30%">Program Name</td>
  <td width="70%"><html:text property="program.name" /></td></tr>
  <tr><td>Program Type</td>
  <td><html:select property="program.type">
	<html:option value="">&nbsp;</html:option>
	<html:option value="Bed">Bed</html:option>
	<html:option value="Service">Service</html:option>
	</html:select></td></tr>
  <tr><td>Man or Woman:</td>
  <td><html:select property="program.manOrWoman">
	<html:option value="">&nbsp;</html:option>				
	<html:option value="Man">Man</html:option>
	<html:option value="Woman">Woman</html:option>
	</html:select></td></tr>
  <tr><td>Transgender:</td>
  <td><html:checkbox property="program.transgender" /></td></tr>
  <tr><td>First Nation:</td>
  <td><html:checkbox property="program.firstNation" /></td></tr>
  <tr><td>Bed Program Affiliated:</td>
  <td><html:checkbox property="program.bedProgramAffiliated" /></td></tr>
  <tr><td>Alcohol:</td>
  <td><html:checkbox property="program.alcohol" /></td></tr>
  <tr><td>Abstinence Support?</td>
  <td><html:select property="program.abstinenceSupport">
	<html:option value="">&nbsp;</html:option>
	<html:option value="Harm Reduction" />
	<html:option value="Abstinence Support" />
	<html:option value="Not Applicable" />			
	</html:select></td></tr>
  <tr><td>Physical Health:</td>
  <td><html:checkbox property="program.physicalHealth" /></td></tr>
  <tr><td>Mental Health:</td>
  <td><html:checkbox property="program.mentalHealth" /></td></tr>
  <tr><td>Housing:</td>
  <td><html:checkbox property="program.housing" /></td></tr>	

  <tr><td><input type="button" value="search" onclick="search_programs()" />&nbsp;&nbsp;<input type="button" name="reset" value="reset" onclick="javascript:resetClientFields();" /></td></tr>
 </table>
</td></tr>

<c:if test="${requestScope.do_refer != null}">
<tr><td><br><div class="tabs" id="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th title="LookupTableList">Referral</th></tr>
</table></div></td></tr>

<tr><td>
  <table class="simple" cellspacing="2" cellpadding="3">
	<tr><th style="color:black">Program Name</th>
	<th style="color:black">Type</th>
	<th style="color:black">Participation</th>
	<th style="color:black">Phone</th>
	<th style="color:black">Email</th></tr>
	<tr><td><c:out value="${program.name }" /></td>
	<td><c:out value="${program.type }" /></td>
	<td><c:out value="${program.numOfMembers}" />/<c:out value="${program.maxAllowed}" /> (<c:out value="${program.queueSize}" /> waiting)</td>
	<td><c:out value="${program.phone }" /></td>
	<td><c:out value="${program.email }" /></td></tr>
 </table>
</td></tr>

<tr><td>
  <table class="simple" cellspacing="2" cellpadding="3">
	<tr><td width="20%">Reason for referral:</td>
	<td><html:textarea cols="50" rows="7" property="referral.notes" /></td></tr>
	<tr><td width="20%">Presenting Problems:</td>
	<td><html:textarea cols="50" rows="7" property="referral.presentProblems" /></td></tr>
	<c:if test="${program.type eq 'Bed' }">
	  <caisi:isModuleLoad moduleName="pmm.refer.temporaryAdmission.enabled">
		<tr><td width="20%">Request Temporary Admission:</td>
		<td><html:checkbox property="referral.temporaryAdmission" /></td></tr>
	  </caisi:isModuleLoad>
	</c:if>
	<tr><td colspan="2"><input type="button" value="Process Referral" onclick="do_referral()" /> <input type="button" value="Cancel" onclick="document.clientManagerForm.submit()" /></td></tr>
  </table>
</td></tr>
</c:if>

</table>

<!--  end of page content -->
</div>
</td>
</tr>
</table>
</html-el:form>
