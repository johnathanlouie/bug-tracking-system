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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Johnathan Louie
 */
public class DatabaseHandler {

    private static Vector<HashMap<String, Object>> toHashMap(ResultSet results) {
        Vector<HashMap<String, Object>> returnValue = new Vector<>();
        if (results == null) {
            return returnValue;
        }
        try {
            ResultSetMetaData metadata = results.getMetaData();
            int columnCount = metadata.getColumnCount();
            while (results.next()) {
                HashMap<String, Object> rowData = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metadata.getColumnName(i);
                    rowData.put(columnName, results.getObject(i));
                }
                returnValue.add(rowData);
            }
            return returnValue;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Sends a query to the database and returns the results.
     */
    private static Vector<HashMap<String, Object>> query(String sql) {
        String url = System.getenv("MYSQL_URL");
        String username = System.getenv("MYSQL_USER");
        String password = System.getenv("MYSQL_PASSWORD");
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
        try ( Connection connection = DriverManager.getConnection(url, username, password);  Statement statement = connection.createStatement()) {
            statement.execute(sql);
            ResultSet results = statement.getResultSet();
            return toHashMap(results);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }

    private static Vector<User> toUsers(Vector<HashMap<String, Object>> sqlResults) {
        Vector<User> users = new Vector<>();
        for (HashMap<String, Object> foundUser : sqlResults) {
            User user = new User();
            user.setUsername((String) foundUser.get("username"));
            user.setPassword((String) foundUser.get("password"));
            user.setEmail((String) foundUser.get("email"));
        }
        return users;
    }

    protected static User validateLogin(String username, String password) {
        String sql = String.format("SELECT username, password, email FROM accounts WHERE username='%s' AND password='%s' LIMIT 1;", username, password);
        Vector<User> users = toUsers(query(sql));
        if (users.size() != 1) {
            return null;
        }
        return users.firstElement();
    }

    protected static void insertBug(int priority, String summary, String description, String username) {
        String sql = String.format("INSERT INTO bugs (id, status, assignee, summary, description, priority) VALUES (NULL, 1, '%s', '%s', '%s', %d);", username, summary, description, priority);
        query(sql);
    }

    protected static void insertUser(String username, String password, String email) {
        String sql = String.format("INSERT INTO accounts (username, password, email) VALUES ('%s', '%s', '%s');", username, password, email);
        query(sql);
    }

    private static Vector<Bug> toBugs(Vector<HashMap<String, Object>> sqlResults) {
        Vector<Bug> bugs = new Vector<>();
        Bug bug;
        for (HashMap<String, Object> bugData : sqlResults) {
            bug = new Bug();
            bug.setId((Long) bugData.get("id"));
            bug.setStatus((Boolean) bugData.get("status"));
            bug.setAssignee((String) bugData.get("assignee"));
            bug.setSummary((String) bugData.get("summary"));
            bug.setDescription((String) bugData.get("description"));
            bug.setPriority((Integer) bugData.get("priority"));
            bugs.add(bug);
        }
        return bugs;
    }

    protected static Vector<Bug> getAllDefects() {
        String sql = "SELECT id, status, assignee, summary, description, priority FROM bugs;";
        return toBugs(query(sql));
    }

    protected static Vector<Bug> getOpenDefects() {
        String sql = "SELECT id, status, assignee, summary, description, priority FROM bugs WHERE status=1;";
        return toBugs(query(sql));
    }

    protected static Vector<Bug> getClosedDefects() {
        String sql = "SELECT id, status, assignee, summary, description, priority FROM bugs WHERE status=0;";
        return toBugs(query(sql));
    }

    protected static Vector<Bug> getAssignments(String username) {
        String sql = String.format("SELECT id, status, assignee, summary, description, priority FROM bugs WHERE assignee='%s';", username);
        return toBugs(query(sql));
    }

    protected static Vector<User> getAllUsers() {
        String sql = "SELECT username, password, email FROM accounts;";
        return toUsers(query(sql));
    }

    protected static User getUserByName(String username) {
        String sql = String.format("SELECT username, password, email FROM accounts WHERE username='%s' LIMIT 1;", username);
        Vector<User> users = toUsers(query(sql));
        if (users.size() != 1) {
            return null;
        }
        return users.firstElement();
    }

    protected static Bug getBugById(long id) {
        String sql = String.format("SELECT id, status, assignee, summary, description, priority FROM bugs WHERE id=%d LIMIT 1;", id);
        Vector<Bug> bugs = toBugs(query(sql));
        if (bugs.size() != 1) {
            return null;
        }
        return bugs.firstElement();
    }

    protected static void updateDefect(Bug bug) {
        String s = String.format("UPDATE bugs SET assignee='%s', priority=%d, summary='%s', description='%s', status=%d WHERE id=%d;", bug.getAssignee(), bug.getPriority(), bug.getSummary(), bug.getDescription(), bug.isStatus() ? 1 : 0, bug.getId());
        query(s);
    }

}
