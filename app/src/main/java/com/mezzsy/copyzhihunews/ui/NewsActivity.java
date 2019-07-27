package com.mezzsy.copyzhihunews.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class NewsActivity extends AppCompatActivity implements
        ContentView, View.OnClickListener {
    private static final String TAG = "NewsActivityzzsy";
    private WebView mWebView;
    private ContentPresenter mPresenter;

    private int id;//新闻的id，用于获取具体内容
    private String mTitle;

    private Toolbar mToolbar;
    private ImageView imgBack;
    private ImageView imgShare;
    private ImageView imgLike;
    private ImageView imgPl;
    private TextView tvPl;
    private ImageView imgDz;
    private TextView tvDz;

    private RelativeLayout mBackground;
    private TextView tvTitle;
    private TextView tvSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        initData();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.requestNewsContent(id);
        mPresenter.requestNewsExtra(id);
    }

    private void initData() {
        id = getIntent().getIntExtra("id", 0);
        mPresenter = new ContentPresenter(this);
    }

    private void initView() {
        mWebView = findViewById(R.id.web_view);

        mBackground = findViewById(R.id.header_layout);
        tvTitle = findViewById(R.id.tv_title);
        tvSource = findViewById(R.id.tv_source);

        mToolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        imgBack = findViewById(R.id.img_back);
        imgShare = findViewById(R.id.img_share);
        imgLike = findViewById(R.id.img_like);
        imgPl = findViewById(R.id.img_pl);
        tvPl = findViewById(R.id.tv_pl);
        imgDz = findViewById(R.id.img_dz);
        tvDz = findViewById(R.id.tv_dz);
        imgBack.setOnClickListener(this);
        imgShare.setOnClickListener(this);
        imgLike.setOnClickListener(this);
        imgPl.setOnClickListener(this);
        tvPl.setOnClickListener(this);
        imgDz.setOnClickListener(this);
        tvDz.setOnClickListener(this);
    }

    @Override
    public void load(ContentBean bean) {
        WebViewConfigs.initWebView(mWebView);
//        String html = Util.modifyHTML(bean.getBody(), bean.getCss().get(0));
        String htmlData = HtmlUtil.createHtmlData(bean.getBody(),
                bean.getCss(), bean.getJs());
//        Log.d(TAG, html);
        mWebView.loadData(htmlData, "text/html; charset=UTF-8", null);

        Glide.with(this).load(bean.getImage()).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                mBackground.setBackground(resource);
            }
        });
        tvSource.setText(bean.getImage_source());
        tvTitle.setText(bean.getTitle());
        mTitle = bean.getTitle();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void extra(ExtraBean bean) {
        tvPl.setText(bean.getComments() + "");
        tvDz.setText(bean.getPopularity() + "");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_share:
                share();
                break;
            case R.id.img_like:
                Toast.makeText(this, "收藏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_pl:
            case R.id.tv_pl:
                Toast.makeText(this, "评论", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_dz:
            case R.id.tv_dz:
                Toast.makeText(this, "点赞", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void share() {
        String url = " http://daily.zhihu.com/story/" + id;
        Intent intent = new Intent();
        intent.setType("text/plain");
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, mTitle + " (分享自 @仿知乎日报 App) " + url);
//        intent.putExtra(Intent.EXTRA_HTML_TEXT, Html.fromHtml("<p>Hello,zzsy!!!</p>"));
//        intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(mBody));
        startActivity(Intent.createChooser(intent, "分享"));
    }
}
