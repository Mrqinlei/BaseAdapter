package com.qinlei.num.refreshview;

import android.support.v4.widget.SwipeRefreshLayout;

import com.qinlei.num.loadrecyclerlib.IsRefreshListener;

/**
 * Created by ql on 2017/3/1.
 */

public class CustomIsRefreshListener implements IsRefreshListener {
    private SwipeRefreshLayout swipeRefreshLayout;

    public CustomIsRefreshListener(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    public boolean isRefresh() {
        if (swipeRefreshLayout == null) {
            return false;
        } else {
            return swipeRefreshLayout.isRefreshing();
        }
    }
}
