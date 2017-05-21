package com.pangge.moontest;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by iuuu on 17/5/21.
 */

public class SettingActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.setContentView();
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
