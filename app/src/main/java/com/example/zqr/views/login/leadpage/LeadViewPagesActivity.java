package com.example.zqr.views.login.leadpage;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import com.example.zqr.R;
import com.example.zqr.model.global.BaseActivity;
import com.example.zqr.MainActivity;


/**
 * Created by Android Studio.
 * User: 86182
 * Date: 2020-11-27
 * Time: 13:16
 */
public class LeadViewPagesActivity extends BaseActivity{
    private ViewPager viewPager;
    private int pageNum;
    /*创建两个变量 一个抬起  一个按下*/
    Float upX;
    Float downX;

    @Override
    public int LayoutId() {
        return R.layout.activity_login_leadviewpages;
    }

    FrameLayout frameLayout;

    @Override
    public void initView() {
        frameLayout=findViewById(R.id.lead_image_frameLayout);
        //是否第一次安装 做操作
        firstInstall();
    }



    /**
     * 第一次安装
     */

    private SharedPreferences SharedFlag;
    SharedPreferences.Editor editor;
    private void firstInstall() {
        SharedFlag = this.getSharedPreferences("config", Activity.MODE_PRIVATE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SharedFlag.getString("name", "第一次安装程序").equals("第一次安装程序")) {
                    editor = SharedFlag.edit();
                    editor.putString("name", "程序安装");
                    //提交才能保存
                    editor.commit();
                    //第一次安装就设置viewpager
                    frameLayout.setVisibility(View.GONE);
                    setViewPager();
                }else {
                    frameLayout.setVisibility(View.GONE);
                    setIntentClass(MainActivity.class);
                }
            }
        },3000);
    }


    /**
     * 设置viewpager
     */
    private void setViewPager(){
        viewPager = (ViewPager) findViewById(R.id.loginleadviewpager);
        viewPager.setVisibility(View.VISIBLE);
        /*数据源图片*/
        ImageView[] views = new ImageView[4];
        for (int i = 0; i < views.length; i++) {
            views[i] = new ImageView(this);
        }
        views[0].setBackgroundResource(R.drawable.timg1);
        views[1].setBackgroundResource(R.drawable.timg2);
        views[2].setBackgroundResource(R.drawable.timg3);
        views[3].setBackgroundResource(R.drawable.timg4);

        /*设置适配器*/
        viewPager.setAdapter(new LeadViewPagesAdapter(views));
        /*手势*/
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                /*获取点击事件*/
                switch (event.getAction()) {
                    /*常量按下*/
                    case MotionEvent.ACTION_DOWN:
                        downX = event.getX();
                        break;
                    /*常量弹起*/
                    case MotionEvent.ACTION_UP:
                        upX = event.getX();
                        if (pageNum== 3&&downX - upX > 200) {
                            setIntentClass(MainActivity.class);
                        }
                        break;
                }
                return false;
            }
        });

        /*viewpager的监听方法*/
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                /*把滑动的position赋值给page*/
                pageNum=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



}
