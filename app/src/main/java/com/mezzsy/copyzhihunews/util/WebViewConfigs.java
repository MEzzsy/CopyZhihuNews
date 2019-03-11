package com.mezzsy.copyzhihunews.util;

import android.annotation.SuppressLint;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebViewConfigs {
    @SuppressLint("SetJavaScriptEnabled")
    public static void initWebView(WebView webView){
        // 设置WebView初始化尺寸，参数为百分比
//        webView.setInitialScale(100);
        // 获取WebSettings对象
        WebSettings webSettings=webView.getSettings();
        //设置WebView可触摸放大缩小
//        webSettings.setSupportZoom(false);
        //WebView双击变大，再双击后变小，当手动放大后，双击可以恢复到原始大小
//        webSettings.setBuiltInZoomControls(false);
        // 设置WebView支持运行普通的Javascript
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(false); //将图片调整到适合webview的大小
//        webSettings.setLoadWithOverviewMode(false); // 缩放至屏幕的大小
//        webSettings.setDisplayZoomControls(false);//隐藏原生的缩放控件
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        webSettings.setDefaultTextEncodingName("UTF -8");
    }
}
