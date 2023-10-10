package com.example.k_contest;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReviewActivity extends AppCompatActivity {
    private EditText reviewtextbox;
    private TextView cityName;

    private ArrayList<String> review;

    private ListView review_List;
    private List_Adapter_Review ReviewListAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Intent intent= getIntent();
        String name= intent.getStringExtra("name");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        review=new ArrayList<String>();
        review_List=findViewById(R.id.review_rlist);

        cityName=findViewById(R.id.cityName);

        cityName.setText(name);


        Map<String, Object> Review_Data = new HashMap<>();
        Review_Data.put("리뷰", null);
        review.clear();
        db.collection("Review_Data")
                .whereEqualTo("con_name",name)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                review.add(document.get("review_text",String.class));
                            }
                            ReviewListAdapter=new List_Adapter_Review(ReviewActivity.this,review);
                            review_List.setAdapter(ReviewListAdapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });



        Button reviewbutton = findViewById(R.id.reviewbutton);
        reviewbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user == null) {
                    Toast.makeText(getApplicationContext(),"로그인을 하세요",Toast.LENGTH_LONG).show();
                    Intent go_intent=new Intent(ReviewActivity.this, Login.class);
                    startActivity(go_intent);
                } else {
                    String email=user.getEmail();
                    reviewtextbox=findViewById(R.id.reviewtextbox);
                    Map<String, Object> adddata = new HashMap<>();
                    adddata.put("review_text", reviewtextbox.getText().toString());
                    adddata.put("user_email",email);
                    adddata.put("con_name",name);



                    db.collection("Review_Data")
                            .add(adddata)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(getApplicationContext(),"리뷰등록완료!",Toast.LENGTH_LONG).show();
                                    review.clear();
                                    db.collection("Review_Data")
                                            .whereEqualTo("con_name",name)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        adddata.put("document_id",documentReference.getId());
                                                        db.collection("Review_Data").document(documentReference.getId())
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


                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                            review.add(document.get("review_text",String.class));
                                                        }
                                                        ReviewListAdapter=new List_Adapter_Review(ReviewActivity.this,review);
                                                        review_List.setAdapter(ReviewListAdapter);
                                                    } else {
                                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                                    }
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

}
