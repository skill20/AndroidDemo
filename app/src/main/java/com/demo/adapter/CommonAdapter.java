package com.demo.adapter;

import android.content.Context;

import com.demo.adapter.base.ItemViewDelegate;
import com.demo.adapter.base.ViewHolder;

import java.util.List;

/**
 * Create by wangqingqing
 * On 2017/10/30 17:15
 * Copyright(c) 2017 世联行
 * Description 单条目列表
 */
public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {

    public CommonAdapter(Context context, List<T> datas, final int layoutId) {
        super(context, datas);

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position) {
                CommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(ViewHolder holder, T t, int position);
}
