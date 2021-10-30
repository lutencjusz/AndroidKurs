package com.example.kursapplication;

import android.app.Application;

public class App extends Application {

    private UserManager userManager;

    @Override
    public void onCreate() {
        super.onCreate();
        userManager = new UserManager();
    }

    public UserManager getUserManager() {
        return userManager;
    }
}
