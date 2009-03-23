<%@ include file="/taglibs.jsp" %>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon" />
<title>signatureview</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Application Developer">
</head>
<body bgcolor="#000000" >
<table width="100%">
<tr><td>This is your signature</td></tr>
<tr><td><img src="<%=request.getContextPath()%>/topazGetImage.do?rid=<%=request.getParameter("rid")%>" width="150px" height="45px"></td></tr>
<tr><td><br><img src="<%=request.getContextPath()%>/topazGetImage.do?rid=<%=request.getParameter("rid")%>"></td></tr>
</table>
</body>
</html>
