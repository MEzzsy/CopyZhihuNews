package com.mezzsy.copyzhihunews.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 头条轮播图
 *
 * @author mezzsy
 * @date 2019-07-23
 */
public class HeadlineBanner extends RelativeLayout {
    private static final String TAG = "HeadlineBanner";

    private ViewPager mViewPager;
    private HeadlineBannerViewPagerAdapter mAdapter;
    private List<ImageView> mImageViews;
    private List mImages;
    private List<String> mTitles;

    private View mView;
    private TextView mTextView;

    /**
     * 底部指示器
     */
    private LinearLayout mIndicatorLayout;
    private List<ImageView> mIndicators;
    private ImageView mCurrentIndicatorImageView;
    private int mIndicatorDistance = 0;
    private int mIndicatorLeftMargin = -1;
    private GradientDrawable mIndicatorUnselected;
    private static final int INDICATOR_ID = 123;

    private ViewGroup.LayoutParams mLayoutParams;

    private MyHandler mHandler = new MyHandler(this);
    private static final int LOOP = 12;//循环展示图片
    private static final int DELAY = 5000;//循环展示图片

    private OnBannerListener mListener;

    public HeadlineBanner(Context context) {
        super(context);
        init();
    }

    public HeadlineBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HeadlineBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化数据和视图
     */
    private void init() {
        mLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        createViewPager();
        createGradientView();
        createIndicatorLayout();
        createTextView();
    }

    /**
     * 创建ViewPager
     */
    private void createViewPager() {
        mViewPager = new ViewPager(getContext());
        addView(mViewPager, mLayoutParams);
    }

    /**
     * 绘制渐变View，从上到下的透明度渐变，从0%到50%
     */
    private void createGradientView() {
        mView = new View(getContext());

        GradientDrawable drawable = new GradientDrawable();
        drawable.setUseLevel(false);
        drawable.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
        drawable.setColor(Color.BLACK);
        drawable.setColors(new int[]{Color.argb(0, 0, 0, 0)
                , Color.argb(128, 0, 0, 0)});

        mView.setBackground(drawable);
        addView(mView, mLayoutParams);
    }

