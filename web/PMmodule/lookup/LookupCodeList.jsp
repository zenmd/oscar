<%@ include file="/taglibs.jsp"%>


<table width="100%" height="100%" cellpadding="0px" cellspacing="0px">
	<tr>
		<th class="pageTitle" align="center"><span
			id="_ctl0_phBody_lblTitle" align="left">Lookup Tables &nbsp;-&nbsp;<bean:write name="lookupCodeListForm" property="tableDef.description"/></span></th>
	</tr>
	<tr>
		<td align="left" class="buttonBar2">
		<logic:notEqual value="true" name="lookupCodeListForm" property="tableDef.readonly">
		<html:link  action="/Lookup/LookupCodeEdit.do" paramName="lookupCodeListForm" paramProperty="tableDef.tableId" paramId="id">
		<img src="../images/New16.png" border="0"/> Add</html:link>&nbsp;|&nbsp;</logic:notEqual>
		<html:link action="/Lookup/LookupTableList.do"> <img src="../images/Back16.png" border="0"/> Back to Lookup Fields</html:link>
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


		



<table>
<tr>
     <th>Category: </th>
     <th align="left"><bean:write name="lookupCodeListForm" property="tableDef.moduleName" /></th>
</tr>
<tr>
     <th>Field: </th>
     <th alighn="left"><bean:write name="lookupCodeListForm" property="tableDef.description"/></th>
</tr>
<tr><td colspan="2">&nbsp;</td>
</tr>
<tr>
	<th>ID</th><th>Description</th><th>Active</th><th>Display Order</th>
</tr>

<logic:iterate id="lkCode" name="lookupCodeListForm" property="codes">
<tr>
	<td>
		<html:link action="/Lookup/LookupCodeEdit.do" paramId="id" paramProperty="codeId" paramName="lkCode">
			<bean:write name="lkCode" property="code" /> 
 		</html:link>
 	</td>
 	<td>
		<html:link action="/Lookup/LookupCodeEdit.do" paramId="id" paramProperty="codeId" paramName="lkCode">
 			<bean:write name="lkCode" property="description" />
	 	</html:link>
 	</td>
 	<td>
 		<logic:equal value="true" name="lkCode" property="active">
 			Yes
 		</logic:equal>
 		<logic:equal value="false" name="lkCode" property="active">
 			No
 		</logic:equal>
 	</td>
 	<td>
 		<bean:write name="lkCode" property="orderByIndex" />
 	</td>
</tr> 	
	</logic:iterate>

<tr><td colspan="2">

</td>

</table>


		</div>
		</td>
	</tr>
</table>
