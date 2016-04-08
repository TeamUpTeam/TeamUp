package com.teamup.teamup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

public class TaskActivity extends AppCompatActivity {
    private LinearLayout mLayout;
    final Context context = this;
    Server x = new Server();

    ArrayAdapter<String> taskAdapter;
    private ArrayList<String> taskList;
    private ArrayList<String> projectList;
    ListView listViewTask;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        final Intent i = getIntent();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_task);
        //MainActivity ma = new MainActivity();
        setTitle(MainActivity.pName);


        listViewTask = (ListView) findViewById(R.id.listViewTask);
        taskList = new ArrayList<String>();
        taskAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.mytextview, taskList);
        listViewTask.setAdapter(taskAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab1);
        // components from main.xml
        // add button listener
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
                final EditText userInput4 = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput4);

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
                                    taskAdapter.add(taskName.getText().toString());
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
                            }
                        });
                    }
                });
                mAlertDialog.show();


            }

        });

    }

    public void newTask(final String taskName, final String taskDesc, final int taskStatusId, final int projectId, final int priorTaskId, int isDel, final Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);

        String url2 = Server.server_URL + String.format("newtask?taskname=%s&taskdesc=%s&statusid=%d&projectid=%d&priorid=%d&isdel=%d",
                taskName, taskDesc, taskStatusId, projectId, priorTaskId, isDel);
        JsonObjectRequest createProjectRequest = new JsonObjectRequest
                (Request.Method.POST, url2, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //successful row return, so allow login
                        Log.d("Created project", response.toString());
                        try {
                            int taskId = response.getInt("insertId");
                            newUserTask(MainActivity.userId, taskId, context);
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

        // this url is the query being sent to the database

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_project, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }

}