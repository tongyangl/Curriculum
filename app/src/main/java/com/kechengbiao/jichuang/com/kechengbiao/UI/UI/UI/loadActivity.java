package com.kechengbiao.jichuang.com.kechengbiao.UI.UI.UI;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.WindowManager;
import android.os.Handler;

import com.kechengbiao.jichuang.com.kechengbiao.R;
import com.kechengbiao.jichuang.com.kechengbiao.UI.UI.LoginActivity;

/**
 * Created by 佟杨 on 2017/3/24.
 */

public class loadActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFormat(PixelFormat.RGBA_8888);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
        setContentView(R.layout.activity_load);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //无初始化数据
                Intent intent = new Intent(loadActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, 1500);
    }
}
