package com.teamup.teamup;

import java.util.ArrayList;

/**
 * Created by Robbie on 2/22/2016.
 */
public class User {
    public String userName;
    public int userID;
    public String userEmail;
    public ArrayList <Project> projectList = new ArrayList<>();

    public User(String userName, int userID, String userEmail, ArrayList <Project> projectList)
    {
        this.userName = userName;
        this.userID = userID;
        this.userEmail = userEmail;
        this.projectList = projectList;
    }

    /*
     *  Returns true if the new project was created, and false if there was a problem
     */
    public boolean createProject (String pName, String pDescription)
    {
        //httpclient request here
        if(true) {
            // Project newProject = new Project(pName,pDescription);
            // newProject.projectName = pName;
            // Will finish this portion once I can get ProjectID from database
            return true;
        }

        return false;
    }

    /*
     *  Returns true if the project was removed, and false if there was a problem
     */
    public boolean deleteProject (Project proj)
    {
        //httpclient request here
        if(true) {
            projectList.remove(proj);
            return true;
        }

        return false;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Username: " + userName);
        sb.append("\tUser ID: " + userID);
        sb.append("\tUser Email: " + userEmail);
        sb.append("\tProject List: ");
        for(Project p:projectList) {
            sb.append(p.projectName + ", ");
        }

        return sb.toString();
    }
}
