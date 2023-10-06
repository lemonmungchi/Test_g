package com.example.k_contest;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.k_contest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class List_Adapter_Route extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    static ArrayList<String> data;

    ArrayList<String> fileurl;

    ArrayList<String> data_con;

    String fileurl1;

    String data_content;

    public List_Adapter_Route(Context context, ArrayList<String> data){
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
        View view = layoutInflater.inflate(R.layout.route_list, null);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        fileurl=new ArrayList<String>();
        data_con=new ArrayList<String>();
        TextView title=view.findViewById(R.id.title);
        title.setText(data.get(position));

        Button inform_b=view.findViewById(R.id.inform_b);
        inform_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String i=title.getText().toString();
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
                                    fileurl1=fileurl.get(0);
                                    data_content=data_con.get(0);
                                    Intent intent=new Intent(context, City_Page_Activity.class);
                                    if(data_content.length()>0 && fileurl1.length()>0 ){
                                        data_content=data_content.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
                                        data_content=data_content.replaceAll("<[^>]*>", " ");
                                        intent.putExtra("fileurl1",fileurl1);
                                        intent.putExtra("data_content",data_content);
                                        intent.putExtra("name",i);
                                        context.startActivity(intent);
                                    }else {
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
                                                            fileurl1=fileurl.get(0);
                                                            data_content=data_con.get(0);
                                                            Intent intent=new Intent(context, City_Page_Activity.class);
                                                            if(data_content.length()>0 && fileurl1.length()>0 ){
                                                                data_content=data_content.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
                                                                data_content=data_content.replaceAll("<[^>]*>", " ");
                                                                intent.putExtra("fileurl1",fileurl1);
                                                                intent.putExtra("data_content",data_content);
                                                                intent.putExtra("name",i);
                                                                context.startActivity(intent);
                                                            }else {
                                                                db.collection("leisure_data")
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
                                                                                    fileurl1=fileurl.get(0);
                                                                                    data_content=data_con.get(0);
                                                                                    Intent intent=new Intent(context, City_Page_Activity.class);
                                                                                    if(data_content.length()>0 && fileurl1.length()>0 ){
                                                                                        data_content=data_content.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
                                                                                        data_content=data_content.replaceAll("<[^>]*>", " ");
                                                                                        intent.putExtra("fileurl1",fileurl1);
                                                                                        intent.putExtra("data_content",data_content);
                                                                                        intent.putExtra("name",i);
                                                                                        context.startActivity(intent);
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

        return view;
    }
}
