package com.example.kursapplication.api;

import androidx.annotation.NonNull;

public class UserResponse {

    public String username;
    public String objectId;
    public String email;
    public String FirstName;
    public String LastName;
    public String sessionToken;

    @NonNull
    @Override
    public String toString() {
        return "LoginResponse{" +
                "username='" + username + '\'' +
                ", objectId='" + objectId + '\'' +
                ", email='" + email + '\'' +
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", sessionToken='" + sessionToken + '\'' +
                '}';
    }
}
