package com.example.kursapplication;

import android.app.Application;
import android.preference.PreferenceManager;
import com.example.kursapplication.api.PodcastApi;
import com.example.kursapplication.screens.login.LoginManager;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private LoginManager loginManager;
    private UserStorage userStorage;
    private PodcastApi podcastApi;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(loggingInterceptor).build();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("https://parseapi.back4app.com/");
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.client(client);
        retrofit = builder.build();
        podcastApi = retrofit.create(PodcastApi.class);

        userStorage = new UserStorage(PreferenceManager.getDefaultSharedPreferences(this));
        loginManager = new LoginManager(userStorage, podcastApi, retrofit);
    }

    public LoginManager getUserManager() {
        return loginManager;
    }

    public UserStorage getUserStorage() {
        return userStorage;
    }

    public PodcastApi getPodcastApi() {
        return podcastApi;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
