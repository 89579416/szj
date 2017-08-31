package com.s.z.j.rxjava;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.s.z.j.R;
import com.szj.library.ui.BaseActivity;

import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017-08-31.
 */
@ContentView(R.layout.activity_rxjava)
public class RxJavaActivity extends BaseActivity {
    EditText usernameEdit;
    EditText pwdEdit;
    TextView msgTv;
    String username, pwd;
    RxHttpClient httpClient;
    EditText serchEdit;

    @Override
    public void initialize(Bundle savedInstanceState) {
        usernameEdit = (EditText) findViewById(R.id.rxjava_username);
        pwdEdit = (EditText) findViewById(R.id.rxjava_pwd);
        msgTv = (TextView) findViewById(R.id.rxjava_msg);
        serchEdit = (EditText) findViewById(R.id.rxjava_serch_edit);

        httpClient = RxHttpClient.getInstance();
        httpClient.init(this);
        serch();
    }

    /**
     * 搜索框内容发生变化时监听
     */
    private void serch() {
        RxTextView.textChanges(this.serchEdit)
                .debounce(500, TimeUnit.MILLISECONDS)
                .switchMap(new Function<CharSequence, ObservableSource<List<String>>>() {
                    @Override
                    public ObservableSource<List<String>> apply(@NonNull CharSequence charSequence) throws Exception {
                        //  网络操作，获取我们需要的数据
                        List<String> list = new ArrayList<String>();

                        if (charSequence.length() > 0) {
                            //模拟收到的数据，实际上是从服务器获取的
                            list.add("111");
                            list.add("222");
                        } else {
                            //当输入框里的面没有内容时，啥子也不做
                        }
                        return Observable.just(list);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(@NonNull List<String> strings) throws Exception {
                        //界面更新，
                        for (String s : strings) {
                            Log.i("AAAA", "搜索结果：" + s);
                        }
                    }
                });
    }

    /**
     * 获取用户输入的用户名和密码
     * @return
     */
    private UserParam getUserParm() {
        username = usernameEdit.getText().toString().trim();
        pwd = pwdEdit.getText().toString().trim();
        UserParam userParam = new UserParam(username, pwd);
        return userParam;
    }

    /**
     * 点击按钮事件
     * 通过 Observable.just 创建一个
     * *#*#4636#*#*
     * @param view
     */
    public void loginChick(View view) {
        Observable.just(getUserParm()).flatMap(new Function<UserParam, ObservableSource<BaseResult>>() {
            @Override
            public ObservableSource<BaseResult> apply(UserParam userParam) throws Exception {
                BaseResult result = httpClient.api.login(username, pwd).execute().body();
                if (result != null) {
                    return Observable.just(result);
                } else {
                    return null;
                }
            }
        }).flatMap(new Function<BaseResult, ObservableSource<User>>() {
            @Override
            public ObservableSource<User> apply(BaseResult baseResult) throws Exception {
                User user = httpClient.api.getUserInfo(baseResult.getUser_id()).execute().body();
                if (user != null) {
                    return Observable.just(user);
                } else {
                    return null;
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<User>() { //  subscribeOn(Schedulers.io())是将前面的放入子线程   observeOn(AndroidSchedulers.mainThread())是将后面的放入主线程
            @Override
            public void accept(User user) throws Exception {
                if (user != null) {
                    msgTv.setText(user.getId() + "     " + user.getName() + "   " + user.getPath());
                }
            }
        });

    }
}