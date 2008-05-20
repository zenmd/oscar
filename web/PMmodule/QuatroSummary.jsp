<%@ include file="/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/quatro-tag.tld" prefix="quatro" %>
<%@page import="org.oscarehr.PMmodule.web.utils.UserRoleUtils"%>
<%@page import="org.oscarehr.PMmodule.model.QuatroAdmission"%>
<%@page import="java.util.Date"%>
<%@page import="oscar.MyDateFormat"%>
<%@page import="org.oscarehr.PMmodule.model.ClientReferral"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<script type="text/javascript" src='<c:out value="${ctx}"/>/js/quatroLookup.js'></script>
 
<html-el:form action="/PMmodule/QuatroClientSummary.do">
<input type="hidden" name="method" value="edit" />
<input type="hidden" name="clientId" value="<c:out value="${requestScope.clientId}"/>"/>
<script lang="javascript">
function submitForm(methodVal) {
	document.forms(0).method.value = methodVal;
	document.forms(0).submit();
}

function openHealthSafety(){
	var url = '<html:rewrite action="/PMmodule/QuatroHealthSafety.do"/>';
		url += '?method=form&clientId='+ '<c:out value="${client.demographicNo}"/>';
	window.open(url,'HealthSafety', 'scrollbars=1,width=800,height=450');
}	

</script>

<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
	<tr><th class="pageTitle" align="center">Client Management - Summary</th></tr>
	<tr>
	<td>
		<table width="100%" class="simple">
			<tr>
			<td style="width: 15%"><font><b>Client No.</b></font></td><td colspan="3"><c:out value="${client.demographicNo}" /></td>
			</tr>
			<tr>
				<td style="width: 15%"<font><b>Name</b></font></td>
				<td style="width: 35%"><font><b><c:out value="${client.formattedName}" /></b></font></td>
				<td style="width: 15%"><font><b>Date of Birth </b></font></td>
				<td style="width: 35%"><font><b><c:out value="${client.yearOfBirth}" />/<c:out value="${client.monthOfBirth}" />/<c:out value="${client.dateOfBirth}" /></b></font></td>
			</tr>
		</table>
	</td>
	</tr>
	<tr><td align="left" valign="middle" class="buttonBar2">
		&nbsp;<b>Summary</b>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html-el:link action="/PMmodule/ClientHistory.do" name="actionParam" style="color:Navy;text-decoration:none;">History</html-el:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroIntake.do" name="actionParam" style="color:Navy;text-decoration:none;">Intake</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroAdmission.do" name="actionParam" style="color:Navy;text-decoration:none;">Admission</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroRefer.do" name="actionParam" style="color:Navy;text-decoration:none;">Refer</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroDischarge.do" name="actionParam" style="color:Navy;text-decoration:none;">Discharge</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroServiceRestriction.do" name="actionParam" style="color:Navy;text-decoration:none;">Service Restriction</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroComplaint.do" name="actionParam" style="color:Navy;text-decoration:none;">Complaints</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroConsent.do" name="actionParam" style="color:Navy;text-decoration:none;">Consent</html:link>
		</td>
	</tr>
	<tr><td align="left" class="buttonBar">
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
<tr><td><br><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Personal information</th></tr>
</table></div></td></tr>

<tr><td>
<table wdith="100%" class="simple">
	<tr><td width="15%">Client No</td>
	<td width="35%"><c:out value="${client.demographicNo}" /></td>
	<td width="15%">Active</td>
	<td width="35%">
	  <logic:equal value="0" property="activeCount" name="client">No</logic:equal>
	  <logic:notEqual value="0" property="activeCount" name="client">Yes</logic:notEqual>
	</td></tr>
	<tr><td>First Name</td>
	<td><c:out value="${client.firstName}" /></td>
	<td>Gender</td>
	<td><c:out value="${client.sexDesc}" /></td></tr>
	<tr><td>Last Name</th>
	<td><c:out value="${client.lastName}" /></td>
	<td>Date of Birth</td>
	<td><c:out value="${client.yearOfBirth}" />/<c:out value="${client.monthOfBirth}" />/<c:out value="${client.dateOfBirth}" /></td></tr>
	</tr>
	<tr><td>Alias</td>
	<td colspan="3"><c:out value="${client.alias}" /></td></tr>
	<tr><td>Health and Safety</td>
	<td colspan="3">
        <table width="100%" class="simple" style="background: #e0e0e0;" cellspacing="2" cellpadding="2">
		<c:choose>
		  <c:when test="${empty healthsafety}">
			<tr><td><span style="color:red">None found</span></td>
			<td><input type="button" value="New Health and Safety" onclick="openHealthSafety()" /></td></tr>
		  </c:when>
	      <c:when test="${empty healthsafety.message}">
			<tr><td><span style="color:red">None found</span></td>
			<td><input type="button" value="New Health and Safety" onclick="openHealthSafety()" /></td></tr>
		  </c:when>
		  <c:otherwise>
			<tr><td colspan="3"><c:out value="${healthsafety.message}" /></td></tr>
			<tr><td width="50%">User Name: <c:out value="${healthsafety.userName}" /></td>
			<td width="30%">Date: <fmt:formatDate value="${healthsafety.updateDate}" pattern="yyyy/MM/dd" /></td>
			<td width="20%"><input type="button" value="Edit" onclick="openHealthSafety()" />
			</td></tr>
		  </c:otherwise>
		</c:choose>
       </table>
	</td></tr>
</table>
</td></tr>

<tr><td><br><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Family
  <c:if test="${groupHead != null}">
    -- <c:out value="${client.formattedName}" /> ( HEAD )
  </c:if>
