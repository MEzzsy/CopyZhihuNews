package com.mezzsy.copyzhihunews.ui;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mezzsy.copyzhihunews.R;
import com.mezzsy.copyzhihunews.bean.ContentBean;
import com.mezzsy.copyzhihunews.bean.ExtraBean;
import com.mezzsy.copyzhihunews.presenter.ContentPresenter;
import com.mezzsy.copyzhihunews.util.HtmlUtil;
import com.mezzsy.copyzhihunews.util.WebViewConfigs;
import com.mezzsy.copyzhihunews.view.ContentView;

/**
 * 具体内容页面
 *
 * @author mezzsy
 * @date 2019-07-28
 */
public class ContentFragment extends Fragment implements ContentView {
    private static final String TAG = "ContentFragment";
    private View mView;//整个View
    private WebView mWebView;

    private int mId;//新闻的id，用于获取具体内容

    private RelativeLayout mBackground;
    private TextView tvTitle;
    private TextView tvSource;

    private ContentPresenter mPresenter;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            loadData();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_content, container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initView();
    }

    private void initData() {
        mPresenter = new ContentPresenter(this);

        if (getArguments() != null) {
            mId = getArguments().getInt("id");
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        mWebView = mView.findViewById(R.id.web_view);

        mBackground = mView.findViewById(R.id.header_layout);
        tvTitle = mView.findViewById(R.id.tv_title);
        tvSource = mView.findViewById(R.id.tv_source);

//        mWebView.setOnTouchListener(new View.OnTouchListener() {
//            float mStart;
//            final float DISTANCE = 30f;
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                final float y = event.getY();
//
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        mStart = y;
////                        Log.d(TAG, "onTouch: mStart=" + mStart);
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        float delta = (y - mStart) / DISTANCE;
//                        setToolbarAlpha(delta);
////                        Log.d(TAG, "onTouch: delta=" + delta);
////                        Log.d(TAG, "onTouch: y=" + y);
////                        Log.d(TAG, "onTouch: mStart=" + mStart);
//                        mStart = y;
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        break;
//                }
//                return false;
//            }
//
//        });
    }

    public void setToolbarAlpha(float f) {
        if (f >= 1f)
            f = 1f;
        if (f <= -1f)
            f = -1f;

        Log.d(TAG, "setToolbarAlpha2: " + f);
        if (getActivity() != null)
            ((NewsActivity) getActivity()).setToolbarAlpha(f);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getUserVisibleHint()) {
            loadData();
        }
    }

    /**
     * 加载数据
     */
    private void loadData() {
        if (mView == null)
            return;
        mPresenter.requestNewsContent(mId);
        mPresenter.requestNewsExtra(mId);
    }

    /**
     * 加载数据后的回调
     *
     * @param bean
     */
    @Override
    public void load(ContentBean bean) {
        WebViewConfigs.initWebView(mWebView);
        String htmlData = HtmlUtil.createHtmlData(bean.getBody(),
                bean.getCss(), bean.getJs());
        mWebView.loadData(htmlData, "text/html; charset=UTF-8", null);

        Glide.with(this).load(bean.getImage()).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                mBackground.setBackground(resource);
            }
        });
        tvSource.setText(bean.getImage_source());
        tvTitle.setText(bean.getTitle());
    }

    @Override
    public void extra(ExtraBean bean) {
        NewsActivity activity = (NewsActivity) getActivity();
        if (activity != null) {
            activity.setToolbarData(bean);
        }
    }
}
