<%@ include file="/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/caisi-tag.tld" prefix="caisi" %>
<%@ taglib uri="/WEB-INF/security.tld" prefix="security" %>
<%@page import="org.oscarehr.util.SpringUtils"%>
<head>
<title>Program Search</title>
<meta http-equiv="Cache-Control" content="no-cache">
<link rel="stylesheet" type="text/css" href='<html:rewrite page="/css/displaytag.css" />' />
<link rel="stylesheet" type="text/css" href='<html:rewrite page="/jsCalendar/skins/aqua/theme.css" />' />
<link rel="stylesheet" type="text/css" href='<html:rewrite page="/css/core.css" />' />
<script language="javascript" type="text/javascript">
	function resetSearchFields() {
		var form = document.programSearchForm;
		form.elements['program.name'].value='';	
		form.elements['program.facilityId'].value='';
		var typeEl = form.elements['program.type'];
		typeEl.options[typeEl.selectedIndex].value='T';
		var manOrWomanEl = form.elements['program.manOrWoman'];
		manOrWomanEl.options[manOrWomanEl.selectedIndex].value='';
 		form.elements['program.ageMin'].value='1';
 		form.elements['program.ageMax'].value='200'; 		
			
	}
		function selectProgram(clientId,id,type) {
			var programId=Number(id);
			if (gender == 'M')
			{
				if (programFemaleOnly.indexOf(programId)>=0 ||  programTransgenderOnly.indexOf(programId)>=0)
				{
					return error("This gender not allowed in selected program.");
				}
			}
			if (gender == 'F')
			{
				if (programMaleOnly.indexOf(programId)>=0 ||  programTransgenderOnly.indexOf(programId)>=0)
				{
					return error("This gender not allowed in selected program.");
				}
			}
			if (gender == 'T')
			{
				if (programFemaleOnly.indexOf(programId)>=0 ||  programMaleOnly.indexOf(programId)>=0)
				{
					return error("This gender not allowed in selected program.");
				}
			}		
		
			if (!validAgeRangeForProgram(programId,age))
			{
				return error("This client does not meet the age range requirements for this program.");
			}
		
			opener.document.<%=request.getParameter("formName")%>.elements['<%=request.getParameter("formElementId")%>'].value=id;
			opener.document.<%=request.getParameter("formName")%>.elements['id'].value=clientId;
			
			<% if(request.getParameter("submit") != null && request.getParameter("submit").equals("true")) { %>
				opener.document.<%=request.getParameter("formName")%>.submit();
			<% } %>
			
			self.close();
		}
		function search_programs() {
		var form = document.programSearchForm;
		
		form.method.value='refer_select_program';
		var programName = form.elements['program.name'].value;
		var facilityId =form.elements['program.facilityId'].value;
		var typeEl = form.elements['program.type'];
		var programType = typeEl.options[typeEl.selectedIndex].value;
		var manOrWomanEl = form.elements['program.manOrWoman'];
		var manOrWoman = manOrWomanEl.options[manOrWomanEl.selectedIndex].value;
 		var ageMin = form.elements['program.ageMin'].value;
 		var ageMax = form.elements['program.ageMax'].value;
 		var id = form.elements['id'].value;		
		
		var url = '<html:rewrite action="/PMmodule/QuatroProgramSearch.do"/>';
		url += '?method=search_programs&program.name=' + programName + '&program.type=' + programType;
		url += '&program.manOrWoman='+manOrWoman+'&program.ageMin='+ageMin+'&program.ageMax='+ageMax;
		url += '&formName=programSearchForm&formElementName=program.name&formElementId=program.id&formElementType=program.type&submit=true';
		url += '&id=' + id;
		
		window.open(url, "program_search", "width=800, height=600, scrollbars=1,location=1,status=1");
	}
	function submitForm(methodVal) {
		document.forms(0).method.value = methodVal;
		document.forms(0).submit();
	}
	
</script>
</head>
<html:form action="/PMmodule/QuatroProgramSearch.do">
	<input type="hidden" name="method" />	
	<table width="100%" cellpadding="0px" cellspacing="0px">
	<tr>
			<th class="pageTitle">Program Search</th>
		</tr>
		<tr>
		<td class="buttonBar" align="left" height="18px">		
		<a href="javascript:submitForm('search_programs')" style="color:Navy;text-decoration:none;">
		<img border=0 src=<html:rewrite page="/images/search16.gif"/> height="16px" width="16px"/>&nbsp;Search&nbsp;&nbsp;|</a>
		<a style="color:Navy;text-decoration:none;" href="javascript:resetSearchFields();">
		<img border=0 src=<html:rewrite page="/images/searchreset.gif" /> height="16px" width="16px"/>&nbsp;Reset&nbsp;&nbsp;</a>
		<br>		
		</td>
		</tr>
<!--  start of page content -->		
		<tr><td>
		</table>
		<html:hidden property="program.id" />
		<%String a="12"; %>
		<table class="simple" width="100%" cellspacing="2" cellpadding="3">
		  <tr><td width="30%">Program Name</td>
  				<td width="70%"><html:text property="program.name" /></td></tr>
  		 <tr><td>Program Type</td>
  			<td><html:select property="program.type">
					<html:option value="">&nbsp;</html:option>
					<html:option value="Bed">Bed</html:option>
					<html:option value="Service">Service</html:option>
				</html:select>
			</td>
		</tr>
		
  		<tr><td>Gender</td>
  			<td><html:select property="program.manOrWoman">
				<html:option value="T">&nbsp;</html:option>				
				<html:option value="M">Man</html:option>
				<html:option value="W">Woman</html:option>
				</html:select>
			</td></tr>
  		<tr><td>Minimun Age (inclusive)</td>
  				<td><html:text property="program.ageMin" /></td></tr>
  		<tr><td>Maximum Age (inclusive)</td>
  			<td><html:text property="program.ageMax" /></td></tr>
  		<tr><td><input type="button" value="search" onclick="javaScript:submitForm('search_programs')" />&nbsp;&nbsp;<input type="button" name="reset" value="reset" onclick="javascript:resetClientFields();" /></td></tr>
 	</table>
	</html:form>
		
		<display:table class="simple" cellspacing="2" cellpadding="3" id="program" name="programs" pagesize="200" requestURI="/PMmodule/QuatroProgramSearch.do">
			<display:setProperty name="paging.banner.placement" value="bottom" />
			<display:column sortable="true" title="Name">
				<a href="#javascript:void(0);" onclick="selectProgram('<c:out value="${id}" />','<c:out value="${program.id}" />','<c:out value="${program.type}" />');"><c:out value="${program.name}" /></a>
			</display:column>
			<display:column property="type" sortable="true" title="Type"></display:column>
			<display:column sortable="false" title="Participation">
				<c:out value="${program.numOfMembers}" />/<c:out value="${program.maxAllowed}" />&nbsp;(<c:out value="${program.queueSize}" /> waiting)
			</display:column>
			<display:column property="descr" sortable="false" title="Description"></display:column>
		</display:table>	

