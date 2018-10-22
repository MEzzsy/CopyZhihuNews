package com.mezzsy.copyzhihunews.model;

import com.mezzsy.copyzhihunews.bean.Bean;
import com.mezzsy.copyzhihunews.bean.Date;
import com.mezzsy.copyzhihunews.bean.StoriesBean;
import com.mezzsy.copyzhihunews.bean.TopStoriesBean;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private List<Bean> beans;
    private List<StoriesBean> latestStoriesBeans;
    private List<StoriesBean> storiesBeans;
    private List<TopStoriesBean> topStoriesBeans;
    private List<Date> date;

    private Model(){
        storiesBeans = new ArrayList<>();
        latestStoriesBeans=new ArrayList<>();
        date=new ArrayList<>();
    }

    public List<StoriesBean> getLatestStoriesBeans() {
        return latestStoriesBeans;
    }

    public List<StoriesBean> getBeans() {
        return storiesBeans;
    }

    public List<Date> getDate() {
        return date;
    }

    public static Model getInstance(){
        return SingletonHolder.singleton;
    }

    private static class SingletonHolder{
        private static final Model singleton=new Model();
    }
}
