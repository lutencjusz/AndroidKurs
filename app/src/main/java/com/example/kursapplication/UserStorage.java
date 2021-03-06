package com.example.kursapplication;

import static android.content.ContentValues.TAG;
import android.content.SharedPreferences;
import android.util.Log;
import com.example.kursapplication.api.UserResponse;

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

    public void applyUserResponse(UserResponse userResponse) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SESSION_TOKEN, userResponse.sessionToken);
        editor.putString(EMAIL, userResponse.email);
        editor.putString(FIRST_NAME, userResponse.FirstName);
        editor.putString(USERNAME, userResponse.username);
        editor.putString(LAST_NAME, userResponse.LastName);
        editor.putString(USER_ID, userResponse.objectId);
        editor.apply();
        Log.d(TAG, "zapisano do sharedReferences " + userResponse);

    }

    public boolean hasToLogin() {
        return sharedPreferences.getString(SESSION_TOKEN, "").isEmpty();
    }

    public void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public String getFullName() {
        return String.format("%s %s", sharedPreferences.getString(FIRST_NAME,""), sharedPreferences.getString(LAST_NAME,""));
    }

    public String getEmail(){
        return sharedPreferences.getString(EMAIL,"");
    }

    public String getUserId() {
        return sharedPreferences.getString(USER_ID, "");
    }

    public String getToken() {
        return sharedPreferences.getString(SESSION_TOKEN, "");
    }
}
