package com.teamup.teamup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ProjectActivity extends AppCompatActivity {


    private LinearLayout mLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        final EditText projName = (EditText) findViewById(R.id.editText);
        final EditText StartDate = (EditText) findViewById(R.id.editText2);
        final EditText EndDate = (EditText) findViewById(R.id.editText3);
        final EditText projDesc = (EditText) findViewById(R.id.editText4);
        final CheckBox auth = (CheckBox) findViewById(R.id.checkBox);
        final Button confirm = (Button) findViewById(R.id.button);


        mLayout = (LinearLayout) findViewById(R.id.linearLayout);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Server.getInstance().createProject(projName, projDesc, ,this);
                mLayout.addView(createNewTextView(projName.getText().toString()));
                Intent i = new Intent(
                        ProjectActivity.this,
                        MainActivity.class);
                startActivity(i);

            }
        });
    }

    private Button createNewTextView(String text) {
        final ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final Button button = new Button(this);
        LinearLayout ll = (LinearLayout) findViewById(R.id.linearLayout);
        //textView.setLayoutParams(lparams);
        button.setWidth(ll.getWidth());
        button.setHeight(200);
        button.setText(text);
        return button;
    }
}