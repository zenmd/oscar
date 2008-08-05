<!--
/*
 *
 * This software is published under the GPL GNU General Public License.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version. *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA. *
 *
 * <OSCAR Service Group>
 */
-->
<%@ taglib uri="/WEB-INF/security.tld" prefix="security" %>
<%@ page errorPage="../errorpage.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
  <html:html locale="true">
    <head>
      <title>
        Unlock
      </title>
      <meta http-equiv="Expires" content="Monday, 8 Aug 88 18:18:18 GMT"/>
      <meta http-equiv="Cache-Control" content="no-cache"/>
      <script type="text/javascript" language="JavaScript">

      <!--
		function setfocus() {
		  this.focus();
		}
	    function onSearch() {
	    }
//-->

      </script>
    </head>
    <body bgcolor="ivory" onLoad="setfocus()" style="margin: 0px">
    <security:oscarSec objectName="_admin" rights="w" reverse="<%=true%>" >
   		<table width="100%">
   		<tr>
   			<td>
   				Sorry, insufficient privilege to unlock user accounts. 
   			</td>
   		</tr>
   		</table>
	</security:oscarSec>

    <security:oscarSec objectName="_admin" rights="w">
      <table BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="100%">
        <tr>
          <td align="left">
            &nbsp;
          </td>
        </tr>
      </table>

      <center>
      <table BORDER="1" CELLPADDING="0" CELLSPACING="0" WIDTH="80%">
        <tr BGCOLOR="#CCFFFF">
          <th>
            <c:out value"${msg}">
          </th>
        </tr>
      </table>
      </center>
      <form method="post" name="baseurl" action="unLock.jsp">
      <table width="100%" border="0" cellspacing="2" cellpadding="2">
          <tr>
            <td>
              &nbsp;
            </td>
          </tr>
          <tr>
            <td align="right">
              <b>Role name</b>
            </td>
            <td>
            <%String a = "1"; %>
			  <select name="userId">
						<c:forEach var="linfo" items="${users}">
							<html-el:option value="${linfo.user.userName}">
								<c:out value="${linfo.user.userName}" />
							</html-el:option>
						</c:forEach>
 			  </select>
              
              <input type="submit" name="submit" value="Unlock" />
            </td>
          </tr>
          <tr>
            <td>
              &nbsp;
            </td>
            <td>
              &nbsp;
            </td>
          </tr>
          <tr>
            <td align="center" bgcolor="#CCCCFF" colspan="2">
              <input type="button" name="Cancel" value="<bean:message key="admin.resourcebaseurl.btnExit"/>" onClick="window.close()"/>
            </td>
          </tr>
      </table>
      </security:oscarSec>
      </form>
    </body>
  </html:html>
