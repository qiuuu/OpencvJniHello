package com.pangge.moontest;

import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by iuuu on 17/5/16.
 */

public class BaseApplication extends Application {
    private DaoMaster daoMaster;
    private static DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();


        //create database db file
        //SQLiteOpenHelper
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "weathers-db", null);
        //get the created database db file
        //db = helper.getWritableDatabase();

        if(null == daoMaster){
            //create masterDao
            daoMaster = new DaoMaster(helper.getWritableDatabase());
            //create Session session

            daoSession = daoMaster.newSession();
        }


        WeatherContentProvider.daoSession = daoSession;

    }

    public static DaoSession getDaoSession(){
        return daoSession;
    }
}
