<!-- 

Source:web/PMmodule/QuatroComplaint.jsp 

-->

<%@ include file="/taglibs.jsp"%>
<%@page import="org.oscarehr.PMmodule.model.Admission"%>
<%@page import="java.util.Date"%>

<%@page import="com.quatro.model.Complaint"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"
	scope="request" />
<script type="text/javascript"
	src='<c:out value="${ctx}"/>/js/quatroLookup.js'></script>
<script lang="javascript">

	function txtAreaLenChecker(obj, maxLen) {
	   //counting each end of line as two characters
	   
		var v = obj.value;
	
		var len = v.length;
		
		if(len > maxLen){
			alert("Length of this field can not exceed " + maxLen + " characters.");
			obj.value = v.substr(0, maxLen);
	   }
	
	}
	
	function submitForm(methodVal) {
		//alert("method=" + methodVal);
	 	getScrollPosition();
	 	document.forms[0].method.value = methodVal;
		document.forms[0].submit();
	}
	
	function setOutstanding(arg){
		//alert("Outstanding state="+arg);
	}
	
	function setStandard(obj){
		//alert(obj.checked);
		var boxes = document.getElementsByName("complaint.standards1");
		for(var i = 0; i < boxes.length; i++ ){
			if(obj.checked == true){
				boxes[i].disabled = false;
			}else{
				boxes[i].disabled = true;
			}
			
		}
	}
	function init() {
		
		var obj = document.getElementsByName("isStandards")[0];
		
		setStandard(obj);
	}
	window.onload = init;
	
</script>

<html-el:form action="/PMmodule/QuatroComplaint.do">
	<input type="hidden" name="method" />
	<input type="hidden" name="clientId" value="<c:out value="${clientId}"/>" />
	<html-el:hidden property="complaint.clientId"  />
	<html-el:hidden property="complaint.id"  />
	<html-el:hidden property="complaint.createdDatex"  />
    <input type="hidden" name="scrollPosition" value='<c:out value="${scrPos}"/>' />
	

<%
		
	String length = request.getAttribute("ComplaintForm_length").toString();
	
 %>
	<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
		<tr>
			<th class="pageTitle" align="center">Client Management - Complaints</th>
			
		</tr>
		<tr>
			<td class="simple" style="background: lavender"><%@ include file="ClientInfo.jsp" %></td>
		</tr>
		<tr>
			<td align="left" class="buttonBar"><html:link
				action="/PMmodule/QuatroComplaint.do" name="actionParam"
				style="color:Navy;text-decoration:none;">
				<img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Close&nbsp;&nbsp;</html:link>
			<a href="javascript:submitForm('save');"
				style="color:Navy;text-decoration:none;">
				<img border=0 src=<html:rewrite page="/images/Save16.png"/> />&nbsp;Save&nbsp;&nbsp;</a>
				</td>
		</tr>
		
		
		<tr>
			<td align="left" class="message">
			
				<logic:messagesPresent
					message="true">
					<br />
					<html:messages id="message" message="true" bundle="pmm">
						<c:out escapeXml="false" value="${message}" />
					</html:messages>
					<br />
				</logic:messagesPresent>
			</td>
		</tr>
		<tr>
			<td height="100%">
			<div
				style="color: Black; background-color: White; border-width: 1px; border-style: Ridge;
                    height: 100%; width: 100%; overflow: auto;" id="scrollBar">

