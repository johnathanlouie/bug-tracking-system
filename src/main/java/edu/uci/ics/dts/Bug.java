/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uci.ics.dts;

/**
 *
 * @author root
 */
public class Bug {

    private boolean status;
    private int priority;
    private String assignee;
    private String summary;
    private String description;
    private int id;

    public Bug(boolean status, int priority, String assignee, String summary, String description, int id) {
        this.status = status;
        this.priority = priority;
        this.assignee = assignee;
        this.summary = summary;
        this.description = description;
        this.id = id;
    }

    public boolean getStatus() {
        return status;
    }

    public int getPriority() {
        return priority;
    }

    public String getAssignee() {
        return assignee;
    }

    public String getSummary() {
        return summary;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Bug{" + "status=" + status + ", priority=" + priority + ", assignee=" + assignee + ", summary=" + summary + ", description=" + description + ", id=" + id + '}';
    }
}
