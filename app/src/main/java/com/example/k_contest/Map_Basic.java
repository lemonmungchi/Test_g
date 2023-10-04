package com.example.k_contest;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
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


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    private String NavaApIKey="nowdd7jigt";

    private String secret="C7toUlRTfdqJlYR8tsX6fGj2WtVIFIEySUFTO72L";

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


    private String[] vertex = {"창원", "진주", "통영", "사천", "김해","밀양", "거제", "양산", "의령",
            "함양", "창녕", "고성", "남해", "하동", "산청", "함안", "거창", "합천"};
    public int stringToInt(String s) {              // String to Int
        int x = 0;
        for (int i = 0; i < vertex.length; i++) {
            if (vertex[i].equals(s)) x = i;
        }
        return x;
    }
    
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

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {                                        //맵객체 매서드 사용시 여기서 작성


        Intent intent = getIntent();
        String st = intent.getStringExtra("Start");          //출발지 받아오기
        int num=intent.getIntExtra("num",0);
        if(num>2) {
            double[] result_lat = intent.getDoubleArrayExtra("rot_lat");
            double[] result_long = intent.getDoubleArrayExtra("rot_long");
        }


        String ed = intent.getStringExtra("End");             //목적지 받아오기


        Marker[] markers= new Marker[num];
        double start_lot= region_position[stringToInt(st)][0];           //출발지 위도경도
        double start_lng= region_position[stringToInt(st)][1];
        double end_lot= region_position[stringToInt(ed)][0];          //목적지 위도경도
        double end_lng= region_position[stringToInt(ed)][1];

        this.naverMap=naverMap;
        /*
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://naveropenapi.apigw.ntruss.com/map-direction/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        RouteFind routeFind = retrofit.create(RouteFind.class);
        switch (result_long.size()){
            case 0:
                Retrofit retrofit0 = new Retrofit.Builder()
                        .baseUrl("https://naveropenapi.apigw.ntruss.com/map-direction/")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                RouteFind_nomid routeFind0 = retrofit.create(RouteFind_nomid.class);
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
                            markers[rot.length-1]=new Marker();
                            markers[rot.length-1].setPosition(new LatLng(end_lot,end_lng));
                            markers[rot.length-1].setCaptionText("목적지");
                            markers[rot.length-1].setMap(naverMap);
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
            case 1:
                Call<RoutePath> call = routeFind.getData(NavaApIKey,secret,start_lng+","+start_lot,end_lng+","+end_lot,mid_lotlng[0][1]+","+mid_lotlng[0][0]);        //네이버 길찾기 rest api 시작 출발점 찍으면 됨
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
                            for(int i=1;i< rot.length-1;i++){
                                markers[i]=new Marker();
                                markers[i].setPosition(new LatLng(mid_lotlng[i-1][0],mid_lotlng[i-1][1]));
                                markers[i].setCaptionText("경유지");
                                markers[i].setMap(naverMap);
                            }
                            markers[rot.length-1]=new Marker();
                            markers[rot.length-1].setPosition(new LatLng(end_lot,end_lng));
                            markers[rot.length-1].setCaptionText("목적지");
                            markers[rot.length-1].setMap(naverMap);
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
            case 2:
                Call<RoutePath> call2 = routeFind.getData(NavaApIKey,secret,start_lng+","+start_lot,end_lng+","+end_lot,mid_lotlng[0][1]+","+mid_lotlng[0][0]+"|"+mid_lotlng[1][1]+","+mid_lotlng[1][0]);        //네이버 길찾기 rest api 시작 출발점 찍으면 됨
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
                            for(int i=0;i<mid_n+1;i++){
                                markers[i+1]=new Marker();
                                markers[i+1].setPosition(new LatLng(mid_lotlng[i][0],mid_lotlng[i][1]));
                                markers[i+1].setCaptionText("경유지");
                                markers[i+1].setMap(naverMap);
                            }
                            markers[rot.length-1]=new Marker();
                            markers[rot.length-1].setPosition(new LatLng(end_lot,end_lng));
                            markers[rot.length-1].setCaptionText("목적지");
                            markers[rot.length-1].setMap(naverMap);
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
                Call<RoutePath> call3 = routeFind.getData(NavaApIKey,secret,start_lng+","+start_lot,end_lng+","+end_lot,mid_lotlng[0][1]+","+mid_lotlng[0][0]+"|"+mid_lotlng[1][1]+","+mid_lotlng[1][0]+"|"+mid_lotlng[2][1]+","+mid_lotlng[2][0]);        //네이버 길찾기 rest api 시작 출발점 찍으면 됨
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
                            for(int i=0;i<mid_n+1;i++){
                                markers[i+1]=new Marker();
                                markers[i+1].setPosition(new LatLng(mid_lotlng[i][0],mid_lotlng[i][1]));
                                markers[i+1].setCaptionText("경유지");
                                markers[i+1].setMap(naverMap);
                            }
                            markers[rot.length-1]=new Marker();
                            markers[rot.length-1].setPosition(new LatLng(end_lot,end_lng));
                            markers[rot.length-1].setCaptionText("목적지");
                            markers[rot.length-1].setMap(naverMap);
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
                Call<RoutePath> call4 = routeFind.getData(NavaApIKey,secret,start_lng+","+start_lot,end_lng+","+end_lot,mid_lotlng[0][1]+","+mid_lotlng[0][0]+"|"+mid_lotlng[1][1]+","+mid_lotlng[1][0]+"|"+mid_lotlng[2][1]+","+mid_lotlng[2][0]+"|"+mid_lotlng[3][1]+","+mid_lotlng[3][0]);        //네이버 길찾기 rest api 시작 출발점 찍으면 됨
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
                            for(int i=0;i<mid_n+1;i++){
                                markers[i+1]=new Marker();
                                markers[i+1].setPosition(new LatLng(mid_lotlng[i][0],mid_lotlng[i][1]));
                                markers[i+1].setCaptionText("경유지");
                                markers[i+1].setMap(naverMap);
                            }
                            markers[rot.length-1]=new Marker();
                            markers[rot.length-1].setPosition(new LatLng(end_lot,end_lng));
                            markers[rot.length-1].setCaptionText("목적지");
                            markers[rot.length-1].setMap(naverMap);
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
                Call<RoutePath> call5 = routeFind.getData(NavaApIKey,secret,start_lng+","+start_lot,end_lng+","+end_lot,mid_lotlng[0][1]+","+mid_lotlng[0][0]+"|"+mid_lotlng[1][1]+","+mid_lotlng[1][0]+"|"+mid_lotlng[2][1]+","+mid_lotlng[2][0]+"|"+mid_lotlng[3][1]+","+mid_lotlng[3][0]+"|"+mid_lotlng[4][1]+","+mid_lotlng[4][0]);        //네이버 길찾기 rest api 시작 출발점 찍으면 됨
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
                            if(mid_n>0){
                                    for(int i=0;i<mid_n+1;i++){
                                        markers[i+1]=new Marker();
                                        markers[i+1].setPosition(new LatLng(mid_lotlng[i][0],mid_lotlng[i][1]));
                                        markers[i+1].setCaptionText("경유지");
                                        markers[i+1].setMap(naverMap);
                                    }
                            }
                            markers[rot.length-1]=new Marker();
                            markers[rot.length-1].setPosition(new LatLng(end_lot,end_lng));
                            markers[rot.length-1].setCaptionText("목적지");
                            markers[rot.length-1].setMap(naverMap);
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
        CameraPosition cameraPosition=new CameraPosition(m_p,8);
        naverMap.setCameraPosition(cameraPosition);
        dataSample = new ArrayList<String>();
        List = findViewById(R.id.listView);
        for(int i=0;i<rot.length;i++){                  //출발,경유,도착지 넣기
            dataSample.add(rot[i]);
        }
        List_Adapter buttonListAdapter = new List_Adapter(this, dataSample);
        List.setAdapter(buttonListAdapter);

         */
    }


}
