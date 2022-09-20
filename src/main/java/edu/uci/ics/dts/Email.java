package edu.uci.ics.dts;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Email", urlPatterns = {"/Email"})
public class Email extends LoginServlet {

    @Override
    protected LoginServletHelper mainMethod(HttpServletRequest request, HttpServletResponse response) {

        int id = Integer.parseInt(request.getParameter("bugid"));
        request.setAttribute("bugid", id);

        String currentUser = ((User) request.getSession(false).getAttribute("user")).getUsername();
        String s = vectorUserToSelect(DatabaseHandler.getAllUsers(), "assignee", currentUser);
        request.setAttribute("userlist", s);

        String forwardPage = "/email.jsp";
        return new LoginServletHelper(request, response, forwardPage);
    }

}
