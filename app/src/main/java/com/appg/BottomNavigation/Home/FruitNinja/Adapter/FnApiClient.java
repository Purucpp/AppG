package com.appg.BottomNavigation.Home.FruitNinja.Adapter;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FnApiClient {


    public static final String Base_url="https://appg.in";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
