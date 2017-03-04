package com.qinlei.num.refreshview;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.qinlei.num.loadrecyclerlib.LoadMoreListener;
import com.qinlei.num.refreshview.adapter.MyAdapter;
import com.qinlei.num.refreshview.api.ServiceGenerator;
import com.qinlei.num.refreshview.api.apiservice.ParkApi;
import com.qinlei.num.refreshview.model.Stories;
import com.qinlei.num.refreshview.model.ThemeContentBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private List<Stories> mDatas = new ArrayList<>();
    private MyAdapter myAdapter;
    private Call refreshCall;
    private Call loadMoreCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initAdapter();
        initListener();
    }

    private void initView() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter(mDatas);
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    private void initListener() {
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        setLoadMoreAble();//设置加载可用
                        refreshData();
                    }
                });

        recyclerView.setOnScrollListener(new LoadMoreListener(
                new CustomIsRefreshListener(swipeRefreshLayout)) {
            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        });
    }

    public void refreshData() {
        myAdapter.setLoadMoreInvisible();
        cancelLoadMoreRequest();//取消加载的网络请求,如果正在加载数据的话
        swipeRefreshLayout.setRefreshing(true);
        refreshCall = ServiceGenerator
                .getNormalRetrofitInstance(ParkApi.class)
                .getThemeNews(11);
        refreshCall.enqueue(new Callback<ThemeContentBean>() {
            @Override
            public void onResponse(Call<ThemeContentBean> call, Response<ThemeContentBean> response) {
                mDatas.clear();
                mDatas.addAll(response.body().getStories());
                myAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ThemeContentBean> call, Throwable t) {
                Toast.makeText(MainActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void setLoadMoreAble() {
        myAdapter.setLoadMoreInvisible();
    }

    private void cancelLoadMoreRequest() {
        if (loadMoreCall != null) {
            loadMoreCall.cancel();
        }
    }

    public void loadMoreData() {
        myAdapter.setLoadMoreLoading();
        loadMoreCall = ServiceGenerator
                .getNormalRetrofitInstance(ParkApi.class)
                .getThemeNewsBefore(11, mDatas.get(mDatas.size() - 1).getId());
        loadMoreCall.enqueue(new Callback<ThemeContentBean>() {
            @Override
            public void onResponse(Call<ThemeContentBean> call, Response<ThemeContentBean> response) {
                if (response.body().getStories().size() > 0) {
                    myAdapter.setLoadMoreInvisible();
                    mDatas.addAll(response.body().getStories());
                    myAdapter.notifyDataSetChanged();
                } else {
                    myAdapter.setLoadMoreOver();
                }
            }

            @Override
            public void onFailure(Call<ThemeContentBean> call, Throwable t) {
                if (call.isCanceled()) {
                    Toast.makeText(MainActivity.this, "刷新时取消加载的网络请求", Toast.LENGTH_SHORT).show();
                } else {
                    myAdapter.setLoadMoreError();
                }
            }
        });
    }
}
