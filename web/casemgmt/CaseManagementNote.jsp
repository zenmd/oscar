<%@ include file="../taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/caisi-tag.tld" prefix="caisi"%>

<html>
<head>
<title>Case Management</title>
<c:set var="ctx" value="${pageContext.request.contextPath}"	scope="request" />
<script type="text/javascript" 	src='<c:out value="${ctx}"/>/js/quatroLookup.js'></script>
<!-- link rel="stylesheet" href="<c:out value="${ctx}"/>/css/casemgmt.css"	type="text/css" -->
<script language="JavaScript"	src="<c:out value="${ctx}"/>/jspspellcheck/spellcheck-caller.js"></script>
<script type="text/javascript">

	var flag=<%=request.getAttribute("change_flag")%>; 
	<%
		org.oscarehr.casemgmt.web.formbeans.CaseManagementEntryFormBean form=(org.oscarehr.casemgmt.web.formbeans.CaseManagementEntryFormBean) session.getAttribute("caseManagementEntryForm");
		int size=form.getIssueCheckList().length;	
		if (session.getAttribute("newNote")!=null && "true".equalsIgnoreCase((String)session.getAttribute("newNote")))
	{%>
		var newNote=true;
	<%}else{%>
		var newNote=false;
	<%}	
		if (session.getAttribute("issueStatusChanged")!=null && "true".equalsIgnoreCase((String)session.getAttribute("issueStatusChanged")))
	{%>
		var issueChanged=true;
	<%}else{%>
		var issueChanged=false;
	<%}%>
	
	var issueSize=<%=size%>;
	
	function setChangeFlag(change){
		flag=change;
		document.getElementById("spanMsg").innerHTML="This note has not been saved yet!";
		document.getElementById("spanMsg").style.color="red";
	}
	
	function validateChange(){
		var str="You haven't saved the change yet. Please save first.";
		if (flag==true){
			/*if (confirm(str)) return true;
			else return false;*/
			alert(str);
			return false;
		}
		return true;
	}
	
	function validateBack(){
		var str="You haven't saved the change yet. Please save first.";
		if (flag==true){
			/*if (confirm(str)) return true;
			else return false;*/
			alert(str);
			return false;
		}else{
			return true;
		}
	}
	function validateIssuecheck(){
		var i=0;
		for (i=0;i<issueSize;i++)
		{
			//alert("checked="+document.caseManagementEntryForm.elements["issueCheckList["+i+"].checked"].checked);
			if (document.caseManagementEntryForm.elements["issueCheckList["+i+"].checked"].checked) 
			{
				//alert("issue check return true");
				return true;
			}
		}
		return false;
			
	}
	function validateEnounter(){
		if (document.caseManagementEntryForm.elements["caseNote.encounter_type"].value=="" ||document.caseManagementEntryForm.elements["caseNote.encounter_type"].value==" ")
		{
			return false;
		}else{
		 	return true;
		}	
	}
	
	function validateIssueStatus(){
		var signed=false;
		
		if (document.caseManagementEntryForm.sign.checked) signed=true;
		
		if (newNote==true && signed==true){
			var i=0;
			/*for (i=0;i<issueSize;i++)
			{
				if (document.caseManagementEntryForm.elements["issueCheckList["+i+"].issue.acute"].value=="true") return true;
				if (document.caseManagementEntryForm.elements["issueCheckList["+i+"].issue.certain"].value=="true") return true;
				if (document.caseManagementEntryForm.elements["issueCheckList["+i+"].issue.major"].value=="true") return true;
				if (document.caseManagementEntryForm.elements["issueCheckList["+i+"].issue.resolved"].value=="true") return true;
			}*/
			if (issueChanged==true) return true;
			else return false;
		}
		return true;
	}
	
	function validateSignStatus() {
		if(document.caseManagementEntryForm.sign.checked) 
			return true;
		else 
			return false;
	}
	function isIssueEmpty()
	{
		if(document.caseManagementEntryForm.lstIssue==null ||document.caseManagementEntryForm.lstIssue=="") 
			return true;		
		else 
			return false;
	}
	function validateSave(){
	
		var str1="You cannot save a note when there is no issue checked, please add an issue or check a currently available issue before save." ;
		var str2="Are you sure that you want to sign and save without changing the status of any of the issues?";
		var str3="Please choose encounter type before saving the note.";
		var str4="Are you sure that you want to save without signing?";
		var str5="You cannot save a note when there is no issue, please add an issue before save." ;
		if (!validateEnounter()){
			alert(str3); return false;
		}
		/*
		if (!validateIssuecheck()){
			alert(str1); return false;
		}
		*/
		if(isIssueEmpty()){
			alert(str5);  return false;
		}
		if (!validateSignStatus()){
			if(confirm(str4)) return true;
			else return false;
		}
		/*
		if (!validateIssueStatus())
			if (confirm(str2)) return true;
			else return false;
		*/
		return true;
	}
	

