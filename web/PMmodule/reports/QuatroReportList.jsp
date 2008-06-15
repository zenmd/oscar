<%@ include file="/taglibs.jsp"%>
<html>
<head>
<script language="JavaScript">
	function submitForm(mthd)
	{
		document.forms[0].method.value=mthd;
		document.forms[0].submit();
	}
</script>
</head>
<body>
<table width="100%" cellpadding="0px" cellspacing="0px" style="border-width: 1px; border-style: solid; bordercolor: black">
	<tr>
		<th width="100%" class="pageTitle" align="center"><span
			id="_ctl0_phBody_lblTitle" align="left">Reports</span></th>
	</tr>
	<tr>
		<td align="left" class="buttonBar">
			<html:link action="/Home.do"
			style="color:Navy;text-decoration:none">&nbsp;
			<img style="vertical-align: middle" border=0 src=<html:rewrite page="/images/close16.png"/> />&nbsp;Close&nbsp;&nbsp;|</html:link>
		
			<a
			href="javascript:submitForm('delete');"
			style="color:Navy;text-decoration:none;">&nbsp;
			<img style="vertical-align: middle" border=0 src=<html:rewrite page="/images/Delete16.gif"/> />&nbsp;Delete Template(s)&nbsp;&nbsp;</a>
		</td>
	</tr>
</table>
	

<html:form action="/QuatroReport/ReportList.do">
<div class="tabs" id="tabs">
	<table cellpadding="3" cellspacing="0" border="0">
		<tr>
			<th title="Reports">Reports</th>
		</tr>
	</table>
</div>

<table cellpadding="3" cellspacing="0" border="0" width="100%" class="toolgroup">
<tr><td align="left">
<img src="../images/Delete16.gif"/>
<a href="javascript:submitForm('delete');">Delete Template(s)</a>&nbsp;|&nbsp;
</td></tr>
</table>
<table width="100%">
<tr><td>
<%StringBuffer str= new StringBuffer(); %>
<logic:iterate id="reportGroup" property="reportGroups" name="quatroReportListForm" indexId="rIndex1">
<ul><bean:write name="reportGroup" property="reportGroupDesc"/>
  <ul>
	<logic:iterate id="report" property="reports" name="reportGroup" indexId="rIndex2">
	  <li>
        <a href="<html:rewrite action="/QuatroReport/ReportRunner.do"/>?id=<c:out value="${report.reportNo}" />"> <c:out value="${report.title}"/> - <c:out value="${report.description}"/> </a>
      </li>
   	  <logic:iterate id="template" property="childList" name="report" indexId="rIndex3">
        <ul>
          <input type="checkbox" name='p<c:out value="${rIndex1}"></c:out>_<c:out value="${rIndex2}"></c:out>_<c:out value="${rIndex3}"></c:out> value="<c:out value="${template.templateNo}" />">
          <%
             str.append("," + String.valueOf(rIndex1) + "_" + String.valueOf(rIndex2) + "_" + String.valueOf(rIndex3)); 
             
          %>
          <a href="<html:rewrite action="/QuatroReport/ReportRunner.do"/>?id=<c:out value="${report.reportNo}" />&templateNo=<c:out value="${template.templateNo}" />"> <c:out value="${template.desc}"/> </a>
        </ul>
	  </logic:iterate>
	</logic:iterate>
  </ul>
</ul>
</logic:iterate>
<input type="hidden" name="chkDel" value='<c:out value="${str}"></c:out>' />
</td></tr>
 </table>
</html:form>
</body>
</html>