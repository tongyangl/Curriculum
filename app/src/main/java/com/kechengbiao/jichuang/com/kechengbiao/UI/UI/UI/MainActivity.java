package com.kechengbiao.jichuang.com.kechengbiao.UI.UI.ui;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kechengbiao.jichuang.com.kechengbiao.R;
import com.kechengbiao.jichuang.com.kechengbiao.UI.UI.LoginActivity;
import com.kechengbiao.jichuang.com.kechengbiao.UI.UI.Util;

import net.qiujuer.genius.blur.StackBlur;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;


/**
 * Created by 佟杨 on 2017/3/7.
 */

public class MainActivity extends baseactivity {
    private NavigationView ngv;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private LinearLayout layout1;
    private LinearLayout layout2;
    private LinearLayout layout3;
    private LinearLayout layout4;
    private LinearLayout layout5;
    private LinearLayout layout6;
    private LinearLayout layout7;
    private LinearLayout layout;
    private TextView month;
    private TextView title_zc;
    private RadioGroup radioGroup;
    private LinearLayout toplinear;
    private TextView resetzc;
    private String realzc = "";
    private BroadcastReceiver receiver;
    private TextView bz;
    private String theme;
    private SharedPreferences preferences;
    private ScrollView background;
    private LinearLayout kb;
    private TextView nokb;
    private LinearLayout bzv;

