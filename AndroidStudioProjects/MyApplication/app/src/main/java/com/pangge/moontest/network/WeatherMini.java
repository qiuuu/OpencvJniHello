package com.pangge.moontest.network;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by iuuu on 17/5/15.
 */

public interface WeatherMini {

    /*@GET("weather_mini")
    Observable<Response<JsonObject>> query(
            @Query("city") String city
    );*/
    @GET("weather_mini")
    Call<JsonObject> query(
            @Query("citykey") String citykey
    );


}
