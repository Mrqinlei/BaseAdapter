package com.qinlei.num.refreshview;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.qinlei.num.refreshview.refresh.LoadAdapter;
import com.qinlei.num.refreshview.refresh.LoadRecyclerView;
import com.qinlei.num.refreshview.refresh.OnLoadListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private LoadRecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MyLoadAdapter myLoadAdapter;
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
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        myLoadAdapter = new MyLoadAdapter(mData);
        mRecyclerView.setAdapter(myLoadAdapter);
    }

    private void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mData.add(0, "refresh_item");
                        myLoadAdapter.notifyItemInserted(0);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
        mRecyclerView.setmOnLoadListener(new OnLoadListener() {
            @Override
            public void onLoadListener() {
                myLoadAdapter.setLoad_status(LoadAdapter.STATUS_LOADING);
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mData.size() < 45) {
                            for (int i = 0; i < 10; i++) {
                                mData.add("load");
                                myLoadAdapter.notifyItemInserted(mData.size()+i);
                            }
                            mRecyclerView.setLoad(false);//加载完成设置标记
                        } else {
                            myLoadAdapter.setLoad_status(LoadAdapter.STATUS_ERROR);
                            mRecyclerView.setLoad(false);//加载完成设置标记
                        }
                    }
                }, 1000);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_grid:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
                return true;
            case R.id.menu_linear:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                return true;
            case R.id.menu_staggered_grid:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
