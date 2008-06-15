<%@page import="java.util.List"%>
<%@page import="com.quatro.model.LookupCodeValue"%>
<%@include file="taglibs.jsp"%>
<%
String _appPath = request.getContextPath();
%>
<style type="text/css">
  li { 
  		list-style-image: url(/QuatroShelter/images/smallhouse.gif);
  		list-style-type: circle;
  		list-style-position: outside
  		}
</style>
<table width="100%">
	<tr>
		<th class="pageTitle" colspan="5">Shelter Selection</th>
	</tr><tr><td colspan="5">&nbsp;</td></tr><tr>
<td width="10%">&nbsp;&nbsp;
<img border="0" width="60px"
										height="60px" src="<%=_appPath %>/images/Home.gif" alt="" /></td>
	 <td width="80%" align="left" class="clsPageHeader" colspan="5">
			<h1><i style="color:#778899">Welcome to Quatro</i><b style="color:	#1E90FF">shelter</b></h1>
	</td>
	
	</tr>
								<tr>
									<td></td>
									<td colspan="5">
									<hr />
				
	<tr>
	<td>
	</td>
	<th align="left">Select a Shelter:</th>
	<tr>
	<td>
	</td>
	<td> 
	<ul>
		<logic:iterate id="shelter" name="shelters">
			<li>&nbsp;<a  href='/QuatroShelter/shelterSelection.do?method=select&shelterId=<c:out value="${shelter.code}"/>' >    
			<c:out value="${shelter.description}"></c:out></a></li>
		</logic:iterate>
	</ul>
	</td>
	</tr>
</table>