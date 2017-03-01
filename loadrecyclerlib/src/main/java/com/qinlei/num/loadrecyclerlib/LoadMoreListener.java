package com.qinlei.num.loadrecyclerlib;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import static com.qinlei.num.loadrecyclerlib.BaseLoadMoreAdapter.STATUS_LOADING;
import static com.qinlei.num.loadrecyclerlib.BaseLoadMoreAdapter.STATUS_OVER;

/**
 * Created by ql on 2017/2/28.
 */

public abstract class LoadMoreListener extends RecyclerView.OnScrollListener {
    private int lastItemPosition; //最后一个item 的positon
    private int totlacount;       //总的item个数

    private BaseLoadMoreAdapter moreAdapter;
    private IsRefreshListener isRefresh;

    public LoadMoreListener(IsRefreshListener isRefresh) {
        this.isRefresh = isRefresh;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        moreAdapter = (BaseLoadMoreAdapter) recyclerView.getAdapter();
        totlacount = recyclerView.getAdapter().getItemCount();
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            lastItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        }
        if (layoutManager instanceof GridLayoutManager) {
            lastItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
        }
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastList = null;
            lastList = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastList);
            for (int i : lastVisibleItemPositions) {
                lastItemPosition = i > lastItemPosition ? i : lastItemPosition;
            }
        }
        if (newState == RecyclerView.SCROLL_STATE_IDLE
                && lastItemPosition + 1 == totlacount
                && totlacount >= moreAdapter.getDataSize() + 1) {
            if (isRefreshing()) {
                //如果正在刷新，则隐藏footer view
                moreAdapter.setLoadMoreInvisible();
            }
            if (moreAdapter.getLoad_status() == STATUS_LOADING
                    || moreAdapter.getLoad_status() == STATUS_OVER) {

            } else {
                if (isRefreshing()) {
                    //正在刷新不加载
                } else {
                    onLoadMore();
                }
            }
        }
    }

    public boolean isRefreshing() {
        return isRefresh.isRefresh();
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }

    /**
     * dataSize 默认为10 如果的数据.size() 小于dataSize 不回调
     * 有刷新控件时刷新时 不回调
     * TATUS_LOADING ，STATUS_OVER 状态下 不回调
     */
    public abstract void onLoadMore();

}
