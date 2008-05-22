<%@ include file="/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/quatro-tag.tld" prefix="quatro" %>
<%@page import="org.oscarehr.PMmodule.model.QuatroAdmission"%>
<%@page import="java.util.Date"%>
<%@page import="org.oscarehr.PMmodule.model.Bed"%>
<%@page import="org.oscarehr.PMmodule.model.Room"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<script type="text/javascript" src='<c:out value="${ctx}"/>/js/quatroLookup.js'></script>
 
<html-el:form action="/PMmodule/QuatroAdmission.do">
<input type="hidden" name="method"/>
<html:hidden property="admission.clientId"/>
<html:hidden property="admission.intakeId"/>
<html:hidden property="admission.id"/>
<html:hidden property="admission.programId"/>
<html:hidden property="admission.admissionDateTxt"/>
<html:hidden property="admission.admissionStatus"/>
<script lang="javascript">
function submitForm(methodVal) {
	document.forms(0).method.value = methodVal;
	document.forms(0).submit();
}

function signSignature(){
   var url='<c:out value="${ctx}" />/PMmodule/ClientManager/signature.jsp?' +
     "rid=<c:out value="${quatroClientAdmissionForm.admission.id}" />" + 
     "&moduleName=admission";

   win = window.open(url,"_blank","toolbar=yes,menubar= yes,resizable=yes,scrollbars=yes,status=yes,width=600,height=400");
   win.focus();
}
function viewSignature(){
   var url='<c:out value="${ctx}" />/topazGetImage.do?' +
     "rid=<c:out value="${quatroClientAdmissionForm.admission.id}" />";

   win = window.open(url,"_blank","toolbar=yes,menubar= yes,resizable=yes,scrollbars=yes,status=yes,width=400,height=200");
   win.focus();
}
</script>
<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
	<tr>
		<th class="pageTitle" align="center">Client Management - Admission</th>
	</tr>
	<tr>
	<td>
		<table width="100%" class="simple">
			<tr>
			<td style="width: 15%"><font><b>Client No.</b></font></td><td colspan="3"><font><b><c:out value="${client.demographicNo}" /></b></font></td>
			</tr>
			<tr>
				<td style="width: 15%"<font><b>Name</b></font></td>
				<td style="width: 35%"><font><b><c:out value="${client.formattedName}" /></b></font></td>
				<td style="width: 15%"><font><b>Date of Birth </b></font></td>
				<td style="width: 35%"><font><b><c:out value="${client.yearOfBirth}" />/<c:out value="${client.monthOfBirth}" />/<c:out value="${client.dateOfBirth}" /></b></font></td>
			</tr>
		</table>
	</td>
	</tr>
	<tr>
		<td align="left" valign="middle" class="buttonBar3">
		&nbsp;Summary&nbsp;&nbsp;|&nbsp;&nbsp;
		History&nbsp;&nbsp;|&nbsp;&nbsp;
		Intake&nbsp;&nbsp;|&nbsp;&nbsp;
		<b>Admission</b>&nbsp;&nbsp;|&nbsp;&nbsp;
		Refer&nbsp;&nbsp;|&nbsp;&nbsp;
		Discharge&nbsp;&nbsp;|&nbsp;&nbsp;
		Service Restriction&nbsp;&nbsp;|&nbsp;&nbsp;
		Complaints
		</td>
	</tr>
	<tr>
		<td align="left" class="buttonBar">
		<a href='javascript:submitForm("save");' style="color:Navy;text-decoration:none;">
		<img border=0 src=<html:rewrite page="/images/Save16.png"/> />&nbsp;Save&nbsp;&nbsp;</a>|

		<logic:greaterThan name="quatroClientAdmissionForm" property="admission.id" value="0">
		  <a href="javascript:signSignature();" style="color:Navy;text-decoration:none;">
		  <img border=0 src=<html:rewrite page="/images/notepad.gif"/> />&nbsp;Sign&nbsp;&nbsp;</a>|
		  <a href="javascript:viewSignature();" style="color:Navy;text-decoration:none;">
		  <img border=0 src=<html:rewrite page="/images/search16.gif"/> />&nbsp;Preview Signature&nbsp;&nbsp;</a>|
        </logic:greaterThan>
		  
		<html:link action="/PMmodule/QuatroAdmission.do" name="actionParam" style="color:Navy;text-decoration:none;">
		<img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Close&nbsp;&nbsp;</html:link></td>
	</tr>
	<tr><td align="left" class="message">
      <logic:messagesPresent message="true">
        <html:messages id="message" message="true" bundle="pmm"><c:out escapeXml="false" value="${message}" />
        </html:messages> 
      </logic:messagesPresent>
	</td></tr>
	<tr>
		<td height="100%">
		<div
			style="color: Black; background-color: White; border-width: 1px; border-style: Ridge;
                    height: 100%; width: 100%; overflow: auto;">

