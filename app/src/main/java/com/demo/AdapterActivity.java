package com.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.demo.adapter.MultiItemTypeAdapter;
import com.demo.adapter.base.ItemViewBinder;
import com.demo.adapter.base.MultiItemType;
import com.demo.adapter.base.ViewHolder;
import com.demo.adapter.wrapper.EmptyWrapper;
import com.demo.adapter.wrapper.HeaderAndFooterWrapper;
import com.demo.adapter.wrapper.LoadMoreWrapper;
import com.demo.adapter.wrapper.StateType;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by wangqingqing
 * On 2017/10/30 17:38
 * Copyright(c) 2017 世联行
 * Description
 */
public class AdapterActivity extends AppCompatActivity {

    private View singleBtn;
    private View multBtn;
    private RecyclerView recycleView;
    private View headerBtn;
    private View emptyBtn;
    private View loadMoreBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter);

        findViewByIds();
        initView();
    }


    private void findViewByIds() {
        singleBtn = findViewById(R.id.btn_single);
        multBtn = findViewById(R.id.btn_mult);
        headerBtn = findViewById(R.id.btn_header);
        emptyBtn = findViewById(R.id.btn_empty);
        loadMoreBtn = findViewById(R.id.btn_loadMore);
        recycleView = (RecyclerView) findViewById(R.id.recycle_view);

    }

    private void initView() {

        singleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                MultiItemTypeAdapter<Result> adapter = new MultiItemTypeAdapter<>(context, getData(10));
                adapter.addItemViewBinder(Result.CLICK_ITEM_CHILD_VIEW,new ListHolder2());
                recycleView.setLayoutManager(layoutManager);
                recycleView.setAdapter(adapter);
            }
        });

        multBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                ArrayList<MultiItemType> objects = new ArrayList<>();
//                objects.add("string - 111");
//                objects.add("string - 111");
//                objects.add("string - 111");
//                objects.add("string - 111");
//                objects.add(12);
                Result2 e = new Result2();
                e.s = "result2-----";
                objects.add(e);
                Result e1 = new Result();
                e1.s = "result-----";
                objects.add(e1);


                MultiItemTypeAdapter<MultiItemType> adapter = new MultiItemTypeAdapter<>(context, objects);
                adapter.addItemViewBinder(Result.CLICK_ITEM_VIEW,new ListHolder1());
                adapter.addItemViewBinder(Result.CLICK_ITEM_CHILD_VIEW,new ListHolder2());


                recycleView.setLayoutManager(layoutManager);
                recycleView.setAdapter(adapter);
            }
        });

        headerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                GridLayoutManager layoutManager = new GridLayoutManager(context, 3);

                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);

                MultiItemTypeAdapter<Result> adapter = new MultiItemTypeAdapter<>(context, getData(100));
                adapter.addItemViewBinder(Result.CLICK_ITEM_CHILD_VIEW,new ListHolder2());
                recycleView.setLayoutManager(staggeredGridLayoutManager);

                View header = LayoutInflater.from(context).inflate(R.layout.holder_list_head, recycleView, false);
                View footer = LayoutInflater.from(context).inflate(R.layout.holder_list_footer, recycleView, false);

                HeaderAndFooterWrapper headerAndFooterWrapper = new HeaderAndFooterWrapper(adapter);
                headerAndFooterWrapper.addHeaderView(header);
                headerAndFooterWrapper.addFootView(footer);

                recycleView.setAdapter(headerAndFooterWrapper);
            }
        });

        emptyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();

                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);

                MultiItemTypeAdapter<Result> adapter = new MultiItemTypeAdapter<>(context, getData(20));
                adapter.addItemViewBinder(Result.CLICK_ITEM_CHILD_VIEW,new ListHolder2());

                recycleView.setLayoutManager(gridLayoutManager);

                EmptyWrapper emptyWrapper = new EmptyWrapper(adapter);
                View ev = LayoutInflater.from(context).inflate(R.layout.holder_list_empty, recycleView, false);
                emptyWrapper.setEmptyView(ev);

                recycleView.setAdapter(emptyWrapper);
            }
        });

        loadMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();

                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);

                final MultiItemTypeAdapter<Result> adapter = new MultiItemTypeAdapter<>(context, getData(10));
                adapter.addItemViewBinder(Result.CLICK_ITEM_CHILD_VIEW,new ListHolder2());
                recycleView.setLayoutManager(layoutManager);

                final LoadMoreWrapper loadMoreWrapper = new LoadMoreWrapper(adapter) {
                    @Override
                    public void setState(View moreView, int state) {

                        switch (state) {
                            case StateType.STATE_LOADING:
                                moreView.findViewById(R.id.pb).setVisibility(View.VISIBLE);
                                moreView.findViewById(R.id.tv).setVisibility(View.VISIBLE);

                                moreView.findViewById(R.id.tv_fail).setVisibility(View.GONE);
                                break;
                            case StateType.STATE_LOADING_MORE_FAIL:
                                moreView.findViewById(R.id.pb).setVisibility(View.GONE);
                                moreView.findViewById(R.id.tv).setVisibility(View.GONE);
                                View v = moreView.findViewById(R.id.tv_fail);
                                v.setVisibility(View.VISIBLE);
                                v.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        setRetry();
                                    }
                                });
                                break;
                            case StateType.STATE_NO_MORE:
                                moreView.findViewById(R.id.pb).setVisibility(View.GONE);
                                TextView vv = (TextView) moreView.findViewById(R.id.tv);
                                vv.setVisibility(View.VISIBLE);
                                vv.setText("no more");

                                moreView.findViewById(R.id.tv_fail).setVisibility(View.GONE);
                                break;

                        }
                    }
                };
                View ev = LayoutInflater.from(context).inflate(R.layout.holder_list_load_more, recycleView, false);
                loadMoreWrapper.setLoadMoreView(ev);

                loadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequested() {
                        recycleView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                adapter.getData().addAll(getData(10));
//                                loadMoreWrapper.setLoadingMoreEnabled(false);
//                                loadMoreWrapper.loadMoreFail("fail");
                                if (i == 2) {
                                    loadMoreWrapper.loadMoreNoMore();
                                } else {
                                    loadMoreWrapper.loadMoreFail();
                                    i++;
                                }
                                loadMoreWrapper.notifyDataSetChanged();
                            }
                        }, 3000);
                    }
                });

                recycleView.setAdapter(loadMoreWrapper);
            }
        });

    }

    int i = 1;

    private List<Result> getData(int size) {

        ArrayList<Result> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Result result = new Result();
            result.s = "String---" + i;
            list.add(result);
        }

        return list;
    }

    private static class ListHolder1 implements ItemViewBinder<Result2> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.holder_list_1;
        }

        @Override
        public void convert(ViewHolder holder, Result2 s, int position) {
            holder.setText(R.id.tv, s.s);
        }
    }

    private static class ListHolder2 implements ItemViewBinder<Result> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.holder_list_2;
        }

        @Override
        public void convert(ViewHolder holder, Result s, int position) {
            holder.setText(R.id.tv, s.s);
        }
    }


    public static class Result implements MultiItemType{

        public static final int CLICK_ITEM_VIEW = 1;
        public static final int CLICK_ITEM_CHILD_VIEW = 2;

        protected int type = CLICK_ITEM_CHILD_VIEW;
        protected String s;

        @Override
        public int getViewType() {
            return type;
        }
    }

    public static class Result2 implements MultiItemType{
        protected int type = Result.CLICK_ITEM_VIEW;
        protected String s;
        @Override
        public int getViewType() {
            return type;
        }
    }
}
