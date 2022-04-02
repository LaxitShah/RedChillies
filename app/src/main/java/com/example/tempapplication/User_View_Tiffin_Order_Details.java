package com.example.tempapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class User_View_Tiffin_Order_Details extends AppCompatActivity {

    TextView tvTP,tvDate,tvQun,tvTime;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_tiffin_order_details);

        btn=(Button)findViewById(R.id.UVTOD_Payment_btn);

        tvTP=(TextView) findViewById(R.id.UVTOD_TIFFIN_Price);
        tvDate=(TextView) findViewById(R.id.UVTOD_BOOK_DATE);
        tvQun=(TextView) findViewById(R.id.UVTOD_TIFFIN_QUANTITY);
        tvTime=(TextView) findViewById(R.id.UVTOD_BOOK_TIME);

        SharedPreferences getUserViewPackageInfo=getSharedPreferences("USER_SHARE_TIFFIN_ORDER_DETAILS",MODE_PRIVATE);
        String strTP=getUserViewPackageInfo.getString("USER_SHARE_TIFFIN_ORDER_TIFFIN_ORDER_AMOUNT","");
        String strDate=getUserViewPackageInfo.getString("USER_SHARE_TIFFIN_ORDER_TIFFIN_ORDER_DATE","");
        String strQun=getUserViewPackageInfo.getString("USER_SHARE_TIFFIN_ORDER_TIFFIN_ORDER_QUANTITY","");
        String strTime=getUserViewPackageInfo.getString("USER_SHARE_TIFFIN_ORDER_TIFFIN_ORDER_TIME","");

        tvTP.setText("Tiffin Price:- "+strTP);
        tvDate.setText("Tiffin Amount:- "+strDate);
        tvQun.setText("Tiffin Quantity:- "+strQun);
        tvTime.setText("Tiffin Time:- "+strTime);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(User_View_Tiffin_Order_Details.this,Payment_Option.class);
                startActivity(intent);
            }
        });
    }
}