package com.example.k_contest;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class UserPageActivity extends AppCompatActivity {

    Button Privacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpage);

        Privacy = findViewById(R.id.privacyBtn);
        Privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserPageActivity.this, PrivacyActivity.class);
                startActivity(intent);
            }
        });
    }
}