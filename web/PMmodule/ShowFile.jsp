

<%
response.setHeader("Cache-Control", "no-cache");
%>

<html>
    <head>
    <link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon" />
    	<c:if test="${requestScope.sFilePath!=null}">
        	<meta http-equiv="refresh" content="2;url=<c:out value="${sFilePath}" /> />        
        </c:if>
        <title>File Viewer</title>
    </head>    
<body>
    <a href="#" onClick='javascript:window.close()'> Close </a>
</body>
</html>