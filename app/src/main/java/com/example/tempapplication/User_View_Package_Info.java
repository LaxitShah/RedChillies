package com.example.tempapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class User_View_Package_Info extends AppCompatActivity {

    TextView tvPN,tvEN,tvPP,tvPD;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_package_info);

        tvPN=(TextView) findViewById(R.id.UVPI_PACKAGE_NAME);
        tvEN=(TextView) findViewById(R.id.UVPI_EVENT_NAME);
        tvPP=(TextView) findViewById(R.id.UVPI_PACKAGE_PRICE);
        tvPD=(TextView) findViewById(R.id.UVPI_PACKAGE_DESC);

        btn=(Button)findViewById(R.id.UVPI_BOOK_BTN);

        SharedPreferences getUserViewPackageInfo=getSharedPreferences("USER_SHARE_PACKAGE_DETAILS",MODE_PRIVATE);
        String strPN=getUserViewPackageInfo.getString("USER_PACKAGE_NAME","");
        String strEN=getUserViewPackageInfo.getString("USER_PACKAGE_EVENT_NAME","");
        String strPP=getUserViewPackageInfo.getString("USER_PACKAGE_PRICE","");
        String strPD=getUserViewPackageInfo.getString("USER_PACKAGE_DESC","");


        tvPN.setText("Package Name:- "+strPN);
        tvEN.setText("Event Name:- "+strEN);
        tvPP.setText("Package Price:- "+strPP);
        tvPD.setText("Package Description:- "+strPD);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(User_View_Package_Info.this,User_Package_Order_Details.class);
                startActivity(intent);
            }
        });

    }
}