package com.teamup.teamup;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Robbie on 2/29/2016.
 */
public class Server {
    private static final Server INSTANCE = new Server();
    private static String server_URL = "http://teamupserver3.mybluemix.net/api/query?query=";

    public Server() { }

    /* Only use one server object for entire app */
    public static Server getInstance() {
        return INSTANCE;
    }

    /*
     *  Returns the pid if the new project was created, and 1 if there was a problem
     * (String projectName, int projectID, String projectDescription, User teamLeader, ArrayList<User> teamMembers, ArrayList<Task> currentTasks, boolean TLaddMems, boolean TLaddTasks) {

     */
    public int createProject (String pName, String pDescription, Context context)
    {
        final String projectName = pName;
        final String projectDescription = pDescription;

        String url = server_URL + "insert^into^project^(project_name,project_description)^values^('"+projectName+"','"+projectDescription+"');";
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
                        Log.d("Error.Response", "Response Error" );
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("project_name", projectName);
                params.put("project_description", projectDescription);
                return params;
            }
        };
        queue.add(postRequest);

        return 0;
    }
    /*
     *  Sets the project_id and user_id in Project_Manager table
     */
    public int setProjectMan(String creatorID, String projectID,Context context){
        
        final String project_ID = projectID;
        final String projectMangerUserID = creatorID;

        String url = server_URL + "insert^into^project_manager^(project_id,user_id)^values^('"+project_ID+"','"+projectMangerUserID+"');";
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
                        Log.d("Error.Response", "Server Response Error" );
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("project_id", project_ID);
                params.put("user_id", projectMangerUserID);
                return params;
            }
        };
        queue.add(postRequest);
        return 0;
    }



    /*
     *  Sets the project description
     */
    public int setProjectDescription (int project_ID, String newDescription, Context context)
    {
        final String projectDesc = newDescription;
        final String projectID = Integer.toString(project_ID);

        String url = server_URL + "update^project^set^project_description='"+projectDesc+"'^where^project_id='"+projectID+"';";
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
                        Log.d("Error.Response", "Response Error" );
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("project_description", projectDesc);
                return params;
            }
        };
        queue.add(postRequest);

        return 0;
    }

    /*
     *  Adds a User to a specific Project's TeamMembers ArrayList
     */
    public int addMember (int project_ID, int user_ID, Context context)
    {
        final String projectID = Integer.toString(project_ID);
        final String userID = Integer.toString(user_ID);


        String url = server_URL + "insert^into^projectteammember^(user_id,project_id)^values^('"+projectID+"','"+userID+");";
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
                        Log.d("Error.Response", "Response Error" );
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("project_id", projectID);
                params.put("user_id",userID );
                return params;
            }
        };
        queue.add(postRequest);

        return 0;
    }

    /*
     *  Returns the project name using proect_id  ****Neil do it like this for all GET methods****
     */
    public int getProjectName(int project_ID, Context context) {

        // setup variables to be used
        String url;
        final String projectID = Integer.toString(project_ID);
        // prepare the Request
        RequestQueue queue = Volley.newRequestQueue(context);

        // this url is the query being sent to the database
        url = server_URL + "select^project_name^from^project^where(project_id='"+projectID+"');";


        JsonObjectRequest getRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            response = response.getJSONObject("args");
                            String projectID = response.getString("project_id");
                            Log.d("project_id: ",projectID);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Error handling
                                Log.d("Error.Response", "Response Error" );
                            }
                        });

        // add it to the RequestQueue
        queue.add(getRequest);
        return 0;
    }

    /*
    *  Returns the project name using proect_id  ****Neil do it like this for all GET methods****
    */
    public int getProjectID(String project_Name, Context context) {

        // setup variables to be used
        String url;
        final String projectName = project_Name;
        // prepare the Request
        RequestQueue queue = Volley.newRequestQueue(context);

        // this url is the query being sent to the database
        url = server_URL + "select^project_id^from^project^where(project_name='"+projectName+"');";


        JsonObjectRequest getRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            response = response.getJSONObject("args");
                            String projectID = response.getString("project_id");
                            Log.d("project_id: ",projectID);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Error handling
                                Log.d("Error.Response", "Response Error" );
                            }
                        });

        // add it to the RequestQueue
        queue.add(getRequest);
        return 0;
    }

    /*
     *  Returns true if the project was removed, and false if there was a problem
     */
    public int deleteProject (int project_ID, Context context)
    {

                final String projectID = Integer.toString(project_ID);
                RequestQueue queue = Volley.newRequestQueue(context);
                String url = server_URL + "delete^from^project^where^project_id='"+projectID+"';";
                 JsonObjectRequest getRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            response = response.getJSONObject("args");
                            String projectID = response.getString("project_id");
                            Log.d("project_id: ",projectID);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Error handling
                                Log.d("Error.Response", "Response Error" );
                            }


                        });

        // add it to the RequestQueue
        queue.add(getRequest);


        //httpclient request here
      //  if(true) {
            //projectList.remove(proj);
        //    return true;
        //}

        return 0;
    }

    /*
    *  Returns true if the member was removed from the group, and false if there was a problem
    */
    public int removeMember (int oldMember_ID, int project_ID, Context context)
    {
        //httpclient request here

        final String projectID = Integer.toString(project_ID);
        final String UserID = Integer.toString(oldMember_ID);

        String url = server_URL + "delete^projectProjectTeamMember^where^project_id='"+projectID+"';";
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
                        Log.d("Error.Response", "Response Error" );
                    }
                });
        queue.add(postRequest);




        return 0;
    }

    /*
     *  Returns true if the team leader was successfully changed to the new one, and false is something went wrong
     */
    public boolean changeTeamLeader (User newTL)
    {
        //httpclient request here
        /*
            final String projectMangerUserID = Integer.toString(this.userID);
            final String projectID = Integer.toString(this.projectID);
           String url = server_URL + "update^project_manager^set^user_id='"+projectDesc+"'^where^project_id='"+projectID+"';";
                   RequestQueue queue = Volley.newRequestQueue(context);
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
                        Log.d("Error.Response", "Response Error" );
                    }
                }
        ) {
         @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("project_description", projectDesc);
                return params;
            }
        };
        queue.add(postRequest);
         */


        return false;
    }

    /*
     *  Returns true if all members can now add other members, and false if something went wrong
     *  dont know which variable to set Neil
     */
    public boolean setTLaddMemsF ()
    {
        //httpclient request here
        /*
        final String projectID = Integer.toString(this.project_ID);

        String url = server_URL + "update^project^set^TLaddMems='"+false+"'^where^project_id='"+projectID+"';";
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
                        Log.d("Error.Response", "Response Error" );
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("project_description", projectDesc);
                return params;
            }
        };
        queue.add(postRequest);

         */


        return false;
    }

    /*
     *  Returns true if only Team Leader can add other members, and false if something went wrong
     */
    public boolean setTLaddMemsT ()
    {
        //httpclient request here
        /*
        final String projectID = Integer.toString(this.project_ID);

        String url = server_URL + "update^project^set^TLaddMems='"+true+"'^where^project_id='"+projectID+"';";
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
                        Log.d("Error.Response", "Response Error" );
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("project_description", projectDesc);
                return params;
            }
        };
        queue.add(postRequest);

         */


        return false;
    }

    /*
     *  Returns true if all members can now add tasks without approval, and false if something went wrong
     */
    public boolean setTLaddTasksF ()
    {
        //httpclient request here
        /*
        final String projectID = Integer.toString(this.project_ID);

        String url = server_URL + "update^project^set^TLaddTasks='"+false+"'^where^project_id='"+projectID+"';";
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
                        Log.d("Error.Response", "Response Error" );
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("project_description", projectDesc);
                return params;
            }
        };
        queue.add(postRequest);

         */


        return false;
    }

    /*
     *  Returns true if only Team Leader can add tasks, and false if something went wrong
     */
    public boolean setTLaddTasksT ()
    {
        //httpclient request here
        /*
        final String projectID = Integer.toString(this.project_ID);

        String url = server_URL + "update^project^set^TLaddTasks='"+true+"'^where^project_id='"+projectID+"';";
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
                        Log.d("Error.Response", "Response Error" );
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("project_description", projectDesc);
                return params;
            }
        };
        queue.add(postRequest);

         */


        return false;
    }

    /*
     *  Returns true if the account was removed, and false if there was a problem
     */
    public boolean deleteAccount (int oldMember_ID, Context context)
    {
        final String UserID = Integer.toString(oldMember_ID);

        String url = server_URL + "delete^User^where^usert_id='"+UserID+"';";
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
                        Log.d("Error.Response", "Response Error" );
                    }
                });
        queue.add(postRequest);
        //httpclient request here
        if(true) {
            // Remove User from DB
            return true;
        }

        return false;
    }


}
