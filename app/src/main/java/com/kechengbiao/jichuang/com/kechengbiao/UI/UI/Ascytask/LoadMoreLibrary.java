package com.kechengbiao.jichuang.com.kechengbiao.UI.UI.Ascytask;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.kechengbiao.jichuang.com.kechengbiao.UI.UI.Util;
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
 * Created by 佟杨 on 2017/3/27.
 */

public class LoadMoreLibrary extends AsyncTask<String, Void, Document> {
    private List<Map<String, String>> list;
    private List<Map<String, String>> list1;
    private MyLisetView lisetView;

    public LoadMoreLibrary(List<Map<String, String>> list1, MyLisetView lisetView) {
        this.list1 = list1;
        this.lisetView = lisetView;
    }

    @Override
    protected void onPostExecute(final Document s) {
        super.onPostExecute(s);
        Util util = new Util();

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
        list1.addAll(list);
        if (s.getElementsByClass("num_prev").hasText()) {


            lisetView.setPagingableListener(new PagingListView.Pagingable() {
                @Override
                public void onLoadMoreItems() {
                    String page = s.getElementsByClass("num_prev").select("b").text();
                    String[] args = page.split("/");
                    int a = Integer.parseInt(args[0]);
                    int b = Integer.parseInt(args[1]);
                    if (b > a & a == 1) {
                        String nexturl = s.getElementsByClass("num_prev").select("a").attr("href");
                        LoadMoreLibrary loadMoreLibrary = new LoadMoreLibrary(list, lisetView);
                        loadMoreLibrary.execute(nexturl);
                        lisetView.onFinishLoading(true,list1);
                    } else if (a > 1 & a < b) {
                        String nexturl = s.getElementsByClass("num_prev").select("a").get(1).attr("href");
                        LoadMoreLibrary loadMoreLibrary = new LoadMoreLibrary(list, lisetView);
                        loadMoreLibrary.execute(nexturl);
                        lisetView.onFinishLoading(true,list1);
                    }else {
                        lisetView.onFinishLoading(false, null);
                    }
                }
            });
        } else {
            lisetView.onFinishLoading(false, null);
        }
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Document doInBackground(String... params) {
        try {
            String url = "http://219.149.214.106:8087/opac/openlink.php" + params[0];
            Document document = Jsoup.parse(new URL(url).openStream(), "UTF-8", params[0]);
            return document;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
