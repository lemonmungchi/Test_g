package com.example.k_contest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class List_Adapter_Review extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    static ArrayList<String> data;

    public List_Adapter_Review(Context context, ArrayList<String> data){
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = layoutInflater.inflate(R.layout.review_rlist, null);

        TextView Review=view.findViewById(R.id.Review);
        Review.setText(data.get(position));

        return view;
    }
}
