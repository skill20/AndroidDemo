package com.demo.retrofit;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Create by wangqingqing
 * On 2017/10/19 15:25
 * Copyright(c) 2017 世联行
 * Description
 */
public class RetrofitFactory {

    private static final String BASE_URL = "http://fanyi.youdao.com/";

    private static final long TIMEOUT = 30;

    // Retrofit是基于OkHttpClient的，可以创建一个OkHttpClient进行一些配置
    private static OkHttpClient httpClient = new OkHttpClient.Builder()
            // 添加通用的Header
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request.Builder builder = chain.request().newBuilder();
                    builder.addHeader("token", "123");
                    return chain.proceed(builder.build());
                }
            })
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build();

    private static RetrofitService retrofitService = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .build()
            .create(RetrofitService.class);

    public static RetrofitService getInstance() {
        return retrofitService;
    }

}
