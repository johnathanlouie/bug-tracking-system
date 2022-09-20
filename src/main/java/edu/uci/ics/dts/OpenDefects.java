package edu.uci.ics.dts;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "OpenDefects", urlPatterns = {"/OpenDefects"})
public class OpenDefects extends LoginServlet {

    @Override
    protected LoginServletHelper mainMethod(HttpServletRequest request, HttpServletResponse response) {
        String s = vectorBugToTable(DatabaseHandler.getOpenDefects());
        request.setAttribute("message", s);
        String forwardPage = "/defectslist.jsp";
        return new LoginServletHelper(request, response, forwardPage);
    }

}
