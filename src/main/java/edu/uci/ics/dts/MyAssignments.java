package edu.uci.ics.dts;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MyAssignments", urlPatterns = {"/MyAssignments"})
public class MyAssignments extends LoginServlet {

    @Override
    protected LoginServletHelper mainMethod(HttpServletRequest request, HttpServletResponse response) {
        String s = vectorBugToTable(DatabaseHandler.getAssignments(((User) request.getSession(false).getAttribute("user")).getUsername()));
        request.setAttribute("message", s);
        String forwardPage = "/defectslist.jsp";
        return new LoginServletHelper(request, response, forwardPage);
    }

}
