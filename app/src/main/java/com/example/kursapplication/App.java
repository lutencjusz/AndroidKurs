package com.example.kursapplication;

import android.app.Application;
import android.preference.PreferenceManager;

public class App extends Application {

    private UserManager userManager;
    private UserStorage userStorage;

    @Override
    public void onCreate() {
        super.onCreate();
        userStorage = new UserStorage(PreferenceManager.getDefaultSharedPreferences(this));
        userManager = new UserManager(userStorage);
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public UserStorage getUserStorage() {
        return userStorage;
    }
}
