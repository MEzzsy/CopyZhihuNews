package com.mezzsy.copyzhihunews.model;

import com.mezzsy.copyzhihunews.bean.BaseBean;
import com.mezzsy.copyzhihunews.bean.StoriesBean;
import com.mezzsy.copyzhihunews.bean.TopStoriesBean;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private static final String TAG = "Model";
//    private final List<StoriesBean> latestStoriesBeans;
    private final List<StoriesBean> storiesBeans;
    private final List<TopStoriesBean> topStoriesBeans;
    private final List<String> headerImages;
    private final List<String> headerTitles;
//    private final List<Date> date;
    private List<BaseBean> beans;

    private Model(){
        storiesBeans = new ArrayList<>();
//        latestStoriesBeans = new ArrayList<>();
        topStoriesBeans = new ArrayList<>();
//        date = new ArrayList<>();
        headerImages = new ArrayList<>();
        headerTitles = new ArrayList<>();
    }

//    public List<StoriesBean> getLatestStoriesBeans() {
//        return latestStoriesBeans;
//    }

    public List<StoriesBean> getStoriesBeans() {
        return storiesBeans;
    }

//    public List<Date> getDate() {
//        return date;
//    }

    public List<TopStoriesBean> getTopStoriesBeans() {
        return topStoriesBeans;
    }

    public List<String> getHeaderImages() {
        return headerImages;
    }

    public List<String> getHeaderTitles() {
        return headerTitles;
    }

    public static Model getInstance(){
        return SingletonHolder.singleton;
    }

    private static class SingletonHolder{
        private static final Model singleton = new Model();
    }
}
