package com.qinlei.num.loadrecyclerlib;

import android.support.v4.widget.SwipeRefreshLayout;

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
