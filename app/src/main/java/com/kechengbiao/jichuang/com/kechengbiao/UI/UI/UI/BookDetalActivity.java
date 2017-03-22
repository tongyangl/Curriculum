package com.kechengbiao.jichuang.com.kechengbiao.UI.UI.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.kechengbiao.jichuang.com.kechengbiao.R;
import com.kechengbiao.jichuang.com.kechengbiao.UI.UI.Ascytask.BookDetalAsyncTask;
import com.zzhoujay.richtext.RichText;

/**
 * Created by 佟杨 on 2017/3/15.
 */

public class BookDetalActivity extends AppCompatActivity {
    private TextView textView;
    private android.support.v7.widget.Toolbar toolbar;
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_bookdetal);
        webView= (WebView) findViewById(R.id.webView);
        toolbar= (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("图书详情");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textView = (TextView) findViewById(R.id.tv);
        Intent intent = getIntent();
        String url = "http://219.149.214.106:8087/opac/" + intent.getStringExtra("url");
        BookDetalAsyncTask asyncTask = new BookDetalAsyncTask(webView,textView);
        asyncTask.execute(url);


    }
}
