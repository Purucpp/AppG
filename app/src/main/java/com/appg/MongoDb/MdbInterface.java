package com.appg.MongoDb;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface MdbInterface {

    @POST("/")
    Call<ResponseBody> paycal(@Body MdbRbody request);
}
