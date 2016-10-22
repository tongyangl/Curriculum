package curriculum.jichuang.tongyang.com.curriculum;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tongyang on 2016/10/6.
 */

public class GradeView extends Activity {

    private List<String> list1;
    private List<String> list2;
    private List<String> list3;
    private List<String> list4;
    private List<String> list5;
    private List<String> list6;
    private List<String> list7;
    private List<String> list8;
    private List<String> parent;
    private Map<String, List<String>> child = null;
    private Button gradeback;
    private TextView tv_time;
    private ExpandableListView elv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gradeview);
        Intent intent = getIntent();
        parent = new ArrayList<>();
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();
        list4 = new ArrayList<>();
        list5 = new ArrayList<>();
        list6 = new ArrayList<>();
        list7 = new ArrayList<>();
        list8 = new ArrayList<>();
        child = new HashMap<>();
        gradeback = (Button) findViewById(R.id.gradeback);
        tv_time = (TextView) findViewById(R.id.tv_time);

        elv = (ExpandableListView) this.findViewById(R.id.elv);
        Bundle bundle = intent.getExtras();
        String garde = bundle.get("grade").toString();
        String time = bundle.get("time").toString();
        tv_time.setText("第" + time + "学期");
        Log.d("========", garde.toString() + "asdasd");
        Document document = Jsoup.parse(garde);
         if (document.getElementById("dataList")==null){
             Toast.makeText(this,"暂无数据",Toast.LENGTH_SHORT).show();

         }else {
             Elements tr = document.getElementById("dataList").select("tbody").select("tr");

             for (int i = 1; i < tr.size(); i++) {
                 Elements td = tr.get(i).select("td");
                 for (int j = 2; j < td.size(); j++) {

                     Log.d("========", td.get(j).text());
                     switch (j) {
                         case 2:
                             list1.add(td.get(j).text());
                             break;
                         case 3:
                             list2.add(td.get(j).text());
                             break;
                         case 4:
                             list3.add(td.get(j).text());
                             break;
                         case 5:
                             list4.add(td.get(j).text());
                             break;
                         case 6:
                             list5.add(td.get(j).text());
                             break;
                         case 7:
                             list6.add(td.get(j).text());
                             break;
                         case 8:
                             list7.add(td.get(j).text());
                             break;
                         case 9:
                             list8.add(td.get(j).text());
                             break;
                     }

                 }

             }
             Log.d("cccccc", list2.size() + "");
             for (int i = 0; i < list2.size(); i++) {
                 List<String> list = new ArrayList<String>();
              /*  Log.d("gradegrade", gradeList.get(i).get(3)+"aa");*/
                 parent.add(list2.get(i));
                 list.add(list2.get(i));
                 child.put(list2.get(i), list);
             }

             elv.setAdapter(new MyAdapter());
             elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                 @Override
                 public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {


                     return true;
                 }
             });
             gradeback.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Intent intent1 = new Intent(GradeView.this, GradeActivity.class);
                     startActivity(intent1);

                 }
             });
         }

         }


    class MyAdapter extends BaseExpandableListAdapter {
        @Override
        public int getGroupCount() {

            return parent.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            String key = parent.get(groupPosition);
            int size = child.get(key).size();
            return size;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return parent.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            String key = parent.get(groupPosition);
            return (child.get(key).get(childPosition));
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) GradeView.this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.maingrade_listitem, null);
                TextView tv_grade = (TextView) convertView.findViewById(R.id.tv_grade);
                TextView tv_class = (TextView) convertView.findViewById(R.id.tv_class);
                String a = list3.get(groupPosition);
                if (isNumeric(a)) {
                    if (Double.parseDouble(a) < 60) {

                        tv_grade.setTextColor(Color.RED);
                    } else {
                        tv_grade.setTextColor(Color.GREEN);
                    }

                } else {
                    if (a.equals("优") | a.equals("良") | a.equals("中") | a.equals("及格")) {

                        tv_grade.setTextColor(Color.GREEN);
                    } else {
                        tv_grade.setTextColor(Color.RED);
                    }

                }
                tv_grade.setText(a);
                tv_class.setText(list2.get(groupPosition));
            }


            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            if (convertView == null) {

                LayoutInflater inflater = (LayoutInflater) GradeView.this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.allgrade_item, null);

            }
            TextView tv1 = (TextView) convertView.findViewById(R.id.grade1);
            TextView tv2 = (TextView) convertView.findViewById(R.id.grade2);
            TextView tv3 = (TextView) convertView.findViewById(R.id.grade3);
            TextView tv4 = (TextView) convertView.findViewById(R.id.grade4);
            TextView tv5 = (TextView) convertView.findViewById(R.id.grade5);
            TextView tv6 = (TextView) convertView.findViewById(R.id.grade6);
            TextView tv7 = (TextView) convertView.findViewById(R.id.grade7);
            TextView tv8 = (TextView) convertView.findViewById(R.id.grade8);
            tv1.setText("课程编号:" + "" + list1.get(groupPosition));
            tv2.setText("课程名称：" + "" + list2.get(groupPosition));
            String a = list3.get(groupPosition);
            if (isNumeric(a)) {
                if (Double.parseDouble(a) < 60) {

                    tv3.setTextColor(Color.RED);
                } else {
                    tv3.setTextColor(Color.GREEN);
                }

            } else {
                if (a.equals("优") | a.equals("良") | a.equals("中") | a.equals("及格")) {

                    tv3.setTextColor(Color.GREEN);
                } else {
                    tv3.setTextColor(Color.RED);
                }

            }
            tv3.setText("成绩：" + "" + list3.get(groupPosition));
            tv4.setText("学分：" + "" + list4.get(groupPosition));
            tv5.setText("总学时：" + "" + list5.get(groupPosition));
            tv6.setText("考核方式：" + "" + list6.get(groupPosition));
            tv7.setText("课程属性：" + "" + list7.get(groupPosition));
            tv8.setText("课程性质：" + "" + list8.get(groupPosition));


            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
