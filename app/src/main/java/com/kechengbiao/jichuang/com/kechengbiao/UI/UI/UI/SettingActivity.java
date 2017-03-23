package com.kechengbiao.jichuang.com.kechengbiao.UI.UI.UI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.kechengbiao.jichuang.com.kechengbiao.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 佟杨 on 2017/3/22.
 */

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private CardView about;
    private CardView git;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        about = (CardView) findViewById(R.id.about);
        git = (CardView) findViewById(R.id.gotogit);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle("设置");
        about.setOnClickListener(this);
        git.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gotogit:
                Log.d("----","https://github.com/tongyangl/Curriculum.git");
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("https://github.com/tongyangl/Curriculum.git");
                intent.setData(content_url);
                startActivity(intent);
                break;
            case R.id.about:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("关于");
                builder.setMessage("本软件制作纯属于个人爱好，无半点盈利。并且已经开源到github，如果喜欢可以给我个star。");
                builder.setPositiveButton("确定",null);
                builder.show();
                break;

        }
    }
}
