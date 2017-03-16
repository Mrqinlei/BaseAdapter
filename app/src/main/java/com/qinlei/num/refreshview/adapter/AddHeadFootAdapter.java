package com.qinlei.num.refreshview.adapter;

import android.view.View;

import com.qinlei.num.loadrecyclerlib.adapter.BaseAdapter;
import com.qinlei.num.loadrecyclerlib.viewholder.ViewHolder;
import com.qinlei.num.refreshview.R;
import com.qinlei.num.refreshview.listener.OnItemClickListener;
import com.qinlei.num.refreshview.model.HeadFootBean;

import java.util.List;

/**
 * Created by ql on 2017/3/14.
 */

public class AddHeadFootAdapter extends BaseAdapter<HeadFootBean> {
    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public AddHeadFootAdapter(List datas) {
        super(datas);
    }

    @Override
    public int getLayoutId(int viewType) {
        if (viewType == TYPE_ONE) {
            return R.layout.item_head_foot_1;
        } else {
            return R.layout.item_head_foot_2;
        }
    }

    @Override
    public void convert(ViewHolder holder, final HeadFootBean data, final int position) {
        if (getDefaultItemViewType(position) == TYPE_ONE) {
            holder.setText(R.id.item_head_foot_one, data.getName());
            holder.getmConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onClick(v, position);
                    }
                }
            });
        }
        if (getDefaultItemViewType(position) == TYPE_TWO) {
            holder.setText(R.id.item_head_foot_two, data.getName());
            holder.getmConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onClick(v, position);
                    }
                }
            });
        }
    }

    @Override
    public int getDefaultItemViewType(int position) {
        if (getmDatas().get(position).getType() % 2 == 0) {
            return TYPE_ONE;
        } else {
            return TYPE_TWO;
        }
    }
}
