package com.kechengbiao.jichuang.com.kechengbiao.UI.UI.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.kechengbiao.jichuang.com.kechengbiao.R;

/**
 * Created by 佟杨 on 2017/3/31.
 */

public class baseactivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("set", MODE_PRIVATE);
        String theme = sharedPreferences.getString("theme", "pink");
        Log.d("aaaa",theme);
        switch (theme) {
            case "pink":
                setTheme(R.style.theme_pink);
                break;
            case "purple":
                setTheme(R.style.theme_purple);

                break;
            case "yellow":
                setTheme(R.style.theme_yellow);

                break;
            case "blue":
                setTheme(R.style.theme_blue);

                break;
            case "green":
                setTheme(R.style.theme_green);

                break;
            case "red":
                setTheme(R.style.theme_red);

                break;

        }


    }




}
