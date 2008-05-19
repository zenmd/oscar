<%@ include file="/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/quatro-tag.tld" prefix="quatro" %>
<%@page import="org.oscarehr.PMmodule.model.Admission"%>
<%@page import="java.util.Date"%>
<%@page import="org.oscarehr.PMmodule.dao.ProviderDao"%>
<%@page import="org.oscarehr.util.SpringUtils"%>
<%@page import="org.oscarehr.PMmodule.model.ProgramClientRestriction" %>
<%@page import="org.oscarehr.PMmodule.model.Provider" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<script type="text/javascript" src='<c:out value="${ctx}"/>/js/quatroLookup.js'></script>
<script lang="javascript">
	function submitForm(methodVal) {
		document.forms(0).method.value = methodVal;
		document.forms(0).submit();
	}
	function resetClientFields() {
        var form = document.clientManagerForm;
        form.elements['program.name'].value='';
    }

    function search_programs() {
        var form = document.clientManagerForm;

        form.method.value='restrict_select_program';
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

        var url = '<html:rewrite action="/PMmodule/ClientManager.do"/>';
        url += '?method=search_programs&program.name=' + programName + '&program.type=' + programType;
        url += '&program.manOrWoman='+manOrWoman+'&program.transgender='+transgender+'&program.firstNation='+firstNation+'&program.bedProgramAffiliated='+bedProgramAffiliated+'&program.alcohol='+alcohol+'&program.abstinenceSupport='+abstinenceSupport+'&program.physicalHealth='+physicalHealth+'&program.mentalHealth='+mentalHealth+'&program.housing='+housing;
			//url += '&program.manOrWoman='+manOrWoman;
        url += '&formName=clientManagerForm&formElementName=program.name&formElementId=program.id&formElementType=program.type&submit=true';

        window.open(url, "program_search", "width=800, height=600, scrollbars=1,location=1,status=1");
    }

    function do_service_restriction() {
        var form = document.clientManagerForm;
        form.method.value='service_restrict';
        form.submit();
    }
    
    function terminateEarly(restrictionId)
    {
    	if (confirm('Do you wish to terminate this service restriction?'))
    	{
	        var form = document.clientManagerForm;
	        form.method.value='terminate_early';
	        form.restrictionId.value=restrictionId;
	        form.submit();
    	}
    }
	
</script>
 
<html-el:form action="/PMmodule/QuatroServiceRestriction.do">
<input type="hidden" name="method"/>
<input type="hidden" name="restrictionId" value="" />
<html:hidden property="program.id" />

<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
	<tr>
		<th class="pageTitle" align="center">Client Management - Service Restriction</th>
	</tr>
	<tr>
		<td align="left" valign="middle" class="buttonBar2">
		&nbsp;<html:link action="/PMmodule/QuatroClientSummary.do" name="actionParam" style="color:Navy;text-decoration:none;">Summary</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/ClientHistory.do" name="actionParam" style="color:Navy;text-decoration:none;">History</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroIntake.do" name="actionParam" style="color:Navy;text-decoration:none;">Intake</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroAdmission.do" name="actionParam" style="color:Navy;text-decoration:none;">Admission</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroRefer.do" name="actionParam" style="color:Navy;text-decoration:none;">Refer</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroDischarge.do" name="actionParam" style="color:Navy;text-decoration:none;">Discharge</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<b>Service Restriction</b>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroComplaint.do" name="actionParam" style="color:Navy;text-decoration:none;">Complaints</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroConsent.do" name="actionParam" style="color:Navy;text-decoration:none;">Consent</html:link>
		</td>
	</tr>
	<tr>
		<td align="left" class="buttonBar">
		<html:link	action="/PMmodule/QuatroServiceRestriction.do?method=save"	style="color:Navy;text-decoration:none;">
			<img border=0 src=<html:rewrite page="/images/New16.png"/> />&nbsp;New Service Restriction&nbsp;&nbsp;|
		</html:link>
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
		<td>
		<div
			style="color: Black; background-color: White; border-width: 1px; border-style: Ridge;
                    height: 100%; width: 100%; overflow: auto;">

			<!--  start of page content -->
			<table width="100%" class="edit">
			<tr><td><br><div class="tabs">
			<table cellpadding="3" cellspacing="0" border="0">
			<tr><th>Service Restrictions</th></tr>
			</table></div></td></tr>
			
			<tr><td>
			
			<table wdith="100%" class="simple">
			  <tr><th style="color:black">Program Name</th>
			  <th style="color:black">Type</th>
			  <th style="color:black">Participation</th>
			  <th style="color:black">Phone</th>
			  <th style="color:black">Email</th></tr>
			  <tr><td><c:out value="${program.name }" /></td>
			  <td><c:out value="${program.type }" /></td>
			  <td><c:out value="${program.phone }" /></td>
			  <td><c:out value="${program.email }" /></td></tr>
			</table>
			<table wdith="100%" class="simple">
			   <tr><td width="20%">Reason for service restriction:</td>
			    <td><html:select property="serviceRestriction.commentId">
				   <c:forEach var="restriction" items="${serviceRestrictionList}">
					 <html-el:option value="${restriction.code}"><c:out value="${restriction.description}"/></html-el:option>
				   </c:forEach>
				   </html:select>
			     </td>
			    </tr>
			    <tr><td>Start Date</td>
			    <td><quatro:datePickerTag property="serviceRestriction.startDate" width="120px" openerForm="quatroClientServiceRestrictionForm"></quatro:datePickerTag></td></tr> 
			     <tr><td>Length of restriction (in days)</td>
			        <td><html:text size="4" property="serviceRestrictionLength" /></td></tr>
             </table>
<!--  end of page content -->
		</div>
		</td>
	</tr>
</table>
</html-el:form>
