package com.mezzsy.copyzhihunews.adapter;

import android.support.v7.widget.RecyclerView;
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
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "MyAdapter";
    // 普通布局
    private final int TYPE_ITEM = 1;
    // 标题布局
    private final int TYPE_TITLE = 2;
    //头布局
    private final int TYPE_HEADER = 3;

    private List<StoriesBean> storiesBeans;
    private List<String> headerImages;
    private List<String> headerTitles;
//    private List<Date> dates;

    private Banner banner;
    private OnItemClickListenter listenter;

    public RecyclerViewAdapter() {
        this.storiesBeans = Model.getInstance().getStoriesBeans();
//        dates = Model.getInstance().getDate();
        headerImages = Model.getInstance().getHeaderImages();
        headerTitles = Model.getInstance().getHeaderTitles();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View
        switch (viewType) {
            case TYPE_HEADER:
                View headerView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycler_view_header, parent, false);
                banner = headerView.findViewById(R.id.banner);
                banner.setImageLoader(new GlideImageLoader())
                        .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
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
        if (storiesBeans.get(position - 1).isTitle) return TYPE_TITLE;
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return storiesBeans.size() + 1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            final StoriesBean storiesBean = storiesBeans.get(position - 1);
            ((ItemHolder) holder).tv.setText(storiesBean.title);
            ImageView img = ((ItemHolder) holder).img;
            Glide.with(img).load(storiesBeans.get(position - 1).images.get(0)).into(img);
            ((ItemHolder) holder).layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listenter.click(storiesBean.id);
                }
            });
        } else if (holder instanceof TitleHolder) {
            String date=storiesBeans.get(position-1).date;
            ((TitleHolder)holder).tvTitle.setText(DateHelper.getDate(date));
        } else {
            //TODO
        }
    }

    public void updateBanner() {
        banner.setImages(headerImages)
                .setBannerTitles(headerTitles)
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
//                        Log.d(TAG, "OnBannerClick: position = " + position);
                        int id = Model.getInstance().getTopStoriesBeans().get(position).id;
                        listenter.click(id);
                    }
                })
                .start();
//        banner.update(headerImages, headerTitles);
//        Log.d(TAG, "updateBanner: "+headerImages);
    }

    public void setListenter(OnItemClickListenter listenter) {
        this.listenter = listenter;
    }

    public interface OnItemClickListenter {
        void click(int id);
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
