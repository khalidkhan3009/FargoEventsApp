package com.eventboard.eventboardapp.retrofit;

import com.eventboard.eventboardapp.pojo.Events;
import com.eventboard.eventboardapp.pojo.Speaker;
import com.eventboard.eventboardapp.pojo.Token;
import com.eventboard.eventboardapp.utils.Constant;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Khalid Khan on 03-04-2019.
 */

public interface APIEndpoints {

    @POST("/api/v1/login")
    @FormUrlEncoded
    Call<Token> getToken(@FieldMap Map<String, String> params);

    @GET("/api/v1/events")
    Call<List<Events>> getEvents(@Header(Constant.AUTHORIZATION) String token);

    @GET("/api/v1/events/{id}")
    Call<Events> getEventDetails(@Header(Constant.AUTHORIZATION) String token, @Path("id") int eventId);


    @GET("/api/v1/speakers/{id}")
    Call<Speaker> getSpeakerDetails(@Header(Constant.AUTHORIZATION) String token, @Path("id") int speakerId);

}
