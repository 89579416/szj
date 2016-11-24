package com.s.z.j.ui.dialog;

/**
 * Created by szj on 2015/5/11.
 */

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.s.z.j.R;
import com.szj.library.ui.BaseActivity;

import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;


@ContentView(R.layout.activity_dialog_array)
public class DialogActivity extends BaseActivity implements Runnable {
    private Button btn_diaNormal;
    private Button btn_diaMulti;
    private Button btn_diaList;
    private Button btn_diaSinChos;
    private Button btn_diaMultiChos;
    private Button btn_diaProcess;
    private Button btn_diaReadProcess;
    private Button btn_diaCustom;
    private Button btn_popUpDia;

    private PopupWindow popupWindow = null;
    private Button cusPopupBtn1;
    private View view;
    ProgressDialog processDia;

    @Override
    public void initialize(Bundle savedInstanceState) {
        processDia = new ProgressDialog(DialogActivity.this);
        getView();
        setListener();
    }


    private void getView() {
        btn_diaNormal = (Button) findViewById(R.id.btn_diaNormal);
        btn_diaMulti = (Button) findViewById(R.id.btn_diaMulti);
        btn_diaList = (Button) findViewById(R.id.btn_diaList);
        btn_diaSinChos = (Button) findViewById(R.id.btn_diaSigChos);
        btn_diaMultiChos = (Button) findViewById(R.id.btn_diaMultiChos);
        btn_diaProcess = (Button) findViewById(R.id.btn_diaProcess);
        btn_diaReadProcess = (Button) findViewById(R.id.btn_diaReadProcess);
        btn_diaCustom = (Button) findViewById(R.id.btn_diaCustom);
        btn_popUpDia = (Button) findViewById(R.id.btn_popUpDia);

    }

    private void setListener() {
        btn_diaNormal.setOnClickListener(btnListener);
        btn_diaMulti.setOnClickListener(btnListener);
        btn_diaList.setOnClickListener(btnListener);
        btn_diaSinChos.setOnClickListener(btnListener);
        btn_diaMultiChos.setOnClickListener(btnListener);
        btn_diaProcess.setOnClickListener(btnListener);
        btn_diaReadProcess.setOnClickListener(btnListener);
        btn_diaCustom.setOnClickListener(btnListener);
        btn_popUpDia.setOnClickListener(btnListener);
    }

    private Button.OnClickListener btnListener = new Button.OnClickListener() {
        public void onClick(View v) {
            if (v instanceof Button) {
                int btnId = v.getId();
                switch (btnId) {
                    case R.id.btn_diaNormal:
                        showNormalDia();
                        break;
                    case R.id.btn_diaMulti:
                        showMultiDia();
                        break;
                    case R.id.btn_diaList:
                        showListDia();
                        break;
                    case R.id.btn_diaSigChos:
                        showSinChosDia();
                        break;
                    case R.id.btn_diaMultiChos:
                        showMultiChosDia();
                        break;
                    case R.id.btn_diaReadProcess:
                        showReadProcess();
                        break;
                    case R.id.btn_diaProcess:
                        showProcessDia();
                        break;
                    case R.id.btn_diaCustom:
                        showCustomDia();
                        break;
                    case R.id.btn_popUpDia:
                        showCusPopUp(v);
                        break;
                    default:
                        break;
                }
            }
        }
    };

