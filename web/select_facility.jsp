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
		<th class="pageTitle">Facility Selection</th>
	</tr>
	<tr> <td>
	<h>Select a Facility:</h> 
	<%
		List<LookupCodeValue> facilities = (List<LookupCodeValue>) request.getAttribute("facilities");
	%>
	<ul>
		<%
			for (LookupCodeValue facility : facilities)
			{
				%>
					<li>&nbsp;<a  href='/QuatroShelter/login.do?nextPage=caisiPMM&<%=SessionConstants.CURRENT_FACILITY_ID%>=<%=facility.getCode()%>'>    <%=facility.getDescription()%></a><p></p></li>
				<%
			}
		%>
	</ul>
	</td>
	</tr>
</table>