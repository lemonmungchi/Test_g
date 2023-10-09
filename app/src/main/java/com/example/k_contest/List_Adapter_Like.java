package com.example.k_contest;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class List_Adapter_Like extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    static ArrayList<String> data;
    FirebaseFirestore db;

    public List_Adapter_Like(Context context, ArrayList<String> data){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.like_list, null);

        TextView Review=view.findViewById(R.id.tour_name);
        Review.setText(data.get(position));

        Button delete_tour=view.findViewById(R.id.delete_tour);

        ArrayList<String> doc_id=new ArrayList<String>();
        db=FirebaseFirestore.getInstance();

        delete_tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item= data.get(position);
                db.collection("like_data")
                        .whereEqualTo("con_name",item)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        doc_id.add(document.get("document_id",String.class));
                                    }
                                    db.collection("like_data").document(doc_id.get(0))
                                            .delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(context,"삭제 성공",Toast.LENGTH_LONG).show();
                                                    Intent intent=new Intent(context,like_l.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |  Intent.FLAG_ACTIVITY_CLEAR_TOP);//액티비티 스택제거
                                                    context.startActivity(intent);
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error deleting document", e);
                                                }
                                            });
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
            }
        });

        return view;
    }
}
