<%--
    Document   : newuser
    Created on : Mar 21, 2015, 2:02:44 AM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Defect Tracking System - New User</title>
    </head>
    <body>
        <h1>New User</h1>
        <form action="/NewUser" method="post">
            <input name="username" placeholder="username" required><br>
            <input name="password" placeholder="password" required><br>
            <input name="email" placeholder="email" type="email" required><br>
            <input type="submit">
            <input type="reset">
        </form>
    </body>
</html>
