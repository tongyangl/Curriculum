package curriculum.jichuang.tongyang.com.curriculum;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tongyang on 2016/9/26.
 */

public class KBActivity extends Activity {
    private ProgressDialog dialog;
    private LinearLayout layout;
    private Spinner sp;
    private ArrayAdapter<String> array;
    private List<String> list;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kb);
        sp = (Spinner) findViewById(R.id.sp);
        list = new ArrayList<>();
        list.add("全部");
        for (int i = 1; i <= 20; i++) {
            list.add("第" + i + "周");
        }
        array = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        array.setDropDownViewResource((android.R.layout.simple_spinner_dropdown_item));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            sp.setDropDownVerticalOffset(70);
        }

        sp.setAdapter(array);

        if (false) {


        } else {
            sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    {
                        if (position==0) {

                            SharedPreferences mySharedPreferences = getSharedPreferences("CookieString",
                                    Activity.MODE_PRIVATE);
                            String cookie = mySharedPreferences.getString("cookie", "");
                            KbAsyncTask kbAsyncTask = new KbAsyncTask();
                            String str[]={
                                    cookie,
                                    "0"
                            };
                            kbAsyncTask.execute(str);
                        }else {
                            String zc= String.valueOf(position+1);
                            SharedPreferences mySharedPreferences = getSharedPreferences("CookieString",
                                    Activity.MODE_PRIVATE);
                            String cookie = mySharedPreferences.getString("cookie", "");
                            String Str[]={
                                    cookie,
                                    "1",
                                    zc
                            };
                            KbAsyncTask kbAsyncTask = new KbAsyncTask();
                            kbAsyncTask.execute(Str);
                        }

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

            });
        }

    }

    private class KbAsyncTask extends AsyncTask<String, Integer, String> {


        protected void onPostExecute(String string) {
            dialog.dismiss();
            Document document = Jsoup.parse(string);

            Elements tr = document.select("table").select("tr");
            for (int i = 2; i < tr.size() - 1; i++) {
                //   Log.d("!!!!!!!!", tr.size()+"");
                Elements td = tr.get(i).select("td");
                //  Log.d("#######", td.size()+"");
                for (int j = -1; j < td.size(); j++) {

                    if (j == -1) {
                        int a = i * 2 - 3;
                        int b = i * 2 - 2;
                        String c, d;
                        if (a < 10) {
                            c = "0" + a;

                        } else {
                            c = a + "";
                        }
                        if (b < 10) {
                            d = "0" + b;

                        } else {
                            d = b + "";
                        }


                        String k = "第" + c + " " + d + "节课";
                        addView(i, j, k);
                        Log.d("=====", k);
                    } else {

                        String curriculum = td.get(j).text();
                        addView(i, j, curriculum);
                        Log.d("----", curriculum);
                    }


                }
            }

            String beizhu = "备注";
            addView(8, -2, beizhu);
            Elements td = tr.get(tr.size() - 1).select("td");
            String cont = td.get(0).text();
            addView(8, -3, cont);
            super.onPostExecute(string);
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(KBActivity.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setCancelable(false);
            dialog.setTitle("请稍候");
            dialog.setMessage("正在偷取课程表...");
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            dialog.setProgress(values[0]);
            super.onProgressUpdate(values);
        }

        protected String doInBackground(String... params) {
            String cookie = params[0];
            String code=params[1];

            if (code.equals("0")){
                KB kb = new KB();
                return kb.GetClass(cookie);
            }else {
                KB kb = new KB();
                 String zc=params[2];
                return      kb.EveryClass(cookie,zc,"");

            }

        }

    }

    private TextView createTv(int j, String text) {
        TextView tv = new TextView(this);
        if (j == -1) {

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, 280, 0.5f);
            tv.setLayoutParams(params);
            tv.setGravity(Gravity.CENTER);
            tv.setText(text);
            tv.setMaxEms(0);
            tv.setTextSize(15);
            return tv;
        } else if (j == -2) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, 100, 0.5f);
            tv.setLayoutParams(params);
            tv.setGravity(Gravity.CENTER);
            tv.setText(text);
            tv.setMaxEms(0);
            tv.setTextSize(15);
            return tv;
        } else if (j == -3) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, 100, 7.0f);
            tv.setLayoutParams(params);
            tv.setGravity(Gravity.CENTER);
            tv.setText(text);
            // tv.setMaxEms(0);
            tv.setTextSize(10);
            return tv;
        } else {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, 280, 1.0f);
            tv.setLayoutParams(params);
            // tv.setGravity(Gravity.CENTER);
            tv.setText(text);
            tv.setTextSize(10);
            return tv;
        }
        // 指定高度和宽度

    }
        private  void  RemoveView(){



        }
    private void addView(int i, int j, String text) {
        TextView tv;
        switch (i) {
            case 2:
                layout = (LinearLayout) findViewById(R.id.one);
                break;
            case 3:
                layout = (LinearLayout) findViewById(R.id.two);
                break;
            case 4:
                layout = (LinearLayout) findViewById(R.id.three);
                break;
            case 5:
                layout = (LinearLayout) findViewById(R.id.four);
                break;
            case 6:
                layout = (LinearLayout) findViewById(R.id.five);
                break;
            case 7:
                layout = (LinearLayout) findViewById(R.id.six);
                break;
            case 8:
                layout = (LinearLayout) findViewById(R.id.other);
                break;
        }
        tv = createTv(j, text);

        layout.addView(tv);
    }
}
