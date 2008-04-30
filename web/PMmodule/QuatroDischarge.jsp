<%@ include file="/taglibs.jsp" %>
<%@page import="org.oscarehr.PMmodule.model.Admission"%>
<%@ page import="org.oscarehr.PMmodule.model.DischargeReason" %>
<%@page import="java.util.Date"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<script type="text/javascript" src='<c:out value="${ctx}"/>/js/quatroLookup.js'></script>
 
<html-el:form action="/PMmodule/QuatroDischarge.do">
<input type="hidden" name="method"/>
<html:hidden property="program.id" />
<script lang="javascript">
	function submitForm(methodVal) {
		document.forms(0).method.value = methodVal;
		document.forms(0).submit();
	}
</script>
<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
	<tr>
		<th class="pageTitle" align="center"><span
			id="_ctl0_phBody_lblTitle" align="left">Client Management - Discharge</span></th>
	</tr>
	<tr>
		<td align="left" valign="middle" class="buttonBar2">
		&nbsp;<html:link action="/PMmodule/QuatroClientSummary.do" name="actionParam" style="color:Navy;text-decoration:none;">Summary</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroHistory.do" name="actionParam" style="color:Navy;text-decoration:none;">History</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroIntake.do" name="actionParam" style="color:Navy;text-decoration:none;">Intake</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroAdmission.do" name="actionParam" style="color:Navy;text-decoration:none;">Admission</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroRefer.do" name="actionParam" style="color:Navy;text-decoration:none;">Refer</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<b>Discharge</b>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroServiceRestriction.do" name="actionParam" style="color:Navy;text-decoration:none;">Service Restriction</html:link>&nbsp;&nbsp;|&nbsp;&nbsp;
		<html:link action="/PMmodule/QuatroComplaint.do" name="actionParam" style="color:Navy;text-decoration:none;">Complaints</html:link>
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
<tr><td>
	This page is for discharging clients from:
	<ol>
		<li>bed programs to non-CAISI community bed programs</li>
		<li>service programs</li>
		<li>temporary admissions in bed programs</li>
	</ol>
	To discharge clients from a bed program to a CAISI bed program, you must make a referral to that bed program. The client can then be admitted to that bed program from the queue.
</td></tr>

<tr><td><br><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Discharge to non-CAISI community bed program</th></tr>
</table></div></td></tr>

<tr><td>
Community Program:&nbsp;
<select id='community_id'>
	<c:forEach var="communityProgram" items="${communityPrograms}">
		<c:choose>
			<c:when test="${clientManagerForm.map.program.id == communityProgram.id}">
				<option value="<c:out value="${communityProgram.id}"/>" selected="true"><c:out value="${communityProgram.name}" /></option>
			</c:when>
			<c:otherwise>
				<option value="<c:out value="${communityProgram.id}"/>"><c:out value="${communityProgram.name}" /></option>
			</c:otherwise>
		</c:choose>
	</c:forEach>
</select>
<c:if test="${sessionScope.performDischargeBed=='true'}">
&nbsp;<input type="button" value="Discharge" onclick="select_program_community()" />
</c:if>
</td></tr>

<tr><td><br><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Discharge - Service Programs</th></tr>
</table></div></td></tr>

<tr><td>
<display:table class="simple" name="serviceAdmissions" uid="admission" requestURI="/PMmodule/ClientManager.do">
	<display:setProperty name="paging.banner.placement" value="bottom" />
	<display:setProperty name="basic.msg.empty_list" value="This client is not currently admitted to any programs." />

	<c:if test="${sessionScope.performDischargeService=='true'}">
	<display:column sortable="false">
		<input type="button" value="Discharge" onclick="select_program('<c:out value="${admission.programId}"/>')" />
	</display:column>
	</c:if>
	
	<display:column sortable="true" title="Program Name">
		<c:out value="${admission.programName}" />
	</display:column>
	<display:column property="admissionDate" sortable="true" title="Admission Date" />
	<display:column property="admissionNotes" sortable="true" title="Admission Notes" />
</display:table>
</td></tr>

<tr><td><br><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Discharge - Temporary Program</th></tr>
</table></div></td></tr>

<tr><td>
<display:table class="simple" name="temporaryAdmissions" uid="admission" requestURI="/PMmodule/ClientManager.do">
  <display:setProperty name="paging.banner.placement" value="bottom" />
  <display:setProperty name="basic.msg.empty_list" value="This client is not currently admitted to any programs." />
	
  <display:column sortable="false">
	<input type="button" value="Discharge" onclick="select_program('<c:out value="${admission.programId}"/>')" />
  </display:column>
  <display:column sortable="true" title="Program Name">
	<c:out value="${admission.programName}" />
  </display:column>
  <display:column property="admissionDate" sortable="true" title="Admission Date" />
  <display:column property="admissionNotes" sortable="true" title="Admission Notes" />
</display:table>

