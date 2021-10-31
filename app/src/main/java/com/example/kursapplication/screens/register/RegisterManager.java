package com.example.kursapplication.screens.register;

import androidx.annotation.NonNull;
import com.example.kursapplication.ErrorResponse;
import com.example.kursapplication.UserStorage;
import com.example.kursapplication.api.PodcastApi;
import com.example.kursapplication.api.RegisterRequest;
import com.example.kursapplication.api.UserResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
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

    public void onAttach(RegisterActivity registerActivity) {
        this.registerActivity = registerActivity;
        updateProgress();
    }

    public void onStop() {
        this.registerActivity = null;
    }

    public void register(String firstName, String lastName, String email, String password, String idDB, String keyDB) {

        final RegisterRequest registerRequest = new RegisterRequest();

        registerRequest.FirstName = firstName;
        registerRequest.LastName = lastName;
        registerRequest.email = email;
        registerRequest.username = email;
        registerRequest.password = password;

        if (userResponseCall == null) {
            userResponseCall = podcastApi.postRegister(registerRequest, idDB, keyDB);
            updateProgress();
            userResponseCall.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                    userResponseCall = null;
                    updateProgress();
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        UserResponse body = response.body();
                        body.FirstName = firstName;
                        body.LastName = lastName;
                        body.email = email;
                        body.username = email;
                        userStorage.applyUserResponse(body);
                        if (registerActivity != null) {
                            registerActivity.registerSuccessFull();
                        }
                    } else {
                        Converter<ResponseBody, ErrorResponse> converter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[]{});
                        try {
                            assert response.errorBody() != null;
                            ErrorResponse errorResponse= converter.convert(response.errorBody());
                            if (registerActivity == null){
                                registerActivity.showError(errorResponse.error);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }

                @Override
                public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                    userResponseCall = null;
                    updateProgress();
                    if (registerActivity == null){
                        registerActivity.showError(t.getLocalizedMessage());
                    }
                }
            });
        }
    }
    private void updateProgress(){
        if (registerActivity != null){
            registerActivity.showProgress(userResponseCall != null);
        }
    }
}

