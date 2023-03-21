package com.example.homechef.model;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;

public class Country {
    String name;
    @SerializedName("alpha2Code")
    String code;
    @SerializedName("flags")
    Flag flag;

    public String getName() {
        return name;
    }

    public String getHebrewName() {
        return this.getForeignName("he");
    }

    public String getForeignName(String foreignLanguage) {
        Locale country = new Locale.Builder().setRegion(this.getCode()).build();
        Locale hebrewLanguage = new Locale.Builder().setLanguage(foreignLanguage).build();

        return country.getDisplayCountry(hebrewLanguage);
    }

    public String getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Flag getFlag() {
        return flag;
    }

    public void setFlag(Flag flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", flag=" + flag +
                '}';
    }
}
