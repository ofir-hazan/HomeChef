package com.example.homechef.model;

import com.google.gson.annotations.SerializedName;

public class Flag {
    @SerializedName("svg")
    String imageUrlSvg;
    @SerializedName("png")
    String imageUrlPng;

    public String getImageUrlSvg() {
        return imageUrlSvg;
    }

    public void setImageUrlSvg(String imageUrlSvg) {
        this.imageUrlSvg = imageUrlSvg;
    }

    public String getImageUrlPng() {
        return imageUrlPng;
    }

    public void setImageUrlPng(String imageUrlPng) {
        this.imageUrlPng = imageUrlPng;
    }

    @Override
    public String toString() {
        return "Flag{" +
                "imageUrlSvg='" + imageUrlSvg + '\'' +
                ", imageUrlPng='" + imageUrlPng + '\'' +
                '}';
    }
}
