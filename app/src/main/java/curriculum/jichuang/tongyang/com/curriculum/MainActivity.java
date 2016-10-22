package curriculum.jichuang.tongyang.com.curriculum;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.system.ErrnoException;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.client.CookieStore;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class MainActivity extends Activity {
    private EditText number;
    private EditText passname;
    private Button land;
    private CharSequence p;
    private CharSequence n;
    private Object o;
    private String S_number;
    private String S_passname;
    private MainActivity activity;
    private static CheckBox a_login;
    private CheckBox remember;
    private SharedPreferences sp;
    private ProgressDialog progressDialog;


    // private TextView result;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        setContentView(R.layout.activity_main);
        number = (EditText) findViewById(R.id.number);
        passname = (EditText) findViewById(R.id.passname);
        land = (Button) findViewById(R.id.land);
        //   result= (TextView) findViewById(R.id.result);
        remember = (CheckBox) findViewById(R.id.remember);
        a_login = (CheckBox) findViewById(R.id.a_login);
        SharedPreferences preferences = getSharedPreferences("userinfo", MODE_PRIVATE);
        String a = preferences.getString("username", "");
        String b = preferences.getString("password", "");
        remember.setChecked(true);
        if (a.equals("") && b.equals("")) {

            number.setText("");
            passname.setText("");
        } else {

            number.setText(a);
            passname.setText(b);
        }

        land.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {


                //判断好好密码是否为空

                if (number.getText().length() == 0 || passname.getText().length() == 0) {

                    Toast.makeText(MainActivity.this, "学号或者密码不能为空", Toast.LENGTH_SHORT).show();

                }
                // 登陆功能
                else {
                    ConnectivityManager con = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
                    boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
                    boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
                    String pass = passname.getText().toString();
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
            }
        });


    }

    public class MyAsyncTask extends AsyncTask<String, Integer, Integer> {
        @Override
        protected void onProgressUpdate(Integer... values) {
            progressDialog.setProgress(values[0]);

            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("登录中,请稍候...");
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
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, page_menu.class);
                startActivity(intent);
                finish();
            } else if (integer == 200) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "帐户名或密码错误", Toast.LENGTH_SHORT).show();

            } else if (integer == 404) {
                Toast.makeText(MainActivity.this, "404", Toast.LENGTH_SHORT).show();
            } else {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "登录异常请重试", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(integer);
        }
    }


}

