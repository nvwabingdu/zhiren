package com.example.zqr.views.ui.news.newsitem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.zqr.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: 86182
 * Date: 2020-11-29
 * Time: 20:11
 */
public class NewsFragment extends Fragment {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private View view;
    private ArrayList<Fragment> fragm_list=new ArrayList<>();
    List<String> titles = new ArrayList<>();
    private String[] mTitleArray = new String[]{"top", "shehui", "guonei", "guoji", "yule", "tiyu", "junshi", "keji", "caijing","shishang"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_main_news, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mTabLayout = view.findViewById(R.id.tabs);
        //初始化viewpager
        initViewPager();
    }


    /**
     * 初始化viewpager
     */
    private void initViewPager() {
        titles.add("头条");
        titles.add("社会");
        titles.add("国内");
        titles.add("国际");
        titles.add("娱乐");
        titles.add("体育");
        titles.add("军事");
        titles.add("科技");
        titles.add("财经");
        titles.add("时尚");
        for (int i = 0; i < titles.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(i)));
        }

        FragmentAdapter mFragmentAdapteradapter =
                new FragmentAdapter(getActivity().getSupportFragmentManager(), getFragm_list(), titles);
        //给ViewPager设置适配器
        mViewPager.setAdapter(mFragmentAdapteradapter);
        //将TabLayout和ViewPager关联起来。
        mTabLayout.setupWithViewPager(mViewPager);
        //给TabLayout设置适配器
        mTabLayout.setTabsFromPagerAdapter(mFragmentAdapteradapter);
    }

    /**
     * @return 获取带参数的fragment集合
     */
    public ArrayList<Fragment> getFragm_list(){
        for(int i=0;i<titles.size();i++){
            ListFragment listFragment=new ListFragment();
            Bundle bundle=new Bundle();
            bundle.putString("mTitle",mTitleArray[i]);
            listFragment.setArguments(bundle);
            fragm_list.add(listFragment);
        }
        return fragm_list;
    }
}
