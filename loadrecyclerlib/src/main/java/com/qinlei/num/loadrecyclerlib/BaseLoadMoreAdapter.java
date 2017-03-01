package com.qinlei.num.loadrecyclerlib;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ql on 2017/1/24.
 */

public abstract class BaseLoadMoreAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_FOTTER = 100001;
    public static final int STATUS_LOADING = 10;
    public static final int STATUS_OVER = 11;
    public static final int STATUS_INVISIBLE = 13;
    public static final int STATUS_ERROR = 14;
    private int dataSize = 10;//允许加载的最小数据量
    private List<T> mDatas;
    private VHFooter vhFooter;
    private int load_status = STATUS_INVISIBLE;//默认状态

    public BaseLoadMoreAdapter(List<T> datas) {
        this.mDatas = datas;
    }

    protected int getLoad_status() {
        return load_status;
    }

    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public List<T> getmDatas() {
        return mDatas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_FOTTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load_more, parent, false);
            vhFooter = new VHFooter(view);
            bindFooterItem(vhFooter);
            return vhFooter;
        } else {
            return VH.get(parent, getLayoutId(viewType));
        }
    }

    public abstract int getLayoutId(int viewType);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM_FOTTER) {

        } else {
            convert((VH) holder, mDatas.get(position), position);
        }
    }

    public abstract void convert(VH holder, T data, int position);

    @Override
    public int getItemViewType(int position) {
        if (mDatas.size() == position) {
            return ITEM_FOTTER;
        } else {
            return getNormalItemViewType(position);
        }
    }

    //getItemViewType 的重写改到getNormalItemViewType中
    public int getNormalItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mDatas.size() + 1;
    }

    public static class VH extends RecyclerView.ViewHolder {
        private SparseArray<View> mViews;
        private View mConvertView;

        private VH(View v) {
            super(v);
            mConvertView = v;
            mViews = new SparseArray<>();
        }

        public static VH get(ViewGroup parent, int layoutId) {
            View convertView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
            return new VH(convertView);
        }

        public <T extends View> T getView(int id) {
            View v = mViews.get(id);
            if (v == null) {
                v = mConvertView.findViewById(id);
                mViews.put(id, v);
            }
            return (T) v;
        }

        public void setText(int id, String value) {
            TextView view = getView(id);
            view.setText(value);
        }
    }

    public static class VHFooter extends RecyclerView.ViewHolder {
        private LinearLayout footRoot;
        private TextView footTv;
        private ProgressBar footProgressBar;

        private VHFooter(View v) {
            super(v);
            footRoot = (LinearLayout) (itemView.findViewById(R.id.ll_item_load_more));
            footTv = (TextView) itemView.findViewById(R.id.tv_item_load_more);
            footProgressBar = (ProgressBar) itemView.findViewById(R.id.pb_item_load_more);
        }
    }

    /**
     * 设置foot的状态来改变view
     *
     * @param load_status
     */
    private void setLoad_status(int load_status) {
        this.load_status = load_status;
        if (vhFooter != null) {
            bindFooterItem(vhFooter);
            notifyItemChanged(getItemCount());
        }
    }

    protected void bindFooterItem(VHFooter holder) {
        switch (load_status) {
            case STATUS_LOADING:
                holder.footRoot.setVisibility(View.VISIBLE);
                holder.footProgressBar.setVisibility(View.VISIBLE);
                holder.footTv.setText("加载中...");
                break;
            case STATUS_ERROR:
                holder.footRoot.setVisibility(View.VISIBLE);
                holder.footProgressBar.setVisibility(View.GONE);
                holder.footTv.setText("加载异常");
                break;
            case STATUS_INVISIBLE:
                holder.footRoot.setVisibility(View.INVISIBLE);
                break;
            case STATUS_OVER:
                holder.footRoot.setVisibility(View.VISIBLE);
                holder.footProgressBar.setVisibility(View.GONE);
                holder.footTv.setText("已经到底了");
                break;
        }
    }

    public void setLoadMoreLoading() {
        setLoad_status(STATUS_LOADING);
    }

    public void setLoadMoreOver() {
        setLoad_status(STATUS_OVER);
    }

    public void setLoadMoreInvisible() {
        setLoad_status(STATUS_INVISIBLE);
    }

    public void setLoadMoreError() {
        setLoad_status(STATUS_ERROR);
    }

}
