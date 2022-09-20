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
