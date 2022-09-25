/*
 * Copyright 2015 Johnathan Louie.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.jlouie.bts;

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
