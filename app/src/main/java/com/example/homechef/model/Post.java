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
@Entity(tableName = "posts")
public class Post {
    @PrimaryKey
    @NonNull
    private String id;

    public String title, dishPic, countryName, userId;
    public Long time;
    public Long lastUpdated;

    public static final String COLLECTION = "posts";
    public static final String LAST_UPDATED = "lastUpdated";
    public static final String LOCAL_LAST_UPDATED = "posts_local_last_update";

    public Post(){}

    public Post(String id, String title, String dishPic, String countryName, Long time){
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.dishPic = dishPic;
        this.countryName = countryName;
        this.time = time;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getDishPic() {
        return dishPic;
    }

    public String getCountryName() {
        return countryName;
    }

    public Long getTime() {
        return time;
    }

    public static Post fromJson(Map<String,Object> json){
        String id = (String)json.get("id");
        String title = (String)json.get("title");
        String dishImg = (String)json.get("dishImg");
        String countryName = (String)json.get("countryName");
        Long time = (Long) json.get("time");
        Post post = new Post(id,title,dishImg,countryName,time);
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
        json.put("dishImg", getDishPic());
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

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", dishImg='" + dishPic + '\'' +
                ", countryName='" + countryName + '\'' +
                ", userId='" + userId + '\'' +
                ", time=" + time +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}
