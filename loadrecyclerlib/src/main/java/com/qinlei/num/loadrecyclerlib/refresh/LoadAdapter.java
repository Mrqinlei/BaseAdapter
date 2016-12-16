package com.qinlei.num.loadrecyclerlib.refresh;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qinlei.num.loadrecyclerlib.R;

import java.util.List;

/**
 * Created by ql on 2016/11/29.
 */

public abstract class LoadAdapter extends RecyclerView.Adapter {
    private static final int TYPE_NORMAL = 1000001;
    private static final int TYPE_FOOT = 1000002;
    private static final String TAG = LoadAdapter.class.getSimpleName();

    private int load_status = STATUS_GONE;

    public static final int STATUS_LOADING = 0;//该状态加载不可用
    public static final int STATUS_OVER = 1;//该状态加载不可用
    public static final int STATUS_REFRESH = 5;//该状态加载不可用
    public static final int STATUS_GONE = 2;
    public static final int STATUS_INVISIBLE = 3;
    public static final int STATUS_ERROR = 4;

    public int getLoad_status() {
        return load_status;
    }

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
            notifyItemChanged(getItemCount());
        } catch (NullPointerException e) {
        }
    }

    private List<?> mData;

    public LoadAdapter(List<?> mData) {
        this.mData = mData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL) {
            return loadOnCreateViewHolder(parent, viewType);
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
     *
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
                holder.footRoot.setVisibility(View.VISIBLE);
                holder.mProgressBar.setVisibility(View.VISIBLE);
                holder.loadTv.setText("loading");
                break;
            case STATUS_ERROR:
                holder.footRoot.setVisibility(View.VISIBLE);
                holder.mProgressBar.setVisibility(View.GONE);
                holder.loadTv.setText("load error");
                break;
            case STATUS_GONE:
                holder.footRoot.setVisibility(View.GONE);
                break;
            case STATUS_INVISIBLE:
                holder.footRoot.setVisibility(View.INVISIBLE);
                break;
            case STATUS_OVER:
                holder.footRoot.setVisibility(View.VISIBLE);
                holder.mProgressBar.setVisibility(View.GONE);
                holder.loadTv.setText("over");
                break;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {//只能被调用一次
        super.onAttachedToRecyclerView(recyclerView);
        Log.d(TAG, "onAttachedToRecyclerView: ");
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_FOOT
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if(lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && holder.getLayoutPosition() == getItemCount()-1) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }

    class FootHolder extends RecyclerView.ViewHolder {
        private RelativeLayout footRoot;
        private TextView loadTv;
        private ProgressBar mProgressBar;

        public FootHolder(View itemView) {
            super(itemView);
            footRoot = (RelativeLayout) (itemView = itemView.findViewById(R.id.root));
            loadTv = (TextView) itemView.findViewById(R.id.footer_textview);
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.footer_progressBar);
        }
    }
}
