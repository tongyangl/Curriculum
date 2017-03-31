package com.kechengbiao.jichuang.com.kechengbiao.UI.UI.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.os.Handler;

import com.kechengbiao.jichuang.com.kechengbiao.R;

import java.util.Calendar;

/**
 * Created by 佟杨 on 2017/3/24.
 */

public class loadActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_load);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //无初始化数据
                Calendar calendar = Calendar.getInstance();
                int zc = calendar.get(Calendar.WEEK_OF_YEAR);
                Log.d("zzzz",zc+"");
                SharedPreferences sharedPreferences = getSharedPreferences("zc", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                int nowzc = sharedPreferences.getInt("zcofmo", -1);
                if (nowzc == -1) {
                    editor.putInt("zcofmo", zc);
                    editor.commit();
                    Log.d("zzz","hahah");
                } else {
                    if (nowzc != zc) {
                       String z= sharedPreferences.getString("zc", "1");
                        Log.d("zzz",z);
                        Log.d("zzzzz",nowzc+"");
                        editor.putString("zc",Integer.parseInt(z)+1+"");
                        editor.commit();

                    }
                }
                Intent intent = new Intent(loadActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1500);
    }
}
