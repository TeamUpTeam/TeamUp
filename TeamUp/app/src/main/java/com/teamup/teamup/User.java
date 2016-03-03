package com.teamup.teamup;

import java.util.ArrayList;

/**
 * Created by Robbie on 2/22/2016.
 */
public class User {
    public String userName;
    public String firstName;
    public String lastName;
    public int userID;
    public String userEmail;
    public String contactPhone;
    public ArrayList <Project> projectList = new ArrayList<>();

    public User(String userName, String firstName, String lastName, int userID, String userEmail, String contactPhone, ArrayList <Project> projectList)
    {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userID = userID;
        this.userEmail = userEmail;
        this.contactPhone = contactPhone;
        this.projectList = projectList;
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
