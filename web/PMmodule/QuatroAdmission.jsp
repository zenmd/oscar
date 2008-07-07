<%@ include file="/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/quatro-tag.tld" prefix="quatro" %>
<%@page import="org.oscarehr.PMmodule.model.Admission"%>
<%@page import="java.util.Date"%>
<%@page import="org.oscarehr.PMmodule.model.Bed"%>
<%@page import="org.oscarehr.PMmodule.model.Room"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
 
<html-el:form action="/PMmodule/QuatroAdmission.do">
<input type="hidden" name="method"/>
<input type="hidden" name="pageChanged" id="pageChanged" value='<c:out value="${pageChanged}" />' />
<html:hidden property="admission.clientId"/>
<html:hidden property="admission.intakeId"/>
<html:hidden property="familyIntakeType"/>
<html:hidden property="admission.id"/>
<html:hidden property="admission.programId"/>
<html:hidden property="admission.admissionDateTxt"/>
<html:hidden property="admission.admissionStatus"/>
<input type="hidden" id="scrollPosition" name="scrollPosition" value='<c:out value="${scrPos}"/>' />
<script lang="javascript">

function submitForm(methodVal) {
    if(methodVal=='save'){
      var primaryWorker = document.getElementsByName("admission.primaryWorker")[0];
      if(primaryWorker.value==''){
        alert("Please select Primary Worker.");
        primaryWorker.focus();
        return;
      }
    }
	document.forms[0].method.value = methodVal;
	document.forms[0].submit();
}

function signSignature(){
   var url='<c:out value="${ctx}" />/PMmodule/ClientManager/signature.jsp?' +
     "rid=<c:out value="${quatroClientAdmissionForm.admission.id}" />" + 
     "&moduleName=admission";
   if(win!=null) win.close();
   win = window.open(url,"_blank","toolbar=yes,menubar= yes,resizable=yes,scrollbars=yes,status=yes,width=600,height=400");
   win.focus();
}
function viewSignature(){
   var url='<c:out value="${ctx}" />/topazGetImage.do?' +
     "rid=<c:out value="${quatroClientAdmissionForm.admission.id}" />" +"&moduleName=admission";

   if(win!=null) win.close();
   win = window.open(url,"_blank","toolbar=yes,menubar= yes,resizable=yes,scrollbars=yes,status=yes,width=400,height=200");
   win.focus();
}
function roomChanged()
{
	quatroClientAdmissionForm.method.value='roomchange';
	quatroClientAdmissionForm.pageChanged.value='1';
	setNoConfirm();
	quatroClientAdmissionForm.submit();
}
</script>
<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
	<tr>
		<th class="pageTitle" align="center">Client Management - Admission</th>
	</tr>
	<tr>	
		<td class="simple" style="background: lavender"><%@ include file="ClientInfo.jsp" %></td>	
	</tr>
	<tr>
		<td align="left" class="buttonBar2">
		<c:if test="${quatroClientAdmissionForm.admission.admissionStatus=='active' ||
		 quatroClientAdmissionForm.admission.admissionStatus=='admitted'}">		 
		<a href='javascript:submitForm("save");' style="color:Navy;text-decoration:none;" onclick="javascript: setNoConfirm();">
		<img border=0 src=<html:rewrite page="/images/Save16.png"/> />&nbsp;Save&nbsp;&nbsp;</a>|
		<logic:greaterThan name="quatroClientAdmissionForm" property="admission.id" value="0">
		  <a href="javascript:signSignature();" style="color:Navy;text-decoration:none;" onclick="javascript: setNoConfirm();">
		  <img border=0 src=<html:rewrite page="/images/notepad.gif"/> />&nbsp;Sign&nbsp;&nbsp;</a>|
		  <a href="javascript:viewSignature();" style="color:Navy;text-decoration:none;" onclick="javascript: setNoConfirm();">
		  <img border=0 src=<html:rewrite page="/images/search16.gif"/> />&nbsp;Preview Signature&nbsp;&nbsp;</a>|
        </logic:greaterThan>
        </c:if>       
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
		<div id="scrollBar"  onscroll="getDivPosition()"
			style="color: Black; background-color: White; border-width: 1px; border-style: Ridge;
                    height: 100%; width: 100%; overflow: auto;" id="scrollBar">

