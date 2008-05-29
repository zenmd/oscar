<%@ include file="/taglibs.jsp"%>

<%@ page import="org.oscarehr.PMmodule.web.formbean.*"%>
<%@ page import="org.oscarehr.PMmodule.model.Program"%>
<%@ page import="org.apache.struts.validator.DynaValidatorForm"%>
<%@ taglib uri="/WEB-INF/security.tld" prefix="security"%>

<html:form action="/PMmodule/ProgramManager">

	<html:hidden property="view.tab" />
	<input type="hidden" name="id" value="<c:out value="${requestScope.id}"/>" />
	<input type="hidden" name="method" value="edit" />
	<html:hidden property="program.id" />
	<input type="hidden" name="tab" />
	<html:hidden property="program.numOfMembers" />
			
	<table cellpadding="0" cellspacing="0" border="0" width="100%"	height="100%">
		
		<!-- Title -->
		<tr>
			<th class="pageTitle" align="center"><c:out value="${pageTitle}" /></th>
		</tr>

		<!-- Functions --> 
		<tr>
			<td>
				<% 	String selectedTab = request.getParameter("view.tab");
			 		//if (selectedTab == null || selectedTab.trim().equals("")) {
			 	 	//	selectedTab = ProgramManagerViewFormBean.tabs[0];
			 	 	//}
			 	  	String roleName$ = (String) session.getAttribute("userrole") + ","
			 			+ (String) session.getAttribute("user");
 				%> 
			
				<c:choose>
					<c:when test="${id != null && id gt 0}">
						<script>
							function clickTab(name) {
								//alert("clicktab = " + name);
								document.programManagerForm.action = "<c:out value='${ctx}'/>/PMmodule/ProgramManagerView.do?id=<c:out value='${requestScope.id}'/>";
								document.getElementsByName("tab")[0].value=name;
								document.programManagerForm.method.value="view";
								//alert("submit");
								document.programManagerForm.submit();
								//document.programManagerForm.method.value='edit';
								//document.programManagerForm.elements['view.tab'].value=name;
								//document.programManagerForm.submit();
							}
						</script>
			
	
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
	
							<tr height="18px">
								<td class="buttonBar2">
								<%
									DynaValidatorForm form = (DynaValidatorForm) session
											.getAttribute("programManagerForm");
									Program program = (Program) form.get("program");
	
									for (int i = 0; i < ProgramManagerViewFormBean.tabs.length; i++) {
										if (ProgramManagerViewFormBean.tabs[i].equalsIgnoreCase("Bed Check") && program.isService()) {
											//break;
											continue;
										}
	
										if (ProgramManagerViewFormBean.tabs[i].equalsIgnoreCase(selectedTab)) {
								%> 
									<b><%=ProgramManagerViewFormBean.tabs[i]%></b>&nbsp;&nbsp;|&nbsp;&nbsp;
								<%
										} else {
								%> 
									<a href="javascript:void(0)" style="color:Navy;text-decoration:none;"
									onclick="javascript:clickTab('<%=ProgramManagerViewFormBean.tabs[i]%>');return false;"><%=ProgramManagerViewFormBean.tabs[i]%></a>&nbsp;&nbsp;|&nbsp;&nbsp;
	
								<%
										}
									}
								%>
								</td>
							</tr>
						</table>

					</c:when>

				</c:choose>
			</td>
		</tr>

		<!-- function body start -->
		<tr>
			<td height="100%"> 
				<c:choose>
					<c:when test="${id != null && id gt 0}">
	
						<%
							if (selectedTab == null || selectedTab.trim().equals("")) {
						%>
						<security:oscarSec 
							objectName="_pmm_editProgram.general" rights="r">
							<jsp:include page="/PMmodule/Admin/ProgramEdit/general.jsp" />
						</security:oscarSec>
	
						<%
							} else {
						%>
						<jsp:include page='<%="/PMmodule/Admin/ProgramView/" + selectedTab.toLowerCase().replaceAll(" ","_") + ".jsp"%>' />
						<%
							}
						%>
					</c:when>
					<c:otherwise>
						<jsp:include page="/PMmodule/Admin/ProgramEdit/general.jsp" />
					</c:otherwise>
				</c:choose> 
			</td>
		</tr>
		<!-- function body end -->
	</table>


</html:form>
