package com.s.z.j.ui.apppackage;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.s.z.j.R;
import com.s.z.j.utils.AppInfoProvider;
import com.szj.library.ui.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 获取系统所有APP包名
 * Created by Administrator on 2016-11-22.
 */
@ContentView(R.layout.activity_package_name)
public class SystemAppPackageNameActivity extends BaseActivity {

    @ViewInject(R.id.package_name_message_textview)
    private TextView messageTxt;
    @Override
    public void initialize(Bundle savedInstanceState) {
        String msg = AppInfoProvider.getAllAppPackageName(context);
        if(!TextUtils.isEmpty(msg)){
            messageTxt.setText(msg);
        }else{
            messageTxt.setText("获取包名失败");
        }
    }
}
