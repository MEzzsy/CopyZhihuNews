package com.mezzsy.copyzhihunews.model;

import com.mezzsy.copyzhihunews.bean.BaseBean;
import com.mezzsy.copyzhihunews.bean.StoriesBean;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private static final String TAG = "Model";
    //    private final List<StoriesBean> latestStoriesBeans;
    /**
     * 普通日报的集合，用于RecyclerView。在RecyclerView中，Item分为日期Item和日报Item，为了简便，
     * 这里将日期Item也定为StoriesBean，其中的isDateTitle为true。
     */
    private final List<StoriesBean> mStoriesBeans;
    private final List<StoriesBean> mStoriesBeansWithoutDate;//不带日期的
    private final List<StoriesBean> mTopStoriesBeans;

    /**
     * 轮播图所需数据，内容为mTopStoriesBeans的Image和Title
     */
    private final List<String> mHeaderImages;
    private final List<String> mHeaderTitles;
    //    private final List<Date> date;
    private List<BaseBean> beans;

    private Model() {
        mStoriesBeans = new ArrayList<>();
        mStoriesBeansWithoutDate = new ArrayList<>();
//        latestStoriesBeans = new ArrayList<>();
        mTopStoriesBeans = new ArrayList<>();
//        date = new ArrayList<>();
        mHeaderImages = new ArrayList<>();
        mHeaderTitles = new ArrayList<>();
    }

//    public List<StoriesBean> getLatestStoriesBeans() {
//        return latestStoriesBeans;
//    }

    public List<StoriesBean> getStoriesBeans() {
        return mStoriesBeans;
    }

    public List<StoriesBean> getStoriesBeansWithoutDate() {
        return mStoriesBeansWithoutDate;
    }

    /**
     * 更新普通日报，被下拉刷新调用
     *
     * @param storiesBeans
     */
    public void updateStoriesBeanBySwipeRefresh(String date, List<StoriesBean> storiesBeans) {
        if (mStoriesBeans.size() == 0) {//初始化
            StoriesBean titleStoriesBean = new StoriesBean();
            titleStoriesBean.date = date;
            titleStoriesBean.isDateTitle = true;
            mStoriesBeans.add(titleStoriesBean);
            mStoriesBeans.addAll(storiesBeans);

            mStoriesBeansWithoutDate.addAll(storiesBeans);
        } else {//更新今天的普通日报，将之前的删除，再添加获取的日报
            int currentLastestNumber = mStoriesBeans.get(1).gaPrefix;
            for (int i = storiesBeans.size() - 1; i >= 0; i--) {
                StoriesBean storiesBean = storiesBeans.get(i);
                int n = storiesBean.gaPrefix;
                if (n > currentLastestNumber) {
                    storiesBean.date = date;
                    mStoriesBeans.add(1, storiesBean);
                }
            }

            currentLastestNumber = mStoriesBeansWithoutDate.get(0).gaPrefix;
            for (int i = storiesBeans.size() - 1; i >= 0; i--) {
                StoriesBean storiesBean = storiesBeans.get(i);
                int n = storiesBean.gaPrefix;
                if (n > currentLastestNumber) {
                    storiesBean.date = date;
                    mStoriesBeansWithoutDate.add(0, storiesBean);
                }
            }
        }
    }

    /**
     * 更新普通日报，被加载更多调用
     *
     * @param storiesBeans
     */
    public void updateStoriesBeanByLoadMore(String date, List<StoriesBean> storiesBeans) {
        StoriesBean titleStoriesBean = new StoriesBean();
        titleStoriesBean.isDateTitle = true;
        titleStoriesBean.date = date;
        mStoriesBeans.add(titleStoriesBean);

        mStoriesBeans.addAll(storiesBeans);

        mStoriesBeansWithoutDate.addAll(storiesBeans);
    }

    /**
     * 获取mStoriesBeans中最早的一天，用于加载更多
     *
     * @return
     */
    public String getStoriesBeanLastDate() {
        for (int i = mStoriesBeans.size() - 1; i >= 0; i--) {
            StoriesBean bean = mStoriesBeans.get(i);
            if (bean.isDateTitle) {
                return bean.date;
            }
        }
        return null;
    }

//    public List<Date> getDate() {
//        return date;
//    }

    public List<StoriesBean> getTopStoriesBeans() {
        return mTopStoriesBeans;
    }

    /**
     * 更新头条日报，主要是将之前的清空，再将传入的topStoriesBeans全部放入。
     *
     * @param topStoriesBeans
     */
    public void updateTopStoriesBean(List<StoriesBean> topStoriesBeans) {
//        //添加新的，并放在头部
//        int firstGaPrefix = mTopStoriesBeans.get(0).gaPrefix;
//        for (StoriesBean topStoriesBean : topStoriesBeans) {
//            if (topStoriesBean.gaPrefix > firstGaPrefix) {
//                mTopStoriesBeans.add(0, topStoriesBean);
//            } else {
//                break;
//            }
//        }
//
//        //删除旧的头条日报
//        int numOfDeleted = mTopStoriesBeans.size() - topStoriesBeans.size();//删除的个数
//        for (int i = 0; i < numOfDeleted; i++) {
//            mTopStoriesBeans.remove(mTopStoriesBeans.size() - 1);
//        }
        //由于个数比较少，无需做算法上的优化
        mTopStoriesBeans.clear();
        mTopStoriesBeans.addAll(topStoriesBeans);

        mHeaderTitles.clear();
        mHeaderImages.clear();
        for (StoriesBean topStoriesBean : topStoriesBeans) {
            mHeaderImages.add(topStoriesBean.image);
            mHeaderTitles.add(topStoriesBean.title);
        }
    }

    public List<String> getHeaderImages() {
        return mHeaderImages;
    }

    public List<String> getHeaderTitles() {
        return mHeaderTitles;
    }

    public static Model getInstance() {
        return SingletonHolder.singleton;
    }

    private static class SingletonHolder {
        private static final Model singleton = new Model();
    }
}
