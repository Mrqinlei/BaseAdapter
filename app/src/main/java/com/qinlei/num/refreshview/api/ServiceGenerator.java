package com.qinlei.num.refreshview.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ql on 2017/2/18.
 */

public class ServiceGenerator {
    private static final String baseUrl = "http://news-at.zhihu.com/api/4/";

    private static int NORMAL = 1000;
    private static int WITH_CACHE = 1002;
    private static int WITH_REQUEST_CALLBACK = 1003;
    private static int WITH_RESPONSE_CALLBACK = 1004;

    private static Map<Integer, Retrofit> map = new HashMap<>();

    ////////////////////////////对外公开的方法//////////////////////////////////
    public static <T> T getNormalRetrofitInstance(Class<T> tClass) {
        return getInstance(NORMAL, tClass);
    }

    /////////////////////////////////////////////////////////////////////////////

    private static <T> T getInstance(int KEY, Class<T> tClass) {
        Retrofit singleton = null;
        if (KEY == NORMAL) {
            if (map.get(KEY) == null) {
                synchronized (ServiceGenerator.class){
                    if(map.get(KEY) == null){
                        singleton = createNormalRrtrofit();
                        map.put(KEY, singleton);
                    }
                }
            } else {
                singleton = map.get(KEY);
            }
        }
        return singleton.create(tClass);
    }

    private final static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .serializeNulls()
            .create();

    /**
     * retrofit 通用设置
     */
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson));

    /**
     * 创建普通的service
     */
    private static Retrofit createNormalRrtrofit() {
        return builder
                .client(HttpClientHelper.normalOkhttp())
                .build();
    }
}
