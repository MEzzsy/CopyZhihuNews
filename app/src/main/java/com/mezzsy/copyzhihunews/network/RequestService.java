package com.mezzsy.copyzhihunews.network;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Mezzsy on 2019/3/11
 * Describe: 描述网络请求
 */
public interface RequestService {
    String HOST = "http://news.at.zhihu.com/api/4/";

    @GET("news/latest")
    Observable<ResponseBody> getLatest();

    @GET("news/before/{date}")
    Observable<ResponseBody> getBefore(@Path("date") String date);

    @GET("news/{id}")
    Observable<ResponseBody> getNewsContent(@Path("id") int id);

    @GET("story-extra/{id}")
    Observable<ResponseBody> getNewsExtra(@Path("id") int id);
}
