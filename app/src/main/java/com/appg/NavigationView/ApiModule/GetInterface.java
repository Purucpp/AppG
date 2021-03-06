package com.appg.NavigationView.ApiModule;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetInterface {

    @POST("/api/process.php?action=show_user")
    @FormUrlEncoded
    Call<JsonArray> req(@FieldMap Map<String,String> map);
}
