package curriculum.jichuang.tongyang.com.curriculum;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by tongyang on 2016/10/7.
 */

public class KBFragment extends Fragment {
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
    private View view;
    private String Week;
    private String time;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.kb, container, false);
        view = v;
        week = (LinearLayout) v.findViewById(R.id.week);
        week.setBackgroundColor(Color.argb(100, 0, 192, 255));
        ll = (LinearLayout) v.findViewById(R.id.kb);
        sp = (Spinner) v.findViewById(R.id.sp);
        popu = (Button) v.findViewById(R.id.popu);
        layout1 = (LinearLayout) v.findViewById(R.id.one);
        layout2 = (LinearLayout) v.findViewById(R.id.two);
        layout3 = (LinearLayout) v.findViewById(R.id.three);
        layout4 = (LinearLayout) v.findViewById(R.id.four);
        layout5 = (LinearLayout) v.findViewById(R.id.five);
        layout6 = (LinearLayout) v.findViewById(R.id.six);
        layout7 = (LinearLayout) v.findViewById(R.id.other);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list = new ArrayList<>();
        list.add("全部");
        for (int i = 1; i <= 20; i++) {
            list.add("第" + i + "周");
        }

        array = new ArrayAdapter<String>(getActivity(), R.layout.spinner_stytle, list);
        //  array.setDropDownViewResource((android.R.layout.simple_spinner_dropdown_item));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            sp.setDropDownVerticalOffset(70);
        }


        sp.setAdapter(array);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Week", Context.MODE_PRIVATE);
        sp.setSelection(Integer.parseInt(sharedPreferences.getString("week", "1")));
        final SharedPreferences preferences1 = getActivity().getSharedPreferences("userinfo",
                Activity.MODE_PRIVATE);
        String user1 = preferences1.getString("username", "");
        SharedPreferences preferences13 = getActivity().getSharedPreferences("userinfo" + user1,
                Activity.MODE_PRIVATE);
        String a = "";
        for (int i = 0; i <= 20; i++) {
            a += preferences13.getString(String.valueOf(i), "");
            Log.d("aaaaa", a);

        }
        if (a.equals("")) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("提示");
            builder.setMessage("尚未导入当前课表，是否立即导入？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences preferences3 = getActivity().getSharedPreferences("Time",
                            Activity.MODE_PRIVATE);

                    String user2 = preferences1.getString("username", "");
                    KbAsyncTask kbAsyncTask = new KbAsyncTask();
                    String asd[] = {
                            user2, preferences3.getString("time", "2016-2017-1")
                    };
                    kbAsyncTask.execute(asd);

                    loadClass();
                }
            });
            builder.setNegativeButton("取消", null);
            builder.show();


        } else {

            loadClass();
        }
        popu.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                PopupWindow popupWindow = new PopupWindow();
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                LayoutInflater inflater = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View view1 = inflater.inflate(R.layout.popuwindow, null);
                int h = getActivity().getWindowManager().getDefaultDisplay().getHeight();
                // int w = getActivity().getWindowManager().getDefaultDisplay().getWidth();
                popupWindow.setContentView(view1);
                popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setHeight(h / 2);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                popupWindow.setOutsideTouchable(true);
                popupWindow.setAnimationStyle(PopupWindow.INPUT_METHOD_FROM_FOCUSABLE);
                ListView listView = (ListView) view1.findViewById(R.id.setting);
                final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Week", Context.MODE_PRIVATE);

                Map<String, Object> d1 = new HashMap<String, Object>();
                d1.put("1", "当前学期");
                d1.put("2", "2016-2017-1");
                Map<String, Object> d2 = new HashMap<String, Object>();
                d2.put("1", "当前周数");
                d2.put("2", sharedPreferences.getString("week", "1"));
                Map<String, Object> d3 = new HashMap<String, Object>();
                d3.put("1", "添加桌面课表");
                d3.put("2", "");
                data.add(d1);
                data.add(d2);
                data.add(d3);
                final MyBaseAdptar baseAdptar = new MyBaseAdptar(getActivity(), data);

                listView.setAdapter(baseAdptar);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                        if (position == 1) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            LayoutInflater layoutInflater = getActivity().getLayoutInflater();
                            List<String> list = new ArrayList<String>();
                            list.add("全部");
                            for (int i = 1; i < 20; i++) {
                                list.add(String.valueOf(i));
                            }

                            View v = layoutInflater.inflate(R.layout.weekset_dialog, null);
                            PickerView pickerView = (PickerView) v.findViewById(R.id.week_pickeview);
                            pickerView.setData(list);
                            pickerView.setAlpha(0.9f);
                            pickerView.setOnSelectListener(new PickerView.onSelectListener() {
                                @Override
                                public void onSelect(String text) {
                                    Week = text;
                                }
                            });
                            builder.setView(v);
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Week", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("week", Week);
                                    editor.commit();
                                    Map<String, Object> d2 = new HashMap<String, Object>();
                                    if (Week.equals("全部")) {
                                        Week = String.valueOf(0);
                                    }
                                    d2.put("1", "当前周数");
                                    d2.put("2", Week);
                                    data.set(1, d2);
                                    baseAdptar.notifyDataSetChanged();
                                    sp.setSelection(Integer.parseInt(Week));
                                }
                            });
                            builder.setNegativeButton("取消", null);
                            Window dialogWindow = builder.show().getWindow();
                            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                            dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
                            lp.alpha = 0.7f;
                            lp.width = 600;
                            lp.height = 600;
                            dialogWindow.setAttributes(lp);
                        } else if (position == 0) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            LayoutInflater layoutInflater = getActivity().getLayoutInflater();
                            List<String> data1 = new ArrayList<String>();
                            data1.add("2016-2017-1");
                            data1.add("2015-2016-2");
                            data1.add("2015-2016-1");
                            data1.add("2014-2015-2");
                            data1.add("2014-2015-1");
                            data1.add("2013-2014-2");
                            data1.add("2013-2014-1");
                            data1.add("2012-2013-2");
                            data1.add("2012-2013-1");
                            data1.add("2011-2012-1");
                            View v = layoutInflater.inflate(R.layout.set_time, null);
                            PickerView pickerView = (PickerView) v.findViewById(R.id.set_time_pick);
                            pickerView.setData(data1);
                            pickerView.setAlpha(0.9f);
                            pickerView.setOnSelectListener(new PickerView.onSelectListener() {
                                @Override
                                public void onSelect(String text) {
                                    time = text;
                                }

                            });
                            builder.setView(v);
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Time", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("time", time);
                                    editor.commit();
                                    Map<String, Object> d8 = new HashMap<String, Object>();
                                    d8.put("1", "当前学期");
                                    d8.put("2", time);
                                    data.set(0, d8);
                                    baseAdptar.notifyDataSetChanged();
                                    KbAsyncTask task = new KbAsyncTask();
                                    SharedPreferences preferences1 = getActivity().getSharedPreferences("userinfo",
                                            Activity.MODE_PRIVATE);

                                    String user2 = preferences1.getString("username", "");
                                    String str[] = {
                                            user2, time
                                    };
                                    task.execute(str);
                                }
                            });

                            builder.setNegativeButton("取消", null);
                            Window dialogWindow = builder.show().getWindow();
                            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                            dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
                            lp.alpha = 0.7f;
                            lp.width = 600;
                            lp.height = 600;
                            dialogWindow.setAttributes(lp);
                        }
                        if (position == 2) {


                        }
                    }
                });
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    popupWindow.showAsDropDown(sp, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                }

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
                        SharedPreferences preferences1 = getActivity().getSharedPreferences("userinfo",
                                Activity.MODE_PRIVATE);
                        String user = preferences1.getString("username", "");
                        SharedPreferences getkb = getActivity().getSharedPreferences("userinfo" + user,
                                Activity.MODE_PRIVATE);
                        String User = getkb.getString(String.valueOf(position), "");
                        if (!User.equals("")) {
                            Log.d("pppp", String.valueOf(position));

                            Document document = Jsoup.parse(User);

                            Elements tr = document.select("table").select("tr");
                            for (int i = 2; i < tr.size() - 1; i++) {

                                Elements td = tr.get(i).select("td");

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

                            Toast.makeText(getActivity(), "尚未获取本周课程表", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getActivity(), "导入成功", Toast.LENGTH_SHORT).show();


        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
            dialog.setIndeterminate(true);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setCancelable(false);
            dialog.setTitle("提示");
            dialog.setMessage("系统正在导入课程表,时间较长,请耐心等待");
            dialog.show();

        }

        @Override
       /* protected void onProgressUpdate(Integer... values) {


            dialog.setProgress(dialog.getProgress()+values[0]);

        }
*/
        protected Integer doInBackground(String... params) {
            String user = params[0];
            String time = params[1];
            KB kb = new KB();

            SharedPreferences spf = getActivity().getSharedPreferences("userinfo" + user,
                    Activity.MODE_PRIVATE);
            SharedPreferences.Editor e = spf.edit();
            for (int i = 0; i <= 20; i++) {
                if (i == 0) {

                    SharedPreferences preferences1 = getActivity().getSharedPreferences("CookieString",
                            Activity.MODE_PRIVATE);
                    String cookie1 = preferences1.getString("cookie", "");
                    String kb3 = kb.GetClass(cookie1);
                    Log.d("iiii" + String.valueOf(i), kb3);

                    e.putString(String.valueOf(i), kb3);

                } else {
                    SharedPreferences preferences1 = getActivity().getSharedPreferences("CookieString",
                            Activity.MODE_PRIVATE);
                    String cookie2 = preferences1.getString("cookie", "");
                    String kb2 = kb.EveryClass(cookie2, String.valueOf(i), time);
                    e.putString(String.valueOf(i), kb2);
                }
                e.commit();
                Log.d("IIIII", String.valueOf(i));
            }


            return 0;
        }
    }

    /*    protected void onDestroy() {
            super.onDestroy();
            WindowManager mWindowManager=getWindowManager();

            mWindowManager.removeView(dialog);
        }*/
    private TextView createTv(int i, int j, final String text) {
        TextView tv = new TextView(getActivity());
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
                params.setMargins(1, 1, 1, 1);
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
                params.setMargins(1, 1, 1, 1);

                tv.setLayoutParams(params);
                tv.setGravity(Gravity.CENTER);
                tv.setText(text);
                tv.setMaxEms(0);

                tv.setBackgroundColor(Color.argb(180, deepbule[0], deepbule[1], deepbule[2]));
                tv.setTextSize(15);


                return tv;
            } else if (j == -3) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 7.0f);
                params.setMargins(1, 1, 1, 1);
                tv.setLayoutParams(params);
                tv.setGravity(Gravity.CENTER);
                tv.setText(text);
                // tv.setMaxEms(0);
                tv.setBackgroundColor(Color.argb(180, 0, 255, 0));
                tv.setTextSize(10);


                return tv;
            } else {


                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
                params.setMargins(1, 1, 1, 1);

                tv.setLayoutParams(params);

                // tv.setGravity(Gravity.CENTER);
                tv.setText(text);
                //tgetBackground().setv.
                switch (c) {
                    case 0:
                        tv.setBackgroundColor(Color.argb(180, 0, 245, 255));
                        break;
                    case 1:
                        tv.setBackgroundColor(Color.argb(180, 0, 245, 255));
                        break;
                    case 2:
                        tv.setBackgroundColor(Color.argb(180, 0, 255, 0));
                        break;
                    case 3:
                        tv.setBackgroundColor(Color.argb(180, 192, 255, 62));
                        break;
                    case 4:
                        tv.setBackgroundColor(Color.argb(180, 0, 206, 209));
                        break;
                    case 5:
                        tv.setBackgroundColor(Color.argb(180, 218, 165, 32));
                        break;
                    case 6:
                        tv.setBackgroundColor(Color.argb(180, 250, 128, 114));
                        break;
                    case 7:
                        tv.setBackgroundColor(Color.argb(180, 255, 0, 255));
                        break;
                    case 8:
                        tv.setBackgroundColor(Color.argb(180, 255, 64, 64));
                        break;
                    case 9:
                        tv.setBackgroundColor(Color.argb(180, 255, 20, 147));
                        break;
                    case 10:
                        tv.setBackgroundColor(Color.argb(180, 105, 105, 105));
                        break;
                }
                tv.setTextSize(10);
                if (text.length() == 3) {
                    return tv;
                } else {
                    tv.setClickable(true);
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                layout = (LinearLayout) view.findViewById(R.id.one);
                break;
            case 3:
                layout = (LinearLayout) view.findViewById(R.id.two);
                break;
            case 4:
                layout = (LinearLayout) view.findViewById(R.id.three);
                break;
            case 5:
                layout = (LinearLayout) view.findViewById(R.id.four);
                break;
            case 6:
                layout = (LinearLayout) view.findViewById(R.id.five);
                break;
            case 7:
                layout = (LinearLayout) view.findViewById(R.id.six);
                break;
            case 8:
                layout = (LinearLayout) view.findViewById(R.id.other);
                break;
        }
        tv = createTv(i, j, text);
        tv.setTextColor(Color.WHITE);

        //tv.setBackgroundColor(Color.argb(100, 0, 0, 0));
        layout.addView(tv);
    }

}



