<%@ include file="/taglibs.jsp"%>

<%@page import="org.oscarehr.PMmodule.web.formbean.*"%>
<%@page import="org.oscarehr.PMmodule.model.Program"%>

<div id="projecttools" class="toolgroup">
	<div class="label"><strong>Navigator</strong></div>
	<div class="body">		
		<div><span><c:out value="${program.name}" />(<c:out	value="${program.id}" />)</span> 
			<c:choose>
				<c:when test="${'C' eq General}">
					<div><b>General</b></div>
				</c:when>
				<c:when test="${'V' eq General}">
					<div>
					<html:link	action="/PMmodule/ProgramManagerView.do?tab=General" name="actionParam"  
						style="color:Navy;text-decoration:none;">General</html:link></div>
				</c:when>
				<c:otherwise>
							&nbsp;
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${'C' eq Queue}">
					<div><b>Queue</b></div>
				</c:when>
				<c:when test="${'V' eq Queue}">
					<div>
						<html:link
						action="/PMmodule/ProgramManagerView.do?tab=Queue" 
						style="color:Navy;text-decoration:none;" name="actionParam">Queue</html:link></div>
				</c:when>
				<c:otherwise>
							&nbsp;
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${'C' eq Clients}">
					<div><b>Clients</b></div>
				</c:when>
				<c:when test="${'V' eq Clients}">
					<div><html:link
						action="/PMmodule/ProgramManagerView.do?tab=Clients" name="actionParam"  
						style="color:Navy;text-decoration:none;">Clients</html:link></div>
				</c:when>
				<c:otherwise>
							&nbsp;
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${'C' eq Incidents}">
					<div><b>Incidents</b></div>
				</c:when>
				<c:when test="${'V' eq Incidents}">
					<div><html:link
						action="/PMmodule/ProgramManagerView.do?tab=Incidents" name="actionParam"  
						style="color:Navy;text-decoration:none;">Incidents</html:link></div>
				</c:when>
				<c:otherwise>
							&nbsp;
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${'C' eq Service}">
					<div><b>Service Restrictions</b></div>
				</c:when>
				<c:when test="${'V' eq Service}">
					<div><html:link
						action="/PMmodule/ProgramManagerView.do?tab=Service" name="actionParam"  
						style="color:Navy;text-decoration:none;">Service Restrictions</html:link></div>
				</c:when>
				<c:otherwise>
							&nbsp;
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${'C' eq Staff}">
					<div><b>Staff</b></div>
				</c:when>
				<c:when test="${'V' eq Staff}">
					<div><html:link
						action="/PMmodule/ProgramManagerView.do?tab=Staff" name="actionParam"  
						style="color:Navy;text-decoration:none;">Staff</html:link></div>
				</c:when>
				<c:otherwise>
							&nbsp;
				</c:otherwise
			</c:choose>	
		</div>
	</div>
</div>

<script>
	function clickTab(name) {
		document.programManagerViewForm.tab.value=name;
		document.programManagerViewForm.submit();
	}
</script>
