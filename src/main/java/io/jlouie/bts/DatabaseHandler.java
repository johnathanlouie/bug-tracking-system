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

    /**
     * Sends a query to the database and returns the results.
     */
    private static Vector<HashMap<String, Object>> query(String sql) {
        String url = System.getenv("MYSQL_URL");
        String username = System.getenv("MYSQL_USER");
        String password = System.getenv("MYSQL_PASSWORD");
        try {
            // load driver
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            // connect to database
            Connection c = DriverManager.getConnection(url, username, password);
            // sends a sql statement
            Statement s = c.createStatement();
            s.execute(sql);
            // get the results
            ResultSet r = s.getResultSet();
            // BEGIN turn ResultSet to vector
            Vector<HashMap<String, Object>> vector = new Vector<>();
            if (r == null) {
                return vector;
            }
            ResultSetMetaData rsmd = r.getMetaData();
            int column = rsmd.getColumnCount();
            String columnName;
            while (r.next()) {
                HashMap<String, Object> v = new HashMap<>();
                for (int i = 1; i <= column; i++) {
                    columnName = rsmd.getColumnName(i);
                    v.put(columnName, r.getObject(i));
                }
                vector.add(v);
            }
            // END finish making vector
            s.close();
            c.close();
            return vector;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
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
        return users.get(0);
    }

    protected static void insertBug(int priority, String summary, String description, String username) {
        String sql = String.format("INSERT INTO bugs (id, status, assignee, summary, description, priority) VALUES (NULL, 1, '%s', '%s', '%s', %d);", username, summary, description, priority);
        query(sql);
    }

    protected static void insertUser(String username, String password, String email) {
        String sql = String.format("INSERT INTO accounts (username, password, email) VALUES ('%s', '%s', '%s');", username, password, email);
        query(sql);
    }

    protected static Vector<Bug> getAllDefects() {
        Vector<HashMap<String, Object>> rawDefectInfo = query("SELECT id, status, assignee, summary, description, priority FROM bugs;");
        Vector<Bug> bugList = new Vector<>();
        Bug bug;
        for (HashMap<String, Object> i : rawDefectInfo) {
            bug = new Bug();
            bug.setId((Long) i.get("id"));
            bug.setStatus((Boolean) i.get("status"));
            bug.setAssignee((String) i.get("assignee"));
            bug.setSummary((String) i.get("summary"));
            bug.setDescription((String) i.get("description"));
            bug.setPriority((Integer) i.get("priority"));
            bugList.add(bug);
        }
        return bugList;
    }

    protected static Vector<Bug> getOpenDefects() {
        Vector<Bug> v = getAllDefects();
        Vector<Bug> v2 = new Vector<>();
        for (Bug i : v) {
            if (i.isStatus()) {
                v2.add(i);
            }
        }
        return v2;
    }

    protected static Vector<Bug> getClosedDefects() {
        Vector<Bug> v = getAllDefects();
        Vector<Bug> v2 = new Vector<>();
        for (Bug i : v) {
            if (!i.isStatus()) {
                v2.add(i);
            }
        }
        return v2;
    }

    protected static Vector<Bug> getAssignments(String username) {
        Vector<Bug> v = getAllDefects();
        Vector<Bug> v2 = new Vector<>();
        for (Bug i : v) {
            if (i.getAssignee().equals(username)) {
                v2.add(i);
            }
        }
        return v2;
    }

    protected static Vector<User> getAllUsers() {
        String sql = "SELECT username, password, email FROM accounts;";
        return toUsers(query(sql));
    }

    protected static User getUserByName(String username) {
        Vector<User> v = getAllUsers();
        for (User i : v) {
            if (i.getUsername().equals(username)) {
                return i;
            }
        }
        return null;
    }

    protected static Bug getBugById(int id) {
        Vector<Bug> v = getAllDefects();
        for (Bug i : v) {
            if (i.getId() == id) {
                return i;
            }
        }
        return null;
    }

    protected static void updateDefect(Bug bug) {
        String s = String.format("UPDATE bugs SET assignee='%s', priority=%d, summary='%s', description='%s', status=%d WHERE id=%d;", bug.getAssignee(), bug.getPriority(), bug.getSummary(), bug.getDescription(), bug.isStatus() ? 1 : 0, bug.getId());
        query(s);
    }

}
