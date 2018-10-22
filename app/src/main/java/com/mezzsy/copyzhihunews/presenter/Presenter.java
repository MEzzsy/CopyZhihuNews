package com.mezzsy.copyzhihunews.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.mezzsy.copyzhihunews.bean.Bean;
import com.mezzsy.copyzhihunews.bean.Date;
import com.mezzsy.copyzhihunews.bean.StoriesBean;
import com.mezzsy.copyzhihunews.bean.TopStoriesBean;
import com.mezzsy.copyzhihunews.model.Model;
import com.mezzsy.copyzhihunews.util.HttpUtil;
import com.mezzsy.copyzhihunews.util.Util;
import com.mezzsy.copyzhihunews.view.IView;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Presenter {
    //    public static final int TYPE_LOADMORE = 0;
//    public static final int TYPE_SWIPE_REFRESH = 1;
    private static final String TAG = "Presenter";
    private IView view;
    private Model model;
    private List<Date> dates;
    private List<StoriesBean> latestStoriesBeans;
    private List<StoriesBean> storiesBeans;
    private List<TopStoriesBean> topStoriesBeans;
    private String currentDate;

    public Presenter(IView view) {
        this.view = view;
        model = Model.getInstance();
        latestStoriesBeans = model.getLatestStoriesBeans();
        storiesBeans = model.getBeans();
        dates = model.getDate();
    }

    public void loadMore(){
        currentDate=dates.get(dates.size()-1).getOriginalDate();
        Log.d(TAG, "loadMore: currentDate = "+currentDate);
        HttpUtil.loadMore(currentDate,new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String responseData = null;
                try {
                    responseData = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                Bean bean = gson.fromJson(responseData, Bean.class);
                storiesBeans.add(null);
                storiesBeans.addAll(bean.getStories());
                dates.add(new Date(bean.getDate()));
                view.load(true);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                view.load(false);
            }
        });
    }

    public void swipeRefresh() {
        HttpUtil.swipeRefresh(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                String responseData = null;
                try {
                    responseData = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                Bean bean = gson.fromJson(responseData, Bean.class);
                if (latestStoriesBeans.size() - 1 != bean.getStories().size()) {
                    if (latestStoriesBeans.size() == 0) {
                        latestStoriesBeans.add(null);
                        latestStoriesBeans.addAll(bean.getStories());
                        currentDate=bean.getDate();
                        dates.add(new Date(bean.getDate()));
                        storiesBeans.addAll(0,latestStoriesBeans);
                    } else {
                        int p=1;
                        for (StoriesBean storiesBean:bean.getStories()){
                            if (!contain(storiesBean.getId())){
                                latestStoriesBeans.add(p,storiesBean);
                                storiesBeans.add(p,storiesBean);
                                p++;
                            }
                        }
                    }
                    topStoriesBeans = bean.getTop_stories();
                }
                view.load(true);
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                view.load(false);
            }
        });
//        HttpUtil.sendHttpRequest("http://news-at.zhihu.com/api/4/news/latest", new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//                view.load(type, false);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String responseData = response.body().string();
//                Gson gson = new Gson();
//                Bean bean = gson.fromJson(responseData, Bean.class);
//                storiesBeans.add(null);
//                storiesBeans.addAll(bean.getStories());
//                date.add(new Date(bean.getDate()));
//                topStoriesBeans = bean.getTop_stories();
////                Log.d(TAG, "onResponse: " + bean.toString());
//                view.load(type, true);
//            }
//        });
    }

    private boolean contain(int id){
        for (StoriesBean bean:latestStoriesBeans){
            if (bean==null)continue;
            if (id==bean.getId())return true;
        }
        return false;
    }
}
