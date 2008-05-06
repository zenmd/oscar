<%@ include file="/taglibs.jsp"%>
<%@page import="org.oscarehr.PMmodule.model.Admission"%>
<%@page import="java.util.Date"%>

<c:set var="ctx" value="${pageContext.request.contextPath}"
	scope="request" />
<script type="text/javascript"
	src='<c:out value="${ctx}"/>/js/quatroLookup.js'></script>

<html-el:form action="/PMmodule/QuatroComplaint.do">
	<input type="hidden" name="method" value="edit" />
	<script lang="javascript">
	function submitForm(methodVal) {
		document.forms(0).method.value = methodVal;
		document.forms(0).submit();
	}
</script>
	<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
		<tr>
			<th class="pageTitle" align="center">Client Management -
			Complaints</th>
		</tr>
		<tr>
			<td align="left" valign="middle" class="buttonBar2">&nbsp;<html:link
				action="/PMmodule/QuatroClientSummary.do" name="actionParam"
				style="color:Navy;text-decoration:none;">Summary</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
			<html:link action="/PMmodule/QuatroHistory.do" name="actionParam"
				style="color:Navy;text-decoration:none;">History</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
			<html:link action="/PMmodule/QuatroIntake.do" name="actionParam"
				style="color:Navy;text-decoration:none;">Intake</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
			<html:link action="/PMmodule/QuatroAdmission.do" name="actionParam"
				style="color:Navy;text-decoration:none;">Admission</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
			<html:link action="/PMmodule/QuatroRefer.do" name="actionParam"
				style="color:Navy;text-decoration:none;">Refer</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
			<html:link action="/PMmodule/QuatroDischarge.do" name="actionParam"
				style="color:Navy;text-decoration:none;">Discharge</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
			<html:link action="/PMmodule/QuatroServiceRestriction.do"
				name="actionParam" style="color:Navy;text-decoration:none;">Service Restriction</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
			<b>Complaints</b></td>
		</tr>
		<tr>
			<td align="left" class="buttonBar"><html:link
				action="/PMmodule/ClientSearch2.do"
				style="color:Navy;text-decoration:none;">
				<img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Close&nbsp;&nbsp;</html:link>
				<html:link
				action="/PMmodule/QuatroComplaint.do?method=edit"
				style="color:Navy;text-decoration:none;">
				<img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;New&nbsp;&nbsp;</html:link>
				<html:link
				action="/PMmodule/QuatroComplaint.do?method=save"
				style="color:Navy;text-decoration:none;">
				<img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Save&nbsp;&nbsp;</html:link></td>
		</tr>
		<tr>
			<td align="left" class="message"><logic:messagesPresent
				message="true">
				<html:messages id="message" message="true" bundle="pmm">
					<c:out escapeXml="false" value="${message}" />
				</html:messages>
			</logic:messagesPresent></td>
		</tr>
		<tr>
			<td height="100%">
			<div
				style="color: Black; background-color: White; border-width: 1px; border-style: Ridge;
                    height: 100%; width: 100%; overflow: auto;">

<!--  start of page content --> 
			

				<table width="100%" cellpadding="0" cellspacing="0" border="10">
					<tr>
						<td align="left" class="clsHomePageHeader">
						<h2>Complaint List</h2>
						</td>
					</tr>
	
				</table>
	
				<display:table class="simple" cellspacing="2" cellpadding="3"
					id="complaint" name="complaints" export="false" pagesize="0"
					requestURI="/PMmodule/QuatroComplaint.do">
	
	
					<display:column sortable="true" title="ComplaintID">
						<a
							href="<html:rewrite action="/PMmodule/QuatroComplaint.do"/>?method=edit&id=<c:out value="${complaint.id}" />">
						<c:out value="${complaint.id}" /> </a>
					</display:column>
	
					<display:column property="source" sortable="true" title="Source of Complaint" />
					
					<display:column property="firstname" sortable="true" title="First Name" />
	
					<display:column property="lastname" sortable="true" title="Last Name" />
					
					<display:column property="lastname" sortable="true" title="Created Date" />
					
					<display:column property="status" sortable="true" title="Status" />
					
						
				</display:table>




<!--  end of page content -->
			
			</div>
			</td>
		</tr>
	</table>
</html-el:form>
