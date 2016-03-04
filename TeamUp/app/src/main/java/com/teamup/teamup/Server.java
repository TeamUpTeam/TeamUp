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


public class Server {
    private static final Server INSTANCE = new Server();
    private static String server_URL = "http://teamupserver3.mybluemix.net/api/query?query=";
    //public String pname;
    public Server() { }
    String xx;
    String passwd;
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

    public int getuid() {
        return uid1;
    }

    public void set_passwd(String password) {
        passwd = password;
    }

    public String get_passwd() {
        return passwd;
    }

    public int setpid1(int yy)
    {
        puid1 = yy;
        return puid1;
    }
    /*
     *  Creates a project with the values given, creates a projectID
     */
    public int createProject (String project_Name, String project_Description, String plan_Start, String plan_End, int pm_UserID, Context context)
    {
        final String projectName = project_Name;
        final String projectDescription = project_Description;
        //final String planStart = plan_Start.toString();
        //final String planEnd = plan_End.toString();
        //final String pmUserID = Integer.toString(pm_UserID);


        //String url = server_URL + "insert^into^project^(project_name,planned_start_date,planned_end_date,project_manager_user_id,project_description)^values^('"+projectName+"','"+planStart+"','"+planEnd+"','"+pmUserID+"','"+projectDescription+"');";
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
                //params.put("planned_start_date", planStart);
                //params.put("planned_end_date", planEnd);
                //params.put("project_manager_user_id", pmUserID);
                params.put("project_description", projectDescription);
                return params;
            }
        };
        queue.add(postRequest);

        // gets the projectID for the Log
        getProjectID(pm_UserID, projectName, context); // not possible

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
    public int getProjectID(int project_manager_user_id, String project_name, Context context) {

        // setup variables to be used
        String url;
        final String pmUserID = Integer.toString(project_manager_user_id);
        final String projectName = project_name;

        // prepare the Request
        RequestQueue queue = Volley.newRequestQueue(context);

        // this url is the query being sent to the database
        url = server_URL + "select^project_id^from^project^where^project_manager_user_id='"+pmUserID+"'^and^project_name='"+projectName+"';";


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
        getTaskID(project_id, taskName, context);

        return 0;
    }

    // SET METHODS FOR TASK------------------------------------------------------------------------------

    public int setTaskDesc (int task_id, String task_desc, Context context)
    {
        final String taskDesc = task_desc;
        final String taskID = Integer.toString(task_id);

        String url = server_URL + "update^task^set^task_desc='"+taskDesc+"'^where^project_id='"+taskID+"';";
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
                params.put("task_desc", taskDesc);
                return params;
            }
        };
        queue.add(postRequest);



        return 0;
    }


    // GET METHODS FOR TASK------------------------------------------------------------------------------
    /*
     *  Gets the TaskID
     */
    public int getTaskID(int project_id, String task_name, Context context)
    {
        String url;
        final String projectID = Integer.toString(project_id);
        final String taskName = task_name;
        // prepare the Request
        RequestQueue queue = Volley.newRequestQueue(context);

        // this url is the query being sent to the database
        url = server_URL + "select^task_id^from^task^where^project_id='"+projectID+"'^and^task_name='"+taskName+"';";
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


    public int getPlannedTaskStartDate(int task_ID, Context context) {

        // setup variables to be used
        String url;
        final String taskID = Integer.toString(task_ID);
        // prepare the Request
        RequestQueue queue = Volley.newRequestQueue(context);

        // this url is the query being sent to the database
        url = server_URL + "select^planned_start_date^from^task^where(task_id='"+taskID+"');";


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

    public int getPlannedTaskEndDate(int task_ID, Context context) {

        // setup variables to be used
        String url;
        final String taskID = Integer.toString(task_ID);
        // prepare the Request
        RequestQueue queue = Volley.newRequestQueue(context);

        // this url is the query being sent to the database
        url = server_URL + "select^planned_end_date^from^task^where(task_id='"+taskID+"');";


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

    public int getPlannedManHours(int task_ID, Context context) {

        // setup variables to be used
        String url;
        final String taskID = Integer.toString(task_ID);
        // prepare the Request
        RequestQueue queue = Volley.newRequestQueue(context);

        // this url is the query being sent to the database
        url = server_URL + "select^planned_man_hours^from^task^where(task_id='"+taskID+"');";


        JsonObjectRequest getRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            response = response.getJSONObject("args");
                            String planned_man_hours = response.getString("planned_man_hours");
                            Log.d("planned_man_hours: ",planned_man_hours);
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


    // APPUSER------------------------------------------------------------------------------------------------


    /*
     *  Creates a new task with the set variables
    */
    public int createAppUser (String login_Name, String first_name, String last_name, String email_address, String contact_phone, String pass_word, Context context)
    {
        final String loginName = login_Name;
        final String firstName = first_name;
        final String lastName = last_name;
        final String emailAddress = email_address;
        final String contactPhone = contact_phone;
        final String password = pass_word;

        String url = server_URL + "insert^into^appuser^(login_name,first_name,last_name,email_address,contact_phone,password)^values^('"+loginName+"','"+firstName+"','"+lastName+"','"+emailAddress+"','"+contactPhone+"','"+password+"');";
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
                params.put("first_name", firstName);
                params.put("last_name", lastName);
                params.put("email_address", emailAddress);
                params.put("contact_phone", contactPhone);
                params.put("password", password);
                return params;
            }
        };
        queue.add(postRequest);

        // gets the projectID for the Log
        getUserID(loginName, context);

        return 0;
    }

    /*
     *  Checks if the login_name is taken
     */
    public int checkLoginName(String login_Name, Context context)
    {
        String url;
        final String loginName = login_Name;
        // prepare the Request
        RequestQueue queue = Volley.newRequestQueue(context);

        // this url is the query being sent to the database
        url = server_URL + "select^login_name^from^appuser^where^(login_name='"+login_Name+"');";
        JsonObjectRequest getRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            response = response.getJSONObject("args");
                            String login_name = response.getString("login_name");
                            setuid1(Integer.parseInt(login_name));
                            Log.d("login_name: ",login_name);
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

    // SET METHODS FOR APPUSER------------------------------------------------------------------------------


    /*
     *   Sets the email for the appuser
     */
    public int setEmailAddress(int user_ID, String email_address, Context context)
    {
        final String emailAddress = email_address;
        final String userID = Integer.toString(user_ID);

        String url = server_URL + "update^appuser^set^email_address='"+emailAddress+"'^where^user_id='"+userID+"';";
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
                params.put("email_address", emailAddress);
                return params;
            }
        };
        queue.add(postRequest);



        return 0;
    }

    /*
     *   Sets the phone number for the appuser
     */
    public int setContactPhone(int user_ID, String contact_phone, Context context)
    {
        final String contactPhone = contact_phone;
        final String userID = Integer.toString(user_ID);

        String url = server_URL + "update^appuser^set^email_address='"+contactPhone+"'^where^user_id='"+userID+"';";
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
                params.put("contact_phone", contactPhone);
                return params;
            }
        };
        queue.add(postRequest);



        return 0;
    }

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
                                Log.d("Error.Response", "Response Error");
                                Log.d("UID", "" + uid1);
                            }
                        });

        // add it to the RequestQueue
        queue.add(getRequest);
        //int id = getuid();
        return uid1;
    }


    /*
     *  Gets the login name connected to the given userID
     */
    public int getLoginName(int user_ID, Context context) {

        // setup variables to be used
        String url;
        final String userID = Integer.toString(user_ID);
        // prepare the Request
        RequestQueue queue = Volley.newRequestQueue(context);

        // this url is the query being sent to the database
        url = server_URL + "select^login_name^from^task^where(user_id='"+userID+"');";


        JsonObjectRequest getRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            response = response.getJSONObject("args");
                            String login_name = response.getString("login_name");
                            Log.d("login_name: ",login_name);
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
    *  Gets the first name connected to the given userID
    */
    public int getFirstName(int user_ID, Context context) {

        // setup variables to be used
        String url;
        final String userID = Integer.toString(user_ID);
        // prepare the Request
        RequestQueue queue = Volley.newRequestQueue(context);

        // this url is the query being sent to the database
        url = server_URL + "select^first_name^from^task^where(user_id='"+userID+"');";


        JsonObjectRequest getRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            response = response.getJSONObject("args");
                            String first_name = response.getString("first_name");
                            Log.d("first_name: ",first_name);
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
    *  Gets the last name connected to the given userID
    */
    public int getLastName(int user_ID, Context context) {

        // setup variables to be used
        String url;
        final String userID = Integer.toString(user_ID);
        // prepare the Request
        RequestQueue queue = Volley.newRequestQueue(context);

        // this url is the query being sent to the database
        url = server_URL + "select^last_name^from^task^where(user_id='"+userID+"');";


        JsonObjectRequest getRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            response = response.getJSONObject("args");
                            String last_name = response.getString("last_name");
                            Log.d("last_name: ",last_name);
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
    *  Gets the email address connected to the given userID
    */
    public int getEmailAddress(int user_ID, Context context) {

        // setup variables to be used
        String url;
        final String userID = Integer.toString(user_ID);
        // prepare the Request
        RequestQueue queue = Volley.newRequestQueue(context);

        // this url is the query being sent to the database
        url = server_URL + "select^email_address^from^task^where(user_id='"+userID+"');";


        JsonObjectRequest getRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            response = response.getJSONObject("args");
                            String email_address = response.getString("email_address");
                            Log.d("email_address: ",email_address);
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
    *  Gets the contact phone connected to the given userID
    */
    public int getContactPhone(int user_ID, Context context) {

        // setup variables to be used
        String url;
        final String userID = Integer.toString(user_ID);
        // prepare the Request
        RequestQueue queue = Volley.newRequestQueue(context);

        // this url is the query being sent to the database
        url = server_URL + "select^contact_phone^from^task^where(user_id='"+userID+"');";


        JsonObjectRequest getRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            response = response.getJSONObject("args");
                            String contact_phone = response.getString("contact_phone");
                            Log.d("contact_phone: ",contact_phone);
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
    *  Gets the password connected to the given userID
    */
    public int getPassword(int user_ID, Context context) {

        // setup variables to be used
        String url;
        final String userID = Integer.toString(user_ID);
        // prepare the Request
        RequestQueue queue = Volley.newRequestQueue(context);

        // this url is the query being sent to the database
        url = server_URL + "select^password^from^task^where(user_id='"+userID+"');";


        JsonObjectRequest getRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            response = response.getJSONObject("args");
                            String password = response.getString("password");
                            Log.d("password: ",password);
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


    public String getPassword(final String login_name, Context context) {
        String url;

        RequestQueue queue = Volley.newRequestQueue(context);

        Log.d("login_name", login_name);

        url = server_URL + "select^password^from^appuser^where^(login_name='"+login_name+"')";

        JsonObjectRequest getRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            response = response.getJSONObject("args");
                            String password = response.getString("password");
                            Log.d("Check", "check");
                            Log.d("password: ",password);
                            set_passwd(password);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Error handling
                                Log.d("UserName", login_name);
                                Log.d("Error.Response", "Response Error" );
                            }
                        });

        // add it to the RequestQueue
        queue.add(getRequest);

        String password = get_passwd();
        Log.d("Check going on", "");

        Log.d("Check_passwd", "");

        return password;
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
