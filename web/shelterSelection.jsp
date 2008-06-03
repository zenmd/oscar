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
		<th class="pageTitle">Shelter Selection</th>
	</tr>
	<tr> <td>
	<h>Select a Shelter:</h> 
	<ul>
		<logic:iterate id="shelter" name="shelters">
			<li>&nbsp;<a  href='/QuatroShelter/login.do?nextPage=caisiPMM&<%=SessionConstants.CURRENT_FACILITY_ID%>=<c:out value="${shelter.code}"/>' >    
			<c:out value="${shelter.description}"></c:out></a></li>
		</logic:iterate>
	</ul>
	</td>
	</tr>
</table>