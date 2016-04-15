package com.teamup.teamup;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
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

/**
 * Created by Edwin Prakarsa on 4/13/2016.
 */
public class MyCustomAdapter extends BaseAdapter implements ListAdapter {
    int tpid;
    int pid;
    int g_uid;
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;
    static int posi;
    static int global_task_id;

    public MyCustomAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.my_custom_list_layout, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position));
        posi = position;
        //Handle buttons and add onClickListeners
        Button deleteBtn = (Button)view.findViewById(R.id.delete_btn);
        Button claimBtn = (Button)view.findViewById(R.id.claim_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                list.remove(position); //or some other task
                notifyDataSetChanged();
            }
        });
        claimBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TaskActivity.claimedList.add(list.get(position));
                TaskActivity.claimedtaskAdapter.notifyDataSetChanged();
                //do something
                Toast.makeText(context,"claimed",Toast.LENGTH_SHORT).show();
                int uid = MainActivity.userId;
                System.out.println("Task to be claimed");
                //TaskActivity.ClaimedTask(uid,list.get(position));
                int pid = MainActivity.projectId;
                g_uid = MainActivity.userId;
                getPid(MainActivity.userId);

               // ClaimTask(uid,pid);

                //System.out.println("BFbilfnbasdfnsdinfaskjldfnakslfjnfkalsjnkajnv");

               // list.remove(position);
               // notifyDataSetChanged();

            }
        });

        return view;
    }
int x;
    public void ClaimTask(int uid,int pid)
    {
        x = uid;
        RequestQueue queue = Volley.newRequestQueue(context);
        String url2 = Server.server_URL + String.format("gettasks?userid=%d&projectid=%d",
                uid, pid);
        System.out.println("URL2 is " + url2);
        JsonArrayRequest createProjectRequest = new JsonArrayRequest
                (Request.Method.GET, url2, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        System.out.println("Her am I ");
                        //Log.d("gettasks", response.toString());
                        try {
                            for (int i=0; i < response.length(); i++) {
                                JSONObject actor = response.getJSONObject(i);
                                String name = actor.getString("task_name");

                                System.out.println("task Name: " + name);

                                if(name.equals(list.get(posi)))
                                {
                                    global_task_id = actor.getInt("task_id");
                                    System.out.println("task Id: " + global_task_id);
                                    //TaskActivity t = new TaskActivity();
                                    // TaskActivity.updateLists();
                                    //t.updateLists();

                                     callnewusertask(x,global_task_id);




                                     list.remove(posi);
                                     notifyDataSetChanged();


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
        queue.add(createProjectRequest);

    }

    public void callnewusertask(int uid,int task_id)
    {

        RequestQueue queue = Volley.newRequestQueue(context);

        String url2 = Server.server_URL + String.format("newusertask?userid=%d&taskid=%d",
                uid, task_id);

           Log.d("URL2 is ", url2);
        JsonObjectRequest createTaskRequest = new JsonObjectRequest
                (Request.Method.POST, url2, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        //Log.d("projectteammember", response.toString());
                        try {
                                //int projectId = response.getInt("insertId");

                                System.out.println("aobgklesbrglsdbglarbglaerbfgks");


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
        queue.add(createTaskRequest);

    }


    public int getPid(int xuid) {
        System.out.println("Hello from the other side");
        RequestQueue queue = Volley.newRequestQueue(context);

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
                                if (pname.equals(MainActivity.pName)) {
                                    pid = act.getInt("project_id");
                                    Log.d("project-id is ", String.format("%d", pid));
                                    ClaimTask(g_uid, pid);

                                    //update();
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


}