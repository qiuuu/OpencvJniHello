package com.pangge.moontest;

import android.content.Intent;
import android.database.Cursor;

import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.pangge.moontest.data.WeatherContract;
import com.pangge.moontest.data.WeatherPreferences;
import com.pangge.moontest.network.WeatherMini;
import com.pangge.moontest.sync.WeatherSyncUtils;

import org.greenrobot.greendao.rx.RxDao;


import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener{
    @BindView(R.id.main_type) TextView typeText;
    @BindView(R.id.temp) TextView tempText;
    @BindView(R.id.city) TextView cityText;
    /**
    *   Caused by: java.lang.NullPointerException: Attempt to invoke virtual method 'void android.view.View.setOnClickListener(android.view.View$OnClickListener)' on a null object reference
    *  BindView 没有Bind成功！！为啥
     *  ButterKnife.bind(this) hhhhahahhh
     */

    @BindView(R.id.weatherCard) CardView weatherCard;

    private static final int ID_FORECAST_LOADER = 26;
    /*
     * The columns of data that we are interested in displaying within our MainActivity's list of
     * weather data.
     */
    public static final String[] MAIN_FORECAST_PROJECTION = {
            WeatherContract.WeatherEntry.COLUMN_DATE,
            WeatherContract.WeatherEntry.COLUMN_MAX_TEMP,
            WeatherContract.WeatherEntry.COLUMN_MIN_TEMP,
            WeatherContract.WeatherEntry.COLUMN_TYPE,
            WeatherContract.WeatherEntry.COLUMN_FX,
            WeatherContract.WeatherEntry.COLUMN_FL,
            WeatherContract.WeatherEntry.COLUMN_CITY,
            WeatherContract.WeatherEntry.COLUMN_WENDU,
            WeatherContract.WeatherEntry.COLUMN_TIP,
    };

    /*
     * We store the indices of the values in the array of Strings above to more quickly be able to
     * access the data from our query. If the order of the Strings above changes, these indices
     * must be adjusted to match the order of the Strings.
     */
    public static final int INDEX_WEATHER_DATE = 0;
    public static final int INDEX_WEATHER_MAX_TEMP = 1;
    public static final int INDEX_WEATHER_MIN_TEMP = 2;
    public static final int INDEX_WEATHER_TYPE = 3;
    public static final int INDEX_WEATHER_FENGXIANG = 4;
    public static final int INDEX_WEATHER_FENGLI = 5;
    public static final int INDEX_WEATHER_CITY = 6;
    public static final int INDEX_WEATHER_WENDU = 7;
    public static final int INDEX_WEATHER_TIP = 8;



    public static final String BASE_URL = "http://wthrcdn.etouch.cn/";
    private CompositeDisposable compositeDisposable;
    //private CardView card1;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //card1 = (CardView)findViewById(R.id.weatherCard);
        //card1.setOnClickListener(this);
        ButterKnife.bind(this);
        getSupportLoaderManager().initLoader(ID_FORECAST_LOADER, null, this);


        weatherCard.setOnClickListener(this);


    }

    @Override
    protected void onPause() {
        super.onPause();
      //  compositeDisposable.clear();
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case ID_FORECAST_LOADER:
                /* URI for all rows of weather data in our weather table */
                Uri forecastQueryUri = WeatherContentProvider.CONTENT_URI;
                /* Sort order: Ascending by date */

               // String sortOrder = WeatherContract.WeatherEntry.COLUMN_DATE + " ASC";
                //date 格式 并不标准

                //String selection = WeatherContract.WeatherEntry.getSqlSelectForTodayOnwards();
                return new CursorLoader(this,
                        forecastQueryUri,
                        MAIN_FORECAST_PROJECTION,
                        null,
                        null,
                        null);
            default:
                throw new RuntimeException("Loader Not Implemented: " + id);

        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data.moveToFirst()){

            String city = data.getString(INDEX_WEATHER_CITY);
            String wendu = data.getString(INDEX_WEATHER_WENDU);
            String type = data.getString(INDEX_WEATHER_TYPE);
           // wendu = wendu.  "°c"
            String sTempFormat = getResources().getString(R.string.temp);
            String sFinalTemp = String.format(sTempFormat,wendu);
            cityText.setText(city);
            tempText.setText(sFinalTemp);
            typeText.setText(type);
        }

        //If mPosition equals RecyclerView.NO_POSITION, set it to 0
        //if(mPosition == RecyclerView.NO_POSITION) mPosition = 0;
        // Smooth scroll the RecyclerView to mPosition
        // mRecyclerView.smoothScrollToPosition(mPosition);
        //If the Cursor's size is not equal to 0, call showWeatherDataView
        // if(data.getCount() != 0) showWeatherDataView();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
         /*
          * Since this Loader's data is now invalid, we need to clear the Adapter that is
          * displaying the data.
          */
       // mForecastAdapter.swapCursor(null);
    }

    @Override
    public void onClick(View view) {
        Log.i("hello--","MainAcitivity----workd");
        Intent weatherListIntent = new Intent(this,WeatherListActivity.class);
        startActivity(weatherListIntent);
    }
}
