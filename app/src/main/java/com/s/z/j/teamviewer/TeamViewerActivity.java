package com.s.z.j.teamviewer;

import android.os.Bundle;
import android.widget.TextView;

import com.s.z.j.R;
import com.szj.library.ui.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2017-03-24.
 */
@ContentView(R.layout.activity_teamviewer)
public class TeamViewerActivity extends BaseActivity {

    @ViewInject(R.id.teamviewer_text)
    TextView showTxt;

    @Override
    public void initialize(Bundle savedInstanceState) {
//        /** 创建一个新的实例 */
//        final TVSessionConfiguration config = new TVSessionConfiguration.Builder( new TVConfigurationID("g589ukh"))//
//                        .setServiceCaseName("0152125421")
//                        .setServiceCaseDescription("android_share_test_description111")
//                        .build();
//
//
//
////        final TVSessionConfiguration config =
////                new TVSessionConfiguration.Builder(new TVSessionCode("s02-027-263"))
////                        .build();
//        TVSessionFactory.createTVSession(this, "fe5c8c45-33fe-5fe5-c721-d83d0f2dd745",//you sdk token
//                new TVSessionCreationCallback() {
//                    @Override
//                    public void onTVSessionCreationSuccess(TVSession session) {
//                        session.start(config);
//                        session.setTVSessionCallback(new TVSessionCallback() {
//                            @Override
//                            public void onTVSessionError(TVSessionError error) {
//                                // React to session errors
//                                L.i("error="+error);
//                            }
//
//                            @Override
//                            public void onTVSessionEnd() {
//                                // React to the session ending
//                                L.i("onTVSessionEnd=");
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onTVSessionCreationFailed(TVCreationError error) {
//                        L.i("onTVSessionCreationFailed--error="+error);
//                    }
//                });
    }
}
