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
String buildDate = "2008-05-01 09:15"; //buildInfo.getBuildDate();

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
if(props.isSiteSecured()) {
	response.sendRedirect("login.jsp");
}
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
    
    <body onLoad="setfocus()" bgcolor="#ffffff">
        
        <table border=0 width="100%" height="100%">
            <tr>
                <td align="center" class="leftbar" height="20px"><%
                    String key = "loginApplication.formLabel" ;
                    if(request.getParameter("login")!=null && request.getParameter("login").equals("failed") )
                    key = "loginApplication.formFailedLabel" ;
                    %><bean:message key="<%=key%>"/>        
                </td>
                <td  class="topbar" align="center" >
                    <%=props.getProperty("logintitle", "")%>
                    <% if (props.getProperty("logintitle", "").equals("")) { %>
                    <bean:message key="loginApplication.alert2"/>
                    <% } %>
                    <br>                    
                </td>
            </tr>
            <tr>
                <td width="200  " class="leftinput" valign="top">
                    <!--- left side -->
                        
                            <html:form action="login" >
                            <bean:message key="loginApplication.formUserName"/><%
                            if(oscar.oscarSecurity.CRHelper.isCRFrameworkEnabled() && !net.sf.cookierevolver.CRFactory.getManager().isMachineIdentified(request)){
                            %><img src="gatekeeper/appid/?act=image&/empty<%=System.currentTimeMillis() %>.gif" width='1' height='1'><%
                            }
                            %>
                        
                        <br/>            
                        <input type="text" name="username" size="15" maxlength="15" autocomplete="off" value="oscardoc"/>
                        <br/>                
                        <bean:message key="loginApplication.formPwd"/><br/>
                        <input type="password" name="password" size="15" maxlength="15" autocomplete="off" value="mac2002"/>
                        </br></br>
                        <bean:message key="index.formPIN"/>: 
                        <br/>
                        <input type="password" name="pin" size="15" maxlength="15" autocomplete="off" value="1117"/><br/>
                       
                        <span class="extrasmall">
                            <bean:message key="loginApplication.formCmt"/>
                        </span>
                        <input type=hidden name='propname' value='<bean:message key="loginApplication.propertyFile"/>' />
                        <br/><input type="submit" value="<bean:message key="index.btnSignIn"/>" />

                        </html:form>
                        <hr width="100%" color="navy">
                        
                        <span class="extrasmall">
                            <bean:message key="loginApplication.leftRmk2"/>
                            <a href=# onClick='popupPage(500,700,"<bean:message key="loginApplication.gpltext"/>")'><bean:message key="loginApplication.gplLink2"/></a>
                        </span>
                    <!-- left side end-->
                </td>
                <td align="center" valign="top"><br><br><br>
                    <div style="margin-top:25px;">
                            <img src="images/QuatroShelterBigLogo.gif">
                            <br/>
                   			<span style="float: center; font-size: xx-small;">build date: <%= buildDate %></span>    
                            <p>
                            <font face="Verdana, Arial, Helvetica, sans-serif" size="-1">
                                <br><br><br><br><br><br>Powered By <br><img src="images/CAISIlogoBIG.bmp"><br><img src="images/OSCARLogo.jpg"><br> version 2.4 02-15-2008
                            </font>
                    </div>
                    
                </td>
            </tr>     
        </table>
        
    </body>
</html:html>
