package com.example.kursapplication;

import android.util.Log;
import androidx.annotation.NonNull;
import com.example.kursapplication.api.LoginResponse;
import com.example.kursapplication.api.PodcastApi;
import java.io.IOException;
import java.lang.annotation.Annotation;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserManager {

    private LoginActivity loginActivity;
    private final UserStorage userStorage;
    private Call<LoginResponse> loginCall;

    public UserManager(UserStorage userStorage) {
        this.userStorage = userStorage;
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

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(loggingInterceptor).build();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("https://parseapi.back4app.com/");
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.client(client);
        Retrofit retrofit = builder.build();
        PodcastApi podcastApi = retrofit.create(PodcastApi.class);
        if (loginCall == null) { //zabezpiecznie przed podwójnym logowaniem
            loginCall = podcastApi.getLogin(email, password, id, key);
            updateProgress();
            loginCall.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                    loginCall=null;
                    updateProgress();
                    if (response.isSuccessful()) {
                        LoginResponse body = response.body();
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
                public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
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
