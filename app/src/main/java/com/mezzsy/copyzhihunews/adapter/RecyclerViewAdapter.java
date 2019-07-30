package com.mezzsy.copyzhihunews.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mezzsy.copyzhihunews.R;
import com.mezzsy.copyzhihunews.bean.StoriesBean;
import com.mezzsy.copyzhihunews.model.Model;
import com.mezzsy.copyzhihunews.util.DateHelper;
import com.mezzsy.copyzhihunews.view.HeadlineBanner;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "MyAdapter";
    // 普通布局
    private final int TYPE_ITEM = 1;
    // 标题布局
    private final int TYPE_TITLE = 2;
    //头布局
    private final int TYPE_HEADER = 3;

    private List<StoriesBean> mStoriesBeans;
    private List<String> mHeaderImages;
    private List<String> mHeaderTitles;
//    private List<Date> dates;

    private HeadlineBanner mBanner;
    private OnItemClickListenter mListenter;

    public RecyclerViewAdapter() {
        this.mStoriesBeans = Model.getInstance().getStoriesBeans();
//        dates = Model.getInstance().getDate();
        mHeaderImages = Model.getInstance().getHeaderImages();
        mHeaderTitles = Model.getInstance().getHeaderTitles();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View
        switch (viewType) {
            case TYPE_HEADER:
                View headerView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycler_view_header, parent, false);
                mBanner = headerView.findViewById(R.id.banner);
                return new HeaderHolder(headerView);
            case TYPE_TITLE:
                View titleView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycler_view_title, parent, false);
                return new TitleHolder(titleView);
            case TYPE_ITEM:
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycler_view_item, parent, false);
                return new ItemHolder(itemView);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return TYPE_HEADER;
        if (mStoriesBeans.get(position - 1).isDateTitle) return TYPE_TITLE;
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mStoriesBeans.size() + 1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            final StoriesBean storiesBean = mStoriesBeans.get(position - 1);
            ((ItemHolder) holder).tv.setText(storiesBean.title);
            ImageView img = ((ItemHolder) holder).img;
            Glide.with(img).load(mStoriesBeans.get(position - 1).images.get(0)).into(img);
            ((ItemHolder) holder).layout.setOnClickListener(v
                    -> mListenter.click(storiesBean.id, false, position));
        } else if (holder instanceof TitleHolder) {
            String date = mStoriesBeans.get(position - 1).date;
            ((TitleHolder) holder).tvTitle.setText(DateHelper.getDate(date));
        }
    }

    public void updateBanner() {
        Log.d(TAG, "updateBanner: " + mHeaderImages.size());
        mBanner.setImages(mHeaderImages)
                .setTitles(mHeaderTitles)
                .setOnBannerListener(position -> {
//                        Log.d(TAG, "OnBannerClick: position = " + position);
                    int id = Model.getInstance().getTopStoriesBeans().get(position).id;
                    mListenter.click(id, true, position);
                })
                .bulid()
                .start();
//        mBanner.update(mHeaderImages, mHeaderTitles);
//        Log.d(TAG, "updateBanner: "+mHeaderImages);
    }

    public void setListenter(OnItemClickListenter mListenter) {
        this.mListenter = mListenter;
    }

    public interface OnItemClickListenter {
        void click(int id, boolean isTopStory, int postition);
    }

    private class HeaderHolder extends RecyclerView.ViewHolder {
        HeaderHolder(View itemView) {
            super(itemView);
        }
    }

    private class TitleHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        TitleHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.title);
        }
    }

    private class ItemHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView img;
        LinearLayout layout;

        ItemHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.text);
            img = itemView.findViewById(R.id.img);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
