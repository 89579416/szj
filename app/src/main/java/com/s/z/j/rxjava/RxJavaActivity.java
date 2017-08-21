package com.s.z.j.rxjava;

import android.app.Activity;
import android.os.Bundle;

import com.s.z.j.R;
import com.s.z.j.utils.L;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017-07-25.
 */
public class RxJavaActivity extends Activity {
    private String TAG = "AAAA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        myObservable.subscribe(mySubscriber);
        L.i("----------------------------");
        myObservable.subscribe(onNextAction);
    }

    /**
     * Observable部分,被观察者部分
     */
    Observable<String> myObservable = Observable.create(
            new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> sub) {
                    sub.onNext("Hello, world!");
                    sub.onNext("测试文字1");
                    sub.onNext("测试文字2");
                    sub.onCompleted();
                }
            }
    );

    /**
     * Subscriber部分，观察者部分
     */
    Subscriber<String> mySubscriber = new Subscriber<String>() {
        @Override
        public void onNext(String s) {
            System.out.println(s);
        }

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
        }
    };

    Action1<String> onNextAction = new Action1<String>() {
        @Override
        public void call(String s) {
            System.out.println(s);
        }
    };
}