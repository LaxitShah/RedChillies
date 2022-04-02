package com.example.tempapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Caterer_Profile_Update_Delete extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://tempapplication-104fd-default-rtdb.firebaseio.com/");
    Button updateBtn,deleteBtn;
    EditText firstName,lastName,email,password,address,city;
    String firstNameTxt,lastNameTxt,emailTxt,passwordTxt,addressTxt,caterer_phone,cityTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caterer_profile_update_delete);


        firstName=(EditText)findViewById(R.id.cp_update_ed_fname);
        lastName=(EditText)findViewById(R.id.cp_update_ed_lname);
        email=(EditText)findViewById(R.id.cp_update_ed_email);
        password=(EditText)findViewById(R.id.cp_update_ed_password);
        address=(EditText)findViewById(R.id.cp_update_ed_haddress);
        city=(EditText) findViewById(R.id.cp_update_ed_city);

        updateBtn=findViewById(R.id.cp_update_btn) ;
        deleteBtn=findViewById(R.id.cp_delete_btn) ;

        SharedPreferences share_user_login_details=getSharedPreferences("MYPREF_CATER_REG",MODE_PRIVATE);
        caterer_phone=share_user_login_details.getString("CATER_LOG_PHONE","");

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstNameTxt=firstName.getText().toString();
                lastNameTxt=lastName.getText().toString();
                emailTxt=email.getText().toString();
                passwordTxt=password.getText().toString();
                addressTxt=address.getText().toString();
                cityTxt=city.getText().toString();



                databaseReference.child("caterers").child(caterer_phone).child("first_name").setValue(firstNameTxt);
                databaseReference.child("caterers").child(caterer_phone).child("last_name").setValue(lastNameTxt);
                databaseReference.child("caterers").child(caterer_phone).child("email").setValue(emailTxt);
                databaseReference.child("caterers").child(caterer_phone).child("password").setValue(passwordTxt);
                databaseReference.child("caterers").child(caterer_phone).child("address").setValue(addressTxt);
                databaseReference.child("caterers").child(caterer_phone).child("city").setValue(cityTxt);

            }
        });


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("caterers").child(caterer_phone).removeValue();
                Intent intent=new Intent(Caterer_Profile_Update_Delete.this,Welcome_Button.class);
                startActivity(intent);
                finish();
            }
        });


    }
}