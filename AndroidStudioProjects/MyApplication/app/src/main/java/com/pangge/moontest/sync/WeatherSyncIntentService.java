package com.pangge.moontest.sync;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by iuuu on 17/5/16.
 *
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */


public class WeatherSyncIntentService extends IntentService {
    //Create a constructor that calls super and passes the name of this class
    public WeatherSyncIntentService(){
        super("WeatherSyncIntentService");
    }
    //Override onHandleIntent, and within it, call WeatherSyncTask.syncWeather
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        WeatherSyncTask.syncWeather(this);

    }
}
