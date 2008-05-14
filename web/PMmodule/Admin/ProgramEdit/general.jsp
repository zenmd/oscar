<!-- 

Source:web/PMmodule/Admin/ProgramEdit/general.jsp 

-->


<%@ include file="/taglibs.jsp"%>
<%@ page import="org.oscarehr.PMmodule.model.ProgramSignature" %>
<%@ page import="org.oscarehr.PMmodule.model.Program" %>



<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<th class="pageTitle" align="center">Program Management - Program New</th>
	</tr>
	<tr>
		<td align="left" class="buttonBar">
			<html:link   
			action="/PMmodule/ProgramManager.do"
			style="color:Navy;text-decoration:none;">
			<img border="0" src="<html:rewrite page="/images/Back16.png"/>" />&nbsp;Close&nbsp;&nbsp;</html:link>
			<html:link
			href="javascript:void(0);"
			style="color:Navy;text-decoration:none;">
			<img border="0" src="<html:rewrite page="/images/Save16.png"/>" />&nbsp;Save&nbsp;&nbsp;</html:link>
			
		</td>
	</tr>
</table>

<%@ include file="/common/messages.jsp"%>

<div class="tabs">
	<table cellpadding="3" cellspacing="0" border="0">
		<tr>
			<th title="sinatures">General Information</th>
		</tr>
	</table>
</div>

<table width="100%" border="1" cellspacing="2" cellpadding="3">
	<tr class="b">
		<td width="20%">Name:</td>
		<td><html:text property="program.name" size="30" maxlength="70"/></td>
	</tr>
	<tr class="b">
		<td width="20%">Facility</td>
		<td>
			<html-el:select property="program.facilityId">
				<c:forEach var="facility" items="${facilities}">
					<html-el:option value="${facility.id}"><c:out value="${facility.name}"/></html-el:option>
				</c:forEach>
			</html-el:select>
		</td>
	</tr>	
	<tr class="b">
		<td width="20%">Description:</td>
		<td><html:text property="program.descr" size="30" maxlength="255"/></td>
	</tr>
	<tr class="b">
		<td width="20%">HIC:</td>
		<td><html:checkbox property="program.hic" /></td>
	</tr>
	<tr class="b">
		<td width="20%">Address:</td>
		<td><html:text property="program.address" size="30" maxlength="255"/></td>
	</tr>
	<tr class="b">
		<td width="20%">Phone:</td>
		<td><html:text property="program.phone" size="30" maxlength="25" /></td>
	</tr>
	<tr class="b">
		<td width="20%">Fax:</td>
		<td><html:text property="program.fax" size="30" maxlength="25" /></td>
	</tr>
	<tr class="b">
		<td width="20%">Type:</td>
		<td>
			<html:select property="program.type">
				<html:option value="Bed" />
				<html:option value="Service" />
				<caisi:isModuleLoad moduleName="TORONTO_RFQ" reverse="false">
				<html:option value="External"/>
				<html:option value="community">Community</html:option>
				</caisi:isModuleLoad>
			</html:select>
		</td>
	</tr>
	<tr class="b">
		<td width="20%">Status:</td>
		<td>
			<html:select property="program.programStatus">
				<html:option value="active" />
				<html:option value="inactive" />
			</html:select>
		</td>
	</tr>
	<tr class="b">
		<td width="20%">Space Capacity:</td>
		<td><html:text property="program.capacity_space" size="8" maxlength="8" /></td>
	</tr>
	<tr class="b">
		<td width="20%">Funding Capacity:</td>
		<td><html:text property="program.capacity_space" size="8" maxlength="8" /></td>
	</tr>
	<tr class="b">
		<td width="20%">Allow Batch Admissions:</td>
		<td><html:checkbox property="program.allowBatchAdmission" /></td>
	</tr>
	<tr class="b">
		<td width="20%">Allow Batch Discharges:</td>
		<td><html:checkbox property="program.allowBatchDischarge" /></td>
	</tr>
	<tr class="b">
		<td width="20%">Male/Female:</td>
		<td>
			<html:select property="program.manOrWoman">
				<html:option value="Male"/>				
				<html:option value="Female" />
				<html:option value="Both" />
			</html:select>
		</td>
	</tr>
	<tr class="b">
		<td width="20%">Bed Program Affiliated:</td>
		<td><html:checkbox property="program.bedProgramAffiliated" /></td>
	</tr>
	<tr class="b">
		<td width="20%">Minimum Age (inclusive):</td>
		<td><html:text property="program.ageMin" size="8" maxlength="8"/></td>
	</tr>
	<tr class="b">
		<td width="20%">Maximum Age (inclusive):</td>
		<td><html:text property="program.ageMax" size="8" maxlength="8"/></td>
	</tr>
		
</table>

 </br>
 
 
<div class="tabs">
	<table cellpadding="3" cellspacing="0" border="0">
		<tr>
			<th title="sinatures">Signature</th>
		</tr>
	</table>
</div>


<table width="100%" border="1" cellspacing="2" cellpadding="3">
	<tr class="b">
		<td>&nbsp;&nbsp;User:&nbsp;&nbsp;<c:out value="${programFirstSignature.providerName}"/></td>
		<td>Date:&nbsp;&nbsp;<c:out value="${programFirstSignature.updateDate}" /></td>
	</tr>
	
</table>

