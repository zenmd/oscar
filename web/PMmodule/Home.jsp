	<%String _appPath=request.getContextPath();%>
    <table style="width: 100%; height: 100%">
        <tr>
            <th class="clsPageTitle" align="center">
                Home</th>
        </tr>
        <tr>
            <td height="100%">
                <div style="color: Black; background-color: White; border-style: ridge; border-width: 1px;
                    width: 100%; height: 100%; overflow: auto; background-image: url(/<%=_appPath%>/images/ICM-Icon32Marcos.gif); background-repeat:repeat-x; background-position:bottom; ">
                    <table width="100%">
                        <tr>
                            <td valign="top" align="center">
                                <table align="center" border="0" width="100%" cellpadding="5" cellspacing="5">
                                    <tbody>
                                        <tr>
                                            <td width="100%" valign="top">
                                                <table width="100%" cellpadding="0" cellspacing="0">
                                                    <tr >
                                                        <td width="10%">&nbsp;&nbsp;<img border="0" width="60px" height="60px" src="/<%=_appPath %>/images/Home.gif" alt=""/></td>
                                                        <td width="80%" align="left" class="clsHomePageHeader" colspan="5">Welcome to Quatro Shelter</td>
                                                        <td width="10%">&nbsp;</td>
                                                    </tr>
                                                    <tr>
                                                        <td></td>
                                                        <td colspan="5"><hr /></td>
                                                        <td></td>
                                                    </tr>
                                                    
                                                </table>
                                                <table width="100%">
                                                    <tr >
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
                                                        <th valign="middle">
                                                            <img ID="lnkClient" src="<%=_appPath%>/images/Client-60.gif" runat="server"
                                                                Height="60" Width="60" OnClick="lnkInv1_Click" /></th>
                                                        <th align="left" valign="middle">
                                                            <table>
                                                                <tr align="left">
                                                                    <th align="left" valign="top"  class="clsHomePageLabels">
                                                                        <a ID="lnkInv2" runat="server" OnClick="lnkInv2_Click">Client Management</a></th>
                                                                </tr>
                                                            </table>
                                                        </th>
                                                        <th>
                                                        </th>
                                                        <th valign="middle">
                                                            <img ID="lnkCare1" runat="server" src="~/<%=_appPath%>/images/Program-60.gif"
                                                                Height="60" Width="60" OnClick="lnkCare1_Click"></img>
                                                        </th>
                                                        <th valign="middle">
                                                            <table align="left">
                                                                <tr>
                                                                    <th align="left" valign="top" class="clsHomePageLabels">
                                                                        <a ID="lnkCare2" runat="server" OnClick="lnkCare2_Click"> Program Management"</a>
                                                                    </th>
                                                                </tr>
                                                                <!--
                                                                <tr>
                                                                    <th align="left" class="clsTableContextTransparent" valign="top">
                                                                        <asp:Label ID="lblCare" runat="server"></asp:Label>
                                                                    </th>
                                                                </tr>
                                                                // -->
                                                            </table>
                                                        </th>
                                                        <th>
                                                        </th>
                                                    </tr>
                                                    <!--
                                                    <tr>
                                                        <td colspan="6" style="height:1px"> &nbsp;
                                                        </td>
                                                    </tr>
                                                    // -->
                                                    <tr>
                                                        <th></th>
                                                        <th valign="middle">
                                                            <img ID="lnkIssue1" runat="server" src="<%=_appPath%>/images/Case-60.gif"
                                                                Height="60" Width="60" OnClick="lnkIssue1_Click"></img>
                                                        </th>
                                                        <th align="left">
                                                            <table>
                                                                <tr align="left">
                                                                    <th align="left" valign="middle" class="clsHomePageLabels">
                                                                        <a ID="lnkIssue2" OnClick="lnkIssue2_Click"> Case Management</a>
                                                                    </th>
                                                                </tr>
                                                                <!--
                                                                <tr align="left">
                                                                    <th align="left" class="clsTableContextTransparent" valign="top">
                                                                        Document Issues and implement Corrective Action Plans. 
                                                                    </th>
                                                                </tr>
                                                                // -->
                                                            </table>
                                                        </th>
                                                        <th>
                                                        </th>
                                                        <th valign="middle">
                                                            <img ID="lnkLTD1" runat="server" src="~/<%=_appPath%>/images/Shelter-60.gif"
                                                                Height="60" Width="60" OnClick="lnkLTD1_Click"></img>
                                                        </th>
                                                        <th valign="middle">
                                                            <table align="left">
                                                                <tr>
                                                                    <th align="left" valign="top" class="clsHomePageLabels">
                                                                        <a ID="lnkLTD2" OnClick="lnkLTD2_Click">Shelter Management</a>
                                                                    </th>
                                                                </tr>
                                                            </table>
                                                        </th>
                                                        <th>
                                                        </th>
                                                    </tr>
                                                    <tr>
                                                        <th></th>
                                                        <th valign="middle">
                                                            <a href="Report/ReportSelector.aspx" id="lnkTickler">
                                                                <img src="<%=_appPath%>/images/Reports-60.gif" height="60" width="60" border="0" alt="" /></a></th>
                                                        <th>
                                                            <table width="100%" align="left">
                                                                <tr>
                                                                    <th align="left" valign="middle" class="clsHomePageLabels">
                                                                        <a id="lnkReport1" href="Report/ReportSelector.aspx">Reports</a></th>
                                                                </tr>
                                                            </table>
                                                        </th>
                                                        <th>
                                                        </th>
                                                        <th valign="middle">
                                                            <img ID="lnkResource1" runat="server" src="<%=_appPath%>/images/Admin-60.gif"
                                                                Height="60" Width="60" OnClick="lnkResource1_Click"></img>
                                                                </th>
                                                        <th align="left" valign="middle">
                                                            <table>
                                                                <tr align="left">
                                                                
                                                        <th valign="top"  align="left" class="clsHomePageLabels"> 
                                                        <a ID="lnkResource2" runat="server" OnClick="lnkResource2_Click" Text="System Administration"></a>
                                                            &nbsp;</th>
                                                            </tr>
                                                            </table>
                                                          </th>  
                                                        <th>   &nbsp;            </th>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </td>
                        </tr>
          
                    </table>
                </div>
            </td>
        </tr>
    </table>
