<%@ include file="/taglibs.jsp"%>
<html>
<head>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<title>Duplicate Client Check</title>
<link rel="stylesheet" type="text/css" href='<html:rewrite page="/css/displaytag.css" />' />
<link rel="stylesheet" type="text/css" href='<html:rewrite page="/css/core.css" />' />
<script language="Javascript">
function selectDuplicateClient(form_name, firstName, firstNameValue, 
   lastName, lastNameValue, sex, sexValue, dob, dobValue, alias, aliasValue, 
   clientNo, clientNoValue, statusMsg, statusMsgValue, 
   newClientChecked,newClientCheckedValue, shortFlag) {
   var myexpr = "opener.document." + form_name + ".elements['" + firstName +"'].value='" + firstNameValue +"'";
   eval(myexpr);
   myexpr = "opener.document." + form_name + ".elements['" + lastName +"'].value='" + lastNameValue + "'";
   eval(myexpr);

   var elSel= window.opener.document.getElementsByName(sex)[0]; 
   for(var i=0;i<elSel.options.length;i++){
      if(sexValue==elSel.options[i].value)
      {
         elSel.options[i].selected=true;
         break;
      }
    }

   myexpr = "opener.document." + form_name + ".elements['" + dob +"'].value='" + dobValue + "'";
   eval(myexpr);
   myexpr = "opener.document." + form_name + ".elements['" + alias +"'].value='" + aliasValue + "'";
   eval(myexpr);
   myexpr = "opener.document." + form_name + ".elements['" + clientNo +"'].value='" + clientNoValue + "'";
   eval(myexpr);

   var statusMsgValue2=statusMsgValue;
   if(shortFlag=="Y"){
     if(statusMsgValue=="(Existing Client)"){
       statusMsgValue2="#";
     }else{
       statusMsgValue2="";
     } 
   }
   myexpr = "opener.document." + form_name + ".elements['" + statusMsg +"'].value='" + statusMsgValue2 + "'";
   eval(myexpr);

   myexpr = "opener.document." + form_name + ".elements['" + newClientChecked +"'].value='" + newClientCheckedValue + "'";
   eval(myexpr);
   
   self.close();
}

function checkExistClients(shortFlagValue){
   var lastName = document.getElementsByName("client.lastName")[0];
   var firstName = document.getElementsByName("client.firstName")[0];
   var dob = document.getElementsByName("client.dob")[0];
   var sex = document.getElementsByName("client.sex")[0];
   var alias = document.getElementsByName("client.alias")[0];
   var url='<c:out value="${ctx}" />/PMmodule/DuplicateClientCheck.do?' +
     "var=" + "<c:out value="${var}"/>" + 
     "&firstName=" + firstName.value + "&lastName=" + lastName.value + 
     "&dob=" + dob.value + "&sex=" + sex.value +
     "&alias=" + alias.value + "&shortFlag=" + shortFlagValue;
   window.location =url;
}

</script>
</head>
<body>
<html:form action="/PMmodule/DuplicateClientCheck.do">
<input type="hidden" name="var" value="<c:out value="${var}"/>" />
<input type="hidden" name="shortFlag" value="<c:out value="${shortFlag}"/>"/>
<table width="100%" class="edit">
<tr><td colspan="4"><br><div class="tabs">
<table cellpadding="3" cellspacing="0" border="0">
<tr><th>Personal information</th></tr>
</table></div></td></tr>
<tr><td width="15%">First name*</td>
<td width="35%"><html-el:text property="client.firstName" size="20" maxlength="30" /></td>
<td width="19%">Gender*</td>
<td width="31%"><html-el:select property="client.sex">
   <html-el:optionsCollection property="genders" value="code" label="description"/>
</html-el:select></td></tr>
<tr><td>Last name*</td><td><html-el:text property="client.lastName" size="20" maxlength="30" /></td>
<td>Date of birth<br>(yyyy/mm/dd)</td><td>
<quatro:datePickerTag property="client.dob" width="65%" openerForm="duplicateClientCheckForm">
</quatro:datePickerTag>
</td></tr>
<tr><td>Alias</td>
<td><html-el:text size="30" maxlength="70" property="client.alias"/></td>
<td>
<a href="javascript:checkExistClients();" style="color:Navy;text-decoration:none;">
<img border="0" src="<html:rewrite page="/images/search16.gif"/>" />&nbsp;Filter&nbsp;&nbsp;
</a>
</td>
<td></td></tr>
</table>

