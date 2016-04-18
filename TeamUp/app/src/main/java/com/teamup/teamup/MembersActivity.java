package com.teamup.teamup;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
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
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class MembersActivity extends AppCompatActivity {
    String user_info;
    int uid;
    EditText emailmember;
    final String projName = MainActivity.pName;
    int global_pid;
    int global_uid;
    String global_email;
    int pid;
    ArrayAdapter<String> memAdapter;
    private ArrayList<String> memList;
    ListView listViewMem;
    Context context = this;
    String email;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    //static int tempUiD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_list);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath(MainActivity.fontPath)
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

        getTeamMemberEmails(TaskActivity.projectId, context);

        listViewMem = (ListView) findViewById(R.id.listViewMembers);
        memList = new ArrayList<String>();
        memAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.mytextview, memList);
        listViewMem.setAdapter(memAdapter);
        emailmember = (EditText) findViewById(R.id.memberEmail);

        //update1();
         //  UpdatePid(MainActivity.userId);

        Button addmember = (Button) findViewById(R.id.AddMember);
        System.out.println("Helloddddddddddddd");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.team_up_final);

        emailmember.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                // you can call or do what you want with your EditText here


            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        addmember.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //email = emailmember.getText().toString();
                memList.add(emailmember.getText().toString());
                memAdapter.notifyDataSetChanged();
                emailmember.setVisibility(View.VISIBLE);


                email = emailmember.getText().toString();
                getUid(email);
                //uid = getUid(email);//ask brian for single query route
                Log.d("UserId in", String.format("%d", uid));

                Log.d("current email is ", email);


