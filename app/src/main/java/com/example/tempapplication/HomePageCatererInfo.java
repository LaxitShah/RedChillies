package com.example.tempapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomePageCatererInfo extends AppCompatActivity {
    TextView tvFN,tvLN,tvPhone,tvEmail,tvCity,tvAddress;
    Button eventBtn,tiffinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_caterer_info);

        tvFN=(TextView) findViewById(R.id.HPCI_FNAME);
        tvLN=(TextView) findViewById(R.id.HPCI_LNAME);
        tvPhone=(TextView) findViewById(R.id.HPCI_PHONE);
        tvEmail=(TextView) findViewById(R.id.HPCI_Email);
        tvCity=(TextView) findViewById(R.id.HPCI_CITY);
        tvAddress=(TextView) findViewById(R.id.HPCI_Address);
        tiffinBtn=(Button)findViewById(R.id.HPCI_TIFFIN_BUTTON);
        eventBtn=(Button)findViewById(R.id.HPCI_EVENT_BUTTON);

        SharedPreferences getCatersInfo=getSharedPreferences("MYPREF_CATERER_INFO",MODE_PRIVATE);
        String strFN=getCatersInfo.getString("CATERER_INFO_FN","");
        String strLN=getCatersInfo.getString("CATERER_INFO_LN","");
        String strPhone=getCatersInfo.getString("CATERER_INFO_Phone","");
//        String strPassword=getCatersInfo.getString("CATER_REG_PASSWORD","");
        String strAddress=getCatersInfo.getString("CATERER_INFO_Address","");
        String strCity=getCatersInfo.getString("CATERER_INFO_CITY","");
        String strEmail=getCatersInfo.getString("CATERER_INFO_Email","");



        tvFN.setText("First Name:- "+strFN);
        tvLN.setText("Last Name:- "+strLN);
        tvPhone.setText("Phone:-  "+strPhone);
        tvEmail.setText("Email:- "+strEmail);
        tvCity.setText("City:- "+strCity);
        tvAddress.setText("Address:-  "+strAddress);


        tiffinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomePageCatererInfo.this,User_View_Tiffin.class);
                startActivity(intent);
            }
        });

        eventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HomePageCatererInfo.this,User_View_Event.class);
                startActivity(i);
            }
        });





    }
}