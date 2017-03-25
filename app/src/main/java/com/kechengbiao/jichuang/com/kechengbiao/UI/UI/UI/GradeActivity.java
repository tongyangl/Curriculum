package com.kechengbiao.jichuang.com.kechengbiao.UI.UI.UI;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.kechengbiao.jichuang.com.kechengbiao.R;
import com.kechengbiao.jichuang.com.kechengbiao.UI.UI.adapter.GradeListAdatper;
import com.kechengbiao.jichuang.com.kechengbiao.UI.UI.view.MyLisetView;
import com.kechengbiao.jichuang.com.kechengbiao.UI.UI.view.TyListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 佟杨 on 2017/3/16.
 */

public class GradeActivity extends AppCompatActivity {
    private ListView listView;
    private String grade;
    private List<Map<String,String>>list;
    private LayoutInflater inflater;
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
        setContentView(R.layout.activity_grade);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("查询");
        setSupportActionBar(toolbar);
        listView= (ListView) findViewById(R.id.lv);
        Intent intent=getIntent();
         grade=intent.getStringExtra("grade");
        getGradelist(grade);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    private void getGradelist(String s){

        Document document = Jsoup.parse(s);
         Elements elements=document.select("div[class=Nsb_pw]");
    String a=     elements.get(2).ownText();
      String b=  elements.get(2).select("span").text();
        Log.d("===",a+b);
        toolbar.setTitle(a.trim().substring(5,a.length()));
        toolbar.setSubtitle(b.trim());
        if (document.getElementById("dataList").select("tr").get(1).text().equals("未查询到数据")){

            Toast.makeText(this,"暂无数据", Toast.LENGTH_SHORT).show();

        }else {
           list=new ArrayList<>();
            Elements tr = document.getElementById("dataList").select("tbody").select("tr");
            for (int i = 1; i < tr.size(); i++) {
                Elements td = tr.get(i).select("td");
                Map<String,String>map=new HashMap<>();
                    map.put("number",td.get(2).text());
                    map.put("name",td.get(3).text());
                    map.put("grade",td.get(4).text());
                    map.put("credit",td.get(5).text());
                    map.put("time",td.get(6).text());
                    map.put("mode",td.get(7).text());
                    map.put("attribute",td.get(8).text());
                    map.put("Nature",td.get(9).text());
                list.add(map);
            }
            inflater=getLayoutInflater();
            GradeListAdatper adatper=new GradeListAdatper(list,inflater);
            listView.setAdapter(adatper);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("---","ddddd");
                    AlertDialog.Builder builder = new AlertDialog.Builder(GradeActivity.this);
                    builder.setTitle("成绩详情");
                    builder.setMessage("学科："+list.get(position).get("name")+"\n"+"成绩:"+list.get(position).get("grade")+"\n"
                            +"课程编号："+list.get(position).get("number")+"\n"+"学时"+list.get(position).get("time")+"课程性质："+list.get(position).get("Nature")+"\n"
                            +"学分："+list.get(position).get("credit")+"\n"+"考查方式："+list.get(position).get("mode")+"\n"+"课程属性："+list.get(position).get("attribute")

                    );
                    builder.setPositiveButton("确定", null
                    );
                    builder.show();
                }


            });
        }

    }
}
