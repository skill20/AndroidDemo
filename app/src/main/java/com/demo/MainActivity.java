package com.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.demo.retrofit.Area;
import com.demo.retrofit.RetrofitFactory;
import com.demo.retrofit.RxSchedulers;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private Button btnView;
    private TextView tvView;
    private RecyclerView recycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnView = (Button) findViewById(R.id.btn);
        tvView = (TextView) findViewById(R.id.tv);
        findViewById(R.id.btn_adapter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),AdapterActivity.class));
            }
        });

        findViewById(R.id.btn_decoration).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),DecorationActivity.class));
            }
        });
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                Observable<Area> observable = RetrofitFactory.getInstance().getArea("i love you");
//                observable.compose(RxSchedulers.<Area>compose()).subscribe(new Consumer<Area>() {
//                    @Override
//                    public void accept(Area area) throws Exception {
//                        Log.i("accept suc", "suc");
//                    }
//
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        Log.i("accept error",throwable.getMessage());
//                    }
//                });



                Observable.interval(2,1, TimeUnit.SECONDS).doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.i("accept", "第 " + aLong + " 次轮询" );
                        Observable<Area> observable = RetrofitFactory.getInstance().getArea("i love you");
                        observable.compose(RxSchedulers.<Area>compose()).subscribe(new Observer<Area>() {
                            @Override
                            public void onSubscribe(Disposable disposable) {

                            }

                            @Override
                            public void onNext(Area area) {

                            }

                            @Override
                            public void onError(Throwable throwable) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                    }
                }).subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {

                    }
                });

//                Observable.interval(1, TimeUnit.SECONDS, Schedulers.trampoline())
//                        .subscribe(new Observer<Long>() {
//                            @Override
//                            public void onSubscribe(Disposable disposable) {
//                                System.out.println("onSubscribe");
//                            }
//
//                            @Override
//                            public void onNext(Long aLong) {
//                                System.out.println("onNext");
//                            }
//
//                            @Override
//                            public void onError(Throwable throwable) {
//                                System.out.println("onError");
//                            }
//
//                            @Override
//                            public void onComplete() {
//                                System.out.println("onComplete");
//                            }
//                        });
            }
        });
    }

}
