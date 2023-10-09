package com.example.k_contest;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class like_l extends AppCompatActivity {

    private ArrayList<String> like;

    private ListView like_List;
    private List_Adapter_Like LikeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_l);

        like=new ArrayList<String>();
        like_List=findViewById(R.id.like_list);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String email=user.getEmail();

        if(user==null){
            Toast.makeText(getApplicationContext(),"로그인을 하세요",Toast.LENGTH_LONG).show();
            Intent go_intent=new Intent(like_l.this, Login.class);
            startActivity(go_intent);
        }else {
            db.collection("like_data")
                    .whereEqualTo("user_email",email)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {

                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    like.add(document.get("con_name",String.class));
                                }
                                LikeListAdapter=new List_Adapter_Like(like_l.this,like);
                                like_List.setAdapter(LikeListAdapter);
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
    }
}