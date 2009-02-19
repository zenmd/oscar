<%@page import="java.util.List"%>
<%@page import="com.quatro.model.LookupCodeValue"%>
<%@include file="taglibs.jsp"%>
<%
String _appPath = request.getContextPath();
%>
<style type="text/css">
  li { 
  		list-style-image: url(<%=_appPath %>/images/smallhouse-red.gif);
  		list-style-type: circle;
  		list-style-position: outside
  		}
</style>
<table width="100%">
	<tr>
		<th class="pageTitle" colspan="5">Shelter Selection</th>
	</tr>
	<tr><td colspan="5">&nbsp;</td></tr>
	<tr>
		<td width="10%">&nbsp;&nbsp;
			<img border="0" width="60px"
			height="60px" src="<%=_appPath %>/images/Home.gif" alt="" />
		</td>
		<td width="80%" align="left" class="clsPageHeader" colspan="3" style="font-style: italic; vertical-align: middle">
			<h1 style="color:#1E90FF" style="vertical-align: middle">Welcome</b></h1>
		</td>
		<td width="10%"></td>
	</tr>
	<tr>
		<td></td>
		<td colspan="3"><hr /></td>
		<td></td>
	</tr>
	<tr>
		<td></td>
		<th align="left">Please select a Shelter:</th>
	</tr>
	<tr>
		<td></td>
		<td colspan="3">
			<ul>
				<logic:iterate id="shelter" name="shelters">
					<li>&nbsp;<a  href='<%=_appPath %>/shelterSelection.do?method=select&shelterId=<c:out value="${shelter.code}"/>' >    
					<c:out value="${shelter.description}"></c:out></a></li>
				</logic:iterate>
			</ul>
		</td>
		<td></td>
	</tr>
</table>
