package com.example.k_contest;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class City_Page_Activity extends AppCompatActivity {

    private ImageView imageV;
    private TextView g_name;
    private TextView infor;
    private String email;

    private ArrayList<String> heart_value;
    private ArrayList<String> heart_count;
    private FirebaseUser user;
    private int heart;

    private TextView countheart;

    protected void onCreate(Bundle savedInstanceState) {

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );//네비바 제거

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_page);

        imageV=findViewById(R.id.imageV);
        g_name=findViewById(R.id.name);
        infor=findViewById(R.id.infor);
        ImageButton emptyheart = findViewById(R.id.emptyheart);
        countheart=findViewById(R.id.countheart);

        heart_value=new ArrayList<String>();
        heart_count=new ArrayList<String>();

        user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String fileurl1 = intent.getStringExtra("fileurl1");          //출발지 받아오기
        String data_content = intent.getStringExtra("data_content");             //목적지 받아오기

        if(user==null){

        }else{
           email=user.getEmail();
        }


        db.collection("like_data")
                .whereEqualTo("con_name",name)
                .whereEqualTo("heart_value","on")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                heart_count.add(document.get("heart_value",String.class));

                            }
                            if(heart_count.size()>0){
                                int i=heart_count.size();
                                String n=String.valueOf(i);
                                countheart.setText(n);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        db.collection("like_data")
                .whereEqualTo("con_name",name)
                .whereEqualTo("user_email",email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                heart_value.add(document.get("heart_value",String.class));
                            }
                            if(heart_value.size()>0){
                                if(heart_value.get(0).equals("on")){
                                    emptyheart.setImageResource(R.drawable.fullheart);
                                    heart=1;
                                }else {
                                    emptyheart.setImageResource(R.drawable.emptyheart);
                                    heart=0;
                                }
                            }else {
                                emptyheart.setImageResource(R.drawable.emptyheart);
                                heart=0;
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

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




        emptyheart.setOnClickListener(new View.OnClickListener() {
            int heart=1;
            public void onClick(View view) {
                if (heart==1) {
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user == null) {
                        Toast.makeText(getApplicationContext(),"로그인을 하세요",Toast.LENGTH_LONG).show();
                        Intent go_intent=new Intent(City_Page_Activity.this, Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |  Intent.FLAG_ACTIVITY_CLEAR_TOP);//액티비티 스택제거
                        startActivity(go_intent);
                    } else {
                        emptyheart.setImageResource(R.drawable.fullheart);
                        String email=user.getEmail();
                        Map<String, Object> adddata = new HashMap<>();
                        adddata.put("user_email",email);
                        adddata.put("con_name",name);
                        adddata.put("heart_value","on");

                        db.collection("like_data")
                                .add(adddata)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        adddata.put("document_id",documentReference.getId());
                                        db.collection("like_data").document(documentReference.getId())
                                                .set(adddata)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {

                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                    }
                                                });
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error adding document", e);
                                    }
                                });
                        String n=countheart.getText().toString();
                        int i=Integer.valueOf(n);
                        i+=1;
                        String num=String.valueOf(i);
                        countheart.setText(num);
                        heart=0;
                    }
                } else {
                    ArrayList<String> doc_id=new ArrayList<String>();
                    db.collection("like_data")
                            .whereEqualTo("con_name",name)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            doc_id.add(document.get("document_id",String.class));
                                        }
                                        if(doc_id.size()>0){
                                            db.collection("like_data").document(doc_id.get(0))
                                                    .delete()
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.w(TAG, "Error deleting document", e);
                                                        }
                                                    });
                                        }

                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                    emptyheart.setImageResource(R.drawable.emptyheart);
                    String n=countheart.getText().toString();
                    int i=Integer.valueOf(n);
                    i-=1;
                    String num=String.valueOf(i);
                    countheart.setText(num);
                    heart=1;
                }
            }
        });

        Button reviewpagebutton = findViewById(R.id.reviewpagebutton);
        reviewpagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });
    }
}