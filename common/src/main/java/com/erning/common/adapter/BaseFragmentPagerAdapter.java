package com.erning.common.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by 二宁 on 2017/11/23.
 */
public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] fragments;

    public BaseFragmentPagerAdapter(FragmentManager fm, Fragment... fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }
}
