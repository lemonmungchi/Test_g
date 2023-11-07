package com.example.k_contest;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Review_manage extends AppCompatActivity {

    private ArrayList<String> review;
    private ArrayList<String> review_title;

    private ListView review_List;
    private List_Adapter_ReviewM ReviewListAdapter_M;

    ArrayList<String> fileurl;

    ArrayList<String> data_con;

    String fileurl1;

    String data_content;

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
        setContentView(R.layout.activity_review_manage);


        fileurl=new ArrayList<String>();
        data_con=new ArrayList<String>();
        review=new ArrayList<String>();
        review_title=new ArrayList<String>();
        review_List=findViewById(R.id.review_mlist);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        String email=user.getEmail();

        if(user==null){
            Toast.makeText(getApplicationContext(),"로그인을 하세요",Toast.LENGTH_LONG).show();
            Intent go_intent=new Intent(Review_manage.this, Login.class);
            startActivity(go_intent);
        }else {
            db.collection("Review_Data")
                    .whereEqualTo("user_email",email)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {

                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    review.add(document.get("review_text",String.class));
                                    review_title.add(document.get("con_name",String.class));
                                }
                                ReviewListAdapter_M=new List_Adapter_ReviewM(Review_manage.this,review);
                                review_List.setAdapter(ReviewListAdapter_M);
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
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
        }

        review_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                TextView text_title=view.findViewById(R.id.Review_m_title);

                fileurl.clear();
                data_con.clear();
                String i=text_title.getText().toString();
                db.collection("nature_data")
                        .whereEqualTo("data_title",i)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        fileurl.add(document.get("fileurl1",String.class));
                                        data_con.add(document.get("data_content",String.class));
                                    }
                                    if(fileurl.size()>0&&data_con.size()>0){
                                        fileurl1=fileurl.get(0);
                                        data_content=data_con.get(0);
                                        Intent intent=new Intent(getApplicationContext(), City_Page_Activity.class);
                                        if(data_content.length()>0 && fileurl1.length()>0 ) {
                                            data_content = data_content.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
                                            data_content = data_content.replaceAll("<[^>]*>", " ");
                                            data_content = data_content.replace("/(<([^>]+)>)/", "");
                                            intent.putExtra("fileurl1", fileurl1);
                                            intent.putExtra("data_content", data_content);
                                            intent.putExtra("name", i);
                                            startActivity(intent);
                                        }
                                    }else {
                                        db.collection("culture_data")
                                                .whereEqualTo("data_title",i)
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if (task.isSuccessful()) {

                                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                                fileurl.add(document.get("fileurl1", String.class));
                                                                data_con.add(document.get("data_content", String.class));
                                                            }
                                                            if (fileurl.size() > 0 && data_con.size() > 0) {
                                                                fileurl1 = fileurl.get(0);
                                                                data_content = data_con.get(0);
                                                                Intent intent = new Intent(getApplicationContext(), City_Page_Activity.class);
                                                                if (data_content.length() > 0 && fileurl1.length() > 0) {
                                                                    data_content = data_content.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
                                                                    data_content = data_content.replaceAll("<[^>]*>", " ");
                                                                    data_content = data_content.replace("/(<([^>]+)>)/", "");
                                                                    intent.putExtra("fileurl1", fileurl1);
                                                                    intent.putExtra("data_content", data_content);
                                                                    intent.putExtra("name", i);
                                                                    startActivity(intent);
                                                                }
                                                            } else {
                                                                db.collection("leisure_data")
                                                                        .whereEqualTo("data_title", i)
                                                                        .get()
                                                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                if (task.isSuccessful()) {
                                                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                                                        fileurl.add(document.get("fileurl1", String.class));
                                                                                        data_con.add(document.get("data_content", String.class));
                                                                                    }
                                                                                    if (fileurl.size() > 0 && data_con.size() > 0) {
                                                                                        fileurl1 = fileurl.get(0);
                                                                                        data_content = data_con.get(0);
                                                                                        Intent intent = new Intent(getApplicationContext(), City_Page_Activity.class);
                                                                                        if (data_content.length() > 0 && fileurl1.length() > 0) {
                                                                                            data_content = data_content.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
                                                                                            data_content = data_content.replaceAll("<[^>]*>", " ");
                                                                                            data_content = data_content.replace("/(<([^>]+)>)/", "");
                                                                                            intent.putExtra("fileurl1", fileurl1);
                                                                                            intent.putExtra("data_content", data_content);
                                                                                            intent.putExtra("name", i);
                                                                                            startActivity(intent);
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }

                                                                        });
                                                            }


                                                        }
                                                    }

                                                });
                                    }



                                }
                            }

                        });


            }
        });

    }
}