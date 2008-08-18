<%@ include file="/taglibs.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page import="org.oscarehr.PMmodule.model.Demographic"%>
<html>
<head>
	<title>Health and Safety</title>
<script type="text/javascript" src="<html:rewrite page="/js/validation.js" />"></script>
<script>
	/* this funcion serves as a protocol call for pages independent of the layout.jsp */
			 function initPage()
			 {
				return true;
			 }

function submitForm(form) {
 trimInputBox();
 var message = form.elements['healthsafety.message'].value;
 if(message!=null && message.length==0){
   alert("message can not be empty.");
   return false;
 }  
 if(message!=null && message.length>500){
   alert("message can not exceed 500 characters.");
   return false;
 }  
			
 if(noChanges())
 {
	 alert("There is no changes detected to save");
 }
 else
 {
  	form.submit();
  	opener.document.quatroClientSummaryForm.submit();
 }
}
</script>	
</head>

<body topmargin="20" leftmargin="10">

<html:form action="/PMmodule/QuatroHealthSafety.do">
<input type="hidden" name="method" value="savehealthSafety" />
<input type="hidden" name="clientId"/>
<input type="hidden" name="id"	value="<c:out value="${requestScope.id}"/>">
<html:hidden property="healthsafety.userName" />
<html:hidden property="healthsafety.demographicNo" />
<table border="2" width="700" cellspacing="0" cellpadding="0">
<tr><td colspan="3">Message:<br>
			<html:textarea property="healthsafety.message" rows="5" style="width:100%;"></html:textarea></td></tr>
<tr><td width="40%">User Name: <c:out value="${healthsafety.userName}"/> </td>
<td width="40%">Date: <fmt:formatDate value="${healthsafety.updateDate}" pattern="yyyy-MM-dd" /></td>
<td width="20%">
	<input type="button" value="Save" onclick="javascript: setNoConfirm();submitForm(document.healthSafetyForm)" />
	<input type="button" value="Cancel"	onclick="window.close()" />
</td></tr>
</table>
<%@ include file="/common/readonly.jsp" %>
</html:form>
</body>
</html>
