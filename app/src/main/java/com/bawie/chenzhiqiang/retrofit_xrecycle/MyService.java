package com.bawie.chenzhiqiang.retrofit_xrecycle;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyService {
    @GET("product/getProducts")
    Call<GoodsBean> getGoodsList(@Query ("pscid") int pscid);
}
