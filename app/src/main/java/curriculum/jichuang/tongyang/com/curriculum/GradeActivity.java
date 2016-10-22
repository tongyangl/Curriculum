package curriculum.jichuang.tongyang.com.curriculum;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.widget.RelativeLayout.CENTER_HORIZONTAL;

/**
 * Created by tongyang on 2016/9/21.
 */
public class GradeActivity extends Activity {

    private LinearLayout li;
    private LinearLayout title;
    private TextView tv1;
    private TextView tv2;
    private String text1;
    private String text2;
    private ListView listview;
    private Button query;
    private EditText et;
    private ProgressDialog dialog;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.grade);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        li = (LinearLayout) findViewById(R.id.li);
        title = (LinearLayout) findViewById(R.id.title);
        title.setBackgroundColor(Color.argb(180, 152, 245, 255));
        li.setBackgroundColor(Color.argb(100, 255, 250, 250));
        et = (EditText) findViewById(R.id.classet);
        query = (Button) findViewById(R.id.query);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv1();
            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv2();
            }
        });
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("CookieString", Activity.MODE_PRIVATE);

                String args[] = {
                        sharedPreferences.getString("cookie", ""),
                        tv1.getText().toString(), tv2.getText().toString(), et.getText().toString(), "all"

                };
                queryAsyncTask asyncTask = new queryAsyncTask();
                asyncTask.execute(args);


            }
        });
    }

    public class queryAsyncTask extends AsyncTask<String, String, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(GradeActivity.this);
            dialog.setCancelable(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setTitle("玩命查询中..");
            dialog.show();
        }

        @Override
        protected void onPostExecute(String[] s) {
            super.onPostExecute(s);
            dialog.dismiss();
            Intent intent = new Intent(GradeActivity.this, GradeView.class);
            intent.putExtra("grade", s[0]);
            intent.putExtra("time",s[1]);
            startActivity(intent);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override

        protected String[] doInBackground(String... params) {
            getGrade grade = new getGrade();
            Log.d("asdasdasd", params[1]+"aa");
            Log.d("asdasdasd", params[2]+"aa");
            Log.d("asdasdasd", params[3]+"aa");
            String g = grade.getgrade(params[0], params[1], params[2], params[3], params[4]);
            Log.d("gggggg", g);
            String args[]={
                g,params[1]
            };
            return args;
        }
    }

    public void tv2() {
        List<String> data = new ArrayList<String>();
        data.add("所有");
        data.add("公共课");
        data.add("公共基础课");
        data.add("专业基础课");
        data.add("专业课");
        data.add("专业选修课");
        data.add("公共选修课");
        data.add("其他");
        data.add("学科基础课程");
        data.add("专业及专业特色课程");
        data.add("素质教育课程");
        data.add("实践环节课程");
        AlertDialog.Builder builder = new AlertDialog.Builder(GradeActivity.this);
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialogpicker, null);
        PickerView pickerView = (PickerView) view.findViewById(R.id.pick);
        pickerView.setData(data);

        pickerView.setAlpha(0.7f);
        pickerView.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                text2 = text;
            }
        });
        builder.setView(view);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv2.setText(text2);
            }
        });
        builder.setNegativeButton("取消", null);
        Window dialogWindow = builder.show().getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        lp.alpha = 0.7f;
        lp.width = 600;
        lp.height = 600;
        dialogWindow.setAttributes(lp);
        //   builder.show();
    }

    public void tv1() {
        List<String> data = new ArrayList<String>();
        data.add("2016-2017-1");
        data.add("2015-2016-2");
        data.add("2015-2016-1");
        data.add("2014-2015-2");
        data.add("2014-2015-1");
        data.add("2013-2014-2");
        data.add("2013-2014-1");
        data.add("2012-2013-2");
        data.add("2012-2013-1");
        data.add("2011-2012-1");
        String str[] = {
                "2016-2017-1", "2015-2016-2", "2015-2016-1", "2014-2015-2"

        };
        AlertDialog.Builder builder = new AlertDialog.Builder(GradeActivity.this);
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialogpicker, null);
        PickerView pickerView = (PickerView) view.findViewById(R.id.pick);
        pickerView.setData(data);
        pickerView.setAlpha(0.7f);

        pickerView.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                text1 = text;
            }
        });
        builder.setView(view);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                tv1.setText(text1);
            }
        });
        builder.setNegativeButton("取消", null);
        Window dialogWindow = builder.show().getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        lp.alpha = 0.7f;
        lp.width = 600;
        lp.height = 600;
        dialogWindow.setAttributes(lp);
        //   builder.show();
    }

}
