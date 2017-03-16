package com.qinlei.num.loadrecyclerlib;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.qinlei.num.loadrecyclerlib.adapter.BaseLoadMoreAdapter;

import static com.qinlei.num.loadrecyclerlib.viewholder.LoadMoreViewHolder.STATUS_LOADING;
import static com.qinlei.num.loadrecyclerlib.viewholder.LoadMoreViewHolder.STATUS_OVER;

/**
 * Created by ql on 2017/2/28.
 */

public abstract class LoadMoreListener extends RecyclerView.OnScrollListener {
    private int dataSize = 10;//允许加载的最小数据量

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    private BaseLoadMoreAdapter moreAdapter;
    private IsRefreshListener isRefresh;

    public LoadMoreListener(IsRefreshListener isRefresh) {
        this.isRefresh = isRefresh;
    }

    /**
     * 获取列表中最后一个显示的item 的 position
     *
     * @param lastItemPosition
     * @return
     */
    private int getLastItemPosition(int lastItemPosition, RecyclerView recyclerView) {
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
        return lastItemPosition;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        int lastItemPosition = 0;
        int totalCount = recyclerView.getAdapter().getItemCount();
        lastItemPosition = getLastItemPosition(lastItemPosition, recyclerView);

        moreAdapter = (BaseLoadMoreAdapter) recyclerView.getAdapter();
        if (newState == RecyclerView.SCROLL_STATE_IDLE
                && lastItemPosition + 1 == totalCount
                && totalCount >= dataSize + 1) {
            if (isRefreshing()) {
                //如果正在刷新，则隐藏 footer view
                moreAdapter.setLoadMoreInvisible();
            } else {
                if (moreAdapter.getLoad_status() == STATUS_LOADING
                        || moreAdapter.getLoad_status() == STATUS_OVER) {

                } else {
                    moreAdapter.setLoadMoreLoading();
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
     * dataSize 默认为10 如果的数据.size() 小于dataSize 不加载
     * 有刷新控件时刷新时 不回调
     * TATUS_LOADING ，STATUS_OVER 状态下 不回调
     */
    public abstract void onLoadMore();

}
