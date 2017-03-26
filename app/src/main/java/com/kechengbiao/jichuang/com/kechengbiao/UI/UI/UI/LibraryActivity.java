package com.kechengbiao.jichuang.com.kechengbiao.UI.UI.UI;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;

import com.kechengbiao.jichuang.com.kechengbiao.R;
import com.kechengbiao.jichuang.com.kechengbiao.UI.UI.Ascytask.QueryAsyncTask;
import com.kechengbiao.jichuang.com.kechengbiao.UI.UI.view.MyLisetView;
import com.paging.listview.PagingListView;

/**
 * Created by 佟杨 on 2017/3/14.
 */

public class LibraryActivity extends AppCompatActivity {
    private MyLisetView listView;
    private FloatingActionButton fab;
    private EditText search_text;
    private ProgressDialog dialog;
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

        setContentView(R.layout.activity_library);
        listView = (MyLisetView) findViewById(R.id.lv);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        search_text = (EditText) findViewById(R.id.search_text);
        dialog = new ProgressDialog(LibraryActivity.this);
        listView.setHasMoreItems(true);
        listView.setPagingableListener(new PagingListView.Pagingable() {
            @Override
            public void onLoadMoreItems() {
                
            }
        });
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView.setPagingableListener(new PagingListView.Pagingable() {
            @Override
            public void onLoadMoreItems() {
                 
            }
        });
        search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (search_text.getText().length() != 0) {
                    search_text.setClickable(true);

                } else {
                    search_text.setClickable(false);
                }
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ProgressDialog(LibraryActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                ConnectivityManager con = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
                boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
                boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
                 if (wifi|internet){
                     QueryAsyncTask asyncTask = new QueryAsyncTask(LibraryActivity.this, listView, dialog, inflater, getApplicationContext());
                     asyncTask.execute(search_text.getText().toString().trim());
                 }else {

                     AlertDialog.Builder builder = new AlertDialog.Builder(LibraryActivity.this);
                     builder.setTitle("提示");
                     builder.setMessage("系统检测到无网络连接!");
                     builder.setCancelable(false);
                     builder.setPositiveButton("知道了！", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                             dialog.cancel();
                         }
                     });
                     builder.create().show();
                 }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}