</script>

<script language="JavaScript">

 	function spellCheck()
            {
            
                // Build an array of form elements (not there values)
                var elements = new Array(0);
                
                // Your form elements that you want to have spell checked
                
                elements[elements.length] = document.caseManagementEntryForm.caseNote_note;
                
                       
                // Start the spell checker
                 startSpellCheck('jspspellcheck/',elements);
                
            }
 function getIssueList(){
  	var elSel= document.getElementsByName("lstIssue")[0]; 
  	var txtKey= document.getElementsByName("txtIssueKey")[0]; 
  	var txtValue= document.getElementsByName("txtIssueValue")[0]; 
  	txtKey.value="";
  	txtValue.value="";
  	for(var i=0;i<elSel.options.length;i++){
    	if(txtKey.value==""){
   	    	txtKey.value = elSel.options[i].value;
    	}else{  
       		txtKey.value = txtKey.value + ":" + elSel.options[i].value;
    	}
    
    	if(txtValue.value==""){
      	 	txtValue.value = elSel.options[i].text;
    	}else{  
       	txtValue.value = txtValue.value + ":" + elSel.options[i].text;
    	}
   }
   alert(txtValue.value);
   return true;
}

function removeSel(str) {
    var elSel= document.getElementsByName(str)[0]; 
    if(elSel.selectedIndex>=0){
      elSel.remove(elSel.selectedIndex);
    }else{
      alert("Please select one item to remove.");
    } 
}            

</script>

<script>
var XMLHttpRequestObject = false;

 if(window.XMLHttpRequest) {
        XMLHttpRequestObject = new XMLHttpRequest();
 } else if(window.ActiveXObject) {
        XMLHttpRequestObject = new ActiveXObject("Microsoft.XMLHTTP");
 }
 
 	function autoSave() {
		if(XMLHttpRequestObject) {
			var obj = document.getElementById('caseNote_note');
			XMLHttpRequestObject.open("POST",'<html:rewrite action="/CaseManagementEntry2"/>',true);
            XMLHttpRequestObject.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
            /*
			XMLHttpRequestObject.onreadystatechange = function()
			{
				if(XMLHttpRequestObject.readyState == 4 &&
						XMLHttpRequestObject.status == 200) {
					alert('saved');
                }
			}
			*/                        
			var demographicNo = '<c:out value="${param.demographicNo}"/>';
                        var noteId = '<%=request.getParameter("noteId") != null ? request.getParameter("noteId") : request.getAttribute("noteId") != null ? request.getAttribute("noteId") : ""%>';
			var programId = '<c:out value="${case_program_id}"/>';
			XMLHttpRequestObject.send("method=autosave&demographicNo=" + demographicNo + "&programId=" + programId + "&note_id=" + noteId + "&note="  + escape(obj.value));
						
		}	
		
		setTimer();	
	}
	
	function setTimer() {
		setTimeout("autoSave()", 60000);
	}
	
	function init() {
		setTimer();
		window.opener.location.reload(true);
	}

	function restore() {
		if(confirm('You have an unsaved note from a previous session.  Click ok to retrieve note.')) {
			document.caseManagementEntryForm.method.value='restore';
			document.caseManagementEntryForm.submit();			
		}		
	}	
	function submitForm(methodValue)
	{
		getIssueList();
		document.forms[0].method.value=methodValue;
		document.forms[0].submit();
	}
