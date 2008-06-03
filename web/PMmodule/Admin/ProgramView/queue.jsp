<%@ page import="java.util.*" %>
<%@ page import="org.oscarehr.PMmodule.model.ProgramQueue" %>
<%@ page import="java.net.URLEncoder" %>
<%@page import="org.apache.commons.lang.time.DateFormatUtils"%>
<%@page import="org.oscarehr.util.SpringUtils"%>
<%@page import="org.oscarehr.PMmodule.model.Demographic"%>


<%@ include file="/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/caisi-tag.tld" prefix="caisi" %>
<jsp:include page="/common/messages.jsp" />
<script>
/*
    function do_admission() {
        var form = document.programManagerViewForm;
        form.method.value='admit';
        form.submit();

    }
    function do_rejection() {
        var form = document.programManagerViewForm;
        form.method.value='reject_from_queue';
        form.submit();

    }
    function refresh_queue() {
        var form = document.programManagerViewForm;
        form.method.value='view';
        form.submit();
    }
*/
    function quatroAdmit(client_id,action,queue_id) {
        var form = document.programManagerViewForm;
        form.elements['clientId'].value=client_id;
        form.elements['queueId'].value=queue_id;
        if(action == 'admit') {
            form.method.value='quatroAdmit';
        }
    }
</script>
<html:hidden property="clientId" />
<html:hidden property="queueId" />
<html:hidden property="remoteReferralId" />
<h3>Local Queue</h3>
<%
//	HashSet<Long> genderConflict=(HashSet<Long>)request.getAttribute("genderConflict");
//	HashSet<Long> ageConflict=(HashSet<Long>)request.getAttribute("ageConflict");
%>
<!--  show current clients -->
<display:table class="simple" cellspacing="2" cellpadding="3" id="queue_entry" name="queue" export="false" pagesize="0" requestURI="/PMmodule/ProgramManagerView.do">
    <display:setProperty name="paging.banner.placement" value="bottom" />
    <display:setProperty name="basic.msg.empty_list" value="Queue is empty." />
    <display:column sortable="false">
    	<%
//			String action="admit";
//    		long clientId=((ProgramQueue)pageContext.getAttribute("queue_entry")).getClientId();
//    		if (genderConflict.contains(clientId)) action="genderConflict";	
//    		if (ageConflict.contains(clientId)) action="ageConflict";	
    	%>
			<a href='<c:out value="${ctx}" />/PMmodule/QuatroAdmission.do?method=queue&clientId=<c:out value="${queue_entry.clientId}"/>&queueId=<c:out value="${queue_entry.id}"/>&programId=<c:out value="${queue_entry.programId}"/>' >Admit</a>
	</display:column>
    <display:column sortable="false">
		<a href='<c:out value="${ctx}" />/PMmodule/QuatroIntakeReject.do?method=edit&clientId=<c:out value="${queue_entry.clientId}"/>&queueId=<c:out value="${queue_entry.id}"/>' >Reject</a>
    </display:column>
    
    <display:column sortable="true" property="clientLastName" title="Last Name"/>
    <display:column sortable="true" property="clientFirstName" title="First Name"/>
    <display:column property="referralDate" sortable="true" title="Referral Date" />
    <display:column property="providerFormattedName" sortable="true" title="Referring Provider" />
    <display:column property="notes" sortable="true" title="Reason for Referral" />
    <display:column property="presentProblems" sortable="true" title="Present Problems"/>
</display:table>
<br />
<br />
<!-- 
<c:if test="${requestScope.do_admit != null}">
    <table width="100%" border="1" cellspacing="2" cellpadding="3">
        <c:if test="${requestScope.current_admission != null}">
            <tr>
                <td colspan="2"><b style="color:red">Warning:<br />
			        <c:choose>
			        	<c:when test="${requestScope.sameFacility}" >
		                    This client is currently admitted to a bed program (<c:out value="${current_program.name}" />).<br />
		                    By completing this admission, you will be discharging them from this current program.
			        	</c:when>
			        	<c:otherwise>
		                    This client is currently admitted to a bed program in another facility.<br />
		                    By completing this admission, you will be discharging them from this other<br />
		                    facility. Please check with the other facility before processing this <br />
		                    automatic discharge and admission.
			        	</c:otherwise>
			        </c:choose>
			     </b></td>
            </tr>
            <tr class="b">
                <td width="20%">Discharge Notes:</td>
                <td><textarea cols="50" rows="7" name="admission.dischargeNotes"></textarea></td>
            </tr>
        </c:if>
        <tr class="b">
            <td width="20%">Admission Notes:</td>
            <td><textarea cols="50" rows="7" name="admission.admissionNotes"></textarea></td>
        </tr>
        <tr class="b">
            <td colspan="2"><input type="button" value="Process Admission" onclick="do_admission()" /> <input type="button" value="Cancel" onclick="refresh_queue()" /></td>
        </tr>
    </table>
</c:if>
<c:if test="${requestScope.do_reject != null}">
    <table width="100%" border="1" cellspacing="2" cellpadding="3">
        <tr>
            <td width="5%"><html:radio property="radioRejectionReason" value="1" /></td>
            <td>Client requires acute care</td>
        </tr>
        <tr>
            <td width="5%"><html:radio property="radioRejectionReason" value="2" /></td>
            <td>Client not interested</td>
        </tr>
        <tr>
            <td width="5%"><html:radio property="radioRejectionReason" value="3" /></td>
            <td>Client does not fit program criteria</td>
        </tr>
        <tr>
            <td width="5%"><html:radio property="radioRejectionReason" value="4" /></td>
            <td>Program does not have space available</td>
        </tr>
        <tr>
            <td width="5%"><html:radio property="radioRejectionReason" value="5" /></td>
            <td>Other</td>
        </tr>
        <tr class="b">
            <td width="20%">Rejection Note:</td>
            <td><textarea cols="50" rows="7" name="admission.admissionNotes"></textarea></td>
        </tr>
        <tr class="b">
            <td colspan="2"><input type="button" value="Process" onclick="do_rejection()" /> <input type="button" value="Cancel" onclick="refresh_queue()" /></td>
        </tr>
    </table>
</c:if>
-->
