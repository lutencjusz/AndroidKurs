package com.example.kursapplication.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface PodcastApi {

    @Headers({
            "X-Parse-Application-Id: u8WaDk6brOK76V9QHPO0vYtT93kbhynb8Jaa0xle",
            "X-Parse-REST-API-Key: Oq5mxXiRpUL2craFNg17hNVDCpK5smVwVcFir3MA",
            "X-Parse-Revocable-Session: 1"
    })
    @GET("login")
    Call<LoginResponse> getLogin(@Query("username") String email, @Query("password") String password);

}