<display:table class="simple" cellspacing="2" cellpadding="3" id="client" name="clients" export="false" pagesize="10" requestURI="/PMmodule/DuplicateClientCheck.do">
  <display:setProperty name="paging.banner.placement" value="bottom" />
  <display:setProperty name="basic.msg.empty_list" value="No clients found." />
  <display:column sortable="false" title="Client No">
    <c:choose>
      <c:when test="${client.demographicNo==0}">
        <a href="javascript:selectDuplicateClient('<c:out value="${formName}"/>',
        '<c:out value="${firstName}"/>', '<c:out value="${client.firstName}"/>',
        '<c:out value="${lastName}"/>', '<c:out value="${client.lastName}"/>',
        '<c:out value="${sex}"/>', '<c:out value="${client.sex}"/>',
        '<c:out value="${dob}"/>', '<c:out value="${client.dob}"/>',
        '<c:out value="${alias}"/>', '<c:out value="${client.alias}"/>',
        '<c:out value="${clientNo}"/>','0',
        '<c:out value="${statusMsg}"/>', '(New Client)',
        '<c:out value="${newClientChecked}"/>', 'Y', '<c:out value="${shortFlag}"/>');">
        New Client</a>
      </c:when>
      <c:otherwise>
        <a href="javascript:selectDuplicateClient('<c:out value="${formName}"/>',
        '<c:out value="${firstName}"/>', '<c:out value="${client.firstName}"/>',
        '<c:out value="${lastName}"/>', '<c:out value="${client.lastName}"/>',
        '<c:out value="${sex}"/>', '<c:out value="${client.sex}"/>',
        '<c:out value="${dob}"/>', '<c:out value="${client.dob}"/>',
        '<c:out value="${alias}"/>', '<c:out value="${client.alias}"/>',
        '<c:out value="${clientNo}"/>','<c:out value="${client.demographicNo}"/>',
        '<c:out value="${statusMsg}"/>', '(Existing Client)',
        '<c:out value="${newClientChecked}"/>', 'N', '<c:out value="${shortFlag}"/>');">
          <c:out value="${client.demographicNo}" /></a>
      </c:otherwise>
    </c:choose>
  </display:column>
  <display:column sortable="false" title="Name">
    <c:choose>
      <c:when test="${client.demographicNo==0}">
        <a href="javascript:selectDuplicateClient('<c:out value="${formName}"/>',
        '<c:out value="${firstName}"/>', '<c:out value="${client.firstName}"/>',
        '<c:out value="${lastName}"/>', '<c:out value="${client.lastName}"/>',
        '<c:out value="${sex}"/>', '<c:out value="${client.sex}"/>',
        '<c:out value="${dob}"/>', '<c:out value="${client.dob}"/>',
        '<c:out value="${alias}"/>', '<c:out value="${client.alias}"/>',
        '<c:out value="${clientNo}"/>','0',
        '<c:out value="${statusMsg}"/>', '(New Client)',
        '<c:out value="${newClientChecked}"/>', 'Y', '<c:out value="${shortFlag}"/>');">
        <c:out value="${client.formattedName}" /></a>
      </c:when>
      <c:otherwise>
        <a href="javascript:selectDuplicateClient('<c:out value="${formName}"/>',
        '<c:out value="${firstName}"/>', '<c:out value="${client.firstName}"/>',
        '<c:out value="${lastName}"/>', '<c:out value="${client.lastName}"/>',
        '<c:out value="${sex}"/>', '<c:out value="${client.sex}"/>',
        '<c:out value="${dob}"/>', '<c:out value="${client.dob}"/>',
        '<c:out value="${alias}"/>', '<c:out value="${client.alias}"/>',
        '<c:out value="${clientNo}"/>','<c:out value="${client.demographicNo}"/>',
        '<c:out value="${statusMsg}"/>', '(Existing Client)',
        '<c:out value="${newClientChecked}"/>', 'N', '<c:out value="${shortFlag}"/>');">
           <c:out value="${client.formattedName}" /></a>
      </c:otherwise>
    </c:choose>
  </display:column>
  <display:column sortable="false" title="Date of Birth">
    <c:if test="${client.yearOfBirth!=null}">
	  <c:out value="${client.dob}" />
    </c:if>
  </display:column>
  <display:column sortable="false" title="Gender"><c:out value="${client.sexDesc}" /></display:column>                        
  <display:column sortable="false" title="Alias"><c:out value="${client.alias}" /></display:column>
</display:table>
</html:form>
</body>
</html>
