package com.example.tempapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.awt.font.TextAttribute;

public class User_Profile_Update_Delete extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://tempapplication-104fd-default-rtdb.firebaseio.com/");
     Button updateBtn,deleteBtn;
     EditText firstName,lastName,email,password,address;
     String firstNameTxt,lastNameTxt,emailTxt,passwordTxt,addressTxt,user_phone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_update_delete);

         firstName=(EditText)findViewById(R.id.userp_update_ed_fname);
         lastName=(EditText)findViewById(R.id.userp_update_ed_lname);
         email=(EditText)findViewById(R.id.userp_update_ed_fname);
         password=(EditText)findViewById(R.id.userp_update_ed_password);
         address=(EditText)findViewById(R.id.userp_update_ed_address);


          updateBtn=findViewById(R.id.userp_update_btn) ;
        deleteBtn=findViewById(R.id.userp_delete_btn) ;
        SharedPreferences share_user_login_details=getSharedPreferences("User_Share_Details",MODE_PRIVATE);
        user_phone=share_user_login_details.getString("User_Share_Phone","");

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 firstNameTxt=firstName.getText().toString();
                 lastNameTxt=lastName.getText().toString();
                 emailTxt=email.getText().toString();
                 passwordTxt=password.getText().toString();
                 addressTxt=address.getText().toString();



                databaseReference.child("users").child(user_phone).child("first_name").setValue(firstNameTxt);
                databaseReference.child("users").child(user_phone).child("last_name").setValue(lastNameTxt);
                databaseReference.child("users").child(user_phone).child("email").setValue(emailTxt);
                databaseReference.child("users").child(user_phone).child("password").setValue(passwordTxt);
                databaseReference.child("users").child(user_phone).child("address").setValue(addressTxt);

            }
        });


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("users").child(user_phone).removeValue();
                Intent intent=new Intent(User_Profile_Update_Delete.this,Welcome_Button.class);
                startActivity(intent);
                finish();
            }
        });





    }
}