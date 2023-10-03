package com.example.k_contest;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.k_contest.fragments.List_Adapter_Route;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Route_choose extends AppCompatActivity {
    // 가중치 곱 함수
    public void multiplyMatix(int[][] newGN, double[][] map) {
        for(int i = 0; i < 18; i++){
            for(int j = 0; j < 18; j++) {
                newGN[i][j] =(int) (newGN[i][j] * map[i][j]);
            }
        }
    }

    private FirebaseFirestore db;

    private ListView Route_List;

    private ArrayList<String> mid_data;

    private ArrayList<Double> mid_lat;
    private ArrayList<Double> mid_long;
    private ArrayList<String> route_data;

    private ArrayList<String> route_data_2;
    private ArrayList<String> route_data_3;
    private ArrayList<String> route_data_4;
    private ArrayList<String> route_data_5;

    private ArrayList<Double> route_lat;
    private ArrayList<Double> route_lat_2;
    private ArrayList<Double> route_lat_3;
    private ArrayList<Double> route_lat_4;
    private ArrayList<Double> route_lat_5;

    private ArrayList<Double> route_long;
    private ArrayList<Double> route_long_2;
    private ArrayList<Double> route_long_3;
    private ArrayList<Double> route_long_4;
    private ArrayList<Double> route_long_5;

    private RadioButton activity;

    private String[] rot_name;

    private RadioButton play;
    private RadioButton ocean;
    private RadioButton river;
    private RadioButton heritage;
    private RadioButton mountain;

    private

    float max1Nature = 368; // 자연 가중치 최대값
    float max1Culture = 656; // 문화 가중치 최대값
    float max1leisure = 104; // 레저 가중치 최대값
    int[] weigth1Nature = {224, 104, 344, 136, 72, 240, 368, 168, 96, 168, 80, 104, 168, 200, 304, 96, 168, 184};   // 자연 가중치
    int[] weigth1Culture = {472, 400, 424, 416, 384, 352, 464, 656, 440, 312, 360, 392, 264, 464, 296, 208, 344, 384};  // 문화 가중치
    int[] weigth1leisure = {56, 104, 88, 40, 32, 80, 40, 16, 32, 16, 48, 72, 32, 80, 16, 56, 56}; // 레저 가중치

    int[][] matrixGN = new int[][]{
            {0,27,Integer.MAX_VALUE,Integer.MAX_VALUE,27,51,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,
                    Integer.MAX_VALUE,35,63,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,31,Integer.MAX_VALUE,Integer.MAX_VALUE},      //창원
            {27,0,Integer.MAX_VALUE,61,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,59,
                    Integer.MAX_VALUE,Integer.MAX_VALUE,64,Integer.MAX_VALUE,58,50,41,Integer.MAX_VALUE,Integer.MAX_VALUE},      //진주
            {Integer.MAX_VALUE,Integer.MAX_VALUE,0,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,100,Integer.MAX_VALUE,Integer.MAX_VALUE,
                    Integer.MAX_VALUE,Integer.MAX_VALUE,79,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE},   //통영
            {Integer.MAX_VALUE,61,Integer.MAX_VALUE,0,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,
                    Integer.MAX_VALUE,Integer.MAX_VALUE,63,22,81,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE},      //사천
            {27,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,0,31,Integer.MAX_VALUE,18,Integer.MAX_VALUE,
                    Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE},      //김해
            {51,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,31,0,Integer.MAX_VALUE,38,Integer.MAX_VALUE,
                    Integer.MAX_VALUE,46,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE},      //밀양
            {Integer.MAX_VALUE,Integer.MAX_VALUE,100,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,0,Integer.MAX_VALUE,Integer.MAX_VALUE,
                    Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE},      //거제
            {Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,18,38,Integer.MAX_VALUE,0,Integer.MAX_VALUE,
                    Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE},   //양산
            {Integer.MAX_VALUE,59,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,0,
                    Integer.MAX_VALUE,50,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,66,30,Integer.MAX_VALUE,28},      //의령
            {Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,
                    0,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,56,85,Integer.MAX_VALUE,29,Integer.MAX_VALUE},      //함양
            {35,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,46,Integer.MAX_VALUE,Integer.MAX_VALUE,50,
                    Integer.MAX_VALUE,0,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,47,Integer.MAX_VALUE,30},      //창녕
            {63,64,79,63,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,
                    Integer.MAX_VALUE,Integer.MAX_VALUE,0,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE},      //고성
            {Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,22,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,
                    Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,0,70,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE},   //남해
            {Integer.MAX_VALUE,58,Integer.MAX_VALUE,81,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,
                    56,Integer.MAX_VALUE,Integer.MAX_VALUE,70,0,69,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE},      //하동
            {Integer.MAX_VALUE,50,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,66,
                    85,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,69,0,Integer.MAX_VALUE,30,31},      //산청
            {31,41,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,30,
                    Integer.MAX_VALUE,47,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,0,Integer.MAX_VALUE,Integer.MAX_VALUE},      //함안
            {Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,
                    29,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,30,Integer.MAX_VALUE,0,27},  //거창
            {Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,28,
                    Integer.MAX_VALUE,30,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,31,Integer.MAX_VALUE,27,0}      //합천
    };

    private String[] vertex = {"창원시", "진주시", "통영시", "사천시", "김해시","밀양시", "거제시", "양산시", "의령군",
            "함양군", "창녕군", "고성군", "남해군", "하동군", "산청군", "함안군", "거창군", "합천군"};

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

    public int stringToInt(String s) {              // String to Int
        int x = 0;
        for (int i = 0; i < vertex.length; i++) {
            if (vertex[i].equals(s)) x = i;
        }
        return x;
    }
    private String first_ca;
    private Button mapFind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_choose);

        db = FirebaseFirestore.getInstance();

        activity=findViewById(R.id.activity);
        play=findViewById(R.id.play);
        ocean=findViewById(R.id.ocean);
        river=findViewById(R.id.river);
        heritage=findViewById(R.id.heritage);
        mountain=findViewById(R.id.mountain);


        Intent intent = getIntent();
        String st = intent.getStringExtra("Start");          //출발지 받아오기
        String ed = intent.getStringExtra("End");             //목적지 받아오기
        boolean curs[]= intent.getBooleanArrayExtra("State"); //체크표시 받아오기
        String[] curs_name={"자연관광","레저관광","문화관광"};
        for(int i=0;i<3;i++){
            if(curs[i]==true){
                first_ca=curs_name[i];
            }
        }

        double coff1 = 1.0; // 1차분류 가중치 계수

        // 자연 가중치 적용
        if(curs[0] == true) {
            for(int i = 0; i < 18; i++) {
                for(int j = 0; j < 18; j++) {
                    matrixGN[i][j] += coff1 * max1Nature / weigth1Nature[j];
                }
            }
        }

        // 레저 가중치 적용
        if(curs[1] == true) {
            for(int i = 0; i < 18; i++) {
                for(int j = 0; j < 18; j++) {
                    matrixGN[i][j] += coff1 * max1leisure / weigth1leisure[j];
                }
            }
        }

        // 문화 가중치 적용
        if(curs[2] == true) {
            for(int i = 0; i < 18; i++) {
                for(int j = 0; j < 18; j++) {
                    matrixGN[i][j] += coff1 * max1Culture / weigth1Culture[j];
                }
            }
        }
        Dijkstra dj = new Dijkstra(18, matrixGN);
        String[]rot =dj.algorithm(vertex[dj.stringToInt(st)],vertex[dj.stringToInt(ed)]);
        Collections.reverse(Arrays.asList(rot));

        route_data = new ArrayList<String>();           //중간 데이터 저장
        route_data_2=new ArrayList<String>();
        route_data_3=new ArrayList<String>();
        route_data_4=new ArrayList<String>();
        route_data_5=new ArrayList<String>();


        route_lat=new ArrayList<Double>();
        route_lat_2=new ArrayList<Double>();
        route_lat_3=new ArrayList<Double>();
        route_lat_4=new ArrayList<Double>();
        route_lat_5=new ArrayList<Double>();


        route_long=new ArrayList<Double>();
        route_long_2=new ArrayList<Double>();
        route_long_3=new ArrayList<Double>();
        route_long_4=new ArrayList<Double>();
        route_long_5=new ArrayList<Double>();


        Route_List = findViewById(R.id.route_list);
        mid_data=new ArrayList<String>();
        mid_lat=new ArrayList<Double>();
        mid_long=new ArrayList<Double>();

        if(rot.length-2>0){
            rot_name=new String[rot.length-2];

            for(int i=1;i<=rot.length-2;i++){
                rot_name[i-1]=rot[i];
            }
        }

        switch (rot.length-2){
            case 0:
                route_data.clear();
                route_lat.clear();
                route_long.clear();
                db.collection("tour_data")
                        .whereEqualTo("city",ed)
                        .whereEqualTo("first_category",first_ca)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_data.add(document.get("tour_name",String.class));
                                        route_lat.add(document.get("lat",Double.class));
                                        route_long.add(document.get("long",Double.class));
                                    }

                                    List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,route_data);
                                    Route_List.setAdapter(RouteListAdapter);


                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
                break;
            case 1:
                mid_data.clear();
                mid_lat.clear();
                mid_long.clear();
                route_data.clear();
                route_lat.clear();
                route_long.clear();
                db.collection("tour_data")
                        .whereEqualTo("city",rot_name[0])
                        .whereEqualTo("first_category",first_ca)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_data.add(document.get("tour_name",String.class));
                                        route_lat.add(document.get("lat",Double.class));
                                        route_long.add(document.get("long",Double.class));
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
                route_data_2.clear();
                route_lat_2.clear();
                route_long_2.clear();
                db.collection("tour_data")
                        .whereEqualTo("city",ed)
                        .whereEqualTo("first_category",first_ca)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_data_2.add(document.get("tour_name",String.class));
                                        route_lat_2.add(document.get("lat",Double.class));
                                        route_long_2.add(document.get("long",Double.class));
                                    }
                                    for(int i=0;i<route_data.size();i++){
                                        mid_data.add(route_data.get(i));
                                        mid_lat.add(route_lat.get(i));
                                        mid_long.add(route_long.get(i));
                                        if(i==5)
                                            break;
                                    }
                                    for(int i=0;i<route_data_2.size();i++){
                                        mid_data.add(route_data_2.get(i));
                                        mid_lat.add(route_lat_2.get(i));
                                        mid_long.add(route_long_2.get(i));
                                        if(i==5)
                                            break;
                                    }
                                    List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
                                    Route_List.setAdapter(RouteListAdapter);

                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
                break;
            case 2:
                mid_data.clear();
                mid_lat.clear();
                mid_long.clear();
                route_data.clear();
                route_lat.clear();
                route_long.clear();
                db.collection("tour_data")
                        .whereEqualTo("city",rot_name[0])
                        .whereEqualTo("first_category",first_ca)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_data.add(document.get("tour_name",String.class));
                                        route_lat.add(document.get("lat",Double.class));
                                        route_long.add(document.get("long",Double.class));
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
                route_data_2.clear();
                route_lat_2.clear();
                route_long_2.clear();
                db.collection("tour_data")
                        .whereEqualTo("city",rot_name[1])
                        .whereEqualTo("first_category",first_ca)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_data_2.add(document.get("tour_name",String.class));
                                        route_lat_2.add(document.get("lat",Double.class));
                                        route_long_2.add(document.get("long",Double.class));
                                    }

                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
                route_data_3.clear();
                route_lat_3.clear();
                route_long_3.clear();
                db.collection("tour_data")
                        .whereEqualTo("city",ed)
                        .whereEqualTo("first_category",first_ca)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_data_3.add(document.get("tour_name",String.class));
                                        route_lat_3.add(document.get("lat",Double.class));
                                        route_long_3.add(document.get("long",Double.class));
                                    }
                                    for(int i=0;i<route_data.size();i++){
                                        mid_data.add(route_data.get(i));
                                        mid_lat.add(route_lat.get(i));
                                        mid_long.add(route_long.get(i));
                                        if(i==5)
                                            break;
                                    }
                                    for(int i=0;i<route_data_2.size();i++){
                                        mid_data.add(route_data_2.get(i));
                                        mid_lat.add(route_lat_2.get(i));
                                        mid_long.add(route_long_2.get(i));
                                        if(i==5)
                                            break;
                                    }
                                    for(int i=0;i<route_data_3.size();i++){
                                        mid_data.add(route_data_3.get(i));
                                        mid_lat.add(route_lat_3.get(i));
                                        mid_long.add(route_long_3.get(i));
                                        if(i==5)
                                            break;
                                    }
                                    List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
                                    Route_List.setAdapter(RouteListAdapter);

                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
                break;
            case 3:
                mid_data.clear();
                mid_lat.clear();
                mid_long.clear();
                route_data.clear();
                route_lat.clear();
                route_long.clear();
                db.collection("tour_data")
                        .whereEqualTo("city",rot_name[0])
                        .whereEqualTo("first_category",first_ca)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_data.add(document.get("tour_name",String.class));
                                        route_lat.add(document.get("lat",Double.class));
                                        route_long.add(document.get("long",Double.class));
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
                route_data_2.clear();
                route_lat_2.clear();
                route_long_2.clear();
                db.collection("tour_data")
                        .whereEqualTo("city",rot_name[1])
                        .whereEqualTo("first_category",first_ca)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_data_2.add(document.get("tour_name",String.class));
                                        route_lat_2.add(document.get("lat",Double.class));
                                        route_long_2.add(document.get("long",Double.class));
                                    }

                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
                route_data_3.clear();
                route_lat_3.clear();
                route_long_3.clear();
                db.collection("tour_data")
                        .whereEqualTo("city",rot_name[2])
                        .whereEqualTo("first_category",first_ca)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_data_3.add(document.get("tour_name",String.class));
                                        route_lat_3.add(document.get("lat",Double.class));
                                        route_long_3.add(document.get("long",Double.class));
                                    }

                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
                route_data_4.clear();
                route_lat_4.clear();
                route_long_4.clear();
                db.collection("tour_data")
                        .whereEqualTo("city",ed)
                        .whereEqualTo("first_category",first_ca)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_data_4.add(document.get("tour_name",String.class));
                                        route_lat_4.add(document.get("lat",Double.class));
                                        route_long_4.add(document.get("long",Double.class));
                                    }
                                    for(int i=0;i<route_data.size();i++){
                                        mid_data.add(route_data.get(i));
                                        mid_lat.add(route_lat.get(i));
                                        mid_long.add(route_long.get(i));
                                        if(i==5)
                                            break;
                                    }
                                    for(int i=0;i<route_data_2.size();i++){
                                        mid_data.add(route_data_2.get(i));
                                        mid_lat.add(route_lat_2.get(i));
                                        mid_long.add(route_long_2.get(i));
                                        if(i==5)
                                            break;
                                    }
                                    for(int i=0;i<route_data_3.size();i++){
                                        mid_data.add(route_data_3.get(i));
                                        mid_lat.add(route_lat_3.get(i));
                                        mid_long.add(route_long_3.get(i));
                                        if(i==5)
                                            break;
                                    }
                                    for(int i=0;i<route_data_4.size();i++){
                                        mid_data.add(route_data_4.get(i));
                                        mid_lat.add(route_lat_4.get(i));
                                        mid_long.add(route_long_4.get(i));
                                        if(i==5)
                                            break;
                                    }
                                    List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
                                    Route_List.setAdapter(RouteListAdapter);

                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
                break;
            case 4:
                mid_data.clear();
                mid_lat.clear();
                mid_long.clear();
                route_data.clear();
                route_lat.clear();
                route_long.clear();
                db.collection("tour_data")
                        .whereEqualTo("city",rot_name[0])
                        .whereEqualTo("first_category",first_ca)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_data.add(document.get("tour_name",String.class));
                                        route_lat.add(document.get("lat",Double.class));
                                        route_long.add(document.get("long",Double.class));
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
                route_data_2.clear();
                route_lat_2.clear();
                route_long_2.clear();
                db.collection("tour_data")
                        .whereEqualTo("city",rot_name[1])
                        .whereEqualTo("first_category",first_ca)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_data_2.add(document.get("tour_name",String.class));
                                        route_lat_2.add(document.get("lat",Double.class));
                                        route_long_2.add(document.get("long",Double.class));
                                    }


                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
                route_data_3.clear();
                route_lat_3.clear();
                route_long_3.clear();
                db.collection("tour_data")
                        .whereEqualTo("city",rot_name[2])
                        .whereEqualTo("first_category",first_ca)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_data_3.add(document.get("tour_name",String.class));
                                        route_lat_3.add(document.get("lat",Double.class));
                                        route_long_3.add(document.get("long",Double.class));
                                    }

                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
                route_data_4.clear();
                route_lat_4.clear();
                route_long_4.clear();
                db.collection("tour_data")
                        .whereEqualTo("city",rot_name[3])
                        .whereEqualTo("first_category",first_ca)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_data_4.add(document.get("tour_name",String.class));
                                        route_lat_4.add(document.get("lat",Double.class));
                                        route_long_4.add(document.get("long",Double.class));
                                    }


                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
                route_data_5.clear();
                route_lat_5.clear();
                route_long_5.clear();
                db.collection("tour_data")
                        .whereEqualTo("city",ed)
                        .whereEqualTo("first_category",first_ca)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_data_5.add(document.get("tour_name",String.class));
                                        route_lat_5.add(document.get("lat",Double.class));
                                        route_long_5.add(document.get("long",Double.class));
                                    }

                                    for(int i=0;i<route_data.size();i++){
                                        mid_data.add(route_data.get(i));
                                        mid_lat.add(route_lat.get(i));
                                        mid_long.add(route_long.get(i));
                                        if(i==5)
                                            break;
                                    }
                                    for(int i=0;i<route_data_2.size();i++){
                                        mid_data.add(route_data_2.get(i));
                                        mid_lat.add(route_lat_2.get(i));
                                        mid_long.add(route_long_2.get(i));
                                        if(i==5)
                                            break;
                                    }
                                    for(int i=0;i<route_data_3.size();i++){
                                        mid_data.add(route_data_3.get(i));
                                        mid_lat.add(route_lat_3.get(i));
                                        mid_long.add(route_long_3.get(i));
                                        if(i==5)
                                            break;
                                    }
                                    for(int i=0;i<route_data_4.size();i++){
                                        mid_data.add(route_data_4.get(i));
                                        mid_lat.add(route_lat_4.get(i));
                                        mid_long.add(route_long_4.get(i));
                                        if(i==5)
                                            break;
                                    }
                                    for(int i=0;i<route_data_5.size();i++){
                                        mid_data.add(route_data_5.get(i));
                                        mid_lat.add(route_lat_5.get(i));
                                        mid_long.add(route_long_5.get(i));
                                        if(i==5)
                                            break;
                                    }
                                    List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
                                    Route_List.setAdapter(RouteListAdapter);


                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
                break;

        }
        activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (rot.length-2){
                    case 0:
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",ed)
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","액티비티")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("tour_name",String.class));
                                                route_lat.add(document.get("lat",Double.class));
                                                route_long.add(document.get("long",Double.class));
                                            }

                                            List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,route_data);
                                            Route_List.setAdapter(RouteListAdapter);


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        break;
                    case 1:
                        mid_data.clear();
                        mid_lat.clear();
                        mid_long.clear();
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[0])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","액티비티")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("tour_name",String.class));
                                                route_lat.add(document.get("lat",Double.class));
                                                route_long.add(document.get("long",Double.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",ed)
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","액티비티")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("tour_name",String.class));
                                                route_lat_2.add(document.get("lat",Double.class));
                                                route_long_2.add(document.get("long",Double.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
                                            Route_List.setAdapter(RouteListAdapter);

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        break;
                    case 2:
                        mid_data.clear();
                        mid_lat.clear();
                        mid_long.clear();
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[0])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","액티비티")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("tour_name",String.class));
                                                route_lat.add(document.get("lat",Double.class));
                                                route_long.add(document.get("long",Double.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[1])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","액티비티")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("tour_name",String.class));
                                                route_lat_2.add(document.get("lat",Double.class));
                                                route_long_2.add(document.get("long",Double.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",ed)
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","액티비티")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("tour_name",String.class));
                                                route_lat_3.add(document.get("lat",Double.class));
                                                route_long_3.add(document.get("long",Double.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
                                            Route_List.setAdapter(RouteListAdapter);

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        break;
                    case 3:
                        mid_data.clear();
                        mid_lat.clear();
                        mid_long.clear();
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[0])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","액티비티")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("tour_name",String.class));
                                                route_lat.add(document.get("lat",Double.class));
                                                route_long.add(document.get("long",Double.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[1])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","액티비티")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("tour_name",String.class));
                                                route_lat_2.add(document.get("lat",Double.class));
                                                route_long_2.add(document.get("long",Double.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[2])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","액티비티")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("tour_name",String.class));
                                                route_lat_3.add(document.get("lat",Double.class));
                                                route_long_3.add(document.get("long",Double.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",ed)
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","액티비티")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("tour_name",String.class));
                                                route_lat_4.add(document.get("lat",Double.class));
                                                route_long_4.add(document.get("long",Double.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
                                            Route_List.setAdapter(RouteListAdapter);

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        break;
                    case 4:
                        mid_data.clear();
                        mid_lat.clear();
                        mid_long.clear();
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[0])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","액티비티")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("tour_name",String.class));
                                                route_lat.add(document.get("lat",Double.class));
                                                route_long.add(document.get("long",Double.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[1])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","액티비티")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("tour_name",String.class));
                                                route_lat_2.add(document.get("lat",Double.class));
                                                route_long_2.add(document.get("long",Double.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[2])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","액티비티")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("tour_name",String.class));
                                                route_lat_3.add(document.get("lat",Double.class));
                                                route_long_3.add(document.get("long",Double.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[3])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","액티비티")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("tour_name",String.class));
                                                route_lat_4.add(document.get("lat",Double.class));
                                                route_long_4.add(document.get("long",Double.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_5.clear();
                        route_lat_5.clear();
                        route_long_5.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",ed)
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","액티비티")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_5.add(document.get("tour_name",String.class));
                                                route_lat_5.add(document.get("lat",Double.class));
                                                route_long_5.add(document.get("long",Double.class));
                                            }

                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_5.size();i++){
                                                mid_data.add(route_data_5.get(i));
                                                mid_lat.add(route_lat_5.get(i));
                                                mid_long.add(route_long_5.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
                                            Route_List.setAdapter(RouteListAdapter);


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        break;
                }
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (rot.length-2){
                    case 0:
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",ed)
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","놀거리")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("tour_name",String.class));
                                                route_lat.add(document.get("lat",Double.class));
                                                route_long.add(document.get("long",Double.class));
                                            }

                                            List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,route_data);
                                            Route_List.setAdapter(RouteListAdapter);


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        break;
                    case 1:
                        mid_data.clear();
                        mid_lat.clear();
                        mid_long.clear();
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[0])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","놀거리")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("tour_name",String.class));
                                                route_lat.add(document.get("lat",Double.class));
                                                route_long.add(document.get("long",Double.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",ed)
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","놀거리")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("tour_name",String.class));
                                                route_lat_2.add(document.get("lat",Double.class));
                                                route_long_2.add(document.get("long",Double.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
                                            Route_List.setAdapter(RouteListAdapter);

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        break;
                    case 2:
                        mid_data.clear();
                        mid_lat.clear();
                        mid_long.clear();
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[0])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","놀거리")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("tour_name",String.class));
                                                route_lat.add(document.get("lat",Double.class));
                                                route_long.add(document.get("long",Double.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[1])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","놀거리")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("tour_name",String.class));
                                                route_lat_2.add(document.get("lat",Double.class));
                                                route_long_2.add(document.get("long",Double.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",ed)
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","놀거리")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("tour_name",String.class));
                                                route_lat_3.add(document.get("lat",Double.class));
                                                route_long_3.add(document.get("long",Double.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
                                            Route_List.setAdapter(RouteListAdapter);

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        break;
                    case 3:
                        mid_data.clear();
                        mid_lat.clear();
                        mid_long.clear();
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[0])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","놀거리")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("tour_name",String.class));
                                                route_lat.add(document.get("lat",Double.class));
                                                route_long.add(document.get("long",Double.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[1])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","놀거리")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("tour_name",String.class));
                                                route_lat_2.add(document.get("lat",Double.class));
                                                route_long_2.add(document.get("long",Double.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[2])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","놀거리")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("tour_name",String.class));
                                                route_lat_3.add(document.get("lat",Double.class));
                                                route_long_3.add(document.get("long",Double.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",ed)
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","놀거리")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("tour_name",String.class));
                                                route_lat_4.add(document.get("lat",Double.class));
                                                route_long_4.add(document.get("long",Double.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
                                            Route_List.setAdapter(RouteListAdapter);

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        break;
                    case 4:
                        mid_data.clear();
                        mid_lat.clear();
                        mid_long.clear();
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[0])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","놀거리")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("tour_name",String.class));
                                                route_lat.add(document.get("lat",Double.class));
                                                route_long.add(document.get("long",Double.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[1])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","놀거리")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("tour_name",String.class));
                                                route_lat_2.add(document.get("lat",Double.class));
                                                route_long_2.add(document.get("long",Double.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[2])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","놀거리")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("tour_name",String.class));
                                                route_lat_3.add(document.get("lat",Double.class));
                                                route_long_3.add(document.get("long",Double.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[3])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","놀거리")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("tour_name",String.class));
                                                route_lat_4.add(document.get("lat",Double.class));
                                                route_long_4.add(document.get("long",Double.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_5.clear();
                        route_lat_5.clear();
                        route_long_5.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",ed)
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","놀거리")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_5.add(document.get("tour_name",String.class));
                                                route_lat_5.add(document.get("lat",Double.class));
                                                route_long_5.add(document.get("long",Double.class));
                                            }

                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_5.size();i++){
                                                mid_data.add(route_data_5.get(i));
                                                mid_lat.add(route_lat_5.get(i));
                                                mid_long.add(route_long_5.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
                                            Route_List.setAdapter(RouteListAdapter);


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        break;
                }
            }
        });
        ocean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (rot.length-2){
                    case 0:
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",ed)
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","바다")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("tour_name",String.class));
                                                route_lat.add(document.get("lat",Double.class));
                                                route_long.add(document.get("long",Double.class));
                                            }

                                            List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,route_data);
                                            Route_List.setAdapter(RouteListAdapter);


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        break;
                    case 1:
                        mid_data.clear();
                        mid_lat.clear();
                        mid_long.clear();
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[0])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","바다")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("tour_name",String.class));
                                                route_lat.add(document.get("lat",Double.class));
                                                route_long.add(document.get("long",Double.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",ed)
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","바다")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("tour_name",String.class));
                                                route_lat_2.add(document.get("lat",Double.class));
                                                route_long_2.add(document.get("long",Double.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
                                            Route_List.setAdapter(RouteListAdapter);

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        break;
                    case 2:
                        mid_data.clear();
                        mid_lat.clear();
                        mid_long.clear();
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[0])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","바다")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("tour_name",String.class));
                                                route_lat.add(document.get("lat",Double.class));
                                                route_long.add(document.get("long",Double.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[1])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","바다")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("tour_name",String.class));
                                                route_lat_2.add(document.get("lat",Double.class));
                                                route_long_2.add(document.get("long",Double.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",ed)
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","바다")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("tour_name",String.class));
                                                route_lat_3.add(document.get("lat",Double.class));
                                                route_long_3.add(document.get("long",Double.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
                                            Route_List.setAdapter(RouteListAdapter);

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        break;
                    case 3:
                        mid_data.clear();
                        mid_lat.clear();
                        mid_long.clear();
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[0])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","바다")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("tour_name",String.class));
                                                route_lat.add(document.get("lat",Double.class));
                                                route_long.add(document.get("long",Double.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[1])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","바다")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("tour_name",String.class));
                                                route_lat_2.add(document.get("lat",Double.class));
                                                route_long_2.add(document.get("long",Double.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[2])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","바다")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("tour_name",String.class));
                                                route_lat_3.add(document.get("lat",Double.class));
                                                route_long_3.add(document.get("long",Double.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",ed)
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","바다")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("tour_name",String.class));
                                                route_lat_4.add(document.get("lat",Double.class));
                                                route_long_4.add(document.get("long",Double.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
                                            Route_List.setAdapter(RouteListAdapter);

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        break;
                    case 4:
                        mid_data.clear();
                        mid_lat.clear();
                        mid_long.clear();
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[0])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","바다")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("tour_name",String.class));
                                                route_lat.add(document.get("lat",Double.class));
                                                route_long.add(document.get("long",Double.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[1])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","바다")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("tour_name",String.class));
                                                route_lat_2.add(document.get("lat",Double.class));
                                                route_long_2.add(document.get("long",Double.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[2])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","바다")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("tour_name",String.class));
                                                route_lat_3.add(document.get("lat",Double.class));
                                                route_long_3.add(document.get("long",Double.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[3])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","바다")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("tour_name",String.class));
                                                route_lat_4.add(document.get("lat",Double.class));
                                                route_long_4.add(document.get("long",Double.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_5.clear();
                        route_lat_5.clear();
                        route_long_5.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",ed)
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","바다")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_5.add(document.get("tour_name",String.class));
                                                route_lat_5.add(document.get("lat",Double.class));
                                                route_long_5.add(document.get("long",Double.class));
                                            }

                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_5.size();i++){
                                                mid_data.add(route_data_5.get(i));
                                                mid_lat.add(route_lat_5.get(i));
                                                mid_long.add(route_long_5.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
                                            Route_List.setAdapter(RouteListAdapter);


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        break;
                }
            }
        });
        river.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (rot.length-2){
                    case 0:
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",ed)
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","강")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("tour_name",String.class));
                                                route_lat.add(document.get("lat",Double.class));
                                                route_long.add(document.get("long",Double.class));
                                            }

                                            List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,route_data);
                                            Route_List.setAdapter(RouteListAdapter);


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        break;
                    case 1:
                        mid_data.clear();
                        mid_lat.clear();
                        mid_long.clear();
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[0])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","강")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("tour_name",String.class));
                                                route_lat.add(document.get("lat",Double.class));
                                                route_long.add(document.get("long",Double.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",ed)
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","강")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("tour_name",String.class));
                                                route_lat_2.add(document.get("lat",Double.class));
                                                route_long_2.add(document.get("long",Double.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
                                            Route_List.setAdapter(RouteListAdapter);

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        break;
                    case 2:
                        mid_data.clear();
                        mid_lat.clear();
                        mid_long.clear();
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[0])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","강")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("tour_name",String.class));
                                                route_lat.add(document.get("lat",Double.class));
                                                route_long.add(document.get("long",Double.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[1])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","강")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("tour_name",String.class));
                                                route_lat_2.add(document.get("lat",Double.class));
                                                route_long_2.add(document.get("long",Double.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",ed)
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","강")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("tour_name",String.class));
                                                route_lat_3.add(document.get("lat",Double.class));
                                                route_long_3.add(document.get("long",Double.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
                                            Route_List.setAdapter(RouteListAdapter);

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        break;
                    case 3:
                        mid_data.clear();
                        mid_lat.clear();
                        mid_long.clear();
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[0])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","강")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("tour_name",String.class));
                                                route_lat.add(document.get("lat",Double.class));
                                                route_long.add(document.get("long",Double.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[1])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","강")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("tour_name",String.class));
                                                route_lat_2.add(document.get("lat",Double.class));
                                                route_long_2.add(document.get("long",Double.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[2])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","강")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("tour_name",String.class));
                                                route_lat_3.add(document.get("lat",Double.class));
                                                route_long_3.add(document.get("long",Double.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",ed)
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","강")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("tour_name",String.class));
                                                route_lat_4.add(document.get("lat",Double.class));
                                                route_long_4.add(document.get("long",Double.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
                                            Route_List.setAdapter(RouteListAdapter);

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        break;
                    case 4:
                        mid_data.clear();
                        mid_lat.clear();
                        mid_long.clear();
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[0])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","강")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("tour_name",String.class));
                                                route_lat.add(document.get("lat",Double.class));
                                                route_long.add(document.get("long",Double.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[1])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","강")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("tour_name",String.class));
                                                route_lat_2.add(document.get("lat",Double.class));
                                                route_long_2.add(document.get("long",Double.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[2])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","강")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("tour_name",String.class));
                                                route_lat_3.add(document.get("lat",Double.class));
                                                route_long_3.add(document.get("long",Double.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[3])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","강")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("tour_name",String.class));
                                                route_lat_4.add(document.get("lat",Double.class));
                                                route_long_4.add(document.get("long",Double.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_5.clear();
                        route_lat_5.clear();
                        route_long_5.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",ed)
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","강")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_5.add(document.get("tour_name",String.class));
                                                route_lat_5.add(document.get("lat",Double.class));
                                                route_long_5.add(document.get("long",Double.class));
                                            }

                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_5.size();i++){
                                                mid_data.add(route_data_5.get(i));
                                                mid_lat.add(route_lat_5.get(i));
                                                mid_long.add(route_long_5.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
                                            Route_List.setAdapter(RouteListAdapter);


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        break;
                }
            }
        });
        heritage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (rot.length-2){
                    case 0:
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",ed)
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","문화재")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("tour_name",String.class));
                                                route_lat.add(document.get("lat",Double.class));
                                                route_long.add(document.get("long",Double.class));
                                            }

                                            List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,route_data);
                                            Route_List.setAdapter(RouteListAdapter);


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        break;
                    case 1:
                        mid_data.clear();
                        mid_lat.clear();
                        mid_long.clear();
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[0])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","문화재")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("tour_name",String.class));
                                                route_lat.add(document.get("lat",Double.class));
                                                route_long.add(document.get("long",Double.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",ed)
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","문화재")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("tour_name",String.class));
                                                route_lat_2.add(document.get("lat",Double.class));
                                                route_long_2.add(document.get("long",Double.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
                                            Route_List.setAdapter(RouteListAdapter);

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        break;
                    case 2:
                        mid_data.clear();
                        mid_lat.clear();
                        mid_long.clear();
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[0])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","문화재")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("tour_name",String.class));
                                                route_lat.add(document.get("lat",Double.class));
                                                route_long.add(document.get("long",Double.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[1])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","문화재")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("tour_name",String.class));
                                                route_lat_2.add(document.get("lat",Double.class));
                                                route_long_2.add(document.get("long",Double.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",ed)
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","문화재")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("tour_name",String.class));
                                                route_lat_3.add(document.get("lat",Double.class));
                                                route_long_3.add(document.get("long",Double.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
                                            Route_List.setAdapter(RouteListAdapter);

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        break;
                    case 3:
                        mid_data.clear();
                        mid_lat.clear();
                        mid_long.clear();
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[0])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","문화재")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("tour_name",String.class));
                                                route_lat.add(document.get("lat",Double.class));
                                                route_long.add(document.get("long",Double.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[1])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","문화재")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("tour_name",String.class));
                                                route_lat_2.add(document.get("lat",Double.class));
                                                route_long_2.add(document.get("long",Double.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[2])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","문화재")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("tour_name",String.class));
                                                route_lat_3.add(document.get("lat",Double.class));
                                                route_long_3.add(document.get("long",Double.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",ed)
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","문화재")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("tour_name",String.class));
                                                route_lat_4.add(document.get("lat",Double.class));
                                                route_long_4.add(document.get("long",Double.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
                                            Route_List.setAdapter(RouteListAdapter);

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        break;
                    case 4:
                        mid_data.clear();
                        mid_lat.clear();
                        mid_long.clear();
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[0])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","문화재")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("tour_name",String.class));
                                                route_lat.add(document.get("lat",Double.class));
                                                route_long.add(document.get("long",Double.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[1])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","문화재")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("tour_name",String.class));
                                                route_lat_2.add(document.get("lat",Double.class));
                                                route_long_2.add(document.get("long",Double.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[2])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","문화재")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("tour_name",String.class));
                                                route_lat_3.add(document.get("lat",Double.class));
                                                route_long_3.add(document.get("long",Double.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[3])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","문화재")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("tour_name",String.class));
                                                route_lat_4.add(document.get("lat",Double.class));
                                                route_long_4.add(document.get("long",Double.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_5.clear();
                        route_lat_5.clear();
                        route_long_5.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",ed)
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","문화재")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_5.add(document.get("tour_name",String.class));
                                                route_lat_5.add(document.get("lat",Double.class));
                                                route_long_5.add(document.get("long",Double.class));
                                            }

                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_5.size();i++){
                                                mid_data.add(route_data_5.get(i));
                                                mid_lat.add(route_lat_5.get(i));
                                                mid_long.add(route_long_5.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
                                            Route_List.setAdapter(RouteListAdapter);


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        break;
                }
            }
        });
        mountain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (rot.length-2){
                    case 0:
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",ed)
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","산과계곡")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("tour_name",String.class));
                                                route_lat.add(document.get("lat",Double.class));
                                                route_long.add(document.get("long",Double.class));
                                            }

                                            List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,route_data);
                                            Route_List.setAdapter(RouteListAdapter);


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        break;
                    case 1:
                        mid_data.clear();
                        mid_lat.clear();
                        mid_long.clear();
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[0])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","산과계곡")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("tour_name",String.class));
                                                route_lat.add(document.get("lat",Double.class));
                                                route_long.add(document.get("long",Double.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",ed)
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","산과계곡")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("tour_name",String.class));
                                                route_lat_2.add(document.get("lat",Double.class));
                                                route_long_2.add(document.get("long",Double.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
                                            Route_List.setAdapter(RouteListAdapter);

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        break;
                    case 2:
                        mid_data.clear();
                        mid_lat.clear();
                        mid_long.clear();
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[0])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","산과계곡")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("tour_name",String.class));
                                                route_lat.add(document.get("lat",Double.class));
                                                route_long.add(document.get("long",Double.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[1])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","산과계곡")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("tour_name",String.class));
                                                route_lat_2.add(document.get("lat",Double.class));
                                                route_long_2.add(document.get("long",Double.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",ed)
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","산과계곡")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("tour_name",String.class));
                                                route_lat_3.add(document.get("lat",Double.class));
                                                route_long_3.add(document.get("long",Double.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
                                            Route_List.setAdapter(RouteListAdapter);

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        break;
                    case 3:
                        mid_data.clear();
                        mid_lat.clear();
                        mid_long.clear();
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[0])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","산과계곡")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("tour_name",String.class));
                                                route_lat.add(document.get("lat",Double.class));
                                                route_long.add(document.get("long",Double.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[1])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","산과계곡")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("tour_name",String.class));
                                                route_lat_2.add(document.get("lat",Double.class));
                                                route_long_2.add(document.get("long",Double.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[2])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","산과계곡")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("tour_name",String.class));
                                                route_lat_3.add(document.get("lat",Double.class));
                                                route_long_3.add(document.get("long",Double.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",ed)
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","산과계곡")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("tour_name",String.class));
                                                route_lat_4.add(document.get("lat",Double.class));
                                                route_long_4.add(document.get("long",Double.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
                                            Route_List.setAdapter(RouteListAdapter);

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        break;
                    case 4:
                        mid_data.clear();
                        mid_lat.clear();
                        mid_long.clear();
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[0])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","산과계곡")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("tour_name",String.class));
                                                route_lat.add(document.get("lat",Double.class));
                                                route_long.add(document.get("long",Double.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[1])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","산과계곡")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("tour_name",String.class));
                                                route_lat_2.add(document.get("lat",Double.class));
                                                route_long_2.add(document.get("long",Double.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[2])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","산과계곡")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("tour_name",String.class));
                                                route_lat_3.add(document.get("lat",Double.class));
                                                route_long_3.add(document.get("long",Double.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",rot_name[3])
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","산과계곡")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("tour_name",String.class));
                                                route_lat_4.add(document.get("lat",Double.class));
                                                route_long_4.add(document.get("long",Double.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_5.clear();
                        route_lat_5.clear();
                        route_long_5.clear();
                        db.collection("tour_data")
                                .whereEqualTo("city",ed)
                                .whereEqualTo("first_category",first_ca)
                                .whereEqualTo("second_category","산과계곡")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_5.add(document.get("tour_name",String.class));
                                                route_lat_5.add(document.get("lat",Double.class));
                                                route_long_5.add(document.get("long",Double.class));
                                            }

                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_5.size();i++){
                                                mid_data.add(route_data_5.get(i));
                                                mid_lat.add(route_lat_5.get(i));
                                                mid_long.add(route_long_5.get(i));
                                                if(i==5)
                                                    break;
                                            }
                                            List_Adapter_Route RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
                                            Route_List.setAdapter(RouteListAdapter);


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        break;
                }
            }
        });




        mapFind=findViewById(R.id.mapFind);

        mapFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentTo = new Intent(getApplicationContext(), Map_Basic.class);
                intent.putExtra("Start", st);
                intent.putExtra("End", ed);
                intent.putExtra("State",curs);
                startActivity(intentTo);


                //dj.algorithm('출발지', '도착지');
            }
        });
    }
}