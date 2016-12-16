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

import com.alibaba.fastjson.JSON;
import com.qinlei.num.loadrecyclerlib.refresh.LoadAdapter;
import com.qinlei.num.loadrecyclerlib.refresh.LoadRecyclerView;
import com.qinlei.num.loadrecyclerlib.refresh.OnLoadListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private LoadRecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MyLoadAdapter myLoadAdapter;
    private List<Bean.ResultBean.ListBean> mData = new ArrayList<>();
    private final OkHttpClient client = new OkHttpClient();
    private int page = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initAdapter();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSwipeRefreshLayout.setRefreshing(true);
        httpLoadData();
    }

    private void httpLoadData() {
        String url = "http://v.juhe.cn/weixin/query?pno=" + page + "&ps=10&dtype=json&key=2aec6f104c6e0b0fa85ebca6e2a00cb8";
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setRefreshOver();
                        if (page != 100) {//代表加载
                            page--;
                            myLoadAdapter.setLoad_status(LoadAdapter.STATUS_ERROR);
                        } else {
                            myLoadAdapter.setLoad_status(LoadAdapter.STATUS_INVISIBLE);
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                final Bean bean = JSON.parseObject(json, Bean.class);
                if (page == 100) {
                    mData.clear();
                }
                if (bean.getError_code() != 0) {
                    page--;
                }
                mData.addAll(bean.getResult().getList());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setRefreshOver();
                        myLoadAdapter.notifyDataSetChanged();
                        if (page != 100) {//代表加载
                            if (bean.getResult().getList().size() == 10) {
                                myLoadAdapter.setLoad_status(LoadAdapter.STATUS_INVISIBLE);
                            } else {
                                myLoadAdapter.setLoad_status(LoadAdapter.STATUS_OVER);
                            }
                        }
                    }
                });
            }
        });
    }

    private void setRefreshOver() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private void initView() {
        mRecyclerView = (LoadRecyclerView) findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        myLoadAdapter = new MyLoadAdapter(mData);
        mRecyclerView.setAdapter(myLoadAdapter);
    }

    private void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 100;
                myLoadAdapter.setLoad_status(LoadAdapter.STATUS_GONE);
                httpLoadData();
            }
        });
        mRecyclerView.setmOnLoadListener(new OnLoadListener() {
            @Override
            public void onLoadListener() {
                myLoadAdapter.setLoad_status(LoadAdapter.STATUS_LOADING);
                page++;
                httpLoadData();
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
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                return true;
            case R.id.menu_linear:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                return true;
            case R.id.menu_staggered_grid:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
