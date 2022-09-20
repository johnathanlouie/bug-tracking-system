package edu.uci.ics.dts;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UpdateBug", urlPatterns = {"/UpdateBug"})
public class UpdateBug extends LoginServlet {

    @Override
    protected LoginServletHelper mainMethod(HttpServletRequest request, HttpServletResponse response) {
        String assignee = request.getParameter("assignee");
        int id = Integer.parseInt(request.getParameter("id"));
        boolean status = Integer.parseInt(request.getParameter("status")) != 0;
        String description = request.getParameter("description");
        String summary = request.getParameter("summary");
        int priority = Integer.parseInt(request.getParameter("priority"));
        DatabaseHandler.updateDefect(new Bug(status, priority, assignee, summary, description, id));
        request.setAttribute("message", "You have successfully updated a defect.");
        String forwardPage = "/updatebugresults.jsp";
        return new LoginServletHelper(request, response, forwardPage);
    }

}
