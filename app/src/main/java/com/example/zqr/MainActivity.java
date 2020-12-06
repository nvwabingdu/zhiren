package com.example.zqr;


import android.content.Intent;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.zqr.model.global.BaseActivity;
import com.example.zqr.model.http.zqrretrofit.MyRetrofitActivity;
import com.example.zqr.views.TempFragment;
import com.example.zqr.views.ui.news.newsitem.NewsFragment;


public class MainActivity extends BaseActivity {
    private RadioGroup mTabRadioGroup;
    private FragmentManager fm;//fragment管理者
    private FragmentTransaction transaction;//fragmet管理事务
    private Fragment[] fragments = new Fragment[4];
    private int currentTAG = 0;

    @Override
    public int LayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mTabRadioGroup = findViewById(R.id.activity_main_tabs_rg);
        //设置RadioGroup的监听
        mTabRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);
        //用数组装入四个固定的Fragment
        fragments[0] = new NewsFragment();
        fragments[1] = new TempFragment();
        fragments[2] = new TempFragment();
        fragments[3] = new TempFragment();
        //初始化默认的fragment
        fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();
        transaction.add(R.id.activity_main_fragment_re, fragments[0]);
        transaction.commit();
    }

    /**
     * @param checkedId 替换当前的fragment
     */
    private void replaceFragment(int checkedId) {
        switch (checkedId) {
            case R.id.activity_main_tab1:
                showOrHide(0);
                break;
            case R.id.activity_main_tab2:
                showOrHide(1);
                break;
            case R.id.activity_main_tab3:
                startActivity(new Intent(MainActivity.this, MyRetrofitActivity.class));
                showOrHide(2);
                break;
            case R.id.activity_main_tab4:
                showOrHide(3);
                break;
        }
    }


    /**
     * RadioGroup的监听事件
     */
    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            replaceFragment(checkedId);
        }
    };


    /**
     * @param newTAG 显示与隐藏的方法
     */
    public void showOrHide(int newTAG) {
        //FragmentManager fm = getSupportFragmentManager();
        // FragmentTransaction transaction = fm.beginTransaction();
        // newsFrament = new NewsFragment();
        // transaction.add(R.id.activity_main_fragment_re, newsFrament);
        // transaction.commit();
        if (currentTAG != newTAG) {
            transaction = fm.beginTransaction();
            if (fragments[newTAG] == null) {
                fragments[newTAG] = new Fragment(newTAG);
                transaction.add(R.id.activity_main_fragment_re, fragments[newTAG]);
            } else {
                transaction.show(fragments[newTAG]);
            }
            transaction.hide(fragments[currentTAG]);
            transaction.commit();
            currentTAG = newTAG;
        }
    }
}