<script>
	function save() {
		var maxAllowed = document.programManagerForm.elements['program.maxAllowed'].value;		
		if(isNaN(maxAllowed)) {
			alert("Maximum participants '" + maxAllowed + "' is not a number");
			return false;
		}
		if(document.programManagerForm.elements['program.maxAllowed'].value <= 0) {
			alert('Maximum participants must be a positive integer');
			return false;
		}
		
		if(document.programManagerForm.elements['program.name'].value==null || document.programManagerForm.elements['program.name'].value.length <= 0) {
			alert('The program name can not be blank.');
			return false;
		}
		
		
		document.programManagerForm.method.value='save';
		document.programManagerForm.submit()
	}
	
	function getProgramSignatures(id) {
	    if (id==null || id == "") return;
		var url = '<html:rewrite action="/PMmodule/ProgramManager.do"/>?method=programSignatures&programId=';
		window.open(url + id, 'signature', 'width=600,height=600,scrollbars=1');
	}
</script>
<html:hidden property="program.numOfMembers" />
<html:hidden property="program.id"/>









<%
Program p = (Program)request.getAttribute("oldProgram");

%>
<input type="hidden" name="old_maxAllowed" value=<%if(p!=null) { %>"<%=p.getMaxAllowed() %>" <%}else{ %> "0" <%} %>/>
<input type="hidden" name="old_name" value=<%if(p!=null) { %>"<%=p.getName()%>" <%}else{ %> "" <%} %>/>
<input type="hidden" name="old_descr" value=<%if(p!=null) { %>"<%=p.getDescr()%>" <%}else{ %> "" <%} %>/>
<input type="hidden" name="old_type" value=<%if(p!=null) { %>"<%=p.getType()%>" <%}else{ %> "" <%} %>/>
<input type="hidden" name="old_address" value=<%if(p!=null) { %>"<%=p.getAddress()%>"<%}else{ %> "" <%} %> />
<input type="hidden" name="old_phone" value=<%if(p!=null) { %>"<%=p.getPhone()%>" <%}else{ %> "" <%} %>/>
<input type="hidden" name="old_fax" value=<%if(p!=null) { %>"<%=p.getFax() %>" <%}else{ %> "" <%} %>/>
<input type="hidden" name="old_url" value=<%if(p!=null) { %>"<%=p.getUrl()%>" <%}else{ %> "" <%} %>/>
<input type="hidden" name="old_email" value=<%if(p!=null) { %>"<%=p.getEmail()%>" <%}else{ %> "" <%} %>/>
<input type="hidden" name="old_emergencyNumber" value=<%if(p!=null) { %>"<%=p.getEmergencyNumber()%>"<%}else{ %> "" <%} %> />
<input type="hidden" name="old_location" value=<%if(p!=null) { %>"<%=p.getLocation()%>" <%}else{ %> "" <%} %>/>
<input type="hidden" name="old_programStatus" value=<%if(p!=null) { %>"<%=p.getProgramStatus()%>" <%}else{ %> "" <%} %>/>
<input type="hidden" name="old_bedProgramLinkId" value=<%if(p!=null) { %>"<%=p.getBedProgramLinkId()%>" <%}else{ %> "0" <%} %>/>
<input type="hidden" name="old_manOrWoman" value=<%if(p!=null) { %>"<%=p.getManOrWoman() %>" <%}else{ %> "" <%} %>/>
<input type="hidden" name="old_abstinenceSupport" value=<%if(p!=null) { %>"<%=p.getAbstinenceSupport() %>" <%}else{ %> "" <%} %>/>
<input type="hidden" name="old_exclusiveView" value=<%if(p!=null) { %>"<%=p.getExclusiveView() %>" <%}else{ %> "" <%} %>/>
<input type="hidden" name="old_holdingTank" value=<%if(p!=null) { %>"<%=p.isHoldingTank() %>" <%}else{ %> "" <%} %>/>
<input type="hidden" name="old_allowBatchAdmission" value=<%if(p!=null) { %>"<%=p.isAllowBatchAdmission() %>" <%}else{ %> "" <%} %>/>
<input type="hidden" name="old_allowBatchDischarge" value=<%if(p!=null) { %>"<%=p.isAllowBatchDischarge() %>" <%}else{ %> "" <%} %>/>
<input type="hidden" name="old_hic" value=<%if(p!=null) { %>"<%=p.isHic() %>" <%}else{ %> "" <%} %>/>
<input type="hidden" name="old_transgender" value=<%if(p!=null) { %>"<%=p.isTransgender() %>" <%}else{ %> "" <%} %>/>
<input type="hidden" name="old_firstNation" value=<%if(p!=null) { %>"<%=p.isFirstNation() %>" <%}else{ %> "" <%} %>/>
<input type="hidden" name="old_bedProgramAffiliated" value=<%if(p!=null) { %>"<%=p.isBedProgramAffiliated() %>" <%}else{ %> "" <%} %>/>
<input type="hidden" name="old_alcohol" value=<%if(p!=null) { %>"<%=p.isAlcohol()%>" <%}else{ %> "" <%} %>/>
<input type="hidden" name="old_physicalHealth" value=<%if(p!=null) { %>"<%=p.isPhysicalHealth() %>" <%}else{ %> "" <%} %>/>
<input type="hidden" name="old_mentalHealth" value=<%if(p!=null) { %>"<%=p.isMentalHealth() %>" <%}else{ %> "" <%} %>/>
<input type="hidden" name="old_facility_id" value=<%if(p!=null) { %>"<%=p.getFacilityId() %>" <%}else{ %> "" <%} %>/>
<input type="hidden" name="old_housing" value=<%if(p!=null) { %>"<%=p.isHousing() %>" <%}else{ %> "" <%} %>/>



