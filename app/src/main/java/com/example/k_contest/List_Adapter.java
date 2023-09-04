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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class List_Adapter extends BaseAdapter{
    Context context;
    LayoutInflater layoutInflater;
    static ArrayList<String> data;

    private String[] vertex = {"창원", "진주", "통영", "사천", "김해","밀양", "거제", "양산", "의령",
            "함양", "창녕", "고성", "남해", "하동", "산청", "함안", "거창", "합천"};

    public int stringToInt(String s) {              // String to Int
        int x = 0;
        for (int i = 0; i < vertex.length; i++) {
            if (vertex[i].equals(s)) x = i;
        }
        return x;
    }



    double[][] region_position = {
            {35.1320, 128.7163},        //창원
            {35.1805, 128.1087},        //진주
            {34.8497, 128.4339},        //통영
            {35.0903, 128.0705},        //사천
            {35.2332, 128.8819},        //김해
            {35.4913, 128.7481},        //밀양
            {34.8918, 128.6206},        //거제
            {35.3385, 129.0265},        //양산
            {35.3227, 128.2878},        //의령
            {35.5202, 127.7259},        //함양
            {35.5414, 128.5004},        //창녕
            {34.9754, 128.3234},        //고성
            {34.8953, 127.8828},        //남해
            {35.0642, 127.7556},        //하동
            {35.4138, 127.8741},        //산청
            {35.2795, 128.4075},        //함안
            {35.6875, 127.9056},        //거창
            {35.5667, 128.1684}         //합천
    };
    String[] url_city={"%ec%b0%bd%ec%9b%90","%ec%a7%84%ec%a3%bc","%ED%86%B5%EC%98%81","%EC%82%AC%EC%B2%9C","%EA%B9%80%ED%95%B4","%EB%B0%80%EC%96%91","%EA%B1%B0%EC%A0%9C"
            ,"%EC%96%91%EC%82%B0","%EC%9D%98%EB%A0%B9","%ED%95%A8%EC%96%91","%ED%95%A8%EC%96%91","%EA%B3%A0%EC%84%B1","%EB%82%A8%ED%95%B4","%ED%95%98%EB%8F%99","%EC%82%B0%EC%B2%AD",
            "%ED%95%A8%EC%95%88","%EA%B1%B0%EC%B0%BD","%ED%95%A9%EC%B2%9C"};


    public List_Adapter(Context context, ArrayList<String> data){
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
        View view = layoutInflater.inflate(R.layout.tour_list, null);

        TextView title = view.findViewById(R.id.title);
        title.setText(data.get(position));



        return view;
    }


}
