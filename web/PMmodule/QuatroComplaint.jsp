<%@ include file="/taglibs.jsp" %>
<%@page import="org.oscarehr.PMmodule.model.Admission"%>
<%@page import="java.util.Date"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<script type="text/javascript" src='<c:out value="${ctx}"/>/js/quatroLookup.js'></script>
 
<html-el:form action="/PMmodule/QuatroComplaint.do">
<input type="hidden" name="method"/>
<script lang="javascript">
	function submitForm(methodVal) {
		document.forms(0).method.value = methodVal;
		document.forms(0).submit();
	}
</script>
<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
	<tr>
		<th class="pageTitle" align="center"><span
			id="_ctl0_phBody_lblTitle" align="left">Client Management - Complaints</span></th>
	</tr>
	<tr>
		<td align="left" valign="middle" class="buttonBar2">
		&nbsp;<html:link action="/PMmodule/QuatroClientSummary.do" name="actionParam" style="color:Navy;text-decoration:none;">Summary</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroHistory.do" name="actionParam" style="color:Navy;text-decoration:none;">History</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroIntake.do" name="actionParam" style="color:Navy;text-decoration:none;">Intake</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroAdmission.do" name="actionParam" style="color:Navy;text-decoration:none;">Admission</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroRefer.do" name="actionParam" style="color:Navy;text-decoration:none;">Refer</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroDischarge.do" name="actionParam" style="color:Navy;text-decoration:none;">Discharge</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroServiceRestriction.do" name="actionParam" style="color:Navy;text-decoration:none;">Service Restriction</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<b>Complaints</b>
		</td>
	</tr>
	<tr>
		<td align="left" class="buttonBar"><a href='javascript:submitForm("close");'
			style="color:Navy;text-decoration:none;">
			<img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Close&nbsp;&nbsp;</a></td>
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
<tr><th>Source of Complaint</th></tr>
</table></div></td></tr>

<tr><td>
<table class="simple" cellspacing="2" cellpadding="3">
<tr><td width="15%">Source of Complaint</td>
<td width="45%"><html-el:select property="complaint.sourceComplaint">
 <html-el:optionsCollection property="sourceComplaintList" value="value" label="label"/>
</html-el:select></td>
<td width="15%">Mathod of Contact</td>
<td width="35%"><html-el:select property="complaint.methodContact">
 <html-el:optionsCollection property="methodContactList" value="value" label="label"/>
</html-el:select></td></tr>
<tr><td>Source's First Name</td>
<td><html-el:text property="complaint.sourceFirstName" /></td>
<td>Source's Last Name</td>
<td><html-el:text property="complaint.sourceLastName" /></td></tr>
</table>
</td></tr>

<tr><td><br><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Complaint Details</th></tr>
</table></div></td></tr>

<tr><td>
<table class="simple" cellspacing="2" cellpadding="3">
<tr><td colspan="2">Is complaint related to Toronto Shelter Standards? <html-el:checkbox property="complaint.shelterStdYN" /></td></tr>
<tr><td colspan="2">If yes, Specify Complaints(identify Section of Toronto Shelter Standards)</td></tr>
<tr><td width="50%"><table class="simple" cellspacing="2" cellpadding="3">
  <tr><td>1. Standards of Organization<br>
  <c:forEach var="organizationStandard" items="${quatroClientComplaintForm.complaint.organizationStandards}">
    <html-el:multibox property="complaint.organizationStandardIds" value="${organizationStandard.value}"/>
      &nbsp;<c:out value="${organizationStandard.label}" /><br/>
  </c:forEach></td></tr>
  <tr><td>2. Access to Shelter<br>
  <c:forEach var="accessToShelter" items="${quatroClientComplaintForm.complaint.accessToShelters}">
    <html-el:multibox property="complaint.accessToShelterIds" value="${accessToShelter.value}"/>
      &nbsp;<c:out value="${accessToShelter.label}" /><br/>
  </c:forEach></td></tr>
  <tr><td>3. Resident Rights and Responsibilities<br>
  <c:forEach var="residentRight" items="${quatroClientComplaintForm.complaint.residentRights}">
    <html-el:multibox property="complaint.residentRightIds" value="${residentRight.value}"/>
      &nbsp;<c:out value="${residentRight.label}" /><br/>
  </c:forEach></td></tr>
  </table>
  </td>
  <td width="50%"><table class="simple" cellspacing="2" cellpadding="3">
  <tr><td>4. Program Standards<br>
  <c:forEach var="programStandard" items="${quatroClientComplaintForm.complaint.programStandards}">
    <html-el:multibox property="complaint.programStandardIds" value="${programStandard.value}"/>
      &nbsp;<c:out value="${programStandard.label}" /><br/>
  </c:forEach></td></tr>
  <tr><td>5. Food Safety and Nutrition Standards <html-el:checkbox property="complaint.foodSafetyNutritionYN" /></td></tr>
  <tr><td>6. Health and Safety Standards<br>
  <c:forEach var="healthSafety" items="${quatroClientComplaintForm.complaint.healthSafetys}">
    <html-el:multibox property="complaint.healthSafetyIds" value="${healthSafety.value}"/>
      &nbsp;<c:out value="${healthSafety.label}" /><br/>
  </c:forEach></td></tr>
  <tr><td>7. Staff Training <html-el:checkbox property="complaint.staffTrainingYN" /></td></tr>
  </table>
  </td></tr>
  
 </table>
 </td></tr>

