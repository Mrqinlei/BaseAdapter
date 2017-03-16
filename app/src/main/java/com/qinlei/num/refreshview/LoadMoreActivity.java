package com.qinlei.num.refreshview;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.qinlei.num.loadrecyclerlib.LoadMoreListener;
import com.qinlei.num.refreshview.adapter.LoadMoreAdapter;
import com.qinlei.num.refreshview.api.ServiceGenerator;
import com.qinlei.num.refreshview.api.apiservice.ParkApi;
import com.qinlei.num.refreshview.listener.CustomIsRefreshListener;
import com.qinlei.num.refreshview.model.Stories;
import com.qinlei.num.refreshview.model.ThemeContentBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadMoreActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private List<Stories> mDatas = new ArrayList<>();
    private LoadMoreAdapter loadMoreAdapter;
    private Call refreshCall;
    private Call loadMoreCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more);
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
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        loadMoreAdapter = new LoadMoreAdapter(mDatas);
        recyclerView.setAdapter(loadMoreAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        swipeRefreshLayout.setRefreshing(true);
        refreshData();
    }

    private void initListener() {
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
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
        cancelLoadMoreRequest();
        refreshCall = ServiceGenerator
                .getNormalRetrofitInstance(ParkApi.class)
                .getThemeNews(13);
        refreshCall.enqueue(new Callback<ThemeContentBean>() {
            @Override
            public void onResponse(Call<ThemeContentBean> call, Response<ThemeContentBean> response) {
                mDatas.clear();
                mDatas.addAll(response.body().getStories());
                loadMoreAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ThemeContentBean> call, Throwable t) {
                Toast.makeText(LoadMoreActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void loadMoreData() {
        loadMoreCall = ServiceGenerator
                .getNormalRetrofitInstance(ParkApi.class)
                .getThemeNewsBefore(13, mDatas.get(mDatas.size() - 1).getId());
        loadMoreCall.enqueue(new Callback<ThemeContentBean>() {
            @Override
            public void onResponse(Call<ThemeContentBean> call, Response<ThemeContentBean> response) {
                if (response.body().getStories().size() > 0) {
                    loadMoreAdapter.setLoadMoreInvisible();
                    mDatas.addAll(response.body().getStories());
                    loadMoreAdapter.notifyDataSetChanged();
                } else {
                    loadMoreAdapter.setLoadMoreOver();
                }
            }

            @Override
            public void onFailure(Call<ThemeContentBean> call, Throwable t) {
                if (call.isCanceled()) {
                    Toast.makeText(LoadMoreActivity.this, "刷新时取消加载的网络请求", Toast.LENGTH_SHORT).show();
                } else {
                    loadMoreAdapter.setLoadMoreError();
                }
            }
        });
    }

    //取消加载的网络请求,如果正在加载数据的话,并重置FootView
    private void cancelLoadMoreRequest() {
        loadMoreAdapter.setLoadMoreInvisible();
        if (loadMoreCall != null) {
            loadMoreCall.cancel();
        }
    }
}
