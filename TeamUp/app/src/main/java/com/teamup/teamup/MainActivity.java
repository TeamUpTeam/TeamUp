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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private LinearLayout mLayout;
    final Context context = this;
    ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
    private ArrayList<String> projectList;
    ListView listViewProj;
    Server x = new Server();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        listViewProj = (ListView) findViewById(R.id.listViewProject);
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.mytextview, arrayList);
        listViewProj.setAdapter(adapter);
        // add button listener

        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.activity_project, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(promptsView);
                final EditText ProjName = (EditText) promptsView.findViewById(R.id.editText);
                ProjName.setSingleLine();
                final EditText StartDate = (EditText) promptsView.findViewById(R.id.editText2);
                StartDate.setSingleLine();
                final EditText EndDate = (EditText) promptsView.findViewById(R.id.editText3);
                EndDate.setSingleLine();
                final EditText ProjDesc = (EditText) promptsView.findViewById(R.id.editText4);
                ProjDesc.setSingleLine();

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK", null)
                        .setNegativeButton("Cancel", null);
                // set dialog message
                final AlertDialog mAlertDialog = alertDialogBuilder.create();
                mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // TODO Do something
                                if (!ProjName.getText().toString().matches("") && !ProjDesc.getText().toString().matches("")) {
                                    mAlertDialog.dismiss();
                                    adapter.add(ProjName.getText().toString());
                                    // next thing you have to do is check if your adapter has changed
                                    adapter.notifyDataSetChanged();
                                    //x.createProject(ProjName.getText().toString(), ProjDesc.getText().toString(),StartDate.getText().toString(),EndDate.getText().toString(),/*userID */  ,context);

                                    listViewProj.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                                        @Override
                                        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                                            Intent i = new Intent(
                                                    MainActivity.this,
                                                    TaskActivity.class);
                                            startActivity(i);
                                        }
                                    });

                                    listViewProj.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                                        @Override
                                        public boolean onItemLongClick(AdapterView<?> parent, View view,
                                                                       final int arg2, long arg3) {
                                            AlertDialog.Builder adb = new AlertDialog.Builder(context);
                                            adb.setTitle("Delete entry");
                                            adb.setMessage("Are you sure you want to delete this entry?");
                                            adb.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // continue with delete
                                                            arrayList.remove(arg2);
                                                            adapter.notifyDataSetChanged();
                                                        }
                                                    })
                                                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // do nothing
                                                        }
                                                    })
                                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                                    .show();
                                            return false;
                                        }
                                    });
                                } else if (ProjName.getText().toString().matches("")) {
                                    ProjName.setError(getString(R.string.error_field_required));
                                } else if (ProjDesc.getText().toString().matches("")) {
                                    ProjDesc.setError(getString(R.string.error_field_required));
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
}