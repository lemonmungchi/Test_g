package com.example.k_contest;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.PathOverlay;
import com.naver.maps.map.util.FusedLocationSource;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
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

    private ListView List;

    private ArrayList<String> dataSample;

    private String NavaApIKey=BuildConfig.NAVER_KEY;

    private String secret=BuildConfig.SECRET_KEY;

    // 가중치 곱 함수
    public void multiplyMatix(int[][] newGN, double[][] map) {
        for(int i = 0; i < 18; i++){
            for(int j = 0; j < 18; j++) {
                newGN[i][j] =(int) (newGN[i][j] * map[i][j]);
            }
        }
    }

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

    List<LatLng> list=new ArrayList<>();

    private  Gson gson = new GsonBuilder().setLenient().create();


    private String[] vertex = {"창원시", "진주시", "통영시", "사천시", "김해시","밀양시", "거제시", "양산시", "의령군",
            "함양군", "창녕군", "고성군", "남해군", "하동군", "산청군", "함안군", "거창군", "합천군"};
    public int stringToInt(String s) {              // String to Int
        int x = 0;
        for (int i = 0; i < vertex.length; i++) {
            if (vertex[i].equals(s)) x = i;
        }
        return x;
    }

;

    
    //코드수정

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
    private double[] result_lat;
    private double[] result_long;
    private String[] result_name;

    public int stringToInt_s(String s) {              // String to Int
        int x = 0;
        for (int i = 0; i < vertex.length; i++) {
            if (result_name[i].equals(s)) x = i;
        }
        return x;
    }
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {                                        //맵객체 매서드 사용시 여기서 작성


        Intent intent = getIntent();
        String st = intent.getStringExtra("Start");          //출발지 받아오기
        int num=intent.getIntExtra("num",0);
        result_name=new String[num];
        result_lat=new double[num];
        result_long=new double[num];
        if(num>2) {
            result_name=intent.getExtras().getStringArray("rot_name");
            result_lat=intent.getExtras().getDoubleArray("rot_lat");
            result_long=intent.getExtras().getDoubleArray("rot_long");
        }


        String ed = intent.getStringExtra("End");             //목적지 받아오기


        Marker[] markers= new Marker[num];
        double start_lot= region_position[stringToInt(st)][0];           //출발지 위도경도
        double start_lng= region_position[stringToInt(st)][1];

        double end_lot= region_position[stringToInt(ed)][0];          //목적지 위도경도
        double end_lng= region_position[stringToInt(ed)][1];

        this.naverMap=naverMap;

        switch (num){
            case 2:
                Retrofit retrofit0 = new Retrofit.Builder()
                        .baseUrl("https://naveropenapi.apigw.ntruss.com/map-direction/")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                RouteFind_nomid routeFind0 = retrofit0.create(RouteFind_nomid.class);
                Call<RoutePath> call0 = routeFind0.getData(NavaApIKey,secret,start_lng+","+start_lot,end_lng+","+end_lot);        //네이버 길찾기 rest api 시작 출발점 찍으면 됨
                call0.enqueue(new Callback<RoutePath>() {
                    @Override
                    public void onResponse(Call<RoutePath> call, Response<RoutePath> response) {
                        if(response.isSuccessful()) {
                            RoutePath routePath=response.body();
                            List<List<Double>> path=routePath.getRoute().getTraoptimal().get(0).getPath();
                            PathOverlay line_path=new PathOverlay();       //길 선 표시할 path 배열
                            for(int i=0;i<path.size();i++) {
                                list.add(new LatLng(path.get(i).get(1), path.get(i).get(0)));
                            }
                            markers[0]=new Marker();
                            markers[0].setPosition(new LatLng(start_lot,start_lng));
                            markers[0].setCaptionText("출발지");
                            markers[0].setMap(naverMap);
                            markers[1]=new Marker();
                            markers[1].setPosition(new LatLng(end_lot,end_lng));
                            markers[1].setCaptionText("목적지");
                            markers[1].setMap(naverMap);
                            line_path.setCoords(list);
                            line_path.setMap(naverMap);

                        }
                    }

                    @Override

                    public void onFailure(Call<RoutePath> call, Throwable t) {
                        Log.d(TAG,"실패");
                    }
                });
                break;
            case 3:
                Retrofit retrofit1 = new Retrofit.Builder()
                        .baseUrl("https://naveropenapi.apigw.ntruss.com/map-direction/")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                RouteFind routeFind1 = retrofit1.create(RouteFind.class);
                Call<RoutePath> call = routeFind1.getData(NavaApIKey,secret,start_lng+","+start_lot,end_lng+","+end_lot,result_long[0]+","+result_lat[0]);        //네이버 길찾기 rest api 시작 출발점 찍으면 됨
                call.enqueue(new Callback<RoutePath>() {
                    @Override
                    public void onResponse(Call<RoutePath> call, Response<RoutePath> response) {
                        if(response.isSuccessful()) {
                            RoutePath routePath=response.body();
                            List<List<Double>> path=routePath.getRoute().getTraoptimal().get(0).getPath();
                            PathOverlay line_path=new PathOverlay();       //길 선 표시할 path 배열
                            for(int i=0;i<path.size();i++) {
                                list.add(new LatLng(path.get(i).get(1), path.get(i).get(0)));
                            }
                            markers[0]=new Marker();
                            markers[0].setPosition(new LatLng(start_lot,start_lng));
                            markers[0].setCaptionText("출발지");
                            markers[0].setMap(naverMap);
                            for(int i=1;i< num-1;i++){
                                markers[i]=new Marker();
                                markers[i].setPosition(new LatLng(result_lat[i-1],result_long[i-1]));
                                markers[i].setCaptionText("경유지");
                                markers[i].setMap(naverMap);
                            }
                            markers[num-1]=new Marker();
                            markers[num-1].setPosition(new LatLng(end_lot,end_lng));
                            markers[num-1].setCaptionText("목적지");
                            markers[num-1].setMap(naverMap);
                            line_path.setCoords(list);
                            line_path.setMap(naverMap);


                        }
                    }

                    @Override

                    public void onFailure(Call<RoutePath> call, Throwable t) {
                        Log.d(TAG,"실패");
                    }
                });
                break;
            case 4:
                Retrofit retrofit2 = new Retrofit.Builder()
                        .baseUrl("https://naveropenapi.apigw.ntruss.com/map-direction/")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                RouteFind routeFind2 = retrofit2.create(RouteFind.class);
                Call<RoutePath> call2 = routeFind2.getData(NavaApIKey,secret,start_lng+","+start_lot,end_lng+","+end_lot,result_long[0]+","+result_lat[0]+"|"+result_long[1]+","+result_lat[1]);        //네이버 길찾기 rest api 시작 출발점 찍으면 됨
                call2.enqueue(new Callback<RoutePath>() {
                    @Override
                    public void onResponse(Call<RoutePath> call, Response<RoutePath> response) {
                        if(response.isSuccessful()) {
                            RoutePath routePath=response.body();
                            List<List<Double>> path=routePath.getRoute().getTraoptimal().get(0).getPath();
                            PathOverlay line_path=new PathOverlay();       //길 선 표시할 path 배열
                            for(int i=0;i<path.size();i++) {
                                list.add(new LatLng(path.get(i).get(1), path.get(i).get(0)));
                            }
                            markers[0]=new Marker();
                            markers[0].setPosition(new LatLng(start_lot,start_lng));
                            markers[0].setCaptionText("출발지");
                            markers[0].setMap(naverMap);
                            for(int i=1;i< num-1;i++){
                                markers[i]=new Marker();
                                markers[i].setPosition(new LatLng(result_lat[i-1],result_long[i-1]));
                                markers[i].setCaptionText("경유지");
                                markers[i].setMap(naverMap);
                            }
                            markers[num-1]=new Marker();
                            markers[num-1].setPosition(new LatLng(end_lot,end_lng));
                            markers[num-1].setCaptionText("목적지");
                            markers[num-1].setMap(naverMap);
                            line_path.setCoords(list);
                            line_path.setMap(naverMap);
                        }
                    }

                    @Override

                    public void onFailure(Call<RoutePath> call, Throwable t) {
                        Log.d(TAG,"실패");
                    }
                });
                break;
            case 5:
                Retrofit retrofit3 = new Retrofit.Builder()
                        .baseUrl("https://naveropenapi.apigw.ntruss.com/map-direction/")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                RouteFind routeFind3 = retrofit3.create(RouteFind.class);
                Call<RoutePath> call3 = routeFind3.getData(NavaApIKey,secret,start_lng+","+start_lot,end_lng+","+end_lot,result_long[0]+","+result_lat[0]+"|"+result_long[1]+","+result_lat[1]+"|"+result_long[2]+","+result_lat[2]);        //네이버 길찾기 rest api 시작 출발점 찍으면 됨
                call3.enqueue(new Callback<RoutePath>() {
                    @Override
                    public void onResponse(Call<RoutePath> call, Response<RoutePath> response) {
                        if(response.isSuccessful()) {
                            RoutePath routePath=response.body();
                            List<List<Double>> path=routePath.getRoute().getTraoptimal().get(0).getPath();
                            PathOverlay line_path=new PathOverlay();       //길 선 표시할 path 배열
                            for(int i=0;i<path.size();i++) {
                                list.add(new LatLng(path.get(i).get(1), path.get(i).get(0)));
                            }
                            markers[0]=new Marker();
                            markers[0].setPosition(new LatLng(start_lot,start_lng));
                            markers[0].setCaptionText("출발지");
                            markers[0].setMap(naverMap);
                            for(int i=1;i< num-1;i++){
                                markers[i]=new Marker();
                                markers[i].setPosition(new LatLng(result_lat[i-1],result_long[i-1]));
                                markers[i].setCaptionText("경유지");
                                markers[i].setMap(naverMap);
                            }
                            markers[num-1]=new Marker();
                            markers[num-1].setPosition(new LatLng(end_lot,end_lng));
                            markers[num-1].setCaptionText("목적지");
                            markers[num-1].setMap(naverMap);
                            line_path.setCoords(list);
                            line_path.setMap(naverMap);

                        }
                    }

                    @Override

                    public void onFailure(Call<RoutePath> call, Throwable t) {
                        Log.d(TAG,"실패");
                    }
                });
                break;
            case 6:
                Retrofit retrofit4 = new Retrofit.Builder()
                        .baseUrl("https://naveropenapi.apigw.ntruss.com/map-direction/")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                RouteFind routeFind4 = retrofit4.create(RouteFind.class);
                Call<RoutePath> call4 = routeFind4.getData(NavaApIKey,secret,start_lng+","+start_lot,end_lng+","+end_lot,result_long[0]+","+result_lat[0]+"|"+result_long[1]+","+result_lat[1]+"|"+result_long[2]+","+result_lat[2]+"|"+result_long[3]+","+result_lat[3]);        //네이버 길찾기 rest api 시작 출발점 찍으면 됨
                call4.enqueue(new Callback<RoutePath>() {
                    @Override
                    public void onResponse(Call<RoutePath> call, Response<RoutePath> response) {
                        if(response.isSuccessful()) {
                            RoutePath routePath=response.body();
                            List<List<Double>> path=routePath.getRoute().getTraoptimal().get(0).getPath();
                            PathOverlay line_path=new PathOverlay();       //길 선 표시할 path 배열
                            for(int i=0;i<path.size();i++) {
                                list.add(new LatLng(path.get(i).get(1), path.get(i).get(0)));
                            }
                            markers[0]=new Marker();
                            markers[0].setPosition(new LatLng(start_lot,start_lng));
                            markers[0].setCaptionText("출발지");
                            markers[0].setMap(naverMap);
                            for(int i=1;i< num-1;i++){
                                markers[i]=new Marker();
                                markers[i].setPosition(new LatLng(result_lat[i-1],result_long[i-1]));
                                markers[i].setCaptionText("경유지");
                                markers[i].setMap(naverMap);
                            }
                            markers[num-1]=new Marker();
                            markers[num-1].setPosition(new LatLng(end_lot,end_lng));
                            markers[num-1].setCaptionText("목적지");
                            markers[num-1].setMap(naverMap);
                            line_path.setCoords(list);
                            line_path.setMap(naverMap);

                        }
                    }

                    @Override

                    public void onFailure(Call<RoutePath> call, Throwable t) {
                        Log.d(TAG,"실패");
                    }
                });
                break;
            case 7:
                Retrofit retrofit5 = new Retrofit.Builder()
                        .baseUrl("https://naveropenapi.apigw.ntruss.com/map-direction/")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                RouteFind routeFind5 = retrofit5.create(RouteFind.class);
                Call<RoutePath> call5 = routeFind5.getData(NavaApIKey,secret,start_lng+","+start_lot,end_lng+","+end_lot,result_long[0]+","+result_lat[0]+"|"+result_long[1]+","+result_lat[1]+"|"+result_long[2]+","+result_lat[2]+"|"+result_long[3]+","+result_lat[3]+"|"+result_long[4]+","+result_lat[4]);        //네이버 길찾기 rest api 시작 출발점 찍으면 됨
                call5.enqueue(new Callback<RoutePath>() {
                    @Override
                    public void onResponse(Call<RoutePath> call, Response<RoutePath> response) {
                        if(response.isSuccessful()) {
                            RoutePath routePath=response.body();
                            List<List<Double>> path=routePath.getRoute().getTraoptimal().get(0).getPath();
                            PathOverlay line_path=new PathOverlay();       //길 선 표시할 path 배열
                            for(int i=0;i<path.size();i++) {
                                list.add(new LatLng(path.get(i).get(1), path.get(i).get(0)));
                            }
                            markers[0]=new Marker();
                            markers[0].setPosition(new LatLng(start_lot,start_lng));
                            markers[0].setCaptionText("출발지");
                            markers[0].setMap(naverMap);
                            for(int i=1;i< num-1;i++){
                                markers[i]=new Marker();
                                markers[i].setPosition(new LatLng(result_lat[i-1],result_long[i-1]));
                                markers[i].setCaptionText("경유지");
                                markers[i].setMap(naverMap);
                            }
                            markers[num-1]=new Marker();
                            markers[num-1].setPosition(new LatLng(end_lot,end_lng));
                            markers[num-1].setCaptionText("목적지");
                            markers[num-1].setMap(naverMap);
                            line_path.setCoords(list);
                            line_path.setMap(naverMap);

                        }
                    }

                    @Override

                    public void onFailure(Call<RoutePath> call, Throwable t) {
                        Log.d(TAG,"실패");
                    }
                });
                break;

        }
        LatLng m_p=new LatLng((start_lot+end_lot)/2,(start_lng+end_lng)/2);
        CameraPosition cameraPosition=new CameraPosition(m_p,7);
        naverMap.setCameraPosition(cameraPosition);
        dataSample = new ArrayList<String>();
        List = findViewById(R.id.listView);
        dataSample.add(st);
        if(num>2){
            for(int i=0;i<result_name.length;i++){
                dataSample.add(result_name[i]);
            }
        }
        dataSample.add(ed);
        List_Adapter buttonListAdapter = new List_Adapter(this, dataSample);
        List.setAdapter(buttonListAdapter);


        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                Button bus_route=view.findViewById(R.id.bus_route);
                Button car_route=view.findViewById(R.id.car_route);
                TextView title=view.findViewById(R.id.title);

                bus_route.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String item= title.getText().toString();
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

                        list = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                        if (list == null || list.isEmpty()) {
                            try {
                                if(item.equals(st)){
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url_f+String.valueOf(region_position[stringToInt(item)][0])+"&dlng="+String.valueOf(region_position[stringToInt(item)][1])+"&dname="+encodeResult+url_b)));
                                } else if (item.equals(ed)) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url_f+String.valueOf(region_position[stringToInt(item)][0])+"&dlng="+String.valueOf(region_position[stringToInt(item)][1])+"&dname="+encodeResult+url_b)));
                                }else {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url_f + String.valueOf(result_lat[stringToInt_s(item)]) + "&dlng=" + String.valueOf(result_long[stringToInt_s(item)]) + "&dname=" + encodeResult + url_b)));
                                }
                            }catch (Exception e){
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.nhn.android.nmap")));
                            }
                        } else {
                            startActivity(intent);
                        }
                    }
                });

                car_route.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String item= title.getText().toString();
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

                        list = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                        if (list == null || list.isEmpty()) {
                            try {
                                if(item.equals(st)){
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url_f+String.valueOf(region_position[stringToInt(item)][0])+"&dlng="+String.valueOf(region_position[stringToInt(item)][1])+"&dname="+encodeResult+url_b)));
                                } else if (item.equals(ed)) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url_f+String.valueOf(region_position[stringToInt(item)][0])+"&dlng="+String.valueOf(region_position[stringToInt(item)][1])+"&dname="+encodeResult+url_b)));
                                }else {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url_f + String.valueOf(result_lat[stringToInt_s(item)]) + "&dlng=" + String.valueOf(result_long[stringToInt_s(item)]) + "&dname=" + encodeResult + url_b)));
                                }
                            }catch (Exception e){
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.nhn.android.nmap")));
                            }
                        } else {
                            startActivity(intent);
                        }
                    }
                });
            }});



    }


}
