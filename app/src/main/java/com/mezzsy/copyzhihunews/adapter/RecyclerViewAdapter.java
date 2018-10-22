package com.mezzsy.copyzhihunews.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mezzsy.copyzhihunews.R;
import com.mezzsy.copyzhihunews.bean.Date;
import com.mezzsy.copyzhihunews.bean.StoriesBean;
import com.mezzsy.copyzhihunews.model.Model;

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
    private List<Date> dates;

    public RecyclerViewAdapter() {
        this.storiesBeans = Model.getInstance().getBeans();
        dates = Model.getInstance().getDate();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return TYPE_HEADER;
        if (storiesBeans.get(position - 1) == null) return TYPE_TITLE;
        return TYPE_ITEM;
    }

//    private boolean isTitlePosition(int position) {
//        position--;
//        for (int i = 0; i < beans.size(); i++) {
//            StoriesBean bean = beans.get(i);
//            if (bean == null) {
//                if (position == i) return true;
//            }
//        }
//        return false;
//    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View
        switch (viewType) {
            case TYPE_HEADER:
                View headerView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycler_view_header, parent, false);
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            ((ItemHolder) holder).tv.setText(storiesBeans.get(position - 1).getTitle());
            ImageView img = ((ItemHolder) holder).img;
            Glide.with(img).load(storiesBeans.get(position - 1).getImages().get(0)).into(img);
        } else if (holder instanceof TitleHolder) {
            try {
                StoriesBean storiesBean=storiesBeans.get(position);
                String d =storiesBean.getGa_prefix();

//                ((TitleHolder) holder).tvTitle.setText(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //TODO
        }
    }

    @Override
    public int getItemCount() {
        return storiesBeans.size() + 1;
    }

    private class HeaderHolder extends RecyclerView.ViewHolder {
        HeaderHolder(View itemView) {
            super(itemView);
        }
    }

    private class ItemHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView img;

        ItemHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.text);
            img = itemView.findViewById(R.id.img);
        }
    }

    private class TitleHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        TitleHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.title);
        }
    }

}
