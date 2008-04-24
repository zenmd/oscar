<%@ include file="/taglibs.jsp"%>

<bean:define id="lstFUNSelection" name="secroleForm" property="funSelectionList" />
<bean:define id="accessTypes" name="secroleForm" property="accessTypes" type="com.quatro.model.LookupCodeValue" />
						
<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
	<tr>
		<th class="pageTitle" align="center"><span
			id="_ctl0_phBody_lblTitle" align="left">Role Management</span></th>
	</tr>
	<tr>
		<td align="left" class="buttonBar"><html:link
			action="/PMmodule/Admin/RoleManager.do"
			style="color:Navy;text-decoration:none;">
			<img border=0 src=<html:rewrite page="/images/Back16.png"/> />&nbsp;Back to Role List&nbsp;&nbsp;|</html:link>
		<html:link href="javascript:submitForm('save');" onclick="javascript:getFunctionsList();"
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
                    height: 100%; width: 100%; overflow: auto;">

		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td align="left" class="clsHomePageHeader" colspan="5">
				<h2>Add Functions to Role</h2>
				</td>
			</tr>


		</table>

		<html:form action="/PMmodule/Admin/RoleManager" method="post" >
			<html:hidden property="method" value="save" />
			<table>

				<tr>
					<td>Role No.:</td>
					<td><html:text property="roleNo" readonly="true"
						style="border: none" /></td>
				</tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>

				<tr>
					<td>Role Name:</td>
					<td><html:text property="roleName" readonly="true"
						style="border: none" /></td>
				</tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td>Description:</td>
					<td><html:text property="description" readonly="true"
						style="border: none" /></td>
				</tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td>Functions:</td>
					<td>
					
					<table width="100%">

						<tr>
							<td><html:select property="lstFUN" multiple="true" size="5"
								style="width:350px;">
								<html:options collection="lstFUNSelection" property="key"
									labelProperty="value"></html:options>
							</html:select> 
							
							<html:hidden property="txtFUNKey" value="" /> <html:hidden
								property="txtFUNValue" value="" /></td>
							<td class="clsButtonBarText"><a
								onclick="javascript:showLookup('FUN', '', '', 'secroleForm','lstFUN','', true, '<c:out value="${ctx}"/>');"><img
								src="/QuatroShelter/images/microsoftsearch.gif"></a><a
								onclick="javascript:removeSel('lstFUN')"><img
								src="/QuatroShelter/images/Reset16.gif"></a></td>
						</tr>

					</table>


					</td>
				</tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td>Access Type:</td>
					<td>
					<quatro:lookupTag name="accessTypes" tableName="PRV"
					formProperty="secroleForm" codeProperty="code"
					bodyProperty="description" width="390px" codeWidth="100px"
					showCode="true" />
				
					</td>
				</tr>

			</table>






<TABLE align="center" class="simple" width="100%"><!--  722px-->
<thead>
   	<TR><th class="sortable">Select</th>
    	<th class="sortable">Function</TH>
		<TH class="sortable">AccessType</TH>
      	</TR></thead>

	<logic:iterate id="tplCriteria" name="secroleForm" property="functionsList" indexId="rIndex">
	<TR><TD align="center">
        <input type="checkbox" name="p<%=String.valueOf(rIndex)%>" value="<%=String.valueOf(rIndex)%>" /> 
     	<input type="hidden" name="lineno" value="<%=String.valueOf(rIndex)%>" /> 
   	</TD>
   	<TD>
		<html:select name="tplCriteria" property="relation" indexed="true">
          <logic:iterate id="relation" name="arRelations" type="String">
            <html:option value="<%=relation%>"><%=relation%></html:option>
          </logic:iterate>
		</html:select>
	</TD>  
	<TD>
		<html:select name="tplCriteria" property="fieldNo" indexed="true" onchange="CriteriaChanged(this);">
           <option value="0"></option>
           <html:options collection="filterFields" property="fieldNo" labelProperty="fieldName"></html:options>
		</html:select>
	</TD>  
	 
   	</TR>
   	<input type="hidden" name="lineno" value="<%=String.valueOf(rIndex)%>" />
   	</logic:iterate>
</TABLE>


           <table width="100%">
	         <tr>
                <td class="clsButtonBarText" width="100%">
                    &nbsp;&nbsp;<a href="javascript:submitForm('addFunction');">Add</a>&nbsp;&nbsp;&nbsp;|
                        &nbsp;&nbsp;<a href="javascript:submitForm('removeFunction');">Remove</a>
	                </td>     
	            </tr>
            </table>
        







		</html:form></div>
		</td>
	</tr>
</table>

<script language="javascript" type="text/javascript">
<!--
function submitForm(mthd){
	document.forms[0].method.value=mthd;
	document.forms[0].submit();
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
function submitForm2(){
	document.forms[0].method.value="save";
	document.forms[0].submit();
/*
/////////////////////////////////
// code for using TAG quatro:lookupTag
Functions:<bean:define id="functions" name="secroleForm"
				property="functions" type="com.quatro.model.LookupCodeValue" />
			<quatro:lookupTag name="functions" tableName="FUN"
				formProperty="secroleForm" codeProperty="code"
				bodyProperty="description" width="500px" codeWidth="200px"
				showCode="true" />
/////////////////////////////////				
				

*/
}
//-->
</script>

