package curriculum.jichuang.tongyang.com.curriculum;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;

import android.widget.GridView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by tongyang on 2016/9/20.
 */
public class MenuActivity extends Activity {

    private GridView gv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Activity标题不显示
      //  this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.menu);
        gv = (GridView) findViewById(R.id.gv);
        int []iv={

                R.drawable.c,
                R.drawable.grade,
                R.drawable.person,
                R.drawable.news,
                R.drawable.book,
                R.drawable.guake,
                R.drawable.cet,
                R.drawable.ping,
                R.drawable.b

        };
        String [] tv={
                "课程表","成绩查询","个人信息","新闻","图书查询","挂科了？","等级考试","评课","待开发"

        };
        ArrayList<HashMap<String,Object>>list=new ArrayList<>();
           for (int i=0;i<9;i++){
               HashMap<String,Object>map=new HashMap<>();
               map.put("iv",iv[i]);
               map.put("tv",tv[i]);
               list.add(map);
           }
        SimpleAdapter simpleAdapter=new SimpleAdapter(MenuActivity.this,list,R.layout.menu_style,new String[]{"iv","tv"},new int[]{R.id.iv,R.id.tv});
        gv.setAdapter(simpleAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int code=position+1;
                if (code==1){
                    Intent intent=new Intent();
                    intent.setClass(MenuActivity.this,KBActivity.class);
                    startActivity(intent);

                }else if (code==2){

                    Intent intent=new Intent();
                    intent.setClass(MenuActivity.this,GradeActivity.class);
                    startActivity(intent);

                }else if (code==3){

                    Intent intent=new Intent();
                   // intent.setClass(MenuActivity.this,testactivity.class);
                    startActivity(intent);
                }
            }
        });

    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
