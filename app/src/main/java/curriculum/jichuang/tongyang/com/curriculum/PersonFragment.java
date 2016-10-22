package curriculum.jichuang.tongyang.com.curriculum;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by tongyang on 2016/10/7.
 */

public class PersonFragment extends Fragment {
    private ListView lv1;
    private ListView lv2;
    private TextView getpic;
    private ProgressBar progressBar;
    private ImageView imageView;
    private List<String> peronMeslist;
    private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.person, container, false);
        lv1 = (ListView) view.findViewById(R.id.lv1);
        lv2 = (ListView) view.findViewById(R.id.lv2);
        getpic = (TextView) view.findViewById(R.id.getpic);
        progressBar = (ProgressBar) view.findViewById(R.id.pro);
        imageView = (ImageView) view.findViewById(R.id.personpic);
        tv1 = (TextView) view.findViewById(R.id.person_1);
        tv2 = (TextView) view.findViewById(R.id.person_2);
        tv3 = (TextView) view.findViewById(R.id.person_3);
        tv4 = (TextView) view.findViewById(R.id.person_4);
        tv5 = (TextView) view.findViewById(R.id.person_5);
        tv6 = (TextView) view.findViewById(R.id.person_6);
        tv7 = (TextView) view.findViewById(R.id.person_7);
        tv8 = (TextView) view.findViewById(R.id.person_8);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SharedPreferences mySharedPreferences = getActivity().getSharedPreferences("CookieString",
                Activity.MODE_PRIVATE);

        String person = mySharedPreferences.getString("person", "");
        if (!person.equals("")) {
            Document document = Jsoup.parse(person);
            Object o = document.getElementById("xjkpTable");
            if (o == null) {
                Toast.makeText(getActivity(), "获取个人信息失败，请手动获取", Toast.LENGTH_SHORT).show();

            } else {
                Elements elements = document.getElementById("xjkpTable").select("tr");
                peronMeslist = new ArrayList<>();
                Elements th = elements.get(2).select("td");
                for (int i = 0; i < th.size(); i++) {
                    peronMeslist.add(th.get(i).text().trim());

                }
                Elements t = elements.get(3).select("td");
                peronMeslist.add(t.get(1).text().trim());
                peronMeslist.add(t.get(3).text().trim());
                Elements th1 = elements.get(4).select("td");
                peronMeslist.add(th1.get(1).text().trim());
                tv1.setText(peronMeslist.get(0));
                tv2.setText(peronMeslist.get(1));
                tv3.setText(peronMeslist.get(2));
                tv4.setText(peronMeslist.get(3));
                tv5.setText(peronMeslist.get(4));
                tv6.setText("姓名：" + "" + peronMeslist.get(5));
                tv7.setText("性别：" + "" + peronMeslist.get(6));
                tv8.setText("生日：" + "" + peronMeslist.get(7));
                getpic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      getpic.setVisibility(View.GONE);

                    }
                });
            }
        }

        final String data[] = {"关于", "注销", "退出"};
        final int img[] = {
                R.drawable.person1,
                R.drawable.perosn5, R.drawable.person4,
        };
        final String text[] = {"捐赠", "BUG反馈"};
        final int imgview[] = {
                R.drawable.person3,
                R.drawable.person2, R.drawable.logoko,
        };
        // lv1.setDividerHeight(0);
        //lv2.setDividerHeight(0);
        lv1.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return text.length;
            }

            @Override
            public Object getItem(int position) {
                return text[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View view = inflater.inflate(R.layout.lv2, null);
                    convertView = view;
                }
                ImageView imageView = (ImageView) convertView.findViewById(R.id.qwe);
                TextView textView = (TextView) convertView.findViewById(R.id.asd);
                textView.setTextSize(20);
                imageView.setImageResource(imgview[position]);
                textView.setText(text[position]);
                textView.setGravity(Gravity.CENTER);
                return convertView;
            }
        });
        lv2.setAdapter(new BaseAdapter() {

            @Override
            public int getCount() {
                return data.length;
            }

            @Override
            public Object getItem(int position) {

                return data[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View view = inflater.inflate(R.layout.lv1, null);
                    convertView = view;
                }
                ImageView imageView = (ImageView) convertView.findViewById(R.id.person_img);
                TextView textView = (TextView) convertView.findViewById(R.id.person_tv);
                textView.setTextSize(20);
                imageView.setImageResource(img[position]);
                textView.setText(data[position]);
                textView.setGravity(Gravity.CENTER);
                return convertView;
            }
        });
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText("");
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    //builder.setView();
                }
            }
        });
        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("提示");
                    builder.setMessage("您将退出当前账号并返回到登陆界面，是否确定");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("取消", null);
                    builder.show();

                }
                if (position == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("关于");
                    builder.setMessage("软件版本1.0.1");
                    builder.setCancelable(true);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    builder.show();

                }
                if (position == 2) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("提示");
                    builder.setMessage("您将退出当前应用，是否确定");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    });
                    builder.setNegativeButton("取消", null);
                    builder.show();

                }
            }
        });
    }


}
