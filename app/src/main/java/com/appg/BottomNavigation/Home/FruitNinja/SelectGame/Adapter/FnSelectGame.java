package com.appg.BottomNavigation.Home.FruitNinja.SelectGame.Adapter;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.POST;

public interface FnSelectGame {

    @POST("/api/process.php?action=show_all_game")
    Call<JsonArray> req();
}
