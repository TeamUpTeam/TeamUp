package com.teamup.teamup;

import java.util.ArrayList;

/**
 * Created by Robbie on 2/22/2016.
 */
public class Project {
    public String projectName;
    public String projectDescription;
    public User teamLeader;
    public ArrayList <User> teamMembers = new ArrayList<>();
    public ArrayList <Task> currentTasks = new ArrayList<>();
    public boolean TLaddMems; // Option for Team Leader being the only one to add members
    public boolean TLaddTasks; // Option for Team Leader being the only one to add Tasks

    public Project(String projectName, String projectDescription, User teamLeader, ArrayList <User> teamMembers, ArrayList <Task> currentTasks, boolean TLaddMems, boolean TLaddTasks )
    {
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.teamLeader = teamLeader;
        this.teamMembers = teamMembers;
        this.currentTasks = currentTasks;
        this.TLaddMems = TLaddMems;
        this.TLaddTasks = TLaddTasks;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Project: " + projectName);
        sb.append("\tDescription: " + projectDescription);
        sb.append("\tTeam Leader: " + teamLeader);
        sb.append("\tTeam Members: ");
        for(User u:teamMembers) {
            sb.append(u.userName + ", ");
        }
        sb.append("\tCurrent Tasks: " );
        for(Task t:currentTasks) {
            sb.append(t.taskName + ", ");
        }
        return sb.toString();
    }
}
