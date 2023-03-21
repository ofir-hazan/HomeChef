package com.example.homechef.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CountryModel {
    private static final CountryModel instance = new CountryModel();

    public static CountryModel getInstance() {
        return instance;
    }

    final String BASE_URL = "https://restcountries.com/";
    Retrofit retrofit;
    CountryApi countryApi;

    private CountryModel() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        countryApi = retrofit.create(CountryApi.class);
    }

    public LiveData<List<Country>> getAllCountries(){
        MutableLiveData<List<Country>> data = new MutableLiveData<>();
        Call<List<Country>> call = countryApi.getAllCountries();
        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if (response.isSuccessful()){
                    data.setValue(response.body());
                }else{
                    Log.d("TAG","----- response error");
                }
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                Log.d("TAG","----- fail");
            }
        });
        return data;
    }
    public LiveData<Country> getCountryByName(String name){
        MutableLiveData<Country> data = new MutableLiveData<>();
        Call<List<Country>> call = countryApi.getCountryByName(name);
        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if (response.isSuccessful()){
                    List<Country> responseCountries = response.body();

                    if(responseCountries != null && responseCountries.size() > 0){
                        data.setValue(responseCountries.get(0));
                    } else {
                        data.setValue(null);
                    }
                }else{
                    Log.d("TAG","----- response error");
                }
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                Log.d("TAG","----- fail");
                t.printStackTrace();
            }
        });
        return data;
    }

}
