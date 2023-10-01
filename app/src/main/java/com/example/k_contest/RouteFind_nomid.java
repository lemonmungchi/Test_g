package com.example.k_contest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface RouteFind_nomid {
    @GET("v1/driving")
    Call<RoutePath> getData(@retrofit2.http.Header("X-NCP-APIGW-API-KEY-ID")String ClientID,
                            @Header("X-NCP-APIGW-API-KEY") String Secret,
                            @Query("start") String start, @Query("goal") String goal)
                            ;
}
