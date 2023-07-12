package com.example.tempapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Payment_Option extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance("https://tempapplication-104fd-default-rtdb.firebaseio.com/");
    DatabaseReference databaseReference=firebaseDatabase.getReference("Payment_Details");
    Button payBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_option);

        payBtn=(Button) findViewById(R.id.pay_op_cod);


        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    uploadtofirebase();
                Intent intent=new Intent(Payment_Option.this,Rating_Activity.class);

                startActivity(intent);
                finish();
            }
        });

    }

    private void uploadtofirebase() {

        SharedPreferences getPaymentDetails=getSharedPreferences("USER_SHARE_TIFFIN_ORDER_DETAILS",MODE_PRIVATE);
        String strAmount=getPaymentDetails.getString("USER_SHARE_TIFFIN_ORDER_TIFFIN_ORDER_AMOUNT","");
        String strOrderID=getPaymentDetails.getString("USER_SHARE_TIFFIN_ORDER_TIFFIN_ORDER_ID","");


        String strCatererID=getPaymentDetails.getString("USER_SHARE_TIFFIN_ORDER_TIFFIN_ORDER_CATERER_ID","");
        String strUserID=getPaymentDetails.getString("USER_SHARE_TIFFIN_ORDER_TIFFIN_ORDER_USER_ID","");
   
        SharedPreferences getEventDetails=getSharedPreferences("USER_SHARE_PACKAGE__ORDER_DETAILS",MODE_PRIVATE);
        String  strBookingID=getEventDetails.getString("USER_SHARE_EVENT_BOOKING_DETAILS_BOOKING_ID","");
        String strUserEventId=getEventDetails.getString("USER_SHARE_EVENT_BOOKING_DETAILS_BOOKING_USER_ID","");
        String strCatererEventID=getEventDetails.getString("USER_SHARE_EVENT_BOOKING_DETAILS_BOOKING_CATERER_ID","");
        String strPackageID=getEventDetails.getString("USER_SHARE_EVENT_BOOKING_DETAILS_BOOKING_PACKAGE_ID","");
        String totalPackagePrice=getEventDetails.getString("USER_SHARE_EVENT_BOOKING_DETAILS_BOOKING_TOTAL_PRICE","");

        SharedPreferences getVenueDetails=getSharedPreferences("USER_SHARE_VENUE_ORDER_DETAILS",MODE_PRIVATE);
        String strVenueID=getVenueDetails.getString("USER_SHARE_VENUE_ORDER_VENUE_ID","");


        String strPaymentID=databaseReference.push().getKey();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            PaymentDataHolder paymentDataHolder=new PaymentDataHolder();
            paymentDataHolder.setPayment_id(strPaymentID);


            if(!strOrderID.isEmpty()) {
                paymentDataHolder.setCaterer_id(strCatererID);
                paymentDataHolder.setOrder_id(strOrderID);
                paymentDataHolder.setUser_id(strUserID);
                paymentDataHolder.setPayment_amount(strAmount);
            }

            if(!strBookingID.isEmpty())
            {
                paymentDataHolder.setEvent_booking_id(strBookingID);
                paymentDataHolder.setUser_id(strUserEventId);
                paymentDataHolder.setCaterer_id(strCatererEventID);
                paymentDataHolder.setVenue_id(strVenueID);
                paymentDataHolder.setPackage_id(strPackageID);
                paymentDataHolder.setPayment_amount(totalPackagePrice);
            }


            databaseReference.child(strPaymentID).setValue(paymentDataHolder);


        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });


    }
}