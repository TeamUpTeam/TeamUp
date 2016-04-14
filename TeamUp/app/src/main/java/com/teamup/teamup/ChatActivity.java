package com.teamup.teamup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
public class ChatActivity extends AppCompatActivity {
    private EditText editTxt;
    private Button btn;
    private ListView list;
    private ArrayList<String> ChatList;
    private ArrayList<String> MemList;
    private ArrayList<String> TimeList;
    int countMessages;

    Firebase mRef;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.chatroom);
            editTxt = (EditText) findViewById(R.id.input_chat_message);
            btn = (Button) findViewById(R.id.button_chat_send);

            list = (ListView) findViewById(R.id.list_chat);
            ChatList = new ArrayList<String>();
            MemList = new ArrayList<String>();
            TimeList = new ArrayList<String>();
            final Context context = this;
            countMessages = 0;

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.team_up_final);

        final DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        final Date date = new Date();

        //instantiate custom adapter
        final ChatCustomAdapter chatAdapter = new ChatCustomAdapter(ChatList, MemList, TimeList, ChatActivity.this);

        //handle listview and assign adapter

        list.setAdapter(chatAdapter);


        System.out.println("Hello, Sir!");

            Firebase.setAndroidContext(this);
            RequestQueue queue = Volley.newRequestQueue(context);

            final String pName = MainActivity.pName;
            final String fName = MainActivity.fName;
            final String uName = MainActivity.uName;

            //System.out.println("Bros and remaining Hoes, we got the Project Name, aaaaaand it isssss: " + pName);

            mRef = new Firebase("https://teamup-messenger.firebaseio.com/" + pName);


            mRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    //Getting messages from FireBase
                    //This method gets the messages once the page is initialized and then everytime the server is updated
                    countMessages++;
                    System.out.println(countMessages + ": This is different: " + dataSnapshot.child("First Name").getValue() + ": " + dataSnapshot.child("message").getValue());
                    MemList.add(dataSnapshot.child("First Name").getValue().toString());
                    ChatList.add(dataSnapshot.child("message").getValue().toString());
                    TimeList.add(dataSnapshot.child("User Name").getValue().toString());
                    chatAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

            System.out.println("First Name: " + fName);
            System.out.println("User Name: " + uName);

            list.setAdapter(chatAdapter);
            list.setStackFromBottom(true);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Addding the message to Firebase:
                    if (!editTxt.getText().toString().equals("")) {
                        String message = editTxt.getText().toString().trim();
                        //System.out.printf("This is the formatted string: \"%s\"\n", message);
                        if (!message.equals("")) {
                            Map<String, String> post = new HashMap<String, String>();
                            post.put("First Name", fName);
                            post.put("User Name", dateFormat.format(date));
                            post.put("message", message);
                            mRef.push().setValue(post);

                            editTxt.setText("");
                        }
                    }
                }
            });

        }

        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            //getMenuInflater().inflate(R.menu.menu_chat, menu);
            return true;
        }

        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
//            if (id == R.id.action_back) {
//                Intent i = new Intent(
//                        ChatActivity.this,
//                        TaskActivity.class);
//                startActivity(i);
//            }
            return super.onOptionsItemSelected(item);
        }
}

