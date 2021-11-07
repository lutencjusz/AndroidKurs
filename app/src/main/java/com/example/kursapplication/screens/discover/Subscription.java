package com.example.kursapplication.screens.discover;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class Subscription {

    public String userId;
    public Long podcastId;

    @SerializedName("ACL")
    public JsonObject acl;

    public Subscription() {

    }
}