<c:if test="${requestScope.do_discharge != null}">
	<br />
	<br />
	<table width="100%" border="1" cellspacing="2" cellpadding="3">
	<caisi:isModuleLoad moduleName="ALT_DISCHARGE_REASON" reverse="true">
		<%if(request.getAttribute("nestedReason")!=null && request.getAttribute("nestedReason").equals("true")){%>
		<tr>
            <td width="5%"><html:radio property="admission.radioDischargeReason" value='<%="" + DischargeReason.MEDICAL_NEEDS_EXCEED_PROVISION.ordinal()%>' /></td>
			<td><bean:message  bundle="pmm" key="discharge.reason.MEDICAL_NEEDS_EXCEED_PROVISION"/></td>
		</tr>
		<tr>
			<td width="5%"><html:radio property="admission.radioDischargeReason" value='<%="" + DischargeReason.SOCIAL_BEHAVIOUR_NEEDS_EXCEED_PROVISION.ordinal()%>' /></td>
			<td><bean:message  bundle="pmm" key="discharge.reason.SOCIAL_BEHAVIOUR_NEEDS_EXCEED_PROVISION"/></td>
		</tr>
		<tr>
			<td width="5%"><html:radio property="admission.radioDischargeReason" value='<%="" + DischargeReason.WITHDRAWAL_NEEDS_EXCEED_PROVISION.ordinal()%>' /></td>
			<td><bean:message  bundle="pmm" key="discharge.reason.WITHDRAWAL_NEEDS_EXCEED_PROVISION"/></td>
		</tr>
		<tr>
			<td width="5%"><html:radio property="admission.radioDischargeReason" value='<%="" + DischargeReason.MENTAL_HEALTH_NEEDS_EXCEED_PROVISION.ordinal()%>' /></td>
			<td><bean:message  bundle="pmm" key="discharge.reason.MENTAL_HEALTH_NEEDS_EXCEED_PROVISION"/></td>
		</tr>
		<tr>
			<td width="5%"><html:radio property="admission.radioDischargeReason" value='<%="" + DischargeReason.OTHER_NEEDS_EXCEED_PROVISION.ordinal()%>' /></td>
			<td><bean:message  bundle="pmm" key="discharge.reason.OTHER_NEEDS_EXCEED_PROVISION"/></td>
		</tr>		
		<%}else{ %>
		<tr>
			<td width="5%"><html:radio property="admission.radioDischargeReason" value='<%="" + DischargeReason.REQUIRES_ACUTE_CARE.ordinal()%>' /></td>
			<td><bean:message  bundle="pmm" key="discharge.reason.REQUIRES_ACUTE_CARE"/></td>
		</tr>
		<tr>
			<td width="5%"><html:radio property="admission.radioDischargeReason" value='<%="" + DischargeReason.NOT_INTERESTED.ordinal()%>' /></td>
			<td><bean:message  bundle="pmm" key="discharge.reason.NOT_INTERESTED"/></td>
		</tr>
		<tr>			
			<td width="5%"><html:radio property="admission.radioDischargeReason" value='<%="" + DischargeReason.DOES_NOT_FIT_CRITERIA.ordinal()%>' onclick="nestedReason()" /> </td>
			<td><bean:message  bundle="pmm" key="discharge.reason.DOES_NOT_FIT_CRITERIA"/></td>
		</tr>
		<tr>
			<td width="5%"><html:radio property="admission.radioDischargeReason" value='<%="" + DischargeReason.NO_SPACE_AVAILABLE.ordinal()%>' /></td>
			<td><bean:message  bundle="pmm" key="discharge.reason.NO_SPACE_AVAILABLE"/></td>
		</tr>
		<tr>
			<td width="5%"><html:radio property="admission.radioDischargeReason" value='<%="" + DischargeReason.OTHER.ordinal()%>' /></td>
			<td><bean:message  bundle="pmm" key="discharge.reason.OTHER"/></td>
		</tr>	
		<%} %>
		</caisi:isModuleLoad>
 	    <caisi:isModuleLoad moduleName="SHERBOURNE_HEALTH_CENTER" reverse="true">
			<c:forEach var="dischargeReason" items="${dischargeReasons}">
				<tr>
					<td width="5%"> <html-el:radio  property="admission.radioDischargeReason" value="${dischargeReason.code}" /></td>
					<td><bean:write name="dischargeReason" property="description"/></td>
				</tr>
			</c:forEach>
		</caisi:isModuleLoad>
		<tr class="b">
			<td width="20%">Discharge Notes:</td>
			<td><html:textarea cols="50" rows="7" property="admission.dischargeNotes" /></td>
		</tr>
		<tr class="b">
			<td colspan="2"><input type="button" value="Process Discharge" onclick="do_discharge();" /> <input type="button" value="Cancel" onclick="document.clientManagerForm.submit()" /></td>
		</tr>
	</table>
</c:if>
</td></tr>

</table>

<!--  end of page content -->
</div>
</td>
</tr>
</table>
</html-el:form>
