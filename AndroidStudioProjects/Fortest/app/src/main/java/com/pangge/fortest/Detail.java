package com.pangge.fortest;

/**
 * Created by iuuu on 16/10/11.
 */
public class Detail {
    private String item;
    private String value;

    public Detail(String item, String value){
        this.item = item;
        this.value = value;
    }

    public String getItem(){
        return item;
    }

    public String getValue(){
        return value;
    }
}
