package com.teamup.teamup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TaskActivity extends AppCompatActivity {
    private LinearLayout mLayout;
    private EditText mEditText;
    private Button mButton;
    final Context context = this;
    String TaskName;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab1);

        mLayout = (LinearLayout) findViewById(R.id.TaskLinearLayout);
        // add button listener
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompts, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(promptsView);
                final EditText taskName = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput1);
                final EditText taskDesc = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput2);
                final EditText taskEndDate = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput3);


                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",null)
                        .setNegativeButton("Cancel", null);

                final AlertDialog mAlertDialog = alertDialogBuilder.create();
                mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // TODO Do something
                                if(!taskName.getText().toString().matches("") && !taskDesc.getText().toString().matches("")){
                                    mAlertDialog.dismiss();
                                    mLayout.addView(createNewTextView(taskName.getText().toString()));
                                }else if(taskName.getText().toString().matches("")){
                                    taskName.setError(getString(R.string.error_field_required));
                                }else if(taskDesc.getText().toString().matches("")){
                                    taskDesc.setError(getString(R.string.error_field_required));
                                }
                            }
                        });
                    }
                });
                mAlertDialog.show();


            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        LinearLayout ll = (LinearLayout) findViewById(R.id.TaskLinearLayout);
        //textView.setLayoutParams(lparams);
        button.setWidth(ll.getWidth());
        button.setHeight(200);
        button.setText(text);

        return button;
    }
}