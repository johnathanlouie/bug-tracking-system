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
    private static Vector<Vector<String>> query(String sql) {
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
            Vector<Vector<String>> vector = new Vector<>();
            if (r == null) {
                return vector;
            }
            ResultSetMetaData rsmd = r.getMetaData();
            int column = rsmd.getColumnCount();
            while (r.next()) {
                Vector<String> v = new Vector<>();
                for (int i = 1; i <= column; i++) {
                    v.add(r.getString(i));
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

    protected static User validateLogin(String username, String password) {
        String sql = String.format("SELECT username, password, email FROM accounts WHERE username='%s' AND password='%s' LIMIT 1;", username, password);
        Vector<Vector<String>> v = query(sql);
        if (v.size() != 1) {
            return null;
        }
        Vector<String> foundUser = v.get(0);
        User user = new User();
        user.setUsername(foundUser.get(0));
        user.setPassword(foundUser.get(1));
        user.setEmail(foundUser.get(2));
        return user;
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
        Vector<Vector<String>> rawDefectInfo = query("SELECT id, status, assignee, summary, description, priority FROM bugs;");
        Vector<Bug> bugList = new Vector<>();
        Bug bug;
        for (Vector<String> i : rawDefectInfo) {
            bug = new Bug();
            bug.setId(Integer.parseInt(i.get(0)));
            bug.setStatus(Integer.parseInt(i.get(1)) != 0);
            bug.setAssignee(i.get(2));
            bug.setSummary(i.get(3));
            bug.setDescription(i.get(4));
            bug.setPriority(Integer.parseInt(i.get(5)));
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
        Vector<Vector<String>> v = query("SELECT username, password, email FROM accounts;");
        Vector<User> v2 = new Vector<>();
        User user;
        for (Vector<String> i : v) {
            user = new User();
            user.setUsername(i.get(0));
            user.setPassword(i.get(1));
            user.setEmail(i.get(2));
            v2.add(user);
        }
        return v2;
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
