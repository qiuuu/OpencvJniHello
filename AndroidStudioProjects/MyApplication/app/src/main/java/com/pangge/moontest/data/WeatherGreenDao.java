package com.pangge.moontest.data;

import android.database.sqlite.SQLiteDatabase;

import com.pangge.moontest.DaoMaster;
import com.pangge.moontest.DaoSession;
import com.pangge.moontest.Weather;

import org.greenrobot.greendao.rx.RxDao;
import org.greenrobot.greendao.rx.RxQuery;

/**
 * Created by iuuu on 17/5/16.
 */

public class WeatherGreenDao {
    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private RxDao<Weather, Long> weatherDao;
    private RxQuery<Weather> weatherRxQuery;
    private DaoSession daoSession;

    public void createDatabase(){
        //create database db file
      //  DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "weathers-db", null);
        //get the created database db file
      //  db = helper.getWritableDatabase();

    }




}
