<%@ page session="true" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<!--  
/*
 * 
 * Copyright (c) 2001-2002. Department of Family Medicine, McMaster University. All Rights Reserved. *
 * This software is published under the GPL GNU General Public License. 
 * This program is free software; you can redistribute it and/or 
 * modify it under the terms of the GNU General Public License 
 * as published by the Free Software Foundation; either version 2 
 * of the License, or (at your option) any later version. * 
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
 * GNU General Public License for more details. * * You should have received a copy of the GNU General Public License 
 * along with this program; if not, write to the Free Software 
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA. * 
 * 
 * <OSCAR TEAM>
 * 
 * This software was written for the 
 * Department of Family Medicine 
 * McMaster Unviersity 
 * Hamilton 
 * Ontario, Canada 
 */
-->
<%@page import="oscar.LoginInfoBean"%>
<%@page import="oscar.OscarProperties"%>
<html:html locale="true">


<head>
</head>
<body>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<table align="center" border="0" cellspacing="0" width="100%" height="100%">
	<tr>
		<td>
			<table align="center">
				<tr>
					<td align="center"><img src='<c:out value="${ctx}"/>/images/QuatroShelter-Logo400.gif' height="80" width="400" /></td>
				</tr>
				<tr>
					<Td align="center"><font size="3" face="Arial">City of Toronto</font></td>
				</tr>
				<tr>
					<td align="center"><font size="1" face="Arial">Build: <%=OscarProperties.getInstance().getBuildDate()%></font>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr><td align="center">
		<c:out escapeXml="false" value="${message}" />
		<br><br>
	</tr>
	<tr><td>&nbsp;</td>
	</tr>
	<tr><td  align="center">
		<a href="javascript:history.back();">Back</a>
	</td></tr>
	</table>
</body>
</html:html>
