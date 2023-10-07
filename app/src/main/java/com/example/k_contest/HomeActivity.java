package com.example.k_contest;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class HomeActivity extends AppCompatActivity {
    List<String> searchList;
    AutoCompleteTextView StartPoint;
    AutoCompleteTextView EndPoint;
    Button MapPoint;
    ImageButton UserPage;


    RadioButton natureCheck;

    RadioButton leisureCheck;
    RadioButton cultureCheck;


    private Button side_btn_changwon;
    private Button side_btn_jinju;
    private Button side_btn_tongyeong;
    private Button side_btn_sacheon;
    private Button side_btn_gimhae;
    private Button side_btn_miryang;
    private Button side_btn_geoje;
    private Button side_btn_yangsan;
    private Button side_btn_uiryeong;
    private Button side_btn_hamyang;
    private Button side_btn_changnyeong;
    private Button side_btn_goseong;
    private Button side_btn_namhae;
    private Button side_btn_hadong;
    private Button side_btn_sancheong;
    private Button side_btn_haman;
    private Button side_btn_geochang;
    private Button side_btn_hapcheon;

    private ArrayList<String> route_name;
    private ArrayList<String> route_con;
    private ArrayList<String> route_image;
    private FirebaseFirestore db;
    String name;
    String con;
    String image;

    boolean nature_p;

    boolean leisure_p;
    boolean culture_p;

    
    DrawerLayout drawerLayout;

    View drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);        //실행페이지


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        natureCheck=findViewById(R.id.nature_check);
        leisureCheck=findViewById(R.id.leisure_check);
        cultureCheck=findViewById(R.id.culture_check);

        route_name=new ArrayList<String>();
        route_con=new ArrayList<String>();
        route_image=new ArrayList<String>();

        natureCheck=findViewById(R.id.nature_check);
        leisureCheck=findViewById(R.id.leisure_check);
        cultureCheck=findViewById(R.id.culture_check);
        side_btn_changwon=findViewById(R.id.side_btn_changwon);
        side_btn_jinju=findViewById(R.id.side_btn_jinju);
        side_btn_tongyeong=findViewById(R.id.side_btn_tongyeong);
        side_btn_sacheon=findViewById(R.id.side_btn_sacheon);
        side_btn_gimhae=findViewById(R.id.side_btn_gimhae);
        side_btn_miryang=findViewById(R.id.side_btn_miryang);
        side_btn_geoje=findViewById(R.id.side_btn_geoje);
        side_btn_yangsan=findViewById(R.id.side_btn_yangsan);
        side_btn_uiryeong=findViewById(R.id.side_btn_uiryeong);
        side_btn_hamyang=findViewById(R.id.side_btn_hamyang);
        side_btn_hapcheon=findViewById(R.id.side_btn_hapcheon);
        side_btn_changnyeong=findViewById(R.id.side_btn_changnyeong);
        side_btn_goseong=findViewById(R.id.side_btn_goseong);
        side_btn_namhae=findViewById(R.id.side_btn_namhae);
        side_btn_hadong=findViewById(R.id.side_btn_hadong);
        side_btn_sancheong=findViewById(R.id.side_btn_sancheong);
        side_btn_haman=findViewById(R.id.side_btn_haman);
        side_btn_geochang=findViewById(R.id.side_btn_geochang);


        db = FirebaseFirestore.getInstance();

       natureCheck.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               nature_p=true;
               leisure_p=false;
               culture_p=false;
           }
       });

       leisureCheck.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               nature_p=false;
               leisure_p=true;
               culture_p=false;
           }
       });

       cultureCheck.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               nature_p=false;
               leisure_p=false;
               culture_p=true;
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
                    Intent intent = new Intent(getApplicationContext(), SearchActivity.class);//수정
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

            drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);  //드로어 레이아웃 선택을 통해 작동
            drawer = (View)findViewById(R.id.drawer);

            ImageButton openSideBtn = (ImageButton)findViewById(R.id.sideMenuBtn);  // 사이드메뉴 열기 버튼

            openSideBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    drawerLayout.openDrawer(drawer);
                }
            });

            Button closeSideBtn = (Button)findViewById(R.id.closeBtn);  //사이드메뉴 닫기
            closeSideBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    drawerLayout.closeDrawer(drawer);
                }
            });

        side_btn_changwon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = side_btn_changwon.getText().toString();
                route_name.clear();
                route_con.clear();
                route_image.clear();
                db.collection("nature_data")
                        .whereEqualTo("category_name2","창원시")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
                                    }
                                    name=route_name.get((int)(Math.random()*route_name.size()));
                                    con=route_con.get((int)(Math.random()*route_con.size()));
                                    image=route_image.get((int)(Math.random()*route_image.size()));
                                    Intent intent =new Intent(getApplicationContext(),City_Page_Activity.class);
                                    if(name.length()>0 && con.length()>0 ) {
                                        name = name.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
                                        name = name.replaceAll("<[^>]*>", " ");
                                        intent.putExtra("fileurl1", image);
                                        intent.putExtra("data_content", con);
                                        intent.putExtra("name", name);
                                        startActivity(intent);

                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });



            }
        });
        side_btn_jinju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                route_name.clear();
                route_con.clear();
                route_image.clear();
                String item = side_btn_jinju.getText().toString();
                db.collection("nature_data")
                        .whereEqualTo("category_name2",item)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
                                    }
                                    name=route_name.get((int)(Math.random()*route_name.size()));
                                    con=route_con.get((int)(Math.random()*route_con.size()));
                                    image=route_image.get((int)(Math.random()*route_image.size()));
                                    Intent intent =new Intent(getApplicationContext(),City_Page_Activity.class);
                                    if(name.length()>0 && con.length()>0 ) {
                                        name = name.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
                                        name = name.replaceAll("<[^>]*>", " ");
                                        intent.putExtra("fileurl1", image);
                                        intent.putExtra("data_content", con);
                                        intent.putExtra("name", name);
                                        startActivity(intent);

                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });
        side_btn_tongyeong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                route_name.clear();
                route_con.clear();
                route_image.clear();
                String item = side_btn_tongyeong.getText().toString();
                db.collection("nature_data")
                        .whereEqualTo("category_name2",item)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
                                    }
                                    name=route_name.get((int)(Math.random()*route_name.size()));
                                    con=route_con.get((int)(Math.random()*route_con.size()));
                                    image=route_image.get((int)(Math.random()*route_image.size()));
                                    Intent intent =new Intent(getApplicationContext(),City_Page_Activity.class);
                                    if(name.length()>0 && con.length()>0 ) {
                                        name = name.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
                                        name = name.replaceAll("<[^>]*>", " ");
                                        intent.putExtra("fileurl1", image);
                                        intent.putExtra("data_content", con);
                                        intent.putExtra("name", name);
                                        startActivity(intent);

                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });
        side_btn_sacheon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                route_name.clear();
                route_con.clear();
                route_image.clear();
                String item = side_btn_sacheon.getText().toString();
                db.collection("nature_data")
                        .whereEqualTo("category_name2",item)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
                                    }
                                    name=route_name.get((int)(Math.random()*route_name.size()));
                                    con=route_con.get((int)(Math.random()*route_con.size()));
                                    image=route_image.get((int)(Math.random()*route_image.size()));
                                    Intent intent =new Intent(getApplicationContext(),City_Page_Activity.class);
                                    if(name.length()>0 && con.length()>0 ) {
                                        name = name.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
                                        name = name.replaceAll("<[^>]*>", " ");
                                        intent.putExtra("fileurl1", image);
                                        intent.putExtra("data_content", con);
                                        intent.putExtra("name", name);
                                        startActivity(intent);

                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });
        side_btn_gimhae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                route_name.clear();
                route_con.clear();
                route_image.clear();
                String item = side_btn_gimhae.getText().toString();
                db.collection("nature_data")
                        .whereEqualTo("category_name2",item)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
                                    }
                                    name=route_name.get((int)(Math.random()*route_name.size()));
                                    con=route_con.get((int)(Math.random()*route_con.size()));
                                    image=route_image.get((int)(Math.random()*route_image.size()));
                                    Intent intent =new Intent(getApplicationContext(),City_Page_Activity.class);
                                    if(name.length()>0 && con.length()>0 ) {
                                        name = name.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
                                        name = name.replaceAll("<[^>]*>", " ");
                                        intent.putExtra("fileurl1", image);
                                        intent.putExtra("data_content", con);
                                        intent.putExtra("name", name);
                                        startActivity(intent);

                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });
        side_btn_miryang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                route_name.clear();
                route_con.clear();
                route_image.clear();
                String item = side_btn_miryang.getText().toString();
                db.collection("nature_data")
                        .whereEqualTo("category_name2",item)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
                                    }
                                    name=route_name.get((int)(Math.random()*route_name.size()));
                                    con=route_con.get((int)(Math.random()*route_con.size()));
                                    image=route_image.get((int)(Math.random()*route_image.size()));
                                    Intent intent =new Intent(getApplicationContext(),City_Page_Activity.class);
                                    if(name.length()>0 && con.length()>0 ) {
                                        name = name.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
                                        name = name.replaceAll("<[^>]*>", " ");
                                        intent.putExtra("fileurl1", image);
                                        intent.putExtra("data_content", con);
                                        intent.putExtra("name", name);
                                        startActivity(intent);

                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });
        side_btn_geoje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                route_name.clear();
                route_con.clear();
                route_image.clear();
                String item = side_btn_geoje.getText().toString();
                db.collection("nature_data")
                        .whereEqualTo("category_name2",item)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
                                    }
                                    name=route_name.get((int)(Math.random()*route_name.size()));
                                    con=route_con.get((int)(Math.random()*route_con.size()));
                                    image=route_image.get((int)(Math.random()*route_image.size()));
                                    Intent intent =new Intent(getApplicationContext(),City_Page_Activity.class);
                                    if(name.length()>0 && con.length()>0 ) {
                                        name = name.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
                                        name = name.replaceAll("<[^>]*>", " ");
                                        intent.putExtra("fileurl1", image);
                                        intent.putExtra("data_content", con);
                                        intent.putExtra("name", name);
                                        startActivity(intent);

                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });
        side_btn_yangsan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                route_name.clear();
                route_con.clear();
                route_image.clear();
                String item = side_btn_yangsan.getText().toString();
                db.collection("nature_data")
                        .whereEqualTo("category_name2",item)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
                                    }
                                    name=route_name.get((int)(Math.random()*route_name.size()));
                                    con=route_con.get((int)(Math.random()*route_con.size()));
                                    image=route_image.get((int)(Math.random()*route_image.size()));
                                    Intent intent =new Intent(getApplicationContext(),City_Page_Activity.class);
                                    if(name.length()>0 && con.length()>0 ) {
                                        name = name.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
                                        name = name.replaceAll("<[^>]*>", " ");
                                        intent.putExtra("fileurl1", image);
                                        intent.putExtra("data_content", con);
                                        intent.putExtra("name", name);
                                        startActivity(intent);

                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });
        side_btn_uiryeong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                route_name.clear();
                route_con.clear();
                route_image.clear();
                String item = side_btn_uiryeong.getText().toString();
                db.collection("nature_data")
                        .whereEqualTo("category_name2",item)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
                                    }
                                    name=route_name.get((int)(Math.random()*route_name.size()));
                                    con=route_con.get((int)(Math.random()*route_con.size()));
                                    image=route_image.get((int)(Math.random()*route_image.size()));
                                    Intent intent =new Intent(getApplicationContext(),City_Page_Activity.class);
                                    if(name.length()>0 && con.length()>0 ) {
                                        name = name.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
                                        name = name.replaceAll("<[^>]*>", " ");
                                        intent.putExtra("fileurl1", image);
                                        intent.putExtra("data_content", con);
                                        intent.putExtra("name", name);
                                        startActivity(intent);

                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });
        side_btn_hamyang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                route_name.clear();
                route_con.clear();
                route_image.clear();
                String item = side_btn_hamyang.getText().toString();
                db.collection("nature_data")
                        .whereEqualTo("category_name2",item)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
                                    }
                                    name=route_name.get((int)(Math.random()*route_name.size()));
                                    con=route_con.get((int)(Math.random()*route_con.size()));
                                    image=route_image.get((int)(Math.random()*route_image.size()));
                                    Intent intent =new Intent(getApplicationContext(),City_Page_Activity.class);
                                    if(name.length()>0 && con.length()>0 ) {
                                        name = name.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
                                        name = name.replaceAll("<[^>]*>", " ");
                                        intent.putExtra("fileurl1", image);
                                        intent.putExtra("data_content", con);
                                        intent.putExtra("name", name);
                                        startActivity(intent);

                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });
        side_btn_changnyeong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                route_name.clear();
                route_con.clear();
                route_image.clear();
                String item = side_btn_changnyeong.getText().toString();
                db.collection("nature_data")
                        .whereEqualTo("category_name2",item)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
                                    }
                                    name=route_name.get((int)(Math.random()*route_name.size()));
                                    con=route_con.get((int)(Math.random()*route_con.size()));
                                    image=route_image.get((int)(Math.random()*route_image.size()));
                                    Intent intent =new Intent(getApplicationContext(),City_Page_Activity.class);
                                    if(name.length()>0 && con.length()>0 ) {
                                        name = name.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
                                        name = name.replaceAll("<[^>]*>", " ");
                                        intent.putExtra("fileurl1", image);
                                        intent.putExtra("data_content", con);
                                        intent.putExtra("name", name);
                                        startActivity(intent);

                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });
        side_btn_goseong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                route_name.clear();
                route_con.clear();
                route_image.clear();
                String item = side_btn_goseong.getText().toString();
                db.collection("nature_data")
                        .whereEqualTo("category_name2",item)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
                                    }
                                    name=route_name.get((int)(Math.random()*route_name.size()));
                                    con=route_con.get((int)(Math.random()*route_con.size()));
                                    image=route_image.get((int)(Math.random()*route_image.size()));
                                    Intent intent =new Intent(getApplicationContext(),City_Page_Activity.class);
                                    if(name.length()>0 && con.length()>0 ) {
                                        name = name.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
                                        name = name.replaceAll("<[^>]*>", " ");
                                        intent.putExtra("fileurl1", image);
                                        intent.putExtra("data_content", con);
                                        intent.putExtra("name", name);
                                        startActivity(intent);

                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });
        side_btn_namhae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                route_name.clear();
                route_con.clear();
                route_image.clear();
                String item = side_btn_namhae.getText().toString();
                db.collection("nature_data")
                        .whereEqualTo("category_name2",item)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
                                    }
                                    name=route_name.get((int)(Math.random()*route_name.size()));
                                    con=route_con.get((int)(Math.random()*route_con.size()));
                                    image=route_image.get((int)(Math.random()*route_image.size()));
                                    Intent intent =new Intent(getApplicationContext(),City_Page_Activity.class);
                                    if(name.length()>0 && con.length()>0 ) {
                                        name = name.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
                                        name = name.replaceAll("<[^>]*>", " ");
                                        intent.putExtra("fileurl1", image);
                                        intent.putExtra("data_content", con);
                                        intent.putExtra("name", name);
                                        startActivity(intent);

                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });
        side_btn_hadong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                route_name.clear();
                route_con.clear();
                route_image.clear();
                String item = side_btn_hadong.getText().toString();
                db.collection("nature_data")
                        .whereEqualTo("category_name2",item)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
                                    }
                                    name=route_name.get((int)(Math.random()*route_name.size()));
                                    con=route_con.get((int)(Math.random()*route_con.size()));
                                    image=route_image.get((int)(Math.random()*route_image.size()));
                                    Intent intent =new Intent(getApplicationContext(),City_Page_Activity.class);
                                    if(name.length()>0 && con.length()>0 ) {
                                        name = name.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
                                        name = name.replaceAll("<[^>]*>", " ");
                                        intent.putExtra("fileurl1", image);
                                        intent.putExtra("data_content", con);
                                        intent.putExtra("name", name);
                                        startActivity(intent);

                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });
        side_btn_sancheong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                route_name.clear();
                route_con.clear();
                route_image.clear();
                String item = side_btn_sancheong.getText().toString();
                db.collection("nature_data")
                        .whereEqualTo("category_name2",item)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
                                    }
                                    name=route_name.get((int)(Math.random()*route_name.size()));
                                    con=route_con.get((int)(Math.random()*route_con.size()));
                                    image=route_image.get((int)(Math.random()*route_image.size()));
                                    Intent intent =new Intent(getApplicationContext(),City_Page_Activity.class);
                                    if(name.length()>0 && con.length()>0 ) {
                                        name = name.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
                                        name = name.replaceAll("<[^>]*>", " ");
                                        intent.putExtra("fileurl1", image);
                                        intent.putExtra("data_content", con);
                                        intent.putExtra("name", name);
                                        startActivity(intent);

                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });
        side_btn_haman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                route_name.clear();
                route_con.clear();
                route_image.clear();
                String item = side_btn_haman.getText().toString();
                db.collection("nature_data")
                        .whereEqualTo("category_name2",item)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
                                    }
                                    name=route_name.get((int)(Math.random()*route_name.size()));
                                    con=route_con.get((int)(Math.random()*route_con.size()));
                                    image=route_image.get((int)(Math.random()*route_image.size()));
                                    Intent intent =new Intent(getApplicationContext(),City_Page_Activity.class);
                                    if(name.length()>0 && con.length()>0 ) {
                                        name = name.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
                                        name = name.replaceAll("<[^>]*>", " ");
                                        intent.putExtra("fileurl1", image);
                                        intent.putExtra("data_content", con);
                                        intent.putExtra("name", name);
                                        startActivity(intent);

                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });
        side_btn_geochang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                route_name.clear();
                route_con.clear();
                route_image.clear();
                String item = side_btn_geochang.getText().toString();
                db.collection("nature_data")
                        .whereEqualTo("category_name2",item)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
                                    }
                                    name=route_name.get((int)(Math.random()*route_name.size()));
                                    con=route_con.get((int)(Math.random()*route_con.size()));
                                    image=route_image.get((int)(Math.random()*route_image.size()));
                                    Intent intent =new Intent(getApplicationContext(),City_Page_Activity.class);
                                    if(name.length()>0 && con.length()>0 ) {
                                        name = name.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
                                        name = name.replaceAll("<[^>]*>", " ");
                                        intent.putExtra("fileurl1", image);
                                        intent.putExtra("data_content", con);
                                        intent.putExtra("name", name);
                                        startActivity(intent);

                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });
        side_btn_hapcheon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                route_name.clear();
                route_con.clear();
                route_image.clear();
                String item = side_btn_hapcheon.getText().toString();
                db.collection("nature_data")
                        .whereEqualTo("category_name2",item)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
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
                                        route_name.add(document.get("data_title",String.class));
                                        route_con.add(document.get("data_content", String.class));
                                        route_image.add(document.get("fileurl1",String.class));
                                    }
                                    name=route_name.get((int)(Math.random()*route_name.size()));
                                    con=route_con.get((int)(Math.random()*route_con.size()));
                                    image=route_image.get((int)(Math.random()*route_image.size()));
                                    Intent intent =new Intent(getApplicationContext(),City_Page_Activity.class);
                                    if(name.length()>0 && con.length()>0 ) {
                                        name = name.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
                                        name = name.replaceAll("<[^>]*>", " ");
                                        intent.putExtra("fileurl1", image);
                                        intent.putExtra("data_content", con);
                                        intent.putExtra("name", name);
                                        startActivity(intent);

                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });



        searchList = new ArrayList<>();
            settingList();
            StartPoint = findViewById(R.id.StartPoint);
            StartPoint.setAdapter(new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, searchList));

            EndPoint = findViewById(R.id.EndPoint);
            EndPoint.setAdapter(new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, searchList));

            MapPoint = findViewById(R.id.MapPoint);
            MapPoint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean [] cur_state={nature_p,leisure_p,culture_p};
                    Intent intent = new Intent(getApplicationContext(), Route_choose.class);
                    intent.putExtra("Start", StartPoint.getText().toString());
                    intent.putExtra("End", EndPoint.getText().toString());
                    intent.putExtra("State",cur_state);
                    startActivity(intent);
                }
            });


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