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

<%@ page language="java" contentType="text/html"
	import="oscar.OscarProperties,oscar.util.BuildInfo,javax.servlet.http.Cookie,oscar.oscarSecurity.CookieSecurity"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/caisi-tag.tld" prefix="caisi"%>
<caisi:isModuleLoad moduleName="ticklerplus">
	<%
		if (session.getAttribute("user") != null) {
			response.sendRedirect("provider/providercontrol.jsp");
		}
	%>
</caisi:isModuleLoad>
<%
	OscarProperties props = OscarProperties.getInstance();

	BuildInfo buildInfo = BuildInfo.getInstance();
	String buildDate = "2008-05-12 14:55"; //buildInfo.getBuildDate();

	// clear old cookies
	Cookie rcpCookie = new Cookie(CookieSecurity.receptionistCookie, "");
	Cookie prvCookie = new Cookie(CookieSecurity.providerCookie, "");
	Cookie admCookie = new Cookie(CookieSecurity.adminCookie, "");
	rcpCookie.setPath("/");
	prvCookie.setPath("/");
	admCookie.setPath("/");
	response.addCookie(rcpCookie);
	response.addCookie(prvCookie);
	response.addCookie(admCookie);
	if (props.isSiteSecured()) {
		response.sendRedirect("login.jsp");
	}
%>

<html:html locale="true">
<head>
<html:base />
<META HTTP-EQUIV="CONTENT-TYPE" CONTENT="text/html; charset=UTF-8">
<title>
<%
if (props.getProperty("logintitle", "").equals("")) {
%> <bean:message
	key="loginApplication.title" /> <%
 } else {
 %> <%=props.getProperty("logintitle", "")%>
<%
}
%>
</title>
<!--LINK REL="StyleSheet" HREF="web.css" TYPE="text/css"-->

<script language="JavaScript">
  <!-- hide
  function setfocus() {
    document.loginForm.username.focus();
    document.loginForm.username.select();
  }
  function popupPage(vheight,vwidth,varpage) {
    var page = "" + varpage;
    windowprops = "height="+vheight+",width="+vwidth+",location=no,scrollbars=yes,menubars=no,toolbars=no,resizable=yes";
    var popup=window.open(page, "gpl", windowprops);
  }
  -->
        </script>

<style type="text/css">
            body { 
               font-family: Verdana, helvetica, sans-serif;
               margin-left: 1px;
               margin-right: 0px;
               margin-bottom: 0px;
               margin-top: 0px;
               margin: 0px;
               padding:0px;
               
            }
            
            td.topbar{
               background-color: gold;
               
               
            }
            td.leftbar{
                background-color: #009966;
                color: white;
            }
            td.leftinput{
                background-color: #f5fffa;
                //background-color: #ffffff;
                
                font-size: small;
                }
                
                span.extrasmall{
                    font-size: x-small;
                }
        </style>
</head>


<body>


<div align="center">

<table border="0" width="766" height="495">
	<tr>
		<td width="766" height="141" colspan="2">
		<p align="center"><img border="0"
			src="images/QuatroShelter-Logo.gif" width="500" height="100"></p>
		<p align="center"><font size="4" face="Arial">City of
		Toronto</font>
		</td>
	</tr>
	<tr>
		<td width="750" height="60" valign="top" colspan="2">
		<p align="center"><font size="4" face="Arial">Welcome to
		QuatroShelter, please Login ...</font></p>
		<p align="center">&nbsp;</p>
	<tr>
		<td height="183"><html:form action="login">

			<table background="images/Silver-background-dark.gif" height="141">
				<tr>
					<td width="334" height="36" valign="top" align="right"><font
						size="3" face="Arial"> <b><bean:message
						key="loginApplication.formUserName" /> <%
 			if (oscar.oscarSecurity.CRHelper.isCRFrameworkEnabled()
 			&& !net.sf.cookierevolver.CRFactory.getManager()
 			.isMachineIdentified(request)) {
 %> <img
						src="gatekeeper/appid/?act=image&/empty<%=System.currentTimeMillis() %>.gif"
						width='1' height='1'> <%
 }
 %>
					</b></font></td>
					<td width="416" height="36" valign="top"><font size="3"
						face="Arial"><b><input type="text" name="username"
						size="15" maxlength="15" autocomplete="off" value="oscardoc" /></b></font></td>
				</tr>
				<tr>
					<td width="334" height="27" valign="top" align="right"><font
						size="3" face="Arial"><b><bean:message
						key="loginApplication.formPwd" /></b></font></td>
					<td width="416" height="27" valign="top"><font size="3"
						face="Arial"><b><input type="password" name="password"
						size="15" maxlength="15" autocomplete="off" value="mac2002" /></b></font></td>
				</tr>
				<tr>
					<td width="334" height="34" valign="top" align="right"><b><font
						size="3" face="Arial"><bean:message key="index.formPIN" /></font></b></td>
					<td width="416" height="34" valign="top"><b><input
						type="password" name="pin" size="15" maxlength="15"
						autocomplete="off" value="1117" /></b></td>
				</tr>
				<tr>
					<td width="334" height="28" valign="top"></td>
					<td width="416" height="28" valign="top"><font face="Arial"
						size="3"><input type="submit"
						value="<bean:message key="index.btnSignIn"/>" />&nbsp; </font><input
						type="reset" value="Reset">&nbsp; <input type="button"
						value="Change PW" name="B2"></td>
				</tr>
			</table>

		</html:form></td>
	<tr>
		<td width="766" height="41" colspan="2"><img border="0"
			src="images/QuatroGroup-Logo.gif" width="174" height="25">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<img border="0" src="images/SMIS-Logo.gif" width="189" height="26">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<img border="0" src="images/Caisi-Logo.gif" width="160" height="36">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<img border="0" src="images/OSCAR-LOGO.gif" width="70" height="40"></td>
	</tr>
	<tr>
		<td width="766" height="40" colspan="2">
		<p align="center"><font face="Arial" size="2">Quatro Group
		Software System Inc. Support at: <a href="http://www.QuatroGroup.com">http://www.QuatroGroup.com</a></font>
		</td>
	</tr>
</table>
</div>

</body>
</html:html>

