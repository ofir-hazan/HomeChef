package com.example.homechef;

public class Post {
    public String title, userName, userImg, dishImg, countryName, time;

    public Post(){}

    public Post(String title, String userName, String userImg, String dishImg, String countryName, String time){
        this.title = title;
        this.userName = userName;
        this.userImg = userImg;
        this.dishImg = dishImg;
        this.countryName = countryName;
        this.time = time;
    }
}