package com.example.k_contest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.example.k_contest.R;
import com.example.k_contest.Route_choose;

public class Loading_4 extends AppCompatActivity {

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
        setContentView(R.layout.loading_4);

        startLoading();

    }// onCreate()..

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent2 = getIntent();
                String st = intent2.getStringExtra("Start");          //출발지 받아오기
                String ed = intent2.getStringExtra("End");             //목적지 받아오기
                boolean curs[]= intent2.getBooleanArrayExtra("State"); //체크표시 받아오기

                Intent intent= new Intent(getApplicationContext(), Route_choose.class);
                intent.putExtra("Start", st);
                intent.putExtra("End", ed);
                intent.putExtra("State",curs);
                startActivity(intent);  //Loagin화면을 띄운다.
                overridePendingTransition(0, 0);
                finish();   //현재 액티비티 종료
            }
        }, 200); // 화면에 Logo 2초간 보이기
    }// startLoading Method..
}// MainActivity Class..
