
<%
String _appPath = request.getContextPath();
%>
<%@ include file="/taglibs.jsp"%>
<table width="100%">
	<tr>
		<th class="pageTitle">Home</th>
	</tr>
	<tr>
		<td>
		<table width="100%">
			<tr>
				<td valign="top" align="center">
				<table align="center" border="0" width="100%" cellpadding="5"
					cellspacing="5">
					<tbody>
						<tr>
							<td width="100%" valign="top">
							<table width="100%" cellpadding="0" cellspacing="0">
								<tr>
									<td width="10%">&nbsp;&nbsp;<img border="0" width="60px"
										height="60px" src="<%=_appPath %>/images/Home.gif" alt="" /></td>
									<td width="80%" align="left" class="clsPageHeader" colspan="5">
									<h1 style="color:#1E90FF">Home</h1>
									</td>
									<td width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td></td>
									<td colspan="5">
									<hr />
									</td>
									<td></td>
								</tr>

							</table>
							<table width="100%">
								<tr>
									<td width="10%"></td>
									<td width="10%"></td>
									<td width="27%"></td>
									<td width="5%"></td>
									<td width="10%"></td>
									<td width="28%"></td>
									<td width="10%"></td>
								</tr>

								<tr>
									<th></th>
									<th valign="middle"><html:link action="/PMmodule/ClientSearch2.do">
										<img id="lnkClient" src="<%=_appPath%>/images/Client-60.gif" height="60" width="60" border="0">
									</html:link></th>
									<th align="left" valign="middle">
									<table>
										<tr align="left">
											<th align="left" valign="top" class="clsHomePageLabels">
											<html:link action="/PMmodule/ClientSearch2.do">Client Management</html:link></th>
										</tr>
									</table>
									</th>
									<th></th>
									<th valign="middle"><html:link action="/PMmodule/ProgramManager.do">
										<img id="lnkCare1" src="<%=_appPath%>/images/Program-60.gif" height="60" width="60" border="0">
									</html:link></th>
									<th valign="middle">
									<table align="left">
										<tr>
											<th align="left" valign="top" class="clsHomePageLabels">
											<html:link action="/PMmodule/ProgramManager.do">Program Management</html:link>
											</th>
										</tr>
									</table>
									</th>
									<th></th>
								</tr>
								<tr>
									<th></th><th valign="middle"><html:link action="QuatroReport/ReportList.do">
										<img src="<%=_appPath%>/images/Reports-60.gif" height="60" width="60" border="0" alt="">
									</html:link></th><th>
									<table width="100%" align="left">
										<tr>
											<th align="left" valign="middle" class="clsHomePageLabels">
											<html:link action="QuatroReport/ReportList.do">Reports</html:link></th>
										</tr>
									</table>
									</th>
									
									
									<th></th>
									<th valign="middle"><html:link action="/PMmodule/FacilityManager.do?method=list">
										<img id="lnkLTD1" src="<%=_appPath%>/images/Shelter-60.gif" height="60" width="60" border="0">
									</html:link></th>
									<th valign="middle">
									<table align="left">
										<tr>
											<th align="left" valign="top" class="clsHomePageLabels">
											<html:link action="/PMmodule/FacilityManager.do?method=list">Facility Management</html:link>
											</th>
										</tr>
									</table>
									</th>
									<th></th>
								</tr>
								<tr>
									<th></th><th valign="middle"> 
									<img src="<%=_appPath%>/images/mytasks60.gif" height="60" width="60" border="0"></th><th>
									<table>
										<tr align="left">
											<th valign="top" align="left" class="clsHomePageLabels">
											<html:link action="/Tickler.do">My Tasks</html:link></th>
										</tr>
									</table>
									</th>
									
									
									<th></th>
									<th valign="middle"><a href="<%=_appPath%>/PMmodule/Admin/SysAdmin.do"><img src="<%=_appPath%>/images/Admin-60.gif" height="60" width="60" border="0" onclick="lnkResource1_Click"></a></th>
									<th align="left" valign="middle">
									<table>
										<tr align="left">

											<th valign="top" align="left" class="clsHomePageLabels">
											<a href="<%=_appPath%>/PMmodule/Admin/SysAdmin.do">System
											Administration</a> &nbsp;</th>
										</tr>
									</table>
									</th>
									<th>&nbsp;</th>
								</tr>


								<tr>
									<th></th>
									<th valign="middle"> 
									</th>
									<th>
									
									</th>
									<th></th>
									<th valign="middle"></th>
									<th align="left" valign="middle"></th>
									<th>&nbsp;</th>
								</tr>
								<!-- Jim added end -->

							</table>
							</td>
						</tr>
					</tbody>
				</table>
				</td>
			</tr>

		</table>
		</td>
	</tr>
	<tr height="100%"> <td> &nbsp;</td></tr>
	<tr>
		<td valign="bottom">
			<tiles:insert name="Messages.jsp" />
		</td>
	</tr>
</table>
