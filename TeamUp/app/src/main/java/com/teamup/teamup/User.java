package com.teamup.teamup;

import java.util.ArrayList;

/**
 * Created by Robbie on 2/22/2016.
 */
public class User {
    public String userName;
    // public String email; if time permits
    public ArrayList <Project> projectList = new ArrayList<>();

    public User(String userName, ArrayList <Project> projectList)
    {
        this.userName = userName;
        this.projectList = projectList;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Username: " + userName);
        sb.append("\tProject List: ");
        for(Project p:projectList) {
            sb.append(p.projectName + ", ");
        }

        return sb.toString();
    }
}
