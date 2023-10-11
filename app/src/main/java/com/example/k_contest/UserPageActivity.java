package com.example.k_contest;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.auth.User;

public class UserPageActivity extends AppCompatActivity {
    private Button logoutbutton;
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
            String name= user.getDisplayName();
            loginMy.setText(name+" 님");
        }

        loginMy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user==null){
                    Toast.makeText(getApplicationContext(),"로그인을 하세요",Toast.LENGTH_LONG).show();
                    Intent go_intent=new Intent(UserPageActivity.this, Login.class);
                    go_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |  Intent.FLAG_ACTIVITY_CLEAR_TOP);//액티비티 스택제거
                    startActivity(go_intent);
                }
            }
        });

        reviewManagementBtn=findViewById(R.id.reviewManagementBtn);
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

        logoutbutton = findViewById(R.id.privacyBtn);
        //FirebaseAuth auth = FirebaseAuth.getInstance();

        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user==null){
                    Toast.makeText(getApplicationContext(),"로그인을 하세요",Toast.LENGTH_LONG).show();
                    Intent go_intent=new Intent(UserPageActivity.this, Login.class);
                    startActivity(go_intent);
                }else{
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.getInstance().signOut();//로그아웃 수정+++++++++++++++++++++++++
                    Toast.makeText(getApplicationContext(),"로그아웃 완료",Toast.LENGTH_LONG).show();
                    try {
                        //TODO 액티비티 화면 재갱신 시키는 코드
                        Intent intent = getIntent();
                        finish(); //현재 액티비티 종료 실시
                        overridePendingTransition(0, 0); //인텐트 애니메이션 없애기
                        startActivity(intent); //현재 액티비티 재실행 실시
                        overridePendingTransition(0, 0); //인텐트 애니메이션 없애기
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

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
    }
}