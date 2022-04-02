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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class User_Package_Order_Details extends AppCompatActivity {
    String be_st_text,str_attendee,str_time,str_date,str_package_id,str_user_id,string_caterer_id;
    String event_booking_id,str_package_price,str_total_package_price;
    EditText event_attendee;
    DatePickerDialog datePickerDialog;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance("https://tempapplication-104fd-default-rtdb.firebaseio.com/");
    DatabaseReference databaseReference=firebaseDatabase.getReference("Event_Booking");
    int totalprice;
    Integer intPrice,intAttendee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_package_order_details);


        TextView title=findViewById(R.id.be_title);
        event_attendee=findViewById(R.id.be_et_event_attendee);
        EditText time =findViewById(R.id.be_et_time);
        EditText date =findViewById(R.id.be_et_date);

        str_attendee=event_attendee.getText().toString();


        SharedPreferences getEventBookingDetails=getSharedPreferences("USER_SHARE_PACKAGE_DETAILS",MODE_PRIVATE);
         str_package_id=getEventBookingDetails.getString("USER_PACKAGE_ID","");
         string_caterer_id=getEventBookingDetails.getString("USER_PACKAGE_CATER_ID","");
           str_package_price=getEventBookingDetails.getString("USER_PACKAGE_PRICE","");
        SharedPreferences getUSerDetails=getSharedPreferences("User_Share_Details",MODE_PRIVATE);
        str_user_id=getUSerDetails.getString("User_Share_Phone","");


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c= Calendar.getInstance();
                int mYear= c.get(Calendar.YEAR);
                int mMonth=c.get(Calendar.MONTH);
                int mDay=c.get(Calendar.DAY_OF_WEEK);
                datePickerDialog=new DatePickerDialog(User_Package_Order_Details.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayofweek) {
                        date.setText(dayofweek + "/"+(month+1)+"/"+year);
                        str_date=dayofweek + "/"+(month+1)+"/"+year;
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Calendar mcurrentTime= Calendar.getInstance();
            int hour=mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute=mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(User_Package_Order_Details.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    time.setText(selectedHour + ":" + selectedMinute);
                    str_time=selectedHour + ":" + selectedMinute;
                }
            }, hour, minute, true);
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

        }
    });

    Button be_ctn_btn=findViewById(R.id.be_continue_btn);
        be_ctn_btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            uploadtofirebase();
            Intent intent=new Intent(User_Package_Order_Details.this,User_Venu_Order_Details.class);
            startActivity(intent);
            finish();
        }
    });

    }

    private void uploadtofirebase() {
        event_booking_id=databaseReference.push().getKey();
        str_attendee=event_attendee.getText().toString();

        intPrice=Integer.parseInt(str_package_price);
        intAttendee=Integer.parseInt(str_attendee);
        totalprice=intPrice*intAttendee;

        str_total_package_price=Integer.toString(totalprice);


        SharedPreferences shared_Tiffin_Order_Details=getSharedPreferences("USER_SHARE_PACKAGE__ORDER_DETAILS",MODE_PRIVATE);
        SharedPreferences.Editor editor=shared_Tiffin_Order_Details.edit();
        editor.putString("USER_SHARE_EVENT_BOOKING_DETAILS_BOOKING_ID",event_booking_id);
        editor.putString("USER_SHARE_EVENT_BOOKING_DETAILS_BOOKING_TIME",str_time);
        editor.putString("USER_SHARE_EVENT_BOOKING_DETAILS_BOOKING_ATTENDEE",str_attendee);
        editor.putString("USER_SHARE_EVENT_BOOKING_DETAILS_BOOKING_DATE",str_date);
        editor.putString("USER_SHARE_EVENT_BOOKING_DETAILS_BOOKING_USER_ID",str_user_id);
        editor.putString("USER_SHARE_EVENT_BOOKING_DETAILS_BOOKING_CATERER_ID",string_caterer_id);
        editor.putString("USER_SHARE_EVENT_BOOKING_DETAILS_BOOKING_PACKAGE_ID",str_package_id);
        editor.putString("USER_SHARE_EVENT_BOOKING_DETAILS_BOOKING_TOTAL_PRICE",str_total_package_price);

        editor.commit();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PackageOrderDataHolder packageOrderDataHolder=new PackageOrderDataHolder();
                packageOrderDataHolder.setBooking_ID(event_booking_id);
                packageOrderDataHolder.setBook_Time(str_time);
                packageOrderDataHolder.setBook_Date(str_date);
                packageOrderDataHolder.setBook_Attendee(str_attendee);
                packageOrderDataHolder.setUser_id(str_user_id);
                packageOrderDataHolder.setCaterer_Id(string_caterer_id);
                packageOrderDataHolder.setPackage_id(str_package_id);

                databaseReference.child(event_booking_id).setValue(packageOrderDataHolder);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });








    }
}