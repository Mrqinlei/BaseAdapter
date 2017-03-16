package com.qinlei.num.refreshview.adapter;

import android.widget.ImageView;

import com.qinlei.num.loadrecyclerlib.adapter.BaseLoadMoreAdapter;
import com.qinlei.num.loadrecyclerlib.viewholder.ViewHolder;
import com.qinlei.num.refreshview.R;
import com.qinlei.num.refreshview.model.Stories;

import java.util.List;

/**
 * Created by ql on 2017/2/28.
 */

public class LoadMoreAdapter extends BaseLoadMoreAdapter<Stories> {
    private final int IMAGE_0 = 100;
    private final int IMAGE_1 = 101;

    public LoadMoreAdapter(List<Stories> datas) {
        super(datas);
    }

    @Override
    public int getLayoutId(int viewType) {
        if (viewType == IMAGE_0) {
            return R.layout.item_load_more_1;
        } else {
            return R.layout.item_load_more_2;
        }
    }

    @Override
    public void convert(ViewHolder holder, final Stories data, int position) {
        if (getDefaultItemViewType(position) == IMAGE_1) {
            holder.setText(R.id.item_text_and_image_tv, data.getTitle());
            ImageView imageView = holder.getView(R.id.item_text_and_image_image);
            imageView.setImageResource(R.mipmap.ic_launcher);
        } else {
            holder.setText(R.id.item_text_tv, data.getTitle());
        }
    }

    @Override
    public int getDefaultItemViewType(int position) {
        if (getmDatas().get(position).getImages() == null || getmDatas().get(position).getImages().size() == 0) {
            return IMAGE_0;
        } else {
            return IMAGE_1;
        }
    }
}
