package com.example.k_contest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.util.FusedLocationSource;

public class Map_myloc extends AppCompatActivity implements OnMapReadyCallback {

    private MapView map_myloc;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;

    private FusedLocationSource locationSource;

    private NaverMap naverMap;

    private String NavaApIKey= BuildConfig.NAVER_API;

    private String secret= BuildConfig.NAVER_SECRET;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );//네비바 제거

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_myloc);

        map_myloc = findViewById(R.id.map_basic);
        map_myloc.onCreate(savedInstanceState);

        map_myloc.getMapAsync(this);


        locationSource =
                new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        map_myloc.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        map_myloc.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        map_myloc.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        map_myloc.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        map_myloc.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        map_myloc.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        map_myloc.onLowMemory();
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

        this.naverMap=naverMap;
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
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
}