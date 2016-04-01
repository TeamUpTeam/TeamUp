package com.teamup.teamup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private LinearLayout mLayout;
    final Context context = this;
    ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
    private ArrayList<String> projectList;
    ListView listViewProj;
    Server x = new Server();
    static String ProjectName;
    static String Description;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        listViewProj = (ListView) findViewById(R.id.listViewProject);
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.mytextview, arrayList);
        listViewProj.setAdapter(adapter);
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

                                newProject(ProjName.getText().toString(), StartDate.getText().toString(), EndDate.getText().toString(), ProjDesc.getText().toString(), LoginActivity.currEmail, context, view);

                                if (!ProjName.getText().toString().matches("") && !ProjDesc.getText().toString().matches("")) {
                                    mAlertDialog.dismiss();
                                    adapter.add(ProjName.getText().toString());
                                    // next thing you have to do is check if your adapter has changed
                                    adapter.notifyDataSetChanged();
                                    LoginActivity ne = new LoginActivity();
                                    int uid = ne.uid;
                                    //x.createProject(ProjName.getText().toString(), ProjDesc.getText().toString(), StartDate.getText().toString(), EndDate.getText().toString(), uid/*userID */, context);
                                    ProjectName = ProjName.getText().toString();
                                    Description = ProjDesc.getText().toString();
                                    listViewProj.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                                        @Override
                                        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                                            Intent i = new Intent(
                                                    MainActivity.this,
                                                    TaskActivity.class);
                                            startActivity(i);
                                        }
                                    });

                                    listViewProj.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                                        @Override
                                        public boolean onItemLongClick(AdapterView<?> parent, View view,
                                                                       final int arg2, long arg3) {
                                            AlertDialog.Builder adb = new AlertDialog.Builder(context);
                                            adb.setTitle("Delete entry");
                                            adb.setMessage("Are you sure you want to delete this entry?");
                                            adb.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // continue with delete
                                                    arrayList.remove(arg2);
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

    public void newProject(final String projectName, final String startDate, final String endDate, final String projectDescription, final String email,  Context context, final View view)
    {
        String url;
        //final String loginName = login_Name;
        // prepare the Request
        RequestQueue queue = Volley.newRequestQueue(context);

        // this url is the query being sent to the database
        url = Server.server_URL + String.format("getuserid?email=%s", email);
        JsonArrayRequest getRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //successful row return, so allow login

                        try {
                            final int projectManagerUserId = response.getJSONObject(0).getInt("user_id");
                            Log.d("response", response.getJSONObject(0).getString("user_id"));
                            String url2 = Server.server_URL + String.format("newproject?projectname=%s&startdate=%d&enddate=%s&projectmanageruserid=%d&projectdescription=%d",
                                    projectName, startDate, endDate, projectManagerUserId, projectDescription);
                            JsonArrayRequest getRequest = new JsonArrayRequest
                                    (Request.Method.GET, url2, null, new Response.Listener<JSONArray>() {
                                        @Override
                                        public void onResponse(JSONArray response) {
                                            //successful row return, so allow login

                                        }
                                    },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    // Error handling
                                                    if (error.toString().contains("success")) {
                                                        Log.d("Created project", error.toString());
                                                    } else {
                                                        
                                                    }
                                                    Log.d("Error.Response", error.toString());


                                                }
                                            });
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
        queue.add(getRequest);
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
        if (id == R.id.action_profile) {
            Intent i = new Intent(
                    MainActivity.this,
                    ProfileActivity.class);
            startActivity(i);
        }else if (id == R.id.action_settings) {
            Intent i = new Intent(
                    MainActivity.this,
                    SettingsActivity.class);
            startActivity(i);
        }else if (id == R.id.action_logout) {

        }

        return super.onOptionsItemSelected(item);
    }

    public String getProjectName(){
        return ProjectName;
    }

}