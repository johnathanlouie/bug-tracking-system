package edu.uci.ics.dts;

import java.io.IOException;
import java.util.Vector;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class LoginServlet extends HttpServlet {

    abstract protected LoginServletHelper mainMethod(HttpServletRequest request, HttpServletResponse response);

    final protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            LoginServletHelper helper = mainMethod(request, response);
            RequestDispatcher dispatcher = getServletConfig().getServletContext().getRequestDispatcher(helper.getForwardPage());
            dispatcher.forward(helper.getRequest(), helper.getResponse());
        } else {
            RequestDispatcher dispatcher = getServletConfig().getServletContext().getRequestDispatcher("/login.jsp");
            request.setAttribute("message", "You are not logged in.");
            dispatcher.forward(request, response);
        }
    }

    protected static String vectorBugToTable(Vector<Bug> v) {
        String s = "";
        if (v != null) {
            for (Bug i : v) {
                s += "<table>\n";
                s += "<tr><td>ID</td><td>" + i.getId() + "</td></tr>\n";
                s += "<tr><td>Status</td><td>" + ((i.getStatus()) ? "Open" : "Closed") + "</td></tr>\n";
                s += "<tr><td>Priority</td><td>" + i.getPriority() + "</td></tr>\n";
                s += "<tr><td>Assignee</td><td>" + i.getAssignee() + "</td></tr>\n";
                s += "<tr><td>Summary</td><td>" + i.getSummary() + "</td></tr>\n";
                s += "<tr><td>Description</td><td>" + i.getDescription() + "</td></tr>\n";
                s += "<tr><td><a href=\"/EditBug?bugid=" + i.getId() + "\">Edit and Reassign</a></td><td><a href=\"/Email?bugid=" + i.getId() + "\">Email</a></td></tr>\n";
                s += "</table>\n";
            }
        }
        return s;
    }

    protected static String vectorUserToSelect(Vector<User> v, String name, String selected) {
        String s = "";
        if (v != null) {
            s += "<select name=\"" + name + "\">\n";
            for (User i : v) {
                s += "<option value=\"" + i.getUsername() + "\" " + ((i.getUsername().equals(selected)) ? "selected" : "") + ">" + i.getUsername() + "</option>\n";
            }
            s += "</select>\n";
        }
        return s;
    }

    @Override
    final protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    final protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
