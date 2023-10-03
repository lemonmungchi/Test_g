package com.example.k_contest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.k_contest.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class List_Adapter_Route extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    static ArrayList<String> data;

    public List_Adapter_Route(Context context, ArrayList<String> data){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.route_list, null);

        CheckBox route_ch=view.findViewById(R.id.mid_check);
        route_ch.setText(data.get(position));
        EditText route_text=view.findViewById(R.id.route_text);

        return view;
    }
}
