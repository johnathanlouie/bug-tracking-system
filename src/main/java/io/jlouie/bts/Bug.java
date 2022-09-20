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
