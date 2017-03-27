package com.kechengbiao.jichuang.com.kechengbiao.UI.UI.Ascytask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.ImageFixCallback;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;


/**
 * Created by 佟杨 on 2017/3/15.
 */

public class BookDetalAsyncTask extends AsyncTask<String, Void, Document> {
    private TextView textView;
   private WebView webView;
  private ProgressDialog dialog;
    public BookDetalAsyncTask(WebView webView, TextView textView,ProgressDialog dialog) {
        this.webView = webView;
        this.textView = textView;
        this.dialog=dialog;
    }

    @Override
    protected void onPostExecute(Document document) {
        super.onPostExecute(document);
        dialog.dismiss();
        loadview(document.select("div[id=item_detail]").toString());

    }

    public void loadview(String s) {
        //textView.setText(Html.fromHtml(s));
        //RichText.fromHtml(s).autoFix(true).into(textView);
        webView.loadDataWithBaseURL(null,s,"text/html","utf-8",null);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
             return false;
            }
        });
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        dialog.show();
    }

    @Override
    protected Document doInBackground(String... params) {

        try {
            Log.d("---", params[0]);
            Document document = Jsoup.parse(new URL(params[0]).openStream(), "UTF-8", params[0]);
            return document;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
