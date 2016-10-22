package curriculum.jichuang.tongyang.com.curriculum;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import static curriculum.jichuang.tongyang.com.curriculum.LibraryFragment.list1;
import static java.security.AccessController.getContext;

/**
 * Created by tongyang on 2016/10/16.
 */

public class More_libraryBook extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_library_book);

        ListView morelist = (ListView) findViewById(R.id.More_list);
            morelist.setDividerHeight(0);
                morelist.setAdapter(new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return list1.size();
                    }

                    @Override
                    public Object getItem(int position) {
                        return list1.get(position);
                    }

                    @Override
                    public long getItemId(int position) {
                        return position;
                    }

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        if (convertView == null) {
                            LayoutInflater i = getLayoutInflater();
                            convertView = i.inflate(R.layout.more_lv, null);

                        }
                        TextView tv1 = (TextView) convertView.findViewById(R.id.more_tv1);
                        TextView tv2 = (TextView) convertView.findViewById(R.id.more_tv2);
                        tv1.setText(list1.get(position).get(0));
                        Log.d("fff", tv1.getText().toString());
                        tv2.setText(list1.get(position).get(1));
                        return convertView;
                    }
                });
                morelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Dialog dialog = new Dialog(More_libraryBook.this);
                        LayoutInflater inflater1 = getLayoutInflater();
                        View v = inflater1.inflate(R.layout.library_more_more, null);

                        TextView tv2 = (TextView) v.findViewById(R.id.tv2);
                        TextView tv1 = (TextView) v.findViewById(R.id.tv1);
                        tv1.setTextColor(Color.RED);
                        tv1.setText(list1.get(position).get(0));
                        tv2.setText(list1.get(position).get(1));
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(v);
                        dialog.setCancelable(true);
                        //dialog.setTitle(list1.get(position).get(0));
                        dialog.show();
                        Window dialogWindow = dialog.getWindow();
                        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                        WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
                        int width = wm.getDefaultDisplay().getWidth();
                        int height = wm.getDefaultDisplay().getHeight();
                        lp.width = (int) (width * 0.8);
                        lp.height = (int) (height * 0.6);
                        // dialogWindow.setAttributes(lp);
                        dialog.getWindow().setAttributes(lp);
                    }
                });

    }
}
