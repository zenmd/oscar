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
<%@ page import="org.oscarehr.PMmodule.web.utils.UserRoleUtils" %>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="org.caisi.service.Version" %>
<%@ taglib uri="/WEB-INF/caisi-tag.tld" prefix="caisi" %>
<%@ taglib uri="/WEB-INF/security.tld" prefix="security" %>

<%
    long loadPage = System.currentTimeMillis();
    if (session.getAttribute("userrole") == null) response.sendRedirect("../logout.jsp");
    String roleName$ = (String) session.getAttribute("userrole") + "," + (String) session.getAttribute("user");

    boolean userHasExternalOrErClerkRole = UserRoleUtils.hasRole(request, UserRoleUtils.Roles_external);
    userHasExternalOrErClerkRole = userHasExternalOrErClerkRole || UserRoleUtils.hasRole(request, UserRoleUtils.Roles_er_clerk);
%>

<%
    String yearStr = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    String mthStr = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
    String dayStr = String.valueOf(Calendar.getInstance().get(Calendar.DATE));

    if (mthStr.length() == 1)
        mthStr = "0" + mthStr;

    if (dayStr.length() == 1)
        dayStr = "0" + dayStr;

    String dateStr = yearStr + "-" + mthStr + "-" + dayStr;
    Version version=null;
	WebApplicationContext ctx=null;
%>

<script type="text/javascript">
    function getIntakeReport(type) {
        var oneWeekAgo = new Date();
        oneWeekAgo.setDate(oneWeekAgo.getDate() - 7);

        var startDate = prompt("Please enter a start date in this format (e.g. 2000-01-01)", dojo.date.format(oneWeekAgo, {selector:'dateOnly', datePattern:'yyyy-MM-dd'}));
        var endDate = prompt("Please enter the end date in this format (e.g. 2000-12-01)", dojo.date.format(new Date(), {selector:'dateOnly', datePattern:'yyyy-MM-dd'}));

        if (!dojo.validate.isValidDate(startDate, 'YYYY-MM-DD')) {
            alert("'" + startDate + "' is not a valid start date");
            return false;
        }

        if (!dojo.validate.isValidDate(endDate, 'YYYY-MM-DD')) {
            alert("'" + endDate + "' is not a valid end date");
            return false;
        }

        alert("Generating report from " + startDate + " to " + endDate + "." + " " + "Please note: it is normal for the generation process to take up to a few minutes to complete, be patient.");

        location.href = '<html:rewrite action="/PMmodule/GenericIntake/Report"/>?' + 'method=report' + '&type=' + type + '&startDate=' + startDate + '&endDate=' + endDate;

        return false;
    }

    function createIntakeCReport1()
    {
        var startDate = prompt("Please enter the date in this format (e.g. 2006-01-01)", "<%=dateStr%>");

        while (startDate.length != 10 || startDate.substring(4, 5) != "-" || startDate.substring(7, 8) != "-")
        {
            startDate = prompt("Please enter the date in this format (e.g. 2006-01-01)", "<%=dateStr%>");
        }

        alert('creating report until ' + startDate);

        location.href = '<html:rewrite action="/PMmodule/IntakeCMentalHealthReportAction.do"/>?startDate=' + startDate;
    }

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
&nbsp; &nbsp;
</div>

