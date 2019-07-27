package com.mezzsy.copyzhihunews.util;

public class HttpUtil {
//    private static Retrofit retrofit;
//    private static ZhihuNewsService service;
//
//    static {
//        retrofit = new Retrofit.Builder()
//                .baseUrl("http://news.at.zhihu.com/api/4/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        service = retrofit.create(ZhihuNewsService.class);
//    }
//
//    public static void requestNewsExtra(int id, Callback<ResponseBody> callback) {
//        Call<ResponseBody> call = service.getNewsExtra(id);
//        call.enqueue(callback);
//    }
//
//    public static void requestNewsContent(int id, Callback<ResponseBody> callback) {
//        Call<ResponseBody> call = service.getNewsContent(id);
//        call.enqueue(callback);
//    }
//
//    public static void loadMore(String date, Callback<ResponseBody> callback) {
//        Call<ResponseBody> call = service.getBefore(date);
//        call.enqueue(callback);
//    }
//
//    public static void swipeRefresh(Callback<ResponseBody> callback) {
//        Call<ResponseBody> call = service.getLatest();
//        call.enqueue(callback);
//    }
//
//    //OkHttp方式网络请求
//    public static void sendHttpRequest(String url, okhttp3.Callback callback) {
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//        client.newCall(request).enqueue(callback);
//    }
}
