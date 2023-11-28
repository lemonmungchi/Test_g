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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class List_Adapter extends BaseAdapter{
    Context context;
    LayoutInflater layoutInflater;
    static ArrayList<String> data;

    private String[] vertex = {"창원시", "진주시", "통영시", "사천시", "김해시","밀양시", "거제시", "양산시", "의령군",
            "함양군", "창녕군", "고성군", "남해군", "하동군", "산청군", "함안군", "거창군", "합천군"};

    public int stringToInt(String s) {              // String to Int
        int x = 0;
        for (int i = 0; i < vertex.length; i++) {
            if (vertex[i].equals(s)) x = i;
        }
        return x;
    }



    private double[][] region_position = {
            {35.2279, 128.6817},        //창원
            {35.1805, 128.1087},        //진주
            {34.8542, 128.4330},        //통영
            {35.0033, 128.0640},        //사천
            {35.2284, 128.8893},        //김해
            {35.2284, 128.8893},        //밀양
            {34.8805, 128.6210},        //거제
            {35.3469, 129.0374},        //양산
            {35.3221, 128.2616},        //의령
            {35.5204, 127.7251},        //함양
            {35.5445, 128.4922},        //창녕
            {34.9730, 128.3223},        //고성
            {34.8375, 127.8926},        //남해
            {35.0671, 127.7513},        //하동
            {35.4154, 127.8734},        //산청
            {35.2723, 128.4065},        //함안
            {35.6865, 127.9095},        //거창
            {35.5665, 128.1658}         //합천
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

        Button bus_route= view.findViewById(R.id.bus_route);
        Button car_route= view.findViewById(R.id.car_route);



        bus_route.setOnClickListener(new View.OnClickListener() {       //대중교통 길찾기
            @Override
            public void onClick(View view) {
                String item= data.get(position);
                Intent intent;
                String url;
                String encodeResult;
                try {
                    encodeResult = URLEncoder.encode(item, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                String url_f="nmap://route/public?dlat=";
                String url_b="&appname=com.example.ownroadrider";
                List<ResolveInfo> list;
                url = "nmap://actionPath?parameter=value&appname=ownroadrider";

                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.addCategory(Intent.CATEGORY_BROWSABLE);

                list = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                if (list == null || list.isEmpty()) {
                    try {
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url_f+String.valueOf(region_position[stringToInt(data.get(position))][0])+"&dlng="+String.valueOf(region_position[stringToInt(data.get(position))][1])+"&dname="+encodeResult+url_b)));
                    }catch (Exception e){
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.nhn.android.nmap")));
                    }
                } else {
                    context.startActivity(intent);
                }

            }
        });
        car_route.setOnClickListener(new View.OnClickListener() {       //자동차 길찾기
            @Override
            public void onClick(View view) {
                String item= data.get(position);
                Intent intent;
                String url;
                String encodeResult;
                try {
                    encodeResult = URLEncoder.encode(item, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                String url_f="nmap://navigation?dlat=";
                String url_b="&appname=com.example.ownroadrider";
                List<ResolveInfo> list;
                url = "nmap://actionPath?parameter=value&appname=ownroadrider";

                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.addCategory(Intent.CATEGORY_BROWSABLE);

                list = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                if (list == null || list.isEmpty()) {
                    try {
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url_f+String.valueOf(region_position[stringToInt(data.get(position))][0])+"&dlng="+String.valueOf(region_position[stringToInt(data.get(position))][1])+"&dname="+encodeResult+url_b)));
                    }catch (Exception e){
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.nhn.android.nmap")));
                    }
                } else {
                    context.startActivity(intent);
                }


            }
        });

        return view;
    }


}
