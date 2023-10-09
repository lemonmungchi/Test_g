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

    private ListView review_List;
    private List_Adapter_ReviewM ReviewListAdapter_M;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_manage);

        review=new ArrayList<String>();
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
                                }
                                ReviewListAdapter_M=new List_Adapter_ReviewM(Review_manage.this,review);
                                review_List.setAdapter(ReviewListAdapter_M);
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }

    }
}