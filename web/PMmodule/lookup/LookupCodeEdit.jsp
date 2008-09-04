<%@ include file="/taglibs.jsp"%>
<script type="text/javascript">
	function submitForm()
	{
		trimInputBox();
 		document.forms[0].method.value="save";
		if(noChanges())
		{
			alert("There are no changes detected to save");
		}
		else
		{
		    var obj1 = document.getElementsByName('tableId')[0];
		    if(obj1.value=='SHL'){
		      var obj2 = document.getElementsByName('field[4].val')[0];
              if(obj2.value=='0' && !confirm("Deactivating this shelter will also deactivate facilities and programs in the shelter. Select Ok to proceed or Cancel to cancel."))
              return;
		    }
		    if(obj1.value=='OGN'){
		      var obj2 = document.getElementsByName('field[2].val')[0];
              if(obj2.value=='0' && !confirm("Deactivating this organization will also deactivate shelters, facilities and programs in the organization. Select Ok to proceed or Cancel to cancel."))
              return;
		    }
			document.forms[0].submit();
		}
	}
</script>
<html:form action="/Lookup/LookupCodeEdit">
<input type="hidden" name="scrollPosition" value='<c:out value="${scrPos}"/>' />
<input type="hidden" name="tableId" value='<c:out value="${lookupCodeEditForm.tableDef.tableId}"/>' />
<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
	<tr>
		<th class="pageTitle" align="center"><span
			id="lblTitle" align="left">Code Edit - <bean:write name="lookupCodeEditForm" property="tableDef.description"/></span></th>
	</tr>
	<tr>
		<td  align="left" class="buttonBar2">
		<input type="hidden" id="method" name="method"></input>
		<html:link action="/Lookup/LookupCodeList.do" paramId="id" paramName="lookupCodeEditForm" paramProperty="tableDef.tableId"> 
		<img src="../images/close16.png" border="0"/> Close</html:link>
		<c:if test="${!isReadOnly}">
			 &nbsp;|&nbsp; <a href="javascript:submitForm();" onclick="javascript:setNoConfirm();">
			<img src="../images/Save16.png" border="0"/> Save </a>
		</c:if>	
			
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
					<logic:notEmpty name="lookupCodeEditForm" property="errMsg">
					<table width="100%">
						<tr><td>
						<b><bean:write name="lookupCodeEditForm" property="errMsg" /></b>
						</td></tr>
						<tr><td>&nbsp;</td></tr>
					</table>
					</logic:notEmpty>
					<table width="100%">
					 
					<logic:iterate id="field" name="lookupCodeEditForm" property="codeFields" indexId="fIndex" type="com.quatro.model.FieldDefValue">
						<tr>
						<td width="30%"> <bean:write name="field" property="fieldDesc" /></td>
						<td>
					       <logic:equal name="field" property="fieldType" value="S">
					         	<logic:equal name="field" property="editable" value="false">
						           <bean:write name="field" property="val"/>
						           <logic:notEmpty name="field" property="valDesc">
						           		 - <bean:write name="field" property="valDesc"/>
						           </logic:notEmpty>
						           <html:hidden name="field" property="val" indexed="true" />
					           </logic:equal>
					           <logic:equal name="field" property="editable" value="true">
					         		<logic:empty name="field" property="lookupTable">
							           <html:text name="field" property="val" indexed="true"  style="{width:100%}" maxlength="<%=field.getFieldLengthStr()%>"/>
							        </logic:empty>
					         		<logic:notEmpty name="field" property="lookupTable">
					           			<html:hidden name="field" property="lookupTable" indexed="true" />
					           			<quatro:lookupTag name="field" tableName="<%=field.getLookupTable()%>" indexed="true" formProperty="lookupCodeEditForm" codeWidth="10%"
					              			codeProperty ="val" bodyProperty="valDesc"></quatro:lookupTag>
					         		</logic:notEmpty>
					       		</logic:equal>
					       </logic:equal>  
					       <logic:equal name="field" property="fieldType" value="D">
					       	 	<bean:define id="dateVal" name="field" property="val"></bean:define>
					         	<logic:equal name="field" property="editable" value="true">
					         		<quatro:datePickerTag name="field" property="val" indexed="true" openerForm="lookupCodeEditForm"  width="200px">
					         		</quatro:datePickerTag>
					         	</logic:equal>
					         	<logic:equal name="field" property="editable" value="false">
						           <bean:write name="field" property="val"/>
						           <html:hidden name="field" property="val" indexed="true"/>
					         	</logic:equal>
					       </logic:equal>  
					       <logic:equal name="field" property="fieldType" value="N">
					       	   <logic:equal name="field" property="editable" value="true">
					          	<html:text name="field" property="val" indexed="true" maxlength="10" />
					           </logic:equal>
					       	   <logic:equal name="field" property="editable" value="false">
					       	   	<bean:write name="field" property="val" /> 
					          	<html:hidden name="field" property="val" indexed="true" />
					           </logic:equal>
					       </logic:equal>
					       <logic:equal name="field" property="fieldType" value="B">
					       	   <logic:equal name="field" property="editable" value="true">
					          		<html:select name="field" property="val" indexed="true">
					          			<html:option value="1">Yes</html:option>
					          			<html:option value="0">No</html:option>
					          		</html:select>
					          	</logic:equal>
					       	   <logic:equal name="field" property="editable" value="false">
					          		<html:select name="field" property="val" indexed="true" disabled="true">
					          			<html:option value="1">Yes</html:option>
					          			<html:option value="0">No</html:option>
					          		</html:select>
					          	</logic:equal>
					          	
					       </logic:equal>
					      </td>
					  </tr>  
				</logic:iterate>
			</table>
		</div>
		</td>
	</tr>
</table>
</html:form>
<%@ include file="/common/readonly.jsp" %>
