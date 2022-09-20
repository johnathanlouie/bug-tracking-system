package edu.uci.ics.dts;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AllDefects", urlPatterns = {"/AllDefects"})
public class AllDefects extends LoginServlet {

    @Override
    protected LoginServletHelper mainMethod(HttpServletRequest request, HttpServletResponse response) {
        String s = vectorBugToTable(DatabaseHandler.getAllDefects());
        request.setAttribute("message", s);
        String forwardPage = "/defectslist.jsp";
        return new LoginServletHelper(request, response, forwardPage);
    }

}
