# RefreshLoadRecyclerView
简单的实现上拉加载，下拉刷新

效果:  
![](https://github.com/Mrqinlei/LoadRecyclerView/blob/master/PreviewImage/load.gif?raw=true)

###使用
xml中定义:  
刷新的控件可以选择任意的控件,这里使用SwipeRefreshLayout

		<android.support.v4.widget.SwipeRefreshLayout
	        android:id="@+id/swipe_refresh_layout"
	        android:layout_width="match_parent"
	        android:layout_height="match_parent">
	
	        <android.support.v7.widget.RecyclerView
	            android:id="@+id/recycler_view"
	            android:layout_width="match_parent"
	            android:layout_height="match_parent">
	
	        </android.support.v7.widget.RecyclerView>

实现IsRefreshListener接口:  
根据不同的刷新控件设置isRefresh()方法

	public class CustomIsRefreshListener implements IsRefreshListener {
	    private SwipeRefreshLayout swipeRefreshLayout;
	
	    public CustomIsRefreshListener(SwipeRefreshLayout swipeRefreshLayout) {
	        this.swipeRefreshLayout = swipeRefreshLayout;
	    }
	
	    @Override
	    public boolean isRefresh() {
	        if (swipeRefreshLayout == null) {
	            return false;
	        } else {
	            return swipeRefreshLayout.isRefreshing();
	        }
	    }
	}

MainActivity中使用:

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





