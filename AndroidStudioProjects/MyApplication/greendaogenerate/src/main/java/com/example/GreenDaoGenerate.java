package com.example;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class GreenDaoGenerate {
    public static void main(String[] args) throws Exception{
        Schema schema = new Schema(2,"com.pangge.moontest");
        addWeather(schema);

        new DaoGenerator().generateAll(schema,"../MyApplication/app/src/main/java-gen");



    }
    private static void addWeather(Schema schema){
        Entity weather = schema.addEntity("Weather");

        weather.addIdProperty();
        weather.addStringProperty("date");
        weather.addStringProperty("type");
        weather.addStringProperty("high");
        weather.addStringProperty("low");
        weather.addStringProperty("fengxiang");
        weather.addStringProperty("fengli");
        weather.addStringProperty("city");
        weather.addStringProperty("wendu");
        weather.addStringProperty("ganmao");

        weather.addContentProvider();

    }
}
