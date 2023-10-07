package com.example.k_contest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;

public class HomeActivity extends AppCompatActivity {
    List<String> searchList;
    AutoCompleteTextView StartPoint;
    AutoCompleteTextView EndPoint;
    Button MapPoint;
    ImageButton UserPage;


    RadioButton natureCheck;

    RadioButton leisureCheck;
    RadioButton cultureCheck;

    boolean nature_p;

    boolean leisure_p;
    boolean culture_p;

    
    DrawerLayout drawerLayout;

    View drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);        //실행페이지


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        natureCheck=findViewById(R.id.nature_check);
        leisureCheck=findViewById(R.id.leisure_check);
        cultureCheck=findViewById(R.id.culture_check);

       natureCheck.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               nature_p=true;
               leisure_p=false;
               culture_p=false;
           }
       });

       leisureCheck.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               nature_p=false;
               leisure_p=true;
               culture_p=false;
           }
       });

       cultureCheck.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               nature_p=false;
               leisure_p=false;
               culture_p=true;
           }
       });

        ImageButton backspaceButton = findViewById(R.id.backspaceicon);
            backspaceButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

            ImageButton homeButton = findViewById(R.id.homeicon);

            homeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                }
            });
            ImageButton mapButton = findViewById(R.id.mapicon);
            mapButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), Map_myloc.class);
                    startActivity(intent);
                }
            });
            ImageButton searchButton = findViewById(R.id.searchicon);
            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), SearchActivity.class);//수정
                    startActivity(intent);
                }
            });
            ImageButton profileButton = findViewById(R.id.profileicon);
            profileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), UserPageActivity.class);
                    startActivity(intent);
                }
            });

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
                    boolean [] cur_state={nature_p,leisure_p,culture_p};
                    Intent intent = new Intent(getApplicationContext(), Route_choose.class);
                    intent.putExtra("Start", StartPoint.getText().toString());
                    intent.putExtra("End", EndPoint.getText().toString());
                    intent.putExtra("State",cur_state);
                    startActivity(intent);
                }
            });


        }



    private void settingList() {
        searchList.add("창원시");
        searchList.add("진주시");
        searchList.add("통영시");
        searchList.add("사천시");
        searchList.add("김해시");
        searchList.add("밀양시");
        searchList.add("거제시");
        searchList.add("양산시");
        searchList.add("의령군");
        searchList.add("함안군");
        searchList.add("창녕군");
        searchList.add("고성군");
        searchList.add("남해군");
        searchList.add("하동군");
        searchList.add("산청군");
        searchList.add("함양군");
        searchList.add("거창군");
        searchList.add("합천군");
    }
}