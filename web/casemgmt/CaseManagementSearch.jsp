<%@ include file="/casemgmt/taglibs.jsp"%>

<%@ page import="org.oscarehr.casemgmt.model.*"%>
<%@ page import="org.oscarehr.casemgmt.web.formbeans.*"%>
<%@ page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page
	import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page import="org.caisi.service.Version"%>
<%@ page import="oscar.OscarProperties"%>

<%@ taglib uri="/WEB-INF/caisi-tag.tld" prefix="caisi"%>
<%@ taglib uri="/WEB-INF/security.tld" prefix="security"%>

<%
response.setHeader("Cache-Control", "no-cache");
%>
<script type="text/javascript">
<!--
	function submitForm(methodValue)
	{
		document.forms[0].method.value=methodValue;
		document.forms[0].submit();
	}
//-->
</script>
<html:form action="/CaseManagementView2" method="get">
	<html:hidden property="demographicNo" />
	<html:hidden property="providerNo" />
	<!--  no need for tabs 
	 
	<html:hidden property="tab" />
	<html:hidden property="hideActiveIssue" />
	-->
	<input type="hidden" name="method" value="view" />

	<script>
	function resetClientFields() {
		var form = document.caseManagementViewForm;
		form.elements['searchProviderNo'].value='';
		form.elements['searchServiceComponent'].value='';
		form.elements['searchCaseStatus'].value='';
		form.elements['searchStartDate'].value='';
		form.elements['searchEndDate'].value='';
		form.elements['searchProviderNo'].selectedIndex = 0;
		form.elements['searchCaseStatus'].selectedIndex = 0;
		}
	function clickTab(name) {
		document.caseManagementViewForm.tab.value=name;
		document.caseManagementViewForm.submit();
	}
	function popupNotePage(varpage) {
        var page = "" + varpage;
        windowprops = "height=800,width=800,location=no,"
          + "scrollbars=yes,menubars=no,toolbars=no,resizable=yes,screenX=50,screenY=50,top=0,left=0";
       var popup = window.open(page, "editNote", windowprops);
       if (popup != null) {
    		if (popup.opener == null) {
      		popup.opener = self;
    		}
    		popup.focus();
  		}
    }
    
    function popupHistoryPage(varpage) {
        var page = "" + varpage;
        windowprops = "location=no,"
          + "scrollbars=yes,menubars=no,toolbars=no,resizable=yes,top=0,left=0";
        window.open(page, "", windowprops);
    }
    

function popupUploadPage(varpage,dn) {
        var page = "" + varpage+"?demographicNo="+dn;
        windowprops = "height=500,width=500,location=no,"
          + "scrollbars=no,menubars=no,toolbars=no,resizable=yes,top=50,left=50";
         var popup=window.open(page, "", windowprops);
         popup.focus();
        
    }
    
    
function delay(time){
string="document.getElementById('ci').src='<c:out value="${ctx}"/>/images/default_img.jpg'";
setTimeout(string,time);
}
    
