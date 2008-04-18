

<%
String _appPath = request.getContextPath();
%>
<%@ include file="/taglibs.jsp"%>


<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
	<tr>
		<th class="pageTitle" align="center"><span
			id="_ctl0_phBody_lblTitle" align="left">Organization Chart</span></th>
	</tr>
	<tr>
		<td align="left" class="buttonBar"><html:link action="/Home.do"
			style="color:Navy;text-decoration:none;">
			<img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Close&nbsp;&nbsp;|</html:link>
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
			<tr>
				<td width="80%" align="left" class="clsPageTitle" colspan="5">
				<h2>System Administration</h2>
				</td>
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
					action="/PMmodule/UserManager.do">
					<img ID="lnkClient" src="<%=_appPath%>/images/Client-60.gif"
						Height="60" Width="60" border="0" />
				</html:link></th>
				<th align="left" valign="middle">
				<table>
					<tr align="left">
						<th align="left" valign="top" class="clsHomePageLabels"><html:link
							action="/PMmodule/UserManager.do">User Management</html:link></th>
					</tr>
				</table>
				</th>

				<th></th>

				<th valign="middle"><html:link
					action="/PMmodule/RoleManager.do">
					<img ID="lnkCare1" src="<%=_appPath%>/images/Program-60.gif"
						Height="60" Width="60" border="0" />
				</html:link></th>
				<th valign="middle">
				<table align="left">
					<tr>
						<th align="left" valign="top" class="clsHomePageLabels"><html:link
							action="/PMmodule/RoleManager.do">Role Management</html:link></th>
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
						Organization Management</a></th>
					</tr>
				</table>
				</th>

				<th></th>
			</tr>


		</table>
		</div>
		</td>
	</tr>
</table>
