<%@ include file="/taglibs.jsp"%>
<%@ page import="java.util.List" %>
<%
//	List pss = (List) request.getAttribute("programSignatures");
//	System.out.println("size of ........"+pss.size());
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
<title>Program History</title>
<style type="text/css">			
	TABLE{
		background: white;
	}
	TH {
		background: #eef;
	}
	TD{
		background: #eee;
	}	
</style>
</head>
<body>

<display:table cellspacing="2" cellpadding="9" id="ps" name="programSignatures" export="false" pagesize="0" requestURI="/PMmodule/ProgramManager.do">
	
	<display:column property="providerName" style="white-space: nowrap;" sortable="true" title="Provider Name" ></display:column>
	<display:column property="providerName" style="white-space: nowrap;" sortable="true" title="Role" ></display:column>
	<display:column property="updateDate" style="white-space: nowrap;" sortable="true" title="Date" ></display:column>
</display:table>

</body>
</html:html>
<!--  
<input type="button" value="Back" onClick="history.go(-1)"/>
-->
</br>
<input type="button" value="Close" onClick="self.close()"/>
