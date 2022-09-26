<%--
    Document   : editbug
    Created on : Mar 19, 2015, 10:12:21 PM
    Author     : Johnathan Louie
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bug Tracking System - Edit Defect</title>
    </head>
    <body>
        <h1>Edit Defect</h1>
        <form action="/UpdateBug" method="post">
            <input name="id" type="hidden" value="${bug.id}">
            <select name="status">
                <option value="1" ${bug.status ? 'selected' : ''}>Open</option>
                <option value="0" ${bug.status ? '' : 'selected'}>Closed</option>
            </select>
            <select name="assignee">
                <c:forEach var="user" items="${users}">
                    <option value="${user.username}" ${user.username == bug.assignee ? 'selected' : ''}>${user.username}</option>
                </c:forEach>
            </select><br>
            <input name="priority" placeholder="priority" type="number" value="${bug.priority}"><br>
            <textarea name="summary" placeholder="summary">${bug.summary}</textarea><br>
            <textarea name="description" placeholder="description">${bug.description}</textarea><br>
            <input type="submit">
            <input type="reset">
        </form>
    </body>
</html>
