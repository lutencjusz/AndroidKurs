package com.example.kursapplication.screens.login;

import android.util.Log;
import androidx.annotation.NonNull;
import com.example.kursapplication.ErrorResponse;
import com.example.kursapplication.UserStorage;
import com.example.kursapplication.api.ErrorConverter;
import com.example.kursapplication.api.UserResponse;
import com.example.kursapplication.api.PodcastApi;
import java.io.IOException;
import java.lang.annotation.Annotation;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginManager {

    private LoginActivity loginActivity;
    private final UserStorage userStorage;
    private final PodcastApi podcastApi;
    private final ErrorConverter converter;
    private Call<UserResponse> loginCall;

    public LoginManager(UserStorage userStorage, PodcastApi podcastApi, ErrorConverter converter) {
        this.userStorage = userStorage;
        this.podcastApi = podcastApi;
        this.converter = converter;
    }

    public void onAttach(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    public void onStop() {
        this.loginActivity = null;
    }

    public void login(String email, String password) {

        if (loginCall == null) { //zabezpiecznie przed podwójnym logowaniem
            loginCall = podcastApi.getLogin(email, password);
            updateProgress();
            loginCall.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                    loginCall = null;
                    updateProgress();
                    if (response.isSuccessful()) {
                        UserResponse body = response.body();
                        assert body != null;
                        userStorage.applyUserResponse(body);
                        Log.d(LoginActivity.class.getSimpleName(), "Odpowiedź: " + body);
                        if (loginActivity != null) {
                            loginActivity.loginSuccess();
                        }
                    } else {
                        ResponseBody responseBody = response.errorBody();
                        ErrorResponse errorResponse = converter.convert(responseBody);

                        if (loginActivity != null && errorResponse != null) {
                            loginActivity.showError("Błąd: " + errorResponse.error);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                    loginCall = null;
                    updateProgress();
                    if (loginActivity != null) {
                        loginActivity.showError("Błąd: " + t.getLocalizedMessage());
                    }
                }
            });
        }
    }

    private void updateProgress() {
        if (loginActivity != null) {
            loginActivity.showProgress(loginCall != null);
        }
    }
}
