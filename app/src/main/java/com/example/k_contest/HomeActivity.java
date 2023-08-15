package com.example.k_contest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    List<String> searchList;

    AutoCompleteTextView StartPoint;

    AutoCompleteTextView EndPoint;

    Button MapPoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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