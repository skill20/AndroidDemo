package com.demo.adapter.base;

/**
 * Create by wangqingqing
 * On 2017/10/30 16:50
 * Copyright(c) 2017 世联行
 * Description
 */
public interface ItemViewDelegate<T> {

    int getItemViewLayoutId();

    void convert(ViewHolder holder, T t, int position);
}
