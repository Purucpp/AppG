package com.appg.BottomNavigation.Home.FruitNinja.Adapter;

import com.google.gson.JsonArray;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface FNApiInterface {


    @POST("/api/process.php?action=show_league")

    Call<JsonArray> req();


}
