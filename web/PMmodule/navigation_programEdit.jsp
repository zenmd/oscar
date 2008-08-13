<%@ include file="/taglibs.jsp"%>

<%@page import="org.oscarehr.PMmodule.web.formbean.*"%>
<%@page import="org.oscarehr.PMmodule.model.Program"%>
<%@page import="org.apache.struts.validator.DynaValidatorForm"%>
<%@page import="com.quatro.common.KeyConstants" %>

<div id="projecttools" class="toolgroup">
	<div class="label"><strong>Navigator</strong></div>
	<div class="body">
		<div><span><c:out value="${programName}" />(<c:out	value="${programId}" />)</span> 		
			<c:choose>
				<c:when test="${'C' eq General}">
					<div><b>General</b></div>
				</c:when>
				<c:when test="${'V' eq General}">
					<div><html:link
						action="/PMmodule/ProgramManager.do?method=edit&view.tab=General" name="actionParam"  
						style="color:Navy;text-decoration:none;">General</html:link></div>
				</c:when>
				<c:otherwise>
							&nbsp;
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${'C' eq Service}">
					<div><b>Service Restriction</b></div>
				</c:when>
				<c:when test="${'V' eq Service}">
					<div>
						<html:link
						action="/PMmodule/ProgramManager.do?method=edit&view.tab=Service" 
						style="color:Navy;text-decoration:none;" name="actionParam">Service Restriction</html:link></div>
				</c:when>
				<c:otherwise>
							&nbsp;
				</c:otherwise>
			</c:choose>			
			<!-- 
				<div><a href="javascript:void(0)" style="color:Navy;text-decoration:none;" onclick="javascript:clickTab('General');return false;">General</a></div>
			<div><a href="javascript:void(0)" style="color:Navy;text-decoration:none;" onclick="javascript:clickTab('service_restrictions');return false;">Service Restriction</a></div>
			<div><a href="javascript:void(0)" style="color:Navy;text-decoration:none;" onclick="javascript:clickTab('staff');return false;">Staff</a></div>
			 -->			
		</div>
	</div>
</div>

<script>
	function clickTab(name) {
		document.programManagerForm.action = "<c:out value='${ctx}'/>/PMmodule/ProgramManager.do?method=edit&id=<c:out value='${requestScope.id}'/>";
		document.programManagerForm.elements["view.tab"].value=name;
		document.programManagerForm.method.value="edit";
		document.programManagerForm.submit();
	}
</script>