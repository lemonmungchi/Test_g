package com.example.k_contest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;

public class City_Page_Activity extends AppCompatActivity {

    private ImageView imageV;
    private TextView g_name;
    private TextView infor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_page);

        imageV=findViewById(R.id.imageV);
        g_name=findViewById(R.id.name);
        infor=findViewById(R.id.infor);


        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String fileurl1 = intent.getStringExtra("fileurl1");          //출발지 받아오기
        String data_content = intent.getStringExtra("data_content");             //목적지 받아오기

        g_name.setText(name);
        infor.setText(data_content);

        Glide.with(this).load(fileurl1).into(imageV);

        ImageButton backspaceButton = findViewById(R.id.backspaceicon);
        backspaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}