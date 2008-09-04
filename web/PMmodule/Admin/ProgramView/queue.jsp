<%@ page import="java.util.*" %>
<%@ page import="org.oscarehr.PMmodule.model.ProgramQueue" %>
<%@ page import="java.net.URLEncoder" %>
<%@page import="org.apache.commons.lang.time.DateFormatUtils"%>
<%@page import="org.oscarehr.util.SpringUtils"%>
<%@page import="org.oscarehr.PMmodule.model.Demographic"%>
<%@page import="com.quatro.common.KeyConstants"  %>

<%@ include file="/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/caisi-tag.tld" prefix="caisi" %>
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

<!--  show current clients -->
<table width="100%" cellpadding="0px" cellspacing="0px" height="100%"
	border="0">
<tr>
		<td align="left" class="buttonBar2">
			<html:link	action="/PMmodule/ProgramManager.do" style="color:Navy;text-decoration:none;">&nbsp;
			<img style="vertical-align: middle" border="0" src="<html:rewrite page="/images/Back16.png"/>" />&nbsp;Back to Programs&nbsp;</html:link>
		</td>
</tr>
<tr>
<td><br>
<div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Queue</th></tr>
</table></div>
</td></tr>
<tr height="100%">
<td>
    <div style="color: Black; background-color: White; border-style: ridge; border-width: 1px;
                        width: 100%; height: 100%; overflow: auto">
<display:table class="simple" cellspacing="2" cellpadding="3" id="queue_entry" name="queue" export="false" pagesize="0" requestURI="/PMmodule/ProgramManagerView.do">
    <display:setProperty name="paging.banner.placement" value="bottom" />
    <display:setProperty name="basic.msg.empty_list" value="Queue is empty." />
    <display:column sortable="false">
    <c:choose>
    <c:when test="${queue_entry.fromIntakeId!=null}" >    
    	<security:oscarSec objectName="<%=KeyConstants.FUN_CLIENTADMISSION %>" rights="<%=KeyConstants.ACCESS_WRITE %>">									
	  		<a href='<c:out value="${ctx}" />/PMmodule/QuatroAdmission.do?method=queue&clientId=<c:out value="${queue_entry.clientId}"/>&queueId=<c:out value="${queue_entry.id}"/>&programId=<c:out value="${queue_entry.programId}"/>' >Admit</a>
	  	</security:oscarSec>	
	</c:when>
	<c:otherwise>
		<security:oscarSec objectName="<%=KeyConstants.FUN_CLIENTINTAKE %>" rights="<%=KeyConstants.ACCESS_WRITE %>">									
	  		<a href='<c:out value="${ctx}" />/PMmodule/QuatroIntakeEdit.do?method=manualreferral&intakeId=0&clientId=<c:out value="${queue_entry.clientId}"/>&queueId=<c:out value="${queue_entry.id}"/>&programId=<c:out value="${queue_entry.programId}"/>' >Intake</a>
		</security:oscarSec>
	</c:otherwise>
	</c:choose>		
	</display:column>
    <display:column sortable="false">  
    	<security:oscarSec objectName="<%=KeyConstants.FUN_PROGRAM_REJECT %>" rights="<%=KeyConstants.ACCESS_WRITE %>"> 
    		<a href='<c:out value="${ctx}" />/PMmodule/QuatroIntakeReject.do?method=edit&clientId=<c:out value="${queue_entry.clientId}"/>&queueId=<c:out value="${queue_entry.id}"/>&programId=<c:out value="${queue_entry.programId}"/>' >Reject</a>	
    	</security:oscarSec>	
    </display:column>
    
    <display:column sortable="true" property="clientLastName" title="Last Name"/>
    <display:column sortable="true" property="clientFirstName" title="First Name"/>
    <display:column property="referralDate" sortable="true" title="Referral Date" format="{0, date, yyyy/MM/dd hh:mm:ss a}" />
    <display:column property="providerFormattedName" sortable="true" title="Referring Provider" />
    <display:column property="notes" sortable="true" title="Reason for Referral" />
    <display:column property="presentProblems" sortable="true" title="Present Problems"/>
    <display:column property="fromIntakeId" sortable="true" title="From IntakeID"/>
</display:table></div>
</td>
</tr>
</table>
<%@ include file="/common/readonly.jsp" %>
<br />
