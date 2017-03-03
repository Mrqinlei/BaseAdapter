package com.qinlei.num.loadrecyclerlib;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import static com.qinlei.num.loadrecyclerlib.FootViewHolder.STATUS_ERROR;
import static com.qinlei.num.loadrecyclerlib.FootViewHolder.STATUS_INVISIBLE;
import static com.qinlei.num.loadrecyclerlib.FootViewHolder.STATUS_LOADING;
import static com.qinlei.num.loadrecyclerlib.FootViewHolder.STATUS_OVER;

/**
 * Created by ql on 2017/1/24.
 */

public abstract class BaseLoadMoreAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_FOTTER = Integer.MIN_VALUE;
    private FootViewHolder footViewHolder;

    private List<T> mDatas;

    public List<T> getmDatas() {
        return mDatas;
    }

    public BaseLoadMoreAdapter(List<T> datas) {
        this.mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_FOTTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load_more, parent, false);
            return footViewHolder = new FootViewHolder(view);
        } else {
            View convertView = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType), parent, false);
            return new ViewHolder(convertView);
        }
    }

    public abstract int getLayoutId(int viewType);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM_FOTTER) {

        } else {
            convert((ViewHolder) holder, mDatas.get(position), position);
        }
    }

    public abstract void convert(ViewHolder holder, T data, int position);

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

    public int getLoad_status() {
        return footViewHolder.getLoad_status();
    }

    public void setLoadMoreLoading() {
        footViewHolder.setLoad_status(STATUS_LOADING);
    }

    public void setLoadMoreOver() {
        footViewHolder.setLoad_status(STATUS_OVER);
    }

    public void setLoadMoreInvisible() {
        footViewHolder.setLoad_status(STATUS_INVISIBLE);
    }

    public void setLoadMoreError() {
        footViewHolder.setLoad_status(STATUS_ERROR);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {//只能被调用一次
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == ITEM_FOTTER
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && holder.getLayoutPosition() == getItemCount() - 1) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }

}
