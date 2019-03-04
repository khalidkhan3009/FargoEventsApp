package com.eventboard.eventboardapp.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Khalid Khan on 03-04-2019.
 */

public class EventsAPI {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://challenge.myriadapps.com";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
    /*@GET("changes/")
    Call<List<Change>> loadChanges(@Query("q") String status);*/

}
