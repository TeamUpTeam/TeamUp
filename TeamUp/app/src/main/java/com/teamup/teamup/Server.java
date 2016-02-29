package com.teamup.teamup;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Robbie on 2/29/2016.
 */
public class Server {
    private static final Server INSTANCE = new Server();
    private static String server_URL = "teamupserver3.mybluemix.net/api/query?query=";

    private Server() { }

    /* Only use one server object for entire app */
    public static Server getInstance() {
        return INSTANCE;
    }

    /*
     *  Returns the projectID if the new project was created, and 0 if there was a problem
     * (String projectName, int projectID, String projectDescription, User teamLeader, ArrayList<User> teamMembers, ArrayList<Task> currentTasks, boolean TLaddMems, boolean TLaddTasks) {

     */
    public int createProject (String pName, String pDescription, User creator, Context context)
    {
        final String projectName = pName;
        final String projectMangerUserID = Integer.toString(creator.userID);

        String url = server_URL + "insert^into^project^(project_name,project_manager_user_id)^values^('"+projectName+"','"+projectMangerUserID+");";
        RequestQueue queue = Volley.newRequestQueue(context);
        // Request a string response
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", response );
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("project_name", projectName);
                params.put("project_manager_user_id",projectMangerUserID );
                return params;
            }
        };
        queue.add(postRequest);




        return 0;
    }

    /*
     *  Returns true if the project was removed, and false if there was a problem
     */
    public boolean deleteProject (Project proj)
    {
        //httpclient request here
        if(true) {
            //projectList.remove(proj);
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


}
