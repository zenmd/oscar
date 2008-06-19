<%@ include file="/taglibs.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<script type="text/javascript" src='<c:out value="${ctx}"/>/js/quatroLookup.js'></script>
<script lang="javascript">
		function submitForm(methodVal) {
			document.forms[0].method.value = methodVal;
			document.forms[0].submit();
		}
	
		function updateQuatroConsent(clientId, rId) {
			location.href = '<html:rewrite action="/PMmodule/QuatroConsent.do"/>' + "?method=edit&rId=" + rId + "&clientId=" + clientId;
		}	
		function withdraw(clientId,rId)
	    {
	    	if (confirm('Do you wish to withdraw this consent?'))
	    	{
		        var form = document.consentDetailForm;
		        form.method.value='withdraw';
		        form.clientId.value=clientId;
		        form.rId.value=rId;
		        form.submit();
	    	}
	    }
</script>
<% int a=1; %>
<html-el:form action="/PMmodule/QuatroConsent.do">
<input type="hidden" name="method"/>
<input type="hidden" name="clientId"/>
<input type="hidden" name="rId"/>
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
		<td align="left" class="buttonBar2">
		<html:link action="/Home.do"
		style="color:Navy;text-decoration:none">&nbsp;
		<img style="vertical-align: middle" border=0 src=<html:rewrite page="/images/close16.png"/> />&nbsp;Close&nbsp;&nbsp;|</html:link>

		<html:link action="/PMmodule/ClientSearch2.do" style="color:Navy;text-decoration:none;">&nbsp;
		<img style="vertical-align: middle" border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Back to Client Search&nbsp;&nbsp;|</html:link>
		
		<html:link	action="/PMmodule/QuatroConsent.do?method=edit&rId=0" paramId="clientId"  paramName="clientId"	style="color:Navy;text-decoration:none;">
			<img style="vertical-align: middle" border=0 src=<html:rewrite page="/images/New16.png"/> />&nbsp;New Consent&nbsp;&nbsp;
		</html:link>
		</td>
	</tr>
	<tr>
		<td>		
				<div class="tabs">
					<table cellpadding="0" cellspacing="0" border="0">
							<tr>
								<th>Consents</th>
							</tr>
						</table>
					</div>
		 </td>
		</tr>	
		
		<tr>
			<td>
				<div style="color: Black; background-color: White; border-width: 1px; border-style: Ridge;
                    height: 500px; width: 100%; overflow: auto;" id="scrollBar">
		 		<table class="simple" cellspacing="2" cellpadding="3">
					<tr>
						<th>Staff</th>							
						<th>Signed Date</th>
						<th>Start Date</th>
						<th>End Date</th>	
						<th>Status</th>					
						<th>Actions</th>
					</tr>
				
					<logic-el:iterate id="consentDetail" collection="${lstConsents}">					
					<tr>				
						<td><c:out value="${consentDetail.providerFormattedName}"></c:out>	</td>				
						<td><c:out value="${consentDetail.dateSignedStr}" /></td>
						<td><c:out value="${consentDetail.startDateStr}" /></td>
						<td><c:out value="${consentDetail.endDateStr}" /></td>	
						<td><c:out value="${consentDetail.status}" /></td>			
						<td> 						
							<c:choose>
								<c:when test="${consentDetail.lnkAction eq 'Withdraw' or consentDetail.lnkAction eq 'withdraw'}">
								 	<a href="javascript:withdraw('<c:out value="${consentDetail.demographicNo}" />','<c:out value="${consentDetail.id}" />')" >Withdraw</a>
								 	<a href="javascript:updateQuatroConsent('<c:out value="${consentDetail.demographicNo}" />', '<c:out value="${consentDetail.id}" />')" >View</a>
								</c:when>
								<c:when test="${consentDetail.lnkAction eq 'View' or consentDetail.lnkAction eq 'view'}">
									<a href="javascript:updateQuatroConsent('<c:out value="${consentDetail.demographicNo}" />', '<c:out value="${consentDetail.id}" />')" >View</a>
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
