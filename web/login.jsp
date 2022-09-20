<%--
    Document   : index
    Created on : Mar 19, 2015, 1:00:09 AM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Defect Tracking System - Login Page</title>
    </head>
    <body>
        <h1>Login Page</h1>
        <%=request.getAttribute("message")%>
        <form action="/Authentication" method="post">
            <input type="text" autofocus placeholder="username" name="username">
            <input type="password" placeholder="password" name="password">
            <input type="submit">
            <input type="reset">
        </form>
    </body>
</html>
