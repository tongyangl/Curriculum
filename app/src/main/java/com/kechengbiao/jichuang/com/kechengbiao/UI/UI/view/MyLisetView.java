package com.kechengbiao.jichuang.com.kechengbiao.UI.UI.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.paging.listview.PagingListView;

/**
 * Created by 佟杨 on 2017/3/15.
 */

public class MyLisetView extends PagingListView {
    public MyLisetView(Context context) {
        super(context);
    }

    public MyLisetView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
