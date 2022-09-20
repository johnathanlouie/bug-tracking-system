<%--
    Document   : editbug
    Created on : Mar 19, 2015, 10:12:21 PM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Defect Tracking System - Edit Defect</title>
    </head>
    <body>
        <h1>Edit Defect</h1>
        <form action="/UpdateBug" method="post">
            <input name="id" type="hidden" value="<%=request.getAttribute("id")%>">
            <select name="status">
                <option value="1" <%=(((boolean) request.getAttribute("status") == true) ? "selected" : "")%>>Open</option>
                <option value="0" <%=(((boolean) request.getAttribute("status") == false) ? "selected" : "")%>>Closed</option>
            </select>
            <%=request.getAttribute("userlist")%><br>
            <input name="priority" placeholder="priority" type="number" value="<%=request.getAttribute("priority")%>"><br>
            <textarea name="summary" placeholder="summary"><%=request.getAttribute("summary")%></textarea><br>
            <textarea name="description" placeholder="description"><%=request.getAttribute("description")%></textarea><br>
            <input type="submit">
            <input type="reset">
        </form>
    </body>
</html>
