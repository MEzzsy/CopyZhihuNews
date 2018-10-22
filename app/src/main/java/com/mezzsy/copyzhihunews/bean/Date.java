package com.mezzsy.copyzhihunews.bean;

import com.mezzsy.copyzhihunews.util.Util;

public class Date {
    private String date;

    public Date(String date) {
        this.date = date;
    }

    public String getDate() throws Exception {
        if (Util.isToday(date)) return "今日热闻";
        return Util.getDate(date);
    }

    public String getOriginalDate(){
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static void main(String[] args) throws Exception {
        Date date=new Date("20181020");
        System.out.print(date.getDate());
    }
}
