package com.mezzsy.copyzhihunews.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 非Top新闻的数据类
 *
 * RecyclerViewAdapter中的TYPE_TITLE和TYPE_ITEM类型，TYPE_TITLE为日期标题，TYPE_ITEM为新闻。
 * 在List中的存放顺序：最新的StoriesBean的index越小，并且以isTitle进行区分是否是日期标题。
 */
public class StoriesBean {
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

    public String date;
    public boolean isTitle;//是否是标题日期，如果是，除date属性外其余为默认值。
    public int id;
    public String title;
    public List<String> images;
    @SerializedName("ga_prefix")
    public int serialNumber;
}
