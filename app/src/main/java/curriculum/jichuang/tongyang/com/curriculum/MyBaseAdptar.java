package curriculum.jichuang.tongyang.com.curriculum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tongyang on 2016/10/7.
 */

public class MyBaseAdptar  extends BaseAdapter{

    private LayoutInflater inflater;
    private List<Map<String,Object>> data;
    private TextView tv1;
    private TextView tv2;
    private List<View> holder;
    public int getCount() {
        return data.size();
    }
    public MyBaseAdptar(Context context, List<Map<String,Object>> data){
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }
    @Override

    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){

            convertView=inflater.inflate(R.layout.popu_set,null);
            tv1= (TextView) convertView.findViewById(R.id.set_tv1);
            tv2= (TextView) convertView.findViewById(R.id.set_tv2);
            holder=new ArrayList<View>();
            holder.add(tv1);
            holder.add(tv2);
            convertView.setTag(holder);
        }else {
            holder= (ArrayList) convertView.getTag();
        }
        ((TextView)holder.get(0)).setText(data.get(position).get("1").toString());
        ((TextView)holder.get(1)).setText(data.get(position).get("2").toString());
        return convertView;
    }
}