</script>

	<div id="pageTitle">
	<table width="100%">
		<tr>
			<th class="pageTitle" width="100%">Case Management Encounter</th>
		</tr>
		<tr>
			<td align="left" class="buttonBar"><html:link action="/Home.do"
				style="color:Navy;text-decoration:none;">
				<img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Summary&nbsp;&nbsp;|
			</html:link> <html:link action="#" style="color:Navy;text-decoration:none;">&nbsp;Detailed&nbsp;&nbsp;|
			</html:link> <!--
				<c:url
					value="/CaseManagementEntry.do?method=edit&note_edit=new&from=casemgmt&demographicNo=${param.demographicNo}&providerNo=${param.providerNo}"
					var="noteURL" />
				<span style="cursor:pointer;color: blue"
					onclick="popupNotePage('<c:out value="${noteURL}" escapeXml="false"/>')">New&nbsp;Note&nbsp;&nbsp;|</span>
				 --> <html:link
				action="/CaseManagementEntry.do?method=edit&note_edit=new&from=casemgmt&demographicNo=${param.demographicNo}&providerNo=${param.providerNo}"
				style="color:Navy;text-decoration:none;">
				 New&nbsp;Note&nbsp;&nbsp;|</html:link> <html:link action="window.print();"
				style="color:Navy;text-decoration:none;">&nbsp;Print&nbsp;&nbsp;|</html:link>
			</td>
		</tr>
	</table>
	<div id="clientInfo">
	<table width="100%">
		<tr>
			<td width="65%">
			<table cellspacing="1" cellpadding="1">
				<tr>
					<td align="right" valign="top" nowrap>Client Name:</td>
					<td><c:out value="${requestScope.casemgmt_demoName}" /></td>
				</tr>
				<tr>
					<td align="right" valign="top" nowrap>Age:</td>
					<td><c:out value="${requestScope.casemgmt_demoAge}" /></td>
				</tr>
				<tr>
					<td align="right" valign="top" nowrap>DOB:</td>
					<td><c:out value="${requestScope.casemgmt_demoDOB}" /></td>
				</tr>

				<%
				if (!OscarProperties.getInstance().isTorontoRFQ()) {
				%>

				<tr>
					<td align="right" valign="top" nowrap>Primary Health Care
					Provider:</td>
					<td><c:out value="${requestScope.cpp.primaryPhysician}" /></td>
				</tr>
				<%
				}
				%>
				<tr>
					<td align="right" valign="top" nowrap>Primary
					Counsellor/Caseworker:</td>
					<td><c:out value="${requestScope.cpp.primaryCounsellor}" /></td>
				</tr>
			</table>
			</td>
			<td>
			<%
			String demo = request.getParameter("demographicNo");
			%> <c:choose>
				<c:when test="${not empty requestScope.image_filename}">
					<img style="cursor: pointer;" id="ci"
						src="<c:out value="${ctx}"/>/images/default_img.jpg"
						alt="id_photo" height="100" title="Click to upload new photo."
						OnMouseOver="document.getElementById('ci').src='<c:out value="${ctx}"/>/images/<c:out value="${requestScope.image_filename}"/>'"
						OnMouseOut="delay(5000)" window.status='Click to upload new photo'
						; return
						true;" onClick="popupUploadPage('<html:rewrite page="/casemgmt/uploadimage.jsp"/>',<%=demo%>);return false;" />
				</c:when>
				<c:otherwise>
					<img style="cursor: pointer;"
						src="<c:out value="${ctx}"/>/images/defaultR_img.jpg"
						alt="No_Id_Photo" height="100" title="Click to upload new photo."
						OnMouseOver="window.status='Click to upload new photo';return true"
						onClick="popupUploadPage('uploadimage.jsp',<%=demo%>);return false;" />
				</c:otherwise>
			</c:choose></td>

		</tr>
	</table>
	</div>
	<br />
	<div class="axial">
	<table border="0" cellspacing="2" cellpadding="3" width="100%">
		<tr>
			<th width="20%"><bean-el:message key="CaseSearch.provider"
				bundle="pmm" /></th>
			<td width="30%"><html:select property="providerNo">
				<html:option value="">
				</html:option>
				<html:options collection="providers" property="providerNo"
					labelProperty="fullName" />
			</html:select></td>
			<th><bean-el:message key="CaseSearch.caseStatus" bundle="pmm" />
			</th>
			<td><html:select property="searchCaseStatus">
				<html:option value="">
				</html:option>
				<html:options collection="caseStatusList" property="code"
					labelProperty="description" />
			</html:select></td>
		</tr>
		<tr>
			<th colspan="2"><bean-el:message
				key="CaseSearch.componentsOfService" bundle="pmm" /></th>
			<td colspan="2"><html:select property="searchServiceComponent">
				<html:option value="">
				</html:option>
				<html:options collection="issues" property="id"
					labelProperty="description" />
			</html:select></td>
		</tr>
		<tr>

		</tr>
		<tr>
			<th><bean-el:message key="CaseSearch.dateRangeFrom" bundle="pmm" />
			</th>

			<td><quatro:datePickerTag property="searchStartDate"
				openerForm="caseManagementViewForm" width="120px"></quatro:datePickerTag></td>
			<th><bean-el:message key="CaseSearch.dateRangeTo" bundle="pmm" /></th>
			<td><quatro:datePickerTag property="searchEndDate" width="120px"
				openerForm="caseManagementViewForm"></quatro:datePickerTag></td>
		</tr>
		<tr>
			<td align="left" colspan="4"><input type="button" name="search"
				value="search" onclick="submitForm('search')" />&nbsp; <input
				type="button" name="reset" value="reset"
				onclick="resetClientFields()" /></td>
		</tr>
	</table>
	</div>


	<br />
	<c:if test="${not empty search_results}">
		<table border="0" width="100%">
			<tr>
				<td align="left"><span
					style="text-decoration: underline;cursor:pointer;color: blue"
					onclick="document.caseManagementViewForm.note_view.value='summary';document.caseManagementViewForm.method.value='setViewType';document.caseManagementViewForm.submit(); return false;">Summary</span>
				&nbsp;|&nbsp; <span
					style="text-decoration: underline;cursor:pointer;color: blue"
					onclick="document.caseManagementViewForm.note_view.value='detailed';document.caseManagementViewForm.method.value='setViewType';document.caseManagementViewForm.submit();return false;">Detailed</span>
				<c:if test="${sessionScope.readonly=='false'}">
					<c:url
						value="/CaseManagementEntry.do?method=edit&note_edit=new&from=casemgmt&demographicNo=${param.demographicNo}&providerNo=${param.providerNo}"
						var="noteURL" />
	&nbsp;|&nbsp;
	<span style="text-decoration: underline;cursor:pointer;color: blue"
						onclick="popupNotePage('<c:out value="${noteURL}" escapeXml="false"/>')">New
					Note</span>
				</c:if> &nbsp;|&nbsp; <span
					style="text-decoration: underline;cursor:pointer;color: blue"
					onclick="window.print();">Print</span> <c:if test="${can_restore}">
					<c:url
						value="/CaseManagementEntry.do?method=restore&from=casemgmt&demographicNo=${param.demographicNo}&providerNo=${param.providerNo}"
						var="noteURL" />
	&nbsp;|&nbsp;
	<span style="text-decoration: underline;cursor:pointer;color: blue"
						onclick="popupNotePage('<c:out value="${noteURL}" escapeXml="false"/>')">Restore
					Lost Note</span>
				</c:if></td>
				<td align="right">Provider: <html:select
					property="filter_provider"
					onchange="document.caseManagementViewForm.method.value='view';document.caseManagementViewForm.submit();">
					<html:option value="">&nbsp;</html:option>
					<html:options collection="providers" property="provider_no"
						labelProperty="formattedName" />
				</html:select> &nbsp; &nbsp; &nbsp; Sort: <html:select property="note_sort"
					onchange="document.caseManagementViewForm.method.value='view';document.caseManagementViewForm.submit()">
					<html:option value="update_date">Date</html:option>
					<html:option value="providerName">Provider</html:option>
					<html:option value="programName">Program</html:option>
					<html:option value="roleName">Role</html:option>
				</html:select></td>
			</tr>
		</table>
		<c:if
			test="${sessionScope.caseManagementViewForm.note_view!='detailed'}">
			<table id="test" width="100%" border="0" cellpadding="0"
				cellspacing="1" bgcolor="#C0C0C0">
				<tr class="title">
					<td></td>
					<td>Date</td>
					<td>Provider</td>
					<td>Status</td>
					<td>Program</td>
					<td>Role</td>
				</tr>

				<%
					int index = 0;
					String bgcolor = "white";
				%>
				<c:forEach var="note" items="${search_results}">
					<%
							if (index++ % 2 != 0) {
							bgcolor = "white";
						} else {
							bgcolor = "#EEEEFF";
						}
					%>
					<tr bgcolor="<%=bgcolor %>" align="center">
						<td><c:choose>
							<c:when
								test="${(!note.signed) and (sessionScope.readonly=='false')}">
								<c:url
									value="/CaseManagementEntry.do?method=edit&from=casemgmt&noteId=${note.id}&demographicNo=${param.demographicNo}&providerNo=${param.providerNo}&forceNote=true"
									var="notesURL" />
								<img src="<c:out value="${ctx}"/>/images/edit_white.png"
									title="Edit/Sign Note" style="cursor:pointer"
									onclick="popupNotePage('<c:out value="${notesURL}" escapeXml="false"/>')" />
							</c:when>
							<c:when
								test="${note.signed and note.provider_no eq param.providerNo and (note.locked !=true)}">
								<c:url
									value="/CaseManagementEntry.do?method=edit&from=casemgmt&noteId=${note.id}&demographicNo=${param.demographicNo}&providerNo=${param.providerNo}&forceNote=true"
									var="notesURL" />
								<img src="<c:out value="${ctx}"/>/images/edit_white.png"
									title="Edit Note" style="cursor:pointer"
									onclick="popupNotePage('<c:out value="${notesURL}" escapeXml="false"/>')" />
							</c:when>
							<c:otherwise>
								<img src="<c:out value="${ctx}"/>/images/transparent_icon.gif"
									title="" />
							</c:otherwise>
						</c:choose> <c:choose>
							<c:when test="${note.hasHistory == true and note.locked != true}">
								<c:url
									value="/CaseManagementEntry.do?method=history&from=casemgmt&noteId=${note.id}&demographicNo=${param.demographicNo}&providerNo=${param.providerNo}"
									var="historyURL" />
								<img src="<c:out value="${ctx}"/>/images/history.gif"
									title="Note History" style="cursor:pointer"
									onclick="popupHistoryPage('<c:out value="${historyURL}" escapeXml="false"/>')">
							</c:when>
							<c:otherwise>
								<img src="<c:out value="${ctx}"/>/images/transparent_icon.gif"
									title="" />
							</c:otherwise>
						</c:choose> <c:choose>
							<c:when test="${note.locked}">
								<c:url
									value="/CaseManagementView.do?method=unlock&noteId=${note.id}"
									var="lockedURL" />
								<img src="<c:out value="${ctx}"/>/images/ulock.gif"
									title="Unlock" style="cursor:pointer"
									onclick="popupPage('<c:out value="${lockedURL}" escapeXml="false"/>')" />
							</c:when>
							<c:otherwise>
								<img src="<c:out value="${ctx}"/>/images/transparent_icon.gif"
									title="" />
							</c:otherwise>
						</c:choose></td>
						<td><fmt:formatDate pattern="yyyy-MM-dd hh:mm a"
							value="${note.observation_date}" /></td>
						<td><c:out value="${note.providerName}" /></td>
						<td><c:out value="${note.status}" /></td>
						<td><c:out value="${note.programName}" /></td>
						<td><c:out value="${note.roleName}" /></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		<c:if
			test="${sessionScope.caseManagementViewForm.note_view=='detailed'}">
			<table id="test" width="100%" border="0" cellpadding="0"
				cellspacing="1" bgcolor="#C0C0C0">
				<%
					int index1 = 0;
					String bgcolor1 = "white";
				%>
				<c:forEach var="note" items="${search_results}">
					<%
							if (index1++ % 2 != 0) {
							bgcolor1 = "white";
						} else {
							bgcolor1 = "#EEEEFF";
						}
						java.util.List noteList = (java.util.List) request
								.getAttribute("search_results");
						String noteId = ((CaseManagementNote) noteList.get(index1 - 1))
								.getId().toString();
						request.setAttribute("noteId", noteId);
					%>
					<tr>
						<td>
						<table width="100%" border="0">
							<tr bgcolor="<%=bgcolor1 %>">
								<td width="7%">Provider</td>
								<td width="93%"><c:out
									value="${note.provider.formattedName }" /></td>
							</tr>
							<tr bgcolor="<%=bgcolor1 %>">
								<td width="7%">Date</td>
								<td width="93%"><fmt:formatDate
									pattern="yyyy-MM-dd hh:mm a" value="${note.observation_date}" /></td>
							</tr>
							<tr bgcolor="<%=bgcolor1 %>">
								<td width="7%">Status</td>
								<td width="93%"><c:out value="${note.status}" /></td>
							</tr>
							<tr bgcolor="<%=bgcolor1 %>">
								<td width="7%">Action</td>
								<td width="93%"><c:if
									test="${(!note.signed) and (sessionScope.readonly=='false')}">
									<c:url
										value="/CaseManagementEntry.do?method=edit&from=casemgmt&noteId=${requestScope.noteId}&demographicNo=${param.demographicNo}&providerNo=${param.providerNo}"
										var="notesURL" />
									<input type="button" value="Edit and Sign"
										onclick="popupNotePage('<c:out value="${notesURL}" escapeXml="false"/>')">
								</c:if> <c:if
									test="${note.signed and note.provider_no eq param.providerNo}">
									<c:url
										value="/CaseManagementEntry.do?method=edit&from=casemgmt&noteId=${requestScope.noteId}&demographicNo=${param.demographicNo}&providerNo=${param.providerNo}"
										var="notesURL" />
									<input type="button" value="Edit This Note"
										onclick="popupNotePage('<c:out value="${notesURL}" escapeXml="false"/>')">
								</c:if> <c:if test="${note.hasHistory == true}">
									<c:url
										value="/CaseManagementEntry.do?method=history&from=casemgmt&noteId=${requestScope.noteId}&demographicNo=${param.demographicNo}&providerNo=${param.providerNo}"
										var="historyURL" />
									<input type="button" value="Note History"
										onclick="popupHistoryPage('<c:out value="${historyURL}" escapeXml="false"/>')">
								</c:if> <c:if test="${note.locked}">
									<c:url
										value="/CaseManagementView.do?method=unlock&noteId=${requestScope.noteId}"
										var="lockedURL" />
									<input type="button" value="Unlock"
										onclick="popupPage('<c:out value="${lockedURL}" escapeXml="false"/>')">
								</c:if></td>
							</tr>
							<tr bgcolor="<%=bgcolor1 %>">
								<td width="7%">Note</td>
								<td width="93%"><c:choose>
									<c:when test="${note.locked}">
										<span style="color:red"><i>Contents Hidden</i></span>
									</c:when>
									<c:otherwise>
										<pre><c:out value="${note.note }" /></pre>
									</c:otherwise>
								</c:choose></td>
							</tr>
						</table>
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		<span style="text-decoration: underline;cursor:pointer;color: blue"
			onclick="document.caseManagementViewForm.note_view.value='summary';document.caseManagementViewForm.method.value='setViewType';document.caseManagementViewForm.submit(); return false;">Summary</span>
