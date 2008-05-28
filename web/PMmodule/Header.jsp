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
<table  width="100%" border="1" bordercolor="red">
	<tr>
		<td align="left" width="200px" rowspan="2">
		<%if (oscar.OscarProperties.getInstance().isTorontoRFQ() && !oscar.OscarProperties.getInstance().getBooleanProperty("USE_CAISI_LOGO", "true")){%>
 	        <img src="<html:rewrite page="/images/QuatroShelterLogo.gif"  />" alt="QuatroShelter" border="0"/>
 	    <%} else {%>
	        <img src="<html:rewrite page="/images/caisi_1.jpg" />" alt="Caisi" id="caisilogo"  border="0"/>
	    <%}%>
        </td>
		<th rowspan="2"> &nbsp; </th>
		<td>Shelter:<b> <c:out value="${sessionScope.currentFacility.name}"></c:out></b></td>
		<td rowspan="2">&nbsp;</td>
        <td width="320px">
        <table width="100%">
        <tr>
        <td class="clsMenuCell" nowrap="nowrap" >
					<c:choose>
						<c:when test="${'C' eq sessionScope.mnuHome}">
						<div><b>Home</b></div>
						</c:when>
						<c:otherwise>
						<div><html:link action="/Home.do" styleClass="clsMenu"
							>Home</html:link>
						</div>
						</c:otherwise>
					</c:choose>
		</td>
        <td  class="clsMenuCell" nowrap="nowrap" >
					<c:choose>
						<c:when test="${'C' eq sessionScope.mnuTask}">
						<div><b>My Tasks</b></div>
						</c:when>
						<c:otherwise>
						<div><html:link action="/Tickler.do"
							 styleClass="clsMenu">My Tasks</html:link>
						</div>
						</c:otherwise>
					</c:choose>
		</td>
        <td  class="clsMenuCell" nowrap="nowrap" >
					<c:choose>
						<c:when test="${'C' eq sessionScope.mnuReport}">
						<div><b>Reports</b></div>
						</c:when>
						<c:otherwise>
						<div><html:link action="QuatroReport/ReportList.do"
							 styleClass="clsMenu">Reports</html:link>
						</div>
						</c:otherwise>
					</c:choose>
		</td>
        <td  class="clsMenuCell" nowrap="nowrap" >
               <a target="_blank" href='<%=request.getContextPath()%>/help/QuatroShelter.htm' class="clsMenu">Help</a>
        </td>
        <td  class="clsMenuCell" nowrap="nowrap" >
               <a href='<%=request.getContextPath()%>/logout.jsp'  class="clsMenu">Logout</a>
        </td>
        </tr></table>
        </td>
	</tr>
	<tr><td>User: <c:out
			value="${sessionScope.provider.formattedName}" />
		</td>
		<td>
			<table width="100%">
				<tr>
					<c:choose>
						<c:when test="${'C' eq sessionScope.mnuClient}">
						<td  class="clsMenuCell" nowrap="nowrap" >
						<div><b>Client</b></div></td>
						</c:when>
						<c:when test="${'V' eq sessionScope.mnuClient}">
						<td  class="clsMenuCell" nowrap="nowrap" ><div><html:link action="/PMmodule/ClientSearch2.do?client=true"
							 styleClass="clsMenu">Client</html:link>
						</div></td>
						</c:when>
					</c:choose>
					<c:choose>
						<c:when test="${'C' eq sessionScope.mnuProg}">
						<td  class="clsMenuCell" nowrap="nowrap" ><div><b>Program</b></div></td>
						</c:when>
						<c:when test="${'V' eq sessionScope.mnuProg}">
						<td  class="clsMenuCell" nowrap="nowrap" ><div><html:link action="/PMmodule/ProgramManager.do"
							 styleClass="clsMenu">Program</html:link>
						</div></td>
						</c:when>
					</c:choose>
					<c:choose>
						<c:when test="${'C' eq sessionScope.mnuFacility}">
						<td class="clsMenuCell" nowrap="nowrap" ><div><b>Facility</b></div></td>
						</c:when>
						<c:when test="${'V' eq sessionScope.mnuFacility}">
						<td  class="clsMenuCell" nowrap="nowrap" ><div><html:link action="/PMmodule/FacilityManager.do?method=list"
							 styleClass="clsMenu">Facility</html:link>
						</div></td>
						</c:when>
					</c:choose>
					<c:choose>
						<c:when test="${'C' eq sessionScope.mnuAdmin}">
						<td  class="clsMenuCell" nowrap="nowrap" ><div><b>Administration</b></div></td>
						</c:when>
						<c:when test="${'V' eq sessionScope.mnuAdmin}">
						<td  class="clsMenuCell" nowrap="nowrap" ><div><html:link action="/PMmodule/Admin/SysAdmin.do"
							 styleClass="clsMenu">Administration</html:link>
						</div></td>
						</c:when>
					</c:choose>
				</tr>
			</table>
		</td>
	</tr>
</table><!--</div>-->
