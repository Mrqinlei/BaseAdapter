package com.qinlei.num.refreshview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qinlei.num.refreshview.refresh.LoadAdapter;

import java.util.List;

/**
 * Created by ql on 2016/11/29.
 */

public class MyLoadAdapter extends LoadAdapter {
    private List<Bean> mData;

    public MyLoadAdapter(List<?> mData) {
        super(mData);
        this.mData = (List<Bean>) mData;
    }

    @Override
    protected RecyclerView.ViewHolder loadOnCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyLoadAdapter.ViewHolder(view);
    }

    @Override
    protected void loadOnBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyLoadAdapter.ViewHolder) holder).itemTitle.setText(mData.get(position).getTitle());
        ((MyLoadAdapter.ViewHolder) holder).itemContent.setText(mData.get(position).getContent());
        ((MyLoadAdapter.ViewHolder) holder).imageView.setImageResource(mData.get(position).getResId());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemTitle;
        private TextView itemContent;
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTitle = (TextView) itemView.findViewById(R.id.list_item);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            itemContent = (TextView) itemView.findViewById(R.id.list_item_content);
        }
    }
}
