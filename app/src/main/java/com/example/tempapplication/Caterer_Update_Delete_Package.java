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

public class Caterer_Update_Delete_Package extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://tempapplication-104fd-default-rtdb.firebaseio.com/");
    Button updateBtn,deleteBtn;
    EditText packageName,eventName,packagePrice;
    String packageNameTxt,eventNameTxt,packagePriceTxt,packageID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caterer_update_delete_package);

        packageName=(EditText) findViewById(R.id.cupdate_ed_pname);
        eventName=(EditText) findViewById(R.id.cupdate_ed_ename);
        packagePrice=(EditText) findViewById(R.id.cupdate_ed_pprice);
        

            updateBtn=findViewById(R.id.cupdate_btn);
            deleteBtn=findViewById(R.id.cdelete_btn);

            packageID=getIntent().getStringExtra("KeyList");


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                packageNameTxt=packageName.getText().toString();
                eventNameTxt=eventName.getText().toString();
                packagePriceTxt=packagePrice.getText().toString();

                databaseReference.child("Package").child(packageID).child("package_name").setValue(packageNameTxt);
                databaseReference.child("Package").child(packageID).child("package_price").setValue(packagePriceTxt);
                databaseReference.child("Package").child(packageID).child("package_event_name").setValue(eventNameTxt);

            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Package").child(packageID).removeValue();
                Intent intent=new Intent(Caterer_Update_Delete_Package.this,Caterer_View_Package.class);
                startActivity(intent);
                finish();
            }
        });




    }
}