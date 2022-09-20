<%--
    Document   : newdefect
    Created on : Mar 19, 2015, 4:26:47 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Defect Tracking System - New Defect</title>
    </head>
    <body>
        <h1>New Defect</h1>
        <form action="/NewDefect" method="post">
            <%=request.getAttribute("userlist")%><br>
            <input name="priority" placeholder="priority" type="number"><br>
            <textarea name="summary" placeholder="summary"></textarea><br>
            <textarea name="description" placeholder="description"></textarea><br>
            <input type="submit">
            <input type="reset">
        </form>
    </body>
</html>
