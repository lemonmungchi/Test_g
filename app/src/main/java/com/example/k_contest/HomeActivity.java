package com.example.k_contest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import androidx.drawerlayout.widget.DrawerLayout;

public class HomeActivity extends AppCompatActivity {
    List<String> searchList;
    AutoCompleteTextView StartPoint;
    AutoCompleteTextView EndPoint;
    Button MapPoint;
    ImageButton UserPage;
    DrawerLayout drawerLayout;
    View drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        //실행페이지

        ImageButton backspaceButton=findViewById(R.id.backspaceicon);
        backspaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ImageButton homeButton=findViewById(R.id.homeicon);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            }
        });
        ImageButton mapButton=findViewById(R.id.mapicon);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Map_Basic.class);
                startActivity(intent);
            }
        });
        ImageButton searchButton=findViewById(R.id.searchicon);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),SearchActivity.class);
                startActivity(intent);
            }
        });
        ImageButton profileButton=findViewById(R.id.profileicon);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(intent);
            }
        });
        //버튼 페이지 이동

        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);  //드로어 레이아웃 선택을 통해 작동
        drawer = (View)findViewById(R.id.drawer);

        ImageButton openSideBtn = (ImageButton)findViewById(R.id.sideMenuBtn);  // 사이드메뉴 열기 버튼

        openSideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(drawer);
            }
        });

        Button closeSideBtn = (Button)findViewById(R.id.closeBtn);  //사이드메뉴 닫기

        closeSideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(drawer);
            }
        });

        searchList = new ArrayList<>();
        settingList();
        StartPoint = findViewById(R.id.StartPoint);
        StartPoint.setAdapter(new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, searchList));

        EndPoint = findViewById(R.id.EndPoint);
        EndPoint.setAdapter(new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, searchList));

        MapPoint = findViewById(R.id.MapPoint);
        MapPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Map_Basic.class);
                intent.putExtra("Start",StartPoint.getText().toString());
                intent.putExtra("End",EndPoint.getText().toString());
                startActivity(intent);
            }
        });

        UserPage = findViewById(R.id.profileicon);

        UserPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, UserPageActivity.class);
                startActivity(intent);
            }
        });

    }
        private void settingList(){
            searchList.add("창원");
            searchList.add("진주");
            searchList.add("통영");
            searchList.add("사천");
            searchList.add("김해");
            searchList.add("밀양");
            searchList.add("거제");
            searchList.add("양산");
            searchList.add("부산");
            searchList.add("의령");
            searchList.add("함안");
            searchList.add("창녕");
            searchList.add("고성");
            searchList.add("남해");
            searchList.add("하동");
            searchList.add("산청");
            searchList.add("함양");
            searchList.add("거창");
            searchList.add("합천");
        }
}