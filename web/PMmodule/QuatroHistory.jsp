<%@ include file="/taglibs.jsp" %>
<%@page import="org.oscarehr.PMmodule.model.Admission"%>
<%@page import="java.util.Date"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<script type="text/javascript" src='<c:out value="${ctx}"/>/js/quatroLookup.js'></script>
 
<html-el:form action="/PMmodule/QuatroHistory.do">
<input type="hidden" name="method"/>
<script lang="javascript">
	function submitForm(methodVal) {
		document.forms(0).method.value = methodVal;
		document.forms(0).submit();
	}
</script>
<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
	<tr>
		<th class="pageTitle" align="center"><span
			id="_ctl0_phBody_lblTitle" align="left">Client Management - History</span></th>
	</tr>
	<tr>
		<td align="left" valign="middle" class="buttonBar2">
		&nbsp;<html:link action="/PMmodule/QuatroClientSummary.do" name="actionParam" style="color:Navy;text-decoration:none;">Summary</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<b>History</b>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroIntake.do" name="actionParam" style="color:Navy;text-decoration:none;">Intake</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroAdmission.do" name="actionParam" style="color:Navy;text-decoration:none;">Admission</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroRefer.do" name="actionParam" style="color:Navy;text-decoration:none;">Refer</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroDischarge.do" name="actionParam" style="color:Navy;text-decoration:none;">Discharge</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroServiceRestriction.do" name="actionParam" style="color:Navy;text-decoration:none;">Service Restriction</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroComplaint.do" name="actionParam" style="color:Navy;text-decoration:none;">Complaints</html:link>
		</td>
	</tr>
	<tr>
		<td align="left" class="buttonBar"><a href='javascript:submitForm("close");'
			style="color:Navy;text-decoration:none;">
			<img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Close&nbsp;&nbsp;</a></td>
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
<tr><th>Admission History</th></tr>
</table></div></td></tr>

<tr><td>
<display:table class="simple" cellspacing="2" cellpadding="3" id="admission" name="admissionHistory" requestURI="/PMmodule/ClientManager.do">
	<display:setProperty name="paging.banner.placement" value="bottom" />
	<display:setProperty name="basic.msg.empty_list" value="This client is not currently admitted to any programs." />
	<%
		Admission tmpAd = (Admission) pageContext.getAttribute("admission");
	%>
    <display:column sortable="false">
        <a href="javascript:void(0)" onclick="popupAdmissionInfo('<c:out value="${admission.id}" />')">
            <img alt="View details" src="<c:out value="${ctx}" />/images/details.gif" border="0"/>
        </a>
    </display:column>
    <display:column property="programName" sortable="true" title="Program Name" />
	<display:column property="programType" sortable="true" title="Program Type" />
	<display:column property="admissionDate" format="{0, date, yyyy-MM-dd kk:mm}" sortable="true" title="Admission Date" />
	<display:column title="Facility<br />Admission" >
	<%=!tmpAd.isAdmissionFromTransfer()%>
	</display:column>
	<display:column property="dischargeDate" format="{0, date, yyyy-MM-dd kk:mm}" sortable="true" title="Discharge Date" />
	<display:column title="Facility<br />Discharge" >
	<%=!tmpAd.isDischargeFromTransfer()%>
	</display:column>
	<display:column sortable="true" title="Days in Program">
	<%
		Date admissionDate = tmpAd.getAdmissionDate();
		Date dischargeDate = tmpAd.getDischargeDate();
		
		if (dischargeDate == null) {
			dischargeDate = new Date();
		}
		
		long diff = dischargeDate.getTime() - admissionDate.getTime();
		
		diff = diff / 1000; // seconds;
		diff = diff / 60; // minutes;
		diff = diff / 60; // hours
		diff = diff / 24; // days
		
		String numDays = String.valueOf(diff);
	%>
	<%=numDays%>
	</display:column>
	<caisi:isModuleLoad moduleName="pmm.refer.temporaryAdmission.enabled">
	<display:column property="temporaryAdmission" sortable="true" title="Temporary Admission" />
	</caisi:isModuleLoad>
</display:table>
</td></tr>

<tr><td><br><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Referral History</th></tr>
</table></div></td></tr>

<tr><td>
<display:table class="simple" cellspacing="2" cellpadding="3" id="referral" name="referralHistory" requestURI="/PMmodule/ClientManager.do">
	<display:setProperty name="paging.banner.placement" value="bottom" />
	
    <display:column sortable="false">
        <a href="javascript:void(0)" title="Referral details" onclick="popupReferralInfo('<c:out value="${referral.id}" />')">
            <img alt="View details" src="<c:out value="${ctx}" />/images/details.gif" border="0"/>
        </a>
    </display:column>
	<display:column property="programName" sortable="true" title="Program Name" />
	<display:column property="programType" sortable="true" title="Program Type" />
	<display:column property="referralDate" format="{0, date, yyyy/MM/dd kk:mm}" sortable="true" title="Referral Date" />
	<display:column property="completionDate" format="{0, date, yyyy/MM/dd kk:mm}" sortable="true" title="Completion Date" />
	<display:column property="completionNotes" sortable="false" title="Referring program/agency" />
	<display:column property="notes" sortable="false" title="External" />
</display:table>
</td></tr>

</table>

<!--  end of page content -->
</div>
</td>
</tr>
</table>
</html-el:form>
