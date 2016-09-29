package curriculum.jichuang.tongyang.com.curriculum;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
    private CheckBox a_login;
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

        land.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                //判断好好密码是否为空

                if (number.getText().length() == 0 || passname.getText().length() == 0) {

                    Toast.makeText(MainActivity.this, "学号或者密码不能为空", Toast.LENGTH_SHORT).show();

                }
                // 登陆功能
                else {

                    String pass = passname.getText().toString();
                    String num = number.getText().toString();
                    String mes[]={
                     num ,
                      pass
                    };
                   MyAsyncTask asyncTask=new MyAsyncTask();
                    asyncTask.execute(mes);

                }
            }
        });


    }
     public  class    MyAsyncTask extends  AsyncTask<String,Integer,Integer>{
         @Override
         protected void onProgressUpdate(Integer... values) {
            progressDialog.setProgress(values[0]);

             super.onProgressUpdate(values);
         }

         @Override
         protected void onPreExecute() {
             progressDialog=new ProgressDialog(MainActivity.this);
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

             String num=params[0];
             String pass=params[1];
             HttpLogin httpLogin=new HttpLogin();

             //CookieStore cookieStore=  httpLogin.login(num,pass);


           String cookieStore=         httpLogin.login(num,pass);

             if (httpLogin.getCode()==302){
                 SharedPreferences mySharedPreferences= getSharedPreferences("CookieString",
                         Activity.MODE_PRIVATE);

                 SharedPreferences.Editor editor = mySharedPreferences.edit();
                 editor.putString("cookie",cookieStore);
                 editor.commit();
                 return httpLogin.getCode();

             }else if (httpLogin.getCode()==200){


                 return httpLogin.getCode();
             }else if (httpLogin.getCode()==404){

                 return httpLogin.getCode();
             }


             return null;
         }


         @Override
         protected void onPostExecute(Integer integer) {
             if (integer==302){
                 progressDialog.dismiss();
                 Intent intent=new Intent();
                 intent.setClass(MainActivity.this,MenuActivity.class);
                 startActivity(intent);
                 finish();
             }else if (integer==200){
                 progressDialog.dismiss();
                 Toast.makeText(MainActivity.this,"帐户名或密码错误",Toast.LENGTH_SHORT).show();

             }else if (integer==404){
                 Toast.makeText(MainActivity.this,"404",Toast.LENGTH_SHORT).show();
             }else {
                 progressDialog.dismiss();
                 Toast.makeText(MainActivity.this,"登录异常请重试",Toast.LENGTH_SHORT).show();
             }
             super.onPostExecute(integer);
         }
     }


}

