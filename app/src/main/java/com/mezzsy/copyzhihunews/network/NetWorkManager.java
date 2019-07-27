package com.mezzsy.copyzhihunews.network;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mezzsy on 2019/3/11
 * Describe: RxJava2与Retrofit2的封装
 */
public class NetWorkManager {
    private static Retrofit sRetrofit;
    private static volatile RequestService sService;

    private NetWorkManager() {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        sRetrofit = new Retrofit.Builder()
                .baseUrl(RequestService.HOST)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        sService = sRetrofit.create(RequestService.class);
    }

    public void requestNewsExtra(int id, Observer<ResponseBody> observer) {
        sService.getNewsExtra(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void requestNewsContent(int id, Observer<ResponseBody> observer) {
        sService.getNewsContent(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void loadMore(String date, Observer<ResponseBody> observer) {
        sService.getBefore(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void swipeRefresh(Observer<ResponseBody> observer) {
        sService.getLatest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static NetWorkManager getInstance() {
        return SingletonHolder.MANAGER;
    }

    private static class SingletonHolder {
        private static final NetWorkManager MANAGER = new NetWorkManager();
    }
}
