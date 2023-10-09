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
        review=new ArrayList<String>();
        review_List=findViewById(R.id.search_list);

        cityName=findViewById(R.id.cityName);

        cityName.setText(name);


        FirebaseFirestore db = FirebaseFirestore.getInstance();

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






        //기존 문서와 병합
        /*Map<String, Object> data = new HashMap<>();
        data.put("capital", true);

        db.collection("Review_Data").document("reviewtext")
                .set(data, SetOptions.merge());

        //문서 데이터 유형
        Map<String, Object> docData = new HashMap<>();
        docData.put("stringExample", "Hello world!");
        docData.put("booleanExample", true);
        docData.put("numberExample", 3.14159265);
        docData.put("dateExample", new Timestamp(new Date()));
        docData.put("listExample", Arrays.asList(1, 2, 3));
        docData.put("nullExample", null);

        Map<String, Object> nestedData = new HashMap<>();
        nestedData.put("a", 5);
        nestedData.put("b", true);

        docData.put("objectExample", nestedData);

        db.collection("Review_Data").document("reviewtext ")
                .set(docData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
        */

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


                    /*DocumentReference docRef = db.collection("reviewtext").document("리뷰");
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });

                     */


                }
                }

        });
//문서 추가 add()를 이용해 id 자동생성 방식
        /*Map<String, Object> adddata = new HashMap<>();
        adddata.put("review", "reviewtextbox");

        db.collection("Review_Data")
                .add(adddata)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
*/
//업데이트
        DocumentReference washingtonRef = db.collection("Review_Data").document("review");

// Set the "isCapital" field of the city 'DC'
        washingtonRef
                .update("capital", true)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
    }

}
