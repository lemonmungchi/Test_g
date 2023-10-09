package com.example.k_contest;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserPageActivity extends AppCompatActivity {

    Button Privacy;

    DrawerLayout drawerLayout;
    View drawer;


    private Button reviewManagementBtn;
    private TextView loginMy;

    public Button bookmarkBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpage);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        bookmarkBtn=findViewById(R.id.bookmarkBtn);
        loginMy=findViewById(R.id.loginMy);
        if(user==null){
            loginMy.setText("로그인 하세요");
        }else {
            loginMy.setText("사용자1");
        }

        Privacy = findViewById(R.id.privacyBtn);
        reviewManagementBtn=findViewById(R.id.reviewManagementBtn);

        //하단바
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
                Intent intent=new Intent(getApplicationContext(),UserPageActivity.class);
                startActivity(intent);
            }
        });//하단바

        Privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user==null){
                    Toast.makeText(getApplicationContext(),"로그인을 하세요",Toast.LENGTH_LONG).show();
                    Intent go_intent=new Intent(UserPageActivity.this, Login.class);
                    startActivity(go_intent);
                }else {
                    Intent intent = new Intent(UserPageActivity.this, PrivacyActivity.class);
                    startActivity(intent);
                }
            }
        });

        reviewManagementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user==null){
                    Toast.makeText(getApplicationContext(),"로그인을 하세요",Toast.LENGTH_LONG).show();
                    Intent go_intent=new Intent(UserPageActivity.this, Login.class);
                    startActivity(go_intent);
                }else{
                    Intent intent=new Intent(UserPageActivity.this,Review_manage.class);
                    startActivity(intent);
                }
            }
        });

        bookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user==null){
                    Toast.makeText(getApplicationContext(),"로그인을 하세요",Toast.LENGTH_LONG).show();
                    Intent go_intent=new Intent(UserPageActivity.this, Login.class);
                    startActivity(go_intent);
                }else {
                    Intent intent=new Intent(UserPageActivity.this,like_l.class);
                    startActivity(intent);
                }
            }
        });


    }
}