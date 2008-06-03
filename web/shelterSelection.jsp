<%@page import="java.util.List"%>
<%@page import="com.quatro.model.LookupCodeValue"%>
<%@page import="org.oscarehr.util.SessionConstants"%>
<%@include file="taglibs.jsp"%>
<style type="text/css">
  li { 
  		list-style-image: url(/QuatroShelter/images/smallhouse.gif);
  		list-style-type: circle;
  		list-style-position: outside
  		}
</style>
<table width="100%">
	<tr>
		<th class="pageTitle" colspan="2">Shelter Selection</th>
	</tr>
	<tr>
	<td><img border="0" src="/QuatroShelter/images/Home.gif" width="49" height="60">
	</td>
	<td valign="bottom"><h1><i style="color:#778899">Welcome to Quatro</i><b style="color:	#1E90FF">shelter</b></h1></td>
	<tr>
	<td></td>
	<td height="2px" style="background-color: gray">
	
	</td>
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
			<li>&nbsp;<a  href='/QuatroShelter/login.do?nextPage=caisiPMM&<%=SessionConstants.CURRENT_FACILITY_ID%>=<c:out value="${shelter.code}"/>' >    
			<c:out value="${shelter.description}"></c:out></a></li>
		</logic:iterate>
	</ul>
	</td>
	</tr>
</table>