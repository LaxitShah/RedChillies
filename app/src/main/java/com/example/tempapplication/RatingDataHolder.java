package com.example.tempapplication;

public class RatingDataHolder {

    float myRating;
    String Order_ID,User_ID,Rating_ID,Payment_ID,event_booking_id;

    public String getEvent_booking_id() {
        return event_booking_id;
    }

    public void setEvent_booking_id(String event_booking_id) {
        this.event_booking_id = event_booking_id;
    }

    public float getMyRating() {
        return myRating;
    }

    public void setMyRating(float myRating) {
        this.myRating = myRating;
    }

    public String getOrder_ID() {
        return Order_ID;
    }

    public void setOrder_ID(String order_ID) {
        Order_ID = order_ID;
    }

    public String getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(String user_ID) {
        User_ID = user_ID;
    }

    public String getRating_ID() {
        return Rating_ID;
    }

    public void setRating_ID(String rating_ID) {
        Rating_ID = rating_ID;
    }

    public String getPayment_ID() {
        return Payment_ID;
    }

    public void setPayment_ID(String payment_ID) {
        Payment_ID = payment_ID;
    }
}
