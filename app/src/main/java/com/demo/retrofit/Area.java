package com.demo.retrofit;

import java.util.List;

/**
 * Create by wangqingqing
 * On 2017/10/19 15:33
 * Copyright(c) 2017 世联行
 * Description
 */
public class Area {
    public String type;
    public int errorCode;
    public int elapsedTime;
    public List<List<TranslateResultBean>> translateResult;

    public static class TranslateResultBean {
        public String src;
        public String tgt;
    }
}