</script>

</head>
<body onload="init()">
<%
	//get programId
	String pId = (String) session.getAttribute("case_program_id");
	if (pId == null)
		pId = "";
%>
<html:form action="/CaseManagementEntry2"	onsubmit="return getIssueList();">
	<html:hidden property="demographicNo" />
	<c:if test="${param.providerNo==null}">
		<input type="hidden" name="providerNo"	value="<%=session.getAttribute("user")%>">
	</c:if>
	<c:if test="${param.providerNo!=null}">
		<html:hidden property="providerNo" />
	</c:if>
	<input type="hidden" name="caseNote.program_no" value="<%=pId%>" />
	<input type="hidden" name="method" value="save" />
	<c:if test="${param.from=='casemgmt'||requestScope.from=='casemgmt'}">
		<input type="hidden" name="from" value="casemgmt" />
	</c:if>
	<input type="hidden" name="lineId" value="0" />
	<input type="hidden" name="addIssue" value="null" />
	<input type="hidden" name="deleteId" value="0" />
	
	<table width="100%">
		<tr>
			<th class="pageTitle" width="100%">Case Management Note</th>
		</tr>
		<tr>
			<td align="left" class="buttonBar">
			<!-- 
			<span
				style="text-decoration: cursor:pointer;color: blue"
				onclick="document.caseManagementEntryForm.method.value='save';return validateSave();">
			<img border=0 src=<html:rewrite page="/images/Save16.png"/> />&nbsp;Save
			&nbsp;| </span>
			-->
			<a onclick="return validateSave();" href='javascript:submitForm("save");'
			style="color:Navy;text-decoration:none;">
			<img border=0 src=<html:rewrite page="/images/Save16.png"/> />&nbsp;Save&nbsp;&nbsp;</a>
			 <html:link action="/CaseManagementView2.do"
				style="color:Navy;text-decoration:none;">
				<img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Close&nbsp;&nbsp;|
			</html:link></td>
		</tr>
	</table>		
	<table width="100%" class="simple" cellspacing="2" cellpadding="3">
		<tr>
			<th style="width: 20%">Client name</th>
			<td><logic:notEmpty name="demoName" scope="request">
				<c:out value="${requestScope.demoName}" />
			</logic:notEmpty> <logic:empty name="demoName" scope="request">
				<c:out value="${param.demoName}" />
			</logic:empty></td>
		</tr>
		<tr>
			<th>Age</th>
			<td><logic:notEmpty name="demoName" scope="request">
				<c:out value="${requestScope.demoAge}" />
			</logic:notEmpty> <logic:empty name="demoName" scope="request">
				<c:out value="${param.demoAge}" />
			</logic:empty></td>
		</tr>
		<tr>
			<th>DOB</th>
			<td><logic:notEmpty name="demoName" scope="request">
				<c:out value="${requestScope.demoDOB}" />
			</logic:notEmpty> <logic:empty name="demoName" scope="request">
				<c:out value="${param.demoDOB}" />
			</logic:empty></td>
		</tr>
	</table>
	
	<br>
	
	<table cellpadding="3" cellspacing="0" border="0" width="90%">
		<tr>
			<th style="width:40%">Case Status</th>
			<td><html:select property="caseNote.caseStatusId">
				<html:options collection="lstCaseStatus" property="code"
					labelProperty="description"></html:options>
				</html:select>
			</td>
		</tr>
	</table>
		
	<table width="90%" border="0" cellpadding="0" cellspacing="1">
	<tr>
			<th>Components of Service Category</th>
		</tr>
		<tr>
			<td><html:select property="lstIssue" multiple="true" size="5"
				style="width:90%;" onchange="setChangeFlag(true);">
				<html:options collection="lstIssueSelection" property="id"
					labelProperty="description"></html:options>
			</html:select>
			 <html:hidden property="txtIssueKey" value="" />
			 <html:hidden	property="txtIssueValue" value="" />
			</td>
		
		<tr>
			<td class="clsButtonBarText"><a id="issAdd"
				href="javascript:showLookup('ISS', '', '', 'caseManagementEntryForm','lstIssue','', true, '<c:out value="${ctx}"/>')">
			Add</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp; <a
				href="javascript:removeSel('lstIssue')">Remove</a>
			</div>
			</td>
		</tr>
	</table>
	<br>
	
	
	<%
		if ("true".equalsIgnoreCase((String) request.getAttribute("change_flag"))) {
	%>
	<span style="color:red">this note has not been saved yet!</span>
	<%
	} else {
	%>
	<span id="spanMsg" style="color:blue"> <logic:messagesPresent
		message="true">
		<html:messages id="message" message="true" bundle="casemgmt">
			<I><c:out value="${message}" /></I>
		</html:messages>
	</logic:messagesPresent> </span>
	<%
	}
	%>
	<table style="width: 100%">
		<tr>
			<td align="left" class="buttonBar"><span
				style="text-decoration: cursor:pointer;color: blue"
				onclick="javascript:spellCheck();"> <img border=0
				src=<html:rewrite page="/images/Back16.png"/> />&nbsp; Spell Check
			&nbsp;|</span></td>
		</tr>
	</table>
	<table width="90%" border="1">
		<tr>
			<td class="fieldValue" colspan="1"><textarea
				name="caseNote_note" id="caseNote_note" cols="60" rows="20"
				wrap="hard" onchange="setChangeFlag(true);"><nested:write
				property="caseNote.note" /></textarea></td>
			<td class="fieldTitle"></td>

		</tr>

		<tr>
			<td class="fieldTitle">Encounter Type</td>
			<td class="fieldValue"><html:select
				property="caseNote.encounter_type" onchange="setChangeFlag(true);">
				<html:option value=""></html:option>>
				<html:option value="face to face encounter with client">face to face encounter with client</html:option>>
				<html:option value="telephone encounter with client">telephone encounter with client</html:option>>
				<html:option value="encounter without client">encounter without client</html:option>
			</html:select></td>
		</tr>

		<!-- tr>
		<td class="fieldTitle">Billing Code:</td>
		<td class="fieldValue"><html:text property="caseNote.billing_code" /><input type="button" value="search"/></td>
	</tr -->

		<tr>
			<td class="fieldTitle">Sign</td>
			<td class="fieldValue"><html:checkbox property="sign"
				onchange="setChangeFlag(true);" /></td>
		</tr>

		<tr>
			<td class="fieldTitle">include checked issues in note</td>
			<td class="fieldValue"><html:checkbox property="includeIssue"
				onchange="setChangeFlag(true);" /></td>
		</tr>

		<caisi:isModuleLoad moduleName="TORONTO_RFQ" reverse="true">
			<c:if
				test="${param.from=='casemgmt' || requestScope.from=='casemgmt'}">
				<c:url value="${sessionScope.billing_url}" var="url" />
				<caisirole:SecurityAccess accessName="billing" accessType="access"
					providerNo='<%=request.getParameter("providerNo")%>'
					demoNo='<%=request.getParameter("demographicNo")%>'
					programId="<%=pId%>">
					<tr>
						<td class="fieldTitle">Billing:</td>

						<td class="fieldValue"><nested:text
							property="caseNote.billing_code" /> <input type="button"
							value="add billing"
							onclick="self.open('<%=(String)session.getAttribute("billing_url")%>','','scrollbars=yes,menubars=no,toolbars=no,resizable=yes');return false;"></td>
					</tr>
				</caisirole:SecurityAccess>
			</c:if>
		</caisi:isModuleLoad>

		<caisi:isModuleLoad moduleName="casemgmt.note.password.enabled">
			<tr>
				<td class="fieldTitle">Password:</td>
				<td class="fieldValue"><html:password
					property="caseNote.password" /></td>
			</tr>
		</caisi:isModuleLoad>
		 
	</table>
	
</html:form>
</body>
</html>
