<!-- Source:web/PMmodule/Admin/Role/Functions.jsp -->

<%@ include file="/taglibs.jsp"%>

<bean:define id="lstFUNSelection" name="secroleForm"
	property="funSelectionList" />
<bean:define id="accessTypes" name="secroleForm" property="accessTypes"
	type="com.quatro.model.LookupCodeValue" />

<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
	<tr>
		<th class="pageTitle" align="center"><span
			id="_ctl0_phBody_lblTitle" align="left">Role Management</span></th>
	</tr>
	<tr>
		<td align="left" class="buttonBar2"><html:link
			action="/PMmodule/Admin/RoleManager.do"
			style="color:Navy;text-decoration:none;">
			<img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Back to Role List&nbsp;&nbsp;|</html:link>
		<html:link href="javascript:submitForm('saveFunction');"
			onclick="javascript:getFunctionsList();"
			style="color:Navy;text-decoration:none;">
			<img border=0 src=<html:rewrite page="/images/Save16.png"/> />&nbsp;Save&nbsp;&nbsp;|</html:link>
		</td>

	</tr>
	<tr>
		<td align="left"></td>
	</tr>
	<tr>
		<td height="100%">
		<div
			style="color: Black; background-color: White; border-width: 1px; border-style: Ridge;
                    height: 100%; width: 100%; overflow: auto;" id="scrollBar">

		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<th align="left"  colspan="5">
				Add Functions to Role
				</th>
			</tr>


		</table>

		<html:form action="/PMmodule/Admin/RoleManager" method="post">
			<html:hidden property="method" value="save" />
			<input id="pageChanged" value='<c:out value="${pageChanged}"/>
			<table>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td>Role Name:</td>
					<td><html:text property="roleName" readonly="true"
						style="border: none" /></td>
				</tr>				
				<tr>
					<td>Description:</td>
					<td><html:text property="description" readonly="true"
						style="border: none" /></td>
				</tr>				
				<tr>
					<td>Functions:</td>
					<td width="650px">
					<table>

						<tr>
							<td align="left" class="message"><logic:messagesPresent
								message="true">
								<html:messages id="message" message="true" bundle="pmm">
									<c:out escapeXml="false" value="${message}" />
								</html:messages>
							</logic:messagesPresent></td>
						</tr>
					</table>
					<br />
					<TABLE align="center" class="simple" width="100%">


						<thead>
							<TR>
								<th align="center"><b>Select</b></th>
								<th align="center"><b>Function</b></TH>
								<TH align="center"><b>AccessType</b></TH>
							</TR>
						</thead>

						<logic:iterate id="secobjprivilege" name="secroleForm"
							property="secobjprivilegeLst" indexId="rIndex">
							<TR>
								<TD align="center" width="50px"><input type="checkbox"
									name="p<%=String.valueOf(rIndex)%>" value="" /> <input
									type="hidden" name="lineno" value="<%=String.valueOf(rIndex)%>" /></TD>

								<TD width="350px">


								<table cellpadding="0" style="border:0px;" cellspacing="0"
									width="100%">
									<tr>
										<td style="border:0px;" width="1px"><input
											style="width:100%;" type="text"
											name="function_code<%=String.valueOf(rIndex)%>"
											value='<c:out value="${secobjprivilege.objectname_code}"/>'></td>
										<td style="border:0px;" width="100%"><input
											style="width:100%;" type="text"
											name="function_description<%=String.valueOf(rIndex)%>"
											value='<c:out value="${secobjprivilege.objectname_desc}"/>'
											readonly></td>
										<td style="border:0px;" width="35px"><a
											onclick="showLookup('FUN', '', '', 'secroleForm','function_code<%=String.valueOf(rIndex)%>','function_description<%=String.valueOf(rIndex)%>', true, '<c:out value="${ctx}"/>');"><img
											src="<c:out value="${ctx}"/>/images/microsoftsearch.gif"></a></td>
									</tr>
								</table>








								</TD>
								<TD width="250px">


								<table cellpadding="0" style="border:0px;" cellspacing="0"
									width="100%">
									<tr>
										<td style="border:0px;" width="1px"><input type="text"
											style="width:100%;"
											name="accessTypes_code<%=String.valueOf(rIndex)%>"
											value='<c:out value="${secobjprivilege.privilege_code}"/>'></td>
										<td style="border:0px;" width="100%"><input
											style="width:100%;" type="text"
											name="accessTypes_description<%=String.valueOf(rIndex)%>"
											value='<c:out value="${secobjprivilege.privilege}"/>'
											readonly></td>
										<td style="border:0px;" width="35px"><a
											onclick="showLookup('PRV', '', '', 'secroleForm','accessTypes_code<%=String.valueOf(rIndex)%>','accessTypes_description<%=String.valueOf(rIndex)%>', true, '<c:out value="${ctx}"/>');"><img
											src="<c:out value="${ctx}"/>/images/microsoftsearch.gif"></a></td>
									</tr>
								</table>


								</TD>

							</TR>

						</logic:iterate>
					</TABLE>


					<table width="100%">
						<tr>
							<td class="clsButtonBarText" width="100%">&nbsp;&nbsp;<a
								href="javascript:submitForm('addFunction');" onclick="javascript:setNoConfirm()">Add</a>&nbsp;&nbsp;&nbsp;|
							&nbsp;&nbsp;<a href="javascript:submitForm('removeFunction');"  onclick="javascript:setNoConfirm()">Remove</a>
							</td>
						</tr>
					</table>



					</td>
				</tr>


			</table>













		</html:form></div>
		</td>
	</tr>
</table>
<%@ include file="/common/readonly.jsp" %>
<script language="javascript" type="text/javascript">
<!--
function submitForm(mthd){
	trimInputBox();
	if(mthd == "saveFunction" && noChanges())
	{
		alert("There is no changes detected to save");
	}
	else
	{
		document.forms[0].method.value=mthd;
		document.forms[0].submit();
	}
}

function gotoRoleList(){
 	window.open("<c:out value='${ctx}'/>/PMmodule/Admin/RoleManager.do?method=list", "_self") ;
}
function removeSel(str) {
    var elSel= document.getElementsByName(str)[0]; 
    
    if(elSel.selectedIndex>=0){
      do {
      	elSel.remove(elSel.selectedIndex);
      }while(elSel.selectedIndex>=0)
     }else{
      alert("Please select the item(s) to remove.");
    } 
}

function getFunctionsList(){
  var elSel= document.getElementsByName("lstFUN")[0]; 
  var txtKey= document.getElementsByName("txtFUNKey")[0]; 
  var txtValue= document.getElementsByName("txtFUNValue")[0]; 
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
  alert(txtKey.value + "====" + txtValue.value);
  
  return true;
}
//-->
</script>

