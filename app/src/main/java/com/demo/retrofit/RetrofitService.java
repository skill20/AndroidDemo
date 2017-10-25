package com.demo.retrofit;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Create by wangqingqing
 * On 2017/10/19 15:11
 * Copyright(c) 2017 世联行
 * Description
 */
public interface RetrofitService {

//    @POST("/crm/hsAdapter/api/common/queryCountyAroundByCityId")
//    Observer<List<Area>> getArea(@Body RequestBody requestBody);//"countyId"

    @POST("translate?doctype=json&jsonversion=&type=&keyfrom=&model=&mid=&imei=&vendor=&screen=&ssid=&network=&abtest=")
    @FormUrlEncoded
    Observable<Area> getArea(@Field("i") String targetSentence);
}
