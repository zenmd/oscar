<%@page import="java.util.List"%>
<%@page import="org.oscarehr.PMmodule.model.Facility"%>
<%@page import="org.oscarehr.util.SessionConstants"%>
<%@include file="taglibs.jsp"%>
<table width="100%">
	<tr>
		<th class="pageTitle">Facility Selection</th>
	</tr>
	<tr> <td>
	<h>Please select which facility you would like to currently work in</h> 
	<%
		List<Facility> facilities = (List<Facility>) request.getAttribute("facilities");
	%>
	<ul>
		<%
			for (Facility facility : facilities)
			{
				%>
					<li><a  href='/QuatroShelter/login.do?nextPage=caisiPMM&<%=SessionConstants.CURRENT_FACILITY_ID%>=<%=facility.getId()%>'><%=facility.getName()%></a></li>
				<%
			}
		%>
	</ul>
	</td>
	</tr>
</table>