<%--
    Document   : updatebugresults
    Created on : Mar 20, 2015, 1:10:52 AM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Defect Tracking System - Results</title>
    </head>
    <body>
        <h1>Results</h1>
        <%=request.getAttribute("message")%><br>
        <a href="/">Main Menu</a><br>
        <a href="/OpenDefects">Open Defects</a><br>
        <a href="/AllDefects">All Defects</a>
    </body>
</html>
