package com.qinlei.num.loadrecyclerlib.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qinlei.num.loadrecyclerlib.R;
import com.qinlei.num.loadrecyclerlib.viewholder.LoadMoreViewHolder;

import java.util.List;

import static com.qinlei.num.loadrecyclerlib.viewholder.LoadMoreViewHolder.STATUS_ERROR;
import static com.qinlei.num.loadrecyclerlib.viewholder.LoadMoreViewHolder.STATUS_INVISIBLE;
import static com.qinlei.num.loadrecyclerlib.viewholder.LoadMoreViewHolder.STATUS_LOADING;
import static com.qinlei.num.loadrecyclerlib.viewholder.LoadMoreViewHolder.STATUS_OVER;

/**
 * Created by ql on 2017/1/24.
 */

public abstract class BaseLoadMoreAdapter<T> extends BaseAdapter<T> {
    private static final int ITEM_FOTTER = Integer.MIN_VALUE;
    private LoadMoreViewHolder loadMoreViewHolder;

    public BaseLoadMoreAdapter(List<T> datas) {
        super(datas);
        isHaveLoad = true;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_FOTTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load_more, parent, false);
            return loadMoreViewHolder = new LoadMoreViewHolder(view);
        } else {
            return super.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM_FOTTER) {

        } else {
            super.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() - 1 == position) {
            return ITEM_FOTTER;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }

    public int getLoad_status() {
        return loadMoreViewHolder.getLoad_status();
    }

    public void setLoadMoreLoading() {
        if (loadMoreViewHolder != null)
            loadMoreViewHolder.setLoad_status(STATUS_LOADING);
    }

    public void setLoadMoreOver() {
        if (loadMoreViewHolder != null)
            loadMoreViewHolder.setLoad_status(STATUS_OVER);
    }

    public void setLoadMoreInvisible() {
        if (loadMoreViewHolder != null)
            loadMoreViewHolder.setLoad_status(STATUS_INVISIBLE);
    }

    public void setLoadMoreError() {
        if (loadMoreViewHolder != null)
            loadMoreViewHolder.setLoad_status(STATUS_ERROR);
    }

    @Override
    public int compatGridLayoutManager(GridLayoutManager gridManager, int position) {
        if (getItemViewType(position) == ITEM_FOTTER) {
            return getItemViewType(position) == ITEM_FOTTER
                    ? gridManager.getSpanCount() : 1;
        } else {
            return super.compatGridLayoutManager(gridManager, position);
        }
    }

    @Override
    public void compatStaggeredGridLayoutManager(RecyclerView.ViewHolder holder, int foot_view_position, StaggeredGridLayoutManager.LayoutParams lp) {
        if (holder.getLayoutPosition() == getItemCount() - 1) {
            StaggeredGridLayoutManager.LayoutParams p = lp;
            p.setFullSpan(true);
        } else {
            super.compatStaggeredGridLayoutManager(holder, foot_view_position, lp);
        }
    }
}
