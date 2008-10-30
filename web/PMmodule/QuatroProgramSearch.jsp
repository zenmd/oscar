<%@ include file="/taglibs.jsp"%>
<%@page import="org.oscarehr.util.SpringUtils"%>
<head>
<title>Program Search</title>
<meta http-equiv="Cache-Control" content="no-cache">
<link rel="stylesheet" type="text/css" href='<html:rewrite page="/css/core.css" />' />
<link rel="stylesheet" type="text/css" href='<html:rewrite page="/css/displaytag.css" />' />
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<script type="text/javascript" src='<c:out value="${ctx}"/>/js/validation.js'></script> 
<script language="javascript" type="text/javascript">	
	/* var gender='<%=request.getSession(true).getAttribute("clientGender")%>'; */
	var age=<%=request.getAttribute("age")%>;
		
	var programMaleOnly=<%=session.getAttribute("programMaleOnly")%>;
    var programFemaleOnly=<%=session.getAttribute("programFemaleOnly")%>;
    var programTransgenderOnly=<%=session.getAttribute("programTransgenderOnly")%>;

	<%=session.getAttribute("programAgeValidationMethod")%>

	function error(msg) {
			alert(msg);
			return false;
	}

	if (!Array.prototype.indexOf)
	{
  		Array.prototype.indexOf = function(elt /*, from*/)
  		{
    		var len = this.length;
    		var from = Number(arguments[1]) || 0;
    		from = (from < 0) ? Math.ceil(from) : Math.floor(from);
    		if (from < 0)	 from += len;
    		for (; from < len; from++)
    		{
      			if (from in this &&  this[from] === elt)
        		return from;
    		}
    		return -1;
 		};
	}
		
	
	function resetSearchFields() {
		var form = document.programSearchForm;
		form.elements['program.name'].value='';	
		form.elements['program.facilityId'].value='';		
		//var typeEl = form.elements['program.type'];
		//typeEl.options[typeEl.selectedIndex].value='T';
		//var manOrWomanEl = form.elements['program.manOrWoman'];
		//manOrWomanEl.options[manOrWomanEl.selectedIndex].value='';
 		form.elements['program.ageMin'].value='1';
 		form.elements['program.ageMax'].value='100'; 		
			
	}
	function selectProgram(clientId,id,type,gender) {
		var programId=Number(id);	
		var ruleCheck =	opener.document.<%=request.getParameter("formName")%>.elements['ruleCheck'].value;
		if(ruleCheck!=null && ruleCheck=='Y')
		{
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
		}
		opener.setNoConfirm();
		opener.document.<%=request.getParameter("formName")%>.elements['pageChanged'].value="1";
		opener.document.<%=request.getParameter("formName")%>.elements['<%=request.getParameter("formElementId")%>'].value=id;
		opener.document.<%=request.getParameter("formName")%>.elements['method'].value="selectProgram";
		opener.document.<%=request.getParameter("formName")%>.elements['clientId'].value=clientId;
		opener.document.<%=request.getParameter("formName")%>.submit();
		self.close();
	}		
	function submitForm(methodVal) {
	// alert("method");
		trimInputBox();
		document.forms[0].method.value = methodVal;
		document.forms[0].submit();
	}
	
</script>
</head>
<html:form action="/PMmodule/QuatroProgramSearch.do">
	<input type="hidden" name="method" />	
	<input type="hidden" name="clientId"  value="<c:out value="${clientId}"/>"/>
	<input type="hidden" name="formName"  value="<c:out value="${formName}"/>"/>
	<input type="hidden" name="formElementId"  value="<c:out value="${formElementId}"/>"/>
	<table width="100%" cellpadding="0px" cellspacing="0px">
	<tr>
			<th class="pageTitle">Program Search</th>
		</tr>
		<tr>
		<td class="buttonBar2" align="left" height="18px">		
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
		<table class="simple" width="100%" cellspacing="2" cellpadding="3">
		  <tr><td width="30%">Program Name</td>
  				<td width="70%"><html:text property="program.name" maxlength="70" /></td></tr>
  		 <tr><td>Facility</td>
  		 	<td><html:select property="program.facilityId">
					<html:option value="">&nbsp;</html:option>
					<html:options collection="lstFacility" property="code" labelProperty="description" />					
				</html:select>
			</td>
  		 </tr>
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
				<html:option value="">&nbsp;</html:option>				
				<html:option value="M">Male</html:option>
				<html:option value="F">Female</html:option>
				<html:option value="T">Co-ed</html:option>
				</html:select>
			</td></tr>
  		<tr><td>Minimun Age (inclusive)</td>
  				<td><html:text property="program.ageMin" maxlength="3" /></td></tr>
  		<tr><td>Maximum Age (inclusive)</td>
  			<td><html:text property="program.ageMax" maxlength="3" /></td></tr>  		
 	</table>
	</html:form>
		
		<display:table class="simple" sort="list" cellspacing="2" cellpadding="3" id="program" name="programs" pagesize="200" requestURI="/PMmodule/QuatroProgramSearch.do">
			<display:setProperty name="paging.banner.placement" value="bottom" />
			<display:column sortable="true" title="Name" sortName="program" sortProperty="name">
				<a href="#javascript:void(0);" onclick="selectProgram('<c:out value="${clientId}" />','<c:out value="${program.id}" />','<c:out value="${program.type}" />','<c:out value="${gender}" />');" >
						<c:out value="${program.name}" /></a>
			</display:column>
			<display:column property="type" sortable="true" title="Type"></display:column>
			<display:column sortable="true" title="Occupancy" sortName="program" sortProperty="numOfMembers" style="{text-align:right}">
				<c:out value="${program.numOfMembers}" />
			</display:column>
			<display:column sortable="true" title="Queue" sortName="program" sortProperty="queueSize" style="{text-align:right}">
				<c:out value="${program.queueSize}" />
			</display:column>
			<display:column sortable="true" title="Capacity (actual)" sortName="program" sortProperty="capacity_actual" style="{text-align:right}">
				<c:out value="${program.capacity_actual}" />
			</display:column>
			<display:column sortable="false" title="Vacancy" style="{text-align:right}">
				<c:if test="${program.type == 'Bed'}">
					<c:out value="${program.capacity_actual - program.numOfMembers}" />
				</c:if>
			</display:column>
		</display:table>	