    /*普通的对话框*/
    private void showNormalDia() {
        //AlertDialog.Builder normalDialog=new AlertDialog.Builder(getApplicationContext());
        AlertDialog.Builder normalDia = new AlertDialog.Builder(DialogActivity.this);
        normalDia.setIcon(R.mipmap.ic_launcher);
        normalDia.setTitle("普通的对话框");
        normalDia.setMessage("普通对话框的message内容");

        normalDia.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                showClickMessage("确定");
            }
        });
        normalDia.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                showClickMessage("取消");
            }
        });
        normalDia.create().show();
    }

    /*多按钮对话框*/
    private void showMultiDia() {
        AlertDialog.Builder multiDia = new AlertDialog.Builder(DialogActivity.this);
        multiDia.setIcon(R.mipmap.ic_launcher);
        multiDia.setTitle("多选项对话框");
        multiDia.setMessage("多选项对话框的message内容");
        multiDia.setPositiveButton("按钮一", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                showClickMessage("按钮一");
            }
        });
        multiDia.setNeutralButton("按钮二", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                showClickMessage("按钮二");
            }
        });
        multiDia.setNegativeButton("按钮三", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                showClickMessage("按钮三");
            }
        });
        multiDia.create().show();
    }


    /*列表对话框*/
    private void showListDia() {
        final String[] mList = {"选项1", "选项2", "选项3", "选项4", "选项5", "选项6", "选项7"};
        AlertDialog.Builder listDia = new AlertDialog.Builder(DialogActivity.this);
        listDia.setTitle("列表对话框");
        listDia.setItems(mList, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                /*下标是从0开始的*/
                showClickMessage(mList[which]);
            }
        });
        listDia.create().show();
    }

    /*单项选择对话框*/
    int yourChose = -1;

    private void showSinChosDia() {
        final String[] mList = {"选项1", "选项2", "选项3", "选项4", "选项5", "选项6", "选项7"};
        yourChose = -1;
        AlertDialog.Builder sinChosDia = new AlertDialog.Builder(DialogActivity.this);
        sinChosDia.setTitle("单项选择对话框");
        sinChosDia.setSingleChoiceItems(mList, 0, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                yourChose = which;

            }
        });
        sinChosDia.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                if (yourChose != -1) {
                    showClickMessage(mList[yourChose]);
                }
            }
        });
        sinChosDia.create().show();
    }

    ArrayList<Integer> myChose = new ArrayList<Integer>();

    private void showMultiChosDia() {
        final String[] mList = {"选项1", "选项2", "选项3", "选项4", "选项5", "选项6", "选项7"};
        final boolean mChoseSts[] = {false, false, false, false, false, false, false};
        myChose.clear();
        AlertDialog.Builder multiChosDia = new AlertDialog.Builder(DialogActivity.this);
        multiChosDia.setTitle("多项选择对话框");
        multiChosDia.setMultiChoiceItems(mList, mChoseSts, new DialogInterface.OnMultiChoiceClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    myChose.add(which);
                } else {
                    myChose.remove(which);
                }
            }
        });
        multiChosDia.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                int size = myChose.size();
                String str = "";
                for (int i = 0; i < size; i++) {
                    str += mList[myChose.get(i)];
                }
                showClickMessage(str);
            }
        });
        multiChosDia.create().show();
    }

    //进度读取框需要模拟读取
    ProgressDialog mReadProcessDia = null;
    public final static int MAX_READPROCESS = 100;

    private void showReadProcess() {
        mReadProcessDia = new ProgressDialog(DialogActivity.this);
        mReadProcessDia.setProgress(0);
        mReadProcessDia.setTitle("正在下载");
        mReadProcessDia.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mReadProcessDia.setMax(MAX_READPROCESS);
        mReadProcessDia.show();
        new Thread(this).start();
    }

    //新开启一个线程，循环的累加，一直到100然后在停止
    @Override
    public void run() {
        int Progress = 0;
        while (Progress < MAX_READPROCESS) {
            try {
                Thread.sleep(100);
                Progress++;
                mReadProcessDia.incrementProgressBy(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //读取完了以后窗口自消失
        mReadProcessDia.cancel();
    }

    /*读取中的对话框*/

    private void showProcessDia() {

        processDia.setTitle("请稍后");
        processDia.setMessage("内容读取中...");
        processDia.setIndeterminate(true);
        processDia.setCancelable(true);//点击返回键或空白处是否会关闭
        processDia.show();
        /**开启一个线程，3秒后消失*/
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                    processDia.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /*自定义对话框*/
    private void showCustomDia() {
        AlertDialog.Builder customDia = new AlertDialog.Builder(DialogActivity.this);
        final View viewDia = LayoutInflater.from(DialogActivity.this).inflate(R.layout.custom_dialog, null);
        customDia.setTitle("自定义对话框");
        customDia.setView(viewDia);
        customDia.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                EditText diaInput = (EditText) viewDia.findViewById(R.id.txt_cusDiaInput);
                showClickMessage(diaInput.getText().toString());
            }
        });
        customDia.create().show();
    }

    /*popup window 的实现*/
    private void showCusPopUp(View parent) {
        if (popupWindow == null) {
            view = LayoutInflater.from(DialogActivity.this).inflate(R.layout.dia_cuspopup_dia, null);
            cusPopupBtn1 = (Button) view.findViewById(R.id.diaCusPopupSure);
            popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,//PopupWindow 对话窗口，里面放View
                    ViewGroup.LayoutParams.WRAP_CONTENT, true);
        }
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        /*必须调用setBackgroundDrawable， 因为popupwindow在初始时，会检测background是否为null,如果是onTouch or onKey events就不会相应，所以必须设置background*/
        /*网上也有很多人说，弹出pop之后，不响应键盘事件了，这个其实是焦点在pop里面的view去了。*/
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.update();
        popupWindow.showAtLocation(parent, Gravity.CENTER_VERTICAL, 0, 0);
        cusPopupBtn1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showClickMessage("popup window的确定");
                popupWindow.dismiss();
            }
        });
    }

    /*显示点击的内容*/
    private void showClickMessage(String message) {
        Toast.makeText(DialogActivity.this, "你选择的是: " + message, Toast.LENGTH_SHORT).show();
    }
}
