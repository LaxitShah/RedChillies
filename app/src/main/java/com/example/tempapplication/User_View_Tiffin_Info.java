package com.example.tempapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class User_View_Tiffin_Info extends AppCompatActivity {


    TextView tvTN,tvMN,tvMP,tvMD;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_tiffin_info);

        btn=(Button)findViewById(R.id.UVTI_BOOK_BTN);

        tvTN=(TextView) findViewById(R.id.UVTI_TIFFIN_NAME);
        tvMN=(TextView) findViewById(R.id.UVTI_MENU_NAME);
        tvMP=(TextView) findViewById(R.id.UVTI_MENU_PRICE);
        tvMD=(TextView) findViewById(R.id.UVTI_MENU_DESC);
        SharedPreferences getUserViewPackageInfo=getSharedPreferences("USER_SHARE_MENU_DETAILS",MODE_PRIVATE);
        String strTN=getUserViewPackageInfo.getString("USER_TIFFIN_NAME","");
        String strMN=getUserViewPackageInfo.getString("USER_TIFFIN_MENU_NAME","");
        String strMP=getUserViewPackageInfo.getString("USER_TIFFIN_MENU_PRICE","");
        String strMD=getUserViewPackageInfo.getString("USER_TIFFIN_MENU_DESC","");

        tvTN.setText("Tiffin Name: -"+strTN);
        tvMN.setText("Menu Name: -"+strMN);
        tvMP.setText("Menu Price: -"+strMP);
        tvMD.setText("Menu Description : -"+strMD);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(User_View_Tiffin_Info.this,User_Tiffin_Order_Details.class);
                startActivity(intent);
            }
        });

    }
}