    //  private RelativeLayout background;
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter("getkb");
        registerReceiver(receiver, intentFilter);
        IntentFilter intentFilter1 = new IntentFilter("resetkb");
        registerReceiver(receiver, intentFilter1);
        IntentFilter intentFilter2 = new IntentFilter("setbackground");
        registerReceiver(receiver, intentFilter2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        initId();
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        preferences = getSharedPreferences("set", MODE_PRIVATE);
        theme = preferences.getString("theme", "pink");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0) {
        };
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("getkb")) {
                    setkb("1");
                    View view = ngv.getHeaderView(0);
                    TextView name = (TextView) view.findViewById(R.id.name);
                    TextView number = (TextView) view.findViewById(R.id.number);
                    SharedPreferences sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);
                    String a = sharedPreferences.getString("name", "");
                    if (!a.equals("")) {
                        String reg = "[^\u4e00-\u9fa5]";
                        String n = a.replaceAll(reg, "");
                        name.setText("姓名:" + n);

                        a = a.trim();
                        String regEx = "[^0-9]";
                        Pattern p = Pattern.compile(regEx);
                        Matcher m = p.matcher(a);
                        number.setText("学号:" + m);
                    }
                    title_zc.setText("第1周");
                } else if (intent.getAction().equals("resetkb")) {
                    SharedPreferences sharedPreferences = getSharedPreferences("zc", MODE_PRIVATE);
                    title_zc.setText("第" + sharedPreferences.getString("zc", "1") + "周");
                    setkb(sharedPreferences.getString("zc", "1"));
                }else if (intent.getAction().equals("setbackground")){
                    Log.d("vvvv","getsend");
                    setBackground();
                }
            }
        };


        title_zc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toplinear.getVisibility() == View.GONE) {
                    toplinear.setVisibility(View.VISIBLE);
                    radioGroup.check(Integer.parseInt(realzc) - 1);
                    Drawable drawable = getResources().getDrawable(R.drawable.text_down);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    title_zc.setCompoundDrawables(null, null, drawable, null);
                } else {

                    Drawable drawable = getResources().getDrawable(R.drawable.text_up);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    title_zc.setCompoundDrawables(null, null, drawable, null);
                    toplinear.setVisibility(View.GONE);
                    setkb(realzc);
                }
            }
        });
        toggle.syncState();
        drawerLayout.setDrawerListener(toggle);
        ngv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            MenuItem mymenuItem;

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                if (mymenuItem != null)
                    mymenuItem.setCheckable(false);
                item.setCheckable(true);
                drawerLayout.closeDrawers();

                mymenuItem = item;

                switch (item.getItemId()) {

                    case R.id.kecheng:

                        break;
                    case R.id.library:
                        Intent intent = new Intent(MainActivity.this, LibraryActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.gradesearch:
                        Intent intent1 = new Intent(MainActivity.this, GradeSearchActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.setting:
                        Intent intent2 = new Intent(MainActivity.this, SettingActivity.class);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });
        resetzc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetzc();
            }
        });
    }

    private void initId() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toolbar.setTitle("课程表");
        kb = (LinearLayout) findViewById(R.id.kb);
        nokb = (TextView) findViewById(R.id.nokb);
        month = (TextView) findViewById(R.id.month);
        bz = (TextView) findViewById(R.id.beizhu);
        background = (ScrollView) findViewById(R.id.scroll);
        layout1 = (LinearLayout) findViewById(R.id.one);
        layout2 = (LinearLayout) findViewById(R.id.two);
        layout3 = (LinearLayout) findViewById(R.id.three);
        layout4 = (LinearLayout) findViewById(R.id.four);
        layout5 = (LinearLayout) findViewById(R.id.five);
        layout6 = (LinearLayout) findViewById(R.id.six);
        layout7 = (LinearLayout) findViewById(R.id.seven);
        ngv = (NavigationView) findViewById(R.id.ngv);
        title_zc = (TextView) findViewById(R.id.title_zc);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        resetzc = (TextView) findViewById(R.id.resetzc);
        toplinear = (LinearLayout) findViewById(R.id.hscroview);
        bzv = (LinearLayout) findViewById(R.id.bz);
        Calendar calendar = Calendar.getInstance();
        int mo = calendar.get(Calendar.MONTH) + 1;
        View view = ngv.getHeaderView(0);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView number = (TextView) view.findViewById(R.id.number);
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);
        String c = sharedPreferences.getString("name", "");
        if (!c.equals("")) {
            c = c.trim();
            String b = sharedPreferences.getString("username", "");
            number.setText("学号:" + b);
            String reg = "[^\u4e00-\u9fa5]";
            String n = c.replaceAll(reg, "");
            name.setText("姓名:" + n);

        }


        month.setText(mo + "\n月");
        SharedPreferences sharedPreferences1 = getSharedPreferences("zc", MODE_PRIVATE);
        realzc = sharedPreferences1.getString("zc", "1");
        SharedPreferences sharedPreferences2 = getSharedPreferences("kb", MODE_PRIVATE);
        if (!sharedPreferences2.getString("kb", "").equals("")) {
            setkb(realzc);
            title_zc.setText("第" + realzc + "周");

        } else {
            kb.setVisibility(View.GONE);
            nokb.setVisibility(View.VISIBLE);
            bzv.setVisibility(View.GONE);
            nokb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
        for (int i = 0; i < 25; i++) {
            RadioButton radioButton = new RadioButton(MainActivity.this);
            int w = Util.dip2px(this, 70);
            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(w, RadioGroup.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(5, 5, 5, 5);
            radioButton.setClickable(true);
            radioButton.setLayoutParams(layoutParams);
            radioButton.setId(i);
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setTextSize(12);
            Drawable drawable = getResources().getDrawable(R.drawable.radio_down);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            radioButton.setCompoundDrawables(null, null, null, drawable);
            radioButton.setButtonDrawable(android.R.color.transparent);
            int a = i + 1;
            radioButton.setText("第" + a + "周");
            radioButton.setBackground(getResources().getDrawable(R.drawable.seleter_radio));
            radioGroup.addView(radioButton);
        }
        radioGroup.check(Integer.parseInt(realzc) - 1);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                switch (group.getCheckedRadioButtonId()) {
                    case 0:
                        setkb("1");
                        break;
                    case 1:
                        setkb("2");
                        break;
                    case 2:
                        setkb("3");
                        break;
                    case 3:
                        setkb("4");
                        break;
                    case 4:
                        setkb("5");
                        break;
                    case 5:
                        setkb("6");
                        break;
                    case 6:
                        setkb("7");
                        break;
                    case 7:
                        setkb("8");
                        break;
                    case 8:
                        setkb("9");
                        break;
                    case 9:
                        setkb("10");
                        break;
                    case 10:
                        setkb("11");
                        break;
                    case 11:
                        setkb("12");
                        break;
                    case 12:
                        setkb("13");
                        break;
                    case 13:
                        setkb("14");
                        break;
                    case 14:
                        setkb("15");
                        break;
                    case 15:
                        setkb("16");
                        break;
                    case 16:
                        setkb("17");
                        break;
                    case 17:
                        setkb("18");
                        break;
                    case 18:
                        setkb("29");
                        break;
                    case 19:
                        setkb("20");
                        break;
                    case 20:
                        setkb("21");
                        break;
                    case 21:
                        setkb("22");
                        break;
                    case 22:
                        setkb("23");
                        break;
                    case 23:
                        setkb("24");
                        break;
                    case 24:
                        setkb("25");
                        break;

                }
            }
        });
        setBackground();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 2) {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/jike";
                File f = new File(path);
                if (!f.exists()) {

                    f.mkdir();
                }

                File file = new File(f, "background.jpg");

                if (file.exists()) {
                    file.delete();
                }
                Uri uri = data.getData();


                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(uri, "image/*");
                intent.putExtra("crop", "true");
                intent.putExtra("aspectX", background.getWidth());
                intent.putExtra("aspectY", background.getHeight());
                intent.putExtra("outputX", 500);
                intent.putExtra("outputY", 500);
                intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                intent.putExtra("return-data", false);
                startActivityForResult(intent, 3);


            } else if (requestCode == 3) {

                   setBackground();

            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        MenuItem mMenuItem;
        menuInflater.inflate(R.menu.kebiao, menu);

        mMenuItem = menu.findItem(R.id.daoru);
        mMenuItem = menu.findItem(R.id.share);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.daoru) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.setzc) {

            resetzc();

        } else if (item.getItemId() == R.id.share) {
         /*  Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
           // intent.setType("image*//**//*");
            intent.setType("image*//*");
            intent.putExtra("crop", true);
            intent.putExtra("return-data", true);
            startActivityForResult(intent, 2);*/

            PopupWindow popupWindow = new PopupWindow(this);
            final View view = getLayoutInflater().inflate(R.layout.popu_main_backset, null);
            ImageView zidingyi = (ImageView) view.findViewById(R.id.zidingyi);
            ImageView ic_1 = (ImageView) view.findViewById(R.id.ic_1);
            ImageView ic_2 = (ImageView) view.findViewById(R.id.ic_2);
            ImageView ic_3 = (ImageView) view.findViewById(R.id.ic_3);
            ImageView ic_4 = (ImageView) view.findViewById(R.id.ic_4);
            ImageView ic_5 = (ImageView) view.findViewById(R.id.ic_5);
            ic_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPreferences = getSharedPreferences("set", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("seek", 1);
                    editor.commit();
                    Resources resources = getResources();
                    Drawable drawable = resources.getDrawable(R.drawable.ic_b22);
                    background.setBackgroundResource(R.drawable.ic_b22);
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                    Util.saveBackground(bitmapDrawable.getBitmap(), MainActivity.this);

                }
            });
            ic_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPreferences = getSharedPreferences("set", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("seek", 1);
                    editor.commit();
                    Resources resources = getResources();
                    Drawable drawable = resources.getDrawable(R.drawable.ic_b33);
                    background.setBackgroundResource(R.drawable.ic_b33);
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                    Util.saveBackground(bitmapDrawable.getBitmap(), MainActivity.this);
                }
            });
            ic_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPreferences = getSharedPreferences("set", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("seek", 1);
                    editor.commit();
                    Resources resources = getResources();
                    Drawable drawable = resources.getDrawable(R.drawable.ic_b44);
                    background.setBackgroundResource(R.drawable.ic_b44);
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                    Util.saveBackground(bitmapDrawable.getBitmap(), MainActivity.this);
                }
            });
            ic_5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPreferences = getSharedPreferences("set", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("seek", 1);
                    editor.commit();
                    Resources resources = getResources();

                    Drawable drawable = resources.getDrawable(R.drawable.ic_b55);
                    background.setBackgroundResource(R.drawable.ic_b55);
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                    Util.saveBackground(bitmapDrawable.getBitmap(), MainActivity.this);


                }
            });
            ic_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPreferences = getSharedPreferences("set", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("seek", 1);
                    editor.commit();
                    Resources resources = getResources();
                    Drawable drawable = resources.getDrawable(R.drawable.ic_b11);
                    background.setBackgroundResource(R.drawable.ic_b11);
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                    Util.saveBackground(bitmapDrawable.getBitmap(), MainActivity.this);

                }
            });

            zidingyi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    intent.putExtra("outputX", background.getWidth());
                    intent.putExtra("outputY", background.getHeight());
                    intent.putExtra("crop", true);
                    intent.putExtra("return-data", true);
                    startActivityForResult(intent, 2);
                }
            });

            ColorDrawable dw = new ColorDrawable(0xffffffff);
            popupWindow.setBackgroundDrawable(dw);
            popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setContentView(view);
            popupWindow.showAtLocation(getLayoutInflater().inflate(R.layout.activity_main, null), Gravity.BOTTOM, 0, 0);


        } else if (item.getItemId() == R.id.share1) {
            Intent intent1 = new Intent(Intent.ACTION_SEND);
            intent1.putExtra(Intent.EXTRA_TEXT, "我在使用极课！一款乖巧好用的课程表！快来和我一起使用吧！http://sj.qq.com/myapp/detail.htm?apkName=com.kechengbiao.jichuang.com.kechengbiao");
            intent1.setType("text/plain");
            startActivity(Intent.createChooser(intent1, "share"));

        }
        return super.onOptionsItemSelected(item);
    }

    public void resetzc() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_choosezc, null);
        final NumberPickerView pickerView = (NumberPickerView) view.findViewById(R.id.picker);
        String[] zc = new String[25];
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
                realzc = z;
                setkb(z);
                Calendar calendar = Calendar.getInstance();
                int zc = calendar.get(Calendar.WEEK_OF_YEAR);
                Log.d("zzzz", zc + "");
                SharedPreferences sharedPreferences1 = getSharedPreferences("zc", MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sharedPreferences.edit();
                editor.putInt("zcofmo", zc);
                editor.commit();
                title_zc.setText("第" + z + "周");

            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    public void setBackground() {

        if (Util.getBackground(this) != null) {
            SharedPreferences preferences = getSharedPreferences("set", MODE_PRIVATE);
            Log.d("zzzz", preferences.getInt("seek", 0) + "");
            int progress = preferences.getInt("seek", 0) / 4;
            if (progress == 0) {
                progress = 1;
            }
            Log.d("vvvv", "have");
            Bitmap newBitmap = StackBlur.blurNativelyPixels(Util.getBackground(this), (int) progress, false);
            Drawable drawable = new BitmapDrawable(newBitmap);
            background.setBackground(drawable);
        } else {
            Drawable d = getResources().getDrawable(R.drawable.ic_b5);
            BitmapDrawable bd = (BitmapDrawable) d;
            Bitmap bitmap = bd.getBitmap();
            Util.saveBackground(bitmap, this);
            SharedPreferences preferences = getSharedPreferences("set", MODE_PRIVATE);
            Log.d("zzzz", preferences.getInt("seek", 0) + "");
            int progress = preferences.getInt("seek", 0) / 4;
            Bitmap newBitmap = StackBlur.blurNativelyPixels(bitmap, (int) progress, false);
            Drawable drawable = new BitmapDrawable(newBitmap);
            background.setBackground(drawable);
            Log.d("vvvv", "null");

        }

    }

    private void setkb(String zc) {
        RemoveView();
        if (!kb.isShown()) {
            kb.setVisibility(View.VISIBLE);
            nokb.setVisibility(View.GONE);
            bzv.setVisibility(View.VISIBLE);
        }
        SharedPreferences sharedPreferences = getSharedPreferences("kb", MODE_PRIVATE);
        String kb = sharedPreferences.getString("kb", "");
        String beizhu = sharedPreferences.getString("beizhu", "无备注");
        bz.setText(beizhu);
        try {
            JSONArray array = new JSONArray(kb);
            for (int i = 0; i < array.length(); i++) {


                getevery(array.getString(i), i + 1, zc);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getevery(String jso, int a, String zc) throws JSONException {
        TextView textView;
        JSONObject jsonObject = new JSONObject(jso);
        int i = a % 7;

        if (jsonObject.has("zero")) {
            textView = CreatTV("", "", "", "", a, 0);
            settextview(i, textView);

        } else {
            if (jsonObject.has("week1")) {
                String week1 = jsonObject.getString("week1");
                String teacher1 = jsonObject.getString("teacher1");
                String w1 = jsonObject.getString("w1");
                String classroom1 = jsonObject.getString("classroom1");
                String week = jsonObject.getString("week");
                String teacher = jsonObject.getString("teacher");
                String classroom = jsonObject.getString("classroom");
                String Class = jsonObject.getString("Class");
                String w = jsonObject.getString("w");
                Log.d("---", Class + "asd");
                String allweek = "." + week + "." + week1 + ".";
                if (allweek.trim().contains("." + zc + ".")) {
                    if (("." + week + ".").trim().contains("." + zc + ".")) {
                        textView = CreatTV(w, teacher, classroom, Class.split("---------------------")[0], a, 1);
                        settextview(i, textView);
                    } else if (("." + week1 + ".").trim().contains("." + zc + ".")) {
                        textView = CreatTV(w1, teacher1, classroom1, Class.split("---------------------")[1], a, 1);
                        settextview(i, textView);
                    }
                } else {
                    textView = CreatTV("", "", "", Class.split("---------------------")[0] + "/" + Class.split("---------------------")[1], a, 2);
                    settextview(i, textView);
                }
            } else {
                String week = "." + jsonObject.getString("week") + ".";
                String teacher = jsonObject.getString("teacher");
                String classroom = jsonObject.getString("classroom");
                String Class = jsonObject.getString("Class");
                String w = jsonObject.getString("w");
                Log.d("---", Class + "asd");
                if ((week).trim().contains("." + zc + ".")) {
                    textView = CreatTV(w, teacher, classroom, Class, a, 1);
                    settextview(i, textView);
                } else {
                    textView = CreatTV(w, teacher, classroom, Class, a, 2);
                    settextview(i, textView);
                }

            }

        }

    }

    private void settextview(int i, TextView textView) {
        switch (i) {
            case 1:
                layout = (LinearLayout) findViewById(R.id.one);
                layout.addView(textView);
                break;
            case 2:
                layout = (LinearLayout) findViewById(R.id.two);
                layout.addView(textView);
                break;
            case 3:
                layout = (LinearLayout) findViewById(R.id.three);
                layout.addView(textView);
                break;
            case 4:
                layout = (LinearLayout) findViewById(R.id.four);
                layout.addView(textView);
                break;
            case 5:
                layout = (LinearLayout) findViewById(R.id.five);
                layout.addView(textView);
                break;
            case 6:
                layout = (LinearLayout) findViewById(R.id.six);
                layout.addView(textView);
                break;
            case 0:
                layout = (LinearLayout) findViewById(R.id.seven);
                layout.addView(textView);
                break;

        }


    }

    private TextView CreatTV(final String week, final String teacher, final String classroom, final String Class, int a, int isweek) {
        TextView tv = new TextView(MainActivity.this);
        int height = Util.dip2px(getApplicationContext(), 100);
        if (isweek == 0) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            params.setMargins(1, 1, 1, 1);
            tv.setLayoutParams(params);
            tv.setClickable(false);

        } else if (isweek == 2) {
            SharedPreferences sharedPreferences = getSharedPreferences("set", MODE_PRIVATE);
            int i = sharedPreferences.getInt("kbview", 0);
            if (i == 0) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                params.setMargins(1, 1, 1, 1);
                tv.setLayoutParams(params);
                tv.setClickable(false);
            } else {

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                params.setMargins(1, 1, 1, 1);
                tv.setLayoutParams(params);
                // tv.setGravity(Gravity.CENTER);
                tv.setText(Class + classroom + teacher);
                tv.setClickable(true);
                tv.setBackgroundResource(R.drawable.shape_class7);
                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("课程信息");
                        builder.setMessage("名称:" + Class + "\n" + "教室:" + classroom + "\n" + "教师:" + teacher + "\n" + "周次:" + week);
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                });
                tv.setTextSize(10);
                tv.setTextColor(Color.WHITE);
            }

        } else {
            int s = (int) a / 7;
            int b = a % 7;
            switch (s) {
                case 0:
                    switch (b) {
                        case 0:
                            tv.setBackgroundResource(R.drawable.shape_class);
                            break;
                        case 1:
                            tv.setBackgroundResource(R.drawable.shape_class1);
                            break;
                        case 2:
                            tv.setBackgroundResource(R.drawable.shape_class2);
                            break;
                        case 3:
                            tv.setBackgroundResource(R.drawable.shape_class3);
                            break;
                        case 4:
                            tv.setBackgroundResource(R.drawable.shape_class4);
                            break;
                        case 5:
                            tv.setBackgroundResource(R.drawable.shape_class5);
                            break;
                        case 6:
                            tv.setBackgroundResource(R.drawable.shape_class6);
                            break;
                    }
                    break;

                case 1:
                    switch (b) {
                        case 1:
                            tv.setBackgroundResource(R.drawable.shape_class);
                            break;
                        case 2:
                            tv.setBackgroundResource(R.drawable.shape_class1);
                            break;
                        case 3:
                            tv.setBackgroundResource(R.drawable.shape_class2);
                            break;
                        case 4:
                            tv.setBackgroundResource(R.drawable.shape_class3);
                            break;
                        case 5:
                            tv.setBackgroundResource(R.drawable.shape_class4);
                            break;
                        case 6:
                            tv.setBackgroundResource(R.drawable.shape_class5);
                            break;
                        case 0:
                            tv.setBackgroundResource(R.drawable.shape_class6);
                            break;
                    }
                    break;
                case 2:
                    switch (b) {
                        case 2:
                            tv.setBackgroundResource(R.drawable.shape_class);
                            break;
                        case 3:
                            tv.setBackgroundResource(R.drawable.shape_class1);
                            break;
                        case 4:
                            tv.setBackgroundResource(R.drawable.shape_class2);
                            break;
                        case 5:
                            tv.setBackgroundResource(R.drawable.shape_class3);
                            break;
                        case 6:
                            tv.setBackgroundResource(R.drawable.shape_class4);
                            break;
                        case 0:
                            tv.setBackgroundResource(R.drawable.shape_class5);
                            break;
                        case 1:
                            tv.setBackgroundResource(R.drawable.shape_class6);
                            break;
                    }
                    break;
                case 3:
                    switch (b) {
                        case 3:
                            tv.setBackgroundResource(R.drawable.shape_class);
                            break;
                        case 4:
                            tv.setBackgroundResource(R.drawable.shape_class1);
                            break;
                        case 5:
                            tv.setBackgroundResource(R.drawable.shape_class2);
                            break;
                        case 6:
                            tv.setBackgroundResource(R.drawable.shape_class3);
                            break;
                        case 0:
                            tv.setBackgroundResource(R.drawable.shape_class4);
                            break;
                        case 1:
                            tv.setBackgroundResource(R.drawable.shape_class5);
                            break;
                        case 2:
                            tv.setBackgroundResource(R.drawable.shape_class6);
                            break;
                    }
                    break;
                case 4:

                    switch (b) {
                        case 4:
                            tv.setBackgroundResource(R.drawable.shape_class);
                            break;
                        case 5:
                            tv.setBackgroundResource(R.drawable.shape_class1);
                            break;
                        case 6:
                            tv.setBackgroundResource(R.drawable.shape_class2);
                            break;
                        case 0:
                            tv.setBackgroundResource(R.drawable.shape_class3);
                            break;
                        case 1:
                            tv.setBackgroundResource(R.drawable.shape_class4);
                            break;
                        case 2:
                            tv.setBackgroundResource(R.drawable.shape_class5);
                            break;
                        case 3:
                            tv.setBackgroundResource(R.drawable.shape_class6);
                            break;
                    }
                    break;
                case 5:
                    switch (b) {
                        case 6:
                            tv.setBackgroundResource(R.drawable.shape_class);
                            break;
                        case 5:
                            tv.setBackgroundResource(R.drawable.shape_class1);
                            break;
                        case 4:
                            tv.setBackgroundResource(R.drawable.shape_class2);
                            break;
                        case 3:
                            tv.setBackgroundResource(R.drawable.shape_class3);
                            break;
                        case 2:
                            tv.setBackgroundResource(R.drawable.shape_class4);
                            break;
                        case 1:
                            tv.setBackgroundResource(R.drawable.shape_class5);
                            break;
                        case 0:
                            tv.setBackgroundResource(R.drawable.shape_class6);
                            break;
                    }
                    break;


            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            params.setMargins(1, 1, 1, 1);
            tv.setLayoutParams(params);
            // tv.setGravity(Gravity.CENTER);
            tv.setText(Class + classroom + teacher);
            tv.setClickable(true);

            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("课程信息");
                    builder.setMessage("名称:" + Class + "\n" + "教室:" + classroom + "\n" + "教师:" + teacher + "\n" + "周次:" + week);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            });
            tv.setTextSize(10);
            tv.setTextColor(Color.WHITE);

        }
        return tv;
    }

    private void RemoveView() {
        layout1 = (LinearLayout) findViewById(R.id.one);
        layout2 = (LinearLayout) findViewById(R.id.two);
        layout3 = (LinearLayout) findViewById(R.id.three);
        layout4 = (LinearLayout) findViewById(R.id.four);
        layout5 = (LinearLayout) findViewById(R.id.five);
        layout6 = (LinearLayout) findViewById(R.id.six);
        layout7 = (LinearLayout) findViewById(R.id.seven);
        layout1.removeAllViews();
        layout2.removeAllViews();
        layout3.removeAllViews();
        layout5.removeAllViews();
        layout4.removeAllViews();
        layout6.removeAllViews();
        layout7.removeAllViews();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;

        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (theme.equals(preferences.getString("theme", "pink"))) {

        } else {
            Util.reload(this);

        }
    }

}
