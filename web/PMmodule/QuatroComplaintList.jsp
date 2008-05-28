<!-- 

Source:web/PMmodule/QuatroComplaintList.jsp 

-->
<%@ include file="/taglibs.jsp"%>
<%@page import="org.oscarehr.PMmodule.model.Admission"%>
<%@page import="java.util.Date"%>

<c:set var="ctx" value="${pageContext.request.contextPath}"
	scope="request" />
<script type="text/javascript"
	src='<c:out value="${ctx}"/>/js/quatroLookup.js'></script>

<html-el:form action="/PMmodule/QuatroComplaint.do">
	<input type="hidden" name="method" value="edit" />
	<input type="hidden" name="clientId"/>
	<script lang="javascript">
	function submitForm(methodVal) {
		document.forms(0).method.value = methodVal;
		document.forms(0).submit();
	}
</script>
	<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
		<tr>
			<th class="pageTitle" align="center">Client Management -
			Complaint</th>
		</tr>
		<tr>
			<td class="simple" style="background: lavender"><%@ include file="ClientInfo.jsp" %></td>
		</tr>
		<tr>
			<td align="left" class="buttonBar">
			<html:link action="/PMmodule/QuatroClientSummary.do" name="actionParam" style="color:Navy;text-decoration:none;">
				<img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Close&nbsp;&nbsp;</html:link>
			<html:link action="/PMmodule/QuatroComplaint.do?method=edit"
				name="actionParam" style="color:Navy;text-decoration:none;">
				<img border=0 src=<html:rewrite page="/images/New16.png"/> />&nbsp;New&nbsp;&nbsp;</html:link>
			</td>
		</tr>
		<tr>
			<td align="left" class="message"><logic:messagesPresent
				message="true">
				<br />
				<html:messages id="message" message="true" bundle="pmm">
					<c:out escapeXml="false" value="${message}" />
				</html:messages>
				<br />
			</logic:messagesPresent></td>
		</tr>
		<tr>
			<td height="100%">
			<div
				style="color: Black; background-color: White; border-width: 1px; border-style: Ridge;
                    height: 100%; width: 100%; overflow: auto;">

<!--  start of page content -->

			<br />
			<div class="tabs" id="tabs">
			<table cellpadding="3" cellspacing="0" border="0">
				<tr>
					<th title="Complaints">Complaints</th>
				</tr>
			</table>
			</div>



			<display:table class="simple" cellspacing="2" cellpadding="3"
				id="complaint" name="complaints" export="false" pagesize="0"
				requestURI="/PMmodule/QuatroComplaint.do">


				<display:column sortable="true" title="ComplaintID" >
					<a
						href="<html:rewrite action="/PMmodule/QuatroComplaint.do"/>?method=edit&clientId=<c:out value="${complaint.clientId}" />&complaintId=<c:out value="${complaint.id}" />">
					<c:out value="${complaint.id}" /> </a>
				</display:column>


				<display:column property="firstname" sortable="true"
					title="First Name" />

				<display:column property="lastname" sortable="true"
					title="Last Name" />

				<display:column property="createdDatex" sortable="true"
					title="Created Date" />

				<display:column sortable="true" title="Status">
					<logic:equal name="complaint" property="status" value="1">Completed</logic:equal>
					<logic:equal name="complaint" property="status" value="0">In Progress</logic:equal>
				</display:column>

			</display:table> 

<!--  end of page content --></div>
			</td>
		</tr>
	</table>
</html-el:form>
