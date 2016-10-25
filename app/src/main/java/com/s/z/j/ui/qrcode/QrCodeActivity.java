package com.s.z.j.ui.qrcode;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.WriterException;
import com.s.z.j.R;
import com.s.z.j.utils.CodeUtil;
import com.s.z.j.utils.L;
import com.szj.library.ui.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import zxing.CaptureActivity;

/**
 * Created by Administrator on 2016-10-25.
 */
@ContentView(R.layout.activity_qr_code)
public class QrCodeActivity extends BaseActivity implements View.OnClickListener{

    @ViewInject(R.id.qr_code_msg_edit)
    private EditText msgEdit;

    @ViewInject(R.id.qr_code_create_btn)
    private Button createBtn;

    @ViewInject(R.id.qr_code_sys_btn)
    private Button sysBtn;

    @ViewInject(R.id.qr_code_show_create_code_imageview)
    private ImageView showCodeImageView;

    @ViewInject(R.id.qr_code_show_sys_imageview)
    private ImageView showSysImageView;

    private String str;
    private Context c;
    private final static int SCANNIN_GREQUEST_CODE = 1;// 扫描返回正常

    @Override
    public void initialize(Bundle savedInstanceState) {
        c = this;
        createBtn.setOnClickListener(this);
        sysBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.qr_code_create_btn:
                str = msgEdit.getText().toString().trim();
                if(!"".equals(str)){
                    try {
                        Bitmap bitmap = CodeUtil.Create2DCode(str);
                        showCodeImageView.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.qr_code_sys_btn:
                Intent intent_verify = new Intent(c, CaptureActivity.class);
                startActivityForResult(intent_verify, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("AAAA", "onActivityResult返回啦！requestCode=" + requestCode + "    resultCode=" + resultCode);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE://扫描二维码返回
                if (resultCode == RESULT_OK) {//在扫描页面，有结果返回-1，没有结果或点击back键后返回0
                    Bundle bundle = data.getExtras();
                    String qrcode = bundle.getString("qrcode");
                    L.i(qrcode);
                    try {
                        Bitmap bitmap = CodeUtil.Create2DCode(qrcode);
                        showSysImageView.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                } else {
                }
                break;
        }
    }
}
