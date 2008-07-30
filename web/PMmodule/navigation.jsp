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
<%@ include file="/taglibs.jsp" %>
<%@ page import="java.util.*" %>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="org.caisi.service.Version" %>
<%@ page import="com.quatro.common.KeyConstants" %>
<%@ taglib uri="/WEB-INF/security.tld" prefix="security" %>

<%
//	WebApplicationContext ctx=null;
%>

<script type="text/javascript">
    function popupPage2(varpage, windowname) {
        var page = "" + varpage;
        windowprops = "height=700,width=1000,location=no,"
                + "scrollbars=yes,menubars=no,toolbars=no,resizable=yes,top=10,left=0";
        var popup = window.open(page, windowname, windowprops);
        if (popup != null) {
            if (popup.opener == null) {
                popup.opener = self;
            }
            popup.focus();
        }
    }
</script>

<div id="projecttools" class="toolgroup">
<div class="label">
    <strong>Navigator</strong>
</div>
<div class="body">
	<security:oscarSec  objectName="<%=KeyConstants.FUN_CLIENT %>" rights="r">
    	<div>
        <span>Client Management</span>
        <div>
            <html:link action="/PMmodule/ClientSearch2.do?client=true" >Search Client</html:link>
        </div>
        <security:oscarSec objectName="_pmm.mergeRecords" rights="r">
        <div>
            <a HREF="#" ONCLICK="popupPage2('<c:out value="${ctx}"/>/admin/demographicmergerecord.jsp', 'Merge');return false;">Merge
                Records</a>
        </div>
        </security:oscarSec>
	    </div>
    </security:oscarSec>
    <security:oscarSec objectName="<%=KeyConstants.FUN_REPORTS %>" rights="r">
	    <div>
            <span>Reporting Tools</span>
			<div>
				<html:link action="QuatroReport/ReportList.do">Quatro Report Runner</html:link>
			</div>
        </div>
    </security:oscarSec>
    <security:oscarSec objectName="<%=KeyConstants.FUN_CLIENTCASE %>" rights="r">
		<div> 
            <span>Case Management</span>
        <div>
            <span><html:link 	action="/CaseManagementView2.do">Case Management</html:link></span>
        </div>
        </div>
    </security:oscarSec>
    <security:oscarSec objectName="<%=KeyConstants.FUN_CLIENTDOCUMENT %>" rights="r">
		<div> 
            <span>Document Attachment</span>
        <div>
            <span><html:link action="/PMmodule/UploadFile.do">Add/View Documents</html:link></span>
        </div>
        </div>
    </security:oscarSec>
    <security:oscarSec objectName="<%=KeyConstants.FUN_CLIENTTASKS %>" rights="r">
		<div> 
            <span>Tasks</span>
        <div>
            <span><html:link 	action="/Tickler.do">My Tasks</html:link></span>
        </div>
        </div>
    </security:oscarSec>
</div>
    <security:oscarSec objectName="_pmm_management" rights="r">
	<div id="admintools" class="toolgroup">
    <div class="label">
        <strong>Administration</strong>
    </div>
    <div class="body">
        <div>
            <span>Agency</span>
			<security:oscarSec  objectName="_pmm.agencyInformation" rights="r">
            <div>
                <html:link action="/PMmodule/AgencyManager.do">Agency Information</html:link>
            </div>
            </security:oscarSec>
            <security:oscarSec objectName="<%=KeyConstants.FUN_FACILITY %>" rights="r">
            <div>
                <html:link action="/PMmodule/FacilityManager.do?method=list">Manage Facilities</html:link>
            </div>
            </security:oscarSec>
        </div>
		<security:oscarSec objectName="<%=KeyConstants.FUN_PROGRAM %>" rights="r">
        <div>
            <span>Program</span>
            <div>
                <html:link action="/PMmodule/ProgramManager.do">Program List</html:link>
            </div>
        </div>
        </security:oscarSec>
	</security:oscarSec>       
    <security:oscarSec objectName="<%=KeyConstants.FUN_ADMIN %>" rights="r">
        <div>
            <span>System Administration</span>

            <div>
                <html:link action="/PMmodule/Admin/SysAdmin.do">Admin Page</html:link>
            </div>
        </div>
    </security:oscarSec>
    </div>
</div>

