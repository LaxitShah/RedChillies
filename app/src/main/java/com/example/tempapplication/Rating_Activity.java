package com.example.tempapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Rating_Activity extends AppCompatActivity {

    Button submitRating;
    RatingBar ratingStars;
    float myRating =0;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance("https://tempapplication-104fd-default-rtdb.firebaseio.com/");
    DatabaseReference databaseReference=firebaseDatabase.getReference("Rating_Details");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        ratingStars = findViewById(R.id.ratingStars);
        submitRating = findViewById(R.id.submitRating);




        ratingStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b)

            {
                int rating =(int) v;
                String message = null;
                myRating = (int)ratingBar.getRating();
                switch (rating)
                {
                    case 1:
                        message = "Sorry to hear that!";
                        break;
                    case 2:
                        message = "We hope to serve better";
                        break;
                    case 3:
                        message = "Thanks";
                        break;
                    case 4:
                        message = "We're Happy to serve you Thanks!";
                        break;
                    case 5:
                        message = "Thanks you're the best";
                        break;

                }
                Toast.makeText(Rating_Activity.this,message,Toast.LENGTH_SHORT).show();

            }
        });
        submitRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadtofirebase();
                Toast.makeText(Rating_Activity.this,String.valueOf(myRating),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Rating_Activity.this,Homepage.class);
                startActivity(intent);
                finish();
            }
        });




    }

    private void uploadtofirebase() {

        SharedPreferences getUserViewPackageInfo=getSharedPreferences("USER_SHARE_TIFFIN_ORDER_DETAILS",MODE_PRIVATE);
        //here payment id will come
        String strOrderID=getUserViewPackageInfo.getString("USER_SHARE_TIFFIN_ORDER_TIFFIN_ORDER_ID","");
        String strUserID=getUserViewPackageInfo.getString("USER_SHARE_TIFFIN_ORDER_TIFFIN_ORDER_USER_ID","");
        String RatingId=databaseReference.push().getKey();

        SharedPreferences getEventDetails=getSharedPreferences("USER_SHARE_PACKAGE__ORDER_DETAILS",MODE_PRIVATE);
        String  strBookingID=getEventDetails.getString("USER_SHARE_EVENT_BOOKING_DETAILS_BOOKING_ID","");
        String strUserEventId=getEventDetails.getString("USER_SHARE_EVENT_BOOKING_DETAILS_BOOKING_USER_ID","");


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                RatingDataHolder ratingDataHolder=new RatingDataHolder();
                ratingDataHolder.setMyRating(myRating);
                ratingDataHolder.setRating_ID(RatingId);
                if(!strOrderID.isEmpty()) {
                    ratingDataHolder.setOrder_ID(strOrderID);
                    ratingDataHolder.setUser_ID(strUserID);
                }
                if(!strBookingID.isEmpty())
                {
                    ratingDataHolder.setEvent_booking_id(strBookingID);
                    ratingDataHolder.setUser_ID(strUserEventId);
                }
                databaseReference.child(RatingId).setValue(ratingDataHolder);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}