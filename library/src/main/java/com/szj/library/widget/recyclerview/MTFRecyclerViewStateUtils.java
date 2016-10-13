package com.szj.library.widget.recyclerview;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by MasterFan on 2016/1/15.
 *
 * 分页展示数据时，RecyclerView的FooterView State 操作工具类
 *
 * RecyclerView一共有几种State：Normal/Loading/Error/TheEnd
 */
public class MTFRecyclerViewStateUtils {

    /**
     * 设置headerAndFooterAdapter的FooterView State
     *
     * @param instance      context
     * @param recyclerView  recyclerView
     * @param pageSize      分页展示时，recyclerView每一页的数量
     * @param state         FooterView State
     * @param errorListener FooterView处于Error状态时的点击事件
     */
    public static void setFooterViewState(Activity instance, RecyclerView recyclerView, int pageSize, MTFLoadingFooter.State state, View.OnClickListener errorListener) {

        if(instance==null || instance.isFinishing()) {
            return;
        }

        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        if (outerAdapter == null || !(outerAdapter instanceof MTFRecyclerViewAdapterWrapper)) {
            return;
        }

        MTFRecyclerViewAdapterWrapper headerAndFooterAdapter = (MTFRecyclerViewAdapterWrapper) outerAdapter;

        //只有一页的时候，就别加什么FooterView了
        if (headerAndFooterAdapter.getInnerAdapter().getItemCount() < pageSize) {
            return;
        }

        MTFLoadingFooter footerView;

        //已经有footerView了
        if (headerAndFooterAdapter.getFooterViewsCount() > 0) {
            footerView = (MTFLoadingFooter) headerAndFooterAdapter.getFooterView();
            footerView.setState(state);

            if (state == MTFLoadingFooter.State.NetWorkError) {
                footerView.setOnClickListener(errorListener);
            }
            recyclerView.scrollToPosition(headerAndFooterAdapter.getItemCount() - 1);
        } else {
            footerView = new MTFLoadingFooter(instance);
            footerView.setState(state);

            if (state == MTFLoadingFooter.State.NetWorkError) {
                footerView.setOnClickListener(errorListener);
            }

            headerAndFooterAdapter.addFooterView(footerView);
            recyclerView.scrollToPosition(headerAndFooterAdapter.getItemCount() - 1);
        }
    }

    /**
     * 获取当前RecyclerView.FooterView的状态
     *
     * @param recyclerView
     */
    public static MTFLoadingFooter.State getFooterViewState(RecyclerView recyclerView) {

        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && outerAdapter instanceof MTFRecyclerViewAdapterWrapper) {
            if (((MTFRecyclerViewAdapterWrapper) outerAdapter).getFooterViewsCount() > 0) {
                MTFLoadingFooter footerView = (MTFLoadingFooter) ((MTFRecyclerViewAdapterWrapper) outerAdapter).getFooterView();
                return footerView.getState();
            }
        }

        return MTFLoadingFooter.State.Normal;
    }

    /**
     * 设置当前RecyclerView.FooterView的状态
     *
     * @param recyclerView
     * @param state
     */
    public static void setFooterViewState(RecyclerView recyclerView, MTFLoadingFooter.State state) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && outerAdapter instanceof MTFRecyclerViewAdapterWrapper) {
            if (((MTFRecyclerViewAdapterWrapper) outerAdapter).getFooterViewsCount() > 0) {
                MTFLoadingFooter footerView = (MTFLoadingFooter) ((MTFRecyclerViewAdapterWrapper) outerAdapter).getFooterView();
                footerView.setState(state);
            }
        }
    }
}
