package com.appg.MongoDb;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MdbClient {


    public static final String Base_url="https://pk-mongodb.herokuapp.com";

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
