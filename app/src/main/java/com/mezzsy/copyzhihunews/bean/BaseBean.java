package com.mezzsy.copyzhihunews.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseBean {
    /**
     * date : 20181019
     * stories : [{"images":["https://pic3.zhimg.com/v2-1ede46bf79de14bb002243221e6c7156.jpg"],"type":0,"id":9698851,"ga_prefix":"101913","title":"中老年表情包，我跟父母聊天的快乐源泉"},{"images":["https://pic1.zhimg.com/v2-23d7d60d7409e7a97815337b88f7e25c.jpg"],"type":0,"id":9698980,"ga_prefix":"101912","title":"大误 · 满城尽带黄金眼"},{"images":["https://pic2.zhimg.com/v2-989d2cecfc46122d3a4ecfc6d45ea779.jpg"],"type":0,"id":9698741,"ga_prefix":"101910","title":"东西坏了敲一敲就好了，这是只属于人类 の 奥义"},{"images":["https://pic3.zhimg.com/v2-c9633c3f12b5c29d9c9a48603f80d782.jpg"],"type":0,"id":9698791,"ga_prefix":"101909","title":"如果把地球上铁和金的产量互换，铁会比金贵么？"},{"images":["https://pic2.zhimg.com/v2-df688e58439db12550ad665eabf94f3d.jpg"],"type":0,"id":9698828,"ga_prefix":"101908","title":"为什么科学上会说一米绳子、一年时间，却不用「个」这个单位？"},{"images":["https://pic2.zhimg.com/v2-e16f46b02444754479a021c69b007bd9.jpg"],"type":0,"id":9698799,"ga_prefix":"101907","title":"全非洲反盗猎力度最大的国家，出现了超大规模的非洲象盗猎"},{"images":["https://pic1.zhimg.com/v2-681b1f706eda0450cdcd9230621062e8.jpg"],"type":0,"id":9698456,"ga_prefix":"101907","title":"为什么很多制造业公司向东南亚搬迁，而不是中西部？"},{"images":["https://pic4.zhimg.com/v2-ff05f39271b9baf30a97e0bb757df76b.jpg"],"type":0,"id":9698872,"ga_prefix":"101906","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"https://pic4.zhimg.com/v2-e093102accf700073af6c3452293cd27.jpg","type":0,"id":9698862,"ga_prefix":"101719","title":"「56 个星座，56 支花」\u2026\u2026等等，不是 56 个民族吗？"},{"image":"https://pic4.zhimg.com/v2-76f03b3ab26c3d76be022a5281a13483.jpg","type":0,"id":9698744,"ga_prefix":"101810","title":"花海沦陷在抖音网红的臀下，想要提前避免，可能真没什么辙"},{"image":"https://pic3.zhimg.com/v2-5babbbaa37cccaf467b996e9bc1aadda.jpg","type":0,"id":9698809,"ga_prefix":"101807","title":"大学校园沦为艾滋病重灾区？一直如此，形势也越来越严峻"},{"image":"https://pic2.zhimg.com/v2-2febc063b701eb36f0276250185a30fd.jpg","type":0,"id":9698783,"ga_prefix":"101716","title":"造假十多年，撤稿 31 篇，哈佛大牛就这样骗了全世界"},{"image":"https://pic4.zhimg.com/v2-ed3cbc41485dec20cf60c6fed3721a93.jpg","type":0,"id":9698782,"ga_prefix":"101707","title":"浴霸三摄、秒变无线充电宝\u2026\u2026华为 Mate20 确实有点「香」"}]
     */

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
    public List<StoriesBean> stories;
    @SerializedName("top_stories")
    public List<TopStoriesBean> topStories;

}