&nbsp;|&nbsp;
<span style="text-decoration: underline;cursor:pointer;color: blue"
			onclick="document.caseManagementViewForm.note_view.value='detailed';document.caseManagementViewForm.method.value='setViewType';document.caseManagementViewForm.submit();return false;">Detailed</span>
		<c:if test="${sessionScope.readonly=='false'}">
			<c:url
				value="/CaseManagementEntry.do?method=edit&note_edit=new&from=casemgmt&demographicNo=${param.demographicNo}&providerNo=${param.providerNo}"
				var="noteURL" />
	&nbsp;|&nbsp;
	<span style="text-decoration: underline;cursor:pointer;color: blue"
				onclick="popupNotePage('<c:out value="${noteURL}" escapeXml="false"/>')">New
			Note</span>
		</c:if>
&nbsp;|&nbsp;
<span style="text-decoration: underline;cursor:pointer;color: blue"
			onclick="window.print();">Print</span>
		<c:if test="${can_restore}">
			<c:url
				value="/CaseManagementEntry.do?method=restore&from=casemgmt&demographicNo=${param.demographicNo}&providerNo=${param.providerNo}"
				var="noteURL" />
	&nbsp;|&nbsp;
	<span style="text-decoration: underline;cursor:pointer;color: blue"
				onclick="popupNotePage('<c:out value="${noteURL}" escapeXml="false"/>')">Restore
			Note</span>
		</c:if>

		<br />
		<br />
	</c:if>
</html:form>

