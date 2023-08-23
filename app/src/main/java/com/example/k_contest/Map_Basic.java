package com.example.k_contest;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.util.FusedLocationSource;


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



    private static final String APIKEY="aE5iTKPfr1Msn7QXu8LmeK1SuDfo36insow1VLonAp3hb0VbTMjYr08mS8h1Q42h";
    private Button ex_retro;
    private  Gson gson = new GsonBuilder().setLenient().create();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_basic);

        mapView = findViewById(R.id.map_basic);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);


        ex_retro=findViewById(R.id.ex_retro);

        ex_retro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://gyeongnam.openapi.redtable.global/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                DataAddress dataAddress = retrofit.create(DataAddress.class);

                Call<DataPath> call = dataAddress.getData("aE5iTKPfr1Msn7QXu8LmeK1SuDfo36insow1VLonAp3hb0VbTMjYr08mS8h1Q42h",1);

                call.enqueue(new Callback<DataPath>() {
                    @Override
                    public void onResponse(Call<DataPath> call, Response<DataPath> response) {
                        if(response.isSuccessful()){
                            DataPath data = response.body();
                            for(int i=0;i<data.getBody().size();i++){

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


    }


}
