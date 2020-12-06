package com.example.zqr.views.login.leadpage;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

/**
 * Created by Android Studio.
 * User: 86182
 * Date: 2020-11-27
 * Time: 13:17
 */
class LeadViewPagesAdapter extends PagerAdapter {
    private ImageView[] images;

    /**
     * @param views 构造方法 传递进一个图片数组
     */
    public LeadViewPagesAdapter(ImageView[] views) {
        this.images = views;
    }

    /**
     * @return 复写方法
     */
    @Override
    public int getCount() {
        return images.length;
    }

    /**
     * @param view   复写方法
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    /**
     * @param container 生成你要滑动的这一页
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(images[position]);
        return images[position];
    }

    /**
     * @param container 销毁的item
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(images[position]);
    }
}
