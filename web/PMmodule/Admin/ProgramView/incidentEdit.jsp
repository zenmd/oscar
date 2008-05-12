<%@ include file="/taglibs.jsp"%>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<td align="left" class="buttonBar"><html:link
			href="javascript:editIncident('0','new');"
			style="color:Navy;text-decoration:none;">
			<img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;New&nbsp;&nbsp;</html:link>
		<html:link href="javascript:editIncident('0','save');"
			style="color:Navy;text-decoration:none;">
			<img border=0 src=<html:rewrite page="/images/Save16.png"/> />&nbsp;save&nbsp;&nbsp;</html:link>
		</td>
	</tr>
</table>
<br />
<table>
	<tr>
		<td align="left" class="message">
		<logic:messagesPresent
			message="true">
			<html:messages id="message" message="true" bundle="pmm">
				<c:out escapeXml="false" value="${message}" />
			</html:messages>
		</logic:messagesPresent></td>
	</tr>
</table>
<br />
<div class="tabs" id="tabs">
<table cellpadding="3" cellspacing="0" border="0">
	<tr>
		<th title="Incidents">Incidents Information</th>
	</tr>
</table>
</div>

<table width="100%" class="simple" cellspacing="2" cellpadding="3">
	<tr>
		<td colspan="2">Incident Record Date/Time: <html-el:text property="incidentForm.createdDateStr" readonly="true" style="border: none"/></td>
		<td colspan="2">Incident Record By: <html-el:hidden property="incidentForm.incident.providerNo" />
			<html-el:text property="incidentForm.providerName" readonly="true" style="border: none" /></td>
	</tr>
	<tr>
		<td>Date of Incident: </td>
		<td><quatro:datePickerTag property="incidentForm.incidentDateStr" 
								width="150px" openerForm="programManagerViewForm" /></td>
		<td colspan="2">Time of Incident: <html-el:text property="incidentForm.hour" style="width:40px" size="2" onchange="javascript:checkHour(this);"/><b>:</b>
			<html-el:text property="incidentForm.minute" style="width:40px" size="2" onchange="javascript:checkMinute(this);" />&nbsp;&nbsp;
			<html-el:radio property="incidentForm.ampm" value="AM">AM</html-el:radio>
			<html-el:radio property="incidentForm.ampm" value="PM">PM</html-el:radio></td>
	</tr>
	<tr>
		<td colspan="2">Clients Involved:<br />
			<bean:define id="lstClientSelection" name="programManagerViewForm" property="incidentForm.clientSelectionList"/>
			<table width="100%" >
		        <tr>
		           <td><html:select property="incidentForm.lstClient" multiple="true" size="5" style="width:90%;">
		              <html:options collection="lstClientSelection" property="key" labelProperty="value"></html:options>
		           </html:select>
		           <html:hidden property="incidentForm.txtClientKeys" value="" />
		           <html:hidden property="incidentForm.txtClientValues" value="" /></td>
		        </tr>
		        <tr>
					<td class="clsButtonBarText">
						&nbsp;&nbsp;
						<a id="clientAdd" href="javascript:showLookup('CLN', '', '', 'programManagerViewForm','incidentForm.lstClient','', true, '<c:out value="${ctx}"/>');">Add</a>
						&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;			
						<a href="javascript:removeSel('incidentForm.lstClient')">Remove</a>
					</td>
		        </tr>
		    </table>
			
		</td>
		<td colspan="2">Staff Involved:<br />
			<bean:define id="lstStaffSelection" name="programManagerViewForm" property="incidentForm.staffSelectionList"/>
			<table width="100%" >
		        <tr>
		           <td><html:select property="incidentForm.lstStaff" multiple="true" size="5" style="width:90%;">
		              <html:options collection="lstStaffSelection" property="key" labelProperty="value"></html:options>
		           </html:select>
		           <html:hidden property="incidentForm.txtStaffKeys" value="" />
		           <html:hidden property="incidentForm.txtStaffValues" value="" /></td>
		        </tr>
		        <tr>
					<td class="clsButtonBarText">
						&nbsp;&nbsp;
						<a id="staffAdd" href="javascript:showLookup('USR', '', '', 'programManagerViewForm','incidentForm.lstStaff','', true, '<c:out value="${ctx}"/>')">Add</a>
						&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;
						<a href="javascript:removeSel('incidentForm.lstStaff')">Remove</a>
					</td>
		        </tr>
		      </table>
		</td>
	</tr>	
	<tr>
		<td colspan="4">Witnesses: <html-el:text property="incidentForm.incident.witnesses" style="width:100%;"/></td>		
	</tr>
	 
	<tr>
		<td colspan="2">Others Involved:<br />
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
		<td colspan="2" rowspan="3">Nature of Incident:<br />
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
		<td colspan="2">Client Issues:<br />
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
		<td colspan="2">Disposition:<br />
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
		<td colspan="4">Location of Incident: <html-el:text property="incidentForm.incident.location" style="width:100%;" /></td>		
	</tr>
	<tr>
		<td colspan="4">Incident Details: <br />
		<html-el:textarea property="incidentForm.incident.description" style="width:100%;" rows="3" /></td>		
	</tr>	
	<tr>
		<td colspan="2">Restriction Information:<html-el:text property="incidentForm.incident.restriction" /></td>		
		<td colspan="2">Charges Laid: 
			<html:select property="incidentForm.incident.chargesLaid">
				<html:option value="-1" >--</html:option>
           		<html:option value="1">Yes</html:option>
           		<html:option value="0">No</html:option>
         	</html:select>
		</td>
	</tr>
	<tr>
		<td colspan="2">Police Report#: <html-el:text property="incidentForm.incident.policeReportNo" /></td>		
		<td colspan="2">Badge Number: <html-el:text property="incidentForm.incident.badgeNo" /></td>
	</tr>
	<tr>
		<td colspan="2">Investigation Recommendation: <html-el:text property="incidentForm.incident.investigationRcmd" style="width:100%;" /></td>
		<td colspan="2">Follow up Information: <html-el:text property="incidentForm.incident.followupInfo" style="width:100%;" /></td>
	</tr>
	<tr>
		<td colspan="2">Investigation Conducted By: <html-el:text property="incidentForm.incident.investigationConductedby" /></td>	
		<td colspan="2">Follow up Completed By: <html-el:text property="incidentForm.incident.followupCompletedby" /></td>	
	</tr>
	<tr>	
		<td>Investigation Date: </td>
		<td><quatro:datePickerTag property="incidentForm.investigationDateStr" width="150px"
								openerForm="programManagerViewForm" /></td>
		<td>Follow up Date: </td>
		<td><quatro:datePickerTag property="incidentForm.followupDateStr" width="150px"
								openerForm="programManagerViewForm" /></td>
	</tr>
	<tr>
		<td colspan="4">Report Complete/Signed <html-el:checkbox name="programManagerViewForm" property="incidentForm.incident.reportCompleted" value="1"/>
		</td>		
	</tr>
</table>	
