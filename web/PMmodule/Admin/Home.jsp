<!-- /PMmodule/Admin/Home.jsp -->

<%
String _appPath = request.getContextPath();
%>
<%@ include file="/taglibs.jsp"%>


<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
	<tr>
		<th class="pageTitle" align="center"><span
			id="_ctl0_phBody_lblTitle" align="left">System Administration</span></th>
	</tr>
	<tr>
		<td align="left" class="buttonBar"><html:link action="/Home.do"
			style="color:Navy;text-decoration:none;">
			<img border=0 src=<html:rewrite page="/images/home16.png"/> />&nbsp;Home&nbsp;&nbsp;|</html:link>
		</td>
	</tr>
	<tr>
		<td align="left"></td>
	</tr>
	<tr>
		<td height="100%">
		<div
			style="color: Black; background-color: White; border-width: 1px; border-style: Ridge;
                    height: 100%; width: 100%; overflow: auto;">

		<table width="100%" cellpadding="0" cellspacing="0">
		<tr><td colspan="7">&nbsp;</td></tr>
			<tr>
				<td width="10%">&nbsp;&nbsp;<img border="0" width="60px"
										height="60px" src="<%=_appPath %>/images/Admin-60.gif" alt="" /></td>
									<td width="80%" align="left" class="clsPageHeader" colspan="5">
									<h1 style="color:#1E90FF">System Administration</h1>
									</td>
									<td width="10%">&nbsp;</td>
								</tr>
								<tr>
									<td></td>
									<td colspan="5">
									<hr />
				<td width="10%">&nbsp;</td>
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

				<th valign="middle"><html:link
					action="/PMmodule/Admin/UserSearch.do">
					<img ID="lnkClient" src="<%=_appPath%>/images/Client-60.gif"
						Height="60" Width="60" border="0" />
				</html:link></th>
				<th align="left" valign="middle">
				<table>
					<tr align="left">
						<th align="left" valign="top" class="clsHomePageLabels"><html:link
							action="/PMmodule/Admin/UserSearch.do">User Management</html:link></th>
					</tr>
				</table>
				</th>

				<th></th>

				<th valign="middle"><html:link
					action="/PMmodule/Admin/RoleManager.do">
					<img ID="lnkCare1" src="<%=_appPath%>/images/Program-60.gif"
						Height="60" Width="60" border="0" />
				</html:link></th>
				<th valign="middle">
				<table align="left">
					<tr>
						<th align="left" valign="top" class="clsHomePageLabels"><html:link
							action="/PMmodule/Admin/RoleManager.do">Role Management</html:link></th>
					</tr>
				</table>
				</th>
				<th></th>
			</tr>

			<tr>
				<th></th>


				<th valign="middle"><a id="orgAdd"
					href="<c:out value='${ctx}'/>/Lookup/LookupTableList.do"> <img
					ID="lnkCare1" src="<%=_appPath%>/images/Program-60.gif" Height="60"
					Width="60" border="0" /></a></th>
				<th valign="middle">
				<table align="left">
					<tr>


						<th align="left" valign="top" class="clsHomePageLabels"><a
							id="orgAdd"
							href="<c:out value='${ctx}'/>/Lookup/LookupTableList.do">
						Lookup Tables</a></th>
					</tr>
				</table>
				</th>
				<th></th>
				<th valign="middle"><a id="orgAdd"
					href="<c:out value='${ctx}'/>/PMmodule/Admin/ShowORGTree.do?tableId=ORG">
				<img ID="lnkClient" src="<%=_appPath%>/images/Client-60.gif"
					Height="60" Width="60" border="0" /></a></th>
				<th align="left" valign="middle">
				<table>
					<tr align="left">
						<th align="left" valign="top" class="clsHomePageLabels"><a
							id="orgAdd"
							href="<c:out value='${ctx}'/>/PMmodule/Admin/ShowORGTree.do?tableId=ORG">
						SMIS Organization Chart</a></th>
					</tr>
				</table>
				</th>
				<th></th>
			</tr>
			<tr>
				<th></th>
				<th valign="middle">
				<a id="lnkMergeClient"	href="<c:out value='${ctx}'/>/PMmodule/MergeClient.do"> 
					<img ID="lnkMereg1" src="<%=_appPath%>/images/Program-60.gif" Height="60"
					Width="60" border="0" /></a></th>
				<th valign="middle">
				<table align="left">
					<tr>
						<th align="left" valign="top" class="clsHomePageLabels">
							<a	id="lnkMerge2"	href="<c:out value='${ctx}'/>/PMmodule/MergeClient.do">
							Merge Client</a>
						</th>
					</tr>
				</table>
				</th>
				<th></th>
				<th valign="middle">
					<a id="lnkMergeClient"	href="<c:out value='${ctx}'/>/SystemMessage.do"> 
					<img ID="lnkAddMessage1" src="<%=_appPath%>/images/Program-60.gif" Height="60"
					Width="60" border="0" /></a>
				</th>
				<th valign="middle">
				<table align="left">
					<tr>
						<th align="left" valign="top" class="clsHomePageLabels">
							<a	id="lnkAddMessage2"	href="<c:out value='${ctx}'/>/SystemMessage.do">
							System Message</a>
							
						</th>
					</tr>
				</table>
				</th>
			</tr>
			
			
		</table>
		</div>
		</td>
		<tr height="100%"> <td> &nbsp;</td></tr>
	<tr>
		<td valign="bottom">
			<tiles:insert name="../Messages.jsp" />
		</td>
	</tr>
	</tr>
</table>
