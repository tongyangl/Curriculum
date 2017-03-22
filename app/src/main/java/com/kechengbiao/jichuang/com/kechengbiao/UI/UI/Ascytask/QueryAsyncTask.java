package com.kechengbiao.jichuang.com.kechengbiao.UI.UI.Ascytask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;

import com.kechengbiao.jichuang.com.kechengbiao.UI.UI.UI.LibraryActivity;
import com.kechengbiao.jichuang.com.kechengbiao.UI.UI.Util;
import com.kechengbiao.jichuang.com.kechengbiao.UI.UI.adapter.LibraryListAdapter;
import com.kechengbiao.jichuang.com.kechengbiao.UI.UI.view.MyLisetView;
import com.paging.listview.PagingListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by 佟杨 on 2017/3/15.
 */

public class QueryAsyncTask extends AsyncTask<String, Integer, Document> {
    private ProgressDialog dialog;
    private Context context;
    private Util util;
    private List<Map<String, String>> list;
    private LibraryListAdapter adapter;
    private LayoutInflater inflater;
    private MyLisetView listView;
    private  Activity activity;
    public QueryAsyncTask( Activity activity,MyLisetView listView, ProgressDialog dialog, LayoutInflater inflater, Context context) {
        this.dialog = dialog;
        this.inflater = inflater;
        this.context = context;
        this.listView = listView;
        this.activity=activity;
    }

    @Override
    protected void onPostExecute(Document s) {
        super.onPostExecute(s);
        util = new Util();
        Object o = s.getElementById("search_book_list");
        if (o == null) {
            dialog.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("提示");
            builder.setMessage("尚未查询到此类书籍,请检查您输入是否有误");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();

        } else {
            dialog.dismiss();

            if (iscontet()) {
                list = new ArrayList<Map<String, String>>();
                Elements ol = s.getElementById("search_book_list").select("li");
                Log.d("tytyty", ol.size() + "");
                for (int i = 0; i < ol.size(); i++) {
                    Map<String, String> map = new HashMap<String, String>();
                    //书类语言
                    Elements language = ol.get(i).select("h3").select("span");
                    map.put("language", language.text());
                    Log.d("llll", language.text());
                    Elements name = ol.get(i).select("h3").select("a");
                    name.text();
                    //书名
                    map.put("name", name.text());
                    Log.d("llll", name.text());
                    Element palce = ol.get(i).select("h3").get(0);
                    Log.d("pppp", palce.ownText());
                    map.put("palce", palce.ownText());
                    //连接
                    Elements url = ol.get(i).select("h3").select("a");
                    Iterator it = url.iterator();
                    Element e = (Element) it.next();
                    String URL = e.attr("href");

                    map.put("url", URL);

                    Log.d("llll", palce.text());
                    //数量
                    Elements num = ol.get(i).select("p").select("span");
                    num.text();
                    map.put("num", num.text());
                    Log.d("llll", num.text());
                    //作者
                    Element writer = ol.get(i).select("p").first();

                    map.put("writer", writer.ownText());
                    Log.d("llll", writer.text());
                    //书籍类型
                    Elements type = ol.get(i).select("p").select("a");
                    type.text();
                    map.put("type", type.text());
                    Log.d("llll", type.text());
                    list.add(map);

                }
            }
            adapter = new LibraryListAdapter(list,activity, inflater);
            listView.setAdapter(adapter);

        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setTitle("正在查询");
        dialog.setMessage("请稍候");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Document doInBackground(String... params) {
        try {
            String url = Util.URL_LIBRARY_SEARCH + params[0] + Util.URL_LIBRARY_SEARCH1;
            Document document = Jsoup.parse(new URL(url).openStream(), "UTF-8", params[0]);
            return document;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean iscontet() {
        ConnectivityManager con = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        if (wifi | internet) {
            return true;
        } else {
            return false;
        }
    }
}

