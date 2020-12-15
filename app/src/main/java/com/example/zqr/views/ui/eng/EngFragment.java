package com.example.zqr.views.ui.eng;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.zqr.R;
import com.example.zqr.model.global.Singleton;
import com.example.zqr.model.global.Tools;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Vector;

/**
 * Created by Android Studio.
 * User: 86182
 * Date: 2020-11-27
 * Time: 13:00
 */
public class EngFragment extends Fragment {

    View view;
    EditText editText;
    ImageView img;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_eng_main, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();//初始化数据
        webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
    }

    WebView webView;
    String[] nameArry=new String[]{"cgccd", "jmyhcd","klsgjsjcd","sklxjcd","njdydccd","njtyccd","njyydpcd","xdyhzhcd","yccydycd"};
    int[] rawID=new int[]{R.raw.cgccd,R.raw.jmyhcd,R.raw.klsgjsjcd,R.raw.sklxjcd,R.raw.njdydccd,R.raw.njtyccd,R.raw.njdydccd,R.raw.xdyhzhcd,R.raw.yccydycd};
    public void initView() {
        editText=view.findViewById(R.id.eng_edit);
        img=view.findViewById(R.id.eng_img);
        webView = view.findViewById(R.id.eng_webview);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editText.getText().toString().equals("")){
                    if(Singleton.getInstance().wordFlag){
                        Singleton.getInstance().wordFlag=false;
                        try {
                            closeInputMethod();
                            //查找数据
                            EngNode.startThread(getContext(),webView,R.raw.sklxjcd,"sklxjcd",editText.getText().toString());
                        }catch (Exception e){
                            Tools.showToast((Activity) getContext(),"发生了一个未知错误",2000);
                        }
                    }
                }
                Tools.showToast((Activity) getContext(),editText.getText().toString()+"正在搜索中……",2000);
            }
        });
        }

    /**
     * 收起键盘
     */
    public void closeInputMethod() {
        /*用于判断虚拟软键盘是否是显示的*/
        View view = getActivity().getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
