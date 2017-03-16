package com.qinlei.num.loadrecyclerlib.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.qinlei.num.loadrecyclerlib.R;
import com.qinlei.num.loadrecyclerlib.viewholder.NullViewHolder;
import com.qinlei.num.loadrecyclerlib.viewholder.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ql on 2017/1/24.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEAD_VIEW = Integer.MIN_VALUE + 101;
    private static final int FOOT_VIEW = Integer.MIN_VALUE + 102;
    private FrameLayout headLayout;
    private FrameLayout footLayout;
    private LinearLayout headroot;
    private LinearLayout footroot;
    private List<View> heads = new ArrayList<>();
    private List<View> foots = new ArrayList<>();
    public boolean isHaveLoad;

    private Context context;
    private List<T> mDatas;

    public List<T> getmDatas() {
        return mDatas;
    }
    public Context getContext() {
        return context;
    }

    public BaseAdapter(List<T> datas) {
        this.mDatas = datas;
    }

    public abstract int getLayoutId(int viewType);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        if (viewType == HEAD_VIEW) {
            headLayout = (FrameLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_head, parent, false);
            headroot = (LinearLayout) headLayout.findViewById(R.id.head_root);
            for (View head : heads) {
                headroot.addView(head);
            }
            return new NullViewHolder(headLayout);
        } else if (viewType == FOOT_VIEW) {
            footLayout = (FrameLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foot, parent, false);
            footroot = (LinearLayout) footLayout.findViewById(R.id.foot_root);
            for (View head : foots) {
                footroot.addView(head);
            }
            return new NullViewHolder(footLayout);
        } else {
            View convertView = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType), parent, false);
            return new ViewHolder(convertView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == HEAD_VIEW) {

        } else if (getItemViewType(position) == FOOT_VIEW) {

        } else {
            convert((ViewHolder) holder, mDatas.get(position - 1), position - 1);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        int foot_view_position = isHaveLoad ? getItemCount() - 2 : getItemCount() - 1;
        if (position == 0) {
            return HEAD_VIEW;
        } else if (position == foot_view_position) {
            return FOOT_VIEW;
        } else {
            return getDefaultItemViewType(position - 1);
        }
    }

    //getItemViewType 的重写改到getDefaultItemViewType中
    public int getDefaultItemViewType(int position) {
        return 0;
    }

    public abstract void convert(ViewHolder holder, T data, int position);

    public void addHead(View view) {
        if (headLayout == null) {
            heads.add(view);
        } else {
            headroot.addView(view);
            heads.add(view);
        }
    }

    public void addFoot(View view) {
        if (footLayout == null) {
            foots.add(view);
        } else {
            footroot.addView(view);
            foots.add(view);
        }
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
                    return compatGridLayoutManager(gridManager, position);
                }
            });
        }
    }

    public int compatGridLayoutManager(GridLayoutManager gridManager, int position) {
        return getItemViewType(position) == FOOT_VIEW || getItemViewType(position) == HEAD_VIEW
                ? gridManager.getSpanCount() : 1;
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int foot_view_position = isHaveLoad ? getItemCount() - 2 : getItemCount() - 1;
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            compatStaggeredGridLayoutManager(holder, foot_view_position, (StaggeredGridLayoutManager.LayoutParams) lp);
        }
    }

    public void compatStaggeredGridLayoutManager(RecyclerView.ViewHolder holder, int foot_view_position, StaggeredGridLayoutManager.LayoutParams lp) {
        if (holder.getLayoutPosition() == 0 || holder.getLayoutPosition() == foot_view_position) {
            StaggeredGridLayoutManager.LayoutParams p = lp;
            p.setFullSpan(true);
        }
    }
}
