package com.teamup.teamup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;

public class Messenger extends AppCompatActivity {

    private Firebase mRef;
    Button mSendMessage = (Button) findViewById(R.id.sendMessage);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.messaging_activity);
    }

    protected void onStart() {
        super.onStart();

        //Temporary Project Name for testing
        String pName = "TeamUp";

        mRef = new Firebase("https://teamup-messenger.firebaseio.com/" + pName);

        mSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRef.child("message").setValue("Do you have data? You'll love Firebase.");
            }
        });
    }

    public void getMessages() {
        mRef = new Firebase("https://teamup-messenger.firebaseio.com/");
    }

}