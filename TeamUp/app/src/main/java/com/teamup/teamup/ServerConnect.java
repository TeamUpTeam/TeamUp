package com.teamup.teamup;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by chan96 on 3/28/16.
 */
public class ServerConnect {
    private static String server_URL = "http://teamupserver3.mybluemix.net/api/";

    public int login(String email, String password, final Context context)
    {
        String url;
        //final String loginName = login_Name;
        // prepare the Request
        RequestQueue queue = Volley.newRequestQueue(context);

        // this url is the query being sent to the database
        url = server_URL + String.format("login?email=%s&password=%s", email, password);
        JsonArrayRequest getRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //successful row return, so allow login

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
        return 0;
    }
}
