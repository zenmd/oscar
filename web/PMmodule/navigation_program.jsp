<%@ include file="/taglibs.jsp"%>

<%@page import="org.oscarehr.PMmodule.web.formbean.*"%>
<%@page import="org.oscarehr.PMmodule.model.Program"%>

<div id="projecttools" class="toolgroup">
	<div class="label"><strong>Navigator</strong></div>
	<div class="body">
		<% 	Program program = (Program) request.getAttribute("program"); 
						
			String selectedTab = request.getParameter("tab");
			if (selectedTab == null || selectedTab.trim().equals("")) {
				selectedTab = ProgramManagerViewFormBean.tabs[0];
			}
		%>
		<div><span><c:out value="${program.name}" />(<c:out	value="${program.id}" />)</span> 
		
		<%
			for (int i = 0; i < ProgramManagerViewFormBean.tabs.length; i++) {
				if (ProgramManagerViewFormBean.tabs[i].equalsIgnoreCase("Clients") && !program.isBed()) {
					//break;
					continue;
				}
				
				if (ProgramManagerViewFormBean.tabs[i].equalsIgnoreCase(selectedTab)) {
		%>
			
				<div><b><%=ProgramManagerViewFormBean.tabs[i]%></b></div>
			
		<%
				} else {
		%>
			
				<div><a href="javascript:void(0)" style="color:Navy;text-decoration:none;" onclick="javascript:clickTab('<%=ProgramManagerViewFormBean.tabs[i]%>');return false;"><%=ProgramManagerViewFormBean.tabs[i]%></a></div>
			
		<%
				}
			}
		%>
		
		
		
			
		</div>
	</div>
</div>

<script>
	function clickTab(name) {
		document.programManagerViewForm.tab.value=name;
		document.programManagerViewForm.submit();
	}
</script>
