package com.mezzsy.copyzhihunews.util;

import com.mezzsy.copyzhihunews.service.ZhihuNewsService;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpUtil {
    private static Retrofit retrofit;

    static {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://news.at.zhihu.com/api/4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public static void sendHttpRequest(String url, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void swipeRefresh(Callback<ResponseBody> callback) {
        ZhihuNewsService service = retrofit.create(ZhihuNewsService.class);

        Call<ResponseBody> call = service.getLatest();
        call.enqueue(callback);
    }

    public static void loadMore(String date, Callback<ResponseBody> callback) {
        ZhihuNewsService service = retrofit.create(ZhihuNewsService.class);

        Call<ResponseBody> call = service.getBefore(date);
        call.enqueue(callback);
    }


}
