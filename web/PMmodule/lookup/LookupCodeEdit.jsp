
<%@ include file="/taglibs.jsp"%>


<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
	<tr>
		<th class="pageTitle" align="center"><span
			id="_ctl0_phBody_lblTitle" align="left">Lookup Tables</span></th>
	</tr>
	<tr>
		<td  align="left" class="buttonBar">
		<input type="hidden" id="method" name="method"></input>
		<a href="javascript:submitForm();">
			<img src="../images/Save16.png" border="0"/> Save </a> &nbsp;|&nbsp;
		<html:link action="/Lookup/LookupCodeList.do" paramId="id" paramName="lookupCodeEditForm" paramProperty="tableDef.tableId"> <img src="../images/Back16.png" border="0"/> Back to Code List</html:link>
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

		<table width="100%" cellpadding="0" cellspacing="0" border="10">
			<tr>
				<td align="left" class="clsHomePageHeader">
				<h2>Lookup Code Edit</h2>
				</td>
			</tr>

		</table>

<logic:notEmpty name="lookupCodeEditForm" property="errMsg">
<table width="100%">
	<tr><td>
	<b><bean:write name="lookupCodeEditForm" property="errMsg" /></b>
	</td></tr>
	<tr><td>&nbsp;</td></tr>
</table>
</logic:notEmpty>
<table width="100%">
<tr>
     <th width="20%">Category: </th>
     <th align="left"><bean:write name="lookupCodeEditForm" property="tableDef.moduleName" /></th>
</tr>
<tr>
     <th>Field: </th>
     <th align="left"><bean:write name="lookupCodeEditForm" property="tableDef.description"/></th>
</tr>
<tr>
<logic:iterate id="field" name="lookupCodeEditForm" property="codeFields" indexId="fIndex" type="com.quatro.model.FieldDefValue">
	<tr>
	<td> <bean:write name="field" property="fieldDesc" /></td>
	<td>
       <logic:equal name="field" property="fieldType" value="S">
         <logic:empty name="field" property="lookupTable">
         	<logic:equal name="field" property="editable" value="true">
	           <html:text name="field" property="val" indexed="true"  style="{width:100%}"/>
           </logic:equal>
         	<logic:equal name="field" property="editable" value="false">
	           <bean:write name="field" property="val"/>
	           <html:hidden name="field" property="val" indexed="true"  style="{width:100%}"/>
           </logic:equal>
         </logic:empty>
         <logic:notEmpty name="field" property="lookupTable">
           <html:hidden name="field" property="lookupTable" indexed="true" />
           <quatro:lookupTag name="field" tableName="<%=field.getLookupTable()%>" indexed="true" formProperty="lookupCodeEditForm" codeWidth="10%"
              codeProperty ="val" bodyProperty="valdesc" codeValue="<%=field.getVal()%>" bodyValue="<%=field.getValDesc()%>"></quatro:lookupTag>
         </logic:notEmpty>
       </logic:equal>  
       <logic:equal name="field" property="fieldType" value="D">
       	 <bean:define id="dateVal" name="field" property="val"></bean:define>
         <quatro:datePickerTag name="field" property="val" indexed="true" openerForm="lookupCodeEditForm"  width="200px"
         value="<%=oscar.MyDateFormat.getMyStandardDate((String)dateVal)%>"></quatro:datePickerTag>
       </logic:equal>  
       <logic:equal name="field" property="fieldType" value="N">
       	   <logic:equal name="field" property="editable" value="true">
          	<html:text name="field" property="val" indexed="true" />
           </logic:equal>
       	   <logic:equal name="field" property="editable" value="false">
       	   	<bean:write name="field" property="val" /> 
          	<html:hidden name="field" property="val" indexed="true" />
           </logic:equal>
       </logic:equal>
       <logic:equal name="field" property="fieldType" value="B">
          <html:select name="field" property="val" indexed="true">
          	<html:option value="1">Yes</html:option>
          	<html:option value="0">No</html:option>
          </html:select>
       </logic:equal>
      </td>
  </tr>  
</logic:iterate>
</table>


		</div>
		</td>
	</tr>
</table>

<script type="text/javascript">
	function submitForm()
	{
		document.forms[0].method.value="save";
		document.forms[0].submit();
	}
</script>