    /**
     * 创建指示器小点容器
     */
    private void createIndicatorLayout() {
        mIndicatorLayout = new LinearLayout(getContext());

        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , 80);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        mIndicatorLayout.setId(INDICATOR_ID);
        mIndicatorLayout.setOrientation(LinearLayout.HORIZONTAL);
        addView(mIndicatorLayout, lp);
    }

    /**
     * 创建TextView
     */
    private void createTextView() {
        mTextView = new TextView(getContext());
        //TODO 这里TextSize用的Px，待改正
        mTextView.setTextSize(22);
        mTextView.setTextColor(Color.WHITE);
        mTextView.setPadding(40, 0, 40, 0);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ABOVE, INDICATOR_ID);
        addView(mTextView, lp);
    }

    /**
     * 设置图片
     *
     * @param images
     * @return
     */
    public HeadlineBanner setImages(List images) {
        if (mImages == null)
            mImages = new ArrayList();
        mImages.clear();
        mImages.addAll(images);
        return this;
    }

    /**
     * 设置标题
     *
     * @param titles
     * @return
     */
    public HeadlineBanner setTitles(List<String> titles) {
        if (mTitles == null)
            mTitles = new ArrayList<>();
        mTitles.clear();
        mTitles.addAll(titles);
        return this;
    }

    public HeadlineBanner setOnBannerListener(OnBannerListener listener) {
        mListener = listener;
        return this;
    }

    public HeadlineBanner bulid() {
        setImageList();
        setIndicators();
        setAdapter();
        return this;
    }

    /**
     * 开始轮播
     *
     * @return
     */
    public HeadlineBanner start() {
        mHandler.sendEmptyMessageDelayed(LOOP, DELAY);
        return this;
    }

    /**
     * 停止轮播
     */
    public void stop() {
        mHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 根据图片的数量创建对应的ImageView
     */
    private void setImageList() {
        if (mImageViews == null)
            mImageViews = new ArrayList<>();
        mImageViews.clear();
        for (int i = 0; i < mImages.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            int p = i;
            imageView.setOnClickListener(v -> {
                mListener.onBannerClick(p);
            });

            mImageViews.add(imageView);
        }
    }

    /**
     * 设置
     */
    private void setIndicators() {
        if (mIndicators == null)
            mIndicators = new ArrayList<>();

        if (mIndicatorUnselected == null) {
            mIndicatorUnselected = new GradientDrawable();
            mIndicatorUnselected.setColor(0xFFC1C1C1);
            mIndicatorUnselected.setShape(GradientDrawable.OVAL);
            mIndicatorUnselected.setSize(20, 20);
        }

        mIndicators.clear();
        mIndicatorLayout.removeAllViews();

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER_VERTICAL;
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        for (int i = 0; i < mImages.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageDrawable(mIndicatorUnselected);
            mIndicators.add(imageView);
            mIndicatorLayout.addView(imageView, lp);
        }

        if (mCurrentIndicatorImageView == null) {
            mCurrentIndicatorImageView = new ImageView(getContext());
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(Color.WHITE);
            drawable.setShape(GradientDrawable.OVAL);
            drawable.setSize(20, 20);
            mCurrentIndicatorImageView.setImageDrawable(drawable);

            mIndicatorLayout.post(() -> {
                LayoutParams rlp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                        , ViewGroup.LayoutParams.WRAP_CONTENT);
                rlp.topMargin = mIndicatorLayout.getTop()
                        + mIndicators.get(0).getTop();
                rlp.leftMargin = mIndicatorLayout.getLeft()
                        + mIndicators.get(0).getLeft();
                addView(mCurrentIndicatorImageView, rlp);
                mIndicatorDistance = lp.leftMargin + lp.rightMargin + mIndicators.get(0).getMeasuredWidth();
                mIndicatorLeftMargin = rlp.leftMargin;
            });

        }
    }

    private void setAdapter() {
        mAdapter = new HeadlineBannerViewPagerAdapter();
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            float prePositionOffset = 0f;

            @Override
            public void onPageScrolled(int i, float v, int i1) {
                moveCurrentIndicator(i, v);
                prePositionOffset = v;
            }

            @Override
            public void onPageSelected(int i) {
                mHandler.removeCallbacksAndMessages(null);
                mHandler.sendEmptyMessageDelayed(LOOP, DELAY);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    /**
     * 移动当前指示点
     *
     * @param i
     * @param positionOffset
     */
    private void moveCurrentIndicator(int i, float positionOffset) {
        positionOffset += i;
        LayoutParams lp = (LayoutParams) mCurrentIndicatorImageView.getLayoutParams();
        if (lp == null)
            return;
        if (mIndicatorLeftMargin == -1) {
            mIndicatorLeftMargin = lp.leftMargin;
        }
        lp.leftMargin = (int) (mIndicatorLeftMargin + mIndicatorDistance * positionOffset);
        mCurrentIndicatorImageView.setLayoutParams(lp);
    }

    public interface OnBannerListener {
        void onBannerClick(int position);
    }

    private static class MyHandler extends Handler {
        private WeakReference<HeadlineBanner> mReference;

        public MyHandler(HeadlineBanner banner) {
            mReference = new WeakReference<>(banner);
        }

        @Override
        public void handleMessage(Message msg) {
            HeadlineBanner banner = mReference.get();
            if (banner == null)
                return;

            if (msg.what == LOOP) {
                int currentPage = banner.mViewPager.getCurrentItem();
                if (currentPage == banner.mImages.size() - 1) {
                    banner.mViewPager.setCurrentItem(0);
                } else {
                    banner.mViewPager.setCurrentItem(currentPage + 1);
                }
            }
        }
    }

    private class HeadlineBannerViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImages.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        /**
         * 此方法会回调当前页面的position
         *
         * @param container
         * @param position
         * @param object
         */
        @Override
        public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            super.setPrimaryItem(container, position, object);
            mTextView.setText(mTitles.get(position));
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView;
            if (position >= mImageViews.size() || mImageViews.get(position) == null) {
                imageView = new ImageView(getContext());
                mImageViews.add(imageView);
            } else {
                imageView = mImageViews.get(position);
            }

            Glide.with(getContext())
                    .load(mImages.get(position))
                    .into(imageView);

            container.addView(imageView, mLayoutParams);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
