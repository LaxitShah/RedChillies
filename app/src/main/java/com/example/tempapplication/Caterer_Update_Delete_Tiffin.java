package com.example.tempapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Caterer_Update_Delete_Tiffin extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://tempapplication-104fd-default-rtdb.firebaseio.com/");
    Button updateBtn,deleteBtn;
    EditText tiffinName,menuName,menuPrice;
    String menuNameTxt,tiffinNameTxt,menuPriceTxt,menuID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caterer_update_delete_tiffin);

        tiffinName=(EditText) findViewById(R.id.ct_update_ed_tname);
        menuName=(EditText) findViewById(R.id.ct_update_ed_mname);
        menuPrice=(EditText) findViewById(R.id.ct_update_ed_mprice);



        updateBtn=findViewById(R.id.ct_update_btn);
        deleteBtn=findViewById(R.id.ct_delete_btn);

        menuID=getIntent().getStringExtra("KEY_LIST_1");

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuNameTxt=menuName.getText().toString();
                tiffinNameTxt=tiffinName.getText().toString();
                menuPriceTxt=menuPrice.getText().toString();

                databaseReference.child("Menu").child(menuID).child("menu_name").setValue(menuNameTxt);
                databaseReference.child("Menu").child(menuID).child("menu_price").setValue(menuPriceTxt);
                databaseReference.child("Menu").child(menuID).child("tiffin_name").setValue(tiffinNameTxt);

            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Menu").child(menuID).removeValue();
                Intent intent=new Intent(Caterer_Update_Delete_Tiffin.this,Caterer_View_Tiffin.class);
                startActivity(intent);
                finish();
            }
        });

    }
}