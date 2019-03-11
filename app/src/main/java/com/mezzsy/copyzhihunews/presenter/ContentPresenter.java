package com.mezzsy.copyzhihunews.presenter;

public class ContentPresenter {
//    private ContentView view;
//
//    public ContentPresenter(ContentView view) {
//        this.view = view;
//    }
//
//    public void requestNewsContent(int id) {
//        HttpUtil.requestNewsContent(id, new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                String responseData = null;
//                try {
//                    responseData = response.body().string();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Gson gson = new Gson();
//                ContentBean bean = gson.fromJson(responseData, ContentBean.class);
//                view.load(bean);
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//    }
//
//    public void requestNewsExtra(int id) {
//        HttpUtil.requestNewsExtra(id, new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                String responseData = null;
//                try {
//                    responseData = response.body().string();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Gson gson = new Gson();
//                ExtraBean bean = gson.fromJson(responseData, ExtraBean.class);
//                view.extra(bean);
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//    }
}
