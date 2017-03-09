package com.example.minhquan.calculator;

import android.graphics.Color;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import android.icu.text.IDNA;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    Button btnClear;
    Button btnDiv;
    Button btnMul;
    Button btnDel;
    Button btnSub;
    Button btnPlus;
    Button btnBrac;
    Button btnEqual;
    Button btnPoM;
    Button btnDot;
    Button btn0;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    Button btn9;

    TextView txtAll;
    String temp="";
    String rq="";

    int flagDot=0; // co hieu xu ly dau .
    int countDot=0; // co hieu xac dinh so lan su dung dau .

    int flagBrac =0;// co hieu xu ly dau ()
    int countBrac = 0; // co hieu xy ly dau ()
    int flagFC = 0; // co hieu xac dinh mo hay dong ()

    int fistrq = 0; // luu vi tri ky tu dau tien trong chuoi rq de them dau -
    int fistTemp = 0; // luu vi tri ky tu dau tien trong chuoi temp de them dau -

    int flagPM = -1; // co hieu xu ly dau +/-
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ánh xạ text view và button
        txtAll = (TextView) findViewById(R.id.txtAll);

        btn0 = (Button) findViewById(R.id.btn0);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btnClear = (Button) findViewById(R.id.btnClear) ;
        btnDiv = (Button) findViewById(R.id.btnDiv) ;
        btnMul = (Button) findViewById(R.id.btnMul) ;
        btnDel = (Button) findViewById(R.id.btnDel) ;
        btnSub = (Button) findViewById(R.id.btnSub) ;
        btnPlus = (Button) findViewById(R.id.btnPlus) ;
        btnBrac = (Button) findViewById(R.id.btnBrac) ;
        btnEqual = (Button) findViewById(R.id.btnEqual) ;
        btnPoM = (Button) findViewById(R.id.btnPoM) ;
        btnDot = (Button) findViewById(R.id.btnDot) ;

        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnDiv.setOnClickListener(this);
        btnMul.setOnClickListener(this);
        btnDel.setOnClickListener(this);
        btnSub.setOnClickListener(this);
        btnPlus.setOnClickListener(this);
        btnBrac.setOnClickListener(this);
        btnEqual.setOnClickListener(this);
        btnPoM.setOnClickListener(this);
        btnDot.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnDel:
            {
                if(rq.length() > 0)
                {
                    char c[] = temp.substring(temp.length()-1,temp.length()).toCharArray();
                    char d[] = rq.substring(rq.length()-1,rq.length()).toCharArray();
                    char e[] = rq.substring(rq.length()-1,rq.length()).toCharArray();

                    if(c[0] == '(' || c[0] == ')') // kiem tra ki tu bi xoa co phai la dau ()
                        countBrac -- ;
                    if(d[0] == '>') // xu ly chuoi requets vi co su dung html de doi mau dau +-*/
                    {
                        rq = rq.substring(0,rq.length()-30); // html đổi màu dấu có độ dài 30 kí tự
                    }
                    else
                    {
                        rq = rq.substring(0,rq.length()-1);
                    }
                    if(e[0] == '.')
                    {
                        flagDot = 0;
                        countDot = 0;
                        rq = rq.substring(0,rq.length()-1);
                    }
                    temp = temp.substring(0,temp.length()-1);
                    txtAll.setText(Html.fromHtml(rq),TextView.BufferType.SPANNABLE);
                }

                break;
            }
            case R.id.btnBrac:
            {
                if(flagFC == 0) // nếu phía trước là các toán tử hoặc bắt đầu biểu thức
                {
                    if(flagBrac == 0)
                    {
                        temp += "(";
                        rq+= "(";

                        fistrq = rq.length();
                        fistTemp = temp.length();
                        countBrac ++;
                    }
                    else
                    {
                        temp+= "×(";
                        rq+="<font color='#1E88E5'>×</font>(";

                        fistrq = rq.length();
                        fistTemp = temp.length();
                        countBrac ++;
                    }
                }
                else // nếu phía trước là số
                {
                    if(countBrac > 0) // nếu phía trước đã có mở ngoặc, ta thực hiện đóng ngoặc
                    {
                        temp += ")";
                        rq+= ")";
                        countBrac --;
                    }
                    else // nếu phía trước chưa có mở ngoặc
                    {
                        if(flagBrac == 0) // nếu là bắt đầu biểu thức
                        {
                            temp += "(";
                            rq+= "(";

                            fistrq = rq.length();
                            fistTemp = temp.length();
                            countBrac ++;
                        }
                        else // nếu là số ta thêm dấu *
                        {
                            temp+= "×(";
                            rq+="<font color='#1E88E5'>×</font>(";
                            fistrq = rq.length();
                            fistTemp = temp.length();
                            countBrac ++;
                        }
                    }

                }
                txtAll.setText(Html.fromHtml(rq),TextView.BufferType.SPANNABLE);
                break;
            }
            case R.id.btnPoM:
            {
                String strRq ="(-";// chuoi tam de xu ly dau +/-
                String strTemp ="(-";// chuoi tam de xu ly dau +/-

                // các chuỗi tạm để cắt ghép
                String rq1="";
                String rq2="";

                String t1="";
                String t2="";

                if(flagPM == 1)  // nếu đã bấm dấu plus minus 1 lần, ta thực hiện xóa dấu
                {
                    if(fistrq == 0) // vị trí đầu chuỗi
                    {
                        rq=rq.substring(2,rq.length());
                        temp = temp.substring(2,temp.length());

                        countBrac --;
                    }
                    else // vị trí bất kì trong chuỗi
                    {
                        rq1=rq.substring(0,fistrq);
                        rq2=rq.substring(fistrq+2,rq.length());

                        t1= temp.substring(0,fistTemp);
                        t2=temp.substring(fistTemp+2,temp.length());

                        rq= rq1 + rq2;
                        temp = t1+t2;

                        countBrac -- ;
                    }

                }
                else // nếu chưa bấm dấu plus minus
                {
                    if(fistrq == 0) // vị trí đầu chuỗi
                    {
                        strRq += rq;
                        strTemp += temp;

                        rq = strRq;
                        temp = strTemp;

                        countBrac ++;
                    }
                    else // vị trí bất kì trong chuỗi
                    {
                        rq1=rq.substring(0,fistrq);
                        rq2=rq.substring(fistrq,rq.length());

                        t1=temp.substring(0,fistTemp);
                        t2=temp.substring(fistTemp,temp.length());

                        rq= rq1 + "(-" + rq2;
                        temp = t1 + "(-" + t2;
                        countBrac ++;
                    }

                }
                flagPM *= -1; // sau mỗi lần bấm, ta thay đổi trạng thái cờ hiệu
                txtAll.setText(Html.fromHtml(rq),TextView.BufferType.SPANNABLE);
                break;
            }
            case R.id.btnDot:
            {
                // xét điều kiện để thêm hoặc ko thêm dấu .
                if(countDot == 0 && flagDot == 0)
                {
                    temp+= "0.";
                    rq+= "0.";
                    txtAll.setText(Html.fromHtml(rq),TextView.BufferType.SPANNABLE);
                    countDot = 1;
                    flagDot = 2;
                }
                else
                {
                    if(countDot == 0 && flagDot == 1 )
                    {
                        temp+= ".";
                        rq+= ".";
                        txtAll.setText(Html.fromHtml(rq),TextView.BufferType.SPANNABLE);
                        countDot = 1;
                        flagDot = 1;
                    }
                }
                break;
            }
            case R.id.btnEqual:
            {
                String rsMul = temp;
                String rs="";
                String rsSub="";
                rsSub=temp.replaceAll("–","-");
                rsMul = rsSub.replaceAll("×","*");
                rs= rsMul.replaceAll("÷","/");

                String ketqua = rq+ "<br><font color='#4DC95D'> = </font>" + TinhToan(rs);

                txtAll.setText(Html.fromHtml(ketqua),TextView.BufferType.SPANNABLE);


                break;
            }
            case R.id.btnClear:
            {
                // reset tat ca gia tri va co hieu
                temp = "";
                rq="";
                countBrac = 0;
                flagBrac = 0;
                flagDot = 0;
                countDot = 0;
                fistTemp = 0;
                fistrq = 0;
                flagPM = -1;
                flagFC = 0;
                txtAll.setText(temp);
                break;
            }
            case R.id.btnMul:
            {
                // thiết lập các cờ hiệu xử lý
                flagFC = 0;
                flagBrac = 0;
                flagDot = 0;
                countDot = 0;
                temp+=btnMul.getText() ;
                rq+= "<font color='#1E88E5'>×</font>";

                // đếm số lượng dấu ngoặc để xác định dấu +/-

                    fistrq = rq.length();
                    fistTemp = temp.length();
                    flagPM = -1;



                txtAll.setText(Html.fromHtml(rq),TextView.BufferType.SPANNABLE);

                break;
            }
            case R.id.btnDiv:
            {
                // thiết lập các cờ hiệu xử lý
                flagFC = 0;
                flagBrac = 0;
                flagDot = 0;
                countDot = 0;
                temp+=btnDiv.getText() ;
                rq+= "<font color='#1E88E5'>÷</font>";

                // đếm số lượng dấu ngoặc để xác định dấu +/-
                if(countBrac != 0)
                {
                    fistrq = rq.length();
                    fistTemp = temp.length();
                    flagPM = -1;
                }


                txtAll.setText(Html.fromHtml(rq),TextView.BufferType.SPANNABLE);

                break;
            }
            case R.id.btnSub:
            {
                // thiết lập các cờ hiệu xử lý
                flagFC = 0;
                flagBrac = 0;
                flagDot = 0;
                countDot = 0;
                temp+=btnSub.getText() ;
                rq+= "<font color='#1E88E5'>-</font>";

                // đếm số lượng dấu ngoặc để xác định dấu +/-
                if(countBrac != 0)
                {
                    fistrq = rq.length();
                    fistTemp = temp.length();
                    flagPM = -1;
                }


                txtAll.setText(Html.fromHtml(rq),TextView.BufferType.SPANNABLE);
                break;
            }
            case R.id.btnPlus:
            {
                // thiết lập các cờ hiệu xử lý
                flagFC = 0;
                flagBrac = 0;
                flagDot = 0;
                countDot = 0;
                temp+=btnPlus.getText() ;
                rq+= "<font color='#1E88E5'>+</font>";

                // đếm số lượng dấu ngoặc để xác định dấu +/-
                if(countBrac != 0)
                {
                    fistrq = rq.length();
                    fistTemp = temp.length();
                    flagPM = -1;
                }


                txtAll.setText(Html.fromHtml(rq),TextView.BufferType.SPANNABLE);
                break;
            }
            case R.id.btn0:
            {
                if(countBrac == 0)
                {
                    flagBrac = 1; // xac dinh phia truoc la so de them dau * truoc dau ngoac
                }
                flagFC = 1;
                flagDot = 1; // xac dinh co the them dau . phia sau so
                temp += btn0.getText();
                rq+= btn0.getText();

                txtAll.setText(Html.fromHtml(rq),TextView.BufferType.SPANNABLE);
                break;
            }
            case R.id.btn1:
            {
                if(countBrac == 0)
                {
                    flagBrac = 1; // xac dinh phia truoc la so de them dau * truoc dau ngoac
                }
                flagFC = 1;
                flagDot = 1; // xac dinh co the them dau . phia sau so
                temp += btn1.getText();
                rq += btn1.getText();
                txtAll.setText(Html.fromHtml(rq),TextView.BufferType.SPANNABLE);
                break;
            }
            case R.id.btn2:
            {
                if(countBrac == 0)
                {
                    flagBrac = 1; // xac dinh phia truoc la so de them dau * truoc dau ngoac
                }
                flagFC = 1;
                flagDot = 1; // xac dinh co the them dau . phia sau so
                temp += btn2.getText();
                rq += btn2.getText();
                txtAll.setText(Html.fromHtml(rq),TextView.BufferType.SPANNABLE);
                break;
            }
            case R.id.btn3:
            {
                if(countBrac == 0)
                {
                    flagBrac = 1; // xac dinh phia truoc la so de them dau * truoc dau ngoac
                }
                flagFC = 1;
                flagDot = 1; // xac dinh co the them dau . phia sau so
                temp += btn3.getText();
                rq += btn3.getText();
                txtAll.setText(Html.fromHtml(rq),TextView.BufferType.SPANNABLE);
                break;
            }
            case R.id.btn4:
            {
                if(countBrac == 0)
                {
                    flagBrac = 1; // xac dinh phia truoc la so de them dau * truoc dau ngoac
                }
                flagFC = 1;
                flagDot = 1; // xac dinh co the them dau . phia sau so
                temp += btn4.getText();
                rq += btn4.getText();
                txtAll.setText(Html.fromHtml(rq),TextView.BufferType.SPANNABLE);
                break;
            }
            case R.id.btn5:
            {
                if(countBrac == 0)
                {
                    flagBrac = 1; // xac dinh phia truoc la so de them dau * truoc dau ngoac
                }
                flagFC = 1;
                flagDot = 1; // xac dinh co the them dau . phia sau so
                temp += btn5.getText();
                rq += btn5.getText();
                txtAll.setText(Html.fromHtml(rq),TextView.BufferType.SPANNABLE);
                break;
            }
            case R.id.btn6:
            {
                if(countBrac == 0)
                {
                    flagBrac = 1; // xac dinh phia truoc la so de them dau * truoc dau ngoac
                }
                flagFC = 1;
                flagDot = 1; // xac dinh co the them dau . phia sau so
                temp += btn6.getText();
                rq += btn6.getText();
                txtAll.setText(Html.fromHtml(rq),TextView.BufferType.SPANNABLE);
                break;
            }
            case R.id.btn7:
            {
                if(countBrac == 0)
                {
                    flagBrac = 1; // xac dinh phia truoc la so de them dau * truoc dau ngoac
                }
                flagFC = 1;
                flagDot = 1; // xac dinh co the them dau . phia sau so
                temp += btn7.getText();
                rq += btn7.getText();
                txtAll.setText(Html.fromHtml(rq),TextView.BufferType.SPANNABLE);
                break;
            }
            case R.id.btn8:
            {
                if(countBrac == 0)
                {
                    flagBrac = 1; // xac dinh phia truoc la so de them dau * truoc dau ngoac
                }
                flagFC = 1;
                flagDot = 1; // xac dinh co the them dau . phia sau so
                temp += btn8.getText();
                rq += btn8.getText();
                txtAll.setText(Html.fromHtml(rq),TextView.BufferType.SPANNABLE);
                break;
            }
            case R.id.btn9:
            {
                if(countBrac == 0)
                {
                    flagBrac = 1; // xac dinh phia truoc la so de them dau * truoc dau ngoac
                }
                flagFC = 1;
                flagDot = 1; // xac dinh co the them dau . phia sau so
                temp += btn9.getText();
                rq += btn9.getText();
                txtAll.setText(Html.fromHtml(rq),TextView.BufferType.SPANNABLE);
                break;
            }
        }
    }

    // Hàm tính toán
    private String TinhToan(String req) {
        String result = "";
        String baiToan = req.trim();
        if (baiToan.trim().length() > 0) {
            Tinhtoan tt = new Tinhtoan(baiToan);
            if (tt.isError()) {
                result = tt.getError();
            } else {
                DecimalFormat df = new DecimalFormat("#.####");
                df.setRoundingMode(RoundingMode.CEILING);
                result = df.format(tt.getResult());
            }
        }

        return  result;
    }


}
