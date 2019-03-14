package com.mezzsy.copyzhihunews.bean;

/**
 * 输入新闻的ID，获取对应新闻的额外信息，如评论数量，所获的『赞』的数量。
 */
public class ExtraBean {

    /**
     * long_comments : 0
     * popularity : 242
     * short_comments : 11
     * comments : 11
     */

    private int long_comments;
    private int popularity;
    private int short_comments;
    private int comments;

    public int getLong_comments() {
        return long_comments;
    }

    public void setLong_comments(int long_comments) {
        this.long_comments = long_comments;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getShort_comments() {
        return short_comments;
    }

    public void setShort_comments(int short_comments) {
        this.short_comments = short_comments;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }
}
