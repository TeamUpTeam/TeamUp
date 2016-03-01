package com.teamup.teamup;

import java.util.ArrayList;

/**
 * Created by Robbie on 2/22/2016.
 */
public class Project {
    public String projectName;
    public int projectID;
    public String projectDescription;
    public User teamLeader;
    public ArrayList<User> teamMembers = new ArrayList<>();
    public ArrayList<Task> currentTasks = new ArrayList<>();
    public boolean TLaddMems; // Option for Team Leader being the only one to add members
    public boolean TLaddTasks; // Option for Team Leader being the only one to add Tasks

    public Project(String projectName, int projectID, String projectDescription, User teamLeader, ArrayList<User> teamMembers, ArrayList<Task> currentTasks, boolean TLaddMems, boolean TLaddTasks) {
        this.projectName = projectName;
        this.projectID = projectID;
        this.projectDescription = projectDescription;
        this.teamLeader = teamLeader;
        this.teamMembers = teamMembers;
        this.currentTasks = currentTasks;
        this.TLaddMems = TLaddMems;
        this.TLaddTasks = TLaddTasks;
    }
    /*
     *  Returns the project name
     */
    public String getProjectName(Project proj) {
        return proj.projectName;
    }

    /*
     *  Returns the project id
     */
    public int getProjectID(Project proj) {
        return proj.projectID;
    }

    /*
     *  Returns the Team Leader's information
     */
    public User getTeamLeader(Project proj) {
        return proj.teamLeader;
    }

    /*
     *  Returns the project desciption
     */
    public String getProjectDescription(Project proj) {
        return proj.projectDescription;
    }

    /*
     *  Returns an ArrayList of the Team Members, including the team leader
     */
    public ArrayList<User> getTeamMembers(Project proj) {
        return proj.teamMembers;
    }

    /*
     *  Returns an ArrayList if the tasks currently being worked on
     */
    public ArrayList<Task> getCurrentTasks(Project proj)
    {
        return proj.currentTasks;
    }



    /*
     *  Returns true if the member was added to the group, and false if there was a problem
     */
    public boolean addMember (User newMember)
    {
        //httpclient request here
        if(true) {
            this.teamMembers.add(newMember);
            return true;
        }

        return false;
    }

    /*
     *  Returns true if the member was removed from the group, and false if there was a problem
     */
    public boolean removeMember (User oldMember)
    {
        //httpclient request here
        if(true) {
            this.teamMembers.remove(oldMember);
            return true;
        }

        return false;
    }

    /*
     *  Returns true if the team leader was successfully changed to the new one, and false is something went wrong
     */
    public boolean changeTeamLeader (User newTL)
    {
        //httpclient request here
        if(true) {
            this.teamLeader = newTL;
            return true;
        }

        return false;
    }

    /*
     *  Returns true if all members can now add other members, and false if something went wrong
     */
    public boolean setTLaddMemsF ()
    {
        //httpclient request here
        if(true) {
            this.TLaddMems = false;
            return true;
        }

        return false;
    }

    /*
     *  Returns true if only Team Leader can add other members, and false if something went wrong
     */
    public boolean setTLaddMemsT ()
    {
        //httpclient request here
        if(true) {
            this.TLaddMems = true;
            return true;
        }

        return false;
    }

    /*
     *  Returns true if all members can now add tasks without approval, and false if something went wrong
     */
    public boolean setTLaddTasksF ()
    {
        //httpclient request here
        if(true) {
            this.TLaddTasks = false;
            return true;
        }

        return false;
    }

    /*
     *  Returns true if only Team Leader can add tasks, and false if something went wrong
     */
    public boolean setTLaddTasksT ()
    {
        //httpclient request here
        if(true) {
            this.TLaddTasks = true;
            return true;
        }

        return false;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Project: " + projectName);
        sb.append("\tProject ID: " + projectID);
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
