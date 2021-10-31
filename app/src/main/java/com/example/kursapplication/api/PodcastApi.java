package com.example.kursapplication.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PodcastApi {

    @Headers({
            "X-Parse-Revocable-Session: 1"
    })
    @GET("login")
    Call<UserResponse> getLogin(@Query("username") String email, @Query("password") String password, @Header("X-Parse-Application-Id") String id, @Header("X-Parse-REST-API-Key") String key);

    @Headers({
            "X-Parse-Revocable-Session: 1"
    })
    @POST("users")
    Call<UserResponse> postRegister (@Body RegisterRequest request, @Header("X-Parse-Application-Id") String id, @Header("X-Parse-REST-API-Key") String key);

}
