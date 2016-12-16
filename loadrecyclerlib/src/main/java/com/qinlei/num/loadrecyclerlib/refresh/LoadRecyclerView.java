package com.qinlei.num.loadrecyclerlib.refresh;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/**
 * Created by ql on 2016/11/29.
 */

public class LoadRecyclerView extends RecyclerView {
    private static final String TAG = LoadRecyclerView.class.getSimpleName();

    private boolean isEnable = true;

    private OnLoadListener mOnLoadListener;

    private int last;

    public void setmOnLoadListener(OnLoadListener mOnLoadListener) {
        this.mOnLoadListener = mOnLoadListener;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }//设置加载可不可用

    public LoadRecyclerView(Context context) {
        super(context);
        init();
    }

    public LoadRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * 给recyclerView设置滑动到底部的监听事件
     */
    private void init() {
        this.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    last = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                }
                if (layoutManager instanceof GridLayoutManager) {
                    last = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                }
                if (layoutManager instanceof StaggeredGridLayoutManager) {
                    int[] lastList = null;
                    lastList = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                    int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastList);
                    for (int i : lastVisibleItemPositions) {
                        last = i > last ? i : last;
                    }
                }
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && last + 1 == recyclerView.getAdapter().getItemCount()
                        && recyclerView.getAdapter().getItemCount() > 1) {
                    if (((LoadAdapter) getAdapter()).getLoad_status() == LoadAdapter.STATUS_LOADING ||
                            ((LoadAdapter) getAdapter()).getLoad_status() == LoadAdapter.STATUS_OVER ||
                            ((LoadAdapter) getAdapter()).getLoad_status() == LoadAdapter.STATUS_REFRESH) {
                        //状态为加载、到达底部、刷新状态时不进行任何操作
                    } else {
                        if (isEnable) {
                            mOnLoadListener.onLoadListener();
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

}
