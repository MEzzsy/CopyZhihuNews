package com.mezzsy.copyzhihunews.bean;

import com.google.gson.annotations.SerializedName;

public class TopStoriesBean {
    /*
    date : 日期
    stories : 当日新闻
        title : 新闻标题
        images : 图像地址（官方 API 使用数组形式。目前暂未有使用多张图片的情形出现，曾见无 images 属性的情况，请在使用中注意 ）
        ga_prefix : 供 Google Analytics 使用
        type : 作用未知
        id : url 与 share_url 中最后的数字（应为内容的 id）
        multipic : 消息是否包含多张图片（仅出现在包含多图的新闻中）
    top_stories : 界面顶部 ViewPager 滚动显示的显示内容（子项格式同上）
     */

    public String image;
    public int id;
    public String title;
    @SerializedName("ga_prefix")
    public int serialNumber;
}
