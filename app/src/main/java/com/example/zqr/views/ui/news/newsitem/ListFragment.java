package com.example.zqr.views.ui.news.newsitem;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zqr.MainActivity;
import com.example.zqr.R;
import com.example.zqr.model.beans.MainNewsBean;
import com.example.zqr.model.global.Singleton;
import com.example.zqr.model.global.Tools;
import com.example.zqr.model.http.zqrretrofit.MyRetrofit;
import com.example.zqr.views.ui.news.WebViewActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Android Studio.
 * User: 86182
 * Date: 2020-12-06
 * Time: 0:05
 */
public class ListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyClerViewAdapter recyClerViewAdapter;
    private String mTitle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.list_fragment, container, false);
        return mRecyclerView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //设置adapter
        recyClerViewAdapter = new RecyClerViewAdapter(getContext());
        mRecyclerView.setAdapter(recyClerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        //获取传递过来的title
        mTitle = getArguments().getString("mTitle");
        getIpInformationForPath(mTitle,Singleton.getInstance().NewsKey);
    }


    /**
     * @param type retrofit网络请求
     * @param key
     */
    public void getIpInformationForPath(String type,String key) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Singleton.getInstance().BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyRetrofit.RetrofitGETInterface retrofitGETInterface = retrofit.create( MyRetrofit.RetrofitGETInterface.class);
        Call<MainNewsBean> call = retrofitGETInterface.getRequestBody(type,key);
        call.enqueue(new Callback<MainNewsBean>() {
            @Override
            public void onResponse(Call<MainNewsBean> call, Response<MainNewsBean> response) {
                //把请求数据加入适配器
                try {
                    recyClerViewAdapter.addList(response.body().getResult().getData());
                }catch (Exception e){
                    Tools.showToast(getActivity(),Singleton.getInstance().TAG+e.toString(),2000);
                }
                //item点击事件 跳转到webView
                recyClerViewAdapter.getComeback(new RecyClerViewAdapter.Comeback() {
                    @Override
                    public void onItemClick(View view, int position) {
                        MainActivity mainactivity = (MainActivity) getActivity();
                        Intent intent = new Intent(mainactivity, WebViewActivity.class);
                        intent.putExtra("url", response.body().getResult().getData().get(position).getUrl());
                        startActivity(intent);
                    }
                });
            }
            @Override
            public void onFailure(Call<MainNewsBean> call, Throwable t) {
                Tools.showToast(getActivity(),Singleton.getInstance().TAG+t.toString(),2000);
            }
        });
    }
}
