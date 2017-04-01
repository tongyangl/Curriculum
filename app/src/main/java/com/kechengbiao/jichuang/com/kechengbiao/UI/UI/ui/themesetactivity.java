package com.kechengbiao.jichuang.com.kechengbiao.UI.UI.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kechengbiao.jichuang.com.kechengbiao.R;
import com.kechengbiao.jichuang.com.kechengbiao.UI.UI.Util;

/**
 * Created by 佟杨 on 2017/4/1.
 */

public class themesetactivity extends baseactivity implements View.OnClickListener {
    private Toolbar toolbar;
    private ListView listView;
    private TextView pink, purple, red, blue, yellow, green;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themeset);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("主题选择");
        toolbar.setTitleTextColor(Color.WHITE);
        listView = (ListView) findViewById(R.id.lv);
        purple = (TextView) findViewById(R.id.user_purple);
        red = (TextView) findViewById(R.id.use_red);
        blue = (TextView) findViewById(R.id.use_blue);
        yellow = (TextView) findViewById(R.id.use_yellow);
        green = (TextView) findViewById(R.id.use_green);
        pink = (TextView) findViewById(R.id.use_pink);
        purple.setOnClickListener(this);
        red.setOnClickListener(this);
        blue.setOnClickListener(this);
        yellow.setOnClickListener(this);
        green.setOnClickListener(this);
        pink.setOnClickListener(this);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("set", MODE_PRIVATE);
        String theme = sharedPreferences.getString("theme", "pink");
        switch (theme) {
            case "pink":
                pink.setText("使用中");
                break;
            case "purple":
                purple.setText("使用中");
                break;
            case "yellow":
                yellow.setText("使用中");
                break;
            case "blue":
                blue.setText("使用中");
                break;
            case "green":
                green.setText("使用中");
                break;
            case "red":
                red.setText("使用中");
                break;

        }

    }

    @Override
    public void onClick(View v) {
        SharedPreferences sharedPreferences = getSharedPreferences("set", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (v.getId()) {
            case R.id.use_pink:
                Util.reload(this);

                Intent intent = new Intent();
                intent.setAction("pink");
                sendBroadcast(intent);
                pink.setText("使用中");
                purple.setText("使用");
                yellow.setText("使用");
                blue.setText("使用");
                green.setText("使用");
                red.setText("使用");
                editor.putString("theme", "pink");
                editor.commit();
                break;
            case R.id.use_green:
                Util.reload(this);


                editor.putString("theme", "green");
                editor.commit();
                Intent intent1 = new Intent();
                intent1.setAction("green");
                sendBroadcast(intent1);

                pink.setText("使用");
                purple.setText("使用");
                yellow.setText("使用");
                blue.setText("使用");
                green.setText("使用中");
                red.setText("使用");
                break;
            case R.id.use_red:
                Util.reload(this);


                editor.putString("theme", "red");
                editor.commit();
                Intent intent2 = new Intent();
                intent2.setAction("red");
                sendBroadcast(intent2);

                pink.setText("使用");
                purple.setText("使用");
                yellow.setText("使用");
                blue.setText("使用");
                green.setText("使用");
                red.setText("使用中");
                break;
            case R.id.use_blue:
                Util.reload(this);


                editor.putString("theme", "blue");
                editor.commit();
                Intent intent3 = new Intent();
                intent3.setAction("blue");
                sendBroadcast(intent3);
                pink.setText("使用");
                purple.setText("使用");
                yellow.setText("使用");
                blue.setText("使用中");
                green.setText("使用");
                red.setText("使用");

                break;
            case R.id.use_yellow:
                editor.putString("theme", "yellow");
                editor.commit();
                Intent intent4 = new Intent();
                intent4.setAction("yellow");
                sendBroadcast(intent4);
                pink.setText("使用");
                purple.setText("使用");
                yellow.setText("使用中");
                blue.setText("使用");
                green.setText("使用");
                red.setText("使用");
                Util.reload(this);


                break;
            case R.id.user_purple:
                Util.reload(this);
                editor.putString("theme", "purple");
                editor.commit();
                Intent intent5 = new Intent();
                intent5.setAction("purple");
                sendBroadcast(intent5);
                pink.setText("使用");
                purple.setText("使用中");
                yellow.setText("使用");
                blue.setText("使用");
                green.setText("使用");
                red.setText("使用");
                break;

        }
    }


}
