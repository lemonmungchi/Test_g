package com.example.k_contest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private static DataAddress dataaddress;

    private final static String BASE_URL = "https://gyeongnam.openapi.redtable.global/";

    private RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        dataaddress = retrofit.create(DataAddress.class);
    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public static DataAddress getRetrofitAPI() {
        return dataaddress;
    }
}
