package com.example.homechef.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CountryApi {
    @GET("/v2/all")
    Call<List<Country>> getAllCountries();

    @GET("/v2/name/{name}")
    Call<List<Country>> getCountryByName(@Path("name") String name);
}