package com.mezzsy.copyzhihunews.presenter;

import com.google.gson.Gson;
import com.mezzsy.copyzhihunews.bean.ContentBean;
import com.mezzsy.copyzhihunews.bean.ExtraBean;
import com.mezzsy.copyzhihunews.network.NetWorkManager;
import com.mezzsy.copyzhihunews.view.ContentView;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class ContentPresenter {
    private ContentView view;

    public ContentPresenter(ContentView view) {
        this.view = view;
    }

    public void requestNewsContent(int id) {
        NetWorkManager.getInstance().requestNewsContent(id, new Observer<ResponseBody>() {
            Disposable d;
            ContentBean bean;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onNext(ResponseBody response) {
                String responseData = null;
                try {
                    responseData = response.string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                bean = gson.fromJson(responseData, ContentBean.class);
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                view.load(bean);
            }
        });
    }

    public void requestNewsExtra(int id) {
        NetWorkManager.getInstance().requestNewsExtra(id, new Observer<ResponseBody>() {
            Disposable d;
            ExtraBean bean;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onNext(ResponseBody response) {
                String responseData = null;
                try {
                    responseData = response.string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                bean = gson.fromJson(responseData, ExtraBean.class);
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                view.extra(bean);
            }
        });
    }
}
