package com.teamup.teamup;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

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

    public String newuser(String username, String fname, String lname, String email) {

        String finalurl = server_URL + "newuser";
        String data;
        String text = "";
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();

        try {
            data = URLEncoder.encode("username", "UTF-8")
                    + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("firstname", "UTF-8") + "="
                    + URLEncoder.encode(fname, "UTF-8");
            data += "&" + URLEncoder.encode("lastname", "UTF-8")
                    + "=" + URLEncoder.encode(lname, "UTF-8");
            data += "&" + URLEncoder.encode("email", "UTF-8")
                    + "=" + URLEncoder.encode(email, "UTF-8");

            // Defined URL  where to send data
            URL url = new URL(finalurl);

            // Send POST data request
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            // Get the server response
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                System.out.println(line);
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();
        } catch(Exception ex) {

        }finally {
            try {
                reader.close();
            } catch(Exception ex) {}
        }

        return sb.toString();
    }

}
