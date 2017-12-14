package com.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.demo.adapter.MultiItemTypeAdapter;
import com.demo.adapter.base.ItemViewBinder;
import com.demo.adapter.base.MultiItemType;
import com.demo.adapter.base.ViewHolder;
import com.demo.decoration.TitleItemDecoration;
import com.demo.weidget.IndexView;
import com.github.promeg.pinyinhelper.Pinyin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Create by wangqingqing
 * On 2017/11/1 14:03
 * Copyright(c) 2017 世联行
 * Description
 */
public class DecorationActivity extends AppCompatActivity {

    private RecyclerView mRecycleView;
    private IndexView mIndexView;
    private TextView mIndexTv;
    private LinearLayoutManager mLayoutManager;
    private List<CityBean> mCityList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoration);
        mRecycleView = (RecyclerView) findViewById(R.id.recycle_view);


        mLayoutManager = new LinearLayoutManager(this);

        mCityList = getData();
        MultiItemTypeAdapter<CityBean> adapter = new MultiItemTypeAdapter<>(this, mCityList);
        adapter.addItemViewDelegate(CityBean.CITY_CHAR, new ListHolder1());
        adapter.addItemViewDelegate(CityBean.CITY_NAME, new ListHolder2());

        mRecycleView.addItemDecoration(new TitleItemDecoration().registerStickType(R.layout.holder_list_1, new TitleItemDecoration.StickTypeCreator() {
            @Override
            public boolean onCreate(RecyclerView parent, int adapterPosition) {
                return true;
            }
        }));
        mRecycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mRecycleView.setLayoutManager(mLayoutManager);
        mRecycleView.setAdapter(adapter);

        mIndexView = (IndexView) findViewById(R.id.indexBar);
        mIndexView.setOnIndexPressedListener(new IndexView.OnIndexPressedListener() {
            @Override
            public void onIndexPressed(int index, String text) {
                mIndexTv.setVisibility(View.VISIBLE);
                mIndexTv.setText(text);

                int indexPosition = indexPosition(text);
                if (indexPosition != -1) {
                    mLayoutManager.scrollToPositionWithOffset(indexPosition, 0);
                }
            }

            @Override
            public void onMotionEventEnd() {
                mIndexTv.setVisibility(View.GONE);
            }
        });

        mIndexTv = (TextView) findViewById(R.id.tv_index_text);
    }

    private int indexPosition(String text) {

        int size = mCityList.size();
        for (int i = 0; i < size; i++) {
            CityBean cityBean = mCityList.get(i);
            if (cityBean.name == null && TextUtils.equals(text, cityBean.firstChar)) {
                return i;
            }
        }

        return -1;
    }

    private List<CityBean> getData() {
        String[] array = getResources().getStringArray(R.array.provinces);

        ArrayList<CityBean> cityList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        String preChar = null;

        for (String s : array) {
            CityBean cityBean = new CityBean();
            cityBean.name = s;

            sb.delete(0, sb.length());

            char[] chars = s.toCharArray();
            for (char c : chars) {
                sb.append(Pinyin.toPinyin(c).toUpperCase());
            }

            String pinY = sb.toString();
            String cha = String.valueOf(pinY.charAt(0));

            cityBean.pinYin = pinY;
            cityBean.firstChar = cha;

            cityList.add(cityBean);
        }

        Collections.sort(cityList, new Comparator<CityBean>() {
            @Override
            public int compare(CityBean o1, CityBean o2) {
                return o1.pinYin.compareTo(o2.pinYin);
            }
        });

        ArrayList<CityBean> list = new ArrayList<>();

        int size = cityList.size();
        for (int i = 0; i < size; i++) {

            CityBean cityBean = cityList.get(i);
            String firstChar = cityBean.firstChar;
            if (!TextUtils.equals(firstChar, preChar)) {
                CityBean bean = new CityBean();
                bean.firstChar = firstChar;
                bean.type = CityBean.CITY_CHAR;
                list.add(bean);
            }

            list.add(cityBean);
            preChar = firstChar;
        }

        return list;
    }

    public static class CityBean implements MultiItemType{

        public static final int CITY_NAME = 10;
        public static final int CITY_CHAR = 11;

        public String name;
        public String pinYin;
        public String firstChar;

        public int type = CITY_NAME;

        @Override
        public int getViewType() {
            return type;
        }
    }

    private static class ListHolder1 implements ItemViewBinder<CityBean> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.holder_list_1;
        }


        @Override
        public void convert(ViewHolder holder, CityBean s, int position) {
            holder.setText(R.id.tv, s.firstChar);
        }
    }

    private static class ListHolder2 implements ItemViewBinder<CityBean> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.holder_list_2;
        }

        @Override
        public void convert(ViewHolder holder, CityBean s, int position) {
            holder.setText(R.id.tv, s.name);
        }
    }
}
