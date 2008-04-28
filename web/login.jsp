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

<%@ page language="java" contentType="text/html" import="oscar.OscarProperties, oscar.util.BuildInfo, javax.servlet.http.Cookie, oscar.oscarSecurity.CookieSecurity" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/caisi-tag.tld" prefix="caisi" %>
<caisi:isModuleLoad moduleName="ticklerplus"><%
    if(session.getAttribute("user") != null) {
        response.sendRedirect("provider/providercontrol.jsp");
    }
%></caisi:isModuleLoad><%
OscarProperties props = OscarProperties.getInstance();
BuildInfo buildInfo = BuildInfo.getInstance();
String buildDate = buildInfo.getBuildDate();
	
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
%>

<html:html locale="true">
    <head>
        <html:base/>
        <META HTTP-EQUIV="CONTENT-TYPE" CONTENT="text/html; charset=UTF-8">
        <title>
            <% if (props.getProperty("logintitle", "").equals("")) { %>
            <bean:message key="loginApplication.title"/>
            <% } else { %>
            <%= props.getProperty("logintitle", "")%>
            <% } %>
        </title>
        <!--LINK REL="StyleSheet" HREF="web.css" TYPE="text/css"-->
        
        <style type="text/css">
            body { 
               font-family: Verdana, helvetica, sans-serif;
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
    
    <body bgcolor="#ffffff">
        
        <table border=0 width="100%" height="100%">
            <tr>
                <td align="center" class="leftbar" height="20px"><%
                    String key = "loginApplication.formLabel" ;
                    if(request.getParameter("login")!=null && request.getParameter("login").equals("failed") )
                    key = "loginApplication.formFailedLabel" ;
                    %><bean:message key="<%=key%>"/>        
                </td>
                <td  class="topbar" align="center" >
<!--                <span style="float: right; color:#FFFFFF; font-size: xx-small;">build date: <%= buildDate %></span> -->    
                    <%=props.getProperty("logintitle", "")%>
                    <% if (props.getProperty("logintitle", "").equals("")) { %>
                    <bean:message key="loginApplication.alert2"/>
                    <% } %>                    
                </td>
            </tr>
            <tr>
                <td width="200  " class="leftinput" valign="top">
                    <!--- left side -->
                    		<%
                            if(oscar.oscarSecurity.CRHelper.isCRFrameworkEnabled() && !net.sf.cookierevolver.CRFactory.getManager().isMachineIdentified(request)){
                            %><img src="gatekeeper/appid/?act=image&/empty<%=System.currentTimeMillis() %>.gif" width='1' height='1'><%
                            }
                            %>
                        <br/>            
					<table border="0" bordercolor="#000000" cellpadding="0" cellspacing="0" width="300px" align="center">
					<tr><td align="center" style="background-color: #c0c0c0;">
					<OBJECT classid="clsid:8AD9C840-044E-11D1-B3E9-00805F499D93"
					    width="300" height="300" align="baseline"
					    codebase="http://java.sun.com/products/plugin/1.4/jinstall-14-win32.cab#Version=1,4,0,mn">
					    <PARAM NAME="code" VALUE="com.quatro.model.security.QSSecurity.class">
					    <PARAM NAME="archive" VALUE="QSSecurity.jar, registry.jar">
					    <PARAM NAME="type" VALUE="application/x-java-applet;jpi-version=1.4">
					    <COMMENT>
					        <EMBED type="application/x-java-applet;jpi-version=1.4" width="300"
					           height="200" align="baseline" code="com.quatro.model.security.QSSecurity.class" 
					           archive="QSSecurity.jar, registry.jar" 
					           pluginspage="http://java.sun.com/j2se/1.4/download.html">
					            <NOEMBED>
					                No Java 2 SDK, Standard Edition v 1.4 support for APPLET!!
					            </NOEMBED>
					        </EMBED>
					    </COMMENT>
					</OBJECT>
					</td></tr></table>
                    <!-- left side end-->
                </td>
                <td align="center" valign="top"><br><br><br>
                    <div style="margin-top:25px;"><% if (props.getProperty("loginlogo", "").equals("")) { %>
                            <html:img srcKey="loginApplication.image.logo"/>
                            <% } else { %>
                            <img src="<%=props.getProperty("loginlogo", "")%>">
                            <% } %>
                            <p>Version 1.1 (Build: Feb 12, 9:00)</p>
                            
                            <p>
                            <font face="Verdana, Arial, Helvetica, sans-serif" size="-1">
                                <% if (props.getProperty("logintext", "").equals("")) { %>
                                <bean:message key="loginApplication.image.logoText2"/>
                                <% } else { %>
                                <%=props.getProperty("logintext", "")%>
                                <% } %>
                            </font>
                    </div>
                    
                </td>
            </tr>     
        </table>
        
    </body>
</html:html>
