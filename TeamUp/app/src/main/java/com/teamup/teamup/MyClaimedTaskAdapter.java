package com.teamup.teamup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MyClaimedTaskAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;
    private String claimedby;



    public MyClaimedTaskAdapter(ArrayList<String> list, String claimedby, Context context) {
        this.list = list;
        this.claimedby = claimedby;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.my_claimed_task, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.listclaimedtask);
        listItemText.setText(list.get(position));

        TextView listclaimedby = (TextView) view.findViewById(R.id.claimedby);
        listclaimedby.setText(" ");

        final Button Complete = (Button)view.findViewById(R.id.complete);

        Complete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Complete.setText("Completed");
                Complete.setClickable(false);

            }
        });



        return view;
    }
}