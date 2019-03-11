package com.mezzsy.copyzhihunews.presenter;

import com.google.gson.Gson;
import com.mezzsy.copyzhihunews.bean.BaseBean;
import com.mezzsy.copyzhihunews.bean.StoriesBean;
import com.mezzsy.copyzhihunews.bean.TopStoriesBean;
import com.mezzsy.copyzhihunews.model.Model;
import com.mezzsy.copyzhihunews.network.NetWorkManager;
import com.mezzsy.copyzhihunews.util.DateHelper;
import com.mezzsy.copyzhihunews.util.Util;
import com.mezzsy.copyzhihunews.view.IView;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class Presenter {
    //    public static final int TYPE_LOADMORE = 0;
//    public static final int TYPE_SWIPE_REFRESH = 1;
    private static final String TAG = "Presenter";
    private IView view;
    private Model model;
    //    private final List<Date> dates;
    //    private final List<StoriesBean> latestStoriesBeans;
    private final List<StoriesBean> storiesBeans;
    private final List<TopStoriesBean> topStoriesBeans;
    private final List<String> headerImages;
    private final List<String> headerTitles;
    private String currentDate;

    public Presenter(IView view) {
        this.view = view;
        model = Model.getInstance();
        storiesBeans = model.getStoriesBeans();
//        dates = model.getDate();
        topStoriesBeans = model.getTopStoriesBeans();
        headerImages = model.getHeaderImages();
        headerTitles = model.getHeaderTitles();
        currentDate = "";
    }

    public void loadMore(){
        currentDate = DateHelper.getSpecifiedDayBefore(currentDate);
        NetWorkManager.getInstance().loadMore(currentDate, new Observer<ResponseBody>() {
            Disposable d;
            boolean isSuccess;

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
                BaseBean bean = gson.fromJson(responseData, BaseBean.class);

                //更新数据
                StoriesBean titleStoriesBean = new StoriesBean();
                titleStoriesBean.isTitle = true;
                titleStoriesBean.date = bean.date;
                storiesBeans.add(titleStoriesBean);
                storiesBeans.addAll(bean.stories);

                isSuccess = true;
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                isSuccess = false;
            }

            @Override
            public void onComplete() {
                view.load(isSuccess);
            }
        });
    }

    /**
     * 下拉刷新
     */
    public void swipeRefresh() {
        NetWorkManager.getInstance().swipeRefresh(new Observer<ResponseBody>() {
            Disposable d;
            boolean isSuccess = true;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            /**
             * 更新数据
             *
             * @param response
             */
            @Override
            public void onNext(ResponseBody response) {
                String responseData = null;
                try {
                    responseData = response.string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                BaseBean bean = gson.fromJson(responseData, BaseBean.class);

                if (Util.isEmpty(currentDate))
                    currentDate = bean.date;//获取当前日期

                //顶部轮播图数据更新
                topStoriesBeans.clear();
                topStoriesBeans.addAll(bean.topStories);
                headerTitles.clear();
                headerImages.clear();
                for (TopStoriesBean topStoriesBean : topStoriesBeans) {
                    headerImages.add(topStoriesBean.image);
                    headerTitles.add(topStoriesBean.title);
                }

                List<StoriesBean> lastestStoriesBeans = bean.stories;
                //添加最新数据
                if (storiesBeans.size() == 0) {
                    StoriesBean titleStoriesBean = new StoriesBean();
                    titleStoriesBean.date = bean.date;
                    titleStoriesBean.isTitle = true;
                    storiesBeans.add(titleStoriesBean);
                    storiesBeans.addAll(lastestStoriesBeans);
                } else {
                    int currentLastestNumber = storiesBeans.get(1).serialNumber;
                    for (int i = lastestStoriesBeans.size() - 1; i >= 0; i--) {
                        StoriesBean storiesBean = lastestStoriesBeans.get(i);
                        int n = storiesBean.serialNumber;
                        if (n > currentLastestNumber) {
                            storiesBean.date = bean.date;
                            storiesBeans.add(1, storiesBean);
                        }
                    }
                }
                isSuccess = true;
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                d.dispose();
                isSuccess = false;
            }

            /**
             * 回调
             */
            @Override
            public void onComplete() {
                view.load(isSuccess);
            }
        });
    }

//    private boolean contain(int id){
//        for (StoriesBean bean:latestStoriesBeans){
//            if (bean==null)continue;
//            if (id==bean.getId())return true;
//        }
//        return false;
//    }
}
