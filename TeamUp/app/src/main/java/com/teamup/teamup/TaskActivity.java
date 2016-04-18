package com.teamup.teamup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
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

public class TaskActivity extends AppCompatActivity {
    final Context context = this;
    Server x = new Server();
    static int projectId;
    static Context ctext;
    static MyCustomAdapter taskAdapter;
    static ArrayList<String> taskList;
    static ArrayList<String> claimedTaskList;
    static MyClaimedTaskAdapter claimedtaskAdapter;
    static ArrayList<String>tlist;
    static ArrayList<String>clist;
    ListView listViewTask;
    static ArrayList<String> claimedList;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    ListView listViewTaskClaim;
    Handler mHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        final Intent i = getIntent();
        super.onCreate(savedInstanceState);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath(MainActivity.fontPath)
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

        init();
        setContentView(R.layout.activity_task);
        //MainActivity ma = new MainActivity();
        setTitle(MainActivity.pName);

        listViewTask = (ListView) findViewById(R.id.listViewTask);
        taskList = new ArrayList<String>();

        taskAdapter = new MyCustomAdapter(taskList, this);
        listViewTask.setAdapter(taskAdapter);


        ListView listclaimedTask = (ListView) findViewById(R.id.listViewTaskClaimed);
        claimedList = new ArrayList<String>();
        claimedtaskAdapter = new MyClaimedTaskAdapter(claimedList, this);
        listclaimedTask.setAdapter(claimedtaskAdapter);

