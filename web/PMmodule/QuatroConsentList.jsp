<%@ include file="/taglibs.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<script type="text/javascript" src='<c:out value="${ctx}"/>/js/quatroLookup.js'></script>
<script lang="javascript">
		function submitForm(methodVal) {
			document.forms(0).method.value = methodVal;
			document.forms(0).submit();
		}
	
		function updateQuatroConsent(clientId, rId) {
			location.href = '<html:rewrite action="/PMmodule/QuatroConsent.do"/>' + "?method=edit&rId=" + rId + "&clientId=" + clientId;
		}	
</script>
<% int a=1; %>
<html-el:form action="/PMmodule/QuatroConsent.do">
<input type="hidden" name="method"/>
<html:hidden property="clientId"/>

<table width="100%"  cellpadding="0px" cellspacing="0px">
	<tr>
		<th class="pageTitle" align="center">Client Management - Consent</th>
	</tr>
	<tr>
		<td class="simple" style="background: lavender"><%@ include file="ClientInfo.jsp" %></td>
	</tr>	
</table>

<table width="100%">	
	<tr>
		<td align="left" class="buttonBar">
		<html:link	action="/PMmodule/QuatroConsent.do?method=edit&rId=0" paramId="clientId"  paramName="clientId"	style="color:Navy;text-decoration:none;">
			<img border=0 src=<html:rewrite page="/images/New16.png"/> />&nbsp;New Consent&nbsp;&nbsp;|
		</html:link>
		<html:link action="/PMmodule/QuatroClientSummary.do" name="actionParam" style="color:Navy;text-decoration:none;">
		<img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Close&nbsp;&nbsp;</html:link></td>
	</tr>
	<tr>
		<td>		
				<div class="tabs">
					<table cellpadding="0" cellspacing="0" border="0">
							<tr>
								<th>Consent</th>
							</tr>
						</table>
					</div>
		 </td>
		</tr>	
		
		<tr>
			<td>
				<div style="color: Black; background-color: White; border-width: 1px; border-style: Ridge;
                    height: 500px; width: 100%; overflow: auto;">
		 		<table class="simple" cellspacing="2" cellpadding="3">
					<tr>
						<th>Staff</th>							
						<th>Signed Date</th>
						<th>Start Date</th>
						<th>End Date</th>						
						<th>Actions</th>
					</tr>
				
					<logic-el:iterate id="consent" collection="${lstConsents}">
					<tr>				
						<td><c:out value="${consent.providerFormattedName}"></c:out>	</td>				
						<td><c:out value="${consent.dateSignedStr}" /></td>
						<td><c:out value="${consent.startDateStr}" /></td>
						<td><c:out value="${consent.endDateStr}" /></td>				
						<td> 						
						<c:choose>
							<c:when test="${consent.status eq 'active'}">
								<input type="button" value="Update"
									onclick="updateQuatroConsent('<c:out value="${consent.demographicNo}" />', '<c:out value="${consent.id}" />')" />
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
