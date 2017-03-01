package com.qinlei.num.refreshview.api.apiservice;


import com.qinlei.num.refreshview.model.ThemeContentBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by wkchen on 2017/1/3.
 */

public interface ParkApi {
    @GET("theme/{id}")
    Call<ThemeContentBean> getThemeNews(@Path("id") int id);

    @GET("theme/{id}/before/{themeid}")
    Call<ThemeContentBean> getThemeNewsBefore(@Path("id") int id, @Path("themeid") int themeid);

}