        //taskAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.mytextview, taskList);
        listViewTask.setAdapter(taskAdapter);
        //updateclaimedList();
        //newTask("test1", "test1", 682, 1, 0, context);
        //newUserTask(402)
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             projectId = extras.getInt("projectId");
            Log.d("Task projectID", projectId + "");

        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab1);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.team_up_final);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // components from main.xml
        // add button listener
        getTasks(MainActivity.userId, projectId, context);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompts, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(promptsView);
                final EditText taskName = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput1);
                final EditText taskDesc = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput2);
                final EditText taskEndDate = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput3);

                //final EditText userInput5 = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput4);
                //userInput5.setSingleLine(); //same for 5,6,7

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK", null)
                        .setNegativeButton("Cancel", null);

                final AlertDialog mAlertDialog = alertDialogBuilder.create();
                mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // TODO Do something
                                if (!taskName.getText().toString().matches("") && !taskDesc.getText().toString().matches("")) {
                                    mAlertDialog.dismiss();
                                    taskList.add(taskName.getText().toString());
                                    // next thing you have to do is check if your adapter has changed
                                    taskAdapter.notifyDataSetChanged();

                                    // get user input and set it to result
                                    // edit text
                                    //TaskName = userInput1.toString();
                                    // int pid = x.getProjectID(i.getStringExtra("pname"),context);
                                    //x.addMember(pid,)
                                } else if (taskName.getText().toString().matches("")) {
                                    taskName.setError(getString(R.string.error_field_required));
                                } else if (taskDesc.getText().toString().matches("")) {
                                    taskDesc.setError(getString(R.string.error_field_required));
                                }

                                listViewTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                                    @Override
                                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {


                                    }
                                });

                                listViewTask.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                                    @Override
                                    public boolean onItemLongClick(AdapterView<?> parent, View view,
                                                                   final int arg2, long arg3) {
                                        AlertDialog.Builder adb = new AlertDialog.Builder(context);
                                        adb.setTitle("Delete entry");
                                        adb.setMessage("Are you sure you want to delete this task?");
                                        adb.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // continue with delete
                                                taskList.remove(arg2);
                                                taskAdapter.notifyDataSetChanged();
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

                                newTaskAndUserTask(taskName.getText().toString(), taskDesc.getText().toString(), taskEndDate.getText().toString().replaceAll("/", "-"), projectId, 1, 0, context, MainActivity.userId);
                            }
                        });
                    }
                });
                mAlertDialog.show();


            }

        });

    }

    public void getTaskInfo(int taskId) {
        RequestQueue queue = Volley.newRequestQueue(context);

        String url2 = Server.server_URL + String.format("gettaskinfo?taskid=%d",
               taskId);
        JsonObjectRequest createProjectRequest = new JsonObjectRequest
                (Request.Method.POST, url2, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //successful row return, so allow login
                        Log.d("Created task", response.toString());
                        int taskId = -1;
                        try {
                            taskId = response.getInt("insertId");
                            newUserTask(MainActivity.userId, taskId, context);
                        } catch (Exception e) {

                        }

                        newUserTask(MainActivity.userId, taskId, context);
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

    public void getTasks(int userId, int projectId, final Context context) {
        if (userId == 0) {
            Log.d("gettasks error", "userid or projectid is 0");
        }
        RequestQueue queue = Volley.newRequestQueue(context);
taskList.clear();
        claimedList.clear();
        String url2 = Server.server_URL + String.format("gettasks?userid=%d&projectid=%d",
                userId, projectId);
        JsonArrayRequest createProjectRequest = new JsonArrayRequest
                (Request.Method.GET, url2, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d("gettasks", response.toString());
                        try {
                            for (int i=0; i < response.length(); i++) {
                                JSONObject actor = response.getJSONObject(i);
                                String name = actor.getString("task_name").replace('_', ' ');

                                System.out.println("task Name: " + name);
                                if(actor.getInt("claimed_user_id")==-1) {

                                    if (taskList.contains(name)) {
                                        System.out.println("The task already exists, bitch!");
                                    } else {
                                        System.out.println("The task wasn't there! Adding it to the list!");
                                        taskList.add(name);
                                        // next thing you have to do is check if your adapter has changed
                                        taskAdapter.notifyDataSetChanged();
                                    }
                                }
                                else
                                {
                                    claimedList.add(name);
                                    claimedtaskAdapter.notifyDataSetChanged();
                                }

                                listViewTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                                    @Override
                                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {


                                    }
                                });

                                listViewTask.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                                    @Override
                                    public boolean onItemLongClick(AdapterView<?> parent, View view,
                                                                   final int arg2, long arg3) {
                                        AlertDialog.Builder adb = new AlertDialog.Builder(context);
                                        adb.setTitle("Delete entry");
                                        adb.setMessage("Are you sure you want to delete this entry?");
                                        adb.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // continue with delete
                                                taskList.remove(arg2);
                                                taskAdapter.notifyDataSetChanged();
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

    public void newTaskAndUserTask(final String taskName, final String taskDesc, String endDate, final int projectId, int isDel, int isDone, final Context context, final int userId) {
        RequestQueue queue = Volley.newRequestQueue(context);

        String url2 = Server.server_URL + String.format("newtask?taskname=%s&taskdesc=%s&isdone=%d&projectid=%d&isdel=%d&enddate=%s",
                taskName.replace(' ', '_'), taskDesc, isDone, projectId, isDel, endDate);
        JsonObjectRequest createProjectRequest = new JsonObjectRequest
                (Request.Method.POST, url2, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //successful row return, so allow login
                        Log.d("Created task", response.toString());
                        int taskId = -1;
                        try {
                            taskId = response.getInt("insertId");
                            newUserTask(MainActivity.userId, taskId, context);
                        } catch (Exception e) {

                        }

                        newUserTask(userId, taskId, context);
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

    public void newUserTask(int userId, int taskId, Context context)
    {
        if (userId == 0|| taskId == 0) {
            Log.d("newUserTask", "userid or projectid is 0");
        }
        RequestQueue queue = Volley.newRequestQueue(context);

        String url2 = Server.server_URL + String.format("newusertask?userid=%d&taskid=%d",
                userId, taskId);
        JsonObjectRequest createProjectRequest = new JsonObjectRequest
                (Request.Method.POST, url2, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("newUserTask", response.toString());
                        try {
                            int id = response.getInt("insertId");
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
        getMenuInflater().inflate(R.menu.menu_project, menu);
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        getMenuInflater().inflate(R.menu.menu_members, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_chat) {
            Intent i = new Intent(
                    TaskActivity.this,
                    ChatActivity.class);
            startActivity(i);
        } else if(id == R.id.action_members) {
            Intent i = new Intent (TaskActivity.this, MembersActivity.class);
           // i.putExtra("projectId", projectId);

            startActivity(i);
        } else if (id == R.id.action_quit){
            Intent i = new Intent(
                    TaskActivity.this,
                    MainActivity.class);
            startActivity(i);
        } else if (id == R.id.action_desc) {
            new AlertDialog.Builder(context)
                    .setTitle("Description")
                    .setMessage(MainActivity.Decs)
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .show();
        } else if (id == android.R.id.home) {
            Intent i = new Intent(
                    TaskActivity.this,
                    MainActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
   static int global_taskid;
public void init()
{
    ctext = context;
}
 public void gettlist()
 {
     tlist = taskList;
     clist = claimedTaskList;
 }

    public static void ClaimedTask(int userid, final String TaskName)
    {
        RequestQueue queue = Volley.newRequestQueue(ctext);
        String url2 = Server.server_URL + String.format("gettasks?userid=%d&projectid=%d",
                userid, MainActivity.projectId);
        JsonArrayRequest createProjectRequest = new JsonArrayRequest
                (Request.Method.GET, url2, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                         System.out.println("Here am I ");
                        //Log.d("gettasks", response.toString());
                        try {
                            for (int i=0; i < response.length(); i++) {
                                JSONObject actor = response.getJSONObject(i);
                                String name = actor.getString("task_name");

                                System.out.println("task Name: " + name);

                                if(name.equals(TaskName))
                                {
                                    global_taskid = actor.getInt("task_id");
                                    System.out.println("task Id: " + global_taskid);
                                    //TaskActivity t = new TaskActivity();
                                    // TaskActivity.updateLists();
                                    //t.updateLists();

                                    claimedTaskList.add(taskList.get(MyCustomAdapter.posi));
                                    Log.d("Added to claim task : ", String.format("%s", taskList.get(MyCustomAdapter.posi)));
                                    //taskList.remove(MyCustomAdapter.posi);

                                    taskAdapter.notifyDataSetChanged();



                                }


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

        /*
        String url2 = Server.server_URL + String.format("newusertask?userid=%d&taskid=%d",
                userId, taskId);
        JsonObjectRequest createProjectRequest = new JsonObjectRequest
                (Request.Method.POST, url2, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("newUserTask", response.toString());
                        try {
                            int id = response.getInt("insertId");
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
        queue.add(createProjectRequest);*/
    }


    public void updateLists() {
        claimedTaskList.add(taskList.get(MyCustomAdapter.posi));
        Log.d("Added to claim task : ", String.format("%s", taskList.get(MyCustomAdapter.posi)));
        //taskList.remove(MyCustomAdapter.posi);

        taskAdapter.notifyDataSetChanged();

    }
ArrayList <String> taskName = new ArrayList<String>();
    ArrayList <Integer> taskid = new ArrayList<Integer>();
//ArrayList <Integer> taskcl = new ArrayList<Integer>();

    public void updateclaimedList()
    {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = Server.server_URL+String.format("gettasks?projectid=%d",projectId);
        Log.d("the url for names ",url);

        JsonArrayRequest createTRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("Here am I ");
                        //Log.d("gettasks", response.toString());
                        try {
                            for (int i=0; i < response.length(); i++) {
                                JSONObject actor = response.getJSONObject(i);
                                String name = actor.getString("task_name");
                                taskid.add(response.getInt(i));
                               // System.out.println("task Name: " + name);
                                taskName.add(name);
                                //taskcl.add(0);
                            }

                            System.out.println("I still feel blessed entering getclaimedlist() ");
                    getclaimedtask();

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

    queue.add(createTRequest);
    }


    public void getclaimedtask() {
        System.out.println("I feel blessed !!!!!!");
        RequestQueue queue = Volley.newRequestQueue(context);

        for (int i = 0; i < taskid.size(); i++) {
            String url = Server.server_URL + String.format("gettaskinfo?taskid=%d", taskid.get(i));
            Log.d("url for tasks is ", url);

            JsonArrayRequest createProjectRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            System.out.println("Here am I tooooooooooo ");
                            //Log.d("gettasks", response.toString());
                            try {

                                JSONObject actor = response.getJSONObject(0);
                                int o = actor.getInt("claimed_user_id");
                                if (o == -1) {
                                    taskList.add(actor.getString("task_name"));
                                    System.out.println("Added tasklist " + taskList.get(taskList.indexOf(actor.getString("task_name"))));
                                } else {
                                    claimedTaskList.add(actor.getString("task_name"));
                                    System.out.println("Added claimedTaskList " + claimedTaskList.get(claimedTaskList.indexOf(actor.getString("task_name"))));

                                }


                                taskAdapter.notifyDataSetChanged();
                                claimedtaskAdapter.notifyDataSetChanged();
                                System.out.println(o);


                                //                               }


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
    }

    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            // Toast.makeText(MainActivity.this,"in runnable", Toast.LENGTH_SHORT).show();
            // Intent i = new Intent(MainActivity.this, MainActivity.class);
            System.out.println("It comes here!!!!!!!!!");
            getTasks(MainActivity.userId, MainActivity.projectId, context);
            TaskActivity.this.mHandler.postDelayed(m_Runnable, 1000);
            //startActivity(i);
        }

    };//runnable
}