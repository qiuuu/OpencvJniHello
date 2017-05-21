package com.pangge.moontest.utilities;

import com.pangge.moontest.R;

import io.reactivex.annotations.NonNull;

/**
 * Created by iuuu on 17/5/15.
 */

public class MoonWeatherUtils {
    public static int getSmallArtDrawableForWeatherCondition(@NonNull String weatherName){
        switch (weatherName){
            case "晴":
                return R.drawable.clear;
            case "多云":
                return R.drawable.cloud;
            case "阴":
                return R.drawable.clouds;
            case "雾":
                return R.drawable.fog;
            case "小雨":
                return R.drawable.light_rain;
            case "中雨":
                return R.drawable.mid_rain;
            case "大雨":
                return R.drawable.heavy_rain;
            case "暴雨":
                return R.drawable.rainstorm;
            case "小到中雨":
                return R.drawable.mid_rain;
            case "阵雨":
                return R.drawable.shower;
            case "雷阵雨":
                return R.drawable.thunderstorms;
            default:
                return R.drawable.clear;
        }
    }
}
