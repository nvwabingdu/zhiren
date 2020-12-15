package com.example.zqr.model.global;

import com.example.zqr.R;

/**
 * Created by Android Studio.
 * User: 86182
 * Date: 2020-11-27
 * Time: 12:21
 */
public class Singleton {
    //简单单例
    private static Singleton single = null;
    private Singleton() {}

    public static Singleton getInstance() {
        if (single == null) {
            synchronized (Singleton.class) {
                if (single == null) {
                    single = new Singleton();
                }
            }
        }
        return single;
    }
    //常用变量
    public  String wq="wq";
    public  String TAG="智人：";
    public  String BaseUrl="http://v.juhe.cn/toutiao/";
    public  String NewsKey="2131f22f6c1e2cec174d8c7b803770c5";
    public  Boolean wordFlag=true;

}
