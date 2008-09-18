<!-- 

Source: web/PMmodule/Admin/ProgramView/incidentEdit.jsp 

-->

<%@ include file="/taglibs.jsp"%>
<%@page import="com.quatro.common.KeyConstants;"%>
<script type="text/javascript">
	function checkExistClients(){	
	   var varStr="programManagerViewForm," + "incidentForm.lstClient" ;
	   var url='<c:out value="${ctx}" />/PMmodule/DuplicateClientCheck.do?'+
		"pageFrom=inc";
	   win = window.open(url,"_blank","toolbar=yes,menubar= yes,resizable=yes,scrollbars=yes,status=yes,width=750,height=600");
	   win.focus();
	}	
</script>
<input type="hidden" id="scrollPosition" name="scrollPosition" value='<c:out value="${scrPos}"/>' />
<input type="hidden" name="pageChanged" id="pageChanged" value='<c:out value="${pageChanged}" />' />

<table width="100%" cellpadding="0px" cellspacing="0px" height="100%"
	border="0">
	<tr>
		<td align="left" class="buttonBar2">
		<a href="javascript:clickTab('Incidents');" style="color:Navy;text-decoration:none;">&nbsp;
			<img style="vertical-align: middle" border="0" src="<html:rewrite page="/images/Back16.png"/>" />&nbsp;Back to Incident List&nbsp;&nbsp;|
		</a>
		<c:if test="${programActive && !isReadOnly}">
		<security:oscarSec objectName="<%=KeyConstants.FUN_PROGRAM_INCIDENT%>" rights="<%=KeyConstants.ACCESS_UPDATE%>">
			<html:link href="javascript:void1();" onclick="javascript:setNoConfirm();return deferedSubmit('save');"	style="color:Navy;text-decoration:none;">
				<img style="vertical-align: middle" border=0 src=<html:rewrite page="/images/Save16.png"/> />&nbsp;Save&nbsp;&nbsp;</html:link>
		</security:oscarSec>
		</c:if>
		</td>
	</tr>
	<tr>
		<td align="left" class="message">
			<logic:messagesPresent message="true">		
				<html:messages id="message" message="true" bundle="pmm">
					<c:out escapeXml="false" value="${message}" />
				</html:messages>
			</logic:messagesPresent>
		</td>
	</tr>
	<tr>
		<td>
			<div class="tabs" id="tabs">
				<br />
				<table cellpadding="3" cellspacing="0" border="0">
					<tr>
						<th title="Incidents">Incident Information</th>
					</tr>
				</table>
			</div>
		</td>
	</tr>
	<tr height="100%">
		<td>
		<div id="scrollBar"  onscroll="getDivPosition()" style="color: Black; background-color: White; border-width: 1px; border-style: Ridge;
                    height: 100%; width: 100%; overflow: auto;">
			<table width="100%" class="simple" cellspacing="2" cellpadding="3">
				<tr>
					<td colspan="2" ><b>Incident Record Date/Time</b> 
						<html-el:text property="incidentForm.createdDateStr" readonly="true" style="border: none"/></td>
					<td colspan="2"><b>Incident Record By</b> 
						<html-el:hidden property="incidentForm.incident.id" styleId="incidentId"/>
						<html-el:hidden property="incidentForm.incident.providerNo" />
						<html-el:text property="incidentForm.providerName" readonly="true" style="border: none" /></td>
				</tr>
				<tr>
					<td colspan="2">
						<table cellpadding="0" cellspacing="0" border="0">
							<tr>
								<td nowrap="nowrap"><b>Date of Incident</b> </td>
								<td><quatro:datePickerTag property="incidentForm.incidentDateStr" width="150px" openerForm="programManagerViewForm" /></td>
								<td width="100%"></td>
							</tr>
						</table>
					</td>
					<td colspan="2"><b>Time of Incident</b> 
						<html-el:text property="incidentForm.hour" style="width:40px" size="2" maxlength="2" onchange="javascript:checkHour(this);"/><b>:</b>
						<html-el:text property="incidentForm.minute" style="width:40px" size="2" maxlength="2" onchange="javascript:checkMinute(this);" />&nbsp;&nbsp;
						<html-el:radio property="incidentForm.ampm" value="AM">AM</html-el:radio>
						<html-el:radio property="incidentForm.ampm" value="PM">PM</html-el:radio></td>
				</tr>
				<tr>
					<td colspan="2"><b>Clients Involved</b><br />
						<bean:define id="lstClientSelection" name="programManagerViewForm" property="incidentForm.clientSelectionList"/>
						<table width="100%" >
					        <tr>
					           <td><html:select property="incidentForm.lstClient" multiple="true"  style="width:90%;height:65px;">
					              <html:options collection="lstClientSelection" property="key" labelProperty="value"></html:options>
					           </html:select>
					           <html:hidden property="incidentForm.txtClientKeys" value="" />
					           <html:hidden property="incidentForm.txtClientValues" value="" /></td>
					        </tr>
					        <tr>
								<td class="clsButtonBarText">
									&nbsp;&nbsp;
									<c:if test="${!isReadOnly}">
										<!--  a id="clientAdd" href="javascript:showLookup('CLN', '', '', 'programManagerViewForm','incidentForm.lstClient','', true, '<c:out value="${ctx}"/>');" onclick="setNoConfirm();" -->
										<a id="clientAdd" href="javascript:checkExistClients();" onclick="setNoConfirm();">
											Add</a>
										&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;			
										<a href="javascript:removeSel('incidentForm.lstClient')"  onclick="setNoConfirm();">Remove</a>
									</c:if>	
								</td>
					        </tr>
					    </table>
						
					</td>
					<td colspan="2"><b>Staff Involved</b><br />
						<bean:define id="lstStaffSelection" name="programManagerViewForm" property="incidentForm.staffSelectionList"/>
						<table width="100%" >
					        <tr>
					           <td><html:select property="incidentForm.lstStaff" multiple="true"  style="width:90%;height:65px;">
					              <html:options collection="lstStaffSelection" property="key" labelProperty="value"></html:options>
					           </html:select>
					           <html:hidden property="incidentForm.txtStaffKeys" value="" />
					           <html:hidden property="incidentForm.txtStaffValues" value="" /></td>
					        </tr>
					        <tr>
								<td class="clsButtonBarText">
									&nbsp;&nbsp;
									<c:if test="${!isReadOnly}">
										<a id="staffAdd" href="javascript:void1();" onclick="setNoConfirm();return showLookup('USR', '', 'programId', 'programManagerViewForm','incidentForm.lstStaff','', true, '<c:out value="${ctx}"/>')">Add</a>
										&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;
										<a href="javascript:removeSel('incidentForm.lstStaff')"  onclick="setNoConfirm();">Remove</a>
									</c:if>	
								</td>
					        </tr>
					      </table>
					</td>
				</tr>	
				<tr>
					<td colspan="4"><b>Witnesses</b><br />
					<html-el:textarea property="incidentForm.incident.witnesses" cols="40" rows="2" onkeyup="javascript:txtAreaLenChecker(this, 1000);" />
					</td>		
				</tr>
				 
				<tr>
					<td colspan="2"><b>Others Involved</b><br />
						<table width="100%">
							<c:forEach var="codeVal" items="${programManagerViewForm.incidentForm.othersLst}" varStatus="index">
								<tr>
									<td>
										&nbsp;&nbsp;&nbsp;<html-el:multibox property="incidentForm.othersArr" value="${codeVal.code}" /><c:out value="${codeVal.description}" />
									</td>
								</tr>
							</c:forEach>
						</table>
					</td>		
					<td colspan="2" rowspan="3"><b>Nature of Incident</b><br />
						<table width="100%">
							<c:forEach var="codeVal" items="${programManagerViewForm.incidentForm.natureLst}" varStatus="index">
								<tr>
									<td>
										&nbsp;&nbsp;&nbsp;<html-el:multibox property="incidentForm.naturesArr" value="${codeVal.code}" /><c:out value="${codeVal.description}" />
									</td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2"><b>Client Issues</b><br />
						<table width="100%">
							<c:forEach var="codeVal" items="${programManagerViewForm.incidentForm.clientIssuesLst}" varStatus="index">
								<tr>
									<td>
										&nbsp;&nbsp;&nbsp;<html-el:multibox property="incidentForm.issuesArr" value="${codeVal.code}" /><c:out value="${codeVal.description}" />
									</td>
								</tr>
							</c:forEach>
						</table>
					</td>		
				</tr>
				<tr>
					<td colspan="2"><b>Disposition</b><br />
						<table width="100%">
							<c:forEach var="codeVal" items="${programManagerViewForm.incidentForm.dispositionLst}" varStatus="index">
								<tr>
									<td>
										&nbsp;&nbsp;&nbsp;<html-el:multibox property="incidentForm.dispositionArr" value="${codeVal.code}" /><c:out value="${codeVal.description}" />
									</td>
								</tr>
							</c:forEach>
						</table>
					</td>		
				</tr>
				<tr>
					<td colspan="4" width="100%"><b>Location of Incident</b> <br /> <html-el:text property="incidentForm.incident.location" size="80" maxlength="200" /></td>		
				</tr>
				<tr>
					<td colspan="4" valign="top"><b>Incident Details:</b> 
					<html-el:textarea property="incidentForm.incident.description" style="width:95%" rows="3" onkeyup="javascript:txtAreaLenChecker(this, 4000);" /></td>		
				</tr>	
				<tr>
					<td colspan="2"><b>Restriction Information</b><br /> 
						<html-el:textarea property="incidentForm.incident.restriction" rows="2" cols="40" onkeyup="javascript:txtAreaLenChecker(this, 1000);"/>
					</td>		
					<td colspan="2"><b>Charges Laid</b> 
						<html:select property="incidentForm.incident.chargesLaid">
							<html:option value="-1" >--</html:option>
			           		<html:option value="1">Yes</html:option>
			           		<html:option value="0">No</html:option>
			         	</html:select>
					</td>
				</tr>
				<tr>
					<td colspan="2"><b>Police Report#</b> <html-el:text property="incidentForm.incident.policeReportNo" maxlength="20"/></td>		
					<td colspan="2"><b>Badge Number</b> <html-el:text property="incidentForm.incident.badgeNo" maxlength="20" /></td>
				</tr>
				<tr>
					<td colspan="2"><b>Investigation Recommendation</b> <br /> 
					<html-el:textarea property="incidentForm.incident.investigationRcmd" rows="2" cols="40" onkeyup="javascript:txtAreaLenChecker(this, 4000);"/></td>
					<td colspan="2"><b>Follow up Information</b> <br /> 
					<html-el:textarea property="incidentForm.incident.followupInfo" rows="2" cols="40" onkeyup="javascript:txtAreaLenChecker(this, 1000);"/>
					</td>
				</tr>
				<tr>
					<td colspan="2"><b>Investigation Conducted By</b> <html-el:text property="incidentForm.incident.investigationConductedby" maxlength="200"/></td>	
					<td colspan="2"><b>Follow up Completed By</b> <html-el:text property="incidentForm.incident.followupCompletedby" maxlength="200"/></td>	
				</tr>
				<tr>
					<td colspan="2">
						<table cellpadding="0" cellspacing="0" border="0">
							<tr>
								<td nowrap="nowrap"><b>Investigation Date</b> </td>
								<td><quatro:datePickerTag property="incidentForm.investigationDateStr" width="150px"
											openerForm="programManagerViewForm" /></td>
								<td width="100%"></td>
							</tr>
						</table>
					</td>
					<td colspan="2">
						<table cellpadding="0" cellspacing="0" border="0">
							<tr>
								<td nowrap="nowrap"><b>Follow up Date</b> </td>
								<td><quatro:datePickerTag property="incidentForm.followupDateStr" width="150px"
											openerForm="programManagerViewForm" /></td>
								<td width="100%"></td>
							</tr>
						</table>
					</td>
					
				</tr>
				<tr>
					<td colspan="4"><b>Report Complete/Signed</b> <html-el:checkbox name="programManagerViewForm" property="incidentForm.incident.reportCompleted" value="1"/>
					</td>		
				</tr>
			</table>	
 		</div>
		</td>
	</tr>
</table>
<%@ include file="/common/readonly.jsp" %>