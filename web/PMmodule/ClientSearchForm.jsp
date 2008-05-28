<%@ include file="/taglibs.jsp"%>
<%@page import="oscar.OscarProperties"%>
<%@page import="org.oscarehr.PMmodule.web.utils.UserRoleUtils"%>

<%@ taglib uri="/WEB-INF/caisi-tag.tld" prefix="caisi"%>
<%@ taglib uri="/WEB-INF/security.tld" prefix="security"%>
<%
if(session.getAttribute("userrole") == null )  response.sendRedirect("../logout.jsp");
    String roleName$ = (String)session.getAttribute("userrole") + "," + (String) session.getAttribute("user");
    String url = "/CaseManagementView2.do";
    if(!"cv".equals(session.getAttribute(com.quatro.common.KeyConstants.SESSION_KEY_CURRENT_FUNCTION))) {
    	url ="/PMmodule/QuatroClientSummary.do";
    }
%>

<script>
	function resetClientFields() {
		var form = document.clientSearchForm2;
		form.elements['criteria.demographicNo'].value='';
		form.elements['criteria.firstName'].value='';
		form.elements['criteria.lastName'].value='';
		form.elements['criteria.dob'].value='';
		// form.elements['criteria.healthCardNumber'].value='';
		// form.elements['criteria.healthCardVersion'].value='';
		// form.elements['criteria.searchOutsideDomain'].checked = true;
		// form.elements['criteria.searchUsingSoundex'].checked = true;
		// form.elements['criteria.dateFrom'].value=''; 
		// form.elements['criteria.dateTo'].value=''; 
		form.elements['criteria.bedProgramId'].selectedIndex = 0;
		form.elements['criteria.assignedToProviderNo'].selectedIndex = 0;
		form.elements['criteria.active'].selectedIndex = 0;
		form.elements['criteria.gender'].selectedIndex = 0;
	}

	function popupHelp(type) {
		alert('not yet implemented... will show term definitions');
	}
	function submitForm(methodVal) {
		document.forms[0].method.value = methodVal;
		document.forms[0].submit();
	}
</script>
<html:form	action="/PMmodule/ClientSearch2.do">
	<input type="hidden" name="method"  />
	<table width="100%" height="100%"   cellpadding="1px" cellspacing="1px">
		<tr>
			<th class="pageTitle">Client Search<c:out value="${moduleName}"/></th>
		</tr>
		<tr>
		<td class="buttonBar" align="left" height="18px"><html:link
			action="/PMmodule/QuatroIntakeEdit.do?method=create&intakeId=0&clientId=0"
			style="color:Navy;text-decoration:none;">
			<img border=0 src=<html:rewrite page="/images/New16.png"/> height="16px" width="16px"/>&nbsp;New Client&nbsp;&nbsp;|</html:link>
		<a href="javascript:submitForm('search')" style="color:Navy;text-decoration:none;">
		<img border=0 src=<html:rewrite page="/images/search16.gif"/> height="16px" width="16px"/>&nbsp;Search&nbsp;&nbsp;|</a>
		<a style="color:Navy;text-decoration:none;" href="javascript:resetClientFields();">
		<img border=0 src=<html:rewrite page="/images/searchreset.gif" /> height="16px" width="16px"/>&nbsp;Reset&nbsp;&nbsp;</a>
		<html:link
			action="/PMmodule/MergeClient.do" style="color:Navy;text-decoration:none;">
			<img border=0 src=<html:rewrite page="/images/searchreset.gif" /> height="16px" width="16px"/>&nbsp;Merge Client&nbsp;&nbsp;|</html:link>
		
		</td>
		</tr>
	<tr> <td>	
	<div id="projecthome" class="app">
		<div class="axial">
			<table border="0" cellspacing="2" cellpadding="3">
				<tr>
					<th><bean-el:message key="ClientSearch.clientNo" bundle="pmm"/></th>
					<td><html:text property="criteria.demographicNo" size="15" /></td>
				</tr>
				<tr>
					<th><bean-el:message key="ClientSearch.firstName"  bundle="pmm"/></th>
					<td><html:text property="criteria.firstName" size="15" /></td>
				</tr>
		
				<tr>
					<th><bean-el:message key="ClientSearch.lastName"  bundle="pmm"/>
					</th>
					<td><html:text property="criteria.lastName" size="15" /></td>
				</tr>
				
				<tr>
					<th><bean-el:message key="ClientSearch.dateOfBirth"  bundle="pmm"/> <br>
					(yyyy/mm/dd)</th>
					<td>
					<quatro:datePickerTag property="criteria.dob" openerForm="clientSearchForm2"></quatro:datePickerTag>
					</td>
				</tr>
				<tr>
					<th> <bean-el:message key="ClientSearch.active" bundle="pmm"/></th>
					<td> <html:select property="criteria.active">
							<html:option value="">Any</html:option>
							<html:option value="1">Yes</html:option>
							<html:option value="0">No</html:option>
						</html:select>
					</td>
				</tr>
				<tr>
					<th><bean-el:message key="ClientSearch.gender" bundle="pmm"/></th>
					<td>
						<html-el:select property="criteria.gender">
							<html-el:option value="">Any</html-el:option>
							<c:forEach var="gen" items="${genders}">
								<html-el:option value="${gen.code}"><c:out value="${gen.description}"/></html-el:option>
							</c:forEach>
						</html-el:select>
					</td>
				</tr>
				<caisi:isModuleLoad moduleName="TORONTO_RFQ" reverse="true">			
				<caisi:isModuleLoad moduleName="GET_OHIP_INFO" reverse="false">
				<tr>
					<th>Health Card Number</th>
					<td><html:text property="criteria.healthCardNumber" size="15" /> <html:text property="criteria.healthCardVersion" size="2" /></td>
				</tr>
				</caisi:isModuleLoad>

				<!--  <th>Search outside of domain <a href="javascript:void(0)" onclick="popupHelp('domain')">?</a></th>
				-->			
				<tr>
					<caisi:isModuleLoad moduleName="pmm.client.search.outside.of.domain.enabled" >
					<th>Search all clients <a href="javascript:void(0)" onclick="popupHelp('domain')">?</a></th>
						<td><html:checkbox property="criteria.searchOutsideDomain" /></td>
					</caisi:isModuleLoad>					
				</tr>
				
				<tr>
					<th>Soundex on names <a href="javascript:void(0)" onclick="popupHelp('soundex')">?</a></th>
					<td><html:checkbox property="criteria.searchUsingSoundex" /></td>
				</tr>
				</caisi:isModuleLoad>
				<tr>
					<th><bean-el:message key="ClientSearch.assignedTo"  bundle="pmm"/> </th>
			          <td>
			            <html:select property="criteria.assignedToProviderNo">
			                <html:option value="">
			                </html:option>
			              	<html:options collection="allProviders" property="providerNo" labelProperty="fullName" />
			            </html:select>
			          </td>
				</tr>
				<tr>
					<th><bean-el:message key="ClientSearch.program"  bundle="pmm"/> </th>
			          <td>
			            <html:select property="criteria.bedProgramId">
			                <html:option value="">
			                </html:option>
			                <html:option value="MyP">My Programs
			                </html:option>
			              	<html:options collection="allBedPrograms" property="id" labelProperty="name" />
			            </html:select>
			          </td>
				</tr>
				<caisi:isModuleLoad moduleName="TORONTO_RFQ" reverse="true">			
				<tr>
					<th>Admission Date From<br>
						(yyyy/mm/dd)</th>
					<td><html:text property="criteria.dateFrom" size="12" /></td>
				</tr>
				<tr>
					<th>Admission Date To<br>
						(yyyy/mm/dd)</th>
					<td><html:text property="criteria.dateTo" size="12" /></td>
				</tr>
				</caisi:isModuleLoad>
			</table>
		</div>
	</div>  
	</td>
	</tr>
