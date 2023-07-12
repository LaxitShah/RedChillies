package com.example.tempapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User_Venu_Order_Details extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance("https://tempapplication-104fd-default-rtdb.firebaseio.com/");
    DatabaseReference databaseReference=firebaseDatabase.getReference("Venue_Details");
    EditText venue_address,venue_name;
    String str_event_booking_id,str_venue_id,str_venue_name,str_venue_address,str_package_id,str_caterer_id,str_user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_venu_order_details);
        TextView venue = findViewById(R.id.be_tv_venue);

        venue_name = findViewById(R.id.be_venue_et_name);
        venue_address=findViewById(R.id.be_venue_et_address);


        Button book_btn= findViewById(R.id.be_bookbtn);
        SharedPreferences getEventBookingDetails=getSharedPreferences("USER_SHARE_PACKAGE__ORDER_DETAILS",MODE_PRIVATE);
        str_user_id=getEventBookingDetails.getString("USER_SHARE_EVENT_BOOKING_DETAILS_BOOKING_USER_ID","");
        str_caterer_id=getEventBookingDetails.getString("USER_SHARE_EVENT_BOOKING_DETAILS_BOOKING_CATERER_ID","");
        str_package_id=getEventBookingDetails.getString("USER_SHARE_EVENT_BOOKING_DETAILS_BOOKING_PACKAGE_ID","");
        str_event_booking_id=getEventBookingDetails.getString("USER_SHARE_EVENT_BOOKING_DETAILS_BOOKING_ID","");

        book_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadtofirebase();
                Intent intent = new Intent(User_Venu_Order_Details.this, Payment_Option.class);
                startActivity(intent);
                finish();
            }
        });




    }

    private void uploadtofirebase() {
        str_venue_id=databaseReference.push().getKey();

        SharedPreferences shared_Venue_Order_Details=getSharedPreferences("USER_SHARE_VENUE_ORDER_DETAILS",MODE_PRIVATE);
        SharedPreferences.Editor editor=shared_Venue_Order_Details.edit();
        editor.putString("USER_SHARE_VENUE_ORDER_VENUE_ID",str_venue_id);
        editor.putString("USER_SHARE_VENUE_ORDER_VENUE_Name",str_venue_name);
        editor.putString("USER_SHARE_VENUE_ORDER_VENUE_Address",str_venue_address);
        editor.commit();


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                str_venue_name=venue_name.getText().toString();
                str_venue_address=venue_address.getText().toString();

                VenueDataHolder venueDataHolder=new VenueDataHolder();
                venueDataHolder.setVenue_Id(str_venue_id);
                venueDataHolder.setVenue_name(str_venue_name);
                venueDataHolder.setVenue_Address(str_venue_address);
                venueDataHolder.setUser_ID(str_user_id);
                venueDataHolder.setCaterer_ID(str_caterer_id);
                venueDataHolder.setPackage_ID(str_package_id);
                venueDataHolder.setEvent_Booking_ID(str_event_booking_id);

                databaseReference.child(str_venue_id).setValue(venueDataHolder);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}