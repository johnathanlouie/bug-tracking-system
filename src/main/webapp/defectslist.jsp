<%-- 
    Document   : defectslist
    Created on : Mar 19, 2015, 7:52:36 PM
    Author     : Johnathan Louie
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bug Tracking System - Defects List</title>
        <link rel="stylesheet" type="text/css" href="/style.css">
    </head>
    <body>
        <h1>Defects List</h1>
        <c:forEach var="bug" items="${bugs}">
            <table>
                <tr>
                    <td>ID</td>
                    <td><c:out value="${bug.id}" /></td>
                </tr>
                <tr>
                    <td>Status</td>
                    <td><c:out value="${bug.status ? 'Open' : 'Closed'}" /></td>
                </tr>
                <tr>
                    <td>Priority</td>
                    <td><c:out value="${bug.priority}" /></td>
                </tr>
                <tr>
                    <td>Assignee</td>
                    <td><c:out value="${bug.assignee}" /></td>
                </tr>
                <tr>
                    <td>Summary</td>
                    <td><c:out value="${bug.summary}" /></td>
                </tr>
                <tr>
                    <td>Description</td>
                    <td><c:out value="${bug.description}" /></td>
                </tr>
                <tr>
                    <td><a href="/EditBug?bugid=${bug.id}">Edit and Reassign</a></td>
                    <td><a href="/Email?bugid=${bug.id}">Email</a></td>
                </tr>
            </table>
        </c:forEach>
    </body>
</html>
