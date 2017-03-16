package com.qinlei.num.loadrecyclerlib.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qinlei.num.loadrecyclerlib.R;

/**
 * Created by ql on 2017/3/3.
 */

public class LoadMoreViewHolder extends RecyclerView.ViewHolder {
    public static final int STATUS_LOADING = Integer.MIN_VALUE + 1;
    public static final int STATUS_OVER = Integer.MIN_VALUE + 2;
    public static final int STATUS_INVISIBLE = Integer.MIN_VALUE + 3;
    public static final int STATUS_ERROR = Integer.MIN_VALUE + 4;

    private int load_status = STATUS_INVISIBLE;//默认状态

    private LinearLayout footRoot;
    private TextView footTv;
    private ProgressBar footProgressBar;

    public LoadMoreViewHolder(View v) {
        super(v);
        footRoot = (LinearLayout) (itemView.findViewById(R.id.ll_item_load_more));
        footTv = (TextView) itemView.findViewById(R.id.tv_item_load_more);
        footProgressBar = (ProgressBar) itemView.findViewById(R.id.pb_item_load_more);
    }

    public int getLoad_status() {
        return load_status;
    }

    /**
     * 设置foot的状态来改变view
     *
     * @param load_status
     */
    public void setLoad_status(int load_status) {
        this.load_status = load_status;
        bindFooterItem();
    }

    protected void bindFooterItem() {
        switch (load_status) {
            case STATUS_LOADING:
                footRoot.setVisibility(View.VISIBLE);
                footProgressBar.setVisibility(View.VISIBLE);
                footTv.setText("加载中...");
                break;
            case STATUS_ERROR:
                footRoot.setVisibility(View.VISIBLE);
                footProgressBar.setVisibility(View.GONE);
                footTv.setText("加载异常");
                break;
            case STATUS_INVISIBLE:
                footRoot.setVisibility(View.INVISIBLE);
                break;
            case STATUS_OVER:
                footRoot.setVisibility(View.VISIBLE);
                footProgressBar.setVisibility(View.GONE);
                footTv.setText("已经到底了");
                break;
        }
    }
}
