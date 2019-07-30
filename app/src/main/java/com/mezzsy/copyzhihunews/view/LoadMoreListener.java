package com.mezzsy.copyzhihunews.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mezzsy.copyzhihunews.bean.StoriesBean;
import com.mezzsy.copyzhihunews.model.Model;
import com.mezzsy.copyzhihunews.util.DateHelper;

/**
 * RecyclerView滑动监听
 * Created by yangle on 2017/10/12.
 */

public abstract class LoadMoreListener extends RecyclerView.OnScrollListener {
    private static final String TAG = "LoadMoreListener";

    // 用来标记是否正在向上滑动
    private boolean isSlidingUpward = false;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        // 当不滑动时
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            // 获取最后一个完全显示的itemPosition
            int lastItemPosition = manager.findLastCompletelyVisibleItemPosition();
            int itemCount = manager.getItemCount();

            // 判断是否滑动到了最后一个item，并且是向上滑动
            if (lastItemPosition == (itemCount - 1) && isSlidingUpward) {
                // 加载更多
                onLoadMore();
            }
        }


    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        // 大于0表示正在向上滑动，小于等于0表示停止或向下滑动
        isSlidingUpward = dy > 0;
        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int firstItemPosition = manager.findFirstVisibleItemPosition();
//        Log.d(TAG, "onScrollStateChanged: firstItemPosition = " + firstItemPosition);
        if (firstItemPosition == 0) setTitle("首页");
        else {
            StoriesBean storiesBean = Model.getInstance().getStoriesBeans().get(firstItemPosition - 1);
            if (storiesBean.isDateTitle) {
                setTitle(DateHelper.getDate(storiesBean.date));
            }
        }
    }

    /**
     * 加载更多回调
     */
    public abstract void onLoadMore();

    public abstract void setTitle(String title);
}
