package curriculum.jichuang.tongyang.com.curriculum;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;

import android.view.View;

import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;
import java.util.Random;


/**
 * Created by tongyang on 2016/9/26.
 */

public class KBActivity extends Activity {
    private List<SparseArray<String>> sparseArrayList;
    private ProgressDialog dialog;
    private LinearLayout layout;
    private Spinner sp;
    private ArrayAdapter<String> array;
    private List<String> list;
    private ScrollView scrollView;
    private LinearLayout ll;
    private LinearLayout layout1;
    private LinearLayout layout2;
    private LinearLayout layout3;
    private LinearLayout layout4;
    private LinearLayout layout5;
    private LinearLayout layout6;
    private LinearLayout layout7;
   private LinearLayout week;
    private Button popu;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.kb);
        week= (LinearLayout) findViewById(R.id.week);
        week.setBackgroundColor(Color.argb(100, 0, 192, 255));
        ll = (LinearLayout) findViewById(R.id.kb);
        sp = (Spinner) findViewById(R.id.sp);
        popu= (Button) findViewById(R.id.popu);
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
        SharedPreferences preferences1 = getSharedPreferences("userinfo",
                Activity.MODE_PRIVATE);
        String user1 = preferences1.getString("username", "");
        SharedPreferences preferences13 = getSharedPreferences("userinfo" + user1,
                Activity.MODE_PRIVATE);
        String a = "";
        for (int i = 0; i <= 20; i++) {
            a += preferences13.getString(String.valueOf(i), "");
            Log.d("aaaaa", a);

        }
        if (a.equals("")) {
            String user2 = preferences1.getString("username", "");
            KbAsyncTask kbAsyncTask = new KbAsyncTask();
            kbAsyncTask.execute(user2);

            loadClass();

        } else {

            loadClass();
        }
          popu.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                  PopupWindow popupWindow=new PopupWindow();

              }
          });
    }


    private void loadClass() {
        {

            sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    {
                        RemoveView();
                        SharedPreferences preferences1 = getSharedPreferences("userinfo",
                                Activity.MODE_PRIVATE);
                        String user = preferences1.getString("username", "");
                        SharedPreferences getkb = getSharedPreferences("userinfo" + user,
                                Activity.MODE_PRIVATE);
                        String User = getkb.getString(String.valueOf(position), "");
                        if (!User.equals("")) {
                            Log.d("pppp", String.valueOf(position));

                            Document document = Jsoup.parse(User);

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
                                        Log.d("&&&&&&&&", String.valueOf(curriculum.length()));
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

                        } else {

                            Toast.makeText(KBActivity.this, "尚未获取本周课程表", Toast.LENGTH_SHORT).show();
                        }


                        //  super.onPostExecute(string);
                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

            });
        }


    }

    private class KbAsyncTask extends AsyncTask<String, Integer, Integer> {

        protected void onPostExecute(Integer result) {

            dialog.dismiss();
            Toast.makeText(KBActivity.this, "导入成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(KBActivity.this);
            dialog.setIndeterminate(true);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setCancelable(false);
            dialog.setTitle("检测到您第一次登录尚未导入课程表");
            dialog.setMessage("系统正在导入课程表,请耐心等待");

            dialog.show();

        }

        @Override
       /* protected void onProgressUpdate(Integer... values) {


            dialog.setProgress(dialog.getProgress()+values[0]);

        }
*/
        protected Integer doInBackground(String... params) {
            String user = params[0];
            KB kb = new KB();
            SharedPreferences spf = getSharedPreferences("userinfo" + user,
                    Activity.MODE_PRIVATE);
            SharedPreferences.Editor e = spf.edit();
            for (int i = 0; i <= 20; i++) {
                if (i == 0) {

                    SharedPreferences preferences1 = getSharedPreferences("CookieString",
                            Activity.MODE_PRIVATE);
                    String cookie1 = preferences1.getString("cookie", "");
                    String kb3 = kb.GetClass(cookie1);
                    Log.d("iiii" + String.valueOf(i), kb3);

                    e.putString(String.valueOf(i), kb3);


                } else {
                    SharedPreferences preferences1 = getSharedPreferences("CookieString",
                            Activity.MODE_PRIVATE);
                    String cookie2 = preferences1.getString("cookie", "");

                    String kb2 = kb.EveryClass(cookie2, String.valueOf(i), "");
                    e.putString(String.valueOf(i), kb2);

                }
                e.commit();
                Log.d("IIIII", String.valueOf(i));
            }


            return 0;
        }
    }

    private TextView createTv(int i, int j, final String text) {
        TextView tv = new TextView(this);
        Random random = new java.util.Random();
        int c = random.nextInt(10);


        int deepbule[] = {
                0, 191, 255
        };
        if (text.length() == 3) {

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
            tv.setLayoutParams(params);


            return tv;
        } else {
            if (j == -1) {

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.5f);
                params.setMargins(1,1,1,1);
                tv.setLayoutParams(params);
                tv.setGravity(Gravity.CENTER);
                tv.setText(text);
                tv.setMaxEms(0);
                tv.setTextSize(15);
                //tv.getBackground().setAlpha(100);
                tv.setBackgroundColor(Color.argb(180, deepbule[0], deepbule[1], deepbule[2]));

                //   tv.setBackgroundColor(Color.argb(155, 0, 255, 0));

                return tv;
            } else if (j == -2) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.5f);
                params.setMargins(1,1,1,1);

                tv.setLayoutParams(params);
                tv.setGravity(Gravity.CENTER);
                tv.setText(text);
                tv.setMaxEms(0);

                tv.setBackgroundColor(Color.argb(180, deepbule[0], deepbule[1], deepbule[2]));
                tv.setTextSize(15);


                return tv;
            } else if (j == -3) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 7.0f);
                params.setMargins(1,1,1,1);
                tv.setLayoutParams(params);
                tv.setGravity(Gravity.CENTER);
                tv.setText(text);
                // tv.setMaxEms(0);
                    tv.setBackgroundColor(Color.argb(180, 0, 255, 0));
                tv.setTextSize(10);


                return tv;
            } else {


                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
                params.setMargins(1,1,1,1);

                tv.setLayoutParams(params);

                // tv.setGravity(Gravity.CENTER);
                tv.setText(text);
                //tgetBackground().setv.
                switch (c) {
                    case 1:
                        tv.setBackgroundColor(Color.argb(180,30 ,144 ,255));
                        break;
                    case 2:
                        tv.setBackgroundColor(Color.argb(180, 191 ,62 ,255	));
                        break;
                    case 3:
                        tv.setBackgroundColor(Color.argb(180, 255 ,20 ,147));
                        break;
                    case 4:
                        tv.setBackgroundColor(Color.argb(180, 255, 64 ,64));
                        break;
                    case 5:
                        tv.setBackgroundColor(Color.argb(180, 0 ,255 ,127));
                        break;
                    case 6:
                        tv.setBackgroundColor(Color.argb(180, 	0 ,255 ,0));
                        break;
                    case 7:
                        tv.setBackgroundColor(Color.argb(180, 255 ,0 ,0));
                        break;
                    case 8:
                        tv.setBackgroundColor(Color.argb(180, 148, 0 ,211));
                        break;
                    case 9:
                        tv.setBackgroundColor(Color.argb(180, 	255 ,69, 0));
                        break;
                    case 10:
                        tv.setBackgroundColor(Color.argb(180, 0, 255, 0));
                        break;
                }
                tv.setTextSize(10);
                if (text.length()==3){
                    return tv;
                }else {
                    tv.setClickable(true);
                     tv.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {

                              final AlertDialog.Builder builder=new AlertDialog.Builder(KBActivity.this);
                             builder.setCancelable(true);
                             builder.setMessage(text);

                             builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                 @Override
                                 public void onClick(DialogInterface dialog, int which) {


                                 }
                             });
                             builder.create();
                             builder.show();
                         }
                     });
                    return tv;
                }

            }

        }


        // 指定高度和宽度

    }

    private void RemoveView() {
        layout1 = (LinearLayout) findViewById(R.id.one);
        layout2 = (LinearLayout) findViewById(R.id.two);
        layout3 = (LinearLayout) findViewById(R.id.three);
        layout4 = (LinearLayout) findViewById(R.id.four);
        layout5 = (LinearLayout) findViewById(R.id.five);
        layout6 = (LinearLayout) findViewById(R.id.six);
        layout7 = (LinearLayout) findViewById(R.id.other);
        layout1.removeAllViews();
        layout2.removeAllViews();
        layout3.removeAllViews();
        layout5.removeAllViews();
        layout4.removeAllViews();
        layout6.removeAllViews();
        layout7.removeAllViews();
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

        tv = createTv(i, j, text);
        tv.setTextColor(Color.WHITE);


        //tv.setBackgroundColor(Color.argb(100, 0, 0, 0));
        layout.addView(tv);
    }

}


