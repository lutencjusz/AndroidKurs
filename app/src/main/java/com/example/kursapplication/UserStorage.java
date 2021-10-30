package com.example.kursapplication;

import android.content.SharedPreferences;
import com.example.kursapplication.api.LoginResponse;

public class UserStorage {

    public static final String SESSION_TOKEN = "sessionToken";
    public static final String EMAIL = "email";
    public static final String FIRST_NAME = "FirstName";
    public static final String USERNAME = "username";
    public static final String LAST_NAME = "LastName";
    public static final String USER_ID = "userId";
    private final SharedPreferences sharedPreferences;

    public UserStorage(SharedPreferences sharedPreferences) {

        this.sharedPreferences = sharedPreferences;
    }

    public void login(LoginResponse loginResponse) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SESSION_TOKEN, loginResponse.sessionToken);
        editor.putString(EMAIL, loginResponse.email);
        editor.putString(FIRST_NAME, loginResponse.FirstName);
        editor.putString(USERNAME, loginResponse.username);
        editor.putString(LAST_NAME, loginResponse.LastName);
        editor.putString(USER_ID, loginResponse.objectId);

        editor.apply();


    }

    public boolean hasToLogin() {
        return sharedPreferences.getString(SESSION_TOKEN, "").isEmpty();
    }

    public void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
