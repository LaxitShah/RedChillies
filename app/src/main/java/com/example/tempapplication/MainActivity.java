package com.example.tempapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://tempapplication-104fd-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText firstName=(EditText) findViewById(R.id.reg_fname);
        final EditText lastName=(EditText) findViewById(R.id.reg_lname);
        final EditText phoneNo=(EditText) findViewById(R.id.reg_phone);
        final EditText password=(EditText) findViewById(R.id.reg_password);
        final EditText email=(EditText) findViewById(R.id.reg_email);
        final EditText address=(EditText) findViewById(R.id.reg_address);

        final Button registerButton=findViewById(R.id.reg_button);
        final TextView loginClick=findViewById(R.id.reg_login_click);



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String firstNameTxt=firstName.getText().toString();
                final String lastNameTxt=lastName.getText().toString();
                final String phoneNoTxt=phoneNo.getText().toString();
                final String passwordTxt=password.getText().toString();
                final String emailTxt=email.getText().toString();
                final String addressTxt=address.getText().toString();

                if(firstNameTxt.isEmpty()||lastNameTxt.isEmpty()||phoneNoTxt.isEmpty()||passwordTxt.isEmpty()||emailTxt.isEmpty()||addressTxt.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Please fill all details", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(phoneNoTxt))
                            {
                                Toast.makeText(MainActivity.this, "Already Registered,Try Login", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                databaseReference.child("users").child(phoneNoTxt).child("first_name").setValue(firstNameTxt);
                                databaseReference.child("users").child(phoneNoTxt).child("last_name").setValue(lastNameTxt);
                                databaseReference.child("users").child(phoneNoTxt).child("email").setValue(emailTxt);
                                databaseReference.child("users").child(phoneNoTxt).child("password").setValue(passwordTxt);
                                databaseReference.child("users").child(phoneNoTxt).child("address").setValue(addressTxt);
                                databaseReference.child("users").child(phoneNoTxt).child("phone").setValue(phoneNoTxt);


                                Toast.makeText(MainActivity.this, "Registeration Success", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        });
        loginClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}