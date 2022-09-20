<%--
    Document   : newdefectresults
    Created on : Mar 19, 2015, 5:21:07 PM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Defect Tracking System - New Defect Results</title>
    </head>
    <body>
        <h1>New Defect Results</h1>
        <%=request.getAttribute("message")%><br>
        <a href="/NewDefectPrep">Submit Another Defect</a><br>
        <a href="/">Main Menu</a>
    </body>
</html>