<tr><td><br><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Description of Complaint/Narrative</th></tr>
</table></div></td></tr>

<tr><td>
<html-el:textarea property="complaint.descComplaint"></html-el:textarea>
</td></tr>

<tr><td><br><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Outcome</th></tr>
</table></div></td></tr>

<tr><td>
<table class="simple" cellspacing="2" cellpadding="3">
  <tr><td width="20%">Was the complaint satisfied with the outcome?</td>
  <td width="30%"><html-el:select property="outcome.outcomeSatisfied" >
   <html-el:optionsCollection property="outcomeSatisfiedList" value="value" label="label"/>
  </html-el:select></td>
  <td width="20%">Was Toronto Shelter Standards Seriously Breached?</td>
  <td width="30%">
  <c:forEach var="radioYN" items="${quatroClientComplaintForm.radioYNs}">
   <html-el:radio property="outcome.standardBreachedYN" value="${radioYN.value}" 
     styleId="${radioYN.label}"/><c:out value="${radioYN.label}" />&nbsp;
  </c:forEach>
  </td></tr>
  <tr><td>Are There Outstanding Service System issues?</td>
  <td>
  <c:forEach var="radioYN" items="${quatroClientComplaintForm.radioYNs}">
   <html-el:radio property="outcome.outstandingIssueYN" value="${radioYN.value}" 
     styleId="${radioYN.label}"/><c:out value="${radioYN.label}" />&nbsp;
  </c:forEach>
  </td>
  <td></td><td></td></tr>
  <tr><td  colspan="4">If yes, Specify Service System Issues:</td></tr>
  <tr><td  colspan="4"><html-el:textarea property="outcome.outstandingIssue"></html-el:textarea></td></tr>

  <tr><td>Complaint Status</td>
  <td>
  <c:forEach var="radioStatus" items="${quatroClientComplaintForm.radioStatuses}">
   <html-el:radio property="outcome.complaintStatus" value="${radioStatus.value}" 
     styleId="${radioStatus.label}"/><c:out value="${radioStatus.label}" />&nbsp;
  </c:forEach>
  </td><td></td><td></td></tr>

  <tr><td>Date Completed</td>
  <td>
    <quatro:datePickerTag property="outcome.dateCompleted" width="70%" openerForm="quatroClientComplaintForm" />
  </td>
  <td>Time Spent on Complaint</td>
  <td><html-el:text property="outcome.timeSpent" /> minutes</td></tr>
 </table>
 </td></tr>

<tr><td>
<table class="simple" cellspacing="2" cellpadding="3">
<tr><td colspna="2"><br></td><td colspan="2"></td></tr>
<tr><td width="15%">Record Reviewed by (Person 1)</td>
<td width="35%"><html-el:text property="outcome.reviewName1" /></td>
<td width="15%">Record Reviewed by (Person 3)</td>
<td width="35%"><html-el:text property="outcome.reviewName3" /></td></tr>

<tr><td>Job Title</td>
<td><html-el:select property="outcome.reviewTitle1" >
   <html-el:optionsCollection property="titleList" value="value" label="label"/>
  </html-el:select>
</td>
<td>Job Title</td>
<td><html-el:select property="outcome.reviewTitle3" >
   <html-el:optionsCollection property="titleList" value="value" label="label"/>
  </html-el:select>
</td></tr>

<tr><td>date Reviewed</td>
<td>
 <quatro:datePickerTag property="outcome.reviewDate1" width="70%" openerForm="quatroClientComplaintForm" />
</td>
<td>date Reviewed</td>
<td>
 <quatro:datePickerTag property="outcome.reviewDate3" width="70%" openerForm="quatroClientComplaintForm" />
</td></tr>

<tr><td>Record Reviewed by (Person 2)</td>
<td><html-el:text property="outcome.reviewName2" /></td>
<td>Record Reviewed by (Person 4)</td>
<td><html-el:text property="outcome.reviewName4" /></td></tr>

<tr><td>Job Title</td>
<td><html-el:select property="outcome.reviewTitle2" >
   <html-el:optionsCollection property="titleList" value="value" label="label"/>
  </html-el:select>
</td>
<td>Job Title</td>
<td><html-el:select property="outcome.reviewTitle4" >
   <html-el:optionsCollection property="titleList" value="value" label="label"/>
  </html-el:select>
</td></tr>

<tr><td>date Reviewed</td>
<td>
 <quatro:datePickerTag property="outcome.reviewDate2" width="70%" openerForm="quatroClientComplaintForm" />
</td>
<td>date Reviewed</td>
<td>
 <quatro:datePickerTag property="outcome.reviewDate4" width="70%" openerForm="quatroClientComplaintForm" />
</td></tr>

</table>
</td></tr>

</table>

<!--  end of page content -->
</div>
</td>
</tr>
</table>
</html-el:form>
