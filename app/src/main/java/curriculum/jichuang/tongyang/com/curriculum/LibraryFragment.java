package curriculum.jichuang.tongyang.com.curriculum;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.SparseArray;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.protocol.HTTP;
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
 * Created by tongyang on 2016/10/7.
 */
public class LibraryFragment extends Fragment {
    private Button button;
    private EditText editText;
    private ProgressDialog dialog;
    private String query;

    private boolean tag;
    private List<Map<String, String>> list;
    private ListView lv;
    private List<View> holder;
    private TextView view1;
    private TextView view2;
    private TextView view3;
    private TextView view4;
    private TextView view5;
    private TextView view6;
    public static List<SparseArray<String>> list1;
    private List<SparseArray<String>> list2;
    private Dialog dialog1;
    private ProgressBar pro;
    private ProgressBar progressBar;
    private String page;
    private String nexturl;
    private String purl;
    private TextView pageView;
    private TextView next;
    private TextView p;
    private LinearLayout layout;
    private BaseAdapter baseAdapter;
    private Document document;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true
        );
    }

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.library, container, false);
        button = (Button) view.findViewById(R.id.query);
        editText = (EditText) view.findViewById(R.id.et);
        lv = (ListView) view.findViewById(R.id.lv);
        pageView = (TextView) view.findViewById(R.id.page);
        next = (TextView) view.findViewById(R.id.next);
        p = (TextView) view.findViewById(R.id.Previous);
        layout = (LinearLayout) view.findViewById(R.id.pagecode);
        progressBar = (ProgressBar) view.findViewById(R.id.pro);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        editText.setHintTextColor(Color.WHITE);
        editText.setTextColor(Color.WHITE);
        button.setTextColor(Color.WHITE);
        editText.setAlpha(0.7f);
        button.setAlpha(0.7f);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager con = (ConnectivityManager) getActivity().getSystemService(Activity.CONNECTIVITY_SERVICE);
                boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
                boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
                if (wifi | internet) {

                    tag = false;
                    query = editText.getText().toString().trim();
                    if (query.equals("")) {

                        Toast.makeText(getActivity(), "查询书籍不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        String url = "http://219.149.214.106:8087/opac/openlink.php?strSearchType=title&match_flag=forward&historyCount=1&strText=" + query + "&doctype=ALL&with_ebook=on&displaypg=20&showmode=list&sort=CATA_DATE&orderby=desc&dept=ALL";
                        QueryAsyncTask asyncTask = new QueryAsyncTask();
                        asyncTask.execute(url);
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override

                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                String url = "http://219.149.214.106:8087/opac/" + list.get(position).get("url");
                                Log.d("ddd", url);
                                MoreAsyncTask a = new MoreAsyncTask();
                                a.execute(url);

                            }

                        });
                    }
                } else {
                    Toast.makeText(getActivity(), "网络连接异常请连接网络", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }


    class MoreAsyncTask extends AsyncTask<String, Integer, Document> {


        @Override
        protected void onPreExecute() {
            dialog1 = new Dialog(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View v = inflater.inflate(R.layout.more_dialog, null);
            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog1.setContentView(v);

            dialog1.setCancelable(true);
            dialog1.show();
            Window dialogWindow = dialog1.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            WindowManager wm = (WindowManager) getContext()
                    .getSystemService(Context.WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            int height = wm.getDefaultDisplay().getHeight();
            lp.width = (int) (width * 0.9);
            lp.height = (int) (height * 0.9);
            // dialogWindow.setAttributes(lp);
            dialog1.getWindow().setAttributes(lp);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Document document) {
            Log.d("ddd", document.toString());
            if (document == null) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View v = inflater.inflate(R.layout.more_dialog, null);
                pro = (ProgressBar) v.findViewById(R.id.pro);
                TextView textView = (TextView) v.findViewById(R.id.state);
                textView.setText("好像出现了什么问题呢...");
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setContentView(v);

                dialog1.setCancelable(true);
                dialog1.show();
                Window dialogWindow = dialog1.getWindow();
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                WindowManager wm = (WindowManager) getContext()
                        .getSystemService(Context.WINDOW_SERVICE);
                int width = wm.getDefaultDisplay().getWidth();
                int height = wm.getDefaultDisplay().getHeight();
                lp.width = (int) (width * 0.9);
                lp.height = (int) (height * 0.9);
                // dialogWindow.setAttributes(lp);
                dialog1.getWindow().setAttributes(lp);
                Toast.makeText(getActivity(), "查询异常", Toast.LENGTH_SHORT).show();

            } else {

                Element e = document.getElementById("item_detail");
                list1 = new ArrayList<>();
                Elements dl = e.select("dl");
                for (int i = 0; i < dl.size(); i++) {
                    SparseArray<String> s = new SparseArray<String>();
                    if (i == dl.size() - 1) {
                        s.put(0, dl.get(i).select("dt").text());
                        Log.d("ddd", dl.get(i).select("dt").text());
                        s.put(1, dl.get(i).select("dd").select("p").text());
                        Log.d("ddd", dl.get(i).select("dd").text());
                    }
                    s.put(0, dl.get(i).select("dt").text());
                    Log.d("ddd", dl.get(i).select("dt").text());
                    s.put(1, dl.get(i).select("dd").text());
                    Log.d("ddd", dl.get(i).select("dd").text());
                    list1.add(s);
                }
                Element element = document.getElementById("item");
                Elements elements = element.select("tr");
                list2 = new ArrayList<>();
                for (int i = 1; i < elements.size(); i++) {

                    SparseArray<String> sparseArray = new SparseArray<>();
                    sparseArray.put(0, elements.get(i).select("td").get(0).text());
                    sparseArray.put(1, elements.get(i).select("td").get(1).text());
                    sparseArray.put(2, elements.get(i).select("td").get(2).text());
                    sparseArray.put(3, elements.get(i).select("td").get(3).text());
                    sparseArray.put(4, elements.get(i).select("td").get(4).text());
                    list2.add(sparseArray);
                }


                LayoutInflater inflater = getActivity().getLayoutInflater();
                View v = inflater.inflate(R.layout.more_dialog, null);

                ListView palecelist = (ListView) v.findViewById(R.id.palec_list);
                TextView more = (TextView) v.findViewById(R.id.more);
                palecelist.setAdapter(new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return list2.size();
                    }

                    @Override
                    public Object getItem(int position) {
                        return list2.get(position);
                    }

                    @Override
                    public long getItemId(int position) {
                        return position;
                    }

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        if (convertView == null) {
                            LayoutInflater i = getActivity().getLayoutInflater();
                            convertView = i.inflate(R.layout.place_list_stytle, null);

                        }
                        TextView tv1 = (TextView) convertView.findViewById(R.id.tv1);
                        TextView tv2 = (TextView) convertView.findViewById(R.id.tv2);
                        TextView tv3 = (TextView) convertView.findViewById(R.id.tv3);
                        TextView tv4 = (TextView) convertView.findViewById(R.id.tv4);
                        TextView tv5 = (TextView) convertView.findViewById(R.id.tv5);

                        tv1.setText(list2.get(position).get(0));
                        tv2.setText(list2.get(position).get(1));
                        tv3.setText(list2.get(position).get(2));
                        tv4.setText(list2.get(position).get(3));
                        tv5.setText(list2.get(position).get(4));


                        return convertView;
                    }
                });

                more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), More_libraryBook.class);

                        startActivity(intent);
                    }
                });
                pro = (ProgressBar) v.findViewById(R.id.pro);
                TextView textView = (TextView) v.findViewById(R.id.state);
                pro.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);
                palecelist.setVisibility(View.VISIBLE);
                dialog1.setCancelable(true);
                dialog1.setContentView(v);
                dialog1.show();
                Window dialogWindow = dialog1.getWindow();

                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                WindowManager wm = (WindowManager) getContext()
                        .getSystemService(Context.WINDOW_SERVICE);
                int width = wm.getDefaultDisplay().getWidth();
                int height = wm.getDefaultDisplay().getHeight();
                lp.width = (int) (width * 0.9);
                lp.height = (int) (height * 0.9);
                // dialogWindow.setAttributes(lp);
                dialog1.getWindow().setAttributes(lp);

            }

            super.onPostExecute(document);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
            super.onProgressUpdate(values);
        }

        @Override
        protected Document doInBackground(String... params) {
            try {
                Document document = Jsoup.parse(new URL(params[0]).openStream(), "UTF-8", params[0]);
                return document;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    class QueryAsyncTask extends AsyncTask<String, Integer, Document> {
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
            dialog.setTitle("正在查询");
            dialog.setMessage("请稍候");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected void onPostExecute(Document s) {
            Object o = s.getElementById("search_book_list");
            if (o == null) {
                dialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("提示");
                builder.setMessage("尚未查询到此类书籍,请检查您输入是否有误");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();

            } else {
                tag = true;
                Log.d("yyyy", String.valueOf(tag));
                dialog.dismiss();
                if (tag) {
                    document = s;
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
                        Elements palce = ol.get(i).select("h3").select("a");
                        Log.d("eee", palce.text());
                        palce.text();
                        map.put("palce", palce.text());
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
                    baseAdapter = new BaseAdapter() {

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
                                LayoutInflater inflater = getActivity().getLayoutInflater();
                                holder = new ArrayList<>();
                                convertView = inflater.inflate(R.layout.library_liststyle, null);
                                view1 = (TextView) convertView.findViewById(R.id.name);
                                view2 = (TextView) convertView.findViewById(R.id.language);
                                view3 = (TextView) convertView.findViewById(R.id.num);
                                view4 = (TextView) convertView.findViewById(R.id.type);
                                view5 = (TextView) convertView.findViewById(R.id.wtiter);
                                view6 = (TextView) convertView.findViewById(R.id.place);
                                holder.add(view1);
                                holder.add(view2);
                                holder.add(view3);
                                holder.add(view4);
                                holder.add(view5);
                                holder.add(view6);
                                convertView.setTag(holder);
                            } else {
                                holder = (List<View>) convertView.getTag();
                            }
                            ((TextView) holder.get(0)).setTextColor(Color.RED);
                            ((TextView) holder.get(0)).setText(list.get(position).get("name"));
                            ((TextView) holder.get(1)).setText(list.get(position).get("language"));
                            ((TextView) holder.get(2)).setText(list.get(position).get("num"));
                            ((TextView) holder.get(3)).setText(list.get(position).get("type"));
                            ((TextView) holder.get(4)).setText(list.get(position).get("writer"));
                            //((TextView) holder.get(5)).setText(((TextView) holder.get(5)).getText()+""+list.get(position).get("place"));

                            return convertView;
                        }
                    };

                    lv.setAdapter(baseAdapter);
                    lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                            /*Intent intent= new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            Uri content_url = Uri.parse("http://www.cnblogs.com");
                            intent.setData(content_url);
                            startActivity(intent);*/
                            ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                            // 将文本内容放到系统剪贴板里。

                            String[] name = list.get(position).get("name").split(".", 2);

                            cm.setText(name[1]);
                            Toast.makeText(getActivity(), "书名已复制到粘贴板", Toast.LENGTH_SHORT).show();

                            return true;
                        }
                    });
                    Object o1 = document.getElementsByClass("num_prev");
                    if (ol == null) {
                        Log.d("pagepage", "aaaa");

                    } else {
                        page = document.getElementsByClass("num_prev").select("b").text();
                        String[] args = page.split("/");
                        if (args.length == 1) {

                        } else {
                            final int a = Integer.parseInt(args[0].trim());
                            int b = Integer.parseInt(args[1].trim());
                            layout.setVisibility(View.VISIBLE);
                            pageView.setText(page);
                            Log.d("pagepage", page);
                            if (a == 1 & a < b) {
                                nexturl = document.getElementsByClass("num_prev").select("a").attr("href");
                                p.setClickable(false);
                                p.setTextColor(Color.BLACK);
                                next.setClickable(true);
                                next.setTextColor(Color.BLUE);
                                next.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        nextAsyncTask asyncTask = new nextAsyncTask();
                                        String url = "http://219.149.214.106:8087/opac/openlink.php" + nexturl;
                                        asyncTask.execute(url);

                                    }
                                });

                            } else if (a > 1 & a < b) {
                                pageView.setText(page);
                                p.setClickable(true);
                                p.setTextColor(Color.BLUE);
                                next.setClickable(true);
                                next.setTextColor(Color.BLUE);

                            }


                        }

                    }
                }

            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            dialog.setProgress(values[0]);
            super.onProgressUpdate(values);
        }

        @Override
        protected Document doInBackground(String... params) {

            try {
                Document document = Jsoup.parse(new URL(params[0]).openStream(), "UTF-8", params[0]);
                Log.d("ddd", document.toString());
                return document;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


    }

    class nextAsyncTask extends AsyncTask<String, Integer, Document> {


        @Override
        protected void onPreExecute() {

            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Document documentt) {
            progressBar.setVisibility(View.GONE);
            Object o = documentt.getElementById("search_book_list");
            if (o == null) {
                dialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("提示");
                builder.setMessage("下一页获取失败");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();

            } else {
                tag = true;
                Log.d("yyyy", String.valueOf(tag));
                dialog.dismiss();
                if (tag) {
                    document = documentt;
                    list = new ArrayList<Map<String, String>>();
                    Elements ol = document.getElementById("search_book_list").select("li");
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
                        Elements palce = ol.get(i).select("h3");
                        palce.text();
                        map.put("palce", palce.text());
                        //连接
                        Elements url = ol.get(i).select("h3").select("a");
                        Iterator it = url.iterator();
                        Element e = (Element) it.next();
                        String URL = e.attr("href");
                        Log.d("eee", URL + "asdasdads");
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
                    baseAdapter.notifyDataSetChanged();
                    Object o1 = document.getElementsByClass("num_prev");
                    if (ol == null) {
                        Log.d("pagepage", "aaaa");

                    } else {
                        page = document.getElementsByClass("num_prev").select("b").text();
                        String[] args = page.split("/");
                        if (args.length == 1) {

                        } else {
                            final int a = Integer.parseInt(args[0].trim());
                            int b = Integer.parseInt(args[1].trim());
                            layout.setVisibility(View.VISIBLE);
                            pageView.setText(page);
                            Log.d("pagepage", page);
                            if (a == 1 & a < b) {
                                nexturl = document.getElementsByClass("num_prev").select("a").attr("href");
                                p.setClickable(false);
                                p.setTextColor(Color.BLACK);
                                next.setClickable(true);
                                next.setTextColor(Color.BLUE);
                                next.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        nextAsyncTask asyncTask = new nextAsyncTask();
                                        String url = "http://219.149.214.106:8087/opac/openlink.php" + nexturl;
                                        asyncTask.execute(url);
                                        lv.smoothScrollToPosition(0);

                                    }
                                });

                            } else if (a > 1 & a < b) {
                                nexturl = document.getElementsByClass("num_prev").select("a").get(1).attr("href");
                                purl = document.getElementsByClass("num_prev").select("a").get(0).attr("href");
                                pageView.setText(page);
                                p.setClickable(true);
                                p.setTextColor(Color.BLUE);
                                next.setClickable(true);
                                next.setTextColor(Color.BLUE);
                                next.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        nextAsyncTask asyncTask = new nextAsyncTask();
                                        String url = "http://219.149.214.106:8087/opac/openlink.php" + nexturl;
                                        asyncTask.execute(url);
                                        lv.smoothScrollToPosition(0);
                                    }
                                });
                                p.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        nextAsyncTask asyncTask = new nextAsyncTask();
                                        String url = "http://219.149.214.106:8087/opac/openlink.php" + purl;
                                        asyncTask.execute(url);
                                        lv.smoothScrollToPosition(0);
                                    }
                                });

                            } else if (a == b) {
                                nexturl = document.getElementsByClass("num_prev").select("a").attr("href");
                                next.setTextColor(Color.BLACK);
                                p.setTextColor(Color.BLUE);
                                p.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        lv.smoothScrollToPosition(0);
                                        nextAsyncTask asyncTask = new nextAsyncTask();
                                        String url = "http://219.149.214.106:8087/opac/openlink.php" + purl;
                                        asyncTask.execute(url);
                                    }
                                });

                            }


                        }

                    }
                }

            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.d("jjjjj", values[0].toString());
            progressBar.setProgress(values[0]);
            super.onProgressUpdate(values);
        }

        @Override
        protected Document doInBackground(String... params) {
            try {
                Document document = Jsoup.parse(new URL(params[0]).openStream(), HTTP.UTF_8, params[0]);

                return document;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}