<!--  start of page content -->
<table width="100%" class="edit">
<tr><td><br>
<table class="simple" cellpadding="3" cellspacing="0" border="0">
<tr><th>Program</th>
<th>Admission Date</th>
<th>Status</th></tr>
<tr><td><c:out value="${quatroClientAdmissionForm.admission.programName}"/>
<html:hidden property="admission.programName" />
</td>
<td>
<c:if test="${quatroClientAdmissionForm.admission.admissionDateStr!=null && quatroClientAdmissionForm.admission.admissionStatus!='active'}">
<c:out value="${quatroClientAdmissionForm.admission.admissionDateStr}"/>
</c:if>
</td>
<td><c:out value="${quatroClientAdmissionForm.admission.admissionStatus}"/></td></tr>
</table></td></tr>

<tr><td><br><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Admission Information</th></tr>
</table></div></td></tr>

<tr><td>
<table class="simple" cellspacing="2" cellpadding="3">
<tr><td width="25%">Ontario Resident Status</td>
<td width="25%"><html:text property="admission.residentStatus" maxlength="20" /></td>
<td width="20%">Primary Worker</td>
<td width="30%">
<html:select property="admission.primaryWorker">
<html-el:optionsCollection property="providerList" value="providerNo" label="formattedName"/>
</html:select>
</td></tr>
<tr><td>Locker#</td>
<td><html:text property="admission.lockerNo" maxlength="20"/></td>
<td># of Bags</td>
<td><html:text property="admission.noOfBags" maxlength="20" /></td></tr>
</table>
</td></tr>

<tr><td><br><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Next of Kin</th></tr>
</table></div></td></tr>

<tr><td>
<table class="simple" cellspacing="2" cellpadding="3">
<tr><td width="10%">Name</td>
<td width="25%"><html:text property="admission.nextKinName" maxlength="60" /></td>
<td width="10%">Relationship</td>
<td width="20%"><html:text property="admission.nextKinRelationship" maxlength="20" /></td>
<td width="5%">Tel</td>
<td width="30%"><html:text property="admission.nextKinTelephone" style="width: 95%" maxlength="25" /></td></tr>
<tr><td>Number</td>
<td><html:text property="admission.nextKinNumber" maxlength="20" /></td>
<td>Street</td>
<td colspan="3"><html:text property="admission.nextKinStreet" style="width: 80%" maxlength="50" /></td></tr>
<tr><td>City</td>
<td><html:text property="admission.nextKinCity" maxlength="20" /></td>
<td>Province</td>
<td><html:select property="admission.nextKinProvince">
<html-el:optionsCollection property="provinceList" value="code" label="description"/>
</html:select></td>
<td>Postal</td>
<td><html:text property="admission.nextKinPostal" style="width: 60%" maxlength="7" /></td></tr>
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
<logic:equal name="quatroClientAdmissionForm" property="familyIntakeType" value="Y">
<table class="simple" cellspacing="2" cellpadding="3">
	<tr><th width="20%">Assign Room</th>
	<td>
 	      <html:select property="roomDemographic.id.roomId">
           <html-el:optionsCollection property="availableRooms" value="id" label="name" /> 
          </html:select>
          <html:hidden property="curDB_RoomId"/>
	</td></tr>
	</table>
</logic:equal>
<logic:notEqual name="quatroClientAdmissionForm" property="familyIntakeType" value="Y">
<table class="simple" cellspacing="2" cellpadding="3">
	<tr><th width="20%">Assign Room</th>
	<td>
	<c:choose>
	<c:when test="${quatroClientAdmissionForm.admission.admissionStatus=='active' ||
		 quatroClientAdmissionForm.admission.admissionStatus=='admitted'}">
	      <html:select property="roomDemographic.id.roomId" onchange="javascript: roomChanged();">
           <html-el:optionsCollection property="availableRooms" value="id" label="name" /> 
          </html:select>
          <html:hidden property="curDB_RoomId"/>
    </c:when>
    <c:otherwise>
	      <html:select property="roomDemographic.id.roomId">
           <html-el:optionsCollection property="availableRooms" value="id" label="name" /> 
          </html:select>
          <html:hidden property="curDB_RoomId"/>
    </c:otherwise>
    </c:choose>          
	</td></tr>

	<tr><th width="20%">Assign Bed</th>
	<td><html:select property="bedDemographic.id.bedId">
            <html-el:optionsCollection property="availableBeds" value="id" label="name"/>
	  </html:select>
      <html:hidden property="curDB_BedId"/>
	</td></tr>
	</table>
</logic:notEqual>
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
<%@ include file="/common/readonly.jsp" %>
</html-el:form>
