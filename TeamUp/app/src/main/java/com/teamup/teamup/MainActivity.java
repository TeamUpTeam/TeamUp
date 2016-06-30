package com.teamup.teamup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {
    private LinearLayout mLayout;
    final Context context = this;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> DecsAdapter;
    private ArrayList<String> arrayList;
    private ArrayList<String> DescList;
    ListView listViewProj;
    Server x = new Server();
    static int userId;
    static int projectId;
    static String pName;
    static String fName;
    static String uName;
    static String Decs;
    String user_info;
    static ArrayList<JSONObject> projectInfo = new ArrayList<JSONObject>();
    static String startD;
    static String endD;
    static Handler mHandler = null;
    static String fontPath = "fonts/Raleway-Medium.ttf";
    static RequestQueue volleyQueue = null;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath(fontPath)
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.team_up_final);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        if (this.mHandler == null) {
            this.mHandler = new Handler();
        }
        if (volleyQueue == null) {
            volleyQueue = Volley.newRequestQueue(context);
        }

        getUserIdAndProjects(LoginActivity3.currEmail);
        listViewProj = (ListView) findViewById(R.id.listViewProject);
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.mytextview, arrayList);

        DescList = new ArrayList<String>();
        listViewProj.setAdapter(adapter);


        this.mHandler.postDelayed(m_Runnable, 5000);





        //Get the First Name and User Name of the logged in User at the start of the application after the user has logged in
        RequestQueue queue = volleyQueue;
        String url = Server.server_URL + String.format("getuserinfo?email=%s",LoginActivity3.currEmail);
        JsonArrayRequest getRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //successful row return, so allow login
                        user_info = response.toString();
                        try {

                            fName = response.getJSONObject(0).getString("first_name");
                            uName = response.getJSONObject(0).getString("login_name");

                            //system.out.println("First Name: " + fName);
                            //system.out.println("User Name: " + uName);

                        }
                        catch (Exception e) {

                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Error handling
                                Log.d("Error.Response", error.toString() );


                            }
                        });

        // add it to the RequestQueue
        queue.add(getRequest);


        // add button listener
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.activity_project, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(promptsView);
                final EditText ProjName = (EditText) promptsView.findViewById(R.id.editText);
                ProjName.setSingleLine();
                final EditText StartDate = (EditText) promptsView.findViewById(R.id.editText2);
                StartDate.setSingleLine();
                final EditText EndDate = (EditText) promptsView.findViewById(R.id.editText3);
                EndDate.setSingleLine();
                final EditText ProjDesc = (EditText) promptsView.findViewById(R.id.editText4);
                ProjDesc.setSingleLine();

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK", null)
                        .setNegativeButton("Cancel", null);
                // set dialog message
                final AlertDialog mAlertDialog = alertDialogBuilder.create();
                mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // TODO Do something

                                newProject(ProjName.getText().toString(), StartDate.getText().toString(), EndDate.getText().toString(), userId, ProjDesc.getText().toString(), context);
                                //newProjectTeamMember(userId, projectId, context, view);

                                if (!ProjName.getText().toString().matches("") && !ProjDesc.getText().toString().matches("")) {
                                    mAlertDialog.dismiss();
                                    adapter.add(ProjName.getText().toString());
                                    // next thing you have to do is check if your adapter has changed
                                    adapter.notifyDataSetChanged();
                                    LoginActivity ne = new LoginActivity();
                                    Decs = ProjDesc.getText().toString();
                                    DescList.add(Decs);
                                    int uid = ne.uid;
                                    //x.createProject(ProjName.getText().toString(), ProjDesc.getText().toString(), StartDate.getText().toString(), EndDate.getText().toString(), uid/*userID */, context);
                                    listViewProj.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                                        @Override
                                        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                                            Intent i = new Intent(
                                                    MainActivity.this,
                                                    TaskActivity.class);


                                            startActivity(i);
                                            //startActivity(i);
                                        }
                                    });

                                    listViewProj.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                                        @Override
                                        public boolean onItemLongClick(AdapterView<?> parent, final View view,
                                                                       final int pos, long id) {
                                            AlertDialog.Builder adb = new AlertDialog.Builder(context);
                                            adb.setTitle("Delete entry");
                                            adb.setMessage("Delete this project?");
                                            adb.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // continue with delete
                                                    //int pos = (Integer)view.getTag();
                                                    //log.d("pos", "" + pos);
                                                    try {
                                                        deleteProject(projectInfo.get(pos).getInt("project_id"), userId);

                                                    } catch (Exception e) {
                                                        Toast.makeText(MainActivity.this,"Error deleting project", Toast.LENGTH_SHORT).show();
                                                        return;
                                                    }
                                                    arrayList.remove(pos);
                                                    adapter.notifyDataSetChanged();

                                                }
                                            })
                                                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // do nothing
                                                        }
                                                    })
                                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                                    .show();
                                            return true;
                                        }
                                    });
                                } else if (ProjName.getText().toString().matches("")) {
                                    ProjName.setError(getString(R.string.error_field_required));
                                } else if (ProjDesc.getText().toString().matches("")) {
                                    ProjDesc.setError(getString(R.string.error_field_required));
                                }
                            }
                        });
                    }
                });
                mAlertDialog.show();

            }

        });
    }

    public void deleteProject(int projectId, int userId) {
        RequestQueue queue = volleyQueue;

        String url2 = Server.server_URL + String.format("deleteuserinproject?projectid=%d&userid=%d",
                projectId, userId);
        JsonObjectRequest createProjectRequest = new JsonObjectRequest
                (Request.Method.POST, url2, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("delete project", response.toString());
                        try {


                        } catch (Exception e) {

                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Error.Response", error.toString());
                            }
                        });
        queue.add(createProjectRequest);
    }

    public void getUserIdAndProjects(String email) {
        RequestQueue queue = volleyQueue;

        String url = Server.server_URL + String.format("getuserinfo?email=%s", email);
        JsonArrayRequest getUserIdRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //successful row return, so allow login

                        try {
                            //log.d("response a", response.getJSONObject(0).getString("user_id"));
                            userId = response.getJSONObject(0).getInt("user_id");
                            getProjects(userId, context);
                        } catch (Exception e) {

                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Error handling
                                Log.d("Error.Response", error.toString());


                            }
                        });

        // add it to the RequestQueue
        queue.add(getUserIdRequest);
    }

    public void newProject(final String projectName, final String startDate, final String endDate, final int projectManagerUserId, final String projectDescription, final Context context)
    {
        RequestQueue queue = volleyQueue;
        startD = startDate;
        endD = endDate;
        String url2 = Server.server_URL + String.format("newproject?projectname=%s&startdate=%s&enddate=%s&projectmanageruserid=%d&projectdescription=%s",
                projectName.replace(' ', '_'), startDate, endDate, projectManagerUserId, projectDescription);
        JsonObjectRequest createProjectRequest = new JsonObjectRequest
                (Request.Method.POST, url2, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //successful row return, so allow login
                        Log.d("Created project", response.toString());
                        try {
                            projectId = response.getInt("insertId");
                            newProjectTeamMember(userId, projectId, context);
                        } catch (Exception e) {

                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Error.Response", error.toString());
                            }
                        });
        queue.add(createProjectRequest);

        // this url is the query being sent to the database

    }

    public void newProjectTeamMember(int userId, int projectId, Context context)
    {
        if (userId == 0|| projectId == 0) {
            Log.d("newProjectTeamMember", "userid or projectid is 0");
        }
        RequestQueue queue = volleyQueue;

        String url2 = Server.server_URL + String.format("newprojectteammember?projectid=%d&userid=%d",
                projectId, userId);
        JsonObjectRequest createProjectRequest = new JsonObjectRequest
                (Request.Method.POST, url2, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("projectteammember", response.toString());
                        try {
                            int projectId = response.getInt("insertId");
                        } catch (Exception e) {

                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Error.Response", error.toString());
                            }
                        });
        queue.add(createProjectRequest);

        // this url is the query being sent to the database

    }

    public void getProjects(final int userId, final Context context)
    {
        if (userId == 0) {
            Log.d("getProjects error", "userid or projectid is 0");
        }
        RequestQueue queue = volleyQueue;
        //adapter.clear();
        String url2 = Server.server_URL + String.format("getprojects?userid=%d",
                userId);
        JsonArrayRequest createProjectRequest = new JsonArrayRequest
                (Request.Method.GET, url2, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        //log.d("getprojects", response.toString());
                        projectInfo.clear();
                        adapter.clear();
                        DescList.clear();
                        try {
                            for (int i=0; i < response.length(); i++) {
                                JSONObject actor = response.getJSONObject(i);
                                projectInfo.add(actor); //stores jsonobject info for each project to be accessed later.
                                String name = actor.getString("project_name").replace('_', ' ');
                                String description = actor.getString("project_description").replace('_',' ');
                                final int projId = actor.getInt("project_id");
                                //log.d("projID", projId + "");

                                //system.out.println("Project Name: " + name);

                                if (arrayList.contains(name)) {
                                    //system.out.println("Bitch already exists, don't add him!");
                                } else {
                                    //system.out.println("Hey! At least it comes here!");
                                    arrayList.add(name);
                                    DescList.add(description);
                                    // next thing you have to do is check if your adapter has changed
                                    adapter.notifyDataSetChanged();
                                }

                                listViewProj.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                                    @Override
                                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                                        //system.out.println("I got here!");
                                        //system.out.println("The position is: " + position);
                                        pName = adapter.getItem(position);
                                        Decs = DescList.get(position);

                                        System.out.println("This is the project name that I got: " + pName);

                                        //system.out.println("This is the project name that I got: " + pName);

                                        Intent i = new Intent(
                                                MainActivity.this,
                                                TaskActivity.class);
                                        //int projectId;
                                        try {
                                            projectId = projectInfo.get(position).getInt("project_id");
                                            i.putExtra("projectId", projectId);
                                        } catch (Exception e) {

                                        }
                                        startActivity(i);

                                    }
                                });

                                listViewProj.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                                    @Override
                                    public boolean onItemLongClick(AdapterView<?> parent, final View view,
                                                                   final int pos, long id) {
                                        AlertDialog.Builder adb = new AlertDialog.Builder(context);
                                        adb.setTitle("Delete entry");
                                        adb.setMessage("Are you sure you want to delete this entry?");
                                        adb.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // continue with delete


                                                //log.d("pos", "" + pos);
                                                try {

                                                    deleteProject(projectInfo.get(pos).getInt("project_id"), userId);

                                                } catch (Exception e) {
                                                    Toast.makeText(MainActivity.this,"Error deleting project", Toast.LENGTH_SHORT).show();
                                                    return;
                                                }
                                                arrayList.remove(pos);
                                                adapter.notifyDataSetChanged();
                                            }
                                        })
                                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // do nothing
                                                    }
                                                })
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .show();
                                        return true;
                                    }
                                });
                            }
                        } catch (Exception e) {

                        }
                    }

                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Error.Response", error.toString());
                            }
                        });
        queue.add(createProjectRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent (
                    MainActivity.this,
                    SettingsActivity.class);
            startActivity(i);
        } else if (id == R.id.action_logout) {
            Intent i = new Intent (MainActivity.this, LoginActivity3.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }


    public boolean checkLogin(int userId) {
        return false;
    }

    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {


           // Toast.makeText(MainActivity.this,"in runnable", Toast.LENGTH_SHORT).show();
           // Intent i = new Intent(MainActivity.this, MainActivity.class);
            getProjects(userId, context);
            //MainActivity.this.mHandler.postDelayed(m_Runnable, 5000);


            //startActivity(i);



            MainActivity.this.mHandler.postDelayed(m_Runnable, 3000);
            //startActivity(i);
        }

    };//runnable
}