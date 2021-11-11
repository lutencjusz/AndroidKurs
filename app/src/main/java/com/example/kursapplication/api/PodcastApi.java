package com.example.kursapplication.api;

import com.example.kursapplication.screens.discover.Subscription;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PodcastApi {

    @GET("login")
    Call<UserResponse> getLogin(@Query("username") String email, @Query("password") String password);

    @POST("users")
    Call<UserResponse> postRegister(@Body RegisterRequest request);

    @GET("classes/Podcast")
    Call<PodcastResponse> getPodcasts();

    @POST("classes/subscription")
    Call<Subscription> postSubscription(@Body Subscription subscription, @Header("X-Pars-Session-Token") String token);

    @GET("classes/Podcast")
    Call<PodcastResponse> getSubscribePodcast(@Query("where") String where, @Header("X-Pars-Session-Token") String token);
}
