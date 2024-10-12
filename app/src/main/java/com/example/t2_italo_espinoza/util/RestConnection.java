package com.example.t2_italo_espinoza.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestConnection {
    private static final String URL = "https://fakestoreapi.com/";
    private static Retrofit retrofit = null;

    public static Retrofit getConnetion(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
