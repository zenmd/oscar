<!--
	Copyright (c) 2001-2002.
	
	Centre for Research on Inner City Health, St. Michael's Hospital, Toronto. All Rights Reserved.
	This software is published under the GPL GNU General Public License.
	This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
	This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
	See the GNU General Public License for more details.
	You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
	
	OSCAR TEAM
	
	This software was written for Centre for Research on Inner City Health, St. Michael's Hospital, Toronto, Ontario, Canada
-->
<%@ include file="/taglibs.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
	<head>
		<title>Program Management Module</title>
	    <link rel="stylesheet" type="text/css" href='<html:rewrite page="/css/tigris.css" />' />
	    <link rel="stylesheet" type="text/css" href='<html:rewrite page="/css/displaytag.css" />' />
	    <link rel="stylesheet" type="text/css" href='<html:rewrite page="/jsCalendar/skins/aqua/theme.css" />' />
	    <link rel="stylesheet" type="text/css" href='<html:rewrite page="/css/core.css" />' />

        <style type="text/css">
            body { 
               font-family: Verdana, helvetica, sans-serif;
               margin: 0px;
               padding:0px;
               
            }
		</style -->
		<script type="text/javascript" src="<html:rewrite page="/jsCalendar/calendar.js" />" /></script>
		<script type="text/javascript" src="<html:rewrite page="/jsCalendar/lang/calendar-en.js" />"></script>
		<script type="text/javascript" src="<html:rewrite page="/jsCalendar/calendar-setup.js" />"></script>
		<script type="text/javascript" src="<html:rewrite page="/js/quatroLookup.js" />"></script>
				
		<script type="text/javascript">
			var djConfig = {
				isDebug: false,
				parseWidgets: false,
				searchIds: ["addPopupTimePicker"]
			};
		</script>
	    
		<script type="text/javascript" src="<html:rewrite page="/dojoAjax/dojo.js" />">
		</script>
		
		<script type="text/javascript" language="JavaScript">
            dojo.require("dojo.date.format");
			dojo.require("dojo.widget.*");
			dojo.require("dojo.validate.*");
		</script>
		
		<script type="text/javascript" src="<html:rewrite page="/js/genericIntake.js.jsp" />"></script>
		
		<html:base />
	</head>
	<body>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="100%">
				<tr height="60px">
				<td>
					<tiles:insert  name="Header.jsp">
					</tiles:insert>
				</td></tr>
				<tr valign="top" height="100%">
					<td><table width="100%" height="100%"><tr>
						<td id="leftcol" width="200px">
							<tiles:insert name="navigation.jsp" />
						</td>
						<td valign="top" width="3px">
							<img src='<html:rewrite page="/images/1x1.gif" />' width="3px" />
						</td>
						<td align="left">
							<!--  div class="body" align="left"  this is the layout-->
								<tiles:insert attribute="body" />
							<!--  /div -->
						</td>
					</tr></table></td>
				</tr>
			</table>
	</body>
</html:html>