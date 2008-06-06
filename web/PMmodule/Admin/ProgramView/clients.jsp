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

<script>
	function assignTeam(id,selectBox) {
	/*
		var team_id = selectBox.options[selectBox.selectedIndex].value;
		document.programManagerViewForm.elements['teamId'].value=team_id;
		document.programManagerViewForm.elements['admissionId'].value=id;
		document.programManagerViewForm.method.value='assign_team_client';
		document.programManagerViewForm.submit();
	*/	
	}
	
	function assignStatus(id,selectBox) {
		var status_id = selectBox.options[selectBox.selectedIndex].value;
		document.programManagerViewForm.elements['clientStatusId'].value=status_id;
		document.programManagerViewForm.elements['admissionId'].value=id;
		document.programManagerViewForm.method.value='assign_status_client';
		document.programManagerViewForm.submit();
	}
	
	function dischargeToCommunity(id,selectBox) {
		var com = selectBox.options[selectBox.selectedIndex].value;
		document.programManagerViewForm.community.value=com;
		document.programManagerViewForm.elements['admissionId'].value=id;
		document.programManagerViewForm.method.value='discharge_To_Community';
		document.programManagerViewForm.submit();
	}
	
	function do_batch_discharge() {
	
		//get clients
		var elements = document.programManagerViewForm.elements;
		var admissionIds = new Array();
		var x=0;
		var numClients = 0;
		for(var i=0;i<elements.length;i++) {
			if(elements[i].type == 'checkbox' && elements[i].name.substring(0,8) == 'checked_') {
				if(elements[i].checked == true) {
					var admissionId = elements[i].name.substring(8);
					admissionIds[x++] = admissionId;
					numClients++;
				}
			}
		}
		
		var msg = 'You are about to discharge ' + numClients + ' client(s).' + '\n' +
					'Are you sure you would like to continue?';
		if(numClients == 0) {
			alert('You have to select the clients');
			return;
		}
	
		if(confirm(msg)) {	
			document.programManagerViewForm.method.value='batch_discharge';
			document.programManagerViewForm.submit();
		}
	}

	function resetForm() {
		document.getElementsByName("clientForm.firstName")[0].value = "";
		document.getElementsByName("clientForm.lastName")[0].value = "";
		document.getElementsByName("clientForm.room")[0].value = "";
		document.getElementsByName("clientForm.bed")[0].value = "";
	}
	
	function searchClients(){

		document.programManagerViewForm.action = document.programManagerViewForm.action + "?mthd=search";
		//alert(document.programManagerViewForm.action);
		document.programManagerViewForm.tab.value = "Clients";
		document.programManagerViewForm.submit();
		
	
	}
</script>

<input type="hidden" name="teamId" value="" />
<input type="hidden" name="admissionId" value="" />
<input type="hidden" name="community" value="" />
<input type="hidden" name="type" value="" />
<input type="hidden" name="program_name" value="<c:out value="${program_name}"/>" />
<input type="hidden" name="clientStatusId" />

 	
 	
 	

 
 
<%@ include file="/taglibs.jsp"%>

