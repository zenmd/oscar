<%@ include file="/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/quatro-tag.tld" prefix="quatro"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"
	scope="request" />
<script type="text/javascript"
	src='<c:out value="${ctx}"/>/js/quatroLookup.js'></script>
	
<html-el:form action="/PMmodule/QuatroServiceRestriction.do">

	<script lang="javascript">

	function resetClientFields() {
		var form = document.serviceRestrictionForm;
		form.elements['program.name'].value='';
	}

	 function popupProgramSearch(clientId) {
        var page = '<html:rewrite action="/PMmodule/QuatroProgramSearch.do?formName=serviceRestrictionForm&formElementId=selectedProgramId&clientId=" />'+clientId;       
        var windowprops = "height=600,width=800,location=no,"
                + "scrollbars=yes,menubars=no,toolbars=no,resizable=yes,top=10,left=0,statusbar=yes";
        // alert("page name" +page);
        var popup = window.open(page, "_blank", windowprops);
        if (popup != null) {
            if (popup.opener == null) {
                popup.opener = self;
            }
            popup.focus();
        }
    }	
    
    
    function submitForm(methodVal) {
    
	    if(validateLength()){
			document.forms[0].method.value = methodVal;
			document.forms[0].submit();
		}
	}
	
	function validateLength() {
		var length = document.getElementsByName("serviceRestrictionLength")[0];
		var maxLengthStr = document.getElementsByName("maxLength")[0].value;
		var maxLength = parseInt( maxLengthStr );
		
		if(isNaN(length.value)){
			length.value="";
			alert("Please enter digital characters for 'Length of restriction' only.");
			return false;
		}  
	
		if(length.value < 1 ){
			length.value="";
			alert("'Length of restriction' must be greater or equal 1 day.");
			return false;
		}
		
		if(maxLength > 0 && length.value > maxLength){
			length.value="";
			alert("'Length of restriction' must be less or equal " + maxLength + " days.");
			return false;
		}
		
		
		return true;
    }	
    
    function programFilter(){
//       var method= document.getElementsByName("method")[0];
//       method.value='edit';
       
		document.forms[0].action = '<html:rewrite action="/PMmodule/QuatroServiceRestriction.do" />';
		document.forms[0].method.value = "edit";
		serviceRestrictionForm.submit();
		
    }
    
</script>
<% String s = "debug"; %>
	<input type="hidden" name="clientId"
		value="<c:out value='${clientId}'/>" />
	<input type="hidden" name="method" value="save" />
	<input type="hidden" name="ruleCheck" value="N" />
	<html:hidden property="serviceRestriction.demographicNo" />
	<html:hidden property="serviceRestriction.id" />
	<input type="hidden" name="scrollPosition" value='<c:out value="${scrPos}"/>' />

	<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
		<tr>
			<th class="pageTitle" align="center">Client Management - Service
			Restriction</th>
		</tr>
		<tr>
			<td class="simple" style="background: lavender"><%@ include
				file="ClientInfo.jsp"%></td>
		</tr>
		<tr>
			<td align="left" class="buttonBar2">
				<html:link action="/PMmodule/QuatroServiceRestriction.do"
				name="actionParam" style="color:Navy;text-decoration:none;">
				<img style="vertical-align: middle" border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Back to Service Restrictions&nbsp;&nbsp;|</html:link>
         <c:if test="${serviceObjStatus!='completed'}">				
			<a
				href="javaScript:submitForm('save')"
				style="color:Navy;text-decoration:none;"> <img style="vertical-align: middle" border=0
				src=<html:rewrite page="/images/Save16.png"/> />&nbsp;Save&nbsp;&nbsp;</a>
         </c:if>				
</td>
		</tr>
		<tr>
			<td align="left" class="message"><logic:messagesPresent
				message="true">
				<html:messages id="message" message="true" bundle="pmm">
					<c:out escapeXml="false" value="${message}" />
				</html:messages>
			</logic:messagesPresent></td>
		</tr>
		<tr height="100%">
			<td>
			<div
				style="color: Black; background-color: White; border-width: 1px; border-style: Ridge;
                    height: 100%; width: 100%; overflow: auto;" id="scrollBar">

			<!--  start of page content -->
			<table width="100%">
				<tr>
					<td><br>
					<div class="tabs">
					<table cellpadding="3" cellspacing="0" border="0">
						<tr>
							<th>Service Restrictions</th>
						</tr>
					</table>
					</div>
					</td>
				</tr>

				<tr>
					<td>
					<table wdith="100%" class="edit">
						<tr>
							<td width="35%">Program</td>
							<td width="65%"><html:select onchange="programFilter();"
								property="serviceRestriction.programId">
								<html:options collection="allPrograms" property="id"
									labelProperty="name" />
							</html:select></td>
						</tr>
						<tr>
							<td width="35%">Reason for service restriction</td>
							<td width="65%"><html:select
								property="serviceRestriction.commentId">
								<c:forEach var="restriction" items="${serviceRestrictionList}">
									<html-el:option value=""></html-el:option>
									<html-el:option value="${restriction.code}">
										<c:out value="${restriction.description}" />
									</html-el:option>
								</c:forEach>
							</html:select></td>
						</tr>
						<!-- tr>
							<td width="35%">Start Date</td>
							<td width="65%"><quatro:datePickerTag
								property="serviceRestriction.startDateStr" width="120px"
								openerForm="serviceRestrictionForm"></quatro:datePickerTag></td>
						</tr-->
						<tr>
							<td width="35%">Length of restriction (in days)</td>
							<td width="65%"><html:text size="12" maxlength="11"	property="serviceRestrictionLength" /> 
								<html:hidden property="maxLength" />
								<html:hidden property="serviceRestriction.startDateStr" />
								
								<!--  
								(from 1 to 
								<logic:equal name="serviceRestrictionForm" property="maxLength" value="0">
									Unlimit
								</logic:equal>
								<logic:notEqual name="serviceRestrictionForm" property="maxLength" value="0">
									<bean:write name="serviceRestrictionForm" property="maxLength" />
								</logic:notEqual>
								)-->
								</td>
						</tr>
						<tr>
							<td width="35%">Notes</td>
							<td width="65%"><html:textarea cols="80" rows="10"
								property="serviceRestriction.notes" /> 
							</td>
						</tr>
						
					</table>
					</td>
				</tr>
			</table>
			<!--  end of page content --></div>
			</td>
		</tr>
	</table>
	<%@ include file="/common/readonly.jsp" %>
</html-el:form>