<!--  start of page content -->

			<table width="100%" class="edit">
				<tr>
					<td>
						<br />
						<div class="tabs">
							<table cellpadding="3" cellspacing="0" border="0">
								<tr>
									<th>Source of Complaint</th>
								</tr>
							</table>
						</div>
					</td>
				</tr>

				<tr>
					<td>
						<table class="simple" cellspacing="2" cellpadding="3">
							<tr>
								<td width="15%">Source of Complaint</td>
								<td width="45%"><html-el:select
									property="complaint.source">
									<html-el:optionsCollection property="sources"
										value="code" label="description" />
								</html-el:select></td>
								<td width="15%">Method of Contact</td>
								<td width="35%"><html-el:select
									property="complaint.method">
									<html-el:optionsCollection property="methods"
										value="code" label="description" />
								</html-el:select></td>
							</tr>
							<tr>
								<td>Source's First Name</td>
								<td><html-el:text property="complaint.firstname" maxlength="30"/></td>
								<td>Source's Last Name</td>
								<td><html-el:text property="complaint.lastname" maxlength="30"/></td>
							</tr>
							<tr>
								<td>Program</td>
								<td colspan="3"><html:select property="complaint.programId"	name="quatroClientComplaintForm">
										<html:optionsCollection property="programs" value="id"
											label="name" />
									</html:select>
								</td>
							</tr>	
						</table>
					</td>
				</tr>
										
				<tr>
					<td><br>
						<div class="tabs">
							<table cellpadding="3" cellspacing="0" border="0">
								<tr>
									<th>Complaint Details</th>
								</tr>
							</table>
						</div>
					</td>
				</tr>

				<tr>
					<td>
						<table class="simple" cellspacing="2" cellpadding="3">
							<tr>
								<td colspan="2">Is complaint related to Toronto Shelter	Standards? 
									<html-el:checkbox name="quatroClientComplaintForm" property="isStandards"  onclick="setStandard(this)" />
								</td>
							</tr>
							<tr>
								<td colspan="2">If yes, Specify Complaints (Identify Section
								of Toronto Shelter Standards)</td>
							</tr>
	
	
							<tr>
								<td>
									<table width="100%">
									<c:forEach var="codeVal" items="${quatroClientComplaintForm.sections}" varStatus="index">
										<tr>
											<td>
												<logic:equal value="0" property="parentCode" name="codeVal">
													<b><c:out value="${codeVal.description}"></c:out></b>
												</logic:equal>
												<logic:notEqual value="0" property="parentCode"  name="codeVal">
														&nbsp;&nbsp;&nbsp;<html-el:multibox property="complaint.standards1" value="${codeVal.code}" /><c:out value="${codeVal.description}" />
												</logic:notEqual>
											</td>
										</tr>
									<c:if test="${index.count==ComplaintForm_length}">
									</table>
								</td>
								<td>
									<table width="100%">
                                    </c:if>
									</c:forEach>
									</table>
								</td>
							<tr>
							
							
						</table>
					</td>
				</tr>

				<tr>
					<td><br>
						<div class="tabs">
							<table cellpadding="3" cellspacing="0" border="0">
								<tr>
									<th>Description of Complaint/Narrative</th>
								</tr>
							</table>
						</div>
					</td>
				</tr>

				<tr>
					<td><html-el:textarea property="complaint.description" rows="10" style="width:500px;" ></html-el:textarea>
					</td>
				</tr>

				<tr>
					<td><br>
						<div class="tabs">
							<table cellpadding="3" cellspacing="0" border="0">
								<tr>
									<th>Outcome</th>
								</tr>
							</table>
						</div>
					</td>
				</tr>

				<tr>
					<td>
						<table class="simple" cellspacing="2" cellpadding="3">
							<tr>
								<td width="20%">Was the complainant satisfied with the outcome?</td>
								<td width="30%"><html-el:select
									property="complaint.satisfiedWithOutcome">
									<html-el:optionsCollection property="outcomes"
										value="code" label="description" />
								</html-el:select></td>
								<td width="50%" colspan="2">Was Toronto Shelter Security Standards Breached?
									<input type="radio" name="complaint.standardsBreached" value="1">Yes</input>
									<input type="radio" name="complaint.standardsBreached" value="0">No</input>
								</td>
							</tr>
							<tr>
								<td colspan="4">Are There Outstanding Service System Issues?
									<font size="1">(Please specify below)</font><!-- 
									<input type="radio" name="OutstandingChk" onclick="setOutstanding('1')" value="1">Yes</input>
									<input type="radio" name="OutstandingChk" onclick="setOutstanding('0')" value="0">No</input>
									 -->
								</td>
															
							</tr>
							<div id="IssuesId">
								<tr>
									<td colspan="4">Specify Service System Issues:</td>
								</tr>
								<tr>
									<td colspan="4"><html-el:textarea style="width:500px;" rows="3"
										property="complaint.outstandingIssues" onkeyup="javascript:txtAreaLenChecker(this, 4000);"></html-el:textarea></td>
								</tr>
							</div>
	
							<tr>
								<td colspan="4">Complaint Status
									<html-el:radio property="complaint.status" value="1">Completed</html-el:radio>
									<html-el:radio property="complaint.status" value="0">In Progress</html-el:radio>
								</td>
								
							</tr>
	
							<tr>
								<td>Date Completed</td>
								<td><quatro:datePickerTag property="complaint.completedDatex"
									width="70%" openerForm="quatroClientComplaintForm" /></td>
								<td>Time Spent on Complaint</td>
								<td><html-el:text property="complaint.duration" maxlength="10"/> minutes</td>
							</tr>
							<tr><td colspan="4"><br /></td></tr>
							<tr>
								<td width="15%">Record Reviewed by (Person 1)</td>
								<td width="35%"><html-el:text property="complaint.person1" maxlength="60"/></td>
								<td width="15%">Record Reviewed by (Person 3)</td>
								<td width="35%"><html-el:text property="complaint.person3" maxlength="60"/></td>
							</tr>
	
							<tr>
								<td>Job Title</td>
								<td><html-el:text property="complaint.title1" maxlength="20"/></td>
								<td>Job Title</td>
								<td><html-el:text property="complaint.title3" maxlength="20"/></td>
							</tr>
	
							<tr>
								<td>Date Reviewed</td>
								<td><quatro:datePickerTag property="complaint.date1x"
									width="70%" openerForm="quatroClientComplaintForm" /></td>
								<td>Date Reviewed</td>
								<td><quatro:datePickerTag property="complaint.date3x"
									width="70%" openerForm="quatroClientComplaintForm" /></td>
							</tr>
	
							<tr>
								<td>Record Reviewed by (Person 2)</td>
								<td><html-el:text property="complaint.person2" maxlength="60"/></td>
								<td>Record Reviewed by (Person 4)</td>
								<td><html-el:text property="complaint.person4" maxlength="60"/></td>
							</tr>
	
							<tr>
								<td>Job Title</td>
								<td><html-el:text property="complaint.title2" maxlength="20"/></td>
								<td>Job Title</td>
								<td><html-el:text property="complaint.title4" maxlength="20"/></td>
							</tr>
	
							<tr>
								<td>Date Reviewed</td>
								<td><quatro:datePickerTag property="complaint.date2x"
									width="70%" openerForm="quatroClientComplaintForm" /></td>
								<td>Date Reviewed</td>
								<td><quatro:datePickerTag property="complaint.date4x"
									width="70%" openerForm="quatroClientComplaintForm" /></td>
							</tr>
	
						</table>
					</td>
				</tr>

			</table>

<!--  end of page content -->
			
			</div>
			</td>
		</tr>
	</table>
	<%@ include file="/common/readonly.jsp" %>
</html-el:form>
