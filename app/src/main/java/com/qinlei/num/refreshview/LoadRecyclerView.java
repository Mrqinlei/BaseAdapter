package com.qinlei.num.refreshview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by ql on 2016/11/29.
 */

public class LoadRecyclerView extends RecyclerView {
    private static final String TAG = LoadRecyclerView.class.getSimpleName();

    private boolean isLoad;//是否处于加载状态，需要手动设置关闭状态，不然无法正常加载

    private OnLoadListener mOnLoadListener;

    public void setmOnLoadListener(OnLoadListener mOnLoadListener) {
        this.mOnLoadListener = mOnLoadListener;
    }

    public void setLoad(boolean load) {
        isLoad = load;
    }

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
                // TODO: 2016/11/29 判断layoutManager的类型，目前还未处理
                int last = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && last + 1 == recyclerView.getAdapter().getItemCount()
                        && recyclerView.getAdapter().getItemCount() > 1) {
                    if (mOnLoadListener != null && isLoad == false) {
                        Log.d(TAG, "onScrollStateChanged: " + "load");
                        isLoad = true;
                        mOnLoadListener.onLoadListener();
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
