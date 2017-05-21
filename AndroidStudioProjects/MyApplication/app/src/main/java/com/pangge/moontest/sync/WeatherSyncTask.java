package com.pangge.moontest.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.pangge.moontest.MainActivity;
import com.pangge.moontest.Weather;
import com.pangge.moontest.WeatherContentProvider;

import com.pangge.moontest.data.WeatherPreferences;
import com.pangge.moontest.network.WeatherMini;
import com.pangge.moontest.utilities.OpenWeatherJsonUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by iuuu on 17/5/16.
 * //  TODO (1) Create a class called WeatherSyncTask
 -//  TODO (2) Within WeatherSyncTask, create a synchronized public static void method called syncWeather
 -//  TODO (3) Within syncWeather, fetch new weather data
 -// TODO (4) If we have valid results, delete the old data and insert the new
 */

public class WeatherSyncTask {
    /**
     +     * Performs the network request for updated weather, parses the JSON from that request, and
     +     * inserts the new weather information into our ContentProvider. Will notify the user that new
     +     * weather has been loaded if the user hasn't been notified of the weather within the last day
     +     * AND they haven't disabled notifications in the preferences screen.
     +     *
     +     * @param context Used to access utility methods and the ContentResolver
     +     */
    private static final String BASE_URL = "http://wthrcdn.etouch.cn/";
    //private static CompositeDisposable compositeDisposable;;


    //synchronized
    public static void syncWeather(Context context){
        //compositeDisposable = new CompositeDisposable();;
        try {
            //default location 北京
            String location = WeatherPreferences.getPreferredWeatherLocation(context);

            Log.i(location,"---------------------");
            WeatherMini weatherMini = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                   // .addCallAdapterFactory()
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(WeatherMini.class);
            /***
             * activity && fragment
             */
            JsonObject body = weatherMini.query(location).execute().body();
           // weatherMini.query(location).headers().

           // Log.i("ddd---",body.get("data")).toString();
            /* Parse the JSON into a list of weather values*/

            ContentValues[] weatherValues = OpenWeatherJsonUtils.getWeatherContentValuesFromJson(context,body);
            if(weatherValues != null && weatherValues.length != 0){
                /* Get a handle on the ContentResolver to delete and insert data*/
                ContentResolver moonContentResolver = context.getContentResolver();

                /*If we have valid results, delete the old data and insert the new
                 Delete old weather data because we don't need to keep multiple days' data*/
                moonContentResolver.delete(
                        WeatherContentProvider.CONTENT_URI,
                        null,
                        null);
                /* Insert our new weather data into Sunshine's ContentProvider*/
                moonContentResolver.bulkInsert(
                        WeatherContentProvider.CONTENT_URI,
                        weatherValues);
            }


            /***
             为什么不使用Rx响应式编程。只能在Acitvity中？？？忘了
            compositeDisposable.add(weatherMini.query(location)
                    .flatMap(new Function<Response<JsonObject>, ObservableSource<?>>() {
                        @Override
                        public ObservableSource<?> apply(@NonNull Response<JsonObject> jsonObjectResponse) throws Exception {
                            if(jsonObjectResponse.isSuccessful()){
                                Log.i("hello  everyone !!!!!","---------------------");
                                Log.i(jsonObjectResponse.message(),"---------------------");

                                /* Parse the JSON into a list of weather values
                                ContentValues[] weatherValues = OpenWeatherJsonUtils.getWeatherContentValuesFromJson(context,jsonObjectResponse.body());
                                if(weatherValues != null && weatherValues.length != 0){

                                /* Get a handle on the ContentResolver to delete and insert data
                                    ContentResolver moonContentResolver = context.getContentResolver();

                                /*If we have valid results, delete the old data and insert the new
                                Delete old weather data because we don't need to keep multiple days' data
                                    moonContentResolver.delete(
                                            WeatherContentProvider.CONTENT_URI,
                                            null,
                                            null);
                                 /* Insert our new weather data into Sunshine's ContentProvider
                                    moonContentResolver.bulkInsert(
                                            WeatherContentProvider.CONTENT_URI,
                                            weatherValues);
                                }

                            }else {
                                String error = jsonObjectResponse.errorBody().toString();
                                Log.i("eeerrr----", error);
                            }
                            return Observable.just("sucess");
                        }
                    })*/
                   // .map(jsonObjectResponse -> jsonObjectResponse.body())
                   /* .flatMap(new Function<JsonObject, ObservableSource<?>>() {
                        @Override
                        public ObservableSource<?> apply(@NonNull JsonObject jsonObject) throws Exception {
                            Log.i("hello  everyone !!!!!","---------------------");
                            Log.i(jsonObject.toString(),"---------------------");

                            ContentValues[] weatherValues = OpenWeatherJsonUtils.getWeatherContentValuesFromJson(context,jsonObject);
                            if(weatherValues != null && weatherValues.length != 0){

                                ContentResolver moonContentResolver = context.getContentResolver();

                               urn Observable.just("success");
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.newThread())
                    .subscribe());*/

        }catch (Exception e){
             /* Server probably invalid */
            e.printStackTrace();

        }

       // compositeDisposable.clear();

    }
}
