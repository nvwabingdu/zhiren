package com.example.zqr;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.zqr.model.beans.MainNewsBean;
import com.example.zqr.model.global.BaseActivity;
import com.example.zqr.model.global.Singleton;
import com.example.zqr.model.global.Tools;
import com.example.zqr.model.http.zqrretrofit.MyRetrofit;
import com.example.zqr.views.TempFragment;
import com.example.zqr.views.ui.eng.EngFragment;
import com.example.zqr.views.ui.news.WebViewActivity;
import com.example.zqr.views.ui.news.newsitem.NewsFragment;
import com.example.zqr.views.ui.news.newsitem.RecyClerViewAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends BaseActivity {
    private RadioGroup mTabRadioGroup;
    private Fragment[] fragments = new Fragment[4];
    private int nowIndex = -1;//当前的fragment标记
    FragmentTransaction transaction;
    FragmentManager fm;


    @Override
    public int LayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mTabRadioGroup = findViewById(R.id.activity_main_tabs_rg);
        //设置RadioGroup的监听
        mTabRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);

//        //初始化默认的fragment
//        fm = getSupportFragmentManager();//fragment管理者
//        transaction = fm.beginTransaction();//fragmet管理事务
//        fragments[0]=new NewsFragment();
//        transaction.add(R.id.activity_main_fragment_re, NewFragmentMehtod(0));
//        transaction.commit();
//        nowIndex=0;
        //特殊show方法
        method();
    }


    //==========================
    private int[] fragmentLayoutID= {R.id.main_view0,R.id.main_view1,R.id.main_view2,R.id.main_view3};
    private View[] fragmentLayoutVIEW=new View[4];
    private void method(){
        fragmentLayoutVIEW[0]=findViewById(fragmentLayoutID[0]);
        fragmentLayoutVIEW[1]=findViewById(fragmentLayoutID[1]);
        fragmentLayoutVIEW[2]=findViewById(fragmentLayoutID[2]);
        fragmentLayoutVIEW[3]=findViewById(fragmentLayoutID[3]);
        fragmentLayoutVIEW[0].setVisibility(View.VISIBLE);
        //初始化默认的fragment
        fm = getSupportFragmentManager();//fragment管理者
        transaction = fm.beginTransaction();//fragmet管理事务
        fragments[0]=new NewsFragment();
        transaction.add(fragmentLayoutID[0], NewFragmentMehtod(0));
        transaction.commit();
    }

    private void show(int nowNum){

        for(int i=0;i<fragmentLayoutID.length;i++){
            fragmentLayoutVIEW[i].setVisibility(View.GONE);
        }

        switch (nowNum){
            case 0:
                if(fragments[0]==null){
                    method3(0,fragmentLayoutID[0]);
                }
                fragmentLayoutVIEW[0].setVisibility(View.VISIBLE);
                break;
            case 1:
                if(fragments[1]==null){
                    method3(1,fragmentLayoutID[1]);
                }
                fragmentLayoutVIEW[1].setVisibility(View.VISIBLE);
                break;
            case 2:
                if(fragments[2]==null){
                    method3(2,fragmentLayoutID[2]);
                }
                fragmentLayoutVIEW[2].setVisibility(View.VISIBLE);
                break;
            case 3:
                if(fragments[3]==null){
                    method3(3,fragmentLayoutID[3]);
                }
                fragmentLayoutVIEW[3].setVisibility(View.VISIBLE);
                break;
        }
    }

    private void method3(int tag,int id){
        fm = getSupportFragmentManager();//fragment管理者
        transaction = fm.beginTransaction();//fragmet管理事务
        fragments[tag]=NewFragmentMehtod(tag);
        transaction.add(id, NewFragmentMehtod(tag));
        transaction.commit();
    }



    //=========================



    /**
     * @param checkedId 替换当前的fragment
     */
    private void replaceFragment(int checkedId) {
        switch (checkedId) {
            case R.id.activity_main_tab1:
                show(0);
                break;
            case R.id.activity_main_tab2:
                show(1);
                break;
            case R.id.activity_main_tab3:
                show(2);
                break;
            case R.id.activity_main_tab4:
                show(3);
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
     * @param newIndex 显示与隐藏的方法
     */
    public void showOrHide(int newIndex) {
        if (newIndex != nowIndex) {//新的标记不等于当前标记 相当于要切换
        transaction = fm.beginTransaction();//fragmet管理事务
            if (fragments[newIndex] == null) {//新的fragment 为空
                fragments[newIndex] = NewFragmentMehtod(newIndex);//选择new一个新的
                transaction.add(R.id.activity_main_fragment_re, fragments[newIndex]);//事务添加布局
            } else {
                transaction.show(fragments[newIndex]);
            }
            transaction.hide(fragments[nowIndex]);
            transaction.commit();
            nowIndex = newIndex;//把新的标记设置为当前标记
        }
    }

    /**
     * @param newIndex new一个新的fragment
     * @return
     */
    public Fragment NewFragmentMehtod(int newIndex){
        switch (newIndex) {
            case 0:
                return new NewsFragment();
            case 1:
                return new TempFragment();
            case 2:
                return new EngFragment();
            case 3:
                return new TempFragment();
        }
        return null;
    }


    /**
     * Fragment替换(核心为隐藏当前的,显示现在的,用过的将不会destrory与create)
     * @param target
     * @param toFragment
     */
    Fragment currentFragment;
    public void smartFragmentReplace(int target, Fragment toFragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        /*如有当前在使用的->隐藏当前的*/
        if (currentFragment != null) { transaction.hide(currentFragment); }
        String toClassName = toFragment.getClass().getSimpleName();
        /*toFragment之前添加使用过->显示出来*/
        if (fm.findFragmentByTag(toClassName) != null) { transaction.show(toFragment); } else {
            /*toFragment还没添加使用过->添加上去*/
            transaction.add(target, toFragment, toClassName); }
        transaction.commit();
        /*toFragment更新为当前的*/
        currentFragment = toFragment;
    }

}