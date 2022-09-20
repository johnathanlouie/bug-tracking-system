/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uci.ics.dts;

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
 * @author user
 */
public class DatabaseHandler {

    // sends a query to the database and returns the results
    private static Vector<Vector<String>> query(String sql) {
        try {
            // load driver
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            // connect to database
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/dts", "root", "");
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
            if (vector.size() > 0) {
                return vector;
            }
            return null;
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
            boolean status;
            int priority;
            String assignee;
            String summary;
            String description;
            int id;
            for (Vector<String> i : rawDefectInfo) {
                status = Integer.parseInt(i.get(1)) != 0;
                //System.out.println(i.get(1));
                priority = Integer.parseInt(i.get(5));
                assignee = i.get(2);
                summary = i.get(3);
                description = i.get(4);
                id = Integer.parseInt(i.get(0));
                bug = new Bug(status, priority, assignee, summary, description, id);
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
            if (i.getStatus()) {
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
            if (!i.getStatus()) {
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
        String s = "UPDATE dts . bugs SET assignee='" + bug.getAssignee() + "', priority='" + bug.getPriority() + "', summary='" + bug.getSummary() + "', description='" + bug.getDescription() + "', status='" + ((bug.getStatus() == true) ? 1 : 0) + "' WHERE bugs . id=" + bug.getId();
        query(s);
    }

    /*public static void main(String[] args) {
     getAssignments("admin");
     }*/
}
