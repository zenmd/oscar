<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>Signature</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Application Developer">
</head>
<body>

<br><br>
<table border="2" bordercolor="#000000" cellpadding="0" cellspacing="0" ><tr><td align="center" style="background-color: #c0c0c0;">
<OBJECT classid="clsid:8AD9C840-044E-11D1-B3E9-00805F499D93"
    width="450" height="200" align="baseline"
    codebase="http://java.sun.com/products/plugin/1.4/jinstall-14-win32.cab#Version=1,4,0,mn">
    <PARAM NAME="code" VALUE="com.quatro.model.signaturePad.SigPlusApp.class">
    <PARAM NAME="archive" VALUE="topazApplet.jar, SigPlus2_51.jar, comm.jar">
    <PARAM NAME="type" VALUE="application/x-java-applet;jpi-version=1.4">
    <PARAM NAME="rid" VALUE="<%=request.getSession().getAttribute("rId")%>">
    <PARAM NAME="moduleName" VALUE="<%=request.getSession().getAttribute("moduleName")%>">
    <COMMENT>
        <EMBED type="application/x-java-applet;jpi-version=1.4" width="450"
           height="200" align="baseline" code="com.quatro.model.signaturePad.SigPlusApp.class" 
           archive="topazApplet.jar, SigPlus2_51.jar, comm.jar" 
           pluginspage="http://java.sun.com/j2se/1.4/download.html">
            <NOEMBED>
                No Java 2 SDK, Standard Edition v 1.4 support for APPLET!!
            </NOEMBED>
        </EMBED>
    </COMMENT>
</OBJECT>
</td></tr></table>
</body>
</html>