</th></tr>
</table></div></td></tr>

<tr><td>
<display:table class="simple" cellspacing="2" cellpadding="3" id="member" name="family" export="false" requestURI="/PMmodule/QuatroClientSummary.do">
	<display:setProperty name="paging.banner.placement" value="bottom" />
	<display:setProperty name="basic.msg.empty_list" value="No family member exists." />
	
	<display:column property="lastName" sortable="true" title="Last Name" />
	<display:column property="firstName" sortable="true" title="First Name" />
	<display:column property="dob" sortable="true" title="DOB" />
	<display:column property="sexDesc" sortable="true" title="Gender" />
	<display:column property="alias" sortable="true" title="Alias" />
	<display:column property="relationshipDesc" sortable="true" title="Relationship" />
</display:table>
</td></tr>

<tr><td><br><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Bed/Room Reservation</th></tr>
</table></div></td></tr>

<tr><td>
<table wdith="100%" class="simple">
  <c:choose>
	<c:when test="${bedDemographic == null}">
    <c:choose>
	  <c:when test="${roomDemographic != null}">
		<tr><th width="20%">Assigned Room:</th>
		<td><c:out value="${roomDemographic.room.name}" /></td></tr>
		<tr><th width="20%">Assigned Bed:</th>
		<td>N/A</td></tr>
		<tr><th width="20%">Until</th>
		<td><fmt:formatDate value="${roomDemographic.assignEnd}" pattern="yyyy/MM/dd" /></td></tr>
	  </c:when>	
	  <c:otherwise>
		<tr><td><span style="color:red">No bed or room reserved</span></td></tr>
	  </c:otherwise>	
	</c:choose>
	</c:when>
	<c:when test="${bedDemographic != null}">
	  <tr><th width="20%">Assigned Room:</th>
	  <td><c:out value="${bedDemographic.roomName}" /> (<c:out value="${bedDemographic.programName}" />)</td></tr>
	  <tr><th width="20%">Assigned Bed:</th>
	  <td><c:out value="${bedDemographic.bedName}" /> (<c:out value="${bedDemographic.programName}" />)</td></tr>
	  <tr><th width="20%">Status</th>
	  <td><c:out value="${bedDemographic.statusName}" /></td></tr>
	  <tr><th width="20%">Late Pass</th>
	  <td><c:out value="${bedDemographic.latePass}" /></td></tr>
	  <tr><th width="20%">Until</th>
	  <td><fmt:formatDate value="${bedDemographic.reservationEnd}" pattern="yyyy/MM/dd" /></td></tr>
	</c:when>
	<c:otherwise>		
	</c:otherwise>
  </c:choose>
</table>
</td></tr>

<tr><td><br><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Current Programs</th></tr>
</table></div></td></tr>

<tr><td>
<display:table class="simple" cellspacing="2" cellpadding="3" id="admission" name="admissions" export="false" pagesize="10" requestURI="/PMmodule/QuatroClientSummary.do">
	<display:setProperty name="paging.banner.placement" value="bottom" />
	<display:setProperty name="basic.msg.empty_list" value="This client is not currently admitted to any programs." />
	
	<display:column property="programName" sortable="true" title="Program Name" />
	<display:column property="programType" sortable="true" title="Program Type" />
    <display:column sortable="true" title="Admission Date">
    <%
	   QuatroAdmission tempAdmission = (QuatroAdmission) pageContext.getAttribute("admission");
       String admissiondate=MyDateFormat.getStandardDate(tempAdmission.getAdmissionDate());
    %>
    <%= admissiondate%>
    </display:column>
	<display:column sortable="true" title="Days in Program">
	<%
	    QuatroAdmission tempAdmission = (QuatroAdmission) pageContext.getAttribute("admission");
		Date admissionDate = tempAdmission.getAdmissionDate().getTime();
		Date dischargeDate = tempAdmission.getDischargeDate() != null ? tempAdmission.getDischargeDate().getTime() : new Date();
			
		long diff = dischargeDate.getTime() - admissionDate.getTime();
		diff = diff / 1000; // seconds
		diff = diff / 60; // minutes
		diff = diff / 60; // hours
		diff = diff / 24; // days

		String numDays = String.valueOf(diff);
	%>
	<%=numDays%>
	</display:column>
</display:table>
</td></tr>

<tr><td><br><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Referrals</th></tr>
</table></div></td></tr>

<tr><td>
<display:table class="simple" cellspacing="2" cellpadding="3" id="referral" name="referrals" export="false" pagesize="10" requestURI="/PMmodule/QuatroClientSummary.do">
	<display:setProperty name="paging.banner.placement" value="bottom" />
	
	<display:column property="programName" sortable="true" title="Program Name" />
	<display:column property="programType" sortable="true" title="Program Type" />
	<display:column property="referralDate" format="{0, date, yyyy/MM/dd kk:mm}" sortable="true" title="Referral Date" />
	<display:column property="providerFormattedName" sortable="true" title="Referring Provider" />
	<display:column sortable="true" title="Days in Queue">
	<%
		ClientReferral tempReferral = (ClientReferral) pageContext.getAttribute("referral");
		Date referralDate = tempReferral.getReferralDate();
		Date currentDate = new Date();
			
		long referralDiff = currentDate.getTime() - referralDate.getTime();
		referralDiff = referralDiff / 1000; // seconds
		referralDiff = referralDiff / 60; // minutes
		referralDiff = referralDiff / 60; // hours
		referralDiff = referralDiff / 24; // days

		String referralNumDays = String.valueOf(referralDiff);
	%>
	<%=referralNumDays%>
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
