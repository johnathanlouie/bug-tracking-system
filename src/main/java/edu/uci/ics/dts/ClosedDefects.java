package edu.uci.ics.dts;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ClosedDefects", urlPatterns = {"/ClosedDefects"})
public class ClosedDefects extends LoginServlet {

    @Override
    protected LoginServletHelper mainMethod(HttpServletRequest request, HttpServletResponse response) {
        String s = vectorBugToTable(DatabaseHandler.getClosedDefects());
        request.setAttribute("message", s);
        String forwardPage = "/defectslist.jsp";
        return new LoginServletHelper(request, response, forwardPage);
    }

}
