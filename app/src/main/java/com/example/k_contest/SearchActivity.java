package com.example.k_contest;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends Activity {

    private Button search_Button;
    private AutoCompleteTextView Search_name;

    List<String> searchList;

    private ArrayList<String> search_name;

    private ListView Search_List;
    private List_Adapter_Search SearchListAdapter;

    FirebaseFirestore db;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //실행페이지

        Search_List=findViewById(R.id.search_list);
        search_Button=findViewById(R.id.search_Button);
        Search_name=findViewById(R.id.Search_name);
        search_name= new ArrayList<String>();
        db = FirebaseFirestore.getInstance();




        search_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item=Search_name.getText().toString();
                search_name.clear();
                db.collection("nature_data")
                        .whereEqualTo("category_name2",item)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        search_name.add(document.get("data_title",String.class));
                                    }

                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
                db.collection("leisure_data")
                        .whereEqualTo("category_name2",item)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        search_name.add(document.get("data_title",String.class));
                                    }


                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
                db.collection("culture_data")
                        .whereEqualTo("category_name2",item)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        search_name.add(document.get("data_title",String.class));
                                    }
                                    SearchListAdapter=new List_Adapter_Search(SearchActivity.this,search_name);
                                    Search_List.setAdapter(SearchListAdapter);
                                    

                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });
            }
        });

        searchList = new ArrayList<>();
        settingList();
        Search_name.setAdapter(new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, searchList));


    }

    private void settingList() {
        searchList.add("창원시");
        searchList.add("진주시");
        searchList.add("통영시");
        searchList.add("사천시");
        searchList.add("김해시");
        searchList.add("밀양시");
        searchList.add("거제시");
        searchList.add("양산시");
        searchList.add("의령군");
        searchList.add("함안군");
        searchList.add("창녕군");
        searchList.add("고성군");
        searchList.add("남해군");
        searchList.add("하동군");
        searchList.add("산청군");
        searchList.add("함양군");
        searchList.add("거창군");
        searchList.add("합천군");
    }
}
