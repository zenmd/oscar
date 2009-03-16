<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp"%>

<html>
<head>
	    <link rel="stylesheet" type="text/css" href="/QuatroShelter/css/core.css" />

</head>
<body>
	<table align="center" border="0" cellspacing="0" width="100%" height="100%">	
		<tr>		
			<td>			
			<table align="center">				
			<tr>					
			<td align="center"><img src='/QuatroShelter/images/QuatroShelter-Logo400.gif' height="80" width="400" /></td>
			</tr>
			<tr>
			<Td align="center">&nbsp;</td></tr>
			<tr>
				<td align="center">&nbsp;</td>				
</tr></table>
		</td>	
		</tr>	
		<tr>
  			<td align="center" style="{color: #000000;background-color: #EEEEFF;text-align: center; font-weight: bold;}">
  						<logic:messagesPresent
			message="true">
			<html:messages id="message" message="true" bundle="pmm">
				<c:out escapeXml="false" value="${message}" />
				</html:messages>
			</logic:messagesPresent>
			</td><tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr>
	    	<tr><td  align="center">
	    		<a href="javascript:window.close();">Close</a>
	    	</td></tr>
	    	</table>
</body>
</html>
