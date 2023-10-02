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

import com.example.k_contest.fragments.Testfrag1;
import com.example.k_contest.fragments.Testfrag2;

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

    private Testfrag1 testfrag1;
    private Testfrag2 testfrag2;
    private Button buttontestfrag1;
    private Button buttontestfrag2;
    
    DrawerLayout drawerLayout;

    View drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);        //실행페이지


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        CheckBox natureCheck=(CheckBox)findViewById(R.id.nature_check);             //체크박스 이벤트생성
        CheckBox leisureCheck=(CheckBox)findViewById(R.id.leisure_check);
        CheckBox cultureCheck=(CheckBox)findViewById(R.id.culture_check);

        boolean nature_p=natureCheck.isChecked();
        boolean leisure_p=leisureCheck.isChecked();
        boolean culture_p=cultureCheck.isChecked();

        boolean cur_state[]={nature_p,leisure_p,culture_p};

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
                    Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
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

            //fragment
            testfrag1 = new Testfrag1();
            testfrag2 = new Testfrag2();
            //프래그먼트 매니저 획득
            FragmentManager fragmentManager = getSupportFragmentManager();

            //프래그먼트 Transaction(프래그먼트를 올리거나 교체하는 작업)
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //프래그먼트를 FrameLayout의 자식으로 등록
            fragmentTransaction.add(R.id.fragmentframe, testfrag1);
            fragmentTransaction.commit();

            buttontestfrag1 = findViewById(R.id.buttontestfrag1);
            buttontestfrag2 = findViewById(R.id.buttontestfrag2);

            buttontestfrag1.setOnClickListener(v -> {
                FragmentManager fm1 = getSupportFragmentManager();
                FragmentTransaction ft1 = fragmentManager.beginTransaction();
                ft1.replace(R.id.fragmentframe, testfrag1);
                ft1.commit();
            });

            buttontestfrag2.setOnClickListener(v -> {
                FragmentManager fm2 = getSupportFragmentManager();
                FragmentTransaction ft2 = fragmentManager.beginTransaction();
                ft2.replace(R.id.fragmentframe, testfrag2);
                ft2.commit();
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
                    Intent intent = new Intent(getApplicationContext(), Route_choose.class);
                    intent.putExtra("Start", StartPoint.getText().toString());
                    intent.putExtra("End", EndPoint.getText().toString());
                    intent.putExtra("State",cur_state);
                    startActivity(intent);
                }
            });


        }



    private void settingList() {
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