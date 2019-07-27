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

/**
 * 主界面的Presenter
 */
public class Presenter {
    //    public static final int TYPE_LOADMORE = 0;
//    public static final int TYPE_SWIPE_REFRESH = 1;
    private static final String TAG = "Presenter";
    private IView mView;
    private Model mModel;
    //    private final List<Date> dates;
    //    private final List<StoriesBean> latestStoriesBeans;
    private final List<StoriesBean> mStoriesBeans;
    private final List<TopStoriesBean> mTopStoriesBeans;
    private final List<String> mHeaderImages;
    private final List<String> mHeaderTitles;
    private String mCurrentDate;

    public Presenter(IView view) {
        this.mView = view;
        mModel = Model.getInstance();
        mStoriesBeans = mModel.getStoriesBeans();
//        dates = model.getDate();
        mTopStoriesBeans = mModel.getTopStoriesBeans();
        mHeaderImages = mModel.getHeaderImages();
        mHeaderTitles = mModel.getHeaderTitles();
        mCurrentDate = "";
    }

    public void loadMore(){
        mCurrentDate = DateHelper.getSpecifiedDayBefore(mCurrentDate);
        NetWorkManager.getInstance().loadMore(mCurrentDate, new Observer<ResponseBody>() {
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
                mStoriesBeans.add(titleStoriesBean);
                mStoriesBeans.addAll(bean.stories);

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

                if (Util.isEmpty(mCurrentDate))
                    mCurrentDate = bean.date;//获取当前日期

                //顶部轮播图数据更新
                mTopStoriesBeans.clear();
                mTopStoriesBeans.addAll(bean.topStories);
                mHeaderTitles.clear();
                mHeaderImages.clear();
                for (TopStoriesBean topStoriesBean : mTopStoriesBeans) {
                    mHeaderImages.add(topStoriesBean.image);
                    mHeaderTitles.add(topStoriesBean.title);
                }

                List<StoriesBean> lastestStoriesBeans = bean.stories;
                //添加最新数据
                if (mStoriesBeans.size() == 0) {
                    StoriesBean titleStoriesBean = new StoriesBean();
                    titleStoriesBean.date = bean.date;
                    titleStoriesBean.isTitle = true;
                    mStoriesBeans.add(titleStoriesBean);
                    mStoriesBeans.addAll(lastestStoriesBeans);
                } else {
                    int currentLastestNumber = mStoriesBeans.get(1).serialNumber;
                    for (int i = lastestStoriesBeans.size() - 1; i >= 0; i--) {
                        StoriesBean storiesBean = lastestStoriesBeans.get(i);
                        int n = storiesBean.serialNumber;
                        if (n > currentLastestNumber) {
                            storiesBean.date = bean.date;
                            mStoriesBeans.add(1, storiesBean);
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
                mView.load(isSuccess);
            }
        });
    }

}
