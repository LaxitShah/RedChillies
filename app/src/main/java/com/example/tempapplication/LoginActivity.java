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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {


    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://tempapplication-104fd-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText phone=findViewById(R.id.login_phone);
        final EditText password=findViewById(R.id.login_password);

        final Button loginBtn=findViewById(R.id.btn_login);
        final TextView registerClick=findViewById(R.id.login_register_click);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phoneTxt=phone.getText().toString();
                final String passwordTxt=password.getText().toString();


                if(phoneTxt.isEmpty()||passwordTxt.isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Try Again!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(phoneTxt))
                            {
                                final String getPassword=snapshot.child(phoneTxt).child("password").getValue(String.class);


                                if(getPassword.equals(passwordTxt))
                                {
                                    SharedPreferences share_user_login_details=getSharedPreferences("User_Share_Details",MODE_PRIVATE);
                                    SharedPreferences.Editor editor=share_user_login_details.edit();
                                    editor.putString("User_Share_Phone",phoneTxt);
                                    editor.commit();
                                    Toast.makeText(LoginActivity.this,"Successfully Logging",Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(LoginActivity.this,Homepage.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(LoginActivity.this, "Incorrect Phone or Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "Incorrect Phone or Password", Toast.LENGTH_SHORT).show();

                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }


            }
        });

        registerClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });



    }
}