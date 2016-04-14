package com.teamup.teamup;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Edwin Prakarsa on 4/13/2016.
 */
public class ChatCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;
    private ArrayList<String> senderlist = new ArrayList<String>();
    private ArrayList<String> TimeList = new ArrayList<String>();
    private String user;
    private String time;



    public ChatCustomAdapter(ArrayList<String> list, ArrayList<String> senderlist, ArrayList<String> TimeList, Context context) {
        this.list = list;
        this.senderlist = senderlist;
        this.TimeList = TimeList;
        this.time = time;
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
            view = inflater.inflate(R.layout.mychatview, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.listitemchat);
        listItemText.setText(list.get(position));


        TextView userText = (TextView)view.findViewById(R.id.user);
        userText.setText(senderlist.get(position));
        TextView timeText = (TextView)view.findViewById(R.id.time);
        timeText.setText(TimeList.get(position));

        return view;
    }
}