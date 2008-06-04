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


<style>
	.non_sorted_header {
		color:black;
		background:white;
	}
</style>


<table width="100%" cellpadding="0px" cellspacing="0px" height="100%"
	border="0">

	
	<tr>
		<td align="left" class="buttonBar">
			<html:link
			action="/PMmodule/ProgramManager.do"
			style="color:Navy;text-decoration:none;">
			<img border="0" src="<html:rewrite page="/images/Back16.png"/>" />&nbsp;Close&nbsp;&nbsp;</html:link>
<!-- 			<html:link
				href="javascript:submitForm('saveStaff');"
				style="color:Navy;text-decoration:none;">
				<img border="0" src="<html:rewrite page="/images/Save16.png"/>" />&nbsp;Save&nbsp;&nbsp;</html:link>
 -->
 			<html:link
			href="javascript:searchStaff();"
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
			<div class="h4">
				<p></p>
				<h4>Search Staff by entering search criteria below</h4>
				<br />
			</div>
		</td>
	</tr>
	

	<tr>
		<td>
			<div class="axial">
				<table border="0" cellspacing="2" cellpadding="3">
					<tr>
						<th>First Name:</th>
						<td><html:text property="staffForm.firstName" size="20" maxlength="30"/></td>
					</tr>
					<tr>
						<th>Last Name:</th>
						<td><html:text property="staffForm.lastName" size="20" maxlength="30"/></td>
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
					<th title="Programs">Staff</th>
				</tr>
			</table>
			</div> 
			
			
			
			
			<display:table class="simple" cellspacing="2" cellpadding="3"
				id="pp" name="existStaffLst" export="false" pagesize="0"
				requestURI="/PMmodule/ProgramManagerView.do" >
				
				<display:setProperty name="paging.banner.placement" value="bottom" />
				<display:setProperty name="basic.msg.empty_list"
					value="No staff currently in place for this program." />
			
	
				<display:column title="Select" >
					<input type="checkbox" name="p2<%=pageContext.getAttribute("pp_rowNum")%>" value='<c:out value="${pp.id}"/>' /> 
					<input type="hidden" name="lineno2" value="<%=pageContext.getAttribute("pp_rowNum")%>" />
					<input type="hidden" name="id<%=pageContext.getAttribute("pp_rowNum")%>" value='<c:out value="${pp.id}"/>' />
				</display:column>			
				
				<display:column sortable="true" title="User No" property="providerNo" sortProperty="providerNo"/>
				
				<display:column sortable="true" title="Name" property="providerName"/>
				
				<display:column sortable="true" title="Role" property="roleName_desc"/>
			
			</display:table>
			
			<logic:empty name="existStaffLst">
				No record to display.<br />
			</logic:empty>
			<logic:notEmpty name="existStaffLst">
				<table width="100%">
					<tr>
						<td class="clsButtonBarText" >&nbsp;&nbsp;<a href="javascript:submitForm('addStaff');">Add</a>
						</td>
						<td class="clsButtonBarText" width="100%">&nbsp;&nbsp;<a 

