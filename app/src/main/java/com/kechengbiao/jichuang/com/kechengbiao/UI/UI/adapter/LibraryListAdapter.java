package com.kechengbiao.jichuang.com.kechengbiao.UI.UI.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kechengbiao.jichuang.com.kechengbiao.R;
import com.kechengbiao.jichuang.com.kechengbiao.UI.UI.UI.BookDetalActivity;
import com.kechengbiao.jichuang.com.kechengbiao.UI.UI.view.MyLisetView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 佟杨 on 2017/3/15.
 */

public class LibraryListAdapter extends BaseAdapter {
    private List<Map<String, String>> list;
    private LayoutInflater inflater;
    private List<View> viewHolder;
    private Activity activity;


    public LibraryListAdapter(List<Map<String, String>> list, Activity activity, LayoutInflater inflater) {
        this.list = list;
        this.activity = activity;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_list_library, null);
            viewHolder = new ArrayList<>();
            TextView bookname = (TextView) convertView.findViewById(R.id.bookname);
            TextView place = (TextView) convertView.findViewById(R.id.place);
            TextView writer = (TextView) convertView.findViewById(R.id.num);
            TextView num = (TextView) convertView.findViewById(R.id.writer);
            TextView more = (TextView) convertView.findViewById(R.id.moer);

            viewHolder.add(bookname);
            viewHolder.add(place);
            viewHolder.add(writer);
            viewHolder.add(num);
            viewHolder.add(more);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (List<View>) convertView.getTag();

        }

        ((TextView) viewHolder.get(0)).setText(list.get(position).get("name"));
        ((TextView) viewHolder.get(1)).setText(list.get(position).get("palce"));
        ((TextView) viewHolder.get(2)).setText(list.get(position).get("writer"));
        ((TextView) viewHolder.get(3)).setText(list.get(position).get("num"));
        ((TextView) viewHolder.get(4)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, BookDetalActivity.class);
                intent.putExtra("url", list.get(position).get("url"));
                activity.startActivity(intent);
            }
        });
        return convertView;
    }

    public class ViewHolder {
        public TextView bookname;
        public TextView writer;
        public TextView num;
        private TextView place;
    }

}
