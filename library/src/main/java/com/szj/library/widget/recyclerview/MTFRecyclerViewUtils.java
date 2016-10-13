package com.szj.library.widget.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by MasterFan on 2016/1/15.
 * <p/>
 * RecyclerView设置Header/Footer所用到的工具类
 */
public class MTFRecyclerViewUtils {

    /**
     * 设置HeaderView
     *
     * @param recyclerView
     * @param view
     */
    public static void setHeaderView(RecyclerView recyclerView, View view) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        if (outerAdapter == null || !(outerAdapter instanceof MTFRecyclerViewAdapterWrapper)) {
            return;
        }

        MTFRecyclerViewAdapterWrapper headerAndFooterAdapter = (MTFRecyclerViewAdapterWrapper) outerAdapter;
        if (headerAndFooterAdapter.getHeaderViewsCount() == 0) {
            headerAndFooterAdapter.addHeaderView(view);
        }
    }

    /**
     * 设置FooterView
     *
     * @param recyclerView
     * @param view
     */
    public static void setFooterView(RecyclerView recyclerView, View view) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        if (outerAdapter == null || !(outerAdapter instanceof MTFRecyclerViewAdapterWrapper)) {
            return;
        }

        MTFRecyclerViewAdapterWrapper headerAndFooterAdapter = (MTFRecyclerViewAdapterWrapper) outerAdapter;
        if (headerAndFooterAdapter.getFooterViewsCount() == 0) {
            headerAndFooterAdapter.addFooterView(view);
        }
    }

    /**
     * 移除FooterView
     *
     * @param recyclerView
     */
    public static void removeFooterView(RecyclerView recyclerView) {

        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        if (outerAdapter != null && outerAdapter instanceof MTFRecyclerViewAdapterWrapper) {

            int footerViewCounter = ((MTFRecyclerViewAdapterWrapper) outerAdapter).getFooterViewsCount();
            if (footerViewCounter > 0) {
                View footerView = ((MTFRecyclerViewAdapterWrapper) outerAdapter).getFooterView();
                ((MTFRecyclerViewAdapterWrapper) outerAdapter).removeFooterView(footerView);
            }
        }
    }

    /**
     * 移除HeaderView
     *
     * @param recyclerView
     */
    public static void removeHeaderView(RecyclerView recyclerView) {

        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        if (outerAdapter != null && outerAdapter instanceof MTFRecyclerViewAdapterWrapper) {

            int headerViewCounter = ((MTFRecyclerViewAdapterWrapper) outerAdapter).getHeaderViewsCount();
            if (headerViewCounter > 0) {
                View headerView = ((MTFRecyclerViewAdapterWrapper) outerAdapter).getHeaderView();
                ((MTFRecyclerViewAdapterWrapper) outerAdapter).removeFooterView(headerView);
            }
        }
    }

    /**
     * 请使用本方法替代RecyclerView.ViewHolder的getLayoutPosition()方法
     *
     * @param recyclerView
     * @param holder
     * @return
     */
    public static int getLayoutPosition(RecyclerView recyclerView, RecyclerView.ViewHolder holder) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && outerAdapter instanceof MTFRecyclerViewAdapterWrapper) {

            int headerViewCounter = ((MTFRecyclerViewAdapterWrapper) outerAdapter).getHeaderViewsCount();
            if (headerViewCounter > 0) {
                return holder.getLayoutPosition() - headerViewCounter;
            }
        }

        return holder.getLayoutPosition();
    }

    /**
     * 请使用本方法替代RecyclerView.ViewHolder的getAdapterPosition()方法
     *
     * @param recyclerView
     * @param holder
     * @return
     */
    public static int getAdapterPosition(RecyclerView recyclerView, RecyclerView.ViewHolder holder) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && outerAdapter instanceof MTFRecyclerViewAdapterWrapper) {

            int headerViewCounter = ((MTFRecyclerViewAdapterWrapper) outerAdapter).getHeaderViewsCount();
            if (headerViewCounter > 0) {
                return holder.getAdapterPosition() - headerViewCounter;
            }
        }

        return holder.getAdapterPosition();
    }
}