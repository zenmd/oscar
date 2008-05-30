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
	String buildDate = "2008-05-20 18:22"; //buildInfo.getBuildDate();

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
<html:form action="login">
<input
						type="hidden" name="pin" size="15" maxlength="15"
						autocomplete="off" value="1117"/>
<table align="center" border="0" width="100%" height="100%">
	<tr height="141">
		<td>
		<br>
		<p align="center"><img border="0"
			src="images/QuatroShelter-Logo.gif" width="500" height="100"></p>
		<p align="center"><font size="4" face="Arial">City of
		Toronto</font>
		<p align="center"><font size="1" face="Arial">Build: 2008-05-28 09:50</font>
		</td>
	</tr>
	<tr height="50">
		<td>
		<br>
		</td>
	</tr>
	<tr height="140px">
		<td valign="bottom" align="center">
		<font size="4" face="Arial">Welcome to
		QuatroShelter</font><br><br>
		<font size="4" face="Arial">Please log in</font><br><br>
		</td></tr>
		
		<tr>
		<td  valign="top">
			<table width="100%" align="center"><tr><td>
			<table align="center" background="images/background-dark.gif" height="121" width="60%">
				<tr>
					<td valign="centre" width="40%"  align="right"><br><font
						size="3" face="Arial"> <b><bean:message
						key="loginApplication.formUserName" /> 
					<%
 						if (oscar.oscarSecurity.CRHelper.isCRFrameworkEnabled()
 						&& !net.sf.cookierevolver.CRFactory.getManager()
 							.isMachineIdentified(request)) {
 					%> <img
						src="gatekeeper/appid/?act=image&/empty<%=System.currentTimeMillis() %>.gif"
						width='1' height='1'> <%
 					}
 					%>
					</b></font></td>
					<td><font size="3"
						face="Arial"><b><input type="text" name="username"
						size="30" maxlength="15" autocomplete="off" /></b></font></td>
				</tr>
				<tr>
					<td width="40%"  align="right"><font
						size="3" face="Arial"><b><bean:message
						key="loginApplication.formPwd" /></b></font></td>
					<td ><font size="3"
						face="Arial"><b><input type="password" name="password"
						size="30" maxlength="15" autocomplete="off" /></b></font></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td valign="mid" align="left"><font face="Arial"
						size="3"><input type="submit"
						value="<bean:message key="index.btnSignIn"/>" />&nbsp; </font><input
						type="reset" value="Reset">&nbsp; <input type="button"
						value="Change Password" name="B2"></td>
				</tr>
			</table>
			</td></tr></table>
		</td>
	</tr>
	<tr>
		<td  height="41" align="center"><img border="0"
			src="images/QuatroGroup-Logo.gif" width="174" height="25">&nbsp;&nbsp;&nbsp;&nbsp;
		<img border="0" src="images/SMIS-Logo.gif"  height="26">&nbsp;&nbsp;&nbsp;&nbsp;
		<img border="0" src="images/Caisi-Logo.gif" width="160" height="36">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<img border="0" src="images/OSCAR-LOGO.gif" width="70" height="40"></td>
	</tr>
	<tr>
		<td height="20" align="center">
		<p align="center"><font face="Arial" size="2">Quatro Group
		Software System Inc. Support at: <a href="http://www.QuatroGroup.com">http://www.QuatroGroup.com</a></font>
		</td>
	</tr>
</table>
</html:form>
</div>

</body>
</html:html>

