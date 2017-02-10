package com.s.z.j.calculator;
import android.os.Bundle;
import com.s.z.j.R;
import com.szj.library.ui.BaseActivity;
/**
 * Created by Administrator on 2017-02-10.
 */
import java.util.Arrays;
import bsh.EvalError;
import bsh.Interpreter;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_calculator)
public class CalculatorActivity extends BaseActivity implements OnClickListener{

    EditText rsText = null;  //显示器
    boolean isClear = false; //用于是否显示器需要被清理

    @Override
    public void initialize(Bundle savedInstanceState) {
        //fun 功能按钮
        rsText = (EditText)findViewById(R.id.calculatorEdittext);
        Button btnd = (Button) findViewById(R.id.calculator_d_btn);
        Button btnDel = (Button)findViewById(R.id.calculator_delete_btn);
        Button btnPlu = (Button)findViewById(R.id.calculator_plus_btn);
        Button btnMin = (Button)findViewById(R.id.calculator_minus_btn);
        Button btnMul = (Button)findViewById(R.id.calculator_multiply_btn);
        Button btnDiv = (Button)findViewById(R.id.calculator_division_btn);
        Button btnEqu = (Button)findViewById(R.id.calculator_equal_btn);
        Button btnLeft = (Button)findViewById(R.id.calculator_left_btn);
        Button btnRight = (Button)findViewById(R.id.calculator_right_btn);

        //num 数字按钮
        Button num0 = (Button)findViewById(R.id.calculator_num0_btn);
        Button num1 = (Button)findViewById(R.id.calculator_num1_btn);
        Button num2 = (Button)findViewById(R.id.calculator_num2_btn);
        Button num3 = (Button)findViewById(R.id.calculator_num3_btn);
        Button num4 = (Button)findViewById(R.id.calculator_num4_btn);
        Button num5 = (Button)findViewById(R.id.calculator_num5_btn);
        Button num6 = (Button)findViewById(R.id.calculator_num6_btn);
        Button num7 = (Button)findViewById(R.id.calculator_num7_btn);
        Button num8 = (Button)findViewById(R.id.calculator_num8_btn);
        Button num9 = (Button)findViewById(R.id.calculator_num9_btn);
        Button dot = (Button)findViewById(R.id.calculator_dot_btn);

        //listener
        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);
        btnDel.setOnClickListener(this);
        btnPlu.setOnClickListener(this);
        btnMin.setOnClickListener(this);
        btnMul.setOnClickListener(this);
        btnDiv.setOnClickListener(this);
        btnEqu.setOnClickListener(this);
        num0.setOnClickListener(this);
        num1.setOnClickListener(this);
        num2.setOnClickListener(this);
        num3.setOnClickListener(this);
        num4.setOnClickListener(this);
        num5.setOnClickListener(this);
        num6.setOnClickListener(this);
        num7.setOnClickListener(this);
        num8.setOnClickListener(this);
        num9.setOnClickListener(this);
        dot.setOnClickListener(this);
        btnd.setOnClickListener(this);
    }

    @Override
    public void onClick(View e) {
        Button btn = (Button) e;
        String exp = rsText.getText().toString();
        if(isClear &&(
                btn.getText().equals("0")
                || btn.getText().equals("1")
                || btn.getText().equals("2")
                || btn.getText().equals("3")
                || btn.getText().equals("4")
                || btn.getText().equals("5")
                || btn.getText().equals("6")
                || btn.getText().equals("7")
                || btn.getText().equals("8")
                || btn.getText().equals("9")
                || btn.getText().equals("."))
                || btn.getText().equals("算数公式错误")){
            rsText.setText("");
            isClear = false;
        }
        if(btn.getText().equals("C")){
            rsText.setText("");
        }else if(btn.getText().equals("D")){
            if(isEmpty(exp)) return;
            else
                rsText.setText(exp.substring(0, exp.length()-1));
        }else if(btn.getText().equals("=")){
            if(isEmpty(exp)) return;
            exp = exp.replaceAll("×", "*");
            exp = exp.replaceAll("÷", "/");
            rsText.setText(getRs(exp));
            isClear = false;
        }else{
            rsText.setText(rsText.getText()+""+btn.getText());
            isClear = false;
        }
        //操作完成后始终保持光标在最后一位
        rsText.setSelection(rsText.getText().length());
    }

    /***
     * @param  exp 算数表达式
     * @return 根据表达式返回结果
     */
    private String getRs(String exp){
        Interpreter bsh = new Interpreter();
        Number result = null;
        try {
            exp = filterExp(exp);
            result = (Number)bsh.eval(exp);
        } catch (EvalError e) {
            e.printStackTrace();
            isClear = true;
            return "算数公式错误";
        }
        exp = result.doubleValue()+"";
        if(exp.endsWith(".0"))
            exp = exp.substring(0, exp.indexOf(".0"));
        return exp;
    }


    /**
     * 因为计算过程中,全程需要有小数参与,所以需要过滤一下
     * @param exp 算数表达式
     * @return
     */
    private String filterExp(String exp) {
        String num[] = exp.split("");
        String temp = null;
        int begin=0,end=0;
        for (int i = 1; i < num.length; i++) {
            temp = num[i];
            if(temp.matches("[+-/()*]")){
                if(temp.equals(".")) continue;
                end = i - 1;
                temp = exp.substring(begin, end);
                if(temp.trim().length() > 0 && temp.indexOf(".")<0)
                    num[i-1] = num[i-1]+".0";
                begin = end + 1;
            }
        }
        return Arrays.toString(num).replaceAll("[\\[\\], ]", "");
    }

    /***
     * @param str
     * @return 字符串非空验证
     */
    private boolean isEmpty(String str){
        return (str==null || str.trim().length()==0);
    }

}