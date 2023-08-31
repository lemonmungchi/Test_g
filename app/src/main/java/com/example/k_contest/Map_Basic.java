package com.example.k_contest;

import static android.content.ContentValues.TAG;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.naver.maps.map.util.FusedLocationSource;


import java.util.ArrayList;
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

    private ArrayList<Double> Data_LA = new ArrayList<>();;       //위도

    private ArrayList<Double> Data_Lo = new ArrayList<>();         //경도

    private ArrayList<String> Data_NM = new ArrayList<>();          //식당이름


    private ArrayList<String> Data_Con = new ArrayList<>();         //식당소개글
    private Button ex_retro;
    private  Gson gson = new GsonBuilder().setLenient().create();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_basic);

        mapView = findViewById(R.id.map_basic);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);

        locationSource =
                new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

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


        this.naverMap=naverMap;
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

        ex_retro=findViewById(R.id.ex_retro);


        ex_retro.setOnClickListener(new View.OnClickListener() {
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
