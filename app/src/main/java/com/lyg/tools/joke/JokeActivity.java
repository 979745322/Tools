package com.lyg.tools.joke;

import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.erning.common.absbase.AbsBaseActivity;
import com.erning.common.adapter.BaseFragmentPagerAdapter;
import com.lyg.tools.R;

public class JokeActivity extends AbsBaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_joke;
    }

    @Override
    protected void initControls() {
        AllFragment allFragment = new AllFragment();
        CollectFragment collectFragment = new CollectFragment();
        ViewPager viewPager = findViewById(R.id.jokeviewpager);
        viewPager.setAdapter(new BaseFragmentPagerAdapter(getSupportFragmentManager(), allFragment, collectFragment));

    }

}
