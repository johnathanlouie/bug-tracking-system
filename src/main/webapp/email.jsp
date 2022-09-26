<%--
    Document   : email
    Created on : Mar 20, 2015, 2:46:32 AM
    Author     : Johnathan Louie
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bug Tracking System - Email</title>
    </head>
    <body>
        <h1>Email</h1>
        <form action="/SendEmail" method="post">
            <input type="hidden" name="bugid" value="${bugId}">
            <select name="assignee">
                <c:forEach var="user" items="${users}">
                    <option value="${user.username}" ${user.username == currentUser.username ? 'selected' : ''}>${user.username}</option>
                </c:forEach>
            </select><br>
            <input name="subject" placeholder="subject"><br>
            <textarea name="message" placeholder="message"></textarea><br>
            <input type="submit">
            <input type="reset">
        </form>
    </body>
</html>
