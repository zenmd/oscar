<%@ include file="/taglibs.jsp"%>

<%@page import="org.oscarehr.PMmodule.web.formbean.*"%>
<%@page import="org.oscarehr.PMmodule.model.Program"%>
<%@page import="org.apache.struts.validator.DynaValidatorForm"%>

<div id="projecttools" class="toolgroup">
	<div class="label"><strong>Navigator</strong></div>
	<div class="body">
		<div><span><c:out value="${programName}" />(<c:out	value="${programId}" />)</span> 
			<div><a href="javascript:void(0)" style="color:Navy;text-decoration:none;" onclick="javascript:clickTab('General');return false;">General</a></div>
			<div><a href="javascript:void(0)" style="color:Navy;text-decoration:none;" onclick="javascript:clickTab('service_restrictions');return false;">Service Restriction</a></div>
			<div><a href="javascript:void(0)" style="color:Navy;text-decoration:none;" onclick="javascript:clickTab('staff');return false;">Staff</a></div>
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
