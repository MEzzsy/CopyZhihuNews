package com.mezzsy.copyzhihunews.ui;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mezzsy.copyzhihunews.R;
import com.mezzsy.copyzhihunews.adapter.ContentViewPagerAdapter;
import com.mezzsy.copyzhihunews.bean.ExtraBean;
import com.mezzsy.copyzhihunews.bean.StoriesBean;
import com.mezzsy.copyzhihunews.model.Model;

import java.util.List;

public class NewsActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "NewsActivityzzsy";

    private ViewPager mViewPager;
    private ContentViewPagerAdapter mAdapter;

    private int mId;//新闻的id，用于获取具体内容
    private boolean isTopStory = false;//是否点击了头条日报
    private int mPostition;

    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private ImageView imgBack;
    private ImageView imgShare;
    private ImageView imgLike;
    private ImageView imgPl;
    private TextView tvPl;
    private ImageView imgDz;
    private TextView tvDz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        initData();
        initView();
    }

    private void initData() {
        mId = getIntent().getIntExtra("id", 0);
        isTopStory = getIntent().getBooleanExtra("isTopStory", false);
    }

    private void initView() {
        mAppBarLayout = findViewById(R.id.app_bar_layout);
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

        mViewPager = findViewById(R.id.content_view_pager);
        Log.d(TAG, "initView: " + isTopStory);
        List<StoriesBean> list;
        if (isTopStory)
            list = Model.getInstance().getTopStoriesBeans();
        else
            list = Model.getInstance().getStoriesBeansWithoutDate();
        mAdapter = new ContentViewPagerAdapter(getSupportFragmentManager(), list);
        mViewPager.setAdapter(mAdapter);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).id == mId) {
                mPostition = i;
                break;
            }
        }
        mViewPager.setCurrentItem(mPostition);
    }

    /**
     * 改变ToolBar上的点赞和评论个数，在Fragment中调用
     *
     * @param bean
     */
    public void setToolbarData(ExtraBean bean) {
        tvPl.setText(String.valueOf(bean.getComments()));
        tvDz.setText(String.valueOf(bean.getPopularity()));
    }

    public void setToolbarAlpha(float f) {
        float currentAlpha = mAppBarLayout.getAlpha();
        currentAlpha += f;
        if (currentAlpha <= 0f) {
            currentAlpha = 0f;
            mAppBarLayout.setVisibility(View.GONE);
        } else {
            if (currentAlpha >= 1f) {
                currentAlpha = 1f;
            }
            mAppBarLayout.setVisibility(View.VISIBLE);
        }
        Log.d(TAG, "setToolbarAlpha: " + currentAlpha);

        mAppBarLayout.setAlpha(currentAlpha);
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
//        String url = " http://daily.zhihu.com/story/" + id;
//        Intent intent = new Intent();
//        intent.setType("text/plain");
//        intent.setAction(Intent.ACTION_SEND);
//        intent.putExtra(Intent.EXTRA_TEXT, mTitle + " (分享自 @仿知乎日报 App) " + url);
////        intent.putExtra(Intent.EXTRA_HTML_TEXT, Html.fromHtml("<p>Hello,zzsy!!!</p>"));
////        intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(mBody));
//        startActivity(Intent.createChooser(intent, "分享"));
    }
}
