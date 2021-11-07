package com.example.kursapplication;

import android.app.Application;
import android.preference.PreferenceManager;
import com.example.kursapplication.api.PodcastApi;
import com.example.kursapplication.screens.discover.DiscoverManager;
import com.example.kursapplication.screens.login.LoginManager;
import com.example.kursapplication.screens.register.RegisterManager;
import com.squareup.otto.Bus;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private LoginManager loginManager;
    private RegisterManager registerManager;
    private UserStorage userStorage;
    private String idDB;
    private String keyDB;
    private Bus bus;
    private DiscoverManager discoverManager;

    @Override
    public void onCreate() {
        super.onCreate();

        Dotenv dotenv = Dotenv.configure().directory("./assets").filename("env").load();

        idDB = dotenv.get("ID");
        keyDB = dotenv.get("REST-API-KEY");

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(loggingInterceptor).build();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("https://parseapi.back4app.com/");
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.client(client);

        Retrofit retrofit = builder.build();
        PodcastApi podcastApi = retrofit.create(PodcastApi.class);
        bus = new Bus();
        userStorage = new UserStorage(PreferenceManager.getDefaultSharedPreferences(this));
        loginManager = new LoginManager(userStorage, podcastApi, retrofit);
        registerManager = new RegisterManager(userStorage, podcastApi, retrofit);
        discoverManager = new DiscoverManager(podcastApi, bus);


    }

    public LoginManager getUserManager() {
        return loginManager;
    }

    public RegisterManager getRegisterManager() {
        return registerManager;
    }

    public UserStorage getUserStorage() {
        return userStorage;
    }

    public String getIdDB() {
        return idDB;
    }

    public String getKeyDB() {
        return keyDB;
    }

    public Bus getBus() {
        return bus;
    }

    public DiscoverManager getDiscoverManager() {
        return discoverManager;
    }
}
