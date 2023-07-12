package com.example.tempapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


public class User_Tiffin_Order_Details extends AppCompatActivity {
    EditText date2,time2,et_Qty;
    TextView tv_Qty,tv_Time,tv_Date,tv_Amount,tv_time;
    Button continued_button;
    String string,strTime,strDate,strMenuId,strCatererID,strUserId,strMenuPrice;
    DatePickerDialog datePickerDialog1;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance("https://tempapplication-104fd-default-rtdb.firebaseio.com/");
    DatabaseReference databaseReference=firebaseDatabase.getReference("Tiffin_Order");

    String QuantityString;
    String strPrice;
    int totalPrice;
    String strQuantity;
    Integer intPrice,intQuantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_tiffin_order_details);

        tv_Qty = (TextView) findViewById(R.id.tiffin_tv_qty);
        et_Qty = (EditText) findViewById(R.id.tiffin_et_qty);
        tv_Time= (TextView) findViewById(R.id.tiffin_tv_time);
        time2 = (EditText) findViewById(R.id.tiffin_et_time);
        tv_Date = (TextView) findViewById(R.id.tiffin_tv_date);
        date2 = (EditText) findViewById(R.id.tiffin_et_date);
//        tv_Amount = (TextView) findViewById(R.id.tiffin_tv_amt);
//        et_Amount = (EditText) findViewById(R.id.tiffin_et_amt);

        strQuantity=et_Qty.getText().toString();



        SharedPreferences getUserTiffinOrder=getSharedPreferences("USER_SHARE_MENU_DETAILS",MODE_PRIVATE);
        strPrice=getUserTiffinOrder.getString("USER_TIFFIN_MENU_PRICE","");
        strMenuId=getUserTiffinOrder.getString("USER_TIFFIN_MENU_ID","");
        strCatererID=getUserTiffinOrder.getString("USER_TIFFIN_CATERER_ID","");
        strPrice=getUserTiffinOrder.getString("USER_TIFFIN_MENU_PRICE","");


        SharedPreferences getUSerDetails=getSharedPreferences("User_Share_Details",MODE_PRIVATE);
        strUserId=getUSerDetails.getString("User_Share_Phone","");





//        intPrice=Integer.parseInt(strPrice);
//        intQuantity=Integer.parseInt(strQuantity);
//        totalPrice=intPrice*intQuantity;

//        et_Amount.setText(totalPrice);

        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c=Calendar.getInstance();
                int mYear= c.get(Calendar.YEAR);
                int mMonth=c.get(Calendar.MONTH);
                int mDay=c.get(Calendar.DAY_OF_WEEK);
                datePickerDialog1=new DatePickerDialog(User_Tiffin_Order_Details.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayofweek) {
                        date2.setText(dayofweek + "/"+(month+1)+"/"+year);
                        strDate=dayofweek + "/"+(month+1)+"/"+year;
                    }
                },mYear,mMonth,mDay);
                datePickerDialog1.show();
            }
        });
        time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime= Calendar.getInstance();
                int hour=mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute=mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(User_Tiffin_Order_Details.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time2.setText(selectedHour + ":" + selectedMinute);
                        strTime=selectedHour + ":" + selectedMinute;
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        continued_button=(Button) findViewById(R.id.continue_button);
        continued_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadtofirebase();
                Intent intent=new Intent(User_Tiffin_Order_Details.this,User_View_Tiffin_Order_Details.class);
//                string=et_Amount.getText().toString();
                intent.putExtra("value",string);
                startActivity(intent);
                finish();
            }
        });

    }

    private void uploadtofirebase() {

        QuantityString =et_Qty.getText().toString();
         String TimeString=strTime;
         String DateString=strDate;

        intPrice=Integer.parseInt(strPrice);
        intQuantity=Integer.parseInt(QuantityString);
        totalPrice=intQuantity*intPrice;

        strMenuPrice=Integer.toString(totalPrice);

        String tiffinOrderId=databaseReference.push().getKey();

        SharedPreferences shared_Tiffin_Order_Details=getSharedPreferences("USER_SHARE_TIFFIN_ORDER_DETAILS",MODE_PRIVATE);
        SharedPreferences.Editor editor=shared_Tiffin_Order_Details.edit();
            editor.putString("USER_SHARE_TIFFIN_ORDER_TIFFIN_ORDER_ID",tiffinOrderId);
        editor.putString("USER_SHARE_TIFFIN_ORDER_TIFFIN_ORDER_DATE",strDate);
        editor.putString("USER_SHARE_TIFFIN_ORDER_TIFFIN_ORDER_TIME",strTime);
        editor.putString("USER_SHARE_TIFFIN_ORDER_TIFFIN_ORDER_QUANTITY",QuantityString);
        editor.putString("USER_SHARE_TIFFIN_ORDER_TIFFIN_ORDER_AMOUNT",strMenuPrice);
        editor.putString("USER_SHARE_TIFFIN_ORDER_TIFFIN_ORDER_MENU_ID",strMenuId);
        editor.putString("USER_SHARE_TIFFIN_ORDER_TIFFIN_ORDER_CATERER_ID",strCatererID);
        editor.putString("USER_SHARE_TIFFIN_ORDER_TIFFIN_ORDER_USER_ID",strUserId);
        editor.commit();





        if(QuantityString.isEmpty()||TimeString.isEmpty()||DateString.isEmpty())
        {
            Toast.makeText(User_Tiffin_Order_Details.this, "Please fill all details", Toast.LENGTH_SHORT).show();
        }
        else
        {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    TiffinOrderDataHolder tiffinOrderDataHolder=new TiffinOrderDataHolder();
                     tiffinOrderDataHolder.setOrder_id(tiffinOrderId);
                     tiffinOrderDataHolder.setOrder_date(strDate);
                     tiffinOrderDataHolder.setOrder_time(strTime);
                     tiffinOrderDataHolder.setOrder_quantity(QuantityString);
                     tiffinOrderDataHolder.setOrder_amount(totalPrice);
                     tiffinOrderDataHolder.setMenu_id(strMenuId);
                     tiffinOrderDataHolder.setCaterer_id(strCatererID);
                     tiffinOrderDataHolder.setUser_id(strUserId);
                     databaseReference.child(tiffinOrderId).setValue(tiffinOrderDataHolder);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


    }
}