<table width="100%" cellpadding="0px" cellspacing="0px" height="100%"
	border="0">

	
	<tr height="18px">
		<td align="left" class="buttonBar">
			<a href="javascript:clickTab('General');"
			style="color:Navy;text-decoration:none;">
			<img border="0" src="<html:rewrite page="/images/Back16.png"/>" />&nbsp;Close&nbsp;&nbsp;</a>
 			<html:link
			href="javascript:searchClients();"
			style="color:Navy;text-decoration:none;">
			<img border="0" src="<html:rewrite page="/images/search16.gif"/>" />&nbsp;Search&nbsp;&nbsp;</html:link>
			<html:link
			href="javascript:resetForm();"
			style="color:Navy;text-decoration:none;">
			<img border="0" src="<html:rewrite page="/images/searchreset.gif"/>" />&nbsp;Reset&nbsp;&nbsp;</html:link>
		</td>
	</tr>
	<tr>
		<td>
			<logic:messagesPresent message="true">
				<br />
				<html:messages id="message" message="true" bundle="pmm">
					<c:out escapeXml="false" value="${message}" />
				</html:messages>
				<br />
			</logic:messagesPresent>
		</td>
	</tr>
	
	<tr>
		<td>
			<div class="axial">
				<table border="0" cellspacing="2" cellpadding="3">
					<tr>
						<th>Client No.:</th>
						<td><html:text property="clientForm.clientId" size="20" maxlength="10"/></td>
					</tr>
					<tr>
						<th>First Name:</th>
						<td><html:text property="clientForm.firstName" size="20" maxlength="30"/></td>
					</tr>
					<tr>
						<th>Last Name:</th>
						<td><html:text property="clientForm.lastName" size="20" maxlength="30"/></td>
					</tr>
				
				</table>
			</div>
			
			

		</td>
	</tr>

	<tr>
		<td height="100%">
		
		<div
			style="color: Black; background-color: White; border-width: 1px; border-style: Ridge;
                    height: 100%; width: 100%; overflow: auto;">
  
			 	
			<br /> 	
						
						
			<div class="tabs" id="tabs">
			<table cellpadding="3" cellspacing="0" border="0">
				<tr>
					<th title="Programs">Clients</th>
				</tr>
			</table>
			</div>
			<!--  show current clients -->
			
			<display:table class="simple" cellspacing="2" cellpadding="3" id="clientInfo" name="clientsLst" export="false" pagesize="0" requestURI="/PMmodule/ProgramManagerView.do">
				<display:setProperty name="paging.banner.placement" value="bottom" />
				<display:setProperty name="basic.msg.empty_list" value="No clients currently admitted to this program." />
			
				<display:column title="Select">
					<logic:equal name="clientInfo" property="isHead" value="true" >
						<logic:equal name="clientInfo" property="isDischargeable" value="1"> 
							<input type="checkbox" name="checked_<c:out value="${clientInfo.admissionId}"/>">
						</logic:equal>
						<logic:equal name="clientInfo" property="isDischargeable" value="0"> 
							<input type="checkbox" disabled="disabled" name="checked_<c:out value="${clientInfo.admissionId}"/>">
						</logic:equal>
					</logic:equal>	
					
				</display:column>
			
				<display:column property="clientId" sortable="true" title="Client No." />
				
				<display:column property="lastName" sortable="true" title="Last Name" />
				
				<display:column property="firstName" sortable="true" title="First Name" />
				
				<display:column property="admissionDate" sortable="true" title="Admission Date" />
				
				<display:column property="admissionNote" sortable="true" title="Admission Notes" />
				
				<display:column property="room" sortable="true" title="Room" />
				
				<display:column property="bed" sortable="true" title="Bed" />
				
				<display:column title="Late Pass">		
					<logic:equal name="clientInfo" property="isDischargeable" value="1"> 
						<input type="checkbox" disabled="disabled">
					</logic:equal>
					<logic:equal name="clientInfo" property="isDischargeable" value="0"> 
						<input type="checkbox" checked="checked" disabled="disabled">
					</logic:equal>
				</display:column>
				
			</display:table>
			
			
			<logic:notEmpty name="clientsLst">
				<br />
				<table align="left">
					<tr>
						<td align="left">
							<table class="edit">
								
								<tr>
									<td width="150px">Discharge Reason</td>
									<td><html:select property="clientForm.dischargeReason" style="width:100%">
										<html:option value=""></html:option>
										<html:options collection="lstDischargeReason" property="code"
											labelProperty="description"></html:options>
									</html:select></td>
									
								</tr>
								<tr>
									<td >Discharge To</td>
									<td >
										<html:select property="clientForm.communityProgramCode">
										<html:option value=""></html:option>
										<html:options collection="lstCommProgram" property="code"
											labelProperty="description"></html:options>
									</html:select></td>
								</tr>
								
							</table>
						</td>
						<td align="left" >
							<input type="button" value="Batch Discharge" onclick="javascript:do_batch_discharge();"/>
						</td>
					</tr>
				</table>
			</logic:notEmpty>
						
			
			
         
        </div>
 
		</td>
	</tr>
</table>
 	

