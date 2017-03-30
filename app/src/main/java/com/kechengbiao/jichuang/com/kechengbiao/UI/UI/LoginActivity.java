package com.kechengbiao.jichuang.com.kechengbiao.UI.UI;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kechengbiao.jichuang.com.kechengbiao.R;
import com.kechengbiao.jichuang.com.kechengbiao.UI.UI.Internet.HttpLogin;
import com.kechengbiao.jichuang.com.kechengbiao.UI.UI.Internet.KB;
import com.kechengbiao.jichuang.com.kechengbiao.UI.UI.Internet.getLibtary;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;


public class LoginActivity extends AppCompatActivity {
  private TextInputLayout textInputLayout_number;
    private  TextInputLayout textInputLayout_password;
    private EditText number;
    private  EditText password;
    private Button login;
    private ProgressDialog progressDialog;
    private TextView data;
    private Toolbar toolbar;
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
        setContentView(R.layout.activity_login);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("导入课表");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        number= (EditText) findViewById(R.id.number);
        password= (EditText) findViewById(R.id.passname);
        data= (TextView) findViewById(R.id.choose_data);
        textInputLayout_number= (TextInputLayout) findViewById(R.id.textinput_number);
        textInputLayout_password= (TextInputLayout) findViewById(R.id.textinput_password);
        SharedPreferences sharedPreferences=getSharedPreferences("userinfo",MODE_PRIVATE);
        if (!sharedPreferences.getString("username","").equals("")){
            number.setText(sharedPreferences.getString("username",""));
        }
        if (!sharedPreferences.getString("password","").equals("")){
           password.setText(sharedPreferences.getString("password",""));
        }
        login= (Button) findViewById(R.id.button_login);
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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
                builder.setTitle("选择学期");
                builder.setView(view);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int a=Integer.valueOf(pickerView.getValue());
                        data.setText(zc[a-1]);
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.show();
            }
        });
        if (data.getText().toString().contains("点击")) {
            login.setClickable(false);
            login.setBackgroundResource(R.drawable.button_login2);
        }
        data.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (data.getText().toString().contains("点击")){
                    login.setClickable(false);
                    login.setBackgroundResource(R.drawable.button_login2);
                }else {
                    if (number.getText().length()==0||password.getText().length()==0){
                        login.setClickable(false);
                        login.setBackgroundResource(R.drawable.button_login2);

                    }else {
                        login.setClickable(true);
                        login.setBackgroundResource(R.drawable.button_login);

                    }
                }
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
                if (data.getText().toString().contains("点击")){
                    login.setClickable(false);
                    login.setBackgroundResource(R.drawable.button_login2);
                }else {
                    if (number.getText().length()==0||password.getText().length()==0){
                        login.setClickable(false);
                        login.setBackgroundResource(R.drawable.button_login2);

                    }else {
                        login.setClickable(true);
                        login.setBackgroundResource(R.drawable.button_login);

                    }
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
                if (data.getText().toString().contains("点击")){

                    login.setClickable(false);
                    login.setBackgroundResource(R.drawable.button_login2);
                }else {
                    if (number.getText().length()==0||password.getText().length()==0){
                        login.setClickable(false);
                        login.setBackgroundResource(R.drawable.button_login2);

                    }else {
                        login.setClickable(true);
                        login.setBackgroundResource(R.drawable.button_login);

                    }
                }}
        });


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
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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
            progressDialog = new ProgressDialog(LoginActivity.this);
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
                GetKBAsynctask getKBAsynctask=new GetKBAsynctask();
                getKBAsynctask.execute(data.getText().toString().trim());
            } else if (integer == 200) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "帐户名或密码错误", Toast.LENGTH_SHORT).show();

            } else if (integer == 404) {
                Toast.makeText(LoginActivity.this, "404", Toast.LENGTH_SHORT).show();
            } else {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "登录异常请重试", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(integer);
        }
    }

    public class GetKBAsynctask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setTitle("正在导入，请稍候");
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                savekb(s);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressDialog.dismiss();
            Intent intent=new Intent();
            intent.setAction("getkb");
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            sendBroadcast(intent);
            finish();
        }

        @Override
        protected String doInBackground(String... params) {
            String data=params[0];
            SharedPreferences preferences1 = getSharedPreferences("CookieString",
                    Activity.MODE_PRIVATE);
            KB kb = new KB();
            String cookie1 = preferences1.getString("cookie", "");

            String kb3 = kb.EveryClass(cookie1,data);
            return kb3;
        }
    }

    private void savekb(String string) throws JSONException {
        JSONArray array = new JSONArray();
        Document document = Jsoup.parse(string);
        Elements tr = document.select("table").select("tr");
     String beizhu=   tr.get(tr.size()-1).select("td").text();

        for (int i = 2; i < tr.size() - 1; i++) {
            Elements td = tr.get(i).select("td");
            for (int j = 0; j < td.size(); j++) {
                JSONObject object = new JSONObject();
                Elements elements = td.get(j).select("div[class=kbcontent]").select("font");
                if (elements.size() == 6) {
                    Util util = new Util();
                    String week = elements.get(1).text();
                    String w = week;
                    String week1 = elements.get(4).text();
                    String w1 = week1;
                    week = util.getZc(week).trim();
                    week1 = util.getZc(week1).trim();
                    Element element = td.get(j).select("div.kbcontent").get(0);
                    String Class = element.ownText();
                    String teacher = elements.get(0).text();
                    String classroom = elements.get(2).text();
                    String teacher1 = elements.get(3).text();
                    String classroom1 = elements.get(5).text();
                    object.put("week", week);
                    object.put("Class", Class);
                    object.put("w", w);
                    object.put("w1", w1);
                    object.put("teacher", teacher);
                    object.put("classroom", classroom);
                    object.put("week1", week1);
                    object.put("teacher1", teacher1);
                    object.put("classroom1", classroom1);
                    array.put(object);
                } else if (elements.size() == 3) {
                    Util util = new Util();
                    String week = elements.get(1).text();
                    String w = week;
                    week = util.getZc(week).trim();
                    Element element = td.get(j).select("div.kbcontent").get(0);
                    String Class = element.ownText();
                    String teacher = elements.get(0).text();
                    String classroom = elements.get(2).text();
                    object.put("w", w);
                    object.put("week", week);
                    object.put("Class", Class);
                    object.put("teacher", teacher);
                    object.put("classroom", classroom);
                    array.put(object);
                } else {
                    object.put("zero", "");
                    array.put(object);
                }

            }
        }
        SharedPreferences sharedPreferences = getSharedPreferences("kb", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("kb", array.toString());
        editor.putString("beizhu",beizhu);
        editor.commit();
    }

}
