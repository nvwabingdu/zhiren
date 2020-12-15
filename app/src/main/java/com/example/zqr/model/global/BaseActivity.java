package com.example.zqr.model.global;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.zqr.R;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * User: 86182
 * Date: 2020-11-27
 * Time: 12:27
 */
public abstract class BaseActivity extends AppCompatActivity {
    /**
     * @param savedInstanceState 创建
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*去掉标题栏*/
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

        /*用于保存值*/
        if (savedInstanceState != null) {
            Singleton.getInstance().wq = savedInstanceState.getString("wq");
        }
        /*加载布局*/
        setContentView(LayoutId());
        /*布局操作*/
        initView();
        /*是否打印log  并没有什么用*/
        //isPrintLog();
        //将activity加入
        ActivityManage.getInstance().addActivity(this);
    }

    /**
     * @return 加载布局
     */
    public abstract int LayoutId();

    /**
     * 初始化控件
     */
    public abstract void initView();


    /**
     * 开始
     */
    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * 重新开始
     */
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    /**
     * @param savedInstanceState 恢复数据
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * 页面获取焦点 可见
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 页面失去焦点  不可见
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * @param hasFocus 获取activity的焦点    该方法会在上面方面之前调用一次
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        /*沉浸式标题栏*/
//        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }
    }

    /**
     * @param outState           保存数据
     * @param outPersistentState
     */
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString("wq", Singleton.getInstance().wq);
    }

    /**
     * 停止 不会销毁 存在于栈中
     */
    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * 销毁 做清理工作
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManage.getInstance().removeActivity(this);
    }

    /**
     * 按下back键？
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    /**
     * @param cla activity的跳转
     */
    public void setIntentClass(Class<?> cla) {
        Intent intent = new Intent();
        intent.setClass(this, cla);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }


    /**
     * @param cla activity的跳转带参数
     * @param obj
     */
    public void setIntentClass(Class<?> cla, Object obj) {
        Intent intent = new Intent();
        intent.setClass(this, cla);
        intent.putExtra("INTENTTAG", (Serializable) obj);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }


    /**
     * 收起键盘
     */
    public void closeInputMethod() {
        /*用于判断虚拟软键盘是否是显示的*/
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    private boolean mIsExit;
    @Override
    /**
     * 双击返回键退出
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsExit) {
                ActivityManage.getInstance().finishAllActivity();
            } else {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                mIsExit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIsExit = false;
                    }
                }, 2000);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /*----------------------------------Fragement相关方法*/
    private Fragment currentFragment;
    /**
     * Fragment替换(当前destrory,新的create)
     * @param target
     * @param toFragment
     * @param backStack
     */
    public void fragmentReplace(int target, Fragment toFragment, boolean backStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        String toClassName = toFragment.getClass().getSimpleName();//获取toClassName的名字

        if (fm.findFragmentByTag(toClassName) == null) {

            transaction.replace(target, toFragment, toClassName);

            if (backStack) {
                transaction.addToBackStack(toClassName);
            }

            transaction.commit();
        }
    }


    /**
     * Fragment替换(核心为隐藏当前的,显示现在的,用过的将不会destrory与create)
     * @param target
     * @param toFragment
     */
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