<!--  start of page content -->
<table width="100%" class="edit">
<tr><td><br><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Admission Information</th></tr>
</table></div></td></tr>

<tr><td>
<table class="simple" cellspacing="2" cellpadding="3">
<tr><td width="25%">Ontario Resident Status</td>
<td width="25%"><html:text property="admission.residentStatus" /></td>
<td width="20%">Primary Worker</td>
<td width="30%">
<html:select property="admission.primaryWorker">
<html-el:optionsCollection property="providerList" value="providerNo" label="formattedName"/>
</html:select>
</td></tr>
<tr><td>Locker#</td>
<td><html:text property="admission.lockerNo" /></td>
<td># of Bags</td>
<td><html:text property="admission.noOfBags" /></td></tr>
</table>
</td></tr>

<tr><td><br><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Next of Kin</th></tr>
</table></div></td></tr>

<tr><td>
<table class="simple" cellspacing="2" cellpadding="3">
<tr><td width="10%">Name</td>
<td width="25%"><html:text property="admission.nextKinName" /></td>
<td width="10%">Relationship</td>
<td width="20%"><html:text property="admission.nextKinRelationship" /></td>
<td width="10%">Telephone</td>
<td width="25%"><html:text property="admission.nextKinTelephone" style="width: 70%" /></td></tr>
<tr><td>Number</td>
<td><html:text property="admission.nextKinNumber" /></td>
<td>Street</td>
<td colspan="3"><html:text property="admission.nextKinStreet" style="width: 80%" /></td></tr>
<tr><td>City</td>
<td><html:text property="admission.nextKinCity" /></td>
<td>Province</td>
<td><html:select property="admission.nextKinProvince">
<html-el:optionsCollection property="provinceList" value="code" label="description"/>
</html:select></td>
<td>Postal</td>
<td><html:text property="admission.nextKinPostal" style="width: 70%" /></td></tr>
</table>
</td></tr>

<tr><td><br><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Overnight Pass</th></tr>
</table></div></td></tr>

<tr><td>
<table class="simple" cellspacing="2" cellpadding="3">
<tr><td colspan="4">Overnight Pass</td>
<td>Issued By</td></tr>
<tr><td width="5%">Start</td>
<td width="20%">
<quatro:datePickerTag property="admission.ovPassStartDateTxt" width="70%" openerForm="quatroClientAdmissionForm" />
</td>
<td width="5%">End</td>
<td width="20%">
<quatro:datePickerTag property="admission.ovPassEndDateTxt" width="70%" openerForm="quatroClientAdmissionForm" />
</td>
<td width="50%"><c:out value="${issuedBy}" /></td></tr>
</table>
</td></tr>

<tr><td><br><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Bed/Room Reservation</th></tr>
</table></div></td></tr>

<tr><td>
<table class="simple" cellspacing="2" cellpadding="3">
<!-- Begin of Assign Room -------------------------------------------------------------->
	<tr><th width="20%">Assign Room</th>
	<td>
	      <html:select property="roomDemographic.id.roomId" onchange="quatroClientAdmissionForm.method.value='edit';quatroClientAdmissionForm.submit();">
	       <option value="0"></option>
           <html-el:optionsCollection property="availableRooms" value="id" label="name" /> 
          </html:select>
	</td></tr>
<!-- End of Assign Room -------------------------------------------------------------->
	<tr><th width="20%">Assign Bed</th>
	<td><html:select property="bedDemographic.id.bedId">
            <html-el:optionsCollection property="availableBeds" value="id" label="name"/>
	  </html:select>
	</td></tr>
	</table>
</td></tr>

<tr><td align="center">Reason for not signing&nbsp;
<html-el:select property="admission.notSignReason">
  <option value="0"></option>
  <html-el:optionsCollection property="notSignReasonList" value="code" label="description"/>
</html-el:select>
</td></tr>
<tr><td><br>
<b>Notice with Regard to the Collection of Personal Information:</b><br>
Personal information is collected under the legal authority of the City of Toronto Act, 1997, 
Municipal Act, 2001, Chapter 169, Article VII, By-law 112-2005 and Ontario Works Act, 1997, 
for the purposes of  administering Government of Ontario social assistance programs, 
providing shelter services and sharing information between shelter providers when 
specific consent is obtained.  Questions about this collection can be directed to the 
Administrative Supervisor, Shelter, Support and Housing Administration Division, 
Telephone no. 416-392-8741, Metro Hall, 55 John St. 6th Floor, Toronto, Ontario M5V 3C6.
</td></tr>

</table>

<!--  end of page content -->
</div>
</td>
</tr>
</table>
</html-el:form>
