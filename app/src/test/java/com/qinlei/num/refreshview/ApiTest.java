package com.qinlei.num.refreshview;

import com.qinlei.num.refreshview.api.ServiceGenerator;
import com.qinlei.num.refreshview.api.apiservice.ParkApi;
import com.qinlei.num.refreshview.model.ThemeContentBean;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ql on 2017/2/28.
 */

public class ApiTest {
    private void print(String string) {
        System.out.println(string);
    }

    @Test
    public void getThemeNews() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1); //创建CountDownLatch
        ServiceGenerator
                .getNormalRetrofitInstance(ParkApi.class)
                .getThemeNews(13)
                .enqueue(new Callback<ThemeContentBean>() {
                    @Override
                    public void onResponse(Call<ThemeContentBean> call, Response<ThemeContentBean> response) {
                        print(response.body().toString());
                        latch.countDown();
                    }

                    @Override
                    public void onFailure(Call<ThemeContentBean> call, Throwable t) {

                    }
                });
        latch.await();
    }
}