href="javascript:submitForm('removeExistStaff');">Remove</a>
						</td>
					</tr>
				</table>
			</logic:notEmpty>
			

			
			<br />
			
			<logic:notEmpty name="newStaffLst">
			
				<table align="center" class="simple" width="100%">
					<thead>
						<tr>
							<th align="center"><b>Provider No.</b></th>
							<th align="center"><b>Name</b></th>
							<th align="center"><b>Role</b></th>
							<th align="center"><b></b></th>
						</tr>
					</thead>
					
					<logic:iterate id="pp" name="newStaffLst" indexId="rIndex">
						<input type="hidden" name="lineno" value="<%=String.valueOf(rIndex)%>" />
						<tr>
							<!-- 
							<td align="center" width="50px">
								<input type="checkbox"	name="p<%=String.valueOf(rIndex)%>" value="" /> 
								<input type="hidden" name="lineno" value="<%=String.valueOf(rIndex)%>" /></td>
	 						-->
													
					
							<td width="120px">
								<table cellpadding="0" style="border:0px;" cellspacing="0"
									width="100%">
									<tr>
										<td style="border:0px;" width="120px">
											<input style="width:100px;" type="text"
											name="providerNo<%=String.valueOf(rIndex)%>"
											value='<c:out value="${pp.providerNo}"/>' readonly></td>
										
									</tr>
								</table>
							</td>
							<td width="250px">
								<table cellpadding="0" style="border:0px;" cellspacing="0"
									width="100%">
									<tr>
										
										<td style="border:0px;" width="100%">
											<input id="ORGfld<%=String.valueOf(rIndex)%>" 
											style="width:100%;" type="text"  
											name="providerName<%=String.valueOf(rIndex)%>"
											value='<c:out value="${pp.providerName}"/>'
											readonly></td>
										<td style="border:0px;" width="35px">
											<a	onclick="showLookup('USR', '', '', 

'programManagerViewForm','providerNo<%=String.valueOf(rIndex)%>','providerName<%=String.valueOf(rIndex)%>', true, '<c:out value="${ctx}"/>');"><img
											src="<c:out 

value="${ctx}"/>/images/microsoftsearch.gif"></a></td>
									</tr>
								</table>
							</td>
							<td width="250px">
								<table cellpadding="0" style="border:0px;" cellspacing="0"
									width="100%">
									<tr>
										<td style="border:0px;" width="1px"><input type="text"
											style="width:1px;"
											name="role_code<%=String.valueOf(rIndex)%>"
											value='<c:out value="${pp.roleName}"/>' readonly></td>
										<td style="border:0px;" width="100%"><input
											style="width:100%;" type="text" 
											name="role_description<%=String.valueOf(rIndex)%>"
											value='<c:out value="${pp.roleName_desc}"/>'
											readonly></td>
										<td style="border:0px;" width="35px"><a  
											onclick="showLookup('ROL', '', '', 

'programManagerViewForm','role_code<%=String.valueOf(rIndex)%>','role_description<%=String.valueOf(rIndex)%>', true, '<c:out value="${ctx}"/>');"><img
											src="<c:out 

value="${ctx}"/>/images/microsoftsearch.gif"></a></td>
									</tr>
								</table>
							</td>
							
							<td class="clsButtonBarText" >&nbsp;&nbsp;
								<logic:equal value="0" name="rIndex">
									<a href="javascript:submitForm('saveStaff');">Save</a>
								</logic:equal>
							</td>	
						</tr>
					</logic:iterate>
				</table>
				<!-- 
				<table width="100%">
					<tr>
						<td class="clsButtonBarText" width="100%">&nbsp;&nbsp;<a
							href="javascript:submitForm('addStaff');">Add</a>&nbsp;&nbsp;&nbsp;|
						&nbsp;&nbsp;<a href="javascript:submitForm('removeStaff');">Remove</a>
						</td>
					</tr>
				</table>
				 -->    
				 
			</logic:notEmpty>  
			    
        
         
        </div>
 
		</td>
	</tr>
</table>

<script>
	function submitForm(mthd) {
		var flag = true;
		if(mthd == "removeExistStaff"){
			flag = confirm('Do you really want to remove these records?');
		}
		if(mthd == "saveStaff"){
			document.programManagerViewForm.action = document.programManagerViewForm.action + "?mthd=search";
		}
			
		if(flag){
			document.programManagerViewForm.method.value=mthd;
			document.programManagerViewForm.submit();
		}
	}
	
	function resetForm() {
		document.getElementsByName("staffForm.firstName")[0].value = "";
		document.getElementsByName("staffForm.lastName")[0].value = "";
	}
	
	function searchStaff(){

		document.programManagerViewForm.action = document.programManagerViewForm.action + "?mthd=search";
		//alert(document.programManagerViewForm.action);
		document.programManagerViewForm.tab.value = "Staff";
		document.programManagerViewForm.submit();
		
	
	}
</script>