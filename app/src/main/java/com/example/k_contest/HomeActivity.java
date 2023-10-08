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


    RadioButton natureCheck;

    RadioButton leisureCheck;
    RadioButton cultureCheck;


    private Testfrag1 testfrag1;
    private Testfrag2 testfrag2;
    private Button buttontestfrag1;
    private Button buttontestfrag2;

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
                    Intent intent = new Intent(getApplicationContext(), City_Page_Activity.class);//수정
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
        searchList.add("의령시");
        searchList.add("함안군");
        searchList.add("창녕군");
        searchList.add("고성군");
        searchList.add("남해군");
        searchList.add("하동군");
        searchList.add("산청시");
        searchList.add("함양시");
        searchList.add("거창군");
        searchList.add("합천군");
    }
}