<%--
    Document   : email
    Created on : Mar 20, 2015, 2:46:32 AM
    Author     : Johnathan Louie
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <form action="/SendEmail" method="post">
            <input type="hidden" name="bugid" value="<%=request.getAttribute("bugid")%>">
            <%=request.getAttribute("userlist")%><br>
            <input name="subject" placeholder="subject"><br>
            <textarea name="message" placeholder="message"></textarea><br>
            <input type="submit">
            <input type="reset">
        </form>
    </body>
</html>
