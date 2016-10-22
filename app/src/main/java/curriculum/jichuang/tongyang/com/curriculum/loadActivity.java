package curriculum.jichuang.tongyang.com.curriculum;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by tongyang on 2016/9/29.
 */

public class loadActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
        setContentView(R.layout.load);


        new Handler().postDelayed(new Runnable() {
            public void run() {

                SharedPreferences sharedPreferences = loadActivity.this.getSharedPreferences("share", MODE_PRIVATE);
                boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Calendar calendar= Calendar.getInstance();
                SharedPreferences sharedPreferences1 = getSharedPreferences("Week", Context.MODE_PRIVATE);
                SharedPreferences.Editor e=sharedPreferences1.edit();
                if (isFirstRun) {
                    Log.d("debug", "第一次运行");
                    int week = calendar.get(Calendar.WEEK_OF_YEAR);
                    SharedPreferences sharedPreferences3 = getSharedPreferences("Time", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor3 = sharedPreferences.edit();
                    editor3.putString("time", "2016-2017-1");

                    e.putString("weekchange",String.valueOf(week));

                    editor.putBoolean("isFirstRun", false);
                    Intent mainIntent = new Intent(loadActivity.this, MainActivity.class);
                    loadActivity.this.startActivity(mainIntent);
                    loadActivity.this.finish();
                    editor.commit();
                } else {
                    int week = calendar.get(Calendar.WEEK_OF_YEAR);
                    String oldweek=  sharedPreferences1.getString("weekchange",String.valueOf(week));
                    if (week!=Integer.parseInt(oldweek)){

                        sharedPreferences1.getString("week",String.valueOf(week));
                         e.putString("week",String.valueOf(week+1));
                    }
                    Intent mainIntent = new Intent(loadActivity.this, page_menu.class);
                    loadActivity.this.startActivity(mainIntent);
                    loadActivity.this.finish();
                    Log.d("debug", "不是第一次运行");
                }

            }

        }, 1500);
    }


}
