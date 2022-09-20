package edu.uci.ics.dts;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "NewDefect", urlPatterns = {"/NewDefect"})
public class NewDefect extends LoginServlet {

    @Override
    protected LoginServletHelper mainMethod(HttpServletRequest request, HttpServletResponse response) {
        int priority = Integer.parseInt(request.getParameter("priority"));
        String summary = request.getParameter("summary");
        String description = request.getParameter("description");
        String assignee = request.getParameter("assignee");
        DatabaseHandler.insertBug(priority, summary, description, assignee);
        request.setAttribute("message", "You have successfully added a defect.");
        String forwardPage = "/newdefectresults.jsp";
        return new LoginServletHelper(request, response, forwardPage);
    }

}
