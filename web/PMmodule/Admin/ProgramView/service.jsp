<!--
/*
*
* Copyright (c) 2001-2002. Centre for Research on Inner City Health, St. Michael's Hospital, Toronto. All Rights Reserved. *
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
* This software was written for
* Centre for Research on Inner City Health, St. Michael's Hospital,
* Toronto, Ontario, Canada
*/
-->

<%@ include file="/taglibs.jsp"%>
<table width="100%" cellpadding="0px" cellspacing="0px" border="0" height="100%"> 
        <tr height="18px"><td align="left" class="buttonBar2">
			<html:link
			action="/PMmodule/ProgramManager.do"
			style="color:Navy;text-decoration:none;">&nbsp;
			<img style="vertical-align: middle" border="0" src="<html:rewrite page="/images/Back16.png"/>" />&nbsp;Back to Programs&nbsp;&nbsp;</html:link>
        </td>
        </tr>
<tr> <td> 	<br />
<div class="tabs" id="tabs">
    <table cellpadding="3" cellspacing="0" border="0">
        <tr>
            <th title="Service Restrictions">Service Restrictions</th>
        </tr>
    </table>
</div>
</td>
</tr>
<tr height="100%">
	<td>
                    <div style="color: Black; background-color: White; border-style: ridge; border-width: 1px;
                        width: 100%; height: 100%; overflow: auto">

<display:table class="simple" cellspacing="2" cellpadding="3" id="restriction" name="service_restrictions" export="false" pagesize="0" requestURI="/PMmodule/ProgramManagerView.do">
    <display:setProperty name="paging.banner.placement" value="bottom" />
    <display:setProperty name="basic.msg.empty_list" value="No service restrictions currently in place for this program." />
    <display:column property="client.demographicNo" sortable="true" title="Client Id" />
    <display:column property="client.formattedName" sortable="true" title="Client Name" />
    <display:column property="provider.formattedName" sortable="true" title="Restricted By"/>
    <display:column property="comments" sortable="true" title="Comments" />
    <display:column property="startDate.time" sortable="true" title="Start date" format="{0,date,yyyy/MM/dd}" />
    <display:column property="endDate.time" sortable="true" title="End date" format="{0,date,yyyy/MM/dd}"/>
</display:table>
</div></td></tr>
</table>

