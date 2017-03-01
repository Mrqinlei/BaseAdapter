package com.qinlei.num.refreshview.api;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by ql on 2017/2/18.
 */

public class HttpClientHelper {
    /**
     * okhttp log配置
     *
     * @return
     */
    private static HttpLoggingInterceptor providerLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    /**
     * okhttp 通用设置
     *
     * @return
     */
    private static OkHttpClient.Builder bulidCommonConfigOkhttp() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.retryOnConnectionFailure(true);//设置失败重连
        client.connectTimeout(20, TimeUnit.SECONDS);
        client.readTimeout(20, TimeUnit.SECONDS);
        return client;
    }

    /**
     * 配置的带日志的okHttpclient
     * 下载文件时加载日志会将数据加载到内存中
     *
     * @return
     */
    public static OkHttpClient normalOkhttp() {
        OkHttpClient.Builder client = bulidCommonConfigOkhttp();
        client.addInterceptor(providerLoggingInterceptor());
        return client.build();
    }

}