</html:form>
	<c:if test="${requestScope.clients != null}">
         <form method="post" name="clientSearchForm2" action="/PMmodule/ClientSearch2.do">
            <tr height="100%">
                <td>
                    <div style="color: Black; background-color: White; border-style: ridge; border-width: 1px;
                        width: 100%; height: 100%; overflow: auto">
            <display:table class="simple" cellspacing="2" cellpadding="3" id="client" name="clients" export="false" pagesize="100" requestURI="/PMmodule/ClientSearch2.do">
			<display:setProperty name="paging.banner.placement" value="bottom" />
			<display:setProperty name="basic.msg.empty_list" value="No clients found." />
			<display:column sortable="true" title="Client No">
                 <a href="<html:rewrite action="<%=url%>"/>?clientId=<c:out value="${client.currentRecord}"/>"><c:out value="${client.demographicNo}" /></a>
            </display:column>
			<display:column sortable="true" title="Name">
                 <a href="<html:rewrite action="<%=url%>"/>?clientId=<c:out value="${client.currentRecord}"/>"><c:out value="${client.formattedName}" /></a>
			</display:column>
			<display:column sortable="true" title="Date of Birth">
				<c:out value="${client.yearOfBirth}" />/<c:out value="${client.monthOfBirth}" />/<c:out value="${client.dateOfBirth}" />
			</display:column>
			<display:column sortable="true" title="Gender">
				<c:out value="${client.sexDesc}" />
			</display:column>                        
			<display:column sortable="true" title="Active">
				<logic:equal value="0" property="activeCount" name="client">No</logic:equal>
				<logic:notEqual value="0" property="activeCount" name="client">Yes</logic:notEqual>
			</display:column>                        
			<display:column sortable="true" title="H&S Alert">
				<logic:equal value="0" property="hsAlertCount" name="client">No</logic:equal>
				<logic:notEqual value="0" property="hsAlertCount" name="client">Yes</logic:notEqual>
			</display:column>                         
		</display:table>
		</div>
		</td>
		</tr>
		</table>       
        </form>
       </c:if>
        
           
        

   
            