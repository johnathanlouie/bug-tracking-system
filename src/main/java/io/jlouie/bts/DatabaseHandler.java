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

    // sends a query to the database and returns the results
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
            if (r != null) {
                ResultSetMetaData rsmd = r.getMetaData();
                int column = rsmd.getColumnCount();
                while (r.next()) {
                    Vector<String> v = new Vector<>();
                    for (int i = 1; i <= column; i++) {
                        v.add(r.getString(i));
                    }
                    vector.add(v);
                }
            }
            // END finish making vector
            s.close();
            c.close();
            return vector;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    protected static User validateLogin(String username, String password) {
        Vector<Vector<String>> v = query("SELECT * FROM accounts WHERE username = '" + username + "' AND password = '" + password + "'");
        if (v != null) {
            return new User(v.get(0).get(0), v.get(0).get(1), v.get(0).get(2));
        }
        return null;
    }

    protected static void insertBug(int priority, String summary, String description, String username) {
        query("INSERT INTO dts . bugs (id, status, assignee, summary, description, priority) VALUES (NULL, '1', '" + username + "', '" + summary + "', '" + description + "', '" + priority + "')");
    }

    protected static void insertUser(String username, String password, String email) {
        query("INSERT INTO dts.accounts (username, password, email) VALUES ('" + username + "', '" + password + "', '" + email + "')");
    }

    protected static Vector<Bug> getAllDefects() {
        Vector<Vector<String>> rawDefectInfo = query("SELECT * FROM bugs");
        if (rawDefectInfo != null) {
            Vector<Bug> bugList = new Vector<>();
            Bug bug;
            for (Vector<String> i : rawDefectInfo) {
                bug = new Bug();
                bug.setStatus(Integer.parseInt(i.get(1)) != 0);
                bug.setPriority(Integer.parseInt(i.get(5)));
                bug.setAssignee(i.get(2));
                bug.setSummary(i.get(3));
                bug.setDescription(i.get(4));
                bug.setId(Integer.parseInt(i.get(0)));
                bugList.add(bug);
            }
            return bugList;
        }
        return null;
    }

    protected static Vector<Bug> getOpenDefects() {
        Vector<Bug> v = getAllDefects();
        Vector<Bug> v2 = new Vector<>();
        for (Bug i : v) {
            if (i.isStatus()) {
                v2.add(i);
            }
        }
        if (v2.size() > 0) {
            return v2;
        }
        return null;
    }

    protected static Vector<Bug> getClosedDefects() {
        Vector<Bug> v = getAllDefects();
        Vector<Bug> v2 = new Vector<>();
        for (Bug i : v) {
            if (!i.isStatus()) {
                v2.add(i);
            }
        }
        if (v2.size() > 0) {
            return v2;
        }
        return null;
    }

    protected static Vector<Bug> getAssignments(String username) {
        Vector<Bug> v = getAllDefects();
        Vector<Bug> v2 = new Vector<>();
        for (Bug i : v) {
            if (i.getAssignee().equals(username)) {
                v2.add(i);
            }
        }
        if (v2.size() > 0) {
            return v2;
        }
        return null;
    }

    protected static Vector<User> getAllUsers() {
        Vector<Vector<String>> v = query("SELECT * FROM accounts");
        if (v != null) {
            Vector<User> v2 = new Vector<>();
            for (Vector<String> i : v) {
                v2.add(new User(i.get(0), i.get(1), i.get(2)));
            }
            return v2;
        }
        return null;
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
        if (v != null) {
            for (Bug i : v) {
                if (i.getId() == id) {
                    return i;
                }
            }
        }
        return null;
    }

    protected static void updateDefect(Bug bug) {
        String s = "UPDATE dts . bugs SET assignee='" + bug.getAssignee() + "', priority='" + bug.getPriority() + "', summary='" + bug.getSummary() + "', description='" + bug.getDescription() + "', status='" + ((bug.isStatus() == true) ? 1 : 0) + "' WHERE bugs . id=" + bug.getId();
        query(s);
    }

}
