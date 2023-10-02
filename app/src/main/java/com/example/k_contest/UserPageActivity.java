package com.example.k_contest;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class UserPageActivity extends AppCompatActivity {

    Button Privacy;

    DrawerLayout drawerLayout;
    View drawer;

    TextView loginMy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpage);

        Privacy = findViewById(R.id.privacyBtn);

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
        Button loginbutton=findViewById(R.id.loginbutton);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }
        });
        Privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserPageActivity.this, PrivacyActivity.class);
                startActivity(intent);
            }
        });

        loginMy=findViewById(R.id.loginMy);

        loginMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserPageActivity.this, Login.class);
                startActivity(intent);
            }
        });


    }
}