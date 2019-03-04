package com.eventboard.eventboardapp.retrofit;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Khalid Khan on 03-04-2019.
 */

public interface APIEndpoints {

    @POST("/api/v1/login")
    @FormUrlEncoded
    Call<Token> getToken(@FieldMap Map<String, String> params);
}
