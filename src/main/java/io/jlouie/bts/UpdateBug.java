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

@WebServlet(name = "UpdateBug", urlPatterns = {"/UpdateBug"})
public class UpdateBug extends LoginServlet {

    @Override
    protected LoginServletHelper mainMethod(HttpServletRequest request, HttpServletResponse response) {
        Bug bug = new Bug();
        bug.setAssignee(request.getParameter("assignee"));
        bug.setId(Integer.parseInt(request.getParameter("id")));
        bug.setStatus(Integer.parseInt(request.getParameter("status")) != 0);
        bug.setDescription(request.getParameter("description"));
        bug.setSummary(request.getParameter("summary"));
        bug.setPriority(Integer.parseInt(request.getParameter("priority")));
        DatabaseHandler.updateDefect(bug);
        request.setAttribute("message", "You have successfully updated a defect.");
        String forwardPage = "/updatebugresults.jsp";
        return new LoginServletHelper(request, response, forwardPage);
    }

}
