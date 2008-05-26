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
		function resetClientFields() {
		var form = document.quatroClientReferForm;
		form.elements['program.name'].value='';
	}

	 function popupProgramSearch(clientId) {
        var page = '<html:rewrite action="/PMmodule/QuatroProgramSearch.do?formName=quatroClientReferForm&formElementId=selectedProgramId&clientId=" />'+clientId;       
        var windowprops = "height=600,width=800,location=no,"
                + "scrollbars=yes,menubars=no,toolbars=no,resizable=yes,top=10,left=0,statusbar=yes";
        // alert("page name" +page);
        var popup = window.open(page, "_blank", windowprops);
        if (popup != null) {
            if (popup.opener == null) {
                popup.opener = self;
            }
            popup.focus();
        }
    }	
    
    
    function submitForm(methodVal) {
		document.forms[0].method.value = methodVal;
		document.forms[0].submit();
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
<input type="hidden" name="demoNo"/>
<html:hidden property="serviceRestriction.clientId" />
<html:hidden property="serviceRestriction.commentId" />
<html:hidden property="serviceRestriction.programId" />
<input type="hidden" name="selectedProgramId"/>

<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
	<tr>
		<th class="pageTitle" align="center">Client Management - Service Restriction</th>
	</tr>
	<tr>
		<td class="simple" style="background: lavender"><%@ include file="ClientInfo.jsp" %></td>
	</tr>
	<tr>
		<td align="left" class="buttonBar">
		<a href="javaScript:popupProgramSearch('<bean:write name="quatroClientServiceRestrictionForm" property="clientId" />');" style="color:Navy;text-decoration:none;">
		<img border=0 src=<html:rewrite page="/images/search16.gif"/> height="16px" width="16px"/>&nbsp;Search Program&nbsp;&nbsp;|</a>
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
