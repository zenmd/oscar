<%@ include file="/taglibs.jsp"%>

<script>
	function ConfirmDelete(name)
	{
		if(confirm("Are you sure you want to delete " + name + " ?")) {
			return true;
		}
		return false;
	}
	function submitForm(method)
	{
		document.programManagerForm.method.value=method;
		document.programManagerForm.submit()
	}
</script>

<%@ include file="/common/messages.jsp"%>

<div class="tabs" id="tabs">
<table cellpadding="3" cellspacing="0" border="0">
	<tr>
		<th title="Roles">Roles</th>
	</tr>
</table>
</div>

<display:table class="simple" cellspacing="2" cellpadding="3"
	id="program" name="programs" export="false" pagesize="0"
	requestURI="/PMmodule/ProgramManager.do">
	<display:setProperty name="paging.banner.placement" value="bottom" />
	<display:setProperty name="paging.banner.item_name" value="program" />
	<display:setProperty name="paging.banner.items_name" value="programs" />
	<display:setProperty name="basic.msg.empty_list" value="No programs found." />

	<display:column sortable="false" title="">
		<a
			onclick="return ConfirmDelete('<c:out value="${program.nameJs}"/>')"
			href="<html:rewrite action="/PMmodule/ProgramManager.do"/>?method=delete&id=<c:out value="${program.id}"/>&name=<c:out value="${program.name}"/>">
		Delete </a>
	</display:column>

	<c:choose>
		<c:when test="${program.programStatus=='active'}">
			<display:column sortable="false" title="">
				<a
					href="<html:rewrite action="/PMmodule/ProgramManager.do"/>?method=edit&id=<c:out value="${program.id}" />">
				Edit </a>
			</display:column>
		</c:when>
		<c:otherwise>
			<display:column sortable="false" title="">
 				Edit 
			</display:column>
		</c:otherwise>
	</c:choose>

	<display:column sortable="true" title="Role">
		<a
			href="<html:rewrite action="/PMmodule/ProgramManagerView.do"/>?id=<c:out value="${program.id}" />">
		<c:out value="${program.name}" /> </a>
	</display:column>
	
	<display:column property="descr" sortable="true" title="Description" />
	
</display:table>
