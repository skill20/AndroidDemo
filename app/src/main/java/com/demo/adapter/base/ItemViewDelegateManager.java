package com.demo.adapter.base;

import android.support.v4.util.SparseArrayCompat;

/**
 * Create by wangqingqing
 * On 2017/10/30 16:52
 * Copyright(c) 2017 世联行
 * Description
 */
public class ItemViewDelegateManager<T extends MultiItemType> {

    private SparseArrayCompat<ItemViewBinder<T>> delegates = new SparseArrayCompat<>();

    public int getItemViewDelegateCount() {
        return delegates.size();
    }

    public ItemViewDelegateManager<T> addDelegate(int viewType, ItemViewBinder<T> delegate) {
        if (delegates.get(viewType) != null) {
            throw new IllegalArgumentException(
                    "An ItemViewBinder is already registered for the viewType = "
                            + viewType
                            + ". Already registered ItemViewBinder is "
                            + delegates.get(viewType));
        }
        delegates.put(viewType, delegate);
        return this;
    }

    public ItemViewDelegateManager<T> removeDelegate(ItemViewBinder<T> delegate) {
        if (delegate == null) {
            throw new NullPointerException("ItemViewBinder is null");
        }
        int indexToRemove = delegates.indexOfValue(delegate);

        if (indexToRemove >= 0) {
            delegates.removeAt(indexToRemove);
        }
        return this;
    }

    public ItemViewDelegateManager<T> removeDelegate(int itemType) {
        int indexToRemove = delegates.indexOfKey(itemType);

        if (indexToRemove >= 0) {
            delegates.removeAt(indexToRemove);
        }
        return this;
    }

    public int getItemViewType(T item, int position) {
        return item.getViewType();
    }

    public void convert(ViewHolder holder, T item, int position) {

        int viewType = item.getViewType();
        ItemViewBinder<T> itemViewBinder = delegates.get(viewType);
        itemViewBinder.convert(holder, item, position);
    }


    public ItemViewBinder getItemViewDelegate(int viewType) {
        return delegates.get(viewType);
    }

    public int getItemViewLayoutId(int viewType) {
        return getItemViewDelegate(viewType).getItemViewLayoutId();
    }

    public int getItemViewType(ItemViewBinder itemViewBinder) {
        return delegates.indexOfValue(itemViewBinder);
    }
}
