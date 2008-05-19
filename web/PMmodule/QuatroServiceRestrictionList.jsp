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
</script>
<script lang="javascript">
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
	          /* var form = document.quatroClientServiceRestrictionForm; */
	        
	        var form =document.forms(0);
	        form.method.value='terminate_early'; 
	        form.recordId.value=restrictionId;
	        form.submit('terminate_early');
    	}
    }
</script>
 
<html-el:form action="/PMmodule/QuatroServiceRestriction.do">
<input type="hidden" name="method"/>
<html:hidden property="program.id" />
<html:hidden property="recordId" styleId="recordId"/>

<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
	<tr>
		<th class="pageTitle" align="center">Client Management - Service Restriction List</th>
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
		<html:link	action="/PMmodule/QuatroServiceRestriction.do?method=edit&rId=0"	style="color:Navy;text-decoration:none;">
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
	<tr><td><br><div class="tabs">
		<table cellpadding="3" cellspacing="0" border="0">
			<tr><th>Service Restrictions</th></tr>
		</table></div>
	</td></tr>	
	<tr>
		<td height="100%">
		<div
			style="color: Black; background-color: White; border-width: 1px; border-style: Ridge;
                    height: 100%; width: 100%; overflow: auto;">

		<!--  start of page content -->
			<table width="100%" class="edit">
				<tr><td>
				<display:table class="simple" cellspacing="2" cellpadding="3" id="service_restriction" name="serviceRestrictions" export="false" pagesize="0" requestURI="/PMmodule/QuatroServiceRestriction.do">
				<%
					boolean allowTerminateEarly=false;
					ProgramClientRestriction temp=null;
					ProviderDao providerDao=(ProviderDao)SpringUtils.getBean("providerDao");
				%>
				  <display:setProperty name="paging.banner.placement" value="bottom" />
				  <display:column property="programDesc" sortable="true" title="Program Name" />
				  <display:column property="providerFormattedName" sortable="true" title="Restricted By"/>
				  <display:column property="comments" sortable="true" title="Comments" />
				  <display:column property="startDate" sortable="true" title="Start date" />
				  <display:column property="endDate" sortable="true" title="End date" />
				  <display:column sortable="true" title="Status">
					<%
						temp=(ProgramClientRestriction)service_restriction;
						String status="";
						allowTerminateEarly=false;
						if (temp.getEarlyTerminationProvider()!=null){
							Provider providerTermination=providerDao.getProvider(temp.getEarlyTerminationProvider());
							status="terminated early by "+providerTermination.getFormattedName();
						}else if (temp.getEndDate().getTime()<System.currentTimeMillis()){
						    status="completed";
						}else if (temp.getStartDate().getTime()<=System.currentTimeMillis() && temp.getEndDate().getTime()>=System.currentTimeMillis()){
							status="in progress";
							allowTerminateEarly=true;
						}
					%>
					   	<%=status%>
 				  </display:column>
				  <display:column sortable="true" title="">
  					<input type="button" <%=allowTerminateEarly?"":"disabled=\"disabled\""%> value="Terminate Early" onclick="terminateEarly(<%=temp.getId()%>)" />
  					</display:column>			  
				</display:table>
				</td></tr>
			</table>
		<!--  end of page content -->
		</div>
		</td>
	</tr>
</table>
</html-el:form>
