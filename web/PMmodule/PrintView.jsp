<%@ include file="/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/quatro-tag.tld" prefix="quatro" %>
<%@ page import="com.crystaldecisions.report.web.viewer.CrystalReportViewer" %>
<%@ page import="com.crystaldecisions.reports.sdk.ReportClientDocument" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<script type="text/javascript" src='<c:out value="${ctx}"/>/js/quatroLookup.js'></script>
 
<html-el:form action="/PMmodule/PrintView.do">
<input type="hidden" name="method"/>
<html:hidden property="id"/>

<script lang="javascript">
function submitForm(methodVal) {
	document.forms[0].method.value = methodVal;
	document.forms[0].submit();
</script>
	<table width="100%">
         <tr>
            <td align="left" class="clsButtonBar" width="100%">
                <A Font-Underline="false" ForeColor="navy" id ="lnkPrint" runat="server" target="_blank">
                <img border=0 src="/<%=_appPath %>/@Images/Print16.png"/>&nbsp;&nbsp;Print&nbsp;&nbsp;</a>
            </td>
        </tr>   
</table>
</html-el:form>
