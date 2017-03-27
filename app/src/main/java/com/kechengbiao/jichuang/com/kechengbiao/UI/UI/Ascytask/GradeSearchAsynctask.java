package com.kechengbiao.jichuang.com.kechengbiao.UI.UI.Ascytask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import com.kechengbiao.jichuang.com.kechengbiao.UI.UI.Internet.GetGrade;
import com.kechengbiao.jichuang.com.kechengbiao.UI.UI.UI.GradeActivity;

/**
 * Created by 佟杨 on 2017/3/16.
 */

public class GradeSearchAsynctask extends AsyncTask<String,Void,String> {
   private ProgressDialog dialog;
   private Activity activity;

    public GradeSearchAsynctask(ProgressDialog dialog, Activity activity) {
        this.dialog = dialog;
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... params) {
        GetGrade getGrade=new GetGrade();
        getGrade.getgrade(params[0],params[1]);

        return getGrade.getgrade(params[0],params[1]);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setTitle("正在查询成绩");
        dialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        dialog.dismiss();
        Intent intent=new Intent(activity, GradeActivity.class);
        intent.putExtra("grade",s);
        activity.startActivity(intent);
    }
}
