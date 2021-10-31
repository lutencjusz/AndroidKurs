package com.example.kursapplication.screens.login;

import android.util.Log;
import androidx.annotation.NonNull;
import com.example.kursapplication.ErrorResponse;
import com.example.kursapplication.UserStorage;
import com.example.kursapplication.api.UserResponse;
import com.example.kursapplication.api.PodcastApi;
import java.io.IOException;
import java.lang.annotation.Annotation;
import io.github.cdimascio.dotenv.Dotenv;
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
    private final Retrofit retrofit;
    private Call<UserResponse> loginCall;

    public LoginManager(UserStorage userStorage, PodcastApi podcastApi ,Retrofit retrofit) {
        this.userStorage = userStorage;
        this.podcastApi = podcastApi;
        this.retrofit = retrofit;
    }

    public void onAttach(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    public void onStop() {
        this.loginActivity = null;
    }


    public void login(String email, String password) {

        Dotenv dotenv = Dotenv.configure().directory("./assets").filename("env").load();

        String id = dotenv.get("ID");
        String key = dotenv.get("REST-API-KEY");

        if (loginCall == null) { //zabezpiecznie przed podwójnym logowaniem
            loginCall = podcastApi.getLogin(email, password, id, key);
            updateProgress();
            loginCall.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                    loginCall=null;
                    updateProgress();
                    if (response.isSuccessful()) {
                        UserResponse body = response.body();
                        assert body != null;
                        userStorage.login(body);
                        Log.d(LoginActivity.class.getSimpleName(), "Odpowiedź: " + body);
                        if (loginActivity != null) {
                            loginActivity.loginSuccess();
                        }
                    } else {
                        try {
                            ResponseBody responseBody = response.errorBody();
                            Converter<ResponseBody, ErrorResponse> converter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[]{});
                            assert responseBody != null;
                            ErrorResponse errorResponse = converter.convert(responseBody);
                            assert errorResponse != null;
                            if (loginActivity != null) {
                                loginActivity.showError("Błąd: " + errorResponse.error);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
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
        if (loginActivity != null){
            loginActivity.showProgress(loginCall != null);
        }
    }
}
