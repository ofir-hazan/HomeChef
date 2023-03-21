package com.example.homechef.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

@Entity(tableName = "users")
public class User {
    @PrimaryKey
    @NonNull
    public String email;
    public String userName;
    public String avatarUrl;

    public User() {
        this.email = "";
        this.userName = "";
        this.avatarUrl = "";
    }

    public User(@NonNull String email, String userName, String userImg ) {
        this.email = email;
        this.avatarUrl = userImg;
        this.userName = userName;
    }

    static final String USERNAME = "userName";
    static final String EMAIL = "email";
    static final String AVATARURL = "avatarUrl";
    static final String COLLECTION = "users";

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put(EMAIL, getEmail());
        json.put(USERNAME, getUserName());
        json.put(AVATARURL, getAvatarUrl());
        return json;
    }

    public static User fromJson(Map<String, Object> json) {
        String email = (String) json.get(EMAIL);
        String userName = (String) json.get(USERNAME);
        String avatarUrl = (String) json.get(AVATARURL);
        User user = new User(email, userName, avatarUrl);
        return user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