//get proj id
                Log.d("UserId in", String.format("%d", uid));

                System.out.println("I am here too biatch");
                Log.d("Project Id is ", String.format("%d", pid));


                // if (uid > 0) {


                // email = emailmember.getText().toString();

                // } else {
                //  Log.d("userid told ",String.format("%d",uid));
                //}
                

            }
        });


        // uid = 0;
        //pid = 0;//fjdjc
        listViewMem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder adb = new AlertDialog.Builder(context);
                adb.setTitle("Delete selected Member");
                adb.setMessage("Are you sure you want to delete this member?");
                adb.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        global_email = memList.get(position);
                        UserId(memList.get(position));
                        memList.remove(position);
                        memAdapter.notifyDataSetChanged();
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

    public void validate() {
        if (emailmember.getText().toString().equals("")) {
            return;
        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_chat) {
            Intent i = new Intent(
                    MembersActivity.this,
                    TaskActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    int tuid;

    public int getUid(String email) {
        String descr = MainActivity.Decs;
        //email = memList.get(memList.size()-1);
        String start = MainActivity.startD;
        String end = MainActivity.endD;

        RequestQueue queue = MainActivity.volleyQueue;
        String url = Server.server_URL + String.format("getuserinfo?email=%s", email);
        Log.d("url is", url);
        JsonArrayRequest getRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //successful row return, so allow login
                        user_info = response.toString();
                        Log.d("user_info is ", user_info);
                        try {
                            tuid = response.getJSONObject(0).getInt("user_id");
                            Log.d("user id is ", String.format("%d", tuid));
                            //tempUiD = uid;
                        } catch (Exception e) {

                        }

                        //final String uid = String.format("%d",userid);
                        //Log.d("user_info is %s",user_info);
                        //Log.d("login_name is %s",login_name);
                        getPid(MainActivity.userId);


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
        uid = tuid;

        //NumberPicker.OnValueChangeListener(uid);

        return tuid;


    }

    int tpid;


    public int getPid(int xuid) {
        System.out.println("Hello from the other side");
        RequestQueue queue = MainActivity.volleyQueue;

        String url2 = Server.server_URL + String.format("getprojects?userid=%d", xuid);
        Log.d("url2 is ", url2);
        JsonArrayRequest getProjects = new JsonArrayRequest
                (Request.Method.GET, url2, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //successful row return, so allow login
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject act = response.getJSONObject(i);
                                String pname = act.getString("project_name");
                                if (pname.equals(projName)) {
                                    pid = act.getInt("project_id");
                                    Log.d("project-id is ", String.format("%d", pid));
                                    update();
                                    break;
                                } else {
                                    continue;
                                }
                            }

                        } catch (Exception e) {

                        }

                        //final String uid = String.format("%d",userid);
                        //Log.d("user_info is %s",user_info);
                        //Log.d("login_name is %s",login_name);


                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Error handling
                                Log.d("Error.Response", error.toString());


                            }
                        });
        // String url1 = Server.server_URL + String.format("newprojectteammember?projectid=%d&userid=%d",,uid);

        queue.add(getProjects);
        return tpid;

    }


    public void UpdatePid(int xuid) {
        System.out.println("Hello from the other side");
        RequestQueue queue = MainActivity.volleyQueue;

        String url2 = Server.server_URL + String.format("getprojects?userid=%d", xuid);
        Log.d("url2 in Update_Pid is ", url2);
        JsonArrayRequest getProjects = new JsonArrayRequest
                (Request.Method.GET, url2, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //successful row return, so allow login
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject act = response.getJSONObject(i);
                                String pname = act.getString("project_name");
                                if (pname.equals(projName)) {
                                    global_pid = act.getInt("project_id");
                                    Log.d("project-id is ", String.format("%d", global_pid));
                                    update1();
                                    break;
                                } else {
                                    continue;
                                }
                            }

                        } catch (Exception e) {

                        }

                        //final String uid = String.format("%d",userid);
                        //Log.d("user_info is %s",user_info);
                        //Log.d("login_name is %s",login_name);


                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Error handling
                                Log.d("Error.Response", error.toString());


                            }
                        });
        // String url1 = Server.server_URL + String.format("newprojectteammember?projectid=%d&userid=%d",,uid);

        queue.add(getProjects);
        //return tpid;

    }



    public void update() {
        RequestQueue queue = MainActivity.volleyQueue;

        String url3 = Server.server_URL + String.format("newprojectteammember?projectid=%d&userid=%d", pid, tuid);
        Log.d("Url 3 is ", url3);
        JsonObjectRequest createProjectRequest = new JsonObjectRequest
                (Request.Method.POST, url3, null, new Response.Listener<JSONObject>() {
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
    }

    ArrayList<Integer> users = new ArrayList<Integer>();


    public void update1() {
        RequestQueue queue = MainActivity.volleyQueue;
        //int ttpid =MainActivity.projectId;
        getPid(MainActivity.userId);
        int ttpid = global_pid;
        Log.d("ttpid dnkdnknd is ",String.format("%d",ttpid));
        String url = Server.server_URL + String.format("getuseridwp?projectid=%d", ttpid);
        Log.d("Url in update1() is ", url);

        JsonArrayRequest getUsers = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject act = response.getJSONObject(i);
                                users.add(act.getInt("user_id"));
                            }
                            updateScreen();

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
        queue.add(getUsers);


    }

    public void updateScreen() {


        RequestQueue queue = MainActivity.volleyQueue;

        for (int j = 0; j < users.size(); j++) {
            String url2 = Server.server_URL + String.format("getuserwid?userid=%d", users.get(j));
            Log.d("url2 in update() is ", url2);
            JsonArrayRequest getRequest = new JsonArrayRequest
                    (Request.Method.GET, url2, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            //successful row return, so allow login
                            user_info = response.toString();
                            Log.d("user_info is ", user_info);
                            try {
                                memList.add(response.getJSONObject(0).getString("email_address"));
                                memAdapter.notifyDataSetChanged();
                                System.out.println("Currently in memlist we have "+ memList.get(memList.size()-1));
                                //tuid = response.getJSONObject(0).getInt("user_id");
                                //Log.d("user id is ", String.format("%d", tuid));
                                //tempUiD = uid;
                            } catch (Exception e) {

                            }

                            //final String uid = String.format("%d",userid);
                            //Log.d("user_info is %s",user_info);
                            //Log.d("login_name is %s",login_name);
                            // getPid(MainActivity.userId);


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


    }
    public void UserId(String email)
    {
        RequestQueue queue = MainActivity.volleyQueue;

        String url = Server.server_URL + String.format("getuserinfo?email=%s",email);
        JsonArrayRequest getRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //successful row return, so allow login
                        user_info = response.toString();
                        try {
                            global_uid = response.getJSONObject(0).getInt("user_id");
                            Log.d("global_uid is ", String.format("%d",global_uid));
                            deleteUser();
                        }
                        catch (Exception e)
                        {

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
    }

public void deleteUser()
{
    RequestQueue queue = MainActivity.volleyQueue;

    String url = Server.server_URL+String.format("deleteuserinproject?projectid=%d&userid=%d",global_pid,global_uid);

    Log.d("url: ", url);
    JsonObjectRequest getRequest = new JsonObjectRequest
            (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //successful row return, so allow login
                    Log.d("deleted user : ", response.toString());
                    Toast.makeText(context,String.format("Requested User with email %s deleted",global_email),Toast.LENGTH_LONG).show();


                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Error handling

                        }
                    });

    // add it to the RequestQueue
    queue.add(getRequest);
}


    public void getTeamMemberEmails(int projectId, final Context context) {

        RequestQueue queue = MainActivity.volleyQueue;

        String url2 = Server.server_URL + String.format("getteammembers?projectid=%d",
                projectId);
        JsonArrayRequest createProjectRequest = new JsonArrayRequest
                (Request.Method.GET, url2, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d("getTeamMemberEmails", response.toString());
                        try {
                            for (int i=0; i < response.length(); i++) {
                                JSONObject actor = response.getJSONObject(i);
                                String name = actor.getString("email_address");

                                System.out.println("email: " + name);

                                memList.add(response.getJSONObject(i).getString("email_address"));
                                memAdapter.notifyDataSetChanged();

                                /*
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
                                });*/
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


}




