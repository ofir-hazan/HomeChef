package com.example.homechef.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

import com.example.homechef.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

@Entity(tableName = "posts")
public class Post {
    @PrimaryKey
    @NonNull
    private String id;
    public String title, dishImg, countryName;
    public long time;
    @Embedded
    public User user;
    public Long lastUpdated;

    public static final String COLLECTION = "posts";
    public static final String LAST_UPDATED = "lastUpdated";
    public static final String LOCAL_LAST_UPDATED = "posts_local_last_update";

    public Post() {
    }

    public Post(String id, String title, User user, String dishImg, String countryName, long time) {
        this.id = id;
        this.title = title;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public String getDishImg() {
        return dishImg;
    }

    public String getCountryName() {
        return countryName;
    }

    public long getTime() {
        return time;
    }

    @SuppressWarnings("unchecked")
    public static Post fromJson(Map<String, Object> json) {
        String id = (String) json.get("id");
        String title = (String) json.get("title");
        String dishImg = (String) json.get("dishImg");
        String countryName = (String) json.get("countryName");
        Long time = (Long) json.get("time");
        Object userJson = json.get("user");
        User user = new User();

        if (userJson instanceof HashMap) {
            user = User.fromJson((HashMap<String, Object>) userJson);
        }

        Post post = new Post(id, title, user, dishImg, countryName, time != null ? time : 0L);
        try {
            Timestamp newTime = (Timestamp) json.get(LAST_UPDATED);
            if (newTime != null) {
                Post.setLocalLastUpdated(newTime.getSeconds());
            }
        } catch (Exception e) {

        }
        return post;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", getId());
        json.put("user", getUser().toJson());
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

    public static void setLocalLastUpdated(Long newTime) {
        MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE).edit().putLong(LOCAL_LAST_UPDATED, newTime).commit();
    }

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", dishImg='" + dishImg + '\'' +
                ", countryName='" + countryName + '\'' +
                ", time=" + time +
                ", user=" + user +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}
