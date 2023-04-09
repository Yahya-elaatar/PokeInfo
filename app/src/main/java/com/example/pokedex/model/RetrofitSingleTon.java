package com.example.pokedex.model;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleTon {

    public static Retrofit retrofit = null;

    public static Retrofit getRetrofitInstance() {
        if(retrofit == null){
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl("https://pokeapi.co/api/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
