package com.teamup.teamup;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Edwin Prakarsa on 2/28/2016.
 */
public class ProjectActivity extends AppCompatActivity{
    private LinearLayout mLayout;
    private EditText mEditText;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);

        mLayout = (LinearLayout) findViewById(R.id.linearlayout2);

        fab.setOnClickListener(onClick());
        //TextView textView = new TextView(this);
        //textView.setText("New text");


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProjectActivity.this);
                builder.setPositiveButton(android.R.string.ok, null);
                AlertDialog ad = builder.create();
                final EditText input = new EditText(ProjectActivity.this);
                input.setSingleLine();
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                ad.setView(input);
                ad.setTitle("Project Name");
                ad.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if(input.getText().toString().matches("")){
                                    input.setError("This field is required");
                                }else {
                                    mLayout.addView(createNewTextView(input.getText().toString()));
                                }
                            }
                        });

                ad.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });


            }
        });
    }

    private View.OnClickListener onClick() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mLayout.addView(createNewTextView(mEditText.getText().toString()));
            }
        };
    }

    private TextView createNewTextView(String text) {
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
