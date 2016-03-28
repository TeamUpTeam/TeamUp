package com.example.chan96.teamup;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.teamup.teamup.ServerConnect;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
        ServerConnect conn = new ServerConnect();

        String output = conn.newuser("a", "b", "c", "abc@abc.com");

        Log.d("test", output);
    }
}