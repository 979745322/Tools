package com.lyg.tools.joke;

import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
        final CollectFragment collectFragment = new CollectFragment();
        final ViewPager viewPager = findViewById(R.id.jokeviewpager);
        viewPager.setAdapter(new BaseFragmentPagerAdapter(getSupportFragmentManager(), allFragment, collectFragment));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 1){
                    collectFragment.reset();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        Button btn_collect = findViewById(R.id.btn_collect);
        Button btn_all = findViewById(R.id.btn_all);

        // 点击全部按钮跳转全部页面
        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });

        // 点击收藏按钮跳转收藏页面
        btn_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });

    }



}
