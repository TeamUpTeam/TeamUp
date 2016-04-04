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
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

<<<<<<< HEAD
=======
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

>>>>>>> master
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class SettingsActivity extends AppCompatActivity{
<<<<<<< HEAD

=======
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
    int userid;
>>>>>>> master
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
<<<<<<< HEAD
=======
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

>>>>>>> master


    }

<<<<<<< HEAD
=======


>>>>>>> master
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

