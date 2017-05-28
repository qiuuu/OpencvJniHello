package com.pangge.testjni;

import android.app.Application;
import android.util.Log;

/**
 * Created by iuuu on 17/5/22.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.w("native-activity", "onCreate");
    }


}
