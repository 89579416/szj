package com.szj.library.widget.recyclerview;

import android.view.View;

/**
 * Created by MasterFan on 2016/1/15.
 *
 * RecyclerView/ListView/GridView 滑动加载下一页时的回调接口
 */
public interface MTFOnListLoadNextPageListener {

    /**
     * 开始加载下一页
     *
     * @param view 当前RecyclerView/ListView/GridView
     */
    void onLoadNextPage(View view);
}
