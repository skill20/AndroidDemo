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

import com.demo.adapter.CommonAdapter;
import com.demo.adapter.MultiItemTypeAdapter;
import com.demo.adapter.base.ItemViewDelegate;
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
                CommonAdapter<String> adapter = new CommonAdapter<String>(context, getData(10), R.layout.holder_list_1) {
                    @Override
                    protected void convert(ViewHolder holder, String s, int position) {
                        holder.setText(R.id.tv, s);
                    }
                };

                recycleView.setLayoutManager(layoutManager);
                recycleView.setAdapter(adapter);
            }
        });

        multBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                MultiItemTypeAdapter<String> adapter = new MultiItemTypeAdapter<String>(context, getData(10));
                adapter.addItemViewDelegate(new ListHolder1());
                adapter.addItemViewDelegate(new ListHolder2());

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

                CommonAdapter<String> adapter = new CommonAdapter<String>(context, getData(100), R.layout.holder_list_1) {
                    @Override
                    protected void convert(ViewHolder holder, String s, int position) {
                        holder.setText(R.id.tv, s);
                    }
                };

                recycleView.setLayoutManager(staggeredGridLayoutManager);

                View header = LayoutInflater.from(context).inflate(R.layout.holder_list_head, recycleView, false);
                View footer = LayoutInflater.from(context).inflate(R.layout.holder_list_footer, recycleView, false);

                HeaderAndFooterWrapper<Object> headerAndFooterWrapper = new HeaderAndFooterWrapper<Object>(adapter);
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

                CommonAdapter<String> adapter = new CommonAdapter<String>(context, getData(20), R.layout.holder_list_1) {
                    @Override
                    protected void convert(ViewHolder holder, String s, int position) {
                        holder.setText(R.id.tv, s);
                    }
                };

                recycleView.setLayoutManager(gridLayoutManager);

                EmptyWrapper<Object> emptyWrapper = new EmptyWrapper<>(adapter);
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

                final CommonAdapter<String> adapter = new CommonAdapter<String>(context, getData(10), R.layout.holder_list_1) {
                    @Override
                    protected void convert(ViewHolder holder, String s, int position) {
                        holder.setText(R.id.tv, s);
                    }
                };

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
                                adapter.getDatas().addAll(getData(10));
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

    private List<String> getData(int size) {

        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add("String---" + i);
        }

        return list;
    }

    private static class ListHolder1 implements ItemViewDelegate<String> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.holder_list_1;
        }

        @Override
        public boolean isForViewType(String item, int position) {
            return position % 2 == 0;
        }

        @Override
        public void convert(ViewHolder holder, String s, int position) {
            holder.setText(R.id.tv, s);
        }
    }

    private static class ListHolder2 implements ItemViewDelegate<String> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.holder_list_2;
        }

        @Override
        public boolean isForViewType(String item, int position) {
            return position % 2 != 0;
        }

        @Override
        public void convert(ViewHolder holder, String s, int position) {
            holder.setText(R.id.tv, s);
        }
    }
}
