<%@ include file="/taglibs.jsp" %>
<%@page import="org.oscarehr.PMmodule.model.Admission"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/WEB-INF/caisi-tag.tld" prefix="caisi" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<script type="text/javascript" src='<c:out value="${ctx}"/>/js/quatroLookup.js'></script>
<script type="text/javascript">
	function resetClientFields() {
		var form = document.quatroClientReferForm;
		form.elements['program.name'].value='';
	}

	 function popupProgramSearch(clientId) {
        var page = '<html:rewrite action="/PMmodule/QuatroProgramSearch.do?clientId=" />'+clientId;       
        var windowprops = "height=600,width=800,location=no,"
                + "scrollbars=yes,menubars=no,toolbars=no,resizable=yes,top=10,left=0";
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
		document.forms(0).method.value = methodVal;
		document.forms(0).submit();
	}
    
</script>
 
<html-el:form action="/PMmodule/QuatroRefer.do">
<input type="hidden" name="method"/>
<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
	<tr>
		<th class="pageTitle" align="center">Client Management - Refer</th>
	</tr>
	<tr>
		<td align="left" valign="middle" class="buttonBar2">
		&nbsp;<html:link action="/PMmodule/QuatroClientSummary.do" name="actionParam" style="color:Navy;text-decoration:none;">Summary</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/ClientHistory.do" name="actionParam" style="color:Navy;text-decoration:none;">History</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroIntake.do" name="actionParam" style="color:Navy;text-decoration:none;">Intake</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroAdmission.do" name="actionParam" style="color:Navy;text-decoration:none;">Admission</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<b>Refer</b>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroDischarge.do" name="actionParam" style="color:Navy;text-decoration:none;">Discharge</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroServiceRestriction.do" name="actionParam" style="color:Navy;text-decoration:none;">Service Restriction</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroComplaint.do" name="actionParam" style="color:Navy;text-decoration:none;">Complaints</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroConsent.do" name="actionParam" style="color:Navy;text-decoration:none;">Consent</html:link>
		</td>
	</tr>
	<tr>
		<td align="left" class="buttonBar">
		<%String a="1"; %>
		<a href="javaScript:popupProgramSearch('<bean:write name="quatroClientReferForm" property="clientId" />');" style="color:Navy;text-decoration:none;">
		<img border=0 src=<html:rewrite page="/images/search16.gif"/> height="16px" width="16px"/>&nbsp;Search Program&nbsp;&nbsp;|</a>
		<a href='javascript:submitForm("save");' style="color:Navy;text-decoration:none;">
		<img border=0 src=<html:rewrite page="/images/Save16.png"/> />&nbsp;Save&nbsp;&nbsp;</a>|		
		<a href='javascript:submitForm("list");' style="color:Navy;text-decoration:none;">
		<img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Return to List&nbsp;&nbsp;</a></td>
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
<table>

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
	<th style="color:black">Email</th>	
	</tr>
	<tr><td><c:out value="${program.name }" /></td>
	<td><c:out value="${program.type }" /></td>
	<td><c:out value="${program.numOfMembers}" />/<c:out value="${program.maxAllowed}" /> (<c:out value="${program.queueSize}" /> waiting)</td>
	<td><c:out value="${program.phone }" /></td>
	<td><c:out value="${program.email }" /></td>
	
	</tr>		
 </table>
</td></tr>

<tr><td>
  <table class="simple" cellspacing="2" cellpadding="3">
	<tr><td width="20%">Reason for referral</td>
	<td><html:textarea cols="50" rows="7" property="referral.notes" /></td></tr>
	<tr><td width="20%">Presenting Problems:</td>
	<td><html:textarea cols="50" rows="7" property="referral.presentProblems" /></td></tr>
	<c:if test="${program.type eq 'Bed' }">
	  <caisi:isModuleLoad moduleName="pmm.refer.temporaryAdmission.enabled">
		<tr><td width="20%">Request Temporary Admission</td>
		<td><html:checkbox property="referral.temporaryAdmission" /></td></tr>
	  </caisi:isModuleLoad>
	</c:if>
	<tr><td colspan="2"><!-- input type="button" value="Process Referral" onclick="do_referral()" /> <input type="button" value="Cancel" onclick="document.clientManagerForm.submit()" / --></td></tr>
  </table>
</td></tr>


</table>

<!--  end of page content -->
</div>
</td>
</tr>
</table>
</html-el:form>
