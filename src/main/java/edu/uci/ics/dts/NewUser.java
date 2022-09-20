package edu.uci.ics.dts;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "NewUser", urlPatterns = {"/NewUser"})
public class NewUser extends LoginServlet {

    @Override
    protected LoginServletHelper mainMethod(HttpServletRequest request, HttpServletResponse response) {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        DatabaseHandler.insertUser(username, password, email);

        request.setAttribute("message", "You have successfully added a user.");
        String forwardPage = "/updatebugresults.jsp";
        return new LoginServletHelper(request, response, forwardPage);
    }

}
