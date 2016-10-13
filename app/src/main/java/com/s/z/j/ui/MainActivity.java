package com.s.z.j.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.s.z.j.R;
import com.s.z.j.utils.HttpUtils;
import com.szj.library.ui.BaseActivity;
import com.szj.library.utils.T;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 程序主入口
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements View.OnClickListener {


    @ViewInject(R.id.main_get_net_type_btn)
    private Button getNetBtn;

    @Override
    public void initialize(Bundle savedInstanceState) {
        getNetBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_get_net_type_btn:
                T.s(context, "当前网络类型："+HttpUtils.getNetType(context));
                break;
            default:break;
        }
    }
}
