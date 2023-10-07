package com.example.k_contest;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Route_choose extends AppCompatActivity {
    // 가중치 곱 함수
    public void multiplyMatix(int[][] newGN, double[][] map) {
        for(int i = 0; i < 18; i++){
            for(int j = 0; j < 18; j++) {
                newGN[i][j] =(int) (newGN[i][j] * map[i][j]);
            }
        }
    }

    private int rot_n;
    private FirebaseFirestore db;

    private ListView Route_List;

    private ArrayList<String> mid_data;

    private List_Adapter_Route RouteListAdapter;

    private ArrayList<String> mid_lat;
    private ArrayList<String> mid_long;
    private ArrayList<String> route_data;

    private ArrayList<String> route_data_2;
    private ArrayList<String> route_data_3;
    private ArrayList<String> route_data_4;
    private ArrayList<String> route_data_5;

    private ArrayList<String> route_lat;
    private ArrayList<String> route_lat_2;
    private ArrayList<String> route_lat_3;
    private ArrayList<String> route_lat_4;
    private ArrayList<String> route_lat_5;

    private ArrayList<String> route_long;
    private ArrayList<String> route_long_2;
    private ArrayList<String> route_long_3;
    private ArrayList<String> route_long_4;
    private ArrayList<String> route_long_5;

    private RadioButton activity;

    private String[] rot_name;

    private RadioButton river_h;
    private RadioButton golf;
    private RadioButton park;
    private RadioButton cele;
    private RadioButton nak;
    private RadioButton road;
    private RadioButton activiy;
    private RadioButton culture;
    private RadioButton museum;
    private RadioButton temple;
    private RadioButton mountain;
    private RadioButton nature;
    private RadioButton island;
    private RadioButton ski;
    private RadioButton camp;
    private RadioButton cave;
    private RadioButton na_park;
    private RadioButton sport;
    private RadioButton camera;
    private RadioButton camp_s;



    private TextView route_text;


    private ArrayList<Boolean> is_Checked;

    float max1Nature = 368; // 자연 가중치 최대값
    float max1Culture = 656; // 문화 가중치 최대값
    float max1leisure = 104; // 레저 가중치 최대값
    int[] weigth1Nature = {224, 104, 344, 136, 72, 240, 368, 168, 96, 168, 80, 104, 168, 200, 304, 96, 168, 184};   // 자연 가중치
    int[] weigth1Culture = {472, 400, 424, 416, 384, 352, 464, 656, 440, 312, 360, 392, 264, 464, 296, 208, 344, 384};  // 문화 가중치
    int[] weigth1leisure = {56,24, 104, 88, 40, 32, 80, 40, 16, 32, 16, 48, 72, 32, 80, 16, 56, 56}; // 레저 가중치

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


    private ArrayList<Double> result_lat;
    private ArrayList<Double> result_long;
    private ArrayList<String> result_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_choose);

        db = FirebaseFirestore.getInstance();

        route_text=findViewById(R.id.route_text);

        river_h=findViewById(R.id.river_h);
        golf=findViewById(R.id.golf);
        park=findViewById(R.id.park);
        cele=findViewById(R.id.cele);
        nak=findViewById(R.id.nak);
        road=findViewById(R.id.road);
        activiy=findViewById(R.id.activiy);
        culture=findViewById(R.id.culture);
        museum=findViewById(R.id.museum);
        temple=findViewById(R.id.temple);
        mountain=findViewById(R.id.mountain);
        nature=findViewById(R.id.nature);
        island=findViewById(R.id.island);
        ski=findViewById(R.id.ski);
        camp=findViewById(R.id.camp);
        cave=findViewById(R.id.cave);
        na_park=findViewById(R.id.na_park);
        sport=findViewById(R.id.sport);
        camera=findViewById(R.id.camera);
        camp_s=findViewById(R.id.camp_s);


        Route_List = findViewById(R.id.route_list);

        result_lat=new ArrayList<Double>();
        result_long=new ArrayList<Double>();
        result_name=new ArrayList<String>();


        Intent intent = getIntent();
        String st = intent.getStringExtra("Start");          //출발지 받아오기
        String ed = intent.getStringExtra("End");             //목적지 받아오기
        boolean curs[]= intent.getBooleanArrayExtra("State"); //체크표시 받아오기
        String[] curs_name={"자연관광","레저관광","문화관광"};
        String[] ca_name={"강과호수","골프","공원과유원지","기념물","낚시","다리와도로","레프팅과활동","문화체험장",
                "박물관과문화채널","사찰과건축물","산과계곡","생태관광","섬과바다","스키장","오토캠핑장","온천과동굴","자연공원","종합레포츠시설","촬영지","캠프장과수련시설"};

       if(curs[0]==true){
           first_ca="nature_data";
       } else if (curs[1]==true) {
           first_ca="leisure_data";
       } else if (curs[2]==true) {
           first_ca="culture_data";
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


        route_lat=new ArrayList<String>();
        route_lat_2=new ArrayList<String>();
        route_lat_3=new ArrayList<String>();
        route_lat_4=new ArrayList<String>();
        route_lat_5=new ArrayList<String>();


        route_long=new ArrayList<String>();
        route_long_2=new ArrayList<String>();
        route_long_3=new ArrayList<String>();
        route_long_4=new ArrayList<String>();
        route_long_5=new ArrayList<String>();



        mid_data=new ArrayList<String>();
        mid_lat=new ArrayList<String>();
        mid_long=new ArrayList<String>();

        is_Checked=new ArrayList<Boolean>();

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
                is_Checked.clear();
                db.collection(first_ca)
                        .whereEqualTo("category_name2",ed)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_data.add(document.get("data_title",String.class));
                                        route_lat.add(document.get("lattitude",String.class));
                                        route_long.add(document.get("logitude",String.class));
                                    }
                                    for(int i=0;i<route_data.size();i++){
                                        is_Checked.add(false);
                                    }
                                    RouteListAdapter=new List_Adapter_Route(Route_choose.this,route_data);
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
                is_Checked.clear();
                db.collection(first_ca)
                        .whereEqualTo("category_name2",rot_name[0])
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_data.add(document.get("data_title",String.class));
                                        route_lat.add(document.get("lattitude",String.class));
                                        route_long.add(document.get("logitude",String.class));
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
                route_data_2.clear();
                route_lat_2.clear();
                route_long_2.clear();
                db.collection(first_ca)
                        .whereEqualTo("category_name2",ed)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_data_2.add(document.get("data_title",String.class));
                                        route_lat_2.add(document.get("lattitude",String.class));
                                        route_long_2.add(document.get("logitude",String.class));
                                    }
                                    for(int i=0;i<route_data.size();i++){
                                        mid_data.add(route_data.get(i));
                                        mid_lat.add(route_lat.get(i));
                                        mid_long.add(route_long.get(i));
                                        is_Checked.add(false);
                                        if(i==5)
                                            break;
                                    }
                                    for(int i=0;i<route_data_2.size();i++){
                                        mid_data.add(route_data_2.get(i));
                                        mid_lat.add(route_lat_2.get(i));
                                        mid_long.add(route_long_2.get(i));
                                        is_Checked.add(false);
                                        if(i==5)
                                            break;
                                    }
                                    RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                is_Checked.clear();
                db.collection(first_ca)
                        .whereEqualTo("category_name2",rot_name[0])
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_data.add(document.get("data_title",String.class));
                                        route_lat.add(document.get("lattitude",String.class));
                                        route_long.add(document.get("logitude",String.class));
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
                route_data_2.clear();
                route_lat_2.clear();
                route_long_2.clear();
                db.collection(first_ca)
                        .whereEqualTo("category_name2",rot_name[1])
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_data_2.add(document.get("data_title",String.class));
                                        route_lat_2.add(document.get("lattitude",String.class));
                                        route_long_2.add(document.get("logitude",String.class));
                                    }

                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
                route_data_3.clear();
                route_lat_3.clear();
                route_long_3.clear();
                db.collection(first_ca)
                        .whereEqualTo("category_name2",ed)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_data_3.add(document.get("data_title",String.class));
                                        route_lat_3.add(document.get("lattitude",String.class));
                                        route_long_3.add(document.get("logitude",String.class));
                                    }
                                    for(int i=0;i<route_data.size();i++){
                                        mid_data.add(route_data.get(i));
                                        mid_lat.add(route_lat.get(i));
                                        mid_long.add(route_long.get(i));
                                        is_Checked.add(false);
                                        if(i==5)
                                            break;
                                    }
                                    for(int i=0;i<route_data_2.size();i++){
                                        mid_data.add(route_data_2.get(i));
                                        mid_lat.add(route_lat_2.get(i));
                                        mid_long.add(route_long_2.get(i));
                                        is_Checked.add(false);
                                        if(i==5)
                                            break;
                                    }
                                    for(int i=0;i<route_data_3.size();i++){
                                        mid_data.add(route_data_3.get(i));
                                        mid_lat.add(route_lat_3.get(i));
                                        mid_long.add(route_long_3.get(i));
                                        is_Checked.add(false);
                                        if(i==5)
                                            break;
                                    }
                                    RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                is_Checked.clear();
                db.collection(first_ca)
                        .whereEqualTo("category_name2",rot_name[0])
                        
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_data.add(document.get("data_title",String.class));
                                        route_lat.add(document.get("lattitude",String.class));
                                        route_long.add(document.get("logitude",String.class));
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
                route_data_2.clear();
                route_lat_2.clear();
                route_long_2.clear();
                db.collection(first_ca)
                        .whereEqualTo("category_name2",rot_name[1])
                        
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_data_2.add(document.get("data_title",String.class));
                                        route_lat_2.add(document.get("lattitude",String.class));
                                        route_long_2.add(document.get("logitude",String.class));
                                    }

                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
                route_data_3.clear();
                route_lat_3.clear();
                route_long_3.clear();
                db.collection(first_ca)
                        .whereEqualTo("category_name2",rot_name[2])
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_data_3.add(document.get("data_title",String.class));
                                        route_lat_3.add(document.get("lattitude",String.class));
                                        route_long_3.add(document.get("logitude",String.class));
                                    }

                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
                route_data_4.clear();
                route_lat_4.clear();
                route_long_4.clear();
                db.collection(first_ca)
                        .whereEqualTo("category_name2",ed)
                        
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_data_4.add(document.get("data_title",String.class));
                                        route_lat_4.add(document.get("lattitude",String.class));
                                        route_long_4.add(document.get("logitude",String.class));
                                    }
                                    for(int i=0;i<route_data.size();i++){
                                        mid_data.add(route_data.get(i));
                                        mid_lat.add(route_lat.get(i));
                                        mid_long.add(route_long.get(i));
                                        is_Checked.add(false);
                                        if(i==5)
                                            break;
                                    }
                                    for(int i=0;i<route_data_2.size();i++){
                                        mid_data.add(route_data_2.get(i));
                                        mid_lat.add(route_lat_2.get(i));
                                        mid_long.add(route_long_2.get(i));
                                        is_Checked.add(false);
                                        if(i==5)
                                            break;
                                    }
                                    for(int i=0;i<route_data_3.size();i++){
                                        mid_data.add(route_data_3.get(i));
                                        mid_lat.add(route_lat_3.get(i));
                                        mid_long.add(route_long_3.get(i));
                                        is_Checked.add(false);
                                        if(i==5)
                                            break;
                                    }
                                    for(int i=0;i<route_data_4.size();i++){
                                        mid_data.add(route_data_4.get(i));
                                        mid_lat.add(route_lat_4.get(i));
                                        mid_long.add(route_long_4.get(i));
                                        is_Checked.add(false);
                                        if(i==5)
                                            break;
                                    }
                                    RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                is_Checked.clear();
                db.collection(first_ca)
                        .whereEqualTo("category_name2",rot_name[0])
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_data.add(document.get("data_title",String.class));
                                        route_lat.add(document.get("lattitude",String.class));
                                        route_long.add(document.get("logitude",String.class));
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
                route_data_2.clear();
                route_lat_2.clear();
                route_long_2.clear();
                db.collection(first_ca)
                        .whereEqualTo("category_name2",rot_name[1])
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_data_2.add(document.get("data_title",String.class));
                                        route_lat_2.add(document.get("lattitude",String.class));
                                        route_long_2.add(document.get("logitude",String.class));
                                    }


                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
                route_data_3.clear();
                route_lat_3.clear();
                route_long_3.clear();
                db.collection(first_ca)
                        .whereEqualTo("category_name2",rot_name[2])
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_data_3.add(document.get("data_title",String.class));
                                        route_lat_3.add(document.get("lattitude",String.class));
                                        route_long_3.add(document.get("logitude",String.class));
                                    }

                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
                route_data_4.clear();
                route_lat_4.clear();
                route_long_4.clear();
                db.collection(first_ca)
                        .whereEqualTo("category_name2",rot_name[3])
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_data_4.add(document.get("data_title",String.class));
                                        route_lat_4.add(document.get("lattitude",String.class));
                                        route_long_4.add(document.get("logitude",String.class));
                                    }


                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
                route_data_5.clear();
                route_lat_5.clear();
                route_long_5.clear();
                db.collection(first_ca)
                        .whereEqualTo("category_name2",ed)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_data_5.add(document.get("data_title",String.class));
                                        route_lat_5.add(document.get("lattitude",String.class));
                                        route_long_5.add(document.get("logitude",String.class));
                                    }

                                    for(int i=0;i<route_data.size();i++){
                                        mid_data.add(route_data.get(i));
                                        mid_lat.add(route_lat.get(i));
                                        mid_long.add(route_long.get(i));
                                        is_Checked.add(false);
                                        if(i==5)
                                            break;
                                    }
                                    for(int i=0;i<route_data_2.size();i++){
                                        mid_data.add(route_data_2.get(i));
                                        mid_lat.add(route_lat_2.get(i));
                                        mid_long.add(route_long_2.get(i));
                                        is_Checked.add(false);
                                        if(i==5)
                                            break;
                                    }
                                    for(int i=0;i<route_data_3.size();i++){
                                        mid_data.add(route_data_3.get(i));
                                        mid_lat.add(route_lat_3.get(i));
                                        mid_long.add(route_long_3.get(i));
                                        is_Checked.add(false);
                                        if(i==5)
                                            break;
                                    }
                                    for(int i=0;i<route_data_4.size();i++){
                                        mid_data.add(route_data_4.get(i));
                                        mid_lat.add(route_lat_4.get(i));
                                        mid_long.add(route_long_4.get(i));
                                        is_Checked.add(false);
                                        if(i==5)
                                            break;
                                    }
                                    for(int i=0;i<route_data_5.size();i++){
                                        mid_data.add(route_data_5.get(i));
                                        mid_lat.add(route_lat_5.get(i));
                                        mid_long.add(route_long_5.get(i));
                                        is_Checked.add(false);
                                        if(i==5)
                                            break;
                                    }
                                    RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
                                    Route_List.setAdapter(RouteListAdapter);


                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
                break;

        }
        river_h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (rot.length-2){
                    case 0:
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)
                                .whereEqualTo("category_name1","강과호수")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }

                                            for(int i=0;i<route_data.size();i++){
                                                is_Checked.add(false);
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,route_data);
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
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])
                                .whereEqualTo("category_name1","강과호수")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)
                                .whereEqualTo("category_name1","강과호수")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }

                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])
                                .whereEqualTo("category_name1","강과호수")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])
                                .whereEqualTo("category_name1","강과호수")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)
                                .whereEqualTo("category_name1","강과호수")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }

                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])
                                .whereEqualTo("category_name1","강과호수")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])
                                .whereEqualTo("category_name1","강과호수")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])
                                .whereEqualTo("category_name1","강과호수")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)
                                .whereEqualTo("category_name1","강과호수")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])
                                .whereEqualTo("category_name1","강과호수")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])
                                .whereEqualTo("category_name1","강과호수")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])
                                .whereEqualTo("category_name1","강과호수")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[3])
                                .whereEqualTo("category_name1","강과호수")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_5.clear();
                        route_lat_5.clear();
                        route_long_5.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)
                                .whereEqualTo("category_name1","강과호수")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_5.add(document.get("data_title",String.class));
                                                route_lat_5.add(document.get("lattitude",String.class));
                                                route_long_5.add(document.get("logitude",String.class));
                                            }

                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_5.size();i++){
                                                mid_data.add(route_data_5.get(i));
                                                mid_lat.add(route_lat_5.get(i));
                                                mid_long.add(route_long_5.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
        golf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (rot.length-2){
                    case 0:
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)
                                .whereEqualTo("category_name1","골프")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }

                                            for(int i=0;i<route_data.size();i++){
                                                is_Checked.add(false);
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,route_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])
                                
                                .whereEqualTo("category_name1","골프")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)
                                
                                .whereEqualTo("category_name1","골프")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])
                                
                                .whereEqualTo("category_name1","골프")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])
                                
                                .whereEqualTo("category_name1","골프")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)
                                
                                .whereEqualTo("category_name1","골프")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])
                                
                                .whereEqualTo("category_name1","골프")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])
                                
                                .whereEqualTo("category_name1","골프")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])
                                
                                .whereEqualTo("category_name1","골프")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)
                                
                                .whereEqualTo("category_name1","골프")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])
                                
                                .whereEqualTo("category_name1","골프")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])
                                
                                .whereEqualTo("category_name1","골프")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])
                                
                                .whereEqualTo("category_name1","골프")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[3])
                                
                                .whereEqualTo("category_name1","골프")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_5.clear();
                        route_lat_5.clear();
                        route_long_5.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)
                                
                                .whereEqualTo("category_name1","골프")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_5.add(document.get("data_title",String.class));
                                                route_lat_5.add(document.get("lattitude",String.class));
                                                route_long_5.add(document.get("logitude",String.class));
                                            }

                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_5.size();i++){
                                                mid_data.add(route_data_5.get(i));
                                                mid_lat.add(route_lat_5.get(i));
                                                mid_long.add(route_long_5.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
        park.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (rot.length-2){
                    case 0:
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)
                                
                                .whereEqualTo("category_name1","공원과유원지")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                is_Checked.add(false);
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,route_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])
                                
                                .whereEqualTo("category_name1","공원과유원지")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)
                                
                                .whereEqualTo("category_name1","공원과유원지")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])
                                
                                .whereEqualTo("category_name1","공원과유원지")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])
                                
                                .whereEqualTo("category_name1","공원과유원지")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)
                                
                                .whereEqualTo("category_name1","공원과유원지")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])
                                
                                .whereEqualTo("category_name1","공원과유원지")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])
                                
                                .whereEqualTo("category_name1","공원과유원지")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])
                                
                                .whereEqualTo("category_name1","공원과유원지")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)
                                
                                .whereEqualTo("category_name1","공원과유원지")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
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
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])
                                
                                .whereEqualTo("category_name1","공원과유원지")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])
                                
                                .whereEqualTo("category_name1","공원과유원지")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])
                                
                                .whereEqualTo("category_name1","공원과유원지")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[3])
                                
                                .whereEqualTo("category_name1","공원과유원지")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_5.clear();
                        route_lat_5.clear();
                        route_long_5.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)
                                
                                .whereEqualTo("category_name1","공원과유원지")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_5.add(document.get("data_title",String.class));
                                                route_lat_5.add(document.get("lattitude",String.class));
                                                route_long_5.add(document.get("logitude",String.class));
                                            }

                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_5.size();i++){
                                                mid_data.add(route_data_5.get(i));
                                                mid_lat.add(route_lat_5.get(i));
                                                mid_long.add(route_long_5.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
        cele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (rot.length-2){
                    case 0:
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)
                                
                                .whereEqualTo("category_name1","기념물")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                is_Checked.add(false);
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,route_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])
                                
                                .whereEqualTo("category_name1","기념물")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)
                                
                                .whereEqualTo("category_name1","기념물")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])
                                
                                .whereEqualTo("category_name1","기념물")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])
                                
                                .whereEqualTo("category_name1","기념물")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)
                                
                                .whereEqualTo("category_name1","기념물")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])
                                
                                .whereEqualTo("category_name1","기념물")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])
                                
                                .whereEqualTo("category_name1","기념물")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])
                                
                                .whereEqualTo("category_name1","기념물")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)
                                
                                .whereEqualTo("category_name1","기념물")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])
                                
                                .whereEqualTo("category_name1","기념물")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])
                                
                                .whereEqualTo("category_name1","기념물")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])
                                
                                .whereEqualTo("category_name1","기념물")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[3])
                                
                                .whereEqualTo("category_name1","기념물")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_5.clear();
                        route_lat_5.clear();
                        route_long_5.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)
                                
                                .whereEqualTo("category_name1","기념물")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_5.add(document.get("data_title",String.class));
                                                route_lat_5.add(document.get("lattitude",String.class));
                                                route_long_5.add(document.get("logitude",String.class));
                                            }

                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_5.size();i++){
                                                mid_data.add(route_data_5.get(i));
                                                mid_lat.add(route_lat_5.get(i));
                                                mid_long.add(route_long_5.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
        nak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (rot.length-2){
                    case 0:
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)
                                
                                .whereEqualTo("category_name1","낚시")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                is_Checked.add(false);
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,route_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])
                                
                                .whereEqualTo("category_name1","낚시")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)
                                
                                .whereEqualTo("category_name1","낚시")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])
                                
                                .whereEqualTo("category_name1","낚시")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])
                                
                                .whereEqualTo("category_name1","낚시")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)
                                
                                .whereEqualTo("category_name1","낚시")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])
                                
                                .whereEqualTo("category_name1","낚시")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])
                                
                                .whereEqualTo("category_name1","낚시")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])
                                
                                .whereEqualTo("category_name1","낚시")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)
                                
                                .whereEqualTo("category_name1","낚시")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])
                                
                                .whereEqualTo("category_name1","낚시")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])
                                
                                .whereEqualTo("category_name1","낚시")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])
                                
                                .whereEqualTo("category_name1","낚시")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[3])
                                
                                .whereEqualTo("category_name1","낚시")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_5.clear();
                        route_lat_5.clear();
                        route_long_5.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)
                                
                                .whereEqualTo("category_name1","낚시")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_5.add(document.get("data_title",String.class));
                                                route_lat_5.add(document.get("lattitude",String.class));
                                                route_long_5.add(document.get("logitude",String.class));
                                            }

                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_5.size();i++){
                                                mid_data.add(route_data_5.get(i));
                                                mid_lat.add(route_lat_5.get(i));
                                                mid_long.add(route_long_5.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
        road.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (rot.length-2){
                    case 0:
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        is_Checked.clear();
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)
                                
                                .whereEqualTo("category_name1","다리와도로")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                is_Checked.add(false);
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,route_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])
                                
                                .whereEqualTo("category_name1","다리와도로")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)
                                
                                .whereEqualTo("category_name1","다리와도로")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])
                                
                                .whereEqualTo("category_name1","다리와도로")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])
                                
                                .whereEqualTo("category_name1","다리와도로")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)
                                
                                .whereEqualTo("category_name1","다리와도로")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])
                                
                                .whereEqualTo("category_name1","다리와도로")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])
                                
                                .whereEqualTo("category_name1","다리와도로")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])
                                
                                .whereEqualTo("category_name1","다리와도로")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)
                                
                                .whereEqualTo("category_name1","다리와도로")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++) {
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if (i == 5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])
                                
                                .whereEqualTo("category_name1","다리와도로")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])
                                
                                .whereEqualTo("category_name1","다리와도로")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])
                                
                                .whereEqualTo("category_name1","다리와도로")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[3])
                                
                                .whereEqualTo("category_name1","다리와도로")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_5.clear();
                        route_lat_5.clear();
                        route_long_5.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)
                                
                                .whereEqualTo("category_name1","다리와도로")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_5.add(document.get("data_title",String.class));
                                                route_lat_5.add(document.get("lattitude",String.class));
                                                route_long_5.add(document.get("logitude",String.class));
                                            }

                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_5.size();i++){
                                                mid_data.add(route_data_5.get(i));
                                                mid_lat.add(route_lat_5.get(i));
                                                mid_long.add(route_long_5.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
        activiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (rot.length-2){
                    case 0:
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        is_Checked.clear();
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","레프팅과활동")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                is_Checked.add(false);
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,route_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","레프팅과활동")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","레프팅과활동")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","레프팅과활동")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","레프팅과활동")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","레프팅과활동")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","레프팅과활동")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","레프팅과활동")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])

                                .whereEqualTo("category_name1","레프팅과활동")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","레프팅과활동")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++) {
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if (i == 5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","레프팅과활동")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","레프팅과활동")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])

                                .whereEqualTo("category_name1","레프팅과활동")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[3])

                                .whereEqualTo("category_name1","레프팅과활동")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_5.clear();
                        route_lat_5.clear();
                        route_long_5.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","레프팅과활동")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_5.add(document.get("data_title",String.class));
                                                route_lat_5.add(document.get("lattitude",String.class));
                                                route_long_5.add(document.get("logitude",String.class));
                                            }

                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_5.size();i++){
                                                mid_data.add(route_data_5.get(i));
                                                mid_lat.add(route_lat_5.get(i));
                                                mid_long.add(route_long_5.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
        culture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (rot.length-2){
                    case 0:
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        is_Checked.clear();
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","문화체험장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                is_Checked.add(false);
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,route_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","문화체험장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","문화체험장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","문화체험장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","문화체험장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","문화체험장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","문화체험장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","문화체험장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])

                                .whereEqualTo("category_name1","문화체험장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","문화체험장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++) {
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if (i == 5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","문화체험장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","문화체험장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])

                                .whereEqualTo("category_name1","문화체험장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[3])

                                .whereEqualTo("category_name1","문화체험장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_5.clear();
                        route_lat_5.clear();
                        route_long_5.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","문화체험장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_5.add(document.get("data_title",String.class));
                                                route_lat_5.add(document.get("lattitude",String.class));
                                                route_long_5.add(document.get("logitude",String.class));
                                            }

                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_5.size();i++){
                                                mid_data.add(route_data_5.get(i));
                                                mid_lat.add(route_lat_5.get(i));
                                                mid_long.add(route_long_5.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
        museum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (rot.length-2){
                    case 0:
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        is_Checked.clear();
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","박물관과문화채널")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                is_Checked.add(false);
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,route_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","박물관과문화채널")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","박물관과문화채널")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","박물관과문화채널")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","박물관과문화채널")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","박물관과문화채널")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","박물관과문화채널")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","박물관과문화채널")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])

                                .whereEqualTo("category_name1","박물관과문화채널")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","박물관과문화채널")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++) {
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if (i == 5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","박물관과문화채널")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","박물관과문화채널")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])

                                .whereEqualTo("category_name1","박물관과문화채널")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[3])

                                .whereEqualTo("category_name1","박물관과문화채널")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_5.clear();
                        route_lat_5.clear();
                        route_long_5.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","박물관과문화채널")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_5.add(document.get("data_title",String.class));
                                                route_lat_5.add(document.get("lattitude",String.class));
                                                route_long_5.add(document.get("logitude",String.class));
                                            }

                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_5.size();i++){
                                                mid_data.add(route_data_5.get(i));
                                                mid_lat.add(route_lat_5.get(i));
                                                mid_long.add(route_long_5.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
        temple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (rot.length-2){
                    case 0:
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        is_Checked.clear();
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","사찰과건축물")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                is_Checked.add(false);
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,route_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","사찰과건축물")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","사찰과건축물")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","사찰과건축물")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","사찰과건축물")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","사찰과건축물")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","사찰과건축물")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","사찰과건축물")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])

                                .whereEqualTo("category_name1","사찰과건축물")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","사찰과건축물")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++) {
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if (i == 5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","사찰과건축물")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","사찰과건축물")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])

                                .whereEqualTo("category_name1","사찰과건축물")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[3])

                                .whereEqualTo("category_name1","사찰과건축물")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_5.clear();
                        route_lat_5.clear();
                        route_long_5.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","사찰과건축물")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_5.add(document.get("data_title",String.class));
                                                route_lat_5.add(document.get("lattitude",String.class));
                                                route_long_5.add(document.get("logitude",String.class));
                                            }

                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_5.size();i++){
                                                mid_data.add(route_data_5.get(i));
                                                mid_lat.add(route_lat_5.get(i));
                                                mid_long.add(route_long_5.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","산과계곡")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                is_Checked.add(false);
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,route_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","산과계곡")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","산과계곡")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","산과계곡")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","산과계곡")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","산과계곡")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","산과계곡")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","산과계곡")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])

                                .whereEqualTo("category_name1","산과계곡")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","산과계곡")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++) {
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if (i == 5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","산과계곡")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","산과계곡")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])

                                .whereEqualTo("category_name1","산과계곡")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[3])

                                .whereEqualTo("category_name1","산과계곡")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_5.clear();
                        route_lat_5.clear();
                        route_long_5.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","산과계곡")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_5.add(document.get("data_title",String.class));
                                                route_lat_5.add(document.get("lattitude",String.class));
                                                route_long_5.add(document.get("logitude",String.class));
                                            }

                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_5.size();i++){
                                                mid_data.add(route_data_5.get(i));
                                                mid_lat.add(route_lat_5.get(i));
                                                mid_long.add(route_long_5.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
        nature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (rot.length-2){
                    case 0:
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        is_Checked.clear();
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","생태관광")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                is_Checked.add(false);
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,route_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","생태관광")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","생태관광")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","생태관광")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","생태관광")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","생태관광")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","생태관광")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","생태관광")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])

                                .whereEqualTo("category_name1","생태관광")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","생태관광")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++) {
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if (i == 5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","생태관광")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","생태관광")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])

                                .whereEqualTo("category_name1","생태관광")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[3])

                                .whereEqualTo("category_name1","생태관광")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_5.clear();
                        route_lat_5.clear();
                        route_long_5.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","생태관광")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_5.add(document.get("data_title",String.class));
                                                route_lat_5.add(document.get("lattitude",String.class));
                                                route_long_5.add(document.get("logitude",String.class));
                                            }

                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_5.size();i++){
                                                mid_data.add(route_data_5.get(i));
                                                mid_lat.add(route_lat_5.get(i));
                                                mid_long.add(route_long_5.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
        island.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (rot.length-2){
                    case 0:
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        is_Checked.clear();
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","섬과바다")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                is_Checked.add(false);
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,route_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","섬과바다")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","섬과바다")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","섬과바다")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","섬과바다")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","섬과바다")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","섬과바다")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","섬과바다")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])

                                .whereEqualTo("category_name1","섬과바다")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","섬과바다")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++) {
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if (i == 5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","섬과바다")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","섬과바다")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])

                                .whereEqualTo("category_name1","섬과바다")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[3])

                                .whereEqualTo("category_name1","섬과바다")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_5.clear();
                        route_lat_5.clear();
                        route_long_5.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","섬과바다")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_5.add(document.get("data_title",String.class));
                                                route_lat_5.add(document.get("lattitude",String.class));
                                                route_long_5.add(document.get("logitude",String.class));
                                            }

                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_5.size();i++){
                                                mid_data.add(route_data_5.get(i));
                                                mid_lat.add(route_lat_5.get(i));
                                                mid_long.add(route_long_5.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
        ski.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (rot.length-2){
                    case 0:
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        is_Checked.clear();
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","스키장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                is_Checked.add(false);
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,route_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","스키장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","스키장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","스키장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","스키장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","스키장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","스키장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","스키장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])

                                .whereEqualTo("category_name1","스키장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","스키장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++) {
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if (i == 5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","스키장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","스키장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])

                                .whereEqualTo("category_name1","스키장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[3])

                                .whereEqualTo("category_name1","스키장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_5.clear();
                        route_lat_5.clear();
                        route_long_5.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","스키장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_5.add(document.get("data_title",String.class));
                                                route_lat_5.add(document.get("lattitude",String.class));
                                                route_long_5.add(document.get("logitude",String.class));
                                            }

                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_5.size();i++){
                                                mid_data.add(route_data_5.get(i));
                                                mid_lat.add(route_lat_5.get(i));
                                                mid_long.add(route_long_5.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
        camp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (rot.length-2){
                    case 0:
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        is_Checked.clear();
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","오토캠핑장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                is_Checked.add(false);
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,route_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","오토캠핑장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","오토캠핑장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","오토캠핑장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","오토캠핑장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","오토캠핑장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","오토캠핑장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","오토캠핑장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])

                                .whereEqualTo("category_name1","오토캠핑장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","오토캠핑장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++) {
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if (i == 5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","오토캠핑장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","오토캠핑장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])

                                .whereEqualTo("category_name1","오토캠핑장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[3])

                                .whereEqualTo("category_name1","오토캠핑장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_5.clear();
                        route_lat_5.clear();
                        route_long_5.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","오토캠핑장")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_5.add(document.get("data_title",String.class));
                                                route_lat_5.add(document.get("lattitude",String.class));
                                                route_long_5.add(document.get("logitude",String.class));
                                            }

                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_5.size();i++){
                                                mid_data.add(route_data_5.get(i));
                                                mid_lat.add(route_lat_5.get(i));
                                                mid_long.add(route_long_5.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
        cave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (rot.length-2){
                    case 0:
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        is_Checked.clear();
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)
                                .whereEqualTo("category_name1","온천과동굴")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                is_Checked.add(false);
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,route_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])
                                .whereEqualTo("category_name1","온천과동굴")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)
                                .whereEqualTo("category_name1","온천과동굴")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","온천과동굴")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","온천과동굴")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","온천과동굴")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","온천과동굴")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","온천과동굴")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])

                                .whereEqualTo("category_name1","온천과동굴")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","온천과동굴")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++) {
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if (i == 5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","온천과동굴")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","온천과동굴")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])

                                .whereEqualTo("category_name1","온천과동굴")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[3])

                                .whereEqualTo("category_name1","온천과동굴")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_5.clear();
                        route_lat_5.clear();
                        route_long_5.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","온천과동굴")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_5.add(document.get("data_title",String.class));
                                                route_lat_5.add(document.get("lattitude",String.class));
                                                route_long_5.add(document.get("logitude",String.class));
                                            }

                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_5.size();i++){
                                                mid_data.add(route_data_5.get(i));
                                                mid_lat.add(route_lat_5.get(i));
                                                mid_long.add(route_long_5.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
        na_park.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (rot.length-2){
                    case 0:
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        is_Checked.clear();
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","자연공원")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                is_Checked.add(false);
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,route_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","자연공원")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","자연공원")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","자연공원")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","자연공원")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","자연공원")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","자연공원")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","자연공원")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])

                                .whereEqualTo("category_name1","자연공원")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","자연공원")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++) {
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if (i == 5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","자연공원")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","자연공원")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])

                                .whereEqualTo("category_name1","자연공원")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[3])

                                .whereEqualTo("category_name1","자연공원")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_5.clear();
                        route_lat_5.clear();
                        route_long_5.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","자연공원")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_5.add(document.get("data_title",String.class));
                                                route_lat_5.add(document.get("lattitude",String.class));
                                                route_long_5.add(document.get("logitude",String.class));
                                            }

                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_5.size();i++){
                                                mid_data.add(route_data_5.get(i));
                                                mid_lat.add(route_lat_5.get(i));
                                                mid_long.add(route_long_5.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (rot.length-2){
                    case 0:
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        is_Checked.clear();
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","종합레포츠시설")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                is_Checked.add(false);
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,route_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","종합레포츠시설")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","종합레포츠시설")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","종합레포츠시설")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","종합레포츠시설")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","종합레포츠시설")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","종합레포츠시설")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","종합레포츠시설")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])

                                .whereEqualTo("category_name1","종합레포츠시설")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","종합레포츠시설")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++) {
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if (i == 5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","종합레포츠시설")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","종합레포츠시설")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])

                                .whereEqualTo("category_name1","종합레포츠시설")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[3])

                                .whereEqualTo("category_name1","종합레포츠시설")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_5.clear();
                        route_lat_5.clear();
                        route_long_5.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","종합레포츠시설")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_5.add(document.get("data_title",String.class));
                                                route_lat_5.add(document.get("lattitude",String.class));
                                                route_long_5.add(document.get("logitude",String.class));
                                            }

                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_5.size();i++){
                                                mid_data.add(route_data_5.get(i));
                                                mid_lat.add(route_lat_5.get(i));
                                                mid_long.add(route_long_5.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (rot.length-2){
                    case 0:
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        is_Checked.clear();
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","촬영지")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                is_Checked.add(false);
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,route_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","촬영지")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","촬영지")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","촬영지")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","촬영지")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","촬영지")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","촬영지")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","촬영지")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])

                                .whereEqualTo("category_name1","촬영지")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","촬영지")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++) {
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if (i == 5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","촬영지")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","촬영지")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])

                                .whereEqualTo("category_name1","촬영지")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[3])

                                .whereEqualTo("category_name1","촬영지")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_5.clear();
                        route_lat_5.clear();
                        route_long_5.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","촬영지")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_5.add(document.get("data_title",String.class));
                                                route_lat_5.add(document.get("lattitude",String.class));
                                                route_long_5.add(document.get("logitude",String.class));
                                            }

                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_5.size();i++){
                                                mid_data.add(route_data_5.get(i));
                                                mid_lat.add(route_lat_5.get(i));
                                                mid_long.add(route_long_5.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
        camp_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (rot.length-2){
                    case 0:
                        route_data.clear();
                        route_lat.clear();
                        route_long.clear();
                        is_Checked.clear();
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","캠프장과수련시설")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                is_Checked.add(false);
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,route_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","캠프장과수련시설")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","캠프장과수련시설")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","캠프장과수련시설")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","캠프장과수련시설")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","캠프장과수련시설")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","캠프장과수련시설")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","캠프장과수련시설")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])

                                .whereEqualTo("category_name1","캠프장과수련시설")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","캠프장과수련시설")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }
                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++) {
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if (i == 5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
                        is_Checked.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[0])

                                .whereEqualTo("category_name1","캠프장과수련시설")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data.add(document.get("data_title",String.class));
                                                route_lat.add(document.get("lattitude",String.class));
                                                route_long.add(document.get("logitude",String.class));
                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_2.clear();
                        route_lat_2.clear();
                        route_long_2.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[1])

                                .whereEqualTo("category_name1","캠프장과수련시설")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_2.add(document.get("data_title",String.class));
                                                route_lat_2.add(document.get("lattitude",String.class));
                                                route_long_2.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_3.clear();
                        route_lat_3.clear();
                        route_long_3.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[2])

                                .whereEqualTo("category_name1","캠프장과수련시설")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_3.add(document.get("data_title",String.class));
                                                route_lat_3.add(document.get("lattitude",String.class));
                                                route_long_3.add(document.get("logitude",String.class));
                                            }

                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_4.clear();
                        route_lat_4.clear();
                        route_long_4.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",rot_name[3])

                                .whereEqualTo("category_name1","캠프장과수련시설")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_4.add(document.get("data_title",String.class));
                                                route_lat_4.add(document.get("lattitude",String.class));
                                                route_long_4.add(document.get("logitude",String.class));
                                            }


                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                        route_data_5.clear();
                        route_lat_5.clear();
                        route_long_5.clear();
                        db.collection(first_ca)
                                .whereEqualTo("category_name2",ed)

                                .whereEqualTo("category_name1","캠프장과수련시설")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                route_data_5.add(document.get("data_title",String.class));
                                                route_lat_5.add(document.get("lattitude",String.class));
                                                route_long_5.add(document.get("logitude",String.class));
                                            }

                                            for(int i=0;i<route_data.size();i++){
                                                mid_data.add(route_data.get(i));
                                                mid_lat.add(route_lat.get(i));
                                                mid_long.add(route_long.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_2.size();i++){
                                                mid_data.add(route_data_2.get(i));
                                                mid_lat.add(route_lat_2.get(i));
                                                mid_long.add(route_long_2.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_3.size();i++){
                                                mid_data.add(route_data_3.get(i));
                                                mid_lat.add(route_lat_3.get(i));
                                                mid_long.add(route_long_3.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_4.size();i++){
                                                mid_data.add(route_data_4.get(i));
                                                mid_lat.add(route_lat_4.get(i));
                                                mid_long.add(route_long_4.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            for(int i=0;i<route_data_5.size();i++){
                                                mid_data.add(route_data_5.get(i));
                                                mid_lat.add(route_lat_5.get(i));
                                                mid_long.add(route_long_5.get(i));
                                                is_Checked.add(false);
                                                if(i==5)
                                                    break;
                                            }
                                            RouteListAdapter=new List_Adapter_Route(Route_choose.this,mid_data);
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
        

        Route_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                TextView text_title=view.findViewById(R.id.title);
                TextView route_choice=view.findViewById(R.id.route_choice);

                if(is_Checked.get(position)==false){
                    is_Checked.set(position,true);
                    route_choice.setBackgroundColor(Color.parseColor("#27005D"));
                    String item=text_title.getText().toString();
                    result_name.add(item);
                    if((rot.length-2)>0){
                        int item_n=mid_data.indexOf(item);
                        double d1=Double.parseDouble(mid_lat.get(item_n));
                        result_lat.add(d1);
                        double d2=Double.parseDouble(mid_long.get(item_n));
                        result_long.add(d2);
                    }else {
                        int item_n=route_data.indexOf(item);
                        double d1=Double.parseDouble(route_lat.get(item_n));
                        result_lat.add(d1);
                        double d2=Double.parseDouble(route_long.get(item_n));
                        result_long.add(d2);
                    }

                    route_text.append(" "+item);
                }
                else{
                    is_Checked.set(position,false);
                    route_choice.setBackgroundColor(Color.parseColor("#AED2FF"));
                    String item=text_title.getText().toString();
                    result_name.remove(item);
                    String remove_item=route_text.getText().toString();
                    remove_item=remove_item.replaceAll(item,"");
                    route_text.setText(remove_item);
                    if((rot.length-2)>0){
                        int item_n=mid_data.indexOf(item);
                        result_lat.remove(mid_lat.get(item_n));
                        result_long.remove(mid_long.get(item_n));
                    }else {
                        int item_n=route_data.indexOf(item);
                        result_lat.remove(route_lat.get(item_n));
                        result_long.remove(route_long.get(item_n));
                    }
                }

            }
        });



        mapFind=findViewById(R.id.mapFind);
        mapFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mid=route_text.getText().toString();
                String[] m=mid.split(" ");
                if(result_lat.size()>0){                 //선택된 경유지가 있음
                    rot_n=result_lat.size()+2;
                }else{
                    rot_n=2;                    //선택된 경유지 없음
                }
                if(result_lat.size()>5){
                    Toast.makeText(getApplicationContext(),"경유지추가는 최대 5개까지 입니다.",Toast.LENGTH_LONG).show();
                }
                else {
                    if(result_lat.size()==0){
                        Intent intentTo = new Intent(getApplicationContext(), Map_Basic.class);
                        intentTo.putExtra("Start", st);
                        intentTo.putExtra("num",rot_n);
                        intentTo.putExtra("End", ed);
                        startActivity(intentTo);
                    }else {
                        Intent intentTo = new Intent(getApplicationContext(), Map_Basic.class);
                        intentTo.putExtra("Start", st);
                        String[] res_name=result_name.toArray(new String[result_name.size()]);
                        //소문자 더블로 넘겨야함
                        Double res_lat[]=result_lat.toArray(new Double[result_lat.size()]);
                        double res_lat_d[]=new double[res_lat.length];
                        for(int i=0;i<res_lat.length;i++){
                            res_lat_d[i]=res_lat[i];
                        }
                        Double res_long[]=result_long.toArray(new Double[result_long.size()]);
                        double res_long_d[]=new double[res_long.length];
                        for(int i=0;i<res_long.length;i++){
                            res_long_d[i]=res_long[i];
                        }
                        intentTo.putExtra("rot_lat", res_lat_d);
                        intentTo.putExtra("num",rot_n);
                        intentTo.putExtra("rot_name",res_name);
                        intentTo.putExtra("rot_long", res_long_d);
                        intentTo.putExtra("End", ed);
                        startActivity(intentTo);
                    }

                }
                //dj.algorithm('출발지', '도착지');
            }
        });
    }
}