

<%
response.setHeader("Cache-Control", "no-cache");
%>

<html>
    <head>
    	<c:if test="${requestScope.sFilePath!=null}">
        	<meta http-equiv="refresh" content="2;url=<%=requestScope.sFilePath%>" />        
        </c:if>
        <title>File Viewer</title>
    </head>    
<body>
    <a href="#" onClick='javascript:window.close()'> Close </a>
</body>
</html>