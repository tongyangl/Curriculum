package com.kechengbiao.jichuang.com.kechengbiao.UI.UI.ui;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.kechengbiao.jichuang.com.kechengbiao.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

/**
 * Created by 佟杨 on 2017/3/22.
 */

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private CardView about;
    private CardView git;
    private CardView clear;
    private CardView week;
    private TextView zcc;
    private CardView update;
    private TextView vercode;
    private DownLoadComplete complete;
    private ProgressDialog dialog;
    private CardView kbview;
    private TextView kbview_tv;

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
        setContentView(R.layout.activity_setting);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("设置");
        kbview = (CardView) findViewById(R.id.kbview);
        kbview_tv = (TextView) findViewById(R.id.kbview_tv);
        update = (CardView) findViewById(R.id.update);
        vercode = (TextView) findViewById(R.id.vercode);
        about = (CardView) findViewById(R.id.about);
        git = (CardView) findViewById(R.id.gotogit);
        clear = (CardView) findViewById(R.id.clear);
        week = (CardView) findViewById(R.id.week);
        zcc = (TextView) findViewById(R.id.zc);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SharedPreferences sharedPreferences1=getSharedPreferences("set",MODE_PRIVATE);
        if (sharedPreferences1.getInt("kbview",0)==0){
            kbview_tv.setText("仅显示本周要上的课程");

        }else {
            kbview_tv.setText("显示所有课程(本周不上的课程为灰色)");
        }
        kbview.setOnClickListener(this);
        about.setOnClickListener(this);
        git.setOnClickListener(this);
        update.setOnClickListener(this);
        week.setOnClickListener(this);
        clear.setOnClickListener(this);
        SharedPreferences sharedPreferences = getSharedPreferences("zc", MODE_PRIVATE);
        vercode.setText(getVerName() + " ");
        zcc.setText(sharedPreferences.getString("zc", "1"));
        complete = new DownLoadComplete();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(complete, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(complete);
    }

    public String getVerName() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String vername = info.versionName;

            return vername;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "1.0";
    }

    public int getVerCode() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            int vername = info.versionCode;

            return vername;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gotogit:
                Log.d("----", "https://github.com/tongyangl/Curriculum.git");
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
                builder.setPositiveButton("确定", null);
                builder.show();
                break;
            case R.id.week:

                resetzc();
                break;

            case R.id.clear:
                final AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setTitle("提示");
                builder1.setMessage("这将会清除本地已保存的密码和帐号信息，是否确认");
                builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences preferences1 = getSharedPreferences("userinfo", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences1.edit();
                        editor.clear();
                        editor.commit();
                        Toast.makeText(SettingActivity.this, "信息清空成功", Toast.LENGTH_SHORT).show();
                    }
                });
                builder1.setNegativeButton("取消", null);
                builder1.show();
                break;
            case R.id.update:

                getServiceCode getServiceCode = new getServiceCode();
                getServiceCode.execute();
                break;
            case  R.id.kbview:
                final String items[]={
                  "仅显示本周课程","显示所有课程"
                };
                AlertDialog dialog = new AlertDialog.Builder(this).setTitle("单选对话框")
                        .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences sharedPreferences=getSharedPreferences("set",MODE_PRIVATE);
                                SharedPreferences.Editor editor=sharedPreferences.edit();

                                if (which==0){
                                    editor.putInt("kbview",which);
                                    kbview_tv.setText("仅显示本周要上的课程");
                                }else {
                                    editor.putInt("kbview",1);
                                    kbview_tv.setText("显示所有课程(本周不上的课程为灰色)");
                                }
                                editor.commit();
                                Log.d("kkkk",which+"");
                                Intent intent1=new Intent();
                                intent1.setAction("resetkb");
                                sendBroadcast(intent1);

                                Toast.makeText(SettingActivity.this, items[which], Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();

        }
    }

    public void resetzc() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_choosezc, null);
        final NumberPickerView pickerView = (NumberPickerView) view.findViewById(R.id.picker);
        final String[] zc = new String[25];
        for (int i = 0; i < zc.length; i++) {
            zc[i] = String.valueOf(i + 1);
        }
        pickerView.setDisplayedValues(zc);
        pickerView.setMaxValue(24);
        pickerView.setMinValue(1);
        pickerView.setValue(1);
        builder.setTitle("设置周次");
        builder.setView(view);
        builder.setPositiveButton("确定修改", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String z = String.valueOf(pickerView.getValue());
                SharedPreferences sharedPreferences = getSharedPreferences("zc", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("zc", z);
                Log.d("----", z);
                Log.d("---", String.valueOf(pickerView.getValue()));
                editor.commit();
                zcc.setText("第" + z + "周");
                Intent intent = new Intent();
                intent.setAction("resetkb");

                intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                sendBroadcast(intent);

            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    private class DownLoadComplete extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                Toast.makeText(SettingActivity.this, "编号：" + id + "的下载任务已经完成！", Toast.LENGTH_SHORT).show();
                installApk(SettingActivity.this, id);
            }
        }

        private void installApk(Context context, long downloadApkId) {
            DownloadManager dManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            Intent install = new Intent(Intent.ACTION_VIEW);
            Uri downloadFileUri = dManager.getUriForDownloadedFile(downloadApkId);
            if (downloadFileUri != null) {
                Log.d("DownloadManager", downloadFileUri.toString());
                install.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
                install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(install);
            } else {
                Log.e("DownloadManager", "download error");
            }
        }
    }

    class getServiceCode extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s == null) {
                dialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setTitle("提示!");
                builder.setMessage("与服务器连接失败！请稍候重试");
                builder.setPositiveButton("确定", null);
                builder.show();
            } else {
                dialog.dismiss();
                try {
                    JSONObject object = new JSONObject(s);
                    String vercode = object.getString("vercode");
                    String vername = object.getString("vername");
                    String update = object.getString("update");
                    if (Double.parseDouble(vercode) > getVerCode()) {
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(SettingActivity.this);
                        builder2.setTitle("检测到新版本");
                        builder2.setMessage("更新内容：" + update + "\n\n" + "更新版本号:" + vername + "当前版本号" + getVerName() + "\n" + "是否继续下载");
                        builder2.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ConnectivityManager con = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
                                boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
                                boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
                                if (internet) {

                                    AlertDialog.Builder builder2 = new AlertDialog.Builder(SettingActivity.this);
                                    builder2.setTitle("警告");
                                    builder2.setMessage("您正在使用手机流量，是否继续？");
                                    builder2.setPositiveButton("继续", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse("http://7xstld.com1.z0.glb.clouddn.com/kechengbiao.apk"));
                                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
                                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                                            request.setTitle("下载");
                                            request.setDescription("正在下载课程表");
                                            request.setAllowedOverRoaming(false);
                                            request.setDestinationInExternalFilesDir(SettingActivity.this, Environment.DIRECTORY_DOWNLOADS, "kechengbiao");
                                            DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                                            long id = downloadManager.enqueue(request);

                                        }
                                    });
                                    builder2.setNegativeButton("取消", null);
                                    builder2.setNeutralButton("打开WiFi", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                                            startActivity(intent);
                                        }
                                    });

                                    builder2.show();
                                } else {
                                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse("http://7xstld.com1.z0.glb.clouddn.com/kechengbiao.apk"));
                                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
                                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                                    request.setTitle("下载");
                                    request.setDescription("正在下载课程表");
                                    request.setAllowedOverRoaming(false);
                                    request.setDestinationInExternalFilesDir(SettingActivity.this, Environment.DIRECTORY_DOWNLOADS, "kechengbiao");
                                    Log.d("sss", Environment.DIRECTORY_DOWNLOADS);
                                    DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                                    long id = downloadManager.enqueue(request);
                                }
                            }
                        });
                        builder2.setNegativeButton("取消", null);
                        builder2.show();

                    } else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                        builder.setTitle("哼！");
                        builder.setMessage("当前已是最新版本啦！");
                        builder.setPositiveButton("确定", null);
                        builder.show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(SettingActivity.this);
            dialog.setTitle("连接服务器中");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet("https://raw.githubusercontent.com/tongyangl/MyApi/master/kechengbiao/kb.json");
            try {
                HttpResponse response = client.execute(get);

                String a = EntityUtils.toString(response.getEntity());
                return a;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
