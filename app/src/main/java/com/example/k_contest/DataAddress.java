package com.example.k_contest;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DataAddress {
    @GET("api/rstr")
    Call<DataPath> getData(@Query("serviceKey") String serviceKey, @Query("pageNo") int pageNo);
}
