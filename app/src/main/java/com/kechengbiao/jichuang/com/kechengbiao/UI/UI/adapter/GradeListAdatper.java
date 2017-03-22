package com.kechengbiao.jichuang.com.kechengbiao.UI.UI.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kechengbiao.jichuang.com.kechengbiao.R;

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
            TextView nature =  (TextView) convertView.findViewById(R.id.nature);
            TextView grade = (TextView) convertView.findViewById(R.id.grade);
            TextView number =  (TextView) convertView.findViewById(R.id.number);
            viewList.add(name);
            viewList.add(xuefen);
            viewList.add(xueshi);
            viewList.add(nature);
            viewList.add(grade);
            viewList.add(number);
            convertView.setTag(viewList);
        }else {
            viewList= (List<View>) convertView.getTag();

        }
       // Log.d("=-=-=",list.get(position).get("name")+"aa");
        ((TextView)viewList.get(0)).setText(list.get(position).get("name"));
        ((TextView)viewList.get(1)).setText(list.get(position).get("credit"));
        ((TextView)viewList.get(2)).setText(list.get(position).get("time"));
        ((TextView)viewList.get(3)).setText(list.get(position).get("Nature"));
        ((TextView)viewList.get(4)).setText(list.get(position).get("grade"));
        ((TextView)viewList.get(5)).setText(list.get(position).get("number"));
        return convertView;
    }


}
