package com.szj.library.widget.recyclerview;

import android.support.v7.widget.GridLayoutManager;

/**
 * Created by MasterFan on 2016/1/15.
 * <p/>
 * RecyclerView为GridLayoutManager或StaggeredGridLayoutManager时，设置了HeaderView，就会用到这个SpanSizeLookup
 */
public class MTFHeaderSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

    private MTFRecyclerViewAdapterWrapper adapter;
    private int mSpanSize = 1;

    public MTFHeaderSpanSizeLookup(MTFRecyclerViewAdapterWrapper adapter, int spanSize) {
        this.adapter = adapter;
        this.mSpanSize = spanSize;
    }

    @Override
    public int getSpanSize(int position) {
        boolean isHeaderOrFooter = adapter.isHeader(position) || adapter.isFooter(position);
        return isHeaderOrFooter ? mSpanSize : 1;
    }
}