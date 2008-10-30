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
<table width="100%" cellpadding="0px" cellspacing="0px" height="100%"
	border="0">
	<tr>
		<td align="left" class="buttonBar2">			
			<html:link
			action="/PMmodule/ProgramManager.do"
			style="color:Navy;text-decoration:none;">&nbsp;
			<img style="vertical-align: middle" border="0" src="<html:rewrite page="/images/Back16.png"/>" />&nbsp;Back to Programs&nbsp;&nbsp;</html:link>
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
			<div class="tabs" id="tabs">
				<br />
				<table cellpadding="3" cellspacing="0" border="0">
					<tr>
						<th title="Programs">General Information</th>
					</tr>
				</table>
			</div>
		</td>
	</tr>
	<tr height="100%">
		<td>
			<div
				style="color: Black; background-color: White; border-width: 1px; border-style: Ridge;
	                    height: 100%; width: 100%; overflow: auto;" id="scrollBar">
	  		
			<table width="100%" border="1" cellspacing="2" cellpadding="3">
				<tr class="b">
					<td width="20%">Name:</td>
					<td><c:out value="${program.name}" /></td>
				</tr>
				<tr class="b">
					<td width="20%">Facility:</td>
					<td>					
					<a href="<html:rewrite action="/PMmodule/FacilityManager.do"/>?method=view&facilityId=<c:out value="${program.facilityId}" />">
						<c:out value="${facilityName}" />					
					</a>
					</td>
				</tr>
				<tr class="b">
					<td width="20%">Description:</td>
					<td><c:out value="${program.descr}" /></td>
				</tr>
				<tr class="b">
					<td width="20%">Phone:</td>
					<td><c:out value="${program.phone}" /></td>
				</tr>
				<tr class="b">
					<td width="20%">Health Information Custodian:</td>
					<td>
						<logic:equal name="program" property="hic" value="true">Yes</logic:equal>
						<logic:equal name="program" property="hic" value="false">No</logic:equal>
					</td>
				</tr>
				
					
				
				
				<tr class="b">
					<td width="20%">Type:</td>
					<td><c:out value="${program.type}" /></td>
				</tr>
				<tr class="b">
					<td width="20%">Status:</td>
					<td><c:out value="${program.programStatusTxt}" /></td>
					
				</tr>
				
				<tr class="b">
					<td width="20%">Space Capacity:</td>
					<td><c:out value="${program.capacity_space}" /></td>
					
				</tr>
				<tr class="b">
					<td width="20%">Funding Capacity:</td>
					<td><c:out value="${program.capacity_funding}" /></td>
					
				</tr>		
				<tr class="b">
					<td width="20%">Male/Female:</td>
					<td>
						<c:out value="${program.genderDesc}" />
					</td>
				</tr>				
				<tr class="b">
					<td width="20%">Minimum Age:</td>
					<td><c:out value="${program.ageMin}" /></td>
				</tr>
				<tr class="b">
					<td width="20%">Maximum Age:</td>
					<td><c:out value="${program.ageMax}" /></td>
				</tr>
				
				<!-- 
				<tr class="b">
					<td width="20%">Location:</td>
					<td><c:out value="${program.location}" /></td>
				</tr>
				<tr class="b">
					<td width="20%">Client Participation:</td>
					<td><c:out value="${program.numOfMembers}" />/<c:out value="${program.maxAllowed}" />&nbsp;(<c:out value="${program.queueSize}" /> waiting)</td>
				</tr>
				<tr class="b">
					<td width="20%">Holding Tank:</td>
					<td><c:out value="${program.holdingTank}" /></td>
				</tr>
				<tr class="b">
					<td width="20%">Transgender:</td>
					<td><c:out value="${program.transgender}" /></td>
				</tr>
				<tr class="b">
					<td width="20%">First Nation:</td>
					<td><c:out value="${program.firstNation}" /></td>
				</tr>
				
				<tr class="b">
					<td width="20%">Alcohol:</td>
					<td><c:out value="${program.alcohol}" /></td>
				</tr>
				<tr class="b">
					<td width="20%">Abstinence Support?</td>
					<td><c:out value="${program.abstinenceSupport}" /></td>
				</tr>
				<tr class="b">
					<td width="20%">Physical Health:</td>
					<td><c:out value="${program.physicalHealth}" /></td>
				</tr>
				<tr class="b">
					<td width="20%">Mental Health:</td>
					<td><c:out value="${program.mentalHealth}" /></td>
				</tr>
				<tr class="b">
					<td width="20%">Housing:</td>
					<td><c:out value="${program.housing}" /></td>
				</tr>
				<tr class="b">
					<td width="20%">Exclusive View:</td>
					<td><c:out value="${program.exclusiveView}" /></td>
				</tr>
				 -->
				
				
			</table>
	        </div>
 		</td>
	</tr>
</table>
