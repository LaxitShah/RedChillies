package com.example.tempapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Caterer_Login extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://tempapplication-104fd-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caterer_login);

        final EditText phone=findViewById(R.id.cl_phone);
        final EditText password=findViewById(R.id.cl_password);

        final Button loginBtn=findViewById(R.id.cl_btn_login);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phoneTxt=phone.getText().toString();
                final String passwordTxt=password.getText().toString();


                if(phoneTxt.isEmpty()||passwordTxt.isEmpty())
                {
                    Toast.makeText(Caterer_Login.this, "Try Again!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    databaseReference.child("caterers").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(phoneTxt))
                            {
                                final String getPassword=snapshot.child(phoneTxt).child("password").getValue(String.class);

                                if(getPassword.equals(passwordTxt))
                                {

                                    SharedPreferences sp_cater_registeration=getSharedPreferences("MYPREF_CATER_REG",MODE_PRIVATE);
                                    SharedPreferences.Editor editor=sp_cater_registeration.edit();
                                    editor.putString("CATER_LOG_PHONE",phoneTxt);
                                    editor.putString("CATER_LOG_PASSWORD",passwordTxt);
                                    editor.commit();


                                    Toast.makeText(Caterer_Login.this,"Successfully Logging",Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(Caterer_Login.this,Caterer_Home.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(Caterer_Login.this, "Incorrect Phone or Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(Caterer_Login.this, "Incorrect Phone or Password", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

    }
}