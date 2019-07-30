package com.mezzsy.copyzhihunews.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.util.SparseArray;

import com.mezzsy.copyzhihunews.bean.StoriesBean;
import com.mezzsy.copyzhihunews.ui.ContentFragment;

import java.util.List;

/**
 * @author mezzsy
 * @date 2019-07-29
 */
public class ContentViewPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "ContentViewPagerAdapter";
    private List<StoriesBean> mStoriesBeans;
    private SparseArray<Fragment> mFragmentMap;

    public ContentViewPagerAdapter(FragmentManager fm, List<StoriesBean> storiesBeans) {
        super(fm);
        mStoriesBeans = storiesBeans;
        mFragmentMap = new SparseArray<>(mStoriesBeans.size());
    }

    public Fragment getItem(int position) {
        Log.d(TAG, "getItem: " + position);
        if (mFragmentMap.get(position) == null) {
            Fragment fragment = new ContentFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id", mStoriesBeans.get(position).id);
            fragment.setArguments(bundle);
            mFragmentMap.put(position, fragment);
        }
        return mFragmentMap.get(position);
    }

    @Override
    public int getCount() {
        return mStoriesBeans.size();
    }

}
