package com.example.zqr.views.ui.eng;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.webkit.WebView;

import androidx.annotation.NonNull;

import com.example.zqr.R;
import com.example.zqr.model.global.Singleton;
import com.example.zqr.model.global.Tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Vector;

import static java.security.AccessController.getContext;

/**
 * Created by Android Studio.
 * User: 86182
 * Date: 2020-11-27
 * Time: 14:23
 */
public class EngNode {
    private static String fileLocation = "C:" + File.separator +
            "Users" + File.separator +
            "86182" + File.separator
            + "Desktop" + File.separator +
            "english" + File.separator +
            "《现代英汉综合词典》.txt";
    private static String fileLocation2 = "C:" + File.separator +
            "Users" + File.separator +
            "86182" + File.separator
            + "Desktop" + File.separator +
            "english" + File.separator +
            "《现代英汉综合词典》2.txt";

    private static Handler handler;
    /**
     * @param context 上下文
     * @param webView webview
     * @param rawID   词典地址  R.raw.jmyhcd
     * @param name   词典名称   cgccd
     * @param findW  love
     */
    public static void startThread(Context context, WebView webView,int rawID,String name,String findW) {
        //设置一个Handler用于更新查找后的数据  并更新webview
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                // 加载并显示HTML代码
                if (msg.what == 77) {
                    if(msg.obj.toString().equals("")){
                        Tools.showToast((Activity) context,"暂无收录……",2000);
                    }else {
                    webView.loadDataWithBaseURL(null, msg.obj.toString(), "text/html", "utf-8", null);
                    }
                    Singleton.getInstance().wordFlag=true;
                    return;
                }
            }
        };
        //开启线程
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                getHtmlDate(context,name,findW,rawID);//开始查找字符串
//            }
//        }).start();
        //webview线程需要在activity中运行，因此需要下面方式写
        webView.post(new Runnable() {
            @Override
            public void run() {
                getHtmlDate(context,name,findW,rawID);//开始查找字符串
            }
        });


    }


    /**
     * @param context 上下文
     * @param name  所查找的词典
     * @param findW   love
     * @param rawID   词典地址  R.raw.jmyhcd
     */
    private static void getHtmlDate(Context context,String name,String findW,int rawID) {
        Vector<String> mVetor = new Vector(5, 10);
        try {
            mVetor = getOneLineTxt(findW, context,rawID);//此行用于返回查找findW返回的数据
        } catch (Exception e) {
            Tools.showToast((Activity) context, e.toString(), 2000);
        }
        String temp = "";
        for (String str : mVetor) {
            temp = temp + str+"<br>"+"<p align=\"right\">"+choice(name)+"</p>";//加入某词典
        }

        //handler发送字符串
        Message tempMsg = handler.obtainMessage();
        tempMsg.what = 77;
        tempMsg.obj = temp;
        handler.handleMessage(tempMsg);
    }



    /**
     * @param TAG 转化为中文名称
     * @return
     */
    public static String choice(String TAG) {
        switch (TAG) {
            case "cgccd":
                return "《词根词缀词典》";
            case "jmyhcd":
                return "《简明英汉词典》";
            case "klsgjsjcd":
                return "《柯林斯高阶双解词典》";
            case "sklxjcd":
                return "《柯林斯星级词典》";
            case "njdydccd":
                return "《牛津短语动词词典》";
            case "njtyccd":
                return "《牛津同义词词典》";
            case "njyydpcd":
                return "《牛津英语搭配词典》";
            case "xdyhzhcd":
                return "《现代英汉综合词典》";
            case "yccydycd":
                return "《英语常用短语词典》";
        }
        return "";
    }



    /**
     * @param findW    love
     * @param mcontext  上下文
     * @param rawID    词典地址  例如：R.raw.jmyhcd
     * @return 获取一行的数据
     * @throws Exception
     */
    public static Vector<String> getOneLineTxt(String findW, Context mcontext,int rawID) throws Exception {
        Vector<String> mVetor = new Vector(5, 10);

        // 创建流
//        BufferedReader bufferedReader = new BufferedReader(
//                new InputStreamReader(new FileInputStream(""), "UTF-8"));

        //创建流 此处用本地文件的获取方式：mcontext.getResources().openRawResource(file)
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(mcontext.getResources().openRawResource(rawID), "UTF-8"));
        //读取流
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (mVetor.capacity()< 6) {//只查找5条数据
                if (regex(line, findW)) {
                    mVetor.add(line.substring(line.indexOf("<"),line.lastIndexOf(">")+1));
                }
            } else {
                break;
            }
        }
        bufferedReader.close();//关闭流
        return mVetor;
    }


    /**
     * @param line  读取的某行
     * @param findW 需要查找的某个单词 love
     * @return
     */
    public static boolean regex(String line, String findW) {
        if (line.contains("<")) {//如果包含这个标记，说明是一个单词的一行开始
            if (line.startsWith(findW)) {
                return true;
            }
        }
        return false;
    }

}
