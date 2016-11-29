package com.qinlei.num.refreshview;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private LoadRecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LoadAdapter mLoadAdapter;
    private List<String> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        initAdapter();
        initListener();
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            mData.add("refresh");
        }
    }

    private void initView() {
        mRecyclerView = (LoadRecyclerView) findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mLoadAdapter = new LoadAdapter(mData);
        mRecyclerView.setAdapter(mLoadAdapter);
    }

    private void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mData.add(0, "refresh_item");
                        mLoadAdapter.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                },1000);
            }
        });
        mRecyclerView.setmOnLoadListener(new OnLoadListener() {
            @Override
            public void onLoadListener() {
                mLoadAdapter.setLoad_status(LoadAdapter.STATUS_LOADING);
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(mData.size()<45){
                            for (int i = 0; i < 10; i++) {
                                mData.add("load");
                                mLoadAdapter.notifyDataSetChanged();
                            }
                            mRecyclerView.setLoad(false);//加载完成设置标记
                        }else {
                            mLoadAdapter.setLoad_status(LoadAdapter.STATUS_ERROR);
                            mRecyclerView.setLoad(false);//加载完成设置标记
                        }
                    }
                }, 1000);
            }
        });
    }
}
