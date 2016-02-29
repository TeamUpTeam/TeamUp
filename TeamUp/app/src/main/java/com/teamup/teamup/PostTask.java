package com.teamup.teamup;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


import org.apache.*;

/**
 * Created by Robbie on 2/29/2016.
 */

class PostTask extends AsyncTask<Void, Void, JSONObject> {
    Socket cSocket;
    ServerSocket sSocket;
    String url;
    JSONObject request;

    PostTask(String url, Socket cSocket,ServerSocket sSocket, JSONObject request) {

        this.url = url;
        this.cSocket = cSocket;
        this.sSocket = sSocket;
        this.request = request;
    }

    @Override
    protected JSONObject doInBackground(Void... v) {
        // Create post method
        HttpPost post = new HttpPost(url);

        HttpResponse response = null;
        try {
            // Add JSON object to post
            StringEntity se = new StringEntity(request.toString());
            post.setEntity(se);
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json");

            // Send request and get response
            response = client.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response == null) {
            Log.e("HTTP Post", "No server response");
            return null;
        }

        // convert response to string
        String responseString = null;
        try {
            responseString = convertToString(response.getEntity().getContent()).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // convert response string to JSON object
        JSONObject responseObj;
        try {
            responseObj = new JSONObject(responseString);
            return responseObj;
        } catch (JSONException e) {
            Log.e("HTTP Post", "Error getting JSON Object");
            e.printStackTrace();
        }
        return null;
    }

    private StringBuilder convertToString(InputStream is) {
        String line;

        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        StringBuilder string = new StringBuilder();
        try {
            while ((line = rd.readLine()) != null) {
                string.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return string;
    }
}