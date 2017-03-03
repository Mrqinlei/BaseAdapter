package com.qinlei.num.loadrecyclerlib;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * Created by ql on 2017/3/3.
 */

public class ViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private View mConvertView;

    public ViewHolder(View v) {
        super(v);
        mConvertView = v;
        mViews = new SparseArray<>();
    }

    public <T extends View> T getView(int id, Class<T> c) {
        View v = mViews.get(id);
        if (v == null) {
            v = mConvertView.findViewById(id);
            mViews.put(id, v);
        }
        return (T) v;
    }

    public void setText(int id, String value) {
        TextView view = getView(id, TextView.class);
        view.setText(value);
    }
}