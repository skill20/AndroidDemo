package com.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.demo.adapter.base.ItemViewBinder;
import com.demo.adapter.base.ItemViewDelegateManager;
import com.demo.adapter.base.MultiItemType;
import com.demo.adapter.base.ViewHolder;

import java.util.List;

/**
 * Create by wangqingqing
 * On 2017/10/30 17:04
 * Copyright(c) 2017 世联行
 * Description
 */
public class MultiItemTypeAdapter<T extends MultiItemType> extends RecyclerView.Adapter<ViewHolder> {

    protected Context mContext;
    protected List<T> mDataList;

    protected ItemViewDelegateManager<T> mItemViewDelegateManager;
    protected OnItemClickListener mOnItemClickListener;


    public MultiItemTypeAdapter(Context context, List<T> data) {
        mContext = context;
        mDataList = data;
        mItemViewDelegateManager = new ItemViewDelegateManager<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (!useItemViewDelegateManager()) {
            return super.getItemViewType(position);
        }

        return mItemViewDelegateManager.getItemViewType(mDataList.get(position), position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = mItemViewDelegateManager.getItemViewLayoutId(viewType);
        ViewHolder holder = ViewHolder.createViewHolder(mContext, parent, layoutId);
        onViewHolderCreated(holder, holder.getConvertView());
        setListener(parent, holder, viewType);
        return holder;
    }

    private void setListener(ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        if (!isClickEnabled(viewType)) {
            return;
        }

        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(v, viewHolder, position);
                }
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    return mOnItemClickListener.onItemLongClick(v, viewHolder, position);
                }
                return false;
            }
        });
    }

    public void onViewHolderCreated(ViewHolder holder, View itemView) {

    }

    protected boolean isClickEnabled(int viewType) {
        return true;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder, mDataList.get(position));
    }

    public void convert(ViewHolder holder, T t) {
        mItemViewDelegateManager.convert(holder, t, holder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        if (mDataList == null) {
            throw new IllegalArgumentException("Adapter data list is null");
        }
        return mDataList.size();
    }

    protected boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    public List<T> getData() {
        return mDataList;
    }

    public MultiItemTypeAdapter addItemViewDelegate(int viewType, ItemViewBinder itemViewBinder) {
        mItemViewDelegateManager.addDelegate(viewType, itemViewBinder);
        return this;
    }


    public interface OnItemClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder holder, int position);

        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
