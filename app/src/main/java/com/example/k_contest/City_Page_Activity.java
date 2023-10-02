package com.example.k_contest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class City_Page_Activity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_page);

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

        ImageButton emptyheart = findViewById(R.id.emptyheart);
        emptyheart.setOnClickListener(new View.OnClickListener() {
            int i=1;
            public void onClick(View view) {
                if (i==1) {
                    emptyheart.setImageResource(R.drawable.fullheart);
                    i=0;
                } else {
                    emptyheart.setImageResource(R.drawable.emptyheart);
                    i=1;
                }
            }
        });
    }
}