package com.bawie.chenzhiqiang.retrofit_xrecycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private static final String TAG = "MainActivity--";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        lv = findViewById (R.id.lv);

        initViews();
    }

    private void initViews() {
        //开始使用
        RetrofitUtil.getInstance().createReq(MyService.class).getGoodsList(1).enqueue (new Callback<GoodsBean> () {
            @Override
            public void onResponse(Call<GoodsBean> call, Response<GoodsBean> response) {
                Log.d (TAG, "onResponse: "+"成功");
            }

            @Override
            public void onFailure(Call<GoodsBean> call, Throwable t) {
                Log.d (TAG, "onFailure: "+"失败");
            }
        });
    }
}
