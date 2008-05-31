<%@ include file="/taglibs.jsp"%>

<%@page import="org.oscarehr.PMmodule.web.formbean.*"%>
<%@page import="org.oscarehr.PMmodule.model.Program"%>
<%@page import="org.apache.struts.validator.DynaValidatorForm"%>

<div id="projecttools" class="toolgroup">
	<div class="label"><strong>Navigator</strong></div>
	<div class="body">
		<% 	DynaValidatorForm form = (DynaValidatorForm) session.getAttribute("programManagerForm");
			Program program = (Program) form.get("program"); 
			request.setAttribute("program", program);	
					
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
				
				
		%>
			
				<div><a href="javascript:void(0)" style="color:Navy;text-decoration:none;" onclick="javascript:clickTab('<%=ProgramManagerViewFormBean.tabs[i]%>');return false;"><%=ProgramManagerViewFormBean.tabs[i]%></a></div>
			
		<%
				
			}
		%>
		
		</div>
	</div>
</div>

<script>
	function clickTab(name) {
		//alert("clicktab = " + name);
		document.programManagerForm.action = "<c:out value='${ctx}'/>/PMmodule/ProgramManagerView.do?id=<c:out value='${requestScope.id}'/>";
		document.getElementsByName("tab")[0].value=name;
		document.programManagerForm.method.value="view";
		//alert("submit");
		document.programManagerForm.submit();
		
	}
</script>
