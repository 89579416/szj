package com.s.z.j.rxjava;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.s.z.j.R;
import com.szj.library.ui.BaseActivity;

import org.xutils.view.annotation.ContentView;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
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

    @Override
    public void initialize(Bundle savedInstanceState) {
        usernameEdit = (EditText) findViewById(R.id.rxjava_username);
        pwdEdit = (EditText) findViewById(R.id.rxjava_pwd);
        msgTv = (TextView) findViewById(R.id.rxjava_msg);

        httpClient = RxHttpClient.getInstance();
        httpClient.init(this);
    }

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