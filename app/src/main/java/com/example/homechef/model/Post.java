package com.example.homechef.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

import com.example.homechef.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;
@Entity
public class Post {
    @PrimaryKey
    @NonNull
    private String id;
    public String title, userName, userImg, dishImg, countryName;
    public int time;
    public Long lastUpdated;

    public static final String COLLECTION = "posts";
    public static final String LAST_UPDATED = "lastUpdated";
    public static final String LOCAL_LAST_UPDATED = "posts_local_last_update";

    public Post(){}

    public Post(String id, String title, String userName, String userImg, String dishImg, String countryName, int time){
        this.id = id;
        this.title = title;
        this.userName = userName;
        this.userImg = userImg;
        this.dishImg = dishImg;
        this.countryName = countryName;
        this.time = time;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserImg() {
        return userImg;
    }

    public String getDishImg() {
        return dishImg;
    }

    public String getCountryName() {
        return countryName;
    }

    public int getTime() {
        return time;
    }

    public static Post fromJson(Map<String,Object> json){
        String id = (String)json.get("id");
        String title = (String)json.get("title");
        String userName = (String)json.get("userName");
        String userImg = (String)json.get("userImg");
        String dishImg = (String)json.get("dishImg");
        String countryName = (String)json.get("countryName");
        int time = (int)json.get("time");
        Post post = new Post(id,title,userName,userImg,dishImg,countryName,time);
        try{
            Timestamp newTime = (Timestamp) json.get(LAST_UPDATED);
            post.setLocalLastUpdated(newTime.getSeconds());
        }catch(Exception e){

        }
        return post;
    }

    public Map<String,Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put("id", getId());
        json.put("userName", getUserName());
        json.put("userImg", getUserImg());
        json.put("dishImg", getDishImg());
        json.put("countryName", getCountryName());
        json.put("time", getTime());
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        return json;
    }

    public static Long getLocalLastUpdate() {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        return sharedPref.getLong(LOCAL_LAST_UPDATED, 0);
    }

    public static void setLocalLastUpdated(Long newTime){
        MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE).edit().putLong(LOCAL_LAST_UPDATED,newTime).commit();
    }
}
