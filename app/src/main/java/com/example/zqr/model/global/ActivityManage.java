package com.example.zqr.model.global;

import android.app.Activity;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Android Studio.
 * User: 86182
 * Date: 2020-11-27
 * Time: 12:28
 */
public class ActivityManage {
    private static ActivityManage instance;
    private static Set<Activity> activitys;

    private ActivityManage() {
    }

    public static ActivityManage getInstance() {
        if (instance == null)
            instance = new ActivityManage();
        if (activitys == null)
            activitys = new HashSet<Activity>();
        return instance;
    }

    public void addActivity(Activity activity) {
        activitys.add(activity);
    }

    public void removeActivity(Activity activity) {
        activitys.remove(activity);
    }

    public void finishAllActivity() {
        for (Activity activity : activitys) {
            if (activity != null)
                activity.finish();
        }

    }
}
