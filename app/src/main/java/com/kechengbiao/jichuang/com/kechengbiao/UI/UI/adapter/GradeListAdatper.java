package com.kechengbiao.jichuang.com.kechengbiao.UI.UI.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kechengbiao.jichuang.com.kechengbiao.R;
import com.kechengbiao.jichuang.com.kechengbiao.UI.UI.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 佟杨 on 2017/3/16.
 */

public class GradeListAdatper extends BaseAdapter {
    private List<Map<String, String>> list;
    private LayoutInflater inflater;
    private List<View> viewList;

    public GradeListAdatper(List<Map<String, String>> list, LayoutInflater inflater) {
        this.list = list;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            viewList = new ArrayList<>();
            convertView=inflater.inflate(R.layout.item_list_grade,null);
            TextView name = (TextView) convertView.findViewById(R.id.classname);
            TextView xuefen =  (TextView) convertView.findViewById(R.id.xuefen);
            TextView xueshi =  (TextView) convertView.findViewById(R.id.xueshi);
          //  TextView nature =  (TextView) convertView.findViewById(R.id.nature);
            TextView grade = (TextView) convertView.findViewById(R.id.grade);
           // TextView number =  (TextView) convertView.findViewById(R.id.number);
            viewList.add(name);
            viewList.add(xuefen);
            viewList.add(xueshi);
          //  viewList.add(nature);
            viewList.add(grade);
          //  viewList.add(number);
            convertView.setTag(viewList);
        }else {
            viewList= (List<View>) convertView.getTag();

        }
         String g=list.get(position).get("grade");
         if (Util.isNumeric(g)){
             int grade=Integer.parseInt(g);
             if (grade<60){
                 ((TextView)viewList.get(3)).setTextColor(Color.RED);
             }else {

                 ((TextView)viewList.get(3)).setTextColor(Color.GREEN);
             }
         }else {
             if (g.equals("及格")|g.equals("中等")|g.equals("优秀")|g.equals("良好")){

                 ((TextView)viewList.get(3)).setTextColor(Color.GREEN);
             }else {
                 ((TextView)viewList.get(3)).setTextColor(Color.RED);
             }

         }
        ((TextView)viewList.get(0)).setText(list.get(position).get("name"));
        ((TextView)viewList.get(1)).setText("学分:"+list.get(position).get("credit"));
        ((TextView)viewList.get(2)).setText("学时:"+list.get(position).get("time"));
       // ((TextView)viewList.get(3)).setText(list.get(position).get("Nature"));
        ((TextView)viewList.get(3)).setText(g);
      //  ((TextView)viewList.get(5)).setText(list.get(position).get("number"));
        return convertView;
    }


}
