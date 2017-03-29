package com.kechengbiao.jichuang.com.kechengbiao.UI.UI.adapter;

import android.app.Activity;
import android.content.Intent;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kechengbiao.jichuang.com.kechengbiao.R;
import com.kechengbiao.jichuang.com.kechengbiao.UI.UI.ui.BookDetalActivity;

import java.util.List;
import java.util.Map;

/**
 * Created by 佟杨 on 2017/3/28.
 */

public class GradeRecyclerAdapter extends RecyclerView.Adapter<GradeRecyclerAdapter.ViewHolder> {
    private List<Map<String, String>> list;
    private Activity activity;

    public GradeRecyclerAdapter(List<Map<String, String>> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_library, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,  int position) {
        holder.bookname.setText(list.get(position).get("name"));
        holder.place.setText(list.get(position).get("palce"));
        holder.writer.setText(list.get(position).get("writer"));
        holder.num.setText(list.get(position).get("num"));
        final  int a=position;
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, BookDetalActivity.class);
                intent.putExtra("url", list.get(a).get("url"));
                activity.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView bookname;
        TextView place;
        TextView writer;
        TextView num;
        TextView more;

        public ViewHolder(View itemView) {
            super(itemView);
            bookname = (TextView) itemView.findViewById(R.id.bookname);
            place = (TextView) itemView.findViewById(R.id.place);
            writer = (TextView) itemView.findViewById(R.id.num);
            num = (TextView) itemView.findViewById(R.id.writer);
            more = (TextView) itemView.findViewById(R.id.moer);
        }
    }
}
