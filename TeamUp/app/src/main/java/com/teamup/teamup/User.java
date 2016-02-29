package com.teamup.teamup;

import org.json.JSONException;
import org.json.JSONObject;

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

    public String server_URL = "teamupserver3.mybluemix.net/api/query?query=";

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

    /*
     *  Returns the projectID if the new project was created, and 0 if there was a problem
     * (String projectName, int projectID, String projectDescription, User teamLeader, ArrayList<User> teamMembers, ArrayList<Task> currentTasks, boolean TLaddMems, boolean TLaddTasks) {

     */
    public int createProject (String pName, String pDescription, User creator)
    {
        //String query = server_URL + "insert^into^project^(project_name,project_manager_user_id)^values^('"+pName+"','"+creator.userID+");";
        JSONObject projectRequest = new JSONObject();

        try{
            projectRequest.put("project_name",pName);
        }catch(JSONException e){
            e.printStackTrace();
        }

        try{
            projectRequest.put("project_manager_user_id",creator.userID);
        }catch(JSONException e){
            e.printStackTrace();
        }


        return 0;
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

    /*
     *  View list of projects given user
     */

    /*
     *  Returns true if the project was removed, and false if there was a problem
     */
    public boolean deleteAccount ()
    {
        //httpclient request here
        if(true) {
            // Remove User from DB
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
