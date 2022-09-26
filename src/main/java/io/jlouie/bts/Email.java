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

import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Email", urlPatterns = {"/Email"})
public class Email extends LoginServlet {

    @Override
    protected LoginServletHelper mainMethod(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("bugid"));
        request.setAttribute("bugId", id);
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("user");
        List<User> users = DatabaseHandler.getAllUsers();
        request.setAttribute("users", users);
        request.setAttribute("currentUsers", currentUser);
        return new LoginServletHelper(request, response, "/email.jsp");
    }

}
