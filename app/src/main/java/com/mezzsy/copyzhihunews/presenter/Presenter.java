package com.mezzsy.copyzhihunews.presenter;

import com.google.gson.Gson;
import com.mezzsy.copyzhihunews.bean.BaseBean;
import com.mezzsy.copyzhihunews.bean.StoriesBean;
import com.mezzsy.copyzhihunews.model.Model;
import com.mezzsy.copyzhihunews.network.NetWorkManager;
import com.mezzsy.copyzhihunews.view.IView;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * 主界面的Presenter
 */
public class Presenter {
    private static final String TAG = "Presenter";
    //    public static final int TYPE_LOADMORE = 0;
//    public static final int TYPE_SWIPE_REFRESH = 1;
    private IView mView;
//    private Model mModel;
    //    private final List<Date> dates;
    //    private final List<StoriesBean> latestStoriesBeans;
//    private final List<StoriesBean> mStoriesBeans;
//    private final List<StoriesBean> mTopStoriesBeans;
//    private final List<String> mHeaderImages;
//    private final List<String> mHeaderTitles;
//    private String mCurrentDate;

    public Presenter(IView view) {
        this.mView = view;
//        mModel = Model.getInstance();
//        mStoriesBeans = mModel.getStoriesBeans();
//        dates = model.getDate();
//        mTopStoriesBeans = mModel.getTopStoriesBeans();
//        mHeaderImages = mModel.getHeaderImages();
//        mHeaderTitles = mModel.getHeaderTitles();
//        mCurrentDate = "";
    }

    /**
     * 上拉加载更多，获取以往信息
     */
    public void loadMore() {
        String lastDate = getModel().getStoriesBeanLastDate();
//        Log.d(TAG, "loadMore: lastDate="+lastDate);
//        String theDayBeforeLastDate = DateHelper.getSpecifiedDayBefore(lastDate);
//        Log.d(TAG, "loadMore: theDayBeforeLastDate="+theDayBeforeLastDate);

        NetWorkManager.getInstance().loadMore(lastDate
                , new Observer<ResponseBody>() {
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
                        getModel().updateStoriesBeanByLoadMore(bean.date, bean.stories);

                        isSuccess = true;
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        isSuccess = false;
                    }

                    @Override
                    public void onComplete() {
                        mView.load(isSuccess);
                    }
                });
    }

    /**
     * 下拉刷新，获取的是最新消息
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
                //获取String型的json数据
                String responseData = null;
                try {
                    responseData = response.string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //json转为BaseBean
                Gson gson = new Gson();
                BaseBean bean = gson.fromJson(responseData, BaseBean.class);

//                if (Util.isEmpty(mCurrentDate))
//                mCurrentDate = bean.date;//获取当前日期

                //头条日报数据更新
                List<StoriesBean> topStories = bean.topStories;
                getModel().updateTopStoriesBean(topStories);

                //普通日报数据更新
                getModel().updateStoriesBeanBySwipeRefresh(bean.date, bean.stories);

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
                mView.load(isSuccess);
            }
        });
    }

    private Model getModel() {
        return Model.getInstance();
    }

}
