package com.bawie.chenzhiqiang.retrofit_xrecycle;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {
    private static RetrofitUtil retrofitUtil;
    private final Retrofit retrofit;
    private static final String TAG = "RetrofitUtil===";

    //单利模式
    public static RetrofitUtil getInstance() {
        if (retrofitUtil == null) {
            retrofitUtil = new RetrofitUtil();
        }
        Log.d(TAG, "---" + retrofitUtil);
        return retrofitUtil;
    }

    //因为Retrofit需要初始化，所以在构造方法里面我们可以将Retrofit初始化
    public RetrofitUtil() {

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new MyIntercepter()).build();

        Retrofit.Builder builder = new Retrofit.Builder();
        retrofit = builder.baseUrl(HttpConfig.url)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(client)//指定配置好okhttp客户端
                .build();
        Log.d(TAG, "初始化完毕---" + retrofit);
    }

    //通过反射创建子类
    public <T> T createReq(Class<T> clz) {
        T t = retrofit.create(clz);
        Log.d(TAG, "创建服务子类------");
        return t;
    }

    //拦截器
    class MyIntercepter implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();
            RequestBody body = request.body();
            if (body instanceof FormBody) {
                FormBody.Builder newBuilder = new FormBody.Builder();
                for (int i = 0; i < ((FormBody) body).size(); i++) {
                    String key = ((FormBody) body).name(i);
                    String value = ((FormBody) body).value(i);
                    newBuilder.add(key, value);
                }

                newBuilder.add("source", "android");

                FormBody body1 = newBuilder.build();

                Request request1 = request.newBuilder().post(body1).build();
                Response response = chain.proceed(request1);
                return response;
            }
            return null;
        }
    }

}
