package com.example.k_contest;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.PathOverlay;
import com.naver.maps.map.util.FusedLocationSource;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Map_Basic extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;

    private String NavaApIKey="nowdd7jigt";

    private String secret="C7toUlRTfdqJlYR8tsX6fGj2WtVIFIEySUFTO72L";

    private ArrayList<Double> Data_LA = new ArrayList<>();;       //위도

    private ArrayList<Double> Data_Lo = new ArrayList<>();         //경도


    private Button ex_retro;

    private Button ex_retro2;
    private  Gson gson = new GsonBuilder().setLenient().create();

    private  FirebaseFirestore db = FirebaseFirestore.getInstance();

    private double[][] region_position = {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_basic);

        mapView = findViewById(R.id.map_basic);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);






    }
    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {                                        //맵객체 매서드 사용시 여기서 작성

        Intent intent = getIntent();
        String st = intent.getStringExtra("start");          //출발지 받아오기
        String ed = intent.getStringExtra("end");             //목적지 받아오기

        /* todo - 출발 목적지 받아서 다익스트라에 돌린다음 restapi에 넣고 각 점을 받아오고
        *   그 점을 마커로 찍고 각 점 사이를 PathOverlay path = new PathOverlay(); 객체 배열로
        * 이어주기 */

        // 다익스트라 함수 Dijkstra dj=new Dijkstra(18, newGN);




        this.naverMap=naverMap;
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

        ex_retro2=findViewById(R.id.ex_retro2);

        /*
        ex_retro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                db.collection("data1")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
            }
        });
        */
        /*ex_retro.setOnClickListener(new View.OnClickListener() {                      식당불러오기코드
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://gyeongnam.openapi.redtable.global/")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                DataAddress dataAddress = retrofit.create(DataAddress.class);



                Call<DataPath> call4 = dataAddress.getData("aE5iTKPfr1Msn7QXu8LmeK1SuDfo36insow1VLonAp3hb0VbTMjYr08mS8h1Q42h",4);

                call4.enqueue(new Callback<DataPath>() {
                    @Override
                    public void onResponse(Call<DataPath> call, Response<DataPath> response) {

                        if(response.isSuccessful()){
                            DataPath data = response.body();
                            for(int i=0;i<data.getBody().size();i++){
                                Data_LA.add(data.getBody().get(i).getRSTR_LA());            //1페이지 1000개 위도
                                Data_Lo.add(data.getBody().get(i).getRSTR_LO());            //1페이지 1000개 경도
                                 Data_NM.add(data.getBody().get(i).getRSTR_NM());
                                Data_Con.add(data.getBody().get(i).getRSTR_INTRCN_CONT());
                            }
                            int i=Data_LA.size();
                            Marker[] m=new Marker[data.getBody().size()];
                            InfoWindow[] I= new InfoWindow[data.getBody().size()];
                            float count=0;        //주변식당수 체크
                            float re_LA=0;      //식당들 위도
                            float re_Lo=0;      //식당들 경도
                            for(int a=0;a<m.length;a++){
                                m[a]=new Marker();
                                m[a].setPosition(new LatLng(Data_LA.get(a),Data_Lo.get(a)));
                                m[a].setCaptionText(Data_NM.get(a));
                                m[a].setMap(naverMap);
                                
                                Location d=locationSource.getLastLocation();
                                double my_LA=d.getLatitude();
                                double my_Lo=d.getLongitude();
                                if(distance(my_LA,my_Lo,Data_LA.get(a),Data_Lo.get(a))<3) {
                                    m[a].setVisible(true);
                                    count++;
                                    re_LA += Data_LA.get(a);
                                    re_Lo += Data_Lo.get(a);
                                }
                                else
                                    m[a].setVisible(false);             //만약에 주면에 없다면
                                CameraPosition cameraPosition= new CameraPosition(new LatLng(re_LA/count,re_Lo/count),12);
                                naverMap.setCameraPosition(cameraPosition);
                            }

                        }else {
                            Log.d(TAG,"실패");
                        }
                    }
                    @Override
                    public void onFailure(Call<DataPath> call, Throwable t) {
                        Log.d(TAG,"실패"+t.getMessage());
                    }


                });
            }
        });*/

        ex_retro2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://naveropenapi.apigw.ntruss.com/map-direction/")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                RouteFind routeFind = retrofit.create(RouteFind.class);

                Location d=locationSource.getLastLocation();

                Call<RoutePath> call = routeFind.getData(NavaApIKey,secret,"127.1058342,37.359708","129.075986,35.179470");        //네이버 길찾기 rest api 시작 출발점 찍으면 됨

                call.enqueue(new Callback<RoutePath>() {
                    @Override
                    public void onResponse(Call<RoutePath> call, Response<RoutePath> response) {
                        if(response.isSuccessful()) {
                            RoutePath routePath=response.body();
                            List<List<Double>> path=routePath.getRoute().getTraoptimal().get(0).getPath();
                            Marker[] marker= new Marker[path.size()];
                            PathOverlay[] line_path=new PathOverlay[path.size()-1];       //길 선 표시할 path 배열
                            List<LatLng> list;
                            for(int i=0;i<path.size();i++) {
                                list.add(new LatLng(path.get(i).get(0),path.get(i).get(1)));
                                    for(int a=0;a<marker.length;a++){
                                        marker[a]=new Marker();
                                        marker[a].setPosition(new LatLng(path.get(i).get(0),path.get(i).get(1)));
                                        marker[a].setMap(naverMap);
                                        line_path[a].setCoords(path.get(i).get(0),path.get(i).get(1)));
                                    }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RoutePath> call, Throwable t) {
                        Log.d(TAG,"실패");
                    }
                });
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,  @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) { // 권한 거부됨
                naverMap.setLocationTrackingMode(LocationTrackingMode.None);
            }
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);                //위치권환가져오는 코드
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2) {

        double theta;
        if(lon1>lon2){
            theta = lon1 - lon2;
        } else{
            theta=lon2-lat1;
        }

        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        dist = dist * 1.609344;


        return (dist);
    }

    //10진수를 radian(라디안)으로 변환
    private static double deg2rad(double deg){
        return (deg * Math.PI/180.0);
    }
    //radian(라디안)을 10진수로 변환
    private static double rad2deg(double rad){
        return (rad * 180 / Math.PI);
    }



}
