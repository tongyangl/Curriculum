package com.kechengbiao.jichuang.com.kechengbiao.UI.UI.UI;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kechengbiao.jichuang.com.kechengbiao.R;
import com.kechengbiao.jichuang.com.kechengbiao.UI.UI.Ascytask.GradeSearchAsynctask;
import com.kechengbiao.jichuang.com.kechengbiao.UI.UI.Internet.HttpLogin;
import com.kechengbiao.jichuang.com.kechengbiao.UI.UI.Internet.KB;
import com.kechengbiao.jichuang.com.kechengbiao.UI.UI.Internet.getLibtary;
import com.kechengbiao.jichuang.com.kechengbiao.UI.UI.LoginActivity;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

/**
 * Created by 佟杨 on 2017/3/16.
 */

public class GradeSearchActivity extends AppCompatActivity {
    private TextInputLayout textInputLayout_number;
    private TextInputLayout textInputLayout_password;
    private EditText number;
    private EditText password;
    private Button login;
    private TextView data;
    private ProgressDialog progressDialog;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_grade_search);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        number = (EditText) findViewById(R.id.number);
        password = (EditText) findViewById(R.id.passname);
        data = (TextView) findViewById(R.id.choose_data);
        textInputLayout_number = (TextInputLayout) findViewById(R.id.textinput_number);
        textInputLayout_password = (TextInputLayout) findViewById(R.id.textinput_password);
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);
        if (!sharedPreferences.getString("username", "").equals("")) {
            number.setText(sharedPreferences.getString("username", ""));
        }
        if (!sharedPreferences.getString("password", "").equals("")) {
            password.setText(sharedPreferences.getString("password", ""));
        }
        login = (Button) findViewById(R.id.button_login);
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GradeSearchActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.layout_choosezc, null);
                final NumberPickerView pickerView = (NumberPickerView) view.findViewById(R.id.picker);
                final String[] zc = new String[]{
                        "2012-2013-1", "2012-2013-2", "2013-2014-1", "2013-2014-2", "2014-2015-1", "2014-2015-2",
                        "2015-2016-1", "2015-2016-2", "2016-2017-1", "2016-2017-2", "2017-2018-1", "2017-2018-2",
                };
                pickerView.setDisplayedValues(zc);
                pickerView.setMaxValue(11);
                pickerView.setMinValue(1);
                pickerView.setValue(5);
                builder.setView(view);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int a = Integer.valueOf(pickerView.getValue());
                        data.setText(zc[a - 1]);
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.show();
            }
        });
        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (number.getText().length() == 0 || password.getText().length() == 0) {
                    login.setClickable(false);
                    login.setBackgroundResource(R.drawable.button_login2);

                } else {
                    login.setClickable(true);
                    login.setBackgroundResource(R.drawable.button_login);
                }
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (number.getText().length() == 0 || password.getText().length() == 0) {
                    login.setClickable(false);
                    login.setBackgroundResource(R.drawable.button_login2);

                } else {
                    login.setClickable(true);
                    login.setBackgroundResource(R.drawable.button_login);
                }
            }
        });
        if (number.getText().length() == 0 || password.getText().length() == 0) {
            login.setClickable(false);
            login.setBackgroundResource(R.drawable.button_login2);

        } else {
            login.setClickable(true);
            login.setBackgroundResource(R.drawable.button_login);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager con = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
                boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
                boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
                String pass = password.getText().toString();
                String num = number.getText().toString();
                String mes[] = {
                        num,
                        pass
                };
                if (wifi | internet) {
                    SharedPreferences preferences1 = getSharedPreferences("userinfo", MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = preferences1.edit();
                    editor1.putString("username", num);
                    editor1.putString("password", pass);
                    editor1.commit();
                    SharedPreferences preferences = getSharedPreferences("userinfo" + String.valueOf(num), MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("username", num);
                    editor.putString("password", pass);
                    editor.commit();
                    MyAsyncTask asyncTask = new MyAsyncTask();
                    asyncTask.execute(mes);

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(GradeSearchActivity.this);
                    builder.setTitle("提示");
                    builder.setMessage("系统检测到无网络连接!");
                    builder.setCancelable(false);
                    builder.setPositiveButton("知道了！", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.create().show();
                }
            }
        });
    }

    public class MyAsyncTask extends AsyncTask<String, Integer, Integer> {

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(GradeSearchActivity.this);
            progressDialog.setMessage("导入中,请稍候...");
            progressDialog.setTitle("亲爱的");
            progressDialog.setCancelable(true);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected void onCancelled() {
            progressDialog.dismiss();
            super.onCancelled();
        }

        @Override

        protected Integer doInBackground(String... params) {
            String num = params[0];
            String pass = params[1];
            HttpLogin httpLogin = new HttpLogin();
            //CookieStore cookieStore=  httpLogin.login(num,pass);
            String cookieStore = httpLogin.login(num, pass);
            getLibtary libtary = new getLibtary();
            String person = libtary.get(cookieStore);

            if (httpLogin.getCode() == 302) {
                SharedPreferences mySharedPreferences = getSharedPreferences("CookieString",
                        Activity.MODE_PRIVATE);

                SharedPreferences.Editor editor = mySharedPreferences.edit();
                editor.putString("cookie", cookieStore);
                editor.putString("person", person);
                editor.commit();
                return httpLogin.getCode();

            } else if (httpLogin.getCode() == 200) {


                return httpLogin.getCode();
            } else if (httpLogin.getCode() == 404) {

                return httpLogin.getCode();
            }


            return null;
        }


        @Override
        protected void onPostExecute(Integer integer) {
            if (integer == 302) {
                progressDialog.dismiss();
                GradeSearchAsynctask asynctask = new GradeSearchAsynctask(progressDialog, GradeSearchActivity.this);
                SharedPreferences preferences1 = getSharedPreferences("CookieString",
                        Activity.MODE_PRIVATE);
                String cookie1 = preferences1.getString("cookie", "");
                String args[] = {

                        cookie1,
                        data.getText().toString().trim()
                };
                asynctask.execute(args);
            } else if (integer == 200) {
                progressDialog.dismiss();
                Toast.makeText(GradeSearchActivity.this, "帐户名或密码错误", Toast.LENGTH_SHORT).show();

            } else if (integer == 404) {
                Toast.makeText(GradeSearchActivity.this, "404", Toast.LENGTH_SHORT).show();
            } else {
                progressDialog.dismiss();
                Toast.makeText(GradeSearchActivity.this, "登录异常请重试", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(integer);
        }
    }

}