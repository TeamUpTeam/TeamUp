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
import java.util.Date;
/**
 * Created by Robbie on 2/29/2016.
 */
public class Server {
    private static final Server INSTANCE = new Server();
    private static String server_URL = "http://teamupserver3.mybluemix.net/api/query?query=";
    //public String pname;
    public Server() { }
    String xx;
    int uid1;
    int puid1;
    /* Only use one server object for entire app */
    public static Server getInstance() {
        return INSTANCE;
    }
    public String setpname(String yy)
    {
        xx=yy;
        return xx;
    }

    public int setuid1(int yy)
    {
        uid1=yy;
        return uid1;
    }

    public int setpid1(int yy)
    {
        puid1 = yy;
        return puid1;
    }
    /*
     *  Creates a project with the values given, creates a projectID
     */
    public int createProject (String project_Name, String project_Description, String plan_Start, Date plan_End, int pm_UserID, Context context)
    {
        final String projectName = project_Name;
        final String projectDescription = project_Description;
        final String planStart = plan_Start.toString();
        final String planEnd = plan_End.toString();
        final String pmUserID = Integer.toString(pm_UserID);


        String url = server_URL + "insert^into^project^(project_name,planned_start_date,planned_end_date,project_manager_user_id,project_description)^values^('"+projectName+"','"+planStart+"','"+planEnd+"','"+pmUserID+"','"+projectDescription+"');";
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
                params.put("planned_start_date", planStart);
                params.put("planned_end_date", planEnd);
                params.put("project_manager_user_id", pmUserID);
                params.put("project_description", projectDescription);
                return params;
            }
        };
        queue.add(postRequest);

        // gets the projectID for the Log
        getProjectID(pm_UserID, context);

        return 0;
    }


    /*
     *  Deletes the project from the database
     */
    public int deleteProject (int project_ID, Context context)
    {

        final String projectID = Integer.toString(project_ID);
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = server_URL + "delete^from^project^where^project_id='"+projectID+"';";

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


    // SET METHODS FOR PROJECT------------------------------------------------------------------------------

    /*
     *  Sets the project_manager_user_id in the Project table
     */
    public int setProjectMananger(int project_manager_user_ID, int project_ID, Context context){

        final String projectID = Integer.toString(project_ID);
        final String projectMangerUserID = Integer.toString(project_manager_user_ID);

        String url = server_URL + "update^project^set^project_manager_user_id='"+projectMangerUserID+"'^where^project_id='"+projectID+"';";
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
                params.put("project_manager_user_id", projectMangerUserID);
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
     *  Sets the actual start date of the project
     */
    public int setActualProjectStart (int project_ID, Date actual_Start_Date, Context context)
    {
        final String actualStartDate = actual_Start_Date.toString();
        final String projectID = Integer.toString(project_ID);

        String url = server_URL + "update^project^set^actual_start_date='"+actualStartDate+"'^where^project_id='"+projectID+"';";
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
                params.put("actual_start_date", actualStartDate);
                return params;
            }
        };
        queue.add(postRequest);

        return 0;
    }

    /*
     *  Sets the actual end date of the project
     */
    public int setActualProjectEnd (int project_ID, Date actual_End_Date, Context context)
    {
        final String actualEndDate = actual_End_Date.toString();
        final String projectID = Integer.toString(project_ID);

        String url = server_URL + "update^project^set^actual_end_date='"+actualEndDate+"'^where^project_id='"+projectID+"';";
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
                params.put("actual_end_date", actualEndDate);
                return params;
            }
        };
        queue.add(postRequest);

        return 0;
    }

    // GET METHODS FOR PROJECT------------------------------------------------------------------------------

    /*
     *  Returns the project name using project_id
     */
    public String getProjectName(int project_ID, Context context) {

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
                            String projectName = response.getString("project_name");
                            setpname(projectName);
                            Log.d("project_name: ",projectName);
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
        return xx;
    }


    /*
    *  Returns the project_id using project_manager_user_id
    */
    public int getProjectID(int project_manager_user_id, Context context) {

        // setup variables to be used
        String url;
        final String pmUserID = Integer.toString(project_manager_user_id);

        // prepare the Request
        RequestQueue queue = Volley.newRequestQueue(context);

        // this url is the query being sent to the database
        url = server_URL + "select^project_id^from^project^where^(project_manager_user_id='"+pmUserID+"');";


        JsonObjectRequest getRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            response = response.getJSONObject("args");
                            String projectID = response.getString("project_id");
                            setpid1(Integer.parseInt(projectID));
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
        return puid1;
    }


    /*
     *  Returns the actual start date using project_id
     */
    public int getActualStartDate(int project_ID, Context context) {

        // setup variables to be used
        String url;
        final String projectID = Integer.toString(project_ID);
        // prepare the Request
        RequestQueue queue = Volley.newRequestQueue(context);

        // this url is the query being sent to the database
        url = server_URL + "select^actual_start_date^from^project^where(project_id='"+projectID+"');";

        JsonObjectRequest getRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            response = response.getJSONObject("args");
                            String actual_start_date = response.getString("actual_start_date");
                            Log.d("actual_start_date: ",actual_start_date);
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
     *  Returns the actual end date using project_id
     */
    public int getActualEndDate(int project_ID, Context context) {

        // setup variables to be used
        String url;
        final String projectID = Integer.toString(project_ID);
        // prepare the Request
        RequestQueue queue = Volley.newRequestQueue(context);

        // this url is the query being sent to the database
        url = server_URL + "select^actual_end_date^from^project^where(project_id='"+projectID+"');";

        JsonObjectRequest getRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            response = response.getJSONObject("args");
                            String actual_end_date = response.getString("actual_end_date");
                            Log.d("actual_end_date: ",actual_end_date);
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
     *  Returns the planned start date using project_id
     */
    public int getPlannedStartDate(int project_ID, Context context) {

        // setup variables to be used
        String url;
        final String projectID = Integer.toString(project_ID);
        // prepare the Request
        RequestQueue queue = Volley.newRequestQueue(context);

        // this url is the query being sent to the database
        url = server_URL + "select^planned_start_date^from^project^where(project_id='"+projectID+"');";

        JsonObjectRequest getRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            response = response.getJSONObject("args");
                            String planned_start_date = response.getString("planned_start_date");
                            Log.d("planned_start_date: ",planned_start_date);
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
     *  Returns the actual end date using project_id
     */
    public int getPlannedEndDate(int project_ID, Context context) {

        // setup variables to be used
        String url;
        final String projectID = Integer.toString(project_ID);
        // prepare the Request
        RequestQueue queue = Volley.newRequestQueue(context);

        // this url is the query being sent to the database
        url = server_URL + "select^planned_end_date^from^project^where(project_id='"+projectID+"');";

        JsonObjectRequest getRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            response = response.getJSONObject("args");
                            String planned_end_date = response.getString("planned_end_date");
                            Log.d("planned_end_date: ",planned_end_date);
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

    // METHODS FOR PROJECT TEAM MEMBER------------------------------------------------------------------------------

    /*
     *  Adds a user ID to projectTeamMember by a projectID
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
    *  Removes a user ID from projectTeamMember using projectID
    */
    public int removeMember (int oldMember_ID, int project_ID, Context context)
    {
        //httpclient request here

        final String projectID = Integer.toString(project_ID);
        final String UserID = Integer.toString(oldMember_ID);

        String url = server_URL + "delete^from^projectProjectTeamMember^where^project_id='"+projectID+"'^and^user_id='"+UserID+"';";
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





    // TASK--------------------------------------------------------------------------------------------

    /*
     *  Creates a new task with the set variables
    */
    public int createTask (String task_Name, String task_Desc, int project_id, Date plan_Start, Date plan_End, int plan_man_hours, Context context)
    {
        final String taskName = task_Name;
        final String taskDesc = task_Desc;
        final String projectID = Integer.toString(project_id);
        final String planStart = plan_Start.toString();
        final String planEnd = plan_End.toString();
        final String planManHours = Integer.toString(plan_man_hours);

        String url = server_URL + "insert^into^task^(task_name,task_desc)^values^('"+taskName+"','"+taskDesc+"');";
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
                params.put("task_name", taskName);
                params.put("task_desc", taskDesc);
                params.put("project_id", projectID);
                params.put("planned_start_date", planStart);
                params.put("planned_end_date", planEnd);
                params.put("planned_man_hours", planManHours);
                return params;
            }
        };
        queue.add(postRequest);

        // gets the TaskID for the Log
        getTaskID(project_id, context);

        return 0;
    }

    // SET METHODS FOR TASK------------------------------------------------------------------------------



    // GET METHODS FOR TASK------------------------------------------------------------------------------
    /*
     *  Gets the TaskID
     */
    public int getTaskID(int project_id, Context context)
    {
        String url;
        final String projectID = Integer.toString(project_id);
        // prepare the Request
        RequestQueue queue = Volley.newRequestQueue(context);

        // this url is the query being sent to the database
        url = server_URL + "select^task_id^from^task^where^(project_id='"+projectID+"');";
        JsonObjectRequest getRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            response = response.getJSONObject("args");
                            String taskID = response.getString("task_id");
                            Log.d("task_id: ",taskID);
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
     *  Gets the task name
     */
    public int getTaskName(int task_ID, Context context)
    {
        String url;
        final String taskID = Integer.toString(task_ID);
        // prepare the Request
        RequestQueue queue = Volley.newRequestQueue(context);

        // this url is the query being sent to the database
        url = server_URL + "select^task_id^from^task^where(task_id='"+taskID+"');";
        JsonObjectRequest getRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            response = response.getJSONObject("args");
                            String taskID = response.getString("task_name");
                            Log.d("task_name: ",taskID);
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
    public String getTaskDescription(int task_ID, Context context) {
        String url;
        final String taskID = Integer.toString(task_ID);
        // prepare the Request
        RequestQueue queue = Volley.newRequestQueue(context);

        // this url is the query being sent to the database
        url = server_URL + "select^task_desc^from^task^where(task_id='" + taskID + "');";
        JsonObjectRequest getRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            response = response.getJSONObject("args");
                            String taskID = response.getString("task_desc");
                            Log.d("task_desc: ", taskID);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Error handling
                                Log.d("Error.Response", "Response Error");
                            }
                        });

        // add it to the RequestQueue
        queue.add(getRequest);
        return url;
    }
    public int getStatusID(int task_ID, Context context) {

        // setup variables to be used
        String url;
        final String taskID = Integer.toString(task_ID);
        // prepare the Request
        RequestQueue queue = Volley.newRequestQueue(context);

        // this url is the query being sent to the database
        url = server_URL + "select^task_status_id^from^task^where(task_id='"+taskID+"');";


        JsonObjectRequest getRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            response = response.getJSONObject("args");
                            String projectID = response.getString("task_status_id");
                            Log.d("task_status_id: ",taskID);
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
        return uid1;
    }
    public Date getStartDate(int task_ID, Context context) {

        // setup variables to be used
        String url;
        Date temp=new Date(2016,3,3);//temp
        final String taskID = Integer.toString(task_ID);
        // prepare the Request
        RequestQueue queue = Volley.newRequestQueue(context);

        // this url is the query being sent to the database
        url = server_URL + "select^plannes_start_date^from^task^where(task_id='"+taskID+"');";


        JsonObjectRequest getRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            response = response.getJSONObject("args");
                            String projectID = response.getString("task_status_id");// this is wrong
                            Log.d("task_status_id: ",taskID);
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
        return temp;
    }



    // APPUSER------------------------------------------------------------------------------------------------


    /*
     *  Creates a new task with the set variables
    */
    public int createAppUser (String login_Name, String first_name, Context context)
    {
        final String loginName = login_Name;
        final String firstName = first_name;

        String url = server_URL + "insert^into^task^(task_name,task_desc)^values^('"+loginName+"','"+firstName+"');";
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
                params.put("login_name", loginName);
                params.put("first_desc", firstName);
                return params;
            }
        };
        queue.add(postRequest);

        // gets the projectID for the Log
        getUserID(loginName, context);

        return 0;
    }


    // SET METHODS FOR APPUSER------------------------------------------------------------------------------


    // GET METHODS FOR APPUSER------------------------------------------------------------------------------

    /*
     *  Gets the UserID
     */
    public int getUserID(String login_Name, Context context)
    {
        String url;
        final String loginName = login_Name;
        // prepare the Request
        RequestQueue queue = Volley.newRequestQueue(context);

        // this url is the query being sent to the database
        url = server_URL + "select^user_id^from^appuser^where^(login_name='"+login_Name+"');";
        JsonObjectRequest getRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            response = response.getJSONObject("args");
                            String taskID = response.getString("user_id");
                            setuid1(Integer.parseInt(taskID));
                            Log.d("user_id: ",taskID);
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
       HANDELED IN LATER SPRINT----------------------------------------------------------------------------

     */

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
