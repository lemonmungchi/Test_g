package com.example.k_contest;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signUp extends AppCompatActivity {

    TextView back;
    EditText pw,pw2,email,name;
    Button pwcheck, submit;


    private FirebaseAuth mFirebaseAuth;     //파이어베이스 인증처리


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );//네비바 제거

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mFirebaseAuth=FirebaseAuth.getInstance();

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        pw=findViewById(R.id.signPW);
        pw2=findViewById(R.id.signPW2);
        email=findViewById(R.id.signmail);
        pwcheck = findViewById(R.id.pwcheckbutton);
        name=findViewById(R.id.signID);
        pwcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pw.getText().toString().equals(pw2.getText().toString())){
                    pwcheck.setText("일치");
                    Toast.makeText(signUp.this, "비밀번호일치", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(signUp.this, "비밀번호가 다릅니다.", Toast.LENGTH_LONG).show();
                }
            }
        });

        submit=findViewById(R.id.signupbutton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mypw=pw.getText().toString();
                String mypw2=pw2.getText().toString();
                String myemail=email.getText().toString();
                String myname=name.getText().toString();
                if(mypw.equals(mypw2)){
                    mFirebaseAuth.createUserWithEmailAndPassword(myemail, mypw)
                            .addOnCompleteListener(signUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(signUp.this, "회원가입성공",Toast.LENGTH_LONG).show();
                                        FirebaseUser user = mFirebaseAuth.getCurrentUser();
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(myname).build();
                                        user.updateProfile(profileUpdates)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {

                                                        }else{
                                                            Log.d(TAG, "실패.");
                                                        }
                                                    }
                                                });

                                        onBackPressed();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(signUp.this, "회원가입 실패",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }else{
                    Toast.makeText(signUp.this, "비밀번호가 중복체크 실패",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}