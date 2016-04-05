package com.teamup.teamup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

/**
 * A login screen that offers login via email/password.
 */
public class SettingsActivity extends AppCompatActivity{
    String email = LoginActivity.currEmail;
    TextView fn;
    TextView ln;
    TextView em;
    TextView log;
    TextView pass;

    Context context = this;
    String user_info;
    String login_name;
    String first_name;
    String last_name;
    String email_curr;
    String password;
    static String fName;
    static String uName;
    int userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        RequestQueue queue = Volley.newRequestQueue(context);
        fn = (TextView) this.findViewById(R.id.textView2);
        ln = (TextView) this.findViewById(R.id.textView3);
        log = (TextView) this.findViewById(R.id.textView4);
        em = (TextView) this.findViewById(R.id.textView5);

        String url = Server.server_URL + String.format("getuserinfo?email=%s",email);
        JsonArrayRequest getRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //successful row return, so allow login
                    user_info = response.toString();
                       try {
                           login_name = response.getJSONObject(0).getString("login_name");
                           first_name = response.getJSONObject(0).getString("first_name");
                           last_name = response.getJSONObject(0).getString("last_name");
                           email_curr = response.getJSONObject(0).getString("email_address");
                           //password = response.getJSONObject(0).getString("password");
                           //Log.d("fn %s",first_name);
                           //Log.d("ln ",last_name);
                           //Log.d("lognam ", login_name);
                           //Log.d("email is ", email_curr);
                           //String temp1 = first_name;
                           fName = response.getJSONObject(0).getString("first_name");
                           uName = response.getJSONObject(0).getString("login_name");
                           fn.setText(response.getJSONObject(0).getString("first_name"));
                           // }
                           ln.setText(response.getJSONObject(0).getString("last_name"));

                           log.setText(response.getJSONObject(0).getString("login_name"));

                           em.setText(response.getJSONObject(0).getString("email_address"));
                       }
                       catch (Exception e)
                       {

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
                                Log.d("Error.Response", error.toString() );


                            }
                        });

        // add it to the RequestQueue
        queue.add(getRequest);
        //if(fn.equals("undefined"))
        //{
           // fn = (TextView) this.findViewById(R.id.textView2);
         //   fn.setText("Not provided");
        //}
        //else {



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
        if (id == R.id.action_back) {
            Intent i = new Intent(
                    SettingsActivity.this,
                    MainActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

}

