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

<<<<<<< HEAD
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
=======
    private ListView List;

<<<<<<< Updated upstream
    private ListView List;

    private ArrayList<String> dataSample;
=======
    private ArrayList<String> dataSample;
>>>>>>> 601550459b080978035aafa8ed3c8e05bb7ac68a
>>>>>>> Stashed changes

    List<LatLng> list=new ArrayList<>();

    private  Gson gson = new GsonBuilder().setLenient().create();

    private  FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String[] vertex = {"창원", "진주", "통영", "사천", "김해","밀양", "거제", "양산", "의령",
            "함양", "창녕", "고성", "남해", "하동", "산청", "함안", "거창", "합천"};
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
        String ed = intent.getStringExtra("End");             //목적지 받아오기

        Dijkstra dj=new Dijkstra(18, matrixGN);
        String[]rot =dj.algorithm(vertex[dj.stringToInt(st)],vertex[dj.stringToInt(ed)]);
        Collections.reverse(Arrays.asList(rot));
        Marker[] markers= new Marker[rot.length];

        double start_lot= region_position[dj.stringToInt(st)][0];           //출발지 위도경도
        double start_lng= region_position[dj.stringToInt(st)][1];

        double end_lot= region_position[dj.stringToInt(ed)][0];          //목적지 위도경도
        double end_lng= region_position[dj.stringToInt(ed)][1];

        this.naverMap=naverMap;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://naveropenapi.apigw.ntruss.com/map-direction/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        RouteFind routeFind = retrofit.create(RouteFind.class);

        Call<RoutePath> call = routeFind.getData(NavaApIKey,secret,start_lng+","+start_lot,end_lng+","+end_lot);        //네이버 길찾기 rest api 시작 출발점 찍으면 됨

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
                    markers[rot.length-1]=new Marker();
                    markers[rot.length-1].setPosition(new LatLng(end_lot,end_lng));
                    markers[rot.length-1].setCaptionText("목적지");
                    markers[rot.length-1].setMap(naverMap);


                    line_path.setCoords(list);


                    line_path.setMap(naverMap);
                    LatLng m_p=new LatLng((start_lot+end_lot)/2,(start_lng+end_lng)/2);
                    CameraPosition cameraPosition=new CameraPosition(m_p,9);
                    naverMap.setCameraPosition(cameraPosition);

                }
            }

            @Override
<<<<<<< HEAD
            public void onFailure(Call<RoutePath> call, Throwable t) {
                Log.d(TAG,"실패");
            }
        });

        dataSample = new ArrayList<String>();
=======
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

                Call<RoutePath> call = routeFind.getData(NavaApIKey,secret,"127.1058342,37.359708","129.075986,35.179470","128.075986,36.179470");        //네이버 길찾기 rest api 시작 출발점 찍으면 됨

                call.enqueue(new Callback<RoutePath>() {
                    @Override
                    public void onResponse(Call<RoutePath> call, Response<RoutePath> response) {
                        if(response.isSuccessful()) {
                            RoutePath routePath=response.body();
                            List<List<Double>> path=routePath.getRoute().getTraoptimal().get(0).getPath();
                            Marker[] marker= new Marker[path.size()];
                            PathOverlay line_path=new PathOverlay();       //길 선 표시할 path 배열
                            for(int i=0;i<path.size();i++) {
                                list.add(new LatLng(path.get(i).get(1), path.get(i).get(0)));
                            }
                            //todo 끝점 경유지점 마커 찍기
                            line_path.setCoords(list);


                            line_path.setMap(naverMap);
                            LatLng m_p=new LatLng((37.359708+35.179470)/2,(127.1058342+129.075986)/2);
                            CameraPosition cameraPosition=new CameraPosition(m_p,6);
                            naverMap.setCameraPosition(cameraPosition);
                            // String encodeResult = URLEncoder.encode(String encodingString, "UTF-8"); 인코딩하기
                            /* 길찾기 자동차 nmap://navigation?dlat=35.5328&dlng=128.7029&dname=%eb%b0%80%ec%96%91+%ec%97%b0%ea%bd%83%eb%a7%88%ec%9d%84&appname=com.example.ownroadrider
                            대중교통 nmap://route/public?dlat=35.5328&dlng=128.7029&dname=%eb%b0%80%ec%96%91+%ec%97%b0%ea%bd%83%eb%a7%88%ec%9d%84&appname=com.example.ownroadrider
                            String url = "nmap://actionPath?parameter=value&appname=ownroadrider";

                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            intent.addCategory(Intent.CATEGORY_BROWSABLE);

                            List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                            if (list == null || list.isEmpty()) {
                                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("nmap://navigation?dlat=35.5328&dlng=128.7029&dname=%eb%b0%80%ec%96%91+%ec%97%b0%ea%bd%83%eb%a7%88%ec%9d%84&appname=com.example.ownroadrider")));
                            } else {
                                context.startActivity(intent);
                            }
                             */
                        }
                    }

                    @Override
                    public void onFailure(Call<RoutePath> call, Throwable t) {
                        Log.d(TAG,"실패");
                    }
                });
            }
        });

        List = findViewById(R.id.listView);
        List_Adapter buttonListAdapter = new List_Adapter(this, dataSample);
        List.setAdapter(buttonListAdapter);
    }
>>>>>>> 601550459b080978035aafa8ed3c8e05bb7ac68a

        for(int i=0;i<rot.length;i++){                  //출발,경유,도착지 넣기
            dataSample.add(rot[i]);
        }

        List = findViewById(R.id.listView);
        List_Adapter buttonListAdapter = new List_Adapter(this, dataSample);
        List.setAdapter(buttonListAdapter);
    }


}
