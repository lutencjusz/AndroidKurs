package com.example.kursapplication.screens.register;

import com.example.kursapplication.ErrorResponse;
import com.example.kursapplication.UserStorage;
import com.example.kursapplication.api.PodcastApi;
import com.example.kursapplication.api.RegisterRequest;
import com.example.kursapplication.api.UserResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterManager {

    private RegisterActivity registerActivity;
    private final UserStorage userStorage;
    private final PodcastApi podcastApi;
    private final Retrofit retrofit;
    private Call<UserResponse> userResponseCall;

    public RegisterManager(UserStorage userStorage, PodcastApi podcastApi, Retrofit retrofit) {
        this.userStorage = userStorage;
        this.podcastApi = podcastApi;
        this.retrofit = retrofit;
    }

    public void register(String firstName, String lastName, String email, String password) {

        Dotenv dotenv = Dotenv.configure().directory("./assets").filename("env").load();

        String id = dotenv.get("ID");
        String key = dotenv.get("REST-API-KEY");

        RegisterRequest registerRequest = new RegisterRequest();

        registerRequest.FirstName = firstName;
        registerRequest.LastName = lastName;
        registerRequest.email = email;
        registerRequest.username = email;
        registerRequest.password = password;

        if (userResponseCall == null) {
            podcastApi.postRegister(registerRequest, id, key);
            userResponseCall.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                    if (response.isSuccessful()) {
                        if (registerActivity != null) {
                            registerActivity.registerSuccessFull();
                        }
                    } else {
                        Converter<ResponseBody, ErrorResponse> converter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[]{});
                        try {
                            ErrorResponse errorResponse= converter.convert(response.errorBody());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {

                }
            });
        }

    }
}

