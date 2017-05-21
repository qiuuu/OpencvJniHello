package com.pangge.moontest.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.pangge.moontest.DaoMaster;
import com.pangge.moontest.DaoSession;
import com.pangge.moontest.Weather;

import org.greenrobot.greendao.rx.RxDao;
import org.greenrobot.greendao.rx.RxQuery;

/**
 * Created by iuuu on 17/5/16.
 */

public class WeatherProvider extends ContentProvider {
    public static final int WEATHER = 100;
    public static final int WEATHER_DATE= 101;

    private DaoMaster daoMaster;
    private RxDao<Weather, Long> weatherDao;
    private RxQuery<Weather> weatherRxQuery;
    private DaoSession daoSession;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = WeatherContract.CONTENT_AUTHORITY;

        matcher.addURI(authority,WeatherContract.PATH_WEATHER, WEATHER);
        matcher.addURI(authority,WeatherContract.PATH_WEATHER + "/#",WEATHER_DATE);

        return matcher;
    }
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder, @Nullable CancellationSignal cancellationSignal) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)){
            case WEATHER_DATE:{
                weatherRxQuery =daoSession.getWeatherDao()
                        .queryBuilder()
                       // .where()
                        .rx();
            }
        }
        return super.query(uri, projection, selection, selectionArgs, sortOrder, cancellationSignal);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}
