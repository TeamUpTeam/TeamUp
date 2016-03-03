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
     *  Returns true if the member was removed from the group, and false if there was a problem
     */
    public boolean removeMember (User oldMember)
    {
        //httpclient request here
        /*
        final String projectID = Integer.toString(this.project_ID);
        final String UserID = Integer.toString(this.userID);

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
        if(true) {
            this.teamLeader = newTL;
            return true;
        }

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
