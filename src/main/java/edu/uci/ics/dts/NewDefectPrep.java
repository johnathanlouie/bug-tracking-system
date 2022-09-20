package edu.uci.ics.dts;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "NewDefectPrep", urlPatterns = {"/NewDefectPrep"})
public class NewDefectPrep extends LoginServlet {

    @Override
    protected LoginServletHelper mainMethod(HttpServletRequest request, HttpServletResponse response) {
        String currentUser = ((User) request.getSession(false).getAttribute("user")).getUsername();
        String s = vectorUserToSelect(DatabaseHandler.getAllUsers(), "assignee", currentUser);
        request.setAttribute("userlist", s);
        String forwardPage = "/newdefect.jsp";
        return new LoginServletHelper(request, response, forwardPage);
    }

}
