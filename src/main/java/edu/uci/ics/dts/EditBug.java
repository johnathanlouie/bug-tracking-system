package edu.uci.ics.dts;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "EditBug", urlPatterns = {"/EditBug"})
public class EditBug extends LoginServlet {

    @Override
    protected LoginServletHelper mainMethod(HttpServletRequest request, HttpServletResponse response) {
        int bugId = Integer.parseInt(request.getParameter("bugid"));
        Bug bug = DatabaseHandler.getBugById(bugId);
        String s = vectorUserToSelect(DatabaseHandler.getAllUsers(), "assignee", bug.getAssignee());
        request.setAttribute("userlist", s);
        request.setAttribute("id", bug.getId());
        request.setAttribute("status", bug.getStatus());
        request.setAttribute("assignee", bug.getAssignee());
        request.setAttribute("description", bug.getDescription());
        request.setAttribute("priority", bug.getPriority());
        request.setAttribute("summary", bug.getSummary());
        String forwardPage = "/editbug.jsp";
        return new LoginServletHelper(request, response, forwardPage);
    }

}
