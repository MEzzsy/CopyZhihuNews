package com.mezzsy.copyzhihunews.service;

import com.mezzsy.copyzhihunews.bean.Bean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ZhihuNewsService {

    @GET("news/latest")
    Call<ResponseBody> getLatest();

    @GET("news/before/{date}")
    Call<ResponseBody> getBefore(@Path("date")String date);
}
