package com.qinlei.num.refreshview.refresh;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qinlei.num.refreshview.R;

import java.util.List;

/**
 * Created by ql on 2016/11/29.
 */

public abstract class LoadAdapter extends RecyclerView.Adapter {
    private static final int TYPE_NORMAL = 1000001;
    private static final int TYPE_FOOT = 1000002;
    private static final String TAG = LoadAdapter.class.getSimpleName();

    private int load_status = STATUS_GONE;

    public static final int STATUS_LOADING = 0;
    public static final int STATUS_OVER = 1;
    public static final int STATUS_GONE = 2;
    public static final int STATUS_INVISIBLE = 3;
    public static final int STATUS_ERROR = 4;

    private FootHolder footHolder;

    /**
     * 设置foot的状态来改变view
     *
     * @param load_status
     */
    public void setLoad_status(int load_status) {
        this.load_status = load_status;
        try {
            bindFooterItem(footHolder);
            notifyDataSetChanged();
        } catch (NullPointerException e) {
            Log.d(TAG, "NullPointerException: ");
        }
    }

    private List<?> mData;

    public LoadAdapter(List<?> mData) {
        this.mData = mData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL) {
            return loadOnCreateViewHolder( parent,  viewType);
        }
        if (viewType == TYPE_FOOT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.load, parent, false);
            return new FootHolder(view);
        }
        return null;
    }

    protected abstract RecyclerView.ViewHolder loadOnCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FootHolder) {
            bindFooterItem((FootHolder) holder);
            footHolder = (FootHolder) holder;
        } else {
            loadOnBindViewHolder(holder, position);
        }
    }

    /**
     * 为item设置数据
     * @param holder
     * @param position
     */
    protected abstract void loadOnBindViewHolder(RecyclerView.ViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOT;
        }
        return TYPE_NORMAL;
    }

    protected void bindFooterItem(FootHolder holder) {
        switch (load_status) {
            case STATUS_LOADING:
                holder.loadTv.setVisibility(View.VISIBLE);
                holder.loadTv.setText("loading");
                break;
            case STATUS_ERROR:
                holder.loadTv.setVisibility(View.VISIBLE);
                holder.loadTv.setText("load error");
                break;
            case STATUS_GONE:
                holder.loadTv.setVisibility(View.GONE);
                break;
            case STATUS_INVISIBLE:
                holder.loadTv.setVisibility(View.INVISIBLE);
                break;
            case STATUS_OVER:
                holder.loadTv.setVisibility(View.VISIBLE);
                holder.loadTv.setText("over");
                break;
        }
    }

    class FootHolder extends RecyclerView.ViewHolder {
        private TextView loadTv;

        public FootHolder(View itemView) {
            super(itemView);
            loadTv = (TextView) itemView.findViewById(R.id.footer);
        }
    }
}
