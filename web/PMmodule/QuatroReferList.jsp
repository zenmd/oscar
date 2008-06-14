<%@ include file="/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/quatro-tag.tld" prefix="quatro" %>
<%@page import="java.util.Date"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<script type="text/javascript" src='<c:out value="${ctx}"/>/js/quatroLookup.js'></script>
<script lang="javascript">
		function submitForm(methodVal) {
			document.forms[0].method.value = methodVal;
			document.forms[0].submit();
		}
	
		function updateQuatroRefer(clientId, rId) {
			location.href = '<html:rewrite action="/PMmodule/QuatroRefer.do"/>' + "?method=edit&rId=" + rId + "&clientId=" + clientId;
		}	
</script>
<% int a=1; %>
<html-el:form action="/PMmodule/QuatroRefer.do">
<input type="hidden" name="method"/>
<input type="hidden" name="clientId"/>
<table width="100%"  cellpadding="0px" cellspacing="0px">
	<tr>
		<th class="pageTitle" align="center">Client Management - Refer</th>
	</tr>
	<tr>
			<td class="simple" style="background: lavender"><%@ include file="ClientInfo.jsp" %></td>
	</tr>	
	<tr>
		<td align="left" class="buttonBar2">
		<html:link	action="/PMmodule/QuatroRefer.do?method=edit&rId=0" paramId="clientId"  paramName="clientId" style="color:Navy;text-decoration:none;">
			<img border=0 src=<html:rewrite page="/images/New16.png"/> />&nbsp;New Referral&nbsp;&nbsp;|
		</html:link>
		<html:link action="/PMmodule/QuatroClientSummary.do" name="actionParam" style="color:Navy;text-decoration:none;">
		<img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Close&nbsp;&nbsp;</html:link></td>
	</tr>
	<tr height="18px">
			<td align="left" class="message">			
			<logic:messagesPresent
				message="true">
				<html:messages id="message" message="true" bundle="pmm">
					<c:out escapeXml="false" value="${message}" />
				</html:messages>
			</logic:messagesPresent>
			<br /></td>
		</tr>
		<tr>
			<td>
			<table class="simple" cellspacing="2" cellpadding="3">
				<tr>
					<th align="left">REFERRAL</th></tr>	
			</table>
			</td>
		</tr>		
		
		<tr>
			<td>
				<div style="color: Black; background-color: White; border-width: 1px; border-style: Ridge;
                    height: 500px; width: 100%; overflow: auto;" id="scrollBar">
		 		<table class="simple" cellspacing="2" cellpadding="3">
					<tr>
						<th>Program Name</th>							
						<th>Created On</th>
						<th>Staff</th>
						<th>Status</th>
						<th>Actions</th>
					</tr>
				
					<logic-el:iterate id="refer" collection="${lstRefers}">
					<tr>				
						<td><c:out value="${refer.programName}"></c:out>	</td>				
						<td><fmt:formatDate pattern="yyyy/MM/dd" value="${refer.referralDate}" /></td>
						<td><c:out value="${refer.providerFormattedName}" /></td>
						<td><c:out value="${refer.status}" /></td>
						<td> 						
						<c:choose>
							<c:when test="${refer.status eq 'active' ||refer.status eq 'rejected' }">
								<a href="javascript:updateQuatroRefer('<c:out value="${refer.clientId}" />', '<c:out value="${refer.id}" />')" >Update</a>
							</c:when>
							<c:when test="${refer.status eq 'admitted'}">
								<a href="javascript:updateQuatroRefer('<c:out value="${refer.clientId}" />', '<c:out value="${refer.id}" />')" >View</a>
							</c:when>
							<c:otherwise>&nbsp;</c:otherwise>
					   </c:choose>
						</td>
					</tr>
					</logic-el:iterate>
				</table>
				</div>
			</td>
		</tr>		
			
	</table>
</html-el:form>
