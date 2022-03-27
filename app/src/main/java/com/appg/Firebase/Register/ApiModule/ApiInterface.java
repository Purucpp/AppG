package com.appg.Firebase.Register.ApiModule;

import com.google.gson.JsonArray;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {


    @POST("/api/process.php?action=signup")
    @FormUrlEncoded
    Call<ResponseBody> req(@FieldMap Map<String,String> map);
}
