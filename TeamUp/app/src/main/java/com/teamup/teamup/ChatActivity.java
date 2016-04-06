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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
public class ChatActivity extends AppCompatActivity{
    private EditText editTxt;
    private Button btn;
    private ListView list;
    private ArrayAdapter<String> chatAdapter;
    private ArrayList<String> ChatList;
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
            final Context context = this;
            countMessages = 0;

            System.out.println("Hello, Sir!");

            Firebase.setAndroidContext(this);
            RequestQueue queue = Volley.newRequestQueue(context);

            final String pName = MainActivity.pName;
            final String fName = MainActivity.fName;
            final String uName = MainActivity.uName;

            System.out.println("Bros and remaining Hoes, we got the Project Name, aaaaaand it isssss: " + pName);

            mRef = new Firebase("https://teamup-messenger.firebaseio.com/" + pName);


            //Trial and Error
            /*mRef.orderByKey().on("child_added", function(snapshot) {
                System.out.println(dataSnapshot.key());
            });*/


            //Checking for firebase data if it can be retrieved
            /*mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    System.out.println(dataSnapshot.getValue());

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    System.out.printf("The read failed: " + firebaseError.getMessage());
                }
            });*/

            mRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    countMessages++;
                    System.out.println(countMessages + ": This is different: " + dataSnapshot.child("First Name").getValue() + ": " + dataSnapshot.child("message").getValue());

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

            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Date date = new Date();
            final String time = dateFormat.format(date);

            chatAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.mychatview, ChatList);

            list.setAdapter(chatAdapter);
            list.setStackFromBottom(true);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ChatList.add(MainActivity.fName + System.lineSeparator() + editTxt.getText().toString() + System.lineSeparator() + time);
                    chatAdapter.notifyDataSetChanged();

                    //Addding the message to Firebase:
                    Map<String, String> post = new HashMap<String, String>();
                    post.put("First Name", fName);
                    post.put("User Name", uName);
                    post.put("message", editTxt.getText().toString());
                    mRef.push().setValue(post);

                    String postID = mRef.getKey();
                    System.out.println("postID is: " + postID);

                    editTxt.setText("");
                }
            });

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
                        ChatActivity.this,
                        TaskActivity.class);
                startActivity(i);
            }
            return super.onOptionsItemSelected(item);
        }
}

